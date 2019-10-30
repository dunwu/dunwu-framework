package io.github.dunwu.util.code;

import io.github.dunwu.util.mock.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-19
 */
public class DigestUtilTest {

	private static final int COUNT = 10000;

	private void doDigest(DigestUtil.IDigest digest) {
		List<String> originList = new ArrayList<>();
		List<String> digestList = new ArrayList<>();

		long begin = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			String msg = MockUtil.anyLetterString(3, 50);
			byte[] bytes = digest.digest(msg.getBytes());
			originList.add(msg);
			digestList.add(new String(bytes));
		}
		long end = System.currentTimeMillis();

		System.out.println("digest 总耗时：" + (end - begin) + "ms");
		System.out.println("digest 平均耗时：" + (end - begin) / COUNT + "ms");
	}

	private void doDigestWithBase64(DigestUtil.IDigest digest) {
		List<String> originList = new ArrayList<>();
		List<String> digestList = new ArrayList<>();

		long begin = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			String msg = MockUtil.anyLetterString(3, 50);
			byte[] bytes = digest.digestWithBase64(msg.getBytes());
			originList.add(msg);
			digestList.add(new String(bytes));
		}
		long end = System.currentTimeMillis();

		System.out.println("digestWithBase64 总耗时：" + (end - begin) + "ms");
		System.out.println("digestWithBase64 平均耗时：" + (end - begin) / COUNT + "ms");
	}

	@Nested
	class MessageDigestTest {

		@Test
		@DisplayName("MD2 摘要")
		void testMD2() {
			doDigest(DigestUtil.MD2);
			doDigestWithBase64(DigestUtil.MD2);
		}

		@Test
		@DisplayName("MD5 摘要")
		void testMD5() {
			doDigest(DigestUtil.MD5);
			doDigestWithBase64(DigestUtil.MD5);
		}

		@Test
		@DisplayName("SHA1 摘要")
		void testSHA1() {
			doDigest(DigestUtil.SHA1);
			doDigestWithBase64(DigestUtil.SHA1);
		}

		@Test
		@DisplayName("SHA256 摘要")
		void testSHA256() {
			doDigest(DigestUtil.SHA256);
			doDigestWithBase64(DigestUtil.SHA256);
		}

		@Test
		@DisplayName("SHA384 摘要")
		void testSHA384() {
			doDigest(DigestUtil.SHA384);
			doDigestWithBase64(DigestUtil.SHA384);
		}

		@Test
		@DisplayName("SHA512 摘要")
		void testSHA512() {
			doDigest(DigestUtil.SHA512);
			doDigestWithBase64(DigestUtil.SHA512);
		}

	}

	@Nested
	class HmacMessageDigestTest {

		@Test
		@DisplayName("HmacMD5 摘要")
		void testHmacMD5() {
			doDigest(DigestUtil.HMAC_MD5);
			doDigestWithBase64(DigestUtil.HMAC_MD5);
		}

		@Test
		@DisplayName("HmacSHA1 摘要")
		void testHmacSHA1() {
			doDigest(DigestUtil.HMAC_SHA1);
			doDigestWithBase64(DigestUtil.HMAC_SHA1);
		}

		@Test
		@DisplayName("HmacSHA256 摘要")
		void testHmacSHA256() {
			doDigest(DigestUtil.HMAC_SHA256);
			doDigestWithBase64(DigestUtil.HMAC_SHA256);
		}

		@Test
		@DisplayName("HmacSHA384 摘要")
		void testHmacSHA384() {
			doDigest(DigestUtil.HMAC_SHA384);
			doDigestWithBase64(DigestUtil.HMAC_SHA384);
		}

		@Test
		@DisplayName("HmacSHA512 摘要")
		void testHmacSHA512() {
			doDigest(DigestUtil.HMAC_SHA512);
			doDigestWithBase64(DigestUtil.HMAC_SHA512);
		}

	}

}
