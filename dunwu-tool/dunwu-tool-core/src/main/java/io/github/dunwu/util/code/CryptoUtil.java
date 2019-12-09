package io.github.dunwu.util.code;

import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密/解密工具类
 * <p>
 * 支持 AES/DES 两类加密/解密算法族
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-19
 */
public class CryptoUtil {

	public static final String AES_NAME = "AES";

	public static final String DES_NAME = "DES";

	public static ICrypto getAes(String key) throws Exception {
		return AesCrypto.getInstace(SymmetricCryptoEnum.AES, key);
	}

	public static ICrypto getAesCbcNoPadding(String key) throws Exception {
		return AesCrypto.getInstace(SymmetricCryptoEnum.AES_CBC_NOPADDING, key);
	}

	public static ICrypto getAesCbcPkcs5padding(String key) throws Exception {
		return AesCrypto.getInstace(SymmetricCryptoEnum.AES_CBC_PKCS5PADDING, key);
	}

	public static ICrypto getAesEcbPkcs5Padding(String key) throws Exception {
		return AesCrypto.getInstace(SymmetricCryptoEnum.AES_ECB_PKCS5PADDING, key);
	}

	public static ICrypto getDes(String key) throws Exception {
		return DesCrypto.getInstace(SymmetricCryptoEnum.DES, key);
	}

	public static ICrypto getDesCbcNoPadding(String key) throws Exception {
		return DesCrypto.getInstace(SymmetricCryptoEnum.DES_CBC_NOPADDING, key);
	}

	public static ICrypto getDesCbcPkcs5Padding(String key) throws Exception {
		return DesCrypto.getInstace(SymmetricCryptoEnum.DES_CBC_PKCS5PADDING, key);
	}

	public static ICrypto getDesEcbPkcs5Padding(String key) throws Exception {
		return DesCrypto.getInstace(SymmetricCryptoEnum.DES_ECB_PKCS5PADDING, key);
	}

	public static ICrypto getInstace(String type, String key) throws Exception {
		String str = type.toUpperCase();
		if (str.contains(AES_NAME)) {
			return AesCrypto.getInstace(type, key);
		} else if (str.contains(DES_NAME)) {
			return DesCrypto.getInstace(type, key);
		}
		return null;
	}

	/**
	 * 对称加密/解密算法类型
	 */
	public enum SymmetricCryptoEnum {

		/**
		 * AES
		 */
		AES("AES"),
		/**
		 * AES_ECB_PKCS5PADDING
		 */
		AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding"),
		/**
		 * AES_CBC_PKCS5PADDING
		 */
		AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding"),
		/**
		 * AES_CBC_NOPADDING
		 */
		AES_CBC_NOPADDING("AES/CBC/NoPadding"),
		/**
		 * DES
		 */
		DES("DES"),
		/**
		 * DES_ECB_PKCS5PADDING
		 */
		DES_ECB_PKCS5PADDING("DES/ECB/PKCS5Padding"),
		/**
		 * DES_CBC_PKCS5PADDING
		 */
		DES_CBC_PKCS5PADDING("DES/CBC/PKCS5Padding"),
		/**
		 * DES_CBC_NOPADDING
		 */
		DES_CBC_NOPADDING("DES/CBC/NoPadding");

		private final String value;

		SymmetricCryptoEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * AES 加密/解密算法
	 */
	public static class AesCrypto implements ICrypto {

		static final String INIT_VECTOR = "1234567890ABCDEF";

		static final String AES_DEFAULT_KEY = "1234567890ABCDEF";

		private Key key;

		private Cipher encryptCipher;

		private Cipher decryptCipher;

		private AesCrypto(String type, String key) throws Exception {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
			this.key = getKey(key);
			this.encryptCipher = Cipher.getInstance(type);
			this.decryptCipher = Cipher.getInstance(type);
			if (type.equals(SymmetricCryptoEnum.AES_CBC_PKCS5PADDING.getValue())
				|| type.equals(SymmetricCryptoEnum.AES_CBC_NOPADDING.getValue())) {
				this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key, iv);
				this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key, iv);
			} else {
				this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key);
				this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
			}
		}

		private Key getKey(String key) {
			if (StringUtils.isNotBlank(key)) {
				return new SecretKeySpec(key.getBytes(), AES_NAME);
			}

			return new SecretKeySpec(AES_DEFAULT_KEY.getBytes(), AES_NAME);
		}

		static ICrypto getInstace(String type, String key) throws Exception {
			return new AesCrypto(type, key);
		}

		static ICrypto getInstace(SymmetricCryptoEnum type, String key) throws Exception {
			return new AesCrypto(type.getValue(), key);
		}

		@Override
		public byte[] decryptToBytes(byte[] ciphertext)
			throws BadPaddingException, IllegalBlockSizeException {
			return this.decryptCipher.doFinal(ciphertext);
		}

		@Override
		public byte[] decryptToBytes(String ciphertext)
			throws BadPaddingException, IllegalBlockSizeException {
			Base64.Decoder decoder = Base64.getUrlDecoder();
			byte[] bytes = decoder.decode(ciphertext);
			return this.decryptCipher.doFinal(bytes);
		}

		@Override
		public byte[] encryptToBytes(byte[] plaintext)
			throws BadPaddingException, IllegalBlockSizeException {
			return this.encryptCipher.doFinal(plaintext);
		}

		@Override
		public String encryptToString(byte[] plaintext)
			throws BadPaddingException, IllegalBlockSizeException {
			byte[] bytes = this.encryptCipher.doFinal(plaintext);
			Base64.Encoder encoder = Base64.getUrlEncoder();
			return encoder.encodeToString(bytes);
		}

	}

	/**
	 * DES 加密/解密算法
	 */
	public static class DesCrypto implements ICrypto {

		static final String INIT_VECTOR = "ABCDEFGH";

		static final String DES_DEFAULT_KEY = "12345678";

		private Key key;

		private Cipher encryptCipher;

		private Cipher decryptCipher;

		private DesCrypto(String type, String key) throws Exception {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());
			this.key = getKey(key);
			this.encryptCipher = Cipher.getInstance(type);
			this.decryptCipher = Cipher.getInstance(type);
			if (type.equals(SymmetricCryptoEnum.DES_CBC_PKCS5PADDING.getValue())
				|| type.equals(SymmetricCryptoEnum.DES_CBC_NOPADDING.getValue())) {
				this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key, iv);
				this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key, iv);
			} else {
				this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.key);
				this.decryptCipher.init(Cipher.DECRYPT_MODE, this.key);
			}
		}

		private Key getKey(String key) {
			if (StringUtils.isNotBlank(key)) {
				return new SecretKeySpec(key.getBytes(), DES_NAME);
			}

			return new SecretKeySpec(DES_DEFAULT_KEY.getBytes(), DES_NAME);
		}

		static ICrypto getInstace(String type, String key) throws Exception {
			return new DesCrypto(type, key);
		}

		static ICrypto getInstace(SymmetricCryptoEnum type, String key) throws Exception {
			return new DesCrypto(type.getValue(), key);
		}

		@Override
		public byte[] decryptToBytes(byte[] ciphertext)
			throws BadPaddingException, IllegalBlockSizeException {
			return this.decryptCipher.doFinal(ciphertext);
		}

		@Override
		public byte[] decryptToBytes(String ciphertext)
			throws BadPaddingException, IllegalBlockSizeException {
			Base64.Decoder decoder = Base64.getUrlDecoder();
			byte[] bytes = decoder.decode(ciphertext);
			return this.decryptCipher.doFinal(bytes);
		}

		@Override
		public byte[] encryptToBytes(byte[] plaintext)
			throws BadPaddingException, IllegalBlockSizeException {
			return this.encryptCipher.doFinal(plaintext);
		}

		@Override
		public String encryptToString(byte[] plaintext)
			throws BadPaddingException, IllegalBlockSizeException {
			byte[] bytes = this.encryptCipher.doFinal(plaintext);
			Base64.Encoder encoder = Base64.getUrlEncoder();
			return encoder.encodeToString(bytes);
		}

	}

}
