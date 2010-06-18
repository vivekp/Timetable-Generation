package org.iitk.brihaspati.modules.screens.call.Bookmarks;

/*
 * @(#)AddBookmarks.java
 *
 *  Copyright (c) 2008-09 ETRG,IIT Kanpur.
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

/**
 *This class contains code for Add Bookmarks 
 *@author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *@author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */

import java.util.Vector;
import java.io.File;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

public class AddBookmarks extends SecureScreen
{
	/**
     	* Place all the data object in the context
     	* for use in the template.
     	*/
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
			
                        ParameterParser pp=data.getParameters();

			/**
                        * Get the user currently logged in and related parameters
                        *Put it in the context for Using in templates
                        */
			String username=data.getUser().getName();
			context.put("username",username);

			String dir=pp.getString("bgroup");
			String topicname=pp.getString("name","");
                        context.put("name",topicname);
                        String url=pp.getString("location","");
                        context.put("location",url);
                        String comment=pp.getString("comment","");
                        context.put("comment",comment);
                        String dirname=pp.getString("dirname","");
                        context.put("dirname",dirname);
                        String stat=pp.getString("stat","");
			context.put("stat",stat);
                        String btname=pp.getString("btname","");
			context.put("btname",btname);
                        String mode=pp.getString("mode");
			context.put("mode",mode);
			TopicMetaDataXmlReader topicmetadata=null;
			ErrorDumpUtil.ErrorLog("\nmode"+mode+"\nstat"+stat);

			/**
			*Get the path and put the bookmarks directories in the Vector
			*for Using in the Templates
			*/
                        String filePath=data.getServletContext().getRealPath("/Bookmarks");
			File topicDir=new File(filePath+"/"+username);
			if(topicDir.exists())
			{
                        	Vector y =new Vector();
                        	String filter[]={".xml"};
                        	NotInclude exclude=new NotInclude(filter);
                        	String dirList []=topicDir.list(exclude);
                        	for(int j=0;j<dirList.length;j++)
                        	{
                        		y.add(dirList[j]);
                        	}
                        	if(y.size()!=0)
                        	{
                        		context.put("dirvalue",y);
					context.put("Mode","NoBlank");
                        	}
                        	else
                        	context.put("Mode","Blank");
			}
			/**
			*Reading the xml file collect the data in the Vector
			*By Using TopicMetaDataXmlReaderUtil.
			*/
			if(stat.equals("view"))
			{
				String bturl=pp.getString("bturl","");
                        	context.put("bturl",bturl);
				String bgroup=pp.getString("bgroup","");
                        	context.put("bgroup",bgroup);
				String filePath1=data.getServletContext().getRealPath("/Courses")+"/"+bgroup+"/Content/"+bturl;
				File f1=new File(filePath1+"/"+bturl+"__des.xml");
				if(f1.exists())
                        	{
					topicmetadata=new TopicMetaDataXmlReader(filePath1+"/"+bturl+"__des.xml");
                                	Vector v=topicmetadata.getFileDetails();
					if(v.size()!=0)
                                        {
                                                context.put("dirContent",v);
                                                context.put("Mode","NoBlank");
                                        }
					else
					context.put("Mode","Blank");
				}//if
			}//if
		}//try
		catch(Exception e){
                ErrorDumpUtil.ErrorLog("The exception in AddBookmarks:"+e);
                data.setMessage("See ExceptionLog!! ");
                }
	}//method
}//class
