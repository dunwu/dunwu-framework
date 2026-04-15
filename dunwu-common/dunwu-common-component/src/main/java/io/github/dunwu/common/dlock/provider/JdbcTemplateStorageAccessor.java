package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.LockConfiguration;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.TimeZone;

import static java.util.Objects.requireNonNull;

/**
 * Spring JdbcTemplate based implementation usable in JTA environment
 */
class JdbcTemplateStorageAccessor extends BaseStorageAccessor {

    private final JdbcTemplate jdbcTemplate;

    private final TransactionTemplate transactionTemplate;

    private final JdbcTemplateLockProvider.Configuration configuration;

    JdbcTemplateStorageAccessor(@NotNull JdbcTemplateLockProvider.Configuration configuration) {
        this.configuration = requireNonNull(configuration, "configuration can not be null");
        this.jdbcTemplate = configuration.getJdbcTemplate();
        PlatformTransactionManager transactionManager =
            configuration.getTransactionManager() != null ? configuration.getTransactionManager()
                : new DataSourceTransactionManager(jdbcTemplate.getDataSource());

        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        final String sql = String.format("CREATE TABLE IF NOT EXISTS %s (\n"
            + "    name       VARCHAR(64),\n"
            + "    lock_until TIMESTAMP(3) NULL,\n"
            + "    locked_at  TIMESTAMP(3) NULL,\n"
            + "    locked_by  VARCHAR(255),\n"
            + "    PRIMARY KEY (name)\n"
            + ");\n", configuration.getTableName());
        jdbcTemplate.execute(sql);
    }

    @Override
    public boolean insertRecord(@NotNull LockConfiguration lockConfiguration) {
        String sql = "INSERT INTO "
            + tableName()
            + "("
            + name()
            + ", "
            + lockUntil()
            + ", "
            + lockedAt()
            + ", "
            + lockedBy()
            + ") VALUES(?, ?, ?, ?)";
        return transactionTemplate.execute(status -> {
            try {
                int insertedRows = jdbcTemplate.update(sql, preparedStatement -> {
                    preparedStatement.setString(1, lockConfiguration.getName());
                    setTimestamp(preparedStatement, 2, lockConfiguration.getLockMaxTime());
                    setTimestamp(preparedStatement, 3, Instant.now());
                    preparedStatement.setString(4, lockedByValue());
                });
                return insertedRows > 0;
            } catch (DuplicateKeyException e) {
                return false;
            } catch (DataIntegrityViolationException e) {
                logger.warn("Unexpected exception", e);
                return false;
            }
        });
    }

    @Override
    public boolean updateRecord(@NotNull LockConfiguration lockConfiguration) {
        String sql = "UPDATE "
            + tableName()
            + " SET "
            + lockUntil()
            + " = ?, "
            + lockedAt()
            + " = ?, "
            + lockedBy()
            + " = ? WHERE "
            + name()
            + " = ? AND "
            + lockUntil()
            + " <= ?";
        return transactionTemplate.execute(status -> {
            int updatedRows = jdbcTemplate.update(sql, statement -> {
                Instant now = Instant.now();
                setTimestamp(statement, 1, lockConfiguration.getLockMaxTime());
                setTimestamp(statement, 2, now);
                statement.setString(3, lockedByValue());
                statement.setString(4, lockConfiguration.getName());
                setTimestamp(statement, 5, now);
            });
            return updatedRows > 0;
        });
    }

    @Override
    public void unlock(@NotNull LockConfiguration lockConfiguration) {
        String sql = "UPDATE " + tableName() + " SET " + lockUntil() + " = ? WHERE " + name() + " = ?";
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                jdbcTemplate.update(sql, statement -> {
                    setTimestamp(statement, 1, lockConfiguration.getUnlockTime());
                    statement.setString(2, lockConfiguration.getName());
                });
            }
        });
    }

    @Override
    public boolean extend(@NotNull LockConfiguration lockConfiguration) {
        String sql = "UPDATE "
            + tableName()
            + " SET "
            + lockUntil()
            + " = ? WHERE "
            + name()
            + " = ? AND "
            + lockedBy()
            + " = ? AND "
            + lockUntil()
            + " > ? ";

        logger.debug("Extending lock={} until={}", lockConfiguration.getName(), lockConfiguration.getLockMaxTime());
        return transactionTemplate.execute(status -> {
            int updatedRows = jdbcTemplate.update(sql, statement -> {
                setTimestamp(statement, 1, lockConfiguration.getLockMaxTime());
                statement.setString(2, lockConfiguration.getName());
                statement.setString(3, lockedByValue());
                setTimestamp(statement, 4, Instant.now());
            });
            return updatedRows > 0;
        });
    }

    private void setTimestamp(PreparedStatement preparedStatement, int parameterIndex, Instant time)
        throws SQLException {
        TimeZone timeZone = configuration.getTimeZone();
        if (timeZone == null) {
            preparedStatement.setTimestamp(parameterIndex, Timestamp.from(time));
        } else {
            preparedStatement.setTimestamp(parameterIndex, Timestamp.from(time), Calendar.getInstance(timeZone));
        }
    }

    private String name() {
        return configuration.getColumnNames().getName();
    }

    private String lockUntil() {
        return configuration.getColumnNames().getLockUntil();
    }

    private String lockedAt() {
        return configuration.getColumnNames().getLockedAt();
    }

    private String lockedBy() {
        return configuration.getColumnNames().getLockedBy();
    }

    private String lockedByValue() {
        return configuration.getLockedByValue();
    }

    private String tableName() {
        return configuration.getTableName();
    }

}
