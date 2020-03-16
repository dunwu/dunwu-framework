package io.github.dunwu.core;

import com.alibaba.fastjson.JSON;
import io.github.dunwu.common.Pagination;
import io.github.dunwu.common.ResultUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/15
 */
public class ResultUtilsTest {

    @Test
    void test() {
        String[] array = new String[] { "1", "2", "3" };
        System.out.println(JSON.toJSONString(ResultUtils.successBaseResult()));
        System.out.println(JSON.toJSONString(ResultUtils.successDataResult("Data")));
        System.out.println(JSON.toJSONString(ResultUtils.successDataListResult(Arrays.asList(array))));
        Pagination<String> pagination = new Pagination<>(1, 5, 3, Arrays.asList(array));
        System.out.println(JSON.toJSONString(ResultUtils.successPageResult(pagination)));
    }

}
