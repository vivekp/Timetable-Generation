package org.iitk.brihaspati.modules.actions;
/*
 * @(#)UploadAction.java
 *
 *  Copyright (c) 2005-2008,2009 ETRG,IIT Kanpur.
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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.commons.fileupload.FileUploadException;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.TotalFileCount;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.QuotaUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
//import org.iitk.brihaspati.modules.utils.DiscSpaceUtil;
import java.util.StringTokenizer;
/**
 * Class responsible for Upload files in particuler Area
 *
 * @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
 * @author <a href="mailto:nksinghiitk@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 * @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
 * @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a> 
 */

public class UploadAction extends SecureAction_Instructor
{
    /**
    * This method responsible for upload files
    * @param data Rundata
    * @param context Context
    */
	private String LangFile=null; 
    public void doUpload(RunData data, Context context)
    {
	try{
		User user=data.getUser();
		String uName=user.getName();
		int uid=UserUtil.getUID(uName);
		LangFile=(String)user.getTemp("LangFile");
		String courseHome=(String)user.getTemp("course_id","");
		ParameterParser pp=data.getParameters();
		String contentTopic=pp.getString("contentTopic","");
		String Pub=pp.getString("publish","");
		context.put("pub",Pub);
		Vector new_files_uploaded=new Vector();
		XmlWriter xmlWriter=null;
		if(contentTopic.equals(""))
		{
			String Mu_msg=MultilingualUtil.ConvertedString("uploadAction_msg ",LangFile);
			context.put("errorMess",Mu_msg);
			return;
		}	
		if(contentTopic.indexOf('/')!=-1 || contentTopic.indexOf("\\")!=-1)
		{
			String Mu_msg1=MultilingualUtil.ConvertedString("uploadAction_msg1",LangFile);
			context.put("errorMess",Mu_msg1);
			return;
		}
		context.put("errorMess","");
		String dateOfCreation=(new java.util.Date()).toString();
		String dateOfModification="";
		String coursesRealPath=TurbineServlet.getRealPath("/Courses");
		String tempFile[]=new String[10];
		FileItem fileItem;
		File f=null;
		File topicDir=new File(coursesRealPath+"/"+courseHome+"/Content/"+contentTopic);
		File tDir=new File(coursesRealPath+"/"+courseHome+"/Content/");
		String Path=coursesRealPath+"/"+courseHome+"/Content/"+contentTopic;
		String way=coursesRealPath+"/"+courseHome+"/Content/";
		String filePath="";
		f=new File(topicDir.getPath()+"/Unpublished/");

//check for available quota space and return true if space available
		String gname=GroupUtil.getGroupName(uid,2);
		long dirS=QuotaUtil.getDirSizeInMegabytes(new File(coursesRealPath+"/"+courseHome));
                boolean check=QuotaUtil.CompareQuotainCourse(dirS,gname);

                if(check){
		try
		{
			if(!Pub.equals("Publish"))	
				filePath = f.getPath()+"/";
			else
				filePath = topicDir.getPath()+"/";
			
			//f.mkdirs();
			try
			{
			File dFile=new File(tDir+"/"+"content__des.xml");
			Vector dc=new Vector();
			boolean flag=false;	
			if(dFile.exists()){
				TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(way+"/"+"content__des.xml");
                                dc=topicMetaData.getFileDetails();
				for(int i=0;i<dc.size();i++){
					String st=((FileEntry) dc.elementAt(i)).getName();
					if(st.equals(contentTopic)){
						flag=true;
					}
				}
				if(!flag){
                        		xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(way,"content");
   					TopicMetaDataXmlWriter.appendFileElement(xmlWriter,contentTopic,contentTopic,dateOfCreation);
                       			xmlWriter.writeXmlFile();
				}
			}
			else{	
				TopicMetaDataXmlWriter.writeWithRootOnly(dFile.getAbsolutePath());
				if(contentTopic.length()>0){
                        		xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(way,"content");
   					TopicMetaDataXmlWriter.appendFileElement(xmlWriter,contentTopic,contentTopic,dateOfCreation);
                       			xmlWriter.writeXmlFile();
                       		}
                       	}
                       	//xmlWriter.writeXmlFile();
			}//try
		catch(FileUploadException ex)
		{
			ErrorDumpUtil.ErrorLog("Error in writing topic in xml"+ex);
			data.setMessage("See ExceptionLog");
		}
			int successfulUploadFilesCount=0;
			int totalFilesEntries=0;
			Vector failedFiles=new Vector();

			boolean flag1=false;
			for(int count=0;count<10;count++)
			{
				boolean fileExists=false;
				fileItem=pp.getFileItem("file"+(count+1));
				if(fileItem!=null && fileItem.getSize() != 0)
				{
					String temp=fileItem.getName();
					int index=temp.lastIndexOf("\\");
					++totalFilesEntries;
					fileExists=false;
					tempFile[count]=temp.substring(index+1);
					File uploadedFileInUnpub=new File(f,tempFile[count]);
					File uploadedFileInTopicDir=new File(topicDir,tempFile[count]);
					if(uploadedFileInUnpub.exists() || uploadedFileInTopicDir.exists() || (temp.indexOf(',')!=-1) )
					{
						fileExists=true;
						failedFiles.addElement(tempFile[count]);
					}
					if(fileExists)
					continue;
					++successfulUploadFilesCount;
					new_files_uploaded.addElement(tempFile[count]);
					
					long fsize=fileItem.getSize()/1024/1024;
					long uquota=QuotaUtil.getCrsQuota(gname);
					uquota= uquota - dirS;
					long disSpace=QuotaUtil.getFileSystemSpace();
					if((uquota>fsize)&&(disSpace>fsize))
					{
                                        	f.mkdirs();
                                        	flag1=true;
                        			if(flag1)
						{
							File descFile= new File(topicDir+"/"+contentTopic+"__des.xml");
                                        		if(!descFile.exists())
							TopicMetaDataXmlWriter.writeWithRootOnly(descFile.getAbsolutePath());
                        				if(Pub.equals("Publish"))
                               			 	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(Path,contentTopic);
							int readCount;
							InputStream is=fileItem.getInputStream();
                                        		FileOutputStream fos=new FileOutputStream(filePath+tempFile[count]);
                                        		byte[] buf=new byte[4*1024];
                                        		while((readCount=is.read(buf)) !=-1)
							{
                                                		fos.write(buf,0,readCount);
	                                        	}
        	                                	fos.close();
                	                        	is.close();
							//fileItem.write(f1);
                                                }

                                        }
					else{
						data.setMessage("");
						data.addMessage(MultilingualUtil.ConvertedString("qmgmt_msg5",LangFile));
					}
	
					System.gc();
				}//fileTiem
			}//count
			if(flag1){	
			if(Pub.equals("Publish"))
			{
				if(new_files_uploaded.size()!=0)
                       		{
                               		for(int k=0;k<new_files_uploaded.size();k++)
                               		{
                                       		String fileName=(String)new_files_uploaded.get(k);
                                       		TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fileName,fileName,dateOfCreation);
                               		}//for
                       		}//if
                       		xmlWriter.writeXmlFile();
			}//ifpublish
			if(successfulUploadFilesCount>0) 
			{	
				if(successfulUploadFilesCount==totalFilesEntries)
				{
				// all the entries given were uploaded successfully
				context.put("uploadStatus","full");	
				}
				else
				{
				// some of the entries given were uploaded successfully
				context.put("uploadStatus","partial");	
				context.put("failedFiles",failedFiles);
		
				}
			}
			else
			{			
			// nothing was uploaded
			context.put("uploadStatus","nothing");	
			context.put("totalFilesEntries",(new TotalFileCount(totalFilesEntries) ) );
			context.put("failedFiles",failedFiles);
			}
			}//ifflag1
			else
			data.setMessage(MultilingualUtil.ConvertedString("qmgmt_msg2",LangFile));

		}//try
		catch(FileUploadException ex)
		{
			data.addMessage("The Error in Upload a file"+ex);
		}
	 }//if 
         else{
			data.setMessage(MultilingualUtil.ConvertedString("qmgmt_msg3",LangFile));
	}
	}//try
	catch(Exception ex)
	{
		data.addMessage("The Error in Uploading in Course contents !!"+ex);
	}
}//do

	public void topicSequence(String way,String contentTopic,String dateOfCreation){
		try
                {
			XmlWriter xmlWriter=null;
                        File dFile=new File(way+"/"+"content__des.xml");
                        Vector dc=new Vector();
                        boolean flag=false;
			try{
                        if(dFile.exists()){
                                TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(way+"/"+"content__des.xml");
                                dc=topicMetaData.getFileDetails();
                                for(int i=0;i<dc.size();i++){
                                        String st=((FileEntry) dc.elementAt(i)).getName();
                                        if(st.equals(contentTopic)){
                                                flag=true;
                                        }
                                }
                                if(!flag){
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(way,"content");
	                                TopicMetaDataXmlWriter.appendFileElement(xmlWriter,contentTopic,contentTopic,dateOfCreation);
                                        xmlWriter.writeXmlFile();
                                }
                        }
                        else{
                                TopicMetaDataXmlWriter.writeWithRootOnly(dFile.getAbsolutePath());
                                if(contentTopic.length()>0){
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(way,"content");
	                                TopicMetaDataXmlWriter.appendFileElement(xmlWriter,contentTopic,contentTopic,dateOfCreation);
                                        xmlWriter.writeXmlFile();
                                }
                        }
                        }//try
                        //xmlWriter.writeXmlFile();
			catch(FileUploadException e)
			{
			ErrorDumpUtil.ErrorLog("Error in writing topic in xml in topic sequence method"+e);
			System.out.println("See ExceptionLog");
                        //data.setMessage("See ExceptionLog");
			}
                }//try
                catch(Exception ex)
                {
                        ErrorDumpUtil.ErrorLog("Error in writing topic in xml in action topic sequence method"+ex);
			System.out.println("See ExceptionLog");
                      //  data.setMessage("See ExceptionLog");
                }

	}

    /**
     * Default action to perform if the specified action
     * cannot be executed
     * @param data RunData
     * @param context Context
     */
    public void doPerform( RunData data,Context context )
    {
	try{
		User user=data.getUser();
		String LangFile=(String)user.getTemp("LangFile"); 
		String actionName=data.getParameters().getString("actionName","");
		context.put("actionName",actionName);
		if(actionName.equals("eventSubmit_doUpload"))
		{
			doUpload(data,context);
		}
		else{
        		data.setMessage(MultilingualUtil.ConvertedString("usr_prof2",LangFile));
		
		}
	}
	catch(Exception ex)
	{
		data.setMessage("The Error in Default method !!"+ex);
	}
    }
}
