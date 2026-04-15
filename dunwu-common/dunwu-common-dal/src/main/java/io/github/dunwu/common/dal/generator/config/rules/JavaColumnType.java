package io.github.dunwu.common.dal.generator.config.rules;

import cn.hutool.core.util.StrUtil;

/**
 * Java 列类型枚举。
 * <p>
 * 定义了数据库字段类型转换后的 Java 类型映射关系，包含基本类型、包装类型、
 * SQL 时间类型、Java 8 时间类型及其他常用类型。
 * 实现了 {@link IColumnType} 接口，用于代码生成过程中的类型推断。
 *
 * @author hubin
 * @since 2017-01-11
 */
public enum JavaColumnType implements IColumnType {
    // ==================== 基本类型 ====================

    /** {@code byte} 基本类型 */
    BASE_BYTE("byte", null),
    /** {@code short} 基本类型 */
    BASE_SHORT("short", null),
    /** {@code char} 基本类型 */
    BASE_CHAR("char", null),
    /** {@code int} 基本类型 */
    BASE_INT("int", null),
    /** {@code long} 基本类型 */
    BASE_LONG("long", null),
    /** {@code float} 基本类型 */
    BASE_FLOAT("float", null),
    /** {@code double} 基本类型 */
    BASE_DOUBLE("double", null),
    /** {@code boolean} 基本类型 */
    BASE_BOOLEAN("boolean", null),

    // ==================== 包装类型 ====================

    /** {@link Byte} 包装类型 */
    BYTE("Byte", null),
    /** {@link Short} 包装类型 */
    SHORT("Short", null),
    /** {@link Character} 包装类型 */
    CHARACTER("Character", null),
    /** {@link Integer} 包装类型 */
    INTEGER("Integer", null),
    /** {@link Long} 包装类型 */
    LONG("Long", null),
    /** {@link Float} 包装类型 */
    FLOAT("Float", null),
    /** {@link Double} 包装类型 */
    DOUBLE("Double", null),
    /** {@link Boolean} 包装类型 */
    BOOLEAN("Boolean", null),
    /** {@link String} 类型 */
    STRING("String", null),

    // ==================== SQL 包下数据类型 ====================

    /** {@link java.sql.Date} 类型 */
    DATE_SQL("Date", "java.sql.Date"),
    /** {@link java.sql.Time} 类型 */
    TIME("Time", "java.sql.Time"),
    /** {@link java.sql.Timestamp} 类型 */
    TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    /** {@link java.sql.Blob} 类型 */
    BLOB("Blob", "java.sql.Blob"),
    /** {@link java.sql.Clob} 类型 */
    CLOB("Clob", "java.sql.Clob"),

    // ==================== Java 8 新时间类型 ====================

    /** {@link java.time.LocalDate} 类型 */
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    /** {@link java.time.LocalTime} 类型 */
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    /** {@link java.time.Year} 类型 */
    YEAR("Year", "java.time.Year"),
    /** {@link java.time.YearMonth} 类型 */
    YEAR_MONTH("YearMonth", "java.time.YearMonth"),
    /** {@link java.time.LocalDateTime} 类型 */
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
    /** {@link java.time.Instant} 类型 */
    INSTANT("Instant", "java.time.Instant"),

    // ==================== 其他杂类 ====================

    /** {@code byte[]} 字节数组类型 */
    BYTE_ARRAY("byte[]", null),
    /** {@link Object} 类型 */
    OBJECT("Object", null),
    /** {@link java.util.Date} 类型 */
    DATE("Date", "java.util.Date"),
    /** {@link java.math.BigInteger} 类型 */
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    /** {@link java.math.BigDecimal} 类型 */
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal");

    /**
     * 类型名称（简单类名）
     */
    private final String type;

    /**
     * 类型所在完整包路径
     */
    private final String pkg;

    /**
     * 构造方法
     *
     * @param type 类型名称
     * @param pkg  类型所在包路径
     */
    JavaColumnType(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    /**
     * 返回类型名称
     *
     * @return 类型名称
     */
    @Override
    public String toString() {
        return type;
    }

    /**
     * 获取类型名称
     *
     * @return 类型名称
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * 获取包路径
     *
     * @return 包路径
     */
    @Override
    public String getPkg() {
        return pkg;
    }

    /**
     * 根据类型名称获取对应的 {@link JavaColumnType} 枚举
     *
     * @param type 类型名称
     * @return 匹配的枚举值，如果未找到返回 null
     */
    public static JavaColumnType getJavaColumnTypeByType(String type) {
        if (StrUtil.isBlank(type)) {
            return null;
        }

        JavaColumnType[] types = JavaColumnType.values();
        for (JavaColumnType i : types) {
            if (i.name().equals(type)) {
                return i;
            }
        }
        return null;
    }

    /**
     * 判断是否为日期/时间类型
     *
     * @param type 列类型
     * @return 如果是日期/时间类型返回 true，否则返回 false
     */
    public static boolean isDateType(IColumnType type) {
        if (type == DATE
            || type == DATE_SQL
            || type == TIME
            || type == TIMESTAMP
            || type == LOCAL_DATE
            || type == LOCAL_TIME
            || type == LOCAL_DATE_TIME) {
            return true;
        }
        return false;
    }
}
