package io.github.dunwu.util.parser;

import io.github.dunwu.util.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestBeanUtilsTest {

	@Test
	void copyArrayObject() {
		Student student1 = new Student("zhang3", 20, new Teacher("li4"),
			ListUtil.newArrayList("chinese", "english"));
		Student student2 = new Student("zhang4", 30, new Teacher("li5"),
			ListUtil.newArrayList("chinese2", "english4"));
		Student student3 = new Student("zhang5", 40, new Teacher("li6"),
			ListUtil.newArrayList("chinese3", "english5"));
		Student[] studentList = new Student[] { student1, student2, student3 };
		StudentVO[] studentVoList = BeanUtils.mapArray(studentList, StudentVO.class);
		assertThat(studentVoList).hasSize(3);
		StudentVO studentVo = studentVoList[0];

		assertThat(studentVo.name).isEqualTo("zhang3");
		assertThat(studentVo.getAge()).isEqualTo(20);
		assertThat(studentVo.getTeacher().getName()).isEqualTo("li4");
		assertThat(studentVo.getCourse()).containsExactly("chinese", "english");
	}

	@Test
	void copyListObject() {
		Student student1 = new Student("zhang3", 20, new Teacher("li4"),
			ListUtil.newArrayList("chinese", "english"));
		Student student2 = new Student("zhang4", 30, new Teacher("li5"),
			ListUtil.newArrayList("chinese2", "english4"));
		Student student3 = new Student("zhang5", 40, new Teacher("li6"),
			ListUtil.newArrayList("chinese3", "english5"));
		List<Student> studentList = ListUtil.newArrayList(student1, student2, student3);

		List<StudentVO> studentVoList = BeanUtils.mapList(studentList, StudentVO.class);
		assertThat(studentVoList).hasSize(3);
		StudentVO studentVo = studentVoList.get(0);

		assertThat(studentVo.name).isEqualTo("zhang3");
		assertThat(studentVo.getAge()).isEqualTo(20);
		assertThat(studentVo.getTeacher().getName()).isEqualTo("li4");
		assertThat(studentVo.getCourse()).containsExactly("chinese", "english");
	}

	@Test
	void copySingleObject() {
		Student student = new Student("zhang3", 20, new Teacher("li4"),
			ListUtil.newArrayList("chinese", "english"));

		StudentVO studentVo = BeanUtils.map(student, StudentVO.class);

		assertThat(studentVo.name).isEqualTo("zhang3");
		assertThat(studentVo.getAge()).isEqualTo(20);
		assertThat(studentVo.getTeacher().getName()).isEqualTo("li4");
		assertThat(studentVo.getCourse()).containsExactly("chinese", "english");
	}

	static class Student {

		public String name;

		private int age;

		private Teacher teacher;

		private List<String> course = ListUtil.newArrayList();

		public Student() {

		}

		public Student(String name, int age, Teacher teacher, List<String> course) {
			this.name = name;
			this.age = age;
			this.teacher = teacher;
			this.course = course;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public List<String> getCourse() {
			return course;
		}

		public void setCourse(List<String> course) {
			this.course = course;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Teacher getTeacher() {
			return teacher;
		}

		public void setTeacher(Teacher teacher) {
			this.teacher = teacher;
		}

	}

	static class Teacher {

		private String name;

		public Teacher() {}

		public Teacher(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	static class StudentVO {

		public String name;

		private int age;

		private TeacherVO teacher;

		private List<String> course = ListUtil.newArrayList();

		public StudentVO() {}

		public StudentVO(String name, int age, TeacherVO teacher, List<String> course) {
			this.name = name;
			this.age = age;
			this.teacher = teacher;
			this.course = course;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public List<String> getCourse() {
			return course;
		}

		public void setCourse(List<String> course) {
			this.course = course;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public TeacherVO getTeacher() {
			return teacher;
		}

		public void setTeacher(TeacherVO teacher) {
			this.teacher = teacher;
		}

	}

	static class TeacherVO {

		private String name;

		public TeacherVO() {}

		public TeacherVO(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
