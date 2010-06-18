package org.iitk.brihaspati.modules.utils;
/*
 * @(#)CourseUserDetail.java
 *
 *  Copyright (c) 2006-2008 ETRG,IIT Kanpur. 
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
 *  In this class all details of Course and User are set and get in Velocity Screens
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

import java.util.Date;
import java.sql.Timestamp;

public class CourseUserDetail 
{
	private String groupName;
	private String roleName;
	private String InsName;
	private String courseName;
	private String courseAlias;
	private String active;
	private String deptt;
	private String desc;
	private String createD;
	private Date lastModified;
	private String userName;
	private String loginName;
	private String userEmail;
	private String Err_user;
	private String Err_type;
	private String CMsg;
	/**
	 * Course details
	 */ 
	public void setGroupName(String gName)
	{
		this.groupName=gName;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setRoleName(String rName)
	{
		this.roleName=rName;
	}
	public String getRoleName()
	{
		return roleName;
	}
	public void setInstructorName(String insName)
	{
		this.InsName=insName;
	}
	public String getInstructorName()
	{
		return InsName;
	}
	public void setLastModified(Date lastModified)
	{
		this.lastModified=lastModified;
	}
	public Date getLastModified()
	{
		return lastModified;
	}
	public boolean hasLastModifiedAfter(Timestamp lastVisited)
	{
		return lastModified.after(lastVisited);
	}
	public void setCourseName(String name)
	{
		this.courseName=name;
	}
	public String getCourseName()
	{
		return courseName;
	}
	public void setCAlias(String alias)
	{
		this.courseAlias=alias;
	}
	public String getCAlias()
	{
		return courseAlias;
	}
	public void setActive(String act)
	{
		this.active=act;
	}
	public String getActive()
	{
		return active;
	}
	public void setDept(String dept)
	{
		this.deptt=dept;
	}
	public String getDept()
	{
		return deptt;
	}
	public void setDescription(String desc)
	{
		this.desc=desc;
	}
	public String getDescription()
	{
		return desc;
	}
	public void setCreateDate(String Cdate)
	{
		this.createD=Cdate;
	}
	public String getCreateDate()
	{
		return createD;
	}
	/**
	 * User details
	 */ 
	public void setLoginName(String uname)
	{
		this.loginName=uname;
	}
	public String getLoginName()
	{
		return loginName;
	}
	public void setUserName(String name)
	{
		this.userName=name;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setEmail(String email)
	{
		this.userEmail=email;
	}
	public String getEmail()
	{
		return userEmail;
	}
	// Massages for Removal Case
	public void setErr_User(String Err_User)
	{
		this.Err_user=Err_User;
	}
	public String getErr_User()
	{
		return Err_user;
	}
	public void setErr_Type(String Err_Type)
	{
		this.Err_type=Err_Type;
	}
	public String getErr_Type()
	{
		return Err_type;
	}
	public void setCourse_Msg(String msg)
	{
		this.CMsg=msg;
	}
	public String getCourse_Msg()
	{
		return CMsg;
	}
}
