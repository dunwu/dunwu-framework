package io.github.dunwu.tool.util;

import io.github.dunwu.tool.clone.CloneSupport;
import io.github.dunwu.tool.collection.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ObjectUtilTest {

    @Test
    public void cloneTest() {
        Obj obj = new Obj();
        Obj obj2 = ObjectUtil.clone(obj);
        Assertions.assertEquals("OK", obj2.doSomeThing());
    }

    @Test
    public void toStringTest() {
        ArrayList<String> strings = CollectionUtil.newArrayList("1", "2");
        String result = ObjectUtil.toString(strings);
        Assertions.assertEquals("[1, 2]", result);
    }

    static class Obj extends CloneSupport<Obj> {

        public String doSomeThing() {
            return "OK";
        }

    }

}
