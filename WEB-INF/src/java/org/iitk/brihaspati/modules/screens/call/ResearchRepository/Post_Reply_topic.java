package org.iitk.brihaspati.modules.screens.call.ResearchRepository;

/*
 * @(#)Post_Reply_topic.java
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import java.util.List;
import java.util.Date;

import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;

import org.iitk.brihaspati.om.ResearchRepositoryPeer;
import org.iitk.brihaspati.om.ResearchRepository;


import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ResrachRepoDetail;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

public class Post_Reply_topic extends SecureScreen
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
                        * Get username  and user_id for the user currently logged in
                        *Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */
			String Username=data.getUser().getName();
			context.put("loginname",Username);
			int user_id=UserUtil.getUID(Username);

			/**
                        *Retrieve the parameters by using the ParameterParser
                        *Putting the parameters context for use in templates.
                        */
			String Subject=pp.getString("subject","");
			context.put("subject",Subject);
			String Message=pp.getString("message","");
			context.put("message",Message);
			String mode=pp.getString("mode","");
			context.put("mode",mode);
			String topicname=pp.getString("topicname","");
			context.put("topicname",topicname);
			String subjectId=pp.getString("subjectId","");
			context.put("subjectId",subjectId);
			if((mode.equals("view")) || (mode.equals("Desc")))
			{
				/*Getting the list of the enter from the Database*/
				/* put in the context for the use in templates */
				List u=null;
				Criteria crit=new Criteria();
				crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
				u=ResearchRepositoryPeer.doSelect(crit);
				Vector entry=new Vector();
				Date date;
				for(int m=0;m<u.size();m++)
				{
					ResearchRepository element=(ResearchRepository)(u.get(m));
					String subject_id=Integer.toString(element.getSubjectId());
					String Reply_id=Integer.toString(element.getReplyId());
					String subject=element.getSubject();
					String Reply=Integer.toString(element.getReplies());
					int sender_userid=(element.getUserId());
					String sender_name=UserUtil.getLoginName(sender_userid);
					String Repo_Name=element.getRepoName();
					date=element.getPostTime();
					String posttime=date.toString();
					date=element.getExpiryDate();
					String expirydate=date.toString();
					
					ResrachRepoDetail RRDetail= new ResrachRepoDetail();
                                        RRDetail.setSender(sender_name);
                                        RRDetail.setPDate(posttime);
                                        RRDetail.setSubject(subject);
                                        RRDetail.setSubjectId(subject_id);
                                        RRDetail.setReplyId(Reply_id);
                                        RRDetail.setRepoName(Repo_Name);
                                        RRDetail.setExpiryDate(expirydate);
                                        RRDetail.setReplies(Reply);
                                        entry.addElement(RRDetail);
				}//for
				if(entry.size()!=0)
                        	{
                                	context.put("entry",entry);
                                	context.put("status","Noblank");
				}//ifentry
                        	else
					context.put("status","blank");
				/* Getting the subject of the related Repository*/
				if((mode.equals("Desc"))||(mode.equals("reply")))
				{
					String str[]=new String[1000];
					int i=0;
					int start=0;
					int stop=0;
					String MessDec="";
					String name=GetOwnername(topicname, data);
					String filePath=data.getServletContext().getRealPath("/ResearchRepository")+"/"+name+"/"+topicname;
					String Subject1=pp.getString("subject","");
					context.put("subject",Subject1);
					String subjectid="" ;
					for(int n=0;n<u.size();n++)
                                        {
                                                ResearchRepository element=(ResearchRepository)(u.get(n));
                                                String subject_id=Integer.toString(element.getSubjectId());
                                                int Reply_id=(element.getReplyId());
						int sender_userid=(element.getUserId());
                                                String sender_name=UserUtil.getLoginName(sender_userid);
                                                date=element.getPostTime();
                                                String posttime=date.toString();
						String subject=element.getSubject();
						if((subject.equals(Subject1)) && (Reply_id == 0))
						{
							subjectid=subject_id;
							context.put("sendername",sender_name);
							context.put("posttime",posttime);
						}
					}//for
					/* Getting the no of the Message */
					/* put in the context for the use in the templates*/
					try
					{
						BufferedReader br=new BufferedReader(new FileReader(filePath+"/"+"/Post"+"/Post.txt"));

						while((str[i]=br.readLine())!=null)
						{
							if(str[i].equals("<"+subjectid+ ">"))
							{
								start=i;
							}
							else if(str[i].equals("</"+subjectid+ ">"))
							{	
								stop=i;
							}
							i=i+1;
						}
						br.close();
					}
					catch(Exception e){data.setMessage("The error in raeding the message in Post_Reply_topic screen !!"+e);}
					for(int j=start+1;j<stop;j++)
					{
						MessDec=MessDec+ "\n"+str[j];
					}
					context.put("message1",MessDec);
					Vector rec=new Vector();
					Vector v=new Vector();
					for(int k=0;k<u.size();k++)
					{
						ResearchRepository element=(ResearchRepository)(u.get(k));
                                       		String subject_id=Integer.toString(element.getSubjectId());
                                       		String Reply_id=Integer.toString(element.getReplyId());
						int sender_userid=(element.getUserId());
                                       		String sender_name=UserUtil.getLoginName(sender_userid);
						date=element.getPostTime();
						String posttime=date.toString();
						if(subjectid.equals(Reply_id))
						{
							ResrachRepoDetail RRDetail= new ResrachRepoDetail();
                                       			RRDetail.setSender(sender_name);
                                        		RRDetail.setPDate(posttime);
							RRDetail.setSubjectId(subject_id);
							rec.addElement(RRDetail);
							try
                                        		{
                                               	 		BufferedReader br=new BufferedReader(new FileReader(filePath+"/"+"/Reply"+"/Reply.txt"));
                                                		while((str[i]=br.readLine())!=null)
                                                		{
                                                        		if(str[i].equals("<"+subject_id+ ">"))
                                                        		{
                                                                		start=i;
                                                        		}
                                                        		else if(str[i].equals("</"+subject_id+ ">"))
                                                        		{
                                                                		stop=i;
                                                        		}
                                                        		i=i+1;
                                                		}
                                                		br.close();
                                        		}//try
                                        		catch(Exception e){data.setMessage("The error in raeding the message in Post_Reply_topic screen !!"+e);}
                                        		for(int j=start+1;j<stop;j++)
                                        		{
								v.addElement(str[j]);
                                        		}
						}//ifcond	
					}//for
					context.put("rec",rec);
					if(v.size()!=0)
                                	{
						context.put("message2",v);
						context.put("Rsize",v.size());
                                        	context.put("status","Noblank");
                                	}//ifentry
                                	else
					{
                                       		context.put("status","blank");
					}
				}//mode close desc
			}//viewmode close main
		}//try
		catch(Exception e){data.setMessage("Error in Screen: Post_Reply_topic !!"+e);}
	}
	/*This method is for getting the owner name of the Repository */ 
	public String GetOwnername(String topicname,RunData data)
        {
                String name="";
                try
                {
                        String Tname="";
			String ResrepoPath=TurbineServlet.getRealPath("/ResearchRepository");
                        TopicMetaDataXmlReader topicmetadata=null;
                        topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+"/"+"RepositoryList.xml");
                        Vector collect1=topicmetadata.getResearchRepositoryDetails();
                        if(collect1!=null)
                        {
                                for(int i=0;i<collect1.size();i++)
                                {//for
                                        Tname =((FileEntry) collect1.elementAt(i)).getTopic();
                                        String Uname =((FileEntry) collect1.elementAt(i)).getUserName();
                                        if(Tname.equals(topicname))
                                        {
                                                name=Uname;
                                                break;
                                        }
                                }//for
                        }
                }
                catch(Exception e){
                ErrorDumpUtil.ErrorLog("Error in method GetOwnername !!"+e);
                data.setMessage("See ExceptionLog !! " );
                }
        return name;
        }//method ownername
}//class
