package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)PermissionReceive.java
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

import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import java.io.File;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
* @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
* @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
*/

public class PermissionReceive extends SecureScreen 
{
	/**
        *@param data RunData
        *@param context Context
        */
        public void doBuildTemplate( RunData data, Context context )
	{
				
                try{
                	/**
                        *Get the UserName and
                        *put it in the context
                        *for template use
                        */
			String authorname=data.getUser().getName();
                        context.put("authorname",authorname);
                      //  context.put("username",authorname);
                        /**
                        *Retrieve the Parameters
                        *by using the Parameter Parser
                        */
                        ParameterParser pp=data.getParameters();
			String stat=pp.getString("status","permissionreceive");
                        context.put("status",stat);
			String CourseDir=pp.getString("CourseDir","");
			context.put("CourseDir",CourseDir);
                        String mode=pp.getString("mode","");
			context.put("mode",mode);

                     	//Get the Path for the different cases reading the permission
                        String UserPath=data.getServletContext().getRealPath("/Repository");
                        String Userarea=data.getServletContext().getRealPath("/UserArea");
                   	if(stat!=null)
			{
				String  userdesc=UserPath+"/"+authorname;
				String  userdesc1=Userarea+"/"+authorname+"/Private";
				File Check;
				File Check1=new File(userdesc+"/permissiongiven__des.xml");
				if(mode.equals("fromauthor"))
				{
					Check=new File(userdesc+"/permissionReceive__des.xml");	
				}
				else
				{
					Check=new File(userdesc1+"/permissionReceive__des.xml");	
				}
			
                        	/**
                        	*Here we getting the Details of the permission
                        	*listing the permitted files and put in the context
                        	*@see TopicMetaDataxmlReader in utils
                        	*/
				if(Check.exists())
				{//if2
                                	TopicMetaDataXmlReader permissionRead=null;
					if(mode.equals("fromauthor"))
					{
					
                                		permissionRead=new TopicMetaDataXmlReader(userdesc+"/permissionReceive__des.xml");
                                	}
					if(mode.equals("frominstructor"))
					{
						permissionRead=new TopicMetaDataXmlReader(userdesc1+"/permissionReceive__des.xml");
					}
                               		Vector Read=permissionRead.getDetails();
					if(Read.size()!=0)
                        		{
                               			context.put("read",Read);
                                		context.put("check","permit");
                        		}
                        		else
                        		{
                                		context.put("check","Nopermit");
                        		}
					/**
					*Here we getting the parameters
					*for reading the xml file
					*/
                                	String name=pp.getString("name","");
                                	context.put("listname",name);
                                	String list=pp.getString("list","");
                                	context.put("listvalue",list);
					String topiclist= UserPath+"/"+name+"/"+list;
					TopicMetaDataXmlReader topicMetaData=null;
					if(!(list.equals("")))
					{
						if(mode.equals("fromauthor")|| mode.equals("frominstructor"))
						{
                                			topicMetaData=new TopicMetaDataXmlReader(topiclist+"/"+list+"__des.xml");
						}
                                		Vector n=topicMetaData.getFileDetails();
                                		context.put("listfile",n);
					}
				}//if2
				/**
				*Here we reading the permission xml file
				*put in the vector and context for the use in templtes
				*@see TopicMetatDataXmlReader in utils
				**/
					
				if(mode.equals("fromauthor"))
				{//if3
					if(Check1.exists())
					{
                                		TopicMetaDataXmlReader permissionwrite;
                                		permissionwrite=new TopicMetaDataXmlReader(userdesc+"/permissiongiven__des.xml");
                                		Vector write=permissionwrite.getDetails();
						if(write.size()!=0)
                                        	{
                                			context.put("write",write);
                                                	context.put("check1","permit");
                                        	}
                                        	else
                                        	{
                                                	context.put("check1","Nopermit");
                                        	}
					}
				}//if3
			}//if stat	
				
                  }//try
                        catch(Exception rx){data.setMessage("The error in reading receiver xml!!!"+rx);}
        }
   }
