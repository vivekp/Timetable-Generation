package org.iitk.brihaspati.modules.screens.call.CourseMgmt_Admin;

/*
 * @(#)CourseHome.java	
 *
 *  Copyright (c) 2004 - 2005 ETRG,IIT Kanpur. 
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

import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.apache.torque.util.BasePeer;
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import java.util.Calendar;
import java.util.Vector;
import java.util.List;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
 * @author <a href="mailto:nagendrakumarpal@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 */

public class CourseHome extends SecureScreen{
	public void doBuildTemplate( RunData data, Context context ) {
		try{
			User user=data.getUser();
			ParameterParser pp=data.getParameters();

			/**
			 * Retrieves the COURSE_ID of the course in which user has entered
			 */

			String courseid=pp.getString("courseid","");
			String userInCourse=(String)user.getTemp("course_id");

			if( userInCourse!=null && !userInCourse.equals("") && courseid.equals(""))
				courseid=userInCourse;

			/**
			 * Gathers Course Name and User Name 
			 */

			String C_Name=CourseUtil.getCourseName(courseid);
 			String username=user.getName();
			int userid=UserUtil.getUID(username);

			/**
			 * Sets all the temporary variables of the session 
			 */
			
			if(!courseid.equals("")&& !courseid.equals(userInCourse)){
				user.setTemp("course_name",C_Name);
				user.setTemp("course_id",courseid);
				String Role=data.getParameters().getString("role");
				user.setTemp("role",Role);
			}


		} catch(Exception e){data.setMessage("there is an exception :-"+e);}
	}
}

