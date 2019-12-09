package io.github.dunwu.util;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 获取Class信息的工具类 1. 获取类名，包名，循环向上的全部父类，全部接口 2. 其他便捷函数
 */
public class ClassExtUtils extends ClassUtils {

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	// getAnnotations
	// -------------------------------------------------------------------------------------------------

	/**
	 * 递归Class所有的Annotation，一个最彻底的实现. 包括所有基类，所有接口的Annotation，同时支持Spring风格的Annotation继承的父Annotation，
	 */
	public static Set<Annotation> getAllAnnotations(final Class<?> cls) {
		List<Class<?>> allTypes = ClassExtUtils.getAllSuperclasses(cls);
		allTypes.addAll(ClassExtUtils.getAllInterfaces(cls));
		allTypes.add(cls);

		Set<Annotation> anns = new HashSet<Annotation>();
		for (Class<?> type : allTypes) {
			anns.addAll(Arrays.asList(type.getDeclaredAnnotations()));
		}

		Set<Annotation> superAnnotations = new HashSet<Annotation>();
		for (Annotation ann : anns) {
			getSuperAnnotations(ann.annotationType(), superAnnotations);
		}

		anns.addAll(superAnnotations);

		return anns;
	}

	/**
	 * 找出所有标注了该annotation的属性，循环遍历父类，包含private属性. 暂未支持Spring风格Annotation继承Annotation copy from
	 * org.unitils.util.AnnotationUtils
	 */
	public static <T extends Annotation> Set<Field> getAnnotatedFields(
		final Class<?> clazz, final Class<T> annotation) {
		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}
		Set<Field> annotatedFields = new HashSet<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(annotation) != null) {
				annotatedFields.add(field);
			}
		}
		annotatedFields.addAll(getAnnotatedFields(clazz.getSuperclass(), annotation));
		return annotatedFields;
	}

	/**
	 * 找出所有标注了该annotation的公共属性，循环遍历父类. 暂未支持Spring风格Annotation继承Annotation copy from org.unitils.util.AnnotationUtils
	 */
	public static <T extends Annotation> Set<Field> getAnnotatedPublicFields(
		final Class<?> clazz, final Class<T> annotation) {

		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}

		Set<Field> annotatedFields = new HashSet<Field>();
		Field[] fields = clazz.getFields();

		for (Field field : fields) {
			if (field.getAnnotation(annotation) != null) {
				annotatedFields.add(field);
			}
		}

		return annotatedFields;
	}

	/**
	 * 找出所有标注了该annotation的公共方法(含父类的公共函数)，循环其接口. 暂未支持Spring风格Annotation继承Annotation 另，如果子类重载父类的公共函数，父类函数上的annotation不会继承，只有接口上的annotation会被继承.
	 */
	public static <T extends Annotation> Set<Method> getAnnotatedPublicMethods(
		final Class<?> clazz, final Class<T> annotation) {
		// 已递归到Objebt.class, 停止递归
		if (Object.class.equals(clazz)) {
			return Collections.emptySet();
		}

		List<Class<?>> ifcs = ClassUtils.getAllInterfaces(clazz);
		Set<Method> annotatedMethods = new HashSet<Method>();

		// 遍历当前类的所有公共方法
		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			// 如果当前方法有标注，或定义了该方法的所有接口有标注
			if (method.getAnnotation(annotation) != null
				|| searchOnInterfaces(method, annotation, ifcs)) {
				annotatedMethods.add(method);
			}
		}

		return annotatedMethods;
	}

	private static <T extends Annotation> boolean searchOnInterfaces(Method method,
		final Class<T> annotationType, final List<Class<?>> ifcs) {
		for (Class<?> iface : ifcs) {
			try {
				Method equivalentMethod = iface.getMethod(method.getName(),
					method.getParameterTypes());
				if (equivalentMethod.getAnnotation(annotationType) != null) {
					return true;
				}
			} catch (NoSuchMethodException ex) { // NOSONAR
				// Skip this interface - it doesn't have the method...
			}
		}
		return false;
	}

	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处. 这是唯一可以通过反射从泛型获得Class实例的地方. 如无法找到, 返回Object.class. eg. public UserDao
	 * extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static Class<?> getClassGenericType(final Class clazz) {
		return getClassGenericType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 注意泛型必须定义在父类处. 这是唯一可以通过反射从泛型获得Class实例的地方. 如无法找到, 返回Object.class. 如public UserDao
	 * extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic declaration, start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class<?> getClassGenericType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			System.out.println(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			System.out.println("Index: " + index + ", Size of " + clazz.getSimpleName()
				+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			System.out.println(clazz.getSimpleName()
				+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * Copy from Spring, 按顺序获取默认 ClassLoader
	 *
	 * <ol>
	 * <li>Thread.currentThread().getContextClassLoader</li>
	 * <li>ClassLoaderUtil的加载 ClassLoader</li>
	 * <li>SystemClassLoader</li>
	 * </ol>
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassExtUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can
					// live with null...
				}
			}
		}
		return cl;
	}

	/**
	 * 探测类是否存在classpath中
	 */
	public static boolean isPresent(final String className, final ClassLoader classLoader) {
		try {
			classLoader.loadClass(className);
			return true;
		} catch (Throwable e) {
			// Class or one of its dependencies is not present...
			return false;
		}
	}

	/**
	 * https://github.com/linkedin/linkedin-utils/blob/master/org.linkedin .util-core/src/main/java/org/linkedin/util/reflect/ReflectUtils.java
	 * The purpose of this method is somewhat to provide a better naming / documentation than the javadoc of
	 * <code>Class.isAssignableFrom</code> method.
	 *
	 * @return <code>true</code> if subclass is a subclass or sub interface of superclass
	 */
	public static boolean isSubClassOrInterfaceOf(final Class subclass, final Class superclass) {
		return superclass.isAssignableFrom(subclass);
	}

	/**
	 * 获取CGLib处理过后的实体的原Class.
	 */
	public static Class<?> unwrapCglib(final Object instance) {
		Validate.notNull(instance, "Instance must not be null");
		Class<?> clazz = instance.getClass();
		if ((clazz != null) && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if ((superClass != null) && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	private static <A extends Annotation> void getSuperAnnotations(
		final Class<A> annotationType, final Set<Annotation> visited) {
		Annotation[] anns = annotationType.getDeclaredAnnotations();

		for (Annotation ann : anns) {
			if (!ann.annotationType().getName().startsWith("java.lang")
				&& visited.add(ann)) {
				getSuperAnnotations(ann.annotationType(), visited);
			}
		}
	}

}
