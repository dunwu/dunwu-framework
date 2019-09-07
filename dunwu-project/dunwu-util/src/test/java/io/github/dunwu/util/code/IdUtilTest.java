package io.github.dunwu.util.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @see IdUtil
 */
public class IdUtilTest {

	public static final int COUNT = 100000;

	@Test
	public void test() {
		System.out.println("randomUuid: " + IdUtil.randomUuid());
		System.out.println("uuid2: " + IdUtil.uuid2());
		System.out.println("randomLong: " + IdUtil.randomLong());
		System.out.println("randomBase64: " + IdUtil.randomBase64(8));
	}

	@Test
	public void randomId8() {
		Set<String> ids = new HashSet<>();
		for (int i = 0; i < COUNT; i++) {
			String id = IdUtil.randomId8();
			ids.add(id);
			// System.out.println("base32Hex: " + id);
		}
		Assertions.assertEquals(ids.size(), COUNT);
	}

	@Test
	void testDistributedId() {
		long begin = System.currentTimeMillis();
		Set<Long> set = new HashSet<>();
		for (int i = 0; i < COUNT; i++) {
			IdUtil.DistributedId worker = new IdUtil.DistributedId(1, 1, i);
			long id = worker.nextId();
			set.add(id);
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Assertions.assertEquals(set.size(), COUNT);
		long end = System.currentTimeMillis();
		System.out.println("[DistributedId] time: " + (end - begin) + " ms");
	}

}
