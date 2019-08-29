package io.github.dunwu.util.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class CryptoUtilTest {

    public static final int COUNT = 10000;
    public static final String Bytes16Key = "1234567890ABCDEF";
    public static final String DES_KEY = "一二三四五";

    public static final String TEST_CONTENT = "测试内容";


    //    @Nested
    //    class AesTest {
    //
    //        @Test
    //        public void testAES() throws Exception {
    //            ICrypto keyCrypto = CryptoUtil.getAes(Bytes16Key);
    //            encryptAndDecrypt(keyCrypto);
    //        }
    //
    //        @Test
    //        public void testAES_CBC_PKCS5PADDING() throws Exception {
    //            ICrypto keyCrypto = CryptoUtil.getAesCbcPkcs5padding(Bytes16Key);
    //            encryptAndDecrypt(keyCrypto);
    //        }
    //
    //        @Test
    //        public void testAES_ECB_PKCS5PADDING() throws Exception {
    //            ICrypto keyCrypto = CryptoUtil.getAesEcbPkcs5Padding(Bytes16Key);
    //            encryptAndDecrypt(keyCrypto);
    //        }
    //
    //        /**
    //         * AES/CBC/NoPadding 模式的输入内容必须为 8 字节的整数倍
    //         */
    //        @Test
    //        public void testAES_CBC_NOPADDING() throws Exception {
    //            ICrypto aesCbcNoPadding = CryptoUtil.getAesCbcNoPadding(Bytes16Key);
    //            System.out.println("crypto class: " + aesCbcNoPadding.getClass()
    //                                                                 .getSimpleName());
    //            long begin = System.currentTimeMillis();
    //            for (int i = 0; i < COUNT; i++) {
    //                String origin = MockUtil.anyLetterString(32, 32);
    //                String encrypt = aesCbcNoPadding.encrypt(origin);
    //                String decrypt = aesCbcNoPadding.decrypt(encrypt);
    //                //            System.out.println("原文: " + origin);
    //                //            System.out.println("密文: " + encrypt);
    //                //            System.out.println("明文: " + decrypt);
    //                Assertions.assertEquals(origin, decrypt);
    //            }
    //            long end = System.currentTimeMillis();
    //            System.out.println("总耗时: " + (end - begin) + " ms");
    //            System.out.println("平均耗时: " + (end - begin) / COUNT + " ms");
    //        }
    //    }
    //
    //
    //    @Nested
    //    class DesTest {
    //
    //        @Test
    //        public void testDES() throws Exception {
    //            encryptAndDecrypt(CryptoUtil.getDes(DES_NAME));
    //        }
    //
    //        @Test
    //        public void testDES_CBC_PKCS5PADDING() throws Exception {
    //            encryptAndDecrypt(CryptoUtil.getDesCbcPkcs5Padding(DES_NAME));
    //        }
    //
    //        @Test
    //        public void testDES_ECB_PKCS5PADDING() throws Exception {
    //            encryptAndDecrypt(CryptoUtil.getDesEcbPkcs5Padding(DES_NAME));
    //        }
    //
    //        /**
    //         * DES/CBC/NoPadding 模式的输入内容必须为 8 字节的整数倍
    //         */
    //        @Test
    //        public void testDES_CBC_NOPADDING() throws Exception {
    //            ICrypto desCbcNoPadding = CryptoUtil.getDesCbcNoPadding(DES_NAME);
    //            System.out.println("crypto class: " + desCbcNoPadding.getClass()
    //                                                                 .getSimpleName());
    //            long begin = System.currentTimeMillis();
    //            for (int i = 0; i < COUNT; i++) {
    //                String origin = MockUtil.anyLetterString(8, 8);
    //                String encrypt = desCbcNoPadding.encrypt(origin);
    //                String decrypt = desCbcNoPadding.decrypt(encrypt);
    //                //            System.out.println("原文: " + origin);
    //                //            System.out.println("密文: " + encrypt);
    //                //            System.out.println("明文: " + decrypt);
    //                Assertions.assertEquals(origin, decrypt);
    //
    //            }
    //            long end = System.currentTimeMillis();
    //            System.out.println("总耗时: " + (end - begin) + " ms");
    //            System.out.println("平均耗时: " + (end - begin) / COUNT + " ms");
    //        }
    //    }

    @Test
    public void test() throws Exception {
        encryptAndDecrypt(CryptoUtil.getAes(Bytes16Key));
        encryptAndDecrypt(CryptoUtil.getAesEcbPkcs5Padding(Bytes16Key));
        encryptAndDecrypt(CryptoUtil.getAesCbcPkcs5padding(Bytes16Key));
        //        encryptAndDecrypt(CryptoUtil.getAesCbcNoPadding(Bytes16Key));
    }

    private void encryptAndDecrypt(ICrypto crypto) throws BadPaddingException, IllegalBlockSizeException {
        test1(crypto);
        test2(crypto);
        test3(crypto);
    }

    private void test1(ICrypto crypto) throws BadPaddingException, IllegalBlockSizeException {
        byte[] ciphertext = crypto.encryptToBytes(TEST_CONTENT.getBytes());
        byte[] plaintext = crypto.decryptToBytes(ciphertext);
        Assertions.assertArrayEquals(TEST_CONTENT.getBytes(), plaintext);
    }

    private void test2(ICrypto crypto) throws BadPaddingException, IllegalBlockSizeException {
        byte[] ciphertext = crypto.encryptToBytes(TEST_CONTENT);
        byte[] plaintext = crypto.decryptToBytes(ciphertext);
        Assertions.assertArrayEquals(TEST_CONTENT.getBytes(), plaintext);
    }

    private void test3(ICrypto crypto) throws BadPaddingException, IllegalBlockSizeException {
        String ciphertext = crypto.encryptToString(TEST_CONTENT.getBytes());
        byte[] plaintext = crypto.decryptToBytes(ciphertext);
        Assertions.assertArrayEquals(TEST_CONTENT.getBytes(), plaintext);
    }
}
