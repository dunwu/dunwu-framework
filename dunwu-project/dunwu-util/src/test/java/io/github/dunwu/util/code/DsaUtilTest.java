package io.github.dunwu.util.code;

import io.github.dunwu.util.mock.MockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-16
 */
public class DsaUtilTest {

    @Test
    @DisplayName("DSA 摘要")
    void testDSA() throws Exception {
        String msg = MockUtil.anyLetterString(3, 50);
        ISignature digest = DsaUtil.DSA;
        byte[] privateKey = digest.getPrivateKey();
        byte[] publicKey = digest.getPublicKey();
        byte[] signature = digest.sign(msg.getBytes(), privateKey);
        boolean verify = digest.verify(msg.getBytes(), publicKey, signature);
        Assertions.assertTrue(verify);
    }

    @Test
    @DisplayName("SHA1_WITH_DSA 摘要")
    void testSHA1_WITH_DSA() throws Exception {
        String msg = MockUtil.anyLetterString(3, 50);
        ISignature digest = DsaUtil.SHA1_WITH_DSA;
        byte[] privateKey = digest.getPrivateKey();
        byte[] publicKey = digest.getPublicKey();
        byte[] signature = digest.sign(msg.getBytes(), privateKey);
        boolean verify = digest.verify(msg.getBytes(), publicKey, signature);
        Assertions.assertTrue(verify);
    }
}
