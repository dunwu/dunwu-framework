package io.github.dunwu.util.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassUtilTest {

	@Test
	public void getMessage() {
		assertThat(ClassUtil.getShortClassName(ClassUtilTest.class))
				.isEqualTo("ClassUtilTest");
		assertThat(ClassUtil.getShortClassName(BClass.class))
				.isEqualTo("ClassUtilTest.BClass");

		assertThat(ClassUtil.getShortClassName(ClassUtilTest.class.getName()))
				.isEqualTo("ClassUtilTest");
		assertThat(ClassUtil.getShortClassName(BClass.class.getName()))
				.isEqualTo("ClassUtilTest.BClass");

		assertThat(ClassUtil.getPackageName(ClassUtilTest.class))
				.isEqualTo("io.github.dunwu.utils.reflect");
		assertThat(ClassUtil.getPackageName(BClass.class))
				.isEqualTo("io.github.dunwu.utils.reflect");
		assertThat(ClassUtil.getPackageName(ClassUtilTest.class.getName()))
				.isEqualTo("io.github.dunwu.utils.reflect");
		assertThat(ClassUtil.getPackageName(BClass.class.getName()))
				.isEqualTo("io.github.dunwu.utils.reflect");
	}

	@Test
	public void getAllClass() {

		assertThat(ClassUtil.getAllInterfaces(BClass.class)).hasSize(4).contains(
				AInterface.class, BInterface.class, CInterface.class, DInterface.class);

		assertThat(ClassUtil.getAllSuperclasses(BClass.class)).hasSize(2)
				.contains(AClass.class, Object.class);

		assertThat(AnnotationUtil.getAllAnnotations(BClass.class)).hasSize(4);

		assertThat(
				AnnotationUtil.getAnnotatedPublicFields(BClass.class, AAnnotation.class))
						.hasSize(2)
						.contains(ReflectionUtil.getField(BClass.class, "sfield"),
								ReflectionUtil.getField(BClass.class, "tfield"));

		assertThat(AnnotationUtil.getAnnotatedFields(BClass.class, EAnnotation.class))
				.hasSize(3).contains(ReflectionUtil.getField(BClass.class, "bfield"),
						ReflectionUtil.getField(BClass.class, "efield"),
						ReflectionUtil.getField(AClass.class, "afield"));

		assertThat(AnnotationUtil.getAnnotatedFields(BClass.class, FAnnotation.class))
				.hasSize(1).contains(ReflectionUtil.getField(AClass.class, "dfield"));

		assertThat(
				AnnotationUtil.getAnnotatedPublicMethods(BClass.class, FAnnotation.class))
						.hasSize(3).contains(
								ReflectionUtil.getAccessibleMethodByName(BClass.class,
										"hello"),
								ReflectionUtil.getAccessibleMethodByName(BClass.class,
										"hello3"),
								ReflectionUtil.getAccessibleMethodByName(AClass.class,
										"hello4"));
	}

	@Test
	public void getSuperClassGenericType() {
		// 获取第1，2个泛型类型
		assertThat(ClassUtil.getClassGenericType(TestBean.class)).isEqualTo(String.class);
		assertThat(ClassUtil.getClassGenericType(TestBean.class, 1))
				.isEqualTo(Long.class);

		// 定义父类时无泛型定义
		assertThat(ClassUtil.getClassGenericType(TestBean2.class))
				.isEqualTo(Object.class);

		// 无父类定义
		assertThat(ClassUtil.getClassGenericType(TestBean3.class))
				.isEqualTo(Object.class);
	}

	public void classPresent() {
		assertThat(ClassLoaderUtil.isPresent("a.b.c",
				ClassLoaderUtil.getDefaultClassLoader())).isFalse();
		assertThat(ClassLoaderUtil.isPresent("io.github.dunwu.utils.reflect.ClassUtil",
				ClassLoaderUtil.getDefaultClassLoader())).isTrue();
	}

	/**
	 * Unit test case of
	 * {@link io.github.dunwu.util.reflect.ClassUtil#isSubClassOrInterfaceOf(Class, Class)}
	 */
	@Test
	public void testIsSubClassOrInterfaceOf() {
		Assertions.assertTrue(
				ClassUtil.isSubClassOrInterfaceOf(BClass.class, AClass.class));
		Assertions.assertTrue(
				ClassUtil.isSubClassOrInterfaceOf(BInterface.class, AInterface.class));
		Assertions.assertTrue(
				ClassUtil.isSubClassOrInterfaceOf(BClass.class, BInterface.class));
		Assertions.assertTrue(
				ClassUtil.isSubClassOrInterfaceOf(BClass.class, AInterface.class));
	}

	public interface AInterface {

	}

	@CAnnotation
	public interface BInterface extends AInterface {

		@FAnnotation
		void hello();

	}

	public interface CInterface {

	}

	public interface DInterface {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface AAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@AAnnotation
	public @interface BAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface CAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface DAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface EAnnotation {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface FAnnotation {

	}

	public static class ParentBean<T, ID> {

	}

	public static class TestBean extends ParentBean<String, Long> {

	}

	public static class TestBean2 extends ParentBean {

	}

	public static class TestBean3 {

	}

	@DAnnotation
	public static class AClass implements DInterface {

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

		// not counted as public annotated method
		@FAnnotation
		protected void hello5(int i) {

		}

		// not counted as public annotated method
		@FAnnotation
		private void hello6(int i) {

		}

		// not counted as public annotated method, because the child override it
		@FAnnotation
		public void hello7(int i) {

		}

	}

	@BAnnotation
	public static class BClass extends AClass implements CInterface, BInterface {

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
