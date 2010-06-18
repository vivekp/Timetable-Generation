package org.iitk.brihaspati.modules.screens.call.Notice_User;

/*
 * @(#)NoticeContent.java	
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur. 
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */

/**
 *  @author: <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 */

import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.Notice_SRDetail;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeReceive;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.torque.util.Criteria;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import java.util.Vector;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.StringTokenizer;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
public class NoticeContent extends SecureScreen{
	public void doBuildTemplate( RunData data, Context context ){
		try{
			ParameterParser pp=data.getParameters();
			User user=data.getUser();
			Criteria crit=new Criteria();
		
			/**
			 * Get the course Id from user's temporary variable and then find the
			 * course alias with the help of util class
			 * @see CourseUtil.java in utils
			 */		

			String flag=pp.getString("flag");
			String user_role=(String)user.getTemp("role");
			String courseId="";
			if(flag.equals("FromNotices"))
			{
				courseId=(String)user.getTemp("course_id");
			}
			else
			{
				courseId=pp.getString("courseId");
			}
			String courseName=(String)user.getTemp("course_name");
			context.put("CName",courseName);
			context.put("courseId",courseId);
			context.put("user_role",user_role);
			context.put("flag",flag);

			/**
			 * Assign the role_id as according to the user's role in the course
			 */
			
			int role_id=0;
			if(user_role.equals("instructor"))
				role_id=2;
			else
				role_id=3;

			/**
			 * Get the user name and find his id. Get the group id with the help of
			 * group name.
			 * @see UserUtil.java in utils 
			 * @see GroupUtil.java in utils 
			 */

			String Username=user.getName();
			int user_id=UserUtil.getUID(Username);
			int gid=GroupUtil.getGID(courseId);
			int msgid=0;

			/**
			* Retrive value of message from text file
			*/
			String desc=pp.getString("desc","");
                        context.put("desc",desc);
                        String topicDesc="";
                        if(desc.equals("Notice_Des"))
                        {
                                String n_id=data.getParameters().getString("notice_id","");
                                /**
         			* From Here comes the code for change in Status.( Unread to Read )
         			*/
                		try
                		{
                        	crit=new Criteria();
                        	crit.add(NoticeReceivePeer.NOTICE_ID,n_id);
                        	crit.add(NoticeReceivePeer.RECEIVER_ID,user_id);
                        	crit.add(NoticeReceivePeer.READ_FLAG,1);
                        	NoticeReceivePeer.doUpdate(crit);
                		}
                		catch(Exception e)
                		{data.setMessage("The Error in Notice Receive table for read flag updatation "+e);}
				crit=new Criteria();
                                crit.add(NoticeSendPeer.NOTICE_ID,n_id);
                                List sub=NoticeSendPeer.doSelect(crit);
                                StringBuffer Notice_sub=new StringBuffer(((NoticeSend)(sub.get(0))).getNoticeSubject());
	                               // Notice_sub.deleteCharAt(0);
                                context.put("Notice_sub",Notice_sub);
                                /**
                                * Getting the actual path where the Mail is stored
                                * @return String
                                */
                                String filePath=data.getServletContext().getRealPath("/Courses")+"/"+courseId+"/Notice_Msg.txt";
                                /**
                                * read the file using XML descriptior
                                *  @try and catch Identifies statements that user want to monitor
                                *  and catch for exceptoin.
                                */
                                        int i =0;
                                        int start=0;
                                        int stop=0;
                                        String str[] = new String[10000];
                                try
                                {
                                        try{
                                        BufferedReader br=new BufferedReader(new FileReader (filePath));
                                        while ((str[i]=br.readLine()) != null)
                                        {
                                                if (str[i].equals("<"+n_id+">")) {
                                                start = i;      }
					else if(str[i].equals("</"+n_id+">"))
                                                {
                                                        stop = i;

                                                }
                                                i= i +1;
                                        }
                                        br.close();
                                        }
                                        catch(IOException e)
                                        {
                                                e.printStackTrace();
                                        }
                                }
                                catch(Exception ex)
                                {
                                        data.setMessage("The Error in reading file "+ex);
                                }
                                for(int j=start+1;j<stop;j++)
                                {
                                        topicDesc=topicDesc+ "\n"+str[j];
                                }
                                /**
                                * here comes the code to view attachment with the message
                                */
                                context.put("message",topicDesc);
                        }

			/**
			 * Get the records from the database where the group id is either that
			 * of the course in which user is present currently or '1' which is
			 * global group
			 */
		
                        crit=new Criteria();
			crit.add(NoticeSendPeer.GROUP_ID,gid);
			crit.or(NoticeSendPeer.GROUP_ID,"1");
			crit.addDescendingOrderByColumn(NoticeSendPeer.NOTICE_ID);

			List w=NoticeSendPeer.doSelect(crit);
			int rec_no=w.size();
		
			Vector a=new Vector();
			int count1=0;

			/**
			 * If the messages satisfying the above condition are found then get 
			 * those messages which are posted either for every one or for specific
			 * role
			 */

			for(count1=0;count1<rec_no;count1++){
				NoticeSend element=(NoticeSend)(w.get(count1));
				if(element.getRoleId()==role_id || element.getRoleId()==7)
					a.add(w.get(count1));
			}
			/**
			 * Get the message details from the database and put all the 
			 * information in the specific vectors
			 */

			Vector noticeSentRec=new Vector();

			for(count1=0;count1<a.size();count1++){
				int msgId=(((NoticeSend)(a.get(count1))).getNoticeId());
				crit=new Criteria();
				crit.add(NoticeReceivePeer.NOTICE_ID,msgId);
				crit.and(NoticeReceivePeer.RECEIVER_ID,user_id);
				List v=NoticeReceivePeer.doSelect(crit);

				int count2=0;
				for(count2=0;count2<v.size();count2++){
					int r_flag=((NoticeReceive)(v.get(count2))).getReadFlag();
					int message_id=(((NoticeReceive)(v.get(count2))).getNoticeId());
					Notice_SRDetail noticeSRDetails=new Notice_SRDetail();
					noticeSRDetails.setReadFlag(Integer.toString(r_flag));
					crit=new Criteria();
					crit.add(NoticeSendPeer.NOTICE_ID,msgId);
					List q=NoticeSendPeer.doSelect(crit);
					NoticeSend element1=(NoticeSend)(q.get(0));

					StringBuffer sb=new StringBuffer(element1.getNoticeSubject());
				//	sb.deleteCharAt(0);

					noticeSRDetails.setMsgSubject(new String(sb));
					noticeSRDetails.setPostTime(element1.getPostTime());
					noticeSRDetails.setMsgId(Integer.toString(element1.getNoticeId()));
					int uid=element1.getUserId();
					String loginName=UserUtil.getLoginName(uid);
					noticeSRDetails.setUserName(loginName);
					noticeSentRec.add(noticeSRDetails);
				}
			}

			/**
 			 * Get the first name and last name of the
 			 * current user
			 */

			crit=new Criteria();
			crit.add(TurbineUserPeer.USER_ID,Integer.toString(user_id));	
			List b=TurbineUserPeer.doSelect(crit);
			String fname=((TurbineUser)(b.get(0))).getFirstName();
			String lname=((TurbineUser)(b.get(0))).getLastName();
			String uname=((TurbineUser)(b.get(0))).getUserName();

			/**
			 * Put all the populated vectors in the context for display in the vm
 			 * pages
			 */
			if(noticeSentRec.size()!=0)
			{	
				context.put("username",uname);
				context.put("firstname",fname);
				context.put("lastname",lname);
				context.put("notice_Sent_Rec",noticeSentRec);
			}
			else
			{
                                String LangFile=data.getUser().getTemp("LangFile").toString();
                                String msg=MultilingualUtil.ConvertedString("notice_msg4",LangFile);
                                data.setMessage(msg);
			}
			context.put("Rec_size",Integer.toString(noticeSentRec.size()));
		}
		catch(Exception e){data.setMessage("The error in Read Notice"+e);}
	}
}

