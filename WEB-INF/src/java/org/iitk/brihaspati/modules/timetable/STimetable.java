package org.iitk.brihaspati.modules.timetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

public class STimetable implements Constants {
	
	/*******    Common data for each timetable object ***********************/
	
	int numRooms;										// Total number of rooms
	int  numSlots;										// Total number of slots
	ArrayList<Event> allEvents;							// indices of all events
	boolean eventsMatrix[][];							// Event matrix representing conflicts
	ArrayList<Integer> roomsForEvent[];					// Rooms fitted for each event
	ArrayList<Course> allCourses;
	ArrayList<ArrayList<Integer>> courseToEvents;		// Mapping from course to events 
	int eventToCourse[];								// Mapping from event to course
	
	/*************************     Details of event		***************************/
	int roomOfEvent[];
	int slotOfEvent[];
	int penaltyOfEvent[]; 								// penalty associated with each event
	
	
	/************************      Details of slot		***************************/
	
	int slotsOrder[];									// Represents ordering of slots.
	ArrayList<Integer> eventsInSlot[];					// List of events in each slot
	ArrayList<Integer> roomsInSlot[];					// Busy Rooms in each slot
	int penaltyOfSlot[]; 								// penalty associated with each slot
	
	/***********************       Others				**************************/

	int totalPenalty; 									// total penalty of this timetable
	ArrayList<Integer> unassignedEvents;				// Unassigned events
	STimetable parentTimetable;
	boolean haveBeenGenerated = false;					// GenerateFirstTimetable can be called only once
	
	public STimetable() throws TimetableException {
		initialize();
	}
	
	public STimetable(STimetable t1) throws TimetableException {
		copy(t1);
	}
	
	protected void initialize() throws TimetableException {
		fetchData();
		
		roomOfEvent = new int[allEvents.size()];
		slotOfEvent = new int[allEvents.size()];
		penaltyOfEvent = new int[allEvents.size()];
		
		slotsOrder = new int[numSlots];
		eventsInSlot = new ArrayList[numSlots];
		roomsInSlot = new ArrayList[numSlots];
		penaltyOfSlot = new int[numSlots];
		
		for (int i = 0; i < allEvents.size(); i++) {
			roomOfEvent[i] = NIL;
			slotOfEvent[i] = NIL;
			penaltyOfEvent[i] = 0;
		}

		for (int i = 0; i < numSlots; i++) {
			eventsInSlot[i] = new ArrayList<Integer>();
			roomsInSlot[i] = new ArrayList<Integer>();
			slotsOrder[i] = i;
			penaltyOfSlot[i] = 0;
		}

		totalPenalty = 0;
		parentTimetable = null;
		unassignedEvents = new ArrayList<Integer>();
	}
	
	protected void copy(STimetable t) throws TimetableException {
		
		fetchData();

		roomOfEvent = new int[allEvents.size()];
		slotOfEvent = new int[allEvents.size()];
		penaltyOfEvent = new int[allEvents.size()];

		penaltyOfSlot = new int[numSlots];
		slotsOrder = new int[numSlots];
		eventsInSlot = new ArrayList[numSlots];
		roomsInSlot = new ArrayList[numSlots];

		for (int i = 0; i < allEvents.size(); i++) {
			roomOfEvent[i] = t.roomOfEvent[i];
			slotOfEvent[i] = t.slotOfEvent[i];
			penaltyOfEvent[i] = t.penaltyOfEvent[i];
		}

		for (int i = 0; i < numSlots; i++) {
			eventsInSlot[i] = new ArrayList<Integer>(t.eventsInSlot[i]);
			roomsInSlot[i] = new ArrayList<Integer>(t.roomsInSlot[i]);
			slotsOrder[i] = t.slotsOrder[i];
			penaltyOfSlot[i] = t.penaltyOfSlot[i];
		}

		totalPenalty = t.totalPenalty;
		this.parentTimetable = t.parentTimetable;
		unassignedEvents = new ArrayList<Integer>(t.unassignedEvents);
		haveBeenGenerated = t.haveBeenGenerated;
	}
	
	private void fetchData() throws TimetableException {
		Data data = Data.getInstance();
		numRooms = data.getRoomList().size();
		allEvents = data.getEventList();
		numSlots = data.getTimeSlots().size();
		eventsMatrix = data.getEventsMatrix();
		roomsForEvent = data.getRoomsForEventList();
		allCourses = data.getAllCourses();
		courseToEvents = data.getCourseToEventMapping();
		eventToCourse = data.getEventToCourseMapping();
		
		if(null == allEvents) 
			throw new TimetableException("List of events is Null");
		
		if(0 == numRooms)
			throw new TimetableException("No rooms are available");
		
		if(0 == numSlots) 
			throw new TimetableException("No slots are available");
	}
	
	public void setParentTimeTable(STimetable parent) {
		this.parentTimetable = parent;
	}
	
	public STimetable mergeWithParent() throws TimetableException {
		if(null != parentTimetable)
			merge(parentTimetable);
		return this;
	}
	
	public STimetable merge(STimetable st) throws TimetableException {
		/* Following data structures will be updated
		 * Events - slotOfEvent, roomOfEvent, penaltyOfEvent
		 * Slot - penaltyOfSlot, eventsInSlot,roomsInSlot
		 * Others - totalPenalty, parentTimetable,unassignedEvents
		 */
		if(null != st.parentTimetable)
			throw new TimetableException("parent Timetable of merging timetable is not null");
		
		for(int slotIndex = 0;slotIndex < numSlots;slotIndex++) {
			int slotNum = slotsOrder[slotIndex];
			ArrayList<Integer> parentEventList = st.getEventsInSlot(slotIndex);
			ArrayList<Integer> parentRoomsList = st.getRoomsInSlot(slotIndex);
			
			eventsInSlot[slotNum].addAll(parentEventList);
			roomsInSlot[slotNum].addAll(parentRoomsList);
			
			for(Integer tmpE:parentEventList) {
				slotOfEvent[tmpE] = slotNum;
				roomOfEvent[tmpE] = st.roomOfEvent[tmpE];
				penaltyOfEvent[tmpE] = st.penaltyOfEvent[tmpE];
			}
		}
		
		unassignedEvents.addAll(st.unassignedEvents);
		totalPenalty += st.totalPenalty;
		parentTimetable = st.parentTimetable;	//parentTimetable of st must be null
		
		return this;
	}
	
	public boolean isBetterThan(STimetable t) throws TimetableException {
		if (null == t)
			throw new TimetableException("Cannot compare null argument");
		if (t.unassignedEvents.size() < this.unassignedEvents.size())
			return false;
		else if (t.unassignedEvents.size() > this.unassignedEvents.size())
			return true;
		else {
			if (t.totalPenalty < this.totalPenalty)
				return false;
			else
				return true;
		}
	}
	
	protected ArrayList<Integer> getRoomsInSlot(int slotIndex) {
		ArrayList<Integer> roomsList = new ArrayList<Integer>(
				roomsInSlot[slotsOrder[slotIndex]]);
		if (null != parentTimetable)
			roomsList.addAll(parentTimetable.getRoomsInSlot(slotIndex));
		return roomsList;
	}
	
	protected ArrayList<Integer> getEventsInSlot(int slotIndex) {
		ArrayList<Integer> eventList = new ArrayList<Integer>(eventsInSlot[slotsOrder[slotIndex]]);
		if (null != parentTimetable)
			eventList.addAll(parentTimetable.getEventsInSlot(slotIndex));
		return eventList;
	}
	
	protected int numEventsInSlot(int timeSlotindex) {
		if (timeSlotindex < 0 || timeSlotindex > numSlots)
			return 0;
		int slotNum = slotsOrder[timeSlotindex];
		return eventsInSlot[slotNum].size()
				+ ((null != parentTimetable) ? parentTimetable
						.numEventsInSlot(timeSlotindex) : 0);
	}

	/**
	 * Find if a given time-slot timeslotIndex in current timetable or parent
	 * timetable has an event scheduled that conflicts with given event
	 * eventIndex
	 * 
	 * @param timeslotIndex
	 * @param eventIndex
	 * @return
	 */
	protected boolean conflictsOnSlot(int timeslotIndex, int eventIndex) {
		int timeSlotNum = slotsOrder[timeslotIndex];

		// Check for conflicts with current timetable
		ArrayList<Integer> currentEvents = eventsInSlot[timeSlotNum];
		if (null != currentEvents)
			for (Integer tmpEvent : currentEvents) {
				if (eventsConflict(tmpEvent, eventIndex)) 
					return true;
			}

		// check for conflicts with parent timetable
		if (null != parentTimetable) {
			if (parentTimetable.conflictsOnSlot(timeslotIndex, eventIndex))
				return true;
		}
		return false;
	}

	/**
	 * check if an event of same course and type is already scheduled on same
	 * day
	 * 
	 * @param slotIndex
	 * @param eventIndex
	 * @return
	 * @throws TimetableException
	 */
	protected boolean conflictsOnDay(int slotIndex, int eventIndex)
			throws TimetableException {
		if (slotIndex < 0 || eventIndex < 0)
			throw new TimetableException(
					"Invalid values of slotIndex and eventIndex" + slotIndex
							+ ":" + eventIndex);
		Event event = allEvents.get(eventIndex);
		Course course = allCourses.get(eventToCourse[eventIndex]);
		ArrayList<Integer> eventsOfSameCourse = courseToEvents
				.get(eventToCourse[eventIndex]);
		int events_of_same_nature = 0;
		for (Integer tmp : eventsOfSameCourse) {
			Event tmpEvent = allEvents.get(tmp);
			if (slotOfEvent[tmp] < 0 || event.getType() != tmpEvent.getType())
				continue; // Not scheduled yet or of different type
			if (false == haveSameDay(findIndexOfSlot(slotOfEvent[tmp]),
					slotIndex))
				continue; // Not scheduled on same day
			events_of_same_nature++;
		}
		if (event.getType() == LECTURE) {
			if (course.getMaxLecturesPerDay() > events_of_same_nature)
				return false;
		} else if (event.getType() == TUTORIAL) {
			if (course.getMaxTutorialsPerDay() > events_of_same_nature)
				return false;
		}
		return true;
	}
	
	protected boolean eventsConflict(int event1, int event2) {
		if (event1 < 0 || event2 < 0)
			return false; /* Exception */
		return eventsMatrix[event1][event2];
	}

	protected void removeEventFromUnassigned(Integer currE) {
		unassignedEvents.remove(currE);
	}
	
	protected void setEventsAndRoomsInSlot(int slotIndex,
			ArrayList<Integer> eventsWithR, ArrayList<Integer> busyRooms)
			throws TimetableException {
		if (slotIndex < 0 || null == eventsWithR || null == busyRooms)
			throw new TimetableException(
					"Eroor during assigning events and rooms to slot");
		if (eventsWithR.size() != busyRooms.size())
			throw new TimetableException(
					"Number of occupied events and rooms are not equal");
		int slotNumber = slotsOrder[slotIndex];
		eventsInSlot[slotNumber] = eventsWithR;
		roomsInSlot[slotNumber] = busyRooms;

	}
	
	protected void addEventToUnassigned(int curr) throws TimetableException {
		if(null == unassignedEvents) throw new TimetableException("Null list in unassigned");
		unassignedEvents.add(curr);
		slotOfEvent[curr] = NIL;
		roomOfEvent[curr] = NIL;
		
	}

	protected void addEventsToUnassigned(ArrayList<Integer> events) throws TimetableException {
		if(null == events || null == unassignedEvents)
			throw new TimetableException("Exception during adding unassigned events");
		for(Integer event:events){
			slotOfEvent[event] = NIL;
			roomOfEvent[event] = NIL;
		}
			
		unassignedEvents.addAll(events);
		
	}
	
	protected int findIndexOfSlot(int slot) {
		if (slot < 0)
			return slot;
		for (int i = 0; i < numSlots; i++)
			if (slotsOrder[i] == slot)
				return i;
		return NIL;
	}
	
	protected void addEventAndRoomToSlot(int slotIndex, int currE, int room)
	throws TimetableException {
		if (slotIndex < 0 || currE < 0 || room < 0)
			throw new TimetableException(
			"Error in adding room and event to slot");
		addEventToSlot(slotIndex, currE);
		roomsInSlot[slotsOrder[slotIndex]].add(room);
		roomOfEvent[currE] = room;
	}

	protected void addEventToSlot(int index, int curr) {
		eventsInSlot[slotsOrder[index]].add(curr);
		slotOfEvent[curr] = slotsOrder[index];
	}

	protected int removeEventFromSlot(int slotIndex, int eventIndex) {
		int room = roomOfEvent[eventIndex];
		int slot = slotsOrder[slotIndex];
		roomsInSlot[slot].remove((Integer) room);
		roomOfEvent[eventIndex] = NIL;
		eventsInSlot[slot].remove((Integer) eventIndex);
		slotOfEvent[eventIndex] = NIL;
		return room;
	}
	
	protected boolean haveSameDay(int slotIndex1, Integer slotIndex2) {
		return (slotIndex1 / Constants.SLOTS_IN_DAY == slotIndex2 / Constants.SLOTS_IN_DAY);
	}
	
	public void insertIntoDB(Connection conn, ArrayList<Integer> events) throws TimetableException {
		
		for(Integer curr:events) {
			int slotIndex = findIndexOfSlot(slotOfEvent[curr]);
			int roomIndex = roomOfEvent[curr];
			int day  = (slotIndex/SLOTS_IN_DAY) + 1;
			int slot = (slotIndex%SLOTS_IN_DAY) + 1;
			if (roomIndex < 0)
				throw new TimetableException("Room index is negative "
						+ allEvents.get(curr).getType());
			String sql = "Delete from timetable;";
	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
	} catch (SQLException e) {
		e.printStackTrace();
		throw new TimetableException("Error while deleting data from DB timetable table");
	}
			Event event = allEvents.get(curr);
			Room room = Data.getInstance().getRoomList().get(roomIndex);
			sql = "INSERT INTO timetable(course_code,slot,day,class_type," +
			"room_code) "
			+ " Values(?,?,?,?,?)";
			try {
				PreparedStatement cs = conn.prepareStatement(sql);
				cs.setString(1, event.getCourse());
				cs.setInt(2, slot);
				cs.setInt(3, day);
				cs.setString(4, event.getStringType());
				cs.setString(5, room.getCode());
				cs.execute();
				cs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void loadFromDB(Connection conn, ArrayList<Integer> events) 
															throws TimetableException {
		if(true == haveBeenGenerated)
			throw new TimetableException("Timetable has already been generated");
		
		Hashtable<String, Hashtable<String, ArrayList<Integer>>> courseToEvents = 
								new Hashtable<String, Hashtable<String,ArrayList<Integer>>>();
		
		for(Integer tmp:events) {
			Event eventObj = Data.getInstance().getEventObject(tmp);
			String courseCode = eventObj.getCourse();
			String classType  = eventObj.getStringType();
			
			if(null == courseToEvents.get(courseCode))
				courseToEvents.put(courseCode, new Hashtable<String, ArrayList<Integer>>());
			
			Hashtable<String, ArrayList<Integer>> eventsWithCourse = 
											courseToEvents.get(courseCode);
			
			if(null == eventsWithCourse.get(classType))
				eventsWithCourse.put(classType,new ArrayList<Integer>());
			
			eventsWithCourse.get(classType).add(tmp);
		}

		Hashtable<String, Integer> roomIndices = new Hashtable<String, Integer>();
		for(int i = 0; i < numRooms; i++) {
			roomIndices.put(Data.getInstance().getRoomCode(i),i);
		}
		
		String sql = "SELECT course_code,slot,day,class_type,room_code " +
				"from timetable";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				String course_code = rs.getString(1);
				int slot = rs.getInt(2);
				int day = rs.getInt(3);
				String classType = rs.getString(4);
				String room_code = rs.getString(5);
				
				ArrayList<Integer> currCourseEvents = courseToEvents.get(course_code).get(classType);
				if(currCourseEvents.size() == 0)
						throw new TimetableException("There must be at least one event");
				int eventIndex = currCourseEvents.get(0);
				currCourseEvents.remove(0);
				
				int slotNum = (day - 1) * Constants.SLOTS_IN_DAY + (slot - 1);
				int roomIndex = roomIndices.get(room_code);
				
				roomsInSlot[slotNum].add(roomIndex);
				eventsInSlot[slotNum].add(eventIndex);
				
				slotOfEvent[eventIndex] = slotNum;
				roomOfEvent[eventIndex] = roomIndex;
				
			}
			
			haveBeenGenerated = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TimetableException("Error while fetching data from DB");
		}
		
		
	}
	
	
	
	public void displayPenalty() {
		System.out.println("Total Penalty :" + totalPenalty + "\t");
		System.out.println("Total Number of unassigned events " + unassignedEvents.size());
		System.out.println("Unassigned Events: ");
		for (Integer tmp : unassignedEvents) {
			Event event = allEvents.get(tmp);
			System.out.print("{ " + CLASS_CODES[event.getType()] 
									+ event.getCourse() + " "  
									+ event.getProfessor().getName()
					+ " }, ");
			System.out.println();
		}
	}

	public void saveBestTimetable(){
		//Criteria c =new Criteria();
	}

	public int getRoomOfEvent(Integer event) {
		return roomOfEvent[event];
	}
	}
