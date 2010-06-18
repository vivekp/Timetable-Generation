package org.iitk.brihaspati.modules.utils;

/* 
 *  @(#)CourseUtil.java
 *
 *  Copyright (c) 2004 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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

import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.Courses;
import org.apache.torque.util.Criteria;
import java.util.List;
import java.util.Date;

public class CourseUtil{

	public static String getCourseName(String Course_id)
	{
		String CourseName=null;
		//List Course_list=null;
		try{
			Criteria crit=new Criteria();
			crit.add(CoursesPeer.GROUP_NAME,Course_id);
			List Course_list=CoursesPeer.doSelect(crit);
			CourseName=((Courses)Course_list.get(0)).getCname();
		}
		catch(Exception e){}
		return CourseName;
	}

	public static String getCourseId(String Course_name)
	{
		String CourseId=null;
		//List Course_list=null;
		try{
			Criteria crit=new Criteria();
			crit.add(CoursesPeer.CNAME,Course_name);
			List Course_list=CoursesPeer.doSelect(crit);
			CourseId=((Courses)Course_list.get(0)).getGroupName();
		}
		catch(Exception e){
		
			//log something
		}
		return CourseId;

	}
	public static String getCourseAlias(String Course_id)
	{
		String CAlias=null;
		List Course_list=null;
		try{
			Criteria crit=new Criteria();
			crit.add(CoursesPeer.GROUP_NAME,Course_id);
			Course_list=CoursesPeer.doSelect(crit);
			CAlias=((Courses)Course_list.get(0)).getGroupAlias();
		}
		catch(Exception e){
			
			//log something
		}
		return CAlias;
	}
	public static Date getLastModified(String courseId) throws Exception
	{
        	Criteria crit=new Criteria();
        	crit.add(CoursesPeer.GROUP_NAME,courseId);
        	List mainResult=CoursesPeer.doSelect(crit);

        	return (Date)((Courses)mainResult.get(0)).getLastmodified();

	}

}
