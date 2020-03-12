package io.github.dunwu.autoconfigure.dlock;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-12
 */
@ConfigurationProperties(prefix = "dunwu.dlock")
public class DistributedLockProperties {

    private LockType type = LockType.jdbc;

    private final Jdbc jdbc = new Jdbc();

    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
        this.type = type;
    }

    public Jdbc getJdbc() {
        return jdbc;
    }

    public static class Jdbc {

        private String tableName;

        public String getTableName() {
            return tableName;
        }

        public Jdbc setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

    }

    enum LockType {
        jdbc,
        redis,
        zookeeper
    }

}
