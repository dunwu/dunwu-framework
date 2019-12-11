package io.github.dunwu.util.collection;

import io.github.dunwu.util.RandomExtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class ArrayExtUtilTest {

    @Test
    void asList() {
        List<String> list = Arrays.asList("d", "a", "c", "b", "e", "i", "g");
        assertThat(list).hasSize(7).containsExactly("d", "a", "c", "b", "e", "i", "g");

        try {
            list.add("a");
            fail("should fail before");
        } catch (Throwable t) {
            assertThat(t).isInstanceOf(UnsupportedOperationException.class);
        }

        List<Integer> list3 = ArrayExtUtil.intAsList(1, 2, 3);
        assertThat(list3).hasSize(3).containsExactly(1, 2, 3);

        List<Long> list4 = ArrayExtUtil.longAsList(1L, 2L, 3L);
        assertThat(list4).hasSize(3).containsExactly(1L, 2L, 3L);

        List<Double> list5 = ArrayExtUtil.doubleAsList(1.1d, 2.2d, 3.3d);
        assertThat(list5).hasSize(3).containsExactly(1.1d, 2.2d, 3.3d);
    }

    @Test
    void contact() {
        String[] array = new String[] { "d", "a", "c" };
        assertThat(ArrayExtUtil.addTail(array, "z")).containsExactly("z", "d", "a", "c");
        assertThat(ArrayExtUtil.addHead(array, "z")).containsExactly("d", "a", "c", "z");
    }

    @Test
    void shuffle() {
        String[] arrays = new String[] { "d", "a", "c", "b", "e", "i", "g" };
        String[] arraysClone = Arrays.copyOf(arrays, arrays.length);
        Arrays.sort(arrays);
        assertThat(arrays).containsExactly("a", "b", "c", "d", "e", "g", "i");
        ArrayExtUtil.shuffle(arrays);
        Assertions.assertFalse(Arrays.equals(arrays, arraysClone),
            "should not be equal to origin array");
        // System.out.println(Arrays.toString(arrays));
        Arrays.sort(arrays);
        ArrayExtUtil.shuffle(arrays, RandomExtUtil.secureRandom());
        Assertions.assertFalse(Arrays.equals(arrays, arraysClone),
            "should not be equal to origin array");
    }

}
