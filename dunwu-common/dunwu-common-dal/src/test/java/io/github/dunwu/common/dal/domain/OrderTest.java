package io.github.dunwu.common.dal.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

/**
 * {@link Order} 测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
public class OrderTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造方法")
        public void testDefaultConstructor() {
            Order order = new Order();
            Assertions.assertThat(order.getField()).isNull();
            Assertions.assertThat(order.getDirection()).isNull();
        }

        @Test
        @DisplayName("带参构造方法")
        public void testParameterizedConstructor() {
            Order order = new Order("id", Order.Direction.asc);
            Assertions.assertThat(order.getField()).isEqualTo("id");
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.asc);
        }

        @Test
        @DisplayName("Builder 构造方法")
        public void testBuilderConstructor() {
            Order order = Order.builder().field("name").direction(Order.Direction.desc).build();
            Assertions.assertThat(order.getField()).isEqualTo("name");
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.desc);
        }

    }

    @Nested
    @DisplayName("业务方法测试")
    class BusinessMethodTest {

        @Test
        @DisplayName("getClause")
        public void testGetClause() {
            Order order = new Order("id", Order.Direction.asc);
            Assertions.assertThat(order.getClause()).isEqualTo("id,asc");
        }

        @Test
        @DisplayName("toString")
        public void testToString() {
            Order order = new Order("name", Order.Direction.desc);
            Assertions.assertThat(order.toString()).isEqualTo("name,desc");
        }

    }

    @Nested
    @DisplayName("静态方法测试")
    class StaticMethodTest {

        @Test
        @DisplayName("parse - 正常表达式")
        public void testParse() {
            Order order = Order.parse("id,asc");
            Assertions.assertThat(order).isNotNull();
            Assertions.assertThat(order.getField()).isEqualTo("id");
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.asc);
        }

        @Test
        @DisplayName("parse - null 表达式")
        public void testParseNull() {
            Order order = Order.parse((String)null);
            Assertions.assertThat(order).isNull();
        }

        @Test
        @DisplayName("parse - 空字符串")
        public void testParseEmpty() {
            Order order = Order.parse("");
            Assertions.assertThat(order).isNull();
        }

        @Test
        @DisplayName("parse - 格式错误")
        public void testParseInvalidFormat() {
            Order order = Order.parse("id");
            Assertions.assertThat(order).isNull();
        }

        @Test
        @DisplayName("parse - 三个部分")
        public void testParseThreeParts() {
            Order order = Order.parse("id,asc,extra");
            Assertions.assertThat(order).isNull();
        }

        @Test
        @DisplayName("parse(Sort.Order) - 升序")
        public void testParseSortOrderAsc() {
            Sort.Order sort = new Sort.Order(Sort.Direction.ASC, "id");
            Order order = Order.parse(sort);
            Assertions.assertThat(order).isNotNull();
            Assertions.assertThat(order.getField()).isEqualTo("id");
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.asc);
        }

        @Test
        @DisplayName("parse(Sort.Order) - 降序")
        public void testParseSortOrderDesc() {
            Sort.Order sort = new Sort.Order(Sort.Direction.DESC, "name");
            Order order = Order.parse(sort);
            Assertions.assertThat(order).isNotNull();
            Assertions.assertThat(order.getField()).isEqualTo("name");
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.desc);
        }

        @Test
        @DisplayName("parse(Sort.Order) - null 参数")
        public void testParseSortOrderNull() {
            Order order = Order.parse((Sort.Order)null);
            Assertions.assertThat(order).isNull();
        }

    }

    @Nested
    @DisplayName("equals/hashCode 测试")
    class EqualsHashCodeTest {

        @Test
        @DisplayName("equals - 相同对象")
        public void testEqualsSameObject() {
            Order order = new Order("id", Order.Direction.asc);
            Assertions.assertThat(order.equals(order)).isTrue();
        }

        @Test
        @DisplayName("equals - null 对象")
        public void testEqualsNull() {
            Order order = new Order("id", Order.Direction.asc);
            Assertions.assertThat(order.equals(null)).isFalse();
        }

        @Test
        @DisplayName("equals - 不同类型")
        public void testEqualsDifferentType() {
            Order order = new Order("id", Order.Direction.asc);
            Assertions.assertThat(order.equals("string")).isFalse();
        }

        @Test
        @DisplayName("equals - 相同 field")
        public void testEqualsSameField() {
            Order order1 = new Order("id", Order.Direction.asc);
            Order order2 = new Order("id", Order.Direction.desc);
            Assertions.assertThat(order1.equals(order2)).isTrue();
        }

        @Test
        @DisplayName("equals - 不同 field")
        public void testEqualsDifferentField() {
            Order order1 = new Order("id", Order.Direction.asc);
            Order order2 = new Order("name", Order.Direction.asc);
            Assertions.assertThat(order1.equals(order2)).isFalse();
        }

        @Test
        @DisplayName("hashCode - 相同 field 哈希值相同")
        public void testHashCodeSame() {
            Order order1 = new Order("id", Order.Direction.asc);
            Order order2 = new Order("id", Order.Direction.desc);
            Assertions.assertThat(order1.hashCode()).isEqualTo(order2.hashCode());
        }

        @Test
        @DisplayName("hashCode - 不同 field 哈希值不同")
        public void testHashCodeDifferent() {
            Order order1 = new Order("id", Order.Direction.asc);
            Order order2 = new Order("name", Order.Direction.asc);
            Assertions.assertThat(order1.hashCode()).isNotEqualTo(order2.hashCode());
        }

    }

    @Nested
    @DisplayName("setter 方法测试")
    class SetterTest {

        @Test
        @DisplayName("setField")
        public void testSetField() {
            Order order = new Order();
            order.setField("newField");
            Assertions.assertThat(order.getField()).isEqualTo("newField");
        }

        @Test
        @DisplayName("setDirection")
        public void testSetDirection() {
            Order order = new Order();
            order.setDirection(Order.Direction.desc);
            Assertions.assertThat(order.getDirection()).isEqualTo(Order.Direction.desc);
        }

    }

}
