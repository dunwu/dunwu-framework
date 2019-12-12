package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.clone.CloneSupport;
import io.github.dunwu.tool.collection.ArrayIter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 不可变数组类型，用于多值返回<br> 多值可以支持每个元素值类型不同
 *
 * @author Looly
 */
public class Tuple extends CloneSupport<Tuple> implements Iterable<Object>, Serializable {

    private static final long serialVersionUID = -7689304393482182157L;

    private Object[] members;

    /**
     * 构造
     *
     * @param members 成员数组
     */
    public Tuple(Object... members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(members);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return false != Arrays.deepEquals(members, other.members);
    }

    @Override
    public String toString() {
        return Arrays.toString(members);
    }

    @Override
    public Iterator<Object> iterator() {
        return new ArrayIter<Object>(members);
    }

    /**
     * 获取指定位置元素
     *
     * @param <T>   返回对象类型
     * @param index 位置
     * @return 元素
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) members[index];
    }

    /**
     * 获得所有元素
     *
     * @return 获得所有元素
     */
    public Object[] getMembers() {
        return this.members;
    }

}
