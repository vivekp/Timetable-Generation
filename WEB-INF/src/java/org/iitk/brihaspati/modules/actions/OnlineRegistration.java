
package org.iitk.brihaspati.modules.actions;

/**
 * @(#)OnlineRegistration.java	
 *  
 *  Copyright (c) 2008, 2009 ETRG,IIT Kanpur. 
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
 */

import java.io.File;
import java.util.Vector;
import java.util.List;
import java.sql.Date;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.modules.actions.VelocitySecureAction;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;


/**
 * This class is responsible for adding a new user in specified group and 
 * assigned a role to the system. 
 * 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:omprakash_kgp@yahoo.co.in">Om Prakash</a>
 * @author <a href="mailto:shaistashekh@hotmail.com">Shaista</a>
 * @modify 20-03-09
 */


public class OnlineRegistration extends VelocitySecureAction
{

	private String LangFile="";
	private	String server_name= "";
        private String srvrPort= "";
	private String emailId = "";
	private String MsgForExpireTime = "";
	private String status = "", lang="";
	private long longCreationDate;
	private long longCurrentDate;
	private long noOfdays;
	private Vector vc = new Vector();
	private String  orgtn="",curDate="", path="";
        private String uname="", gname="", email="", fname="", lname="", passwd="";

        protected boolean isAuthorized( RunData data ) throws Exception
        {
                return true;
        }

/**
 *
 * Method for registered a user as Secondary Instructor,Student
 * and Content Author
 * @param data RunData instance
 * @param context Context instance
 *
 */
	public void UserRegister(RunData data, Context context) throws Exception
	{
		Vector indexList= new Vector();
		StringUtil S=new StringUtil();
		int group_id =0;
			
		ParameterParser pp=data.getParameters();
		
		/**
                 * Retreiving details entered by the user
                 */
                XmlWriter xmlWriter=null;
		uname=pp.getString("UNAME");
                passwd=pp.getString("PASSWD");
                if(passwd.equals(""))
                        passwd=uname;
		fname=pp.getString("FNAME","");
		lname=pp.getString("LNAME","");
		orgtn=pp.getString("ORGTN","");
                email=pp.getString("EMAIL");
		gname=pp.getString("group","");
                String roleName=pp.getString("role","");
		curDate = Integer.toString(Integer.parseInt(ExpiryUtil.getCurrentDate("")));
                if(gname.equals(""))
                {
                        gname=pp.getString("group_author");
                }
                if(roleName.equals(""))
                {
                        roleName=pp.getString("role_author");
                }

                path=data.getServletContext().getRealPath("/OnlineUsers");
                File filepath=new File(path);
                if(!filepath.exists())
                {
                        filepath.mkdirs();
                }
                File file=new File(path+"/OnlineUser.xml");

                if(!file.exists())
                {
			TopicMetaDataXmlWriter.WriteOnlineReqRootOnly(file.getAbsolutePath());
                        xmlWriter=new XmlWriter(path+"/OnlineUser.xml");			
		}
		TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(path+"/OnlineUser.xml");
                Vector userlist = topicmetadata.getOnlineUserDetails();
		lang=pp.getString("lang","english");
        	context.put("lang",lang);
		context.put("status","UserResitration");
		LangFile=MultilingualUtil.LanguageSelection(lang);
		if(S.checkString(uname)==-1 && S.checkString(fname)==-1 && S.checkString(lname)==-1 && S.checkString(gname)==-1 && S.checkString(orgtn)==-1)
		{ //if 1
			boolean userExists = true;
			userExists=UserManagement.checkUserExist(uname);
			boolean RoleExist= true;
			if(userExists == false)
			{
				int user_id=UserUtil.getUID(uname);
                                group_id=GroupUtil.getGID(gname);
                          	RoleExist=UserManagement.checkRoleinCourse(user_id,group_id);
			}
                        /**
                         *Checks if existing user already has some
                         * role in the group if yes then the specified
                         * message is given else the user is assigned
                         * the role in specified group
                         */
			if(userExists == false && RoleExist==false)
			{ //if 2
                        	for(int j=0; j<10; j++)
	                        {
					userExists=UserManagement.checkUserExist(uname +getNext());
					if(userExists == true)
					 	vc.addElement(uname+getNext()+"\n");
				}
				data.addMessage(MultilingualUtil.ConvertedString("u_msg",LangFile));
                        	data.addMessage(MultilingualUtil.ConvertedString("online_msg10",LangFile) +vc.toString());
                                setTemplate(data,"OnlineRegistration.vm");
				return;
			} //if 2					
			else
			{ //else 2
				if(userlist != null) //if 3
				{ 
					int i=0,j=0;
	                                String gName = "";
        	                        String uName = "";
                	                boolean flag= false;
                                	vc = new Vector();
        	                        for( i=0; i <userlist.size(); i++)
                	                {
                        	                gName =((CourseUserDetail)userlist.get(i)).getGroupName();
                                	        uName=((CourseUserDetail)userlist.get(i)).getLoginName();
	                                        if(gName.equals(gname) && uName.equals(uname))
        	                                {
                	                                for(j=0; j<10; j++)
                        	                        {
                                	                        /**
                                        	                 * Getting Random Group name if Given Group Name with same User Name is already  exist in xml
                                                	         * See OnlineReg/courses.xml file
                                                        	 */
	                                                        vc.addElement(uname+getNext()+"\n");
                	                                        flag = true;
                        	                        }
                                	        }
                                        	if(flag == true)
	                                        break;
        	                        }
					if(vc != null && vc.size() > 0 ) //if 4
	                                {
        	                                data.addMessage(MultilingualUtil.ConvertedString("u_msg9",LangFile));
						//data.addMessage(vc.toString());
                        			data.addMessage(MultilingualUtil.ConvertedString("online_msg10",LangFile) +vc.toString());
                                	        setTemplate(data,"OnlineRegistration.vm");
                                        	return;

	                                } //if 4
	                                else  //else 4
        	                        {
						server_name= TurbineServlet.getServerName();
	        	                	srvrPort= TurbineServlet.getServerPort();
						MsgForExpireTime = "Your Request for user "; 
						indexList = sendMail_MoreThanSevenDays(userlist, MsgForExpireTime, uname, server_name, srvrPort, LangFile);
	/**					
						String subMsgForExpireTime =" registration is expired. Please talk to the administrator personally";
						for( i=0; i <userlist.size(); i++)
						{
							cdate=((CourseUserDetail)userlist.get(i)).getCreateDate();
							creation_date = cdate.substring(0,4)+"-"+cdate.substring(4,6)+"-"+cdate.substring(6,8);
							java.util.Date currentDate= new java.util.Date();
                				        Creation_date=Date.valueOf(creation_date);
	                		        	longCreationDate= Creation_date.getTime();
	        	        		        longCurrentDate= currentDate.getTime();
	        	        	        	noOfdays=(longCurrentDate-longCreationDate)/(24*3600*1000)+1;
							if(noOfdays > 7 && (longCurrentDate-longCreationDate)!=0)
							{
								//String gname=((CourseUserDetail)userlist.elementAt(i)).getGroupName();
								emailId = ((CourseUserDetail)userlist.get(i)).getEmail();
								Mail_msg=MailNotification.sendMail(MsgForExpireTime+gName+subMsgForExpireTime,emailId,"","Updation Mail","","","",server_name,srvrPort,LangFile);
								indexList.add(i);
							}
						}
**/
						xmlWriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(path,"/OnlineUser.xml",indexList);
				                TopicMetaDataXmlWriter.appendOnlineUserElement(xmlWriter,uname,passwd,fname,lname,orgtn,email,gname,roleName,curDate);
        				        xmlWriter.writeXmlFile();
						sendMailToApproval(gname,LangFile);
			        	} //else 4
				} //if 3

				else //else 3
				{
					indexList.add(-1);
	                		xmlWriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(path,"/OnlineUser.xml",indexList);
		        	        TopicMetaDataXmlWriter.appendOnlineUserElement(xmlWriter,uname,passwd,fname,lname,orgtn,email,gname,roleName,curDate);
        		        	xmlWriter.writeXmlFile();
					sendMailToApproval(gname,LangFile);
				} //else 3
				String str=MultilingualUtil.ConvertedString("online_msg5",LangFile);
        		        data.setMessage(str);
			} // else 2
		} //if 1
		else //else 1
                {
			data.addMessage(MultilingualUtil.ConvertedString("c_msg3",LangFile));
			setTemplate(data,"OnlineRegistration.vm");
                        return;
                } //else 1

	} //method

	/**
	 * This method actually registers a new course along with the instructor in the system
 	 */
        public void CourseRegister(RunData data, Context context) throws Exception
        {
 
		boolean doesNotExists = true;
		//Vector vc = new Vector();
                ParameterParser pp=data.getParameters();
                /**
                 * store details from the page where user has entered them
                 */
                String gname=pp.getString("COURSEID","").toUpperCase();
                String cname=pp.getString("CNAME","");
                String dept=pp.getString("DEPT","");
                String uname=pp.getString("UNAME");
                String passwd=pp.getString("PASS");
                if(passwd.equals(""))
                        passwd=uname;
                String fname=pp.getString("FNAME","");
                String lname=pp.getString("LNAME","");
		String orgtn=pp.getString("ORGTN","");
                String email=pp.getString("EMAIL","");
		String curDate = Integer.toString(Integer.parseInt(ExpiryUtil.getCurrentDate("")));
                String path=TurbineServlet.getRealPath("/OnlineUsers");
                File filepath=new File(path);
                XmlWriter xmlWriter=null;
                if(!filepath.exists())
                {
                        filepath.mkdirs();
                }
                File file=new File(path+"/courses.xml");
                if(!file.exists())
                {
                        TopicMetaDataXmlWriter.WriteOnlineReqRootOnly(file.getAbsolutePath());
                        xmlWriter=new XmlWriter(path+"/courses.xml");
                }

		TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(path+"/courses.xml");
                Vector courselist = topicmetadata.getOnlineCourseDetails();
		Vector indexList = new Vector();
		CourseManagement courseMgmt = new  CourseManagement();
                lang=pp.getString("lang","english");
                LangFile=MultilingualUtil.LanguageSelection(lang);
		context.put("status","CourseRegistration");

		/**
		 * Getting Random Group name if Given Group Name for same User already  exist in database
		 * @param vc is vector having list of Random Group name 
		 */	

		doesNotExists = courseMgmt.CheckCourseExist(gname+uname,LangFile);
                if(doesNotExists==false)
                {
		 	/** Again checking Random Group name if Given Group Name for same User in database **/

                        for(int j=0; j<10; j++)
                        {
                                //getNext();
			doesNotExists = courseMgmt.CheckCourseExist(gname+getNext()+uname,LangFile);
                	if(doesNotExists==true)
			
                                vc.addElement(gname+getNext()+"\n");
                        }
                        data.addMessage(MultilingualUtil.ConvertedString("c_msg1",LangFile));
                        //data.addMessage(vc.toString());
       			data.addMessage(MultilingualUtil.ConvertedString("online_msg9",LangFile) +vc.toString());
                        setTemplate(data,"OnlineRegistration.vm");
                        return;
		}
                else 
		{
			vc = new Vector();
			if(courselist != null)
			{
				int i=0,j=0;
				String gName = "";
				String uName = "";
				boolean flag= false;
				Vector randomGroupNameVect = new Vector();
				for( i=0; i <courselist.size(); i++)
				{
					gName =((CourseUserDetail)courselist.get(i)).getGroupName();
					uName=((CourseUserDetail)courselist.get(i)).getLoginName();
					if(gName.equals(gname) && uName.equals(uname))
					{
                        			for(j=0; j<10; j++)
			                	{
							/**
							 * Getting Random Group name if Given Group Name with same User Name is already  exist in xml
							 * See OnlineReg/courses.xml file
							 */	
                	        	        	vc.addElement(gname+getNext()+"\n");
		        	                }
						flag = true;
					}
					if(flag == true)
					break;
				}
				if(vc != null && vc.size() > 0)
				{
					data.addMessage(MultilingualUtil.ConvertedString("c_msg1",LangFile));
		       			data.addMessage(MultilingualUtil.ConvertedString("online_msg9",LangFile) +vc.toString());
			                setTemplate(data,"OnlineRegistration.vm");
                			return;
					
				}
				else //inner
				{
					MsgForExpireTime = "Your Request for course ";
					server_name= TurbineServlet.getServerName();
        	                	srvrPort= TurbineServlet.getServerPort();
					indexList = sendMail_MoreThanSevenDays(courselist, MsgForExpireTime, gName, server_name, srvrPort, LangFile);
/**
					String subMsgForExpireTime=" registration is expired. Please talk to the administrator personally";
					for( i=0; i <courselist.size(); i++)
					{
						server_name= TurbineServlet.getServerName();
	        	                	srvrPort= TurbineServlet.getServerPort();
						cdate=((CourseUserDetail)courselist.get(i)).getCreateDate();
						creation_date = cdate.substring(0,4)+"-"+cdate.substring(4,6)+"-"+cdate.substring(6,8);
						java.util.Date currentDate= new java.util.Date();
        	        		        Creation_date=Date.valueOf(creation_date);
	        	        	        longCreationDate= Creation_date.getTime();
        	        	        	longCurrentDate= currentDate.getTime();
	                		        noOfdays=(longCurrentDate-longCreationDate)/(24*3600*1000)+1;
					
						if(noOfdays > 7 && (longCurrentDate-longCreationDate)!=0)
						{
							emailId = ((CourseUserDetail)courselist.get(i)).getEmail();
							Mail_msg=MailNotification.sendMail(MsgForExpireTime+gName+subMsgForExpireTime,emailId,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,LangFile);
							indexList.add(i);
						}
					}	
**/
					xmlWriter=TopicMetaDataXmlWriter.WriteXml_OnlineCourse(path,"/courses.xml",indexList);
	        	        	TopicMetaDataXmlWriter.appendOnlineCrsElement(xmlWriter,gname,cname,uname,orgtn,email,fname,lname,curDate);
	        		        xmlWriter.writeXmlFile();
					sendMailToApproval("fromCourse",LangFile);
				} //else inner
        		}
			else
			{
				indexList.add(-1);
                		xmlWriter=TopicMetaDataXmlWriter.WriteXml_OnlineCourse(path,"/courses.xml",indexList);
		                TopicMetaDataXmlWriter.appendOnlineCrsElement(xmlWriter,gname,cname,uname,orgtn,email,fname,lname,curDate);
        		        xmlWriter.writeXmlFile();
				sendMailToApproval("fromCourse",LangFile);
			}
		}
                context.put("lang",lang);
                String str=MultilingualUtil.ConvertedString("online_msg6",LangFile);
                data.setMessage(str);
        }
		
	void sendMailToApproval(String gname, String LangFile)
	{	
		int j=0;
		server_name= TurbineServlet.getServerName();
                srvrPort= TurbineServlet.getServerPort();
		MsgForExpireTime = " A new request for ";
		String subMsgForExpireTime =", is found to register. Please approve the request for registration.";
		String temp="";
		if(!gname.equals("fromCourse") && !gname.equals("author")) {
			int counter=0;
			int gid=GroupUtil.getGID(gname);
			Vector uid=UserGroupRoleUtil.getUID(gid,2);
			for(counter =0; counter<uid.size(); counter++)
			{
				String s=uid.elementAt(counter).toString();
				List st=UserManagement.getUserDetail(s);
               	        	for(j=0;j<st.size();j++)
                       		{
					TurbineUser element1=(TurbineUser)st.get(j);
					String userName = element1.getUserName();
					boolean check_Primary=CourseManagement.IsPrimaryInstructor(gname,userName);
					boolean check_Active=CourseManagement.CheckcourseIsActive(gid);
					if(check_Primary==true && check_Active==false)
					{
						emailId = element1.getEmail();
						String Mail_msg=MailNotification.sendMail(MsgForExpireTime+"user named "+userName+subMsgForExpireTime,emailId,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,LangFile);
					}		
				}
			}// for
		} //if
		else
		{
			String Mail_msg="";
			String s=Integer.toString(UserUtil.getUID("admin"));
			List st=UserManagement.getUserDetail(s);
			for(j=0;j<st.size();j++)
                        {
				TurbineUser element1=(TurbineUser)st.get(j);
				emailId = element1.getEmail();
				Mail_msg=MailNotification.sendMail(MsgForExpireTime+"a new course"+subMsgForExpireTime,emailId,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,LangFile);
			}
		}
	}// sendMailToApproval

	/* Generate a Password object with a random password. */

	public String getNext() {
		/** Minimum length for a decent password */
		final int MIN_LENGTH = 2;
  		/** The random number generator. */
		java.util.Random r = new java.util.Random();

		  /*
		   * Set of characters that is valid. Must be printable, memorable, and "won't
		   * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands
		   * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out,
		   * as are numeric zero and one.
		   */
	      char[] goodChar = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
			'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 
			'p','q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y','z',
			'2', '3', '4', '5', '6', '7', '8', '9', '+', '-', };

    		StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < MIN_LENGTH; i++) {
		      sb.append(goodChar[r.nextInt(goodChar.length)]);
		    }
		    return sb.toString();
		
	}
	public Vector sendMail_MoreThanSevenDays(Vector listCrs_OR_Usr, String MsgForExpireTime, String name, String serverName, String serverPort, String LangFile)
	{
		Vector indexList = new Vector();
		Date Creation_date;
		String subMsgForExpireTime =" registration is expired. Please talk to the administrator personally";
		for( int i=0; i <listCrs_OR_Usr.size(); i++)
		{
			String cdate=((CourseUserDetail)listCrs_OR_Usr.get(i)).getCreateDate();
			String creation_date = cdate.substring(0,4)+"-"+cdate.substring(4,6)+"-"+cdate.substring(6,8);
			java.util.Date currentDate= new java.util.Date();
	        	Creation_date=Date.valueOf(creation_date);
		        longCreationDate= Creation_date.getTime();
	        	longCurrentDate= currentDate.getTime();
		        noOfdays=(longCurrentDate-longCreationDate)/(24*3600*1000)+1;

        		if(noOfdays > 7 && (longCurrentDate-longCreationDate)!=0)
		        {
        			emailId = ((CourseUserDetail)listCrs_OR_Usr.get(i)).getEmail();
                		String Mail_msg=MailNotification.sendMail(MsgForExpireTime+name+subMsgForExpireTime,emailId,"onlineRegRequest","Updation Mail","","","",serverName,serverPort,LangFile);
	                	indexList.add(i);
			}
		}
		return indexList;              
	}
	/**
	 * This is the default method called when the button is not found
	 * @param data RunData
	 * @param context Context
	 */

	public void doPerform(RunData data,Context context) throws Exception
	{
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_UserRegister"))
			UserRegister(data,context);
		
		else if(action.equals("eventSubmit_CourseRegister"))
                        CourseRegister(data,context);

		else
		{
			data.setMessage("Action not found");
		}
	}
}

