package org.iitk.brihaspati.modules.screens.call.Calendar_Mgmt;

/*
 * @(#)Calendar_Display.java
 *
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur.
 *  All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or
 *  without modification, are permitted provided that the following
 *  conditions are met:
 *
 *  Redistributions of source code must retain the above copyright
 *  notice, this  list of conditions and the following disclaimer.
 *
 *  Redistribution in binary form must reproducuce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 *  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 *  BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 */
import java.util.Date;
import java.util.Vector;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.torque.util.Criteria;                                                  
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.CalInformation;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CalendarUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.apache.turbine.services.servlet.TurbineServlet;

/**
 * @author <a href="mailto:singhnk@iitk.ac.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a> 
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

/**
 * This class displays the current month calendar as well as the for the month 
 * selected by the user
 */
public class Calendar_Display extends SecureScreen{
	
	/**
	* Hashtable to store Day and Event for Academic events
	* and Institute Holidays
	*/
	Hashtable hday=new Hashtable();
	Hashtable acal=new Hashtable();
	public void doBuildTemplate(RunData data,Context context){
		try{

			/**
			* Get the current date and time. Put it in context
			*/
			Date d= new Date();
			context.put("date",d);
			/**
			* Get the path from URL specifying if the user
			* is in personalised or course calendar
			*/
			ParameterParser pp=data.getParameters();
			String path=pp.getString("path");
			context.put("path",path);
			/**
			 * Get the current date in the specific format
			 * @see ExpiryUtil in utils
			 */
			int date=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
			/**
			 * Seperate the day, month and year from the
			 * current date obtained above
			 */
			int curr_day=date%100;
			String day_today=Integer.toString(curr_day);
			context.put("c",day_today);
			if(curr_day<10)
			{	
				day_today="0"+day_today;
			}
			date=date/100;
			int curr_month=date%100;
			date=date/100;
			int curr_year=date;
			/**
			 * Make year list having 10 years back and 20
			 * years later with respect to the current year
			 */

			Vector year_list=new Vector();
			for(int i=curr_year-10;i<curr_year;i++){
				year_list.addElement(Integer.toString(i));
			}

			for(int i=curr_year;i<=curr_year+20;i++){
				year_list.addElement(Integer.toString(i));
			}
			
			context.put("year_list",year_list);
			int month=pp.getInt("month");
			int year=pp.getInt("year");
			String status=pp.getString("status","current");

			/**
			 * Reintializing variables if the user wants to
			 * see the calendar of the previous or the next
			 * month
			 */

			if(status.equals("previous"))
			{
				month=month-1;
				if(month==0)
				{
					month=12;
					year=year-1;
				}
			}
			else if(status.equals("next")){
				month=month+1;
				if(month>12){
					month=1;
					year=year+1;
				}
			}
			else{
				if(month==0)
					month=curr_month;
				if(year==0)
					year=curr_year;
			}

			/**
			 * Calculating the total number of days for the
			 * month requested by the user
			 */

			int days_of_month=0;

			if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
			{
				days_of_month=31;
			}
			else if(month==2)
			{
				if( (year%4==0 && year%100!=0) || (year%400==0))
					days_of_month=29;
				else
					days_of_month=28;
			}
			else
			{
				days_of_month=30;
			}
			String cMonth=Integer.toString(month);
			context.put("month1",cMonth);
			int d_month=Integer.parseInt(cMonth);
			String dmonth="";
			if(d_month<10)
				dmonth="0"+cMonth;
			else
				dmonth=cMonth;
			String cYear=Integer.toString(year);	
			context.put("year1",cYear);
			/**
			 * Calculating the zeller month and zeller year
			 * according to the present month and year.
			 * Further using Zeller's algorithm to get the
			 * weekday on which first day of the month will fall.
			 */

			int zeller_month=0;
			int zeller_year=0;

			if(month<3){
				zeller_month=month+10;
			}
			else{
				zeller_month=month-2;
			}

			if(zeller_month>10){
				zeller_year=year-1;
			}
			else{
				zeller_year=year;
			}

			int zeller_day=1;
			int zeller_firstday=((int)((13*zeller_month-1)/5)+zeller_day+zeller_year%100+(int)((zeller_year%100)/4)-2*(int)(zeller_year/100)+(int)(zeller_year/400)+77)%7;
			
			String first_day=Integer.toString(zeller_firstday);
			context.put("fday",first_day);
	
			Vector space=new Vector();
			for(int i=1;i<=zeller_firstday;i++){
				space.addElement(Integer.toString(i));
			}

			if(zeller_firstday != 0)
				context.put("space",space);

			User user=data.getUser();
			String userName=user.getName();
			context.put("name",userName);

			/**
			 * Putting the course id and course name in
			 * context. These are used when the user is in
			 * course calendar.
			 */

			int user_id=UserUtil.getUID(userName);
			String uid=Integer.toString(user_id);
			String course_id=(String)user.getTemp("course_id");
			String course_name=(String)user.getTemp("course_name");
			context.put("course",course_name);

			Vector day=new Vector();
			Vector token_day=new Vector();
			Criteria crit= new Criteria();
			/**
			 * Retreiving the days for which events are
			 * specified in the calendar. Those days will be
			 * marked with '*' so that the user can know
			 * that there is an entry for that day.
			 */
			int gid=1;
			for(int i=1;i<=days_of_month;i++)
			{
				String get_date="";
				if(i<=9)
					get_date=cYear+dmonth+"0"+i;
				else
					get_date=cYear+dmonth+i;

				crit.add(CalInformationPeer.P_DATE,(Object)get_date,crit.EQUAL);
				if(path.equals("personal"))
				{
					crit.add(CalInformationPeer.GROUP_ID,gid);
					crit.add(CalInformationPeer.USER_ID,user_id);
				}
				else
				{
					gid=GroupUtil.getGID(course_id);
					crit.add(CalInformationPeer.GROUP_ID,gid);
				}
				List result=CalInformationPeer.doSelect(crit);
				String all_date=new String();
				if(result.size()!=0)
				{
					for(int j=0;j<result.size();j++)
					{
                                		CalInformation element=(CalInformation)result.get(j);
						all_date=(element.getPDate()).toString();
					}
					Vector Cal_Date=ExpiryUtil.getPostDate(all_date);	
					String Cal_Day=(String)Cal_Date.elementAt(2);
					token_day.addElement(Cal_Day);
				}
				day.addElement(Integer.toString(i));
			} 
			if(day.size()!=0)
				context.put("element1",day);
			if(token_day.size()!=0)
				context.put("keyword",token_day);
			/**
			 * Initializing the month name depending on the
			 * month number
			 */
			String LangFile=(String)data.getUser().getTemp("LangFile");
			String month_name=MultilingualUtil.ConvertedMonth(month, LangFile);
/*
			switch(month){
				case 1:
					//month_name="January";
					month_name=MultilingualUtil.ConvertedString("brih_January",LangFile);
					break;
				case 2:
					//month_name="February";
					month_name=MultilingualUtil.ConvertedString("brih_February",LangFile);
					break;
				case 3:
					//month_name="March";
					month_name=MultilingualUtil.ConvertedString("brih_March",LangFile);
					break;
				case 4:
//					month_name="April";
					month_name=MultilingualUtil.ConvertedString("brih_April",LangFile);
					break;
				case 5:
					//month_name="May";
					month_name=MultilingualUtil.ConvertedString("brih_May",LangFile);
					break;
				case 6:
					//month_name="June";
					month_name=MultilingualUtil.ConvertedString("brih_June",LangFile);
					break;
				case 7:
//					month_name="July";
					month_name=MultilingualUtil.ConvertedString("brih_July",LangFile);
					break;
				case 8:
//					month_name="August";
					month_name=MultilingualUtil.ConvertedString("brih_August",LangFile);
					break;
				case 9:
//					month_name="September";
					month_name=MultilingualUtil.ConvertedString("brih_September",LangFile);
					break;
				case 10:
//					month_name="October";
					month_name=MultilingualUtil.ConvertedString("brih_October",LangFile);
					break;
				case 11:
//					month_name="November";
					month_name=MultilingualUtil.ConvertedString("brih_November",LangFile);
					break;
				case 12:
					//month_name="December";
					month_name=MultilingualUtil.ConvertedString("brih_December",LangFile);
					break;
			}
*/
			context.put("currentmonth",Integer.toString(curr_month));
			context.put("M",month_name);
			context.put("currentyear",Integer.toString(curr_year));
 			/**
			*Code for Academic calendar and
			*Institute holidays
			*/
			String way=data.getServletContext().getRealPath("/WEB-INF")+"/conf";
			String path1=way+"/"+"CalendarHolidays.properties";
			String path2=way+"/"+"AcademicCalendar.properties";
			/**
			* key will have value of month and year of which 
			* you want holiday and academic event
			*/
			String key = dmonth + "."+year;
			context.put("key",key);
			Holiday(key,path1);
			Holiday(key,path2);
			/**
			 * Putting all the things in context for user in
			 * the vm page
			 */
			context.put("hday",hday);
			context.put("acal",acal);
			/**
                        * Sending object Integer to context
                        */
                        Integer ii = new Integer(1000);
                        context.put("INT",ii);
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("Exception in calendar Display screen"+e);
			data.addMessage("See ExceptionLog");
		}
	}
	/**
	*This method read the value of 
	*Academic_Calendar and Calendar_Holidays properties files
	*and put in the hash table for using in templates
	*@see CalendarUtil in utils
	*/		

	public void Holiday(String key,String path){
		try{
			String [] AdminConf = CalendarUtil.getValue(path,key);
				
		
			String [] dayfromprop =null;
			String daypp=null;
			String eventpp=null;
			if(AdminConf != null)
			{
				for(int ac=0;ac<AdminConf.length;ac++)
				{
					dayfromprop = AdminConf[ac].split("#");
					if(dayfromprop ==null)
					break;
					else
					{
						daypp = dayfromprop[0];
						eventpp = dayfromprop[1];
						/**
						* put daypp and eventpp in Hashtable
						*/
						if(path.endsWith("CalendarHolidays.properties"))
						{	
							hday.put(daypp,eventpp);
						}
						else
						{
							acal.put(daypp,eventpp);
						}
					}			
				}
			}
		}
		catch(Exception e){
				ErrorDumpUtil.ErrorLog("Exception in holiday method in calendar screen"+e);	
				System.out.println("see ExceptionLog");
				}
	}
			
}
       
