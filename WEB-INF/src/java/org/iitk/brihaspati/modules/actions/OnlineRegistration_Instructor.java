package org.iitk.brihaspati.modules.actions;

/*
 * @(#) OnlineRegistration_Instructor.java	
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
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;


/**
 * 
 * @author  <a href="mailto:omprakash_kgp@yahoo.co.in">Om Prakash</a>
 * @author  <a href="mailto:shaistashekh@hotmail.com">Shaista Bano</a>
 * @modify 20-03-09
 */


public class  OnlineRegistration_Instructor extends SecureAction{

	private String LangFile=null;
	private String tokn="", userName="", groupName="", mailId="" ;
	private String uname="", gname="", email="";
	private String [] splitedTokn ;


        protected boolean isAuthorized( RunData data ) throws Exception
        {
                return true;
        }

        public void RejectUser(RunData data, Context context)
        {
             try{	
                        Vector userlist=new Vector();
			Vector indexList=new Vector();

                        User user=data.getUser();
			LangFile=(String)user.getTemp("LangFile");
                        ParameterParser pp=data.getParameters();
	                String accept=pp.getString("deleteFileNames");
			String path=data.getServletContext().getRealPath("/OnlineUsers");
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path+"/OnlineUser.xml");
			userlist=topicmetadata.getOnlineUserDetails();
			StringTokenizer st=new StringTokenizer(accept,"^");
			String msgForExpireTime= "Your Request for "; 
			String subMsgForExpireTime= " registration is rejected. Please contact to the administrator personally";
                        String server_name= TurbineServlet.getServerName();
                        String srvrPort= TurbineServlet.getServerPort();
			for(int j=0;st.hasMoreTokens();j++)
                        {
				tokn=st.nextToken();
				splitedTokn = tokn.split(":");
				userName = splitedTokn[0];
				groupName = splitedTokn[1];
				mailId = splitedTokn[2];
				

                        	if(userlist!= null)
                        	{
                                        for(int i=0;i<userlist.size();i++)
					{  
                                        					    
						uname=((CourseUserDetail) userlist.elementAt(i)).getLoginName();
                                               	gname=((CourseUserDetail) userlist.elementAt(i)).getGroupName();
						email=((CourseUserDetail) userlist.elementAt(i)).getEmail();

						if(uname.equals(userName) && gname.equals(groupName) && email.equals(mailId))
						{						
							String Mail_msg=MailNotification.sendMail(msgForExpireTime+gname+subMsgForExpireTime,mailId,"onlineRegRequest","Updation Mail","","","",server_name,srvrPort,LangFile);
							indexList.add(i);
							String str=MultilingualUtil.ConvertedString("online_msg3",LangFile);
                					data.setMessage(str);
						} 
                                	}
                        	}
			}
		/**
		 *  Here  rejecting the particular entry from the "xml" file
		 */
	                XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(path,"/OnlineUser.xml",indexList);
			xmlwriter.writeXmlFile();	
		}//try
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in Online registartion do reject user method" +e);
	                data.setMessage("Please see Error log or Contact to administrator");
		}
               
	}

      	public void AcceptUser(RunData data, Context context)
	{

		try{
			
			Vector userlist=new Vector();
			Vector indexList=new Vector();
	
			User user=data.getUser();
                        ParameterParser pp=data.getParameters();
		        String serverName=data.getServerName();
                	int srvrPort=data.getServerPort();
                	String serverPort=Integer.toString(srvrPort);
	                String accept=pp.getString("deleteFileNames");
                	String path=data.getServletContext().getRealPath("/OnlineUsers"+"/OnlineUser.xml");
                	String xmlfilepath=data.getServletContext().getRealPath("/OnlineUsers");
                        TopicMetaDataXmlReader topicmetadata =new TopicMetaDataXmlReader(path);
		        userlist=topicmetadata.getOnlineUserDetails();
			LangFile=(String)user.getTemp("LangFile");
			StringTokenizer st=new StringTokenizer(accept,"^");
			for(int j=0;st.hasMoreTokens();j++)
                        {
				//String mailid=st.nextToken();
				tokn=st.nextToken();
                                splitedTokn = tokn.split(":");
                                userName = splitedTokn[0];
                                groupName = splitedTokn[1];
                                mailId = splitedTokn[2];

				if(userlist!=null)
                        	{
                                  				
					for(int i=0;i<userlist.size();i++)
                                	{
						
						//String email=((CourseUserDetail) userlist.elementAt(i)).getEmail();
						uname=((CourseUserDetail) userlist.elementAt(i)).getLoginName();
                                               	gname=((CourseUserDetail) userlist.elementAt(i)).getGroupName();
						email=((CourseUserDetail) userlist.elementAt(i)).getEmail();
                                        	//if(email.equals(mailid))
						if(uname.equals(userName) && gname.equals(groupName) && email.equals(mailId))
						{
							String uname=((CourseUserDetail) userlist.elementAt(i)).getLoginName();
	        	                                String passwd=((CourseUserDetail) userlist.elementAt(i)).getActive();
		        	                        //String gname=((CourseUserDetail) userlist.elementAt(i)).getGroupName();
                	                	        String roleName=((CourseUserDetail) userlist.elementAt(i)).getRoleName();
							String fname=((CourseUserDetail) userlist.elementAt(i)).getInstructorName();
							String lname=((CourseUserDetail) userlist.elementAt(i)).getUserName();
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
							String str=MultilingualUtil.ConvertedString("online_msg1",LangFile);
                					data.addMessage(str);
						}	
					}//for
				}//if
			}
			XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_OnlineUser(xmlfilepath,"/OnlineUser.xml",indexList); 
			xmlwriter.writeXmlFile();

		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in Online registartion action do accept user" +e);
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
         		String action=data.getParameters().getString("actionName","");

			if(action.equals("eventSubmit_AcceptUser"))
                	{
				AcceptUser(data,context);
			}
			else if(action.equals("eventSubmit_RejectUser"))
			{
				RejectUser(data,context);
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
