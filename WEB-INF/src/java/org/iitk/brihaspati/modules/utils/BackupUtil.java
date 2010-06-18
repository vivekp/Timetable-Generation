package org.iitk.brihaspati.modules.utils;

/*
 * @(#)BackupUtil.java
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur.
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
 * Contributors: Members of ETRG, I.I.T. Kanpur
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import org.apache.torque.util.BasePeer;
import org.apache.turbine.services.servlet.TurbineServlet;
import com.workingdogs.village.Record;


        public class BackupUtil
	{
		public static void createTxt(String courseID)
		{ 
	        	try{

		                GroupUtil temp_group_info=new GroupUtil();

	                        int group_id=temp_group_info.getGID(courseID);
                                String query1="select USER_ID from TURBINE_USER_GROUP_ROLE where ROLE_ID=3 AND GROUP_ID=" +group_id;
        	                List u=BasePeer.executeQuery(query1);
	
        	                String login_name="",password_value="",firstname="",lastname="",e_mail="";
                	        int temp_user_id=0;
	
        	                String filePath=TurbineServlet.getRealPath("/BackupData");
				File fil=new File(filePath+"/");
				fil.mkdirs();
				Date date=new Date();
                        	FileWriter f=new FileWriter(filePath+"/"+courseID+ExpiryUtil.getCurrentDate("")+".txt");
				f.write("Login_Name " + " Password "+" First_Name " +" Last_Name " + " E_Mail " + " \n");  

	                        for(Iterator j=u.iterator();j.hasNext();)
        	                {
                	                Record temp_item=(Record)j.next();
        	                        temp_user_id=temp_item.getValue("USER_ID").asInt();
                        	        String query2="select * from TURBINE_USER where USER_ID="+temp_user_id;
        	                        List v=BasePeer.executeQuery(query2);
                	//	        ErrorDumpUtil.ErrorLog("Inside AdminBackupForStudents.java line 151 =====>list"+v);


                                	for(Iterator i=v.iterator();i.hasNext();)
                               		 {


                                        	Record item=(Record)i.next();
	                                        login_name=item.getValue("LOGIN_NAME").asString();
        	                                password_value=item.getValue("PASSWORD_VALUE").asString();
                	                        firstname=item.getValue("FIRST_NAME").asString();
                        	                lastname=item.getValue("LAST_NAME").asString();
                                	        e_mail=item.getValue("EMAIL").asString();
	                                        if(!login_name.equals("guest"))
        	                                f.write(login_name+";"+password_value+";"+firstname+";"+lastname+";"+e_mail+"\n");
//						f.write("Backup hasbeen taken successfully");
                	                }

                        	}

	                        f.close();

       }//try
        catch(Exception e)
        {
                ErrorDumpUtil.ErrorLog("Error in utils Exception [BackupUtil] in method[createTxt()] is "+ e);
  
             }  
     }   

 } 

  
