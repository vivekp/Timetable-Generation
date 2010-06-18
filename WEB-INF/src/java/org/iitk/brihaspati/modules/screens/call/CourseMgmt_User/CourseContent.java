package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)CourseContent.java	
 *
 *  Copyright (c) 2006-2008 ETRG,IIT Kanpur. 
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

import java.util.Vector;
import java.io.File;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.security.AccessControlList;
//import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.Turbine;
//import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.om.security.User;
import org.apache.turbine.modules.screens.VelocitySecureScreen;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
//import org.iitk.brihaspati.modules.utils.NotInclude;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
//import org.iitk.brihaspati.modules.actions.RemoteCoursesAction;
//import org.iitk.brihaspati.modules.actions.UploadAction;
/**
* This class manage all course contents
* @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
* @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
* @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
*/

public class CourseContent extends VelocitySecureScreen{
	 MultilingualUtil Mutil=new MultilingualUtil();

	public void doBuildTemplate(RunData data,Context context) 
	{
		String langfile=data.getUser().getTemp("LangFile").toString();
		try{

			Vector v=new Vector();
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
			AccessControlList acl=data.getACL();
			context.put("isAdmin",acl.hasRole("turbine_root")?"true":"false");
			String group,dir;
			String stat=pp.getString("stat","");
			context.put("course",(String)user.getTemp("course_name"));
			group=dir=(String)user.getTemp("course_id");

			String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content";

			String filePath1=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Permission";
	
			File f=new File(filePath1+"/permissionReceive__des.xml");

			if( acl.hasRole("instructor",group))
			{
				context.put("isInstructor","true");
			}
			
			File Path=new File(filePath+"/content__des.xml");
                        ErrorDumpUtil.ErrorLog("path in coursecontent at line 92-->"+Path);
			/**
			* Write all topic in xml file if topic is not present
			*/
			CommonUtility.cretUpdtxml(filePath);
/*			String filter[]={"Permission","Remotecourse","content__des.xml"};
                        NotInclude exclude=new  NotInclude(filter);
                        String file[]=(new File(filePath)).list(exclude);
                        for(int i=0;i<file.length;i++)
                        {
				// get the value from xml file
				if(Path.exists()){
					TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+"content__des.xml");
					String tpcxml[]=topicMetaData.getTopicNames();

					for(int j=0;j<tpcxml.length;j++){
						String tnm=tpcxml[j];
				//check topic name exist in xml file or not. If not then write in to xml file
						if(file[i].equals(tnm)){
						}
						else{
		                                	UploadAction.topicSequence(filePath,file[i],((new Date()).toString()),data);
						}
					}
				}
				else{
					UploadAction.topicSequence(filePath,file[i],((new Date()).toString()),data);
				}
                        }
*/

                        if(Path.exists())
                        {
                                TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+"content__des.xml");
                                Vector dc=topicMetaData.getFileDetails();
                                if(dc.size()!=0)
                                {
                                        context.put("dirContent",dc);
                                }
                                ErrorDumpUtil.ErrorLog("vector in coursecontent at line 100--->"+dc);
                        }

			
			//File dirHandle=new File(filePath);

			/**
			* Filter out Permission from Courses/Content directory
			*/

			/*String filter[]={"Permission","Remotecourse"};
                        NotInclude exclude=new  NotInclude(filter);
			String file[]=dirHandle.list(exclude);
			for(int i=0;i<file.length;i++)
			{
				v.addElement(file[i]);
			}
			context.put("courseid",dir);*/
			//context.put("dirContent",v);
			if(f.exists())
			{
				Vector Read=new Vector();
				TopicMetaDataXmlReader permissionRead=new TopicMetaDataXmlReader(filePath1+"/permissionReceive__des.xml");
				Read=permissionRead.getDetails();
                        	context.put("read",Read);
			}
			
                        /**
                        * Remote Course Path
                        */

                        String filePathR=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Remotecourse";

                        File fR=new File(filePathR+"/RemoteCourse__des.xml");


                        if(fR.exists())
                        {
                                Vector ReadR=new Vector();
                                TopicMetaDataXmlReader permissionReadR=new TopicMetaDataXmlReader(filePathR+"/RemoteCourse__des.xml");
                                ReadR=permissionReadR.getDetails();
                                context.put("readR",ReadR);
                        }


		}//try
		catch(Exception ex)
		{
		data.setMessage("The error in Course Contents !! "+ex);
		}
	}
	/**
	* This method responsible for authorization
	* @param data RunData
	*/	
	protected boolean isAuthorized( RunData data )  throws Exception
        {
                boolean isAuthorized = false;
                AccessControlList acl = data.getACL();
		if( acl.hasRole("turbine_root") )
		{
			isAuthorized=true;
			return isAuthorized;
		}
                try
                {
                        User user=data.getUser();
                        String g=user.getTemp("course_id").toString();

                        if (acl==null || (! acl.hasRole("instructor",g) && !acl.hasRole("student",g) && !acl.hasRole("turbine_root")) )
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

