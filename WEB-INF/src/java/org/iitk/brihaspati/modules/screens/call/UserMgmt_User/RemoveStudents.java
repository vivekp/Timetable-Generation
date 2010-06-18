package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)RemoveStudents.java	
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
 */

import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.util.security.AccessControlList;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.UserConfiguration;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;


/**
 * This class contains code for removing the students from a particular course.
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
 */
  
public class RemoveStudents extends SecureScreen_Instructor{
       /**
	 * This method provide the facility for the appearence of VM page.
	 * Accessing all the students to remove according to GroupId.
	 * Using GroupUtil class and call getGID() to find groupId
         * Using UserGroupRoleUtil class and call getUID() to find  UserId according group
         * @see UserGroupRoleUtil and GroupUtil
	 */

	public void doBuildTemplate(RunData data,Context context){
		/**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
                String LangFile = null;
                User user=data.getUser();
		LangFile=(String)user.getTemp("LangFile");		
		try{
			int gid=GroupUtil.getGID(user.getTemp("course_id").toString());
			int current_user_id=UserUtil.getUID(user.getName());
			
			String courseName=(String)user.getTemp("course_name");
			/**
                          * Get all the user ids having the specified role in the
                          * group selected
                          * @see UserGroupRoleUtil in utils
                          */
                        Vector UID=UserGroupRoleUtil.getUID(gid,3);
                        /**
                          * For all the user ids obtained above, add the user details in
                          * a vector and the name of group in another vector
                          */
			Vector userList=new Vector();
			List users=null;
                        for(int i=0;i<UID.size();i++)
			{ //first 'for'
                        	int uid=Integer.parseInt(UID.elementAt(i).toString());
                                Criteria crit=new Criteria();
	                        crit.add(TurbineUserPeer.USER_ID,uid);
                                try{
                                	users=TurbineUserPeer.doSelect(crit);
                                }
				catch(Exception e){data.setMessage("The error in userdeatails !!"+e);}
                                userList.addElement(users);
			}
			String status=new String();
			if(userList.isEmpty()){
				String msg1=MultilingualUtil.ConvertedString("stu_msgc",LangFile);
                                if(LangFile.endsWith("hi.properties"))
                                	data.setMessage(courseName+" "+msg1);
                                else
                                        data.setMessage(msg1+" "+courseName);

				
				//data.setMessage("No students are registered in the course "+courseName);
				
				status="empty";
			}
			else{
                                status="notempty";
				String conf=(String)user.getTemp("confParam","10");
				int list_conf=Integer.parseInt(conf);	
                                
				context.put("userConf",new Integer(list_conf));
                                context.put("userConf_string",conf);

                        	ParameterParser pp=data.getParameters();
                        	int startIndex=pp.getInt("startIndex",0);
                        	int t_size=userList.size();

                                int value[]=new int[7];
                                value=ListManagement.linkVisibility(startIndex,t_size,list_conf);

                                //k=startIndex+1
                                int k=value[6];
                                context.put("k",String.valueOf(k));

                                Integer total_size=new Integer(t_size);
                                context.put("total_size",total_size);

                                int eI=value[1];
                                Integer endIndex=new Integer(eI);
                                context.put("endIndex",endIndex);

                                //check_first shows the first five records
                                int check_first=value[2];
                                context.put("check_first",String.valueOf(check_first));

                                //check_pre shows the first the previous list to the current records
                                int check_pre=value[3];
                                context.put("check_pre",String.valueOf(check_pre));
				
				//check_last1 gives the remainder values:-
                                int check_last1=value[4];
                                context.put("check_last1",String.valueOf(check_last1));

                                //check_last shows the last records:-
                                int check_last=value[5];
                                context.put("check_last",String.valueOf(check_last));

                                context.put("startIndex",String.valueOf(eI));
                                Vector splitlist=ListManagement.listDivide(userList,startIndex,list_conf);
				context.put("userdetail",splitlist);								
			}
			context.put("course",courseName);
			context.put("status",status);
		}
		catch(Exception e){
			data.setMessage("The exception is :- "+e);
		}
	}
}
