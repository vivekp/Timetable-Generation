package org.iitk.brihaspati.modules.timetable;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Abhishek Agarwal 
 * Timetable class for scheduling events with single slot.
 */
public class SingleSlotTimetable extends STimetable  {

	ArrayList<Integer> eventsWithOneSlot;

	public SingleSlotTimetable() throws TimetableException {
		initialize();
	}

	public SingleSlotTimetable(SingleSlotTimetable t1) throws TimetableException {
		copy(t1);
	}

	@SuppressWarnings("unchecked")
	protected void initialize() throws TimetableException {
		super.initialize();
		fetchEventsWithOneSlot();
	}

	@SuppressWarnings("unchecked")
	private void copy(SingleSlotTimetable t) throws TimetableException {
		super.copy(t);
		fetchEventsWithOneSlot();
		haveBeenGenerated = t.haveBeenGenerated;
	}

	private void fetchEventsWithOneSlot() throws TimetableException {
		Data data = Data.getInstance();
		eventsWithOneSlot = data.getEventsWithOneSlot();
	
		if(null == eventsWithOneSlot)
			throw new TimetableException("List of events with single slot is null");
	}

	/** ********************** Generate Initial Feasible Solution *************** */
	/**
	 * Generate first feasible and non-optimized solution *
	 * @throws TimetableException
	 */
	public void generateFirstTimetable() throws TimetableException {

		if (true == haveBeenGenerated)
			throw new TimetableException(
					"Timetable has already been initialized and generated");

		// For each event with one slot, find a free time-slot with no conflicts 
		// and minimum events
		for (Integer currE : eventsWithOneSlot) {
			int index = searchFreeSlot(currE);
			if (index < 0)
				addEventToUnassigned(currE);
			else {
				addEventToSlot(index, currE);
			}
		}
		// for each time-slot, assign the available rooms to events in this slot
		// TODO: code-snippet is repeated
		for (int slotIndex = 0; slotIndex < numSlots; slotIndex++) {
			assignRoomsToEvents(slotIndex);
		}

		/*
		 * Re-check if possible to place any unassigned event
		 */

		for (int i = unassignedEvents.size() - 1 ; i >=0; i--)
			searchAndAddRoomSlot(unassignedEvents.get(i));

		// Calculate and update penalty score due to the sequence of slots
		for (int slotIndex = 0; slotIndex < numSlots - 1; slotIndex++) {
			ComputePenalty(slotIndex, slotIndex + 1);
		}

		// Mark the first generation of this timetable
		haveBeenGenerated = true;

	}

	/**
	 * Sequence slots so as to minimize the soft constraints violations
	 * 
	 * @throws TimetableException
	 */
	public void sequenceSlots() throws TimetableException {
		// sequence slots by swapping them so as to minimize the penalty score
		int slotIndices[] = new int[2];
		selectTimeSlot(slotIndices);
		Integer slotIndex1 = slotIndices[0], slotIndex2 = slotIndices[1];
		if (slotIndex1 < 0 || slotIndex2 < 0 || slotIndex1 == slotIndex2)
			throw new TimetableException("Error in swapping slots");

		int slot1 = slotsOrder[slotIndex1], slot2 = slotsOrder[slotIndex2];

		/*
		 * Before swapping two slots, we need to find whether these two slots
		 * can be scheduled on the designated days or not. Also events of one
		 * slot in current timetable must not conflict with events of other slot
		 * in parent timetable.
		 */
		ArrayList<Integer> events1 = eventsInSlot[slot1];
		ArrayList<Integer> events2 = eventsInSlot[slot2];

		for (Integer tmp : events1) {
			if (conflictsOnDay(slotIndex2, tmp))
				return;
			if (null != parentTimetable
					&& parentTimetable.conflictsOnSlot(slotIndex2, tmp))
				return;
		}

		for (Integer tmp : events2) {
			if (conflictsOnDay(slotIndex1, tmp))
				return;
			if (null != parentTimetable
					&& parentTimetable.conflictsOnSlot(slotIndex1, tmp))
				return;
		}

		/* ************************************** */
		/*
		 * To swap these two values, penalty values has to be adjusted
		 * accordingly First delete slot1 so penalties of slotIndex+1 and
		 * slotIndex-1 will be changed if slot1 and slot2 and consecutive,
		 * delete and compute penalty operation will be called three times only.
		 */
		if (slotIndex1 > 0)
			deleteSlotAndUpdatePenalty(slotIndex1, slotIndex1 - 1);
		if (slotIndex1 + 1 < numSlots)
			deleteSlotAndUpdatePenalty(slotIndex1, slotIndex1 + 1);
		if (slotIndex2 > 0 && slotIndex1 != slotIndex2 - 1)
			deleteSlotAndUpdatePenalty(slotIndex2, slotIndex2 - 1);
		if (slotIndex2 + 1 < numSlots && slotIndex1 != slotIndex2 + 1)
			deleteSlotAndUpdatePenalty(slotIndex2, slotIndex2 + 1);

		// Swap the order of two slots
		slotsOrder[slotIndex1] = slot2;
		slotsOrder[slotIndex2] = slot1;

		// Now update the penalty scores
		if (slotIndex1 > 0)
			ComputePenalty(slotIndex1, slotIndex1 - 1);
		if (slotIndex1 + 1 < numSlots)
			ComputePenalty(slotIndex1, slotIndex1 + 1);
		if (slotIndex2 > 0 && slotIndex1 != slotIndex2 - 1)
			ComputePenalty(slotIndex2, slotIndex2 - 1);
		if (slotIndex2 + 1 < numSlots && slotIndex1 != slotIndex2 + 1)
			ComputePenalty(slotIndex2, slotIndex2 + 1);

	}

	/**
	 * Sequence events to minimize soft constraints violation and to place
	 * unassigned events
	 * 
	 * @throws TimetableException
	 */
	public void shuffleEvents() throws TimetableException {
		/*
		 * After sequencing slots, events will be replaced. An unassigned event
		 * can be selected or an already assigned event can be placed in some
		 * other slot. 
		
		 * An event can be scheduled in other slot only if it does not conflict
		 * with other events scheduled on that day.
		 * Keep looking till such slot and event combination is found
		 * This event must not conflict with events scheduled in parent timetable in that slot.
		 * 
		 */

		Integer slotIndex1, slotIndex2, slot1, slot2, eventIndex;
		int slotandEvent[] = new int[2];
		do {
			selectSlotAndEvent(slotandEvent);
			slotIndex2 = slotandEvent[0];
			eventIndex = slotandEvent[1];
			if (eventIndex < 0 || slotIndex2 < 0)
				throw new TimetableException("Invalid values of eventIndex "
						+ "and slot in sequenceEvents");

			slot1 = slotOfEvent[eventIndex];
			slot2 = slotsOrder[slotIndex2];
			slotIndex1 = (slot1 < 0) ? slot1 : findIndexOfSlot(slot1);

		} while (slot1 >= 0 && !haveSameDay(slotIndex1, slotIndex2)
				&& conflictsOnDay(slotIndex2, eventIndex));
		
		if(null != parentTimetable) 
			if(parentTimetable.conflictsOnSlot(slotIndex2, eventIndex))
				return;

		// Nullify the penalties associated due to slot1 and slot2
		if (slot1 >= 0) {
			if (slotIndex1 > 0)
				deleteSlotAndUpdatePenalty(slotIndex1, slotIndex1 - 1);
			if (slotIndex1 + 1 < numSlots)
				deleteSlotAndUpdatePenalty(slotIndex1, slotIndex1 + 1);
		}

		if (slotIndex2 > 0 && slotIndex1 != slotIndex2 - 1)
			deleteSlotAndUpdatePenalty(slotIndex2, slotIndex2 - 1);
		if (slotIndex2 + 1 < numSlots && slotIndex1 != slotIndex2 + 1)
			deleteSlotAndUpdatePenalty(slotIndex2, slotIndex2 + 1);

		/* Remove event from slot or else remove it from unassigned events */
		if (slot1 >= 0)
			removeEventFromSlot(slotIndex1, eventIndex);
		else
			removeEventFromUnassigned(eventIndex);

		/* ******** Place this event in slot2 and remove conflicting events */

		ArrayList<Integer> conflictingEvents = new ArrayList<Integer>();

		// Remove all conflicting events from this slot and free their rooms
		for (int i = eventsInSlot[slot2].size() - 1; i >= 0; i--) {
			Integer tmpEvent = eventsInSlot[slot2].get(i);
			if (eventsConflict(eventIndex, tmpEvent)) {
				removeEventFromSlot(slotIndex2, tmpEvent);
				conflictingEvents.add(tmpEvent);
			}
		}
		// Add this event to this slot
		addEventToSlot(slotIndex2, eventIndex);

		/* ************************************************************* */
		/* Find the rooms for eventIndex and remaining events in slot2 */
		assignRoomsToEvents(slotIndex2);

		addEventsToUnassigned(conflictingEvents);

		if (slot1 >= 0) {
			// Try scheduling the events in unassigned events in slot1
			/* Place all Non-conflicting events to slot1 */
			for (int i = unassignedEvents.size() - 1; i >= 0; i--) {
				Integer tmp = unassignedEvents.get(i);
				if (conflictsOnSlot(slotIndex1, tmp)
						|| conflictsOnDay(slotIndex1, tmp))
					continue;
				else {
					addEventToSlot(slotIndex1, tmp);
					removeEventFromUnassigned(tmp);
				}
			}

			/* Repeat the procedure for room assignment for events in slot1 */
			assignRoomsToEvents(slotIndex1);
		}
		
		// Update the penalty scores of slot1 and slot2
		if (slot1 >= 0) {
			if (slotIndex1 > 0)
				ComputePenalty(slotIndex1, slotIndex1 - 1);
			if (slotIndex1 + 1 < numSlots)
				ComputePenalty(slotIndex1, slotIndex1 + 1);
		}

		if (slotIndex2 > 0 && slotIndex1 != slotIndex2 - 1)
			ComputePenalty(slotIndex2, slotIndex2 - 1);
		if (slotIndex2 + 1 < numSlots && slotIndex1 != slotIndex2 + 1)
			ComputePenalty(slotIndex2, slotIndex2 + 1);
	}
	
	//TODO: Free rooms can also be considered.
	public void exchangeRooms() throws TimetableException {
		int events[] = new int[2];
		selectEventsForRoomEx(events);
		int event1 = events[0];
		int event2 = events[1];
		
		if(event1 == event2)	return;
		
		int room1 = roomOfEvent[event1];
		int room2 = roomOfEvent[event2];
		
		if(false == roomsForEvent[event1].contains(room2)
				|| false == roomsForEvent[event2].contains(room1))
			return;
		if (room1 < 0 || room2 < 0)
			throw new TimetableException("Error while swapping rooms");

		int slotIndex = findIndexOfSlot(slotOfEvent[event1]);

		// Delete the penalty caused by this slot.
		if (slotIndex > 0)
			deleteSlotAndUpdatePenalty(slotIndex, slotIndex - 1);
		if (slotIndex + 1 < numSlots)
			deleteSlotAndUpdatePenalty(slotIndex, slotIndex + 1);

		// swap the rooms of two events
		roomOfEvent[event1] = room2;
		roomOfEvent[event2] = room1;

		// Update the penalty score
		if (slotIndex > 0)
			ComputePenalty(slotIndex, slotIndex - 1);
		if (slotIndex + 1 < numSlots)
			ComputePenalty(slotIndex, slotIndex + 1);

	}

	/**
	 * For each slot, run a deterministic routine to match and assign suitable 
	 * rooms for events in that slot.
	 * @throws TimetableException
	 */
	public void assignRoomsToEvents(int slotIndex) throws TimetableException {

			int slotNumber = slotsOrder[slotIndex];
			ArrayList<Integer> eventsWithoutR = eventsInSlot[slotNumber];
			ArrayList<Integer> eventsWithR = new ArrayList<Integer>();
			ArrayList<Integer> busyRooms = new ArrayList<Integer>();
			ArrayList<Integer> occupiedRooms = (null == parentTimetable)?null
																		:parentTimetable
																				.getRoomsInSlot(slotIndex);
			Util.assignRoomsToEvents(numRooms, eventsWithoutR, eventsWithR,
												busyRooms, occupiedRooms,
												roomsForEvent, roomOfEvent);
			addEventsToUnassigned(eventsWithoutR);
			setEventsAndRoomsInSlot(slotIndex, eventsWithR, busyRooms);
	}


	/**
	 * It will calculate the penalty score between slotIndex1 and slotIndex2 and
	 * update data structures penaltyEvent and penaltySlot. A penalty score
	 * between two events in different slots will be shared by both events and
	 * thus both slots. total penalty will increase by twice of score. Penalty
	 * score between currentTimetable and parentTimetable is also computed and
	 * updated. This score is updated only in currentTimetable. slotIndices must
	 * be consecutive
	 * 
	 * @param slotIndex1
	 * @param slotIndex2
	 * @return
	 * @throws TimetableException
	 */
	private void ComputePenalty(int slotIndex1, int slotIndex2)
			throws TimetableException {

		if (slotIndex1 < 0 || slotIndex2 < 0 || slotIndex2 >= numSlots
				|| slotIndex1 >= numSlots)
			throw new TimetableException("Invalid values of slotIndex");

		if (Math.abs(slotIndex1 - slotIndex2) > 1)
			throw new TimetableException("Slots are not consecutive");

		if (slotIndex1 == slotIndex2) {
			return;
		}

		// if both slots are scheduled in different sessions or days then return 0
		if (getSession(slotIndex1) != getSession(slotIndex2))
			return;

		int score = 0, penalty = 0, slot1 = slotsOrder[slotIndex1], slot2 = slotsOrder[slotIndex2];
		ArrayList<Integer> list1 = eventsInSlot[slot1];
		ArrayList<Integer> list2 = eventsInSlot[slot2];
		ArrayList<Integer> parentList1 = ((null != parentTimetable) ? parentTimetable
																			.getEventsInSlot(slotIndex1)
																	: null);
		ArrayList<Integer> parentList2 = ((null != parentTimetable) ? parentTimetable
																			.getEventsInSlot(slotIndex2)
																	: null);

		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if ((score = penaltyBtwEvents(list1.get(i),roomOfEvent[list1.get(i)]
				                                            ,list2.get(j), roomOfEvent[list2.get(j)])) > 0) {
					penaltyOfEvent[list1.get(i)] += score;
					penaltyOfEvent[list2.get(j)] += score;
					penalty += score;
				}
			}
		}

		penaltyOfSlot[slot1] += penalty;
		penaltyOfSlot[slot2] += penalty;
		
		if(null != parentTimetable) {
			for (int i = 0; i < list1.size(); i++) {
				for (int j = 0; j < parentList2.size(); j++) {
					if ((score = penaltyBtwEvents(list1.get(i),roomOfEvent[list1.get(i)],
							parentList2.get(j), parentTimetable.roomOfEvent[parentList2.get(j)])) > 0) {

						penaltyOfEvent[list1.get(i)] += score;
						penaltyOfSlot[slot1] += score;
						penalty += score;
					}
				}
			}

			for (int i = 0; i < parentList1.size(); i++) {
				for (int j = 0; j < list2.size(); j++) {
					if ((score = penaltyBtwEvents(parentList1.get(i), parentTimetable.roomOfEvent[parentList1.get(i)],
							list2.get(j), roomOfEvent[list2.get(j)])) > 0) {
						penaltyOfEvent[list2.get(j)] += score;
						penaltyOfSlot[slot2] += score;
						penalty += score;
					}
				}
			}
		}

		totalPenalty += 2 * penalty;
		/*
		 * if(false == worstSlots.add(slot1)) { worstSlots.remove(slot1);
		 * worstSlots.add(slot1); } if(false == worstSlots.add(slot2)) {
		 * worstSlots.remove(slot2); worstSlots.add(slot2); }
		 */

	}


	private int getSession(int slotIndex) {
		return slotIndex / 4;
	}

	/**
	 * It will nullify the penalty score between two slots slotIndex1 and
	 * slotIndex2 and update data structures penaltyEvent and penaltySlot.
	 * 
	 * @param slotIndex1
	 * @param slotIndex2
	 * @return
	 * @throws TimetableException
	 */
	private void deleteSlotAndUpdatePenalty(int slotIndex1, int slotIndex2)
			throws TimetableException {
		if (slotIndex1 < 0 || slotIndex2 < 0 || slotIndex2 >= numSlots
				|| slotIndex1 >= numSlots)
			throw new TimetableException("Invalid values of slotIndex");

		if (Math.abs(slotIndex1 - slotIndex2) > 1)
			throw new TimetableException("Slots are not consecutive");

		if (slotIndex1 == slotIndex2) {
			return;
		}

		// if both slots are scheduled in different sessions or days then return
		// 0
		if (getSession(slotIndex1) != getSession(slotIndex2))
			return;

		int score = 0, penalty = 0, slot1 = slotsOrder[slotIndex1], slot2 = slotsOrder[slotIndex2];
		ArrayList<Integer> list1 = eventsInSlot[slot1];
		ArrayList<Integer> list2 = eventsInSlot[slot2];
		ArrayList<Integer> parentList1 = ((null != parentTimetable) ? parentTimetable
																			.getEventsInSlot(slotIndex1)
																	: null);
		ArrayList<Integer> parentList2 = ((null != parentTimetable) ? parentTimetable
																			.getEventsInSlot(slotIndex2)
																	: null);
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if ((score = penaltyBtwEvents(list1.get(i),roomOfEvent[list1.get(i)]
							                                            ,list2.get(j), roomOfEvent[list2.get(j)])) > 0) {
					penaltyOfEvent[list1.get(i)] -= score;
					penaltyOfEvent[list2.get(j)] -= score;
					penalty += score;
				}
			}
		}
		penaltyOfSlot[slot1] -= penalty;
		penaltyOfSlot[slot2] -= penalty;
		
		if(null != parentList2) {
			for (int i = 0; i < list1.size(); i++) {
				for (int j = 0; j < parentList2.size(); j++) {
					if ((score = penaltyBtwEvents(list1.get(i),roomOfEvent[list1.get(i)],
							parentList2.get(j), parentTimetable.roomOfEvent[parentList2.get(j)])) > 0) {
						penaltyOfEvent[list1.get(i)] -= score;
						penaltyOfSlot[slot1] -= score;
						penalty += score;
					}
				}
			}
		}

		if(null != parentList1) {
			for (int i = 0; i < parentList1.size(); i++) {
				for (int j = 0; j < list2.size(); j++) {
					if ((score = penaltyBtwEvents(parentList1.get(i), parentTimetable.roomOfEvent[parentList1.get(i)],
																		list2.get(j), roomOfEvent[list2.get(j)])) > 0) {
						penaltyOfEvent[list2.get(j)] -= score;
						penaltyOfSlot[slot2] -= score;
						penalty += score;
					}
				}
			}
		}

		totalPenalty -= 2 * penalty;
	}

	// Computes penalty between two events scheduled in consecutive slots
	private int penaltyBtwEvents(Integer eIndex1,Integer rIndex1, Integer eIndex2,Integer rIndex2)
			throws TimetableException {
		if (eIndex1 < 0 || eIndex2 < 0)
			throw new TimetableException("Negative values of event Index");
		Event e1 = allEvents.get(eIndex1);
		Event e2 = allEvents.get(eIndex2);
		int penalty = 5 * boolToInt(e1.getProfessor().getId().equals(e2.getProfessor().getId()));

		/*
		 * if(roomOfEvent[i1] < 0 || roomOfEvent[i2] < 0) throw new
		 * TimetableException("Cannot compute penalty " + "between events with
		 * no room");
		 */
		Room r1 = Data.getInstance().getRoomList().get(rIndex1);
		Room r2 = Data.getInstance().getRoomList().get(rIndex2);
		if (null == r1 || null == r2)
			throw new TimetableException("Null Room obtained");

		penalty += 4 * boolToInt(eventsConflict(eIndex1, eIndex2)
				&& (r1.getBuilding() != r2.getBuilding()));
		penalty += 3 * boolToInt(eventsConflict(eIndex1, eIndex2)
									&& !e1.getProfessor().getId().equals(e2.getProfessor().getId())
									&& !r1.getCode().equals(r2.getCode()));

		return penalty;
	}

	private int boolToInt(boolean exp) {
		return (exp) ? 1 : 0;
	}

	private boolean searchAndAddRoomSlot(int currE) throws TimetableException {
		for (int slotIndex = 0; slotIndex < numSlots; slotIndex++) {
			// first check if it conflicts with any event already scheduled
			if (conflictsOnSlot(slotIndex, currE))
				continue;

			// check if it conflicts for the day of slot
			if (conflictsOnDay(slotIndex, currE))
				continue;

			// Check if there is room available for it
			ArrayList<Integer> fitRooms = roomsForEvent[currE];
			int slotNum = slotsOrder[slotIndex];
			for (Integer tmpRoom : fitRooms) {
				if (null != parentTimetable
						&& true == parentTimetable.getRoomsInSlot(slotIndex).contains(tmpRoom))
					continue;
				if (false == roomsInSlot[slotNum].contains(tmpRoom)) {
					addEventAndRoomToSlot(slotIndex, currE, tmpRoom);
					removeEventFromUnassigned(currE);
					return true;
				}
			}
		}

		return false;

	}

	/**
	 * search a free time slot for given event with no clashes and having
	 * minimum number of events scheduled Also search the time-slot according to
	 * event type if event is lecture - search morning time-slot first(add = 0)
	 * if event is tutorial - search evening time-slot first(add = 4) if event
	 * is Laborartory - search evening time-slot with consecutive timeslots
	 * available assign events in order of time-slots they require it is assumed
	 * that arrayList of events in sorted in order of required timeslots
	 * 
	 * @param event
	 * @return
	 * @throws TimetableException
	 */
	private int searchFreeSlot(int eventIndex) throws TimetableException {
		int cnt = 0, add = 0;
		int minSlot = NIL;

		for (int index = cnt + add; cnt < numSlots; cnt++, index = 2 * cnt
				- cnt % 4 + add) {
			if (cnt == numSlots / 2 - 1) {
				add = -numSlots + 4 - add;
			}

			// check if this event can be scheduled on the same day
			if (true == conflictsOnDay(index, eventIndex))
				continue;

			// Check for any conflicting event in this slot
			if (true == conflictsOnSlot(index, eventIndex))
				continue;

			if (minSlot < 0
					|| numEventsInSlot(minSlot) > numEventsInSlot(index))
				minSlot = index;
		}
		return minSlot;
	}

	/**
	 * Return indices of two slots which can be swapped //TODO: this function
	 * can be made more intelligent
	 * 
	 * @param slotIndex1
	 * @param slotIndex2
	 */
	private void selectTimeSlot(int slotIndices[]) {
		Random rand = new Random();
		slotIndices[0] = rand.nextInt(numSlots);
		do {
			slotIndices[1] = rand.nextInt(numSlots);
		} while (slotIndices[0] == slotIndices[1]);
	}

	/**
	 * Select an Event and a slot other than the one in which this event is
	 * already placed. //TODO: this function can be made more intelligent
	 * 
	 * @param event1
	 * @param event2
	 */
	private void selectSlotAndEvent(int slotandEvent[]) {
		Random rand = new Random();
		slotandEvent[1] = eventsWithOneSlot.get(rand.nextInt(eventsWithOneSlot.size()));
		slotandEvent[0] = rand.nextInt(numSlots);
		while (slotsOrder[slotandEvent[0]] == slotOfEvent[slotandEvent[1]])
			slotandEvent[0] = rand.nextInt(numSlots);
	}

	// TODO: Make it more intelligent
	private void selectEventsForRoomEx(int[] events) {
		Random rand = new Random();
		int slotIndex = rand.nextInt(numSlots);
		int numEvents, totalIterations = numSlots;
		while ((numEvents = eventsInSlot[slotsOrder[slotIndex]].size()) < 2
				&& (0 != totalIterations--)) {
			slotIndex = rand.nextInt(numSlots);
		}
		//TODO: Error condition if(numEvents <= 0 ) 
		int randN = rand.nextInt(numEvents);
		events[0] = eventsInSlot[slotsOrder[slotIndex]].get(randN);
		if(numEvents < 2) {
			events[0] = events[1];
			return;
		}
		else {
			do {
				events[1] = eventsInSlot[slotsOrder[slotIndex]].get(rand.nextInt(numEvents));
			}
			while(events[0] == events[1]);
		}
			
	}
}
