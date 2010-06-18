package org.iitk.brihaspati.modules.actions;

/*
 * @(#) ResearchRepo_Action.java
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
 */

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Vector;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

import java.sql.Date;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.torque.util.Criteria;

import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import com.workingdogs.village.Record;

import org.iitk.brihaspati.om.ResearchRepositoryPeer;
import org.iitk.brihaspati.om.ResearchRepository;

import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;

import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.modules.screens.VelocityScreen;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;


/**This class contain the code Create, Delete, Update
* @author: <a href="mailto:seema_020504@yahoo.com">seema pal</a>
* @author: <a href="mailto:kshuklak@rediffmail.com">kishore kumar shukla</a>
*/


public class ResearchRepo_Action extends SecureAction
{
	/**
         * Place all the data object in the context for use in the template.
         * @param data RunData instance
         * @param context Context instance
         * @exception Exception, a generic exception
         */
	
	/** Get path for ResearchRepository.*/

        String ResrepoPath=TurbineServlet.getRealPath("/ResearchRepository");
	VelocityScreen vs=new VelocityScreen();
	String LangFile ="";
	/**This method is for the cancelling process.*/

	public void doCancel(RunData data,Context context)
        {
                data.setScreenTemplate("call,ResearchRepository,ResearchRepo.vm");
        }

	/**This method is for the creating grouptype.*/

	public void doCreate_resrepo(RunData data,Context context)
	{       
		try{
        		ParameterParser pp=data.getParameters();
			LangFile=data.getUser().getTemp("LangFile").toString();
			/**
                        * Get username for the user currently logged in
                        * @see UserUtil in Util.
                        */
			String userName=data.getUser().getName();
			String topicdesc=pp.getString("description","");
			String topicname=pp.getString("topicname","");
			if(StringUtil.checkString(topicname) != -1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                                return;
                        }
			context.put("topics",topicname);


                        /**
			*Get  path and creating ResearchRepository dir.
			*/
                        File f=new File(ResrepoPath+"/"+userName+"/"+topicname);
			if(!f.exists())
                        f.mkdirs();
			/**
			*Here we creating the blank xml for maintaining the record
			*@see TopicMetaDataXmlWriter in Util.
			*/
			File Repolistxml= new File(ResrepoPath+"/RepositoryList.xml");
                        if(!Repolistxml.exists())
                                TopicMetaDataXmlWriter.writeWithRootOnly(Repolistxml.getAbsolutePath());
			XmlWriter xmlWriter=null;
                        boolean found=false;
                        String joineduser="";
			String NumberofPost="0";
			java.util.Date date=new java.util.Date();
                	String CreationDate=date.toString();
                        String xmlfile="/RepositoryList.xml";
                        if(!Repolistxml.exists())
                        {
                                xmlWriter=new XmlWriter(ResrepoPath+"/"+xmlfile);
                        }
                        /**
                        *Checking for  the existing group
                        *@see TopicMetaDataXmlReader in Util.
                        */
                        else
                        {
                                TopicMetaDataXmlReader topicmetadata=null;
                                topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+"/"+xmlfile);
                                Vector collect=topicmetadata.getResearchRepositoryDetails();
                                if(collect!=null)
                                {
                                        for(int i=0;i<collect.size();i++)
					{//for
                                        	String Reponame =((FileEntry) collect.elementAt(i)).getTopic();
                                                if(topicname.equals(Reponame))
                                                        found=true;
                                                	//data.setMessage("This group is already exists !!");
							 data.setMessage(MultilingualUtil.ConvertedString("brih_This",LangFile) +" "+MultilingualUtil.ConvertedString("ResearchGroup",LangFile)+" "+MultilingualUtil.ConvertedString("GrpmgmtGroup",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction6",LangFile)+" " +"!!");

                                        }//for
                                }//if
                        }//else

                        /**
                        *Appending the entry in the xml File
                        *@see TopicMetaDataXmlWriter in Util.
                        */
                        if(found==false)
                        {
                                xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(ResrepoPath,xmlfile);
                                TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,topicname,userName,joineduser,NumberofPost,CreationDate);
                                xmlWriter.writeXmlFile();
				//data.setMessage("Group Created Successfully");
				data.setMessage(MultilingualUtil.ConvertedString("ResearchGroup",LangFile)+" "+MultilingualUtil.ConvertedString("GrpmgmtGroup",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction5",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile)+" "+"!!");
			}
                        String filepath=(ResrepoPath+"/"+userName+"/"+topicname);
			File topicdesxml =new File(filepath+"/"+topicname+"_des.xml");
                        if(!topicdesxml.exists())
                        {
                                TopicMetaDataXmlWriter.writeWithRootOnly(topicdesxml.getAbsolutePath());
                                xmlWriter=new XmlWriter(filepath+"/"+topicname+"_des.xml");
                        }//if
                        else
                                xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(filepath,topicname);
                                TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,topicname,userName,userName,NumberofPost,CreationDate);
                                xmlWriter.changeData("Desc",topicdesc,0);
                                xmlWriter.writeXmlFile();


		}//try
		catch(Exception e){
				 	ErrorDumpUtil.ErrorLog("The exception in doCreate_resrepo "+e);
                        		System.out.println("See Exception message in ExceptionLog.txt file:: ");
			}
	}//docreategrouptype
	 /** This method is for the Group Deletion .*/

        public void doDeleteTopic(RunData data,Context context)
        {
                try
                {
                        ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();

			String userName=data.getUser().getName();
                        /** Getting the groupnames for the deletion.*/
			String mode=pp.getString("mode","");
			String topicname=pp.getString("topicname","");
                        String deleteList=pp.getString("deleteFileNames","");
                        StringTokenizer st=new StringTokenizer(deleteList,"^");
                        String fileToDelete[]=new String[st.countTokens()];
                        for(int i=0;st.hasMoreTokens();i++)
                        {
                                fileToDelete[i]=st.nextToken();
                        }
                        /** Get the path of the groups and xml file.*/
			String deltype="grpdel";
                        XmlWriter xmlWriter;
                        Vector str=new Vector();
			String xmlfile="RepositoryList.xml";
			String name=GetOwnername(topicname, data);
                       	TopicMetaDataXmlReader topicmetadata=null;
                        /**
                        * Delete the Entry  and geting the groupname for deleting
                        *the file.
                        */
			String path=ResrepoPath+"/"+name+"/"+topicname;
			String filepath="",del="";
                       	List n=null;
                       	Criteria crit=new Criteria();
                       	crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                       	n=ResearchRepositoryPeer.doSelect(crit);
			for(int i=0;i<fileToDelete.length;i++)
                        {
				if(!mode.equals("view"))
				{
                                	String TopicName=fileToDelete[i];
                                	str=DeleteEntry(ResrepoPath,xmlfile,deltype,TopicName,data);
                                	File file=new File(ResrepoPath+"/"+userName+"/"+TopicName);
                                	SystemIndependentUtil.deleteFile(file);
					/*Delete data from database of related topic */
					crit=new Criteria();
                                        crit.add(ResearchRepositoryPeer.REPO_NAME,TopicName);
                                        ResearchRepositoryPeer.doDelete(crit);
                        	}
				if(mode.equals("view"))
				{
					String subject=pp.getString("subject","");
					String subject_id=fileToDelete[i];
					/*Delete message in database */
					crit=new Criteria();
                                        crit.add(ResearchRepositoryPeer.SUBJECT_ID,subject_id);
                                        ResearchRepositoryPeer.doDelete(crit);
					filepath=path+"/Post"+"/Post.txt";
					del=DeletePostReply(filepath,subject_id);
					/* deleting the reply of related subject */
					String subnewid="";
                        		for(int b=0;b<n.size();b++)
					{
                                		ResearchRepository element=(ResearchRepository)(n.get(b));
                                		String subject_id1=Integer.toString(element.getSubjectId());
                                		String  Reply_id=Integer.toString(element.getReplyId());
						if(subject_id.equals(Reply_id))
						{
							subnewid=subject_id1;
						}
						crit=new Criteria();
                                        	crit.add(ResearchRepositoryPeer.SUBJECT_ID,subnewid);
                                        	ResearchRepositoryPeer.doDelete(crit);
					}
					/* updating the number of posts */
					Vector postcount=new Vector();
                       			crit=new Criteria();
                       			crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                       			n=ResearchRepositoryPeer.doSelect(crit);
                        		for(int m=0;m<n.size();m++)
                        		{
                                		ResearchRepository element=(ResearchRepository)(n.get(m));
                                		String subjectid=Integer.toString(element.getSubjectId());
                                		int Reply_id=(element.getReplyId());
                                		String Repo_Name=element.getRepoName();
                                		if(Repo_Name.equals(topicname)&&(Reply_id == 0))
                                		{
                                        		postcount.addElement(subjectid);
                                		}
                        		}//for
                        		String NumberofPost=Integer.toString(postcount.size());
					ErrorDumpUtil.ErrorLog("postcount"+postcount.size());
                        		topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+"/"+xmlfile);
                        		Vector collect=topicmetadata.getResearchRepositoryDetails();
                        		if(collect!=null)
                        		{
                                		for(int k=0;k<collect.size();k++)
                                		{//for
                                        		String tname =((FileEntry) collect.elementAt(k)).getTopic();
                                        		String name1 =((FileEntry)collect.elementAt(k)).getName();
                                        		String userName1=((FileEntry)collect.elementAt(k)).getUserName();
                                        		String CreationDate =((FileEntry)collect.elementAt(k)).getPDate();
                                        		if(tname.equals(topicname))
                                        		{
                                                		xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(ResrepoPath,xmlfile);
                                                		TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,tname,userName1,name1,NumberofPost,CreationDate);
                                                		xmlWriter.writeXmlFile();
                                                		str=DeleteEntry(ResrepoPath,xmlfile,deltype,topicname,data);
                                        		}//if
                                		}//for
                        		}//if

				}//ifview
			}//for
			if(mode.equals("view"))
                        data.setMessage(MultilingualUtil.ConvertedString("brih_sub",LangFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangFile)+" "+"!!");
			else
                        data.setMessage(MultilingualUtil.ConvertedString("GrpmgmtGroup",LangFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangFile)+" "+"!!");
			if(mode.equals("Desc"))
                        {
                        	String subject_id=pp.getString("subjectId","");
                               	/*Delete message in database */
                               	crit=new Criteria();
                               	crit.add(ResearchRepositoryPeer.SUBJECT_ID,subject_id);
                        	ResearchRepositoryPeer.doDelete(crit);
                        //	ErrorDumpUtil.ErrorLog("actionsubject_id"+subject_id+"mode"+mode);
				filepath=path+"/Reply"+"/Reply.txt";
				del=DeletePostReply(filepath,subject_id);
				/* updating the number of Replies */
				int j=0;
                                Vector postcount1=new Vector();
                        	crit=new Criteria();
                        	crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                        	n=ResearchRepositoryPeer.doSelect(crit);
				String Subject1=pp.getString("subject","");
				String subjectid="" ;
                                for(int l=0;l<n.size();l++)
                                {
                                	ResearchRepository element=(ResearchRepository)(n.get(l));
                                        String subject_id2=Integer.toString(element.getSubjectId());
                                        int Reply_id=(element.getReplyId());
                                        String subject=element.getSubject();
                                        if((subject.equals(Subject1)) && (Reply_id == 0))
                                        {
                                        	subjectid=subject_id2;
					}
                                }//for
                                for(j=0;j<n.size();j++)
                                {
                                        ResearchRepository element=(ResearchRepository)(n.get(j));
                                        String subject_id1=Integer.toString(element.getSubjectId());
                                        String Reply_id=Integer.toString(element.getReplyId());
                                        if(subjectid.equals(Reply_id))
                                        {
                                                postcount1.addElement(subject_id1);
                                        }
                                }
                                int Replies=postcount1.size();
                                for(j=0;j<n.size();j++)
                                {
                                        ResearchRepository element=(ResearchRepository)(n.get(j));
                                        String subject_id2=Integer.toString(element.getSubjectId());
                                        String subject2=element.getSubject();
                                        int Reply_id=(element.getReplyId());
                                        if((Reply_id==0)&&(subject2.equals(Subject1)))
                                        {
                                                crit.add(ResearchRepositoryPeer.SUBJECT_ID,subjectid);
                                                crit.add(ResearchRepositoryPeer.REPLIES,Replies);
                                                ResearchRepositoryPeer.doUpdate(crit);
                                        }//if
                                }//for
                        data.setMessage(MultilingualUtil.ConvertedString("brih_Rep",LangFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangFile)+" "+"!!");
                        }//modeDesc
                        //data.setMessage(" '"+groupName+"' Group deleted successfully");
                        //data.setMessage(MultilingualUtil.ConvertedString("GrpmgmtGroup",LangFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangFile)+" "+"!!");
			context.put("view",mode);
                  }//try
                  catch(Exception e){
                  ErrorDumpUtil.ErrorLog("Error in method:doDeleteGroup !!"+e);
                  data.setMessage("See ExceptionLog !! " );
                  }
       	}//method
	public void doJoinUser(RunData data,Context context)
	{
		try
		{
			ParameterParser pp=data.getParameters();
        	        LangFile=data.getUser().getTemp("LangFile").toString();
			//Get the topicname for using to get the path
			String userName=data.getUser().getName();
			String topicname=pp.getString("topicname","");
                        XmlWriter xmlWriter=null;
                        boolean found=false;
                        java.util.Date date=new java.util.Date();
                        String CreationDate=date.toString();
                        String NumberofPost="";
                        TopicMetaDataXmlReader topicmetadata=null;
			//get the owner of the group by GetOwnername method
			String name=GetOwnername(topicname,data);
			String filepath1=(ResrepoPath+"/"+name+"/"+topicname);
			File topicdesxml =new File(filepath1+"/"+topicname+"_des.xml");
                        if(!topicdesxml.exists())
                        {
                                xmlWriter=new XmlWriter(filepath1+"/"+topicname+"_des.xml");
                        }
                        /**
                        *Checking for  the existing user
                        *@see TopicMetaDataXmlReader in Util.
                        */
                        else
                        {
                                topicmetadata=new TopicMetaDataXmlReader(filepath1+"/"+topicname+"_des.xml");
                                Vector collect=topicmetadata.getResearchRepositoryDetails();
                                if(collect!=null)
                                {
                                        for(int i=0;i<collect.size();i++)
                                        {//for
                                                String Uname =((FileEntry) collect.elementAt(i)).getName();
                                                if(userName.equals(Uname))
                                                        found=true;
                                                //data.setMessage("This User is already exists !!");
						data.setMessage(MultilingualUtil.ConvertedString("brih_This",LangFile)+" "+MultilingualUtil.ConvertedString("brih_user",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction6",LangFile)+" "+"!!");
                                        }//for
                                }//if
                        }//else
			/**
                        *Appending the entry in the xml File
                        *@see TopicMetaDataXmlWriter in Util.
                        */
                        if(found==false) 
                        {
                                xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(filepath1,topicname+"_des.xml");
                                TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,topicname,name,userName,NumberofPost,CreationDate);
                                xmlWriter.writeXmlFile();
                                //data.setMessage("Group Joined Successfully");
				 data.setMessage(MultilingualUtil.ConvertedString("ResearchGroup",LangFile)+" "+MultilingualUtil.ConvertedString("GrpmgmtGroup",LangFile)+" "+MultilingualUtil.ConvertedString("brih_join",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile)+" "+"!!");

                        }
		}
		catch(Exception e){
                ErrorDumpUtil.ErrorLog("Error in method:doJoinUser !!"+e);
                data.setMessage("See ExceptionLog !! " );
                }
	}
	/** This mehod is for unjoin ..................*/

	public void doUnjoinUser(RunData data,Context context)
	{
		try
		{
			ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();
			//Get the parameters form the templates
			String userName=data.getUser().getName();
                        String topicname=pp.getString("tname","");
                        String deleteList=pp.getString("deleteFileNames","");
                        StringTokenizer st=new StringTokenizer(deleteList,"^");
                        String fileToDelete[]=new String[st.countTokens()];
                        for(int i=0;st.hasMoreTokens();i++)
                        {
                                fileToDelete[i]=st.nextToken();
                        }
			String deltype="unjoin",del="",path="",typedel="grpdel",xmlfile=topicname+"_des.xml",xmlfile1="RepositoryList.xml",User="";
			int Replyid=0;
			List u=null;
			XmlWriter xmlWriter;
			//Get the owner of the Repository by GetOwnername method
			String name=GetOwnername(topicname,data);
			String filepath=ResrepoPath+"/"+name+"/"+topicname;
			Vector str=new Vector();
			//Get the values from the Database of related user
                       	Criteria crit=new Criteria();
                       	crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                       	u=ResearchRepositoryPeer.doSelect(crit);
			for(int i=0;i<fileToDelete.length;i++)
                        {
                                User=fileToDelete[i];
				str=DeleteEntry(filepath,xmlfile,deltype,User,data);
				ErrorDumpUtil.ErrorLog("str"+str+"filepath"+filepath+"User"+User+"xmlfile"+xmlfile+"name"+name);
				int user_id=UserUtil.getUID(User);
				for(int m=0;m<u.size();m++)
                        	{
					ResearchRepository element=(ResearchRepository)(u.get(m));
					String subject_id=Integer.toString(element.getSubjectId());
					int sender_userid=(element.getUserId());
					int Reply_id=(element.getReplyId());
					if(user_id == sender_userid)
					{
                        			/*Delete message in database */
                        			crit.add(ResearchRepositoryPeer.SUBJECT_ID,subject_id);
                       				ResearchRepositoryPeer.doDelete(crit);
						if(Replyid == Reply_id)
						{
							/*Delete Post message */
                        				path=filepath+"/Post"+"/Post.txt";
                        				del=DeletePostReply(path,subject_id);
						}//reply
						else
						{
							/*Delete Reply message */
							path=filepath+"/Reply"+"/Reply.txt";
                        				del=DeletePostReply(path,subject_id);
						}//else
					}//if
				}//for
				/* updating the number of posts */
                                Vector postcount=new Vector();
                                crit=new Criteria();
                               	crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                              	u=ResearchRepositoryPeer.doSelect(crit);
                                for(int h=0;h<u.size();h++)
				{
                                	ResearchRepository element1=(ResearchRepository)(u.get(h));
                                       	String subjectid=Integer.toString(element1.getSubjectId());
                                       	int Reply_id1=(element1.getReplyId());
                                        String Repo_Name=element1.getRepoName();
                                        if(Repo_Name.equals(topicname)&&(Reply_id1 == 0))
                                        {
                                        	postcount.addElement(subjectid);
                                       	}
                               	}//for
				String NumberofPost=Integer.toString(postcount.size());
                               	TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+"/"+xmlfile1);
                               	Vector collect=topicmetadata.getResearchRepositoryDetails();
                               	if(collect!=null)
                               	{
                                       	for(int k=0;k<collect.size();k++)
                                	{//for
                                               	String tname =((FileEntry) collect.elementAt(k)).getTopic();
                                        	String name1 =((FileEntry)collect.elementAt(k)).getName();
                                                String userName1=((FileEntry)collect.elementAt(k)).getUserName();
                                                String CreationDate =((FileEntry)collect.elementAt(k)).getPDate();
                                                if(tname.equals(topicname))
                                                {
                                                	xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(ResrepoPath,xmlfile1);
                                                        TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,tname,userName1,name1,NumberofPost,CreationDate);
                                                        xmlWriter.writeXmlFile();
                                                        str=DeleteEntry(ResrepoPath,xmlfile1,typedel,topicname,data);
                                                }//if
                                        }//for
                                }//if
			}//for
			/* updating the number of Replies */
			int l=0;
                        Vector postcount1=new Vector();
                        Vector postcount2=new Vector();
                        crit=new Criteria();
                        crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                        u=ResearchRepositoryPeer.doSelect(crit);
                        String subjectid="" ,Subject1="";
                        for(l=0;l<u.size();l++)
                        {
                       		ResearchRepository element2=(ResearchRepository)(u.get(l));
                                String subject_id4=Integer.toString(element2.getSubjectId());
               			int Reply_id1=(element2.getReplyId());
				String subject=element2.getSubject();
                                if((Reply_id1 == 0))
                                {
					postcount1.addElement(subject_id4);
                                }
			}//for1
			for(int s=0;s<postcount1.size();s++)
			{
				ErrorDumpUtil.ErrorLog("s----"+s);
				String firstid=(String)postcount1.get(s);
				ErrorDumpUtil.ErrorLog("firstid ....."+firstid);
                               	for(l=0;l<u.size();l++)
				{
					ErrorDumpUtil.ErrorLog("\nl-----"+l);
                               		ResearchRepository element5=(ResearchRepository)(u.get(l));
                                	String subject_id5=Integer.toString(element5.getSubjectId());
               				int Reply_id5=(element5.getReplyId());
					String subject5=element5.getSubject();
                                        if(Reply_id5==(Integer.parseInt(firstid)))
                                        {
                                        	postcount2.addElement(subject_id5);
						ErrorDumpUtil.ErrorLog("Replyid5-----"+Reply_id5);
                                        }
				}//for2
                               	int Replies=postcount2.size();
				ErrorDumpUtil.ErrorLog("Replies...."+Replies);
                                for(int j=0;j<u.size();j++)
                                {
                                	ResearchRepository element4=(ResearchRepository)(u.get(j));
                                       	String subject_id2=Integer.toString(element4.getSubjectId());
                                       	int Reply_id3=(element4.getReplyId());
                                       	if((Reply_id3==0)&&(subject_id2.equals(firstid)))
                                       	{
						ErrorDumpUtil.ErrorLog("\nsubject_id2-----"+subject_id2);
                                       		crit.add(ResearchRepositoryPeer.SUBJECT_ID,subject_id2);
                                		crit.add(ResearchRepositoryPeer.REPLIES,Replies);
                                        	ResearchRepositoryPeer.doUpdate(crit);
                                       	}//if
					ErrorDumpUtil.ErrorLog("\nj-----"+j+"\nReplies....."+Replies);
                                }//for3
				postcount2= new Vector();
				Replies = 0 ;
				ErrorDumpUtil.ErrorLog("\nRepliesout3....."+Replies);
			}//for1
			//data.setMessage("unjoined successfully ");
			data.setMessage(MultilingualUtil.ConvertedString("brih_unjoin",LangFile)+" "+MultilingualUtil.ConvertedString("QueBankUtil_msg5",LangFile)+" "+"!!");
		}//try
		catch(Exception e){
		ErrorDumpUtil.ErrorLog("Error in method:doUnjoineUser !!"+e);
                data.setMessage("See ExceptionLog !! " );
                }
	}

	/** this method is for the post the topic */

	public void doPosttopic(RunData data,Context context) 
	{
		try
		{
			ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();
                        //These parameter are retrive from the template for post topic
			String UserName=data.getUser().getName();
			String subject=pp.getString("subject","");
			String message=pp.getString("message","");
			String topicname=pp.getString("topicname","");
			String mode=pp.getString("mode","");
			String Rep_id=pp.getString("Repid");
			File f;
			String name=GetOwnername(topicname, data);
			//get userid and expired date by the help of User Util and ExpiryUtil
			int user_id=UserUtil.getUID(UserName);
			String Cur_date=ExpiryUtil.getCurrentDate("-");
                        Date Post_date=Date.valueOf(Cur_date);
			Date expDate=Date.valueOf(ExpiryUtil.getExpired(Cur_date,30));

			//insert subject with message in the database.
			Criteria crit=new Criteria();
			crit.add(ResearchRepositoryPeer.REPLY_ID,Rep_id);
			crit.add(ResearchRepositoryPeer.SUBJECT,subject);
			crit.add(ResearchRepositoryPeer.REPLIES,0);
			crit.add(ResearchRepositoryPeer.USER_ID,user_id);
			crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
			crit.add(ResearchRepositoryPeer.POST_TIME,Post_date);
			crit.add(ResearchRepositoryPeer.EXPIRY,30);
			crit.add(ResearchRepositoryPeer.EXPIRY_DATE,expDate);
			ResearchRepositoryPeer.doInsert(crit);
			int subject_id=0;
			String Query_subjectid="SELECT MAX(SUBJECT_ID) FROM RESEARCH_REPOSITORY";
			List u=ResearchRepositoryPeer.executeQuery(Query_subjectid);
			for(ListIterator j=u.listIterator();j.hasNext();)
			{
				Record item=(Record)j.next();
                                subject_id=item.getValue("MAX(SUBJECT_ID)").asInt();
			}
			context.put("subject_id",subject_id);
			/** From here we start to strore the problem(post/reply) 
			* in a txt file.
			*/
			String filepath;
			if(mode.equals("post"))
			filepath=ResrepoPath+"/"+name+"/"+topicname+"/"+"Post";
			else
			filepath=ResrepoPath+"/"+name+"/"+topicname+"/"+"Reply";
			f=new File(filepath);
			if(!f.exists())
			f.mkdirs();
			if(mode.equals("post"))
			filepath=f+"/"+"Post.txt";
			else
				filepath=f+"/"+"Reply.txt";
			java .util.Date date=new java.util.Date();
			FileWriter fw=new FileWriter(filepath,true);
			fw.write("\n");	
			fw.write("<" + subject_id + ">"+"\n"+message);
			fw.write("\n"+"</" + subject_id + ">");
			fw.close();
			//count and insert the no of post & Reply in the Database //
			Vector postcount=new Vector();
			List n=null;
			TopicMetaDataXmlReader topicmetadata=null;
                       	crit=new Criteria();
                       	crit.add(ResearchRepositoryPeer.REPO_NAME,topicname);
                        n=ResearchRepositoryPeer.doSelect(crit);
			for(int m=0;m<n.size();m++)
                        {
                        	ResearchRepository element=(ResearchRepository)(n.get(m));
                                String subjectid=Integer.toString(element.getSubjectId());
				int Reply_id=(element.getReplyId());
				String Repo_Name=element.getRepoName();
				if(Repo_Name.equals(topicname)&&(Reply_id == 0))
				{
					postcount.addElement(subjectid);
				}
			}//for
			String NumberofPost=Integer.toString(postcount.size());
			String xmlfile="/RepositoryList.xml";
			XmlWriter xmlWriter=null;
			String deltype="grpdel";
			topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+"/"+xmlfile);
                        Vector collect=topicmetadata.getResearchRepositoryDetails();
                        if(collect!=null)
                        {
                        	for(int i=0;i<collect.size();i++)
                                {//for
                                	String tname =((FileEntry) collect.elementAt(i)).getTopic();
                                        String name1 =((FileEntry)collect.elementAt(i)).getName();
                                        String userName =((FileEntry)collect.elementAt(i)).getUserName();
                                        String CreationDate =((FileEntry)collect.elementAt(i)).getPDate();
                                        String Post =((FileEntry)collect.elementAt(i)).getstudentno();
                                        if(tname.equals(topicname))
					{
                                		xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(ResrepoPath,xmlfile);
                                		TopicMetaDataXmlWriter.appendResearchRepository(xmlWriter,tname,userName,name1,NumberofPost,CreationDate);
                                		xmlWriter.writeXmlFile();
						Vector str=DeleteEntry(ResrepoPath,xmlfile,deltype,topicname,data);

					}//if
				}//for
			}//if
			if(!mode.equals("post"))
			{
				int j=0;
				Vector postcount1=new Vector();
				for(j=0;j<n.size();j++)
                        	{
					ResearchRepository element=(ResearchRepository)(n.get(j));
                                	String subject_id1=Integer.toString(element.getSubjectId());
					String Reply_id=Integer.toString(element.getReplyId());
                                	if(Rep_id.equals(Reply_id)) 
                                	{
                                        	postcount1.addElement(subject_id1);
                                	}
				}
				int Replies=postcount1.size();
					//ErrorDumpUtil.ErrorLog("Replies"+Replies+"postcount"+postcount);
				for(j=0;j<n.size();j++)
                        	{
					ResearchRepository element=(ResearchRepository)(n.get(j));
                                	String subject_id2=Integer.toString(element.getSubjectId());
                                	String subject2=element.getSubject();
					int Reply_id=(element.getReplyId());
					if((Reply_id==0)&&(subject2.equals(subject)))
					{
						crit.add(ResearchRepositoryPeer.SUBJECT_ID,Rep_id);
                        			crit.add(ResearchRepositoryPeer.REPLIES,Replies);
                       				ResearchRepositoryPeer.doUpdate(crit);
					}//if
				}//for
			}//ifmodereply
			//---------------------------------//		
			//data.setMessage("Send successfully !!!!");
			data.setMessage(MultilingualUtil.ConvertedString("brih_send",LangFile)+" "+MultilingualUtil.ConvertedString("QueBankUtil_msg5",LangFile)+" "+"!!");
			if(mode.equals("post")||mode.equals("reply"))
				vs.doRedirect(data,"call,ResearchRepository,ResearchRepo.vm");
		}
		catch(Exception e){	
		ErrorDumpUtil.ErrorLog("Error in method:doPosttopic !!"+e);
                data.setMessage("See ExceptionLog !! " );
		}
	}
	public void doPost(RunData data,Context context)
	{
		try
		{
			ParameterParser pp=data.getParameters();
			LangFile=data.getUser().getTemp("LangFile").toString();
			//These parameters retrive from the templates 
			String UserName=data.getUser().getName();
                        String topicname=pp.getString("topicname","");
                        String mode=pp.getString("mode","");
			String name=GetOwnername(topicname,data);
			String filePath=ResrepoPath+"/"+name+"/"+topicname;
                        TopicMetaDataXmlReader topicmetadata=null;
			if(mode .equals("post"))
			{
				//here checking the Existing user to post the topic
                                String checkForUser="";
                                topicmetadata=new TopicMetaDataXmlReader(filePath+"/"+topicname+"_des.xml");
                                Vector topicList=topicmetadata.getResearchRepositoryDetails();
						ErrorDumpUtil.ErrorLog("filePath"+topicList);
				String matchName="";
				if(topicList!= null)
				{
                                	for(int i=0;i<topicList.size();i++)
                                	{//for
                                        	String Username =((FileEntry)topicList.elementAt(i)).getName();
                                        	if(Username.equals(UserName))
                                        	{
							matchName=Username;
							break;
                                        	}
					}//for	
					if(matchName.equals(UserName))
						vs.doRedirect(data,"call,ResearchRepository,Post_Reply_topic.vm");
					else
						//data.setMessage("Sorry! you have not joined this Researchgroup!!");
						 data.setMessage(MultilingualUtil.ConvertedString("Research_mess",LangFile)+" "+"!!");

				}//if
				//else
				//data.setMessage("Sorry! you have not joined this Researchgroup!!");
			//	data.setMessage(MultilingualUtil.ConvertedString("Research_mess",LangFile)+" "+"!!");
			}//if
		}//try
                catch(Exception e){
                ErrorDumpUtil.ErrorLog("Error in method:doPost !!"+e);
                data.setMessage("See ExceptionLog !! " );
                }
	}//method close

	public void doSearch(RunData data,Context context)
	{
		try
		{
			ParameterParser pp=data.getParameters();
                        String select=pp.getString("queryList");
                        if(select.equals(""))
                        	select=pp.getString("select");
                        /*Check for special characters*/
                        String matchvalue=StringUtil.replaceXmlSpecialCharacters(pp.getString("valueString"));
                        context.put("select",select);
                        context.put("valueString",matchvalue);
			FileEntry fileEntry = new FileEntry();
			String xmlfile="/RepositoryList.xml";
			Vector Search1=new Vector();
			//here we reading the xml file by help of TopicMetaDataXmlReader util
			TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(ResrepoPath+xmlfile);
                        Vector Search=topicmetadata.getResearchRepositoryDetails();
                        if(Search!=null)
                        {
                                for(int i=0;i<Search.size();i++)
				{
					/* This code is for Searching the group*/
					/*and user by the String or character*/
                                	String tname =((FileEntry)Search.elementAt(i)).getTopic();
                                       	String name =((FileEntry)Search.elementAt(i)).getUserName();
                                       	String Post =((FileEntry)Search.elementAt(i)).getstudentno();
                        		if(select.equals("Research Group"))
                                	{
						Pattern pat =  Pattern.compile(matchvalue);
                                        	Matcher mat = pat.matcher(tname);
                                        	if(mat.lookingAt())
                                        	{
							fileEntry = new FileEntry();
                                                	fileEntry.setTopic(tname);
                                                	fileEntry.setUserName(name);
                                                	fileEntry.setstudentno(Post);
                                                	Search1.addElement(fileEntry);
                                        	}
                                        }
                                        if(select.equals("Author Name"))
                                        {
						Pattern pat =  Pattern.compile(matchvalue);
                                                Matcher mat = pat.matcher(name);
                                                if(mat.lookingAt())
                                                {
                                                        fileEntry = new FileEntry();
                                                        fileEntry.setTopic(tname);
                                                        fileEntry.setUserName(name);
                                                        fileEntry.setstudentno(Post);
                                                        Search1.addElement(fileEntry);
                                                }
					}
				}//for
				if(Search1.size()!=0)
				{
                        		context.put("Searchtopics",Search1);
					context.put("check","Noblank");
                        		ErrorDumpUtil.ErrorLog("\nListaction"+Search1);
				}
				else
				context.put("check","blank");
			}
		}//try
		catch(Exception e){
                ErrorDumpUtil.ErrorLog("Error in method:doSearch !!"+e);
                data.setMessage("See ExceptionLog !! " );
		}
	}//method close
	
	
 	/**
	* Default action to perform if the specified action
	* cannot be executed.
	*/
	public void doPerform(RunData data,Context context)throws Exception
	{
		String LangFile=data.getUser().getTemp("LangFile").toString();
		ParameterParser pp=data.getParameters();
       		String action = pp.getString("actionName","");
		context.put("actionName",action);
       		if(action.equals("eventSubmit_doCreate_resrepo"))
       			doCreate_resrepo(data,context);
       		else if(action.equals("eventSubmit_doDeleteTopic"))
       			doDeleteTopic(data,context);
       		else if(action.equals("eventSubmit_doJoinUser"))
       			doJoinUser(data,context);
       		else if(action.equals("eventSubmit_doPosttopic"))
       			doPosttopic(data,context);
       		else if(action.equals("eventSubmit_doUnjoinUser"))
       			doUnjoinUser(data,context);
       		else if(action.equals("eventSubmit_doPost"))
       			doPost(data,context);
       		else if(action.equals("eventSubmit_doSearch"))
       			doSearch(data,context);
		
       		else
       	 		data.setMessage(MultilingualUtil.ConvertedString("action_msg",LangFile));	
	}
	//This method for the deleting......
	public  Vector DeleteEntry(String filePath,String xmlfile,String deltype, String TopicName,RunData data)
       	{
                Vector Read=null;
                try
                {
                        XmlWriter xmlWriter=null;
                        String Tname="";
                        int seq=-1;
			TopicMetaDataXmlReader tr=new TopicMetaDataXmlReader(filePath+"/"+xmlfile);
                        Read=tr.getResearchRepositoryDetails();;
                        if(Read != null)
                        {
                                for(int n=0;n<Read.size();n++)
                                {
					if(deltype.equals("grpdel"))
                                        Tname =((FileEntry)Read.elementAt(n)).getTopic();
					if(deltype.equals("unjoin"))
                                        Tname =((FileEntry)Read.elementAt(n)).getName();
                                        if(TopicName.equals(Tname))
                                        {
                                                seq=n;
                                                break;
                                        }
                                }
                        }
                        /**
                        Here we deleting the particular entry from the "xml" file
                        */
                        xmlWriter=TopicMetaDataXmlWriter.Repositorywriterxml(filePath,xmlfile);
                        xmlWriter.removeElement("Repository",seq);
                        xmlWriter.writeXmlFile();
                }//try
		catch(Exception e){
                                   ErrorDumpUtil.ErrorLog("Error in method:DeleteEntry !!"+e);
                                   data.setMessage("See ExceptionLog !! " );
                                }
       	return Read;
       	}//readmethod
	public String GetOwnername(String topicname,RunData data)
	{
		String name="";
		try
		{
			String Tname="";
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
	public static String DeletePostReply(String filepath,String subjectid) throws Exception
        {
		String del="";
                try{
                String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
                BufferedReader br=new BufferedReader(new FileReader (filepath));

                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<"+subjectid+">"))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals("</"+subjectid+">"))
                                {
                                        stopd = i;

                                }
                        i= i + 1;
                        }
                        br.close();
                        FileWriter fw=new FileWriter(filepath);
                        for(int x=0;x<startd;x++)
                        {
                                fw.write(str[x]+"\r\n");
                        }
                        for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\r\n");
                        }
			fw.close();
		}//try
		catch(Exception ex) {
		ErrorDumpUtil.ErrorLog("Error in method DeletePostReply !!"+ex);
                }
                return del;
        }//method
}//class
