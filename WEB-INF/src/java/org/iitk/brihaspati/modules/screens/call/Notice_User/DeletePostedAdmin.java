package org.iitk.brihaspati.modules.screens.call.Notice_User;

/*
 * @(#)DeletePostedAdmin.java	
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur. 
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
 * In this class,We deleted self posted notices
 * @author <a href="mailto:madhavi_mungole@hotmail.com ">Madhavi Mungole</a>
 * @author <a href="mailto:nksinghiitk@gmail.com">Nagendra Kumar Singh</a>
 */

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.Notice_SRDetail;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.om.security.TurbineUser;
import org.apache.turbine.om.security.peer.TurbineUserPeer;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;
import java.util.Vector;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class DeletePostedAdmin extends SecureScreen_Admin
{
	public void doBuildTemplate( RunData data, Context context ){
		try{
			/**
			 * Retrevies the course details and user details
			 */

			User user=data.getUser();
			Criteria crit=new Criteria();
		
			String Username=user.getName();
			crit.add(TurbineUserPeer.USERNAME,Username);
			List name=TurbineUserPeer.doSelect(crit);
			String fname=((TurbineUser)(name.get(0))).getFirstName();
			String lname=((TurbineUser)(name.get(0))).getLastName();

			context.put("loginName",Username);
			context.put("username",Username);
			context.put("firstname",fname);
			context.put("lastname",lname);
			int user_id=UserUtil.getUID(Username);
			String message_text=new String();

			/**
			 * Retreives the notice details from MESSAGE_SEND table
			 */

			crit=new Criteria();
			crit.add(NoticeSendPeer.USER_ID,Integer.toString(user_id));
			crit.addDescendingOrderByColumn(NoticeSendPeer.POST_TIME);

			List messageDetails=NoticeSendPeer.doSelect(crit);

			Vector noticeSentRec=new Vector();
			for(int i=0;i<messageDetails.size();i++)
			{
					Notice_SRDetail noticeSRDetails=new Notice_SRDetail();
                                        NoticeSend element1=(NoticeSend)(messageDetails.get(i));
                                        
					StringBuffer sb=new StringBuffer(element1.getNoticeSubject());
                                       // sb.deleteCharAt(0);

                                        noticeSRDetails.setMsgSubject(new String(sb));
                                        noticeSRDetails.setPostTime(element1.getPostTime());
                                        noticeSRDetails.setMsgId(Integer.toString(element1.getNoticeId()));
                                        noticeSentRec.add(noticeSRDetails);
			}
			if(noticeSentRec.size()!=0)
			{
				context.put("msgDetail",noticeSentRec);
			}
			else
			{
				String LangFile=data.getUser().getTemp("LangFile").toString();
				String msg=MultilingualUtil.ConvertedString("notice_msg4",LangFile);
				data.setMessage(msg);
			}
			context.put("Mas_size",Integer.toString(noticeSentRec.size()));
			String desc=data.getParameters().getString("desc","");
                	context.put("desc",desc);
                	String topicDesc="";
			if(desc.equals("Notice_Des"))
			{
				String msg_id=data.getParameters().getString("notice_id","");
				String flag=data.getParameters().getString("flag");
                		context.put("flag",flag);
				crit=new Criteria();
                        	crit.add(NoticeSendPeer.NOTICE_ID,msg_id);
				List sub=NoticeSendPeer.doSelect(crit);
				StringBuffer Notice_sub=new StringBuffer(((NoticeSend)(sub.get(0))).getNoticeSubject());
				Notice_sub.deleteCharAt(0);
                		context.put("Notice_sub",Notice_sub);

				int grid=(((NoticeSend)(sub.get(0))).getGroupId());
                		/**
                 		* Getting the actual path where the Mail is stored
                 		* @return String
                 		*/
				
				String courseid=GroupUtil.getGroupName(grid);
                		String filePath=data.getServletContext().getRealPath("/Courses")+"/"+courseid+"/Notice_Msg.txt";
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
                                		if (str[i].equals("<"+msg_id+">")) {
                                		start = i;      }
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
		}
		catch(Exception e)
		{
			data.setMessage("The Error in "+e);
		}
	}
}

