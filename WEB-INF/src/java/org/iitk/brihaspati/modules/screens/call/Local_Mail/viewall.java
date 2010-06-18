package org.iitk.brihaspati.modules.screens.call.Local_Mail;

/*
 * @(#)ViewAll.java
 *
 *  Copyright (c) 2008 ETRG,IIT Kanpur.
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

import org.apache.turbine.Turbine;
import org.apache.torque.util.Criteria;
import java.util.List;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.turbine.om.security.User;
import org.apache.turbine.modules.screens.VelocitySecureScreen;
import org.apache.turbine.util.security.AccessControlList;
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.om.MailSend;
import org.iitk.brihaspati.om.MailSendPeer;
import org.iitk.brihaspati.modules.screens.call.SecureScreen ;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;


/**
 *   This class contains code for sending message to any particular user if exists.
 *
 * @author  <a href="mailto:rachanadwivedi22@gmail.com">Rachana Dwivedi</a>

 */

public class viewall extends SecureScreen{


        public void doBuildTemplate(RunData data, Context context)
        {
		try
		{
			String LangFile = null;
        	        User user=data.getUser();
                	LangFile=(String)user.getTemp("LangFile");
                        MultilingualUtil m_u=new MultilingualUtil();
			Vector userList=new Vector();
                        String course_id=(String)user.getTemp("course_id");
                        String cname=(String)user.getTemp("course_name");
                        context.put("cname",cname);
                        int g_id=GroupUtil.getGID(course_id);
                        ParameterParser pp=data.getParameters();
                        String stat=pp.getString("status","");
                        context.put("stat",stat);
			String valueString=data.getParameters().getString("valueString","");
       			String query="User Name";
			String Mode= pp.getString("mode");
			String modes=data.getParameters().getString("mod","");
                        context.put("modes",modes);
			ErrorDumpUtil.ErrorLog("\nMode"+Mode+"\nmodes"+modes);
		
			if(Mode.equals("All"))
                        {
                                userList=ListManagement.getUserList();
                                context.put("mode","All");
                        }

                        else if(Mode.equals("courseWiseAll"))
                        {
                                userList=UserGroupRoleUtil.getUDetail(g_id,3);
                                context.put("mode","ShowAll");
                        }
                         else if(Mode.equals("NotAll"))
                        {
                           context.put("mode","NotAll");
                        }

			else{
                                context.put("query",query);
                                context.put("valueString",valueString);
                                userList=ListManagement.getListBySearchString("UserWise",query,valueString);
                                context.put("mode","Search");

                            }
		

			String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                        int AdminConf = Integer.valueOf(AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value"));
                        context.put("AdminConf",new Integer(AdminConf));
                        context.put("AdminConf_str",Integer.toString(AdminConf));
                        int startIndex=pp.getInt("startIndex",0);
                        String status=new String();
                        int t_size=userList.size();
                        context.put("AdminConf",new Integer(AdminConf));
                        context.put("AdminConf_str",Integer.toString(AdminConf));
                        
				if(userList.size()!=0){

                                status="notempty";
                                int value[]=new int[7];
                                value=ListManagement.linkVisibility(startIndex,t_size,AdminConf);

                                int k=value[6];
                                context.put("k",String.valueOf(k));

                                Integer total_size=new Integer(t_size);
                                context.put("total_size",total_size);

                                int eI=value[1];
                                Integer endIndex=new Integer(eI);
                                context.put("endIndex",endIndex);

                                int check_first=value[2];
                                context.put("check_first",String.valueOf(check_first));

                                int check_pre=value[3];
                                context.put("check_pre",String.valueOf(check_pre));

                                int check_last1=value[4];
                                context.put("check_last1",String.valueOf(check_last1));

                                int check_last=value[5];
                                context.put("check_last",String.valueOf(check_last));

                                context.put("startIndex",String.valueOf(eI));
                                Vector splitlist=ListManagement.listDivide(userList,startIndex,AdminConf);
				context.put("userlist",splitlist);
			}
	
			else
			{
			  if(Mode.equals("All"))
                                {
					String msg1=MultilingualUtil.ConvertedString("stu_msgc",LangFile);
                                        if(LangFile.endsWith("hi.properties"))
                                                data.setMessage(cname+" "+msg1);
                                        else
                                                data.setMessage(msg1+" "+cname);

                                }
 			
                              else if(Mode.equals("ShowAll"))
				{
                                        String viewAllMsg=m_u.ConvertedString("viewAllMsg",LangFile);
                                        data.setMessage(viewAllMsg);
                                }
				else 
					if(valueString!="")

                                {
					String usrWith=m_u.ConvertedString("usrWith",LangFile);
                                        String notExist=m_u.ConvertedString("notExist",LangFile);
                                         data.setMessage(usrWith+" "+query+" '"+valueString+"' "+ notExist);
				}
				status="empty";
				}
                        context.put("status",status);
                }
                catch(Exception e)
                {
                        data.setMessage("The error in List of all users :- "+e);
                }
        }
}



















