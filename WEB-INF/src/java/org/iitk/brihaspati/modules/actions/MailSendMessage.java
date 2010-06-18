package org.iitk.brihaspati.modules.actions;

/* @(#)MailSendMessage.java
 *
 * Copyright (c) 2005-2007 ETRG,IIT Kanpur.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 * 
 * Redistribution in binary form must reproducuce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 * 
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Contributors: Members of ETRG, I.I.T. Kanpur
 */ 

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileOutputStream;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.TurbineSecurity;

import com.workingdogs.village.Record;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.utils.RomanToUnicode;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.om.MailSendPeer;
import org.iitk.brihaspati.om.MailReceivePeer;

/** This class contains code of Sending Message to the any user 
 *  with or without attachments and Delete messages from mail a/c
 *
 *  @author<a href="mailto:chitvesh@yahoo.com">Chitvesh Dutta</a>
 *  @author<a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>

 */
public class MailSendMessage extends SecureAction
{
	/**
     	* In this method, We send message or Local_mail for users(Local)
     	* @param data RunData
     	* @param context Context
     	* @exception Exception a generic exception
     	*/ 
    	public void doSend(RunData data, Context context)
    	{
		try
		{
			String cerMsg = "", message = "";
			User user = data.getUser();
			ParameterParser pp=data.getParameters();
			String AddList=new String();
                	String dir = (String)user.getTemp("course_id");
			String LangFile=user.getTemp("LangFile").toString();
		  	String RepMsg_id=pp.getString("RepMsg_Id","-1");
	  		String Add_FstList=pp.getString("addressNames","");
	  		String Add_SndList=pp.getString("addressNames1","");
			String mode =pp.getString("mode1","");
			String name="",path="",gpath="";
			int msgid = 0,Status=0,receiver_id=0,readf=0,index=0;
			RomanToUnicode romanToUni = new RomanToUnicode();
			cerMsg = pp.getString("hexaStr");
			ErrorDumpUtil.ErrorLog("cerMsg.in action..."+cerMsg);
			String lang = (String)user.getTemp("lang");

			//if(!(user.getTemp("lang").equals("english")))
			 if( lang.equals("hindi") || lang.equals("marathi"))
			{
				
				message= romanToUni.getstrUnicode(cerMsg,lang);
			}
			else
				message = pp.getString("hexaStr");

			if(!Add_SndList.equals(""))
			{
				AddList=Add_FstList+","+Add_SndList;
			}
			else
			{
				AddList=Add_FstList;
				
			}
                	if(!AddList.equals(""))
			{ //outer 'if'
				Criteria crit=new Criteria();
				if(!RepMsg_id.equals("-1"))
				{
					/**
					* Update Reply Status in MAIL_SEND table
					*/	
					crit=new Criteria();
					crit.add(MailSendPeer.MAIL_ID,RepMsg_id);
					crit.add(MailSendPeer.REPLY_STATUS,1);
					MailSendPeer.doUpdate(crit);
				}
				/**
				* inserting new mail message in the MAIL_SEND table.
				* escape the special character in subject
				*/
				String subject = pp.getString("subject","");
                                String sender_name = data.getUser().getName();
                                int sender_id = UserUtil.getUID(sender_name);
			
                                subject =StringUtil.replaceXmlSpecialCharacters(subject);

                                Date d=new Date();
                                crit=new Criteria();
                                crit.add(MailSendPeer.SENDER_ID,sender_id);
                                crit.add(MailSendPeer.MAIL_SUBJECT,subject);
                                crit.add(MailSendPeer.REPLY_STATUS,0);
                                crit.add(MailSendPeer.POST_TIME,d);
                                MailSendPeer.doInsert(crit);
				StringTokenizer st=new StringTokenizer(AddList,", ");
                      		for(int i=0;st.hasMoreTokens();i++)
				{ //first 'for' loop
					String toAddress=st.nextToken();
					/**
			 		* From here starts the code do store message
			 		* in a single txt file.
			 		*/
					if(mode.equals("grpmgmt"))
					{
						gpath = data.getServletContext().getRealPath("/Courses"+"/"+dir)+"/GroupManagement";
						TopicMetaDataXmlReader topicmetadata = new TopicMetaDataXmlReader(gpath+"/"+toAddress+"__des.xml");
                	               		Vector list=topicmetadata.getGroupDetails();
                               			if(list!=null)
                               			{
                               				for(int k=0;k<list.size();k++)
                                        		{
                                               			name=((FileEntry)list.elementAt(k)).getUserName();
								path = data.getServletContext().getRealPath("/UserArea")+"/"+name;
								doWriteMail(LangFile, path, msgid, message, name, data,readf, receiver_id,context,Status,mode,"");
							}
						}
					}
					else
					{
						path = data.getServletContext().getRealPath("/UserArea")+"/"+toAddress;
						doWriteMail(LangFile, path, msgid, message, toAddress,data,readf,receiver_id,context,Status,mode,cerMsg);
					}
					String msg1=MultilingualUtil.ConvertedString("mail_msg2",LangFile);
					data.setMessage(msg1);
				}//end first 'for' loop
			}//close outer 'if'
			if(Status==1)
			{
				String msg4=MultilingualUtil.ConvertedString("mail_msg2",LangFile);
				String msg5=MultilingualUtil.ConvertedString("attach",LangFile);
				if(LangFile.endsWith("hi.properties"))
				data.setMessage(msg5+" "+msg4);
				else
				data.setMessage(msg4+" "+msg5);
				Status=0;
			}
 		}//close outer 'try'
		catch(Exception e)
		{
			data.setMessage("Some Error Occured in Sending Mail !!!!");
		}
	}//method
	/**
	* In this method, We deleted message/s or Local_mail for users(Local)
     	* @param data RunData
     	* @param context Context
     	* @exception Exception a generic exception
     	*/ 
	public void doDeleteMessage(RunData data,Context context)
	{
		User user = data.getUser();
		String user_named = user.getName();
		String LangFile=user.getTemp("LangFile").toString();
		ParameterParser pp=data.getParameters();
		String mid_delete = pp.getString("deleteFileNames","");
		if(!mid_delete.equals(""))
                { //outer 'if'
                        StringTokenizer st=new StringTokenizer(mid_delete,"^");
                        for(int j=0;st.hasMoreTokens();j++)
                        { //first 'for' loop
                                String msg_idd=st.nextToken();
			try{
			/**
			 * deleting the particular message from the table.
			 */
				Criteria crit=new Criteria();
				crit.add(MailSendPeer.MAIL_ID,msg_idd);
				MailSendPeer.doDelete(crit);

				crit=new Criteria();
				crit.add(MailReceivePeer.MAIL_ID,msg_idd);
				MailReceivePeer.doDelete(crit);
			}
			catch(Exception e)
			{
				data.setMessage("The Error in delete message from table !! "+e); 
			}
			try{
			String path= data.getServletContext().getRealPath("/UserArea")+"/"+user_named+"/Msg.txt";
			String path1= data.getServletContext().getRealPath("/UserArea")+"/"+user_named+"/typedCharMsg.txt";
			CommonUtility.UpdateTxtFile(path,msg_idd,"",false);
			CommonUtility.UpdateTxtFile(path1,msg_idd,"",false);
                        	/**
                         	* deleting attachment if any with the message.
                         	*/
                        	String Path = data.getServletContext().getRealPath("/UserArea")+"/"+user_named+"/Attachment"+"/"+msg_idd
;
                        	File f1 = new File(Path);
                        	if(f1.exists() && f1.isDirectory())
                        	{
                                	String flist[] = f1.list();
                                	for(int z=0;z<flist.length;z++)
                                	{
                                		File f2 = new File(Path+"/"+flist[z]);
                                	/**
                                	* removing directory of attachment with
                                	* message
                                	*/
                                		f2.delete();
                                	}
                        		f1.delete();
    				}
                	}
                 	catch(Exception e)
                	{
				data.setMessage("The error in delete message in MailSendMessage Screens !!"+e);
                	//	e.printStackTrace();
                	}
			}	
		
		String msg=MultilingualUtil.ConvertedString("mail_msg4",LangFile);
		data.setMessage(msg);
		}
	} 
	/**
 	* This method is invoked when no button corresponding to
 	* @param data RunData
 	* @param context Context
 	* @exception Exception, a generic exception
 	*
 	*/
        public void doPerform(RunData data,Context context)
	throws Exception
	{
		String action=data.getParameters().getString("actionName","");
		context.put("action",action);
		if(action.equals("eventSubmit_doSend"))
			doSend(data,context);
		else if(action.equals("eventSubmit_doDelete"))
			doDeleteMessage(data,context);
		else
		{
			User user = data.getUser();
			String LangFile=user.getTemp("LangFile").toString();
			String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
		       	data.setMessage(msg);
		}
        }
	 /**
        * In this method, We write message/s with attachment or Local_mail for users(Local)
        * @param LangFile String
        * @param path String
        * @param msgid int
        * @param message String
        * @param toAddress String
        * @param readf int
        * @param receiver_id int
        * @param Status int
        * @param mode String
        * @param data RunData
        * @param context Context
        */


	public void doWriteMail(String LangFile,String path,int msgid,String message, String toAddress, RunData data, int readf, int receiver_id,Context context,int Status,String mode, String msg)
	{
		try
		{
			ParameterParser pp=data.getParameters();
                        Criteria crit=new Criteria();
			ErrorDumpUtil.ErrorLog("message....in action.."+message);
			if(TurbineSecurity.accountExists(toAddress)) 
                        {// inner 'if'
                        	/**
                               	* Select highest mail_id from Mail_Send table
                               	*/
                               	String qmsgid="SELECT MAX(MAIL_ID) FROM MAIL_SEND";
                               	List vmsgid = MailSendPeer.executeQuery(qmsgid);
                               	/**
                               	* getting the mail_id of the message.
                               	*/
                               	for(ListIterator j = vmsgid.listIterator();j.hasNext();)
                               	{
                               		Record item = (Record)j.next();
                                       	msgid = item.getValue("MAX(MAIL_ID)").asInt();
					ErrorDumpUtil.ErrorLog("msgid....in action.."+msgid);
                               	}        
				/**
                        	* inserting into the MAIL_RECEIVE table.
                        	*/
                        	receiver_id = UserUtil.getUID(toAddress);
				//ErrorDumpUtil.ErrorLog("receiver_id"+receiver_id);
                        	crit=new Criteria();
                        	crit.add(MailReceivePeer.MAIL_ID,msgid);
                        	crit.add(MailReceivePeer.RECEIVER_ID,receiver_id);
                        	crit.add(MailReceivePeer.MAIL_READFLAG,readf);
				if(!mode.equals("grpmgmt"))
                        		crit.add(MailReceivePeer.MAIL_TYPE,0);
				else
                        		crit.add(MailReceivePeer.MAIL_TYPE,1);
                        	MailReceivePeer.doInsert(crit);

				//ErrorDumpUtil.ErrorLog("receiver_id after insert after insert"+receiver_id);

				/**here we first make the Dir
				*for writing the message.
				*/
				File first = new File(path);
				if(!first.exists())
                		first.mkdirs();

                		String path1 = path+"/"+"typedCharMsg.txt";
                		path = path+"/"+"Msg.txt";
                		FileWriter fw = new FileWriter(path,true);
                		FileWriter fw1 = new FileWriter(path1,true);
                		fw.write("\r\n");
                		fw.write("<" + msgid + ">");
                		fw.write("\r\n");
                		fw.write(message);
                		fw.write("\r\n"+"</" + msgid + ">");
                		fw.close();
                		fw1.write("\r\n");
                		fw1.write("<" + msgid + ">");
                		fw1.write("\r\n");
                		fw1.write(msg);
                		fw1.write("\r\n"+"</" + msgid + ">");
                		fw1.close();
				/**
                        	* From here starts the code to upload
                        	* the attachment if any with the message.
                        	*/
				FileItem fileItem = pp.getFileItem("file");
                        	try {//inner try
	                		if((fileItem!=null) && (fileItem.getSize()!=0))
                                	{
                                		String realPath = TurbineServlet.getRealPath("/UserArea");
                                        	String  temp = fileItem.getName();
                                        	context.put("to",temp);
                                        	int index = temp.lastIndexOf("\\");
                                        	String tempFile=temp.substring(index+1);
                                        	String filePath = realPath+"/"+toAddress+"/Attachment/"+msgid+"/"+tempFile;
                                        	File f1=new File(filePath);
                                        	File f=new File(realPath+"/"+toAddress+"/Attachment/"+msgid);
                                        	f.mkdirs();
                                        	fileItem.write(f1);
                                        	Status=1;
                                 	}
                          	}//close inner try
                          	catch(Exception e)
                         	{
                          		data.setMessage("The Error in Attachement"+e);
                          	}
                   	}//close inner 'if'
                   	else
                   	{
                  		String msg3=MultilingualUtil.ConvertedString("check_user",LangFile);
                        	String msg2=MultilingualUtil.ConvertedString("mail_msg3",LangFile);
                        	data.setMessage(msg2+""+msg3);
                  	}
      		}//close inner try
  		catch(Exception e)
       		{
			data.setMessage("The Error in Attachement"+e);
        	}
	}//DoWriteMail
}//class

