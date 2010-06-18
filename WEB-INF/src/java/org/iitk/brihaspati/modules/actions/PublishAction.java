package org.iitk.brihaspati.modules.actions;

/*
 * @(#)PublishAction.java	
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
//JDK
import java.io.File;
import java.util.Date;
import java.util.Vector;
import java.util.StringTokenizer;
//Turbine
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
//Brihaspati
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.CalendarUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

/**
 * This class is responsible of manage file for Visible Accessible, Unpublish, Accessible and Deletion.
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class PublishAction extends SecureAction_Instructor
{
	String fileName=new String();
	/**
        * This method is responsible for when deleting process is cancelled
        * @param data RunData
        * @param context Context
        */
	public void doCancel(RunData data,Context context)
	{
		data.setScreenTemplate("call,CourseMgmt_User,CourseContent.vm");
	}
	/**
        * This method is responsible for deleting  a Topic with all contents
        * @param data RunData
        * @param context Context
        */
	public void doDeleteTopic(RunData data,Context context) 
	{
		try{
			User user=data.getUser();
                	String LangFile=(String)user.getTemp("LangFile");
			ParameterParser pp=data.getParameters();		
			String topic=pp.getString("topic","");
			String status=pp.getString("stat","");
			context.put("stat",status);
			String username=pp.getString("uname","");
			context.put("username",username);
			String cnm=(String)user.getTemp("course_name");
			String cName=pp.getString("cName","cnm");
			context.put("cName",cName);
			XmlWriter xmlwriter=null;
			String group,dir,filePath;
			group=dir=(String)user.getTemp("course_id");
                        String BPath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content";
			if(status.equals("Repo"))
			filePath=BPath+"/Permission/"+username+"/"+topic;
			else
			filePath=BPath+"/"+topic;
			File f=new File(filePath);
			 File f1=new File(BPath+"/content__des.xml");
                        if(status.equals("ccontent"))
                        {
                                int seq=CalendarUtil.getSequence(BPath,"content",topic);
                                xmlwriter=TopicMetaDataXmlWriter.WriteXml_New(BPath,"content");
                                xmlwriter.writeXmlFile();
                                TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(BPath+"/"+"content__des.xml");
                                Vector dc=topicMetaData.getFileDetails();
                                if(dc.size()>1)
                                {
                                        xmlwriter.removeElement("File",seq);
                                        xmlwriter.writeXmlFile();
                                }
                                else{
                                        SystemIndependentUtil.deleteFile(f1);
                                }
                        }


			SystemIndependentUtil.deleteFile(f);
			data.setScreenTemplate("call,CourseMgmt_User,CourseContent.vm");
			//EditContent_Action ECA=new EditContent_Action();
			EditContent_Action ECA = new EditContent_Action();
			if(status.equals("Remote"))
			{
				ECA.RemoteDelTopic(dir,cName,topic,username);
			}
			else if(status.equals("Repo"))
                        {
				String match=topic+username+dir;
				/**
                                *Get the path for delete the particular topic from the xml file
                                */
                                String Path=BPath+"/Permission";
                                String PathR=data.getServletContext().getRealPath("/Repository")+"/"+username;
                                String xmlFile= "/permissionReceive__des.xml";
                                String xmlFile1 = "/permissiongiven__des.xml";
				Vector Readrec=new Vector();
				Vector Readgiven=new Vector();
				/**
                                *Here we read the  permissionReceive xml
                                *and compare with the topicname,username,course_id which we want to delete
                                */

				Readrec=SystemIndependentUtil.DeleteXmlEntry(Path,xmlFile,match,username);

				/**
                                *Here we read the  permissiongiven xml
                                *and compare with the topicname,username,coursename which we want to delete
                                */
				Readgiven=SystemIndependentUtil.DeleteXmlEntry(PathR,xmlFile1,match,username);
				
				SystemIndependentUtil.deleteEmptDir(Path, username);
                                /**
                                * if this is last record in xml file delete the file
                                */
                                if(Readgiven.size()==1)
                                {
                                  	File filepath = new File(PathR+xmlFile1);
                                        filepath.delete();
                               } //inner if

                        }//status if
			data.setMessage(MultilingualUtil.ConvertedString("personal_del1",LangFile));

		}//try
		catch(Exception ex)
		{
			data.setMessage("The Error in Topic Deletions "+ex);
		}
	}//do delete topic

	/**
        * This method is responsible for updation of last modified date in course
        * @param courseId String
        * @param lastMod Date
        */
	public void updateLastModified(String courseId,Date lastMod) throws Exception
	{
		Criteria crit=new Criteria();
		crit.add(CoursesPeer.GROUP_NAME,courseId);
		crit.add(CoursesPeer.LASTMODIFIED,lastMod);
		CoursesPeer.doUpdate(crit);
	}

	/**
        * This method is responsible for publish course contents and manage some utility
        * @param data RunData
        * @param context Context
        */

	public void doPublish(RunData data,Context context) 
	{
		EditContent_Action ECA = new EditContent_Action();
		User user=data.getUser();
		ParameterParser pp=data.getParameters();		
		String topic=pp.getString("topic","");
		context.put("topic",topic);
		//These parameter are retrive from the templates for the Repository case
		String status=pp.getString("stat","");
		context.put("stat",status);
		String username=pp.getString("uname","");
		context.put("username",username);
		String cName=data.getParameters().getString("cName","");
		context.put("cName",cName);		
		String visibleList=pp.getString("visibleList","");
		String accessibleList=pp.getString("accessibleList","");
		String unpublishedList=pp.getString("unpublishedList","");
		try{
                	XmlWriter xmlWriter=null;
			String group,dir,filePath=null;
			group=dir=(String)user.getTemp("course_id");
                        Vector ReadPub=new Vector();
                       	Vector ReadAcces=new Vector();
                       	Vector ReadUnp=new Vector();
			String accessxml="/Access__des.xml";
			if(status.equals("Remote"))
			{
				filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Remotecourse"+"/"+username+"/"+cName+"/"+topic;
			}	
			else if(status.equals("Repo"))
			{
				filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content"+"/Permission"+"/"+username+"/"+topic;
			}
			else
			{
				filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic;

			}
			String Pathremov=(filePath+"/Unpublished");
			File descFile=new File(filePath+"/"+topic+"__des.xml");
			File accessFile=new File(filePath+accessxml);
			File UnPublishFile=new File(Pathremov+"/"+topic+"__des.xml");
			Date d=new Date();
			String pubDate=d.toString();
			if(!status.equals("Repo") && !status.equals("Remote"))
			{
				if(!descFile.exists())  
                		{
                        		xmlWriter=new XmlWriter(filePath+"/"+topic+"__des.xml");
                		}
				else
				{
					xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,topic);
				}
			}//if
			int countFiles=0;
			int fileseqno[];
/**
****************************************************  Do For Published File List *****************************************
*/			
			StringTokenizer st=new StringTokenizer(visibleList,"^");
			String fNames[]=new String[st.countTokens()];
			for(int i=0;st.hasMoreTokens();i++)
			{
				fNames[i]=st.nextToken();
			}

			for(int i=0;i<fNames.length;i++)
			{
				int value=pp.getInt(fNames[i]);	
				if(value!=3)
					++countFiles;
			}
			fileseqno=new int[countFiles];
			for(int i=0,j=0;i<fNames.length;i++)
			{	
				char replaceWith='.';
				int value=pp.getInt(fNames[i]);			
				fileName=new String();
				int index_of=fNames[i].indexOf('$');
				if( fNames[i].charAt(index_of+1)!='$' )
					fileName=fNames[i].replace('$',replaceWith);
				else
					fileName=fNames[i].substring(0,index_of);
				File file=new File(filePath+"/"+fileName);

/**
* Here Case 1  = Unpublish ;Case 2  = Accessible ;Case 3  = Visible Accessible ;Case 4  = Delete;
*/
					
				switch(value)
				{
					case 1:
						/**
						*Here we reading the "topic__des.xml"file that is define in Readxmlfile 
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")||status.equals("Remote"))
						{//ifstatus
							ReadPub=Readxmlfile(filePath,topic);

							/**
							*Here we checking the "topic__des.xml" file exists or not
							*We define the path for the xmlwriter
							*/
							if(!descFile.exists())
                                                        {
                                                        	xmlWriter=new XmlWriter(Pathremov+"/"+topic+"__des.xml");
                                                        }
							/**
                					*Here we reading the element in tne Xml File
                					*@see TopicMetaDataXmlWriter in utils
                					*/
                                                        else
                                                        {
                                                        	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(Pathremov,topic);
                                                        }
							/**
							*Here we appending the new element in the Xml File
                					*@see TopicMetaDataXmlWriter and XmlWriter in utils
							*/
                                                        TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                                                        xmlWriter.writeXmlFile();
						}//if status
						else
						{
							File newPath=new File(filePath+"/Unpublished/"+fileName);
							file.renameTo(newPath);
							fileseqno[j++]=pp.getInt(fileName);
						}//else
							break;	
					case 2:
						/**
						*Here we reading the "topic__des.xml"file(that is define in Readxmlfile)
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")|| status.equals("Remote"))
						{
							ReadPub=Readxmlfile(filePath,topic);
							/**
							*Here we checking the "accessFile" file exists or not
							*We define the path for the xmlwriter
							*/
							if(!accessFile.exists())
                        				{
                                				xmlWriter=new XmlWriter(filePath+accessxml);
                        				}
							/**
                					*Here we reading the element in tne Xml File
                					*@see TopicMetaDataXmlWriter in utils
                					*/
                        				else
                        				{
                                				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,accessxml);
                        				}
							/**
							*Here we appending the new element in the Xml File
                					*@see TopicMetaDataXmlWriter and XmlWriter in utils
							*/
							TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                        				xmlWriter.writeXmlFile();
						} //if
						else
							fileseqno[j++]=pp.getInt(fileName);
							break;
					case 4: 
						if(status.equals("Repo"))
						{
							ReadPub=Readxmlfile(filePath,topic);
						}
						else if(status.equals("Remote"))
                                                {
                                                        ECA.RemoteDelFile(dir,cName,topic,username,fileName,3,data);
                                                }
						else
						{
							SystemIndependentUtil.deleteFile(file);
							fileseqno[j++]=pp.getInt(fileName);
						}//else
							break;
				}//switch	
			}//for
			if(fileseqno.length>0 && !(status.equals("Repo")) && !(status.equals("Remote")))
				xmlWriter.removeElement("File",fileseqno);
                               //xmlWriter.removeElement("File",fileseqno);
/**
*************************************************  Do For Accessible File List ********************************************
*/

			countFiles=0;	
			st=new StringTokenizer(accessibleList,"^");
			fNames=new String[st.countTokens()];
			for(int i=0;st.hasMoreTokens();i++)
			{
				fNames[i]=st.nextToken();
			}
			boolean courseModified=false;
			for(int i=0,j=0;i<fNames.length;i++)
			{	
				char replaceWith='.';
				int value=pp.getInt(fNames[i]);			
				fileName=new String();
				int index_of=fNames[i].indexOf('$');
				if(fNames[i].charAt(index_of+1)!='$')
					fileName=fNames[i].replace('$',replaceWith);
				else
					fileName=fNames[i].substring(0,index_of);
				
				File file=new File(filePath+"/"+fileName);
/**
* Here Case 1  = Unpublish ;Case 2  = Accessible ;Case 3  = Visible Accessible ;Case 4  = Delete;
*/			
				switch(value)
				{
					case 1:
						
						/**
						*Here we reading the "des.xml"file (that is define in Readxmlfile)
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")|| status.equals("Remote"))
						{
							ReadAcces=Readxmlfile(filePath,accessxml);
							/**
							*Here we checking the "des.xml" file exists or not
							*We define the path for the xmlwriter
							*/
							if(!UnPublishFile.exists())
                        				{
                                				xmlWriter=new XmlWriter(Pathremov+"/"+topic+"__des.xml");
                        				}
							/**
                					*Here we reading the element in tne Xml File
                					*@see TopicMetaDataXmlWriter in utils
                					*/
                        				else
                        				{
                                				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(Pathremov,topic);
                        				}
							/**
							*Here we appending the new element in the Xml File
                					*@see TopicMetaDataXmlWriter and XmlWriter in utils
							*/
							TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                        				xmlWriter.writeXmlFile();
						} //if
						else
						{
							File newPath=new File(filePath+"/Unpublished/"+fileName);
							file.renameTo(newPath);
						}//else
							break;	
				
					case 3:
						/**
						*Here we reading the "des.xml"file (that is define in Readxmlfile)
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")|| status.equals("Remote"))
						{
							ReadAcces=Readxmlfile(filePath,accessxml);

							/**
							*Here we checking the "topic__des.xml" file exists or not
							*We define the path for the xmlwriter
							*/
							if(!descFile.exists())
                        				{
                                				xmlWriter=new XmlWriter(filePath+"/"+topic+"__des.xml");
                        				}
                        				else
							/**
                					*Here we reading the element in tne Xml File
                					*@see TopicMetaDataXmlWriter in utils
                					*/
                        				{
                                				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,topic);
                        				}
							/**
							*Here we appending the new element in the Xml File
                					*@see TopicMetaDataXmlWriter and XmlWriter in utils
							*/
							if(status.equals("Remote"))
                                                        {
                                                                pubDate = "----";
                                                        }

							TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                        				xmlWriter.writeXmlFile();
						} //if
						else
						TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fileName,fileName,pubDate);
						courseModified=true;
						break;
					case 4:	
						if(status.equals("Repo"))
						{	
							ReadAcces=Readxmlfile(filePath,accessxml);
						}
						else if(status.equals("Remote"))
                                                {
                                                        ECA.RemoteDelFile(dir,cName,topic,username,fileName,2,data);
                                                }
						else
							SystemIndependentUtil.deleteFile(file);
							break;
				}//switch	
			}//for
/**
****************************************************  Do For Unpublished File  List ***************************************
*/
			countFiles=0;	
			st=new StringTokenizer(unpublishedList,"^");
			fNames=new String[st.countTokens()];
			for(int i=0;st.hasMoreTokens();i++)
			{
				fNames[i]=st.nextToken();
			}
			for(int i=0,j=0;i<fNames.length;i++)
			{	
				char replaceWith='.';
				int value=pp.getInt(fNames[i]);		
				fileName=new String();
				int index_of=fNames[i].indexOf('$');
				if(fNames[i].charAt(index_of+1)!='$')
					fileName=fNames[i].replace('$',replaceWith);
				else
					fileName=fNames[i].substring(0,index_of);

				File file=new File(filePath+"/"+fileName);
/**
* Here Case 1  = Unpublish ;Case 2  = Accessible ;Case 3  = Visible Accessible ;Case 4  = Delete;
*/			
				switch(value)
				{
					case 2:
						/**
						* substring(12) to remove "Unpublished/" string before file name
						*/
						fileName=fileName.substring(12);
						/**
						*Here we reading the "topic__des.xml"file (that is define in Readxmlfile)
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")|| status.equals("Remote"))
						{
							ReadUnp=Readxmlfile(Pathremov,topic);
								/**
								*Here we checking the "des.xml" file exists or not
								*We define the path for the xmlwriter
								*/
                                                        	if(!accessFile.exists())
                                                        	{
                                                                	xmlWriter=new XmlWriter(filePath+accessxml);
                                                        	}
								/**
                						*Here we reading the element in tne Xml File
                						*@see TopicMetaDataXmlWriter in utils
                						*/
                                                        	else
                                                        	{
                                                                	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,accessxml);
                                                        	}
								/**
								*Here we appending the new element in the Xml File
                						*@see TopicMetaDataXmlWriter and XmlWriter in utils
								*/
                                                        	TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                                                        	xmlWriter.writeXmlFile();
							}//if
							else
							{
								File newPath=new File(filePath+"/"+fileName);
								file.renameTo(newPath);
							}
								break;	
					case 3:	
						fileName=fileName.substring(12);
						/**
						*Here we reading the "topic__des.xml"file (that is define in Readxmlfile)
						*comparing the filename with "visiblelist" filename 
						*/
						if(status.equals("Repo")|| status.equals("Remote"))
						{
							ReadUnp=Readxmlfile(Pathremov,topic);
							/**
							*Here we checking the "topic__des.xml" file exists or not
							*We define the path for the xmlwriter
							*/
							if(!descFile.exists())
                        				{
                                				xmlWriter=new XmlWriter(filePath+"/"+topic+"__des.xml");
                        				}
							/**
                					*Here we reading the element in tne Xml File
                					*@see TopicMetaDataXmlWriter in utils
                					*/
                        				else
                        				{
                                				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,topic);
                        				}
							/**
							*Here we appending the new element in the Xml File
                					*@see TopicMetaDataXmlWriter and XmlWriter in utils
							*/
							if(status.equals("Remote"))
                                                        {
                                                                pubDate = "----";
                                                        }

							TopicMetaDataXmlWriter.appendFileElement(xmlWriter, fileName, fileName,pubDate);
                        				xmlWriter.writeXmlFile();
						}//if
						else
						{
							File newPath=new File(filePath+"/"+fileName);
							file.renameTo(newPath);
							TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fileName,fileName,pubDate);
						}//else
							courseModified=true;
							break;
					case 4:
						fileName=fileName.substring(12);
						if(status.equals("Repo"))
						{
							ReadUnp=Readxmlfile(Pathremov,topic);
						}
						else if(status.equals("Remote"))
                                                {
                                                        ECA.RemoteDelFile(dir,cName,topic,username,fileName,1,data);
                                                }
						else
							SystemIndependentUtil.deleteFile(file);
							break;
					default:
							ErrorDumpUtil.ErrorLog("\n InDefault Status"+status+"-- value"+Integer.toString(value));
							break;
				}//switch	
			}//for
			// Finally Write XML File
			if(!status.equals("Repo")&& !status.equals("Remote"))
			{
				 if(xmlWriter != null)
                                 {
					xmlWriter.writeXmlFile();
				 }
			}	
			if(courseModified)
				updateLastModified(group,d);
			/**
			*checking the xml files by reading the util "TopicMetaDataXmlReader"
			*Here we deleteing the topic if there is no files
			*@see  SystemIndependentUtil in util
			*/
			if(status.equals("Repo"))
			{
                                String Path=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/Permission";
                                String PathR=data.getServletContext().getRealPath("/Repository")+"/"+username;
                                String xmlFile= "/permissionReceive__des.xml";
                                String xmlFile1 = "/permissiongiven__des.xml";
				String match=topic+username+dir;
				/**
                                *Here we reading the xml files
                                *put the all details in the Vector
                                */
                                TopicMetaDataXmlReader tr=null;
				tr=new TopicMetaDataXmlReader(filePath+"/"+topic+"__des.xml");
				ReadPub=tr.getFileDetails();
				tr=new TopicMetaDataXmlReader(filePath+accessxml);
				ReadAcces=tr.getFileDetails();
				tr=new TopicMetaDataXmlReader(Pathremov+"/"+topic+"__des.xml");
				ReadUnp=tr.getFileDetails();
				if((ReadPub==null)&&(ReadAcces==null)&& (ReadUnp==null))
				{//if inner
					 /**
                                        *Here we checking the Vector value
                                        *for deleting the whole topic
                                        */
                             		File filedel=new File(filePath);
                                	SystemIndependentUtil.deleteFile(filedel);
					data.setScreenTemplate("call,CourseMgmt_User,CourseContent.vm");
                                	Vector Readperm=new Vector();
                                	Vector Readperm1=new Vector();
					/**
                                        *Here we read the  permissionReceive xml
                                       	*and compare with the topicname,username,coursename which we want to delete
					*@see SystemIndependentUtil in utils
                                        */

					Readperm=SystemIndependentUtil.DeleteXmlEntry(Path,xmlFile,match,username);

                                	/**
                                	*Here we read the  permissiongiven xml
                                	*and compare with the topicname,username,coursename which we want to delete
					*@see SystemIndependentUtil in utils
                                	*/
				
					Readperm1=SystemIndependentUtil.DeleteXmlEntry(PathR,xmlFile1,match,username);

				 	/**
                                	*Here we deleting the author dir in which permission given
                                	*when it empty
                                	*/
					SystemIndependentUtil.deleteEmptDir(Path, username);
                                	/**
                                	*if this is last record in xml file delete the file
                                	*/
                                	if(Readperm1.size()==1)
                                	{
                                        	 File filepath = new File(PathR+"/permissiongiven__des.xml");
                                        	filepath.delete();
					}
                               	} //inner if
			} //status if
		}//try
		catch(Exception ex)
		{
			data.setMessage("The Error in Publish Action !! "+ex);
		}
	}//do Publish
	/**
        * This is default method,to perform if the specified action cannot be executed
        * @param data RunData
        * @param context Context
        */
	public void doPerform(RunData data,Context context) throws Exception 
	{
		String actionToPerform=data.getParameters().getString("actionName","");
		context.put("actionO",actionToPerform);
			
		if( actionToPerform.equals("eventSubmit_doPublish") )
		{
			doPublish(data,context);
		}
		else if( actionToPerform.equals("eventSubmit_doDeleteTopic") )
		{
			doDeleteTopic(data,context);
		}
	}
        /**
        * This Method is responsible for reading the xml descriptor file 
	* then remove the perticular entry and finally write xml descriptor file
	* @param filePath String
	* @param topic String
	* @return Vector
       */
	public  Vector Readxmlfile(String filePath,String topic)
	{
		Vector Read=new Vector();
		try
		{
			XmlWriter xmlWriter=null;
                	String Xml_file="";
                	if(topic.endsWith(".xml"))
                        	Xml_file=topic;
                	else
                        	Xml_file=topic+"__des.xml";
			int seq=-1;
			TopicMetaDataXmlReader tr =new TopicMetaDataXmlReader(filePath+"/"+Xml_file);
                	Read=tr.getFileDetails();
                	if(Read.size()!=0)
                	{
                		for(int n=0;n<Read.size();n++)
                        	{
                        		String filenameRem =((FileEntry)Read.elementAt(n)).getName();
                                	if(fileName.equals(filenameRem))
                                	{
                                		seq=n;
                                        	break;
                                	}
                       		}
                	}
                	/**
                	Here we deleting the particular entry from the "xml" file
                	*/
                	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,topic);
                	xmlWriter.removeElement("File",seq);
                	xmlWriter.writeXmlFile();
			/**
                        * Again read the newly created xml file and send its results
                        */
                        Read=(new TopicMetaDataXmlReader(filePath+"/"+Xml_file)).getFileDetails();

		}//try
		catch(Exception ew)
		{
			ErrorDumpUtil.ErrorLog("The Error in Publish Action in Readxmlfile  !! "+ew);
		}
	return Read;
	}//readmethod
//----------------------------------------------------------------------
}//class
