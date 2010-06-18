package org.iitk.brihaspati.modules.screens.call.Bookmarks;

/*
 * @(#)ManageBookmarks.java
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
 *This class contains code for Manage Bookmarks
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
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

public class ManageBookmarks extends SecureScreen
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
			
			String move=pp.getString("stat","");
			context.put("stat",move);
			String search=pp.getString("stat1");
			String btname=pp.getString("btname","");
			context.put("btname",btname);
			String filePath=data.getServletContext().getRealPath("/Bookmarks"+"/"+username);
                        File f=new File(filePath+"/BookmarksList.xml");
			String Mode=new String();

			/**
                        *Reading the BookmarksList.xml for getting the details
                        *Put in the contexts for use in template
                        *see @ TopicMetaDataXmlReader in Utils.
                        */
                       	TopicMetaDataXmlReader topicmetadata=null;
                       	Vector topicList=new Vector();
			if(f.exists())
                        {
				if(search.equals("All"))
				{
                        		topicmetadata=new TopicMetaDataXmlReader(filePath+"/BookmarksList.xml");
                               		topicList=topicmetadata.getBookmarksDetails();
					context.put("stat1","All");
				}
				else
				{
					String valdir=pp.getString("valdir");
					context.put("valdir",valdir);
					topicmetadata=new TopicMetaDataXmlReader(filePath+"/"+valdir+"/"+valdir+"_des.xml");
                               		topicList=topicmetadata.getBookmarksDetails();
					context.put("stat1","Search");
				}
				if(topicList==null)
                                return;
                        	if(topicList.size()!=0)
                        	{
                        		String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                               		String AdminConf = AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value");
                               		context.put("userConf",new Integer(AdminConf));
                               		context.put("userConf_string",AdminConf);

                               		int startIndex=pp.getInt("startIndex",0);
                               		int t_size=topicList.size();
					Mode="NoBlank";
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
                        	}
				else
				Mode="Blank";

				context.put("Mode",Mode);
				/**
				*Getting the List of All Bookmark Directories
				*Put in the Context (as a Vector)for Using in the Templates.
				*/
				File topicDir1=new File(filePath);
                        	Vector v =new Vector();
                        	String filter[]={".xml"};
                        	NotInclude exclude=new NotInclude(filter);
                        	String dirList1 []=topicDir1.list(exclude);
                        	for(int m=0;m<dirList1.length;m++)
                        	{
                        		v.add(dirList1[m]);
                      	  	}
                        	if(v.size()!=0)
                        	{
                        		context.put("dirvalue1",v);
                        	}
			}//if exists
		}//try
		catch(Exception e){
                ErrorDumpUtil.ErrorLog("The exception in ManageBookmarks:"+e);
                data.setMessage("See ExceptionLog!! ");
                }
	}//method
}//class
