package org.iitk.brihaspati.modules.utils;

/*
 * @(#)TopicMetaDataXmlWriter.java
 *
 *  Copyright (c) 2005-2008, 2009 ETRG,IIT Kanpur.
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

import java.util.Date;
import java.io.File;
import java.util.Vector;
import java.io.FileOutputStream;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;

/**
 * This class generate Xml file with attributes and values
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:shaistashekh@hotmail.com">Shaista Bano</a>
 * @modify 20-03-2009
 */

public class TopicMetaDataXmlWriter
{
	/**
	* This method write xml file with tags
	* @param fileName String
	*/
	public static void writeWithRootOnly(String fileName) throws Exception
	{
		FileOutputStream fos=new FileOutputStream(fileName);
		fos.write( ("<Topic>\n<Desc></Desc>\n</Topic>").getBytes() );
		fos.close();
	}
	/**
	* This method write xml file with tags and UserName
	* @param UserName String
	*/

	 public static void WriteWithRootOnly(String UserName) throws Exception
        {
                FileOutputStream fos=new FileOutputStream(UserName);
                fos.write(("<Permission>\n</Permission>").getBytes() );
                fos.close();
        }
	/**
        * This method write xml file with tags
        * @param fileName String
        * @return nothing
        */
        public static void GroupxmlRootOnly(String GroupName) throws Exception
        {
                FileOutputStream fos=new FileOutputStream(GroupName);
                fos.write( ("<groupmgmt>\n<Desc></Desc>\n<activity></activity>\n</groupmgmt>").getBytes() );
                fos.close();
        }
	/**
	* This method write online request for registartion
	* @param fileName String
	*/
	public static void WriteOnlineReqRootOnly(String fileName) throws Exception
        {
                FileOutputStream fos=new FileOutputStream(fileName);
                fos.write(("<Request>\n</Request>").getBytes());
                fos.close();
        }

	/**
        * This method write xml file with tags
        * @param file String
        */

	 public static void writeWithRootOnly1(String file) throws Exception
       {

                FileOutputStream fos=new FileOutputStream(file);
                fos.write( ("<Instructor>\n</Instructor>").getBytes() );
                fos.close();
        }
	/**
        * This method write xml file with tags for sending email info
        * @param file String
        */
	public static void updationRootOnly(String fileName) throws Exception
        {
                FileOutputStream fos=new FileOutputStream(fileName);
                fos.write( ("<Updation>\n</Updation>").getBytes() );
                fos.close();
        }


	/**
        * This method append element in existing xml file
        * @param xmlWriter XmlWriter
        * @param GroupName String
        * @param type String
        * @param studentname String
        * @param CreationDate Integer
        * @param studentid Integer
        * @return nothing
        */
        public static void appendGroupElement(XmlWriter xmlWriter,String GroupName,String type,String CreationDate,String studentname,String studentno)
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","name","","",StringUtil.replaceXmlSpecialCharacters(GroupName));
                ats.addAttribute("","type","","",StringUtil.replaceXmlSpecialCharacters(type));
                ats.addAttribute("","CreationDate","","",StringUtil.replaceXmlSpecialCharacters(CreationDate));
                ats.addAttribute("","studentname","","",StringUtil.replaceXmlSpecialCharacters(studentname));
                ats.addAttribute("","studentno","","",StringUtil.replaceXmlSpecialCharacters(studentno));
                xmlWriter.appendElement("Group",null,ats);
        }


	/**
	* This method append file element in existing xml file
	* @param xmlWriter XmlWriter
	* @param fileName String
	* @param alias String
	* @param publishingDate String
	*/
		
	public static void appendFileElement(XmlWriter xmlWriter,String fileName,String alias,String publishingDate)
	{
		AttributesImpl ats=new AttributesImpl();
		ats.addAttribute("","name","","",StringUtil.replaceXmlSpecialCharacters(fileName));
		ats.addAttribute("","alias","","",StringUtil.replaceXmlSpecialCharacters(alias));
		ats.addAttribute("","publishingDate","","",publishingDate);
		xmlWriter.appendElement("File",null,ats);
	}
	/**
	* This method append user registration information in existing on line user file
	* @param xmlWriter XmlWriter
	* @param uname String
	* @param passwd String
	* @param email String
	* @param gname String
	* @param roleName String
	*/
	public static void appendOnlineUserElement(XmlWriter xmlWriter,String uname,String passwd,String fname,String lname,String orgtn,String email,String gname,String roleName, String registerationDate)
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","uname","","",StringUtil.replaceXmlSpecialCharacters(uname));
                ats.addAttribute("","passwd","","",StringUtil.replaceXmlSpecialCharacters(passwd));
		ats.addAttribute("","fname","","",StringUtil.replaceXmlSpecialCharacters(fname));
		ats.addAttribute("","lname","","",StringUtil.replaceXmlSpecialCharacters(lname));
		ats.addAttribute("","orgtn","","",StringUtil.replaceXmlSpecialCharacters(orgtn));
                ats.addAttribute("","email","","",StringUtil.replaceXmlSpecialCharacters(email));
                ats.addAttribute("","gname","","",StringUtil.replaceXmlSpecialCharacters(gname));
                ats.addAttribute("","roleName","","",StringUtil.replaceXmlSpecialCharacters(roleName));
		ats.addAttribute("","registerationDate","","",StringUtil.replaceXmlSpecialCharacters(registerationDate));
                xmlWriter.appendElement("File",null,ats);

        }

	/**
	* This method append course registration information in existing on line course file
        * @param xmlWriter XmlWriter
        * @param gname String
        * @param cname String
        * @param uname String
        * @param email String
        * @param fname String
        * @param lname String
        */

        public static void appendOnlineCrsElement(XmlWriter xmlWriter,String gname,String cname,String uname,String orgtn,String email,String fname,String lname, String registerationDate )
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","gname","","",StringUtil.replaceXmlSpecialCharacters(gname));
                ats.addAttribute("","cname","","",StringUtil.replaceXmlSpecialCharacters(cname));
                ats.addAttribute("","uname","","",StringUtil.replaceXmlSpecialCharacters(uname));
		ats.addAttribute("","orgtn","","",StringUtil.replaceXmlSpecialCharacters(orgtn));
                ats.addAttribute("","email","","",StringUtil.replaceXmlSpecialCharacters(email));
                ats.addAttribute("","fname","","",StringUtil.replaceXmlSpecialCharacters(fname));
                ats.addAttribute("","lname","","",StringUtil.replaceXmlSpecialCharacters(lname));
		ats.addAttribute("","registerationDate","","",StringUtil.replaceXmlSpecialCharacters(registerationDate));
                xmlWriter.appendElement("File",null,ats);

        }

	/**
	* This method insert file element in existing xml file with sequence number
	* @param xmlWriter XmlWriter
	* @param fileName String
	* @param alias String
	* @param publishingDate String
	* @param seqno Integer
	*/

	public static void insertFileElement(XmlWriter xmlWriter,String fileName,String alias,String publishingDate,int seqno)
	{
		AttributesImpl ats=new AttributesImpl();
		ats.addAttribute("","name","","",StringUtil.replaceXmlSpecialCharacters(fileName));
		ats.addAttribute("","alias","","",StringUtil.replaceXmlSpecialCharacters(alias));
		ats.addAttribute("","publishingDate","","",publishingDate);
		xmlWriter.insertElement("File",null,ats,seqno);
	}
	/**
	* This method append file element in existing xml file with username for permission
	* @param xmlwriter XmlWriter
	* @param TopicName String
	* @param UserName String
	*/
	public static void appendFile(XmlWriter xmlwriter,String TopicName,String UserName,String CourseName,String Role)
        {
                AttributesImpl ats=new AttributesImpl();
              	ats.addAttribute("","topicname","","",StringUtil.replaceXmlSpecialCharacters(TopicName));
                ats.addAttribute("","username","","",UserName);
                ats.addAttribute("","coursename","","",CourseName);
                ats.addAttribute("","role","","",Role);
                xmlwriter.appendElement("Topic",null,ats);
        }

	/**
        * This method append file element in existing xml file
        * @param xmlWriter XmlWriter
        * @param userid String
        * @param emailId String
        * @param status String
        */

        public static void appendUpdationInfoMailElement(XmlWriter xmlWriter,String userid,String emailId,String status)
        {

                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","userid","","",userid);
                ats.addAttribute("","emailId","","",emailId);
                ats.addAttribute("","status","","",status);
                xmlWriter.appendElement("Course",null,ats);
        }
	

	/**
	* This method change value of alias file element in existing xml file 
	* @param xmlWriter XmlWriter
	* @param alias String
	* @param seqno Integer
	*/

	public static void changeFileAlias(XmlWriter xmlWriter,String alias,int seqno) throws Exception
	{
		XmlData xmlData=xmlWriter.getElement("File",seqno);
		Attributes old=xmlData.getAttributes();
		AttributesImpl atsHasAliasEntry , atsNoAliasEntry , ats;
		String attName,attValue;
		int totalAttributes=old.getLength();
		if(totalAttributes==3)
		{
			atsHasAliasEntry=new AttributesImpl(old);
			atsHasAliasEntry.setValue(1,alias);
			ats=atsHasAliasEntry;
		}
		else
		{
			atsNoAliasEntry=new AttributesImpl();
			for(int i=0,j=0;i<3;i++)
			{
			
				if(i!=1)
                        	{
                                	attName=old.getLocalName(j);
                                	attValue=old.getValue(j++);
                        	}
                        	else
                        	{
                                	attName="alias";
                                	attValue=alias;
                        	}
				atsNoAliasEntry.addAttribute("",attName,"","",attValue);
			}
			ats=atsNoAliasEntry;
		}
                xmlWriter.changeAttributes("File",ats,seqno);
	}


	/**
	* This method read existing on line user xml file and write new xml file with old values
	* @param filePath String
	* @param xmlFile String
	* @param indexList Vector
	* @return xmlWriter XmlWriter
	*/

       public static XmlWriter WriteXml_OnlineUser(String filePath1,String xmlFile1,Vector indexList)
        {
                XmlWriter xmlWriter=null;
                Vector v = new Vector();
                File descFile=new File(filePath1+xmlFile1);
                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath1+xmlFile1);
                        v=topicMetaData.getOnlineUserDetails();
			descFile.delete();
                        WriteOnlineReqRootOnly(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath1+xmlFile1);
                        for(int i=0;i<v.size();i++)
                        {
                                if(!indexList.contains(i))
                                {
                                        String uname=((CourseUserDetail)v.get(i)).getLoginName();
                                        String passwd=((CourseUserDetail)v.get(i)).getActive();
                                        String email=((CourseUserDetail)v.get(i)).getEmail();
                                        String gname=((CourseUserDetail)v.get(i)).getGroupName();
                                        String roleName=((CourseUserDetail)v.get(i)).getRoleName();
					String fname=((CourseUserDetail)v.get(i)).getInstructorName();
					String lname=((CourseUserDetail)v.get(i)).getUserName();
					String orgtn=((CourseUserDetail)v.get(i)).getDept();
					String registerationDate=((CourseUserDetail)v.get(i)).getCreateDate();
                                        //appendElement1(xmlWriter,uname,passwd,email,gname,roleName);
                                        appendOnlineUserElement(xmlWriter,uname,passwd,fname,lname,orgtn,email,gname,roleName,registerationDate);
                                }
                        }
                }

                catch(Exception e) { 
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in On line user Method ::"+e);
                        System.out.println("See Exception log  in log file:: ");
		}

                return xmlWriter;
       }


	/**
	* This method read existing on line course xml file and write new xml file with old values
	* @param filePath String
	* @param xmlFile String
	* @param indexList Vector
	* @return xmlWriter XmlWriter
	*/
     public static XmlWriter WriteXml_OnlineCourse(String filePath1,String xmlFile1,Vector indexList)
        {
                XmlWriter xmlWriter=null;
                Vector v = new Vector();
                File descFile=new File(filePath1+xmlFile1);
                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath1+xmlFile1);

                        v=topicMetaData.getOnlineCourseDetails();

                        WriteOnlineReqRootOnly(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath1+xmlFile1);
                        for(int i=0;i<v.size();i++)
                        {
                                if(!indexList.contains(i))
                                {
                                        String gname=((CourseUserDetail)v.get(i)).getGroupName();
                                        String cname=((CourseUserDetail)v.get(i)).getCourseName();
                                        String uname=((CourseUserDetail)v.get(i)).getLoginName();
                                        String email=((CourseUserDetail)v.get(i)).getEmail();
                                        String fname=((CourseUserDetail)v.get(i)).getInstructorName();
                                        String lname=((CourseUserDetail)v.get(i)).getUserName();
					String orgtn=((CourseUserDetail)v.get(i)).getDept();
					String registerationDate=((CourseUserDetail)v.get(i)).getCreateDate();
                                        //appendElementC(xmlWriter,gname,cname,uname,email,fname,lname);
                                        appendOnlineCrsElement(xmlWriter,gname,cname,uname,orgtn,email,fname,lname,registerationDate);
                                }


                        }
                }

                catch(Exception e) { 
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in onlinecourse method ::"+e);
                        System.out.println("See Exception log in log file:: ");
		}

                return xmlWriter;
       }


	/**
	* This method read existing xml file and write new xml file with old values
	* @param filePath String
	* @param topic String
	* @return xmlWriter XmlWriter
	*/
	public static XmlWriter WriteXml_New(String filePath,String topic)
        {
                XmlWriter xmlWriter=null;
		String Xml_file="";
		if(topic.endsWith(".xml"))
			Xml_file=topic;
		else
			Xml_file=topic+"__des.xml";
		
               	File descFile=new File(filePath+"/"+Xml_file);

                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+Xml_file);
                        String topicDesc=topicMetaData.getTopicDescription();
                        Vector v=topicMetaData.getFileDetails();
                        descFile.delete();
                        writeWithRootOnly(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath+"/"+Xml_file);

                        for(int i=0;i<v.size();i++)
                        {
                                String name=((FileEntry)v.get(i)).getName();
                                String alias=((FileEntry)v.get(i)).getAlias();
                                String Pdate=((FileEntry)v.get(i)).getPDate();
                                appendFileElement(xmlWriter,name,alias,Pdate);
                                xmlWriter.changeData("Desc",topicDesc,0);
                        }
                }
                catch(Exception ex){
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 252::"+ex);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");
		}
        return xmlWriter;
        }


	public static XmlWriter WriteXml_New1(String filePath,String xmlFile)
        {
                XmlWriter xmlWriter=null;
                File descFile=new File(filePath+xmlFile);

                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+xmlFile);
                        Vector v=topicMetaData.getDetails();
                        descFile.delete();
                        WriteWithRootOnly(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath+xmlFile);

                        for(int i=0;i<v.size();i++)
                        {
                                String topic=StringUtil.replaceXmlSpecialCharacters(((FileEntry)v.get(i)).getTopic());
                                String username=((FileEntry)v.get(i)).getUserName();
                                String coursename=((FileEntry)v.get(i)).getCourseName();
                                String role=((FileEntry)v.get(i)).getRole();
                                appendFile(xmlWriter,topic,username,coursename,role);

                        }

                }
                catch(Exception e){
			 ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 283::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");

		}

        return xmlWriter;
        }

	public static XmlWriter Groupwriterxml(String filePath,String groupname)
                {
                        XmlWriter xmlWriter=null;
                        String Xml_file="";
                        if(groupname.endsWith(".xml"))
                        Xml_file=groupname;
                        else
                        Xml_file=groupname+"__des.xml";

                        File descFile=new File(filePath+"/"+Xml_file);
                        try{
                                TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+Xml_file);
                                String groupdesc=topicMetaData.getTopicDescription();
                                String activity=topicMetaData.getActivity();
                                Vector v=topicMetaData.getGroupDetails();
                                descFile.delete();
                                GroupxmlRootOnly(descFile.getAbsolutePath());
                                xmlWriter=new XmlWriter(filePath+"/"+Xml_file);
                                for(int i=0;i<v.size();i++)
                                {
                                        String name=((FileEntry)v.get(i)).getName();
                                        String type=((FileEntry)v.get(i)).gettype();
                                        String CreationDate=((FileEntry)v.get(i)).getPDate();
                                        String studentname=((FileEntry)v.get(i)).getUserName();
                                        String studentno=((FileEntry)v.get(i)).getstudentno();
                                        appendGroupElement(xmlWriter,name,type,CreationDate,studentname,studentno);
                                        xmlWriter.changeData("Desc",groupdesc,0);
                                        xmlWriter.changeData("activity",activity,0);
                        }

                }
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 323::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");
		}

              return xmlWriter;
        }

	 public static void appendQuiz(XmlWriter xmlWriter,String Quizname,String noofquestion,String totalmarks,String marksofquestion,String totalquestion,String Typeofquestion,String passmarks,String questionid,String marks )
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","optionA","","",StringUtil.replaceXmlSpecialCharacters(Quizname));
                ats.addAttribute("","optionB","","",StringUtil.replaceXmlSpecialCharacters(noofquestion));
                ats.addAttribute("","optionC","","",StringUtil.replaceXmlSpecialCharacters(totalmarks));
                ats.addAttribute("","optionD","","",StringUtil.replaceXmlSpecialCharacters(marksofquestion));
                ats.addAttribute("","instructorans","","",StringUtil.replaceXmlSpecialCharacters(totalquestion));
                ats.addAttribute("","Typeofquestion","","",StringUtil.replaceXmlSpecialCharacters(Typeofquestion));
                ats.addAttribute("","question","","",StringUtil.replaceXmlSpecialCharacters(passmarks));
                ats.addAttribute("","questionid","","",StringUtil.replaceXmlSpecialCharacters(questionid));
                ats.addAttribute("","marksperqustion","","",StringUtil.replaceXmlSpecialCharacters(marks));
                xmlWriter.appendElement("File",null,ats);

	}
	public static void appendUpdationMailElement(XmlWriter xmlWriter,String fileName,String username,String grade,String Duedate)
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","fileName","","",StringUtil.replaceXmlSpecialCharacters(fileName));
                ats.addAttribute("","username","","",StringUtil.replaceXmlSpecialCharacters(username));
                ats.addAttribute("","grade","","",StringUtil.replaceXmlSpecialCharacters(grade));
                ats.addAttribute("","Duedate","","",StringUtil.replaceXmlSpecialCharacters(Duedate));
                xmlWriter.appendElement("File",null,ats);
        }

	public static XmlWriter writeXml_Assignment(String filePath1,String xmlFile1,int kk)
        {
                XmlWriter xmlWriter=null;
                Vector v= new Vector();
                File descFile=new File(filePath1+xmlFile1);
                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath1+xmlFile1);
                        if(xmlFile1.equals("/__file.xml"))
                                v=topicMetaData.getAssignmentDetails();

                        descFile.delete();
                        if(xmlFile1.equals("/__file.xml"))
                                writeWithRootOnly1(descFile.getAbsolutePath());
                        else
                                writeWithRootOnly1(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath1+xmlFile1);
                        for(int i=0;i<v.size();i++)
                        {
                                if(xmlFile1.equals("/__file.xml"))
                                {
                                        String fileName=((FileEntry)v.get(i)).getfileName();
                                        String username=((FileEntry)v.get(i)).getUserName();
                                        String grade=((FileEntry)v.get(i)).getGrade();
                                        String Duedate=((FileEntry)v.get(i)).getDuedate();

                                        appendUpdationMailElement(xmlWriter,fileName,username,grade,Duedate);
                                }
                        }
                }
                catch(Exception e){}
                return xmlWriter;
       }


	 //public static void appendGrade(XmlWriter xmlWriter,String userid,String username,String grade,String feedback)
	 public static void appendGrade(XmlWriter xmlWriter,String username,String grade,String feedback)
        {
                        AttributesImpl ats=new AttributesImpl();
                        ats.addAttribute("","username","","",StringUtil.replaceXmlSpecialCharacters(username));
                        ats.addAttribute("","grade","","",StringUtil.replaceXmlSpecialCharacters(grade));
                        ats.addAttribute("","feedback","","",StringUtil.replaceXmlSpecialCharacters(feedback));
                        xmlWriter.appendElement("File",null,ats);

        }

	public static XmlWriter WriteXml_New4(String filePath1,String xmlFile1,Vector arv)
        {

                XmlWriter xmlWriter=null;
                Vector v= new Vector();
                File descFile=new File(filePath1+xmlFile1);
                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath1+xmlFile1);
                        v=topicMetaData.getAssignmentDetails1();
                        writeWithRootOnly1(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath1+xmlFile1);
                        for(int i=0;i<v.size();i++)
                        {
				/** arv.contains(i) return boolean value **/
                                if(!(arv.contains(i)))
                                {

                                        String username=((FileEntry)v.get(i)).getUserName();
                                        String grade=((FileEntry)v.get(i)).getGrade();
                                        String feedback=((FileEntry)v.get(i)).getfeedback();
                                        appendGrade(xmlWriter,username,grade,feedback);
                                }
                        }
                }
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 384::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");

		}
                return xmlWriter;
       }

	public static XmlWriter WriteXml_New6(String filePath,String xmlFile,int kk)
        {
                XmlWriter xmlWriter=null;
                Vector v= new Vector();
                File descFile=new File(filePath+xmlFile);
                try{
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+xmlFile);
                        //if(xmlFile.equals("/Quizid.xml"))
                                v=topicMetaData.getQuizDetails();
                        //descFile.delete();
                        //if(xmlFile.equals("/Quizid.xml"))
                                writeWithRootOnly1(descFile.getAbsolutePath());
                        /*else
                                writeWithRootOnly1(descFile.getAbsolutePath());
                        */
                        xmlWriter=new XmlWriter(filePath+xmlFile);
                        for(int i=0;i<v.size();i++)
                        {
                                if(i!=kk)
                                {
                                        String Quizname=((FileEntry)v.get(i)).getoptionA();
                                        String noofquestion=((FileEntry)v.get(i)).getoptionB();
                                        String totalmarks=((FileEntry)v.get(i)).getoptionC();
                                        String marksofquestion=((FileEntry)v.get(i)).getoptionD();
                                        String totalquestion=((FileEntry)v.get(i)).getinstructorans();
                                        String Typeofquestion=((FileEntry)v.get(i)).getTypeofquestion();
                                        String passmarks=((FileEntry)v.get(i)).getquestion();
                                        String questionid=((FileEntry)v.get(i)).getquestionid();
                                        String marks=((FileEntry)v.get(i)).getmarksperqustion();
                                        appendQuiz(xmlWriter,Quizname,noofquestion,totalmarks,marksofquestion,totalquestion,Typeofquestion,passmarks,questionid,marks);
                                }
                        }
                }
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 425::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");
		}
                return xmlWriter;
        }

	//----------------------------------------ResearchRepository---------------------------------------------//
	public static void appendResearchRepository(XmlWriter xmlWriter,String RepositoryName,String username,String joineduser,String NumberofPost,String CreationDate)
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","name","","",RepositoryName);
                ats.addAttribute("","username","","",username);
                ats.addAttribute("","joineduser","","",joineduser);
                ats.addAttribute("","NumberofPost","","",NumberofPost);
                ats.addAttribute("","CreationDate","","",CreationDate);
                xmlWriter.appendElement("Repository",null,ats);
        }
   	public static XmlWriter Repositorywriterxml(String filePath,String xmlfile)
        {
        	XmlWriter xmlWriter=null;
                File descFile=new File(filePath+"/"+xmlfile);
                try{
			 TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+xmlfile);
                         String Repodesc=topicMetaData.getTopicDescription();
                         Vector v=topicMetaData.getResearchRepositoryDetails();
                         descFile.delete();
                         writeWithRootOnly(descFile.getAbsolutePath());
                         xmlWriter=new XmlWriter(filePath+"/"+xmlfile);
                         for(int i=0;i<v.size();i++)
                         {
                         	String name=((FileEntry)v.get(i)).getTopic();
                                String username=((FileEntry)v.get(i)).getUserName();
                                String NumberofPost=((FileEntry)v.get(i)).getstudentno();
                                String joineduser=((FileEntry)v.get(i)).getName();
                                String CreationDate=((FileEntry)v.get(i)).getPDate();
                                appendResearchRepository(xmlWriter,name,username,joineduser,NumberofPost,CreationDate);
                                xmlWriter.changeData("Desc",Repodesc,0);
                        }

                }
                catch(Exception e){
                        ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 323::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");
                }

              return xmlWriter;
        }

	public static XmlWriter WriteXml_UpdationInfo(String filePath,String xmlFile)
        {
                XmlWriter xmlWriter=null;
                File descFile=new File(filePath+xmlFile);

                try{
                        Vector v= new Vector();
                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+xmlFile);
                        if(xmlFile.equals("/Update__des.xml"))
                                v=topicMetaData.getUpdationDetails();
                        else
                                v=topicMetaData.getDetails();
                        descFile.delete();
                        if(xmlFile.equals("/Update__des.xml"))
                                updationRootOnly(descFile.getAbsolutePath());
                        else
                                WriteWithRootOnly(descFile.getAbsolutePath());
                        xmlWriter=new XmlWriter(filePath+xmlFile);
                        for(int i=0;i<v.size();i++)
                        {
                                if(xmlFile.equals("/Update__des.xml"))
                                {
                                        String userid=((FileEntry)v.get(i)).getuserid();
                                        String emailId=((FileEntry)v.get(i)).getemailId();
                                        String status=((FileEntry)v.get(i)).getstatus();
                                        appendUpdationInfoMailElement(xmlWriter,userid,emailId,status);


                                }
			}
                } catch(Exception e){
			ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil Updation Info Method ::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");

		}
                return xmlWriter;
        }

//-----------------------------------------------------------Bookmarks----------------------------//	
	public static void appendBookmarks(XmlWriter xmlWriter,String Bookmarkname,String urllocation,String dirname,String type,String comment)
        {
                AttributesImpl ats=new AttributesImpl();
                ats.addAttribute("","name","","",Bookmarkname);
                ats.addAttribute("","url","","",urllocation);
                ats.addAttribute("","dirname","","",dirname);
                ats.addAttribute("","type","","",type);
                ats.addAttribute("","comment","","",comment);
                xmlWriter.appendElement("Bookmarks",null,ats);
        }
   	public static XmlWriter Bookmarksxml(String filePath,String xmlfile)
        {
        	XmlWriter xmlWriter=null;
                File descFile=new File(filePath+"/"+xmlfile);
                try{
			 TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+xmlfile);
                         String Bookmarksdesc=topicMetaData.getTopicDescription();
                         Vector v=topicMetaData.getBookmarksDetails();
                         descFile.delete();
                         writeWithRootOnly(descFile.getAbsolutePath());
                         xmlWriter=new XmlWriter(filePath+"/"+xmlfile);
                         for(int i=0;i<v.size();i++)
                         {
                         	String name=((FileEntry)v.get(i)).getTopic();
                                String urllocation=((FileEntry)v.get(i)).getUrl();
                                String dirname=((FileEntry)v.get(i)).getName();
                                String type=((FileEntry)v.get(i)).gettype();
                                String comment=((FileEntry)v.get(i)).getfeedback();
                                appendBookmarks(xmlWriter,name,urllocation,dirname,type,comment);
                                xmlWriter.changeData("Desc",Bookmarksdesc,0);
                        }

                }
                catch(Exception e){
                        ErrorDumpUtil.ErrorLog("The exception in xmlwriterutil in line 323::"+e);
                        System.out.println("See Exception message in ExceptionLog.txt file:: ");
                }

              return xmlWriter;
        }

//-----------------------------------------------------------Bookmarks----------------------------//
}
