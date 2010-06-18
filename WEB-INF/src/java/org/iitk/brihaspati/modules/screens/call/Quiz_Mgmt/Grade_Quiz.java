package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt; 

/*
 * @(#)Grade_Quiz.java
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
 */


import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.QuizDetail;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 *   @author  <a href="nksinghiitk@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */

public class Grade_Quiz extends  SecureScreen

{
        /**
        * @param data RunData instance
	* @return nothing
        * @try and catch Identifies statements that user want to monitor
        * and catch for exceptoin.
        */
        
	public void doBuildTemplate(RunData data, Context context)
        {
                try
                {
			String LangFile=(String)data.getUser().getTemp("LangFile");
			context.put("test","test");
                        String stat = data.getParameters().getString("status");
                        context.put("status",stat);
                        User user=data.getUser();
			String uname=user.getName();
                        String uid=Integer.toString(UserUtil.getUID(uname));
			context.put("uid",uid);
                        ParameterParser pp=data.getParameters();
                        String courseid=(String)user.getTemp("course_id");
			String Quizid=pp.getString("Quizid","0");
			
			if(Quizid.equals("0"))
			{
				Vector Quizid2=new Vector();
                                Calendar calendar=Calendar.getInstance();
                                int curmin=calendar.get(Calendar.HOUR);
                                int am_pm=calendar.get(Calendar.AM_PM);
                                if(am_pm == 1)
                                curmin= curmin+12;
                                curmin= (curmin*60) +(calendar.get(Calendar.MINUTE));
                                Criteria crit=new Criteria();
                                crit=new Criteria();
				crit.add(QuizPeer.CID,courseid);
				List u = QuizPeer.doSelect(crit);
                                String datestr1="-";
                                if(u.size() != 0)
                                {
                                        for(int i=0;i<u.size();i++)
                                        {
                                                Quiz element=(Quiz)(u.get(i));
                                                String str1=element.getPostDate().toString();
                                                str1=str1.substring(0,10);
                                                str1=str1.replaceAll(datestr1,"");
                                                int date1=Integer.parseInt(str1);
                                                int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
                                                if(date1<=curdate)
                                                {
                                                        String str2=(element.getStartTime().toString());
                                                        String str3=(element.getEndTime().toString());
                                                        str2=str2.substring(0,5);
                                                        str3=str3.substring(0,5);
                                                        String [] str2array = str2.split(":");
                                                        String [] str3array = str3.split(":");
                                                        str2="";str3="";
							int starthour=0;int endhour=0;
                                                        for(int j=0;j<2;j++)
                                                        {
                                                                if(j==0){
                                                                        starthour=Integer.parseInt(str2array[j])*60;
                                                                        endhour=Integer.parseInt(str3array[j])*60;
                                                                }
                                                                else {
									starthour=starthour+Integer.parseInt(str2array[j]);
                                                                        endhour=endhour+Integer.parseInt(str3array[j]);
                                                                }
                                                        }
                                                        //link startQuiz is open
							if(date1== curdate)
							{
                                                        	if(endhour < curmin)
                                                      	         	Quizid2.add(element.getQuizId());
							}
							else
								Quizid2.add(element.getQuizId());
                                                }
                                        }
                                }
				context.put("Quizid2",Quizid2);
				Quizid2=null;
			}
			else {
				context.put("Quizid1",Quizid);
				context.put("slct","slcted");	
				
			}
			
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+Quizid);	
			String Userlist=pp.getString("userList","0");
			if(Userlist.equals("0"))
			{
				int g_id=GroupUtil.getGID(courseid);
                        	Vector userList2=UserGroupRoleUtil.getUDetail(g_id,3);
                        	context.put("userList2",userList2);
				userList2=null;
			}
			else
				context.put("userList1",Userlist);
			try{	
			if(!(Quizid.equals("0"))){
				Vector quizid=new Vector();
				quizid.add("Quiz_1");
				context.put("quizid",quizid);
				quizid=null;
				String Role=(String)user.getTemp("role");
				/**  Path */ 
					
				String createquiz1=createquiz;
				String createquiz3=createquiz;
				String createquiz2=createquiz+"/Student_Quiz";
				
				/**  Path */
				
				int userid=UserUtil.getUID(Userlist);
				String str4=Integer.toString(userid);
				File cheked=new File(createquiz2+"/"+str4+".xml");
				if(!(cheked.exists()))
				{
					String msg=MultilingualUtil.ConvertedString("quiz_msg13",LangFile);
					data.setMessage(msg);
					return;
				}
				/**   Quetion are submited */	
				Vector Gradelist=new Vector();
				File studentfile=new File(createquiz2+"/"+str4+".xml");
				if(studentfile.exists()){
					TopicMetaDataXmlReader topicmetadata=null;
                                	topicmetadata=new TopicMetaDataXmlReader(createquiz2+"/"+str4+".xml");
                                	Gradelist=topicmetadata.getAssignmentDetails1();
				}
				/**   Quetion are submited */
				/**   Check Quetion are submited Question banks in particular Quid id  */
				File studentfile1=new File(createquiz1+"/Quizid.xml");
				Vector VectorQuiz=new Vector();
                                if(studentfile1.exists()) {
					TopicMetaDataXmlReader topicmetadata1=null;
                        	        topicmetadata1=new TopicMetaDataXmlReader(createquiz1+"/Quizid.xml");
                               		VectorQuiz=topicmetadata1.getQuizDetails();
				}	
				/**   Check Quetion are submited Question banks in particular Quid id  */
				/** Given grade from Quiz/quizid/1080.xml */
				Vector Gradelist1=new Vector();
				studentfile=new File(createquiz3+"/"+str4+".xml");
                                if(studentfile.exists()){
					TopicMetaDataXmlReader topicmetadata2=null;
					topicmetadata2=new TopicMetaDataXmlReader(createquiz3+"/"+str4+".xml");
					Gradelist1=topicmetadata2.getAssignmentDetails1();
				}
					
				Vector entry=new Vector();
				/** Given grade from Quiz/quizid/1080.xml */
				boolean longtype=true;
				for(int kk=0;kk<Gradelist.size();kk++)
                                {
		                        String ans1=((FileEntry) Gradelist.elementAt(kk)).getUserName().trim();
                                        String studentans=((FileEntry) Gradelist.elementAt(kk)).getGrade().trim();
					ErrorDumpUtil.ErrorLog("  "+VectorQuiz.size());	
					for(int i=1;i<VectorQuiz.size();i++)
                                        {
						String ans=((FileEntry) VectorQuiz.elementAt(i)).getinstructorans().trim();
                                                String Question=((FileEntry) VectorQuiz.elementAt(i)).getquestion().trim();
                                                String tmarks=((FileEntry) VectorQuiz.elementAt(i)).getmarksperqustion().trim();
                                                String quizquestionid=((FileEntry) VectorQuiz.elementAt(i)).getquestionid().trim();
                                                String Typeofquestion=((FileEntry) VectorQuiz.elementAt(i)).getTypeofquestion().trim();
						int questionid=Integer.parseInt(ans1);
                                                int intuestionid=Integer.parseInt(quizquestionid);
						
                                                if((Typeofquestion.equals("Short") || Typeofquestion.equals("Long")) && (questionid == intuestionid )) {
						
							longtype=false;
							QuizDetail qDetail= new QuizDetail();
                                                	qDetail.setinsAnswer(ans);
                                               		qDetail.setQuestion(Question);
                                                	qDetail.setmarks(tmarks);
							qDetail.setquestionid(ans1);
                                                        qDetail.setstudentAns(studentans);
                                                        boolean checktrue=true;
							for(int k=0;k<Gradelist1.size();k++)  {
                                                        	String ans2=((FileEntry) Gradelist1.elementAt(k)).getUserName().trim();
                                                                int questionid2=Integer.parseInt(ans2);
                                                                if((questionid2 == questionid) && checktrue) {
									String studentans3=((FileEntry) Gradelist1.elementAt(k)).getGrade().trim();
                                                                        String studentans4=((FileEntry) Gradelist1.elementAt(k)).getfeedback().trim();
									qDetail.setoptionA(studentans3);
                                                       			qDetail.setoptionB(studentans4);
									checktrue=false;
                                                                        break;
								}
							}
							if(checktrue){
								qDetail.setoptionA("0");
                                                        	qDetail.setoptionB("");
                                                        }
        		                                entry.addElement(qDetail);

						}
					}
				}
				if(longtype)
				{
        	                        data.setMessage(MultilingualUtil.ConvertedString("quiz_msg32",LangFile));
				//	data.setMessage("Long type or Short type Question are not available");
					return;
       				}
				context.put("longtype",longtype);
				
				//if(totalmarks.equals(""))
				//	context.put("totalmarks","0");
				//else	
				//	context.put("totalmarks",totalmarks);
				Integer ii = new Integer(1000);
		                context.put("INT",ii);

				/**
                	        * Get courseid  and coursename for the user currently logged in
                        	*Put it in the context for Using in templates
	                        * @see UserUtil in Util.
        	                */
			
				String Mode=data.getParameters().getString("mode");
                        	context.put("mode","All");
	                        int AdminConf = 1;
        	                context.put("AdminConf_str",Integer.toString(AdminConf));
                	        
                        	String startIndex1=pp.getString("startIndex","0");
				int startIndex=Integer.parseInt(startIndex1);
        	                int t_size=entry.size();
				context.put("long_short",entry.size());
                	        String status=new String();
                        	status="notempty";
	                        int value[]=new int[7];
        	                value=ListManagement.linkVisibility(startIndex,t_size,AdminConf);
                	        int k=value[6];
                        	context.put("k",String.valueOf(k));
	                        Integer total_size=new Integer(t_size);
        	                context.put("total_size",total_size);
                	        int eI=value[1];
                        	Integer endIndex=new Integer(eI);
	                        context.put("endIndex",endIndex);
        	                int check_first=value[2];
                	        context.put("check_first",String.valueOf(check_first));
                        	int check_pre=value[3];
	                        context.put("check_pre",String.valueOf(check_pre));
        	                int check_last1=value[4];
                	        context.put("check_last1",String.valueOf(check_last1));
                        	int check_last=value[5];
	                        context.put("check_last",String.valueOf(check_last));
        	                context.put("startIndex",String.valueOf(eI));
                	        Vector splitlist=ListManagement.listDivide(entry,startIndex,AdminConf);
				context.put("entry",splitlist);  
				context.put("slct","slcted");
			}
			else
				context.put("slct","slct");
			}		
			catch(Exception e) {
				ErrorDumpUtil.ErrorLog("The exception in grade quiz in line 333::"+e);
				data.setMessage("See ExceptionLog!! ");	
			}
		}
		catch(Exception e) {
				ErrorDumpUtil.ErrorLog("The exception in gradequiz class ::"+e);
				data.setMessage("See ExceptionLog!!");
		}
	}
}			
