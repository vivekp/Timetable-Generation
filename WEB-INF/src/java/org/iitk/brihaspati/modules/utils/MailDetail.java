package org.iitk.brihaspati.modules.utils;

/*
 * @(#)MailDetail.java
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
 *  @author <a href="chitvesh@yahoo.com">Chitvesh Dutta</a>
 */

public class MailDetail 
{
	private String Sender;
	private String Mail_Subject;
	private String Mailid;
	private String PublishDate;
	private String Status;
	private String Rep_Status;
	private String MailType;
	
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
	public void setMSubject(String msg)
	{
		this.Mail_Subject=msg;
	}
	public String getMSubject()
	{
		return Mail_Subject;
	}
	public void setReplyStatus(String Re_status)
	{
		this.Rep_Status=Re_status;
	}
	public String getReplyStatus()
	{
		return Rep_Status;
	}
	public void setStatus(String status)
	{
		this.Status=status;
	}
	public String getStatus()
	{
		return Status;
	}
	public void setMailID(String mid)
	{
		this.Mailid=mid;
	}
	public String getMailID()
	{
		return Mailid;
	}

	public void setMailType(String mailType)
        {
                this.MailType=mailType;
        }
        public String getMailType()
        {
                return MailType;
        }

}
