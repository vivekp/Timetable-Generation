package org.iitk.brihaspati.modules.actions;


/*
 * Copyright (c) 2006 ETRG,IIT Kanpur.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * Redistribution in binary form must reproducuce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Contributors: Members of ETRG, I.I.T. Kanpur
 */

import java.io.File;
import java.util.List;
import java.util.Date;
import java.io.FileWriter;
//import java.lang.reflect.Array;
import java.util.StringTokenizer;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.BasePeer;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.util.security.AccessControlList;

import org.iitk.brihaspati.modules.utils.CreateZip;
import org.iitk.brihaspati.modules.utils.BackupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;

/**
 * This class is responsible for restoring the database entries and course contents. 
 *
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:mann_singh2004@yahoo.com">Manvendra Singh</a>
*/

public class BackupAction extends SecureAction
{
	Date date=new Date();
	private String fileID=null;
//	private ServletOutputStream out;
	/**
     	* This is gives the all course content backup as well as perticular course.
     	* @param data Turbine information.
     	* @param context Context for web pages.
     	* @exception Exception, a generic exception.
     	*/
    	public void doCourse_backup(RunData data,Context context)
    	{
		User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");
		try{
			String dir=data.getParameters().getString("courses");
			String destination=TurbineServlet.getRealPath("/BackupData");
			File f=new File(destination+"/");
			f.mkdirs();
			String docRoot="";
			CreateZip cz;
			if(dir.equals("Courses"))
			{
			     	docRoot=TurbineServlet.getRealPath("/");
				cz=new CreateZip(docRoot+"/Repository",destination);
				cz=new CreateZip(docRoot+"/UserArea",destination);
				cz=new CreateZip(docRoot+"/WIKI",destination);
				cz=new CreateZip(docRoot+"/QuestionBank",destination);
			}
			else{
				docRoot=TurbineServlet.getRealPath("/Courses");
			}
			cz=new CreateZip(docRoot+"/"+dir,destination);
			data.setMessage(MultilingualUtil.ConvertedString("backup_backup",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg1",LangFile));
                        data.addMessage(MultilingualUtil.ConvertedString("backup_msg6",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg7",LangFile));
//			data.setMessage("Backup has been taken properly");
//			setTemplate(data,"call,Backups,BackupAdmin.vm");
		}
		catch(Exception ex){data.setMessage("The error in  course's backup "+ex);}
    	}//doCourse method
		/**
        	* This gives a student backup for a perticular course.
	        * @param data Turbine information.
        	* @param context Context for web pages.
	        * @exception Exception, a generic exception.
        	*/

    	public void doStudent_db_backup(RunData data,Context context) throws Exception
    	{   
		User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");
		String courseId=data.getParameters().getString("courses","");
		if ((courseId.equals("Courses"))||(courseId.equals("author"))){
			data.setMessage(MultilingualUtil.ConvertedString("backup_msg2",LangFile));
//			data.setMessage("Please choose the course except All.");
//			setTemplate(data,"call,Backups,BackupAdmin.vm");
		}
		else{
			BackupUtil.createTxt(courseId);
			data.setMessage(MultilingualUtil.ConvertedString("backup_backup",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg1",LangFile));
                        data.addMessage(MultilingualUtil.ConvertedString("backup_msg6",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg7",LangFile));

//			data.setMessage("Backup has been taken properly");
  //              	setTemplate(data,"call,Backups,BackupAdmin.vm");
		}

    	}//methodstudent
		/**
	        * This is responsble for complete database backup.
        	* @param data Turbine information.
	        * @param context Context for web pages.
        	* @exception Exception, a generic exception.
	        */

        public void doComplete_backup(RunData data,Context context) throws Exception {
		User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");
	
                AccessControlList acl=data.getACL();
                String CoursesRealPath=TurbineServlet.getRealPath("/BackupData");
		File fi=new File(CoursesRealPath +"/");
                fi.mkdirs();
		String filePath=CoursesRealPath+"/CompleteDBBackup"+ExpiryUtil.getCurrentDate("")+".sql";
                FileWriter fw=new FileWriter(filePath);

                if(acl.hasRole("turbine_root","global")){

                        try{//try0
                                /**
                                 * Getting the list of tables in current database
                                 */

                                String tbnm="SHOW TABLES";
                                List u=BasePeer.executeQuery(tbnm);
                                for(int i=0;i<u.size();i++)
                                {//for0
                                        String tNm=(u.get(i)).toString();
					/**
					 * Getting Table Name like {'COURSES'}
					 * To remove {' use substring & getting COURSES
					*/
                                        int j=tNm.length();
                                        String ftm=tNm.substring(2,j-2);
					ErrorDumpUtil.ErrorLog("the name of table is "+ftm);
				
                                        fw.write("# drop table if exists "+ ftm+";"+"\n");
                                        fw.write(" DELETE FROM "+ ftm+";"+"\n");

                                        /**
                                         * Getting the fields of particular table
                                         */

                                        String desc="DESC "+ftm;
                                        List v=BasePeer.executeQuery(desc);
                                        for(int k=0;k<v.size();k++)
                                        {
                                                String d1=(v.get(k)).toString();
                                                int l=d1.length();
                                                String d2=d1.substring(2,l);
                                                StringTokenizer st = new StringTokenizer(d2,"'");
                                                String d3=st.nextToken();
                                        }
                                        /**
                                         * Getting all entries of table
                                         */
                                           String detail="SELECT * FROM "+ftm;
                                       	   List w=BasePeer.executeQuery(detail);
					   for(int m=0;m<w.size();m++)
                                	        {//for1
                                                String e1=(w.get(m)).toString();
                                                int n=e1.length();
                                                String e2=e1.substring(1,n-1);
                                                String entry="";
                                                StringTokenizer st1 = new StringTokenizer(e2,",");
                                                int a=st1.countTokens();
					/** 
					  * Object binary data may contain comma 
					  * so we fix number of token for turbine user table
					  */
						if(ftm.equals("TURBINE_USER")){
							a=11;
						}

                                                try{
                                                        /**
                                                          * Replace apostrophy by slash apostrophy
                                                          */
                                                        for(int c=1;c<=a; c++){
	                                                        String token=st1.nextToken() ;
        	                                                int tl=token.length();
                	                                        String gettoken=token.substring(1,tl-1);
                                                        	String finaltoken="'"+gettoken.replace("'","\\'")+"'";
                                	                        if(a==c){
									/**
									  * Convert object binary data in to null
									  * for turbine user table
									  */
									if(ftm.equals("TURBINE_USER")){
                                                	                	entry=entry+"'null'";
									}
									else{
                                                                		entry=entry+finaltoken;
									}
								}
                	                                        else{
                        	                                        entry=entry+finaltoken+",";
								}
                                                	}
						}//try1
                                                catch (Exception e)
                                                        {data.setMessage("The error in replaceing apostropy" + e);}
			                                fw.write("\n"+"INSERT INTO "+ftm+" values ("+entry+");");
                                        }//for1
                                        fw.write("\n\n");
                        }//for0
                        fw.close();
			 data.setMessage(MultilingualUtil.ConvertedString("backup_msg3",LangFile)+" " + MultilingualUtil.ConvertedString("backup_backup",LangFile)+" " + MultilingualUtil.ConvertedString("backup_msg1",LangFile));
                        data.addMessage(MultilingualUtil.ConvertedString("backup_msg6",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg7",LangFile));

//			data.setMessage("Database Backup has been taken properly");
                        setTemplate(data,"call,Backups,Backups.vm");

                }//try0
                catch(Exception e){data.addMessage("The error in Backups file" +e);}
	}//if

	}//doComplete_backup
		/**
	        * This method is responsble for deleting the backup files.
        	* @param data Turbine information.
	        * @param context Context for web pages.
        	* @exception Exception, a generic exception.
	        */
	public void doDeletebackup(RunData data,Context context){
		User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");
		String fname=data.getParameters().getString("fname","");
		AccessControlList acl=data.getACL();
		if(acl.hasRole("turbine_root","global")){//if0
			try{ //try0
				File fle=new File(TurbineServlet.getRealPath("/BackupData/"+fname));
				boolean res=CommonUtility.autoDeletebackup();
				fle.delete();
				data.setMessage(MultilingualUtil.ConvertedString("personal_del2",LangFile));
				setTemplate(data,"call,Backups,BackupList.vm");
			}
			catch(Exception e){data.addMessage("The error in Backups file deletion : " +e);}
		}
	}//doDeletebackup
	 /**
                * This method is responsble for automatic 
		* deleting the 3days old backup files.
                * @param data Turbine information.
                * @param context Context for web pages.
                * @exception Exception, a generic exception.
                */

		/**
        	* This method gives the glossary backup.
	        * @param data Turbine information.
        	* @param context Context for web pages.
	        * @exception Exception, a generic exception.
        	*/

	public void doGlossary_backup(RunData data,Context context) throws Exception {
                User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");

                AccessControlList acl=data.getACL();
                String CoursesRealPath=TurbineServlet.getRealPath("/BackupData");
                File fi=new File(CoursesRealPath +"/");
                fi.mkdirs();
                String filePath=CoursesRealPath+"/GlossaryBackup"+ExpiryUtil.getCurrentDate("")+".sql";
                FileWriter fw=new FileWriter(filePath);
		if(acl.hasRole("turbine_root","global")){//if0

                        try{ //try0
                                /**
                                 * Getting the list of tables in current database
                                 */
				String Directory [] =new String [] {"GLOSSARY","GLOSSARY_ALIAS" } ;
			        for(int i =0; i < Directory.length ;i++)
			        {
					String ftm=Directory[i];
                                        fw.write("# drop table if exists "+ ftm+";"+"\n");
                                        fw.write(" DELETE FROM "+ ftm+";"+"\n");

                                        /**
                                         * Getting all entries of table
                                         */
					String detail="SELECT * FROM "+ftm;
                                        List w=BasePeer.executeQuery(detail);
                                        for(int m=0;m<w.size();m++)
                                        {//for1
                                                String e1=(w.get(m)).toString();
                                                int n=e1.length();
                                                String e2=e1.substring(1,n-1);
                                                String entry="";
                                                StringTokenizer st1 = new StringTokenizer(e2,",");
                                                int a=st1.countTokens();
                                                try{//try1
                                                        /**
                                                          * Replace apostrophy by slash apostrophy
                                                          */
                                                for(int c=1;c<=a; c++){
							String token=st1.nextToken() ;
                                                        int tl=token.length();
                                                        String gettoken=token.substring(1,tl-1);
                                                        String finaltoken="'"+gettoken.replace("'","\\'")+"'";
                                                        if(a==c)
                                                                entry=entry+finaltoken;
                                                        else
                                                                entry=entry+finaltoken+",";
                                                }
                                                }//try1
						catch (Exception e)
                                                        {data.setMessage("The error in replaceing apostropy" + e);}
                                                fw.write("\n"+"INSERT INTO "+ftm+" values ("+entry+");");
                                        }//for1
                                        fw.write("\n\n");
                     //   }//if1
		}//for0
                        fw.close();
			data.setMessage(MultilingualUtil.ConvertedString("Glossary",LangFile)+" "+MultilingualUtil.ConvertedString("backup_backup",LangFile));
                        data.addMessage(MultilingualUtil.ConvertedString("backup_msg1",LangFile));
                        data.addMessage(MultilingualUtil.ConvertedString("backup_msg6",LangFile)+" "+MultilingualUtil.ConvertedString("backup_msg7",LangFile));

                        setTemplate(data,"call,Backups,Backups.vm");

                }//try0
                catch(Exception e){data.addMessage("The error in Backups file" +e);}
        }//if0

        }//doGlossary_backup

    /**
     * Default action to perform if the specified action
     * cannot be executed
     * @param data RunData
     * @param context Context for web pages.
     * @exception Exception, a generic exception.
     */
       
	 public void doPerform(RunData data,Context context) throws Exception{
		User user= data.getUser();
                String LangFile=(String)user.getTemp("LangFile");
                String actionName=data.getParameters().getString("actionName","");
                context.put("actionName",actionName);
                if(actionName.equals("eventSubmit_doCourseBackup"))
                        doCourse_backup(data,context);
		else if(actionName.equals("eventSubmit_doStudent_db_backup"))
			doStudent_db_backup(data,context);
		else if(actionName.equals("eventSubmit_doCompleteBackup"))
                       	doComplete_backup(data,context);
		else if(actionName.equals("eventSubmit_doGlossaryBackup"))
                       	doGlossary_backup(data,context);
		else if(actionName.equals("eventSubmit_doDeletebackup"))
                       	doDeletebackup(data,context);
                else
                        data.setMessage(MultilingualUtil.ConvertedString("usr_prof2",LangFile));
	}//doPerform                                                                
   		
}//class
