package org.iitk.brihaspati.modules.utils;

/*
 *  @(#) UserManagement.java
 *  Copyright (c) 2005-2008, 2009 ETRG,IIT Kanpur 
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

//JDK
import java.io.File;
import java.util.List;
import java.util.Vector;
//Turbine
import org.apache.turbine.Turbine;
import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.User;
import org.apache.turbine.om.security.Role;
import org.apache.turbine.om.security.Group;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.util.security.DataBackendException;
import org.apache.turbine.util.security.UnknownEntityException;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
//Brihaspati
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.UsageDetailsPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
//Babylon
import babylon.babylonUserTool;
import babylon.babylonPasswordEncryptor;

//import org.iitk.brihaspati.modules.utils.GongUtil;
/**
 * In this class manage all utility of user
 * @author <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:satyapalsingh@gmail.com">Satyapal Singh</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 */

public class UserManagement
{
    public static String Msg=null;	
    public Boolean flag=new Boolean(true);

		/**
		 * Adds the new user in the database as well as in babylon chat server
		 * @param UName String The Login Name of new user who has to be registered
		 * @param Passwd String The password of new user who has to be registered
		 * @param FName String The first name of new user who has to be registered
		 * @param LName String The last name of new user who has to be registered
		 * @param Email String The email of new user who has to be registered
		 * @param GroupName String The group in which the user has to be registered	   
		 * @param Role String The role of the new user 
		 * @return String
		 */

	public static String CreateUserProfile(String UName,String Passwd,String FName,String LName,String Email,String GroupName,String Role,String serverName,String serverPort,String file)
	{
    		babylonUserTool tool=new babylonUserTool();	
		String message=new String();
		StringUtil S=new StringUtil();
		Criteria crit=new Criteria();
		/**
		 * Checks if there are any illegal characters in the values
		 * entered
		 */
		if(S.checkString(UName)==-1 && S.checkString(FName)==-1 && S.checkString(LName)==-1)
		{
				String userRole=new String();
			try{
				String Mail_msg=new String();
				String email_existing=new String();
				String cAlias=new String();
				String dept=new String();
				String fileName=new String();

				
				/**
			 	* Get the group and role from TurbineSecurity
			 	*/
				Group user_group=TurbineSecurity.getGroupByName(GroupName);
				Role user_role=TurbineSecurity.getRoleByName(Role);
				/**
				* Check if the user profile already exists 
				* then assign a role in group else create a
				* user profile and assign role in group
				*/
				boolean UserExist=checkUserExist(UName);
				if(UserExist==false)
				{
					try
					{
					int user_id=UserUtil.getUID(UName);
					int group_id=GroupUtil.getGID(GroupName);
					/**
					* Checks if existing user already has some 
					* role in the group if yes then the specified
					* message is given else the user is assigned
					* the role in specified group
					*/
					boolean RoleExist=checkRoleinCourse(user_id,group_id);
					if(RoleExist==false)
					{
						
						message=MultilingualUtil.ConvertedString("u_msg",file);
					}
					else
					{
						User existingUser=TurbineSecurity.getUser(UName);
						TurbineSecurity.grant(existingUser,user_group,user_role);
						crit=new Criteria();
						crit.add(TurbineUserPeer.LOGIN_NAME,UName);
						List result=TurbineUserPeer.doSelect(crit);
						email_existing=((TurbineUser)result.get(0)).getEmail();
						if(!Role.equals("author"))
						{		
							crit=new Criteria();
							crit.add(CoursesPeer.GROUP_NAME,GroupName);
							List result1=CoursesPeer.doSelect(crit);	
							cAlias=((Courses)result1.get(0)).getGroupAlias();
							dept=((Courses)result1.get(0)).getDept();
						}
						if(Role.equals("instructor")){
		                	                if(serverPort.equals("8080"))
                	                	        	userRole="newInstructor";
                        		        	else
                                        			userRole="newInstructorhttps";
                        			}
                        			else if(Role.equals("student")){
                                			if(serverPort.equals("8080"))
                                        			userRole="newStudent";
                                			else
                                        			userRole="newStudenthttps";
                        			}
                        			else{
                                			if(serverPort.equals("8080"))
                                        			userRole="newAuthor";
                                			else
                                        			userRole="newAuthorhttps";
                        			}

                        			/**
                         			 * Check if the user already exists. If the user exists then 
						 * initialize the 				
						 * mail id and the
			                         * variable which will fetch approriate message from the properties file
                        			 */
						fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");

						Mail_msg=message+MailNotification.sendMail(userRole,email_existing,cAlias,dept,"","",fileName,serverName,serverPort,file);
						
						Msg =MultilingualUtil.ConvertedString("u_msg1",file);
						String Msg2 =MultilingualUtil.ConvertedString("u_msg5",file);
						/**
						*
        					* If assigning role of existing user in existing group then
        					* received all notices and Group Discussion Board messages by
        					* existing user
						*/
						InsertMessages(UName,GroupName,Role);

						message=Msg+" "+Msg2+" "+Mail_msg;

					}
					}
					catch(Exception e)
					{
					message="The error in assign role of existing user"+e+userRole;
					
					}
				}
				else
				{
					try{
		 				User new_user=TurbineSecurity.getUserInstance();
			 			/**
			  			* Sets the data entered by the user in a blank user object
			  			*/
						java.util.Date date=new java.util.Date();
                        			new_user.setName(UName);
                       	 			new_user.setPassword(Passwd);
                        			new_user.setFirstName(FName);
                        			new_user.setLastName(LName);
                        			new_user.setCreateDate(date);
                        			//new_user.setCreated(date);
                        			new_user.setLastLogin(date);
						String email_new=new String();
						fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
                                        	userRole=new String();
	                                        if(Role.equals("instructor")){
        	                                	if(serverPort.equals("8080"))
                	                                	userRole="newInstructor";
                        	                        else
                                	                        userRole="newInstructorhttps";
                                        	}
                        			else if(Role.equals("student")){
                                			if(serverPort.equals("8080"))
                                        			userRole="newStudent";
                                			else
                                        			userRole="newStudenthttps";
                        			}
                        			else{
                                			if(serverPort.equals("8080"))
                                        			userRole="newAuthor";
                                			else
                                        			userRole="newAuthorhttps";
                        			}
                        			if(Email.equals("") || Email.equals(null))
						{
							new_user.setEmail(Email);
						}
						else
						{
							if(!Role.equals("author"))
							{
								crit=new Criteria();
        	                                        	crit.add(CoursesPeer.GROUP_NAME,GroupName);
                	                                	List result2=CoursesPeer.doSelect(crit);
                        	                        
								cAlias=((Courses)result2.get(0)).getGroupAlias();
                                	                	dept=((Courses)result2.get(0)).getDept();
							}
        	                                        email_existing=Email;
							email_new=ChkMailId(email_existing);
							new_user.setEmail(email_new);
						}
						/**
			 			* Encrypt the password entered by the user
			 			* @see EncryptionUtil in utils
			 			*/
						String encrPassword=EncryptionUtil.createDigest("MD5",Passwd);	
						/**
				 		* Adds the new user using TurbineSecurity which throws
				 		* EntityExistsException and DataBackendException
				 		*/

						TurbineSecurity.addUser(new_user,encrPassword);
						/**
						* Update last login, user quota  and create date field of turbine user
						*/
						String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
				                String SpacefPrp=AdminProperties.getValue(path,"brihaspati.user.quota.value");
				                long UQuota=new Long(SpacefPrp).longValue();
	
						int u1=UserUtil.getUID(UName);
						crit=new Criteria();
                                                crit.add(org.iitk.brihaspati.om.TurbineUserPeer.USER_ID,u1);
                                                crit.add(org.iitk.brihaspati.om.TurbineUserPeer.CREATED,date);
                                                crit.add(org.iitk.brihaspati.om.TurbineUserPeer.QUOTA,UQuota);
                                                org.iitk.brihaspati.om.TurbineUserPeer.doUpdate(crit);
						/**
                                 		* The code below does not executes if the user already exists in 
						* turbine database
                                 		*/

                                		/**
                                 		* The password sent as the parameter in encryptPassword() method of
                                 		* babylonPasswordEncryptor() is in encrypted form. Hence, the babylon
                                 		* password is double encrypted.
                                 		*/

                                		String encrPasswd_babylon=new babylonPasswordEncryptor().encryptPassword(encrPassword);

                                		/**
                                 		* Create the new user entry in the babylon chat server
                                 		*/

                                		tool.createUser(UName,encrPasswd_babylon);

						/**
				 		* Grants the new user in the role "user" in "global" group
				 		* Grants the role in specified group
				 		*/

						Group global=TurbineSecurity.getGroupByName("global");
						Role role_of_user=TurbineSecurity.getRoleByName("user");
						TurbineSecurity.grant(new_user,global,role_of_user);
						TurbineSecurity.grant(new_user,user_group,user_role);
						String NewUser= new String();
						if(serverPort.equals("8080"))
							NewUser="newUser";
						else
							NewUser="newUserhttps";
						/**
						* Create new user entry for Gong audio chat
						*
						*/
						/*
							int roleid=getRoleinCourse( u1, GroupUtil.getGID(GroupName));
							String nme=FName+LName;
							GongUtil.creatUser(UName,nme,Passwd,"default",roleid,String.valueOf(u1),Email);
						*/
						/**
                                                 * This mail will specify the user name and password of the new user
                                                 * @see MailNotification in utils
                                                 */

                                                MailNotification.sendMail(NewUser,email_new,"","",UName,Passwd,fileName,serverName,serverPort,file);
						if(Role.equals("author"))
						{
							Mail_msg=message+MailNotification.sendMail(userRole,email_new,"","","","",fileName,serverName,serverPort,file);
						}
						else
						{
							Mail_msg=message+MailNotification.sendMail(userRole,email_new,cAlias,dept,"","",fileName,serverName,serverPort,file);
						}
						/**
						  * Insert default value of configuartion parameter
						  */
						int u_id=UserUtil.getUID(UName);
						crit=new Criteria();
						crit.add(UserConfigurationPeer.USER_ID,u_id);
						UserConfigurationPeer.doInsert(crit);
						/**
						 * Create User area for the new user
						 */
						String userRealPath=TurbineServlet.getRealPath("/UserArea");
						File f=new File(userRealPath+"/"+UName);
						if(!f.exists())
						{
							boolean b=f.mkdirs();
							String cd="/"+userRealPath+"/"+UName+"/Private";
							File ff=new File(cd);
							boolean cc=ff.mkdirs();
							File f3=new File(userRealPath+"/"+UName+"/Shared");
							boolean c3=f3.mkdirs();
							Msg=MultilingualUtil.ConvertedString("u_msg3",file);
						}
						else
						{
							String Msg1 =MultilingualUtil.ConvertedString("u_msg4",file);
							String Msg2 =MultilingualUtil.ConvertedString("u_msg5",file);
							Msg=Msg1+""+Msg2;
						}
						message=Msg+" "+Mail_msg;
					}
					catch(Exception e)
					{
						message="The error in create a new user profile"+e;
						
					}
					/**
					*
        				* If register new user in existing group then
        				* received all notices and Group Discussion Board messages by new user
        				* 
					*/
					InsertMessages(UName,GroupName,Role);
				}
			}
			catch(Exception ex)
			{
				message="The exception is here !!"+ex;
			}
		}
		else
		{
			message=MultilingualUtil.ConvertedString("c_msg3",file);
		}
		/**
		 * Return the message which is set above as per the condition
		 */

		return(message);
	}
	/**
	 * In this method,Check Mailid for adding local domain in mailid
	 * @param email String The Email_id of the user
	 * @return boolean
	 */
	public static String ChkMailId(String email)
        {
        	try{
                	if(!email.equals("")){
                        	int i=email.indexOf("@");
                                if(i==-1){
					/**
					* get local domain name from TurbineResource.properties
					*/
                                        String local_domain=Turbine.getConfiguration().getString("local.domain.name","");
                                       	if (local_domain.equals(""))
					{
                                                email="";
						ErrorDumpUtil.ErrorLog("Local domain name not found in TurbineResource.properties !!");
                                        }
                                        else
					{
                                       		email=email.concat(local_domain);
                                        }
                                }
                        }
                }
                catch(Exception cx)
                {
                        ErrorDumpUtil.ErrorLog("This is the exception in check mail id :--utils(UserManagement) "+cx);
                }
                return email;
        }
	public static String DeleteInstructor(String Gname,String LangFile)
        {	
		String Message="";
		try
		{
			int gid=GroupUtil.getGID(Gname);
                        Criteria crit=new Criteria();
                        crit.add(TurbineUserGroupRolePeer.GROUP_ID,gid);
                        List lst=TurbineUserGroupRolePeer.doSelect(crit);
                        int userId=((TurbineUserGroupRole)lst.get(0)).getUserId();
                        String uid=Integer.toString(userId);
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
                        String rmsg=CourseManagement.RemoveCourse(Gname,"ByCourseMgmt",LangFile);
                        String Mail_msg=MailNotification.sendMail(subject,email,"","","","",fileName,server_name,srvrPort,LangFile);
                        Message=rmsg+" "+Mail_msg;

                }
                catch(Exception cx)
                {
                        ErrorDumpUtil.ErrorLog("The exception in DeleteInstructor method :--utils(UserManagement) "+cx);
                }
                return Message;
        }
	/**
	 * In this method,Check existing user
	 * @param uName String The user name of the user
	 * @return boolean
	 */
	public static boolean checkUserExist(String uName)
	{
		boolean userExist=false;
		try
		{
			Criteria crit=new Criteria();
			crit.add(TurbineUserPeer.LOGIN_NAME,uName);
			List v=TurbineUserPeer.doSelect(crit);
			userExist=v.isEmpty();
		}
		catch(Exception e)
		{
                        ErrorDumpUtil.ErrorLog("This is the exception in check user Exist :--utils(UserManagement) "+e);
		}
		return userExist;
	}
	/**
	 * In this method,Check role of user
	 * 
	 * @param uid Integer The userid of the user
	 * @param gid Integer The groupid of the user which registered in group
	 * @return boolean
	 */
	public static boolean checkRoleinCourse(int uid,int gid)
	{
		boolean roleExist=false;
		try
		{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,uid);
			crit.and(TurbineUserGroupRolePeer.GROUP_ID,gid);
			List v=TurbineUserGroupRolePeer.doSelect(crit);
			roleExist=v.isEmpty();
		}
		catch(Exception e){
                        ErrorDumpUtil.ErrorLog("This is the exception in check role in Course :--utils(UserManagement) "+e);
		}
		return roleExist;
	}
	/**
	 * In this method,get all role of user in particular group
	 * 
	 * @param uid Integer The userid of the user
	 * @param gid Integer The groupid of the user which registered in group
	 * @return int
	 */
	public static int getRoleinCourse(int uid,int gid)
	{
		int roleid=0;
		try
		{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,uid);
			crit.and(TurbineUserGroupRolePeer.GROUP_ID,gid);
			List v=TurbineUserGroupRolePeer.doSelect(crit);
			TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(0);
			roleid=element.getRoleId();
		}
		catch(Exception e){
                        ErrorDumpUtil.ErrorLog("This is the exception in get role in Course -utils(UserManagement) :- "+e);
			
		}
		return(roleid);
	}
	/**
	 * In this method,get details of specific user
	 * 
	 * @param uid Integer The userid of the user
	 * @return List 
	 */
	public static List getUserDetail(String uid)
	{
		List v=null;
		try
		{
			Criteria crit=new Criteria();
			if(!uid.equals("All"))
			{
				int UID=Integer.parseInt(uid);
				crit.add(TurbineUserPeer.USER_ID,UID);
				v=TurbineUserPeer.doSelect(crit);
			}
			else
			{
				int noUid[]={0,1};
				crit.addNotIn(TurbineUserPeer.USER_ID,noUid);
				v=TurbineUserPeer.doSelect(crit);
			}
		}
		catch(Exception e)
		{
                        ErrorDumpUtil.ErrorLog("This is the exception in get user details -utils(UserManagement)  :- "+e);
					
		}
		return v;
	}
	/**
	 * In this method,Update the user profile 
	 * 
	 * @param userName String The loginName of the user
	 * @param fName String The FirstName of the user 
	 * @param lName String The LastName of the user 
	 * @param eMail String The Email of the user 
	 * @return String
	 */
	public static String updateUserDetails(String userName,String fName,String lName,String eMail,String file)
	{
		String msg=new String();
		try
		{
                	User user = TurbineSecurity.getUser(userName);
                	user.setFirstName(fName);
                	user.setLastName(lName);
			if(!eMail.equals(""))
			{
				String NewEmail=ChkMailId(eMail);
				user.setEmail(NewEmail);
			}
			else
                		user.setEmail(eMail);

                	TurbineSecurity.saveUser(user);
                	String profileOf=MultilingualUtil.ConvertedString("profileOf",file);
                        String update_msg=MultilingualUtil.ConvertedString("update_msg",file);
			if(file.endsWith("hi.properties"))
                                msg=userName+" "+profileOf+" "+update_msg;
                        else if(file.endsWith("urd.properties"))
                                msg=profileOf+" "+update_msg +" "+userName;
                        else
                                msg= profileOf+" "+userName+" "+update_msg;


			ErrorDumpUtil.ErrorLog(userName +" Profile has been changed");

		}
		catch(Exception ex)
		{
			msg="Error in "+userName+" profile updatation"+ex;
		}
		return(msg);
        }
	/**
        * Delete the users from the specified group and if the
        * user is an orphan user then deletes the user profile
        * @param userName String Contains the user name of the users for removal
        * @param group_name String The name of the group in which all the above
        *                    users are registered
        * @return String
        */
        public String removeUserProfile(String userName,String group_name,String file)
	{
    		babylonUserTool tool=new babylonUserTool();	
                String message=new String();
                this.flag=new Boolean(true);
                try{
                	/**
                        * Get the 'Group' and 'Role' objects
                        * for the group and role specified in
                        * the parameters passed
                        */
                        User user=TurbineSecurity.getUser(userName);
                        Group user_group=TurbineSecurity.getGroupByName(group_name);
			int uid=UserUtil.getUID(userName);
			int gid=GroupUtil.getGID(group_name);
			int role=getRoleinCourse(uid,gid);
			String roleName=UserGroupRoleUtil.getRoleName(role);
                        Role user_role=TurbineSecurity.getRoleByName(roleName);
                        Criteria crit=new Criteria();

                        /**
                        * Delete the role of the user from the specified group
                        */

                        try{
				this.flag=new Boolean(false);
                                TurbineSecurity.revoke(user,user_group,user_role);
				this.flag=new Boolean(false);
                                String usr_role=MultilingualUtil.ConvertedString("usr's_role",file);
                                String remove=MultilingualUtil.ConvertedString("remove",file);
				String msg1="";
				if(file.endsWith("hi.properties"))
	                                 msg1= group_name+" "+usr_role+" "+remove;       
				else if(file.endsWith("urd.properties"))
                                         msg1= usr_role+" "+remove +" "+group_name;

				else
					 msg1= usr_role+" "+group_name+" "+remove;
                                /**
                                * Check if the user has any other role except 'user'
                                * in 'global' group.If not then entries related to the
                                * user from the database are removed
                                */
				String msg2="";
				int user_id=UserUtil.getUID(userName);
                                if(user_id!=0 && user_id!=1){
					int i[]={1,2};
                                       	crit.add(TurbineUserGroupRolePeer.USER_ID,user_id);
					crit.addNotIn(TurbineUserGroupRolePeer.GROUP_ID,i);
					List check=TurbineUserGroupRolePeer.doSelect(crit);
					if(check.size()==0){
                                               	/**
                                               	* Remove the login details for the user
                                               	*/	
                                               	crit=new Criteria();
                                               	crit.add(UsageDetailsPeer.USER_ID,user_id);
						UsageDetailsPeer.doDelete(crit);
						/**
                                                * Remove the Configuration details for the user
                                                */
                                                crit=new Criteria();
                                                crit.add(UserConfigurationPeer.USER_ID,user_id);
                                                UserConfigurationPeer.doDelete(crit);

                                               	/**
                                               	* Finally remove the user profile from the
                                               	* database and babylon chat server
                                               	*/
                                      		TurbineSecurity.removeUser(user);
                                               	tool.deleteUser(userName);
                                               	/**
                       				* Delete the repository from the server for
                       				* this User
                       				*/
                       				String userRealPath=TurbineServlet.getRealPath("/UserArea");
                       				String fileName=userRealPath+"/"+userName;
                       				File f=new File(fileName);
                       				SystemIndependentUtil.deleteFile(f);
						this.flag=new Boolean(false);
                                               	msg2=MultilingualUtil.ConvertedString("profile2",file);
                                       	}
	                       	} 
				if(file.endsWith("urd.properties")) {message= usr_role+" "+remove +"-"+" "+msg2+ " "+group_name;}
                                else
				message=msg1+" "+msg2;
			}
                        catch(DataBackendException dbe){
                        	this.flag=new Boolean(false);
                                message="There was an error accessing the data backend.";
                                return(message);
                        }
                        catch(UnknownEntityException uee){
                        	this.flag=new Boolean(false);
                                message="User account, group or role is not present.";
				return(message);
                        }

		} //end of 'try' block
                catch(Exception e){
                	this.flag=new Boolean(false);
                        message="The error in removal Profile !! "+e;
		} 
                return(message);
	} 
	/**
        * This method is main for removal a user
	* Delete the users from the specified group and if the
        * user is an orphan user then deletes the user profile
        * @param userName String Contains the user name of the users for removal
        * @return String
        */
	public static Vector RemoveUser(String userName,String file)
	{
        	Vector Err_type=new Vector();
		Vector Err_user=new Vector();
		Vector Err_Msg=new Vector();
		try
		{
                Criteria crit=new Criteria();
                /**
                * Get the username from the screen and find the corresponding user id
                */
		UserManagement umt=new UserManagement();
                CourseUserDetail CuDetail=new CourseUserDetail();
                int userId=UserUtil.getUID(userName);
		Vector gid_author=UserGroupRoleUtil.getGID(userId,5);
		if(gid_author.size()!=0)
		{
			for(int count1=0;count1<gid_author.size();count1++)
			{
                       		int Gid=Integer.parseInt((String)gid_author.get(count1));
				if(Gid==2)
				{
					String msg=umt.removeUserProfile(userName,"author",file);
                                        if(umt.flag.booleanValue()==false)
                                	{
                                               	CuDetail=new CourseUserDetail();
                                                CuDetail.setErr_User(userName);
                                                CuDetail.setErr_Type(msg);
                        	                Err_Msg.add(CuDetail);
                	                }
				}
			}
				
		}
                /**
                * Get all the group id in vector "grpInstructor" where the user is instructor
                */
                Vector grpInstructor=UserGroupRoleUtil.getGID(userId,2);
                Vector primary_active=new Vector();
                Vector primary_inactive=new Vector();
                Vector sec_Instructor=new Vector();
		String groupName="";
		/**
                * This 'if' checks if the user is an instructor in any
                * group. If he is not, then 'else' part is executed.
                */
                if(grpInstructor.size()!=0)
		{
                	/**
                	* This for loop will check for each for each group id
                	* in "grpInstructor" whether the user is primary instructor or not
                 	*/
			for(int count=0;count<grpInstructor.size();count++)
			{
                        	/**
                         	* Get the group id and find the corresponding group name
                         	*/
                        	int gId=Integer.parseInt((String)grpInstructor.get(count));
                        	groupName=GroupUtil.getGroupName(gId);
                        	/**
                         	* Check if the course obtained above is active
                         	* or inactive and PrimaryInstructor
				* @see CourseManagement from Utils
                         	*/
				boolean check_Primary=CourseManagement.IsPrimaryInstructor(groupName,userName);
				boolean check_Active=CourseManagement.CheckcourseIsActive(gId);
                        	/**
                         	* Check if the user is a primary instructor in
                         	* the course. If he is and the course is active
                         	* then add the group id to vector "primary_active"
                         	* else add the group id to vector "primary_inactive".
				* otherwise add the group id to "sec_Instructor".
                         	*/
                        	if(check_Primary==true)
				{
                                	if(check_Active==false)
                                       		primary_active.add(Integer.toString(gId));
                                	else
                        			primary_inactive.add(Integer.toString(gId));
                        	}
				else
				{
					sec_Instructor.add(Integer.toString(gId));
				}
                	}
                        /**
                        * This 'if' checks if the user is a primary
                        * instructor. If he is,
                        * then 'else' part is executed
                        * (condition) grpInstructor.size()!=0 && check_Primary==true
                        */
                        if(sec_Instructor.size()==0)
			{
                                /**
                                * This 'if' checks if the user is
                                * primary instructor of an inactive
                                * course. If he is not, then 'else'
                                * part is executed.
                                * (condition) grpInstructor.size()!=0  && primary_active.size()  
				* ==0 && primary_inactive.size()!=0 && check_Primary==true
                                */
				if((primary_active.size()==0) && (primary_inactive.size()!=0))
				{
					String msg1="";
                                       	/**
                                       	* The 'for' loop will execute for each of the group id
                                       	* of inactive course in which the user is primary
                                       	* instructor
                                       	*/
                                       	for(int i=0;i<primary_inactive.size();i++)
					{
	                                       	int group_id=Integer.parseInt((String)primary_inactive.get(i));
                                               	String gName=GroupUtil.getGroupName(group_id);
                                               	/**
                                               	* Get the user ids of the users who are
                                               	* registered as instructor in this course in
                                               	* vector "groups"
                                               	* @see UserGroupRoleUtil in util
  						*/
                                               	Vector groups_User=UserGroupRoleUtil.getUID(group_id);
                                               	int counter=0;
                                               	/**
                                               	* Delete each such user obtained in "group"
                                               	* except this user who is primary instructor
                                              	* @see UserUtil in utils
                                               	*/
                                               	for(counter=0;counter<groups_User.size();counter++)
						{
                                                    	int uId=Integer.parseInt((String)groups_User.get(counter));
                                                       	String uName=UserUtil.getLoginName(uId);
                                                        String msg=umt.removeUserProfile(uName,gName,file);
                                                        if(umt.flag.booleanValue()==false)
							{
                                      				CuDetail=new CourseUserDetail();
								CuDetail.setErr_User(uName);
								CuDetail.setErr_Type(msg);
								Err_Msg.add(CuDetail);
                                                	}
                                               	}
                                       		/**
                                       		* Delete the entries of the course from the
                                       		* related tables
                                       		*/
						msg1=CourseManagement.RemoveCourse(gName,"ByUserMgmt",file);
                               		}
				}
              			else if((primary_active.size()!=0) && (primary_inactive.size()==0))
				{
                              		for(int i=0;i<primary_active.size();i++)
					{
       	                                       	int g_id=Integer.parseInt((String)primary_active.get(i));
                                               	String GName=GroupUtil.getGroupName(g_id);
						CuDetail=new CourseUserDetail();
                               			CuDetail.setErr_User(userName);
                               			String remove_msg=MultilingualUtil.ConvertedString("remove_msg",file);
                                                CuDetail.setErr_Type(remove_msg);
						Err_Msg.add(CuDetail);
						}
					}
				}
				else
				{
                                       	for(int i=0;i<sec_Instructor.size();i++)
                                       	{
                                               	int gId=Integer.parseInt((String)sec_Instructor.get(i));
                                       		String gName=GroupUtil.getGroupName(gId);
                                               	String msg=umt.removeUserProfile(userName,gName,file);
						// Remove messages from Notices, Discussion Board and News 
						RemoveMessages(userId,gId);
                                               	if(umt.flag.booleanValue()==false)
                                               	{
                                                    	CuDetail=new CourseUserDetail();
                                                       	CuDetail.setErr_User(userName);
                                                       	CuDetail.setErr_Type(msg);
                                                       	Err_Msg.add(CuDetail);
                                               	}
                                       	}
                        	}
                	}
                        Vector stud_grp=UserGroupRoleUtil.getGID(userId,3);
                	if(stud_grp.size()!=0)
			{
                       		for(int i=0;i<stud_grp.size();i++)
				{
                               		int gId=Integer.parseInt((String)stud_grp.get(i));
                               		String gName=GroupUtil.getGroupName(gId);

                               		String msg=umt.removeUserProfile(userName,gName,file);
					// Remove messages from Notices, Discussion Board and News 
					RemoveMessages(userId,gId);
                               		if(umt.flag.booleanValue()==false)
					{
                                               	CuDetail=new CourseUserDetail();
						CuDetail.setErr_User(userName);
						CuDetail.setErr_Type(msg);
                                       		Err_Msg.add(CuDetail);
                              		}
                       		}
			}
		}
		catch(Exception e)
		{
			String masg="The Error " +e;
			Err_Msg.add(masg +e);
			
		}
		return(Err_Msg);
	}
	/**
        * In this method, If register new user or assigning role in existing group then
	* received all notices and Group Discussion Board messages by new user
	* or existing user
        * 
        * @param userName String Contains the name of existing user or new user
        * @param groupName String Contains the groupname of existing user or new user
        * @param role String Contains the role of existing user or new user
        */
	public static void InsertMessages(String userName,String groupName,String role)
	{
                Criteria crit = new Criteria();
		try
		{	
			/**
                         * Send the notices meant for the new user registered or assigning role
                         */
                        int role_id=0;
                        if(role.equals("instructor"))
                                role_id=2;
                        else if(role.equals("student"))
                                role_id=3;

                        int gid=GroupUtil.getGID(groupName);
                        int userid=UserUtil.getUID(userName);
			boolean userExists=checkUserExist(userName);
                        crit = new Criteria();
                        crit.add(NoticeSendPeer.GROUP_ID,gid);
                        crit.or(NoticeSendPeer.GROUP_ID,"1");
                        List v=NoticeSendPeer.doSelect(crit);

                        for(int j=0;j<v.size();j++)
			{
                                NoticeSend element=(NoticeSend)v.get(j);
                                if(element.getRoleId()==role_id || element.getRoleId()==7)
				{
                                        int noticeId=element.getNoticeId();
                                        crit=new Criteria();
                                        crit.add(NoticeReceivePeer.NOTICE_ID,noticeId);
                                        crit.add(NoticeReceivePeer.RECEIVER_ID,userid);
                                        crit.add(NoticeReceivePeer.GROUP_ID,gid);
					if(userExists==false)
					{
                                        	List notice_posted=NoticeReceivePeer.doSelect(crit);
						if(notice_posted.isEmpty())
						{
                                        		crit.add(NoticeReceivePeer.READ_FLAG,0);
                                        		NoticeReceivePeer.doInsert(crit);
						}
					}
					else
					{
                                        	crit.add(NoticeReceivePeer.READ_FLAG,0);
                                        	NoticeReceivePeer.doInsert(crit);
					}
                                }
                        }
                        /**
                        * Send the Messages(DB) meant for the new user registered or assigning role
                        */
                        crit=new Criteria();
                        crit.add(DbSendPeer.GROUP_ID,gid);
                        List check=DbSendPeer.doSelect(crit);
                        for(int k=0;k<check.size();k++)
                        {
                                DbSend element=(DbSend)check.get(k);
                                int MsgId=element.getMsgId();
                                crit=new Criteria();
                                crit.add(DbReceivePeer.MSG_ID,MsgId);
                                crit.add(DbReceivePeer.GROUP_ID,gid);
                                crit.add(DbReceivePeer.RECEIVER_ID,userid);
                                if(userExists==false)
                                {
                                        List msg_posted=DbReceivePeer.doSelect(crit);
                                        if(msg_posted.isEmpty())
                                        {
                                                crit.add(DbReceivePeer.READ_FLAG,"0");
                                                DbReceivePeer.doInsert(crit);
                                        }
                                }
                                else
                                {
                                        crit.add(DbReceivePeer.READ_FLAG,"0");
                                        DbReceivePeer.doInsert(crit);
                                }
                        }
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in exist message received by new user or assigning Role !!-utils(UserManagement)"+ex);
		}
	}		
	/**
        * In this method, If remove user from existing group then
	* removed all notices and Group Discussion Board messages
        * 
        * @param uid Integer Contains the id of existing user
        * @param gid Integer Contains the gid of existing group
        */
	public static void RemoveMessages(int uid,int gid)
	{
                Criteria crit = new Criteria();
		try
		{
			/**
                	* Delete the entries of the Notices from the related tables
                	*/
                        crit=new Criteria();
                        crit.add(NoticeSendPeer.USER_ID,uid);
                        crit.add(NoticeSendPeer.GROUP_ID,gid);
			List v=NoticeSendPeer.doSelect(crit);
			boolean entry=v.isEmpty();
			if(entry==true)
                        {	
                        	crit=new Criteria();
                        	crit.add(NoticeReceivePeer.RECEIVER_ID,uid);
                        	crit.add(NoticeReceivePeer.GROUP_ID,gid);
                        	NoticeReceivePeer.doDelete(crit);
			}
			else
			{
				NoticeSendPeer.doDelete(crit);
                        	crit=new Criteria();
                        	crit.add(NoticeReceivePeer.GROUP_ID,gid);
                        	NoticeReceivePeer.doDelete(crit);
			}
                        /**
                        * Delete the entries of the discussion board from the related tables
                        */
                        crit=new Criteria();
                        crit.add(DbSendPeer.USER_ID,uid);
                        crit.add(DbSendPeer.GROUP_ID,gid);
			v=NoticeSendPeer.doSelect(crit);
			entry=v.isEmpty();
			if(entry==true)
                        {	
                        	crit=new Criteria();
                        	crit.add(DbReceivePeer.RECEIVER_ID,uid);
                        	crit.add(DbReceivePeer.GROUP_ID,gid);
                        	DbReceivePeer.doDelete(crit);
			}
			else
			{
                        	DbSendPeer.doDelete(crit);
                        	crit=new Criteria();
                        	crit.add(DbReceivePeer.GROUP_ID,gid);
                        	DbReceivePeer.doDelete(crit);
			}
                        /**
                        * Delete the entries of the News from the related tables
                        */
                        crit=new Criteria();
                        crit.add(NewsPeer.USER_ID,uid);
                        NewsPeer.doDelete(crit);
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("The error in RemoveMessages - UserManagement.java Util !!" +ex);
		}
	}
        public String removeUserProfileWithMail(String userName, String group_name, String langFile, String info_new, String mail_id, String courseId, String dept, String userPassword, String file, String serverName, String serverPort )
	{
		String Msg=removeUserProfile(userName,group_name,langFile);
		String Mail_msg=MailNotification.sendMail(info_new,mail_id,group_name,"","","",file,serverName,serverPort,langFile);
		return Mail_msg+":"+Msg;
	}

}
