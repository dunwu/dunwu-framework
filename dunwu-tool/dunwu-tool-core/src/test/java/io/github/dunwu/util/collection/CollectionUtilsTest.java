package io.github.dunwu.util.collection;

import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

	@Test
	void elementsEqual() {
		List<String> list1 = Arrays.asList("d", "a", "c", "b", "e", "i", "g");
		List<String> list2 = Arrays.asList("d", "a", "c", "b", "e", "i", "g");
		List<String> list3 = Arrays.asList("d", "c", "a", "b", "e", "i", "g");
		List<String> list4 = Arrays.asList("d", "a", "c", "b", "e");
		List<String> list5 = Arrays.asList("d", "a", "c", "b", "e", "i", "g", "x");

		assertThat(CollectionUtils.elementsEqual(list1, list1)).isTrue();
		assertThat(CollectionUtils.elementsEqual(list1, list2)).isTrue();
		assertThat(CollectionUtils.elementsEqual(list1, list3)).isFalse();
		assertThat(CollectionUtils.elementsEqual(list1, list4)).isFalse();
		assertThat(CollectionUtils.elementsEqual(list1, list5)).isFalse();
	}

	@Test
	void minAndMax() {
		List<Integer> list = ListUtil.newArrayList(4, 1, 9, 100, 20, 101, 40);

		assertThat(CollectionUtils.min(list)).isEqualTo(1);
		assertThat(CollectionUtils.min(list, Ordering.natural())).isEqualTo(1);
		assertThat(CollectionUtils.max(list)).isEqualTo(101);
		assertThat(CollectionUtils.max(list, Ordering.natural())).isEqualTo(101);

		assertThat(CollectionUtils.minAndMax(list).getLeft()).isEqualTo(1);
		assertThat(CollectionUtils.minAndMax(list).getRight()).isEqualTo(101);

		assertThat(CollectionUtils.minAndMax(list, Ordering.natural()).getLeft())
			.isEqualTo(1);
		assertThat(CollectionUtils.minAndMax(list, Ordering.natural()).getRight())
			.isEqualTo(101);
	}

	@Test
	void test() {
		List<String> list1 = ListUtil.newArrayList();

		List<String> list2 = ListUtil.newArrayList("a", "b", "c");
		List<String> list3 = ListUtil.newArrayList("a");

		Set<String> set1 = SetUtil.newSortedSet();
		set1.add("a");
		set1.add("b");
		set1.add("c");

		Set<String> set2 = SetUtil.newSortedSet();
		set2.add("a");

		assertThat(CollectionUtils.isEmpty(list1)).isTrue();
		assertThat(CollectionUtils.isEmpty(null)).isTrue();
		assertThat(CollectionUtils.isEmpty(list2)).isFalse();

		assertThat(CollectionUtils.isNotEmpty(list1)).isFalse();
		assertThat(CollectionUtils.isNotEmpty(null)).isFalse();
		assertThat(CollectionUtils.isNotEmpty(list2)).isTrue();

		assertThat(CollectionUtils.getFirst(list2)).isEqualTo("a");
		assertThat(CollectionUtils.getLast(list2)).isEqualTo("c");

		assertThat(CollectionUtils.getFirst(set1)).isEqualTo("a");
		assertThat(CollectionUtils.getLast(set1)).isEqualTo("c");

		assertThat(CollectionUtils.getFirst(list3)).isEqualTo("a");
		assertThat(CollectionUtils.getLast(list3)).isEqualTo("a");

		assertThat(CollectionUtils.getFirst(set2)).isEqualTo("a");
		assertThat(CollectionUtils.getLast(set2)).isEqualTo("a");

		// assertThat(CollectionUtil.getFirst(list1)).isNull();
		// assertThat(CollectionUtil.getFirst(null)).isNull();
		// assertThat(CollectionUtil.getLast(list1)).isNull();
		// assertThat(CollectionUtil.getLast(null)).isNull();
	}

	@Test
	void topNAndBottomN() {
		List<Integer> list = Arrays.asList(3, 5, 7, 4, 2, 6, 9);

		assertThat(CollectionUtils.top(list, 3)).containsExactly(9, 7, 6);
		assertThat(CollectionUtils.top(list, 3, Ordering.natural().reverse()))
			.containsExactly(2, 3, 4);
		assertThat(CollectionUtils.leastOf(list, 3)).containsExactly(2, 3, 4);
		assertThat(CollectionUtils.leastOf(list, 3, Ordering.natural().reverse()))
			.containsExactly(9, 7, 6);
	}

}
