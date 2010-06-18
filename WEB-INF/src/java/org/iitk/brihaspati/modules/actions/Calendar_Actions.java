package org.iitk.brihaspati.modules.actions;

/*
 * @(#)Calendar_Actions.java	
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

import java.sql.Date;
import java.sql.Time;

import java.util.StringTokenizer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

import org.apache.velocity.context.Context;

import org.apache.torque.util.Criteria;

import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.iitk.brihaspati.om.CalInformationPeer;

 /** This is a single action file for inserting new entries, editing and
 * deleting the existing entries in both personalised or course calendar
 *
 *
 * @author <a href="mailto:singhnk@iitk.ac.in">Nagendra Kumar Singh</a> 
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>  
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class Calendar_Actions extends SecureAction
{     
	
	private static int expiry;
	private static int userid;
	private static String newDate;
	private static String detail_info;
	private static Time stime;
	private static Time etime;
	private static String courseId;
	private static String path;
	private static int gid;

	/**
	 * This method insert the information in the datbase entered by the user from the vm
	 * page for personal as well as course calendar
	 * @param data RunData
	 * @param context Context
	 */

	public void doInsert(RunData data, Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			ParameterParser pp=data.getParameters();
			User user=data.getUser();

			/**
			 * Get the path from the URL specifying if the
			 * user is in personalised or course specific
			 * calendar
			 */

			 path=pp.getString("path");
			/**
			 * Get the course id from the temporary variable
			 * of user when he is in course calendar
			 */

			if(!path.equals("personal"))
			{
				courseId=(String)user.getTemp("course_id");
				gid=GroupUtil.getGID(courseId);
			}

			/**
			 * Get the Deletion Time from the text box in the
			 * vm page
			 */

			expiry=Integer.parseInt(pp.getString("expiry"));
			String UserName=user.getName();

			/**
			 * Get the Information from the text box in the
			 * vm page
			 */

			String event_detail=pp.getString("detail");
			String detail_info=StringUtil.replaceXmlSpecialCharacters(event_detail);
			String day_calendar=pp.getString("day");
			int daycalendar=Integer.parseInt(day_calendar);
                        //String month_name=pp.getString("month");
                        String month_number=pp.getString("mon");
			int mnumber=Integer.parseInt(month_number);
                        String year_calendar=pp.getString("year");
                        context.put("day",day_calendar);
                        context.put("month_num",month_number);
                        //context.put("month_name",month_name);
                        context.put("year",year_calendar);
			/**
			* Get the current date of the system
			* @see ExpiryUtil in utils
			*/
			String cdate=ExpiryUtil.getCurrentDate("");
                        int curdate=Integer.parseInt(cdate);
 
                        /**
                         * Generate the date for which event has to be
                         * entered
                         */
			String edate=new String();
			if((daycalendar<10) && (mnumber<10))
			{
				edate=year_calendar+"0"+month_number+"0"+day_calendar;  	
			}
			else if((daycalendar<10) && (mnumber>9))
			{
				edate=year_calendar+month_number+"0"+day_calendar;
			}
			else if((daycalendar>9) && (mnumber<10))
			{
				edate=year_calendar+"0"+month_number+day_calendar;
			}
			else
				edate=year_calendar+month_number+day_calendar;
			int edate1=Integer.parseInt(edate);
			/**
			* date convert String type to Date type
			*/
                        String date= year_calendar+"-"+month_number+"-"+day_calendar;
                        Date Cal_date=Date.valueOf(date);
                        context.put("date",Cal_date);
			String exd=ExpiryUtil.getExpired(date,expiry);
			Date Expdate=Date.valueOf(exd);
			/**
			 * Get the hours and minutes for the start time
			 * and hence concatenate them to get start time
			 */

			String sh=pp.getString("Start_hr");  
			String sm=pp.getString("Start_min");  
			String sTime= sh + ":" + sm+":00";
			/**
			 * Get the hours and minutes for the end time
			 * and hence concatenate them to get end time
			 */

			String eh=pp.getString("Last_hr");  
			String em=pp.getString("Last_min");  
			String eTime= eh + ":"  + em+":00";
			etime=Time.valueOf(eTime);
			context.put("etime",eTime);	
			userid=UserUtil.getUID(UserName);
			String msg=new String();
			if(edate1 >= curdate)
			{
				Criteria crit=new Criteria();
				crit.add(CalInformationPeer.USER_ID,userid);
				crit.add(CalInformationPeer.P_DATE,Cal_date);
				crit.add(CalInformationPeer.DETAIL_INFORMATION,detail_info);
				crit.add(CalInformationPeer.START_TIME,Time.valueOf(sTime));
				crit.add(CalInformationPeer.END_TIME,etime);
				crit.add(CalInformationPeer.EXPIRY,expiry);
				crit.add(CalInformationPeer.EXPIRY_DATE,Expdate);
				if(path.equals("personal"))
				{
					crit.add(CalInformationPeer.GROUP_ID,1);
				}
				else
				{
					crit.add(CalInformationPeer.GROUP_ID,gid);
				}
				CalInformationPeer.doInsert(crit);
				msg=MultilingualUtil.ConvertedString("cal_ins",LangFile);
				data.setMessage(msg);
			}
			else
			{
				msg=MultilingualUtil.ConvertedString("news_msg2",LangFile);
                                data.setMessage(msg);
			}
		}
		catch(Exception e)
		{
			data.setMessage("Problem in inserting the event "+e+" !!");
		}
	}

	/**
	 * Update the event details in the database for personal as well
	 * as course calendar
	 * @param data RunData
	 * @param context Context
	 */

	public void doUpdate(RunData data, Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			ParameterParser pp=data.getParameters();
			expiry=Integer.parseInt(pp.getString("expiry"));
			String event_detail=pp.getString("detail");
			String detail_info=StringUtil.replaceXmlSpecialCharacters(event_detail);
			String sh=pp.getString("Start_hr");
                        String sm=pp.getString("Start_min");
                        String sTime= sh + ":" + sm+":00";
                        stime=Time.valueOf(sTime);
                        context.put("stime",sTime);
 			String eh=pp.getString("Last_hr");
                        String em=pp.getString("Last_min");
                        String eTime= eh + ":"  + em+":00";
                        etime=Time.valueOf(eTime);
                        context.put("etime",eTime);
			String info_id=data.getParameters().getString("info_id");
			Criteria crit=new Criteria();
			crit.add(CalInformationPeer.DETAIL_INFORMATION,detail_info);
			crit.add(CalInformationPeer.START_TIME,stime);
			crit.add(CalInformationPeer.END_TIME,etime);
			crit.add(CalInformationPeer.EXPIRY,expiry);
			crit.add(CalInformationPeer.INFO_ID,info_id);
			CalInformationPeer.doUpdate(crit);
			String msg=MultilingualUtil.ConvertedString("c_msg5",LangFile);
                        data.setMessage(msg);
		}
		catch(Exception e)
		{
			data.setMessage("Problem in Update method "+e+" !!");
		}
	}

	/**
	 * Delete the event submitted by the user 
	 * @param data RunData
	 * @param context Context
	 */
	
	public void doDelete(RunData data,Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			String desc_list=data.getParameters().getString("deleteFileNames","");
			if(!desc_list.equals(""))
			{
				StringTokenizer st=new StringTokenizer(desc_list,"^");
				for(int i=0;st.hasMoreTokens();i++)
				{
					String infoId=st.nextToken();
					Criteria crit=new Criteria();
					crit.add(CalInformationPeer.INFO_ID,infoId);
					CalInformationPeer.doDelete(crit);
					String msg=MultilingualUtil.ConvertedString("c_msg9",LangFile);
                                       	data.setMessage(msg);
				}
			}
		}
		catch(Exception e)
		{
			data.setMessage("The event could not be deleted due to exception :- "+e);
		}
	}
	

	/**
	 * Default action to perform if the specified action is not
	 * found.
	 * @param data RunData
	 * @param context Context
	 */

	public void doPerform( RunData data,Context context ) throws Exception
	{
		String LangFile=data.getUser().getTemp("LangFile").toString();  
		String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doInsert"))
		{
			doInsert(data,context);
		}
		else if(action.equals("eventSubmit_doUpdate"))
		{
			doUpdate(data,context);
		}
		else if(action.equals("eventSubmit_doDelete"))
		{
			doDelete(data,context);
		}
		else
		{
			data.setMessage(msg);
		}
	}
}
