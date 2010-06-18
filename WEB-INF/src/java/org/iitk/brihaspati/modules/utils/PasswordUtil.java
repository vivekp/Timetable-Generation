package org.iitk.brihaspati.modules.utils;

/*(#)PasswordUtil.java
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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

/**
 * @author: <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar Trivedi</a>
 * @author: <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 */
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.security.TurbineSecurity;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import babylon.babylonUserTool;
import babylon.babylonPasswordEncryptor;
/**
 * This is a util class used to change the password of the user
 */
public class PasswordUtil{

	/**
	 * Used to change the password
	 * @param user User The user object for the user for whom
	 *  password is to be modified
	 * @param oldPassword String Old password of user
	 * @param newPassword String New password to be update
	 * @return String
	 */
	public static String doChangepassword(User user, String oldPassword,String newPassword,String file){
                /**
                 * Getting file value from temporary variable according to selection of Language
                 * Replacing the static value from Property file
                 **/
		MultilingualUtil m_u=new MultilingualUtil();
		babylonUserTool tool=new babylonUserTool();
		String message=new String();
		try{
			/**
		  	 * Get the user name of the user from User object
			 */
			String userName=user.getName();
			/**
			 * Encrypt the new password
			 * @see EncryptionUtil in utils
			 */
			String encryptPasswd=EncryptionUtil.createDigest("MD5",newPassword);
			/**
		  	 * Get the original password of the user from
		  	 * the user object
			 */
			String existingPassword=user.getPassword();
			if(!oldPassword.equals("")){
				/**
				 * Compare the old password entered by
				 * the user with the one in database.If
				 * it is not same then sent the error
				 * message
				 */
				int result=existingPassword.compareTo(EncryptionUtil.createDigest("MD5",oldPassword));
				if(result!=0){
                		/**
                 		* @param file Getting file value from temporary variable according to selection of Language
                 		* @param brih_pwdUpdate Replacing the static value from Property file
                 		**/
					message=m_u.ConvertedString("brih_pwdUpdate",file);
					return message;
				}
			}
			
			/**
			 * Set the new password in the user object of
			 * user and save it
			 */
			user.setPassword(encryptPasswd);
			TurbineSecurity.saveUser(user);
			/**
			 * Encrypt the new passoword using babylonPasswordEncryptor. Here,the parameter 
			 * that has to be passed to babylonPasswordEncryptor should be the encrypted one.
                         * Hence, the resultant password in babylon chat server will be double encrypted.
			 */
			String babylonPassword=new babylonPasswordEncryptor().encryptPassword(encryptPasswd);		
			/**
                         * Delete the user from babylon chat server
                         */
                        String msg=new String();
                        try{
                                tool.deleteUser(userName);
                        }catch(Exception e){
                                msg="Cannot delete the user's account from chat server !!"+e;
                        }
                        /**
                         * Create user into babylon chat server with new
                         * password
                         */
                        try{
                                tool.createUser(userName,babylonPassword);
                        }catch(Exception ex){
                                msg="Unable to create new user in chat server !!"+ex;
                        }
	                /**
	                 * Getting file value from temporary variable according to selection of Language
	                 * Replacing the static value from Property file
	                 **/
			String pwdOf=m_u.ConvertedString("pwdOf",file);
			String pwdChangeSuccess=m_u.ConvertedString("pwdChangeSuccess",file);
			 if(file.endsWith("hi.properties")) 
				message=userName+" " +pwdOf+" " +pwdChangeSuccess +msg;
                        else if(file.endsWith("urd.properties"))
                                message=pwdOf+" "+pwdChangeSuccess+" "+userName +msg;

			else
				message=pwdOf+" "+userName+" " +pwdChangeSuccess +msg;
		}
		catch(Exception exc)
		{
	                /**
	                 * Getting file value from temporary variable according to selection of Language
	                 * Replacing the static value from Property file
	                 **/
			message="The error in PasswordUtil :- "+exc;
		}
		return message;
	}
}
