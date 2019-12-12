package io.github.dunwu.tool.date;

/**
 * 季度枚举
 *
 * @author zhfish(https : / / github.com / zhfish)
 * @see #Q1
 * @see #Q2
 * @see #Q3
 * @see #Q4
 */
public enum Quarter {

    /**
     * 第一季度
     */
    Q1(1),
    /**
     * 第二季度
     */
    Q2(2),
    /**
     * 第三季度
     */
    Q3(3),
    /**
     * 第四季度
     */
    Q4(4);

    // ---------------------------------------------------------------
    private int value;

    Quarter(int value) {
        this.value = value;
    }

    /**
     * 将 季度int转换为Season枚举对象<br>
     *
     * @param intValue 季度int表示
     * @return {@link Quarter}
     * @see #Q1
     * @see #Q2
     * @see #Q3
     * @see #Q4
     */
    public static Quarter of(int intValue) {
        switch (intValue) {
            case 1:
                return Q1;
            case 2:
                return Q2;
            case 3:
                return Q3;
            case 4:
                return Q4;
            default:
                return null;
        }
    }

    public int getValue() {
        return this.value;
    }
}
