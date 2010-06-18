package org.iitk.brihaspati.modules.actions;

/*
 * @(#)Notice_Send_Delete.java	
 *
 *  Copyright (c) 2005-2008 ETRG,IIT Kanpur. 
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileOutputStream;

import java.util.List;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import java.sql.Date;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;

import com.workingdogs.village.Record;
import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.NoticeReceive;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.apache.turbine.services.servlet.TurbineServlet;

/**
 * In this class,we send notice and delete self copy or all(Only Sender) notices
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */
public class Notice_Send_Delete extends SecureAction
{
	/**
	* In this method,You Send notice to all or instructor or student in particuler group when
	* you are registered
	* @param data Rundata
	* @param context Context
	*/
	public void doSend(RunData data, Context context)
	{
		try{
			User user=data.getUser();
			String LangFile=(String)user.getTemp("LangFile");
			ParameterParser pp=data.getParameters();	
			String UserName=user.getName();
			int userid=UserUtil.getUID(UserName);	
			String uid=Integer.toString(userid);
			int group_id=0;

			Criteria crit=new Criteria();
			String courses[]=pp.getStrings("course_list");
			int rec_no=0, notice_expiry=0;
			if(courses == null)
				rec_no=0;
			else
	 			rec_no=courses.length;
			String mode=pp.getString("mode1","notice");

			/**
		 	* Retrieve the notice details from the screen with the help of 
		 	* ParameterParser
		 	*/

			String notice_role=pp.getString("role");
			String notice_message=pp.getString("message");	

			String notice_subject=pp.getString("subject");	
			/**
			 *   Replace special character and scripts
			 */
			notice_subject =StringUtil.replaceXmlSpecialCharacters(notice_subject);

			String notice_sub_modify=notice_subject.replaceAll("'","\'");
			boolean flag=false;

			//this group id is used only for quiz


         		if(mode.equals("notice"))
	                        notice_expiry=pp.getInt("expiry");
                        else if(mode.equals("quiz"))
        	                notice_expiry=30;
			/**
			* Get current date and expiry date with yyyy-mm-dd format
			* @see ExpiryUtil from utils
			*/	
			String c_date="";
			if(mode.equals("notice") || (mode.equals("grpmgmt")))
                                c_date=ExpiryUtil.getCurrentDate("-");
                        else if(mode.equals("quiz"))
                                c_date=pp.getString("quezdate");

			Date current_date=Date.valueOf(c_date);
			String E_date=ExpiryUtil.getExpired(c_date,notice_expiry);
			Date Expiry_date=Date.valueOf(E_date);

			for(int num=0;num<rec_no;num++){
		
				/**
			 	* Retrieves the group_id for the corresponding group name from 
			 	* TURBINE_GROUP
			 	*/
				String course_id="";
				if(userid==1){
					course_id=courses[num];
				}else{
					course_id=CourseUtil.getCourseId(courses[num]);
				}

				//this is used for notices and group management

                                        group_id=GroupUtil.getGID(course_id);

				//Check for repeat quiz announcement

				if(mode.equals("quiz")){
                                        crit=new Criteria();
					crit.add(NoticeSendPeer.GROUP_ID,group_id);
                                        crit.add(NoticeSendPeer.USER_ID,uid);
                                        crit.add(NoticeSendPeer.NOTICE_SUBJECT,notice_sub_modify);
                                        List lst1=NoticeSendPeer.doSelect(crit);
                                        if(lst1.size() !=0)
                                       flag=true;
                         	}
	                         if(flag==true){
                                     String mssg=MultilingualUtil.ConvertedString("quiz_msg",LangFile);
                                     data.setMessage(mssg);
        	                 return;
                	        }
				/**
				 * Checks the group of users to whom this notice has to be delivered
			 	*/

				int role_id=0;
				if(notice_role.equals("instructor"))
					role_id=2;
				if(notice_role.equals("student"))
					role_id=3;
				if(notice_role.equals("all"))
					role_id=7;

				/**	
			 	* Inserts the notice details in the table MESSAGE_SEND
			 	*/

				try{
					crit=new Criteria();
					crit.add(NoticeSendPeer.NOTICE_SUBJECT,notice_sub_modify);
					crit.add(NoticeSendPeer.USER_ID,uid);
					crit.add(NoticeSendPeer.GROUP_ID,group_id);
					crit.add(NoticeSendPeer.ROLE_ID,role_id);
					crit.add(NoticeSendPeer.POST_TIME,current_date);
					crit.add(NoticeSendPeer.EXPIRY,notice_expiry);
					crit.add(NoticeSendPeer.EXPIRY_DATE,Expiry_date);
					NoticeSendPeer.doInsert(crit);

					if(mode.equals("quiz")){
						crit=new Criteria();
                                        	crit.add(NewsPeer.GROUP_ID,group_id);
	                                        crit.add(NewsPeer.USER_ID,uid);
        	                                crit.add(NewsPeer.NEWS_TITLE,notice_sub_modify);
                	                        crit.add(NewsPeer.NEWS_DESCRIPTION,notice_message);
                        	                crit.add(NewsPeer.PUBLISH_DATE,current_date);
                                	        crit.add(NewsPeer.EXPIRY,notice_expiry);
                                        	crit.add(NewsPeer.EXPIRY_DATE,Expiry_date);
	                                        NewsPeer.doInsert(crit);
					}


				}
				catch(Exception e)
				{
					data.setMessage("The exception in Send Notice... :-"+e);
				}
				/**
			 	* Gets the message id of the notice entered into the table
			 	*/

				int msg_id=0;
	
				String Query_msgid="SELECT MAX(NOTICE_ID) FROM NOTICE_SEND";
				List u=NoticeSendPeer.executeQuery(Query_msgid);

				for(ListIterator j=u.listIterator();j.hasNext();){
					Record item=(Record)j.next();
					msg_id=item.getValue("MAX(NOTICE_ID)").asInt();
				}
				/**
                         	* From here starts the code do store message
                         	* in a single txt file.
                         	*/
                         	String path = data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/";
                         	File first = new File(path);
                         	first.mkdirs();
                         	path = path+"/"+"Notice_Msg.txt";
                         	FileWriter fw = new FileWriter(path,true);
                         	fw.write("\r\n");
                         	fw.write("<" + msg_id + ">");
                         	fw.write("\r\n");
                         	fw.write(notice_message);
                         	fw.write("\r\n"+"</" + msg_id + ">");
                         	fw.close();

				/**
			 	* Inserts notice details in NOTICE_RECEIVE table after checking 
			 	* the users to whom the notice has to be sent
			 	*/
				try{
					if(mode.equals("grpmgmt"))
                                        {
                                                String grpname=pp.getString("val","");
						String gpath=TurbineServlet.getRealPath("/Courses"+"/"+course_id)+"/GroupManagement";
						ErrorDumpUtil.ErrorLog("grpmgmtpath-->"+gpath);
                                                TopicMetaDataXmlReader topicmetadata=new TopicMetaDataXmlReader(gpath+"/"+grpname+"__des.xml");
                                                Vector grouplist=topicmetadata.getGroupDetails();
                                               // int usrid=0;
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
                                                                userid=UserUtil.getUID(str);
								insertReceiveNotice( msg_id, userid, group_id);
                                                        }
                                                }
                                        }
                                        else
                                        {

					Vector userList=new Vector();
					if(role_id==2 || role_id==3)
					{
						userList=UserGroupRoleUtil.getUID(group_id,role_id);
						int rows=userList.size();
						if(rows!=0){
							for(int count=0;count<rows;count++){
								String user_id=(String)userList.elementAt(count);
								userid=Integer.parseInt(user_id);
								insertReceiveNotice( msg_id, userid, group_id);
							}
						}
					}
					if(role_id==7)
					{
						userList=UserGroupRoleUtil.getUID(group_id);
						int list=userList.size();	
						if(list!=0)
						{
						for(int k=0;k<list;k++)
						{
							userid=Integer.parseInt((String)userList.get(k));
							insertReceiveNotice( msg_id, userid, group_id);
						}
						}
					}
					}//else
				}
				catch(Exception cx)
				{
					data.setMessage("The error in Receive "+cx);
				}
			}//for
		//	String LangFile=(String)user.getTemp("LangFile");

			String msg1="";
                        if(mode.equals("quiz"))
	                        msg1=MultilingualUtil.ConvertedString("quiz_msg2",LangFile);
                        //"Quiz schedule of this course send through Notices";
                        else
				msg1=MultilingualUtil.ConvertedString("notice_msg1",LangFile);

			if(mode.equals("grpmgmt"))
                                data.setScreenTemplate("call,Group_Mgmt,Activitygroup.vm");
                        else if(mode.equals("quiz"))
				data.setScreenTemplate("call,Quiz_Mgmt,Quiz_Detail.vm");
			else
                                data.setScreenTemplate("call,Notice_User,Notices.vm");

                        data.setMessage(msg1);
		}
		catch(Exception ex)
		{
			data.setMessage("The Error in Send Notice .."+ex);
		}
	}
	/**
        * In this method,we insert notice to database
        * @param msg_id int
        * @param userid int
        * @param group_id int
        */

	public void insertReceiveNotice(int msg_id,int userid,int group_id)
	{
		try{
			Criteria crit=new Criteria();
                        crit.add(NoticeReceivePeer.NOTICE_ID,msg_id);
                        crit.add(NoticeReceivePeer.RECEIVER_ID,userid);
                        crit.add(NoticeReceivePeer.GROUP_ID,group_id);
                        crit.add(NoticeReceivePeer.READ_FLAG,0);
                        NoticeReceivePeer.doInsert(crit);

		}
		catch(Exception ex)
                {
                       ErrorDumpUtil.ErrorLog("The Error in Insert Receive Notice .."+ex);
                }


	}

	/**
	* In this method,we deleted notice/'s 
	* @param data Rundata
	* @param context Context
	*/
	public void doDelete(RunData data,Context context)
	{
		try{
			String LangFile=(String)data.getUser().getTemp("LangFile");
			String course_id=(String)data.getUser().getTemp("course_id");
			Criteria crit=new Criteria();
			String noticeList=data.getParameters().getString("deleteFileNames","");
			if(!noticeList.equals(""))
			{
			StringTokenizer st=new StringTokenizer(noticeList,"^");
                        for(int j=0;st.hasMoreTokens();j++)
                        	{ //first 'for' loop
                                String n_id=st.nextToken();

				crit=new Criteria();
                        	crit.add(NoticeSendPeer.NOTICE_ID,n_id);
                        	List v=NoticeSendPeer.doSelect(crit);

                        	int user_id=((NoticeSend)(v.get(0))).getUserId();
                        	String userName=data.getUser().getName();
                        	int u_id=UserUtil.getUID(userName);
                        	if( u_id  != user_id )
				{
                                	crit=new Criteria();
                                	crit.add(NoticeReceivePeer.NOTICE_ID,n_id);
                                	crit.add(NoticeReceivePeer.RECEIVER_ID,u_id);
                                	NoticeReceivePeer.doDelete(crit);
					String msg1=MultilingualUtil.ConvertedString("notice_msg2",LangFile);
                        		data.setMessage(msg1);
                        	}
                        	else{
                                	crit=new Criteria();
                                	crit.add(NoticeSendPeer.NOTICE_ID,n_id);
                                	NoticeSendPeer.doDelete(crit);

                                	crit=new Criteria();
                                	crit.add(NoticeReceivePeer.NOTICE_ID,n_id);
                                	NoticeReceivePeer.doDelete(crit);
                                	/**
                                	* deleting the message from the txt file
					* @see CommonUtility in Util
                                	*/
                         		String path = data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/Notice_Msg.txt";
					CommonUtility.UpdateTxtFile(path, n_id, "",false );	
		/*			String str[] = new String[10000];
                        		int i =0;
                        		int startd = 0;
                        		int stopd = 0;
                         		String path = data.getServletContext().getRealPath("/Courses")+"/"+course_id+"/Notice_Msg.txt";
                                	BufferedReader br=new BufferedReader(new FileReader (path));
                                	while ((str[i]=br.readLine()) != null)
                                	{
                                        	if (str[i].equals("<"+n_id+">"))
                                        	{
                                                	startd = i;
                                        	}
                                        	else if(str[i].equals("</"+n_id+">"))
                                        	{
                                                	stopd = i;
                                        	}
                                		i= i +1;
                                	}
                                	br.close();
                                	FileWriter fw=new FileWriter(path);
                                	for(int x=0;x<startd;x++)
                                	{
                                        	fw.write(str[x]+"\r\n");
                                	}
                                	for(int y=stopd+1;y<i;y++)
                                	{
                                        	fw.write(str[y]+"\r\n");
                                	}

                                	fw.close();
		*/
					
					String msg2=MultilingualUtil.ConvertedString("notice_msg3",LangFile);
                                	data.setMessage(msg2);
                        	}
				}//end for Loop
			}//end outer if
		}
		catch(Exception ex)
		{
                	data.setMessage("The error in deletion of notice..."+ex);
		}
	}

	/**
        * In this method,we configure flash heading
        * @param data Rundata
        * @param context Context
        */
        public void doWrite(RunData data,Context context)
        {
                try{
                        String LangFile=(String)data.getUser().getTemp("LangFile");
		//	String nflag=pp.getString("flag","");
			ParameterParser pp=data.getParameters();
			String Fheading=pp.getString("message","");
		//	String Fheadexp=pp.getString("expiry","");
			String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Notification.properties";
			(new File(path)).delete();
			AdminProperties.setValue(path,Fheading,"brihaspati.admin.flashHeading.value");
		//	AdminProperties.setValue(path,Fheadexp,"brihaspati.admin.flashHeadExp.value");
			String Fhupdate=MultilingualUtil.ConvertedString("cal_ins",LangFile);
                        data.setMessage(Fhupdate);

		}
		catch(Exception ex){data.setMessage("The error in do Write method in Notice Send"+ex);}
	}

    /**
     * Default action to perform if the specified action cannot be executed.
     * @param data RunData
     * @param context Context
     */
	public void doPerform( RunData data,Context context )throws Exception
	{
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doSend"))
			doSend(data,context);
		else if(action.equals("eventSubmit_doDelete"))
			doDelete(data,context);
		else if(action.equals("eventSubmit_doWrite"))
                        doWrite(data,context);

		else
			data.setMessage("Cannot find the button");
	}
}
