package org.iitk.brihaspati.modules.actions;

/*
 * @(#)SurveyAction.java	
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
 */

import java.sql.Date;  
import java.util.List;  
import java.util.Vector;  
import java.util.StringTokenizer;  
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.om.SurveyStudent;
import org.iitk.brihaspati.om.SurveyResultPeer;
import org.iitk.brihaspati.om.SurveyStudentPeer;
import org.iitk.brihaspati.om.SurveyQuestionPeer;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;

/**
 * @author  <a href="nagendrakumarpal@yahoo.co.in">Nagendra kumar singh</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class SurveyAction extends SecureAction
{     
	public void doSearch(RunData data,Context context)
	{
		try
		{
			String mode=data.getParameters().getString("mode");
			String group=data.getParameters().getString("val");
			int gid=GroupUtil.getGID(group);
			context.put("group",group);
			if((mode.equals("adphase")) ||(mode.equals("sresult")) ||(mode.equals("update")))
			{		  	
                                List user=null;
                                Vector user_list=new Vector();
                                Vector UID=UserGroupRoleUtil.getUID(gid,2);
                                for(int i=0;i<UID.size();i++)
                                {
                                        int uid=Integer.parseInt(UID.elementAt(i).toString());
                                        Criteria crt=new Criteria();
                                        crt.add(TurbineUserPeer.USER_ID,uid);
                                        try
                                        {
                                                user=TurbineUserPeer.doSelect(crt);
					}
                                        catch(Exception e){data.setMessage("the error in name"+e);}
                                        user_list.addElement(user);
				
                                }
                                context.put("ul",user_list);
			}
		}
		catch(Exception e)
		{
			data.setMessage("The error in search the iname"+e);
		}
	}	

	public void doInsert(RunData data, Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			ParameterParser pp=data.getParameters();
			String mode=pp.getString("mode");
			context.put("mode",mode);
			if(mode.equals("adphase"))
			{
				Criteria crit=new Criteria();

				String year=pp.getString("Start_year");
				String month=pp.getString("Start_month");
				String day=pp.getString("Start_day");
				String date=year+"-"+month+"-"+day;
				Date post_date=Date.valueOf(date);

				String ques1=pp.getString("q1","");
				String ques2=pp.getString("q2","");
				String ques3=pp.getString("q3","");
				String ques4=pp.getString("q4","");
				String ques5=pp.getString("q5","");
				String Iname=pp.getString("uname","");
				int uid=UserUtil.getUID(Iname);
				String v=pp.getString("g");
				crit.add(SurveyQuestionPeer.CID,v);
				crit.add(SurveyQuestionPeer.INST_ID,uid);
				crit.add(SurveyQuestionPeer.QUES1,ques1);
				crit.add(SurveyQuestionPeer.QUES2,ques2);
				crit.add(SurveyQuestionPeer.QUES3,ques3);
				crit.add(SurveyQuestionPeer.QUES4,ques4);
				crit.add(SurveyQuestionPeer.QUES5,ques5);
				crit.add(SurveyQuestionPeer.PDATE,post_date);
				Criteria cr=new Criteria();
				cr.add(SurveyQuestionPeer.CID,v);
				cr.add(SurveyQuestionPeer.INST_ID,uid);
				List checklist=SurveyQuestionPeer.doSelect(cr);
                           	String msg="";
				if(checklist.size()!=0)
				{
                           		 msg=MultilingualUtil.ConvertedString("survey_msg4",LangFile);
					data.setMessage(msg +v);
				}
				else{		
				SurveyQuestionPeer.doInsert(crit);
                           	msg=MultilingualUtil.ConvertedString("survey_msg1",LangFile);
				data.setMessage(msg);
				}
			}
		}
		catch(Exception e)
		{
			data.setMessage("Problem in inserting the question "+e+" !!");
		}
	}
	public void doSend(RunData data, Context context)
	{
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			User user=data.getUser();
			String sname=user.getName();
			int sid=UserUtil.getUID(sname);	
			ParameterParser pp=data.getParameters(); 
			String mode=pp.getString("mode");
			context.put("mode",mode);
			if(mode.equals("stphase")||(mode.equals("sresult")))
			{	
				int m1=Integer.parseInt(pp.getString("marks1",""));
				int m2=Integer.parseInt(pp.getString("marks2",""));
				int m3=Integer.parseInt(pp.getString("marks3",""));
				int m4=Integer.parseInt(pp.getString("marks4",""));
				int m5=Integer.parseInt(pp.getString("marks5",""));
				int marks=m1+m2+m3+m4+m5;
				int permarks=(marks*100)/25;
                                int rank;
				if(permarks>=80)
				{
					rank=1;
				}
                                else if(permarks >= 60 && permarks<80)
                                {
                                	rank=2;
                                }
                                else
                                	rank=3;
				String cid=pp.getString("cid");
				int uid=pp.getInt("iname");
				int suid=pp.getInt("sid");
				
				Criteria cri=new Criteria();
                                cri.add(SurveyStudentPeer.SURVEY_ID,suid);
                                cri.add(SurveyStudentPeer.CID,cid);
                                cri.add(SurveyStudentPeer.INST_ID,uid);
                                cri.add(SurveyStudentPeer.STU_ID,sid);
                                cri.add(SurveyStudentPeer.QUES1,m1);
                                cri.add(SurveyStudentPeer.QUES2,m2);
                                cri.add(SurveyStudentPeer.QUES3,m3);
                                cri.add(SurveyStudentPeer.QUES4,m4);
                                cri.add(SurveyStudentPeer.QUES5,m5);
                                cri.add(SurveyStudentPeer.TOTALMARKS,marks);
                                SurveyStudentPeer.doInsert(cri);
				String msg=MultilingualUtil.ConvertedString("survey_msg3",LangFile);
                                data.setMessage(msg);
				updateGrade(cid,uid,suid,LangFile);
			}
		}
		catch(Exception e)
		{
			data.setMessage("The error in store the marks"+e);
		}
	}
	public void doDelete(RunData data,Context context)
	{
		try			
		{
			String deleteList=data.getParameters().getString("deleteFileNames","");
                		String LangFile=data.getUser().getTemp("LangFile").toString();
                        	if(!deleteList.equals(""))
                        	{
	            			StringTokenizer st=new StringTokenizer(deleteList,"^");
                                	for(int i=0;st.hasMoreTokens();i++)
                                	{
                                        	String sid=st.nextToken();
                                        	Criteria crit=new Criteria();
                                        	crit.add(SurveyStudentPeer.SURVEY_ID,sid); 
                                        	SurveyStudentPeer.doDelete(crit);
                                        	crit.add(SurveyResultPeer.SURVEY_ID,sid);
                                        	SurveyResultPeer.doDelete(crit);
                                        	crit.add(SurveyQuestionPeer.SURVEY_ID,sid);
                                        	SurveyQuestionPeer.doDelete(crit);
					        String msg=MultilingualUtil.ConvertedString("c_msg9",LangFile);
                                        	data.setMessage(msg);
                                	}
                        	}
                }
                catch(Exception e)
                {
                        data.setMessage("The event could not be deleted due to exception :- "+e);
                }

	} 
	public void doGet(RunData data,Context context)
	{
		try			
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			ParameterParser pp=data.getParameters();
			String Cid=pp.getString("g");
			String name=pp.getString("uname");
			int uid=UserUtil.getUID(name);
			context.put("name",name);
			Criteria crit=new Criteria();
                        crit.add(SurveyQuestionPeer.INST_ID,uid);
                        List detail=SurveyQuestionPeer.doSelect(crit);
		        String msg=MultilingualUtil.ConvertedString("survey_msg6",LangFile);
			if(detail.size()!=0)
			{
	                        context.put("list",detail);
			}
			else
			{
				data.setMessage(msg);
			}
		}
		catch(Exception e)
		{
			data.setMessage("error in getting details"+e);
		}
	}
	public void doUpdate(RunData data,Context context)
	{
		try			
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();  
			ParameterParser pp=data.getParameters();
			int srid=pp.getInt("sid");
			String ques1=pp.getString("q1","");
			String ques2=pp.getString("q2","");
			String ques3=pp.getString("q3","");
			String ques4=pp.getString("q4","");
			String ques5=pp.getString("q5","");
			String da=pp.getString("pdate");
			String year=da.substring(0,10);	
			Date date=Date.valueOf(year);
			Criteria crit=new Criteria();
                        crit.add(SurveyQuestionPeer.SURVEY_ID,srid);
                        crit.add(SurveyQuestionPeer.PDATE,date);
                        crit.add(SurveyQuestionPeer.QUES1,ques1);
                        crit.add(SurveyQuestionPeer.QUES2,ques2);
                        crit.add(SurveyQuestionPeer.QUES3,ques3);
                        crit.add(SurveyQuestionPeer.QUES4,ques4);
                        crit.add(SurveyQuestionPeer.QUES5,ques5);
			SurveyQuestionPeer.doUpdate(crit);
			String msg=MultilingualUtil.ConvertedString("update_msg",LangFile);
			data.setMessage(msg);

		}
		catch(Exception e)
		{
			data.setMessage("error in getting details"+e);
		}
	}
	public void doGetrank(RunData data,Context context)
	{
		try{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			ParameterParser pp=data.getParameters();
			String cid=pp.getString("g");
			Criteria crit=new Criteria();
			crit.add(SurveyResultPeer.CID,cid);
                        List list=SurveyResultPeer.doSelect(crit);
			crit=new Criteria();
                        crit.add(SurveyQuestionPeer.CID,cid);
                        List list1=SurveyQuestionPeer.doSelect(crit);
                        String msg="";
                        if(list1.size()==0)
                        {
                                msg=MultilingualUtil.ConvertedString("survey_msg7",LangFile);
                                data.setMessage(msg);
                        }
                        else
                        {

				if(list.size()==0)
				{
					msg=MultilingualUtil.ConvertedString("survey_msg2",LangFile);
					data.setMessage(msg);
				}
				context.put("lst",list);
			
			}
		}
		catch(Exception ex){data.setMessage("The error in get Rank Method"+ex);}
	}
	public void updateGrade(String cid,int uid, int suid, String LangFile)
	{
		try			
		{
				int gid=GroupUtil.getGID(cid);
				Vector g=UserGroupRoleUtil.getUID(gid,3);
			int pos=(g.size()*40)/100;
			Criteria crt=new Criteria();
			crt.add(SurveyStudentPeer.INST_ID,uid);
			List lst=SurveyStudentPeer.doSelect(crt);
			if(lst.size()!=0)
			{
				if(lst.size()<pos)
				{
					ErrorDumpUtil.ErrorLog("The survey attend by student is less"+pos);
				}
				else{
					int sumRank=0;
					for(int n=0;n<lst.size();n++)
					{
						sumRank +=((SurveyStudent)lst.get(n)).getTotalmarks();
					}
	
					int permarks=(sumRank*100)/(lst.size()*25);
					String grade="";
					if(permarks>=75)
						grade="A";
					if(permarks>=60 && permarks<75)
						grade="B";
					if(permarks>=50 && permarks<=60)
						grade="C";
					if(permarks>0 && permarks<50)
						grade="D";
					Criteria crit=new Criteria();
					crit.add(SurveyResultPeer.SURVEY_ID,suid);
					List list=SurveyResultPeer.doSelect(crit);
					if(list.size()!=0)
					{
						String info="UPDATE SURVEY_RESULT SET NUM_STU_ATTD='"+lst.size()+"',TOTALMARKS_PER='"+permarks+"',GRADE='"+grade+"' WHERE SURVEY_ID="+suid;
			                        SurveyResultPeer.executeStatement(info);
					}
					else{	
						crit.add(SurveyResultPeer.NUM_STU_ATTD,lst.size());
						crit.add(SurveyResultPeer.TOTALMARKS_PER,permarks);
						crit.add(SurveyResultPeer.GRADE,grade);
						crit.add(SurveyResultPeer.CID,cid);
						SurveyResultPeer.doInsert(crit);
					}	
				}
			}
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("error in submitting the grade"+e);
		}
	}
	/**
	 * Default action to perform if the specified action is not
	 * found.
	 * @param data RunData
	 * @param context Context
	 */

	public void doPerform( RunData data,Context context ) throws Exception
	{
		String LangFile=data.getUser().getTemp("LangFile").toString();  
		String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doSearch"))
		{
			doSearch(data,context);
		}
		else if(action.equals("eventSubmit_doInsert"))
		{
			doInsert(data,context);
		}
		else if(action.equals("eventSubmit_doSend"))
		{
			doSend(data,context);
		}
		else if(action.equals("eventSubmit_doDelete"))
		{
			doDelete(data,context);
		}
		else if(action.equals("eventSubmit_doGet"))
		{
			doGet(data,context);
		}
		else if(action.equals("eventSubmit_doGetrank"))
		{
			doGetrank(data,context);
		}
		else if(action.equals("eventSubmit_doUpdate"))
		{
			doUpdate(data,context);
		}
		else
		{
			data.setMessage(msg);
		}
	}
}
