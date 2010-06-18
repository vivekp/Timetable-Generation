package org.iitk.brihaspati.modules.actions;

/*
 * @(#)ExtractAction.java	
 *
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur. 
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
import java.util.StringTokenizer;
import java.util.Date;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TotalFileCount;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;

/**
 * This class Read zip file and Extract specified path
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *
 */
public class ExtractAction extends SecureAction_Instructor
{
	/**
        * In this method, Cancel process then back to original postion
        *
        * @param data RunData
        * @param context Context
        * @see SystemIndependentUtil Utils
        */

	public void doCancel(RunData data,Context context)
	{
		User user=data.getUser();
		ParameterParser pp=data.getParameters();		
		String topic=pp.getString("topic","");
		
		String userLoginName=user.getName();

		String dir;
		dir=(String)user.getTemp("course_id");
		String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic;
                File tempDirFilePath=new File(filePath+"/Unpublished/"+userLoginName);
                SystemIndependentUtil.deleteFile(tempDirFilePath);
		data.setScreenTemplate("call,CourseMgmt_User,PublishModule.vm");
	}
	/**
        * In this method, Extract a zip file
        * @param data RunData
        * @param context Context
        * @see SystemIndependentUtil Utils
        */


	public void doExtract(RunData data,Context context) throws Exception 
	{
		User user=data.getUser();
		ParameterParser pp=data.getParameters();		
		String topic=pp.getString("topic","");
		
		int totalFiles=pp.getInt("totalfiles",0);

		String userLoginName=user.getName();

		String group,dir;
		group=dir=(String)user.getTemp("course_id");
		String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic;			context.put("filePath",filePath);
		int countFiles=0;
		int fileseqno[];

		int successfulUploadFilesCount=0;
        	int totalFilesEntries=0;
        	Vector failedFiles=new Vector();
		String fileItem;

		Vector fileList=new Vector();
	        for(int count=1;count<totalFiles;count++)
        	{
                	boolean fileExists=false;
	                fileItem=pp.getString("extract"+count);
			String tempFile;
        	        if(fileItem!=null)
	                {
                	        String temp=fileItem;
        	                int indexbackslash=temp.lastIndexOf("\\");
				int indexfrontslash=temp.lastIndexOf("/");
				int index=indexfrontslash>indexbackslash?indexfrontslash:indexbackslash;
	                        ++totalFilesEntries;
                	        fileExists=false;
        	                tempFile=temp.substring(index+1);
                        	File uploadedFileInTopicDir=new File(filePath,tempFile);
    				File uploadedFileInUnpub=new File(filePath+"/Unpublished/"+tempFile);
                        	if(uploadedFileInUnpub.exists() || uploadedFileInTopicDir.exists())
                        	{
                                	fileExists=true;
                                	failedFiles.addElement(tempFile);
                        	}
						
				if(fileExists)
                	                continue;
                        	++successfulUploadFilesCount;
                        	File tempDirFilePath=new File(filePath+"/Unpublished/"+userLoginName+"/"+fileItem);
				tempDirFilePath.renameTo(uploadedFileInUnpub);
				fileList.addElement("extract"+count+"   :   "+fileItem);
                	}
				
		}
                File tempDirFilePath=new File(filePath+"/Unpublished/"+userLoginName);
                SystemIndependentUtil.deleteFile(tempDirFilePath);
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
                	context.put("fileList",fileList);
	}
	/**
        * Invoke this method as default
        * @param data RunData
        * @param context Context
        */

public void doPerform(RunData data,Context context) throws Exception 
	{
		String actionToPerform=data.getParameters().getString("actionName","");
		context.put("actionO",actionToPerform);
			
		if( actionToPerform.equals("eventSubmit_doExtract") )
		{
			doExtract(data,context);
		}
	}

}

