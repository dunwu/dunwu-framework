package io.github.dunwu.common.log.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 日志记录变量上下文
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
public class LogRecordContext {

    private static final InheritableThreadLocal<Stack<Map<String, Object>>> VARIABLE_MAP_STACK =
        new InheritableThreadLocal<>();

    /**
     * 设置变量
     *
     * @param name  变量名
     * @param value 变量值
     */
    public static void putVariable(String name, Object value) {
        if (VARIABLE_MAP_STACK.get() == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack.size() == 0) {
            VARIABLE_MAP_STACK.get().push(new HashMap<>());
        }
        VARIABLE_MAP_STACK.get().peek().put(name, value);
    }

    /**
     * 获取变量
     *
     * @param key 变量名
     * @return 变量值
     */
    public static Object getVariable(String key) {
        Map<String, Object> variableMap = VARIABLE_MAP_STACK.get().peek();
        return variableMap.get(key);
    }

    /**
     * 获取所有变量
     *
     * @return 变量映射
     */
    public static Map<String, Object> getVariables() {
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        return mapStack.peek();
    }

    /**
     * 清除当前上下文
     */
    public static void clear() {
        if (VARIABLE_MAP_STACK.get() != null) {
            VARIABLE_MAP_STACK.get().pop();
        }
    }

    /**
     * 放入空 Span，用于方法级别的上下文隔离
     * <p>
     * 日志使用方通常不需要手动调用此方法。每进入一个方法时会初始化一个 span 放入栈中，方法执行完后 pop 掉该 span。
     */
    public static void putEmptySpan() {
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        VARIABLE_MAP_STACK.get().push(new HashMap<>());
    }

}
