package org.iitk.brihaspati.modules.actions;

/*
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
import java.io.BufferedReader;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;

import java.sql.Date;

import com.workingdogs.village.Record;

import org.apache.velocity.context.Context;

import org.apache.torque.util.Criteria;

import org.apache.commons.fileupload.FileItem; 

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;

import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbReceivePeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;   
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

import java.nio.MappedByteBuffer;
import java.io.FileInputStream;
import java.io.FileInputStream;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.FileChannel;
import java.io.FileOutputStream;



/** This class contains code of Sending Message to the Discussion Board 
 *  with or without attachments
 *  @author <a href="aktri@iitk.ac.in">Awadhesh Kumar Trivedi</a>
 *  @author <a href="sumanrjpt@yahoo.co.in">Suman Rajput</a>
 *  @author <a href="rekha_20july@yahoo.co.in">Rekha Pal</a>
 *  @author <a href="nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */
public class SendDB extends SecureAction
{
	/**
     	* @param data RunData
     	* @param context Context
     	* @exception Exception a generic exception
     	*/ 
    	public void doSend(RunData data, Context context)
    	{  
		try
		{
			//Here we retrive from the templates by the help parameter parser 

			int Status=0; 
			//boolean Status=false;
		        String LangFile=data.getUser().getTemp("LangFile").toString();
			ParameterParser pp=data.getParameters();
                        String UserName=data.getUser().getName();
			String DB_message=pp.getString("message");
			String DB_subject=pp.getString("contentTopic");
			String mode=pp.getString("mode1","");
                        String grpname=pp.getString("val","");
                        ErrorDumpUtil.ErrorLog("In send DB java action mode"+mode+"\ngrpname----"+grpname);
			/**
			* Check for special character in Topic Name excluding Re: /Re:Re:Re:
			*/
			if(StringUtil.checkString(DB_subject) != -1)
	                {
                        	data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                                return;
                        }
			String TopicArray[]=DB_subject.split("Re:");
			if(TopicArray.length >1 )
			DB_subject=TopicArray[TopicArray.length -1];
			/**
			* Add Re:
			*/	
			String Add_RE_DB_subject = "";
			for(int i=0;i<TopicArray.length -1;i++)
			{
				Add_RE_DB_subject = Add_RE_DB_subject+"Re:";
			}
			DB_subject=Add_RE_DB_subject + DB_subject;
			
	
			String Rep_id=pp.getString("Repid");
			context.put("Repid",Rep_id);
			String expiry=pp.getString("expiry","");
			String status=pp.getString("status");
                        context.put("status",status);
			String course_id=pp.getString("courseid");
        		context.put("courseid",course_id);
			int group_id=GroupUtil.getGID(course_id);

			//here we get userid by the help of UserUtil

			int userid=UserUtil.getUID(UserName);
			if(expiry.equals(""))
			{
				expiry=pp.getString("expstatus");
				
			}
			int exp=Integer.parseInt(expiry);
			String Cur_date=ExpiryUtil.getCurrentDate("-");
			Date Post_date=Date.valueOf(Cur_date);
			Date exDate=Date.valueOf(ExpiryUtil.getExpired(Cur_date,exp));
				
               		/* Insert message in database*/
			Criteria crit=new Criteria();
			crit.add(DbSendPeer.REPLY_ID,Rep_id); 
			crit.add(DbSendPeer.MSG_SUBJECT,DB_subject); 
			crit.add(DbSendPeer.USER_ID,Integer.toString(userid)); 
			crit.add(DbSendPeer.GROUP_ID,Integer.toString(group_id)); 
			crit.add(DbSendPeer.POST_TIME,Post_date); 
			crit.add(DbSendPeer.EXPIRY,expiry);
			crit.add(DbSendPeer.EXPIRY_DATE,exDate);
			crit.add(DbSendPeer.PERMISSION,1);
			if(mode.equals("grpmgmt"))
                                crit.add(DbSendPeer.GRPMGMT_TYPE,1);
                        else
                                crit.add(DbSendPeer.GRPMGMT_TYPE,0);
			DbSendPeer.doInsert(crit);
			int msg_id=0;
	  		String Query_msgid="SELECT MAX(MSG_ID) FROM DB_SEND";
	   		List u=DbSendPeer.executeQuery(Query_msgid);
             		for(ListIterator j=u.listIterator();j.hasNext();)
			{
				Record item=(Record)j.next();
				msg_id=item.getValue("MAX(MSG_ID)").asInt();
			}

                        /**
			*  From here starts the code do store message               
		        * in a single txt file.
			*/
           
        		String path = data.getServletContext().getRealPath("/Courses/"+course_id+"/DisBoard")+"/"+DB_subject;
			File topicDir = new File(path);
			topicDir.mkdirs();
			path = path+"/"+"Msg.txt";
			java.util.Date date=new java.util.Date();
			FileWriter fw = new FileWriter(path,true);
			fw.write("\n");
			fw.write("<" + msg_id + ">");
			fw.write("\n");
			fw.write("< Send date "+ date +">"+"\n"+DB_message+"\n");
			fw.write("\n"+"</" + msg_id + ">");
			fw.close();

                     	// From here starts the code for uploading the file 
			try
			{
				FileItem fileItem = pp.getFileItem("file");
				String tempFile=(fileItem.getName()).substring(((fileItem.getName()).lastIndexOf("\\"))+1);
				File f= new File(TurbineServlet.getRealPath("/Courses/"+course_id+"/DisBoard/"+DB_subject+"/Attachment/"+msg_id));
				f.mkdirs();
				if(fileItem.getSize() > 0 )
				{
					fileItem.write(new File(f+"/"+tempFile));
					Status=1;
				}
				
            		}//try
                 	catch(Exception e){}
			
      			//Insert Detail in Receiver Table 
			crit=new Criteria();
			int i[]={0,1};
			crit.add(TurbineUserGroupRolePeer.GROUP_ID,group_id);
			crit.addNotIn(TurbineUserGroupRolePeer.USER_ID,i);
			List q=TurbineUserGroupRolePeer.doSelect(crit);
			String uid=new String();
			if(mode.equals("grpmgmt"))
                        {
                                String gpath=TurbineServlet.getRealPath("/Courses"+"/"+course_id)+"/GroupManagement";
                                TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(gpath+"/"+grpname+"__des.xml"
);
                                Vector grouplist=topicmetadata.getGroupDetails();
                                int usrid=0;
                                if(grouplist!=null)
                                {
                                        String username[]=new String[1000];
                                        int l=0;
                                        for(int m=0;m<grouplist.size();m++)
                                        {//for
                                                String uname =((FileEntry) grouplist.elementAt(m)).getUserName();
                                                username[m]=uname;
                                                l++;
                                        }
                                        username[l]=UserName;
                                        for(int k=0;k<=l;k++)
                                        {
                                                String str=username[k];
                                                usrid=UserUtil.getUID(str);
                                                crit.add(DbReceivePeer.RECEIVER_ID,usrid);
                                                crit.add(DbReceivePeer.MSG_ID,msg_id);
                                                crit.add(DbReceivePeer.GROUP_ID,group_id);
                                                crit.add(DbReceivePeer.READ_FLAG,0);
                                                context.put("test",Integer.toString(msg_id));
                                                DbReceivePeer.doInsert(crit);
                                        }
                                }
                        }
                        else
			{
				for(int k=0;k<q.size();k++)
				{
					TurbineUserGroupRole element=(TurbineUserGroupRole)q.get(k);
					int u_id=element.getUserId();
					crit=new Criteria();
					crit.add(DbReceivePeer.RECEIVER_ID,u_id);
					crit.add(DbReceivePeer.MSG_ID,msg_id);
					crit.add(DbReceivePeer.GROUP_ID,group_id);
					crit.add(DbReceivePeer.READ_FLAG,0);
		 			context.put("test",Integer.toString(msg_id));
					DbReceivePeer.doInsert(crit);
                       
				}//for
			}
			String msg="",msg1="";
	   		if(Status==1)
			{	
                       		msg=MultilingualUtil.ConvertedString("attach",LangFile);			
                       		msg1=MultilingualUtil.ConvertedString("db_msg1",LangFile);
			
				if(LangFile.endsWith("en.properties"))
                                         data.setMessage(msg1+" "+msg);
                                 else
                                         data.setMessage(msg+" "+msg1);

				
			}
			else
			{
				msg=MultilingualUtil.ConvertedString("db_msg1",LangFile);
				data.setMessage(msg);
				
			}
	    	}//try
            	catch(Exception e){data.setMessage("Some Error Occured in Sending Discussion !!!!" +e);}
	}//method(doSend)												

 	/**
  	* Place all the data object in the context for use in the template.
 	* @param data RunData instance
  	* @param context Context instance
 	* @exception Exception, a generic exception
  	**/

        public void doPermission(RunData data, Context context)
        {
		try
		{
			/**
			* Get the user name  and find the user id
                	* @see UserUtil in utils
                	*/
			
			ParameterParser pp=data.getParameters();
			String mgid=pp.getString("msgid");
                        context.put("mgid",mgid);
                        String permit=pp.getString("perm");
			context.put("permit",permit);
                        String course_id=pp.getString("courseid");
                        context.put("courseid",course_id);
                        int group_id=GroupUtil.getGID(course_id);
                	/* Update Permision field according to instructor permission*/
			Criteria crit=new Criteria();
			crit.add(DbSendPeer.MSG_ID,mgid);
			crit.add(DbSendPeer.PERMISSION,permit);
			DbSendPeer.doUpdate(crit);
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg5=MultilingualUtil.ConvertedString("db_msg3",LangFile);
			data.setMessage(msg5);
			data.setScreenTemplate("call,Dis_Board,Edit.vm");

			
		}//try
		catch(Exception e){data.setMessage("Some Error Occured in Sending Permission !!!!" +e);}
	}//doPermission
                    
	/**
 	* Get the user name  and find the user id
 	* @see UserUtil in utils
 	*/

	public void doUpdate(RunData data, Context context)
	{
		try
		{
                        String LangFile=data.getUser().getTemp("LangFile").toString();
			int Status=0;
		 	String UserName=data.getUser().getName();
	  	 	ParameterParser pp=data.getParameters();
		  	String DB_message=pp.getString("message");
			String DB_mgid=pp.getString("mgid");
			String Rep_id=pp.getString("Repid");
	     		int expiry=pp.getInt("expiry");
	     		String expirytime=pp.getString("expiry","");
                        context.put("expirytime",expiry);      
	                String course_id=pp.getString("course_id","");
			context.put("course_id",course_id);
	      		int group_id=GroupUtil.getGID(course_id);
	        	context.put("cid",course_id);

			//Here  we get the userid by the help of UserUtil

			int userid=UserUtil.getUID(UserName);
			String DB_subject=pp.getString("contentTopic","");
			if(StringUtil.checkString(DB_subject) != -1)
	                {
                        	data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                                return;
                        }
			context.put("contentTopic",DB_subject);
			String status=pp.getString("status","");
                        context.put("status",status);   
			/**
			 * Get Post date and Expiry Date in yyyy-mm-dd format
			 */
                        if(expirytime.equals(""))
                        {
                                expirytime=pp.getString("expstatus");

                        }
                        int exp=Integer.parseInt(expirytime);
			String Cur_date=ExpiryUtil.getCurrentDate("-"); 
			Date P_Date=Date.valueOf(Cur_date);
			Date Ex_Date=Date.valueOf(ExpiryUtil.getExpired(Cur_date,exp));
			
			Criteria crit=new Criteria();
                        crit.add(DbSendPeer.MSG_ID,DB_mgid);
                        List sendDetail=DbSendPeer.doSelect(crit);
                        for(int i=0;i<sendDetail.size();i++)
                        {
                                DbSend element=(DbSend)sendDetail.get(i);
                                Rep_id=Integer.toString(element.getReplyId());
                        //        ErrorDumpUtil.ErrorLog("arvind      ,,c vxc,vn xcv x xcv   "+Rep_id);
                        }
			

	          	/* Update  the message  */
	      		crit=new Criteria();
			crit.add(DbSendPeer.MSG_ID,DB_mgid);
	      		crit.add(DbSendPeer.MSG_SUBJECT,DB_subject);
	      		crit.add(DbSendPeer.REPLY_ID,Rep_id);
	      		crit.add(DbSendPeer.POST_TIME,P_Date);
	      		crit.add(DbSendPeer.EXPIRY,expiry);
	      		crit.add(DbSendPeer.EXPIRY,expirytime);
	      		crit.add(DbSendPeer.EXPIRY_DATE,Ex_Date);
	      		DbSendPeer.doUpdate(crit);
                        int msg_id=0;
		        List u=null;
			String Query_msgid="SELECT MAX(MSG_ID) FROM DB_SEND";
			u=DbSendPeer.executeQuery(Query_msgid);
		        for(ListIterator j=u.listIterator();j.hasNext();)
			{
                        	Record item=(Record)j.next();
                             	msg_id=item.getValue("MAX(MSG_ID)").asInt();
			}
			
		        /**
                        * read the file using fileReader
                        * @try and catch Identifies statements that user want to monitor
                        * and catch for exceptoin.
                        */
	                 		
  			String filePath=data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/DisBoard/"+ DB_subject+"/Msg.txt";

			CommonUtility.UpdateTxtFile(filePath,DB_mgid,DB_message,true);

			String msg6=MultilingualUtil.ConvertedString("c_msg5",LangFile);
			data.setMessage(msg6);
                        	
			try
			{	
				FileItem fileItem = pp.getFileItem("file");
                                String realPath = TurbineServlet.getRealPath("/Courses/"+course_id+"/DisBoard");
		        	String  temp = fileItem.getName();
		        	int index = temp.lastIndexOf("\\");
		        	String tempFile=temp.substring(index+1);
				File filePath1 = new File (realPath+"/"+DB_subject+"/Attachment/"+msg_id+"/"+tempFile);
				File f=new File(realPath+"/"+DB_subject+"/Attachment/"+msg_id);
			       	f.mkdirs();
                                if(fileItem.getSize() > 0 )
                                {
                                        fileItem.write(filePath1);
                                        Status=1;
                                }

                               
			}//try
			catch(Exception e1){}

			if(Status==1)
			{	
				String msg7=MultilingualUtil.ConvertedString("db_msg5",LangFile);
			    	data.setMessage(msg7);
                                

				Status=0;
			}					   
		}//try
		catch(Exception e){data.setMessage("Some Error Occured in doUpdate !!!!" +e);}
	}//method doupdate 

	/* for the deletion message*/
	
 	public void doDelete(RunData data, Context context) throws Exception
	{
		try{
 			ParameterParser pp=data.getParameters();
			String UserName=data.getUser().getName();
			int userid=UserUtil.getUID(UserName);
			String DB_subject=pp.getString("DB_subject","");
			context.put("contentTopic",DB_subject);
			String msg_id=pp.getString("msg_id","");
                        context.put("msgid",msg_id);
 			String course_id=pp.getString("course_id","");
 			context.put("courseid",course_id);
 		        context.put("userid",userid);
                        Criteria crit=new Criteria();
 			String mid_delete = pp.getString("deleteFileNames","");
			String Deleteper = pp.getString("Deleteper","");

			// Code to get message from DB_subject

			String [] subjectarray = DB_subject.split("@@@@");
                       	if(!mid_delete.equals(""))
		        { //outer 'if'
                        	StringTokenizer st=new StringTokenizer(mid_delete,"^");
		          	for(int j=0;st.hasMoreTokens();j++)
		              	{ //first 'for' loop
		                	String msg_idd=st.nextToken();
			 		// get New subject	
			 
					DB_subject = subjectarray[j];
					if(!Deleteper.equals("Archive_Deleted")){
                                                /***  select the replyid in database  */
						crit=new Criteria();
	                                        crit.add(DbSendPeer.MSG_ID,msg_idd); //7
        	                                List sendDetail=DbSendPeer.doSelect(crit);
                	                        int Rep_id=0;
                        	                for(int i=0;i<sendDetail.size();i++)
                                	        {
                                        	        DbSend element=(DbSend)sendDetail.get(i);
                                                	Rep_id=element.getReplyId(); //4
                                        	}

	                                        sendDetail=null;
        	                                crit=new Criteria();
                	                        crit.add(DbSendPeer.REPLY_ID,Integer.parseInt(msg_idd));
                        	                sendDetail=DbSendPeer.doSelect(crit);
                                	        for(int i=0;i<sendDetail.size();i++)
                                        	{
	                                                DbSend element=(DbSend)sendDetail.get(i);
        	                                        int newid=element.getMsgId(); //4
                	                                crit=new Criteria();
                        	                        crit.add(DbSendPeer.MSG_ID,newid);
                                	                crit.add(DbSendPeer.REPLY_ID,Rep_id);
                                        	        DbSendPeer.doUpdate(crit);

                                        	}	

	                   			/*Delete message in database */
	
			        		crit.add(DbSendPeer.MSG_ID,msg_idd);
			        		DbSendPeer.doDelete(crit);
                        			crit=new Criteria();
		        			crit.add(DbReceivePeer.MSG_ID,msg_idd);
						String LangFile=data.getUser().getTemp("LangFile").toString();
			        		DbReceivePeer.doDelete(crit);
						String msg8=MultilingualUtil.ConvertedString("db_msg7",LangFile);
						data.setMessage(msg8);
						String filePath=data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/DisBoard"+"/"+ DB_subject+"/"+"/Msg.txt";
						CommonUtility.UpdateTxtFile(filePath,msg_idd,"",false);
	
 						/* deleting attachment if any with the message */	
	
						String fPath1=data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/DisBoard"+"/"+ DB_subject;
	
						String fPath= fPath1 +"/Attachment"+"/"+msg_idd;
	     					File f11 = new File(fPath1);
	    					File f1 = new File(fPath);
					        if(f1.exists() && f1.isDirectory())
						{
							String flist[] = f1.list();
							for(int z=0;z<flist.length;z++)
							{
								File f2 = new File(fPath+"/"+flist[z]);
      	
       							 	/* removing directory of attachment with message */
	     							f2.delete();
             						}
	     						f1.delete();
	  					}
					} else {
                                                String LangFile=data.getUser().getTemp("LangFile").toString();
                                                String msg8=MultilingualUtil.ConvertedString("db_msg7",LangFile);
                                                String APath1=data.getServletContext().getRealPath("/Courses"+"/"+course_id
+"/Archive");
                                                String msgId=APath1 +"/"+msg_idd;
                                                String message= msgId+"/"+DB_subject;
                                                File fid=new File(msgId);
                                                if(fid.exists())
                                                {
                                                        File f3 = new File(message);
                                                        f3.delete();
                                                        String amsgId=msgId+"/Attachment/";
                                                        File afile=new File(amsgId);
                                                        String flist[] = afile.list();
                                                        for(int z=0;z<flist.length;z++)
                                                        {
                                                                File f2 = new File(afile+"/"+flist[z]);
                                                                f2.delete();
                                                        }
                                                        afile.delete();
                                                }
                                                fid.delete();
                                                data.setMessage(msg8);
                                        }


      				}//for
			}//if		     
		
		}//TRY	
		catch(Exception e){data.setMessage("Some Error Occured in DeletingMessage !!!!" +e);}
	}//do delete
	
	                /**
         * This method is invoked when no button corresponding to
         * @param data RunData
         * @param context Context
         * @exception Exception, a generic exception
         */

        public void doArchive(RunData data , Context context)
        {
                try
                {
                        String LangFile=data.getUser().getTemp("LangFile").toString();
                        ParameterParser pp=data.getParameters();
                        String UserName=data.getUser().getName();
                        String DB_subject=pp.getString("DB_subject","");
                        context.put("contentTopic",DB_subject);
                        String Cur_date=ExpiryUtil.getCurrentDate("-");
                        Date Post_date=Date.valueOf(Cur_date);
                        context.put("pst",Post_date);
                        String DB_message=pp.getString("message");
                        context.put("DB_message",DB_message);
                        String msg_id=pp.getString("msg_id","");
                        context.put("msgid",msg_id);
                        String course_id=pp.getString("course_id","");
                        context.put("courseid",course_id);
                        context.put("Post_date",Post_date);
                        String topiclist = pp.getString("deleteFileNames","");
                        String [] topicarray = DB_subject.split("@@@@");
                        if(!topiclist.equals(""))
                        { //outer 'if'
                                StringTokenizer st=new StringTokenizer(topiclist,"^");
                                for(int j=0;st.hasMoreTokens();j++)
                                { //first 'for' loop
                                        String msg_idd=st.nextToken();
                                        // get New subject
                                        DB_subject = topicarray[j];
                                        String readMsg=data.getServletContext().getRealPath("/Courses"+"/"+course_id+"/DisBoard"+"/"+DB_subject+"/Msg.txt");
					 String readAttach = data.getServletContext().getRealPath("/Courses"+"/"+course_id+"/DisBoard"+"/"+DB_subject+"/Attachment/"+msg_idd);
                                        String writepath=data.getServletContext().getRealPath("/Courses"+"/"+course_id+"/Archive"+"/"+msg_idd);
                                        File f=new File(writepath);
                                        if(f.exists()){
                                                String strmess=MultilingualUtil.ConvertedString("archive_msg1",LangFile);
                                                data.setMessage(strmess);
                                                return;
                                        }
                                        String temp1=writepath+"/"+"Attachment/";
                                        File dest=new File(temp1);
                                        if(!dest.exists())
                                                dest.mkdirs();
                                        File source = new File(readAttach);
                                        String flist[] = source.list();
                                        for(int z=0;z<flist.length;z++)
                                        {
                                                source = new File(readAttach+"/"+flist[z]);
                                                temp1=temp1+flist[z];
                                                dest=new File(temp1);
                                                FileChannel in = null, out = null;
                                                try {
                                                        in = new FileInputStream(source).getChannel();
                                                        out = new FileOutputStream(dest).getChannel();
                                                        long size = in.size();
                                                        MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
                                                        out.write(buf);
                                                } finally {
                                                        if (in != null)          in.close();
                                                        if (out != null)     out.close();
                                                }
                                        }

                                        File topicDir = new File(writepath);
					if(!topicDir.exists())
                                                topicDir.mkdirs();
                                        writepath=writepath+"/"+DB_subject+".html";
                                        String str[]=new String[10000];
                                        int i=0; int start = 0; int stop= 0;String string="";

                                        BufferedReader br=new BufferedReader(new FileReader (readMsg));
                                        while ((str[i]=br.readLine()) != null)
                                        {
                                                if (str[i].equals("<"+msg_idd+">"))
                                                {
                                                        start = i;
                                                }
                                                else if(str[i].equals("</"+msg_idd+">"))
                                                {
                                                        stop = i;
                                                }
                                                     i= i +1;
                                        }                        //while
                                        br.close();
                                        FileWriter fw=new FileWriter(writepath);
                                        fw.write("<html>"+"<title>"+"</title>"+"<body>");
                                        fw.write("<b>"+"Subject :  "+"</b>"+DB_subject+"<br>\n");
                                        fw.write("<b>"+"Sender  :  "+"</b>"+UserName+"<br>");
                                        fw.write("<b>"+"Posted on  :  "+"</b>"+Post_date+"<br>");
                                        fw.write("<b>"+"Message  :  "+"</b><br>");
                                        for(int x=start;x<stop;x++)
                                        {
                                                string=string+str[x];
                                        }
                                        stop=0;
                                        stop=string.lastIndexOf("Send date");
                                        //stop=stop+21;
                                        String tempString="";
                                        for(int x=stop;x<string.length();x++)
                                        {
                                                tempString=tempString+string.charAt(x);
                                        }
					fw.write(tempString);
                                        fw.close();
                                }// for
                        }// outer if
                        String strmess=MultilingualUtil.ConvertedString("archive_msg2",LangFile);
                        data.setMessage(strmess);
                }//TRY
                catch(Exception e){  data.addMessage("Some Error Occured in ArchiveMessage !!!!" +e);   }
        } //do Archive
		
	
	public void doShowArchive(RunData data , Context context)
        {
                data.setScreenTemplate("call,Dis_Board,Archive.vm");
        }

        public void doShowDBContent(RunData data , Context context)
        {
                data.setScreenTemplate("call,Dis_Board,DBContent.vm");
        }
	


	/**  
 	* This method is invoked when no button corresponding to
 	* @param data RunData
 	* @param context Context
 	* @exception Exception, a generic exception
 	*/

        public void doPerform(RunData data,Context context)
	{
		try{
			String action=data.getParameters().getString("actionName","");
			if(action.equals("eventSubmit_doSend"))
			{
				doSend(data,context);
			}
			else if(action.equals("eventSubmit_doPermission"))
				doPermission(data,context);
			else if(action.equals("eventSubmit_doUpdate"))
		        	doUpdate(data,context);
		 	else if(action.equals("eventSubmit_doDelete"))
				doDelete(data,context);
			else if(action.equals("eventSubmit_doArchive"))
                        	doArchive(data,context);
			else if(action.equals("eventSubmit_doShowDBContent"))
                                doShowDBContent(data,context);
			else if(action.equals("eventSubmit_doShowArchive"))
				doShowArchive(data,context);
			else
			{ 
		        	String LangFile=data.getUser().getTemp("LangFile").toString();
		        	String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
		        	data.setMessage(msg);
			}
		}//try
		catch(Exception ex){data.setMessage("The error in Send DB !!"+ex);}
        }//(doPerform)


}//class
