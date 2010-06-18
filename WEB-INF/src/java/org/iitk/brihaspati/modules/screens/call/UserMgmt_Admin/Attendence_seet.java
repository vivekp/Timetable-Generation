package org.iitk.brihaspati.modules.screens.call.UserMgmt_Admin;

/*
 * @(#) Attendence_seet.java
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

import org.apache.velocity.context.Context;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.om.AttendenceSeetPeer;
import org.iitk.brihaspati.om.AttendenceSeet;
import org.iitk.brihaspati.om.TurbineUserPeer;
import org.iitk.brihaspati.om.TurbineUser;


import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.DbDetail;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;




/**
 * This class loads the vm file and checks for the access rights of the user if he can access this page
 * @author <a href="mailto:arvindjss17@yahoo.co.in ">Arvind Pal</a>
 */

public class Attendence_seet extends SecureScreen_Admin
{
    /*
     * Places all the data objects in the context for further use
     */

	public void doBuildTemplate( RunData data, Context context )
	{
		String LangFile =(String)data.getUser().getTemp("LangFile");
		try {	
			
			/** 
			* Select userlist from this vector userlist 
			* @see UserGroupRoleUtil 
			* and put in the context for template use 
			*/
			
			Vector userlist=new Vector();
			Vector userlistname=new Vector();
			
			userlist=UserGroupRoleUtil.getAllUID(2);

			ParameterParser pp=data.getParameters();
			String valueString =pp.getString("valueString","null");
			
			for(int i=0;i<userlist.size();i++)
			{
				int str1=Integer.parseInt(userlist.get(i).toString());
												
				Criteria crit=new Criteria();
                                crit.add(TurbineUserPeer.USER_ID,str1);
                                List check=TurbineUserPeer.doSelect(crit);
				for(int j=0;j<check.size();j++)
				{
					TurbineUser element=(TurbineUser)(check.get(j));
                                        String fname=(element.getFirstName());
                                        String lname=(element.getLastName());
					String logname=(element.getLoginName());
					if(!fname.equals(""))
						fname=fname+" "+lname;
					else
						fname=logname;		
					
					DbDetail dbdetail=new DbDetail();
                                        dbdetail.setMSubject(Integer.toString(str1));
					dbdetail.setSender(fname);
					if(fname.startsWith(valueString)) {
	                                        userlistname.add(dbdetail);
						
					}
					if(valueString.equals("null")) {
						userlistname.add(dbdetail);
					}
				}
			}
//			context.put("userlist",userlistname);			
//for pagination
			String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                        int AdminConf = Integer.valueOf(AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value"));
			context.put("AdminConf",new Integer(AdminConf));
                        context.put("AdminConf_str",Integer.toString(AdminConf));
                        int startIndex=pp.getInt("startIndex",0);
//                        String status=new String();
                        int t_size=userlistname.size();
                        	context.put("mode","All");
                        if(userlistname.size()!=0){

                          //     status="notempty";
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
                                Vector splitlist=ListManagement.listDivide(userlistname,startIndex,AdminConf);
                                context.put("userlist",splitlist);
                        }
			else {
				String usrWith=MultilingualUtil.ConvertedString("check_user",LangFile);
                                data.setMessage(usrWith);
                                context.put("total_size",t_size);
			}

			
			
//			ParameterParser pp=data.getParameters(); 
			String name=pp.getString("name","");
			String status=pp.getString("status","aa");
			if(!name.equals(""))
			{
				int  uid=Integer.parseInt(pp.getString("userId",""));
				Vector instructorList=new Vector();
				Criteria crit=new Criteria();
				crit.addDescendingOrderByColumn(AttendenceSeetPeer.ID);
        	                crit.add(AttendenceSeetPeer.USER_ID,uid);
                	        List check=AttendenceSeetPeer.doSelect(crit);
				for(int checklist=0;checklist<check.size();checklist++)
				{
					AttendenceSeet element=(AttendenceSeet)(check.get(checklist));
	                                String date=(element.getLoginDate()).toString();
					date=date.substring(0,19);
				      	instructorList.add(date);
				}
				if(check.size()==0) {

					/** This is diff template */ 
					context.put("status1","Uncheck");
					String mess=name + " " + MultilingualUtil.ConvertedString("Attendence",LangFile);
					context.put("mess",mess);	
				}
				else{
					context.put("status1","check");
					context.put("instructorList",instructorList);	
				}
			}
			context.put("status",status);
			context.put("name",name);
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("Exception in Attendence Sheet "+e);
			data.setMessage("See the Exception log !!");
		}	
    	}
}



