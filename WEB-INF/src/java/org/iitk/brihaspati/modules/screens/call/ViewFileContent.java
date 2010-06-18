package org.iitk.brihaspati.modules.screens.call;         

/*
 * @(#)ViewFileContent.java	
 *
 *  Copyright (c) 2005-2006, 2008 ETRG,IIT Kanpur. 
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

import  org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;  
import org.iitk.brihaspati.modules.utils.ViewFileUtil; 
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil; 
import org.iitk.brihaspati.modules.utils.RemoteCourseUtilClient;
import org.apache.velocity.context.Context;
import javax.servlet.ServletOutputStream;
import org.apache.turbine.om.security.User;
import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 * In this class, Display of contents for any file.
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

public class ViewFileContent extends SecureScreen
{
	private String fileID=null;
        private String filePath=new String();
        private String URL=new String();
        private int index_filename;
	private MultilingualUtil m_u=new MultilingualUtil();
	
	/**
	* In this method,We display contents of any file or dir
	* @param data RunData
	* @param context Context
	* @see ViewFileUtil from Utils
	*/

     	public void doBuildTemplate( RunData data, Context context ) 
     	{
		try{
                        ParameterParser pp=data.getParameters();
                        URL = data.getRequest().getPathInfo().toString();
			index_filename=URL.indexOf("filename");
                        int endIndex=URL.indexOf("/",index_filename);
                        fileID=URL.substring(endIndex+1);
                        fileID=fileID.replace(",","/");
                        fileID=URLDecoder.decode(fileID, "UTF-8");
			
			String Type=pp.getString("type","");
			
			/**
                        * getting the actual path where stored the Repository contents file
                        */
	
			if(Type.equals("repo")) {
				String autherNm=pp.getString("contents","");
				String topcNm=pp.getString("TotalFiles","");
				String docRoot=data.getServletContext().getRealPath("/Repository")+"/";
				filePath=docRoot + autherNm +"/"+ topcNm +"/"+fileID;
			}
 
			/**
			 * getting the actual path where stored the Repository contents file
                         */

			else if(Type.equals("Remote")) {
				String dir=(String)data.getUser().getTemp("course_id");
                                String docRoot=data.getServletContext().getRealPath("/Courses")+"/" + dir + "/Content"+"/Remotecourse"+"/";
				String autherNm=pp.getString("contents","");
				String courseNm=pp.getString("cName","");
				String topcNm=pp.getString("TotalFiles","");
                                filePath=docRoot + autherNm +"/"+courseNm +"/"+ topcNm +"/"+fileID;
				String pDate =pp.getString("pDate");
                                RemoteCourseUtilClient.LocalCache(pDate ,autherNm ,courseNm,fileID,topcNm,dir);
                                if(!(new File(filePath)).exists())
                                {
                                        filePath = docRoot + "/ErrorResponse.html";
                                }
                        } 
			
			/**
                         * getting the actual path where stored the UserArea contents file
                         */
			
			else if(Type.equals("UserArea")) {
				String autherNm=pp.getString("contents","");
                        	String docRoot=data.getServletContext().getRealPath("/UserArea")+"/";
				String topcNm=pp.getString("TotalFiles","");
				String area=pp.getString("area","");
				if(area.equals("private"))
				{
                          		filePath=docRoot + autherNm +"/"+"Private/"+ topcNm +"/"+fileID;
                		} else if(area.equals("shared")) {
                          		filePath=docRoot + autherNm +"/"+ "Shared/"+fileID;
                		} else {
					String uname=(String)data.getUser().getName();
					String topic=pp.getString("topic","")+"/";
                          		filePath=docRoot + uname +"/Attachment/"+topic+fileID;
                		}
			} 
			
			else if(Type.equals("reload")) {
                           	String docRoot=data.getServletContext().getRealPath("/BrihaspatiEditor")+"/";
                            	filePath=docRoot+ fileID;
                        } 

			/**
			 * getting the actual path where stored the DB file
                         */

			else if(Type.equals("DB")){
				String docRoot=data.getServletContext().getRealPath("/Courses")+"/";
				String dir=(String)data.getUser().getTemp("course_id")+"/";
				String topic=pp.getString("topic","")+"/";
				String msg_id=pp.getString("msgid","");
                             	filePath=docRoot+dir+"/DisBoard/"+topic+"/Attachment/"+msg_id+"/"+fileID;
			}
                         else if(Type.equals("reload"))
                        {
                           String docRoot=data.getServletContext().getRealPath("/BrihaspatiEditor")+"/";
                            filePath=docRoot+ fileID;
                        }
			else if(Type.equals("Bookmarks"))
			{
				String topic=pp.getString("topic","")+"/";
                       		String dir=pp.getString("dir","")+"/";
				String docRoot=data.getServletContext().getRealPath("/Courses")+"/";
				filePath=docRoot+dir+"Content/"+topic+fileID;
			}
				
			/**
                         * getting the actual path where stored the Assignment file
                         */

			else if(Type.equals("Assignment")){
				String dir=(String)data.getUser().getTemp("course_id")+"/";
                                String msg_id=pp.getString("msgid","");
	                        filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Assignment/"+msg_id+"/"+fileID;
			} 
			
			/**
                         * getting the actual path where stored the Archive file
                         */
		
			else if(Type.equals("Archive")) {
				String attachment=pp.getString("attachment","notAttachment");
				String dir=(String)data.getUser().getTemp("course_id")+"/";
				String msg_id=pp.getString("msgid","");
				if(attachment.equals("Attachment")){
                                	filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Archive/"+msg_id+"/Attachment"+"/"+fileID;
                               	} else{
					filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/Archive/"+msg_id+"/"+fileID;
                             	}
			} else if(Type.equals("marks")) {
				String docRoot=data.getServletContext().getRealPath("/Courses")+"/";
                       		String dir=(String)data.getUser().getTemp("course_id")+"/";
				filePath=docRoot+dir+"Marks/"+fileID;
			}
			else if(Type.equals("content")) {
				
                                String docRoot=data.getServletContext().getRealPath("/Courses")+"/";
                                String dir=(String)data.getUser().getTemp("course_id")+"/";
				String topic=pp.getString("topic","")+"/";
                        	filePath=docRoot+dir+"Content/"+topic+fileID;
			}else if(Type.equals("fromsearch")){
				String dir=pp.getString("dir")+"/";
				String docRoot=data.getServletContext().getRealPath("/Courses")+"/";
				String topic=pp.getString("topic","")+"/";
				filePath=docRoot+dir+"Content/"+topic+fileID;
			}
			
			/**
                         * getting the actual path where stored the unpublish file
                         */

			else if(Type.equals("unpub"))
                        {
                                String dir=(String)data.getUser().getTemp("course_id");
                                String docRoot=data.getServletContext().getRealPath("/Courses");
                                String topic=pp.getString("topic","")+"/";
                                filePath=docRoot +"/"+ dir +"/Content/"+ topic +"/Unpublished/"+fileID;
                        }else{
				String msg="Error in Raw Page for display contains !!";
                        	ErrorDumpUtil.ErrorLog(msg);
			}

			int dl=pp.getInt("dl",-1);
                
                        /**
                         * @see ViewFileUtil from Utils
                         */
                        
                        ViewFileUtil.ViewFile(filePath,dl,fileID,data);
		
		}//try
		catch(Exception e)
		{	
			String msg="Error in Raw Page for display contains !!"+ e;
			ErrorDumpUtil.ErrorLog(msg);

		}
    }//dobuild
}
