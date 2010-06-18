package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/*
 * @(#)Quiz_Schedule.java	
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

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Calendar;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.YearListUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import java.io.File;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.FileEntry;
import com.workingdogs.village.Record;
import java.util.ListIterator;

public class Quiz_Schedule extends SecureScreen_Instructor
{
	/**
	* Place all the data object in the context
    	* for use in the template.
     	*/
    	public void doBuildTemplate( RunData data, Context context )
	{
		try{
			User user=data.getUser();
			String username=user.getName();
                        String uid=Integer.toString(UserUtil.getUID(username));
		 	String courseid=(String)user.getTemp("course_id");
			String currentdate=ExpiryUtil.getCurrentDate("-");
			
			ParameterParser pp=data.getParameters();
			String mode=pp.getString("mode","");
			context.put("quizinfo",mode);
			if(mode.equals("QuizDetail"))
			{
				String qname=pp.getString("qname","");
				context.put("qid",qname);	
				Criteria crit=new Criteria();
	                        crit.add(QuizPeer.QUIZ_ID,qname);
	                        crit.add(QuizPeer.CID,courseid);
	                        crit.add(QuizPeer.USER_ID,uid);
        	                List list=QuizPeer.doSelect(crit);
				for(int i=0;i<list.size();i++)
				{
					Quiz element=(Quiz)(list.get(i));
	                                String str1=(element.getPostDate().toString());
	                                String str2=(element.getQuizTitle().toString());
	                                int mmarks=(element.getMaxMarks());
					context.put("title",str2);
					context.put("marks",mmarks);
        	                        str1=str1.substring(0,10);
					//Date
					Vector date=ExpiryUtil.getPostDate(str1);
					context.put("cyear",date.elementAt(0));
					context.put("cmonth",date.elementAt(1));
					context.put("cdays",date.elementAt(2));
					//Date
					//TIME	
					String str3=(element.getStartTime().toString());
					String str4=(element.getEndTime().toString());
                	        	str3=str3.replaceAll(":","-");
                	        	str4=str4.replaceAll(":","-");
					Vector date1=ExpiryUtil.getPostDate(str3);
					context.put("hr",date1.elementAt(0));
                                        context.put("m",date1.elementAt(1));
					Vector date2=ExpiryUtil.getPostDate(str4);
					context.put("hr1",date2.elementAt(0));
                                        context.put("m1",date2.elementAt(1));	
					String strdatetype=currentdate.replaceAll("-","");
					strdatetype=strdatetype.substring(0,8);
					try{
					int strint=Integer.parseInt(str1.replaceAll("-",""));
					int checkdate=Integer.parseInt(strdatetype);
					if(strint == checkdate) {	
						try {
						Calendar calendar=Calendar.getInstance();
			                        int curmin=calendar.get(Calendar.HOUR);
                        			int am_pm=calendar.get(Calendar.AM_PM);
						if(am_pm == 1)
                                 			curmin= curmin+12;
                        			curmin= (curmin*60) +(calendar.get(Calendar.MINUTE));
						String starttime=str3.substring(0,5);
                                        	String [] str2array = starttime.split("-");
                                        	int starthour=0;int endhour=0;
                                        	for(int j=0;j<2;j++)
                                        	{
                                                	if(j==0){
                                                       	 starthour=Integer.parseInt(str2array[j])*60;
                                                	}
                                                	else {
                                                       	 starthour=starthour+Integer.parseInt(str2array[j]);
                                                	}
                                        	}
						if(( starthour < curmin))
                                        	{
							String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+qname+"/Student_Quiz");
                                                        File f=new File(path);
                                                        if(f.exists()) {
                                                        String temparr1[]=f.list();
                                                        if(temparr1.length!=0)
                                                                context.put("totaltime","BlockUpdate");
                                                        else
                                                                context.put("totaltime","notBlockUpdate");
                                                        }
                                                        else
                                                                context.put("totaltime","notBlockUpdate");
                                        	}
						else {
							
							/**String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+qname+"/Student_Quiz");
							File f=new File(path);
							if(f.exists()) {
							String temparr1[]=f.list();
							if(temparr1.length!=0)
								context.put("totaltime","BlockUpdate");
							else
								context.put("totaltime","notBlockUpdate");
							}
							else
							*/
								context.put("totaltime","notBlockUpdate");
						}
						} catch(Exception e){}	
					}
					else if(strint < checkdate){
						try {
							String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+qname+"/Student_Quiz");
                                                        File f=new File(path);
                                                        if(f.exists()) {
                                                        String temparr1[]=f.list();
                                                        if(temparr1.length!=0)
                                                                context.put("totaltime","BlockUpdate");
                                                        else
                                                                context.put("totaltime","notBlockUpdate");
                                                        }
                                                        else
                                                                context.put("totaltime","notBlockUpdate");
						}catch(Exception e){}

					}	
					else {
						try {
						String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+qname+"/Student_Quiz");
						File f=new File(path);
						if(f.exists()) {
							String temparr[]=f.list();
							String temparr1[]=f.list();
                                                	if(temparr1.length!=0)
                                                		context.put("totaltime","BlockUpdate");
							else
								context.put("totaltime","notBlockUpdate");
						}
						else
							context.put("totaltime","notBlockUpdate");
						} catch(Exception e){}
					}	
					}catch(Exception e){ErrorDumpUtil.ErrorLog(e.getMessage());}	
				}
				
				
			}
			String cmonth=currentdate.substring(5,7);
			String cday=currentdate.substring(8,10);
			//context.put("cmonth",cmonth);
			//context.put("cdays",cday);
			Vector hour=new Vector();
        	        for(int i=0;i<=23;i++){
                		String hr=new String();
                        	if(i<10){
                               		hr="0"+i;
				}
	                       	else{
        		              	hr=Integer.toString(i);
				}
                        	        hour.addElement(hr);
                	}
			String cdate=ExpiryUtil.getCurrentDate("");
                        int currentdate1=Integer.parseInt(cdate);
			int cyear1=currentdate1/10000;
                        String cyear=Integer.toString(cyear1);
                        context.put("year",cyear);
	                context.put("hour",hour);
			Vector year=YearListUtil.getYearList();
			context.put("year_list",year);
			if(!mode.equals("QuizDetail")) {
				context.put("cmonth",cmonth);
				context.put("totaltime","notBlockUpdate");
	                        context.put("cdays",cday);
				Criteria crit=new Criteria();
	                	crit.add(QuizPeer.CID,courseid);
	                	crit.addAscendingOrderByColumn(QuizPeer.ID);
        	        	List list=QuizPeer.doSelect(crit);
				if(list.size()!=0){
					String ele="";
					for(int i=0;i<list.size();i++)
					{
						ele=(((Quiz)(list.get(i))).getQuizId()).toString();
					}
					int num=Integer.parseInt(ele.substring(4));
					context.put("qid","quiz"+(num+1));
				}
				else{
					context.put("qid","quiz1");
				}
			}
			
			
		}
		catch(Exception ex){
			ErrorDumpUtil.ErrorLog("The exception in quiz scheduling!!"+ex);
			data.setMessage("See ExceptionLog !! ");
		}
	}

}

