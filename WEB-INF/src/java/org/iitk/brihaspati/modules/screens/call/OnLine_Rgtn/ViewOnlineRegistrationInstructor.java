package org.iitk.brihaspati.modules.screens.call.OnLine_Rgtn;

/*
 * @(#)ViewOnlineRegistrationInstructor.java
 *
 *  Copyright (c) 2008 ETRG,IIT Kanpur.
 *  
 *  All Rights Reserved.
 *  Redistributions of source code must retain the above copyright
 *  notice, this  list of conditions and the following disclaimer.
 *  Redistribution in binary form must reproducuce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 */



import java.io.File;
import java.util.List;
import java.util.Vector;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.DbDetail;



/**
 * @author <a href="mailto:ynsingh@iitk.ac.in">Dr. Y. N. Singh </a>	
 * @author  <a href="mailto:omprakash_kgp@yahoo.co.in">Om Prakash</a>
 */


public class ViewOnlineRegistrationInstructor extends SecureScreen_Instructor {

	/**
	 * Place all the data object in the context for use in the template.
	 * @param data RunData
	 * @param context Context
	 */

	public void doBuildTemplate( RunData data, Context context ){
	try{
		
		User user=data.getUser();
                ParameterParser pp=data.getParameters();
                String lang=pp.getString("lang","english");
                context.put("lang",lang);
		String course=(String)user.getTemp("course_name");
		context.put("course",course);
	 	String status=pp.getString("status","");
 		String path=TurbineServlet.getRealPath("/OnlineUsers");
                Vector entry=new Vector();
                File xmlfile= new File(path+"/OnlineUser.xml");
                if(xmlfile.exists())
                {
                        TopicMetaDataXmlReader topicmetadata=null;
                       	Vector list=new Vector();
                       	topicmetadata=new TopicMetaDataXmlReader(path+"/OnlineUser.xml");
                      	list=topicmetadata.getOnlineUserDetails();
			context.put("list",list);
			String courseid=data.getUser().getTemp("course_id").toString();
			context.put("courseID",courseid);
                       	if(list!=null)
                       	{
                               	for(int i=0;i<list.size();i++)
                               	{

                               	       	String gname=((CourseUserDetail) list.elementAt(i)).getGroupName();
                               	   	if(gname.equals(courseid))
					{
						String uname=((CourseUserDetail) list.elementAt(i)).getLoginName();
                               	        	String passwd=((CourseUserDetail) list.elementAt(i)).getActive();
                                                String fname=((CourseUserDetail) list.elementAt(i)).getInstructorName();
                                                String lname=((CourseUserDetail) list.elementAt(i)).getUserName();
                                                String orgtn=((CourseUserDetail) list.elementAt(i)).getDept();
                               	        	String email=((CourseUserDetail) list.elementAt(i)).getEmail();
                               	               	String roleName=((CourseUserDetail) list.elementAt(i)).getRoleName();
                               	        	DbDetail dbDetail= new DbDetail();
                               	        	dbDetail.setSender(uname);
                               	        	dbDetail.setPDate(passwd);
						dbDetail.setExpiryDate(fname);
						dbDetail.setPermission(lname);
						dbDetail.setGrpmgmtType(orgtn);
                               	        	dbDetail.setMSubject(email);
                               	        	dbDetail.setStatus(gname);
                               	        	dbDetail.setMsgID(roleName);
                               	        	entry.addElement(dbDetail);
					}
					
                               	}
                       	}
                    else {
				
                               	xmlfile.delete();
                       	}
                }
 		else{
			 data.setMessage(MultilingualUtil.ConvertedString("online_msg8",user.getTemp("LangFile").toString()));
                       //	data.setMessage("Sorry, No User Registration till now !!");
                }
			context.put("entry",entry);
               	

	}//try end
	catch(Exception e) { 
		ErrorDumpUtil.ErrorLog("The error in Online registartion reading file" +e);
		data.setMessage("Please see Error log or Contact to administrator");
	}
	}//end method
}//end class

