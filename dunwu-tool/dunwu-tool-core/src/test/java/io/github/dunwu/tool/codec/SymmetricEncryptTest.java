package io.github.dunwu.tool.codec;

import cn.hutool.core.codec.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class SymmetricEncryptTest {

    private final String ORIGIN_TEXT = "Hello World!";
    private final byte[] ORIGIN_TEXT_BYTES = ORIGIN_TEXT.getBytes(StandardCharsets.UTF_8);
    private final String TEST_SEED = "一二三四五";
    private final String TEST_IV_8 = "012345678";
    private final String TEST_IV_16 = "0123456789ABCDEF";

    @Nested
    @DisplayName("DES 测试")
    class DesTest {

        @Test
        @DisplayName(DesEncode.CIPHER_DES_DEFAULT)
        public void testDES() throws GeneralSecurityException {
            DesEncode encode = new DesEncode(DesEncode.Type.DEFAULT);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("C9lAh_zUbi_qRuJOP0g6vA"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName("test encodeWithBase64")
        public void testDES_encodeWithBase64() throws GeneralSecurityException {
            DesEncode encode = new DesEncode(DesEncode.Type.DEFAULT);

            final String encodedBase64 = encode.encodeWithBase64(ORIGIN_TEXT);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertEquals("C9lAh_zUbi_qRuJOP0g6vA", encodedBase64);
            Assertions.assertEquals(ORIGIN_TEXT, encode.decodeStrFromBase64(encodedBase64));
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT, encodedBase64));
        }

        @Test
        @DisplayName(DesEncode.CIPHER_DES_ECB_PKCS5PADDING)
        public void testDES_ECB_PKCS5PADDING() throws GeneralSecurityException {
            DesEncode encode = new DesEncode(DesEncode.Type.ECB_PKCS5PADDING);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("C9lAh_zUbi_qRuJOP0g6vA"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(DesEncode.CIPHER_DES_CBC_PKCS5PADDING)
        public void testDES_CBC_PKCS5PADDING() throws GeneralSecurityException {
            DesEncode encode = new DesEncode(DesEncode.Type.CBC_PKCS5PADDING);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("kbKKVqy_Aub4xvhTJhMoUA"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(DesEncode.CIPHER_DES_CBC_NOPADDING)
        public void testDES_CBC_NOPADDING() throws GeneralSecurityException {
            DesEncode encode = new DesEncode(DesEncode.Type.CBC_NOPADDING);

            final String orginText = "12345678";
            final byte[] orginBytes = orginText.getBytes(StandardCharsets.UTF_8);
            // Input length must be multiple of 8 bytes
            final byte[] encoded = encode.encode(orginBytes);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + orginText);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("wPaL6Y8l87g"), encoded);
            Assertions.assertArrayEquals(orginBytes, decoded);
            Assertions.assertTrue(encode.matches(orginBytes, encoded));
        }

    }

    @Nested
    @DisplayName("AES 测试")
    class AseTest {

        @Test
        @DisplayName(AesEncode.CIPHER_AES_DEFAULT)
        public void testAES() throws GeneralSecurityException {
            AesEncode encode = new AesEncode(AesEncode.Type.DEFAULT, TEST_SEED, TEST_IV_8);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("1IWwtOUoFhZZ2_5gqBbTHw"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(AesEncode.CIPHER_AES_ECB_PKCS5PADDING)
        public void testAES_ECB_PKCS5PADDING() throws GeneralSecurityException {
            AesEncode encode = new AesEncode(AesEncode.Type.ECB_PKCS5PADDING, TEST_SEED, TEST_IV_16);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("1IWwtOUoFhZZ2_5gqBbTHw"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(AesEncode.CIPHER_AES_CBC_PKCS5PADDING)
        public void testAES_CBC_PKCS5PADDING() throws GeneralSecurityException {
            AesEncode encode = new AesEncode(AesEncode.Type.CBC_PKCS5PADDING, TEST_SEED, TEST_IV_16);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("pMkDXai-HnXENKxT74gKfg"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(AesEncode.CIPHER_AES_CBC_NOPADDING)
        public void testAES_CBC_NOPADDING() throws GeneralSecurityException {
            AesEncode encode = new AesEncode(AesEncode.Type.CBC_NOPADDING, TEST_SEED, TEST_IV_16);

            final String orginText = "0123456789ABCDEF";
            final byte[] orginBytes = orginText.getBytes(StandardCharsets.UTF_8);
            // Input length must be multiple of 8 bytes
            final byte[] encoded = encode.encode(orginBytes);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + orginText);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("j_GZskwaw5luVJJD9Ila7A"), encoded);
            Assertions.assertArrayEquals(orginBytes, decoded);
            Assertions.assertTrue(encode.matches(orginBytes, encoded));
        }

    }

    @Nested
    @DisplayName("DESede 测试")
    class DesedeTest {

        @Test
        @DisplayName(DesedeEncode.CIPHER_DESEDE_ECB_PKCS5PADDING)
        public void testDesede_ECB_PKCS5PADDING() throws GeneralSecurityException {
            DesedeEncode encode = new DesedeEncode(DesedeEncode.Type.ECB_PKCS5PADDING);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("sIuZzsGdPeehed05T_fjnw"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

        @Test
        @DisplayName(DesedeEncode.CIPHER_DESEDE_CBC_PKCS5PADDING)
        public void testDesede_CBC_PKCS5PADDING() throws GeneralSecurityException {
            DesedeEncode encode = new DesedeEncode(DesedeEncode.Type.CBC_PKCS5PADDING);

            final byte[] encoded = encode.encode(ORIGIN_TEXT_BYTES);
            final String encodedBase64 = Base64.encodeUrlSafe(encoded);
            final byte[] decodedBase64 = Base64.decode(encodedBase64);
            byte[] decoded = encode.decode(decodedBase64);

            System.out.println("原文: " + ORIGIN_TEXT);
            System.out.println("密文: " + encodedBase64);
            Assertions.assertArrayEquals(Base64.decode("aSP5IyIVxnXeSO6MnXXnlQ"), encoded);
            Assertions.assertArrayEquals(ORIGIN_TEXT_BYTES, decoded);
            Assertions.assertTrue(encode.matches(ORIGIN_TEXT_BYTES, encoded));
        }

    }

}
