package io.github.dunwu.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class ClassExtUtilTest {

    @Test
    public void test() {
        ClassLoader loader = ClassExtUtil.getDefaultClassLoader();
        ClassExtUtil.isPresent("io.github.dunwu.utils.reflect.ClassUtil", loader);
    }

    @Test
    void getAllClass() {

        assertThat(ClassExtUtil.getAllInterfaces(BClass.class)).hasSize(4).contains(
            AInterface.class, BInterface.class, CInterface.class, DInterface.class);

        assertThat(ClassExtUtil.getAllSuperclasses(BClass.class)).hasSize(2)
            .contains(AClass.class, Object.class);

        assertThat(ClassExtUtil.getAllAnnotations(BClass.class)).hasSize(4);

        assertThat(
            ClassExtUtil.getAnnotatedPublicFields(BClass.class, AAnnotation.class))
            .hasSize(2)
            .contains(ReflectionUtil.getField(BClass.class, "sfield"),
                ReflectionUtil.getField(BClass.class, "tfield"));

        assertThat(ClassExtUtil.getAnnotatedFields(BClass.class, EAnnotation.class))
            .hasSize(3).contains(ReflectionUtil.getField(BClass.class, "bfield"),
            ReflectionUtil.getField(BClass.class, "efield"),
            ReflectionUtil.getField(AClass.class, "afield"));

        assertThat(ClassExtUtil.getAnnotatedFields(BClass.class, FAnnotation.class))
            .hasSize(1).contains(ReflectionUtil.getField(AClass.class, "dfield"));

        assertThat(
            ClassExtUtil.getAnnotatedPublicMethods(BClass.class, FAnnotation.class))
            .hasSize(3).contains(
            ReflectionUtil.getAccessibleMethodByName(BClass.class,
                "hello"),
            ReflectionUtil.getAccessibleMethodByName(BClass.class,
                "hello3"),
            ReflectionUtil.getAccessibleMethodByName(AClass.class,
                "hello4"));
    }

    @Test
    void getClassGenericType() {
        // 获取第1，2个泛型类型
        assertThat(ClassExtUtil.getClassGenericType(TestBean.class)).isEqualTo(String.class);
        assertThat(ClassExtUtil.getClassGenericType(TestBean.class, 1)).isEqualTo(Long.class);

        // 定义父类时无泛型定义
        assertThat(ClassExtUtil.getClassGenericType(TestBean2.class)).isEqualTo(Object.class);

        // 无父类定义
        assertThat(ClassExtUtil.getClassGenericType(TestBean3.class)).isEqualTo(Object.class);
    }

    @Test
    void getPackageName() {
        assertThat(ClassExtUtil.getPackageName(ClassExtUtilTest.class))
            .isEqualTo("io.github.dunwu.util");
        assertThat(ClassExtUtil.getPackageName(BClass.class))
            .isEqualTo("io.github.dunwu.util");
        assertThat(ClassExtUtil.getPackageName(ClassExtUtilTest.class.getName()))
            .isEqualTo("io.github.dunwu.util");
        assertThat(ClassExtUtil.getPackageName(BClass.class.getName()))
            .isEqualTo("io.github.dunwu.util");
    }

    @Test
    void getShortClassName() {
        assertThat(ClassExtUtil.getShortClassName(ClassExtUtilTest.class)).isEqualTo("ClassExtUtilsTest");
        assertThat(ClassExtUtil.getShortClassName(BClass.class)).isEqualTo("ClassExtUtilsTest.BClass");
        assertThat(ClassExtUtil.getShortClassName(ClassExtUtilTest.class.getName())).isEqualTo("ClassExtUtilsTest");
        assertThat(ClassExtUtil.getShortClassName(BClass.class.getName())).isEqualTo("ClassExtUtilsTest.BClass");
    }

    @Test
    void isPresent() {
        assertThat(ClassExtUtil.isPresent("a.b.c",
            ClassExtUtil.getDefaultClassLoader())).isFalse();
        assertThat(ClassExtUtil.isPresent("io.github.dunwu.util.ClassExtUtils",
            ClassExtUtil.getDefaultClassLoader())).isTrue();
    }

    @Test
    void isSubClassOrInterfaceOf() {
        Assertions.assertTrue(ClassExtUtil.isSubClassOrInterfaceOf(BClass.class, AClass.class));
        Assertions.assertTrue(ClassExtUtil.isSubClassOrInterfaceOf(BInterface.class, AInterface.class));
        Assertions.assertTrue(ClassExtUtil.isSubClassOrInterfaceOf(BClass.class, BInterface.class));
        Assertions.assertTrue(ClassExtUtil.isSubClassOrInterfaceOf(BClass.class, AInterface.class));
    }

    interface AInterface {}

    @CAnnotation
    interface BInterface extends AInterface {

        @FAnnotation
        void hello();

    }

    interface CInterface {}

    interface DInterface {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface AAnnotation {}

    @Retention(RetentionPolicy.RUNTIME)
    @AAnnotation
    @interface BAnnotation {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface CAnnotation {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface DAnnotation {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface EAnnotation {}

    @Retention(RetentionPolicy.RUNTIME)
    @interface FAnnotation {}

    static class ParentBean<T, ID> {}

    static class TestBean extends ParentBean<String, Long> {}

    static class TestBean2 extends ParentBean {}

    static class TestBean3 {}

    @DAnnotation
    static class AClass implements DInterface {

        @AAnnotation
        public int tfield;

        @AAnnotation
        protected int vfield;

        @EAnnotation
        private int afield;

        private int cfield;

        @FAnnotation
        private int dfield;

        // not counted as public annotated method
        public void hello2(int i) {

        }

        // counted as public annotated method
        @FAnnotation
        public void hello4(int i) {

        }

        // not counted as public annotated method, because the child override it
        @FAnnotation
        public void hello7(int i) {

        }

        // not counted as public annotated method
        @FAnnotation
        protected void hello5(int i) {

        }

        // not counted as public annotated method
        @FAnnotation
        private void hello6(int i) {

        }

    }

    @BAnnotation
    static class BClass extends AClass implements CInterface, BInterface {

        @AAnnotation
        public int sfield;

        @AAnnotation
        protected int ufield;

        @EAnnotation
        private int bfield;

        @EAnnotation
        private int efield;

        // counted as public annotated method, BInterface
        @Override
        @EAnnotation
        public void hello() {
            // TODO Auto-generated method stub

        }

        public void hello2(int i) {

        }

        // not counted as public annotated method
        @Override
        public void hello7(int i) {

        }

        // counted as public annotated method
        @FAnnotation
        public void hello3(int i) {

        }

    }

}
