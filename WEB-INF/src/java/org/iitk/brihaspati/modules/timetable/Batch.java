package org.iitk.brihaspati.modules.timetable;

public class Batch {
	
	private String id;
	private int strength;
	private String name;
	
	Batch(String id, int strength, String name) {
		this.id = id;
		this.strength = strength;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public int getStrength() {
		return this.strength;
	}

	public String getName() {
		return this.name;
	}

}
