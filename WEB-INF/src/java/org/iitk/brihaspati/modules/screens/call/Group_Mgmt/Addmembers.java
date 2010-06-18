package org.iitk.brihaspati.modules.screens.call.Group_Mgmt;

/*
 * @(#)Addmembers.java
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

/**This class contain the code for the Addmember in the groups. 
 *  @author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *  @author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */

import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

import org.apache.torque.util.Criteria;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;

import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;



public class Addmembers extends SecureScreen
{
	/**
     	* Place all the data object in the context
     	* for use in the template.
     	*/
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();
                        ParameterParser pp=data.getParameters();
			
			/**
                        * Get courseid  and coursename for the user currently logged in
                        *Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */
                	User user=data.getUser();
			context.put("coursename",(String)user.getTemp("course_name"));
                        String courseid=(String)user.getTemp("course_id");
                        int g_id=GroupUtil.getGID(courseid);

			/**
			*Retrieve the parameters by using the ParameterParser
                        *Putting the parameters context for use in templates.
                        */
                        String grouptype=pp.getString("type","");
			String status=pp.getString("Status","");
			context.put("Status",status);
			String grpName1=pp.getString("grpname","");
			context.put("grpname",grpName1);
			String grpName=pp.getString("val","");
			context.put("val",grpName);
                        Vector studentlist=new Vector();
			String sname="",gtype="",grpdesc="",studentname="";

                        //Get the Path where groupmanagement dir and xml file. 
		        String groupPath=data.getServletContext().getRealPath("/Courses"+"/"+courseid+"/GroupManagement");
                        File f1=new File(groupPath+"/GroupList__des.xml");

			/**
                        *Reading the GroupList xml file
                        *put in the vector and context for the use in templtes
                        *@see TopicMetatDataXmlReader in utils.
                        **/
                        TopicMetaDataXmlReader topicmetadata=null;
                        Vector grplist1=new Vector();
                        if(f1.exists())
                        {
                                topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
                                grplist1=topicmetadata.getGroupDetails();
                                if(grplist1==null)
                                return;
                                if(grplist1.size()!=0)
                                {
                                        context.put("grplist",grplist1);
                                        context.put("Mode","NoBlank");
                                }
                                else
                                context.put("Mode","Blank");
                        }//if exists

			/**
                        *Selecting the particular course student detail
                        *put in the context for the use in templates.
                        */
                        Criteria crit =new Criteria();
                        crit.addJoin(TurbineUserPeer.USER_ID,TurbineUserGroupRolePeer.USER_ID);
                        crit.add(TurbineUserGroupRolePeer.ROLE_ID,3);
                        crit.and(TurbineUserGroupRolePeer.GROUP_ID,g_id);
                        crit.setDistinct();
                        List v=TurbineUserPeer.doSelect(crit);
                        for(int i=0;i<v.size();i++)
                        {
                                TurbineUser element=(TurbineUser)v.get(i);
                                studentname=element.getUserName();
                                studentlist.addElement(studentname);
                        }
			if(studentlist.size()==0)
			{
				data.setMessage(MultilingualUtil.ConvertedString("stu_msgc",LangFile));
				context.put("nolist","nolist");
			}
			else
			{
                        	context.put("studentlist",studentlist);
                        	context.put("nolist","yeslist");
			}

			/**
			*Reading the group xml for getting the details
			*put in the context for the use in templates.
			*@see TopicMetaDataXmlReader in Utils
			*/
			Vector selectedlist=new Vector();
			File f=new File(groupPath+"/"+grpName+"__des.xml");
			File f2=new File(groupPath+"/"+grpName1+"__des.xml");
			if((f.exists()) ||(f2.exists()))
			{
				if(status.equals("Edit"))
					topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+grpName1+"__des.xml");
				else
					topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+grpName+"__des.xml");

                                selectedlist=topicmetadata.getGroupDetails();
				if(selectedlist == null)
                               	return;
                                if(selectedlist.size()!=0)
                                {
					context.put("selectedlist",selectedlist);
                                        context.put("mode","noempty");
                                }
                                else
                                	context.put("mode","empty");
			}
			Vector list=new Vector();
			if(f1.exists())
			{
                        	topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
                        	Vector grouplist=topicmetadata.getGroupDetails();
                       		if(grouplist!=null)
                        	{
                              		for(int m=0;m<grouplist.size();m++)
                                	{//for
                                        	String gname =((FileEntry) grouplist.elementAt(m)).getName();
                                        	topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+gname+"__des.xml");
                                        	Vector sellist=topicmetadata.getGroupDetails();
                                        	if(sellist!=null)
						{
                                              		for(int k=0;k<sellist.size();k++)
                                                 	{
                       		                        	sname =((FileEntry) sellist.elementAt(k)).getUserName();
								list.addElement(sname);
                                                  	}//for
						}//if
                                	}//for
                        	}//if
				context.put("list",list);
			}
			/**
			*Getting the records of particular groups (description and type)
			*put in the context for the use in templates
			*@see TopicMetaDataXmlReader in Utils.
			*/
			if(f1.exists())
			{
				topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
                        	Vector collect=topicmetadata.getGroupDetails();
                        	if(collect!=null)
                        	{
                                	for(int i=0;i<collect.size();i++)
                                        {//for
                                        	String gname =((FileEntry) collect.elementAt(i)).getName();
                                               	if(grpName.equals(gname))
                                               	{
                                             		gtype =((FileEntry) collect.elementAt(i)).gettype();
                                                        topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+gname+"__des.xml");
                                                        grpdesc=topicmetadata.getTopicDescription();
                                                }
                                        }
                                        context.put("type",gtype);
                                        context.put("grpdesc",grpdesc);
                        	}
			}
		}//try
                  catch(Exception e){
                                   ErrorDumpUtil.ErrorLog("Error in Screen:Addmembers !!"+e);
                                   data.setMessage("See ExceptionLog !! " );
                                  }

	}
}
