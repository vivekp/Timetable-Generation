package org.iitk.brihaspati.modules.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import org.apache.turbine.modules.actions.VelocityAction;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.timetable.*;

import com.lowagie.text.DocumentException;

public class GenerateTimeTable extends VelocityAction {
	
	static STimetable best;
	static SingleSlotTimetable sST;
	static MultiSlotTimetable mST;
	static STimetable univT;
	
	public GenerateTimeTable() {
		initialize();
		System.out.println("Leaving constructor GenerateTimeTable");
	}
	
	public static void initialize() {
		try {
			best = new STimetable();
			//univT = new STimetable();
			//univT.loadFromDB(Data.getConnection(), Data.getInstance().getFixedEvents());
//			Data.getInstance().reloadData();
			STimetable parent = null;
			for(int i = Constants.MAX_SLOTS_FOR_EVENT; i >= 2;i--) {
				mST = new MultiSlotTimetable(i);
				mST.setParentTimeTable(parent);
				mST.generateFirstTimetable();
				parent = mST.mergeWithParent();
			}
			sST = new SingleSlotTimetable();
			sST.setParentTimeTable(parent);
			sST.generateFirstTimetable();
			SingleSlotTimetable mySST = new SingleSlotTimetable(sST);
			best = mySST.mergeWithParent();
		} catch (TimetableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void searchBestTimetable() throws TimetableException {
		if(null == best) initialize();
		SingleSlotTimetable tmp = new SingleSlotTimetable(sST);
		long start  = System.currentTimeMillis();
		// Swap slots
		for(int i=1;i<100000;i++) {
			tmp.sequenceSlots();
			if(tmp.isBetterThan(sST)) {
				sST = new SingleSlotTimetable(tmp);
				best = new SingleSlotTimetable(tmp).mergeWithParent();
			}
		}
		System.out.println("After Sequence slots ");
		best.displayPenalty();
		System.out.println("Total time taken : " + (System.currentTimeMillis() - start)/60 + " seconds\n");
		start = System.currentTimeMillis();

//		 Exchange events between slots 
		tmp = new SingleSlotTimetable(sST); 
		for(int i=1;i < 100000;i++) {
			tmp.shuffleEvents();
			if(tmp.isBetterThan(sST)) {
				sST = new SingleSlotTimetable(tmp);
				best = new SingleSlotTimetable(tmp).mergeWithParent();
			}
		}
		
		System.out.println("After shuffle events");
		best.displayPenalty();
		System.out.println("Total time taken : " + (System.currentTimeMillis() - start)/60 + " seconds\n");
		start = System.currentTimeMillis();
		
		/*// Swap slots
		for(int i=1;i<100000;i++) {
			tmp.sequenceSlots();
			if(tmp.isBetterThan(sST)) {
				sST = new SingleSlotTimetable(tmp);
				best = new SingleSlotTimetable(tmp).mergeWithParent();
			}
		}
		
		System.out.println("After again swapping slots");
		best.displayPenalty();
		System.out.println("Total time taken : " + (System.currentTimeMillis() - start)/60 + " seconds\n");
		start = System.currentTimeMillis();
		
		//Exchange rooms
		for(int i=1;i<100000;i++) {
			tmp.exchangeRooms();
			if(tmp.isBetterThan(sST)) {
				sST = new SingleSlotTimetable(tmp);
				best = new SingleSlotTimetable(tmp).mergeWithParent();
			}
		}
		
		System.out.println("After rooms exchange");
		best.displayPenalty();
		System.out.println("Total time taken : " + (System.currentTimeMillis() - start)/60 + " seconds\n");
		start = System.currentTimeMillis();*/
		
	}
	
	public STimetable getBest() {
		return best;
	}
	
	public void doGenerate(RunData data, Context context) {
		try {
			initialize();
//			best.display();
			best.displayPenalty();
			System.out.println("\n");
			searchBestTimetable();
			String path = data.getServletContext().getRealPath("/reports/");
			try {
				ArrayList<Integer> eventList = Data.getInstance().getEventsWithOneSlot();
				for(int i = Constants.MAX_SLOTS_FOR_EVENT; i >= 2;i--) {
					eventList.addAll(Data.getInstance().getEventsWithMultiSlots(i));
				}
				best.insertIntoDB(Data.getConnection(), eventList);
				PDFGenerator reportGen = new PDFGenerator(best);
				reportGen.publishAllBatchesTimetables(path);
				reportGen.publishAllFacultyTimetables(path);
				reportGen.publishAllRoomTimetables(path);
				reportGen.publishAllFacultyHTML(path);
				reportGen.publishAllBatchHTML(path);
				//best.saveBestTimetable();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
//			System.out.println(Test.counter);
//			best.displayPenalty();
		} catch (TimetableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	static public void main(String args[]) {
		try {
			System.out.println("Before sequencing slots");
			initialize();
//			best.display();
			best.displayPenalty();
//			System.out.println("\n");
			searchBestTimetable();
//			best = new STimetable();
			try {
				ArrayList<Integer> eventList = Data.getInstance().getEventsWithOneSlot();
				for(int i = Constants.MAX_SLOTS_FOR_EVENT; i >= 2;i--) {
					eventList.addAll(Data.getInstance().getEventsWithMultiSlots(i));
				}
				
//				best.loadFromDB(Data.getConnection(), eventList);
				best.insertIntoDB(Data.getConnection(), eventList);
				String path = "E:\\course_timetables\\";
				PDFGenerator reportGen = new PDFGenerator(best);
				reportGen.publishAllBatchesTimetables(path);
				reportGen.publishAllFacultyTimetables(path);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(Test.counter);
//			best.displayPenalty();
		} catch (TimetableException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public void doPerform(RunData arg0, Context arg1) throws Exception {
		throw new TimetableException("Button not found");

	}

	
}
