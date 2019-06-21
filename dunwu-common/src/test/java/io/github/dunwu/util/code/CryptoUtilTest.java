package io.github.dunwu.util.code;

import io.github.dunwu.util.mock.MockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CryptoUtilTest {

    public static final int COUNT = 10000;
    public static final String Bytes16Key = "1234567890ABCDEF";
    public static final String DES_KEY = "一二三四五";


    @Nested
    class AesTest {

        @Test
        public void testAES() {
            CryptoUtil.KeyCrypto keyCrypto = CryptoUtil.getAes(Bytes16Key);
            encryptAndDecrypt(keyCrypto);
        }

        @Test
        public void testAES_CBC_PKCS5PADDING() {
            CryptoUtil.KeyCrypto keyCrypto = CryptoUtil.getAesCbcPkcs5padding(Bytes16Key);
            encryptAndDecrypt(keyCrypto);
        }

        @Test
        public void testAES_ECB_PKCS5PADDING() {
            CryptoUtil.KeyCrypto keyCrypto = CryptoUtil.getAesEcbPkcs5Padding(Bytes16Key);
            encryptAndDecrypt(keyCrypto);
        }

        /**
         * AES/CBC/NoPadding 模式的输入内容必须为 8 字节的整数倍
         */
        @Test
        public void testAES_CBC_NOPADDING() {
            CryptoUtil.KeyCrypto aesCbcNoPadding = CryptoUtil.getAesCbcNoPadding(Bytes16Key);
            System.out.println("crypto class: " + aesCbcNoPadding.getClass()
                                                                 .getSimpleName());
            long begin = System.currentTimeMillis();
            for (int i = 0; i < COUNT; i++) {
                String origin = MockUtil.anyLetterString(32, 32);
                String encrypt = aesCbcNoPadding.encrypt(origin);
                String decrypt = aesCbcNoPadding.decrypt(encrypt);
                //            System.out.println("原文: " + origin);
                //            System.out.println("密文: " + encrypt);
                //            System.out.println("明文: " + decrypt);
                Assertions.assertEquals(origin, decrypt);
            }
            long end = System.currentTimeMillis();
            System.out.println("总耗时: " + (end - begin) + " ms");
            System.out.println("平均耗时: " + (end - begin) / COUNT + " ms");
        }
    }


    @Nested
    class DesTest {

        @Test
        public void testDES() {
            encryptAndDecrypt(CryptoUtil.getDes(DES_KEY));
        }

        @Test
        public void testDES_CBC_PKCS5PADDING() {
            encryptAndDecrypt(CryptoUtil.getDesCbcPkcs5Padding(DES_KEY));
        }

        @Test
        public void testDES_ECB_PKCS5PADDING() {
            encryptAndDecrypt(CryptoUtil.getDesEcbPkcs5Padding(DES_KEY));
        }

        /**
         * DES/CBC/NoPadding 模式的输入内容必须为 8 字节的整数倍
         */
        @Test
        public void testDES_CBC_NOPADDING() {
            CryptoUtil.KeyCrypto desCbcNoPadding = CryptoUtil.getDesCbcNoPadding(DES_KEY);
            System.out.println("crypto class: " + desCbcNoPadding.getClass()
                                                                 .getSimpleName());
            long begin = System.currentTimeMillis();
            for (int i = 0; i < COUNT; i++) {
                String origin = MockUtil.anyLetterString(8, 8);
                String encrypt = desCbcNoPadding.encrypt(origin);
                String decrypt = desCbcNoPadding.decrypt(encrypt);
                //            System.out.println("原文: " + origin);
                //            System.out.println("密文: " + encrypt);
                //            System.out.println("明文: " + decrypt);
                Assertions.assertEquals(origin, decrypt);

            }
            long end = System.currentTimeMillis();
            System.out.println("总耗时: " + (end - begin) + " ms");
            System.out.println("平均耗时: " + (end - begin) / COUNT + " ms");
        }
    }

    private static void encryptAndDecrypt(CryptoUtil.KeyCrypto crypto) {
        System.out.println("crypto class: " + crypto.getClass()
                                                    .getSimpleName());
        long begin = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            String origin = MockUtil.anyLetterString(3, 100);
            String encrypt = crypto.encrypt(origin);
            String decrypt = crypto.decrypt(encrypt);
            //            System.out.println("原文: " + origin);
            //            System.out.println("密文: " + encrypt);
            //            System.out.println("明文: " + decrypt);
            Assertions.assertEquals(origin, decrypt);
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时: " + (end - begin) + " ms");
        System.out.println("平均耗时: " + (end - begin) / COUNT + " ms");
    }
}
