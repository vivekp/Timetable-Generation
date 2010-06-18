package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt;

/*
 * @(#)Quiz_Detail.java	
 *
 *  Copyright (c) 2007 ETRG,IIT Kanpur. 
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
import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.QuizDetail;
import java.io.File;

/**
 *   This class contains code for all discussions in workgroup
 *   Compose a discussion and reply.
 *   @author  <a href="nksingh@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */


public class Quiz_Detail extends SecureScreen
{
    	public void doBuildTemplate( RunData data, Context context )
	{
		try
                {
                        User user=data.getUser();
                        String uname=user.getName();
                        int userid=UserUtil.getUID(uname);
                        String uid=Integer.toString(userid);
                        String courseid=(String)user.getTemp("course_id");
			//String mode=data.getParameters().getString("mode");
			//context.put("mode",mode);
			
                        Vector vect=new Vector();
                        Criteria crit=new Criteria();
                        crit.add(QuizPeer.CID,courseid);
                        crit.add(QuizPeer.USER_ID,uid);
                        List u = QuizPeer.doSelect(crit);
                        for(int i=0;i<u.size();i++)
                        {
                                Quiz element=(Quiz)(u.get(i));
                                //vect.add((element.getQuizId()));
				String quizid=(element.getQuizId()); 
				QuizDetail qDetail= new QuizDetail();
                                qDetail.setquizid(quizid);
				String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
				File f1=new File(createquiz+"/Quizid.xml");
				if(!f1.exists())	
					quizid="0";			
				else
					quizid="1";
				qDetail.setsqid(quizid);
				vect.add(qDetail);			
			}
			context.put("Quizid",vect);
                        context.put("courselist",(String)user.getTemp("course_name"));
			crit=new Criteria();
                        crit.add(QuizPeer.CID,courseid);
                        crit.add(QuizPeer.USER_ID,uid);
                        crit.addGroupByColumn(QuizPeer.QUIZ_ID);
                        List lst=QuizPeer.doSelect(crit);
			String emessage="";
			String qdate="";
                        for(int j=0;j<lst.size();j++){
                        	Quiz detail=(Quiz)lst.get(j);
                                String title=detail.getQuizTitle();
                                String stime=detail.getStartTime().toString();
                                String etime=detail.getEndTime().toString();
                                String ques_date= detail.getPostDate().toString();
                                qdate=ques_date.substring(0,10);
                                int mmarks=detail.getMaxMarks();
				emessage="Title-"+title+" "+"Starttime-"+stime+" "+"Endtime-"+etime+" "+"Quizdate-"+qdate+" "+"Maxmarks-"+mmarks;
			}
			context.put("message",emessage);
			context.put("quezdate",qdate);
	        }	
		catch(Exception ex)
        	{
			ErrorDumpUtil.ErrorLog("The exception in detail quiz file!!"+ex);
			data.setMessage("See ExceptionLog !! ");
                }
	}
}


