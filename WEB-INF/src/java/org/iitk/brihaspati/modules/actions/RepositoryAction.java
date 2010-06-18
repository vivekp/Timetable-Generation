package org.iitk.brihaspati.modules.actions;

/*
 * @(#) UploadSCo.java
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur.
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
 */

import org.iitk.brihaspati.modules.utils.TotalFileCount;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.apache.commons.fileupload.FileItem;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.apache.turbine.services.servlet.TurbineServlet;
import java.io.FileOutputStream;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;


/**
* @author: <a href="mailto:seema_020504@yahoo.com">seema pal</a>
* @author: <a href="mailto:kshuklak@rediffmail.com">kishore kumar shukla</a>
* @author <a href="mailto:manjaripal@yahoo.co.in">Manjari Pal</a>
*/

/**
*This class contain the code for creating the Repository 
*Uploading the file in the Repository and
*Deleting and Moving files
*Grab all the record in the xmlFiles
*/

public class RepositoryAction extends SecureAction_Author
{
	MultilingualUtil Mutil=new MultilingualUtil();
	
	/**
	* Method for creating the Repository
 	* @param data RunData instance
 	* @param context Context instance
 	*/
        public void doCreate_Repo(RunData data,Context context)
        {
        	try
                {
                	String file=data.getUser().getTemp("LangFile").toString();
                        String create=Mutil.ConvertedString("Repo_create",file);
			/**
                        *Get the UserName and
                        *put it in the context
                        *for template use
                        */

                        User user = data.getUser();
                        String username = user.getName();
                        ParameterParser pp=data.getParameters();
			context.put("username",username);
			
                        /**
                        *get the path where created repository
                        *Here Author created Repository
                        */
                        String UserRealPath=TurbineServlet.getRealPath("/Repository");
                        File f=new File(UserRealPath+"/"+username);
                        f.mkdirs();
                        data.setMessage(create);
                }//try
                catch(Exception e2){data.setMessage("Repository created"+e2);}
        }//create_repo

   	/**
  	* Method for Uploadingfiles in the  Repository by the  Author
   	* @param data RunData instance
   	* @param context Context instance
     	*/

	public void doUpload(RunData data, Context context) throws Exception
	{
		String file=data.getUser().getTemp("LangFile").toString();
		String Mu_msg=Mutil.ConvertedString("uploadAction_msg",file);
                String Mu_msg1=Mutil.ConvertedString("uploadAction_msg1",file);
		/**
                *Get the User name and Parameters
                *by Using the ParameterParser and
                *Put in the context for using in the templates
                */

		User user=data.getUser();
		String username=user.getName();
		ParameterParser pp=data.getParameters();
		String mode=pp.getString("mode","");
		context.put("mode",mode);
		String contentTopic=pp.getString("contentTopic","");
		context.put("contenttopic",contentTopic);
                if(contentTopic.equals(""))
                {
			
                        context.put("errorMess",Mu_msg);
                        return;
                }
                if(contentTopic.indexOf('/')!=-1 || contentTopic.indexOf("\\")!=-1)
                {
                        context.put("errorMess",Mu_msg1);
                        return;

                }
		String tempFile[]=new String[10];
		FileItem fileItem;
		XmlWriter xmlWriter;
		Vector new_files_uploaded=new Vector();
               	// Get the Path in which the file is uploaded

		String repositoryRealPath=TurbineServlet.getRealPath("/Repository");
		File topicDir=new File(repositoryRealPath+"/"+username+"/"+contentTopic);
		File f=new File(topicDir.getPath()+"/");
		String filePath = f.getPath()+"/";
		f.mkdirs();
		String path=repositoryRealPath+"/"+username+"/"+contentTopic;

		/**
		*creating the new XmlFile
		*@see TopicMetaDataXmlWriter in utils
		*/

		File descFile= new File(topicDir+"/"+contentTopic+"__des.xml");

	        if(!descFile.exists())
		{
		        TopicMetaDataXmlWriter.writeWithRootOnly(descFile.getAbsolutePath());
			xmlWriter=new XmlWriter(topicDir+"/"+contentTopic+"__des.xml");
		}
		/**
		*Here we append the new element in tne Xml File
		*@see TopicMetaDtaXmlWriter in utils
		*/
		else
		{
			xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(path,contentTopic);
		}
		/**
		* ulpoading the file  in the Directory
		* checking for the existing file
		*/
		Date date=new Date();
		String pubDate=date.toString();
		int successfulUploadFilesCount=0;
		int totalFilesEntries=0;
		Vector failedFiles=new Vector();
		try
		{
			for(int count=0;count<10;count++)
			{
				boolean fileExists=false;
				fileItem=pp.getFileItem("file"+(count+1));
				if(fileItem != null && fileItem.getSize() != 0)
				{
					String temp=fileItem.getName();
					int index=temp.lastIndexOf("\\");
					++totalFilesEntries;
					fileExists=false;
					tempFile[count]=temp.substring(index+1);
					File uploadedFileInUnpub=new File(f,tempFile[count]);
					File uploadedFileInTopicDir=new File(topicDir,tempFile[count]);

					if(uploadedFileInUnpub.exists() || uploadedFileInTopicDir.exists())
					{
						fileExists=true;
						failedFiles.addElement(tempFile[count]);
					}
			
					if(fileExists)
					continue;
					++successfulUploadFilesCount;
					byte b[]=fileItem.get();
                                	FileOutputStream fos=new FileOutputStream(filePath+tempFile[count]);
                                	fos.write(b);
                                	fos.close();
					new_files_uploaded.addElement(tempFile[count]);
				}
			}
                                

			if(successfulUploadFilesCount>0) 
			{
				if(successfulUploadFilesCount==totalFilesEntries)
				{
					context.put("uploadStatus","full");

				}
				else
				{
					context.put("uploadStatus","partial");
                                        context.put("failedFiles",failedFiles);

				}
			}
			else
			{			
				 context.put("uploadStatus","nothing");
                                context.put("totalFilesEntries",(new TotalFileCount(totalFilesEntries)));
                                context.put("failedFiles",failedFiles);


			}
			/** here we append the element in the XmlFile
			*@see Xmlwriter in utils
			*/
			if(new_files_uploaded.size()!=0)
			{
       				for(int k=0;k<new_files_uploaded.size();k++)
       				{
               	       			String fileName=(String)new_files_uploaded.get(k);
                        		TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fileName,fileName,pubDate);
				}//for
			}//if
			xmlWriter.writeXmlFile();
		}//try
		catch(Exception ex){data.setMessage("The Error in uploading"+ex);}
	}//upload
	
	/**
	* Method for Moving files
 	* @param data RunData instance
 	* @param context Context instance
 	*/
        public void doMove(RunData data,Context context)
	{
        	String file=data.getUser().getTemp("LangFile").toString();
                String Movefile=Mutil.ConvertedString("Repo_Movefile",file);
                try{
                        /**
                        * Get the user object from RunData for the user
                        * currently logged in
                        */
                        User user=data.getUser();
                        String authorname=user.getName();
                        context.put("authorname",authorname);
                        /**
                        *Retrieve the parameters
                        *by using the ParameterParser and
                        *Put it in the context for
                        *Using in templates
                        */
                        ParameterParser pp=data.getParameters();
                        String content1=pp.getString("name","");
                        context.put("content1",content1);
                        String topic=pp.getString("topic","");
                        context.put("topic",topic);
                        String filename=pp.getString("FileName","");
                        String mvdir =pp.getString("Total","");
                        context.put("mvdir",mvdir);
                        context.put("filename",filename);
                        XmlWriter xmlWriter;
  			/**
                  	*Author move File from one Directory to another
                  	*/
                        String read=data.getServletContext().getRealPath("Repository")+"/"+content1+"/"+topic+"/"+filename;
                        File f=new File(read);
                        String writefile=data.getServletContext().getRealPath("Repository")+"/"+content1+"/"+mvdir+"/"+filename;
                        File fo=new File(writefile);
                        /**
			* Here we  move  file in another directory
			*/
			f.renameTo(fo);
		
                        data.setMessage(Movefile);
                	/**
  			*Moving the Particular File From The XmlFile
                	*@see TopicMetaDataXmlWriter in utils
                	*/
                        String UserPath=data.getServletContext().getRealPath("/Repository");
                        Date date=new Date();
                        String pubDate=date.toString();
                        String path=UserPath+"/"+authorname+"/"+topic;
                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(path,topic);
                        int seq=pp.getInt("seq",-1);
                        xmlWriter.removeElement("File",seq);
                        xmlWriter.writeXmlFile();
                        String filePath=UserPath+"/"+authorname+"/"+mvdir;
                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(filePath,mvdir);
                        TopicMetaDataXmlWriter.appendFileElement(xmlWriter,filename,filename,pubDate);
                        xmlWriter.writeXmlFile();
                }//try
                catch(Exception e){data.setMessage("The Error in Move"+e);}
	}//Move

	/**
        * This function contain the code for
        * delete the files or directory
        */


        void deletefilename(File f)
        {
                if(f.isFile()){
                f.delete();
                }
        }
	/**
       	*Method for Deleting Directory and File by the content author
       	*@param data RunData instance
       	*@param context Context instance
       	*/
	public void doDelete(RunData data,Context context)
        {
                String file=data.getUser().getTemp("LangFile").toString();
                String del1=Mutil.ConvertedString("personal_del1",file);
                String del2=Mutil.ConvertedString("personal_del2",file);
                try
		{
			/**
                        * Get the user object from RunData for the user
                        * currently logged in
                        */

                	User user=data.getUser();
                	String authorname=user.getName();
                	context.put("authorname",authorname);
			/**
                        *Retrieve the parameters
                        *by using the ParameterParser and
                        *Put it in the context for
                        *Using in templates
                        */

                	ParameterParser pp=data.getParameters();
                	String mode=pp.getString("mode","");
                	String topic=pp.getString("topic","");
                	context.put("topic",topic);
                	String stat=data.getParameters().getString("status");
                	context.put("status",stat);
                	XmlWriter xmlWriter=null;

                	// Deleting the topic from the Repository
                	String UserPath=data.getServletContext().getRealPath("/Repository");
                	File filePath=new File(UserPath+"/"+authorname+"/"+topic);
                	String fileName=pp.getString("FileName","");
                	context.put("filename",fileName);
			if (mode.equals("DirRemoval"))
                	{
                		SystemIndependentUtil.deleteFile(filePath);
                        	data.setMessage(del1);
                 	}
                	//Deleting the particular File from the topic 
                 	else
                 	{
                 		File fileway=new File(UserPath+"/"+authorname+"/"+topic+"/"+fileName);
                        	deletefilename(fileway);
                        	data.setMessage(del2);

                  	/**
                  	* deleting the particular File from the XmlFile.
                  	*@see TopicMetaDataXmlWriter in utils.
                  	*/
                  	String path=UserPath+"/"+authorname+"/"+topic;
                  	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(path,topic);

                  	int seq=pp.getInt("seq",10000);
                  	xmlWriter.removeElement("File",seq);
                  	xmlWriter.writeXmlFile();
			}//else
        	}//try
        	catch(Exception ew){data.setMessage("The Error in DeleteAction"+ew);}

	}//Delete

/**
* Default action to perform if the specified action
* cannot be executed.
*/
public void doPerform(RunData data,Context context)throws Exception

	{

		String file=data.getUser().getTemp("LangFile").toString();
        	String nfind=Mutil.ConvertedString("action_msg",file);
		ParameterParser pp=data.getParameters();
        	String action = pp.getString("actionName","");
        	if(action.equals("eventSubmit_doMove"))
        		doMove(data,context);
        	else if(action.equals("eventSubmit_doUpload"))
        		doUpload(data,context);
		else if(action.equals("eventSubmit_doDelete"))
                	doDelete(data,context);
		else if(action.equals("eventSubmit_doCreate_Repo"))
                	doCreate_Repo(data,context);
        	else
        		 data.setMessage(nfind);

	}
}



