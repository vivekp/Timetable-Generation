package org.iitk.brihaspati.modules.timetable;

import java.util.ArrayList;

public class Event {
	Faculty faculty;
	ArrayList<Batch> batchList;
	int id;
	Room room;
	Course course;
	int type;
	int slotsRequired;
	int fixedSchedule;
//	ArrayList<Student> studentList;
	String fixedRoom;
	
	public Event(Course course, Faculty faculty, ArrayList<Batch> batchList,
												int type, int duration,String fixedRoom, 
												int fixedSchedule) {
		this.course = course;
		this.faculty = faculty;
		this.batchList = batchList;
		this.type = type;
		this.slotsRequired = duration;
		this.fixedRoom = fixedRoom;
		this.fixedSchedule = fixedSchedule; 
	}
	
	public Event(Course course, Faculty faculty, ArrayList<Batch> batchList,int type) {
		this.course = course;
		this.faculty = faculty;
		this.batchList = batchList;
		this.type = type;
		this.slotsRequired = Constants.DEFAULT_SLOTS;
	}

	public Faculty getProfessor() {
		return faculty;
	}
	
	/*public Batch getBatch() {
		return batch;
	}*/
	
	public Faculty getFaculty(){
		return faculty;
	}
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public String getStringType() {
		return String.valueOf(Constants.CLASS_CODES[type]);
	}
	
	public String getCourse() {
		return course.getCourseCode();
	}
	
	public int getRequiredSlots() {
		return slotsRequired;
	}
	
	public Room getRoom(){
		return room;
	}
	public void setRoom (Room room) {
		this.room = room;
	}
	
	public boolean hasRoom() {
		return (null == room);
	}

	public ArrayList<Batch> getBatchList() {
		return batchList;
	}

	public String getFixedRoom() {
		return this.fixedRoom;
	}
	
	public boolean hasFixedSchedule() {
		return (fixedSchedule == 1); 
	}
	
}
