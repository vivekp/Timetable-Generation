package org.iitk.brihaspati.modules.screens.call.UserMgmt_Admin;

/*
 * @(#)StudentList.java	
 *
 *  Copyright (c) 2005-2006,2009 ETRG,IIT Kanpur. 
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
 */
/**
 * @author  <a href="singh_jaivir@rediffmail.com">Jaivir Singh</a>
 * @author  <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author  <a href="shaistashekh@gmail.com">Shaista</a>
 */

import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.MultilingualUtil; 
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;
import org.apache.torque.util.Criteria;

public class StudentList extends SecureScreen_Admin
{
	 /**
	   * Retreive the list of students in the specific group
	   * @param data RunData instance
	   * @param context Context instance
	   * @exception Exception, a generic exception
	   */ 
	public void doBuildTemplate(RunData data, Context context)	
	{
		try{
			/**
        	         * Getting the value of file from temporary variable
	                 * According to selection of Language.
                	 * and replacing the String from property file.
	                 * @see MultilingualUtil in utils
        	         */
			String file=null;
                	MultilingualUtil m_u=new MultilingualUtil();
	                file=(String)data.getUser().getTemp("LangFile"); 
			/**
			 * Get the search criteria and the search string
			 * from the screen
			 */
			

			ParameterParser pp=data.getParameters();
			String query=pp.getString("queryList");
			if(query.equals(""))
		              query=pp.getString("query");
			
//			String valueString=pp.getString("value");
			/**
                          * Check for special characters
                          */
                         String  valueString =StringUtil.replaceXmlSpecialCharacters(data.getParameters().getString("value"));

			context.put("query",query);
			context.put("value",valueString);
			String str=null;

			if(query.equals("First Name"))
				str="FIRST_NAME";
			else if(query.equals("Last Name"))
				str="LAST_NAME";
			else if(query.equals("User Name"))
				str="LOGIN_NAME";
			else if(query.equals("Email"))
				str="EMAIL";

		    	/**
			  * Checks for Matching Records
			  */

			Criteria crit=new Criteria();
			crit.addJoin(TurbineUserPeer.USER_ID,TurbineUserGroupRolePeer.USER_ID);
			crit.add("TURBINE_USER",str,(Object)(valueString+"%"),crit.LIKE);
			crit.add(TurbineUserGroupRolePeer.ROLE_ID,3);
			crit.setDistinct();
			List v=null;
			v=TurbineUserPeer.doSelect(crit);
			/**
			 * Add the details of each detail in a vector
			 * and put the same in context for use in
			 * template
			 */
			if(v.size()!=0)
                        	{
				Vector userList=ListManagement.getDetails(v,"User");
                                String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
				int AdminConf=10;
                                AdminConf = Integer.parseInt(AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value"));
                                context.put("AdminConf",new Integer(AdminConf));
                                context.put("AdminConf_str",Integer.toString(AdminConf));
                                int startIndex=pp.getInt("startIndex",0);
                                int endlastIndex=pp.getInt("end");
                                context.put("endlastIndex",String.valueOf(endlastIndex));
                                String status=new String();
                                int t_size=userList.size();

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
                                context.put("ListUser",splitlist);
                        }
                        else
                        {
	                        String usrWith=m_u.ConvertedString("usrWith",file);
                                String notExist=m_u.ConvertedString("notExist",file);
				if(((String)data.getUser().getTemp("lang")).equals("hindi"))
                                	data.setMessage(usrWith+" "+query+" "+"'"+ valueString+"'"+" "+notExist ); 
				else if(((String)data.getUser().getTemp("lang")).equals("urdu"))
                                        data.setMessage(usrWith+" "+ notExist +" "+query+" "+"'"+ valueString+"'");

				else
	                                data.setMessage(usrWith+" "+query+" "+"'"+ valueString+"'"+" "+notExist ); 
				
	                        context.put("stat","ForallUser");
        	                //data.setScreenTemplate("call,UserMgmt_Admin,SelectUser.vm");
        	                //data.setScreenTemplate("call,UserMgmt_Admin,UserManagement_Admin.vm");
				
                        }

		}
		catch(Exception e){data.setMessage("The error is :- "+e);}
	}

}
