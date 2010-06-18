package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)ViewZipContent.java
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
 * 
 */
 /**
 * Always performs a Security Check that you've defined before
 * executing the doBuildtemplate().
 * @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * 
 */

import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import org.apache.turbine.om.security.Group;
import java.io.File;
import org.iitk.brihaspati.modules.utils.GetUnzip;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;

public class ViewZipContent extends SecureScreen_Instructor{
	public void doBuildTemplate(RunData data,Context context)
	{
		try{
		Vector zipFileView=new Vector();
		User user=data.getUser();
		
		String topic=data.getParameters().getString("topic","");
		String fileName=data.getParameters().getString("filename","");
		context.put("topic",topic);
		String group,dir;
		group=dir=(String)user.getTemp("course_id");
		context.put("course",(String)user.getTemp("course_name"));

		String userLoginName=user.getName();
		String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Content/"+topic+"/Unpublished/";
		
		File temp=new File(filePath+userLoginName);
		temp.mkdirs();
		File zipSourceFile=new File(filePath+fileName);
		try{
	        	GetUnzip guz=new GetUnzip( zipSourceFile.getAbsolutePath(),temp.getAbsolutePath());
		}catch(Exception e){
			data.setMessage("The ZIP FILE FORMAT is not appropriate !! For zipping the files in WINDOWS, use 'WINZIP' tool and in LINUX use the 'zip' command !! Press the 'Cancel' button to go back.");
		}

/*		String filter[]={"__des.xml","Unpublished"};
		NotInclude exclude=new  NotInclude(filter);
		File[] list=f.listFiles(exclude);
		boolean found[]=new boolean[list.length];*/
	
		recursiveDirectoryTrace(temp,zipFileView);
	
/*		for(int i=0;i<list.length;i++)
		{
			if(list[i].isDirectory())
			{
		for(int i=0;i<list.length;i++)
		{
	//		FileEntry fileEntry = new FileEntry();

			fileName=list[i].getName();
	//		String relFilePath=list[i].getParentFile().getName()+"/"+fileName;
	//		fileEntry.setName(relFilePath);

	//		Date date=new Date(list[i].lastModified());
	//		fileEntry.setPDate(date.toString());

			unpublished.addElement(fileName);
		}*/
/*		context.put("courseid",dir);
		context.put("visibleContent",visible);
		context.put("accessibleContent",accessible);*/
		context.put("zipFileView",zipFileView);
		}
		catch(Exception ex)
		{
			data.setMessage("The Error in Zip File For Extraction !!"+ex);
		}
	}
	public void recursiveDirectoryTrace(File temp,Vector zipFileView)
	{		
		File[] list=temp.listFiles();
		for(int i=0;i<list.length;i++)
		{
			if(list[i].isDirectory())
			{
				zipFileView.addElement(list[i]);
				recursiveDirectoryTrace(list[i],zipFileView);
			}
			else
			{
				zipFileView.addElement(list[i]);
			}
		
		}
	}

}
