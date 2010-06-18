package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)PublishModule.java	
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
import java.util.Vector;
import java.util.Date;
import java.io.File;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;

/**
 * This class contains code for Publishing files
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
 * @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class PublishModule extends SecureScreen_Instructor{
	/**
	* This method for publishing file for visible or accessible or unpublished area
	* @param data RunData
	* @param context Context
	* @see TopicMetaDataXmlReader from Utils
	*/
	public void doBuildTemplate(RunData data,Context context)
	{
		try{
			Vector visible=new Vector();
			Vector accessible=new Vector();
			Vector unpublished=new Vector();
			User user=data.getUser();
			ParameterParser pp=data.getParameters();	
			String topic=data.getParameters().getString("topic","");
			context.put("topic",topic);
			String group,dir,filePath=null;
			group=dir=(String)user.getTemp("course_id");
			context.put("course",(String)user.getTemp("course_name"));

			/**
			*These parameter are retrive from the template for Repository case
			* checking the Status (Repositry / Course / Remote)
			*/
			String status=pp.getString("stat","");
			context.put("stat",status);
			String username=pp.getString("uname","");
			context.put("username",username);
			String cName=data.getParameters().getString("cName","");
	                context.put("cName",cName);
                        File descFile = null;
			if(status.equals("Repo") ||status.equals("Remote"))
                        {
				if(status.equals("Repo"))
				{
					filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Permission"+"/"+username+"/"+topic;
				}
                                else
                                {
                                        filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Remotecourse/"+"/"+username+"/"+cName +"/" +topic;
                                }
				/**
	                        * If filepath does not exists means topic is deleted hence stop processing
                                */
                                File descDir=new File(filePath);
                                if(!descDir.exists())
                                return;

				/**
				*Here we make the blank Xml
				*for the Repository case 
				*/
				descFile=new File(filePath+"/"+topic+"__des.xml");
                        	if(!descFile.exists())
                        	{
					TopicMetaDataXmlWriter.writeWithRootOnly(descFile.getAbsolutePath());
				}//if
			}//if

			else
			{
				filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic;
				descFile =new File(filePath+"/"+topic+"__des.xml");
			}//else

			/**
			*Here we reading the xml file for the both cases
			*putting the files details in the Vector(visible)
			*/
			TopicMetaDataXmlReader topicMetaData=null;
			if(descFile.exists())
                        {
				topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+topic+"__des.xml");
				visible=topicMetaData.getFileDetails();
			}
			/**
			*Here we make the blank xml (Access__des.xml)
			*Blank xml is for the Repository for the case of accessible
			*Reading the xml files and put in the Vector (accessible)
			*/
			if(status.equals("Repo")||status.equals("Remote"))
			{
				File accessFile=new File(filePath+"/Access__des.xml");
                                if(!accessFile.exists())
                                {
                                        TopicMetaDataXmlWriter.writeWithRootOnly(accessFile.getAbsolutePath());
                                }//if
					
				TopicMetaDataXmlReader topicMetaDataaccess;
				topicMetaDataaccess=new TopicMetaDataXmlReader(filePath+"/Access__des.xml");
				accessible=topicMetaDataaccess.getFileDetails();
			}//if
			/**
                        * Here fileArray will have Visible File list
                        */
			String fileArray[]=topicMetaData.getFileNames();
			File f=new File(filePath);
			String filter[]={"__des.xml","Unpublished"};
			NotInclude exclude=new  NotInclude(filter);
			/**
                        * Here list will have Accesible + Visible(Published)  File list (combined);if status == "" or Remote
                        * Here list will have nothing ;if status == Repo
                        */
			File[] list=f.listFiles(exclude);
			/**
                        * Here found  will have false  for Accesible  File and true for visible file
                        * as defalut value of boolean in boolean array is false ;if status =="" or Remote
                        */
			boolean found[]=new boolean[list.length];	
			if(visible!=null)
			{
				for(int i=0;i<fileArray.length;i++)
				{
					String temp=fileArray[i];
					for(int j=0;j<list.length;j++)
					{
						if(temp.equals(list[j].getName()))
						{
							found[j]=true;
							break;
						}
					}
				}
			}
			char replacingChar='$';
			if(!status.equals("Remote"))
                        {
				for(int i=0;i<found.length;i++)
				{
					if(found[i]==false)
					{
						FileEntry fileEntry = new FileEntry();
						fileEntry.setName(list[i].getName()); 
						Date date=new Date(list[i].lastModified());
						fileEntry.setPDate(date.toString());
						accessible.addElement(fileEntry);
					}
				}
			}
			/**
			*Here we reading the xml which is in the Unpublished dir(Repository case) 
			*putting the all details in the vector(unpublished)
			*we also adding the "Unpublished/" with the FileName  
			*/
			if(status.equals("Repo")||status.equals("Remote"))
			{
				String filePathU= null;
				if(status.equals("Repo"))
                                {
					filePathU=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Permission"+"/"+username+"/"+topic+"/Unpublished";
				}
				 else
                                {
                                        filePathU=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Remotecourse"+"/"+username+"/" +cName  +"/"+topic+"/Unpublished";
                                }
				TopicMetaDataXmlReader topicMetaDataRepo=null;
				 if((new File(filePathU+"/"+topic+"__des.xml")).exists())
                                {
					topicMetaDataRepo=new TopicMetaDataXmlReader(filePathU+"/"+topic+"__des.xml");
					unpublished=topicMetaDataRepo.getFileDetails();
					if(unpublished!=null)
					{
						for(int i=0;i< unpublished.size();i++)
						{
							String fileName= ((FileEntry)unpublished.elementAt(i)).getName();
							String relFilePath="Unpublished/"+fileName;
							context.put("relFilePath",relFilePath);
							((FileEntry)unpublished.elementAt(i)).setName(relFilePath);
						}//for
					}//if
				}//if
			}//if
			else
			{
				f=new File(filePath+"/Unpublished");
				list=f.listFiles();
				for(int i=0;i<list.length;i++)
				{
					FileEntry fileEntry = new FileEntry();
					String fileName=list[i].getName();
					String relFilePath=list[i].getParentFile().getName()+"/"+fileName;
					context.put("relFilePath",relFilePath);
					fileEntry.setName(relFilePath);
					Date date=new Date(list[i].lastModified());
					fileEntry.setPDate(date.toString());
					unpublished.addElement(fileEntry);
				}//for
			 }//else
			/**
			*Here we putting the all Vector in the Context
			*for the use in templates	 
			*/
			context.put("courseid",dir);
			context.put("visibleContent",visible);
			context.put("accessibleContent",accessible);
			context.put("unpubContent",unpublished);
		}//try
		catch(Exception ex)
		{
			data.setMessage("The Error in Publish !!"+ex);
		}
	}
}
