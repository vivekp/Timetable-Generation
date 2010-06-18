package org.iitk.brihaspati.modules.utils;

/*
 * @(#)ResrachRepoDetail.java
 *
 *  Copyright (c) 2008-09 ETRG,IIT Kanpur. 
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

 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */

/**
* @author: <a href="mailto:seema_020504@yahoo.com">seema pal</a>
* @author: <a href="mailto:kshuklak@rediffmail.com">kishore kumar shukla</a>
*/

/**
 * This class is a bean class that stores values from table SEND_DB and
 * store in object .
 */

public class ResrachRepoDetail 
{
	private String Sender;
	private String Subject;
	private String SubjectId;
	private String PublishDate;
	private String ReplyId;
	private String RepoName;
	private String ExpiryDate;
	private String Replies;

	public void setSender(String Sender)
	{
		this.Sender=Sender;
	}
	public String getSender()
	{
		return Sender;
	}
	public void setPDate(String PublishDate)
	{
		this.PublishDate=PublishDate;
	}
	public String getPDate()
	{
		return PublishDate;
	}
	public void setSubject(String sub)
	{
		this.Subject=sub;
	}
	public String getSubject()
	{
		return Subject;
	}
	public void setReplies(String Rep)
	{
		this.Replies=Rep;
	}
	public String getReplies()
	{
		return Replies;
	}
	public void setSubjectId(String subjectid)
	{
		this.SubjectId=subjectid;
	}
	public String getSubjectId()
	{
		return SubjectId;
	}
	public void setReplyId(String replyid)
	{
		this.ReplyId=replyid;
	}
	public String getReplyId()
	{
		return ReplyId;
	}
	public void setExpiryDate(String ExDate)
	{
		this.ExpiryDate=ExDate;
	}
	public String getExpiryDate()
	{
		return ExpiryDate;
	}
	
	public void setRepoName(String reponame)
	{
		this.RepoName=reponame;
	}
	public String getRepoName()
	{
		return RepoName;
	}
}
