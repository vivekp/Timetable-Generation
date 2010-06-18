package org.iitk.brihaspati.modules.actions;

/*
 * @(#)InstructorRegisteration.java	
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur. 
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


import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 * This class is responsible for adding a secondary instructor to the system.
 *
 *  @author <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar Trivedi</a>
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */
 

public class InstructorRegisteration extends SecureAction_Admin
{

/**
 * Method for registering a new student
 * @param data RunData instance
 * @param context Context instance
 * @return nothing
 */
	private String LangFile=null;
	public void doRegister(RunData data, Context context)
	{	
		MultilingualUtil m_u=new MultilingualUtil();
		try
		{

		/**
                 * getting the value of file from temporary variable
                 * According to selection of Language.
                 **/  
		LangFile=(String)data.getUser().getTemp("LangFile");
		
		ParameterParser pp = data.getParameters();
		/**
		 * Retreiving details entered by the user
		 */
	
		String mod=pp.getString("mode");
		context.put("mode",mod);
		String gName=pp.getString("cName");
		String uname=pp.getString("UNAME");
		String fname=pp.getString("FNAME");
		String lname=pp.getString("LNAME");
		String email=pp.getString("EMAIL");
		String passwd=pp.getString("PASSWD");
		if(passwd.equals(""))
			passwd=uname;

		String mail_msg="";
		String serverName=data.getServerName();
                int srvrPort=data.getServerPort();
                String serverPort=Integer.toString(srvrPort);

		String msg=UserManagement.CreateUserProfile(uname,passwd,fname,lname,email,gName,"instructor",serverName,serverPort,LangFile);
		context.put("msg",msg);
		data.setMessage(msg +" "+ mail_msg);

		}
		catch(Exception ex)
		{
			data.setMessage("The error !!!"+ex);
			
		}
	}

	/**
	 * This is the default method called when the button is not found
	 */

	public void doPerform(RunData data,Context context) throws Exception{
		String action=data.getParameters().getString("actionName","");
		LangFile=(String)data.getUser().getTemp("LangFile");
		if(action.equals("eventSubmit_doRegister"))
			doRegister(data,context);
		else
			
			data.setMessage(MultilingualUtil.ConvertedString("c_msg",LangFile));
	}
}

