package org.iitk.brihaspati.modules.screens.call.Group_Mgmt;

/*
 * @(#)ViewActivity.java
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
import java.util.StringTokenizer;
import java.io.File;


public class ViewActivity extends SecureScreen
{
	/**
	*This class is use for viewing activity set by group administrator
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

			//Get the path where the GroupList and groupname xml are there.
		        String groupPath=data.getServletContext().getRealPath("/Courses"+"/"+courseid+"/GroupManagement");
			File f=new File(groupPath+"/GroupList__des.xml");

			TopicMetaDataXmlReader topicmetadata=null;
			Vector uName=new Vector();
			String uname="",grpname="",groupdesc="";
			if(f.exists())
			{//if1
                        	/**
                        	*Reading the GroupList xml for getting the details
                        	*groups (grouplist,groupname,grouptype) and Mode
                        	*Put in the contexts for use in template
                        	*@see TopicMetaDataXmlReader in Utils.
                                **/
				topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
				Vector grplist=topicmetadata.getGroupDetails();
                                for(int i=0;i<grplist.size();i++)
				{//for1
					grpname=((FileEntry) grplist.elementAt(i)).getName();
					String type =((FileEntry) grplist.elementAt(i)).gettype();
					topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+grpname+"__des.xml");
					groupdesc=topicmetadata.getActivity();
                                        Vector list=topicmetadata.getGroupDetails();
					if(list!=null) 
					{//if2
						for(int j=0;j<list.size();j++)
						{//for2
							String gnam=new String();
							uname=((FileEntry) list.elementAt(j)).getUserName();
							uName.addElement(uname);
                                                        if(username.equals(uname))
					                {//if3
                                                		context.put("type",type);
                                                        	context.put("grpname",grpname);
								if(groupdesc!=null)
								{
									/**
									* Get the groupActivity
									* and set the vm according to the activity.
									*/
	                                                        	StringTokenizer st=new StringTokenizer(groupdesc,",");
				                                	for(int kk=0;st.hasMoreTokens();kk++)
                                					{ //first 'for' loop
                                        					String msg_idd=st.nextToken();
										if(msg_idd.equals("Discussion Board"))
											context.put("act0",msg_idd);
										if(msg_idd.equals("Notice"))
											context.put("act1",msg_idd);
                                                                     	 	if(msg_idd.equals("Chat"))
  	                                                                        	context.put("act2",msg_idd);
										if(msg_idd.equals("Local Mail"))
											context.put("act3",msg_idd);
										if(msg_idd.equals("Assignment"))
                                                                        		context.put("act4",msg_idd);
                                        				}//for
								}//if
								gnam=grpname;
								if(gnam.equals(grpname))
                                                        		context.put("mblist",uName);
                                                        		context.put("Mode","noempty");
									context.put("groupdesc",groupdesc);
							}//if3	
						}//for2
						uName = new Vector();
					}//if2
				}//for1
			}//if1
		}//try
		catch(Exception e){
                                        ErrorDumpUtil.ErrorLog("Error in Screen:ViewActivity !!"+e);
                                        data.setMessage("See ExceptionLog !! " );
                                  }
	}//method
}//class
