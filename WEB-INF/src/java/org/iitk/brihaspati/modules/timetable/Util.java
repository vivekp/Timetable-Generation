package org.iitk.brihaspati.modules.timetable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Util implements Constants {

	/**
	 * run a maximal matching algorithm to match events in a timeslot to available rooms
	 * @throws TimetableException 
	 */
	public static void assignRoomsToEvents(int numRooms,
													ArrayList<Integer> eventsWithoutR,
													ArrayList<Integer> eventsWithR,
													ArrayList<Integer> busyRooms,
													ArrayList<Integer> preOccupiedRooms,
													ArrayList<Integer> roomsForEvent[],
													int eventRoom[]) throws TimetableException {
		if(null == eventsWithoutR) 
		{
			throw new TimetableException("list of events without room is null");
		}
		if(eventsWithoutR.size() == 0)
			return;				/* No events to assign 		*/
		
		if(null == roomsForEvent || roomsForEvent.length == 0)
			throw new TimetableException("List of rooms for each event is empty");
		
		if(null == eventsWithR)
			throw new TimetableException("List for storing events with rooms is null");
		
		if(null == busyRooms)
			throw new TimetableException("list for storing occupied rooms is null");
		
		int numEvents = eventsWithoutR.size();
		int numVertices = numEvents + numRooms;
		
		int color[] = new int[numVertices];		// Stores the color of all vertices
		int match[] = new int[numVertices];		//stores the number of event that is matched to this room
		int label[] = new int[numVertices];
		int parent[] = new int[numVertices];
		
		

		//currently every event and room is unmatched
		for(int i = 0;i < numVertices;i++) {
			color[i] = WHITE;
			parent[i] = -1;
		}

		//put all white vertices representing event into queue
		for(int i = 0;i < numEvents;i++) {
			label[i] = 0;
			//	queue.add(i);
		}

		for(int i = numEvents;i < numVertices;i++) {
			match[i] = -1;
			label[i] = -1;
		}

		//iteratively find an augmenting path
		for(int i = 0;i < numEvents;i++) {
			if(WHITE == color[i]) {
				//re-initialize the labels and parent fields
				for(int j = 0;j < numVertices;j++) {
					label[j] = -1;
					parent[j] = -1;
				}
				Queue<Integer> queue = new LinkedList<Integer>();
				queue.add(i);
				
				int root = i;
				label[root] = 0;
				int leaf = -1;
				
				outer:
				while(!queue.isEmpty()) {
					int eventV = queue.remove();
					ArrayList<Integer> adjacent = new ArrayList<Integer>
																(roomsForEvent[eventsWithoutR.get(eventV)]);
					if(null != preOccupiedRooms)
						adjacent.removeAll(preOccupiedRooms);	// ignore rooms that are preoccupied
					for(int j = 0;j < adjacent.size();j++) {
						int roomIndex = numEvents + adjacent.get(j);
						if(label[roomIndex] >= 0)
							continue;										//continue if already labeled
						parent[roomIndex] = eventV;
						label[roomIndex] = label[eventV] + 1;
				
						if(WHITE == color[roomIndex]) {
							leaf = roomIndex;
							break outer;
						}
						else {
							label[match[roomIndex]] = label[roomIndex] + 1;
							parent[match[roomIndex]] = roomIndex;
							queue.add(match[roomIndex]);
						}
					}
				}
				for(int j = leaf;j > 0; j = parent[parent[j]]) {
					match[j] = parent[j];
					color[j] = GREEN;
					color[parent[j]] = GREEN;
				}
			}
		}

		
		for(int i = numEvents;i < numVertices;i++) {
			if(color[i] == GREEN) {
				int roomIndex = i - numEvents;
				int event = eventsWithoutR.get(match[i]);
				eventRoom[event] = roomIndex;
				eventsWithR.add(event);
				busyRooms.add(roomIndex);
			}
		}
		eventsWithoutR.removeAll(eventsWithR);

		
	}

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws TimetableException {
		ArrayList<Integer> eventsWithoutR = new ArrayList<Integer>();
		eventsWithoutR.add(0);
		eventsWithoutR.add(1);
		eventsWithoutR.add(2);
		ArrayList<Integer> eventsWithR = new ArrayList<Integer>();
		ArrayList<Integer> busyRooms = new ArrayList<Integer>();
		int eventRoom[] = new int[3];
		ArrayList<Integer> roomsForEvent[] = new ArrayList[3];
		ArrayList<Integer> preOccupiedRooms = new ArrayList<Integer>();
		preOccupiedRooms.add(0);
		preOccupiedRooms.add(1);
		for(int i = 0;i<eventsWithoutR.size();i++)
			roomsForEvent[i] = new ArrayList<Integer>();
		roomsForEvent[0].add(0);
		roomsForEvent[0].add(1);
		roomsForEvent[1].add(1);
		roomsForEvent[1].add(2);
		roomsForEvent[2].add(3);
		roomsForEvent[2].add(3);
		int numRooms = 4;
		assignRoomsToEvents(numRooms, eventsWithoutR, eventsWithR, 
												busyRooms,preOccupiedRooms, roomsForEvent, eventRoom);
		
		for(int i=0;i<eventsWithR.size();i++) {
			System.out.println(eventsWithR.get(i) + " " + eventRoom[eventsWithR.get(i)]);
		}
		
	}
		
}
