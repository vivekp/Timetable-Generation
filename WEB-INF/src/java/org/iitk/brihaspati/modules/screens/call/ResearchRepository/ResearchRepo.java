package org.iitk.brihaspati.modules.screens.call.ResearchRepository;

/*
 * @(#)ResearchRepo.java
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
 *This class contains code for Creating a group
 *@author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *@author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.AdminProperties;

import java.util.Vector;
import java.io.File;

public class ResearchRepo extends SecureScreen
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
			String topicname=pp.getString("topicname","");
			context.put("topicname",topicname);
			String mode=pp.getString("mode","");
			context.put("mode",mode);
			String stat=pp.getString("stat","");
			context.put("stat",stat);
			String type=pp.getString("type","");
			context.put("type",type);
			String actionName=pp.getString("actionName","");
                        String filePath=data.getServletContext().getRealPath("/ResearchRepository")+"/";
			File f=new File(filePath+"/RepositoryList.xml");

                        /**
                        *Reading the RepositoryList xml for getting the details
                        *groups (Reposirtorylist,name) and Mode
                        *Put in the contexts for use in template
                        *see @ TopicMetaDataXmlReader in Utils.
                        */
                        TopicMetaDataXmlReader topicmetadata=null;
			String xmlfile="/RepositoryList.xml";
                        Vector topicList=new Vector();
                        if((f.exists())&&(!actionName.equals("eventSubmit_doSearch"))&&(!type.equals("Unjoin")))
                        {
					context.put("NoSearch","NoSearch");
                               	topicmetadata=new TopicMetaDataXmlReader(filePath+"/RepositoryList.xml");
                               	topicList=topicmetadata.getResearchRepositoryDetails();
				context.put("allTopics1",topicList);
				if(topicList== null)
                                return;
                        	if(topicList.size()!=0)
                        	{
                        		String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                                	String AdminConf = AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value");
                                	context.put("userConf",new Integer(AdminConf));
                                	context.put("userConf_string",AdminConf);

                                	int startIndex=pp.getInt("startIndex",0);
                                	int t_size=topicList.size();

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
                                	Vector splitlist=ListManagement.listDivide(topicList,startIndex,Integer.parseInt(AdminConf));

                                	context.put("allTopics",splitlist);
					context.put("content1","value");
				}
				else
					context.put("content1","empty");
			}//ifexists
			/* here we getting the list of the user which join the group*/
			/* put in the context for the templates */
			if(type.equals("Unjoin"))
			{
				String owner=pp.getString("owner","");
				context.put("owner",owner);
				String TopicName=pp.getString("tname","");
				context.put("tname",TopicName);
				String path=filePath+"/"+owner+"/"+TopicName;
				File topicdesxml =new File(path+"/"+TopicName+"_des.xml");
                        	if(topicdesxml.exists())
                        	{
                        		/**
                        		*Getting  the list of user 
                        		*@see TopicMetaDataXmlReader in Util.
                        		*/
					Vector topicList1=new Vector();
                               		topicmetadata=new TopicMetaDataXmlReader(path+"/"+TopicName+"_des.xml");
                                	topicList=topicmetadata.getResearchRepositoryDetails();
					if(topicList==null)
                                	return;
                                	if(topicList.size()!=0)
					{
						for(int j=0;j<topicList.size();j++)
						{
							String tname =((FileEntry)topicList.elementAt(j)).getTopic();
                                        		String name =((FileEntry)topicList.elementAt(j)).getName();
                                        		String userName =((FileEntry)topicList.elementAt(j)).getUserName();
							if(!name.equals(username))
							{
								FileEntry fileEntry = new FileEntry();
                                                        	fileEntry.setTopic(tname);
                                                        	fileEntry.setName(name);
                                                        	fileEntry.setUserName(userName);
                                                        	topicList1.addElement(fileEntry);
							}
							
						}//for
						if(topicList1.size()!=0)
						{
							context.put("collect",topicList1);
							context.put("value","yesvalue");
						}
						else
						context.put("value1","blank");
					}
					else
					context.put("value","novalue");
				}
			}//closeunjoin
		}//try
		catch(Exception e){
                ErrorDumpUtil.ErrorLog("The exception in ResearchRepo:"+e);
                data.setMessage("See ExceptionLog!! ");
                }
	}//method
}//class
