package io.github.dunwu.util.code;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 消息摘要工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-19
 */
public class DigestUtil {

    public static String PRIVATE_KEY = "天王盖地虎";
    public static String PUBLIC_KEY = "宝塔镇河妖";

    public static IDigest MD2 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.MD2);
    public static IDigest MD5 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.MD5);
    public static IDigest SHA1 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.SHA1);
    public static IDigest SHA256 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.SHA256);
    public static IDigest SHA384 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.SHA384);
    public static IDigest SHA512 = MessageDigestWrapper.getInstace(MessageDigestWrapper.MessageDigestType.SHA512);

    public static IDigest HmacMD5 = HmacMessageDigestWrapper.getInstace(
        HmacMessageDigestWrapper.HmacMessageDigestType.HmacMD5);
    public static IDigest HmacSHA1 = HmacMessageDigestWrapper.getInstace(
        HmacMessageDigestWrapper.HmacMessageDigestType.HmacSHA1);
    public static IDigest HmacSHA256 = HmacMessageDigestWrapper.getInstace(
        HmacMessageDigestWrapper.HmacMessageDigestType.HmacSHA256);
    public static IDigest HmacSHA384 = HmacMessageDigestWrapper.getInstace(
        HmacMessageDigestWrapper.HmacMessageDigestType.HmacSHA384);
    public static IDigest HmacSHA512 = HmacMessageDigestWrapper.getInstace(
        HmacMessageDigestWrapper.HmacMessageDigestType.HmacSHA512);


    public interface IDigest {

        default byte[] digest(byte[] input) {
            return null;
        }

        default byte[] digestWithBase64(byte[] input) {
            return null;
        }
    }


    static class MessageDigestWrapper implements IDigest {

        private MessageDigest md;

        private MessageDigestWrapper(MessageDigestWrapper.MessageDigestType type) {
            switch (type) {
                case MD2:
                case MD5:
                case SHA1:
                case SHA256:
                case SHA384:
                case SHA512:
                    try {
                        md = MessageDigest.getInstance(type.key());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

        public static IDigest getInstace(MessageDigestWrapper.MessageDigestType type) {
            return new MessageDigestWrapper(type);
        }

        @Override
        public byte[] digest(byte[] input) {
            return md.digest(input);
        }

        @Override
        public byte[] digestWithBase64(byte[] input) {
            return Base64.getUrlEncoder()
                         .encode(md.digest(input));
        }

        public enum MessageDigestType {
            MD2("MD2"),
            MD5("MD5"),
            SHA1("SHA1"),
            SHA256("SHA-256"),
            SHA384("SHA-384"),
            SHA512("SHA-512");

            private String key;

            MessageDigestType(String key) {
                this.key = key;
            }

            public String key() {
                return this.key;
            }
        }
    }


    static class HmacMessageDigestWrapper implements IDigest {

        private Mac mac;

        private HmacMessageDigestWrapper(HmacMessageDigestWrapper.HmacMessageDigestType type) {
            switch (type) {
                case HmacMD5:
                case HmacSHA1:
                case HmacSHA256:
                case HmacSHA384:
                case HmacSHA512:

                    try {
                        SecretKeySpec keySpec = new SecretKeySpec(PRIVATE_KEY.getBytes(), type.key());
                        mac = Mac.getInstance(keySpec.getAlgorithm());
                        mac.init(keySpec);
                    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

        public static IDigest getInstace(HmacMessageDigestWrapper.HmacMessageDigestType type) {
            return new HmacMessageDigestWrapper(type);
        }

        @Override
        public byte[] digest(byte[] input) {
            return mac.doFinal(input);
        }

        @Override
        public byte[] digestWithBase64(byte[] input) {
            return Base64.getUrlEncoder()
                         .encode(mac.doFinal(input));
        }

        public enum HmacMessageDigestType {
            HmacMD5("HmacMD5"),
            HmacSHA1("HmacSHA1"),
            HmacSHA256("HmacSHA256"),
            HmacSHA384("HmacSHA384"),
            HmacSHA512("HmacSHA512");

            private String key;

            HmacMessageDigestType(String key) {
                this.key = key;
            }

            public String key() {
                return this.key;
            }
        }
    }


    static class DsaMessageDigestWrapper {

        public static String PRIVATE_KEY = "天王盖地虎";
        public static String PUBLIC_KEY = "宝塔镇河妖";

        /**
         * DSA密钥长度默认1024位。 密钥长度必须是64的整数倍，范围在512~1024之间
         */
        private static final int KEY_SIZE = 1024;

        private KeyPair keyPair;
        private DsaMessageDigestType type;

        private DsaMessageDigestWrapper(DsaMessageDigestType type) {
            try {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(type.key());
                keyPairGen.initialize(KEY_SIZE);
                this.type = type;
                this.keyPair = keyPairGen.genKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        public static DsaMessageDigestWrapper getInstace(DsaMessageDigestWrapper.DsaMessageDigestType type) {
            return new DsaMessageDigestWrapper(type);
        }

        public byte[] signature(byte[] data, byte[] privateKey) throws Exception {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(type.key());
            PrivateKey key = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance(type.key());
            signature.initSign(key);
            signature.update(data);
            return signature.sign();
        }

        public boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(type.key());
            PublicKey key = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance(type.key());
            signature.initVerify(key);
            signature.update(data);
            return signature.verify(sign);
        }

        public byte[] getPublicKey() {
            return keyPair.getPublic()
                          .getEncoded();
        }

        public byte[] getPrivateKey() {
            return keyPair.getPrivate()
                          .getEncoded();
        }

        public enum DsaMessageDigestType {
            DSA("DSA"),
            SHA1withDSA("SHA1withDSA");

            private String key;

            DsaMessageDigestType(String key) {
                this.key = key;
            }

            public String key() {
                return this.key;
            }
        }
    }
}

