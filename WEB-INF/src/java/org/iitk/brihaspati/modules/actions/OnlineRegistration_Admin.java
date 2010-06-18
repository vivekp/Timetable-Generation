package org.iitk.brihaspati.modules.actions;

/*
 * @(#) OnlineRegistration_Admin.java	
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
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria; 
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
//brihaspati classes
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;

/**
 * @author  <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar singh</a>
 * @author  <a href="mailto:omprakash_kgp@yahoo.co.in">Om Prakash</a>
 * @author <a href="mailto:shaistashekh@hotmail.com">Shaista</a>
 * @modify 20-03-09

 */


public class  OnlineRegistration_Admin extends SecureAction_Admin{

	private String LangFile=null;
	private String tokn="", uName="", gName="", mailid="";
        private String uname="", gname="", email="", fname="", lname="", passwd="";
	private String [] splitedTokn ;

        public void RejectUser(RunData data, Context context)
        {
                        Vector userlist=new Vector();
			Vector indexList=new Vector();
			String Mail_msg="";
			context.put("status","UserResitration");	
                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
	                String accept=pp.getString("deleteFileNames");
             try{
			String path=data.getServletContext().getRealPath("/OnlineUsers");
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path+"/OnlineUser.xml");
			userlist=topicmetadata.getOnlineUserDetails();
			StringTokenizer st=new StringTokenizer(accept,"^");
			String MsgForExpireTime= "Your Request for "; 
			String subMsgForExpireTime= " Registration is rejected. Please contact to the administrator personally";
                        String server_name= TurbineServlet.getServerName();
                        String srvrPort= TurbineServlet.getServerPort();
			while(st.hasMoreTokens())
                        {
				tokn = st.nextToken();
				splitedTokn = tokn.split(":");
				mailid= splitedTokn [0];
				gName = splitedTokn [1];
				uName = splitedTokn [2];
				LangFile=(String)data.getUser().getTemp("LangFile");
                        	if(userlist!= null)
                        	{
                                        for(int i=0;i<userlist.size();i++)
					{  
                                        					    
						email=((CourseUserDetail) userlist.elementAt(i)).getEmail();
						uname=((CourseUserDetail) userlist.elementAt(i)).getLoginName();
                                               	gname=((CourseUserDetail) userlist.elementAt(i)).getGroupName();
                                                if(email.equals(mailid) && gname.equals(gName) && uname.equals(uName))
						{						
							Mail_msg=MailNotification.sendMail(MsgForExpireTime+gname+subMsgForExpireTime,mailid,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,"");
							indexList.add(i);
                					data.setMessage(MultilingualUtil.ConvertedString("online_msg3",LangFile));
						}
                                	} //for
                        	} //if

			} //while
		/**
		 *  Here  rejecting the particular entry from the "xml" file
		 */
	                XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(path,"/OnlineUser.xml",indexList);
			xmlwriter.writeXmlFile();	

		}//try
		catch(Exception e)
		{
	                data.setMessage("Please see Error log or Contact to administrator");
		}
               
	}

      	public void AcceptUser(RunData data, Context context)
	{

		try{
			Vector userlist=new Vector();
			Vector indexList=new Vector();	
			String roleName="";
			context.put("status","UserResitration");	
			User user=data.getUser();
                        ParameterParser pp=data.getParameters();
		        String serverName=data.getServerName();
			String serverPort=TurbineServlet.getServerPort();
	                String accept=pp.getString("deleteFileNames");
                	String path=data.getServletContext().getRealPath("/OnlineUsers"+"/OnlineUser.xml");
                	String xmlfilepath=data.getServletContext().getRealPath("/OnlineUsers");
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path);
		        userlist=topicmetadata.getOnlineUserDetails();
			LangFile=(String)data.getUser().getTemp("LangFile");
			StringTokenizer st=new StringTokenizer(accept,"^");
			for(int j=0;st.hasMoreTokens();j++)
                        {
				tokn = st.nextToken();
                                splitedTokn = tokn.split(":");
                                mailid= splitedTokn [0];
                                gName = splitedTokn [1];
                                uName = splitedTokn [2];

							
				if(userlist!=null)
                        	{
                                  				
					for(int i=0;i<userlist.size();i++)
                                	{
						
						email=((CourseUserDetail) userlist.elementAt(i)).getEmail();
						uname=((CourseUserDetail) userlist.elementAt(i)).getLoginName();
	        	                        gname=((CourseUserDetail) userlist.elementAt(i)).getGroupName();
                                                if(email.equals(mailid) && gname.equals(gName) && uname.equals(uName))
						{
                	                	        roleName=((CourseUserDetail) userlist.elementAt(i)).getRoleName();
	        	                                passwd=((CourseUserDetail) userlist.elementAt(i)).getActive();
							fname=((CourseUserDetail) userlist.elementAt(i)).getInstructorName();
							lname=((CourseUserDetail) userlist.elementAt(i)).getUserName();
							if(uname!=null)
							{
								try{
			              					String msg=UserManagement.CreateUserProfile(uname,passwd,fname,lname,email,gname,roleName,serverName,serverPort,LangFile);
									data.setMessage(msg);
								}
								catch(Exception e){
									data.setMessage("Error in new user Registration"+e);
								}
							}//if
							indexList.add(i);
                					data.addMessage("\n"+MultilingualUtil.ConvertedString("online_msg1",LangFile));
						}	
					}//for
				}//if
			}
			XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(xmlfilepath,"/OnlineUser.xml",indexList); 
			xmlwriter.writeXmlFile();

		}//try
		
		catch(Exception e){
	                data.setMessage("Please see Error log or Contact to administrator");
		}
	}

        public void doDeleteCourse(RunData data, Context context)
        {
             try{
                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
                        String path=TurbineServlet.getRealPath("/OnlineUsers");
                        XmlWriter xmlWriter=null;
                        String accept=pp.getString("deleteFileNames");
                        Vector indexList=new Vector();
                        Vector courselist = new Vector();
			String Mail_msg="";
			context.put("status","CourseRegistration");
			String server_name= TurbineServlet.getServerName();
			String srvrPort= TurbineServlet.getServerPort();
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path+"/courses.xml");
                        courselist=topicmetadata.getOnlineUserDetails();
                        StringTokenizer st= new StringTokenizer(accept,"^");
			String MsgForExpireTime= "Your Request for ";
			String subMsgForExpireTime= " Registration is rejected. Please contact to the administrator personally";
                        for(int j=0;st.hasMoreTokens();j++)
                        {
				/** @param gName getting GroupId & user name**/

				tokn=st.nextToken();
				splitedTokn = tokn.split(":");
				mailid = splitedTokn[0]; 
                                gName = splitedTokn[1];
                                if(courselist!= null)
                                {
                                        for(int i=0;i<courselist.size();i++)
                                        {
                                                email=((CourseUserDetail) courselist.elementAt(i)).getEmail();
						gname=((CourseUserDetail) courselist.elementAt(i)).getGroupName()+((CourseUserDetail) courselist.elementAt(i)).getLoginName();
                                                if((email.equals(mailid)) && gName.equals(gname))
                                                {
							Mail_msg=MailNotification.sendMail(MsgForExpireTime+gname+subMsgForExpireTime,mailid,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,"");
                                                        indexList.add(i);
							LangFile=(String)data.getUser().getTemp("LangFile");
                                                        data.setMessage(MultilingualUtil.ConvertedString("online_msg4",LangFile));
                                                }
                                        }
                                }
                        }
                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_OnlineCourse(path,"/courses.xml",indexList);
                        xmlWriter.writeXmlFile();
                }//try
 
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in Online registartion action do delete course method" +e);
	                data.setMessage("Please see Error log or Contact to administrator");
			}
        }

        public void doAcceptCourses(RunData data, Context context)
        {
                try{
                        Vector indexList=new Vector();
                        Vector courselist=new Vector();
			String gidUname="", passwd="", cname="";
			context.put("status","CourseRegistration");
                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
                        String serverName=data.getServerName();
			String serverPort=TurbineServlet.getServerPort();
                        String accept=pp.getString("deleteFileNames");
                        String path=data.getServletContext().getRealPath("/OnlineUsers"+"/courses.xml");
                        String xmlfilepath=data.getServletContext().getRealPath("/OnlineUsers");
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path);
                        courselist=topicmetadata.getOnlineCourseDetails();
                        StringTokenizer st=new StringTokenizer(accept,"^");

                        for(int j=0;st.hasMoreTokens();j++)
                        {
				tokn=st.nextToken();
                                splitedTokn = tokn.split(":");
                                mailid = splitedTokn[0];
                                gName = splitedTokn[1]; //Getting gid +user name
                                if(courselist!=null)
                                {
                                        for(int i=0;i<courselist.size();i++)
                                        {
 
                                                email=((CourseUserDetail) courselist.elementAt(i)).getEmail();
						gidUname=((CourseUserDetail) courselist.elementAt(i)).getGroupName()+((CourseUserDetail) courselist.elementAt(i)).getLoginName();
                                                if((email.equals(mailid)) && gName.equals(gidUname))
                                                {
 
                                                        gname=((CourseUserDetail) courselist.elementAt(i)).getGroupName();
                                                        cname=((CourseUserDetail) courselist.elementAt(i)).getCourseName();
                                                        uname=((CourseUserDetail) courselist.elementAt(i)).getLoginName();
                                                        if(passwd.equals(""))
                                                                passwd=uname;
                                                        fname=((CourseUserDetail) courselist.elementAt(i)).getInstructorName();
                                                        lname=((CourseUserDetail) courselist.elementAt(i)).getUserName();
                                                       {
                                                                try{
                                                                        String msg=CourseManagement.CreateCourse(gname,cname,"","",uname,passwd,fname,lname,email,serverName,serverPort,LangFile);
									String subject="";
									if(serverPort.equals("8080"))
								                   subject="newOnlineCourse";
							                else
								                   subject="newOnlineCoursehttps";
                							String fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
									String Mail_msg=MailNotification.sendMail(subject,email,cname,"","","",fileName,serverName,serverPort,LangFile);
                                                                        data.setMessage(msg+Mail_msg);
                                                                }
                                                                catch(Exception e){data.setMessage("Error in new Course Registration "+e);}
                                                        }
                                                        indexList.add(i);
 
							LangFile=(String)data.getUser().getTemp("LangFile");
                                                        data.setMessage(MultilingualUtil.ConvertedString("online_msg2",LangFile));
                                                } //if
                                        }  //for
                                } //if
                        }
                        XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_OnlineCourse(xmlfilepath,"/courses.xml",indexList);
                        xmlwriter.writeXmlFile();

                 }
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in Online registartion action do accept course method" +e);
	                data.setMessage("Please see Error log or Contact to administrator");
		}
        }

	/**
          * ActionEvent responsible if no method found in this action i.e. Default Method
          * @param data RunData
          * @param context Context
          */

	public void doPerform(RunData data,Context context) throws Exception
	{
	    try{	
			LangFile=(String)data.getUser().getTemp("LangFile");
         		String action=data.getParameters().getString("actionName","");

			if(action.equals("eventSubmit_AcceptUser"))
                	{
				AcceptUser(data,context);
			}
			else if(action.equals("eventSubmit_RejectUser"))
			{
				RejectUser(data,context);
			}
			else if(action.equals("eventSubmit_doAcceptCourses"))
			{
				doAcceptCourses(data,context);	
			}
			else if(action.equals("eventSubmit_doDeleteCourse"))
			{
				doDeleteCourse(data,context);
			}
			else
			{	
                       		data.setMessage("Action is not properly defind.");
                	}   
		}
		catch(Exception e){ 
			ErrorDumpUtil.ErrorLog("The error in Online registartion admin action file" +e);
	                data.setMessage("Please see Error log or Contact to administrator");
		}			
	}

}
