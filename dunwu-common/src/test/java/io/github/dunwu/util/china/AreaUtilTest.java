package io.github.dunwu.util.china;

import io.github.dunwu.util.china.bean.City;
import io.github.dunwu.util.china.bean.County;
import io.github.dunwu.util.china.bean.Province;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-12
 */
public class AreaUtilTest {
    private static final Logger log = LoggerFactory.getLogger(AreaUtilTest.class);
    private static final String PROVINCE_STR =
        "北京市,天津市,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海市,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西壮族自治区,海南省,重庆市,四川省,贵州省,云南省,"
            + "西藏自治区,陕西省,甘肃省,青海省,宁夏回族自治区,新疆维吾尔自治区";
    private static final String CITY_STR = "南京市,杭州市,合肥市";
    private static final String COUNTY_STR = "花山区,雨山区,河东区";

    @Test
    void getAllProvinces() {
        Set<Province> provinceSet = ChinaAreaUtil.getAllProvinces();
        Assertions.assertEquals(31, provinceSet.size());
        StringBuilder sb = new StringBuilder();
        provinceSet.forEach(item -> {
            sb.append(item.getName()).append(",");
        });
        log.info("所有省级行政单位：{}", sb.toString());
    }

    @Test
    void getProvinceByName() {
        String[] provinceNames = StringUtils.splitByWholeSeparator(PROVINCE_STR, ",");
        Arrays.stream(provinceNames).forEach(item -> {
            Province province = ChinaAreaUtil.getProvinceByName(item);
            Assertions.assertNotNull(province);
        });
    }

    @Test
    void getCityByName() {
        String[] cityNames = StringUtils.splitByWholeSeparator(CITY_STR, ",");
        Arrays.stream(cityNames).forEach(item -> {
            City city = ChinaAreaUtil.getCityByName(item);
            Assertions.assertNotNull(city);
        });
    }

    @Test
    void getCountyByName() {
        String[] countyNames = StringUtils.splitByWholeSeparator(COUNTY_STR, ",");
        Arrays.stream(countyNames).forEach(item -> {
            Set<County> counties = ChinaAreaUtil.getCountyByName(item);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(counties));
        });
    }
}
