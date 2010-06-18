package org.iitk.brihaspati.modules.timetable;

public class Room {
	int type;
	int capacity;
	int building;
	int numComputers;
	boolean hasProjector;
	String code;
	
	public Room(String code,int type,int capacity,int building,int numCompters, boolean hasProjector ) {
		this.code = new String(code);
		this.type = type;
		this.capacity = capacity;
		this.building = building;
		this.numComputers = numCompters;
		this.hasProjector = hasProjector;
	}
	
	public String getCode() {
		return code;
	}
	
	public int getBuilding() {
		return building;
	}
	
	public int getType() {
		return type;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getComputers() {
		return numComputers;
	}
	
	public boolean hasProjector() {
		return hasProjector;
	}
	
	public void setCode(String code) {
		this.code = new String(code);
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void displayTimeTable() {
		return;
	}
}
