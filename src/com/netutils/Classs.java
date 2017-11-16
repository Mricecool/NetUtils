package com.netutils;

import java.util.List;

public class Classs {
	private List<Student> stus;

	public List<Student> getStus() {
		return stus;
	}

	public void setStus(List<Student> stus) {
		this.stus = stus;
	}

}

class Student {
	
	public Student() {
	}
	
	public Student(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}