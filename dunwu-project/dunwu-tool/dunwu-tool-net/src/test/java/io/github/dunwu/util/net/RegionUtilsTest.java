package io.github.dunwu.util.net;

import io.github.dunwu.tool.io.AnsiSystem;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.util.net.bean.City;
import io.github.dunwu.util.net.bean.County;
import io.github.dunwu.util.net.bean.Province;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-12
 */
class RegionUtilsTest {

    private static final String PROVINCE_STR =
        "北京市,天津市,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海市,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西壮族自治区,海南省,重庆市,四川省,贵州省,云南省,"
            + "西藏自治区,陕西省,甘肃省,青海省,宁夏回族自治区,新疆维吾尔自治区";

    private static final String CITY_STR = "南京市,杭州市,合肥市";

    private static final String COUNTY_STR = "花山区,雨山区,河东区";

    @Test
    void getAllProvinces() {
        List<Province> provinces = RegionUtils.getAllProvinces();
        assertThat(provinces.size()).isEqualTo(31);
        StringBuilder sb = new StringBuilder();
        provinces.forEach(item -> {
            sb.append(item.toString()).append(",");
        });
        AnsiSystem.YELLOW.println("所有省级行政单位：");
        AnsiSystem.BLUE.println(sb.toString());
    }

    @Test
    void getAllCities() {
        List<City> cities = RegionUtils.getAllCities();
        assertThat(cities.size()).isEqualTo(343);
        StringBuilder sb = new StringBuilder();
        cities.forEach(item -> {
            sb.append(item.toString()).append(",");
        });
        AnsiSystem.YELLOW.println("所有市级行政单位：");
        AnsiSystem.BLUE.println(sb.toString());
    }

    @Test
    void getAllCounties() {
        List<County> counties = RegionUtils.getAllCounties();
        assertThat(counties.size()).isEqualTo(3088);
        StringBuilder sb = new StringBuilder();
        counties.forEach(item -> {
            sb.append(item.toString()).append(",");
        });
        AnsiSystem.YELLOW.println("所有区级行政单位：");
        AnsiSystem.BLUE.println(sb.toString());
    }

    @Test
    void getCityByName() {
        String[] cityNames = StringUtil.split(CITY_STR, ",");
        Arrays.stream(cityNames).forEach(item -> {
            City city = RegionUtils.getCityByName(item);
            assertThat(city).isNotNull();
        });
    }

    @Test
    void getCountyByName() {
        String[] countyNames = StringUtil.split(COUNTY_STR, ",");
        Arrays.stream(countyNames).forEach(item -> {
            Set<County> counties = RegionUtils.getCountyByName(item);
            assertThat(counties).isNotNull();
        });
    }

    @Test
    void getProvinceByName() {
        String[] provinceNames = StringUtil.split(PROVINCE_STR, ",");
        Arrays.stream(provinceNames).forEach(item -> {
            Province province = RegionUtils.getProvinceByName(item);
            assertThat(province).isNotNull();
        });
    }

}
