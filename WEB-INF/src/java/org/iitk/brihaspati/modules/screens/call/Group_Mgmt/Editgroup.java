package org.iitk.brihaspati.modules.screens.call.Group_Mgmt;

/*
 * @(#)Editgroup.java
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
* This class is contain the code for Edit groups
* @author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
* @author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
*/

import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;


public class Editgroup extends SecureScreen
{
	/**
        *This class is using for Edit groups(edit group name and group description)
     	* Place all the data object in the context
     	* for use in the template.
     	*/
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
                        ParameterParser pp=data.getParameters();
			
			/**
                        * Get courseid  and coursename for the user currently logged in
                        * Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */
			User user=data.getUser();
                        context.put("coursename",(String)user.getTemp("course_name"));
                        String courseid=(String)user.getTemp("course_id");
                        context.put("courseid",courseid);
			
			/**
                        *Retrieve the parameters by using the ParameterParser
                        *Put the parameters in context for use in templates.
                        */
			String status=pp.getString("Status","");
			context.put("Status",status);
			String grpName=pp.getString("grpname","");
			context.put("grpname",grpName);
			String type=pp.getString("type","");
			context.put("type",type);
			String studentno=pp.getString("studentno","");
			context.put("studentno",studentno);
			

                        //Get the path where the GroupList and groupname xml are there.
                        String groupPath=data.getServletContext().getRealPath("/Courses"+"/"+courseid+"/GroupManagement");
                        File f=new File(groupPath+"/GroupList__des.xml");

                        /**
                        *Reading the GroupList xml for getting the details
                        *groups (grouplist,groupname, groupdesc and grouptype)
                        *put in the contexts for use in template use
                        *@see TopicMetaDataXmlReader in Utils.
                        */
                        TopicMetaDataXmlReader topicmetadata=null;
                        Vector grplist=new Vector();
			String groupdesc="";
                        if(f.exists())
                        {
                                topicmetadata=new TopicMetaDataXmlReader(groupPath+"/GroupList__des.xml");
                                grplist=topicmetadata.getGroupDetails();
                                if(grplist==null)
                                return;
                                if(grplist.size()!=0)
                                {
					String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                                        String AdminConf = AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value");
                                        context.put("userConf",new Integer(AdminConf));
                                        context.put("userConf_string",AdminConf);

                                        int startIndex=pp.getInt("startIndex",0);
                                        int t_size=grplist.size();

                                        int value[]=new int[7];
                                        value=ListManagement.linkVisibility(startIndex,t_size,Integer.parseInt(AdminConf));

                                        int k=value[6];
                                        context.put("k",String.valueOf(k));

                                        Integer total_size=new Integer(t_size);
                                        context.put("total_size",total_size);

                                        int eI=value[1];
                                        Integer endIndex=new Integer(eI);
                                        context.put("endIndex",endIndex);

                                        //check_first shows the first five records
                                        int check_first=value[2];
                                        context.put("check_first",String.valueOf(check_first));

                                        //check_pre shows the first the previous list to the current records
                                        int check_pre=value[3];
                                        context.put("check_pre",String.valueOf(check_pre));

                                        //check_last1 gives the remainder values:-
                                        int check_last1=value[4];
                                        context.put("check_last1",String.valueOf(check_last1));

                                        //check_last shows the last records:-
                                        int check_last=value[5];
                                        context.put("check_last",String.valueOf(check_last));

                                        context.put("startIndex",String.valueOf(eI));
                                        Vector splitlist=ListManagement.listDivide(grplist,startIndex,Integer.parseInt(AdminConf));
                                        context.put("grplist",splitlist);

                                        //context.put("grplist",grplist);
                                        context.put("Mode","NoBlank");
                                }
                                else
                                	context.put("Mode","Blank");
				if(status.equals("Edit"))
				{	
					if(grplist!=null)
					for(int i=0;i<grplist.size();i++)
					{
                                		String gname =((FileEntry) grplist.elementAt(i)).getName();
                                        	if(grpName.equals(gname))
                                               	{
							topicmetadata=new TopicMetaDataXmlReader(groupPath+"/"+grpName+"__des.xml");
                                			groupdesc=topicmetadata.getTopicDescription();
						}
					}
					context.put("groupdesc",groupdesc);
				}//if
			
                        }//if exists

		}//try
                  catch(Exception e){
                                      ErrorDumpUtil.ErrorLog("Error in Screen:Editgroup !!"+e);
                                      data.setMessage("See ExceptionLog !! " );
                                    }

	}//method
}//calss
