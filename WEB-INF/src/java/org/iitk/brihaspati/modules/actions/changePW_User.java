package org.iitk.brihaspati.modules.actions;

/*
 * @(#)changePW_User.java	
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 */

/**
 * This class responsible for change user password in dataBase
 * @author <a href="mailto:madhavi_mungole@hotmail.com ">Madhavi Mungole</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
 */

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.PasswordUtil;

public class changePW_User extends SecureAction
{
	/**
	 * This method updates the password of the user
	 * @param data RunData
	 * @param context Context
	 * @return nothing
	 */
	 private String LangFile=null;
	public void doUpdate(RunData data, Context context) 
	{     
		try{
			ParameterParser pp=data.getParameters();
			String stat=pp.getString("status");
			context.put("status",stat);

			/**
			 * Get the user object from RunData for the user
		 	* currently logged in
		 	*/
			User user=data.getUser();
			LangFile=(String)data.getUser().getTemp("LangFile");
			/**
		 	* Get the old password and new passowrd entered by the
		 	* user
		 	*/

			String oldPW=pp.getString("oldpassword","");
			String newPW=pp.getString("newpassword","");

			/**
		 	* Update the password and get appropriate message
		 	* @see PasswordUtil in utils
		 	*/
			String msg="";
			if(!user.getName().equals("guest")){
			 msg=PasswordUtil.doChangepassword(user,oldPW,newPW,LangFile);
			}
			else{
			 msg="Password can not be Modified for guest";
			}
			data.setMessage(msg);
		}
		catch(Exception ex)
		{
			data.setMessage("The error in change Password"+ex);
		}
	
	}	

	/**
	 * This is the default method called when the action is not
	 * found
	 * @param data RunData
 	 * @param context Context
	 */
	public void doPerform(RunData data, Context context) throws Exception{
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doUpdate"))
			doUpdate(data,context);
		else
			data.setMessage("Can not find the action !");
	}
}
