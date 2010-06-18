package org.iitk.brihaspati.modules.utils;         

/*
 * @(#) ViewFileUtil.java	
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur. 
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
import java.io.FileInputStream;
import javax.servlet.ServletOutputStream;
import org.apache.turbine.services.mimetype.MimeTypeService;
import org.apache.turbine.services.TurbineServices;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.apache.turbine.util.RunData;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
/**
 * This class display contains of any file 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

public class ViewFileUtil
{
	public static void ViewFile(String filePath,int dl,String fileID,RunData data ) 
	{
		try
		{
			String mimeTp="";
           		File file=new File(filePath);
			if(file.isDirectory())
			{
                       		mimeTp="text/html";
                	}
			/**
		  	*Getting the content type with the help of file extension
		  	*/	
			else
			{
				MimeTypeService mtService = (MimeTypeService)TurbineServices.getInstance().getService(MimeTypeService.SERVICE_NAME);

				int startIndex=fileID.lastIndexOf(".")+1;
                		String fileExt=fileID.substring(startIndex);
		 		mimeTp=mtService.getContentType(fileExt,"application/octet-stream");
			}
			/**
		  	*Display the content of file with the help of servlet
		  	*/
			ServletOutputStream out=data.getResponse().getOutputStream();
			File fileURLpath =new File(filePath);
			String fileNew_ID=fileID.replace("/",",");
                	if(fileURLpath.isDirectory()){
                        	String listFiles[]=fileURLpath.list();
                        	int length_list=listFiles.length;
                 		data.getResponse().setHeader("Content-Type",mimeTp);
                        	out.write(("<table width=100% bgcolor=#d9e3ec align=center>").getBytes());
                        	out.write(("<tr><td align=center>These are the files/directories present in the current directory </td></tr></table><br><br>").getBytes());
                        	out.write(("<table width=50% cellspacing=0 align=center border=1 bgcolor=#e5e5e5>").getBytes());
                        	for(int i=0;i < length_list;i++){
                                	out.write(("<tr><td>").getBytes());
                                	out.write(("<a href="+URLEncoder.encode((fileNew_ID+","+listFiles[i]),"UTF-8")+">"+listFiles[i]+"</a><br>").getBytes());
                                	out.write(("</td><td>").getBytes());
                                	File fileCurrent=new File(filePath+"/"+listFiles[i]);
                                	if(fileCurrent.isDirectory())
                                        	out.write(("Directory").getBytes());
                                	else
                                        	out.write(("File").getBytes());
                                	out.write(("</td></tr>").getBytes());
                        	}
                        	out.write(("</table><br><br>").getBytes());
				out=null;
               	 	}//if
                	else{
                 		FileInputStream fis=new FileInputStream(filePath);
				if(dl==1)
				{
					mimeTp="application/x-download";
				}
                 		data.getResponse().setHeader("Content-Type",mimeTp);
                 		data.getResponse().setHeader("Content-Disposition","inline;filename="+fileID);

                        	int readCount;
				
                        	byte[] buf=new byte[4*1024];
                        	while((readCount=fis.read(buf)) !=-1){
                                	out.write(buf,0,readCount);
              			}
				out=null;
			}//else
		}//try
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The Error in ViewFileUtil class ViewFile() --"+e);
		}
    }//
	public static void ViewInfo(String str,RunData data )
	{
		try{
		int readCount;
		data.getResponse().setHeader("Content-Type","text/html");
		ServletOutputStream out=data.getResponse().getOutputStream();
                out.write(str.getBytes());
		}
		catch (Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The Error in ViewFileUtil class ViewInfo() --"+ex);
                }
	}

}
