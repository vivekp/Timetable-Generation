package org.iitk.brihaspati.modules.utils;

/*
 * @(#)AssignmentDetail
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
 *  @author <a href="mailto:arvindjss17@yahoo.co.in"> Arvind Pal </a>
 *
*/


public class AssignmentDetail
{
        private String Studentfile;
        private String Assignmentfile;
        private String Studentansfile;
        private String Instructoransfile;
        private String maxgrade;
        private String grade;
        private String answerfile;
        private String feedback;
	private String Duedate;
	private String AssignmentDuedate;
        private String Assignmentdate;
        private String Studentname;
	public void setStudentname(String Studentname)
        {
                this.Studentname=Studentname;
        }
        public String getStudentname()
        {
                return Studentname;
        }
	
		
        public void setStudentfile(String Studentfile)
        {
                this.Studentfile=Studentfile;
        }
        public String getStudentfile()
        {
                return Studentfile;
        }

        public void setAssignmentfile(String Assignmentfile)
        {
                this.Assignmentfile=Assignmentfile;
        }
        public String getAssignmentfile()
        {
                return Assignmentfile;
        }
        public void setStudentansfile(String Studentansfile)
        {
                this.Studentansfile=Studentansfile;
        }
        public String getStudentansfile()
        {
                return Studentansfile;
        }
	public void setInstructoransfile(String Instructoransfile)
        {
                this.Instructoransfile=Instructoransfile;
        }
        public String getInstructoransfile()
        {
                return Instructoransfile;
        }
	public void setmaxgrade(String maxgrade)
        {
                this.maxgrade=maxgrade;
        }
        public String getmaxgrade()
        {
                return maxgrade;
        }
		
	public void setgrade(String grade)
        {
                this.grade=grade;
        }
        public String getgrade()
        {
                return grade;
        }


	public void setanswerfile(String answerfile)
        {
                this.answerfile=answerfile;
        }
        public String getanswerfile()
        {
                return answerfile;
        }
	
	
	public void setfeedback(String feedback)
        {
                this.feedback=feedback;
        }
        public String getfeedback()
        {
                return feedback;
        }

	public void setDuedate(String Duedate)
        {
                this.Duedate=Duedate;
        }
        public String getDuedate()
        {
                return Duedate;
        }


	
	public void setAssignmentdate(String Assignmentdate)
        {
                this.Assignmentdate=Assignmentdate;
        }
        public String getAssignmentdate()
        {
                return Assignmentdate;
        }
	
	public void setAssignmentDuedate(String AssignmentDuedate)
        {
                this.AssignmentDuedate=AssignmentDuedate;
        }
        public String getAssignmentDuedate()
        {
                return AssignmentDuedate;
        }
	
}
