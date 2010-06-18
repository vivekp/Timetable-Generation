package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/*
 * @(#)Quiz_Start.java	
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

import java.util.Calendar;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.QuizUtil;
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
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.DbDetail;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.om.Quiz;

/**
 *   This class contains code for all discussions in workgroup
 *   Compose a discussion and reply.
 *   @author  <a href="nksinghiitk@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */
public class Quiz_Start extends SecureScreen
{
	public void doBuildTemplate( RunData data, Context context )
	{
		try{
			User user=data.getUser();
			String LangFile=user.getTemp("LangFile").toString();
                        String loginname=user.getName();
                        int user_id=UserUtil.getUID(loginname);
                        String cid=(String)user.getTemp("course_id");
			Criteria crit=new Criteria();
			String Role=(String)user.getTemp("role");
                        context.put("user_role",Role);
			String createquizdir=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz");
			File filedir=new File(createquizdir);
			if(!filedir.exists())
				filedir.mkdir();
			Calendar calendar=Calendar.getInstance();
                        int curmin=calendar.get(Calendar.HOUR);
                        int am_pm=calendar.get(Calendar.AM_PM);
			if(am_pm == 1)
                                 curmin= curmin+12;
                        curmin= (curmin*60) +(calendar.get(Calendar.MINUTE));

                        boolean startQuiz=false;
                        boolean startQuiz1=false;
                        boolean QuizGrade=false;
			boolean QuizSched=false;
                        crit=new Criteria();
                        crit.add(QuizPeer.CID,cid);
                        List u = QuizPeer.doSelect(crit);
                        String datestr1="-";
			String quiztitle="";
			String scorecard="",msg="",stTime="";
			int date1=0;
			if(u.size() != 0)
			{
		                for(int i=0;i<u.size();i++)
                	        {
                        	        Quiz element=(Quiz)(u.get(i));
                                	String str1=element.getPostDate().toString();
		                        str1=str1.substring(0,10);
                	                str1=str1.replaceAll(datestr1,"");
                        	        date1=Integer.parseInt(str1);
                              		int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
					startQuiz=true;
					if(date1 <= curdate)
        	                	{
						String str2=(element.getStartTime().toString());
						stTime=str2;
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
                        	        	quiztitle=(element.getQuizId());
						//link startQuiz is open 
						if(date1 == curdate)
						{
                                               if( (starthour > curmin) && (!QuizSched) && (Role.equals("student"))){
								
								quiztitle=(element.getQuizId());
								msg=MultilingualUtil.ConvertedString("quiz_msg35",LangFile);
								data.setMessage(quiztitle+" "+ msg + " Date "+date1+" Time "+stTime);
                                				QuizSched=true;
					                }

							
							if((endhour > curmin) && ( starthour <= curmin))
        	                        		{
                	                      			quiztitle=(element.getQuizId());
                        	               			startQuiz1=true;
							}
							else{
								/**    Delete the  Draft  */
								quiztitle=(element.getQuizId());
								String fPath=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz"+"/"+quiztitle+"/Draft");
								File f1 = new File(fPath);
                        					if(f1.exists())
                                				{
									String flist[] = f1.list();
	                	                                	for(int z=0;z<flist.length;z++)
        	                	                        	{
                	                	                        	File f2 = new File(fPath+"/"+flist[z]);
	       			                                       		/* removing directory of attachment with message */
                                		                        	f2.delete();
                                                			}
                                                		}
								/**    Delete the  Draft  */
							}
							if(endhour < curmin) 
                                                	scorecard =(element.getQuizId());
						}
						else if(date1 < curdate)
	                                        	QuizGrade=true;
						if(endhour < curmin)
							QuizGrade=true;
					//link startQuiz is open
					}
					
				}
				String path=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz"+"/"+quiztitle+"/"+"Student_Quiz");
				int userid=user_id;
				boolean flag1=true;
                        	String str4=Integer.toString(userid);
				String fname=path+"/"+str4+".xml";
				File file1=new File(fname);
				if(file1.exists())
					flag1=false;
				try{
					/** scorcard  */
				
				QuizUtil qz=new QuizUtil();
				qz.getGradecard(scorecard,cid);
				qz=null;
				ErrorDumpUtil.ErrorLog("The exception in quiz start before using quiz utils::");
					//QuizUtil.getgradecard("quiz2",cid);
					/** scorcard  */
				/** xml write in Courseid by defauld */
				String xmlPath=TurbineServlet.getRealPath("/Courses"+"/"+cid);
				File xmlFile=new File(xmlPath+"/configuregrade.xml");
				if(!xmlFile.exists())
				{
					
					XmlWriter xmlwriter=null;
                                        if(!xmlFile.exists())
                                        {
	                                        TopicMetaDataXmlWriter.writeWithRootOnly1(xmlFile.getAbsolutePath());
                                                xmlwriter=new XmlWriter(xmlPath+"/configuregrade.xml");
                                        }  //if
					Vector xmlVct=new Vector();
					xmlVct.add(-1);
					String  str[]={"A","B","C","D","E"};
					String  str1[]={"85","75","65","55","45"};
					for(int xmlint=0;xmlint<5;xmlint++)
					{
						xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(xmlPath,"/configuregrade.xml",xmlVct);
                                                String temp="";
                                                TopicMetaDataXmlWriter.appendGrade(xmlwriter,str1[xmlint],str[xmlint],temp);
                                                xmlwriter.writeXmlFile();			
					}  //end for
					xmlVct=null;
				}
				/**    xml write in Courseid by defauld  */
 				}
		                catch(Exception e){
					ErrorDumpUtil.ErrorLog("The exception in xml writer::"+e);
					data.setMessage("See ExceptionLog !! ");	
 				}
			}
			else
			{	if(Role.equals("student")){
					msg=MultilingualUtil.ConvertedString("quiz_msg34",LangFile);
	                                data.setMessage(msg);
				}
				
			}
			/**
			if(QuizSched) {
                                if(Role.equals("student")){
                                        msg=MultilingualUtil.ConvertedString("quiz_msg35",LangFile);
                                        data.setMessage(quiztitle+" "+ msg + " Date "+date1+" Time "+stTime);
                                }
                        }
			**/
			context.put("flag",startQuiz);
			context.put("QuizGrade",QuizGrade);
			context.put("startQuiz1",startQuiz1);
			u=null;
		}
                catch(Exception e)
                {
			ErrorDumpUtil.ErrorLog("The exception in getting detail ::"+e);
			data.setMessage("See ExceptionLog !! ");
		}
	}
}


