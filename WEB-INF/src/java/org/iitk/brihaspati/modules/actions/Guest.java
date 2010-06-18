package org.iitk.brihaspati.modules.actions;

/*
 * @(#)Guest.java	
 *
 *  Copyright (c) 2002 ETRG,IIT Kanpur. 
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
 *  
 */

import java.util.Vector;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.torque.util.Criteria;
import org.apache.turbine.Turbine;
//import org.apache.turbine.util.db.Criteria;
import org.apache.turbine.modules.actions.VelocitySecureAction;
//import org.apache.turbine.services.resources.TurbineResources;
import org.apache.turbine.om.security.User;
import org.apache.turbine.om.security.Group;
import org.apache.turbine.om.security.Role;
import org.apache.turbine.services.security.TurbineSecurity;
/*import org.iitk.brihaspati.om.MessageSendPeer;
import org.iitk.brihaspati.om.MessageSend;
import org.iitk.brihaspati.om.MessageReceivePeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.apache.turbine.om.peer.BasePeer;
*/
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;

public class Guest extends VelocitySecureAction
{

					
	public boolean isAuthorized(RunData data)
	{
		boolean isAuthorized=false;
		AccessControlList acl=data.getACL();
		if(acl.hasRole("instructor",data.getUser().getTemp("course_id").toString()))
		{
			isAuthorized=true;
		}
		else{
			isAuthorized=false;
			data.setScreenTemplate(Turbine.getConfiguration().getString("template.login"));
		}
		return isAuthorized;
	}
   
	public void doPerform(RunData data,Context context) throws Exception
	{
		User user=TurbineSecurity.getUser("guest");
		Criteria crit=new Criteria();

		/**
		 * Get the user id of 'guest'
		 * @see UserUtil in utils
		 */

		String userId=Integer.toString(UserUtil.getUID("guest"));

		/**
		 * Get the group name and id in which guest role is
		 * enabled is disabled
		 * @see GroupUtil in utils
		 */

		String courseId=data.getUser().getTemp("course_id").toString();
		int GroupId=GroupUtil.getGID(courseId);

		Group group=TurbineSecurity.getGroupByName(data.getUser().getTemp("course_id").toString());
		Role role=TurbineSecurity.getRoleByName("student");
		AccessControlList acl=TurbineSecurity.getACL(user);

		if(acl.hasRole(role,group))
		{
			/**
			 * If the guest role is enabled in this group
			 * then revoke the role of guest from the group
			 */

			TurbineSecurity.revoke(user,group,role);
			
			/**
			 * Remove the notices received by the user
			 */
		
	/*		crit=new Criteria();
			Vector value=new Vector();
			crit.add(MessageReceivePeer.RECEIVER_ID,userId);
			MessageReceivePeer.doDelete(crit);
	
			/**
			 * Remove the discussions received by
			 * the user
			 *
		
			crit=new Criteria();
			value=new Vector();
			crit.add(DbReceivePeer.RECEIVER_ID,userId);
			DbReceivePeer.doDelete(crit);
	*/
		}
		else
		{
			/**
			 * If the guest role is disabled in this group
			 * then grant the role to guest in the group
			 */

			TurbineSecurity.grant(user,group,role);

			/**
			 * Check if there are any notices posted in
			 * this group or global group
			 */
	/*
			crit = new Criteria();
			crit.add(MessageSendPeer.GROUP_ID,GroupId);
			crit.or(MessageSendPeer.GROUP_ID,"1");
			Vector v=MessageSendPeer.doSelect(crit);

			/**
			 * If the notices for this group are not posted
			 * to guest then post them
			 *

			for(int j=0;j<v.size();j++){
				MessageSend element=(MessageSend)v.elementAt(j);
				if(element.getRoleId()==3 || element.getRoleId()==5){
					String messageId=(element.getMsgId()).toString();
					int msgid=Integer.parseInt(messageId);
					crit=new Criteria();
					crit.add(MessageReceivePeer.MSG_ID,messageId);
					crit.and(MessageReceivePeer.RECEIVER_ID,userId);
					Vector notice_posted=MessageReceivePeer.doSelect(crit);
					
					if(notice_posted.isEmpty()){
						String notice_insert="INSERT INTO MESSAGE_RECEIVE VALUES("+msgid+","+userId+","+GroupId+","+0+")";
						BasePeer.executeStatement(notice_insert);
					}
				}
			}

			/**
			 * Check if there are any disussions posted in
			 * this group 
			 *
	
			crit=new Criteria();
			crit.add(DbSendPeer.GROUP_ID,GroupId);
			Vector check=DbSendPeer.doSelect(crit);

			/**
			 * If the guest has not received the discussion
			 * then post the same to him
			 *

			for(int k=0;k<check.size();k++)
			{
				DbSend element=(DbSend)check.elementAt(k);
				String msgId=(element.getMsgId()).toString();
				int MsgId=Integer.parseInt(msgId);
				crit=new Criteria();
				crit.add(DbReceivePeer.MSG_ID,msgId);
				crit.add(DbReceivePeer.GROUP_ID,GroupId);
				crit.and(DbReceivePeer.RECEIVER_ID,userId);
				Vector msg_posted=DbReceivePeer.doSelect(crit);
				
				if(msg_posted.isEmpty())
				{
					crit=new Criteria();
					crit.add(DbReceivePeer.MSG_ID,MsgId);
					crit.add(DbReceivePeer.GROUP_ID,GroupId);
					crit.add(DbReceivePeer.RECEIVER_ID,userId);
					crit.add(DbReceivePeer.READ_FLAG,"0");
					DbReceivePeer.doInsert(crit);
				}
			}*/
		}
	}
}
