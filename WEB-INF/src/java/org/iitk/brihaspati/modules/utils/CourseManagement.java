package org.iitk.brihaspati.modules.utils;

/*
 * @(#)CourseManagement.java
 *
 *  Copyright (c) 2004-2006,2009 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.Group;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.services.security.torque.om.TurbineUser;

import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;

/**
 * This utils class have all details of course
 * @author <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 * @author <a href="mailto:satyapal@gmail.com">Satyapal Singh</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */
public class CourseManagement
{
		/**
		 * In this method create a course with a instructor
		 * 
		 * @param groupalias String The CourseId of new course
		 * @param cname String The Course Name of the new course 
		 * @param dept String The department name of the new course	   
		 * @param desc String The description of the new course	   
		 * @param uname String The user name who has be registered in the new course
		 * @param passwd String The user password who has be registered in the new course
		 * @param fname String The user first name who has be registered in the new course
		 * @param lname String The user last name who has be registered in the new course
		 * @param email String The user email-id who has be registered in the new course
		 * @return String
		 * @see StringUtil In this utils check illegal characters
		 * @see UserManagement In this utils manage all details of user
		 * 
		 */
		public static String CreateCourse(String groupalias,String cname,String dept,String desc,String uname,String passwd,String fname,String lname,String email,String serverName,String serverPort,String file) throws Exception
		{
			String message=new String();
			/**
			 * @see StringUtil from Utils
			 */ 
			StringUtil S=new StringUtil();
	                /**
			 * Checks if there are any illegal characters in the values 
			 * entered
			 */
			if(S.checkString(groupalias)==-1 && S.checkString(uname)==-1 && S.checkString(fname)==-1 && S.checkString(lname)==-1)
			{
			/**
		   	  * Concatenate CourseId and UserName
		   	  */
                   	  String newcid=groupalias + uname;
		   	  if(!newcid.equals(""))
			  {
		  	 	/**
				 * Checks if the course with the same instructor 
				 * already exists
				 */
		   	  	boolean doesNotExists=CheckCourseExist(newcid,file);
		   	  	if(doesNotExists==false)
		   	  		{
					
					 message=MultilingualUtil.ConvertedString("c_msg1",file); 
		    	  	}
				else
		    	  	{
			 		try
					{
				  	/**
			   	  	* Create Directory Structure for the new course
			   	  	*/
                           		String coursesRealPath=TurbineServlet.getRealPath("/Courses");
					File f=new File(coursesRealPath+"/"+newcid);
					String message1="";
                           		if(!f.exists())
					{
                           			boolean b=f.mkdirs();
                           			String cd="/"+coursesRealPath+"/"+newcid+"/DisBoard";
                           			File ff=new File(cd);
                           			boolean cc=ff.mkdirs();
                           			File f3=new File(coursesRealPath+"/"+newcid+"/Content");
                           			boolean c3=f3.mkdirs();
					}
					else
					{
						
						message1=MultilingualUtil.ConvertedString("c_msg7",file);
					}
					/**
					* Get the course default quota which is set by administrator
					*/
					String pathq=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                                        String SpacefPrp=AdminProperties.getValue(pathq,"brihaspati.admin.quota.value");
                                        long cquota=new Long(SpacefPrp).longValue();
	
			 			/**
			  	  		* Insert course details in COURSES table
			  	  		*/
						Date date=new Date();
                           			Criteria crit=new Criteria();
                          			crit.add(CoursesPeer.GROUP_NAME,newcid);
                          			crit.add(CoursesPeer.CNAME,cname);
                          			crit.add(CoursesPeer.GROUP_ALIAS,groupalias);
                          			crit.add(CoursesPeer.DEPT,dept);
                          			crit.add(CoursesPeer.DESCRIPTION,desc);
                          			crit.add(CoursesPeer.ACTIVE,"1");
                          			crit.add(CoursesPeer.CREATIONDATE,date);
                          			crit.add(CoursesPeer.LASTMODIFIED,date);
                          			crit.add(CoursesPeer.LASTACCESS,date);
						crit.add(CoursesPeer.QUOTA,cquota);
						CoursesPeer.doInsert(crit);
                         			/**
                           	  		* Create a New Group according to newcid in TURBINE_GROUP
                           	  		*/
                          			Group g=TurbineSecurity.createGroup(newcid);
						/**
				 		* Create a user(Primary Instructor) for the new Course
				 		*/
						
						String message2=UserManagement.CreateUserProfile(uname,passwd,fname,lname,email,newcid,"instructor",serverName,serverPort,file);
						message=message1+message2;
					}
					catch(Exception e)
					{
						message="The error in create a course"+e;
						 
					}
				}
			  }
			}
			else
			{
				 message=MultilingualUtil.ConvertedString("c_msg3",file);
			}
			return(message);
		}
	/**
	 * In this method checking a exist course
	 * @param groupName String 
	 * @param LangFile String 
	 * @return boolean
	 */ 
	public static boolean CheckCourseExist(String groupName,String LangFile)
	{
		boolean checkExists=false;
		try{
		     	  Criteria crit=new Criteria();
                          crit.add(CoursesPeer.GROUP_NAME,groupName);
                   	  List v=CoursesPeer.doSelect(crit);
		   	  checkExists=v.isEmpty();
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in CheckCourseExist() - CourseManagement Utils "+e);
		}
		return(checkExists);
	}
	/**
	 * In this method check a existing course is Active or not
	 * @param groupId Integer 
	 * @return boolean
	 */ 
	public static boolean CheckcourseIsActive(int groupId)
	{
		boolean checkActive=false;
		try
		{
			Criteria crit=new Criteria();
		        String gName=GroupUtil.getGroupName(groupId);
			crit.add(CoursesPeer.GROUP_NAME,gName);
			crit.and(CoursesPeer.ACTIVE,1);
			List check=CoursesPeer.doSelect(crit);
			checkActive=check.isEmpty();
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in CheckcourseIsActive() - CourseManagement Utils "+e);
		}
		return(checkActive);
	}
	/**
	 * In this method,All Details of a existing all courses or specific course
	 * @param groupName String 
	 * @return Vector
	 */ 
	
	public static Vector getCourseNUserDetails(String groupName)
	{
		Vector details=new Vector();
		try
		{
			Criteria crit=new Criteria();
			if(groupName.equals("All"))
			{
				crit.addGroupByColumn(CoursesPeer.GROUP_NAME);
			}
			else
			{
				crit.add(CoursesPeer.GROUP_NAME,groupName);
			}
			List v=CoursesPeer.doSelect(crit);
			for(int i=0;i<v.size();i++)
			{
				
				String GName=((Courses)v.get(i)).getGroupName();
				String courseName=((Courses)v.get(i)).getCname();
				String dept=((Courses)v.get(i)).getDept();
				String gAlias=((Courses)v.get(i)).getGroupAlias();
				String description=((Courses)v.get(i)).getDescription();
				byte active=((Courses)v.get(i)).getActive();
				String act=Byte.toString(active);
				Date CDate=((Courses)v.get(i)).getCreationdate();
				String CrDate=CDate.toString();
				CourseUserDetail cuDetail=new CourseUserDetail();
				
				if(!groupName.equals("All"))
				{
					int index=gAlias.length();
					String loginName=groupName.substring(index);
					int UId=UserUtil.getUID(loginName);
					String uID=Integer.toString(UId);
					List userDetails=UserManagement.getUserDetail(uID);
					TurbineUser element=(TurbineUser)userDetails.get(0);
					String firstName=element.getFirstName().toString();
					String lastName=element.getLastName().toString();
					String email=element.getEmail().toString();
					String userName=firstName+lastName;
					cuDetail.setLoginName(loginName);
					cuDetail.setUserName(userName);
					cuDetail.setEmail(email);
				}
				
				cuDetail.setGroupName(GName);
				cuDetail.setCourseName(courseName);
				cuDetail.setCAlias(gAlias);
				cuDetail.setDept(dept);
				cuDetail.setActive(act);
				cuDetail.setDescription(description);
				cuDetail.setCreateDate(CrDate);
				details.add(cuDetail);
			}
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in getCourseNUserDetails() - CourseManagement Utils "+e);
		}
		return(details);
	}
	/**
	 * In this method,Update all details of a existing course
	 * @param courseName String 
	 * @param dept String 
	 * @param desc String 
	 * @param active String 
	 * @param file String 
	 * @return String
	 */ 
	
	public static String UpdateCourseDetails(String groupName,String courseName,String dept,String desc,String active,String file)
	{
		String message="";
		try
		{
			Date date=new Date();
			Criteria crit=new Criteria();
			crit.add(CoursesPeer.GROUP_NAME,groupName);
			crit.add(CoursesPeer.CNAME,courseName);
			crit.add(CoursesPeer.DEPT,dept);
			crit.add(CoursesPeer.DESCRIPTION,desc);
			crit.add(CoursesPeer.ACTIVE,active);
			crit.add(CoursesPeer.LASTMODIFIED,date);
			CoursesPeer.doUpdate(crit);
			
			String course=MultilingualUtil.ConvertedString("brih_course",file);
                        String c_msg5=MultilingualUtil.ConvertedString("c_msg5",file);
			if (file.endsWith("urd.properties"))
		                message=course+" " +MultilingualUtil.ConvertedString("online_in",file)+" "+ c_msg5 +" " +groupName;
                        else

                        message=course+" " +groupName+" "+ c_msg5;    
		}
		catch(Exception e)
		{
			message="The error in course updating"+e;
			 
		}
		return(message);
	}
	/**
	 * In this method,Remove all details of a course
	 * @param gName String 
	 * @param fromPath String 
	 * @param file String 
	 * @return String
	 */
	
	public static String RemoveCourse(String gName,String fromPath,String file)
	{
		String message="";
		String msg="";
		try{
			int gid=GroupUtil.getGID(gName);
			boolean check_active=CheckcourseIsActive(gid);
			if(check_active!=false || fromPath.equals("admin"))
			{
			   	if(fromPath.equals("ByCourseMgmt")|| fromPath.equals("admin"))
				{
					Vector All_User=UserGroupRoleUtil.getUID(gid);
					if(All_User.size()!=0)
					{
						String userName="";
						for(int i=0;i<All_User.size();i++)
						{
						int uId=Integer.parseInt((String)All_User.get(i));
						userName=UserUtil.getLoginName(uId);
			   			UserManagement umt=new UserManagement();
						msg=umt.removeUserProfile(userName,gName,file);
						}
					}
				}
			/**
                          * Delete the entries of the course from the
                          * related tables
                          */
                          Criteria crit=new Criteria();
                          crit.add(CoursesPeer.GROUP_NAME,gName);
                          CoursesPeer.doDelete(crit);
			/**
                          * Delete the entries of the Notices from the related tables
                          */
                          crit=new Criteria();
                          crit.add(NoticeSendPeer.GROUP_ID,gid);
                          NoticeSendPeer.doDelete(crit);
                          crit=new Criteria();
                          crit.add(NoticeReceivePeer.GROUP_ID,gid);
                          NoticeReceivePeer.doDelete(crit);
			 /**
                          * Delete the entries of the discussion board from the related tables
                          */
                          crit=new Criteria();
			  crit.add(DbSendPeer.GROUP_ID,gid);
                          DbSendPeer.doDelete(crit);
                          crit=new Criteria();
                          crit.add(DbReceivePeer.GROUP_ID,gid);
                          DbReceivePeer.doDelete(crit);
			/**
                          * Delete the entries of the News from the related tables
                          */
                          crit=new Criteria();
			  crit.add(NewsPeer.GROUP_ID,gid);
                          NewsPeer.doDelete(crit);
                          /**
                            * Delete the repository from the server for
                            * this course
                            */
                           String coursesRealPath=TurbineServlet.getRealPath("/Courses");
                           String fileName=coursesRealPath+"/"+gName;
                           File f=new File(fileName);
                           SystemIndependentUtil.deleteFile(f);
			   File f_Index=new File(fileName+"_Index");
                           SystemIndependentUtil.deleteFile(f_Index);

                           	/**
                            	* Remove the group completely from the system
                            	*/
                            	Group g=TurbineSecurity.getGroupByName(gName);
                            	g.remove();
                            //Log.info("system","The Course "+gName+" has been deleted from the address ");
			    //message="Course "+gName+" Successfully deleted";
				String course=MultilingualUtil.ConvertedString("brih_course",file);
                                String c_msg9=MultilingualUtil.ConvertedString("c_msg9",file);
                                message=course+" " +gName+" "+ c_msg9; 
			}
			else
			{
				
				String course=MultilingualUtil.ConvertedString("brih_course",file);
                                String c_msg10=MultilingualUtil.ConvertedString("c_msg10",file);
                                message=course+" " +gName+" "+ c_msg10;
			}

		}
		catch(Exception e)
		{
			message="The error in "+gName+" Course removal "+e;
			
                        
		}
		return(message);
	}
	/**
	  * Check if the user obtained is a primary instructor of 
	  * this course
	  * @param groupName String
	  * @param userName String
	  * @return boolean
	  */
	public static boolean IsPrimaryInstructor(String groupName,String userName)
	{
		boolean checkPrimary=false;
		try
		{
			 checkPrimary=groupName.endsWith(userName);
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in IsPrimaryInstructor() - CourseManagement Utils "+e);
		}
		return(checkPrimary);
	}
	
}
