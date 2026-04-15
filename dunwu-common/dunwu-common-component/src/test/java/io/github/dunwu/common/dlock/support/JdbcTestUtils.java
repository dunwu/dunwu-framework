package io.github.dunwu.common.dlock.support;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTestUtils {

    private final HikariDataSource datasource;

    private final JdbcTemplate jdbcTemplate;

    JdbcTestUtils(DbConfig dbConfig) {
        datasource = new HikariDataSource();
        datasource.setJdbcUrl(dbConfig.getJdbcUrl());
        datasource.setUsername(dbConfig.getUsername());
        datasource.setPassword(dbConfig.getPassword());

        jdbcTemplate = new JdbcTemplate(datasource);
        String sql = "CREATE TABLE IF NOT EXISTS `dunwulock` (\n"
            + "    `name`       VARCHAR(64),\n"
            + "    `lock_until` TIMESTAMP(3) NULL,\n"
            + "    `locked_at`  TIMESTAMP(3) NULL,\n"
            + "    `locked_by`  VARCHAR(255),\n"
            + "    PRIMARY KEY (`name`)\n"
            + ");\n";
        jdbcTemplate.execute(sql);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    void clean() {
        jdbcTemplate.execute("DROP TABLE dunwulock");
        datasource.close();
    }

}
