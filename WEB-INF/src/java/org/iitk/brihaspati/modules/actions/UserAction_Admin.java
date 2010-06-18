package org.iitk.brihaspati.modules.actions;

/*
 * @(#)UserAction_Admin.java	
 *
 *  Copyright (c) 2005-2006, 2008 ETRG,IIT Kanpur. 
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
//JDK
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
//Turbine
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.TurbineSecurity;
//Brihaspati
import org.iitk.brihaspati.modules.utils.GetUnzip;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.PasswordUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.RegisterMultiUser;
import org.iitk.brihaspati.modules.utils.RegisterMultiUser;
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.torque.om.TurbineUser;

import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.TurbineUserGroupRolePeer;
import org.iitk.brihaspati.om.TurbineUserGroupRole;
import org.iitk.brihaspati.om.TurbineRole;
import org.iitk.brihaspati.om.TurbineRolePeer;
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.CoursesPeer;
/** 
 * This class contains code for registration, updation profile and password or
 * removal of user in the system
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class UserAction_Admin extends SecureAction_Admin{
    	/**
    	  * ActionEvent responsible for register multiple user in the system
     	  * @param data RunData
      	  * @param context Context
	  * @see RegisterMultiUser from Utils
     	  * @return nothing
     	  */
	String LangFile="";
	
	public void doUploadMultiUser(RunData data, Context context){
        	try{

		/**
		 * Getting file value from temporary variable according to selection
		 * Replacing the static value from Property file
        	 **/
			System.gc();
			LangFile=(String)data.getUser().getTemp("LangFile");	
			ParameterParser pp=data.getParameters();
	        	FileItem file = pp.getFileItem("file");
        		String fileName=file.getName();
			if((!fileName.endsWith(".txt"))||(file.getSize()<=0))
	        	{
				 /**
                                 * Getting file value from temporary variable according to selection of Language
                                 * Replacing the static value from Property file
                                 **/ 
        	        	
				String upload_msg2=MultilingualUtil.ConvertedString("upload_msg2",LangFile);
				data.setMessage(upload_msg2);
        		}
	        	else{
				String serverName=data.getServerName();
        	                int srvrPort=data.getServerPort();
	                        String serverPort=Integer.toString(srvrPort);
				String group=pp.getString("GroupName");
        			String role=pp.getString("Role");
				Date date=new Date();
                		File f=new File(TurbineServlet.getRealPath("/tmp")+"/"+group+role+date.toString()+".txt");
				file.write(f);
        			Vector msg=RegisterMultiUser.Multi_Register(f,group,role,serverName,serverPort,LangFile);
	        		context.put("Msg",msg);
			}
		}
		catch(Exception ex)
		{
			data.setMessage("The Error in Uploading!! "+ex);
		}
    	}

    	/**
    	  * ActionEvent responsible for updating user profile in the system and
     	  * Must check the input for integrity before allowing the user info
     	  * to be update in the database.
     	  * @param data RunData
      	  * @param context Context
	  * @see UserManagement from Utils
     	  */
	public void doUpdate(RunData data, Context context)
	{
		LangFile=(String)data.getUser().getTemp("LangFile");	
	 	ParameterParser pp=data.getParameters();
                String uname=pp.getString("username");
		if(StringUtil.checkString(uname) != -1)
                       {
                               data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                               return;
                       }

		String fname=StringUtil.replaceXmlSpecialCharacters(pp.getString("firstname"));
	 	String lname=StringUtil.replaceXmlSpecialCharacters(pp.getString("lastname"));
         	String email=StringUtil.replaceXmlSpecialCharacters(pp.getString("email"));
		String msg=UserManagement.updateUserDetails(uname,fname,lname,email,LangFile);
	 	data.setMessage(msg);
	}
    	/**
     	  * ActionEvent responsible for updating user password in the system
     	  * @param data RunData
     	  * @param context Context
	  * @see PasswordUtil from Utils
     	  */
	public void doUpdatePass(RunData data, Context context)
	{
		try
		{
			LangFile=(String)data.getUser().getTemp("LangFile");
			ParameterParser pp=data.getParameters();
			/**
		 	* Get the user name and new password enterd by admin for the user.
		 	*/
              //  	String uname=pp.getString("username");
			String userName=pp.getString("username");
			if(StringUtil.checkString(userName) != -1)
                       	{
                               data.addMessage(MultilingualUtil.ConvertedString("usr_prof1",LangFile));
                               return;
                       	}
			String newPW=StringUtil.replaceXmlSpecialCharacters(pp.getString("newpass"));
			User user=TurbineSecurity.getUser(userName);
			/**
		 	* Update password entered by the admin for the user
		 	* @see PasswordUtil in utils
		 	*/
			String msg=PasswordUtil.doChangepassword(user,"",newPW,LangFile);
			data.setMessage(msg);
		}
		catch(Exception ex)
		{
			data.setMessage("Password updataion failed! Error occured "+ex);
			
			
		}
	}
	 /**
          * ActionEvent responsible for removing a user from the system
          * @param data RunData
          * @param context Context
          */
        public void doDelete(RunData data, Context context) throws Exception
	{
		MultilingualUtil mu=new MultilingualUtil();	
		LangFile=(String)data.getUser().getTemp("LangFile");
		ParameterParser pp=data.getParameters();
		String userName=pp.getString("username");
		context.put("uname",userName);
		int userId=UserUtil.getUID(userName);
		String uid=Integer.toString(userId);
		Vector Messages=new Vector();
		int in[]={1};
		Criteria crit=new Criteria();
		crit.add(TurbineUserGroupRolePeer.USER_ID,uid);
		crit.addNotIn(TurbineUserGroupRolePeer.GROUP_ID,in);
                List lst=TurbineUserGroupRolePeer.doSelect(crit);
                int roleId=((TurbineUserGroupRole)lst.get(0)).getRoleId();
                crit=new Criteria();
                crit.add(TurbineRolePeer.ROLE_ID,roleId);
                List listone=TurbineRolePeer.doSelect(crit);
                String RoleName=((TurbineRole)listone.get(0)).getRoleName();
		context.put("roleName",RoleName);
		Vector grpInstructor=UserGroupRoleUtil.getGID(userId,2);
		int gId=0;
		String Gname="";
		String server_name=TurbineServlet.getServerName();
                String srvrPort=TurbineServlet.getServerPort();
		String subject="";
		if(srvrPort.equals("8080"))
                   subject="deleteUser";
                else
                   subject="deleteUserhttps";
		String email="";
                TurbineUser element=(TurbineUser)UserManagement.getUserDetail(uid).get(0);
                email=element.getEmail();
		String fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
		String Mail_msg="";
		boolean flag=false;
		if(grpInstructor.size()!=0)
		gId=Integer.parseInt((String)grpInstructor.get(0));
		if(RoleName.equals("instructor"))
		{
			crit=new Criteria();
			crit.addGroupByColumn(CoursesPeer.GROUP_NAME);
                	List lstt=CoursesPeer.doSelect(crit);
                	for(int j=0;j<lstt.size();j++)
                	{
                		Courses nm=(Courses)lstt.get(j);
                       		Gname=nm.getGroupName();
				if(Gname.endsWith(userName))
                                {
					boolean check_Primary=CourseManagement.IsPrimaryInstructor(Gname,userName);
					context.put("pInst",check_Primary);
					flag=true;
				}
                       		String cName=nm.getCname();
                       		String active=(new Byte(nm.getActive())).toString();
				if(active.equals("0"))
				{
					String Message=CourseManagement.RemoveCourse(Gname,"ByCourseMgmt",LangFile);
					Mail_msg=MailNotification.sendMail(subject,email,"","","","",fileName,server_name,srvrPort,LangFile);
					data.setMessage(Mail_msg);
					String st1=mu.ConvertedString("delIns1",LangFile);
					String st2=mu.ConvertedString("delIns2",LangFile);
					String infrmtn=st1+" "+userName+" "+st2;
					context.put("infrmtn",infrmtn);
				}
				else
				data.setScreenTemplate("call,ListMgmt_Admin,Adminviewall.vm");
			}
		}
		if(flag)
                {
			return;
		}
		else{
			Mail_msg=MailNotification.sendMail(subject,email,"","","","",fileName,server_name,srvrPort,LangFile);
			Messages=UserManagement.RemoveUser(userName,LangFile);
			context.put("error_Messages",Messages);
			data.setMessage(Mail_msg);
		}
        }

	/**
	 * ActionEvent responsible for uploading  users photo from the system
         * @param data RunData
         * @param context Context
	 */
	public void doUploadMultiUserPhoto(RunData data, Context context){
                try{
		
		 /**
                 * Getting file value from temporary variable according to selection
                 * Replacing the static value from Property file
                 **/
			LangFile=(String)data.getUser().getTemp("LangFile");
                        ParameterParser pp=data.getParameters();
                        FileItem file = pp.getFileItem("file");
                        String fileName=file.getName();
                        if((!fileName.endsWith(".zip"))||(file.getSize()<=0))
                        {
                                 /**
                                 * Getting file value from temporary variable according to selection of Language
                                 * Replacing the static value from Property file
                                 **/

                                String upload_msg3=MultilingualUtil.ConvertedString("upload_msg3",LangFile);
                                data.setMessage(upload_msg3);
                        }
                        else{
				//here code for unzip a zip file in appropriate place
				Date date=new Date();
                                File f=new File(TurbineServlet.getRealPath("/tmp")+"/"+date.toString()+".zip");
                                file.write(f);
				File photoDir=new File(TurbineServlet.getRealPath("/images")+"/Photo/");
				photoDir.mkdirs();
				GetUnzip guz=new GetUnzip(f.getAbsolutePath(),photoDir.getAbsolutePath());	
				String photoArr[]=photoDir.list();
				for(int i=0;i<photoArr.length;i++){
					String photoName=photoArr[i];
					int indx=photoName.indexOf(".");
					if (indx > 0){
						photoName=photoName.substring(0,indx);
					}
					int uid=UserUtil.getUID(photoName);
					Criteria crit=new Criteria();
                                        crit.add(UserConfigurationPeer.USER_ID,uid);
                                        crit.add(UserConfigurationPeer.PHOTO,photoName);
                                        UserConfigurationPeer.doUpdate(crit);

				}

                                data.setMessage(MultilingualUtil.ConvertedString("upload_msg4",LangFile));
				f.delete();
			 }
                }
                catch(Exception ex){
	                data.setMessage("The Error in Photo Uploading!! "+ex);

                }
        }

	public void doExpire(RunData data, Context context)
        {
                try{
			LangFile=(String)data.getUser().getTemp("LangFile");
			String Grp_List=data.getParameters().getString("deleteFileNames","");
                        String Gname="";
			String msg="";
			if(!Grp_List.equals(""))
                	{
				StringTokenizer st=new StringTokenizer(Grp_List,"^");
                        	for(int j=0;st.hasMoreTokens();j++)
                        	{
					String str1=st.nextToken();
					String str2[]=str1.split(",");
					Gname=str2[0];
					String CourseName=str2[1];
					String act=str2[2];
					if(act.equals("1"))
						act="0";
					else
						act="1";
	                        	String CStatus=CourseManagement.UpdateCourseDetails(Gname,CourseName,"","",act,LangFile);
					msg=UserManagement.DeleteInstructor(Gname,LangFile);
				}
			}
                        data.setMessage(msg);
                }
                catch(Exception ex)
                {
                        data.setMessage("The error in doExpire method -"+ex);
                }
        }

	 /**
          * ActionEvent responsible if no method found in this action i.e. Default Method
          * @param data RunData
          * @param context Context
	  */

	public void doPerform( RunData data,Context context )
	throws Exception
    	{
        	String action=data.getParameters().getString("actionName","");
		context.put("actionName",action);
		LangFile=(String)data.getUser().getTemp("LangFile");
		ErrorDumpUtil.ErrorLog("actionName in doPerform method:==="+action);
		if(action.equals("eventSubmit_doUpdatePass"))
			doUpdatePass(data,context);
		else if(action.equals("eventSubmit_doDelete"))
			doDelete(data,context);
		else if(action.equals("eventSubmit_doExpire"))
			doExpire(data,context);
		else if(action.equals("eventSubmit_doUploadadmin"))
			doUploadMultiUser(data,context);
		else if(action.equals("eventSubmit_doUploadphoto"))
			doUploadMultiUserPhoto(data,context);
		//else if(action.equals("eventSubmit_doChangeStatus"))
		//	doChangeStatus(data,context);
		else
		{
			 /**
                          * Getting file value from temporary variable according to selection of Language
                          * Replacing the static value from Property file
                          **/ 
			String action_msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
			data.setMessage(action_msg);
			
		}
    	}
}
