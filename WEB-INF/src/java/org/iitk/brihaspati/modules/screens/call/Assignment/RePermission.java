package org.iitk.brihaspati.modules.screens.call.Assignment;

/*
 * @(#) RePermission.java  
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 */


	/** 
	*  This class contains code of Post Answer to the Assignment
	*  @author<a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
	*/


import java.util.Vector;
import java.util.Date;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.om.security.User;             
import java.util.List;
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import java.util.ListIterator;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.AssignmentPeer;      
import org.iitk.brihaspati.om.Assignment;      
import org.iitk.brihaspati.om.TurbineGroupPeer;      
import org.iitk.brihaspati.om.TurbineGroup;      
import org.iitk.brihaspati.om.TurbineUserPeer;      
import org.iitk.brihaspati.om.TurbineUser;      
import org.iitk.brihaspati.om.TurbineUserGroupRolePeer;      
import org.iitk.brihaspati.om.TurbineUserGroupRole;      
import org.apache.turbine.services.servlet.TurbineServlet; 
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;    
import org.iitk.brihaspati.modules.utils.GroupUtil;         
import org.iitk.brihaspati.modules.utils.YearListUtil;

	/**
        *  This class contains code of Post Answer to the Assignment
        *  @author<a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
        */


public class RePermission  extends  SecureScreen 
{
	 /**
         * @param data RunData instance
         * @param context Context instance
         * @return nothing
         * @try and catch Identifies statements that user want to monitor
         * and catch for exceptoin.
        */

        public void doBuildTemplate(RunData data, Context context)
        {
                try
                {
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
                        context.put("coursename",(String)user.getTemp("course_name"));
                        String courseid=(String)user.getTemp("course_id");
                        
			String Assign=TurbineServlet.getRealPath("/Courses"+"/"+courseid+"/Assignment");
                        //String DB_subject1=pp.getString("topicList");
                        //context.put("topicList",DB_subject1);
                        
			//Vector v=new Vector();
                        //Vector name=new Vector();
                        Vector w=new Vector();

                        Date curDate=new Date();
                        long longCurDate= curDate.getTime();

                        Criteria crit=new Criteria();
                        crit.add(AssignmentPeer.GROUP_NAME,courseid);
          		List u=AssignmentPeer.doSelect(crit);
                        
			for(int i=0;i<u.size();i++)
                        {       Assignment element=(Assignment)(u.get(i));
                                Date date1=(element.getDueDate());
                                long longCurDate1= date1.getTime();
                                long coursedate=(longCurDate1-longCurDate)/(24*3600*1000);
                                if(coursedate<0)
                                {
                                        String str2=(element.getTopicName());
                                        w.add(str2);
                                }
                        }
                        context.put("allTopics",w);
			
			
			String cdate=ExpiryUtil.getCurrentDate("");
                        			
			/**
                        * Get the current day from the currentdate
                        */
                        
			context.put("cdays",cdate.substring(6,8));
                        context.put("cmonth",cdate.substring(4,6));
                        context.put("cyear",cdate.substring(0,4));
                        context.put("year_list",YearListUtil.getYearList());


			Vector userList=new Vector();
                        int g_id=GroupUtil.getGID(courseid);
                        userList=UserGroupRoleUtil.getUDetail(g_id,3);
                        context.put("userList",userList);
                        
                }//try
                catch(Exception e) {  }
        }
}





