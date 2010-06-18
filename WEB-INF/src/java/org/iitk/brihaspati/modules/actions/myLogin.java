package org.iitk.brihaspati.modules.actions;
/*
 * @(#)myLogin.java	
 *
 *  Copyright (c) 2004-2008,2009 ETRG,IIT Kanpur. 
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
 *  Contributors : members of ETRG, IIT Kanpur  
 *  
 */

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.security.NoSuchAlgorithmException;

import com.workingdogs.village.Record;
import org.apache.velocity.context.Context;

import org.apache.turbine.util.RunData;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.util.security.TurbineSecurityException;
import org.apache.turbine.om.security.User;
import org.apache.turbine.modules.actions.VelocityAction;
import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.Turbine;
import org.apache.torque.util.Criteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.iitk.brihaspati.om.UsageDetailsPeer;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.utils.UpdateInfoMail;
import org.iitk.brihaspati.modules.utils.EncryptionUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 * Action class for authenticating a user into the system
 * This class also contains code for recording login statistics of 
 * users.This class is invoked whenever a user logs in to the system
 *
 * NOTE :- Use 'Turbine.getConfiguration().getString(String str)' instead of
 * 'TurbineResources.getString(String str)'.This is deprecated in TDK2.3.
 *
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kuamr Trivedi</a>
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class myLogin extends VelocityAction{
		private Log log = LogFactory.getLog(this.getClass());
/**
 * This method is invoked upon when user logging in
 * @param data RunData instance
 * @param context Context instance
 */
	
	public void doPerform( RunData data, Context context )
	{
		System.gc();

		Criteria crit=new Criteria();
		/** Getting Language according to Selection of Language in lang Variable
                 *  Getting Property file  according to Selection of Language
		 */

                String LangFile =data.getParameters().getString("Langfile","");
                String lang=data.getParameters().getString("lang","english");
		UpdateInfoMail.checknWriteXml();

		String username = data.getParameters().getString("username", "" );
		if(StringUtil.checkString(username) != -1) username="";
		String password = data.getParameters().getString("password", "" );
		User user=null;		
			//Get the session if exist then remove and create new session
			try{
				user = data.getUser();
				ErrorDumpUtil.ErrorLog("user in mylogin action=="+user);


			boolean a=data.userExists();
			ErrorDumpUtil.ErrorLog("check data.userexist=="+a);
			if(a)
				ErrorDumpUtil.ErrorLog("User exists already");
			}//try end
			catch(Exception e){data.setMessage("The error in session block:-- "+e);}
		
		user = null;
		String page=new String();
		// Provide a logger with the class name as category. This
		// is recommended because you can split your log files
		// by packages in the Log4j.properties. You can provide
		// any other category name here, though.

		log.info("this message would go to any facility configured to use the " + this.getClass().getName() + " Facility");//
		try{//(1)
			String str=new String();
			// Authenticate the user and get the object.
			password=EncryptionUtil.createDigest("MD5",password);
			user=TurbineSecurity.getAuthenticatedUser(username, password );

			// Store the user object.
			data.setUser(user);
			
			// Mark the user as being logged in.
			user.setHasLoggedIn(new Boolean(true));
			// get User ID 
			int uid=UserUtil.getUID(username);
			Date date=new Date();
			try{//(2)
				// Store the file object in Temporary Variable.
                                user.setTemp("LangFile",LangFile);

                                // Store the lang object in Temporary Variable.
                                user.setTemp("lang",lang);

				// Set the last_login date in the database.
 		
				user.updateLastLogin();
				int least_entry=0,count=0;

				//code for usage details starts here
				crit=new Criteria();
				crit.add(UsageDetailsPeer.USER_ID,uid);
				List entry=UsageDetailsPeer.doSelect(crit);
				count=entry.size();
				String find_minimum="SELECT MIN(ENTRY_ID) FROM USAGE_DETAILS WHERE USER_ID="+uid;
				ErrorDumpUtil.ErrorLog("fm from usage details=="+find_minimum);
				if(count==10)
				{
					List v=UsageDetailsPeer.executeQuery(find_minimum);
					for(Iterator j=v.iterator();j.hasNext();)
					{
						Record item2=(Record)j.next();
						least_entry=item2.getValue("MIN(ENTRY_ID)").asInt();
						ErrorDumpUtil.ErrorLog("least_entry from usage details=="+least_entry);
					}
					crit=new Criteria();
					crit.add(UsageDetailsPeer.ENTRY_ID,Integer.toString(least_entry));
					UsageDetailsPeer.doDelete(crit);
				} //end of 'if'

				crit=new Criteria();
				crit.add(UsageDetailsPeer.USER_ID,Integer.toString(uid));
				crit.add(UsageDetailsPeer.LOGIN_TIME,date);
				UsageDetailsPeer.doInsert(crit);
				
			}//try(2)
			catch(Exception e){
				data.setMessage("Cannot Login !! The error is :- "+e);
				page=Turbine.getConfiguration().getString("BrihaspatiLogin.vm");
				data.setScreen(page);
				
				log.info("this message would go to any facility configured to use the " + this.getClass().getName() + " Facility");
			}
			//If there is an error redirect to login page with a message"Cannot Login"
			AccessControlList acl = data.getACL();
			if( acl == null ){
				acl = TurbineSecurity.getACL( data.getUser() );
				data.getSession().setAttribute( AccessControlList.SESSION_KEY,(Object)acl );
				ErrorDumpUtil.ErrorLog("acl in mylogin action=="+acl);	
			}
			data.setACL(acl);
			data.save();

			
					boolean CL=CommonUtility.CleanSystem();
					if(!CL)
						data.addMessage("The Error in Clean System: see Common Utility");

					boolean AB=CommonUtility.IFLoginEntry(uid,date);

			/**
                          *Check the user for hint question when login at the first time.
                          */
                        try
			{
                                /**
                                *Check for the admin and the guest.
                                */
                                if(uid!=0 && uid!=1)
                                {
                                        crit=new Criteria();
                                        crit.add(UserConfigurationPeer.USER_ID,uid);
                                        crit.add(UserConfigurationPeer.QUESTION_ID,0);
                                        List check=UserConfigurationPeer.doSelect(crit);
                                        if((check.size()!=0))
                                        {
                                                setTemplate(data,"call,SelectHintQuestion.vm");
                                        }//end of 'if'
                                }//end of 'if'
                        }
                        catch(Exception e)
			{
				data.setMessage("Error in selecting hint question.Exception is :- "+e);
			}

		}//end try1		
		
	         /** In case of an error, get the appropriate error message from
	          *  TurbineResources.properties  
		  */
	 	catch ( TurbineSecurityException e ){
			LangFile =data.getParameters().getString("Langfile");
			String msg1=MultilingualUtil.ConvertedString("t_msg",LangFile);
			//page=Turbine.getConfiguration().getString("login.error");
			data.setMessage(msg1);
			data.setScreenTemplate("BrihaspatiLogin.vm");
			log.info("this message would go to any facility configured to use the " + this.getClass().getName() + " Facility"+e);
		}
		catch (NoSuchAlgorithmException e){
			data.setMessage("Could not find the required implementation");
			page=Turbine.getConfiguration().getString("screen.login");
			data.setScreenTemplate(page);
		}
		System.gc();
	}
}
