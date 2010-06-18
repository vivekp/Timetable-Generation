package org.iitk.brihaspati.modules.actions;

/**
 * @(#)UserAreaAction.java
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
 *
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 */
 /**
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a>
 */ 

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.TotalFileCount;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.QuotaUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
/**
*This class contain the code for Uploading the file,
*in the Private Area and
*Deleting and Moving files
*Grab all the record in the xmlFiles
*/

public class UserAreaAction extends SecureAction
{	  
	MultilingualUtil mu=new MultilingualUtil();
	/**
  	* Method for creating Uploading the files
   	* @param data RunData instance
   	* @param context Context instance
   	*
     	*/
 	public void doUpload(RunData data, Context context) throws Exception
        {
		String file=data.getUser().getTemp("LangFile").toString();
		/**
		*Get the User name and Parameters
		*by Using the ParameterParser and
		*Put in the context for using in the templates
		*/	
		User user=data.getUser();
                String username=user.getName();
		int uid=UserUtil.getUID(username);	
                ParameterParser pp=data.getParameters();
                String contentTopic=pp.getString("contentTopic","");
                String mode=pp.getString("mode","");
                context.put("contenttopic",contentTopic);
                String dateOfCreation=(new Date()).toString();
                String dateOfModification="";
                String Path=TurbineServlet.getRealPath("/UserArea");
                String tempFile[]=new String[5];
                FileItem fileItem;
                XmlWriter xmlWriter=null;
		Vector new_files_uploaded=new Vector();
//check for available quota space and return true if space available
		File UAreaFile=new File(Path+"/"+username);

		String repositoryRealPath=TurbineServlet.getRealPath("/Repository");
		File Rlist=new File(repositoryRealPath+"/"+username);

		long UArea=QuotaUtil.getDirSizeInMegabytes(UAreaFile);
                long Repos=0;
                if(Rlist.exists()){
                        Repos=QuotaUtil.getDirSizeInMegabytes(Rlist);
                }
                long totalsize=UArea+Repos;

		boolean check=QuotaUtil.CompareQuotainRoleUser(totalsize,uid);

		if(check){
		/**
		*Get the Path in which the file is uploaded
		*/	
			File topicDir=null;
			if(mode.equals("uarea"))
				topicDir=new File(Path+"/"+username+"/"+"/Private"+"/"+contentTopic);
			else
				topicDir=new File(repositoryRealPath+"/"+username+"/"+contentTopic);
              		File f=new File(topicDir.getPath()+"/");
        		String filePath = f.getPath()+"/";
 			String RPath=topicDir.getPath();
                	File descFile= new File(topicDir+"/"+contentTopic+"__des.xml");
			/**
        		*Uploading the file  in the Directory
        		*Checking for the existing file
        		*/
	
			Date date=new Date();
        		String pubDate=date.toString();
			int successfulUploadFilesCount=0;
                	int totalFilesEntries=0;
                	Vector failedFiles=new Vector();
                	Vector filelist=new Vector();
			boolean flag=false;
//		ErrorDumpUtil.ErrorLog("repository at line 133===");
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
			               		File f1=new File(filePath+tempFile[count]);
						new_files_uploaded.addElement(tempFile[count]);

						long fsize=(fileItem.getSize())/1024/1024;
						ErrorDumpUtil.ErrorLog("testing at line 170==="+fsize);
					  	long uquota=QuotaUtil.getUsrQuota(uid);
						ErrorDumpUtil.ErrorLog("testing at line 172==="+uquota);
						uquota = uquota - totalsize;
						long disSpace=QuotaUtil.getFileSystemSpace();
					ErrorDumpUtil.ErrorLog("testing at line 175==="+uquota+"\ndisSpace="+disSpace);

                	                        if((uquota>fsize)&&(disSpace>fsize)){
						ErrorDumpUtil.ErrorLog("testing at line 178==="+(uquota>fsize));
	                				f.mkdirs();
							flag=true;
							if(flag){
                        		        		if(!descFile.exists())
                                				{
                                        				TopicMetaDataXmlWriter.writeWithRootOnly(descFile.getAbsolutePath());
		                                        		xmlWriter=new XmlWriter(topicDir+"/"+contentTopic+"__des.xml");
                		                		}
                                				else
                                				{
                                        				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(RPath,contentTopic);
	                                			}
        	                        		fileItem.write(f1);
                        				}

                        			}
						else{
							data.setMessage("");
							data.addMessage(MultilingualUtil.ConvertedString("qmgmt_msg5",file));
						}	
                        		}
				}
				if(flag){
 				if(new_files_uploaded.size()!=0){		
                			for(int k=0;k<new_files_uploaded.size();k++)
                			{
                				String fileName=(String)new_files_uploaded.elementAt(k);
						TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fileName,fileName,pubDate);
                			}
				}
                		xmlWriter.writeXmlFile();
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
				}//if 
				else
                                data.setMessage(MultilingualUtil.ConvertedString("qmgmt_msg2",file));
                                //data.setMessage("You can not upload files having much size than alloted quota to you !!");
		
		}//try
		catch(Exception e){data.addMessage("The error in upload "+e);}
	}//if check 
	else
        data.setMessage(MultilingualUtil.ConvertedString("qmgmt_msg3",file));
	//data.setMessage("You have not sufficient quota for uploading the file,so send a Local Mail for Increas the Quota or You can delete some files !!");
        }// end method
 
 
  	/**
        * Method for moving  the files
        * @param data RunData instance
        * @param context Context instance
        *
        */
	private  void doMove(RunData data,Context context)
	{
		String file=data.getUser().getTemp("LangFile").toString();
        	try
		{
                	/**
			*Retrieve the parameters
			*by using the ParameterParser and 
			*Put it in the context for 
			*Using in templates
			*/
			ParameterParser pp=data.getParameters();
                        String status=pp.getString("stat","");
			context.put("stat",status);
                        String Content=pp.getString("topic","");
			String UName=pp.getString("name",""); 
                        String filename=pp.getString("filename","");
                        String mode=pp.getString("mode","");
                        context.put("fname",filename);
                        context.put("mode",mode);
			context.put("cvalue",Content);
                	String read=data.getServletContext().getRealPath("UserArea"+"/"+UName+"/"+"/Private"+"/"+Content+"/"+filename);
                	String writefile=data.getServletContext().getRealPath("UserArea"+"/"+UName+"/"+"/Shared"+"/"+filename); 
			String dirPath=data.getServletContext().getRealPath("/UserArea");
                        XmlWriter xmlWriter= null;
                        Date date=new Date();
                        String pubDate=date.toString();
                        context.put("date",pubDate);
                        int seq=pp.getInt("seq",-1);
                        String fPath;
                        String Path=dirPath+"/"+UName+"/"+"/Shared";
			if(mode.equals("private"))
			{
				read=read;
				writefile=writefile;
				fPath=dirPath+"/"+UName+"/"+"/Private"+"/"+Content;
                                xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(fPath,Content);
                                xmlWriter.removeElement("File",seq);
                                xmlWriter.writeXmlFile();
                                xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(Path,"Shared");
				String move1=mu.ConvertedString("personal_move1",file);
				if(file.equals("english"))
	                 		data.addMessage(filename+" "+move1);
				else
                        		data.setMessage(move1+" "+filename+" ");
			}
			else
			{
				read=writefile;
				writefile=data.getServletContext().getRealPath("UserArea"+"/"+UName+"/"+"/Private"+"/"+Content+"/"+filename) ;
				xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(Path,"Shared");
                                xmlWriter.removeElement("File",seq);
                                xmlWriter.writeXmlFile();
                                fPath=dirPath+"/"+UName+"/"+"Private"+"/"+Content;
                                xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(fPath,Content);	
				String move2=mu.ConvertedString("personal_move2",file);
				if(file.equals("english"))
                        		data.setMessage(filename+" "+move2);
				else
                        		data.setMessage(move2+" "+filename+" ");
			}
		       	File f=new File(read);
		       	File fo=new File(writefile);
                       	f.renameTo(fo);
			TopicMetaDataXmlWriter.appendFileElement(xmlWriter,filename,filename,pubDate);
                	xmlWriter.writeXmlFile();
				
                                                
		}
 		catch(Exception e){data.setMessage("The error in do Move "+e);}
	}                

	/**
        * Method for deleting the file from
	* Private Area and Shared Area
	* @param data RunData
        * @param context Context
        */
	public void doDelete(RunData data,Context context)
	{
		String file=data.getUser().getTemp("LangFile").toString();
		try
		{
                	User user=data.getUser();
                	String username=user.getName();
                	context.put("uname",username);
                	
			/**
			*Retreive the Parameters by help of
			*Parameter Parser and put in the context
			*for using in templates
			*/ 
			ParameterParser pp=data.getParameters();
                	String Filename=pp.getString("filename","");
                	String mode=pp.getString("mode","");
                	String stat1=pp.getString("stat1","");
                	context.put("stat1",stat1);
                	context.put("mode",mode);
                	String Content=pp.getString("topic","");
                	context.put("topic",Content);
                	String UName=pp.getString("name","");
                	/**
			*Get the RealPath and Path of
			*Private Area in which the topic exist
			*/	
			String UserPath=data.getServletContext().getRealPath("/UserArea");
                	File fileway;
			File filePath=new File(UserPath+"/"+UName+"/"+"Private"+"/"+Content);
		 	Vector name=new Vector();
			 int seq=pp.getInt("seq",-1); 
			 XmlWriter xmlwriter=null;
                        String dPath;
                  	if(mode.equals("DirRemoval"))
                        {	
                        	/**
				*Delete the topic name exist
				*in the Private Area of User
				*@see SystemIndependentUtil in utils
				*/
				SystemIndependentUtil.deleteFile(filePath);
				String del1=mu.ConvertedString("personal_del1",file);
				if(file.equals("english"))
					data.setMessage(Filename+" "+del1);
				else
					data.setMessage(del1+" "+Filename+" ");
                        }
                        else
                        {
				if(stat1.equals("fromPrivate"))
				{
					/**
					*Get the Path where the file exist in
					*the particular topic in the 
					*Private Area and
					*Deleting the particular File from the XmlFile.
                        		*@see TopicMetaDataXmlWriter in utils.
                        		*/	
					fileway=new File(UserPath+"/"+UName+"/"+"/Private"+"/"+Content+"/"+Filename);
					dPath=UserPath+"/"+UName+"/"+"Private"+"/"+Content;
                                	xmlwriter=TopicMetaDataXmlWriter.WriteXml_New(dPath,Content);
				}
				else
				{
					/**Get the Path of Shared Area of User
					*in which the file exist
					*/
					fileway=new File(UserPath+"/"+UName+"/"+"/Shared"+"/"+Filename);
					dPath=UserPath+"/"+UName+"/"+"/Shared";
                        	        xmlwriter=TopicMetaDataXmlWriter.WriteXml_New(dPath,"Shared");
				}
				SystemIndependentUtil.deleteFile(fileway);
				String del2=mu.ConvertedString("personal_del2",file);
					if(file.equals("english"))
						data.setMessage(Filename+" "+del2);
					else
						data.setMessage(del2+" "+Filename+" ");
			}
                          	xmlwriter.removeElement("File",seq);
                        	xmlwriter.writeXmlFile(); 
		}
                catch(Exception e){}
       }
	/**
         * This method is invoked when no button corresponding to
         * called Method
         * @param data RunData
         * @param context Context
         * @exception Exception, a generic exception
         *
         */

	public void doPerform(RunData data,Context context) throws Exception

	{
		
		String file=data.getUser().getTemp("LangFile").toString();
        	String nfind=mu.ConvertedString("action_msg",file);
        	ParameterParser pp=data.getParameters();
		String action = pp.getString("actionName","");
	//	ErrorDumpUtil.ErrorLog("repository at line 413==="+action);
		if(action.equals("eventSubmit_doMove"))
			doMove(data,context);

                else if(action.equals("eventSubmit_doUpload"))
                        doUpload(data,context);
                else if(action.equals("eventSubmit_doDelete"))
                        doDelete(data,context);
		else
			data.setMessage(nfind);

        	
        }

}	
