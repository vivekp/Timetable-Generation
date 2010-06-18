package org.iitk.brihaspati.modules.screens.call.Assignment;

/*
 * @(#)AssView.java 
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




import java.util.Vector;
import java.util.List;

import org.iitk.brihaspati.om.AssignmentPeer; 
import org.iitk.brihaspati.om.Assignment; 
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.YearListUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;    

import org.apache.torque.util.Criteria; 
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;

 /** 
 *  This class contains code of Sending Asignment to the Assignment
 *  @author<a href="arvindjss17@yahoo.co.in">Arvind Pal</a>
 */ 



public class AssView extends SecureScreen
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
			
                        /**
                        * Get courseid  and coursename for the user currently logged in
                        *Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */

                        User user=data.getUser();
                        context.put("coursename",(String)user.getTemp("course_name"));
                        String courseid=(String)user.getTemp("course_id");
			/**  this vector is contains Topic Name  */
			Vector w=new Vector();
                        
			/**
                        * Select all the Topic Name according to GroupName
                        * from the Assignment table
                        */
                        
			Criteria crit=new Criteria();
			crit.add(AssignmentPeer.GROUP_NAME,(String)user.getTemp("course_id"));
                        List u=AssignmentPeer.doSelect(crit);
                        for(int i=0;i<u.size();i++)
                        {
                                Assignment element=(Assignment)(u.get(i));
                                String str2=(element.getTopicName());
                                w.add(str2);
                        }
			
                        context.put("allTopics",w);
                        
			/**
                        *  Retrieves the current date of the System
                        *  @see ExpiryUtil in utils
                        */
                        String cdate=ExpiryUtil.getCurrentDate("");
                        /**
                         * Get the current day from the currentdate
                         */
			context.put("cdays",cdate.substring(6,8));
			context.put("cmonth",cdate.substring(4,6));
			context.put("cyear",cdate.substring(0,4));			
			context.put("year_list",YearListUtil.getYearList());
			
                }
                catch(Exception e) {    data.setMessage("The file for uploading should have '.txt' extension !!");         }
        }
}
		
