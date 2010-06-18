package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)UploadMarks.java	
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
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */
import java.io.File;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
 /** 
  * In this class, We upload marks in particuler course by instructor 
  * if before upload marks then show and download marks list by Instructor
  * @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
  * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
  */
 
  
public class UploadMarks extends SecureScreen_Instructor
{
	public void doBuildTemplate( RunData data, Context context )
	{
		try{
			User user=data.getUser();
			String dir=(String)user.getTemp("course_id");
			context.put("course",(String)user.getTemp("course_name"));
	        	String coursesRealPath=TurbineServlet.getRealPath("/Courses");

			String marks=coursesRealPath+"/"+dir+"/Marks/MARK.txt";
			File marksFile=new File(marks);
			if(marksFile.exists())
			{
				context.put("fileExists","true");
				context.put("fileName","MARK.txt");

				if(data.getMessage()==null)
				{
					String LangFile=user.getTemp("LangFile").toString();
					data.setMessage(MultilingualUtil.ConvertedString("Marks_msg5",LangFile));
				}
			}
			else
			{
				context.put("fileExists","false");
			}
		}
		catch(Exception ex)
		{
			data.setMessage("The error in Upload Screen !!"+ex);
		}
    	}

}


