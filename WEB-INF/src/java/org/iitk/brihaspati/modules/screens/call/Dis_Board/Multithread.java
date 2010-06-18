package org.iitk.brihaspati.modules.screens.call.Dis_Board;

/*
 * @(#)DBContent.java
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
 *
 */
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;

import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.DbDetail;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.turbine.om.security.User;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.security.AccessControlList;


import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.DbReceive;
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

import java.util.Vector;
import java.util.List;
import java.util.Date;
import javax.swing.JTree;
import java.util.LinkedList;
/**
 * This class contains code for all discussion are shows
 * @author  <a href="arvindjss17@yahoomail.co.in">Aarvind Pal</a>
 */

public class Multithread extends SecureScreen
{
	
        /**
         *   This method actually give the detail of messages in the Disscussion board in group system
         *   @param data RunData instance
         *   @param context Context instance
         *   @return nothing
	         *   @try and catch Identifies statements that user want to monitor
         *   and catch for exceptoin.
         */

        public void doBuildTemplate( RunData data, Context context )
        {
                try
                {
			ParameterParser pp=data.getParameters();
                        String perm=pp.getString("perm");
                        context.put("perm",perm);
                        String DB_subject=pp.getString("DB_subject","");
                        String Username=data.getUser().getName();
                        context.put("UserName",Username);

                        /**
                        * Retrive the UserId from Turbine_User table
                        * @see UserUtil
                        */

                        int user_id=UserUtil.getUID(Username);
                        String group=(String)data.getUser().getTemp("course_id");
                        context.put("course_id",group);
                        int group_id=GroupUtil.getGID(group);
                        String gid=Integer.toString(group_id);
                        String uid=Integer.toString(user_id);
                        context.put("group",gid);
                        context.put("userid",uid);


                        /**
                        * Select all the messagesid according to the ReceiverId
                        * from the Db_Receive table
                        */


                        LinkedList li1=new LinkedList();
                        LinkedList Reli2=new LinkedList();
                        LinkedList temp=new LinkedList();
                        LinkedList li=new LinkedList();
                        LinkedList li3=new LinkedList();



                        String mode=data.getParameters().getString("mode");
                        context.put("mode",mode);
			Criteria crit=new Criteria();
                        List v=null;


                        if(mode.equals("All"))
                        {
                                crit.add(DbReceivePeer.RECEIVER_ID,user_id);
                                crit.add(DbReceivePeer.GROUP_ID,group_id);
                                v=DbReceivePeer.doSelect(crit);
                        }
                        else
                        {
                                int readflg=0;
                                crit.add(DbReceivePeer.RECEIVER_ID,user_id);
                                crit.add(DbReceivePeer.GROUP_ID,group_id);
                                crit.add(DbReceivePeer.READ_FLAG,readflg);
                                v=DbReceivePeer.doSelect(crit);
                        }


                        /**
                        * Below code just converts the List 'v' into Vector 'entry'
                        */


                        String storestring[]=new String[10000];
                        Object new1=new Object();
                        int k=0;
                        for(int n=0;n<v.size();n++)
                        {
                                DbReceive element=(DbReceive)(v.get(n));
                                String m_id=Integer.toString(element.getMsgId());
                                li3.add(m_id);
                                new1=li3.get(n);
                                storestring[n]=(String)new1;
                                k++;
                        }
                        for(int n=0;n<k;n++)
			{
                                for(int n1=n+1;n1<k;n1++)
                                {
                                        if(storestring[n1].compareTo(storestring[n])<0)
                                        {
                                                String t=storestring[n];
                                                storestring[n]=storestring[n1];
                                                storestring[n1]=t;
                                        }
                                }
                        }
                        for(int n=0;n<v.size();n++)
                        {

                                Criteria crit1=new Criteria();
                                crit1.add(DbSendPeer.MSG_ID,storestring[n]);
                                List u=DbSendPeer.doSelect(crit1);
                                for(int n1=0;n1<u.size();n1++)
                                { // for2
                                        DbSend element1=(DbSend)(u.get(n1));
                                        String msgid=Integer.toString(element1.getReplyId());
                                        Reli2.add(msgid);
                                        li1.add(storestring[n]);
                                } // for
                        }

                        int space=0;
                        Vector spacevector=new Vector();
                        Object tem=new Object();
                        int id=0;
                        while(Reli2.size()!=0)
                        {
                                int i=0;
                                tem=li1.get(i);
                                li.add(tem);
                                temp.add(tem);
                                Reli2.remove(i);
                                li1.remove(i);
				spacevector.add(space);
                                while(temp.size()!=0)
                                {
                                        if(Reli2.contains(tem))
                                        {       id=Reli2.indexOf(tem);
                                                tem=li1.get(id);
                                                li.add(tem);
                                                temp.add(tem);
                                                Reli2.remove(id);
                                                li1.remove(id);
                                                space=space+1;
                                                spacevector.add(space);
                                        }
                                        else
                                        {
                                                if(space!=0)
                                                space=space-1;
                                                id=temp.indexOf(tem);
                                                temp.remove(id);
                                                if(temp.size()!=0)
                                                {
                                                        id=id-1;
                                                        tem=temp.get(id);
                                                }
                                        }
                                }  //while
                        }   //  while
			
                        context.put("spacevector",spacevector);
                        Vector entry=new Vector();
                        for(int n2=0;n2<li.size();n2++)
                        {
                                Object val1=new Object();
                                val1=li.get(n2);
                                String str3=(String)val1;
				//Vector entry=new Vector();
                        	for(int count=0;count<v.size();count++)
                        	{//for1
                                DbReceive element=(DbReceive)(v.get(count));
                                String m_id=Integer.toString(element.getMsgId());
                                if(str3.equals(m_id))
                                {
                                        int msg_id=Integer.parseInt(m_id);
                                        int read_flag=(element.getReadFlag());
                                        String read_flag1=Integer.toString(read_flag);

                                        /**
                                        * Select all the messages according to the MessageId
                                        * from the Db_Send table
                                        */

                                        Criteria crit1=new Criteria();
                                        crit1.add(DbSendPeer.MSG_ID,msg_id);
                                        List u=DbSendPeer.doSelect(crit1);
                                        for(int count1=0;count1<u.size();count1++)
                                        {//for2

                                                DbSend element1=(DbSend)(u.get(count1));
                                                String msgid=Integer.toString(element1.getMsgId());
                                                String message_subject=(element1.getMsgSubject());
                                                int sender_userid=(element1.getUserId());
                                                String permit=Integer.toString(element1.getPermission());
                                                String sender_name=UserUtil.getLoginName(sender_userid);
                                                context.put("msgid",msgid);
                                                context.put("contentTopic",message_subject);
                                                String sender=UserUtil.getLoginName(sender_userid);
                                                context.put("sender",sender);
                                                Date dat=(element1.getPostTime());
                                                String posttime=dat.toString();
                                                int ExDay=(element1.getExpiry());
                                                context.put("ExDay",ExDay);
                                                String exDate= null;
                                                if(ExDay == -1)
						{
                                                        exDate="infinte";
                                                }
                                                else
                                                {
                                                        exDate = ExpiryUtil.getExpired(posttime, ExDay);
                                                }

                                                DbDetail dbDetail= new DbDetail();
                                                dbDetail.setSender(sender_name);
                                                dbDetail.setPDate(posttime);
                                                dbDetail.setMSubject(message_subject);
                                                dbDetail.setStatus(read_flag1);
                                                dbDetail.setMsgID(m_id);
                                                dbDetail.setPermission(permit);
                                                dbDetail.setExpiryDate(exDate);
                                                entry.addElement(dbDetail);
                                        }
                                }//for2
                        }//for1
                        }
                        String newgroup=(String)data.getUser().getTemp("course_id");
                        String cname=CourseUtil.getCourseName(newgroup);
                        AccessControlList acl=data.getACL();
                        if(acl.hasRole("instructor",newgroup))
                        {
                                context.put("isInstructor","true");
                        }
                        context.put("user_role",data.getUser().getTemp("role"));
                        //Adds the information to the context
                        if(entry.size()!=0)
                        {
                                context.put("status","Noblank");
                                context.put("entry",entry);
                        }
                        else
                        {
                                context.put("status","blank");
				String LangFile=(String)data.getUser().getTemp("LangFile");
                                String  mssg=MultilingualUtil.ConvertedString("db-Contmsg",LangFile);
                                data.setMessage(mssg);
                        }
                        context.put("username",Username);
                        context.put("cname",(String)data.getUser().getTemp("course_name"));
                        context.put("workgroup",group);
                }//try
                catch(Exception e){data.setMessage("Exception screens [Dis_Board,DBContent.java]" + e);}
        }//method
}//class

				
