package org.iitk.brihaspati.modules.screens.call.UserMgmt_Admin;

/*
 * @(#) StudentCourselist.java	
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
 *  BUSINESS INTERRUPTIiON) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 *  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */
/**
 * @author  <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author  <a href="shaistashekh@gmail.com">Shasita</a>
 */

import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;

/**
  * This class contains code for courses list where Students Registered
  * and registered in new course
  */

public class StudentCourselist extends SecureScreen_Admin{
	/**
	  * Place all the data object in the context for use in the template.
	  * @param data RunData instance
	  * @param context Context instance
	  * @exception Exception, a generic exception
	  * @see UserUtil in utils
	  * @see UserGroupRoleUtil in utils
	  * @see GroupUtil in utils
	  * @see UserManagement in utils
	  * @see ListManagement in utils
	  *
	  */
	public void doBuildTemplate(RunData data, Context context){
		try{
			Vector students=new Vector();
			Vector g=new Vector();
			Criteria crit=new Criteria();
			/**
			 * Get the user name from url and find the user id
			 * @see UserUtil in utils
			 */
			String uname=data.getParameters().getString("username");
		        context.put("username",uname);
			int uid=UserUtil.getUID(uname);
	 		/**
			 * Find all groupId according userid and roleid
			 * @see UserGroupRoleUtil in utils
			 * @see UserManagement in utils
			 */
			Vector gid=UserGroupRoleUtil.getGID(uid,3);
			List student=UserManagement.getUserDetail(Integer.toString(uid));
			for(int j=0;j<gid.size();j++){
	 			/**
			 	  * Find all groupname according groupid
			 	  * @see GroupUtil in utils
			 	  */
				String group_id=(String)(gid.elementAt(j));
				String gname=GroupUtil.getGroupName(Integer.parseInt(group_id));
              			g.add(gname);
             		}
	 		/**
			  * Find all groupname
			  * @see ListManagement in utils
			  */
			List courseList=ListManagement.getCourseList();
			if(g.size()!=0)
			{
				context.put("student",student);
				context.put("groupname",g);
				context.put("CourseList",courseList);
			}
			else
			{
				setTemplate(data,"call,UserMgmt_Admin,SelectUser.vm");
			}
		}
		catch (Exception e)
		{
			data.setMessage("The error is :-"+e);
		}
	}
}

