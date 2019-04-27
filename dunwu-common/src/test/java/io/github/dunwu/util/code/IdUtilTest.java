package io.github.dunwu.util.code;

import org.junit.jupiter.api.Test;

/**
 * @see IdUtil
 */
public class IdUtilTest {

    @Test
    public void test() {
        System.out.println("uuid: " + IdUtil.uuid());
        System.out.println("uuid2: " + IdUtil.uuid2());
        System.out.println("randomLong: " + IdUtil.randomLong());
        System.out.println("randomBase64: " + IdUtil.randomBase64(8));
    }
}
