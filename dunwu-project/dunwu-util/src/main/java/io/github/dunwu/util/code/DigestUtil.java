package io.github.dunwu.util.code;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
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

	private static final String HMAC = "HMAC";

	public static final IDigest MD2 = Digest.getInstance(DigestTypeEnum.MD2);

	public static final IDigest MD5 = Digest.getInstance(DigestTypeEnum.MD5);

	public static final IDigest SHA1 = Digest.getInstance(DigestTypeEnum.SHA1);

	public static final IDigest SHA256 = Digest.getInstance(DigestTypeEnum.SHA256);

	public static final IDigest SHA384 = Digest.getInstance(DigestTypeEnum.SHA384);

	public static final IDigest SHA512 = Digest.getInstance(DigestTypeEnum.SHA512);

	public static final IDigest HMAC_MD5 = HmacDigest
			.getInstance(DigestTypeEnum.HMAC_MD5);

	public static final IDigest HMAC_SHA1 = HmacDigest
			.getInstance(DigestTypeEnum.HMAC_SHA1);

	public static final IDigest HMAC_SHA256 = HmacDigest
			.getInstance(DigestTypeEnum.HMAC_SHA256);

	public static final IDigest HMAC_SHA384 = HmacDigest
			.getInstance(DigestTypeEnum.HMAC_SHA384);

	public static final IDigest HMAC_SHA512 = HmacDigest
			.getInstance(DigestTypeEnum.HMAC_SHA512);

	public static IDigest getInstance(String type) {

		IDigest instace = null;
		if (type.toUpperCase().contains(HMAC)) {
			instace = HmacDigest.getInstance(type);
		}
		else {
			instace = Digest.getInstance(type);
		}

		return instace;
	}

	public static IDigest getInstance(DigestTypeEnum type) {
		IDigest instace = null;

		switch (type) {
		case MD2:
		case MD5:
		case SHA1:
		case SHA256:
		case SHA384:
		case SHA512:
			instace = Digest.getInstance(type);
			break;

		case HMAC_MD5:
		case HMAC_SHA1:
		case HMAC_SHA256:
		case HMAC_SHA384:
		case HMAC_SHA512:
			instace = HmacDigest.getInstance(type);
			break;
		default:
			break;
		}

		return instace;
	}

	public static KeyPair genKeyPair(String algorithm, int keySize)
			throws NoSuchAlgorithmException {

		// 初始化密钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
		// 实例化密钥对生成器
		keyPairGen.initialize(keySize);
		// 实例化密钥对
		return keyPairGen.genKeyPair();
	}

	public interface IDigest {

		byte[] digest(byte[] input);

		byte[] digestWithBase64(byte[] input);

	}

	public static class Digest implements IDigest {

		private MessageDigest md;

		private Digest(String type) {
			try {
				md = MessageDigest.getInstance(type);
			}
			catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		public static IDigest getInstance(String type) {
			return new Digest(type);
		}

		public static IDigest getInstance(DigestTypeEnum type) {
			return new Digest(type.getValue());
		}

		@Override
		public byte[] digest(byte[] input) {
			return md.digest(input);
		}

		@Override
		public byte[] digestWithBase64(byte[] input) {
			return Base64.getUrlEncoder().encode(md.digest(input));
		}

	}

	public static class HmacDigest implements IDigest {

		private Mac mac;

		private HmacDigest(String type) {
			SecretKeySpec keySpec = new SecretKeySpec(PRIVATE_KEY.getBytes(), type);
			try {
				mac = Mac.getInstance(keySpec.getAlgorithm());
				mac.init(keySpec);
			}
			catch (NoSuchAlgorithmException | InvalidKeyException e) {
				e.printStackTrace();
			}
		}

		public static IDigest getInstance(String type) {
			return new HmacDigest(type);
		}

		public static IDigest getInstance(DigestTypeEnum type) {
			return new HmacDigest(type.getValue());
		}

		@Override
		public byte[] digest(byte[] input) {
			return mac.doFinal(input);
		}

		@Override
		public byte[] digestWithBase64(byte[] input) {
			return Base64.getUrlEncoder().encode(mac.doFinal(input));
		}

	}

	/**
	 * 数字摘要类型
	 */
	public enum DigestTypeEnum {

		/**
		 * 常规数字摘要算法
		 */
		MD2("MD2"), MD5("MD5"), SHA1("SHA1"), SHA256("SHA-256"), SHA384(
				"SHA-384"), SHA512("SHA-512"),

		/**
		 * HMAC 数字摘要算法
		 */
		HMAC_MD5("HmacMD5"), HMAC_SHA1("HmacSHA1"), HMAC_SHA256(
				"HmacSHA256"), HMAC_SHA384("HmacSHA384"), HMAC_SHA512("HmacSHA512");

		private final String value;

		DigestTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
