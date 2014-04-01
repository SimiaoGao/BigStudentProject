package edu.rutgers.ess.utility;

public enum YearTermOfStudy {

	FreshmenFirst(1, "First Semester of Freshmen"),
	FreshmenSecond(2, "Second Semester of Freshmen"),
	FreshmenThird(3, "Third Semester of Freshmen"),
	SophomoreFirst(4, "First Semester of Sophomore"),
	SophomoreSecond(5, "Second Semester of Sophomore"),
	SophomoreThird(6, "Third Semester of Sophomore"),
	JuniorFirst(7, "First Semester of Junior"),
	JuniorSecond(8, "Second Semester of Junior"),
	JuniorThird(9, "Third Semester of Junior"),
	SeniorFirst(10, "First Semester of Senior"),
	SeniorSecond(11, "Second Semester of Senior"),
	SeniorThird(12, "Third Semester of Senior"),
	NULL(-1, "IllegalArgument");
	
	private final int weight;
	private final String asString;
	
	private YearTermOfStudy(int weight, String asString) {
	
		this.weight = weight;
		this.asString = asString;
	}
	
	public int getWeight() {
		
		return this.weight;
	}
	
	public static YearTermOfStudy transform(String ay, String at, String cy, String ct) {
		
		int admitYear = Integer.parseInt(ay);
		int admitTerm = Integer.parseInt(at);
		int courseTakenYear = Integer.parseInt(cy);
		int courseTakenTerm = Integer.parseInt(ct);
		
		admitTerm = admitTerm >= 7 ? 9 : 1;
		courseTakenTerm = courseTakenTerm == 0 ? 1 : courseTakenTerm;
		
		int admitYearTerm = admitYear * 10 + admitTerm;
		int courseTakenYearTerm = courseTakenYear * 10 + courseTakenTerm;
		switch(courseTakenYearTerm - admitYearTerm) {
		case 0: return FreshmenFirst;
		case 2: case 6: return FreshmenSecond;
		case 8: return FreshmenThird;
		case 10: return SophomoreFirst;
		case 12: case 16: return SophomoreSecond;
		case 18: return SophomoreThird;
		case 20: return JuniorFirst;
		case 22: case 26: return JuniorSecond;
		case 28: return JuniorThird;
		case 30: return SeniorFirst;
		case 32: case 36: return SeniorSecond;
		case 38: return SeniorThird;
		default: throw new IllegalArgumentException();
		}
	}
	
	
	@Override
	public String toString() {
		
		return this.asString;
	}
}
