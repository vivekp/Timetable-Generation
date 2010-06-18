package org.iitk.brihaspati.modules.actions;

/*
 * @(#) Quiz_Action.java
 * Copyright (c) 2007 ETRG,IIT Kanpur.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * Redistribution in binary form must reproducuce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Contributors: Members of ETRG, I.I.T. Kanpur
 */

import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;
import java.util.StringTokenizer;

import java.sql.Time;
import java.sql.Date;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.NoticeReceive;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;

import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.QuizDetail;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.StringUtil;

/**
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
*/



public class Quiz_Action extends SecureAction
{
        public void fileUpload(RunData data,Context context)
	{
		data.setScreenTemplate("call,UserMgmt_User,UploadMarks.vm");		
	}
	public void checkfinishedQuestion(RunData data,Context context){
		try {
			ErrorDumpUtil.ErrorLog("We are in finished question");	
			String LangFile=data.getUser().getTemp("LangFile").toString();
                	String msg="";
                	ParameterParser pp=data.getParameters();
                	User user=data.getUser();	
			String cid=(String)user.getTemp("course_id");
			String quizid=pp.getString("quizid","");
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz"+"/"+quizid);		
			TopicMetaDataXmlReader topicmetadata1=null;
                	Vector VectorQuiz=new Vector();
                	topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
                	VectorQuiz=topicmetadata1.getQuizDetails();
                	File f2= new File(createquiz+"/Quizid.xml");
                	String noofquestion="";
                	if(f2.exists()) {
                		if(VectorQuiz!=null) {
					for(int i=0;i<1;i++) {
					      	noofquestion=((FileEntry)VectorQuiz.elementAt(i)).getinstructorans().trim();
                                                break;
					}
				}
                        	if(Integer.parseInt(noofquestion)==(VectorQuiz.size()-1)) {
					data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Detail.vm");
                        		data.setMessage(MultilingualUtil.ConvertedString("quiz_msg36",LangFile));
                        	}
				else
					context.put("quizid",quizid);
			}
			VectorQuiz=null;
		} 
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("We are in finished question"+e);
			data.setMessage("See Exception log or tell to Administrator");
			}


	}

	public void doSchedule(RunData data, Context context)
        {
                try
                {
                        String LangFile=data.getUser().getTemp("LangFile").toString();
                        String msg="";
                        ParameterParser pp=data.getParameters();
                        User user=data.getUser();
		// Geting current time fron server for comparing to schedule quiz
			Calendar calendar=Calendar.getInstance();
                        int curmin=calendar.get(Calendar.HOUR);
                        int min=calendar.get(Calendar.MINUTE);
                        int am_pm=calendar.get(Calendar.AM_PM);
                        if(am_pm == 1)
                                curmin= curmin+12;

                        String uname=user.getName();
                        String uid=Integer.toString(UserUtil.getUID(uname));
                        String cid=(String)user.getTemp("course_id");
                        String qid=pp.get("qid");
                        String title=pp.get("title");
                        title = StringUtil.replaceXmlSpecialCharacters(title);
                        String year=pp.get("Start_year");
                        String month=pp.get("Start_mon");
                        String day=pp.get("Start_day");
                        String date=year+"-"+month+"-"+day;
                        String date1=date.replaceAll("-","");
                        int intdate=Integer.parseInt(date1);
                        int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
                        if(intdate < curdate)
                        {
                                msg=MultilingualUtil.ConvertedString("quiz_msg26",LangFile);
                                data.setMessage(msg);
                                return;
                        }
                        Date qdate=Date.valueOf(date);
                        String sh=pp.getString("Start_hr");

                        int s_hour=Integer.parseInt(sh);
                        String sm=pp.getString("Start_min");
                        String sTime= sh + ":" + sm+":00";
                        Time stime=Time.valueOf(sTime);
                        String eh=pp.getString("Last_hr");
                        int e_hour=Integer.parseInt(eh);
                        String em=pp.getString("Last_min");
			String eTime= eh + ":"  + em+":00";
                        Time etime=Time.valueOf(eTime);
                        String mmarks=pp.get("marks");
                        String exp="30";
                        //time...
                        int starttime=(s_hour*60) + Integer.parseInt(sm);
                        int endtime=(e_hour*60) +Integer.parseInt(em);
                        if(starttime >= endtime)
                        {
                                msg=MultilingualUtil.ConvertedString("quiz_msg27",LangFile);
                                data.setMessage(msg);
                                return;
                        }
			if(intdate == curdate)
                        {
                                if(s_hour <= curmin )
                                {
                                        if((Integer.parseInt(sm)) < min )
                                        {
                                                msg=MultilingualUtil.ConvertedString("quiz_msg33",LangFile);
                                                data.setMessage(msg);
                                                return;
                                        }
                                }
                        }
                        Criteria crit=new Criteria();
                        crit.add(QuizPeer.CID,cid);
                        List li=QuizPeer.doSelect(crit);
                        for(int i=0;i<li.size();i++)
                        {
                                String str6="";
                                Quiz element=(Quiz)(li.get(i));
                                String str1=(element.getPostDate().toString());
                                str1=str1.substring(0,10);
                                str1=str1.replaceAll("-",str6);
                                int date2=Integer.parseInt(str1);
                                if(date2 == intdate)
                                {
                                        String str3=(element.getEndTime().toString());
                                        String str4=(element.getStartTime().toString());
                                        /**   Start time   */
                                        str4=str4.substring(0,5);
                                        String starthourcheck=str4.substring(0,2);
                                        String startmincheck=str4.substring(3,5);
			 		int startcheckmin=((Integer.parseInt(starthourcheck))*60) + Integer.parseInt(startmincheck);
                                        /**  End Start Time  */
                                        str3=str3.substring(0,5);
                                        String hourcheck=str3.substring(0,2);
                                        String mincheck=str3.substring(3,5);
                                        int checkmin=((Integer.parseInt(hourcheck))*60) + Integer.parseInt(mincheck);
                                        if(startcheckmin <= starttime && starttime <=checkmin){
                                                msg=MultilingualUtil.ConvertedString("quiz_msg30",LangFile);
                                                data.setMessage(msg);
                                                return;
                                        }
                                        if(startcheckmin <= endtime && endtime <=checkmin){
                                                return;
                                        }
                                } //if
                        }
			li=null;
                        crit.add(QuizPeer.QUIZ_ID,qid);
                        crit.add(QuizPeer.USER_ID,uid);
                        crit.add(QuizPeer.CID,cid);
                        crit.add(QuizPeer.QUIZ_TITLE,title);
                        crit.add(QuizPeer.POST_DATE,qdate);
                        crit.add(QuizPeer.START_TIME,stime);
                        crit.add(QuizPeer.END_TIME,etime);
                        crit.add(QuizPeer.MAX_MARKS,mmarks);
                        crit.add(QuizPeer.EXPIRY_DATE,Date.valueOf(ExpiryUtil.getExpired(date,Integer.parseInt(exp))));
                        if(s_hour <= e_hour){
                        	QuizPeer.doInsert(crit);
                     		msg=MultilingualUtil.ConvertedString("quiz_msg1",LangFile);
                        	data.setMessage(msg);
                        }
                        else{
                                msg=MultilingualUtil.ConvertedString("quiz_msg27",LangFile);
                                data.setMessage(msg);
			}
                }
		  catch(Exception e)
                {
			ErrorDumpUtil.ErrorLog("The Exception in quiz scheduling::"+e);
			 data.setMessage("See ExceptionLog !! ");
                }
        }
	public void doCreate(RunData data,Context context)
        {
		String strcheck="";
		String LangFile=data.getUser().getTemp("LangFile").toString();
		try{
			
			ParameterParser pp=data.getParameters();
			User user=data.getUser();     
			String typeList=pp.getString("typeList");   	
			String courseid=(String)user.getTemp("course_id",""); 	
			String quizid=pp.getString("quizid");
			String maxmarks=pp.getString("maxmarks");
			String marks=pp.getString("marks");
			String noofques=pp.getString("noofques");
			String passmarks=pp.getString("passmarks");
			String Q8="";
	                String Q9="";
	                String Q2="";
			context.put("marks",marks);	
			context.put("quizid",quizid);	
			strcheck=maxmarks+marks+noofques+passmarks;
			File f3= new File(courseid);
	                if(!f3.exists())
        	        	f3.mkdirs();
				String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
				File file1=new File(createquiz);
				if(!file1.exists())
	 	        		file1.mkdirs();	
				File file=new File(createquiz+"/Quizid.xml");
				if(file.exists())
				{
					return;	
				}
				XmlWriter xmlwriter=null;
				if(!file.exists())
        	       		{
	              			TopicMetaDataXmlWriter.writeWithRootOnly1(file.getAbsolutePath());
        	       			xmlwriter=new XmlWriter(createquiz+"/Quizid.xml");
				}
			        int kk=-1;
				xmlwriter=TopicMetaDataXmlWriter.WriteXml_New6(createquiz,"/Quizid.xml",kk);
				/**
				* REplace charector 
				*
				*/
				
        			TopicMetaDataXmlWriter.appendQuiz(xmlwriter,quizid,Q2,maxmarks,Q9,noofques,typeList,passmarks,Q8,marks);
	     	         	xmlwriter.writeXmlFile();
                		data.setScreenTemplate("call,Quiz_Mgmt,Multi_Type.vm");
		} //try
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The Exception in Creat method::"+e);
			 data.setMessage("See ExceptionLog !! ");
		}
	}
	public void doRegister(RunData data,Context context)
        {
		try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			
			ParameterParser pp=data.getParameters();
                        User user=data.getUser();
                        String username=user.getName();
                        //int userid=UserUtil.getUID(username);
                        //String str4=Integer.toString(userid);
                        
			String str4=Integer.toString(UserUtil.getUID(username));
                        String courseid=(String)user.getTemp("course_id","");
                        String shorttype=pp.getString("shorttype");
                        String message=pp.getString("message","");
			String startIndex=pp.getString("startIndex");
			String quiztitle=pp.getString("quiztitle");
			context.put("quiztitle",quiztitle);
			context.put("startIndex",startIndex);
			String mid_delete = pp.getString("deleteFileNames","");
			String msg_idd=""; 
			startIndex = pp.getString("idques");
			String ViewDraft=pp.getString("ViewDraft");
                        context.put("ViewDraft",ViewDraft);
			if(message.length()<1)	
			{	if(!mid_delete.equals(""))
                        	{
					StringTokenizer st=new StringTokenizer(mid_delete,"^");
                                	for(int j=0;st.hasMoreTokens();j++)
						if(msg_idd.length()!=0)
							msg_idd= msg_idd+","+st.nextToken();
						else
						msg_idd= st.nextToken();
			}	}	
			else
				 msg_idd=message; 
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quiztitle+"/Student_Quiz");
                        File file1=new File(createquiz);
                        if(!file1.exists())
				file1.mkdirs();
                        File file=new File(createquiz+"/"+str4+".xml");
                        XmlWriter xmlwriter=null;
			boolean flag=true;
			try{
                        	if(!file.exists())
                        	{
                        	        TopicMetaDataXmlWriter.writeWithRootOnly1(file.getAbsolutePath());
	                                xmlwriter=new XmlWriter(createquiz+"/"+str4+".xml");
                        	}
				else
				{
					Vector detail=new Vector();
                                	TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(createquiz+"/"+str4+".xml");
                                	detail=topicmetadata.getAssignmentDetails1();
					String msg="";
					for(int i=0;i<detail.size();i++)
					{
						String ans=((FileEntry)detail.elementAt(i)).getUserName().trim();
						startIndex=startIndex.trim();
						if(ans.equals(startIndex)){
						String msg1=MultilingualUtil.ConvertedString("quiz_msg21",LangFile);
						data.setMessage(msg1);
						flag=false;
						}
					}	detail=null;	
				}
			}
			catch(Exception e){
			ErrorDumpUtil.ErrorLog("The Exception in reading/writing xml in line 333::"+e);
			 data.setMessage("See ExceptionLog !! ");
			}
			if(flag){
				//String temp="";
				String temp1="";
                        	Vector xmlVct=new Vector();
				xmlVct.add(-1);
				startIndex=startIndex.trim();
                        	xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/"+str4+".xml",xmlVct);
			       	TopicMetaDataXmlWriter.appendGrade(xmlwriter,startIndex,msg_idd,temp1);
                        	xmlwriter.writeXmlFile();
				String msg=MultilingualUtil.ConvertedString("quiz_msg17",LangFile);
				data.setMessage(msg);
				
			}
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The Exception in submit method::"+e);
			data.setMessage("See ExceptionLog !! ");
		}
	}	
	
	public void Go(RunData data,Context context)
        {
                try{
                	
		        ParameterParser pp=data.getParameters();
        
	                String typeList=pp.getString("typeList");
                        String topicList=pp.getString("topicList");
                        String userList=pp.getString("userList");
			String Quizid=pp.getString("Quizid");
	
			context.put("Quizid",Quizid);
			context.put("typeList",typeList);
                	context.put("topicList",topicList);
                	context.put("uerList",userList);
					
                }
		catch(Exception ex) {
			ErrorDumpUtil.ErrorLog("The Exception in Go method::"+ex);
			data.setMessage("See ExceptionLog !! ");
		} 
        }
	public void doAddcreate(RunData data,Context context)
        {
                try{
                        ParameterParser pp=data.getParameters();
			User user=data.getUser();
                        String username=user.getName();
			String typeList=pp.getString("typeList");
			context.put("typelist",typeList);
                        String courseid=(String)user.getTemp("course_id","");
                        String quizid=pp.getString("quizid");
                        //xmlreader
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
			File fpath=new File(createquiz); 
		        if(!fpath.exists())
			{
				return;
			}
			TopicMetaDataXmlReader topicmetadata1=null;
                        Vector quizdetail=new Vector();
                        topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
                        quizdetail=topicmetadata1.getQuizDetails();
			data.setScreenTemplate("call,Quiz_Mgmt,Multi_Type.vm");
                }
                catch(Exception ex) {
			ErrorDumpUtil.ErrorLog("The Exception in Addcreate method::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
        }
	
	public void doSaveQuestion(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";	     
			ParameterParser pp=data.getParameters();
                        User user=data.getUser();
	                String courseid=(String)user.getTemp("course_id","");
			String typeList=pp.getString("typeList","");
			
			context.put("typeList",typeList);
			String op1=pp.getString("op1","");
			String op2=pp.getString("op2","");
			String op3=pp.getString("op3","");
			String op4=pp.getString("op4","");
			String ans1=pp.getString("ans");
			String ans=ans1.trim();
			String question=pp.getString("question");
			String marks=pp.getString("marks","");
                       	String quizid=pp.getString("quizid","");
			context.put("quizid",quizid);
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
			File f1=new File(createquiz+"/Quizid.xml");
			if(!f1.exists())
                        	return;
                        if(typeList.equals("TF"))
                        {
				ans=ans.toUpperCase();
                                op1="true";
                                op2="false";
                        }
			//ErrorDumpUtil.ErrorLog("   TF OP1   "+op1+"     op2   "+op2);
                        boolean found=false;
                        boolean found1=false;
                        if(typeList.equals("Multiple"))
                        {       String answers="A,B,C,D";
				ans=ans.toUpperCase();
				if(ans.length()<7) {
					StringTokenizer st=new StringTokenizer(ans,",");
					for(int j=0;st.hasMoreTokens();j++)
					{
                                        	String ans2=st.nextToken();
                                        	if(!(answers.contains(ans2)))
                                        		found=true;
                                	}//for
				}else {
					found1=true;
				}
                        }
                        if(typeList.equals("TF"))
                        {
                                String answers="A,B";
				if((ans.length()) == 1)
				{
                                	if(!(answers.contains(ans)))
                                	{       found=true;
	                                }
				}
				else {
					found1=true;
                                }
			
                        }
			if(found1) {
				data.setMessage(MultilingualUtil.ConvertedString("quiz_msg37",LangFile));				
				return;
			}
			//this is for edit question
			String mode=pp.getString("mode");
			if(mode.equals("edit"))	
			{
				context.put("mode",mode);
				String questionsid=pp.getString("questionsid");
				File file1=new File(createquiz);
                                File file=new File(createquiz+"/Quizid.xml");
                                if(file.exists())
                                {
					Vector vct=new Vector();
					int kk=-1;
                                	try {
						questionsid=questionsid.trim();
                                        	TopicMetaDataXmlReader topicmetadata1=null;
	                                        topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
        	                                vct=topicmetadata1.getQuizDetails();
                	                        File f2= new File(createquiz+"/Quizid.xml");
						for(int i=1;i<vct.size();i++)
						{
							String qid=((FileEntry) vct.elementAt(i)).getquestionid();
		                	                
							if(qid.equals(questionsid))
							{
	                                	               	kk=i;
							}
						}
						vct=null;
					} catch(Exception e){
						ErrorDumpUtil.ErrorLog("error in doSaveQuestion in line491 "+e);
						data.setMessage("See ExceptionLog !! ");
					}
					if(kk > -1)
					{	
                                		XmlWriter xmlwriter=null;
                                		xmlwriter=TopicMetaDataXmlWriter.WriteXml_New6(createquiz,"/Quizid.xml",kk);
					       	TopicMetaDataXmlWriter.appendQuiz(xmlwriter,op1,op2,op3,op4,ans,typeList,question,questionsid,marks);
        	        	                xmlwriter.writeXmlFile();
						msg=MultilingualUtil.ConvertedString("c_msg5",LangFile);
						data.setMessage(msg);
						//data.setMessage("Update is Successfully !!");
						data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Detail.vm");
					}
					else
						msg=MultilingualUtil.ConvertedString("quiz_msg8",LangFile);
					data.setMessage(msg);
                	        	//        data.setMessage("Update is not  Successfully !!");  
				}
			}
			// add question in quiz
			else {
				int  noofquestion=Integer.parseInt(pp.getString("noofquestion"));
				int  xmlsize=Integer.parseInt(pp.getString("xmlsize"));
				if(!found)
                        	{
                                        //TopicMetaDataXmlReader topicmetadata1=null;
         		                //Vector VectorQuiz=new Vector();
                                        //topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
                                        //VectorQuiz=topicmetadata1.getQuizDetails();
					//String op6=Integer.toString(VectorQuiz.size());
					String op6=Integer.toString(xmlsize+1);
				
					ErrorDumpUtil.ErrorLog("XML SIZE   IN XML !!!--"+xmlsize+" OP6  "+op6);
					
					XmlWriter xmlwriter=null; int kk=-1;
	        			xmlwriter=TopicMetaDataXmlWriter.WriteXml_New6(createquiz,"/Quizid.xml",kk);
					TopicMetaDataXmlWriter.appendQuiz(xmlwriter,op1,op2,op3,op4,ans,typeList,question,op6,marks);
	        	        	xmlwriter.writeXmlFile();
					//VectorQuiz=null;
					if((xmlsize+1)  == noofquestion)
					{
						data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Detail.vm");
						msg=MultilingualUtil.ConvertedString("quiz_msg7",LangFile);
						data.setMessage(msg);
						return;
					}
					msg=MultilingualUtil.ConvertedString("quiz_msg5",LangFile);
					data.setMessage(msg);
				}	
				else {	
                         		//data.setMessage("Answer should be in capital latter ");
					msg=MultilingualUtil.ConvertedString("quiz_msg6",LangFile);
					data.setMessage(msg);
				}
			} //else
		}
                catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in doSaveQuestionMethod::"+ex);
		data.setMessage("See ExceptionLog !! ");
		}
        }	

	public void doFinishQuestion(RunData data,Context context)
        {
                try{
                        ParameterParser pp=data.getParameters();
                        String typeList=pp.getString("typeList");
                        context.put("typeList",typeList);
			data.setScreenTemplate("call,Quiz_Bank,Create_Quiz.vm");
                }
                catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in finishquestion method::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
	}
	public void ViewDraft1(RunData data,Context context)
        {
                try{
                        ParameterParser pp=data.getParameters();
                	String ViewDraft="ViewDraftQuiz";
			data.getUser().setTemp("ViewDraft",ViewDraft);
	       	}
		catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in viewdraft method1::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
        }
	public void Draft(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
                        ParameterParser pp=data.getParameters();
			User user=data.getUser();
 			String username=user.getName();
			int userid=UserUtil.getUID(username);
                        String str4=Integer.toString(userid);
			String courseid=(String)user.getTemp("course_id","");
			
			String idques=pp.getString("idques").trim();
			String id1=pp.getString("start");	
			String quiztitle=pp.getString("quiztitle");	
			context.put("quiztitle",quiztitle);
			int id=Integer.parseInt(idques);	
			String startIndex=pp.getString("startIndex");
			context.put("startIndex",startIndex);
                        String ans="";
			String ViewDraft="ViewDraftQuiz";
                        context.put("ViewDraft",ViewDraft);
			//Reader
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quiztitle);
                       	String createquiz1=createquiz;
			TopicMetaDataXmlReader topicmetadata1=null;
                        Vector VectorQuiz=new Vector();
                        topicmetadata1=new TopicMetaDataXmlReader(createquiz1+"/Quizid.xml");
                        VectorQuiz=topicmetadata1.getQuizDetails();
                        File f2= new File(createquiz1+"/Quizid.xml");
			String option_a="";
			String option_c="";
			String option_b="";
                        String option_d="";
                        String Question="";
                        String tmarks="";
                        String Typeofquestion="";
                        String id2="";
			if(f2.exists() && VectorQuiz!=null)
                        {       for(int i=1;i<VectorQuiz.size();i++)
                        	{	if( id == i)
					{	
						option_a=((FileEntry) VectorQuiz.elementAt(i)).getoptionA().trim();
                                		option_c=((FileEntry) VectorQuiz.elementAt(i)).getoptionC().trim();
			                        option_b=((FileEntry) VectorQuiz.elementAt(i)).getoptionB().trim();
                	        	       	option_d=((FileEntry) VectorQuiz.elementAt(i)).getoptionD().trim();
		                               	Question=((FileEntry) VectorQuiz.elementAt(i)).getquestion().trim();
                        		        tmarks=((FileEntry) VectorQuiz.elementAt(i)).getmarksperqustion().trim();
			                        Typeofquestion=((FileEntry) VectorQuiz.elementAt(i)).getTypeofquestion().trim();
						id2=Integer.toString(id);
						break;
                       			}
		        	}
			}
			VectorQuiz=null;
			//writer
			createquiz=createquiz+"/"+"Draft";
			File file1=new File(createquiz);
			if(!file1.exists())
                	        file1.mkdirs();
                        File file=new File(createquiz+"/"+str4+".xml");
                	XmlWriter xmlwriter=null;
                        if(!file.exists())
                        {
                        	TopicMetaDataXmlWriter.writeWithRootOnly1(file.getAbsolutePath());
                        	xmlwriter=new XmlWriter(createquiz+"/"+str4+".xml");
                        }
			//reader
			topicmetadata1=null;
                        Vector vct=new Vector();
                        topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/"+str4+".xml");
                        vct=topicmetadata1.getQuizDetails();
			boolean flag=true;
                        if(vct!=null)
			{
				for(int i=0;i<vct.size();i++)	
				{		
					String questionsid=((FileEntry) vct.elementAt(i)).getquestionid().trim();
					if(questionsid.equals(id)) {
						flag=false;
						break;			
					}
				}
			}
			vct=null;	
			//reader
			if(flag)
			{	
				int kk=-1;
        	                xmlwriter=TopicMetaDataXmlWriter.WriteXml_New6(createquiz,"/"+str4+".xml",kk);
				TopicMetaDataXmlWriter.appendQuiz(xmlwriter,option_a,option_b,option_c,option_d,ans,Typeofquestion,Question,id2,tmarks);     
	                	xmlwriter.writeXmlFile();	
				msg=MultilingualUtil.ConvertedString("quiz_msg18",LangFile);
				//data.setMessage(msg);	
				//data.setMessage("Question saved in Draft");
			}
			else
				msg=MultilingualUtil.ConvertedString("quiz_msg23",LangFile);
			
			
			data.setMessage(msg);
                }
                catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in draft method::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
        }
	public void doGrade(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
                        ParameterParser pp=data.getParameters();
			User user=data.getUser();
			String feedback=pp.getString("message","");  
			//String marks=pp.getString("minmarks","0");  
			String marks=pp.getString("minmarks");  
			String maxmrks=pp.getString("MaxGrade");
			int maxm=Integer.parseInt(maxmrks);
			int minm=Integer.parseInt(marks);	
			  
			String Quiztype=pp.getString("Quiztype","0"); 
			String startIndex=pp.getString("startIndex");
			context.put("startIndex",startIndex);
                        String courseid=(String)user.getTemp("course_id","");			
			String username=pp.getString("userList");
                        String Quizid=pp.getString("Quizid");
                        context.put("Quizid",Quizid);
                        context.put("userList",username);
                        int userid=UserUtil.getUID(username);
			String str4=Integer.toString(userid);
			String sizelong_short=pp.getString("long_short","");
                        int long_short=Integer.parseInt(sizelong_short);
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+Quizid);
			boolean flag=true;
			try{
				
				TopicMetaDataXmlReader topicmetadata1=null;
		        	Vector Quizlist=new Vector();
        			topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/"+str4+".xml");
                		Quizlist=topicmetadata1.getAssignmentDetails1();
                       		File f2= new File(createquiz+"/"+str4+".xml");
				if(long_short== Integer.parseInt(startIndex))
                        		data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Start.vm");
                        	if(f2.exists()) {
					createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+Quizid);
	                               	for(int i=0;i<Quizlist.size();i++) {
						String question=((FileEntry) Quizlist.elementAt(i)).getUserName();
						if(question.equals(Quiztype))
							flag=false;
					}
					if(!flag) {
						msg=MultilingualUtil.ConvertedString("quiz_msg12",LangFile);
						//data.setMessage(msg);		
						//data.setMessage("Grade already given");
						return;
					}
				}
				else
					msg=MultilingualUtil.ConvertedString("quiz_msg24",LangFile);
				//data.setMessage("did not submit the Quiz");
				data.setMessage(msg);
			}
			catch(Exception ex){
				ErrorDumpUtil.ErrorLog("exception in topicmetadetaxmlreader::"+ex);
			 	data.setMessage("See ExceptionLog !! ");	
			}
			if(flag)
			{
				//boolean check=false;		
				if(maxm >=minm){
					File file1=new File(createquiz);
                                	if(!file1.exists())
                                		file1.mkdirs();
                                	File file=new File(createquiz+"/"+str4+".xml");
                                	XmlWriter xmlwriter=null;
			        	//String temp="";
                                	Vector xmlVct=new Vector();
					xmlVct.add(-1);
					if(!file.exists()) {
						TopicMetaDataXmlWriter.writeWithRootOnly1(file.getAbsolutePath());
						xmlwriter=new XmlWriter(createquiz+"/"+str4+".xml");
					}
                                	xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/"+str4+".xml",xmlVct);
                                	TopicMetaDataXmlWriter.appendGrade(xmlwriter,Quiztype,marks,feedback);
                                	xmlwriter.writeXmlFile();
					msg=MultilingualUtil.ConvertedString("quiz_msg11",LangFile);
					data.setMessage(msg);
				}
				else
					context.put("flg","empty");
			}
                }
                catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in grade method::"+ex);
			data.setMessage("See ExceptionLog !! ");
        	}
        }
	
	public void doStopQuiz(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
			ParameterParser pp=data.getParameters();
                        String quiztitle=pp.getString("quiztitle");
                        context.put("Quizid",quiztitle);
                        User user=data.getUser();
                        String username=user.getName();
                        int userid=UserUtil.getUID(username);
                        String str4=Integer.toString(userid);
                        String courseid=(String)user.getTemp("course_id","");
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quiztitle+"/Student_Quiz");
                        File file=new File(createquiz+"/"+str4+".xml");
                        if(file.exists()){
				long length=file.length();
				context.put("length",length);
			}
			String createquiz1=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quiztitle+"/Draft"+"/"+str4+".xml");
                        File f1 = new File(createquiz1);
                        if(f1.exists())
                        	f1.delete();
		        data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Start.vm");
			msg=MultilingualUtil.ConvertedString("quiz_msg19",LangFile);
			data.setMessage(msg);
                }
                catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in stop quiz::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
        }
	public void doGetdetail(RunData data,Context context)
        {
		try{
				
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
			ParameterParser pp=data.getParameters();
                        String quizid=pp.getString("Quizid");
                        context.put("Quizid",quizid);
                        User user=data.getUser();
                        String username=user.getName();
                        int userid=UserUtil.getUID(username);
                        String str4=Integer.toString(userid);
                        String courseid=(String)user.getTemp("course_id","");
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid+"/Student_Quiz");
			String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
                        File file=new File(createquiz+"/"+str4+".xml");
			String quizname="";
                        if(file.exists()){
                        	TopicMetaDataXmlReader xmlreader=null;
         		        Vector vect=new Vector();
                                xmlreader=new TopicMetaDataXmlReader(path+"/Quizid.xml");
                                vect=xmlreader.getQuizDetails();
				// student record
				
				xmlreader=null;
				Vector vect1=new Vector();
                                xmlreader=new TopicMetaDataXmlReader(path+"/Student_Quiz"+"/"+str4+".xml");
				vect1=xmlreader.getAssignmentDetails1();
				Vector detail=new Vector(); 				
				
				//student record
				boolean found1=true;
				for(int i=1; i<vect.size(); i++)
				{
					String quid=((FileEntry) vect.elementAt(i)).getquestionid();
					String answer=((FileEntry) vect.elementAt(i)).getinstructorans();
                                        String ques=((FileEntry) vect.elementAt(i)).getquestion();
                                        QuizDetail qDetail= new QuizDetail();
					qDetail.setAnswer(answer);
                                        qDetail.setQuestion(ques);
					for(int j=0;j<vect1.size();j++)
					{
						String uid=((FileEntry) vect1.elementAt(j)).getUserName();
						if(uid.equals(quid))
						{	
							found1=false;
							String uid1=((FileEntry) vect1.elementAt(j)).getGrade();
							qDetail.setGrade(uid1);
							Vector vect2=new Vector();
			                                xmlreader=new TopicMetaDataXmlReader(path+"/"+str4+".xml");
                        			        vect2=xmlreader.getAssignmentDetails1();
							for(int k=0;k<vect2.size();k++)
							{
								String uid2=((FileEntry) vect2.elementAt(k)).getUserName();
								if(uid2.equals(quid)){
									String uid3=((FileEntry) vect2.elementAt(k)).getfeedback();
									qDetail.setfeedback(uid3);
								}	
							}
							vect2=null;	
						}
					}
					if(found1)
					{
						qDetail.setGrade("");
                                                qDetail.setfeedback("");
					}
					found1=true;
					detail.addElement(qDetail);
				} //for
				vect=null;vect1=null;
				context.put("detail",detail);
				//msg=MultilingualUtil.ConvertedString("quiz_msg29",LangFile);
				//data.setMessage(msg);	
			}
			else
				msg=MultilingualUtil.ConvertedString("quiz_msg31",LangFile);
                        data.setMessage(msg);
                }
                catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in getdetail method::"+ex);
		 data.setMessage("See ExceptionLog !! ");
		}
        }
	public void Savegradecard(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
                        ParameterParser pp=data.getParameters();
                        User user=data.getUser();
                        String username=user.getName();
                        String courseid=(String)user.getTemp("course_id","");
                        	
			String quizid = pp.getString("quizid","");
                        String userList = pp.getString("userList","");
			//int userid=UserUtil.getUID(userList);
			//String str4=Integer.toString(userid);
			String str4=Integer.toString(UserUtil.getUID(userList));
                        String quiztitle=pp.getString("quiztitle");
                        String grade=pp.getString("grade");
                        context.put("quiztitle",quiztitle);
                        String mid_delete = pp.getString("deleteFileNames","");
                        String DB_subject = pp.getString("DB_subject","");
			String [] subjectarray=	DB_subject.split("@");
			if(!mid_delete.equals(""))
                        {
                        	StringTokenizer st=new StringTokenizer(mid_delete,"^");
                                for(int j=0;st.hasMoreTokens();j++)
                                {
					String msg_idd=st.nextToken();
					DB_subject = subjectarray[j];
				
					Criteria crit=new Criteria();
		                        crit.add(QuizPeer.QUIZ_ID,msg_idd);
                		        List u = QuizPeer.doSelect(crit);
					int mmarks=0;
					for(int i=0;i<u.size();i++)
					{
						Quiz element=(Quiz)(u.get(i));
						mmarks=element.getMaxMarks();
					}
					if((Integer.parseInt(DB_subject))>mmarks)
					{
						msg=MultilingualUtil.ConvertedString("quiz_msg10",LangFile);
						data.setMessage(msg);
						return;
					}
					String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz");
					File f1=new File(createquiz+"/GradeCard.xml");
					String feedback="";
					Vector xmlVct=new Vector();
					if(f1.exists())
					{
						Vector detail=new Vector();
		                                TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(createquiz+"/GradeCard.xml");
                	                	detail=topicmetadata.getAssignmentDetails1();
						int aa=-1;
	                        	        for(int i=0;i<detail.size();i++)
        	                        	{
                	                        	String quizid1=((FileEntry)detail.elementAt(i)).getUserName();
                	                        	String userid1=((FileEntry)detail.elementAt(i)).getGrade();
							if(quizid1.equals(msg_idd) && userid1.equals(str4))
								aa=i;
								xmlVct.add(aa);
						} //for
						if(aa>-1) {
							XmlWriter xmlwriter=null;
							//String temp="";
	        	     	        	        xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/GradeCard.xml",xmlVct);
		              	       		        TopicMetaDataXmlWriter.appendGrade(xmlwriter,msg_idd,str4,DB_subject);
	        		      		        xmlwriter.writeXmlFile();
							msg=MultilingualUtil.ConvertedString("c_msg5",LangFile);
						  	data.setMessage(msg);
						}
					} // if
				} //for
			}//if
		}  //try
		catch(Exception ex){
			ErrorDumpUtil.ErrorLog("exception in savegradecard::"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
	}	
	public void configuregrade(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
		        ParameterParser pp=data.getParameters();
                        User user=data.getUser();
                        String username=user.getName();
                        String cid=(String)user.getTemp("course_id","");
			
			String mid_delete = pp.getString("deleteFileNames","");
                        String DB_subject = pp.getString("DB_subject","");
                        String [] subjectarray= DB_subject.split("@");
			if(!mid_delete.equals(""))
                        {
                        	StringTokenizer st=new StringTokenizer(mid_delete,"^");
                                for(int j=0;st.hasMoreTokens();j++)
                                {
                                        String msg_idd=st.nextToken();
                                        DB_subject = subjectarray[j];
					String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+cid);
					File file=new File(createquiz+"/"+"configuregrade.xml");
                        		if(!file.exists())
                                	{
                                        	XmlWriter xmlwriter=null;	
						TopicMetaDataXmlWriter.writeWithRootOnly1(file.getAbsolutePath());
	                                       	xmlwriter=new XmlWriter(createquiz+"/"+"configuregrade.xml");
        	                        }
					XmlWriter xmlwriter=null;
                                        String temp="";
					Vector xmlVct=new Vector();
                                        xmlVct.add(-1);
                                        xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/"+"configuregrade.xml",xmlVct);
		                        TopicMetaDataXmlWriter.appendGrade(xmlwriter,msg_idd,DB_subject,temp);
                                        xmlwriter.writeXmlFile();
				}
			}
		}
		catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in configuregrade::"+ex);
		data.setMessage("See ExceptionLog !! ");
		}
	}
	public void doUpdate(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";				
			ParameterParser pp=data.getParameters();
                        User user=data.getUser();
                     //   String username=user.getName();
                  //      String uid=Integer.toString(UserUtil.getUID(username));
                        String cid=(String)user.getTemp("course_id","");
			int gid=GroupUtil.getGID(cid);
			String quizid=pp.getString("quizid");
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz"+"/"+quizid);
		//	String qname=pp.getString("qname");
			String mode=pp.getString("mode");
		//	String tmarks=pp.getString("tmarks");
		//	String marks=pp.getString("marks");
		//	String tquestion=pp.getString("tquestion");
		//	String title=pp.getString("title");
			context.put("mode",mode);
                        String year=pp.get("Start_year");
                        String month=pp.get("Start_mon");
                        String day=pp.get("Start_day");
                        String date=year+"-"+month+"-"+day;
                        String date1=date.replaceAll("-","");
                        int intdate=Integer.parseInt(date1);
                        int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
                        if(intdate < curdate)
                        {
				msg=MultilingualUtil.ConvertedString("quiz_msg26",LangFile);
				data.setMessage(msg);
                                return;
                        }
                        Date qdate=Date.valueOf(date);
			// Geting current time fron server for comparing to schedule quiz
			Calendar calendar=Calendar.getInstance();
                        int curmin=calendar.get(Calendar.HOUR);
                        int min=calendar.get(Calendar.MINUTE);
                        int am_pm=calendar.get(Calendar.AM_PM);
                        if(am_pm == 1)
                                curmin= curmin+12;

                        
			String sh=pp.getString("Start_hr");
                        int s_hour=Integer.parseInt(sh);
                        String sm=pp.getString("Start_min");
                        String sTime= sh + ":" + sm+":00";
			 // Check only for today quiz time

                       if(intdate == curdate)
                        {
                                if(s_hour <= curmin )
                                {
                                        if(Integer.parseInt(sm) <= min )
                                        {
                                                msg=MultilingualUtil.ConvertedString("quiz_msg33",LangFile);
                                                data.setMessage(msg);
                                                return;
                                        }
                                }
                        }
			Time stime=Time.valueOf(sTime);
                        String eh=pp.getString("Last_hr");
                        int e_hour=Integer.parseInt(eh);
                        String em=pp.getString("Last_min");
                        String eTime= eh + ":"  + em+":00";
                        Time etime=Time.valueOf(eTime);
                  //      String mmarks=pp.get("marks");
                        String exp="30";
		        int starttime=(s_hour*60) + Integer.parseInt(sm);
                        int endtime=(e_hour*60) +Integer.parseInt(em);
                        if(starttime >= endtime)
                        {
				msg=MultilingualUtil.ConvertedString("quiz_msg27",LangFile);

				data.setMessage(msg);
                                return;
                        }
			Criteria crit=new Criteria();
			crit.add(QuizPeer.QUIZ_ID,quizid);
                        List li=QuizPeer.doSelect(crit);
			int id=0;
			for(int i=0;i<li.size();i++)
			{
				Quiz element=(Quiz)(li.get(i));
                                id=(element.getId());	
			}

			crit=new Criteria();
                        crit.add(QuizPeer.CID,cid);
                        List li1=QuizPeer.doSelect(crit);
                        for(int i=0;i<li1.size();i++)
                        {
                                String str6="";
                                Quiz element=(Quiz)(li1.get(i));
                                String str1=(element.getPostDate().toString());
                                str1=str1.substring(0,10);
                                str1=str1.replaceAll("-",str6);
                                int date2=Integer.parseInt(str1);
                                if(date2 == intdate)
                                {
                                        String str3=(element.getEndTime().toString());
                                        String str4=(element.getStartTime().toString());
                                        /**   Start time   */
                                        str4=str4.substring(0,5);
                                        String starthourcheck=str4.substring(0,2);
                                        String startmincheck=str4.substring(3,5);
                                        int startcheckmin=((Integer.parseInt(starthourcheck))*60) + Integer.parseInt(startmincheck);
                                        /**  End Start Time  */
                                        str3=str3.substring(0,5);
                                        String hourcheck=str3.substring(0,2);
                                        String mincheck=str3.substring(3,5);
                                        int checkmin=((Integer.parseInt(hourcheck))*60) + Integer.parseInt(mincheck);
                                        if(startcheckmin <= starttime && starttime <=checkmin){
                                                msg=MultilingualUtil.ConvertedString("quiz_msg30",LangFile);
                                                data.setMessage(msg);
                                                return;
                                        }
                                        if(startcheckmin <= endtime && endtime <=checkmin){
                                                return;
                                        }
                                } //if
                        }

			


			crit=new Criteria();
			crit.add(QuizPeer.ID,id);
			//crit.add(QuizPeer.QUIZ_ID,quizid);
//			crit.add(QuizPeer.USER_ID,uid);
  //                      crit.add(QuizPeer.QUIZ_TITLE,title);
                       crit.add(QuizPeer.START_TIME,stime);
                        crit.add(QuizPeer.END_TIME,etime);
                      crit.add(QuizPeer.POST_DATE,qdate);
          //             	crit.add(QuizPeer.MAX_MARKS,marks);
			QuizPeer.doUpdate(crit);
			/**
			 * Delete the News and Notice for perticular quiz after update
			 */
			crit=new Criteria();
                        crit.add(NoticeSendPeer.NOTICE_SUBJECT,quizid);
                        List lst=NoticeSendPeer.doSelect(crit);
                        int nid=0;
                        for(int i=0;i<lst.size();i++){
                                NoticeSend nsdetail=(NoticeSend)lst.get(i);
                                nid=nsdetail.getNoticeId();
                        }
                        //String n_id=Integer.toString(nid);
                        crit=new Criteria();
                        crit.add(NewsPeer.NEWS_TITLE,quizid);
                        crit.add(NewsPeer.GROUP_ID,gid);
                        //crit.add(NewsPeer.USER_ID,uid);
                        NewsPeer.doDelete(crit);

                        crit=new Criteria();
                        crit.add(NoticeSendPeer.NOTICE_ID,nid);
                        //crit.add(NoticeSendPeer.USER_ID,uid);
                        NoticeSendPeer.doDelete(crit);

                        crit=new Criteria();
                        crit.add(NoticeReceivePeer.NOTICE_ID,nid);
                        NoticeReceivePeer.doDelete(crit);	

			msg=MultilingualUtil.ConvertedString("quiz_msg4",LangFile);
			data.setMessage(msg);
		}
                catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in update method::"+ex);
		data.setMessage("See ExceptionLog !! ");
		}
        }
	public void doDelete(RunData data,Context context)
        {
                try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";
			User user=data.getUser();
			String courseid=(String)user.getTemp("course_id"); 
			String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid +"/configuregrade.xml");
                        File file=new File(path);
                        if(file.exists())
                        {
                                SystemIndependentUtil.deleteFile(file);
				msg=MultilingualUtil.ConvertedString("quiz_msg9",LangFile);
				data.setMessage(msg);	
                        }
		}
		catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in doDelete method::"+ex);
		 data.setMessage("See ExceptionLog !! ");
		}
	}	
	public void doDestroy(RunData data,Context context,String str1)
        {
                try
                {
                        String LangFile=data.getUser().getTemp("LangFile").toString();
                        User user=data.getUser();
                        String uname=user.getName();
                        int userid=UserUtil.getUID(uname);
                        String uid=Integer.toString(userid);
                        String courseid=(String)user.getTemp("course_id");
                        ParameterParser pp=data.getParameters();
                        String qname="";
                        if(str1.equals(""))
                                qname=pp.getString("qname").trim();
                        else
                                qname=str1.trim();
                        Criteria crit=new Criteria();
                        crit.add(NoticeSendPeer.NOTICE_SUBJECT,qname);
                        crit.add(NoticeSendPeer.USER_ID,userid);
                        List lst=NoticeSendPeer.doSelect(crit);
                        int nid=0;
                        for(int i=0;i<lst.size();i++){
                                NoticeSend nsdetail=(NoticeSend)lst.get(i);
                                nid=nsdetail.getNoticeId();
                        }
                        String n_id=Integer.toString(nid);

                        String path1 = data.getServletContext().getRealPath("/Courses")+"/"+courseid+"/Notice_Msg.txt";
                        String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+qname);
                        File file=new File(path);
                        if(file.exists())
                        {
                                SystemIndependentUtil.deleteFile(file);
                        }
                        CommonUtility.UpdateTxtFile(path1,n_id,"",false );
			 if(!qname.equals(""))
                        {
                                crit=new Criteria();
                                crit.add(QuizPeer.QUIZ_ID,qname);
                                crit.add(QuizPeer.CID,courseid);
                                crit.add(QuizPeer.USER_ID,userid);
                                QuizPeer.doDelete(crit);
				
                                crit=new Criteria();
                                crit.add(NewsPeer.NEWS_TITLE,qname);
                                crit.add(NewsPeer.USER_ID,userid);
                                NewsPeer.doDelete(crit);
				
                                crit=new Criteria();
                                crit.add(NoticeSendPeer.NOTICE_ID,nid);
                                crit.add(NoticeSendPeer.USER_ID,userid);
                                NoticeSendPeer.doDelete(crit);
				
                                crit=new Criteria();
                                crit.add(NoticeReceivePeer.NOTICE_ID,nid);
                                NoticeReceivePeer.doDelete(crit);
				
                                String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz");
                                Vector idlist=new Vector();
                                Vector indx= new Vector();
                                File f2= new File(createquiz+"/GradeCard.xml");
                                String msg="";
                                if(f2.exists())
                                {
                                        TopicMetaDataXmlReader topicmetadata1=null;
                                        topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/GradeCard.xml");
                                        idlist=topicmetadata1.getAssignmentDetails1();
                                        if(idlist==null){
                                                SystemIndependentUtil.deleteFile(f2);
                                                msg=MultilingualUtil.ConvertedString("quiz_msg9",LangFile);
                                                data.setMessage(msg);
                                                return;
                                        }
                                        if(idlist.size()!=0){
                                        	for(int k=0;k<idlist.size();k++)
                                        	{
                                                	String id=((FileEntry) idlist.elementAt(k)).getUserName().trim();
                                                	id=id.trim();
							qname=qname.trim();
                                                	if(id.equals(qname))
                                                	indx.add(k);
     	                                 	}
        	                                XmlWriter xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/GradeCard.xml",indx);
                	                        xmlwriter.writeXmlFile();
                                        }
                                }
                                msg=MultilingualUtil.ConvertedString("quiz_msg9",LangFile);
                                data.setMessage(msg);
                        }
		}
                catch(Exception e)
                {
                	ErrorDumpUtil.ErrorLog("exception in destroy method::"+e);
                	 data.setMessage("See ExceptionLog !! ");
		}
        }
	public void doRemove(RunData data,Context context)
        {
                try{
                        String qdelete=data.getParameters().getString("deleteFileNames");
                        if(!qdelete.equals("")){
                                StringTokenizer st=new StringTokenizer(qdelete,"^");
                                for(int j=0;st.hasMoreTokens();j++){
                                        String q_id=st.nextToken();
                                        doDestroy(data,context,q_id);
                                }
                        }
                }
                catch(Exception e)
                {
                	ErrorDumpUtil.ErrorLog("exception in remove method::"+e);
                	 data.setMessage("See ExceptionLog !! ");
		}
        }
	public void doPerform(RunData data,Context context)
        {       try
                {
                        String action=data.getParameters().getString("actionName","");
			context.put("action",action);	
                        if(action.equals("FileUpload"))
			{
                                fileUpload(data,context);
			}
                        else if(action.equals("eventSubmit_doSchedule"))
			{
                                doSchedule(data,context);
			}
                        else if(action.equals("eventSubmit_doCreate"))   
			{
				doCreate(data,context);
			}
			else if(action.equals("eventSubmit_doRegister1"))  
                        {       
                               // dosubmit1(data,context);
                                doRegister(data,context);
			}
			else if(action.equals("eventSubmit_doSaveQuestion"))  
                        {       
                                doSaveQuestion(data,context);
			}
			else if(action.equals("eventSubmit_doFinishQuestion")) 
                        {       
                                doFinishQuestion(data,context);
			}
			else if(action.equals("ViewDraft1")) 
                        {       
                                ViewDraft1(data,context);
			}
                        else if(action.equals("finishedQuestion")) {
				checkfinishedQuestion(data,context);			
			}
			else if(action.equals("Draft"))
                        {       
                                Draft(data,context);
			}
			else if(action.equals("Go")) 
                        {       
                                Go(data,context);
			}
			else if(action.equals("Savegradecard"))
                        {       
                                Savegradecard(data,context);
			}
			else if(action.equals("doAddcreate"))
                        {       
                                doAddcreate(data,context);
			}
			else if(action.equals("eventSubmit_doGrade"))
                        {       
                        	 doGrade(data,context);			
			}
			else if(action.equals("eventSubmit_doStopQuiz"))
                        {       
                        	 doStopQuiz(data,context);			
			}
			else if(action.equals("eventSubmit_doGetdetail"))
                        {       
                        	 doGetdetail(data,context);			
			}
			else if(action.equals("configuregrade"))
                        {       
                                 configuregrade(data,context);
			}
			else if(action.equals("eventSubmit_doDestroy"))
                	{
                        	String str1="";
                        	doDestroy(data,context,str1);
                	}
                	else if(action.equals("eventSubmit_doRemove"))
                	{
                        	doRemove(data,context);
                	}
			else if(action.equals("eventSubmit_doDelete"))
                        {       
                        	 doDelete(data,context);			
			}
			else if(action.equals("eventSubmit_doUpdate"))
                        {       
                                 doUpdate(data,context);
			}
			else
                        {       
				String LangFile=data.getUser().getTemp("LangFile").toString();
                                String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
                                data.setMessage(msg);
                        }
                }
                catch(Exception ex){
		ErrorDumpUtil.ErrorLog("exception in perform method::"+ex);
        	data.setMessage("See ExceptionLog !! ");
		}
        }
}
