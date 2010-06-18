package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt; 

/*
 * @(#)ViewQuiz.java
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
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.QuizDetail;  
import java.util.Vector;
import java.io.File;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import java.sql.Date;
import org.apache.torque.util.Criteria;
import java.util.List;
import java.util.Calendar;
		
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.apache.turbine.services.servlet.TurbineServlet;
//import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.UserUtil;
//import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.XmlData;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.om.Quiz;

/**
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */


public class ViewQuiz extends  SecureScreen
{
	public void doBuildTemplate(RunData data, Context context)
        {
        	try
                {

                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
			String LangFile=data.getUser().getTemp("LangFile").toString();
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
			Vector quid=new Vector();
			for(int i=0;i<u.size();i++)
                        {
				Quiz element=(Quiz)(u.get(i));
				quiztitle=(element.getQuizId());
				startQuiz=true;
				quid.add(element.getQuizId());	
			}
			
			quiztitle=pp.getString("qname","quiztitle");
			context.put("quid",quiztitle);
			 
			if(startQuiz==true && !quiztitle.equals("quiztitle") ){		
			
			
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
				//data.setMessage("Question did not Created ");
				String msg=MultilingualUtil.ConvertedString("quiz_msg3",LangFile);
                                data.setMessage(msg);
				return;
			}	
			topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
			VectorQuiz=topicmetadata1.getQuizDetails();
			f2= new File(createquiz+"/Quizid.xml");
			
			
			if(f2.exists() && VectorQuiz!=null)
                        {
				
			       	for(int i=1;i<VectorQuiz.size();i++)
                                {

					String option_a=((FileEntry) VectorQuiz.elementAt(i)).getoptionA();
                                        String option_c=((FileEntry) VectorQuiz.elementAt(i)).getoptionC();
                                        String option_b=((FileEntry) VectorQuiz.elementAt(i)).getoptionB();
                                        String option_d=((FileEntry) VectorQuiz.elementAt(i)).getoptionD();
                                        String Question=((FileEntry) VectorQuiz.elementAt(i)).getquestion();
                                        String tmarks=((FileEntry) VectorQuiz.elementAt(i)).getmarksperqustion();
                                        String Typeofquestion=((FileEntry) VectorQuiz.elementAt(i)).getTypeofquestion()
;
                                        String questionsid=((FileEntry) VectorQuiz.elementAt(i)).getquestionid();
                                        String totalquestion=((FileEntry) VectorQuiz.elementAt(i)).getinstructorans();					
					QuizDetail qDetail= new QuizDetail();
					qDetail.setquestionid(questionsid);
					qDetail.setoptionA(option_a);
                                        qDetail.setoptionB(option_b);
                                        qDetail.setoptionC(option_c);
                                        qDetail.setoptionD(option_d);
                                        qDetail.setQuestion(Question);
                                        qDetail.setmarks(tmarks);
					qDetail.setAnswer(totalquestion);
					qDetail.setQuiztype(Typeofquestion);
					entry.addElement(qDetail);
				} //for
				context.put("contentvalue",entry);
			} //if 
			}
		} //try
		catch(Exception e) {
			ErrorDumpUtil.ErrorLog("The exception in ViewQuizFile::"+e);
			 data.setMessage("See ExceptionLog !! ");	
		}
	}
}			
