package org.iitk.brihaspati.modules.utils;

/*
 * @(#)QuizDetail.java
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

 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */

/**
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *  @author (modify)<a href="mailto:arvindjss17@yahoo.co.in">Arvind Pal</a>
 */

public class QuizDetail 
{
	private String userid;
	private String quizid;
	private String maxmarks;
	private String marks;
	private String QDate;
	private String Quiztype;
	private String questionid;
	private String sqid;
	private String Question;
	private String Answer;
	
	private String insAnswer;
	
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String feedback;
	private String Grade;
	private String ExpiryDate;
	private String studentAns;
	//private String Permission;

	



	public void setinsAnswer(String insAnswer)
        {
                this.insAnswer=insAnswer;
        }
        public String getinsAnswer()
        {
                return insAnswer;
        }

	public void setQuiztype(String Quiztype)
        {
        	this.Quiztype=Quiztype;
        }
	public void setstudentAns(String studentAns)
        {
        	this.studentAns=studentAns;
	}
        public String getstudentAns()
        {
        	return studentAns;
	}



        public String getQuiztype()
        {
                return Quiztype;
        }
	public void setquestionid(String questionid)
        {
                this.questionid=questionid;
        }
        public String getquestionid()
        {
                return questionid;
        }
	public void setuserid(String userid)
	{
		this.userid=userid;
	}
	public String getuserid()
	{
		return userid;
	}
	public void setQDate(String QDate)
	{
		this.QDate=QDate;
	}
	public String getQDate()
	{
		return QDate;  
	}
	public void setquizid(String quizid)
	{
		this.quizid=quizid;
	}
	public String getquizid()
	{
		return quizid;
	}
	public void setmaxmarks(String maxmarks)
	{
		this.maxmarks=maxmarks;
	}
	public String getmaxmarks()
	{
		return maxmarks;
	}
	public void setmarks(String marks)
	{
		this.marks=marks;
	}
	public String getmarks()
	{
		return marks;
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
	public String getoptionD()
	{
		return optionD;
	}
	public void setsqid(String sqid)
	{
		this.sqid=sqid;
	}
	public String getsqid()
	{
		return sqid;
	}
	public void setfeedback(String feedback)
	{
		this.feedback=feedback;
	}
	public String getfeedback()
	{
		return feedback;
	}
	public void setAnswer(String Answer)
	{
		this.Answer=Answer;
	}
	public String getAnswer()
	{
		return Answer;
	}
	public void setQuestion(String Question)
	{
		this.Question=Question;
	}
	public String getQuestion()
	{
		return Question;
	}
	public void setGrade(String Grade)
	{
		this.Grade=Grade;
	}
	public String getGrade()
	{
		return Grade;
	}
	public void setExpiryDate(String ExpiryDate)
	{
		this.ExpiryDate=ExpiryDate;
	}
	public String getExpiryDate()
	{
		return ExpiryDate;
	}
	/*public void setPermission(String Permission)
	{
		this.Permission=Permission;
	}
	public String getPermission()
	{
		return Permission;
	}*/
}
