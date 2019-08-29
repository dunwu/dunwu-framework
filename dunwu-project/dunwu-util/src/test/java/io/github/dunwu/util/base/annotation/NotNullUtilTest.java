package io.github.dunwu.util.base.annotation;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-13
 */
public class NotNullUtilTest {
    static class MyBean {
        @NotNull
        private Integer id;
        private String name;

        public MyBean(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void test() {
        MyBean myBean = new MyBean(null, "jack");
        try {
            NotNullUtil.check(myBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
