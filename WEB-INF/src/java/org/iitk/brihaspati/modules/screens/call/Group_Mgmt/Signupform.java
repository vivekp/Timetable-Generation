package org.iitk.brihaspati.modules.screens.call.Group_Mgmt;

/*
 * @(#)Signform.java
 *
 *  Copyright (c) 2006-07 ETRG,IIT Kanpur.
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
 *  @author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *  @author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */


import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;

import java.util.Vector;
import java.io.File;


public class Signupform extends SecureScreen
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
                        * Get courseid,username and coursename for the user currently logged in
                        * Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */
                	User user=data.getUser();
			String username=user.getName();
			context.put("username",username);
			context.put("coursename",(String)user.getTemp("course_name"));
			String courseid=(String)user.getTemp("course_id");
			context.put("courseid",courseid);

			Vector glist=new Vector();
			String stuno="",uname="",Mode="";
			int len=0;

			//Get the path where the GroupList and groupname xml are there.
		        String groupPath=data.getServletContext().getRealPath("/Courses"+"/"+courseid+"/GroupManagement");
			File f=new File(groupPath+"/GroupList__des.xml");

			/**
                        *Reading the GroupList xml for getting the details
                        *groups (grouplist,groupname,username) and Mode
                        *Put in the contexts for use in template
                        *see @ TopicMetaDataXmlReader in Utils.
                        */
			TopicMetaDataXmlReader topicmetadata=null;
			if(f.exists())
			{
				topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
				Vector grplist=topicmetadata.getGroupDetails();
				if(grplist!=null)
				{
					for(int i=0;i<grplist.size();i++)
					{
						String grpname=((FileEntry) grplist.elementAt(i)).getName();
						String type =((FileEntry) grplist.elementAt(i)).gettype();
                                       		stuno=((FileEntry) grplist.elementAt(i)).getstudentno();
						//XML According to groupname like Scorm.xml
						topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+grpname+"__des.xml");
                                        	Vector list=topicmetadata.getGroupDetails();
						if(list!=null) 
						{
							for(int j=0;j<list.size();j++)
							{
								uname=((FileEntry) list.elementAt(j)).getUserName();
								if(username.equals(uname))
								{
									Mode="noempty";
									context.put("Mode","noempty");
									break;
								}
							}//for
						} //if
						if(Mode.equals("noempty"))
							break;
						if(type.equals("signupgroup")&&(!username.equals(uname)))
                                		{
							/**
							*Checking the signupgroups which are empty or having space
							* Put in the context for use in templates.
							*/
                                			len=Integer.parseInt(stuno);
                                        		if((list == null) || (list.size()<len ))
                                        			glist.addElement(grpname);
                               				if(glist.size()!=0)
                               				{
                                				context.put("glist",glist);
                                       				context.put("Mode","NoBlank");
							}
							else
							{
                                				context.put("Mode","Blank");
							}
						}//if
					}//for
				}//if
			}//if
		}//try
		 catch(Exception e){
                                        ErrorDumpUtil.ErrorLog("Error in Screen:Signupform !!"+e);
                                        data.setMessage("See ExceptionLog !! " );
                                  }

	}
}//class
