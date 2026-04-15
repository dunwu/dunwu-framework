package io.github.dunwu.common.dal.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * {@link TableInfo} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class TableInfoTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            TableInfo info = new TableInfo();
            Assertions.assertThat(info.getTableSchema()).isNull();
            Assertions.assertThat(info.getTableName()).isNull();
            Assertions.assertThat(info.getEngine()).isNull();
            Assertions.assertThat(info.getTableCollation()).isNull();
            Assertions.assertThat(info.getTableComment()).isNull();
            Assertions.assertThat(info.getCreateTime()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            LocalDateTime now = LocalDateTime.now();
            TableInfo info = new TableInfo("test_schema", "user_table", "InnoDB", "utf8mb4_general_ci", "用户表", now);
            Assertions.assertThat(info.getTableSchema()).isEqualTo("test_schema");
            Assertions.assertThat(info.getTableName()).isEqualTo("user_table");
            Assertions.assertThat(info.getEngine()).isEqualTo("InnoDB");
            Assertions.assertThat(info.getTableCollation()).isEqualTo("utf8mb4_general_ci");
            Assertions.assertThat(info.getTableComment()).isEqualTo("用户表");
            Assertions.assertThat(info.getCreateTime()).isEqualTo(now);
        }

        @Test
        @DisplayName("Builder 构造方法")
        public void testBuilderConstructor() {
            LocalDateTime now = LocalDateTime.now();
            TableInfo info = TableInfo.builder().tableSchema("test_schema").tableName("order_table").engine("InnoDB")
                .tableCollation("utf8mb4_unicode_ci").tableComment("订单表").createTime(now).build();
            Assertions.assertThat(info.getTableName()).isEqualTo("order_table");
            Assertions.assertThat(info.getEngine()).isEqualTo("InnoDB");
            Assertions.assertThat(info.getCreateTime()).isEqualTo(now);
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setTableSchema")
        public void testSetTableSchema() {
            TableInfo info = new TableInfo();
            info.setTableSchema("new_schema");
            Assertions.assertThat(info.getTableSchema()).isEqualTo("new_schema");
        }

        @Test
        @DisplayName("setTableName")
        public void testSetTableName() {
            TableInfo info = new TableInfo();
            info.setTableName("new_table");
            Assertions.assertThat(info.getTableName()).isEqualTo("new_table");
        }

        @Test
        @DisplayName("setEngine")
        public void testSetEngine() {
            TableInfo info = new TableInfo();
            info.setEngine("MyISAM");
            Assertions.assertThat(info.getEngine()).isEqualTo("MyISAM");
        }

        @Test
        @DisplayName("setTableCollation")
        public void testSetTableCollation() {
            TableInfo info = new TableInfo();
            info.setTableCollation("utf8_general_ci");
            Assertions.assertThat(info.getTableCollation()).isEqualTo("utf8_general_ci");
        }

        @Test
        @DisplayName("setTableComment")
        public void testSetTableComment() {
            TableInfo info = new TableInfo();
            info.setTableComment("测试表注释");
            Assertions.assertThat(info.getTableComment()).isEqualTo("测试表注释");
        }

        @Test
        @DisplayName("setCreateTime")
        public void testSetCreateTime() {
            TableInfo info = new TableInfo();
            LocalDateTime now = LocalDateTime.now();
            info.setCreateTime(now);
            Assertions.assertThat(info.getCreateTime()).isEqualTo(now);
        }

    }

}
