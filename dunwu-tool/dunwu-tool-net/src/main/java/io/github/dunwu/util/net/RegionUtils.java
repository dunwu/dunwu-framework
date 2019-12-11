package io.github.dunwu.util.net;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
import io.github.dunwu.util.collection.CollectionUtil;
import io.github.dunwu.util.net.bean.City;
import io.github.dunwu.util.net.bean.County;
import io.github.dunwu.util.net.bean.Province;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 中国省市区查询工具类
 * <p>
 * 省市区数据来源于 <a href= "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 * @since 2018-12-11
 */
public class RegionUtils {

    private static final String JSON_DATA_FILE = "db/cn-area-info.json";

    private static List<Province> provinces = new LinkedList<>();

    private static List<City> cities = new LinkedList<>();

    private static List<County> counties = new LinkedList<>();

    static {
        loadRegionDbData();
    }

    private RegionUtils() {
    }

    /**
     * 获取所有（地级行政单位）集合
     *
     * @return Set<Province>
     */
    public static List<City> getAllCities() {
        return cities;
    }

    /**
     * 获取所有（县级行政单位）集合
     *
     * @return Set<Province>
     */
    public static List<County> getAllCounties() {
        return counties;
    }

    /**
     * 获取所有（省级行政单位）集合
     *
     * @return Set<Province>
     */
    public static List<Province> getAllProvinces() {
        return provinces;
    }

    /**
     * 根据编码查询（地级行政单位）
     *
     * @param code 编码
     * @return City
     */
    public static City getCityByCode(String code) {
        if (CollectionUtil.isEmpty(cities)) {
            return null;
        }

        return cities.stream().filter(item -> item.getCode().equals(code)).findAny()
            .orElse(null);
    }

    /**
     * 根据编码查询（县级行政单位）
     *
     * @param code 编码
     * @return County
     */
    public static County getCountyByCode(String code) {
        if (CollectionUtil.isEmpty(counties)) {
            return null;
        }

        return counties.stream().filter(item -> item.getCode().equals(code)).findAny()
            .orElse(null);
    }

    /**
     * 根据名称查询（县级行政单位）
     * <p>
     * 注：全国范围内的区/县级行政单位可能存在重名情况
     *
     * @param name 名称
     * @return Set<County>
     */
    public static Set<County> getCountyByName(String name) {
        if (CollectionUtil.isEmpty(counties)) {
            return null;
        }

        return counties.stream().filter(item -> item.getName().contains(name))
            .collect(Collectors.toSet());
    }

    public static County getCountyByName(String cityName, String countyName) {
        City city = getCityByName(cityName);
        return getCountyByName(city, countyName);
    }

    /**
     * 根据名称查询（地级行政单位）
     *
     * @param name 名称
     * @return City
     */
    public static City getCityByName(String name) {
        if (CollectionUtil.isEmpty(cities)) {
            return null;
        }

        return cities.stream().filter(item -> item.getName().contains(name)).findAny()
            .orElse(null);
    }

    public static County getCountyByName(City city, String countyName) {
        if (city == null) {
            return null;
        }

        Set<County> counties = city.getCounties();
        if (CollectionUtil.isEmpty(counties)) {
            return null;
        }

        for (County item : counties) {
            if (item.getName().equals(countyName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据编码查询（省级行政单位）
     *
     * @param code 编码
     * @return Province
     */
    public static Province getProvinceByCode(String code) {
        if (CollectionUtil.isEmpty(provinces)) {
            return null;
        }

        return provinces.stream().filter(item -> item.getName().equals(code)).findAny()
            .orElse(null);
    }

    /**
     * 根据名称查询（省级行政单位）
     *
     * @param name 名称
     * @return Province
     */
    public static Province getProvinceByName(String name) {
        if (CollectionUtil.isEmpty(provinces)) {
            return null;
        }

        return provinces.stream().filter(item -> item.getName().contains(name)).findAny()
            .orElse(null);
    }

    private static void loadRegionDbData() {
        String json = null;
        try {
            URL url = RegionUtils.class.getClassLoader().getResource(JSON_DATA_FILE);
            json = Files.asCharSource(new File(url.getPath()), StandardCharsets.UTF_8).read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseRegionsFromJson(json);
    }

    private static void parseRegionsFromJson(final String json) {
        provinces.clear();
        cities.clear();
        counties.clear();

        provinces = JSON.parseArray(json, Province.class);
        if (CollectionUtil.isEmpty(provinces)) {
            return;
        }
        for (Province province : provinces) {
            for (City city : province.getCities()) {
                city.setProvince(province);
                for (County county : city.getCounties()) {
                    county.setProvince(province);
                    county.setCity(city);
                    counties.add(county);
                }
                cities.add(city);
            }
        }
    }

}
