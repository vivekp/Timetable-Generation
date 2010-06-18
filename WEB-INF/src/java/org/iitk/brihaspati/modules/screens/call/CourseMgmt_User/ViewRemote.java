package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;         

/*
 * @(#)ViewRemote.java        
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
 * 
 */


import  org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;  
import org.apache.turbine.om.security.User;
import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.RemoteCourses;
import org.iitk.brihaspati.om.RemoteCoursesPeer;
//import org.iitk.brihaspati.modules.screens.call.Local_Mail.MailContent;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.screens.call.News.News_Add;

/**
 * This class shows BUY/SELL Table 
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 */

public class ViewRemote extends SecureScreen_Instructor
{
        public void doBuildTemplate( RunData data, Context context ) 
           {
                try{
			//MailContent mc = new MailContent();
			ParameterParser pp=data.getParameters();
                        User user=data.getUser();
			context.put("course",(String)user.getTemp("course_name"));
			String cId=(String)user.getTemp("course_id");
			
			int serial = pp.getInt("serial",0);
			context.put("serial",serial);
			/**
			* status =0 is Buy
			* status =1 is Sell 
			*/
			int status = pp.getInt("status");
			String act = pp.getString("act");
                        context.put("act",act);
			Vector entry= getTable(status,cId);

			context.put("status",status);

			if(entry.size()!=0)
		        {
				//mc.toContext(data ,context ,entry);
				CommonUtility.PListing(data ,context ,entry);
                        	context.put("entrystatus","Noblank");
                	}
            		else
                	{
                        	context.put("entrystatus","blank");
				/**
				* If their is no entry say no entry on Configuration.vm
	                        * call new add template
        	                */
		                News_Add na = new News_Add();
                	        na.doBuildTemplate(data, context);

				setTemplate(data,"call,CourseMgmt_User,Configuration.vm");
			}

                     }//try
                 catch(Exception e)
                 {        
                        data.addMessage("Error in screen [call,CourseMgmt_User,ViewRemote] is "+ e);

                 }//catch

        }//function ends

	/**
	* This method gives table for buy or sell based on given courseId
	* @param status int
	* @param cId String
	* @return Vector
	*/
        public Vector getTable(int status ,String cId) 
           {
		Vector buy=new Vector();
                try{
			Criteria crit=new Criteria();
			crit.addDescendingOrderByColumn(RemoteCoursesPeer.SR_NO);
 	                crit.add(RemoteCoursesPeer.STATUS,status);
			crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,cId);
                        List l =  RemoteCoursesPeer.doSelect(crit);
                        RemoteCourses rce =null;
			for(int i = 0;i<l.size();i++)
			{
                        	rce  =(RemoteCourses)l.get(i);
				buy.addElement(rce);
			}
                     }//try
                 catch(Exception e)
                 {        
                  	ErrorDumpUtil.ErrorLog("Error in screen [call,CourseMgmt_User,ViewRemote] in method[getTable()] is "+ e);		
                 }//catch
		return buy;


        }//function ends
}//class ends
