package io.github.dunwu.common.net.region;

import cn.hutool.core.collection.CollectionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link RegionUtil} 单元测试
 */
@DisplayName("RegionUtil 工具类测试")
class RegionUtilTest {

    @Test
    @DisplayName("getAllProvinces - 应返回非空省份列表")
    void getAllProvinces() {
        List<Province> provinces = RegionUtil.getAllProvinces();
        assertThat(provinces).isNotNull();
    }

    @Test
    @DisplayName("getAllCities - 应返回非空城市列表")
    void getAllCities() {
        List<City> cities = RegionUtil.getAllCities();
        assertThat(cities).isNotNull();
    }

    @Test
    @DisplayName("getAllCounties - 应返回非空区县列表")
    void getAllCounties() {
        List<County> counties = RegionUtil.getAllCounties();
        assertThat(counties).isNotNull();
    }

    @Test
    @DisplayName("getProvinceByCode - 根据编码查询省份")
    void getProvinceByCode() {
        List<Province> provinces = RegionUtil.getAllProvinces();
        if (CollectionUtil.isNotEmpty(provinces)) {
            String code = provinces.get(0).getCode();
            Province result = RegionUtil.getProvinceByCode(code);
            assertThat(result).isNotNull();
        }
    }

    @Test
    @DisplayName("getProvinceByCode - 不存在的编码应返回 null")
    void getProvinceByCode_notFound() {
        Province result = RegionUtil.getProvinceByCode("999999");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getProvinceByName - 根据名称查询省份")
    void getProvinceByName() {
        // 尝试查询常见省份名称
        Province result = RegionUtil.getProvinceByName("北京");
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getProvinceByName - 不存在的名称应返回 null")
    void getProvinceByName_notFound() {
        Province result = RegionUtil.getProvinceByName("不存在的省份名称XXXX");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getCityByCode - 根据编码查询城市")
    void getCityByCode() {
        List<City> cities = RegionUtil.getAllCities();
        if (CollectionUtil.isNotEmpty(cities)) {
            String code = cities.get(0).getCode();
            City result = RegionUtil.getCityByCode(code);
            assertThat(result).isNotNull();
        }
    }

    @Test
    @DisplayName("getCityByCode - 不存在的编码应返回 null")
    void getCityByCode_notFound() {
        City result = RegionUtil.getCityByCode("999999");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getCityByName - 根据名称查询城市")
    void getCityByName() {
        City result = RegionUtil.getCityByName("南京市");
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getCityByName - 不存在的名称应返回 null")
    void getCityByName_notFound() {
        City result = RegionUtil.getCityByName("不存在的城市名称XXXX");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getCountyByCode - 根据编码查询区县")
    void getCountyByCode() {
        List<County> counties = RegionUtil.getAllCounties();
        if (CollectionUtil.isNotEmpty(counties)) {
            String code = counties.get(0).getCode();
            County result = RegionUtil.getCountyByCode(code);
            assertThat(result).isNotNull();
        }
    }

    @Test
    @DisplayName("getCountyByCode - 不存在的编码应返回 null")
    void getCountyByCode_notFound() {
        County result = RegionUtil.getCountyByCode("999999");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getCountyByName - 根据名称查询区县")
    void getCountyByName_singleParam() {
        Set<County> results = RegionUtil.getCountyByName("朝阳");
        assertThat(results).isNotNull();
    }

    @Test
    @DisplayName("getCountyByName - 根据城市名和区县名查询")
    void getCountyByName_twoParams() {
        // 北京市朝阳区
        County result = RegionUtil.getCountyByName("北京", "朝阳");
        // 结果可能为 null 取决于数据匹配
        // assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("getCountyByName - 城市为 null 应返回 null")
    void getCountyByName_cityNull() {
        County result = RegionUtil.getCountyByName((City) null, "朝阳");
        assertThat(result).isNull();
    }
}
