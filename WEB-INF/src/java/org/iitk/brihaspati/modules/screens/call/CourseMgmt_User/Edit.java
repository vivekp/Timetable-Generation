package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)Edit.java	
 *
 *  Copyright (c) 2005-2006,2009 ETRG,IIT Kanpur. 
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
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 * This class for Editing course contents 
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *
 */

public class Edit extends SecureScreen{

	/**
	 * This is the default method that builds the template page
	 * @param data Rundata
	 * @param context Context
	 */

	public void doBuildTemplate(RunData data,Context context) 
	{
		try
		{
			User user=data.getUser();

			/**
			* Get the topic name from URL and put it in context
		 	*/
			String cName=data.getParameters().getString("cName","");
                        context.put("cName",cName);
			String topic=data.getParameters().getString("topic","");
			context.put("topic",topic);
			String username=data.getParameters().getString("uname","");
			context.put("username",username);
			String status=data.getParameters().getString("status","");
			context.put("status",status);

			AccessControlList acl=data.getACL();

			/**
		 	* Retreive the course id and course name from the
		 	* temporary variable and get the file path for XML file name
		 	*/
			String topicDesc="";
			Vector v=new Vector();
			String group=(String)user.getTemp("course_id");
			context.put("course",(String)user.getTemp("course_name"));
			if(!topic.equals(""))
			{
				
				String filePath;
				if(status.equals("Repo"))
				{
					filePath=data.getServletContext().getRealPath("/Courses")+"/"+group+"/Content/Permission/"+username+"/"+topic;
				}
				else if(status.equals("Remote"))
                                {
                                        filePath=data.getServletContext().getRealPath("/Courses")+"/"+group+"/Content/Remotecourse/"+username+"/"+cName+"/"+topic;


                                }
				else
				{
				filePath=data.getServletContext().getRealPath("/Courses")+"/"+group+"/Content/"+topic;
				}	
				if( acl.hasRole("instructor",group) )
				{
					context.put("isInstructor","true");
				}
				/**
			 	* Get description from the XML file
			 	* @see TopicMetaDataXmlReader in utils
			 	*/
				File f = new File(filePath+"/"+topic+"__des.xml");
				if(f.exists())
				{
				TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+topic+"__des.xml");
				topicDesc=topicMetaData.getTopicDescription();
				v=topicMetaData.getFileDetails();
				}
			}
			else
			{
				data.setMessage("The Topic Name is Null !!");
			}
			/**
		 	* Put all the required variables in context
		 	*/
				if(v==null)
                                return;
				if(v.size()!=0)
				{
				
					context.put("topicDesc",topicDesc);
					context.put("dirContent",v);
					context.put("Mode","NoBlank");
				}
				else
				{
					context.put("Mode","Blank");
				}
				context.put("courseid",group);
		}catch(Exception e)
		{
			data.setMessage("The error in Editing in Course Contents "+e);
		}	

	}

}
