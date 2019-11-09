package io.github.dunwu.util.collection;

import com.google.common.collect.Ordering;
import io.github.dunwu.util.collection.MapUtils.ValueCreator;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MapUtilsTest {

	@Test
	public void generalMethod() {
		HashMap<String, Integer> map = new HashMap<>(1);
		assertThat(MapUtils.isEmpty(map)).isTrue();
		assertThat(MapUtils.isEmpty(null)).isTrue();
		assertThat(MapUtils.isNotEmpty(map)).isFalse();
		assertThat(MapUtils.isNotEmpty(null)).isFalse();

		map.put("haha", 1);
		assertThat(MapUtils.isEmpty(map)).isFalse();
		assertThat(MapUtils.isNotEmpty(map)).isTrue();

		//////////
		ConcurrentMap<String, Integer> map2 = new ConcurrentHashMap<String, Integer>();
		assertThat(MapUtils.putIfAbsentReturnLast(map2, "haha", 3)).isEqualTo(3);
		assertThat(MapUtils.putIfAbsentReturnLast(map2, "haha", 4)).isEqualTo(3);

		MapUtils.createIfAbsentReturnLast(map2, "haha", new ValueCreator<Integer>() {
			@Override
			public Integer get() {
				return 5;
			}
		});

		assertThat(map2).hasSize(1).containsEntry("haha", 3);

		MapUtils.createIfAbsentReturnLast(map2, "haha2", new ValueCreator<Integer>() {
			@Override
			public Integer get() {
				return 5;
			}
		});

		assertThat(map2).hasSize(2).containsEntry("haha2", 5);
	}

	@Test
	public void guavaBuildMap() {
		HashMap<String, Integer> map1 = new HashMap<>(1);

		HashMap<String, Integer> map2 = MapUtils.newHashMapWithCapacity(10, 0.5f);
		map2 = MapUtils.newHashMapWithCapacity(10, 0.5f);

		HashMap<String, Integer> map3 = new HashMap<>(1);
		assertThat(map3).hasSize(1).containsEntry("1", 1);

		HashMap<String, Integer> map4 = MapUtils.newHashMap(new String[] { "1", "2" },
			new Integer[] { 1, 2 });
		assertThat(map4).hasSize(2).containsEntry("1", 1).containsEntry("2", 2);

		HashMap<String, Integer> map5 = MapUtils.newHashMap(Arrays.asList("1", "2", "3"),
			Arrays.asList(1, 2, 3));
		assertThat(map5).hasSize(3).containsEntry("1", 1).containsEntry("2", 2)
			.containsEntry("3", 3);

		TreeMap<String, Integer> map6 = MapUtils.newSortedMap();

		TreeMap<String, Integer> map7 = MapUtils.newSortedMap(Ordering.natural());

		ConcurrentSkipListMap map10 = MapUtils.newConcurrentSortedMap();

		EnumMap map11 = MapUtils.newEnumMap(EnumA.class);
	}

	@Test
	public void jdkBuildMap() {
		Map<String, Integer> map1 = MapUtils.emptyMap();
		assertThat(map1).hasSize(0);

		Map<String, Integer> map2 = MapUtils.emptyMapIfNull(null);
		assertThat(map2).isNotNull().hasSize(0);

		Map<String, Integer> map3 = MapUtils.emptyMapIfNull(map1);
		assertThat(map3).isSameAs(map1);

		Map<String, Integer> map4 = MapUtils.singletonMap("haha", 1);
		assertThat(map4).hasSize(1).containsEntry("haha", 1);
		try {
			map4.put("dada", 2);
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(t).isInstanceOf(UnsupportedOperationException.class);
		}

		Map<String, Integer> map5 = new HashMap<>(1);
		Map<String, Integer> map6 = MapUtils.unmodifiableMap(map5);

		try {
			map6.put("a", 2);
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(t).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	@Test
	public void sortAndTop() {
		Map<String, Integer> map = MapUtils.newHashMap(new String[] { "A", "B", "C" },
			new Integer[] { 3, 1, 2 });
		// sort
		Map<String, Integer> resultMap = MapUtils.sortByValue(map, false);
		assertThat(resultMap.toString()).isEqualTo("{B=1, C=2, A=3}");
		resultMap = MapUtils.sortByValue(map, true);
		assertThat(resultMap.toString()).isEqualTo("{A=3, C=2, B=1}");

		resultMap = MapUtils.sortByValue(map, Ordering.natural());
		assertThat(resultMap.toString()).isEqualTo("{B=1, C=2, A=3}");
		resultMap = MapUtils.sortByValue(map, Ordering.natural().reverse());
		assertThat(resultMap.toString()).isEqualTo("{A=3, C=2, B=1}");

		// Top n
		resultMap = MapUtils.topByValue(map, false, 2);
		assertThat(resultMap.toString()).isEqualTo("{B=1, C=2}");
		resultMap = MapUtils.topByValue(map, true, 2);
		assertThat(resultMap.toString()).isEqualTo("{A=3, C=2}");

		resultMap = MapUtils.topByValue(map, Ordering.natural(), 2);
		assertThat(resultMap.toString()).isEqualTo("{B=1, C=2}");
		resultMap = MapUtils.topByValue(map, Ordering.natural().reverse(), 2);
		assertThat(resultMap.toString()).isEqualTo("{A=3, C=2}");

		// top Size > array Size
		resultMap = MapUtils.topByValue(map, false, 4);
		assertThat(resultMap.toString()).isEqualTo("{B=1, C=2, A=3}");
		resultMap = MapUtils.topByValue(map, true, 4);
		assertThat(resultMap.toString()).isEqualTo("{A=3, C=2, B=1}");
	}

	@Test
	public void weakMap() {
		ConcurrentMap<MyBean, MyBean> weakKeyMap = MoreMaps.createWeakKeyConcurrentMap(10,
			1);
		initExpireAllMap(weakKeyMap);
		System.gc();
		assertThat(weakKeyMap.get(new MyBean("A"))).isNull();
		assertThat(weakKeyMap).hasSize(1); // key仍然在

		ConcurrentMap<MyBean, MyBean> weakKeyMap2 = MoreMaps
			.createWeakKeyConcurrentMap(10, 1);
		MyBean value = new MyBean("B");
		initExpireKeyMap(weakKeyMap2, value);
		System.gc();
		assertThat(weakKeyMap2.get(new MyBean("A"))).isNull();

		ConcurrentMap<MyBean, MyBean> weakKeyMap3 = MoreMaps
			.createWeakKeyConcurrentMap(10, 1);
		MyBean key = new MyBean("A");
		initExpireValueMap(weakKeyMap3, key);
		System.gc();
		assertThat(weakKeyMap3.get(key)).isEqualTo(new MyBean("B"));

		// weak value
		ConcurrentMap<MyBean, MyBean> weakValueMap = MoreMaps
			.createWeakValueConcurrentMap(10, 1);
		initExpireAllMap(weakValueMap);
		System.gc();
		assertThat(weakValueMap.get(new MyBean("A"))).isNull();

		ConcurrentMap<MyBean, MyBean> weakValueMap2 = MoreMaps
			.createWeakValueConcurrentMap(10, 1);
		MyBean value2 = new MyBean("B");
		initExpireKeyMap(weakValueMap2, value2);
		System.gc();
		assertThat(weakValueMap2.get(new MyBean("A"))).isEqualTo(new MyBean("B"));

		ConcurrentMap<MyBean, MyBean> weakValueMap3 = MoreMaps
			.createWeakValueConcurrentMap(10, 1);
		MyBean key3 = new MyBean("A");
		initExpireValueMap(weakValueMap3, key3);
		System.gc();
		assertThat(weakValueMap3.get(new MyBean("A"))).isNull();
	}

	// 抽出子函数，使得Key/Value的生命周期过期
	private void initExpireAllMap(ConcurrentMap<MyBean, MyBean> weakKeyMap) {
		MyBean key = new MyBean("A");
		MyBean value = new MyBean("B");
		weakKeyMap.put(key, value);
		assertThat(weakKeyMap.get(key)).isEqualTo(value);
	}

	// 抽出子函数，使得key过期，value不过期
	private void initExpireKeyMap(ConcurrentMap<MyBean, MyBean> weakKeyMap,
		MyBean value) {
		MyBean key = new MyBean("A");
		weakKeyMap.put(key, value);
		assertThat(weakKeyMap.get(key)).isEqualTo(value);
	}

	// 抽出子函数，使得key不过期，value过期
	private void initExpireValueMap(ConcurrentMap<MyBean, MyBean> weakKeyMap,
		MyBean key) {
		MyBean value = new MyBean("B");
		weakKeyMap.put(key, value);
		assertThat(weakKeyMap.get(key)).isEqualTo(value);
	}

	// 抽出子函数，使得Key/Value的生命周琦过期
	private void initWeakValue(ConcurrentMap<MyBean, MyBean> weakKeyMap) {
		MyBean key = new MyBean("A");
		MyBean value = new MyBean("B");
		weakKeyMap.put(key, value);
		assertThat(weakKeyMap.get(new MyBean("A"))).isEqualTo(value);
	}

	public enum EnumA {

		A,
		B,
		C
	}

	public static class MyBean {

		String name;

		public MyBean(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			MyBean other = (MyBean) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

	}

}
