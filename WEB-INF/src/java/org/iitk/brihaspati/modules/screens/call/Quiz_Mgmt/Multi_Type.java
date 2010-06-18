package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/* @(#)Multi_Type.java
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur.
*/

import org.apache.turbine.util.parser.ParameterParser;
import java.util.Vector;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;

/**
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */

public class Multi_Type extends SecureScreen
{
	/**
     	* Place all the data object in the context
     	* for use in the template.
     	*/
        public void doBuildTemplate(RunData data,Context context)
        {
		/**
                *Retrieve the Parameters by using the Parameter Parser
                *Get the UserName and put it in the context
                *for template use
                */
                ParameterParser pp=data.getParameters();
                try
                {
		        User user=data.getUser();
                        String mode =pp.getString("mode"," ");
			context.put("mode",mode);
			String courseid=(String)user.getTemp("course_id");
                        if(mode.equals("edit"))
			{
				String questioid =pp.getString("questioid");				
				String quizid =pp.getString("quizid");				
				String typeList =pp.getString("typeList");
				context.put("typeList",typeList);
				context.put("allTopics1",typeList);
				context.put("quizid",quizid);
				String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
	                        File f1=new File(createquiz);
        	                if(!f1.exists())
                	        f1.mkdirs();
				ErrorDumpUtil.ErrorLog("qid-->"+quizid+"\npath-->"+createquiz+"\nf1-->"+f1);
                        	TopicMetaDataXmlReader topicmetadata1=null;
	                        Vector VectorQuiz=new Vector();
        	                topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
                	        VectorQuiz=topicmetadata1.getQuizDetails();
                        	File f2= new File(createquiz+"/Quizid.xml");
	       	                if(f2.exists())
                	        {
                        	        if(VectorQuiz!=null)
                                	{
                                        	for(int i=1;i<VectorQuiz.size();i++)
	                                        {
							String questionsid=((FileEntry) VectorQuiz.elementAt(i)).getquestionid();
							if(questionsid.equals(questioid) )
	                                                {

							        String noofquestion=((FileEntry)VectorQuiz.elementAt(i)).getinstructorans().trim();

	                                                       	String marks=((FileEntry)VectorQuiz.elementAt(i)).getmarksperqustion().trim();
                                                        	context.put("marks",marks);
								
								String option_a=((FileEntry) VectorQuiz.elementAt(i)).getoptionA().trim();
		                                                String option_c=((FileEntry) VectorQuiz.elementAt(i)).getoptionC().trim();
                		                                String option_b=((FileEntry) VectorQuiz.elementAt(i)).getoptionB().trim();
			                                        String option_d=((FileEntry) VectorQuiz.elementAt(i)).getoptionD().trim();
								context.put("initial","typeList");				
		                                                String Question=((FileEntry) VectorQuiz.elementAt(i)).getquestion().trim();
               		                                	context.put("op1",option_a);
               		                                	context.put("op2",option_b);
               		                                	context.put("op3",option_c);
               		                                	context.put("op4",option_d);
               		                                	context.put("question",Question);
               		                                	context.put("ans",noofquestion);
               		                                	context.put("questionsid",questionsid);
							}	
						}
                                	}
                        	}
			}
			else
			{
				String typeList =pp.getString("typeList","");
				context.put("typeList",typeList);
				String marks =pp.getString("marks","");		
				String quizid =pp.getString("quizid","0");
        	                context.put("quizid",quizid);
	                        Vector v=new Vector();
				if(typeList.equals(""))
				{ 
					context.put("allTopics1","");
					context.put("initial","initialnull");
				}
				else
					context.put("initial","");
				if(typeList.equals("Multiple") || typeList.equals("TF") || typeList.equals("Short") )
					context.put("allTopics1",typeList);  
				String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
				File f1=new File(createquiz);
        	        	if(!f1.exists())
                	                f1.mkdirs();
				
				TopicMetaDataXmlReader topicmetadata1=null;
	                	Vector VectorQuiz=new Vector();
        	                topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/Quizid.xml");
	        	        VectorQuiz=topicmetadata1.getQuizDetails();
				File f2= new File(createquiz+"/Quizid.xml");
	                        String noofquestion="";
        	                
					
        	        	if(f2.exists())
                		{  
					if(VectorQuiz!=null)
                                	{
                        	        	for(int i=0;i<VectorQuiz.size();i++)
                                        	{
							if(i==0)
		                               		noofquestion=((FileEntry)VectorQuiz.elementAt(i)).getinstructorans().trim();
							marks=((FileEntry)VectorQuiz.elementAt(i)).getmarksperqustion().trim();
                                                        context.put("marks",marks);
	                                                break;
						}
					}
					context.put("noofquestion",Integer.parseInt(noofquestion));
					context.put("xmlsize",(VectorQuiz.size()-1));
					if(Integer.parseInt(noofquestion)==(VectorQuiz.size()-1)) {
						data.setMessage(MultilingualUtil.ConvertedString("quiz_msg36",(String)user.getTemp("LangFile")));
					}
					context.put("noq",VectorQuiz.size());
				}
				
			} //else
                }
                catch(Exception e) {
			ErrorDumpUtil.ErrorLog("The exception in multi type screen::"+e);
			data.setMessage("See ExceptionLog !! ");
		}
        }
}


