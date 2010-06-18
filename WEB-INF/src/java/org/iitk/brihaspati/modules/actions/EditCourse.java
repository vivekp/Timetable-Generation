package org.iitk.brihaspati.modules.actions;

/*
 * @(#)EditCourse.java	
 *
 *  Copyright (c) 2004-2006,2008 ETRG,IIT Kanpur. 
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
 */


import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

/**
 * This class contains code for Updatecourse details
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 */
 
public class EditCourse extends SecureAction_Admin
{
	/**
	 * This function is responsible for updating course details in
	 * the database.
	 * @param data RunData instance
	 * @param context Context instance
	 * @throws Exception, a generic exception
	 * 
	 */
	private String file=null;
	private String msg=null;
	public void doUpdate(RunData data, Context context)
	{
		try
		{
		/**
		  * Getting file Parameter by Temporary Variable
		  * file Parameter having the property file according to selected language
		  */
		file=(String)data.getUser().getTemp("LangFile");
		StringUtil S=new StringUtil();

		/**
		 * Get all detail of Course for updatation 	   
		 */
			ParameterParser pp=data.getParameters();
			String CourseId=pp.getString("gName");
			String CourseName=pp.getString("Cname");
			String act=pp.getString("isactive");
			String dept=pp.getString("Department");
			String desc=pp.getString("DESCRIPTION","");
		/**
		 * Update the course deatils
		 * @see CourseManagement from Utils
		 */
			if(S.checkString(CourseName)==-1 && S.checkString(dept)==-1 && S.checkString(desc)==-1 )
                       	{
				msg=CourseManagement.UpdateCourseDetails(CourseId,CourseName,dept,desc,act,file);
				data.setMessage(msg);
			}
			else
			{
				data.setMessage(MultilingualUtil.ConvertedString("usr_prof1",file));
			}
		}
		catch(Exception e)
		{
			
			data.setMessage("The error in Updating for Course "+e);
		}
	}
	 /**
         * This method deletes the existing course along with the role of the users
         * registered in the specific course
         * @param data RunData instance
         * @param context Context instance
         */

        public void doDelete(RunData data, Context context)
	{
		/**
		  * Getting file Parameter by Temporary Variable
		  * file Parameter having the property file according to selected language
		  */
		file=(String)data.getUser().getTemp("LangFile");
		try{
			String Gname=data.getParameters().getString("gName");
			msg=UserManagement.DeleteInstructor(Gname,file);
			data.setMessage(msg);
			setTemplate(data,"call,ListMgmt_Admin,ListCourse.vm");
		}
		catch(Exception ex)
		{
			data.setMessage("The error in deleting in Course -"+ex);
		}
	}

	
	/**
	 * This method is called by default when no method corresponding to the 
	 * button is found.
	 * @param data RunData instance
	 * @param context Context instance
 	 */

	public void doPerform(RunData data,Context context)
	{
		/**
		  * Getting file Parameter by Temporary Variable
		  * file Parameter having the property file according to selected language
		  */
		file=(String)data.getUser().getTemp("LangFile");
		try
		{
			String actionToPerform=data.getParameters().getString("actionButton","");
			if(actionToPerform.equals("eventSubmit_doUpdate"))
			{
				doUpdate(data,context);
			}
			else if(actionToPerform.equals("eventSubmit_doDelete"))
			{
				doDelete(data,context);
			}
			else
			{
				msg=MultilingualUtil.ConvertedString("error_msg4",file);
				data.setMessage(msg);
			}
		}
		catch(Exception e)
		{
			
			data.setMessage("The error"+e);
		}
	}
}

