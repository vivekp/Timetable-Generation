package org.iitk.brihaspati.modules.screens.call.Calendar_Mgmt;

/*
 * @(#)Calendar_Day.java	
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
 */

import java.util.Vector;
//import java.lang.Integer;
import java.util.List;
import java.sql.Date;
import java.sql.Time;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;          
import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.CalInformation;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.velocity.context.Context;

/**
 * @author <a href="mailto:singhnk@iitk.ac.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a> 
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *
 * This class displays the list of entries for the selected date. It also
 * allows the user to insert,update or delete entries.
 */

public class Calendar_Day extends SecureScreen
{              
	public void doBuildTemplate( RunData data, Context context )
	{
		try
		{

			ParameterParser pp=data.getParameters();
			User user=data.getUser();
			String LangFile =(String)user.getTemp("LangFile");

			/**
			 * Get the path from URL specifying whether the
			 * user is using personal or course specific
			 * calendar and put it in the context
			 */

			String path=pp.getString("path","");
			context.put("path",path);

			/**
			 * Retreive the user name and then user id of
			 * the user who is currently logged in and put
			 * it in context
			 * @see UserUtil in utils
			 */

			String uName=user.getName();
			String uid_user=Integer.toString(UserUtil.getUID(uName));		
			context.put("current_userid",uid_user);

			/**
			 * Set the course id for the event depending
			 * upon the path variable obtained previously.
			 */

			int gid=1;
			String cid=new String();
			if(!path.equals("personal"))
			{
				cid=(String)user.getTemp("course_id");
				gid=GroupUtil.getGID(cid);
				context.put("course",(String)user.getTemp("course_name"));
			}
			/**
			* Get the roleid of currently logged user
			* @see UserManagement in utils
			*/
			int userid=Integer.parseInt(uid_user);
			int rid=UserManagement.getRoleinCourse(userid,gid);
			context.put("currentRoleId",rid);  
			/**
			 * Get the day, month number, month name and
			 * year from the URL and put them in context
			 */
			String day_calendar=pp.getString("day","");
			int daycalendar=Integer.parseInt(day_calendar);
			//String month_name=pp.getString("month","");
			String month_number=pp.getString("mon","");
			int mnumber=Integer.parseInt(month_number);
			String year_calendar=pp.getString("year","");
			context.put("day",day_calendar);
			context.put("month_num",month_number);
			//context.put("month_name",month_name);
			context.put("year",year_calendar);
			/**
                         * Initializing the month name depending on the
                         * month number
                         */
                        String month_name = MultilingualUtil.ConvertedMonth(mnumber, LangFile);
                     /*   switch(mnumber){
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
			 * Generate the date for which event has to be
			 * entered
			 */
			String Cdate=new String();
			if((daycalendar<10) && (mnumber<10))
			{
				Cdate= year_calendar+"0"+month_number+"0"+day_calendar;
			}
			else if((daycalendar<10) && (mnumber>9))
                        {
                                Cdate=year_calendar+month_number+"0"+day_calendar;
                        }
                        else if((daycalendar>9) && (mnumber<10))
                        {
                                Cdate=year_calendar+"0"+month_number+day_calendar;
                        }
			else
			 	Cdate= year_calendar+month_number+day_calendar;
			
		//	Integer date=valueOf(Cdate);
			context.put("date",Cdate);
			int d=Integer.parseInt(Cdate);
			int cD=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
			if(cD <= d)
				context.put("t","true");	
		//	context.put("cDate",ExpiryUtil.getCurrentDate(""));
			Criteria crit=new Criteria();
			/**
			 * Populate the criteria object for selecting
			 * the entries submitted for the specified date
			 * depending on the path variable
			 */
			if(path.equals("personal"))
			{
				try
				{
				crit=new Criteria();
				crit.add(CalInformationPeer.USER_ID,uid_user);
				crit.add(CalInformationPeer.GROUP_ID,gid);
				crit.add(CalInformationPeer.P_DATE,(Object)Cdate,crit.EQUAL);
				crit.addAscendingOrderByColumn(CalInformationPeer.START_TIME);
				}
				catch(Exception e){data.setMessage("The error in caldetail"+e);}
			}
			else
			{
				crit=new Criteria();
				crit.add(CalInformationPeer.GROUP_ID,gid);
				crit.add(CalInformationPeer.P_DATE,(Object)Cdate,crit.EQUAL);
				crit.addAscendingOrderByColumn(CalInformationPeer.START_TIME);
			}
			List cal_detail=CalInformationPeer.doSelect(crit);
			Vector desc=new Vector();
			Vector accessible_events=new Vector();
			Vector st_time=new Vector();
			/**
			 * Retreive the details of the events in seperate vectors. 
			 * Check if the user logged in is a primary instructor. 
			 * This will be used while editing and deleting the events.
			 * Primary Instructor will have the authority to edit and delete
			 * the course specific entries of any users. On the other hand 
			 * the students and secondary instructors can only modify or
			 * delete their own course entries. In personalised calendar the events
			 * of the user logged will be displayed.
			 */

			for(int i=0;i<cal_detail.size();i++)
			{
				CalInformation element=(CalInformation)cal_detail.get(i);
				String startTime=(element.getStartTime()).toString();
				st_time.addElement(startTime);
				byte b[]=element.getDetailInformation();
				String description=new String(b);
				desc.addElement(description);
				String uid=Integer.toString(element.getUserId());
				context.put("uid",uid);
				accessible_events.addElement(uid);
				if(path.equals("fromcourse"))
				{
					String isPrimaryInstructor="false";
					if(cid.endsWith(uName))
					{
						isPrimaryInstructor="true";
					}
					context.put("primary_instructor",isPrimaryInstructor);
				}
			}

			/**
			 * Put all the vectors having the details of the
			 * events in the context
			 */
			context.put("accessible",accessible_events);
			context.put("description",desc);
			context.put("information",cal_detail);
                        context.put("start_time",st_time);
			context.put("size",Integer.toString(cal_detail.size()));
		}
		catch(Exception e){data.setMessage("The error in showing the event of a particular day"+e);}
	}
}

