package org.iitk.brihaspati.modules.utils;

/*
 * @(#)DbDetail.java
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

 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */

/**
 *  @author <a href="mailto:aktri@iitk.ac.in">Awadhesh Kumar Trivedi</a>
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author(modify) <a href="mailto:sumanrjpt@yahoo.co.in">Suman Rajput</a>
 *  @author (modify)<a href="mailto:rekha_20july@yahoo.co.in">Rekha Pal</a>
 */

/**
 * This class is a bean class that stores values from table SEND_DB and
 * store in object .
 */

public class DbDetail 
{
	private String Sender;
	private String Msg_Subject;
	private String Msgid;
	private String PublishDate;
	private String Status;
	private String ExpiryDate;
	private String Permission;
	private String GrpmgmtType;

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
		this.Msg_Subject=msg;
	}
	public String getMSubject()
	{
		return Msg_Subject;
	}
	public void setStatus(String status)
	{
		this.Status=status;
	}
	public String getStatus()
	{
		return Status;
	}
	public void setMsgID(String mid)
	{
		this.Msgid=mid;
	}
	public String getMsgID()
	{
		return Msgid;
	}
	public void setExpiryDate(String ExDate)
	{
		this.ExpiryDate=ExDate;
	}
	public String getExpiryDate()
	{
		return ExpiryDate;
	}
	
	public void setPermission(String permit)
	{
		this.Permission=permit;
	}
	public String getPermission()
	{
		return Permission;
	}

	public void setGrpmgmtType(String grpType)
        {
                this.GrpmgmtType=grpType;
        }
        public String getGrpmgmtType()
        {
                return GrpmgmtType;
        }

}
