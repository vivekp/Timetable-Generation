package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)CourseHome.java	
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

import java.io.File;
import java.util.Vector;
import java.util.List;
import java.util.Date;
//import java.util.Calendar;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.NoticeUnreadMsg;
import org.iitk.brihaspati.modules.utils.NewsHeadlinesUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.CalInformation;
import org.iitk.brihaspati.om.SurveyQuestionPeer;
import org.iitk.brihaspati.om.SurveyStudentPeer;
import org.iitk.brihaspati.om.SurveyResultPeer;
import org.iitk.brihaspati.om.SurveyQuestion;

/**
 * This Class manage all functionality of Course
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
 * @author <a href="mailto:nagendrakumarpal@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 */

public class CourseHome extends SecureScreen{
	public void doBuildTemplate( RunData data, Context context ) 
	{
		try{
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
			/**
			  * Get the Guest user name and put in context
			  */

			if(user.getName().equals("guest")){
                                context.put("guest_login","true");
                        }
                        else{
                                context.put("guest_login","false");
                        }


			/**
			 * Retrieves the COURSE_ID of the course in which user has entered
			 */

			String courseid=pp.getString("courseid","");
			String userInCourse=(String)user.getTemp("course_id");
			/** Check course id for null
			*/
			if( userInCourse!=null && !userInCourse.equals("") && courseid.equals(""))
				courseid=userInCourse;
			/**
			 * Gathers Course Name and User Name 
			 */
			String C_Name=CourseUtil.getCourseName(courseid);
 			String username=user.getName();
			int userid=UserUtil.getUID(username);
			/**
			 * Sets all the temporary variables of the session 
			 */
			
			String Role=(String)user.getTemp("role");
			context.put("user_role",Role);
			int role_id=0;
			if(Role.equals("instructor"))
				role_id=2;
			else if(Role.equals("student"))
				role_id=3;
			if(!courseid.equals("")&& !courseid.equals(userInCourse))
			{
				user.setTemp("course_name",C_Name);
				user.setTemp("course_id",courseid);
			/**
			* For setting the course header
			*/
				File f=new File(TurbineServlet.getRealPath("/images")+"/Header/"+courseid);
				boolean istat=f.exists();
				user.setTemp("istat",istat);
			}
			/**
                        *Code for Survey Visible to students
                        */
	                        Criteria cr=new Criteria();
                        	cr.add(SurveyQuestionPeer.CID,courseid);
                	        List list=SurveyQuestionPeer.doSelect(cr);
        	                context.put("size",list.size());
	                        cr=new Criteria();
                        	cr.add(SurveyStudentPeer.CID,courseid);
                	        //cr.add(SurveyStudentPeer.STU_ID,uid);
                	        cr.add(SurveyStudentPeer.STU_ID,userid);
        	                List list1=SurveyStudentPeer.doSelect(cr);
	                        context.put("size1",list1.size());
			/**	
			* Count the unread notices from database for particuler course 
			* which user logged in
			*/
			if(role_id!=0)
			{
				String groupName=(String)user.getTemp("course_id");
				context.put("groupName",groupName);
				Vector unreadMsg=NoticeUnreadMsg.getUnreadNotice(userid,role_id,groupName);
				context.put("unreadMsg",unreadMsg);
			}
			context.put("cName",(String)user.getTemp("course_name"));
			/**	
			* Latest Course news from database for particuler course 
			* which user logged in
			*/
			int gid=GroupUtil.getGID((String)user.getTemp("course_id"));
			Vector newsd=NewsHeadlinesUtil.getNews(gid);
                	context.put("sample",newsd);
                	if(newsd.size()!=0)
                	{
				Vector split_news=ListManagement.listDivide(newsd,0,7);
                        	context.put("detail",split_news);
                        	context.put("status","Notempty");
                	}
                	else
                	{
                        	context.put("status","empty");
                	}

			 /**
                        * Code for "Today's Events"
                         * Get the current date
                         */

                        String Cdate=ExpiryUtil.getCurrentDate("-");
                        /**
                        * Retreive the current time from System date
                        * and put in the context for use in templates
                        */
		/*	int hr=Calendar.get(Calendar.HOUR_OF_DAY);
			int mu=Calendar.get(Calendar.MINUTE);
		*/
                        String time=(new Date()).toString();
                        String ch=time.substring(11,13);
                        String cm=time.substring(14,16);
		
                        //String ch=valueOf(Calendar.get(Calendar.HOUR_OF_DAY));
                        //String cm=(Calendar.get(Calendar.MINUTE)).toString();
                        String currtime=ch+cm;
                        context.put("currenttime",currtime);
                        /**
                        * Sending object Integer to context
                        */
                        Integer ii = new Integer(1000);
                        context.put("INT",ii);
                        /**
                         * Retreive the events from the database based on today's date for the
                         * specific user
                         */
                        Criteria criteria=new Criteria();
			try{//inner try
                        criteria.add(CalInformationPeer.GROUP_ID,gid);
                        criteria.add(CalInformationPeer.P_DATE,(Object)Cdate,criteria.EQUAL);
                        criteria.addAscendingOrderByColumn(CalInformationPeer.START_TIME);
                        List Cdetail=CalInformationPeer.doSelect(criteria);
                        Vector u=new Vector();
                        Vector Stime=new Vector();
                        Vector Etime=new Vector();
                        for(int i=0;i<Cdetail.size();i++)
			{
                                CalInformation element=(CalInformation)Cdetail.get(i);
                                String stime=(element.getStartTime()).toString();
                                String sh=stime.substring(0,2);
                                String sm=stime.substring(3,5);
                                String btime=sh+sm;
                                Stime.addElement(btime);
                                byte b[]=element.getDetailInformation();
                                String description=new String(b);
                                String DI=new String();
                                StringBuffer sb1=new StringBuffer(stime);
                                sb1.delete(5,8);
                                String etime=(element.getEndTime()).toString();
                                String eh=etime.substring(0,2);
                                String em=etime.substring(3,5);
                                String stoptime=eh+em;
                                Etime.addElement(stoptime);
                                StringBuffer sb2=new StringBuffer(etime);
                                sb2.delete(5,8);
                                DI=sb1 + "-" + sb2 + " " + description;
                                u.addElement(DI);
                        }
                        if(u.size()!=0)
                        {
                                context.put("information",u);
                                context.put("Stime",Stime);
                                context.put("Etime",Etime);
                        }
			}//inner try
			catch(Exception e)
        	        {
	                        data.setMessage("The error in Course Home page no1 :-"+e);
                	}//inner catch

			/**
			 * Update the courses table for last access
			 * Last access used for automatic Course Expiry
			 */
			criteria=new Criteria();
			criteria.add(CoursesPeer.GROUP_NAME,courseid);
			criteria.add(CoursesPeer.LASTACCESS,new Date());
			CoursesPeer.doUpdate(criteria);

			/**
                         * Code for Survey result
                         */
                        Criteria cri=new Criteria();
                        cri.add(SurveyResultPeer.CID,courseid);
                        List lst=SurveyResultPeer.doSelect(cri);
                        context.put("ldetail",lst);

		}
		catch(Exception e)
		{
			data.addMessage("The error in Course Home page :-"+e);
		}
	}
}

