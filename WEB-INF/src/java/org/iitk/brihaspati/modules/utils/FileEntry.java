package org.iitk.brihaspati.modules.utils;
/*
 * @(#)FileEntry.java
 *
 *  Copyright (c) 2007 ETRG,IIT Kanpur.
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
 * This class set some value and get in templates from XML file 
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */
public class FileEntry
{
	private String topic;
	private String role;
	private String username;
	private String coursename;
	private String name;
	private String pDate;
	private String alias;
	private String type;
        private String studentno;
	private char replacingChar='$'; //default value;
	
	private String result;
        private String cid;
	private String fileName;
        private String userid;
        private String Grade;
        private String feedback;
	private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String instructorans;
        private String Typeofquestion;
        private String question;
        private String questionid;
        private String marksperqustion;
	private String marks;
	private String emailId;
        private String status;
        private String url;

	private String Duedate;

	public void setemailId(String emailId)
        {
                this.emailId=emailId;
        }
        public String getemailId()
        {
                return emailId;
        }
        public void setstatus(String status)
        {
                this.status=status;
        }
        public String getstatus()
        {
                return status;
        }
        public void setDuedate(String Duedate)
                {
                        this.Duedate=Duedate;
                }
                public String  getDuedate()
                {
                        return Duedate;
                }

	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		int index=name.lastIndexOf('/'); // truncate the relative path from the this.name
		return name.substring(index+1);  // to retrieve the file name.
	}
	public void setTopic(String topic)
	{
		this.topic=topic;
	}
	public String getTopic()
	{
		int index=topic.lastIndexOf('/'); // truncate the relative path from the this.name
		return topic.substring(index+1);  // to retrieve the topic name.
	}
	public void setUserName(String username)
	{
		this.username=username;
	}
	public String getUserName()
	{
		int index=username.lastIndexOf('/'); // truncate the relative path from the this.name
		return username.substring(index+1);  // to retrieve the user name.
	}
	public void setCourseName(String coursename)
	{
		this.coursename=coursename;
	}
	public String getCourseName()
	{
		int index=coursename.lastIndexOf('/'); // truncate the relative path from the this.name
		return coursename.substring(index+1);  // to retrieve the course name.
	}
	public void setRole(String role)
        {
                this.role=role;
        }
        public String getRole()
        {
                int index=role.lastIndexOf('/'); // truncate the relative path from the this.name
                return role.substring(index+1);  // to retrieve the Role.
        }


	public void setPDate(String publishingDate)
	{
		this.pDate=publishingDate;
	}
	public String getPDate()
	{
		return pDate;
	}

	public String getRelativePath()
	{
		return name; 
	}

	public void setAlias(String alias)
	{
		this.alias=alias;
	}
	public String getAlias()
	{
		return alias;
	}

	public void setReplacingChar(char replacingChar)
	{
		this.replacingChar=replacingChar;
	}

	public String getCheckboxName()
	{
		String temp=name.replace('.', replacingChar); 
		return temp;
	}
	//group setings
        public void settype(String type)
        {
                this.type=type;
        }
        public String gettype()
        {
                int index=type.lastIndexOf('/'); // truncate the relative path from the this.name
                return type.substring(index+1);  // to retrieve the file name.
        }
        public void setstudentno(String studentno)
        {
                this.studentno=studentno;
        }
        public String getstudentno()
        {
                return studentno;
        }

	 public void setResult(String result)
        {
                this.result=result;
        }
        public String getResult()
        {
                int index=result.lastIndexOf('/'); // truncate the relative path from the this.name
                return result.substring(index+1);  // to retrieve the course name.
        }
	public void setCid(String cid)
        {
                this.cid=cid;
        }
        public String getCid()
        {
                int index=cid.lastIndexOf('/'); // truncate the relative path from the this.name
                return cid.substring(index+1);  // to retrieve the course name.
        }
	public void setoptionA(String optionA)
                {
                        this.optionA=optionA;
                }
                public String getoptionA()
                {
                        return optionA;
                }

                public void setoptionB(String optionB)
                {
                        this.optionB=optionB;
                }
                public String getoptionB()
                {
                        return optionB;
                }

                public void setoptionC(String optionC)
                {
                        this.optionC=optionC;
                }
                public String getoptionC()
                {
                        return optionC;
                }

                public void setoptionD(String optionD)
                {
                        this.optionD=optionD;
                }
                public String  getoptionD()
                {
                        return optionD;
                }
		 public void setinstructorans(String instructorans)
                {
                        this.instructorans=instructorans;
                }
                public String  getinstructorans()
                {
                        return instructorans;
                }


                public void setTypeofquestion(String Typeofquestion)
                {
                        this.Typeofquestion=Typeofquestion;
                }
                public String  getTypeofquestion()
                {
                        return Typeofquestion;
                }

                public void setquestion(String question)
                {
                        this.question=question;
                }
                public String  getquestion()
                {
                        return question;
                }

                public void setquestionid(String questionid)
                {
                        this.questionid=questionid;
                }
                public String  getquestionid()
                {
                        return questionid;
                }
		public void setmarksperqustion(String marksperqustion)
                {
                       this.marksperqustion=marksperqustion;
                }
                public String  getmarksperqustion()
                {
                        return marksperqustion;
                }
                 public void setfeedback(String feedback)
                {
                        this.feedback=feedback;
                }
                public String  getfeedback()
                {
                        return feedback;
                }
                public void setGrade(String Grade)
                {
                        this.Grade=Grade;
                }
                public String getGrade()
                {
                        return Grade;
                }

		public void setuserid(String userid)
                {
                        this.userid=userid;
                }
                public String getuserid()
                {
                        return userid;
                }
                 public void setfileName(String fileName)
                {
                        this.fileName=fileName;
                }
                public String getfileName()
                {
                        return fileName;
                }

                public void setmarks(String marks)
                {
                       this.marks=marks;
                }
                public String  getmarks()
                {
                        return marks;
                }
                public void setUrl(String url)
                {
                       this.url=url;
                }
                public String  getUrl()
                {
                        return url;
                }

		
}
