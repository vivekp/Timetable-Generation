package org.iitk.brihaspati.modules.screens.call.Assignment;

/*
 * @(#) RePostAns.java
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

 
import java.io.File;

import java.util.List;
import java.util.Date;
import java.util.Vector;

import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.om.Assignment;
import org.iitk.brihaspati.om.AssignmentPeer;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;


	/** 
	* This class contains code of Repost Answer to the Assignment
	* @author<a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
	*/

public class RePostAns extends  SecureScreen
{
        
	public void doBuildTemplate(RunData data, Context context)
        {
                try
                {
                        User user=data.getUser();
                        String UserName=data.getUser().getName();
			
			context.put("Ans","Ans");
                        context.put("coursename",(String)user.getTemp("course_name"));
                        
			String courseid=(String)user.getTemp("course_id");
			
			Date curdate=new Date();
                        long longCurDate = curdate.getTime();
                       	int curdate1=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
                       	String Assign=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Assignment");
                        Vector w=new Vector();
			String cdate=ExpiryUtil.getCurrentDate("");
			
			String date1=cdate.substring(0,4);
                        date1=date1+"-"+cdate.substring(4,6)+"-"+cdate.substring(6,8);
                        context.put("date",date1);
			
			Criteria crit=new Criteria();
                        crit.add(AssignmentPeer.GROUP_NAME,courseid);
                        List u=AssignmentPeer.doSelect(crit);
			
			for(int i=0;i<u.size();i++)
                        {       Assignment element=(Assignment)(u.get(i));
                                Date date=(element.getDueDate());
                                long date2=date.getTime();
				long redate=(longCurDate-date2)/(24*3600*1000);
                                if(redate>=0)
                                {
					String str1=(element.getAssignId());
                                        String Assign1 =Assign+"/"+str1;
					try{
					TopicMetaDataXmlReader topicmetadata1=null;
                                        Vector Assignmentlist1=new Vector();
                                        topicmetadata1=new TopicMetaDataXmlReader(Assign1+"/__permission.xml");  
					Assignmentlist1=topicmetadata1.getAssignmentDetails();
					File f2= new File(Assign1+"/__permission.xml");
					if(f2.exists())
                                        {	if(Assignmentlist1!=null)
						{	for(int grade=0;grade<Assignmentlist1.size();grade++)
                                        		{
						 		String date3="";  
								String Duedate =((FileEntry) Assignmentlist1.elementAt(grade)).getDuedate();
		                                       		String  fileName =((FileEntry) Assignmentlist1.elementAt(grade)).getfileName();
								Duedate=Duedate.replaceAll("-","");
								int date4=Integer.parseInt(Duedate);
									
								if(date4>=curdate1)
			                                        {
									String str3=(element.getTopicName());
									if(fileName.equals("All"))
			                        				w.add(str3);    
									else if(fileName.equals(UserName))
										w.add(str3);
								}//if 
							} //for
						}
					}
					}
					catch(Exception e){  } 
				}  //if
                        }   //for
                        context.put("allTopics",w);
                }//try
                catch(Exception e){  }
        }
}
	
