package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt; 

/*
 * @(#)Close_Quiz.java
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
import java.sql.Date;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;

import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.QuizDetail;  
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 *   @author  <a href="nksinghiitk@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
*/


public class Close_Quiz extends  SecureScreen
{
	public void doBuildTemplate(RunData data, Context context)
        {
        	try
                {
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";	
                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
                        String  ViewDraft = user.getTemp("ViewDraft","").toString();	
                     	context.put("ViewDraft",ViewDraft);
			String courseid=(String)user.getTemp("course_id");
                        String username1=(String)user.getTemp("course_name");
                        String username=data.getUser().getName();
                        context.put("coursename",username1);
			String quiztitle=pp.getString("quiztitle","quiztitle");
			int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));	
			Calendar calendar=Calendar.getInstance();
                        int curmin=calendar.get(Calendar.HOUR);
                        int am_pm=calendar.get(Calendar.AM_PM);
                        if(am_pm == 1)
                                 curmin= curmin+12;
                	curmin= (curmin*60) +(calendar.get(Calendar.MINUTE));  
                        boolean startQuiz=false;
			Criteria crit=new Criteria();
        	        crit.add(QuizPeer.CID,courseid);
                	List u = QuizPeer.doSelect(crit);
			String datestr1="-";
                	for(int i=0;i<u.size();i++)
                        {
				String str6="";
                               	Quiz element=(Quiz)(u.get(i));
				String str1=(element.getPostDate().toString());
				str1=str1.substring(0,10);
				str1=str1.replaceAll(datestr1,str6);
				int date1=Integer.parseInt(str1);
				if(date1 == curdate)
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
					if((endhour > curmin) && ( starthour <= curmin))
					{ 
						quiztitle=(element.getQuizId());
						startQuiz=true;
						context.put("totaltime",(endhour-curmin));	
					}
				}
			}
			u=null;
			if(startQuiz==true){		
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quiztitle);
			File f1=new File(createquiz);
                        if(!f1.exists())
                        	f1.mkdirs();	
			TopicMetaDataXmlReader topicmetadata1=null;
                        Vector VectorQuiz=new Vector();
                        Vector entry=new Vector();
                        Vector vct=new Vector();
			File f2=new File(createquiz+"/Quizid.xml");
			if(!f2.exists())
			{
				context.put("quiztitle",quiztitle);
				context.put("questionscreations","questionnotcreated");
				msg=MultilingualUtil.ConvertedString("quiz_msg3",LangFile);
				data.setMessage(msg);
				return;
			}
			else
				context.put("questionscreations","questioncreated");	
			
			if( ViewDraft.equals("ViewDraftQuiz") )
			{
				
				String createquiz1=createquiz+"/Draft";
				int userid=UserUtil.getUID(username);
        	                String str4=Integer.toString(userid);
                        	f2= new File(createquiz1+"/"+str4+".xml");
				if(f2.exists()){
					topicmetadata1=new TopicMetaDataXmlReader(createquiz1+"/"+str4+".xml");
		                        VectorQuiz=topicmetadata1.getQuizDetails();
					topicmetadata1=null;
					topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
	                                vct=topicmetadata1.getQuizDetails();
					for(int i=0;i<vct.size();i++)
                	                {
                        	                if(i==0)
                                	        {
                                                	String Quizname=((FileEntry) vct.elementAt(i)).getoptionA();
                                                	String totalmarks=((FileEntry) vct.elementAt(i)).getoptionC();
                                                	String passmarks=((FileEntry) vct.elementAt(i)).getquestion();
                                                	String noofquestion=((FileEntry) vct.elementAt(i)).getinstructorans();
                                                	context.put("Quizname",Quizname);
                                                	context.put("totalmarks",totalmarks);
                                                	context.put("noofquestion",noofquestion);
                                                	context.put("passmarks",passmarks);
							break;
                                        	}
					}
				} //if	
				else
				{
					msg=MultilingualUtil.ConvertedString("quiz_msg28",LangFile);
					context.put("questionscreations","questionnotcreated");	
					data.setMessage(msg);		
					return;
				}
			}
			else
			{
				topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
				VectorQuiz=topicmetadata1.getQuizDetails();
				f2= new File(createquiz+"/Quizid.xml");
			}
			if(f2.exists() && VectorQuiz!=null)
                        {
				for(int i=0;i<VectorQuiz.size();i++)
                                {
					
					if(i==0 && !(ViewDraft.equals("ViewDraftQuiz")))
                                        {
						String Quizname=((FileEntry) VectorQuiz.elementAt(i)).getoptionA();	
						String totalmarks=((FileEntry) VectorQuiz.elementAt(i)).getoptionC();	
  						String passmarks=((FileEntry) VectorQuiz.elementAt(i)).getquestion();	
					        String noofquestion=((FileEntry) VectorQuiz.elementAt(i)).getinstructorans();	
						context.put("Quizname",Quizname);
						context.put("totalmarks",totalmarks);
						context.put("noofquestion",noofquestion);
						context.put("passmarks",passmarks);
					}
					else
					{
						String option_a=((FileEntry) VectorQuiz.elementAt(i)).getoptionA();
	                                        String option_c=((FileEntry) VectorQuiz.elementAt(i)).getoptionC();
        	                                String option_b=((FileEntry) VectorQuiz.elementAt(i)).getoptionB();
                	                        String option_d=((FileEntry) VectorQuiz.elementAt(i)).getoptionD();
						String Question=((FileEntry) VectorQuiz.elementAt(i)).getquestion();
                        	                String tmarks=((FileEntry) VectorQuiz.elementAt(i)).getmarksperqustion();
                        	                String Typeofquestion=((FileEntry) VectorQuiz.elementAt(i)).getTypeofquestion();
                        	                String questionsid=((FileEntry) VectorQuiz.elementAt(i)).getquestionid();
						QuizDetail qDetail= new QuizDetail();
                                                qDetail.setquestionid(questionsid);
                                                qDetail.setoptionA(option_a);
                                                qDetail.setoptionB(option_b);
                                                qDetail.setoptionC(option_c);
                                                qDetail.setoptionD(option_d);
                                                qDetail.setQuestion(Question);
                                                qDetail.setmarks(tmarks);
                                                qDetail.setExpiryDate(Integer.toString(i));
                                                qDetail.setQuiztype(Typeofquestion);
                                                entry.addElement(qDetail);
	
					} //if else
				} //for
			} //if 
			String Mode=data.getParameters().getString("mode");
	  		context.put("mode","All");
			int AdminConf = 1;
			context.put("AdminConf_str",Integer.toString(AdminConf));
			String startIndex1=pp.getString("startIndex","0");
                        int startIndex=Integer.parseInt(startIndex1);
			int arv=vct.size();
			if(ViewDraft.equals("ViewDraftQuiz") && (startIndex == arv))
			{
				startIndex=0;
			}
       	                int t_size=entry.size();
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
               		context.put("contentvalue",splitlist);
                       	context.put("totalentry1",(entry.size()));
			context.put("start",startIndex);
		entry=null;
		vct=null;
		VectorQuiz=null;
		}
		context.put("startQuiz",startQuiz);
		context.put("quiztitle",quiztitle);	
		} 
		catch(Exception e) {
			ErrorDumpUtil.ErrorLog("The exception in close quiz screen::"+e);
			data.setMessage("See ExceptionLog:: ");		
		}
	}
}			
