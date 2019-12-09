package io.github.dunwu.util.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-16
 */
public class RsaUtilTest {

	public static final byte[] TEST_CONTENT = "Hello World".getBytes();

	@Test
	@DisplayName("测试 RSA 公钥加密，私钥解密")
	public void test1() throws Exception {
		doTestEncryptByPublicAndDecryptByPrivate(RsaUtil.RSA);
		doTestEncryptByPublicAndDecryptByPrivate(RsaUtil.MD5_WITH_RSA);
		doTestEncryptByPublicAndDecryptByPrivate(RsaUtil.SHA1_WITH_RSA);
	}

	private void doTestEncryptByPublicAndDecryptByPrivate(RsaUtil.RsaDigest rsaDigest)
		throws Exception {
		byte[] ciphertext = rsaDigest.encryptByPublicKey(TEST_CONTENT,
			rsaDigest.getPublicKey());
		byte[] plaintext = rsaDigest.decryptByPrivateKey(ciphertext,
			rsaDigest.getPrivateKey());
		Assertions.assertArrayEquals(TEST_CONTENT, plaintext);
	}

	@Test
	@DisplayName("测试 RSA 私钥加密，公钥解密")
	public void test2() throws Exception {
		doTestEncryptByPrivateAndDecryptByPublic(RsaUtil.RSA);
		doTestEncryptByPrivateAndDecryptByPublic(RsaUtil.MD5_WITH_RSA);
		doTestEncryptByPrivateAndDecryptByPublic(RsaUtil.SHA1_WITH_RSA);
	}

	private void doTestEncryptByPrivateAndDecryptByPublic(RsaUtil.RsaDigest rsaDigest)
		throws Exception {
		byte[] ciphertext = rsaDigest.encryptByPrivateKey(TEST_CONTENT,
			rsaDigest.getPrivateKey());
		byte[] plaintext = rsaDigest.decryptByPublicKey(ciphertext,
			rsaDigest.getPublicKey());
		Assertions.assertArrayEquals(TEST_CONTENT, plaintext);
	}

	@Test
	@DisplayName("测试 RSA 签名以及验证")
	public void testEncryptByPrivateAndDecryptByPublic() throws Exception {
		doTestSignAndVerify(RsaUtil.MD5_WITH_RSA);
		doTestSignAndVerify(RsaUtil.SHA1_WITH_RSA);
	}

	private void doTestSignAndVerify(RsaUtil.RsaDigest rsaDigest) throws Exception {
		byte[] ciphertext = rsaDigest.sign(TEST_CONTENT, rsaDigest.getPrivateKey());
		boolean verify = rsaDigest.verify(TEST_CONTENT, rsaDigest.getPublicKey(),
			ciphertext);
		Assertions.assertTrue(verify);
	}

}
