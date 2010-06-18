package org.iitk.brihaspati.modules.timetable;

import java.util.ArrayList;

public class TimeSlot implements Constants {
	ArrayList<Integer> freeRooms;
	ArrayList<Course> coursesList;
//	ArrayList<Batch>  busyBatches;
	ArrayList<Faculty> busyProfessors;
	ArrayList<Integer> eventsWithoutRooms;
	ArrayList<Integer> eventsWithRooms;
 	int day;
	int slotNumber;
	
	public TimeSlot(int day,int slotNumber) {
		freeRooms = new ArrayList<Integer>();
		coursesList = new ArrayList<Course>();
		eventsWithoutRooms = new ArrayList<Integer>();
		eventsWithRooms = new ArrayList<Integer>();
		this.day = day;
		this.slotNumber = slotNumber;
	}
	
	public ArrayList<Integer> getEventsWithoutRooms() {
		return eventsWithoutRooms;
	}
	
	public void setEventsWithoutRooms(ArrayList<Integer> events) {
		this.eventsWithoutRooms = events;
	}
	public ArrayList<Integer> getFreeRooms() {
		return freeRooms;
	}
	public ArrayList<Integer> getEventsList() {
		ArrayList<Integer> events = eventsWithoutRooms;
		events.addAll(eventsWithRooms);
		return events;
	}
	
	public void setEventsWithRooms(ArrayList<Integer> events) {
		this.eventsWithRooms = events;
	}
	
	public ArrayList<Integer> getEventsWithRooms() {
		return this.eventsWithRooms;
	}
	
	public void assign(int event,boolean hasRoom) {
		if(hasRoom) {
			if(null == eventsWithRooms)
				eventsWithRooms = new ArrayList<Integer>();
			eventsWithRooms.add(event);
		}
		else {
			if(null == eventsWithoutRooms)
				eventsWithoutRooms = new ArrayList<Integer>();
			eventsWithoutRooms.add(event);
		}
		
	}
	//TODO : Create generic binary search
	/*public Room roomSearch(int strength) {
		if(null == freeRooms)
			return null;
		int numOfRooms = freeRooms.size();
		int first = 0;
		int last = numOfRooms;
		while(first != last) {
			int middle = (first + last)/2;
			int middleCapacity = freeRooms.get(middle).getCapacity();
			if(middleCapacity == strength)
				return freeRooms.get(middle);
			else if(middleCapacity < strength) 
				first = middle;
			else
				last = middle;
		}
		return freeRooms.remove(first);
	}*/


}
