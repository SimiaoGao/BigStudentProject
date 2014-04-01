package edu.rutgers.ess.model;

import java.util.ArrayList;

public class Student {

	private int id;
	private int admitTerm;
	private int admitYear;
	private ArrayList<Course> courses;
	private double gpa;
	private int major;
	private int major2;
	private int minor;
	private int yt;

	public Student(int id, int admitTerm, int admitYear, ArrayList<Course> courses, double gpa, int major, int major2, int minor, int yt) {
		super();
		this.id = id;
		this.admitTerm = admitTerm;
		this.admitYear = admitYear;
		this.courses = courses;
		this.gpa = gpa;
		this.major = major;
		this.major2 = major2;
		this.minor = minor;
		this.yt = yt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdmitTerm() {
		return admitTerm;
	}

	public void setAdmitTerm(int admitTerm) {
		this.admitTerm = admitTerm;
	}

	public int getAdmitYear() {
		return admitYear;
	}

	public void setAdmitYear(int admitYear) {
		this.admitYear = admitYear;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMajor2() {
		return major2;
	}

	public void setMajor2(int major2) {
		this.major2 = major2;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getYt() {
		return yt;
	}

	public void setYt(int yt) {
		this.yt = yt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
