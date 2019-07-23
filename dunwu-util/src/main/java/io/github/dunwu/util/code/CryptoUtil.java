package io.github.dunwu.util.code;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类. 支持Hex与Base64两种编码方式.
 */
public class CryptoUtil {

    public static KeyCrypto getAes(String key) {
        return AesCrypto.getInstace(AesCrypto.AesType.AES, key);
    }

    public static KeyCrypto getAesEcbPkcs5Padding(String key) {
        return AesCrypto.getInstace(AesCrypto.AesType.AES_ECB_PKCS5PADDING, key);
    }

    public static KeyCrypto getAesCbcPkcs5padding(String key) {
        return AesCrypto.getInstace(AesCrypto.AesType.AES_CBC_PKCS5PADDING, key);
    }

    public static KeyCrypto getAesCbcNoPadding(String key) {
        return AesCrypto.getInstace(AesCrypto.AesType.AES_CBC_NOPADDING, key);
    }

    public static KeyCrypto getDes(String key) {
        return DesCrypto.getInstace(DesCrypto.DesType.DES, key);
    }

    public static KeyCrypto getDesEcbPkcs5Padding(String key) {
        return DesCrypto.getInstace(DesCrypto.DesType.DES_ECB_PKCS5PADDING, key);
    }

    public static KeyCrypto getDesCbcPkcs5Padding(String key) {
        return DesCrypto.getInstace(DesCrypto.DesType.DES_CBC_PKCS5PADDING, key);
    }

    public static KeyCrypto getDesCbcNoPadding(String key) {
        return DesCrypto.getInstace(DesCrypto.DesType.DES_CBC_NOPADDING, key);
    }


    interface KeyCrypto {
        String encrypt(String input);
        String decrypt(String input);
    }


    public static class AesCrypto implements KeyCrypto {
        private Key key;
        private Cipher encryptCipher;
        private Cipher decryptCipher;

        public static final String INIT_VECTOR = "1234567890ABCDEF";

        private AesCrypto(AesType type, String key) {
            try {
                IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
                this.key = new SecretKeySpec(key.getBytes(), "AES");
                this.encryptCipher = Cipher.getInstance(type.key());
                this.decryptCipher = Cipher.getInstance(type.key());
                if (type == AesType.AES_CBC_PKCS5PADDING || type == AesType.AES_CBC_NOPADDING) {
                    this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key, iv);
                    this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key, iv);
                } else {
                    this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key);
                    this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static KeyCrypto getInstace(AesType type, String key) {
            return new AesCrypto(type, key);
        }

        @Override
        public String encrypt(String input) {
            try {
                byte[] bytes = this.encryptCipher.doFinal(input.getBytes());
                return Base64.getUrlEncoder()
                             .encodeToString(bytes);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String decrypt(String input) {
            try {
                byte[] bytes = Base64.getUrlDecoder()
                                     .decode(input);
                return new String(this.decryptCipher.doFinal(bytes));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        public enum AesType {
            AES("AES"),
            AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding"),
            AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding"),
            AES_CBC_NOPADDING("AES/CBC/NoPadding");

            private String key;

            AesType(String key) {
                this.key = key;
            }

            public String key() {
                return this.key;
            }
        }
    }


    public static class DesCrypto implements KeyCrypto {
        private Key key;
        private Cipher encryptCipher;
        private Cipher decryptCipher;

        public static final String INIT_VECTOR = "ABCDEFGH";

        private DesCrypto(DesType type, String key) {
            try {
                DESKeySpec desKey = new DESKeySpec(key.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                this.key = keyFactory.generateSecret(desKey);

                IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
                this.encryptCipher = Cipher.getInstance(type.key());
                this.decryptCipher = Cipher.getInstance(type.key());
                if (type == DesType.DES_CBC_PKCS5PADDING || type == DesType.DES_CBC_NOPADDING) {
                    this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key, iv);
                    this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key, iv);
                } else {
                    this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key);
                    this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static KeyCrypto getInstace(DesCrypto.DesType type, String key) {
            return new DesCrypto(type, key);
        }

        @Override
        public String encrypt(String input) {
            try {
                byte[] bytes = this.encryptCipher.doFinal(input.getBytes());
                return Base64.getUrlEncoder()
                             .encodeToString(bytes);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String decrypt(String input) {
            try {
                byte[] bytes = Base64.getUrlDecoder()
                                     .decode(input);
                return new String(this.decryptCipher.doFinal(bytes));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
            return null;
        }

        public enum DesType {
            DES("DES"),
            DES_ECB_PKCS5PADDING("DES/ECB/PKCS5Padding"),
            DES_CBC_PKCS5PADDING("DES/CBC/PKCS5Padding"),
            DES_CBC_NOPADDING("DES/CBC/NoPadding");

            private String key;

            DesType(String key) {
                this.key = key;
            }

            public String key() {
                return this.key;
            }
        }
    }
}
