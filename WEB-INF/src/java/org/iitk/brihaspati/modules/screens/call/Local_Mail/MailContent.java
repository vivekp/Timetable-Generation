package org.iitk.brihaspati.modules.screens.call.Local_Mail;

/*
 * @(#)MailContent.java	
 *
 *  Copyright (c) 2005-2007 ETRG,IIT Kanpur. 
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

import java.util.List;
import java.util.Vector;
import java.io.FileWriter;
import org.iitk.brihaspati.om.MailSend;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.om.MailSendPeer;
import org.iitk.brihaspati.om.MailReceive;
import org.iitk.brihaspati.om.MailReceivePeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MailDetail;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/** 
 * This class contains code to show Messages in specific user's Mailbox 
 *  
 * @author  <a href="mailto:chitvesh@yahoo.com">Chitvesh Dutta</a>
 * @author  <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a> 
 * @author  <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a> 
 */

public class MailContent extends SecureScreen
{
	/**
	 *   This method actually viewing the list of messages in the Local Mail 
	 *   @param data RunData instance
	 *   @param context Context instance
	 *   @see MailDetail from Util
	 *   @see UserUtil from Util
	 *   try and catch Identifies statements that user want to monitor
	 *   and catch for exceptoin.
	 */
	 private String LangFile=null;
    	public void doBuildTemplate( RunData data, Context context )
    	{
		List u=null;
		List v=null;
		try
		{
			User user=data.getUser();
                        ParameterParser pp=data.getParameters();
			Vector entry=new Vector();
			String mode=pp.getString("mode");
			context.put("mode",mode);
			Criteria crit=new Criteria();
			String stat=pp.getString("stat");
			context.put("stat",stat);
		
           		/**	
	    		* Retrive the UserId from Turbine_User table
	    		* @see UserUtil
	    		*/
			String Username=user.getName();
			int user_id=UserUtil.getUID(Username);
	    
	    		/** 
	     		* Select all the messagesid according to the ReceiverId
	     		* from the MAIL_RECEIVE table
	     		*/
			if(mode.equals("All"))
			{
				crit.add(MailReceivePeer.RECEIVER_ID,user_id);
				crit.addDescendingOrderByColumn(MailReceivePeer.MAIL_ID);
				v=MailReceivePeer.doSelect(crit);
			}
			else
			{
				int readflg=0;
				crit.add(MailReceivePeer.RECEIVER_ID,user_id);
				crit.addDescendingOrderByColumn(MailReceivePeer.MAIL_ID);
				crit.add(MailReceivePeer.MAIL_READFLAG,readflg);
	        		v=MailReceivePeer.doSelect(crit);
			}
			for(int count=0;count<v.size();count++)
			{
				 MailReceive element=(MailReceive)(v.get(count));
                         	int msg_id=element.getMailId();
			 	int read_flag=(element.getMailReadflag());
			 	String read_flag1=Integer.toString(read_flag);
				String mailType=Integer.toString((element.getMailType()));

	 			/**
	  			*   Select all the messages according to the MessageId
	  			*   from the MAIL_SEND table
	  			*/  
			 	crit=new Criteria();
			 	crit.add(MailSendPeer.MAIL_ID,msg_id);
			 	u=MailSendPeer.doSelect(crit);
				for(int count1=0;count1<u.size();count1++)
				{ 
					MailSend element1=(MailSend)(u.get(count1));
					String message_subject=(element1.getMailSubject());
					int sender_userid=(element1.getSenderId());
					String sender_name=UserUtil.getLoginName(sender_userid);
					String posttime=(element1.getPostTime()).toString();
					int Rep_status=(element1.getReplyStatus());
					/** set all value in MailDetail Bean Class
					* @see MailDetail from Util
					*/
					MailDetail mailDetail= new MailDetail();
                        		mailDetail.setSender(sender_name);
                        		mailDetail.setPDate(posttime);
                        		mailDetail.setMSubject(message_subject);
                        		mailDetail.setReplyStatus(Integer.toString(Rep_status));
                        		mailDetail.setStatus(read_flag1);
                        		mailDetail.setMailID(Integer.toString(msg_id));
					mailDetail.setMailType(mailType);
                        		entry.addElement(mailDetail);
				}	
			}
			String group=(String)user.getTemp("course_id");
			String cname=(String)user.getTemp("course_name");
		 	/**
		  	* Adds the information to the context
		  	*/
			if(entry.size()!=0)
			{
				CommonUtility.PListing(data ,context ,entry);
				context.put("status","Noblank");
			}
			else
			{	
				String path = data.getServletContext().getRealPath("/UserArea")+"/"+Username+"/Msg.txt";
				FileWriter fw = new FileWriter(path);		
				fw.write("");
				context.put("status","blank");
				String LangFile=(String)user.getTemp("LangFile");
				String mssg=new String();
				if(mode.equals("All"))
					mssg=MultilingualUtil.ConvertedString("mail_msg1",LangFile);
				else
					mssg=MultilingualUtil.ConvertedString("mail_msg5",LangFile);
				data.setMessage(Username+"---"+mssg);
			
			}
		context.put("username",Username);
		context.put("CName",cname);
		context.put("workgroup",group);
	}
	catch(Exception e){data.setMessage("The error in Mail Content !!"+e);}
    }
}



