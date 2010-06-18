package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)RepositoryBrowser.java
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
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
* @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
* @author <a href="mailto:kshuklak@rediffmail.com">Kishore Kuamr shukla</a>
*/

public class RepositoryBrowser extends SecureScreen 
{
	MultilingualUtil Mutil=new MultilingualUtil();
	/**
        *@param data RunData
        *@param context Context
        */
	
 	public void doBuildTemplate( RunData data, Context context )
	{
		String file=data.getUser().getTemp("LangFile").toString();
                String etopic=Mutil.ConvertedString("personal_etopic",file);
                String topic1=Mutil.ConvertedString("Repo_topic",file);
		try
		{
                	/**
                	*Get the UserName and
                	*put it in the context
                	*for template use
                	*/
			String userdesc=new String();
               		User user=data.getUser();
               		String authorname=user.getName();
               		context.put("authorname",authorname);
                	/**
                	*Retrieve the Parameters
                	*by using the Parameter Parser
                	*/
               		ParameterParser pp=data.getParameters();
			String stat=pp.getString("status");
			context.put("status",stat);
               		String content=pp.getString("name","");
               		context.put("contentlist",content);
               		String topic=pp.getString("topic","");
               		context.put("topic",topic);
        		String UserPath=data.getServletContext().getRealPath("/Repository");
			/**
			*Get the authorname list
			*put in the Context for
			*use in Templates
			*/
               		Vector v=new Vector();
               		File dirHandle=new File(UserPath);
               		String author[]=dirHandle.list();
                	for(int i=0;i<author.length;i++)
               		{
                		v.add(author[i]);
               		}
               		context.put("authorvalue",v);

			/**
			*Get the Authors topics
			*put in the Context for
			*use in templates
			*filtering the des.xmlFiles	
			*/

			if(stat.equals("fromDirectory") || stat.equals("fromSubDirectory"))
			{//if1
				File topicDir=new File(UserPath+"/"+content);
				Vector y =new Vector();
                		String filter[]={"__des.xml"};
                		NotInclude exclude=new NotInclude(filter);
               			String ContentList []=topicDir.list(exclude);
                		for(int j=0;j<ContentList.length;j++)
                		{
                			y.add(ContentList[j]);
                		}
				if(y.size()==0)
				{
					data.setMessage(topic1);
				}
				else
				{
                			context.put("contentvalue",y);
				}
				//Get the file list
				Vector vct=new Vector();
				if(stat.equals("fromSubDirectory"))
				{//if3
					String filetopic= UserPath+"/"+content+"/"+topic;
                                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filetopic+"/"+topic+"__des.xml");
                                        Vector h=topicMetaData.getFileDetails();
                                        if(h==null)
                                        {
                                                data.setMessage(etopic);
                                        }
                                        else
                                        {
                                                context.put("FFfiles",h);
                                        }

				}//if3
			}//if1
		}//try

		catch(Exception e){}
	}
}

