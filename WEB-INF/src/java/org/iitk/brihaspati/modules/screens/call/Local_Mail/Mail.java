package org.iitk.brihaspati.modules.screens.call.Local_Mail;

/*
 * @(#) Mail.java	
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

import java.util.Vector;
import java.util.List;
import java.util.ListIterator;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.modules.screens.VelocitySecureScreen;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.Turbine;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import com.workingdogs.village.Record;
import org.iitk.brihaspati.modules.utils.UserUtil; 
import org.iitk.brihaspati.om.MailReceivePeer;
/**
 *   This class contains code for all Messages in a local mail account.
 *   
 * @author  <a href="mailto:chitvesh@yahoo.com">chitvesh dutta</a>
 * @author  <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author  <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *    
 */

//public class Mail extends VelocitySecureScreen
public class Mail extends SecureScreen
{
	 /**
	   * This method for Authorisation check 
	   * @param data RunData instance
	   * @return boolean
	   * try and catch Identifies statements that user want to monitor
	   * and catch for exceptoin.
	   */
	/*public boolean isAuthorized(RunData data)
	{
		boolean authorised=true;
		try
		{
		AccessControlList acl=data.getACL();
		User user=data.getUser();
		String g=user.getTemp("course_id").toString();
	*/
		 /**
		  *  Checks if the user has logged in as an instructor. If so, then he is
		  *  authorized to view this page
		  **/ 
	/*	   if(g!=null && acl.hasRole("instructor",g) || acl.hasRole("student",g))
		{
			authorised=true;
		}
		else
		{
			data.setScreenTemplate(Turbine.getConfiguration().getString("template.login"));
			authorised=false;
		}	
		}
		catch(Exception e){}
		return authorised;
	}*/
	 /**
	   * This method actually viewing the messages in the Local Mailing system
	   * @param data RunData instance
	   * @param context Context instance
	   * try and catch Identifies statements that user want to monitor
	   * and catch for exceptoin.
	   */
    	public void doBuildTemplate(RunData data, Context context)
    	{
		try
		{
			/**
		 	* Create the instance of user
		 	*/
			User user=data.getUser();
			String mod=data.getParameters().getString("mod","");
			context.put("mode",mod);
			/**
		  	* Getting the CourseId and CourseName from Temp variables
		  	*/
                	String dir=(String)user.getTemp("course_id");
			String coursename=(String)user.getTemp("course_name");
			/**
		  	* Getting the userId of logged user from Turbine_User table
		  	* @see UserUtil in Utils
		  	*/
			String user_name = user.getName();
			String F_name = user.getFirstName();
			int user_id = UserUtil.getUID(user_name);
			int unread_flag=0;
			String unreads=new String();
			String totalMsg=new String();
			String msg=new String();
			List massage=null;
		 	/**
		   	* Count the UnRead messages according to userId
		   	*/
			msg="SELECT COUNT(MAIL_RECEIVE.MAIL_ID) UNREAD FROM MAIL_RECEIVE, MAIL_SEND WHERE MAIL_SEND.MAIL_ID=MAIL_RECEIVE.MAIL_ID  AND RECEIVER_ID="+user_id+" AND MAIL_READFLAG="+unread_flag;
		 	massage=MailReceivePeer.executeQuery(msg);
			for(ListIterator j=massage.listIterator(); j.hasNext();)
			{
				Record item1=(Record)j.next();
				unreads=item1.getValue("UNREAD").asString();
			}
		 	/**
		   	* Count the Total messages according to userId
		   	*/
			msg=new String();
			msg="SELECT COUNT(MAIL_ID) TOTAL FROM MAIL_RECEIVE WHERE RECEIVER_ID ="+user_id;
			massage=MailReceivePeer.executeQuery(msg);
			for(ListIterator k=massage.listIterator(); k.hasNext();)
			{
				Record item=(Record)k.next();
				totalMsg=item.getValue("TOTAL").asString();
			}
		  	/**
		   	* Adds the information to context
		   	*/
		
			context.put("unread",unreads);
			context.put("total",totalMsg);
			context.put("Uname",user_name);
			context.put("Fname",F_name);
			context.put("cname",coursename);
		}
	catch(Exception ex)
	{
		data.setMessage("The error in Mail Home !! "+ex);
		
	}
    }
}

