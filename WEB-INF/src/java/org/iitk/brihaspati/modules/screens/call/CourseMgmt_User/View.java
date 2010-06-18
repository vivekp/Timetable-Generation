package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)View.java	
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

/**
 * This class contains code for viewing course contens and downloading
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Aawadhesh Kumar trivedi</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;
import org.apache.turbine.modules.screens.VelocitySecureScreen;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.Turbine;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.actions.RemoteCoursesAction;
import org.iitk.brihaspati.modules.utils.CommonUtility;

public class View extends VelocitySecureScreen{
	/**
	 * This is the default method that builds the template page
	 * @param data Rundata
	 * @param context Context
	 */
	public void doBuildTemplate(RunData data,Context context) 
	{
		Vector v=new Vector();
		User user=data.getUser();

		/**
		 * Get the topic name from URL and put it in context
		 */
		
		String topic=data.getParameters().getString("topic","");
		context.put("topic",topic);
		String status=data.getParameters().getString("status","");
		String username=data.getParameters().getString("uname","");
		String cName=data.getParameters().getString("cName","");
		/**
		 * Check if the user is an admin or not
		 */

		AccessControlList acl=data.getACL();
		context.put("isAdmin",acl.hasRole("turbine_root")?"true":"false");
		String group,dir;

		/**
		 * Retreive the course id and course name from the
		 * temporary variable and get the file path for XML file name
		 */

		group=dir=(String)user.getTemp("course_id");
		context.put("course",(String)user.getTemp("course_name"));
		String filePath=null;
		if(status.equals("Repo"))
		{
			filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/Permission/"+username+"/"+topic;
		}
		else if(status.equals("Remote"))
                {
                        filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/Remotecourse/"+username+"/"+ cName+"/"+topic;
                         /**
                         * check if user is primary instructor
                         * as he alone should know from which Institute Course has come
                         */
                         context.put("instnm",RemoteCoursesAction.getRemoteInstituteName(username));
                         String nameofuser=user.getName();
                         boolean check_Primary=CourseManagement.IsPrimaryInstructor(dir,nameofuser);
                         if(check_Primary)
                         context.put("role","instructor");
                         else
                         context.put("role","");

                }
		else 
		{
			filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic;	
		}
		if(acl.hasRole("instructor",group)){
			File filePathUn=new File(data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic+"/Unpublished/");
			String[] listFile=filePathUn.list();
			context.put("unpublist",listFile);
		}
		String fPth=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/";	
		
		if(acl.hasRole("instructor",group)||acl.hasRole("author",group))
		{
			context.put("isInstructor","true");
			context.put("isAuthor","true");
		}
		String topicDesc="";
		Vector fileType=new Vector();
		try
		{
			/**
			 * Get description from the XML file
			 * @see TopicMetaDataXmlReader in utils
			 */
			File f1=new File(filePath+"/"+topic+"__des.xml");
                        if(f1.exists())
                        {
				TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+topic+"__des.xml");
				topicDesc=topicMetaData.getTopicDescription();
				v=topicMetaData.getFileDetails();
				if(v==null)
	                        return;
				for(int i=0;i<v.size();i++)
				{
					String element=((FileEntry)v.get(i)).getName();
					String absoluteFilePath=filePath+"/"+element;
					File f=new File(absoluteFilePath);
					if(f.isDirectory()){
						fileType.add("directory");
					}
					else{
						fileType.add("file");
					}
					if(v.size()!=0)
					{
						context.put("topicDesc",topicDesc);
						context.put("dirContent",v);
						context.put("fileType",fileType);
						context.put("Mode","NoBlank");
					}
					else
					{
						context.put("Mode","Blank");
					}
					context.put("courseid",dir);
				}
			}//if
		
				context.put("topic",topic);
				context.put("status",status);
				context.put("username",username);
				context.put("cName",cName);
		}//try
		catch(Exception e)
		{
			data.setMessage("The error in viewing !!"+e);
		}

	}

	/**
	 * This method checks the authorization of the user
	 * @param data Rundata
	 * @return boolean
	 */

	protected boolean isAuthorized( RunData data )  throws Exception
        {
                boolean isAuthorized = false;
                try
                {
                        AccessControlList acl = data.getACL();
                        User user=data.getUser();
                        String g=user.getTemp("course_id").toString();

                        if (acl==null || (! acl.hasRole("instructor",g) && !acl.hasRole("Author",g) && !acl.hasRole("student",g)&& !acl.hasRole("turbine_root")) )
                        {
                                data.setScreenTemplate( Turbine.getConfiguration().getString("template.login"));

                                isAuthorized = false;
                        }
                        else if(acl.hasRole("instructor",g) || acl.hasRole("student",g) || acl.hasRole("turbine_root"))
                        {
                                isAuthorized = true;
                        }
                }
                catch(Exception e)
                {
                        data.setScreenTemplate(Turbine.getConfiguration().getString("template.login"));
                        return false;
                }
                return isAuthorized;
        }

}

