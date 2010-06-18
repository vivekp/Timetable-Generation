package org.iitk.brihaspati.modules.utils;

/*
 * @(#)StudentInstructorMAP.java	
 *
 *  Copyright (c) 2004-2006 ETRG,IIT Kanpur. 
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

import java.util.List;
import java.util.Vector;

import org.apache.torque.util.Criteria;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineGroup;
import org.apache.turbine.services.security.torque.om.TurbineGroupPeer;
import org.apache.turbine.services.security.torque.om.TurbineUser;

import org.iitk.brihaspati.modules.utils.CourseUserDetail;

/**
 *  This Class contains all information of Instructor and Student
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

public class StudentInstructorMAP
{
	/**
          * This Method used for shows all details of student in Active Course
	  * 
	  * @param uid int USER_ID
	  * @see CourseUserDetail from Utils
	  * @exception Exception, a generic exception
	  * @return Vector
	  */
	public static Vector getSMAP(int uid){
		Vector subResult=new Vector();
		Vector finalResult=new Vector();
		Vector entries=new Vector();
		String role_id="3";
		String insName="",courseName;
		CourseUserDetail cDetail=new CourseUserDetail();

		int user_id=UserUtil.getUID("admin");

		try{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,Integer.toString(uid));
			crit.add(TurbineUserGroupRolePeer.ROLE_ID,role_id);
			List stud_list=TurbineUserGroupRolePeer.doSelect(crit);
		
			for(int i=0;i<stud_list.size();i++){
				TurbineUserGroupRole element=(TurbineUserGroupRole)stud_list.get(i);
				int gid=element.getGroupId();
				int userId[]={user_id};
				crit=new Criteria();
				crit.add(TurbineUserGroupRolePeer.GROUP_ID,Integer.toString(gid));
				crit.and(TurbineUserGroupRolePeer.ROLE_ID,"2");
				crit.andNotIn(TurbineUserGroupRolePeer.USER_ID,userId);
				List substud_list=TurbineUserGroupRolePeer.doSelect(crit);	

				for(int j=0;j<substud_list.size();j++){
					TurbineUserGroupRole element1=(TurbineUserGroupRole)substud_list.get(j);
					uid=element1.getUserId();
					crit=new Criteria();
					crit.add(TurbineUserPeer.USER_ID,Integer.toString(uid));
					List final_list=TurbineUserPeer.doSelect(crit);
					for(int k=0;k<final_list.size();k++){
						TurbineUser element2=(TurbineUser)final_list.get(k);
						insName=element2.getFirstName();
						insName=insName+" "+element2.getLastName();
                                		String loginname=element2.getUserName();

						String groupName=GroupUtil.getGroupName(gid);
						boolean checkuser=groupName.endsWith(loginname);
						if(checkuser == true){
							boolean checkactive=CourseManagement.CheckcourseIsActive(gid);	
							if(checkactive==false){
								courseName=CourseUtil.getCourseName(groupName);
                    						String Coursealias=CourseUtil.getCourseAlias(groupName); 
								cDetail= new CourseUserDetail();
								cDetail.setGroupName(groupName);
								cDetail.setCAlias(Coursealias);
								cDetail.setLastModified(CourseUtil.getLastModified(groupName));
								cDetail.setCourseName(courseName);
								cDetail.setInstructorName(insName);
								entries.add(cDetail);
							}
						}
					}
				}
			}
		}
		catch(Exception e){
		entries.add("The Error"+e);	
		}
	return entries;
	}

	/**
          * This Method used for shows all details of Instructor
	  * @param uid int USER_ID
	  * @see CourseUserDetail from Utils
	  * @exception Exception, a generic exception
	  * @return Vector
	  */
	public static Vector getIMAP(int uid)
	{
		Vector entries=new Vector();
		String role_id="2";
		try{	

			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,Integer.toString(uid));
			crit.and(TurbineUserGroupRolePeer.ROLE_ID,role_id);
			List inst_list=TurbineUserGroupRolePeer.doSelect(crit);
			CourseUserDetail cDetail=new CourseUserDetail();
			String act="";
			for(int i=0;i<inst_list.size();i++)
			{
				TurbineUserGroupRole element=(TurbineUserGroupRole)inst_list.get(i);
				int gid=element.getGroupId();

				String groupName=GroupUtil.getGroupName(gid);
                    		String courseName=CourseUtil.getCourseName(groupName); 
                    		String Coursealias=CourseUtil.getCourseAlias(groupName); 
				boolean check_act=CourseManagement.CheckcourseIsActive(gid);
				if(check_act==false)
					act="1";
				else
					act="0";
				cDetail=new CourseUserDetail();
				cDetail.setCourseName(courseName);
				cDetail.setGroupName(groupName);
				cDetail.setCAlias(Coursealias);
				cDetail.setActive(act);
				entries.add(cDetail);
			}

		}
		catch(Exception e){
			String error="The error in Instructor Login --"+e;
			entries.add(error);	
		}
		return entries;
	}
}
