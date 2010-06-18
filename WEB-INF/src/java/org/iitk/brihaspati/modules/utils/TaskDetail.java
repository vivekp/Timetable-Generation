package org.iitk.brihaspati.modules.utils;

/*
 * @(#) TaskDetail.java
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
 *
 */
/**
 * In this class,set or get details of news 
 *  @author <a href="nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class TaskDetail 
{
	private int User_Id;
	private int Task_Id;
	private String Title;
	private int StartDate;
	private int EndDate;
	private int Status;
	
	public void setUser_Id(int uid)
	{
		this.User_Id=uid;
	}
	public int getUser_Id()
	{
		return User_Id;
	}
	public void setStartDate(int StartDate)
	{
		this.StartDate=StartDate;
	}
	public int getStartDate()
	{
		return StartDate;
	}
	public void setEndDate(int EndDate)
	{
		this.EndDate=EndDate;
	}
	public int getEndDate()
	{
		return EndDate;
	}
	public void setTitle(String msg)
	{
		this.Title=msg;
	}
	public String getTitle()
	{
		return Title;
	}
	public void setStatus(int status)
	{
		this.Status=status;
	}
	public int getStatus()
	{
		return Status;
	}
	public void setTask_Id(int tid)
	{
		this.Task_Id=tid;
	}
	public int getTask_Id()
	{
		return Task_Id;
	}
}
