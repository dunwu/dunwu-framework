package io.github.dunwu.test.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = 3148862192859981226L;

	private int id;

	private String name;

	private Date birthday;

	private GenderEnum sex;

	public User() {}

	public User(int id, String name, Date birthday, GenderEnum sex) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, birthday, sex);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (!(o instanceof User)) { return false; }
		User user = (User) o;
		return id == user.id &&
			Objects.equals(name, user.name) &&
			Objects.equals(birthday, user.birthday) &&
			sex == user.sex;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", name='" + name + '\'' +
			", birthday=" + birthday +
			", sex=" + sex +
			'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public GenderEnum getSex() {
		return sex;
	}

	public void setSex(GenderEnum sex) {
		this.sex = sex;
	}

}
