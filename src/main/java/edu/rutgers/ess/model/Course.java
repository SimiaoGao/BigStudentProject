package edu.rutgers.ess.model;

public class Course {

	private int year;
	private int term;
	private int offeringUnitCd;
	private int subjCd;
	private int courseNo;
	private int courseSupplCd;
	private String sectionNo;
	
	public Course(int year, int term, int offeringUnitCd, int subjCd, int courseNo, int courseSupplCd, String sectionNo) {
		super();
		this.year = year;
		this.term = term;
		this.offeringUnitCd = offeringUnitCd;
		this.subjCd = subjCd;
		this.courseNo = courseNo;
		this.courseSupplCd = courseSupplCd;
		this.sectionNo = sectionNo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getOfferingUnitCd() {
		return offeringUnitCd;
	}

	public void setOfferingUnitCd(int offeringUnitCd) {
		this.offeringUnitCd = offeringUnitCd;
	}

	public int getSubjCd() {
		return subjCd;
	}

	public void setSubjCd(int subjCd) {
		this.subjCd = subjCd;
	}

	public int getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}

	public int getCourseSupplCd() {
		return courseSupplCd;
	}

	public void setCourseSupplCd(int courseSupplCd) {
		this.courseSupplCd = courseSupplCd;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseNo;
		result = prime * result + offeringUnitCd;
		result = prime * result + subjCd;
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
		Course other = (Course) obj;
		if (courseNo != other.courseNo)
			return false;
		if (offeringUnitCd != other.offeringUnitCd)
			return false;
		if (subjCd != other.subjCd)
			return false;
		return true;
	}
}
