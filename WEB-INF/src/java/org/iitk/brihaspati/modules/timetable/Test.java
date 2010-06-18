package org.iitk.brihaspati.modules.timetable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {
	public static void main(String args[]) throws SQLException {
		
		/*Connection conn = Data.getConnection();
		String sql = "Select distinct course_code from event_list";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			String course_code = rs.getString(1);
			String sql2 = "Select distinct course_code from course_student where course_code like '%" 
										+ course_code + "%'";
			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			if(!rs2.next()) {
				System.out.println(course_code + " Does not have entry");
			}
			else {
				String course_code2 = rs2.getString(1);
				if(false == course_code.equals(course_code2.trim()))
					System.out.println(course_code + "  :  " + course_code2);
					
			}
		}*/
		/*int cnt = 0,add = 4,slots = 1,numSlots = 40;
		outer:
			for(int index = cnt + add;cnt < numSlots;cnt++,index = 2*cnt - cnt%4 + add) {
				if(cnt == numSlots/2 - 1) {
					add = -36 - add;
				 }
				System.out.println(index);
				if(index/4 != (index + slots - 1)/4)		// No consecutive slots available on this day
					continue;

				if(false == conflicts(index,eventIndex))
					continue;

				for(i=1;i<slots;i++) {
					if(conflicts(index+i,eventIndex))
						continue outer;
				}
				if(i < slots)
					continue;
				if(minSlot < 0 || numEvents(minSlot) > numEvents(index))
					minSlot = index;
			}*/
		int cnt = 0,add = 4, numSlots = 32;
		for(int index = cnt + add;cnt < numSlots;cnt++,index = 2*cnt - cnt%4 + add) {
			if(cnt == numSlots/2 - 1) {
				add = -numSlots + 4 - add;
			 }
			System.out.println(index);
		}
	}
	
	private static void change(Integer a) {
		
	}
}
