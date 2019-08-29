package io.github.dunwu.util.code;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-16
 */
public class DsaUtil {

    public static final ISignature DSA = DsaDigest.getInstance(DsaTypeEnum.DSA);
    public static final ISignature SHA1_WITH_DSA = DsaDigest.getInstance(DsaTypeEnum.SHA1_WITH_DSA);


    public static class DsaDigest implements ISignature {

        /**
         * DSA密钥长度默认1024位。 密钥长度必须是64的整数倍，范围在512~1024之间
         */
        private static final int KEY_SIZE = 1024;
        private static final String ALGORITHM_DSA = "DSA";

        private final String type;
        private final KeyPair keyPair;

        private DsaDigest(String type) throws NoSuchAlgorithmException {
            this.type = type;
            this.keyPair = genKeyPair(ALGORITHM_DSA, KEY_SIZE);
        }

        public static ISignature getInstance(String type) {
            try {
                return new DsaDigest(type);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static ISignature getInstance(DsaTypeEnum type) {
            try {
                return new DsaDigest(type.getValue());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public byte[] sign(byte[] plaintext, byte[] privateKey) throws Exception {
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_DSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);

            // 生成签名
            Signature signature = Signature.getInstance(this.type);
            signature.initSign(key);
            signature.update(plaintext);
            return signature.sign();
        }

        @Override
        public boolean verify(byte[] plaintext, byte[] publicKey, byte[] ciphertext) throws Exception {
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_DSA);
            PublicKey key = keyFactory.generatePublic(keySpec);

            // 认证签名
            Signature signature = Signature.getInstance(this.type);
            signature.initVerify(key);
            signature.update(plaintext);
            return signature.verify(ciphertext);
        }

        @Override
        public byte[] getPrivateKey() {
            if (this.keyPair != null) {
                return this.keyPair.getPrivate()
                                   .getEncoded();
            }
            return null;
        }

        @Override
        public byte[] getPublicKey() {
            if (this.keyPair != null) {
                return this.keyPair.getPublic()
                                   .getEncoded();
            }
            return null;
        }
    }


    /**
     * 数字摘要类型
     */
    public enum DsaTypeEnum {

        /**
         * DSA 数字摘要算法
         */
        DSA("DSA"),
        SHA1_WITH_DSA("SHA1withDSA");

        private final String value;

        DsaTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
