package org.iitk.brihaspati.modules.actions;
/*
 * @(#)ForgotPassword.java	
 *
 *  Copyright (c) 2005-2007 ETRG,IIT Kanpur. 
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


import java.util.Vector; 
import java.util.List; 
import java.util.Random;

import org.apache.velocity.context.Context;
import org.apache.turbine.modules.actions.VelocitySecureAction;
import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.util.RunData;  
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.torque.util.Criteria; 
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.EncryptionUtil;
import org.iitk.brihaspati.modules.utils.UserUtil; 
import org.iitk.brihaspati.modules.utils.UserManagement; 
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil; 
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.UserConfiguration;
import org.iitk.brihaspati.om.HintQuestionPeer;
import org.iitk.brihaspati.om.HintQuestion;
/**
 * This class have different methods for using forgot password procedure
 * @author <a href="singhsatyapal@rediffmail.com">Satyapal Singh</a>
 * @author <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="ynsingh@iitk.ac.in">Y.N.Singh</a>
 */

public class ForgotPassword extends VelocitySecureAction
{
	String LangFile=new String();
	String lang=new String();
	String msg=new String();
	protected boolean isAuthorized( RunData data ) throws Exception
	{
		return true;
	}
	/**
 	* This method checks the existence and authority of the user to retrieve the new password
 	* @param data RunData instance
 	* @param context Context instance
 	* 
 	*/
 	public void doCheckUser(RunData data, Context context)
        {
		ParameterParser pp=data.getParameters();
                try
                {
                	String loginName=pp.getString("username");
		//	if(StringUtil.checkString(loginName) != -1) loginName = "";
			if(StringUtil.checkString(loginName) != -1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                                return;
                        }
			context.put("username",loginName);
                	if((loginName.equals("admin"))||(loginName.equals("guest")))
                	{
				msg=MultilingualUtil.ConvertedString("forgotPwd_msg1",LangFile);
                		data.setMessage(msg);
				context.put("status","User");
                	}
			else
			{
				boolean userExists=UserManagement.checkUserExist(loginName);
 				/**
                 		*Check the user for hint question when login at the first time.
		 		* @see UserUtil in utils
                 		*/
				if(userExists==false)
				{
					int uid=UserUtil.getUID(loginName); 
					Criteria crit=new Criteria();
                			crit.add(UserConfigurationPeer.USER_ID,Integer.toString(uid));
                			crit.add(UserConfigurationPeer.QUESTION_ID,0);
                	 		List check=UserConfigurationPeer.doSelect(crit);
			 		if((check.size()!=0))
                         		{
						msg=MultilingualUtil.ConvertedString("forgotPwd_msg2",LangFile);
			 			data.setMessage(msg);
                         			setTemplate(data,"BrihaspatiLogin.vm");
                         		}//end of inner'if'
			 		else
			 		{
						context.put("status","HintQus");
                        			crit=new Criteria();
                        			crit.addJoin(HintQuestionPeer.QUESTION_ID,UserConfigurationPeer.QUESTION_ID);
                        			crit.add(UserConfigurationPeer.USER_ID,uid);
                        			check=null;
						check=HintQuestionPeer.doSelect(crit);
                        			HintQuestion element=(HintQuestion)check.get(0);
                        			String que=element.getQuestionName();
                        			context.put("qname",que);
                        			context.put("uid",Integer.toString(uid));
			 		}
				}//end of outer'if'
                		else
                		{
					msg=MultilingualUtil.ConvertedString("check_user",LangFile);
                                	data.setMessage(loginName+" ==> "+msg);
					context.put("status","User");
                		}
			}
                }
            	catch(Exception e)
                {
                	data.setMessage("the error"+e);
                }
        }
	/**
 	* This method retrieve a new password for the user, where
	* password randomaly generated
 	* @param data RunData instance
 	* @param context Context instance
 	* @exception Exception, a generic exception
 	*/	
	public void doSend_NewPasswd(RunData data, Context context)
	{
               	try{	
		
			ParameterParser pp=data.getParameters();
			String loginName=pp.getString("username");
			String uid=pp.getString("uid");
			String ansOfInput=pp.getString("HintAns");
			/** 
	  		* Matching the hint answer from 
	  		* the database table.
	  		*/	
			Criteria crit=new Criteria();
                	crit.add(UserConfigurationPeer.USER_ID,uid);
                	List check_ans=UserConfigurationPeer.doSelect(crit);
			UserConfiguration element=(UserConfiguration)check_ans.get(0); 
                	String ansOfDb=element.getAnswer();
			if(ansOfDb.equals(ansOfInput))
		        {
				User user = TurbineSecurity.getUser(loginName);
                		String mailId=user.getEmail();
				/** 
			    	* checking for the user's e-mail
			    	* from the database table.	
			    	*/	
        	        	if(!mailId.equals(""))
				{
					AccessControlList acl=TurbineSecurity.getACL(user);
					byte[] pass=new byte[8];

					/** 
				   	* Random is a function for 
				   	* generating random numbers. 
				   	*/

	        	        	Random rnd=new Random();
					for(int i=0;i<8;i++)
					{ 
                	   			int inPass=rnd.nextInt(26);
                   				int p1;
		        			if((inPass%2)==0)
						{ 
	               					p1=inPass%10;
        	              				pass[i]=(byte)(p1+48); 
  			   			}
        	   	  			else
			  			{
							pass[i]=(byte)(inPass+97);
	                  			}	 
					}
					/**
					* Get Server Name and Server Port
					*/
					String serverName=data.getServerName();
 					int srvrPort=data.getServerPort();
					String serverPort=Integer.toString(srvrPort);
        		        	String str=new String(pass);
	        			String fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
					String msg1=new String();
					try
					{
						if(srvrPort == 8080)
						{
							msg1=MailNotification.sendMail("newPassword",mailId,"","","",str,fileName,serverName,serverPort,LangFile); 
	      					}
						else
						{
							msg1=MailNotification.sendMail("newPasswordhttps",mailId,"","","",str,fileName,serverName,serverPort,LangFile);
						}
						 if(msg1.equals(MultilingualUtil.ConvertedString("mail_msg2",LangFile)))
                                                {
						/**
						* new Password encrypted by MD5 then modify database 
						* password for user
						*/
						String encPass=EncryptionUtil.createDigest("MD5",str);
		       				user.setPassword(encPass); 
						TurbineSecurity.saveUser(user);
						msg=MultilingualUtil.ConvertedString("forgotPwd_msg3",LangFile);
						data.setMessage(msg);
						}
                                               else
                                               {
                                                       data.setMessage(msg1);
                                               }

					}
					catch(Exception e)
					{
						msg=MultilingualUtil.ConvertedString("forgotPwd_msg4",LangFile);
						data.setMessage(msg);
					}
		        	}
        	   		else
				{
					msg=MultilingualUtil.ConvertedString("forgotPwd_msg5",LangFile);
					data.setMessage(msg);
	                    	}
			}
		 	else
		 	{
				msg=MultilingualUtil.ConvertedString("forgotPwd_msg6",LangFile);
				data.setMessage(msg);
			}
		}
		catch(Exception ex)
		{
			data.setMessage("The error in send new password !!"+ex);
		}
	}	

	/**
 	* This method is invoked when user write an answer of selected hint question
 	* @param data RunData
 	* @param context Context
 	* @exception Exception, a generic exception
 	*/
	public void doInsertHintAns(RunData data, Context context)
	{
		ParameterParser pp=data.getParameters();
		try{
                	String uid=pp.getString("uid");
			String qid=pp.getString("qid");
			String ans=pp.getString("answer");
	
			Criteria crit=new Criteria();
			crit.add(UserConfigurationPeer.USER_ID,uid);
			crit.add(UserConfigurationPeer.QUESTION_ID,qid);
			crit.add(UserConfigurationPeer.ANSWER,ans);
			UserConfigurationPeer.doUpdate(crit);
               		setTemplate(data,"Index.vm");
			msg=MultilingualUtil.ConvertedString("forgotPwd_msg7",LangFile);
			data.setMessage(msg);
		}
		catch(Exception ex)
		{
			data.setMessage("The error in Insert Hint Ans !! "+ex);
		}
	}

	/**
 	* This method is invoked when no button corresponding to
 	* doForgotpassword  is found
 	* @param data RunData
 	* @param context Context
 	* @exception Exception, a generic exception
 	*
 	*/
        public void doPerform(RunData data,Context context) throws Exception
	{
		ParameterParser pp=data.getParameters();
        	lang=pp.getString("lang","english");
        	context.put("lang",lang);
		LangFile = MultilingualUtil.LanguageSelection(lang);
        	context.put("LangFile",LangFile);
                String action=data.getParameters().getString("actionName","");
                if(action.equals("eventSubmit_doSendNewPassword"))
                        doSend_NewPasswd(data,context);
                else if(action.equals("eventSubmit_doCheckUser"))
                        doCheckUser(data,context);
                else if(action.equals("eventSubmit_doInsertHint"))
                        doInsertHintAns(data,context);
                else
		{
			msg=MultilingualUtil.ConvertedString("error_msg4",LangFile);
                        data.setMessage(msg);
		}
        }
}

