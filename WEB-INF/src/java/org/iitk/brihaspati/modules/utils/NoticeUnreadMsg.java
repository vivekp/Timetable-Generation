package org.iitk.brihaspati.modules.utils;
/*
 * @(#)NoticeUnreadMsg.java
 *
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur. 
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
 */
import java.util.Vector;
import java.util.List;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
 
/**
 * In this class,We have count unread notices from all groups or particuler group
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */


public class NoticeUnreadMsg{
	/**
	* Count unread notice messages
	* @param userId integer
	* @param role_id integer
	* @param groupName String
	* @return Vector
	*/
	public static Vector getUnreadNotice(int userId,int role_id,String groupName)
	{
		Vector unread_UserMsg=new Vector();
		/**
                  * Get group ids in which the user registered
                  * @see UserGroupRole in utils
                  */
                try
		{
			Vector g_id=new Vector();
                        if(groupName.equals("All"))
			{
				g_id=UserGroupRoleUtil.getGID(userId,role_id);
			}
			else
			{
				int Gid=GroupUtil.getGID(groupName);
				g_id.add(Integer.toString(Gid));
			}
                        int unread_messages=0;
                        int count1=0,count2=0;
                        String msgId=new String();
                        String groupId=new String();

                        /**
                         * This for loop will get the details regarding
                         * the messages for each group id retrieved above
                         */

                         Criteria crit=new Criteria();
                        for(int j=0;j<g_id.size();j++)
                        {
                                groupId=(String)g_id.elementAt(j);
                                unread_messages=0;


                        /**
                          * Get the messages from database where
                          * group Id either 1 or specified in
                          * the variable "groupId"
                          */

                          crit=new Criteria();
                          crit.add(NoticeSendPeer.GROUP_ID,groupId);
                          crit.or(NoticeSendPeer.GROUP_ID,"1");
                          List v1=NoticeSendPeer.doSelect(crit);

                          /**
                            * This for loop retreives the number of
                            * unread messages
			  */

                          for(count1=0;count1<v1.size();count1++){
                                NoticeSend element=(NoticeSend)(v1.get(count1));
                                int role=element.getRoleId();
                                msgId=Integer.toString(element.getNoticeId());

                                /**
                                  * The if loop below will get those messages from
                                  * Notice_RECIVE table for which the role id is either
                                  * 2(instructor) or 7(global)
                                 */

                                 if(role==2 || role==3 || role==7){
                                        crit=new Criteria();
                                        crit.add(NoticeReceivePeer.NOTICE_ID,msgId);
                                        crit.and(NoticeReceivePeer.RECEIVER_ID,userId);
                                        crit.and(NoticeReceivePeer.READ_FLAG,"0");
                                        List v2=NoticeReceivePeer.doSelect(crit);

                                        /**
                                         * "unread_messages" contains the number
                                         * of unread messages for the instructor in a
                                         * specific group
                                         */

                                         unread_messages+=v2.size();
                                        }
                                }
                                String unread_mess=Integer.toString(unread_messages);
                                unread_UserMsg.add(unread_mess);
                        }
			}
			catch(Exception ex)
			{
				
			}
		return unread_UserMsg;
	}
}
