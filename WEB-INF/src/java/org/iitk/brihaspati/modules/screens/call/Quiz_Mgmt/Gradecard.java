package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/*
 * @(#)Gradecard.java
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
//import java.io.FileReader;
//import java.io.BufferedReader;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;
import java.util.LinkedList;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.QuizDetail;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;	

import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 *   This class contains code for all discussions in workgroup
 *   Compose a discussion and reply.
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
*/


public class Gradecard extends SecureScreen
{
    	public void doBuildTemplate( RunData data, Context context )
	{
		try{
			String LangFile=data.getUser().getTemp("LangFile").toString();	
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
                        String loginname=user.getName();
		        int user_id=UserUtil.getUID(loginname);
                        String cid=(String)user.getTemp("course_id");
            		String createquiz=TurbineServlet.getRealPath("/Courses/"+cid+"/Quiz");
		        int g_id=GroupUtil.getGID(cid);
			// student case userList1 is null and In case of instructor userList1 is student name

			String userList1=pp.getString("userList","");
			String Role=(String)user.getTemp("role");
			
                        context.put("user_role",Role);

			if(Role.equals("student"))
			{
				userList1=loginname;
				context.put("userList1",userList1);
			}

                       	Vector vqid=new Vector();
			if(Role.equals("instructor")){
				context.put("userList1",userList1);
        		        Vector userList=UserGroupRoleUtil.getUDetail(g_id,3);
                		context.put("userList",userList);
				userList=null;
				//Get the Current time
		 		Calendar calendar=Calendar.getInstance();
                	        int curmin=calendar.get(Calendar.HOUR);
	                        int am_pm=calendar.get(Calendar.AM_PM);
        	                if(am_pm == 1)
                	                 curmin= curmin+12;
                        	curmin= (curmin*60) +(calendar.get(Calendar.MINUTE));
				//Get the list of Quiz
				Criteria crit=new Criteria();
				crit=new Criteria();
                	        crit.add(QuizPeer.CID,cid);
                	        crit.add(QuizPeer.USER_ID,user_id);
                        	List u = QuizPeer.doSelect(crit);

	                        String datestr1="-";
        	                if(u.size() != 0)
                        	{
                                	for(int i=0;i<u.size();i++)
                                	{
						//Retrive the database information
                                        	Quiz element=(Quiz)(u.get(i));
	                                        String str1=element.getPostDate().toString();
        	                                str1=str1.substring(0,10);
                	                        str1=str1.replaceAll(datestr1,"");
                        	                int date1=Integer.parseInt(str1);
						//Get the current date
                                	        int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
						//Getting the list of quiz which is completed today
                                       		if(date1 == curdate)
                                		{	
                        	                	String str3=(element.getEndTime().toString());
        	                                	str3=str3.substring(0,5);
        	        	                        String [] str3array = str3.split(":");
							str3="";
							int endhour=0;
                                	        	for(int j=0;j<2;j++)
	                                        	{
		                                                if(j==0){
                		                                        endhour=Integer.parseInt(str3array[j])*60;
                        		                        }
                                		                else {
                                                		        endhour=endhour+Integer.parseInt(str3array[j]);
                                                		}
                                        		}
	        	                                //link startQuiz is open
                                        		if(endhour < curmin) 
							{
								vqid.add(element.getQuizId());
							}
						}
						if(date1<curdate) { 
							vqid.add(element.getQuizId());
							ErrorDumpUtil.ErrorLog(" In grade file Instructor block post date   "+date1+"  curdate  "+curdate);
						}	
					}//for loop
				}//if condition
				u=null;
				context.put("vqid",vqid);
			}	//if Instructor
			if(!userList1.equals("")){
				int str[]=new int[1000];
				int studntid=UserUtil.getUID(userList1);
        	        	String str4=Integer.toString(studntid);  
			//Get the list of students ids
        		        Vector v=UserGroupRoleUtil.getUID(g_id,3);
                		LinkedList li1=new LinkedList();
		                Vector qname=new Vector();
                		int curentstudent=0;
		                int mmarks=0; 
				int totalmarks=0;	

			//Calculate Max marks and total marks
				for(int i=0;i<v.size();i++)
                		{//for #1
		                        String s=(v.get(i)).toString();
			        	TopicMetaDataXmlReader topicmetadata1=null;
		               	        File f2= new File(createquiz+"/GradeCard.xml");
					if(!f2.exists())
        	        	        {
                                		TopicMetaDataXmlWriter.writeWithRootOnly1(f2.getAbsolutePath());
		                                XmlWriter xmlwriter=new XmlWriter(createquiz+"/GradeCard.xml");
	        	                }

					//check the number of line in f2
					long numLines=StringUtil.checkNumberoflines(f2);
					if(numLines<=2){
						String msg=MultilingualUtil.ConvertedString("quiz_msg14",LangFile);
	                                	data.setMessage(msg);
						return;
					}

                        		if(f2.exists()) //f2.exists()
                        		{
					ErrorDumpUtil.ErrorLog("idlist.size() before  "+f2.length());
					
                        		Vector idlist=new Vector();
		                        TopicMetaDataXmlReader topicmetadata2=new TopicMetaDataXmlReader(createquiz+"/GradeCard.xml");
        		                idlist=topicmetadata2.getAssignmentDetails1();
					boolean flag=true;
                        		int mark=0;
                                	int umark=0;
					if(idlist==null){
					ErrorDumpUtil.ErrorLog("idlist.size()"+idlist.size());
						return;
					}
				//Get the total marks for perticular students
                                	for(int k=0;k<idlist.size();k++)//fOR#2
                                	{
	                               		 String id=((FileEntry) idlist.elementAt(k)).getGrade().trim();
         	                               	 String marks=((FileEntry) idlist.elementAt(k)).getfeedback().trim();
                                        	if(id.equals(s))
                                        	{
                                                	mark=mark+(Integer.parseInt(marks));
                                        	}
						//Getting the detail of perticular student
                                        	if(id.equals(s) &&  s.equals(str4))
                                        	{
							flag=false;
                                                	String qid=((FileEntry) idlist.elementAt(k)).getUserName().trim();
                                			totalmarks=totalmarks+(Integer.parseInt(marks));               

							Criteria crit1=new Criteria();
				                	crit1.add(QuizPeer.QUIZ_ID,qid);
				                	crit1.add(QuizPeer.CID,cid);
				                	List li2=QuizPeer.doSelect(crit1);
							//try{	
                					for(int count=0;count<li2.size();count++){//FOR #3
				                        	Quiz element1=(Quiz)(li2.get(count));
								QuizDetail qDetail= new QuizDetail();
                               					qDetail.setmaxmarks(Integer.toString(element1.getMaxMarks())); //max marks
				                                qDetail.setfeedback(marks);//optain
				                                qDetail.setquizid(qid); //quizid
								String str1=element1.getPostDate().toString();
								str1=str1.substring(0,10);
				                                qDetail.setQDate(str1); //duedate
								qname.addElement(qDetail);
								//Getting Total max marks for calculating percentage
						        	mmarks=mmarks+(element1.getMaxMarks());    //max..marks
							}  //for #3
							curentstudent=mark;//replace it from total marks
							li2=null;
          					}  //if perticular marks
					}// for #2
				try{	
					if(!(li1.contains(Integer.toString(mark)))) {
					//use list in place of array and linked list
						str[li1.size()]=mark;
	                                	li1.add(Integer.toString(mark));
						ErrorDumpUtil.ErrorLog(" Making a list of marks of all students in a course   "+li1);
					}
				}
				catch(Exception ex){ErrorDumpUtil.ErrorLog("Error in contains block==>"+ex);}
				} //if exist
			context.put("size",qname.size());
			} //for #1
			if(qname.size()==0) {
				String msg=MultilingualUtil.ConvertedString("quiz_msg14",LangFile);  
				data.setMessage(msg);
				return;
			}
		//Calculate the grade 
			int percentage=(totalmarks*100)/mmarks;
			context.put("totalmarks",mmarks);
			String path=TurbineServlet.getRealPath("/Courses"+"/"+cid);
        	        File f1=new File(path+"/configuregrade.xml");
                	if(f1.exists())

                	{
							ErrorDumpUtil.ErrorLog(" In grade file  inside gard block " );
		                String grd="F";
				TopicMetaDataXmlReader xmlreader1=null;
                	        Vector vect=new Vector();
                        	xmlreader1=new TopicMetaDataXmlReader(path+"/configuregrade.xml");
	                        vect=xmlreader1.getAssignmentDetails1();
        	                for(int kk=0;kk<vect.size();kk++)
                	        {
                                	String marks=((FileEntry) vect.elementAt(kk)).getUserName();
                        	        int per=Integer.parseInt(marks);
					if(percentage >= per) 
					{	grd=((FileEntry) vect.elementAt(kk)).getGrade();
						break;
					}
				}
				context.put("per",grd);
			}//end if
	                context.put("percentage",percentage);
			
                	for(int n=0;n<li1.size();n++)
                	{	
							ErrorDumpUtil.ErrorLog(" In grade file  inside sorting block " );
                		for(int n1=n+1;n1<li1.size();n1++)
	                        {
					
        		                if(str[n1] >  str[n])
                		        {
	                		        int t =str[n];
                                		str[n] = str[n1];
	                                	str[n1]=t;
        				}
				}
			}
			//for free memory
			li1=null;
			//Calculating rank
		        String ranks="";
                	for(int i=0;i<str.length;i++)
                	{
							ErrorDumpUtil.ErrorLog(" In grade file  inside rank block " );
        	        	if(str[i] == (totalmarks))
                        	{
					context.put("ranks",(Integer.toString(i+1)));
	                                context.put("overallgrade",str[i]);
					break;
				}
                	}	
                	context.put("qname",qname);
	qname=null;
	v=null;
		
	}  //instructor  and student if exist
	
	if(Role.equals("instructor")){
			Vector  v=UserGroupRoleUtil.getUID(g_id,3);
			File file1=new File(createquiz);
			//   xml reader
			TopicMetaDataXmlReader topicmetadata1=null;
                        Vector Quizlist=new Vector();
                        topicmetadata1=new TopicMetaDataXmlReader(createquiz+"/GradeCard.xml");
                        Quizlist=topicmetadata1.getAssignmentDetails1();
			//   xmlReader
			if(file1.isDirectory())
                        {	
				ErrorDumpUtil.ErrorLog("list of quiz files comparenum "+vqid.size());	
				for(int w=0;w<vqid.size();w++)//quiz list 
                                {
				//Calculate score for per quiz basis per student
					for(int z=0;z<v.size();z++)//user list
                        	        {
						
						File userfile=new File(createquiz+"/"+((vqid.get(w)).toString())+"/"+((v.get(z)).toString())+".xml");
						int totalnumber=0;
						try {
							if(userfile.exists())
							{
								TopicMetaDataXmlReader xmlreader=null;
								Vector vect=new Vector();
        	                                                xmlreader=new TopicMetaDataXmlReader(createquiz+"/"+((vqid.get(w)).toString())+"/"+((v.get(z)).toString())+".xml");//Userid.xml
								vect=xmlreader.getAssignmentDetails1();
                        	                                for(int k=0;k<vect.size();k++)
                                	                        {
                                        	                	String grade=((FileEntry) vect.elementAt(k)).getGrade().trim();
									int temp=Integer.parseInt(grade);
									totalnumber=totalnumber+temp ; 
								}
							}
						}catch(Exception e){ErrorDumpUtil.ErrorLog("The exception in Gradecard file score block::"+e);}	
						//Writer		
						//resder  if exits
						int updateCheck=-1;
						Vector xmlVct=new Vector();
						boolean flag=true;
					//
						if(Quizlist!= null){
							for(int i=0;i<Quizlist.size();i++) {
								String user1=((FileEntry) Quizlist.elementAt(i)).getGrade().trim();  //userid
	                        			        String qnames=((FileEntry) Quizlist.elementAt(i)).getUserName().trim();  //quizid
        	                			        String number=((FileEntry) Quizlist.elementAt(i)).getfeedback().trim();  //marks
								int comparenum=Integer.parseInt(number);
					                        if( user1.equals(((v.get(z)).toString())) && qnames.equals(((vqid.get(w)).toString()))) {
									if(comparenum < totalnumber)	{
										      ErrorDumpUtil.ErrorLog("updateCheck=i  "+i);
										      updateCheck=i;	
										      xmlVct.add(updateCheck);
									}
									else {
										flag=false;
									}
								}
							}// for
						}//if
				//Write update and new entry in grade card xml
						if(flag){
							if(xmlVct.size()==0)
								xmlVct.add(updateCheck);
	                                                XmlWriter xmlwriter=null;
							//String temp="blank";
		                                	xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(createquiz,"/GradeCard.xml",xmlVct);
							String number1=Integer.toString(totalnumber);
							TopicMetaDataXmlWriter.appendGrade(xmlwriter,(vqid.get(w)).toString(),((v.get(z)).toString()),number1);
                                			xmlwriter.writeXmlFile();
						} //flag
					}  //for user list
					}//for quiz list
				} //if file
				
			} //if instructor
//	} //if
//	
	}
        catch(Exception e){
		ErrorDumpUtil.ErrorLog("The exception in Gradecard file::"+e);
		data.setMessage("See ExceptionLog:: ");
	}
	}
	
	
}


