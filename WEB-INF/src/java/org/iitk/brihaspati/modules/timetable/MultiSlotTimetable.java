package org.iitk.brihaspati.modules.timetable;

import java.util.ArrayList;

/**
 * 
 * @author Abhishek Agarwal
 * Timetable class for scheduling classes with more than one slots. 
 */
public class MultiSlotTimetable extends STimetable {
	
	ArrayList<Integer> eventsWithMultiSlots;
	int slotsRequired;
	
	public MultiSlotTimetable(int slotsRequired) throws TimetableException {
		this.slotsRequired = slotsRequired;
		initialize(slotsRequired);
	}
	
	public MultiSlotTimetable(MultiSlotTimetable t1) throws TimetableException {
		copy(t1);
	}
	
	private void initialize(int slotsRequired) throws TimetableException {
		super.initialize();
		fetchEventsWithMultiSlots(slotsRequired);
	}
	
	private void copy(MultiSlotTimetable t) throws TimetableException {
		super.copy(t);
		fetchEventsWithMultiSlots(t.slotsRequired);
	}
	
	private void fetchEventsWithMultiSlots(int slotsRequired) throws TimetableException {
		Data data = Data.getInstance();
		eventsWithMultiSlots = data.getEventsWithMultiSlots(slotsRequired);
		
		if(null == eventsWithMultiSlots) 
			throw new TimetableException("List of events is Null");
	}

	/* *********************** Generate Initial Feasible Solution  ****************/ 						
	/** Generate first feasible and non-optimized solution  
	 * * @throws TimetableException */
	public void generateFirstTimetable() throws TimetableException {
		
		if(true == haveBeenGenerated) 
			throw new TimetableException("Timetable has already been initialized and generated");


		//For each event, find a free time-slot with no conflicts and minimum events
		for(Integer currE:eventsWithMultiSlots) {
			if(false == searchAndPlaceEvent(currE,slotsRequired)) 
				addEventToUnassigned(currE);
		}

		// Mark the first generation of this timetable
		haveBeenGenerated = true;

	}
		
	/**
	 * first make a search for the afternoon sessions and then go for morning sessions. 
	 * while going for morning sessions,it should be ensured that no student or faculty 
	 * has two practical classes on same day.
	 * Instead of searching slots, we will search for sessions. For each day, we have two sessions. 
	 * These sessions may have different starting times. usually there is always a choice 
	 * between 2-5 and 3-6 and any one of them can be chosen but its quite possible, 
	 * that there may be a class already scheduled in 5-6 or 2-3 which restricts us to single option.
	 */
	
	private boolean searchAndPlaceEvent(int eventIndex,int reqSlots) throws TimetableException {
		int cnt = 0,add = 4;
		int minSlot = NIL, room = NIL;
		//Event event = allEvents.get(eventIndex);
		
		outer:
			for(int index = cnt + add;cnt < numSlots;cnt++,index = 2*cnt - cnt%4 + add) {
				if(cnt == numSlots/2 - 1) {
					add = -numSlots + 4 - add;
				 }
				if(index/4 != (index + reqSlots - 1)/4)		// No consecutive slots available on this day
					continue outer;
		
				/*if(true == conflictsOnDay(index,eventIndex))
					continue;*/
				//Check for any conflicting event in this slot
				if(true == conflictsOnSlot(index,eventIndex,reqSlots))
					continue outer;
				//TODO: Now faculty or student should have two practical classes on same day(not compulsory)
//				if(minSlot > 0 && havePracticalOnSameDay(index,eventIndex,reqSlots))
//				    continue outer;
				
				//Check if there is room available for it
				ArrayList<Integer> fitRooms = roomsForEvent[eventIndex];
				for(Integer tmpRoom:fitRooms) {
					if(isAvailable(tmpRoom,index,reqSlots)) {
						/*if(minSlot < 0 
								|| numEventsInSlots(minSlot,reqSlots) > numEventsInSlots(index,reqSlots)) {*/
							
							minSlot = index;
							room = tmpRoom;
							break outer;
//							continue outer;
//						}
					}
				}
			}
		if(minSlot < 0) return false;
		else {
			addEventAndRoomToSlots(minSlot,eventIndex,room,reqSlots);
			removeEventFromUnassigned(eventIndex);
			return true;
		}
	}

	private boolean isAvailable(int tmpRoom, int index, int reqSlots) {
		for(int id = index;id < index + reqSlots;id++) {
			int slotNum = slotsOrder[id];
			if(roomsInSlot[slotNum].contains(tmpRoom) 
					|| (null != parentTimetable 
							&& parentTimetable.getRoomsInSlot(id).contains(tmpRoom)))
				return false;
		}
		
		return true;
	}

	private int numEventsInSlots(int timeSlotIndex,int reqSlots) {
		int num = 0;
		for(int id = timeSlotIndex; id < timeSlotIndex + reqSlots; id++) {
			num += numEventsInSlot(id);
		}
		return num;
	}
	/**
	 * Find if a given time-slot timeslotIndex has an event scheduled that 
	 * conflicts with given event eventIndex
	 * @param timeslotIndex
	 * @param eventIndex
	 * @paran reqSlots
	 * @return
	 */
	private boolean conflictsOnSlot(int timeslotIndex, int eventIndex,int reqSlots) {
		for(int id = timeslotIndex;id < timeslotIndex + reqSlots;id++) {
			if(conflictsOnSlot(id, eventIndex))
				return true;
			}
		return false;
	}

	private void addEventAndRoomToSlots(int slotIndex,int currE, int room,int reqSlots) 
													throws TimetableException {
		if(slotIndex < 0 || currE < 0 || room < 0)
			throw new TimetableException("Error in adding room and event to slot");
		addEventToSlots(slotIndex, currE,reqSlots);
		for(int id = slotIndex;id < slotIndex + reqSlots;id++) 
			roomsInSlot[slotsOrder[id]].add(room);
		roomOfEvent[currE] = room;
	}

	private void addEventToSlots(int index, int curr,int reqSlots) {
		for(int id = index;id < index + reqSlots;id++) {
			eventsInSlot[slotsOrder[id]].add(curr);
		}
		slotOfEvent[curr] = slotsOrder[index];
	}
	
	public void display() {
		System.out.println(eventsWithMultiSlots.size());
		for(int i = 0; i < numSlots;i++) {
			System.out.print("SLOT " + i + ": ");
			ArrayList<Integer> eventList = eventsInSlot[slotsOrder[i]];
			for(Integer currE:eventList) {
				System.out.print("{" + currE + "," + roomOfEvent[currE] + "}");
			}
			System.out.println("\n");
		}
	}
	public static void main(String args[]) throws TimetableException {
		STimetable parent = null;
		MultiSlotTimetable mST = null;
		for(int i = Constants.MAX_SLOTS_FOR_EVENT; i >= 2;i--) {
			mST = new MultiSlotTimetable(i);
			mST.setParentTimeTable(parent);
			mST.generateFirstTimetable();
			parent = mST.mergeWithParent();
		}
		
//		mST.generateFirstTimetable();
		mST.display();
		mST.displayPenalty();
	}
		
}
