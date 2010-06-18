package org.iitk.brihaspati.modules.screens.call.Quiz_Mgmt; 

/*
 * @(#)Create_Quiz.java
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

import java.io.File;
import java.util.List;

import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.om.Quiz;
import org.iitk.brihaspati.om.QuizPeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 *   @author  <a href="nksinghiitk@yahoo.co.in">Nagendra Kimar Singh</a>
 *   @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 *   @author  <a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */


public class Create_Quiz extends  SecureScreen
{

        /**
        * @param data RunData instance
        * @param context Context instance
        * @try and catch Identifies statements that user want to monitor
        * and catch for exceptoin.
        */
        
	public void doBuildTemplate(RunData data, Context context)
        {
                try
                {
                        String stat = data.getParameters().getString("status");
                        context.put("status",stat);
                        User user=data.getUser();
			String uname=user.getName();
                        String uid=Integer.toString(UserUtil.getUID(uname));
			ParameterParser pp=data.getParameters();   
			String quizid=pp.getString("qname","");
			
                        String courseid=(String)user.getTemp("course_id");
			String createquiz=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Quiz"+"/"+quizid);
			File f1=new File(createquiz);
			if(!f1.exists())
				f1.mkdirs();
			f1=new File(createquiz+"Quizid.xml");
			if(!f1.exists())
			{
                        	/**
                        	* Get courseid  and coursename for the user currently logged in
	                        *Put it in the context for Using in templates
        	                * @see UserUtil in Util.
                	        */
			
				Criteria crit=new Criteria();
        	                crit.add(QuizPeer.CID,courseid);
				crit.add(QuizPeer.USER_ID,uid);
				crit.add(QuizPeer.QUIZ_ID,quizid);
                        	List u = QuizPeer.doSelect(crit);
				int mmarks=0;	
	                        for(int i=0;i<u.size();i++)
        	                {
                	                Quiz element=(Quiz)(u.get(i));
                        	        mmarks=(element.getMaxMarks());
                        	}
				
				context.put("mmarks",mmarks);
				context.put("Quizid",quizid);
        		 	String username1=(String)user.getTemp("course_name");
                        	String username=data.getUser().getName();
	                        context.put("coursename",username1);
				
			} //if		
			//else
			//{	}
		}
		catch(Exception e) {
			ErrorDumpUtil.ErrorLog("The exception in create quiz screen::"+e);
			data.setMessage("See ExceptionLog:: ");
		}
	}
}			
