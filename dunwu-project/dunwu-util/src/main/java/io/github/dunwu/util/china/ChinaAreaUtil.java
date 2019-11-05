package io.github.dunwu.util.china;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.dunwu.util.china.bean.City;
import io.github.dunwu.util.china.bean.County;
import io.github.dunwu.util.china.bean.Province;
import io.github.dunwu.util.io.ResourceUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 中国省市区查询工具类
 * <p>
 * 省市区数据来源于 <a href=
 * "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href=
 * "https://github.com/modood/Administrative-divisions-of-China">Administrative-divisions-of-China</a>
 * @since 2018-12-11
 */
public class ChinaAreaUtil {

	private static final String JSON_DATA_FILE = "cn-area-info.json";

	private static Set<Province> provinces = new TreeSet<>();

	private static Set<City> cities = new TreeSet<>();

	private static Set<County> counties = new TreeSet<>();

	static {
		try {
			String json = ResourceUtil.toString(ChinaAreaUtil.class, JSON_DATA_FILE);
			parseJsonData(json);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有（省级行政单位）集合
	 * @return Set<Province>
	 */
	public static Set<Province> getAllProvinces() {
		return provinces;
	}

	/**
	 * 获取所有（地级行政单位）集合
	 * @return Set<Province>
	 */
	public static Set<City> getAllCities() {
		return cities;
	}

	/**
	 * 获取所有（县级行政单位）集合
	 * @return Set<Province>
	 */
	public static Set<County> getAllCounties() {
		return counties;
	}

	/**
	 * 根据编码查询（省级行政单位）
	 * @param code 编码
	 * @return Province
	 */
	public static Province getProvinceByCode(String code) {
		if (CollectionUtils.isEmpty(provinces)) {
			return null;
		}

		return provinces.stream().filter(item -> item.getName().equals(code)).findAny()
				.orElse(null);
	}

	/**
	 * 根据名称查询（省级行政单位）
	 * @param name 名称
	 * @return Province
	 */
	public static Province getProvinceByName(String name) {
		if (CollectionUtils.isEmpty(provinces)) {
			return null;
		}

		return provinces.stream().filter(item -> item.getName().equals(name)).findAny()
				.orElse(null);
	}

	/**
	 * 根据编码查询（地级行政单位）
	 * @param code 编码
	 * @return City
	 */
	public static City getCityByCode(String code) {
		if (CollectionUtils.isEmpty(cities)) {
			return null;
		}

		return cities.stream().filter(item -> item.getCode().equals(code)).findAny()
				.orElse(null);
	}

	/**
	 * 根据名称查询（地级行政单位）
	 * @param name 名称
	 * @return City
	 */
	public static City getCityByName(String name) {
		if (CollectionUtils.isEmpty(cities)) {
			return null;
		}

		return cities.stream().filter(item -> item.getName().equals(name)).findAny()
				.orElse(null);
	}

	/**
	 * 根据编码查询（县级行政单位）
	 * @param code 编码
	 * @return County
	 */
	public static County getCountyByCode(String code) {
		if (CollectionUtils.isEmpty(counties)) {
			return null;
		}

		return counties.stream().filter(item -> item.getCode().equals(code)).findAny()
				.orElse(null);
	}

	/**
	 * 根据名称查询（县级行政单位）
	 * <p>
	 * 注：县级行政单位可能存在重名情况
	 * @param name 名称
	 * @return Set<County>
	 */
	public static Set<County> getCountyByName(String name) {
		if (CollectionUtils.isEmpty(counties)) {
			return null;
		}

		return counties.stream().filter(item -> item.getName().equals(name))
				.collect(Collectors.toSet());
	}

	private static void parseJsonData(String json) {
		JSONArray provinceArray = JSONArray.parseArray(json);
		for (int i = 0; i < provinceArray.size(); i++) {
			JSONObject jsonObject = provinceArray.getJSONObject(i);
			Province province = new Province();
			province.setCode(jsonObject.get("code").toString());
			province.setName(jsonObject.get("name").toString());
			JSONArray cityArray = JSONArray
					.parseArray(jsonObject.get("children").toString());
			for (int j = 0; j < cityArray.size(); j++) {
				JSONObject cityObject = cityArray.getJSONObject(j);
				City city = new City();
				city.setCode(cityObject.get("code").toString());
				city.setName(cityObject.get("name").toString());
				city.setProvince(province);
				JSONArray countyArray = (JSONArray) cityObject.get("children");
				for (int k = 0; k < countyArray.size(); k++) {
					JSONObject countyObject = countyArray.getJSONObject(k);
					County county = new County();
					county.setCode(countyObject.get("code").toString());
					county.setName(countyObject.get("name").toString());
					county.setCity(city);
					counties.add(county);
					city.getCounties().add(county);
				}
				cities.add(city);
				province.getCities().add(city);
			}
			provinces.add(province);
		}
	}

	private ChinaAreaUtil() {
	}

}
