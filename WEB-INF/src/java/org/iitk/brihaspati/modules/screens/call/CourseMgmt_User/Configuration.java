package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;         

/*
 * @(#)Configuration.java        
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
import org.apache.turbine.services.security.TurbineSecurity;
import java.util.List;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.RemoteCourseUtilClient;
import org.apache.xmlrpc.XmlRpc;
import org.iitk.brihaspati.modules.screens.call.News.News_Add;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.RemoteCourses; 
import org.iitk.brihaspati.om.RemoteCoursesPeer;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.turbine.util.security.AccessControlList;

/**
 *
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class Configuration extends SecureScreen_Instructor
{
        public void doBuildTemplate( RunData data, Context context ) 
           {
                try{
                        User user=data.getUser();
 	                context.put("course",(String)user.getTemp("course_name"));
			ParameterParser pp = data.getParameters();
			String serial = pp.getString("serial","");
 	                context.put("serial",serial);
			/**
			* Put the list start index in context
			*/
			String conf=(String)user.getTemp("confParam","10");
                        int list_conf=Integer.parseInt(conf);

                        int startIndex=pp.getInt("updatestartIndex",0);
			if(startIndex > 0)
			{
                        	context.put("updatestartIndex",startIndex - list_conf);
			}

			RemoteCourses rce = null;

			if(!serial.equals(""))
			{

			 	Criteria crit=new Criteria();
				crit.add(RemoteCoursesPeer.SR_NO, serial);
				List l =  RemoteCoursesPeer.doSelect(crit);
				rce =(org.iitk.brihaspati.om.RemoteCourses)l.get(0);
				String course_id= rce.getRemoteCourseId() ;
			        context.put("c1",course_id);
                		String inst_ip=rce.getInstituteIp();
	                        context.put("c4",inst_ip);


        	                String inst_name=rce.getInstituteName();
                	        context.put("c5",inst_name);

        	                String sec_key=rce.getSecretKey();
                	        context.put("sec_key",sec_key);

                        	int status=rce.getStatus();
                        	String order=null;
				String RemoteInstructor = null;
				if(status == 1)
		                {
                	        	order = "Sell" ;
                   			RemoteInstructor = rce.getCoursePurchaser();
                        	}
                        	else if(status == 0)
                        	{
                                	order = "Purchase";
                        		RemoteInstructor = rce.getCourseSeller();
                        	}
                    		context.put("order",order);
              		        context.put("c2",RemoteInstructor);
			}//if serial
				

			/**
			* Keep xmlrpc port Alive
			*/

                        boolean bool= XmlRpc.getKeepAlive();
                        if(!bool)
			{
                        	XmlRpc.setKeepAlive(true);
			}//if
/*			String activatemsg = "";
			String Successmsg =   data.getServerName();
			while(!activatemsg.equals(Successmsg))
			{
				activatemsg = RemoteCourseUtilClient.ActivateLocalXmlRpcPort();
			}
*/
			/**
			* call new add template
			*/

			News_Add na = new News_Add();
			na.doBuildTemplate(data, context);
			/**
			* Change here for date from update action
			*/
			if(!serial.equals(""))
			{
				String Expiry = rce.getExpiryDate().toString();
                        	Expiry=Expiry.replace(" 00:00:00.0","");
                        	String sarr[]=Expiry.split("-");
				context.put("cyear",sarr[0]);
				context.put("cmonth",sarr[1]);
				context.put("cdays",sarr[2]);
			}//if
			/**
			* Call Guest 
			*/
			Guest(data,context);
        
                     }//try
                 catch(Exception e)
  	         {        
                        data.addMessage("Error in screen [call,CourseMgmt_User,RemoteCourses] is "+ e);


                 }//catch
        }//function ends

    
    public void Guest( RunData data, Context context) throws Exception
    {
			String LangFile=(String)data.getUser().getTemp("LangFile");

			String g=data.getUser().getTemp("course_id").toString();
			String cname=data.getParameters().getString("cName");
			context.put("course",cname);
			User user=TurbineSecurity.getUser("guest");
			AccessControlList acl=TurbineSecurity.getACL(user);
			if(acl.hasRole("student",g))
			{
				context.put("Guest",true);
				
			}
			else
			{
				context.put("Guest",false);
			}
    }


}//class ends
