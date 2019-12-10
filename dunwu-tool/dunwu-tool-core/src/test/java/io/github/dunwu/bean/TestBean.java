package io.github.dunwu.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定义一个满足大多数情况的 Bean 结构（含 JDK8 数据类型），使得各种 Json 库测试性能时能相对公平
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-22
 */
public class TestBean implements Serializable {

	private static final long serialVersionUID = -6473181683996762084L;

	private int i1;

	private Integer i2;

	private float f1;

	private Double d1;

	private Date date1;

	private LocalDateTime date2;

	private LocalDate date3;

	private GenderEnum gender;

	private String[] strArray;

	private List<Integer> intList;

	private Map<String, Object> map;

	public TestBean() {
	}

	public TestBean(int i1, Integer i2, float f1, Double d1, Date date1, LocalDateTime date2, LocalDate date3,
		GenderEnum gender, String[] strArray, List<Integer> intList, Map<String, Object> map) {
		this.i1 = i1;
		this.i2 = i2;
		this.f1 = f1;
		this.d1 = d1;
		this.date1 = date1;
		this.date2 = date2;
		this.date3 = date3;
		this.gender = gender;
		this.strArray = strArray;
		this.intList = intList;
		this.map = map;
	}

	@Override
	public String toString() {
		return "TestBean{" +
			"i1=" + i1 +
			", i2=" + i2 +
			", f1=" + f1 +
			", d1=" + d1 +
			", date1=" + date1 +
			", date2=" + date2 +
			", date3=" + date3 +
			", gender=" + gender +
			", strArray=" + Arrays.toString(strArray) +
			", intList=" + intList +
			", map=" + map +
			'}';
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public Integer getI2() {
		return i2;
	}

	public void setI2(Integer i2) {
		this.i2 = i2;
	}

	public float getF1() {
		return f1;
	}

	public void setF1(float f1) {
		this.f1 = f1;
	}

	public Double getD1() {
		return d1;
	}

	public void setD1(Double d1) {
		this.d1 = d1;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public LocalDateTime getDate2() {
		return date2;
	}

	public void setDate2(LocalDateTime date2) {
		this.date2 = date2;
	}

	public LocalDate getDate3() {
		return date3;
	}

	public void setDate3(LocalDate date3) {
		this.date3 = date3;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String[] getStrArray() {
		return strArray;
	}

	public void setStrArray(String[] strArray) {
		this.strArray = strArray;
	}

	public List<Integer> getIntList() {
		return intList;
	}

	public void setIntList(List<Integer> intList) {
		this.intList = intList;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
