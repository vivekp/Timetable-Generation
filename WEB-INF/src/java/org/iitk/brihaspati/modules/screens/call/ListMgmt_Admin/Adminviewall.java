package org.iitk.brihaspati.modules.screens.call.ListMgmt_Admin;

/*
 * @(#)Adminviewall.java	
 *
 *  Copyright (c) 2005-2006,2008 ETRG,IIT Kanpur. 
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
 * @author <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="manjaripal@yahoo.co.in">Manjari Pal</a>
 * @author <a href="madhavi@iitk.ac.in">Madhavi Mungole</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a> 
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a> 
 */

import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.utils.MultilingualUtil; 
import org.iitk.brihaspati.modules.utils.StringUtil; 
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil; 
import org.iitk.brihaspati.modules.utils.UserUtil; 
import org.iitk.brihaspati.modules.utils.CourseManagement; 
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.modules.utils.StudentInstructorMAP;
import org.iitk.brihaspati.om.TurbineUserGroupRole;
import org.iitk.brihaspati.om.TurbineUserGroupRolePeer;
import org.iitk.brihaspati.om.TurbineRole;
import org.iitk.brihaspati.om.TurbineRolePeer;

/**
  * This class contains code for listing of all user without admin and guest
  * Grab all the records in a table using a Peer, and
  * place the Vector of data objects in the context
  * where they can be displayed by a #foreach loop.
  */
  
public class Adminviewall extends SecureScreen_Admin{

	 /**
	  * Place all the data object in the context for use in the template.
	  * @param data RunData instance
	  * @param context Context instance
	  * @exception Exception, a generic exception
	  * @see ListManagement from Utils
	  * @return nothing
	  */
	private String file=null;  
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
			MultilingualUtil m_u=new MultilingualUtil(); 
			file=(String)data.getUser().getTemp("LangFile");  
			Vector userList=new Vector();
			ParameterParser pp=data.getParameters();
                       	String stat=pp.getString("status","");
                       	String userName=pp.getString("username","");
		        context.put("stat",stat);
			String query="";
			String valueString="";
                        String Mode=pp.getString("mode");
			if(Mode.equals("All"))
			{
				userList=ListManagement.getUserList();
                        	context.put("mode","All");
			}
			else
			{
			/**
                         * Get the search criteria and the search string
                         * from the screen
                         */
				query=pp.getString("queryList");
                        	if(query.equals(""))
                                	query=pp.getString("query");
                        /**
                          * Check for special characters
                          */
                           valueString =StringUtil.replaceXmlSpecialCharacters(pp.getString("valueString"));

		//			valueString=data.getParameters().getString("valueString");
                        	context.put("query",query);
                        	context.put("valueString",valueString);
                        	userList=ListManagement.getListBySearchString("UserWise",query,valueString);
                        	context.put("mode","Search");
			}
			String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
			int AdminConf = Integer.valueOf(AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value"));

   //                     int AdminConf = AdminProperties.getValue(path);
                        context.put("AdminConf",new Integer(AdminConf));
                        context.put("AdminConf_str",Integer.toString(AdminConf));
			//ParameterParser pp=data.getParameters();
                        int startIndex=pp.getInt("startIndex",0);
			String status=new String();
                        int t_size=userList.size();

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
                                        String viewAllMsg=m_u.ConvertedString("viewAllMsg",file);
                                        data.setMessage(viewAllMsg);                               
				}
				else
				{
					String usrWith=m_u.ConvertedString("usrWith",file);
                                        String notExist=m_u.ConvertedString("notExist",file);
				 	data.setMessage(usrWith+" "+query+" '"+valueString+"' "+ notExist);
				}
				status="empty";
                        }
                        context.put("status",status);
                       	String rName=pp.getString("Rname","instructor");
			Vector vct=new Vector();
                        Vector Cn=new Vector();
                        Vector Act=new Vector();
			if(rName.equals("instructor"))
			{
				Criteria crit=new Criteria();
				crit.addGroupByColumn(CoursesPeer.GROUP_NAME);
                        	List lst=CoursesPeer.doSelect(crit);
                        	for(int k=0;k<lst.size();k++)
                        	{
                                	Courses nm1=(Courses)lst.get(k);
                               		String Gnme=nm1.getGroupName();
                                	String cNme=nm1.getCname();
                                	String actve=(new Byte(nm1.getActive())).toString();
                                	if(Gnme.endsWith(userName))
                                	{
                                        	vct.add(Gnme);
                                        	Cn.add(cNme);
                                        	Act.add(actve);
                                	}
                        	}
                        		context.put("lst",vct);
                        		context.put("CN",Cn);
                        		context.put("actn",Act);
			}
			if(vct.size()>0)
			{
				context.put("noList","nolist");
			}
		        context.put("roleName",rName);
		}
		catch(Exception e)
		{
			data.setMessage("The error in List of all users :- "+e);
		}
	}
}

	
