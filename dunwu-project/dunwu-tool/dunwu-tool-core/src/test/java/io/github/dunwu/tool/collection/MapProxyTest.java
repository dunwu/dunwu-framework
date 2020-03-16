package io.github.dunwu.tool.collection;

import io.github.dunwu.tool.map.MapProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapProxyTest {

    @Test
    public void mapProxyTest() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        MapProxy mapProxy = new MapProxy(map);
        Integer b = mapProxy.getInt("b");
        Assertions.assertEquals(new Integer(2), b);

        Set<Object> keys = mapProxy.keySet();
        Assertions.assertFalse(keys.isEmpty());

        Set<Entry<Object, Object>> entrys = mapProxy.entrySet();
        Assertions.assertFalse(entrys.isEmpty());
    }

}
