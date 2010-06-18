package org.iitk.brihaspati.modules.timetable;


public class Faculty {
	int fac_Id;
	String name;
	String department;
	String institute;
	String id;
	
	public Faculty(int fac_Id,String name,String department,String institute,String id) {
		this.fac_Id = fac_Id;
		this.name = name;
		this.department = department;
		this.institute = institute;
		this.id = id;
	}
	public String getId(){
		return id;
	}
	public int getFac_Id() {
		return fac_Id;
	}
	public String getName() {
		return name;
	}
	public String getDepartment(){
		return department;
	}
	public String getInstitute(){
		return institute;
	}
}
