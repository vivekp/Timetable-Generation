package org.iitk.brihaspati.modules.utils;


/*@(#)ExpiryUtil.java
 *  Copyright (c) 2004-2006 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */

import java.util.Calendar;
import java.util.Vector;
import java.util.List;
import java.sql.Date;
import java.util.StringTokenizer;
import java.io.File;

import org.apache.torque.util.Criteria;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;

import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.iitk.brihaspati.om.TaskPeer;
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.CoursesPeer;

/**
 * This class gets current date from system, expiry date,data expired from database 
 * and get year,month and day are seperated and put in a
 * vector as seperate elements from date
 * @author <a href=madhavi_mungole@hotmail.com>Madhavi Mungole</a>
 * @author <a href=awadhesh_trivedi@yahoo.co.in>Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class ExpiryUtil{
	/**
	 * This method retreives the current date of the system with delimiter
	 * in yyyymmdd or yyyy-mm-dd etc format
	 * @param delimiter String delimitertype (-,/,. etc)
	 * @return String 
	 */
	public static String getCurrentDate(String delimiter)
	{
		String cdate="";
		try{
			Calendar calendar=Calendar.getInstance();

			int curr_day=calendar.get(calendar.DATE);
			int curr_month=calendar.get(calendar.MONTH)+1;
			int curr_year=calendar.get(calendar.YEAR);
			String current_day=Integer.toString(curr_day);
			String current_month=Integer.toString(curr_month);
			String current_year=Integer.toString(curr_year);
			if(curr_month<10)
				current_month="0"+current_month;
			if(curr_day<10)
				current_day="0"+current_day;
			if(!delimiter.equals(""))
				cdate=current_year+delimiter+current_month+delimiter+current_day;
			else
				cdate=current_year+current_month+current_day;
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in getCurrentDate() - ExpiryUtil class !!"+ex);
		}
		return(cdate);
	}
	/**
         * This method gets the  date
         * @param date String Contains the  date and time if present
         * @return String without time and hyphen like(20051121)
         */
	public static String getDate(String date){
                /**
                 * Retreives the date and time (if present) as seperate
                 * elements in a vector
                 */

                StringTokenizer date1=new StringTokenizer(date," ");
                Vector v=new Vector();

                while(date1.hasMoreTokens()){
                        v.addElement(date1.nextToken());
                }
		/**
                 * If time is present then it is deleted and only the
                 * date is obtained
                 */

                int tokens=v.size();
                if(tokens > 1){
                        for(int i=1;i<tokens;i++){
                                v.removeElementAt(i);
                        }
                }

                /**
                 * The year, month and day are seperated and put in a
                 * vector as seperate elements
                 */

                date1=new StringTokenizer((String)v.elementAt(0),"-");
                String dt=new String();
                //while(date1.hasMoreTokens()){
                        String y=date1.nextToken();
                        String m=date1.nextToken();
                        String d=date1.nextToken();
                //}
		dt=y+m+d;
                return(dt);
        }


	/**
	 * This method gets the year, month and day are seperated and put in a
         * vector as seperate elements
	 * @param date String Contains the posting date and time if present
	 * date format yyyy-mm-dd
	 * @return Vector
	 */
	public static Vector getPostDate(String date)
	{
		Vector v=new Vector();
		try
		{
			/**
		 	* Retreives the date and time (if present) as seperate
		 	* elements in a vector
		 	*/
			StringTokenizer date1=new StringTokenizer(date," ");
			while(date1.hasMoreTokens())
			{
				v.addElement(date1.nextToken());
			}
			/**
		 	* If time is present then it is deleted and only the
		 	* date is obtained
		 	*/
			int tokens=v.size();
			if(tokens > 1)
			{
				for(int i=1;i<tokens;i++)
				{
					v.removeElementAt(i);
				}
			}
			/**
		 	* The year, month and day are seperated and put in a
		 	* vector as seperate elements
		 	*/
			date1=new StringTokenizer((String)v.elementAt(0),"-");
			v=new Vector();
			while(date1.hasMoreTokens())
			{
				v.addElement(date1.nextToken());
			}
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in getPostDate() - ExpiryUtil class !!"+ex);
		}

		return(v);
	}

	/**
	 * This method gets expiry date in yyyy-mm-dd format 
	 * @param Postdate String Posting dates
	 * @param expiry int The number of expiry days
	 * @return String
	 */
	public static String getExpired(String Postdate, int expiry)
	{
		String expiry_date=new String();
		try
		{	
			/**
			 * Get the year, month and day from the posting
			 * date
			 */
			Vector p_date=getPostDate(Postdate);
			String year=(String)p_date.elementAt(0);
			String month=(String)p_date.elementAt(1);
			String day=(String)p_date.elementAt(2);
			int post_year=Integer.parseInt(year);
			int post_month=Integer.parseInt(month);
			int post_day=Integer.parseInt(day);
			/**
			 * Calculate the probable date for the entry
			 * after adding the number of days of validity
			 * to the posting date
			 */
			
			while(expiry>0){
				int feb=0;
				int days=0;
				if(post_month==2){
					if((post_year%4==0 && post_year%100!=0)||(post_year%400==0))
						feb=29;
					else
						feb=28;
					days=feb-post_day;
					if(expiry>days){
						expiry=expiry-days;
						post_month+=1;
						post_day=0;
					}
					else{
						post_day=post_day+expiry;
						expiry=expiry-days;
					}
				}
				else if(post_month==1 || post_month==3 || post_month==5 || post_month==7 || post_month==8 || post_month==10 || post_month==12){
					days=31-post_day;
					if(expiry>days){
						expiry=expiry-days;
						if(post_month<12){
							post_month+=1;
							post_day=0;
						}
						else{
							post_year+=1;
							post_month=1;
							post_day=0;
						}
					}
					else{
						post_day=post_day+expiry;
						expiry=0;
					}
				}
				else{
					days=30-post_day;
					if(expiry>days){
						expiry=expiry-days;
						if(post_month<12){
							post_month+=1;
							post_day=0;
						}
						else{
							post_year+=1;
							post_month=1;
							post_day=0;
						}
					}
					else{
						post_day=post_day+expiry;
						expiry=0;
					}
				}
			} //end of 'while' loop
			
			year=Integer.toString(post_year);
			month=Integer.toString(post_month);
			if(post_month<10)
				month="0"+month;
			
			day=Integer.toString(post_day);
			if(post_day<10)
				day="0"+day;
			
			expiry_date=year+"-"+month+"-"+day;
		//	if(expiry < 0)
		//	{
		//		expiry_date="infinite";
		//	}
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in getExpired() - ExpiryUtil class !!"+ex);
		}
		return(expiry_date);
	}
	 /**
         * This method used for removing the course 
	 * if it is not accessed a certain duration 
	 * which is configured by admin
         */

	public static void CrsExpiry ()
	{
		try{
		List crslist=ListManagement.getCourseList();
		String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
		String crsExpday=AdminProperties.getValue(path,"brihaspati.admin.courseExpiry");
		int cex=Integer.parseInt(crsExpday);
		int Cdate=Integer.parseInt(getCurrentDate(""));
			for(int i=0; i<= crslist.size(); i++)
			{
				//get the course
				String crs=((Courses)crslist.get(i)).getGroupName();
				//get last access date to that course
				String lad=(((Courses)crslist.get(i)).getLastaccess()).toString();
				//get expiry date after given days from util method
				String expdate=getExpired(lad,cex);
				int edate=Integer.parseInt(expdate.replace("-",""));
				//check current date is greater than expiry date
				if(edate < Cdate){
					//delete all course related areas
					String file=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_en.properties");
						//file is multilingual file
					CourseManagement.RemoveCourse(crs,"admin",file);
					//(new File(TurbineServlet.getRealPath("/Courses/")+crs)).delete();
					//(new File(TurbineServlet.getRealPath("/Courses/")+crs+"_Index")).delete();
					(new File(TurbineServlet.getRealPath("/images")+"/Header/"+crs)).delete();
					(new File(TurbineServlet.getRealPath("/WIKI/")+crs)).delete();
					//delete entry from database
					Criteria crit=new Criteria();
					crit.add(CoursesPeer.GROUP_NAME,crs);
					CoursesPeer.doDelete(crit);
				}
			}
		}
		catch (Exception e){ErrorDumpUtil.ErrorLog( "The error in Course Expiry" + e);}
	}
	/**
         * In this method, all those entries which has to be expired as they are deleted
         * @return boolean
         */
        public static boolean Expiry()
	{
		boolean success=false;
		try{
				//here add survey and remote course
			String str[]={"CalInfo","DisBoard","News","Notices","Task"};
			Criteria crit=new Criteria();
			String current_date=getCurrentDate("-");
			List v=null;
			for(int i=0;i<str.length;i++)
			{
				if(str[i].equals("CalInfo"))
				{
					crit=new Criteria();
					crit.add(CalInformationPeer.EXPIRY_DATE,(Object)current_date,crit.LESS_EQUAL);
					CalInformationPeer.doDelete(crit);
				}
				else if(str[i].equals("DisBoard"))
				{
					crit=new Criteria();
					crit.add(DbSendPeer.EXPIRY_DATE,(Object)current_date,crit.LESS_EQUAL);
					v=DbSendPeer.doSelect(crit);
					/**
					* Delete Disscussion message from Send Table
					*/
					DbSendPeer.doDelete(crit);
					/**
					* Delete Disscussion message from Receive Table
					*/
					for(int j=0;j<v.size();j++)
					{
						DbSend element=(DbSend)v.get(j);
                                                int db_id=element.getMsgId();
						crit=new Criteria();
						crit.add(DbReceivePeer.MSG_ID,db_id);
						DbReceivePeer.doDelete(crit);
					}
					v=null;
				}
				else if(str[i].equals("News"))
				{
					crit=new Criteria();
					crit.add(NewsPeer.EXPIRY_DATE,(Object)current_date,crit.LESS_EQUAL);
					NewsPeer.doDelete(crit);
				}
				else if(str[i].equals("Notices"))
				{
					crit=new Criteria();
					crit.add(NoticeSendPeer.EXPIRY_DATE,(Object)current_date,crit.LESS_EQUAL);
					v=NoticeSendPeer.doSelect(crit);
					/**
					* Delete Notice from Send Table
					*/
					NoticeSendPeer.doDelete(crit);
					/**
					* Delete Notice from Receive Table
					*/
					for(int j=0;j<v.size();j++)
					{
						NoticeSend element=(NoticeSend)v.get(j);
                                                int notice_id=element.getNoticeId();
						crit=new Criteria();
						crit.add(NoticeReceivePeer.NOTICE_ID,notice_id);
						NoticeReceivePeer.doDelete(crit);
					}
					v=null;
				}
				else if(str[i].equals("Task"))
				{
					crit=new Criteria();
					crit.add(TaskPeer.EXPIRY_DATE,(Object)current_date,crit.LESS_EQUAL);
					TaskPeer.doDelete(crit);
				}
			}
	 
			success=true;
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in Expiry() - ExpiryUtil) !!"+ex);
			success=false;
		}
		return success;
	}
}//end of class
