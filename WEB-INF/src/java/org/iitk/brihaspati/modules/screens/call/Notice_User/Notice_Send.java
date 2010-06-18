package org.iitk.brihaspati.modules.screens.call.Notice_User;

/*
 * @(#)Notice_Send.java	
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
 * 
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */

/**
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

import java.util.Vector;
import java.util.List;

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

public class Notice_Send extends SecureScreen{
	/**
	 * Loads the template screen
	 */

	public void doBuildTemplate( RunData data, Context context ) {
		try{
			/**
			 * Retreives the login name and user id of the user logged in
			 */

			User user=data.getUser();
			String loginname=user.getName();
			int user_id=UserUtil.getUID(loginname);
			//used for group management 
			String mode1=data.getParameters().getString("mode1","");
                        context.put("mode1",mode1);
                        String grpname=data.getParameters().getString("val1","");
                        context.put("val",grpname);

                        String flag=data.getParameters().getString("nflag","");
			context.put("nflag",flag);

			/**
			 * Retreives all courses for use of admin
			 */
			if(loginname.equals("admin")){
				List CList=ListManagement.getCourseList();
				context.put("clist",CList);
				CList=null;
			}
			/**
			 * Retreives all the courses in which the user is an instructor
			 */

			Vector courselist=new Vector();
			Vector v=UserGroupRoleUtil.getGID(user_id,2);
			int rows=v.size();
			for(int count=0;count<rows;count++){
				String groupId=(String)(v.elementAt(count));
				int gid=Integer.parseInt(groupId);
				String group_name=GroupUtil.getGroupName(gid);
				String coursename=CourseUtil.getCourseName(group_name);
				courselist.addElement(coursename);
			}

			String C_Name=(String)(user.getTemp("course_name"));
			context.put("course",C_Name);
			context.put("courselist",courselist);
		}
		catch(Exception e){data.setMessage("the error in notice send java---->"+e);} 
	}
}

