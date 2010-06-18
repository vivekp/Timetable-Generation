package org.iitk.brihaspati.modules.screens.call.Calendar_Mgmt;
                         
/*
 * @(#)Calendar_Insert.java	
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

import java.sql.Time; 
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;                                                
import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.CalInformation;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 * @author <a href="mailto:singhnk@iitk.ac.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a> 
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

/**
 * This class allows the user to insert new events or edit or delete the
 * existing events
 */

public class Calendar_Insert extends SecureScreen
{
	private static Time stime;
	private static Time etime;
	public void doBuildTemplate( RunData data, Context context )
	{
		ParameterParser pp=data.getParameters();
		String username=pp.getString("uname","");
		context.put("name",username);
		/**
		 * Get the mode i.e. either insert,update or delete and
		 * put it in context
		 */

		String mode=pp.getString("mode");
		context.put("mode",mode);
		
		/**
		 * Get the path from URL specifying if the user in
		 * personalised or course calendar
		 */

		String path=pp.getString("path");
		context.put("path",path);

		/**
		 * Obtain a list of the hours in a day and put it in
		 * context for display in the drop down list
		 */

		Vector hour=new Vector();
		for(int i=0;i<=23;i++)
		{
			String hr_string=new String();
			if(i<10)
				hr_string="0"+i;
			else
				hr_string=Integer.toString(i);		
				hour.add(hr_string);
		}
		context.put("hour",hour);
		User user=data.getUser();
		/**
		 * Put the course name in context if the user is in
		 * course calendar. Also put the day, month number,
		 * month name and year in the context.
		 */

		if(!path.equals("personal"))
		{
			context.put("course",(String)user.getTemp("course_name"));	
		}

		String day_calendar=pp.getString("day");
		context.put("day",day_calendar);

		String month_number=pp.getString("mon"); 
		int mnumber=Integer.parseInt(month_number);
		context.put("month_num",month_number);

		String year_calendar=pp.getString("year");
		context.put("year",year_calendar);

	//	String month_name=pp.getString("month");
	//	context.put("month_name",month_name);

			String LangFile =(String)user.getTemp("LangFile");
                        String month_name=MultilingualUtil.ConvertedMonth(mnumber, LangFile);
/*
                        switch(mnumber){
                                case 1:
                                        month_name=MultilingualUtil.ConvertedString("brih_January",LangFile);
                                        break;
                                case 2:
                                        month_name=MultilingualUtil.ConvertedString("brih_February",LangFile);
                                        break;
                                case 3:
                                        month_name=MultilingualUtil.ConvertedString("brih_March",LangFile);
                                        break;
                                case 4:
                                        month_name=MultilingualUtil.ConvertedString("brih_April",LangFile);
                                        break;
                                case 5:
                                        month_name=MultilingualUtil.ConvertedString("brih_May",LangFile);
                                        break;
                                case 6:
                                        month_name=MultilingualUtil.ConvertedString("brih_June",LangFile);
                                        break;
                                case 7:
                                        month_name=MultilingualUtil.ConvertedString("brih_July",LangFile);
                                        break;
                                case 8:
                                        month_name=MultilingualUtil.ConvertedString("brih_August",LangFile);
                                        break;
                                case 9:
                                        month_name=MultilingualUtil.ConvertedString("brih_September",LangFile);
                                        break;
                                case 10:
                                        month_name=MultilingualUtil.ConvertedString("brih_October",LangFile);
                                        break;
                                case 11:
                                        month_name=MultilingualUtil.ConvertedString("brih_November",LangFile);
                                        break;
				case 12:
                                        month_name=MultilingualUtil.ConvertedString("brih_December",LangFile);
                                        break;
                        }
*/
                        context.put("month_name",month_name);

		/**
		 * The following 'if' loop is executed only when the
		 * user is editing or deleting the event. In case of
		 * inserting new events, this loop is skipped.
		 */
		if(mode.equals("update"))
		{
			try
			{
				Criteria crit=new Criteria();
				String infoId=pp.getString("info_id");
				context.put("id",infoId);
				/**
				 * Retreive all the details of the event
				 * requested by the user for updation or
				 * deletion.
				 */
		
				crit.add(CalInformationPeer.INFO_ID,infoId);
				List event_details=CalInformationPeer.doSelect(crit);	
				CalInformation element=(CalInformation)event_details.get(0);
				/**
				 * Get the detail information of the
				 * event in a string
				 */

				String desc=new String(element.getDetailInformation());
				String expiry=Integer.toString(element.getExpiry());
				int expiry1=Integer.parseInt(expiry);
				String start_hour=new String();
				String end_hour=new String();
				String start_min=new String();
				String end_min=new String();
				
				/**
				 * Get the start time and hence seprate
				 * the time in hours and minutes
				 */

				String time=(element.getStartTime()).toString();
				StringTokenizer st_time=new StringTokenizer(time,":");
				while(st_time.hasMoreTokens())
				{
					start_hour=st_time.nextToken();
					start_min=st_time.nextToken();
					String str=st_time.nextToken();
				}
					
				context.put("time",time);
				/**
				 * Get the end time and hence seprate
				 * the time in hours and minutes
				 */

				 time=(element.getEndTime()).toString();
				 st_time=new StringTokenizer(time,":");
				while(st_time.hasMoreTokens())
				{
					end_hour=st_time.nextToken();
					end_min=st_time.nextToken();
					String str=st_time.nextToken();
				}
				/**
				 * Put all the above variables in context
				 */

				context.put("description",desc);
				context.put("expiry",expiry1);
				context.put("st_hr",start_hour);
				context.put("st_min",start_min);
				context.put("end_hr",end_hour);
				context.put("end_min",end_min);
				context.put("info_id",infoId);
			}
			catch(Exception e)
			{
				data.setMessage("Could not get event details from the data base due to exception "+e);
			}
		}
	}
}

