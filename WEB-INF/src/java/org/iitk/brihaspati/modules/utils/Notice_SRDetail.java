package org.iitk.brihaspati.modules.utils;
/*
 * @(#)Notice_SRDetail.java
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
 * 
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */
 
/**
 * In this class,We collect details of notices
 *  @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

import java.util.Date;

public class Notice_SRDetail{
	private String msgId;
	private String msgSubject;
	private String msg;
	private String userId;
	private String groupId;
	private String roleId;
	private String userName;
	private String groupName;
	private Date postTime;
	private String readFlag;

	public void setMsgId(String msgId)
	{
		this.msgId=msgId;
	}

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgSubject(String msgSubject)
	{
		this.msgSubject=msgSubject;
	}

	public String getMsgSubject()
	{
		return msgSubject;
	}

	public void setMsg(String msg)
	{
		this.msg=msg;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setUserId(String userId)
	{
		this.userId=userId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId=groupId;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupName(String groupName)
        {
                this.groupName=groupName;
        }

        public String getGroupName()
        {
                return groupName;
        }

	public void setRoleId(String roleId)
	{
		this.roleId=roleId;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public void setUserName(String userName)
	{
		this.userName=userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setPostTime(Date postTime)
	{
		this.postTime=postTime;
	}

	public Date getPostTime()
	{
		return postTime;
	}
	  public void setReadFlag(String readFlag)
        {
                this.readFlag=readFlag;
        }

        public String getReadFlag()
        {
                return readFlag;
        }

}
