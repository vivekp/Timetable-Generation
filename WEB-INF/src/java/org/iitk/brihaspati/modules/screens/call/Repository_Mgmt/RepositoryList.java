package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)RepositoryList.java
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
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Author;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

/**
 * @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
 * @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
 */


public class RepositoryList extends SecureScreen_Author 
{
	MultilingualUtil Mutil=new MultilingualUtil();
	/**
        *@param data RunData
        *@param context Context
        */


	public void doBuildTemplate( RunData data, Context context )
	{
		String LangFile=data.getUser().getTemp("LangFile").toString();
                String etopic=Mutil.ConvertedString("personal_etopic",LangFile);
                String ncontent=Mutil.ConvertedString("Repo_topic",LangFile);

		try{  
                	/**
                       	 *Get the UserName and
                       	 *put it in the context
                       	 *for template use
                       	 */
                        User user=data.getUser();
	   		String authorname=user.getName();
			context.put("authorname",authorname);

                        /**
                         *Retrieve the Parameters
                         *by using the Parameter Parser
                         */

                        ParameterParser pp=data.getParameters();
                        String status=new String();
                        Vector v=new Vector();
			Vector Read= new Vector();

                	String stat=pp.getString("status","");
                	context.put("status",stat);
			String content1=pp.getString("name","");
			context.put("contentlist",content1);
			String topic=pp.getString("topic","");
			context.put("topic",topic);
			String Files=pp.getString("FileName","");
                        context.put("filename",Files);
			String seqno=pp.getString("seq","");
                        context.put("seq",seqno);
			String mode1=pp.getString("mode1","");
                        context.put("mode1",mode1);
			String UserPath=data.getServletContext().getRealPath("/Repository");

			if(stat.equals("fromDirectory") || stat.equals("fromSubDirectory")||mode1.equals("Move"))
			{//main if
				/**
				 *Get the list of author topic
				 *put in the context for use in templets
				 */
				File topicDir=new File(UserPath+"/"+content1);
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
					data.setMessage(ncontent);
				}
				else
				{
       	        	        	context.put("contentvalue",y);
				}
				/**
				 *Get the Files list
		                 *of Particular topic put in the context
                		 * for use in templates
				 */
				if(stat.equals("fromSubDirectory"))
				{//if2
					String filetopic= UserPath+"/"+content1+"/"+topic;
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
				}//if2
				/**
				 *Get the topic list 
				 *of particular author and put in the context
				 *for use in templates
				 */
				if(mode1.equals("Move"))
				{//if3
					File topicDir1=new File(UserPath+"/"+content1);
        	                	Vector k =new Vector();
					String filter1[]={"__des.xml"};
                			NotInclude exclude1=new NotInclude(filter1);
              		 		String ContentList1 []=topicDir1.list(exclude1);
                        		for(int m=0;m<ContentList1.length;m++)
					{
						if(!(ContentList1[m]).equals(topic))
                                         	{
	
        	                			k.add(ContentList1[m]);
						}
					}//for
       	        	        	context.put("contentvalue1",k);
				}//if3
					
			}//main if
	
		}//try
                catch(Exception e){}
    }
}
