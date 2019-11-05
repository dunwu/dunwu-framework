package io.github.dunwu.util.china.bean;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-11
 */
public class Province implements Comparable<Province> {

	String code;

	String name;

	Set<City> cities;

	public Province() {
		cities = new TreeSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	@Override
	public int compareTo(Province o) {
		return this.getCode().compareToIgnoreCase(o.getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Province province = (Province) obj;
		if (province.getCode().equals(this.getCode())) {
			return true;
		} else {
			return false;
		}
	}

}
