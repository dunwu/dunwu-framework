package io.github.dunwu.common.dal.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * {@link TableColumnInfo} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class TableColumnInfoTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            TableColumnInfo info = new TableColumnInfo();
            Assertions.assertThat(info.getTableSchema()).isNull();
            Assertions.assertThat(info.getTableName()).isNull();
            Assertions.assertThat(info.getColumnName()).isNull();
            Assertions.assertThat(info.getColumnComment()).isNull();
            Assertions.assertThat(info.getDataType()).isNull();
        }

        @Test
        @DisplayName("全参构造方法")
        public void testFullConstructor() {
            TableColumnInfo info = new TableColumnInfo("test_schema", "user_table", "user_id", "用户ID", "bigint");
            Assertions.assertThat(info.getTableSchema()).isEqualTo("test_schema");
            Assertions.assertThat(info.getTableName()).isEqualTo("user_table");
            Assertions.assertThat(info.getColumnName()).isEqualTo("user_id");
            Assertions.assertThat(info.getColumnComment()).isEqualTo("用户ID");
            Assertions.assertThat(info.getDataType()).isEqualTo("bigint");
        }

        @Test
        @DisplayName("Builder 构造方法")
        public void testBuilderConstructor() {
            TableColumnInfo info =
                TableColumnInfo.builder().tableSchema("test_schema").tableName("order_table").columnName("order_no")
                    .columnComment("订单号").dataType("varchar").build();
            Assertions.assertThat(info.getColumnName()).isEqualTo("order_no");
            Assertions.assertThat(info.getColumnComment()).isEqualTo("订单号");
            Assertions.assertThat(info.getDataType()).isEqualTo("varchar");
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setTableSchema")
        public void testSetTableSchema() {
            TableColumnInfo info = new TableColumnInfo();
            info.setTableSchema("new_schema");
            Assertions.assertThat(info.getTableSchema()).isEqualTo("new_schema");
        }

        @Test
        @DisplayName("setTableName")
        public void testSetTableName() {
            TableColumnInfo info = new TableColumnInfo();
            info.setTableName("new_table");
            Assertions.assertThat(info.getTableName()).isEqualTo("new_table");
        }

        @Test
        @DisplayName("setColumnName")
        public void testSetColumnName() {
            TableColumnInfo info = new TableColumnInfo();
            info.setColumnName("new_column");
            Assertions.assertThat(info.getColumnName()).isEqualTo("new_column");
        }

        @Test
        @DisplayName("setColumnComment")
        public void testSetColumnComment() {
            TableColumnInfo info = new TableColumnInfo();
            info.setColumnComment("列注释");
            Assertions.assertThat(info.getColumnComment()).isEqualTo("列注释");
        }

        @Test
        @DisplayName("setDataType")
        public void testSetDataType() {
            TableColumnInfo info = new TableColumnInfo();
            info.setDataType("int");
            Assertions.assertThat(info.getDataType()).isEqualTo("int");
        }

    }

}
