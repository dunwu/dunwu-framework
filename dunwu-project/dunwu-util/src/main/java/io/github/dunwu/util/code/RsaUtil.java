package io.github.dunwu.util.code;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-16
 */
public class RsaUtil {

	public static final RsaDigest RSA = RsaDigest.getInstance(RsaTypeEnum.RSA);

	public static final RsaDigest MD5_WITH_RSA = RsaDigest
		.getInstance(RsaTypeEnum.MD5_WITH_RSA);

	public static final RsaDigest SHA1_WITH_RSA = RsaDigest
		.getInstance(RsaTypeEnum.SHA1_WITH_RSA);

	/**
	 * 数字摘要类型
	 */
	public enum RsaTypeEnum {

		/**
		 * DSA 数字摘要算法
		 */
		RSA("RSA"), MD5_WITH_RSA("MD5withRSA"), SHA1_WITH_RSA("SHA1withRSA");

		private final String value;

		RsaTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static class RsaDigest implements ISignature {

		/**
		 * DSA密钥长度默认1024位。 密钥长度必须是64的整数倍，范围在512~1024之间
		 */
		private static final int KEY_SIZE = 1024;

		private static final String ALGORITHM_RSA = "RSA";

		private final String type;

		private final KeyPair keyPair;

		private RsaDigest(String type) throws NoSuchAlgorithmException {
			this.type = type;
			this.keyPair = genKeyPair(ALGORITHM_RSA, KEY_SIZE);
		}

		public static RsaDigest getInstance(String type) {
			try {
				return new RsaDigest(type);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static RsaDigest getInstance(RsaTypeEnum type) {
			try {
				return new RsaDigest(type.getValue());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public byte[] sign(byte[] plaintext, byte[] privateKey) throws Exception {
			// 取得私钥
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PrivateKey key = keyFactory.generatePrivate(keySpec);

			// 生成签名
			Signature signature = Signature.getInstance(this.type);
			signature.initSign(key);
			signature.update(plaintext);
			return signature.sign();
		}

		@Override
		public boolean verify(byte[] plaintext, byte[] publicKey, byte[] ciphertext)
			throws Exception {
			// 取得公钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
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
				return this.keyPair.getPrivate().getEncoded();
			}
			return null;
		}

		@Override
		public byte[] getPublicKey() {
			if (this.keyPair != null) {
				return this.keyPair.getPublic().getEncoded();
			}
			return null;
		}

		public byte[] encryptByPublicKey(byte[] plaintext, byte[] publicKey)
			throws Exception {
			// 取得公钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key key = keyFactory.generatePublic(keySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plaintext);
		}

		public byte[] decryptByPrivateKey(byte[] ciphertext, byte[] privateKey)
			throws Exception {
			// 取得私钥
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key key = keyFactory.generatePrivate(keySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(ciphertext);
		}

		public byte[] encryptByPrivateKey(byte[] plaintext, byte[] privateKey)
			throws Exception {
			// 取得私钥
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key key = keyFactory.generatePrivate(keySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plaintext);
		}

		public byte[] decryptByPublicKey(byte[] ciphertext, byte[] publicKey)
			throws Exception {
			// 取得私钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			Key key = keyFactory.generatePublic(keySpec);

			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(ciphertext);
		}

	}

}
