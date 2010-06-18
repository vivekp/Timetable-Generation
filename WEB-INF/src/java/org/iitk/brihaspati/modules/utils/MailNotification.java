package org.iitk.brihaspati.modules.utils;

/*@(#)MailNotification.java
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
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;
import java.util.Properties;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.Email;
import org.apache.turbine.Turbine;
import java.io.FileOutputStream;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.apache.turbine.services.servlet.TurbineServlet;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
/**
 * This class is used to send the mail for the concerned activity
 * @author <a href=satyapalsingh@gmail.com>Satyapal Singh</a>
 * @author <a href="mailto:shaistashekh@hotmail.com">Shaista Bano</a>
 * modify 31-08-2005, 20-03-2009
 */

public class MailNotification{

	private static StringBuffer message;	
        
	/**
	 * This method extracts the message string from brihaspati.properties and replaces
	 * the tokens in the same
	 * 
	 * @param info String Portion of a property in brihaspati.properties file
	 * @param course_id String Course ID
	 * @param dept_name String Name of department
	 * @param uName String User Name 
	 * @param uPassword String Password of the user
	 * @param server_name String Address of the server
	 * @param server_port String Port Number of the server
	 * @param pr Properties  File in which the properties file is opened
	 *
	 * @return String
	 */

	public static String getMessage(String info, String course_id, String dept_name, String uName, String uPassword,String server_name,String server_port,Properties pr) throws Exception{
		
		message = new StringBuffer(pr.getProperty("brihaspati.Mailnotification."+info+".message"));
		if( (info.equals("deleteUser")) || (info.equals("deleteUserhttps")) )
		{
	                replaceString("server_name",server_name);
        	        return(replaceString("server_port",server_port));
		}
		else
		{
	                replaceString("course_id",course_id);
        	        replaceString("dept_name",dept_name);
                	replaceString("user_name",uName);
	                replaceString("server_name",server_name);
        	        replaceString("server_port",server_port);
                	return(replaceString("user_pass",uPassword));
		}
	}


	/**
	 * This method replaces the a string with the replacement string
	 * @param searchString String The substring to be searched in the string
	 * @param replacement String The string with which the substring has to be replaced
	 * @return String
	 */

	public static String replaceString(String searchString, String replacement){
		 if(replacement.equals("")){
			return(message.toString());
                }
                else{		
		String str=message.toString();
		int startIndex=str.indexOf(searchString);
		int endIndex=searchString.length();
		message.replace(startIndex,startIndex+endIndex,replacement);
		return(message.toString());
		}
	}

	/**
	 * This method is used to send the mails with suitable messages
	 * @param info_new String Portion of property in brihaspati.properties
	 * @param mail_id String E-Mail of the user
	 * @param courseId String Course Id of the course registered
	 * @param dept String Name of the department
	 * @param userName String User Name 
	 * @param userPassword String Passowrd of the user
         * @param serverName String Address of the server
         * @param serverPort String Port Number of the server
         * @param LangFile String Language properties file
	 * @return String
	 */

	public static String sendMail(String info_new , String mail_id , String courseId , String dept , String userName , String userPassword , String file,String serverName,String serverPort,String LangFile){
			String msg="";
			String msg1= "";
			String email_new="";
			try{
				SimpleEmail email = new SimpleEmail();
				InputStream f = null;
				Properties p = new Properties();
				if(!dept.equals("Updation Mail")){
					f = new FileInputStream(file);
					msg1=MultilingualUtil.ConvertedString("mailNotification_msg1",LangFile);
					p.load(f);
				}
				if(!mail_id.equals(""))
				{
					email_new=mail_id;
				}
				/**
				 * Retrive message according user from brihaspati.properties
				 */
				String emailMsg="";
				if(!dept.equals("Updation Mail")){
					emailMsg = getMessage(info_new,courseId,dept,userName,userPassword,serverName,serverPort,p);
				}
				/**
				 * Retrive value of mail.smtp.from, mail.server and local.domain.name 
				 * from TurbineRecourses.properties
				 */
		/*		String mail_smtp=Turbine.getConfiguration().getString("mail.smtp.from","");
	                	String host_name=Turbine.getConfiguration().getString("mail.server","");
	                	String mail_uname=Turbine.getConfiguration().getString("mail.username","");
	                	String mail_pass=Turbine.getConfiguration().getString("mail.password","");
                        	String local_domain=Turbine.getConfiguration().getString("local.domain.name","");
		*/
				String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
				String mail_smtp=AdminProperties.getValue(path,"brihaspati.mail.smtp.from");
	                	String host_name=AdminProperties.getValue(path,"brihaspati.mail.server");
	                	String mail_uname=AdminProperties.getValue(path,"brihaspati.mail.username");
	                	String mail_pass=AdminProperties.getValue(path,"brihaspati.mail.password");
                        	String local_domain=AdminProperties.getValue(path,"brihaspati.mail.local.domain.name");
				//newAuthenticator auth;
				if((!mail_smtp.equals("")) && (!mail_smtp.equals(null))){
					if((!email_new.equals("")) && (!email_new.equals(null))){
						Properties l_props = System.getProperties();
						l_props.put("mail.smtp.host", host_name);
						if(!mail_pass.equals("")){
							l_props.put("mail.smtp.auth", "true");
						}
						Session l_session = Session.getDefaultInstance(l_props,  null);
//						Session l_session = Session.getDefaultInstance(l_props,  new Authenticator() { public PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(mail_uname, mail_pass);}});
						l_session.setDebug(true);
						try {
						      MimeMessage l_msg = new MimeMessage(l_session); // Create a New message
						      l_msg.setFrom(new InternetAddress(mail_smtp)); // Set the From address
						      // Setting the "To recipients" addresses
						      l_msg.setRecipients(Message.RecipientType.TO,
	 	                                      InternetAddress.parse(email_new, false));
							if( (info_new.equals("deleteFromGroup")) || (info_new.equals("deleteFromGrouphttps")) )
							{
								// Sets the Subject
								message=new StringBuffer(p.getProperty("brihaspati.Mailnotification."+info_new+".subject"));
		      						l_msg.setSubject(replaceString("courseId",courseId)); 
							}
							else if(!dept.equals("Updation Mail"))
							{
		      						l_msg.setSubject(p.getProperty("brihaspati.Mailnotification."+info_new+".subject")); // Sets the Subject
							}
							else
							{
								if(courseId.equals("onlineRegRequest"))
									l_msg.setSubject("Online Registration Request");//Sets the Subject
								else
	      						      	        l_msg.setSubject("Updation Information");//Sets the Subject
							}
							//Create and fill the first message part
						      MimeBodyPart l_mbp = new MimeBodyPart();
						      if(!dept.equals("Updation Mail")){
                                                      		l_mbp.setContent(emailMsg, "text/html");
                                                      }else{
                                                      		l_mbp.setContent(info_new, "text/html");
                                                      }
						      // Create the Multipart and its parts to it
						      Multipart l_mp = new MimeMultipart();
						      l_mp.addBodyPart(l_mbp);
						      l_msg.setContent(l_mp);
						      // Set the Date: header
						      java.util.Date date=new java.util.Date();
						      l_msg.setSentDate(date);
					              // Send the message
			//			      Transport.send(l_msg);
						      Transport tr = l_session.getTransport("smtp");
						      // Send the message
					              if(!mail_pass.equals("")){
						         tr.connect(host_name, mail_uname, mail_pass);
		                                         // Send the message
						      }
						      else{
							tr.connect();
						      }
					              l_msg.saveChanges();     // don't forget this
					                 tr.sendMessage(l_msg, l_msg.getAllRecipients());
						         tr.close();
						} catch (MessagingException mex) { // Trap the MessagingException Error
					        // If here, then error in sending Mail. Display Error message.
					        msg=msg+"The error in sending Mail Message "+mex.toString();
    						}

						//msg="Mail send succesfully!!";
						msg=msg + MultilingualUtil.ConvertedString("mail_msg2",LangFile);
					}
					else{
						//msg="Mail can't send since your mail id is null!!";
						msg=msg+MultilingualUtil.ConvertedString("mailNotification_msg2",LangFile);
					}
				}
				else{
                                     msg=msg+MultilingualUtil.ConvertedString("mailNotification_msg",LangFile);
                                     }	
				
		}
		catch(Exception ex)
		{
			msg=msg+"The error in mail send !!!"+ex;
		}
		return(msg);
	}
		
}

