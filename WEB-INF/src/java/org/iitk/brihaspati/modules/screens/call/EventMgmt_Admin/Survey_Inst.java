package org.iitk.brihaspati.modules.screens.call.EventMgmt_Admin;

/*
 * @(#)Survey_Inst.java	
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */


import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import java.util.List;
import java.util.Vector;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.om.SurveyResult;
import org.iitk.brihaspati.om.SurveyStudent;
import org.iitk.brihaspati.om.SurveyQuestion;
import org.iitk.brihaspati.om.SurveyResultPeer;
import org.iitk.brihaspati.om.SurveyStudentPeer;
import org.iitk.brihaspati.om.SurveyQuestionPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.screens.call.News.News_Add;

/**
 * @author  <a href="nagendrakumarpal@yahoo.co.in">Nagendra kumar singh</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a> 
 */


public class Survey_Inst extends SecureScreen
{
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
			ParameterParser pp=data.getParameters();
			String  mode=pp.getString("mode","");
			context.put("mode",mode);
		//Start admin phase
			/**
			*call News_Add template for getting the year,month and days in seprate
			*/
                        News_Add nadd = new News_Add();
                        nadd.doBuildTemplate(data, context);

			/**
			*Get the group name(courseId+IName)
			*@see ListManagement util in utils
			*/

			context.put("glist",ListManagement.getCourseList());
			
			String value=pp.getString("val");
			context.put("value",value);

		//end admin phase
		//start student phase

			User user=data.getUser();
			context.put("cname",(String)user.getTemp("course_name"));
			
			String uname=user.getName();
			int uid=UserUtil.getUID(uname);
			String course_id=(String)user.getTemp("course_id");
			Criteria crit=new Criteria();
			crit.add(SurveyQuestionPeer.CID,course_id);
			List detail=SurveyQuestionPeer.doSelect(crit);
			context.put("sdetail",detail);
			/**
			* The students can give the rank	
			* only once to the particular course
			*/
      			crit=new Criteria();
			crit.add(SurveyStudentPeer.CID,course_id);
			crit.add(SurveyStudentPeer.STU_ID,uid);
			List sdetail=SurveyStudentPeer.doSelect(crit);
			context.put("rank",sdetail.size());
		//end student phase
		//start admin phase
			if(mode.equals("sresult"))
			{
				crit=new Criteria();
				crit.addGroupByColumn(SurveyResultPeer.SURVEY_ID);
				List lst=SurveyResultPeer.doSelect(crit);
				if(lst.size()!=0)
					context.put("lst",lst);
				Vector clist=new Vector();
				for(int i=0;i<lst.size();i++)
				{
				String cid=((SurveyResult)lst.get(i)).getCid();
				String cnme=CourseUtil.getCourseName(cid);
				clist.add(cnme);
				}
				context.put("clist",clist);
			
			}
		//end admin phase
		}
		catch(Exception e)
		{
			data.setMessage("Error in survey"+e);
		}
	}
}

