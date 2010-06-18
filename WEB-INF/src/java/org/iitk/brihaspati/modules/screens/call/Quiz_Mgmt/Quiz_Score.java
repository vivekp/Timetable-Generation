package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/*
 * @(#)Quiz_Score.java	
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

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import java.util.Vector;
import java.io.File;
import java.util.StringTokenizer;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import java.util.Date;
import org.apache.torque.util.Criteria;
import java.util.List;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
//import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
//import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.DbDetail;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.om.Quiz;
import java.util.Calendar;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;


/**
 *   This class contains code for all discussions in workgroup
 *   Compose a discussion and reply.
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */



public class Quiz_Score extends SecureScreen
{
	 MultilingualUtil mu=new MultilingualUtil();

    	public void doBuildTemplate( RunData data, Context context )
	{
		try
                {
                	String file=data.getUser().getTemp("LangFile").toString();
			String stat = data.getParameters().getString("status");
                        ParameterParser pp=data.getParameters();
			context.put("status",stat);
                        User user=data.getUser();
                        String uname=user.getName();
                        String courseid=(String)user.getTemp("course_id");
                        String uid=Integer.toString(UserUtil.getUID(uname));
			  
			Vector Quizid1=new Vector();
                        Calendar calendar=Calendar.getInstance();
                        int curmin=calendar.get(Calendar.HOUR);
                        //int curmin=calendar.get(Calendar.MINUTE);
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
                                                        	Quizid1.add(element.getQuizId());
                                                }
                                                else
                                                	Quizid1.add(element.getQuizId());
					}
				}
			}
			context.put("Quizid",Quizid1);
                        String Quizid =pp.getString("Quizid","");
                        if(Quizid.equals("")) {
				context.put("checkedQuiz","Uncheked");
				return;
			}
			context.put("checkedQuiz","cheked");
			
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+Quizid);
			context.put("quizid",Quizid);
                        String createquiz1=createquiz;
			String UserName=data.getUser().getName();
                        int userid=UserUtil.getUID(UserName);
                        String str4=Integer.toString(userid);
			                 
			String totalmarks="";
                        String Quizname="";
                        String noofquestion="";
                        String passmarks="";
			Vector Gradelist1=new Vector();
			String Typeofquestion="";	
			
						
			String createquiz2=createquiz+"/Student_Quiz";
			File f1= new File(createquiz2+"/"+str4+".xml");
			
			
                        if(f1.exists())
                        {
                        	try {	
				File f2= new File(createquiz1+"/Quizid.xml");
				File f3= new File(createquiz2+"/"+str4+".xml");	
				if(f2.exists() && f3.exists() )
                        	{
					TopicMetaDataXmlReader topicmetadata=null;
	                                Vector Gradelist=new Vector();
        	                        topicmetadata=new TopicMetaDataXmlReader(createquiz2+"/"+str4+".xml");
                	                Gradelist=topicmetadata.getAssignmentDetails1();
				
					topicmetadata=null;
                                	Vector Quiz1=new Vector();
                                	topicmetadata=new TopicMetaDataXmlReader(createquiz1+"/Quizid.xml");
                                	Quiz1=topicmetadata.getQuizDetails();
					//xmlreader
					for(int i=0;i<Quiz1.size();i++)
	                                {
        	                                if(i==0){
                	                                Quizname=((FileEntry) Quiz1.elementAt(i)).getoptionA();
                                                        totalmarks=((FileEntry) Quiz1.elementAt(i)).getoptionC();
                                                        noofquestion=((FileEntry) Quiz1.elementAt(i)).getoptionB();
                                                        passmarks=((FileEntry) Quiz1.elementAt(i)).getquestion();
                         			}
						//else condition removed by jai
					} //for     					
				}//if f2
				}catch(Exception e){}
				try{
					File f4= new File(createquiz2+"/"+str4+".xml");	
					if(f4.exists()) {
					int finalgrade=0;
					Vector detail=new Vector();
					TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(createquiz+"/"+str4+".xml");
					detail=topicmetadata.getAssignmentDetails1();
                                	for(int k=0;k<detail.size();k++)
                                	{
	                                        String ans=( (FileEntry) detail.elementAt(k)).getGrade();
        	                                int intans=Integer.parseInt(ans);
                	                        finalgrade +=intans;
                        	        }

					context.put("marks",finalgrade);
                                	context.put("quizid",Quizname);
                                	context.put("mmarks",totalmarks);
                                	context.put("passingmarks",passmarks);
                                	finalgrade=(finalgrade*100)/(Integer.parseInt(totalmarks));
                                	context.put("pscore",finalgrade);
                                	int finalgrade1=(Integer.parseInt(passmarks)*100)/(Integer.parseInt(totalmarks));
                                	if(finalgrade >= finalgrade1)
                                        	context.put("pass","Passed");
                                	else
                                        	context.put("pass","failed");
					}
				}catch(Exception e){ErrorDumpUtil.ErrorLog("Error in Quiz  Score --> "+e.getMessage());}
			}
                        else
				data.setMessage(MultilingualUtil.ConvertedString("quiz_msg13",file)); 
		}	
		catch(Exception ex)
        	{
			ErrorDumpUtil.ErrorLog("The exception in detail Score Quiz file!!"+ex); data.setMessage("See ExceptionLog !! ");
                }
	}
}


