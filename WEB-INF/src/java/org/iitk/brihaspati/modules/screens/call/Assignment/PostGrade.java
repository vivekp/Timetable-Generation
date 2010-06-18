package org.iitk.brihaspati.modules.screens.call.Assignment;

/*
 * @(#) PostGrade.java
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
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
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


import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;             
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.om.Assignment;      
import org.iitk.brihaspati.om.AssignmentPeer;      
import org.iitk.brihaspati.modules.utils.GroupUtil;         
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;    
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

	 /**
	 *  This class contains code of Sending Asignment to the Assignment
	 *  @author<a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
	 */

public class PostGrade  extends  SecureScreen 
{
	
	
        public void doBuildTemplate(RunData data, Context context)
        {
                try
                {       
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
                        String Assignmentid=pp.getString("qname","");	
                        String studentname=pp.getString("studentsname","");	
			context.put("topicList",Assignmentid);
			context.put("topicList1",studentname);


			context.put("coursename",(String)user.getTemp("course_name"));
                        String courseid=(String)user.getTemp("course_id");
                        context.put("courseid",courseid);
                        Vector v=new Vector();
                        Vector name=new Vector();
                        Vector w=new Vector();
                        Date curDate=new Date();
                        long longCurDate= curDate.getTime();
                        Criteria crit=new Criteria();
                        crit.add(AssignmentPeer.GROUP_NAME,courseid);
                        List u=AssignmentPeer.doSelect(crit);
                        for(int i=0;i<u.size();i++)
                        {
                                Assignment element=(Assignment)(u.get(i));
                                Date date1=(element.getDueDate());
                                long longCurDate1= date1.getTime();
                                long coursedate=(longCurDate1-longCurDate)/(24*3600*1000);
				if(coursedate<0)
                                {

                                        String str2=(element.getTopicName());
                                        w.add(str2);
				}
                        }       //for

                        context.put("allTopics",w);
			Vector userList=new Vector();
                        int g_id=GroupUtil.getGID(courseid);
                        userList=UserGroupRoleUtil.getUDetail(g_id,3);
                        context.put("userList",userList);
		}//try
		catch(Exception e) {  }
	}
}
