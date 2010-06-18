package org.iitk.brihaspati.modules.actions;
/*
 * @(#)Task_Action.java
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
 *  Contributors : members of ETRG, IIT Kanpur
 *
 */

import java.util.List;

import java.sql.Date;

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;

import org.iitk.brihaspati.om.UserConfiguration;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.Task;
import org.iitk.brihaspati.om.TaskPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
*
*@author  <a href="nagendrakumarpal@yahoo.co.in">Nagendra kumar singh</a>
*@author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
*
*/


public class Task_Action extends SecureAction
{

    /**
     * This method used for delete the task from database	
     * @param data Rundata
     * @param context Context
     * @exception genric exception
     *
     */
    public void doDelete(RunData data, Context context)
        throws Exception
        {
	User user = data.getUser();
	String LangFile=(String)user.getTemp("LangFile");

   /**
    * Code for getting the  task id
    */

        ParameterParser pp=data.getParameters();
        String d=pp.getString("id");
        int tid=Integer.parseInt(d);

     /**
      * Delete the task according taskid
      */
	Criteria crit = new Criteria();
	crit.add(TaskPeer.TASK_ID,tid);
	TaskPeer.doDelete(crit);
	String msg1=MultilingualUtil.ConvertedString("Task_msg6",LangFile);
        data.setMessage(msg1);

	}
    /**
     * This method used for insert the task into database	
     * @param data Rundata
     * @param context Context
     * @exception genric exception
     */
public void doInsert(RunData data, Context context) throws Exception
    {
       try{
	 User user=data.getUser();
	String LangFile=(String)user.getTemp("LangFile");
        String UserName=user.getName();

        ParameterParser pp=data.getParameters();
	String status=pp.getString("mode","");
	String exp=pp.getString("et","");
        String detailinfo1=pp.getString("title");
        String detailinfo=StringUtil.replaceXmlSpecialCharacters(detailinfo1);
	
        String sd=pp.getString("Start_day");
        String sm=pp.getString("Start_mon");
        String sy=pp.getString("Start_year");

        String sti="";
        sti= sti + sy + sm + sd;
        int sti1=Integer.parseInt(sti);
        String sdate= sy + "-" + sm + "-" + sd;
        String ed=pp.getString("End_day");
        String em=pp.getString("End_mon");
        String ey=pp.getString("End_year");
        String edate= ey + "-"  + em + "-" + ed;
		context.put("edate",edate);
        String et="";
        et= et + ey + em + ed;
        int et1=Integer.parseInt(et);

         if (et1 <= sti1)
         {
		String msg1=MultilingualUtil.ConvertedString("Task_msg5",LangFile);
	        data.setMessage(msg1);

                 setTemplate(data,"call,Task_Mgmt,TaskDIUD.vm");
                 return;
         }
	int userid=UserUtil.getUID(UserName);
	String stdate=ExpiryUtil.getCurrentDate("-");
	String currd=stdate.replace("-","");
        int currentdate=Integer.parseInt(currd);
    // Store the information according userid*******************************
	Criteria crit = new Criteria();
	String msg1="";	
	if(status.equals("update")){
		if(sti1 >= currentdate){
	 	crit = new Criteria();
        	int key1=pp.getInt("id");
		context.put("key1",key1);
		crit = new Criteria();
                crit.add(TaskPeer.TASK_ID,key1);
                List l=TaskPeer.doSelect(crit);
                int STATUS=0;
                for(int i=0;i<l.size();i++)
                {
                	STATUS=((Task)l.get(i)).getStatus();
		}
                String information="";
               	if(STATUS==1){
                	String dday=pp.getString("dday","");
                    	information="UPDATE TASK SET TITLE='"+detailinfo+"',START_DATE='"+Date.valueOf(sdate)+"',END_DATE='"+Date.valueOf(edate)+"',EXPIRY='"+exp+"',EXPIRY_DATE='"+Date.valueOf(ExpiryUtil.getExpired(edate,Integer.parseInt(exp)))+"',DUE_DAYS='"+dday+"',DUE_DATE='"+Date.valueOf(ExpiryUtil.getExpired(stdate,Integer.parseInt(dday)))+"' WHERE TASK_ID="+key1;
		}
                else{
                    	information="UPDATE TASK SET TITLE='"+detailinfo+"',START_DATE='"+Date.valueOf(sdate)+"',END_DATE='"+Date.valueOf(edate)+"',EXPIRY='"+exp+"',EXPIRY_DATE='"+Date.valueOf(ExpiryUtil.getExpired(edate,Integer.parseInt(exp)))+"' WHERE TASK_ID="+key1;
                }
		TaskPeer.executeStatement(information);
		msg1=MultilingualUtil.ConvertedString("Task_msg4",LangFile);
        	data.setMessage(msg1);
		}
                else
                        msg1=MultilingualUtil.ConvertedString("Task_msg7",LangFile);
                        data.setMessage(msg1);

	}
	else{
		if(sti1 >= currentdate){
	 	crit = new Criteria();
		crit.add(TaskPeer.TITLE,detailinfo);
		crit.add(TaskPeer.START_DATE,Date.valueOf(sdate));
		crit.add(TaskPeer.END_DATE,Date.valueOf(edate));
		crit.add(TaskPeer.EXPIRY,exp);
		crit.add(TaskPeer.EXPIRY_DATE,Date.valueOf(ExpiryUtil.getExpired(edate,Integer.parseInt(exp))));
		crit.add(TaskPeer.USER_ID,userid);
		crit.add(TaskPeer.STATUS,null);
		crit.add(TaskPeer.DUE_DATE,Date.valueOf(sdate));
		TaskPeer.doInsert(crit);
		msg1=MultilingualUtil.ConvertedString("Task_msg3",LangFile);
	        data.setMessage(msg1);
		}
                else
                        msg1=MultilingualUtil.ConvertedString("Task_msg7",LangFile);
                        data.setMessage(msg1);
	}
	}
	catch(Exception e){data.addMessage("The error is do Insert "+e);
	ErrorDumpUtil.ErrorLog(e.toString());
	}
   }
    /**
     * This method move the task from list to current list
     * @param data Rundata
     * @param context Context
     * @exception genric exception
     *
     */
public void doMove(RunData data, Context context)
        throws Exception
    {
	User user = data.getUser();
	String uname=user.getName();
        int uid=UserUtil.getUID(uname);
        String LangFile=(String)user.getTemp("LangFile");
        String tid=data.getParameters().getString("id");
        int id=Integer.parseInt(tid);
	Criteria crit = new Criteria();
        crit.add(UserConfigurationPeer.USER_ID,uid);
        List w = UserConfigurationPeer.doSelect(crit);
        int tcd=0;
        for(int k=0;k<w.size();k++)
        {
                UserConfiguration uc=(UserConfiguration)w.get(k);
                tcd=uc.getTaskConfiguration();
        }
        String tcdays=Integer.toString(tcd);
        String cdate=ExpiryUtil.getCurrentDate("-");
        String mvd=Date.valueOf(ExpiryUtil.getExpired(cdate,Integer.parseInt(tcdays))).toString();
        String rmvd=mvd.replaceAll("-","");
        int mvdate=Integer.parseInt(rmvd);
        crit=new Criteria();
        crit.add(TaskPeer.TASK_ID,tid);
        List lst=TaskPeer.doSelect(crit);
        String exd="";
        if(lst.size() > 0 ){
        exd=((Task)(lst.get(0))).getExpiryDate().toString();
        }
        String sbst=exd.substring(0,10).replaceAll("-","");
        int expdt=Integer.parseInt(sbst);
        int diffr=mvdate-expdt;
/**
 * check the taskid which is move or not
 * If moved then give the message task is already moved
 * else task is added to current task
 * and update the status
 */
                crit = new Criteria();
		crit.add(TaskPeer.TASK_ID,tid);
	        crit.and(TaskPeer.STATUS,"1");
	        List v=TaskPeer.doSelect(crit);
		int info1=0;
		String msg1="";
		if(v.size() > 0 )
			info1=((Task)(v.get(0))).getTaskId();

                           if(info1 == id)
                           {
				msg1=MultilingualUtil.ConvertedString("Task_msg2",LangFile);
			        data.setMessage(msg1);

                                setTemplate(data,"call,Task_Mgmt,TaskDIUD.vm");
                                return;
                           }
                           else
                           {
				if(diffr<1){
                                String information="UPDATE TASK SET STATUS=1,DUE_DAYS='"+tcdays+"',DUE_DATE='"+Date.valueOf(ExpiryUtil.getExpired(cdate,Integer.parseInt(tcdays)))+"' WHERE TASK_ID="+tid;
				TaskPeer.executeStatement(information);
                                msg1=MultilingualUtil.ConvertedString("Task_msg1",LangFile);
                                data.setMessage(msg1);
				}
                                else{
                                        msg1=MultilingualUtil.ConvertedString("Task_msg",LangFile);
                                        data.setMessage(msg1);
                                }
	                   }
}
    /**
     * Default action to perform if the specified action cannot be executed.
     * @param data RunData
     * @param context Context
     */

	public void doPerform(RunData data,Context context) throws Exception  {

		User user=data.getUser();
        	String LangFile=(String)user.getTemp("LangFile");
                String action=data.getParameters().getString("actionName","");
			context.put("action",action);
                if(action.equals("eventSubmit_doInsert"))
                        doInsert(data,context);
                else if(action.equals("eventSubmit_doDelete"))
                        doDelete(data,context);
                else if(action.equals("eventSubmit_doUpdate"))
                        doInsert(data,context);
                else if(action.equals("eventSubmit_doMove"))
                        doMove(data,context);
                else{
			  String msg1=MultilingualUtil.ConvertedString("action_msg",LangFile);
		          data.setMessage(msg1);
		}
          }
}




