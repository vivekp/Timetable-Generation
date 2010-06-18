package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)Communication.java	
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
 */

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/**
 *  Communication functionality for Instructors and Students 
 *  @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *
 */
public class Communication extends SecureScreen
{
	public void doBuildTemplate(RunData data,Context context ){
		try{
			User user=data.getUser();
			/**
			 * Retrieves the course name from temporary variable
			 */

			String C_Name=(String)user.getTemp("course_name");

			/**
			 * Retrieves the course id from temporary variable
			 */
		
			String C_ID=(String)user.getTemp("course_id");
			
			/**
			 * Retrieves user's role from temporary variable
			 */

			String Role=(String)user.getTemp("role");

			/**
			 * Checks if the user has logged in as guest
			 */

			String userName=user.getName();
			if(userName.equals("guest")){
				context.put("guestRole","yes");
			}
			else{
				context.put("guestRole","no");
			}
			context.put("user_role",Role);
			context.put("courseName",C_Name);
		}
		catch(Exception e){}
	}
}

