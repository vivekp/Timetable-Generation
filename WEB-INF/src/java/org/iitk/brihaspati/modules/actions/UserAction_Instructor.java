package org.iitk.brihaspati.modules.actions;

/*
 * @(#) UserAction_Instructor.java	
 *
 *  Copyright (c) 2005-2006, 2008-2009 ETRG,IIT Kanpur. 
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
import java.util.Date;
import java.util.Vector;
import java.util.List;
import java.util.StringTokenizer;
//Turbine
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
//Brihaspati
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.RegisterMultiUser;

import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MailNotification;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;

/**
 * Register a new student and remove student in database
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a> 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a> 
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a> 
 */
public class UserAction_Instructor extends SecureAction_Instructor
{
	/**
	 * This method performs the action for registering the student in the course
	 * @param data RunData
	 * @param context Context
	 * @return nothing
	 * @see UserManagement from Utils
	 */
	private String LangFile=null;
	private String msg1=null;
	private String[] msg;
	public void doRegister(RunData data, Context context) 
	{
		try
		{
	
 			/**
                 	* getting property file According to selection of Language in temporary variable
                 	* getting the values of first,last names and
                 	* configuration parameter.
                 	*/
                	User user=data.getUser();
                	LangFile=(String)user.getTemp("LangFile"); 
			String serverName=data.getServerName();
                        int srvrPort=data.getServerPort();
                        String serverPort=Integer.toString(srvrPort);

			ParameterParser pp=data.getParameters();
			String gName=data.getUser().getTemp("course_id").toString();
			String uname=pp.getString("UNAME");
			String passwd=pp.getString("PASSWD");
			if(passwd.equals(""))
				passwd=uname;
			String fname=pp.getString("FNAME");
			String lname=pp.getString("LNAME");
			String email=pp.getString("EMAIL");

			String msg=UserManagement.CreateUserProfile(uname,passwd,fname,lname,email,gName,"student",serverName,serverPort,LangFile);
			data.setMessage(msg);

		}
		catch (Exception ex)
		{
			data.setMessage("The error in Student Registration !!"+ex);
		}
	}
  	/**
	 * This method performs the action for removing the student/students in the course
         * @param data RunData
         * @param context Context
	 * @see UserManagement in Utils
         * Here StudentList has studentName from Selected CheckBoxes for Removing.
         * Using StringTokenizer to break  string after "^".
         */

        public void doRemove(RunData data, Context context)
	{
		/**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
		try{
                	User user=data.getUser();
                	LangFile=(String)user.getTemp("LangFile"); 
                	String studentList=data.getParameters().getString("deleteFileNames","");
                	context.put("deleteFile",studentList);
			UserManagement umt=new UserManagement();
			Vector Err_Msg=new Vector();
                        CourseUserDetail CuDetail=new CourseUserDetail();
                	/**
                 	* This 'if' loop is executed when the student list have
                 	* the entries for removal and is not empty
                 	*/
			String email="";
			String userName="";
                        String group="";
                        //String msg="";
			/**
			*Get the Server Name and
			*Server Port using in mail on deletion
			*/
			String serverName=TurbineServlet.getServerName();
	                String serverPort=TurbineServlet.getServerPort();
			String fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
			String subject="";
			/**
	                if(serverPort.equals("8080"))
        	        subject="deleteUser";
	             	else
                	subject="deleteUserhttps";
			**/
                	if(!studentList.equals(""))
			{
                	        group=data.getUser().getTemp("course_id").toString();

                        /**
                         * Use StringTokenizer to break  string after "^".
                         */

                        	StringTokenizer st=new StringTokenizer(studentList,"^");
                        	Vector v=new Vector();
                        	Vector Err_user=new Vector();
                        	Vector Err_type=new Vector();

                        	for(int i=0;st.hasMoreTokens();i++)
				{
                                	v.addElement(st.nextToken());
                        	}

                        /**
                         * For all the users ontained from the list get
			 * the user name and then respective user id.
                         * Afterwards delete the role of the student
                         * from the group
                         * @see UserUtil in utils
                         * @see UserManagement in utils
                         */

                        	for(int i=0;i<v.size();i++)
				{
                                	userName=v.elementAt(i).toString();
					/**
					*Send the Mail on deletion 	
					*/
					String uid=Integer.toString(UserUtil.getUID(userName));
					TurbineUser element=(TurbineUser)UserManagement.getUserDetail(uid).get(0);
                                	email=element.getEmail();
					subject=checkUserAvailabilityDifferntGroup(userName,serverPort);
			                msg1=umt.removeUserProfileWithMail(userName,group,LangFile,subject,email,"","","",fileName,serverName,serverPort);
		                	msg = msg1.split(":");
	                		data.setMessage(msg[0]);
					/**
	                                String Mail_msg=MailNotification.sendMail(subject,email,"","","","",fileName,serverName,serverPort,LangFile);
					data.setMessage(Mail_msg);
					msg=umt.removeUserProfile(userName,group,LangFile);
					**/
                                        if(umt.flag.booleanValue()==false)
                                        {
                                        	CuDetail=new CourseUserDetail();
                                                CuDetail.setErr_User(userName);
                                                CuDetail.setErr_Type(msg[1]);
                                                Err_Msg.add(CuDetail);
                                        }
                        	}
                        	context.put("error_user",Err_Msg);
                	}

                	/**
                 	* This 'else' is executed when there are no students
                 	* in the list obtained.
                 	*/

                	else
			{
                        	data.addMessage(MultilingualUtil.ConvertedString("stu_msg2",LangFile));
                	}
		}
		catch(Exception ex)
		{
			data.setMessage("The Error in Student removal !!"+ex);
		}
        }
  	/**
          * ActionEvent responsible for register multiple user in the system
          * @param data RunData
          * @param context Context
          * @see RegisterMultiUser from Utils
          */
        public void doUploadMultiUser(RunData data, Context context)
        {
		/**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
		String serverName=data.getServerName();
                int srvrPort=data.getServerPort();
                String serverPort=Integer.toString(srvrPort); 
	        User user=data.getUser();
                LangFile=(String)user.getTemp("LangFile"); 
	        try{
                        ParameterParser pp=data.getParameters();
                        FileItem file = pp.getFileItem("file");
                        String fileName=file.getName();
                        if((!fileName.endsWith(".txt"))||(file.getSize()<=0))
                        {
				String msg=MultilingualUtil.ConvertedString("upload_msg2",LangFile);
                        	data.setMessage(msg);
                        }
                        else{
				String group=(String)user.getTemp("course_id");
                                Date date=new Date();
                                File f=new File(TurbineServlet.getRealPath("/tmp")+"/"+group+"student"+date.toString()+".txt");
                                file.write(f);
                                Vector msg=RegisterMultiUser.Multi_Register(f,group,"student",serverName,serverPort,LangFile);
                                context.put("Msg",msg);
                        }
                }
                catch(Exception ex){data.setMessage("The Error in Uploading!! "+ex);}
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
		 /**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
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
          * ActionEvent responsible for removing a user from the system
          * @param data RunData
          * @param context Context
          */
        public void doDelete(RunData data, Context context) throws Exception
        {
		/**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
                User user=data.getUser();
                LangFile=(String)user.getTemp("LangFile"); 
                ParameterParser pp=data.getParameters();
                String userName=pp.getString("username");
		String uid=Integer.toString(UserUtil.getUID(userName));
		TurbineUser element=(TurbineUser)UserManagement.getUserDetail(uid).get(0);
                String email=element.getEmail();
		String group=data.getUser().getTemp("course_id").toString();
		/**
                *Get the Server Name and
                *Server Port using in sending mail on deletion
                */
                String serverName=TurbineServlet.getServerName();
                String serverPort=TurbineServlet.getServerPort();
		String subject="";	
		subject=checkUserAvailabilityDifferntGroup(userName,serverPort);
		 /**
                *Release the disk space
                */
		String fileName=TurbineServlet.getRealPath("/WEB-INF/conf/brihaspati.properties");
		UserManagement umt= new UserManagement();
		msg1=umt.removeUserProfileWithMail(userName,group,LangFile,subject,email,"","","",fileName,serverName,serverPort);
                msg = msg1.split(":");
		data.setMessage(msg[0]);
		data.addMessage(msg[1]);
		
                /**String Mail_msg=MailNotification.sendMail(subject,email,"","","","",fileName,serverName,serverPort,LangFile);
		//data.setMessage(Mail_msg);
		//UserManagement umgmt=new UserManagement();
		//String inf=umgmt.removeUserProfile(userName,group,LangFile);
		//data.setMessage(inf);	
                //Vector Messages=UserManagement.RemoveUser(userName,LangFile);
                //context.put("error_Messages",Messages); **/
        }
	/**
          * ActionEvent responsible for register multiple user in the system
          * @param data RunData
          * @param context Context
          * @see RegisterMultiUser from Utils
          */
        public void doUploadImage(RunData data, Context context)
        {
		User user=data.getUser();
                LangFile=(String)user.getTemp("LangFile");
		String ImageName=(String)user.getTemp("course_id");
                ParameterParser pp=data.getParameters();
		FileItem file = pp.getFileItem("file");
                String fileName=file.getName();
		String imagesRealPath=TurbineServlet.getRealPath("/images");
		if(fileName.endsWith("jpg")|| fileName.endsWith("gif")|| fileName.endsWith("png"))
                {
			try{
				File filePath=new File(imagesRealPath+"/Header/");
				filePath.mkdirs();
				filePath=new File(filePath+"/"+ImageName);
        	                file.write(filePath);
	                        msg1=MultilingualUtil.ConvertedString("c_msg5",LangFile);
                                data.setMessage(msg1);
			}
			catch(Exception ex){data.setMessage("The Error in Uploading!! "+ex);}
		} 
                else
                {
                	msg1=MultilingualUtil.ConvertedString("Profile_PhotoMsg2",LangFile);
                        data.setMessage(msg1);
                        setTemplate(data,"call,CourseMgmt_User,UploadImage.vm");
		}


	}

       public void doGradecard(RunData data, Context context)
        {
                try {
                        User user=data.getUser();
                        LangFile=(String)user.getTemp("LangFile");
                        String ImageName=(String)user.getTemp("course_id");
                        ParameterParser pp=data.getParameters();
                        String A=pp.getString("A","");
                        String B=pp.getString("B","");
                        String C=pp.getString("C","");
                        String D=pp.getString("D","");
                        String E=pp.getString("E","");
                        String courseid=(String)user.getTemp("course_id","");
                        String xmlPath=TurbineServlet.getRealPath("/Courses"+"/"+courseid);
                        File xmlFile=new File(xmlPath+"/configuregrade.xml");
                        //File xmlFile1=new File(xmlPath+"/configuregrade.xml");
                        //xmlFile1.delete();
                        xmlFile.delete();
                        //xmlFile.deleteOnExit();
                        XmlWriter xmlwriter=null;
                        //if(!xmlFile1.exists())
                        if(!xmlFile.exists())
                        {
                                //TopicMetaDataXmlWriter.writeWithRootOnly1(xmlFile1.getAbsolutePath());
                                TopicMetaDataXmlWriter.writeWithRootOnly1(xmlFile.getAbsolutePath());
                                xmlwriter=new XmlWriter(xmlPath+"/configuregrade.xml");
                        }  //if

                        Vector xmlVct=new Vector();
                        xmlVct.add(-1);
                        String  str[]={"A","B","C","D","E"};

			/** @param str1[]={A,B,C,D,E}; is coming from (vm) getting from PrameterParser, writing grade in % i.g. 85%, 75%, 65%, etc. 
			  * Instructor can change these numeric grade 
			  * @param str1[]={"A","B","C","D","E"}; writing grade in alphabate i.g. A, B, C, etc. 
			**/

                        String  str1[]={A,B,C,D,E};
			 for(int xmlint=0;xmlint<str.length;xmlint++) {
                                xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(xmlPath,"/configuregrade.xml",xmlVct);
                                TopicMetaDataXmlWriter.appendGrade(xmlwriter,str1[xmlint],str[xmlint],"");
                                xmlwriter.writeXmlFile();
                        }  //end for
                }catch(Exception e){}

        }

	/**
	 * Getting the list of groupid whenever selected user is registered in multiple group
	 * @param grpId, getting the list of group id as per the user id inwhich user is registered.
	 * @param data subject, deciding the value of "subject"  parameter. 
	 * if user is in multiple group subject parameter value would be, subject="deleteFromGroup"
	 * otherwise it would be, subject="deleteUser"
	*/

	public String checkUserAvailabilityDifferntGroup( String userName, String serverPort)
        {
                String subject="";
		int[]  rId={1,6};
		Vector grpId = new Vector();
                int userId=UserUtil.getUID(userName);
		try{
		
			Criteria crit = new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,userId);
			crit.andNotIn(TurbineUserGroupRolePeer.ROLE_ID,rId);
                	List v=TurbineUserGroupRolePeer.doSelect(crit);
                        for(int i=0;i<v.size();i++){
                                TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
				String s=Integer.toString(element.getGroupId());
                                grpId.add(s);
                        }

                }
                catch(Exception e){
                        ErrorDumpUtil.ErrorLog("The error in try checkUserAvailabilityDifferntGroup()- UserAction_Instructor !!"+e);
                        //Log something
                }
                if( (grpId.size() > 1) )
                {
                        if(serverPort.equals("8080"))
                                 subject="deleteFromGroup";
                        else
                                subject="deleteFromGrouphttps";
                }
                else
                {
                        if(serverPort.equals("8080"))
                                subject="deleteUser";
                        else
                                subject="deleteUserhttps";
                }
                return subject;
	}

	/**
	 * This is the default method called if the button is not found
	 * @param data RunData
	 * @param context Context
	 */

	public void doPerform(RunData data,Context context) throws Exception{
		String action=data.getParameters().getString("actionName","");
		context.put("action",action);
		User user=data.getUser();
                LangFile=(String)user.getTemp("LangFile");  
                if(action.equals("eventSubmit_doRegister"))
                        doRegister(data,context);
                else if(action.equals("eventSubmit_doRemove"))
                        doRemove(data,context);
                else if(action.equals("eventSubmit_doUpload"))
                        doUploadMultiUser(data,context);
                else if(action.equals("eventSubmit_doDelete"))
                        doDelete(data,context);
                else if(action.equals("eventSubmit_doUploadImage"))
                        doUploadImage(data,context);
		else if(action.equals("eventSubmit_doGradecard"))
                        doGradecard(data,context);
		else
			data.setMessage(MultilingualUtil.ConvertedString("usr_prof2",LangFile));
	}
}

