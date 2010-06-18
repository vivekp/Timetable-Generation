package org.iitk.brihaspati.modules.screens.call.Local_Mail;

/*
 * @(#)MailCompose.java	
 *
 *  Copyright (c) 2009 ETRG,IIT Kanpur. 
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
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;   
import org.iitk.brihaspati.om.MailSend;
import org.iitk.brihaspati.om.MailSendPeer;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.apache.turbine.util.parser.ParameterParser;  
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
     
 * @author  <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>

 */

public class MailCompose extends SecureScreen
{
	/**
	 * In this method,Send or Reply mail for local users
	 * @param data RunData
	 * @param context Context
	 * @exception a generic exception
	 */
	public void doBuildTemplate(RunData data,Context context) 
	{
		String screenMessage = "";
		User user = data.getUser();
		String uname = user.getName();
		String dir = (String)user.getTemp("course_id");
		String cname = (String)user.getTemp("course_name");
		ParameterParser pp=data.getParameters(); 
		String stat=pp.getString("status","");
		context.put("cname",cname);	
		String stat1=pp.getString("stat1","");
		context.put("stat1",stat1);
                String mode1=pp.getString("mode1","");
                context.put("mode1",mode1);
                String grpname=pp.getString("val1","");
                context.put("val",grpname);
		String msg_id="", lang="";
		lang= (String)user.getTemp("lang");
		if( lang.equals("hindi") || lang.equals("marathi"))
		{
			String tempStat = stat;
		        stat = tempStat.substring(0,5);
        	        msg_id = tempStat.substring(5);
		}
		else
		{
			msg_id=pp.getString("msgid","");
		}

	      		context.put("status",stat);
	
	        /**
		 * checks the mode from compose or reply.
		 */
		if (stat.equals("reply"))
		{
			try
			{
			
			context.put("RepMsg_id",msg_id);
			Criteria crit = new Criteria();
			crit.add(MailSendPeer.MAIL_ID,msg_id);
			List u = MailSendPeer.doSelect(crit);
			String replyto="",retrive_date="",subject="";
			for(int count = 0;count<u.size();count++)
			{
				MailSend element = (MailSend)(u.get(count));
				subject = (element.getMailSubject());
				int userid = (element.getSenderId());
				String username = UserUtil.getLoginName(userid);	
				retrive_date = (element.getPostTime()).toString();
 				String filePath = data.getServletContext().getRealPath("/UserArea")+"/"+uname+"/Msg.txt"; 
	
			/**
			 * getting the particular message.
			 */

				String str[] ;
				str = new String[1000];
				int i =0;
				int j =0;
				int start = 0;
				int stop = 0;
				String topicDesc="";
			try{
				BufferedReader br=new BufferedReader(new FileReader (filePath));
				while ((str[i]=br.readLine()) != null)
				{
				if (str[i].equals("<"+msg_id+">")) {
				start = i;	
				}
				else if(str[i].equals("</"+msg_id+">"))
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
			for( j=start+1;j<stop;j++)
			{
				topicDesc=topicDesc+ "\n"+str[j];
			}
			try{
				j = 0; 
				str = new String[1000]; 
				start =0; stop = 0;
				BufferedReader br=new BufferedReader(new FileReader (data.getServletContext().getRealPath("/UserArea")+"/"+uname+"/typedCharMsg.txt"));
				while ((str[i]=br.readLine()) != null)
				{
				if (str[i].equals("<"+msg_id+">")) {
				start = i;	
				}
				else if(str[i].equals("</"+msg_id+">"))
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
			String typedCharDesc = "";
			for(j=start+1;j<stop;j++)
			{
				typedCharDesc = typedCharDesc+str[j];
			}
				context.put("sentMess",typedCharDesc);
			String subject1 = "Re:"+subject;
			screenMessage = username+" wrote: "+"\n"+topicDesc;	
			context.put("toAddress",username);	
			context.put("message",screenMessage);	
			context.put("subject",subject1);
		}
	     }
	     catch(Exception e)
	     {
	     	data.setMessage("The error in Compose or Reply message !! "+e);
	     }
	
	
	}
   }
}
