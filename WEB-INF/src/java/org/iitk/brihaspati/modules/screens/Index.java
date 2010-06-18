package org.iitk.brihaspati.modules.screens;

/*
 * @(#)Index.java	
 *
 *  Copyright (c) 2004 ETRG,IIT Kanpur. 
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
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.UserConfiguration;
/**
 * @author <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar Trivedi</a>
 */

public class Index extends SecureScreen{
	public void doBuildTemplate( RunData data, Context context ){
		try{
			User user=data.getUser();
			
			String username=user.getName();
			int uid=UserUtil.getUID(username);
			/** 
			 *	code for Photo display 
			 */
				Criteria crt=new Criteria();
			        crt.add(UserConfigurationPeer.USER_ID,uid);
			        List qu=UserConfigurationPeer.doSelect(crt);
				context.put("Image",qu);

			String fname=user.getFirstName();
			String lname=user.getLastName();
                        context.put("username",username);
                        context.put("firstname",fname);
                        context.put("lastname",lname);
			user.setTemp("role","");
		// check for Admin Role
			Vector Admin_Role=UserGroupRoleUtil.getGID(uid,1);
		// check for Instructor Role
			Vector Instructor_Role=UserGroupRoleUtil.getGID(uid,2);
		// check for Student Role
			Vector Student_Role=UserGroupRoleUtil.getGID(uid,3);
		// check for GroupAdmin Role
			Vector GAdmin_Role=UserGroupRoleUtil.getGID(uid,4);
		// check for ContentAuthor Role
			Vector Author_Role=UserGroupRoleUtil.getGID(uid,5);
			
			if(Admin_Role.size()!=0)
			{
	                        context.put("Role1","AdminRole");
			}
	
			if(Instructor_Role.size()!=0)
			{
                        	context.put("Role2","InstructorRole");
			}
			
			if(Student_Role.size()!=0)
			{
                        	context.put("Role3","StudentRole");
			}

			if(GAdmin_Role.size()!=0)
			{
                        	context.put("Role4","GAdminRole");
			}

			if(Author_Role.size()!=0)
			{
                        	context.put("Role5","AuthorRole");
			}
			
			if(user.getName().equals("guest")){
				context.put("guest_login","true");
			}
			else{
				context.put("guest_login","false");
			}

		}
		catch(Exception e){data.setMessage("The error is :- "+e);}
	}
}
