package io.github.dunwu.tool.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public class MessageDigestUtilTest {

    @RepeatedTest(2)
    public void testSHA384() throws NoSuchAlgorithmException {
        String username = "root";
        String password = "root";
        String target = MessageDigestUtil.encodeWithBase64(MessageDigestUtil.Type.SHA384, username.getBytes(),
            password.getBytes());
        String result = String.format("%s摘要:%s", MessageDigestUtil.Type.SHA384.getName(), target);
        Assertions.assertEquals("ZApimbcDO6rAvpQyi9yFpOFxvIgvi5dXu6VKQPgUduIC22MwT3Wlli62MNn9m9d_", target);
        System.out.println(result);
    }

    @RepeatedTest(2)
    public void testMD5() throws NoSuchAlgorithmException {
        String username = "root";
        String password = "root";
        String target = MessageDigestUtil.encodeWithBase64(MessageDigestUtil.Type.MD5, username.getBytes(),
            password.getBytes());
        String result = String.format("%s摘要:%s", MessageDigestUtil.Type.MD5.getName(), target);
        Assertions.assertEquals("tLja9LjqnTlWhxnh4yAHbw", target);
        System.out.println(result);
    }

}
