package io.github.dunwu.common.net.region;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 中国省市区查询工具类
 * <p>
 * 省市区数据来源于 <a href=
 * "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 * @since 2018-12-11
 */
@Slf4j
public class RegionUtil {

    private static final String JSON_DATA_FILE = "db/cn-area-info.json";

    /** 使用线程安全的集合存储省市区数据 */
    private static List<Province> provinces = new CopyOnWriteArrayList<>();
    private static List<City> cities = new CopyOnWriteArrayList<>();
    private static List<County> counties = new CopyOnWriteArrayList<>();

    static {
        loadData();
    }

    private RegionUtil() { }

    /**
     * 获取所有（地级行政单位）集合
     *
     * @return 不可修改的城市列表
     */
    public static List<City> getAllCities() {
        return Collections.unmodifiableList(cities);
    }

    /**
     * 获取所有（县级行政单位）集合
     *
     * @return 不可修改的区县列表
     */
    public static List<County> getAllCounties() {
        return Collections.unmodifiableList(counties);
    }

    /**
     * 获取所有（省级行政单位）集合
     *
     * @return 不可修改的省份列表
     */
    public static List<Province> getAllProvinces() {
        return Collections.unmodifiableList(provinces);
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

        return cities.stream().filter(item -> item.getCode().equals(code)).findAny().orElse(null);
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

        return counties.stream().filter(item -> item.getCode().equals(code)).findAny().orElse(null);
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

        return counties.stream().filter(item -> item.getName().contains(name)).collect(Collectors.toSet());
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

        return cities.stream().filter(item -> item.getName().contains(name)).findAny().orElse(null);
    }

    public static County getCountyByName(City city, String countyName) {
        if (city == null) {
            return null;
        }

        Set<County> counties = city.getChildren();
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

        return provinces.stream().filter(item -> item.getCode().equals(code)).findAny().orElse(null);
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

        return provinces.stream().filter(item -> item.getName().contains(name)).findAny().orElse(null);
    }

    private static void loadData() {
        try {
            URL url = RegionUtil.class.getClassLoader().getResource(JSON_DATA_FILE);
            if (url == null) {
                log.error("省市区数据文件 {} 不存在", JSON_DATA_FILE);
                return;
            }
            String json = FileUtil.readString(url, StandardCharsets.UTF_8);
            parseRegionsFromJson(json);
            log.info("成功加载省市区数据：省份 {} 个，城市 {} 个，区县 {} 个", provinces.size(), cities.size(),
                counties.size());
        } catch (Exception e) {
            log.error("加载省市区数据失败", e);
        }
    }

    private static void parseRegionsFromJson(final String json) {
        List<Province> tempProvinces = JSONUtil.parseArray(json).toList(Province.class);
        if (CollectionUtil.isEmpty(tempProvinces)) {
            log.warn("省市区数据为空");
            return;
        }

        List<City> tempCities = new LinkedList<>();
        List<County> tempCounties = new LinkedList<>();

        for (Province province : tempProvinces) {
            Set<City> children = province.getChildren();
            if (children == null) {
                continue;
            }
            for (City city : children) {
                city.setProvince(province);
                Set<County> countyList = city.getChildren();
                if (countyList == null) {
                    continue;
                }
                for (County county : countyList) {
                    county.setProvince(province);
                    county.setCity(city);
                    tempCounties.add(county);
                }
                tempCities.add(city);
            }
        }

        provinces.clear();
        provinces.addAll(tempProvinces);
        cities.clear();
        cities.addAll(tempCities);
        counties.clear();
        counties.addAll(tempCounties);
    }

}
