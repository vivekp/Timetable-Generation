package org.iitk.brihaspati.modules.actions;

/*
 * @(#) Bookmarks.java
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
import java.util.Vector;

import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.modules.screens.VelocityScreen;

import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;


/**This class contain the code Create, Delete, Move,Save
* @author: <a href="mailto:seema_020504@yahoo.com">seema pal</a>
* @author: <a href="mailto:kshuklak@rediffmail.com">kishore kumar shukla</a>
*/


public class Bookmarks extends SecureAction
{
	/**
         * Place all the data object in the context for use in the template.
         * @param data RunData instance
         * @param context Context instance
         * @exception Exception, a generic exception
         */
	
	/** Get path for Bookmarks.*/

        String BookmarksPath=TurbineServlet.getRealPath("/Bookmarks");
	VelocityScreen vs=new VelocityScreen();
	String LangFile ="";
	/**This method is for the cancelling process.*/

	public void doCancel(RunData data,Context context)
        {
                data.setScreenTemplate("call,Bookmarks,AddBookmarks.vm");
        }
	/**This method is for the creating newfolder.*/

	public void doCreateFolder(RunData data,Context context)
	{       
		try{
        		ParameterParser pp=data.getParameters();
			LangFile=data.getUser().getTemp("LangFile").toString();
			
			String userName=data.getUser().getName();
			//Get the dirname by Using parameter parser.//
			String dirname=pp.getString("dirname","");
			/**
			*Getting the path for making Directory
			*And also checking the existing directory
			*/
                       	File f=new File(BookmarksPath+"/"+userName+"/"+dirname);
			if(!f.exists())
			{
                        	f.mkdirs();
                       		String filepath=(BookmarksPath+"/"+userName+"/"+dirname);
				File topicdesxml =new File(filepath+"/"+dirname+"_des.xml");
                        	if(!topicdesxml.exists())
                        		TopicMetaDataXmlWriter.writeWithRootOnly(topicdesxml.getAbsolutePath());
                        	//data.setMessage("new folder  created successfully !!");
				data.setMessage(MultilingualUtil.ConvertedString("brih_new",LangFile)+" "+MultilingualUtil.ConvertedString("brih_folder",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction5",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile));
			}
			else
                        //data.setMessage("This folder is already exists !!");
				data.setMessage(MultilingualUtil.ConvertedString("brih_This",LangFile)+" "+MultilingualUtil.ConvertedString("brih_folder",LangFile)+" "+MultilingualUtil.ConvertedString("brih_is",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction6",LangFile)+" "+"!!");
		}
		catch(Exception e){
				 	ErrorDumpUtil.ErrorLog("The exception in doCreateFolder "+e);
                        		System.out.println("See Exception message in ExceptionLog.txt file:: ");
			}
	}//close method

	/**This method is for the Save Bookmark.*/

	public void doSave(RunData data,Context context)
	{       
		try{
        		ParameterParser pp=data.getParameters();
			LangFile=data.getUser().getTemp("LangFile").toString();
			/**
                        * Get username for the user currently logged in
                        */
			String userName=data.getUser().getName();
			String group=(String)data.getUser().getTemp("course_id");
			String topicdesc=pp.getString("description","");
			String topicname=pp.getString("name","");
			String comment=pp.getString("comment","");
			String urllocation=pp.getString("location","");
			String dirname=pp.getString("valfolder","");
			String type=pp.getString("type","");
			ErrorDumpUtil.ErrorLog("\ndirname"+dirname);
			if(StringUtil.checkString(topicname) != -1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                                return;
                        }
			context.put("topics",topicname);
                       	File f=new File(BookmarksPath+"/"+userName);
			if(!f.exists())
                        	f.mkdirs();

			/**
			*Creating the blank xml for maintaining the record
			*@see TopicMetaDataXmlWriter in Util.
			*/
			String filepath=(BookmarksPath+"/"+userName);
			File Bookmarkslistxml= new File(filepath+"/BookmarksList.xml");
                        if(!Bookmarkslistxml.exists())
                                TopicMetaDataXmlWriter.writeWithRootOnly(Bookmarkslistxml.getAbsolutePath());
			XmlWriter xmlWriter=null;
                        boolean found=false;
                        String xmlfile="/BookmarksList.xml";
                        if(!Bookmarkslistxml.exists())
                        {
                                xmlWriter=new XmlWriter(filepath+"/"+xmlfile);
                        }
                        /**
                        *Checking for  the existing Bookmark
                        *@see TopicMetaDataXmlReader in Util.
                        */
                        else
                        {
                                TopicMetaDataXmlReader topicmetadata=null;
                                topicmetadata=new TopicMetaDataXmlReader(filepath+"/"+xmlfile);
                                Vector collect=topicmetadata.getBookmarksDetails();
                                if(collect!=null)
                                {
                                        for(int i=0;i<collect.size();i++)
					{//for
                                        	String Bookmarkname =((FileEntry) collect.elementAt(i)).getTopic();
                                        	String type1 =((FileEntry) collect.elementAt(i)).gettype();
						String urllocation1 =((FileEntry) collect.elementAt(i)).getUrl();
                                                //if(topicname.equals(Bookmarkname)&&(type.equals(type1))
                                                if((topicname.equals(Bookmarkname))&&(urllocation1.equals(urllocation)))
                                                        found=true;
                                                	//data.setMessage("This Bookmark is already exists !!");
				data.setMessage(MultilingualUtil.ConvertedString("brih_This",LangFile)+" "+MultilingualUtil.ConvertedString("brih_Bookmark",LangFile)+" "+MultilingualUtil.ConvertedString("brih_is",LangFile)+" "+MultilingualUtil.ConvertedString("Wikiaction6",LangFile)+" "+"!!");
                                        }//for
                                }//if
                        }//else

                        /**
                        *Appending the entry in the xml File
                        *@see TopicMetaDataXmlWriter in Util.
                        */
                        if(found==false)
                        {
                                xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(filepath,xmlfile);
				if((type.equals("Course"))||(type.equals("Course_file")))
				dirname=group;
                                TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,topicname,urllocation,dirname,type,comment);
                                xmlWriter.writeXmlFile();
				if((!dirname.equals(""))&&(type.equals("general")))
				{
                       			String filepath1=(filepath+"/"+dirname);
					File topicdesxml =new File(filepath1+"/"+dirname+"_des.xml");
                        		if(!topicdesxml.exists())
                        		{
                        			xmlWriter=new XmlWriter(filepath1+"/"+dirname+"_des.xml");
                        		}//if
                        		else
                        			xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(filepath1,dirname+"_des.xml");
                                		TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,topicname,urllocation,dirname,type,comment);
                                		xmlWriter.changeData("Desc",topicdesc,0);
                                		xmlWriter.writeXmlFile();
				}//
			data.setMessage(MultilingualUtil.ConvertedString("brih_Bookmark",LangFile)+" "+MultilingualUtil.ConvertedString("brih_Added",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile));
			}
			//data.setMessage("Bookmark Add Successfully !!");
		}//try
		catch(Exception e){
				 	ErrorDumpUtil.ErrorLog("The exception in doCreate_resrepo "+e);
                        		System.out.println("See Exception message in ExceptionLog.txt file:: ");
			}
	}//dosave

	/** This method is for the Bookmarks Deletion .*/

        public void doDeleteBookmark(RunData data,Context context)
        {
                try
                {
                        ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();

                        // Get username for the user currently logged in

                        User user = data.getUser();
			String userName=data.getUser().getName();

                        /** Get the Bookmarks for the deletion.*/
			String btname=pp.getString("btname","");
                        /** Get the path of the Bookmarks and xml file.*/
                        String Bookpath=BookmarksPath+"/"+userName;

                        String xmlfile="BookmarksList.xml";
                        Vector str=new Vector();
                        /**
                        * Delete the Entry  and geting Bookmark  for deleting
                        *Reading  xml file by TopicMetaDataXmlReaderUtil.
                        */
			String Bookmarkname="",dirname="",path="",type="";
			TopicMetaDataXmlReader topicmetadata=null;
                        topicmetadata=new TopicMetaDataXmlReader(Bookpath+"/"+xmlfile);
                        Vector collect=topicmetadata.getBookmarksDetails();
                        if(collect!=null)
                        {
                        	for(int i=0;i<collect.size();i++)
                                {//for
					Bookmarkname =((FileEntry) collect.elementAt(i)).getTopic();
                                	dirname =((FileEntry) collect.elementAt(i)).getName();
                                	type =((FileEntry) collect.elementAt(i)).gettype();
                                        if((btname.equals(Bookmarkname))&&(!dirname.equals(""))&&(type.equals("general")))
					{
						path=Bookpath+"/"+dirname;
						topicmetadata=new TopicMetaDataXmlReader(path+"/"+dirname+"_des.xml");
						Vector collect1=topicmetadata.getBookmarksDetails();
                                		for(int k=0;k<collect1.size();k++)
                                		{
                                        		Bookmarkname =((FileEntry) collect1.elementAt(k)).getTopic();
                                        		if(btname.equals(Bookmarkname))
                                        		{
								Vector str2=DeleteEntry(path,dirname+"_des.xml",btname,data);
							}
						}
					}
				}
			}
                        str=DeleteEntry(Bookpath,xmlfile,btname,data);
                        //data.setMessage("Bookmark delete successfully !!");
			data.setMessage(MultilingualUtil.ConvertedString("brih_Bookmark",LangFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangFile));

                  }//try
                  catch(Exception e){
                                        ErrorDumpUtil.ErrorLog("Error in method:doDelete !!"+e);
                                        data.setMessage("See ExceptionLog !! " );
                                        }
         }//methodDeleteBookmark

	/** This method is for the Bookmarks Rename .*/

        public void doRename(RunData data,Context context)
        {
                try
                {
                        ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();

			User user = data.getUser();
                        String userName=data.getUser().getName();


                        String rename=pp.getString("bname","");
                        String name=pp.getString("btname","");
			String stat=pp.getString("stat","");
			/** Getting the Bookmarks for the Rename.*/
			String xmlfile="BookmarksList.xml",Bookmarkname="",urllocation="",dirname="",comment="",dirname1="",type="" ;
			/**
			*Reading xml file by TopicMetaDataXmlReader
			*check the name for renam.
			*/
			TopicMetaDataXmlReader topicmetadata=null;
			String path=BookmarksPath+"/"+userName;
			XmlWriter xmlWriter=null;
                       	topicmetadata=new TopicMetaDataXmlReader(path+"/"+xmlfile);
                       	Vector collect=topicmetadata.getBookmarksDetails();
                       	if(collect!=null)
                       	{
                       		for(int i=0;i<collect.size();i++)
                               	{//for
                               		Bookmarkname =((FileEntry) collect.elementAt(i)).getTopic();
                               		urllocation =((FileEntry) collect.elementAt(i)).getUrl();
                               		dirname =((FileEntry) collect.elementAt(i)).getName();
                               		type=((FileEntry) collect.elementAt(i)).gettype();
                               		comment =((FileEntry) collect.elementAt(i)).getfeedback();
                                       	if(name.equals(Bookmarkname))
					{
						dirname1=dirname;
						xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(path,xmlfile);
                                		TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,rename,urllocation,dirname,type,comment);
                                		xmlWriter.writeXmlFile();
						Vector str=DeleteEntry(path,xmlfile,name,data);
					}
				}//for
				/**
				*Reading xml file by TopicMetaDataXmlReader
				*Check the name exists in the related folder.
				*/
				String path2=path+"/"+dirname1;
                               	topicmetadata=new TopicMetaDataXmlReader(path2+"/"+dirname1+"_des.xml");
                               	Vector collect1=topicmetadata.getBookmarksDetails();
                               	for(int k=0;k<collect1.size();k++)
				{
                        		//ErrorDumpUtil.ErrorLog("\nRename"+name+"\npath2"+path2+"\nrename"+rename);
                                	Bookmarkname =((FileEntry) collect1.elementAt(k)).getTopic();
                                	if(name.equals(Bookmarkname))
                                	{
                                       		xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(path2,dirname1+"_des.xml");
                                       		TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,rename,urllocation,dirname,type,comment);
                                       		xmlWriter.writeXmlFile();
						Vector str=DeleteEntry(path2,dirname1+"_des.xml",name,data);
                               		}
				}//for
                    	}//if
			//data.setMessage("Rename successfully. !!");
			data.setMessage(MultilingualUtil.ConvertedString("brih_Rename",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile)+" "+"!!");
                }//try
                catch(Exception e){
                                        ErrorDumpUtil.ErrorLog("Error in method:doRename !!"+e);
                                        data.setMessage("See ExceptionLog !! " );
                                  }
	}//method

	/** This method is for the Bookmarks Move .*/

        public void doMove(RunData data,Context context)
        {
                try
                {
                        ParameterParser pp=data.getParameters();
                        LangFile=data.getUser().getTemp("LangFile").toString();

			User user = data.getUser();
                        String userName=data.getUser().getName();
			/**Getting the value for moving. */
                        String Redir=pp.getString("dirvalue");
			String move=pp.getString("stat","");
			String topicdir=pp.getString("btname","");
			String xmlfile="BookmarksList.xml",Bookmarkname="",urllocation="",dirname="",comment="",dirname1="",path2="",type="";
			/**
			*Reading xml file by TopicMetaDataXmlReader
			*Check the name exists .
			*/
                        String path=BookmarksPath+"/"+userName;
			TopicMetaDataXmlReader topicmetadata=null;
                        XmlWriter xmlWriter=null;
                        topicmetadata=new TopicMetaDataXmlReader(path+"/"+xmlfile);
                        Vector collect=topicmetadata.getBookmarksDetails();
                        if(collect!=null)
                        {
                        	for(int i=0;i<collect.size();i++)
                                {//for
                                	Bookmarkname =((FileEntry) collect.elementAt(i)).getTopic();
                                	urllocation =((FileEntry) collect.elementAt(i)).getUrl();
                                        dirname =((FileEntry) collect.elementAt(i)).getName();
					type=((FileEntry) collect.elementAt(i)).gettype();
                                        comment =((FileEntry) collect.elementAt(i)).getfeedback();
                                        if(Bookmarkname.equals(topicdir))
                                        {
						if(!dirname.equals(""))
							dirname1=dirname;
						ErrorDumpUtil.ErrorLog("\ntopicdir"+topicdir+"\ndirname1"+dirname1);
                                		xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(path,xmlfile);
                                		TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,Bookmarkname,urllocation,Redir,type,comment);
                                		xmlWriter.writeXmlFile();
                                		Vector str=DeleteEntry(path,xmlfile,topicdir,data);
                                        }
				}//for
				/**
				*Reading xml file by TpopicMetaDataXmlReader
				*Check the name exists in the related folder.
				*/
				if(!dirname1.equals(""))
				{
					path2=path+"/"+dirname1;
					ErrorDumpUtil.ErrorLog("\npath2"+path2);
                                	topicmetadata=new TopicMetaDataXmlReader(path2+"/"+dirname1+"_des.xml");
                                	Vector collect2=topicmetadata.getBookmarksDetails();
                        		if(collect!=null)
					{
                                		for(int j=0;j<collect2.size();j++)
                                		{
                                        		Bookmarkname =((FileEntry) collect2.elementAt(j)).getTopic();
                                        		if(topicdir.equals(Bookmarkname))
                                        		{
                                                		Vector str2=DeleteEntry(path2,dirname1+"_des.xml",topicdir,data);
                                        		}
                                		}//for
					}
				}
				path2=path+"/"+Redir;
                                xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(path2,Redir+"_des.xml");
				TopicMetaDataXmlWriter.appendBookmarks(xmlWriter,topicdir,urllocation,Redir,type,comment);
                               	xmlWriter.writeXmlFile();
                        }//if
                        //data.setMessage("Move successfully. !!");
			data.setMessage(MultilingualUtil.ConvertedString("brih_Move",LangFile)+" "+MultilingualUtil.ConvertedString("brih_successfully",LangFile));
		}//try
                catch(Exception e){
                                        ErrorDumpUtil.ErrorLog("Error in method:domove !!"+e);
                                        data.setMessage("See ExceptionLog !! " );
                                  }
	}//method

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
		if(action.equals("eventSubmit_doCreateFolder"))
                        doCreateFolder(data,context);
       		else if(action.equals("eventSubmit_doSave"))
       			doSave(data,context);
		else if(action.equals("eventSubmit_doDeleteBookmark"))
                        doDeleteBookmark(data,context);
		else if(action.equals("eventSubmit_doRename"))
                        doRename(data,context);
		else if(action.equals("eventSubmit_doMove"))
                        doMove(data,context);
       		else
       	 		data.setMessage(MultilingualUtil.ConvertedString("action_msg",LangFile));	
	}
	public  Vector DeleteEntry(String filePath,String xmlfile,String bname,RunData data)
        {
                Vector Read=null;
                try
                {
                        XmlWriter xmlWriter=null;
                        String name="";
                        int seq=-1;
                        TopicMetaDataXmlReader tr =new TopicMetaDataXmlReader(filePath+"/"+xmlfile);
                        Read=tr.getBookmarksDetails();
                        if(Read != null)
                        {
                                for(int n=0;n<Read.size();n++)
                                {
                                        name =((FileEntry)Read.elementAt(n)).getTopic();
                                        if(bname.equals(name))
                                        {
                                                seq=n;
                                                break;
                                        }
                                }
                        }
                        /**
                        Here we deleting the particular entry from the "xml" file
                        */
                        xmlWriter=TopicMetaDataXmlWriter.Bookmarksxml(filePath,xmlfile);
                        xmlWriter.removeElement("Bookmarks",seq);
                        xmlWriter.writeXmlFile();
                }//try
                catch(Exception e){
                                   ErrorDumpUtil.ErrorLog("Error in method:DeleteEntry !!"+e);
                                   data.setMessage("See ExceptionLog !! " );
                                }
	return Read;
	}//methodDeleteEntry
}//class
