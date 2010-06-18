package org.iitk.brihaspati.modules.utils;


/*@(#)CommonUtility.java
 *  Copyright (c) 2005-2008 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
//java
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.util.List;
import java.util.Vector;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import java.util.Iterator;

import com.workingdogs.village.Record;

import java.sql.Date;

//turbine
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

//brihaspati
import org.iitk.brihaspati.om.Task;
import org.iitk.brihaspati.om.SystemCleantime;
import org.iitk.brihaspati.om.TaskPeer;
import org.iitk.brihaspati.om.SystemCleantimePeer;
import org.iitk.brihaspati.om.CalInformationPeer;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.GlossaryAliasPeer;
import org.iitk.brihaspati.om.GlossaryPeer;
import org.iitk.brihaspati.om.HintQuestionPeer;
import org.iitk.brihaspati.om.MailReceivePeer;
import org.iitk.brihaspati.om.MailSendPeer;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.ProxyUserPeer;
import org.iitk.brihaspati.om.RemoteCoursesPeer;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.om.UsageDetailsPeer;
import org.iitk.brihaspati.om.TurbineUserPeer;
import org.iitk.brihaspati.om.TurbineUserGroupRolePeer;
import org.iitk.brihaspati.om.TurbineGroupPeer;
import org.iitk.brihaspati.om.AttendenceSeetPeer;
import org.iitk.brihaspati.om.AttendenceSeet;
import org.iitk.brihaspati.om.QuizPeer;
import org.iitk.brihaspati.modules.actions.UploadAction;
import org.iitk.brihaspati.modules.utils.AdminProperties;

import org.iitk.brihaspati.om.NoticeReceive;
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.News;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.Courses;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.turbine.services.security.torque.om.TurbineGroup;

/**
 * This class is used for call the method in mylogin 
 * like Create index for Search, Clean the system 
 * 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @version 1.0
 * @since 1.0
 * @see ExpiryUtil
 */
public class CommonUtility{
	/**
	 * This method create the index of course
	 * @return boolean 
	 */
	public static boolean CreateIndex(){

	try{
		File f1=new File(TurbineServlet.getRealPath("/Courses"));
                String arr[]=null;
                if(f1.isDirectory())
                {
			 arr=f1.list();
                }
                for(int i=0;i<arr.length;i++)
                {
	                if(arr[i].endsWith("_Index")){
        	        }
                	else
                	{
	                	String s=TurbineServlet.getRealPath("/");
        		        File f2=new File(s+"/Courses/"+arr[i]+"_Index");
		                if(!f2.exists()){
                			boolean success=false;
			                success=f2.mkdir();
		                }
			}
                }
                arr=f1.list();
                for(int i=0;i<arr.length;i++)
                {
                	if(arr[i].endsWith("_Index")){
		                StringTokenizer st=new StringTokenizer(arr[i],"_");
                		if(st.hasMoreTokens()){
			                String ne=st.nextToken();
			                CreateIndexer.StartIndex(TurbineServlet.getRealPath("/Courses")+"/"+arr[i],TurbineServlet.getRealPath("/Courses")+"/"+ne);
		                }
                	}
                }
	}
        catch(Exception e){
		ErrorDumpUtil.ErrorLog("The error in getCreateIndex() - CommonUtil class !!"+e);
	}
	return true;
	}//end of Method

	/**
	 * This method clear all the expired entries from database
	 * @return String 
	 */
	public static boolean CleanSystem(){
	try {
		String c_date=ExpiryUtil.getCurrentDate("-");
                Date current_date=Date.valueOf(c_date);
                Criteria crit=new Criteria();
                crit.add(SystemCleantimePeer.ID,1);
                List z=SystemCleantimePeer.doSelect(crit);
                if(z.size()!=0){//1 if
			SystemCleantime element=(SystemCleantime)z.get(0);
                        String Clean_date=(element.getCleanDate()).toString();
                        /**
                        * Date and Time seprated from Date
                        * here using only Date
                        */
				
                        String clean_Date=Clean_date.substring(0,10);
                        if(!clean_Date.equals(c_date)){//2 if
                        	boolean Expiry_Success=ExpiryUtil.Expiry();
				String  Update_Mail = UpdateInfoMail.getUpdationMail();
                                if(Expiry_Success=true){//3 if
                                	/**
                                        *Code for update the clean date in database
                                        */
					
                                	crit=new Criteria();
                                        crit.add(SystemCleantimePeer.ID,1);
                                        crit.add(SystemCleantimePeer.CLEAN_DATE,current_date);
                                        SystemCleantimePeer.doUpdate(crit);
                                }//end of if 3 loop
				boolean CI=CreateIndex();
				boolean OT=optimizeTables();
				boolean ADB=autoDeletebackup();
                        }//end of if 2 loop

		}//end of if 1 loop
                else{
                                        /**
                                         * Code for first time fill the clean Date in database
                                         */
                	crit=new Criteria();
			crit.add(SystemCleantimePeer.ID,1);
                        crit.add(SystemCleantimePeer.CLEAN_DATE,current_date);
                        SystemCleantimePeer.doInsert(crit);
		}
	}//end of try
        catch(Exception ex)
        {
		ErrorDumpUtil.ErrorLog("The error in CleanSystem - CommonUtil class !!"+ex);
        }
	return true;
	}//end of method

	/**
                * This method is responsble for automatic
                * deleting the 3days old backup files.
		* @return boolean
                */
        public static boolean autoDeletebackup(){
                try{ //try0
                        File dir=new File(TurbineServlet.getRealPath("/BackupData/"));
                        if(dir.exists()){
                                File list[]=dir.listFiles();
                                for(int i=0;i<(Array.getLength(list));i++){
                                        String filename=list[i].getName();
                                        int ln=filename.length();
                                        String dtime=filename.substring(ln-12,ln-4);
                                        int fdt=Integer.parseInt(dtime);
                                //      get the current date
                                        String curdate=ExpiryUtil.getCurrentDate("");
                                        int cdt=Integer.parseInt(curdate);
                                        int diffday=cdt - fdt;
                                //      compare it
                                //      if grether than 4
                                        if(diffday>4){
                                                File fle=list[i].getAbsoluteFile();
                                                ErrorDumpUtil.ErrorLog("delete file path in util is :"+fle.toString());
                                //      delete it
                                                fle.delete();

                                        }

                                }
                        }
                }
                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in Backups file deletion : " +e);
		}
	return true;
        }//autoDeletebackup


	/**
	 * This method display all task which are moved to index page
	 * @return Vector listTask 
	 */
	public static Vector DisplayTask(int uid,String date){
		Vector listTask=new Vector();
	try{
		Criteria crit=new Criteria();
		crit.add(TaskPeer.USER_ID,uid);
		crit.add(TaskPeer.STATUS,1);
		crit.addAscendingOrderByColumn(TaskPeer.START_DATE);
		crit.add(TaskPeer.DUE_DATE,(Object)date,crit.GREATER_EQUAL);
                List v = TaskPeer.doSelect(crit);
                for(int i=0;i<v.size();i++)
                {
                	String Title=((Task)v.get(i)).getTitle();
                        int tid=((Task)v.get(i)).getTaskId();
                        TaskDetail tDetail=new TaskDetail();
                        tDetail.setTask_Id(tid);
                        tDetail.setTitle(Title);
                        listTask.add(tDetail);
                }
 
	}//end of try
	catch(Exception ex)
        {
                ErrorDumpUtil.ErrorLog("The error in Display Task - CommonUtil class !!"+ex);
                listTask.add(ex);
        }
	return listTask;
	}//end of Method
	/**
	* This Method used to update text file
	* @param filePath String
	* @param msgid String
	* @param DbMessage String
	* @param update boolean 
	*
	*/
	public static void UpdateTxtFile(String filePath, String msg_idd, String DB_message, boolean update)
	{
	try{
		String str[] = new String[1000];
                int i =0;
                int startd = 0;
                int stopd = 0;

                 /* deleting the message from the txt file  */
                BufferedReader br=new BufferedReader(new FileReader (filePath));
                while ((str[i]=br.readLine()) != null)
		{                
                        if (str[i].equals("<"+msg_idd+">"))
                        {
                               	startd = i;
                        }
                        else if(str[i].equals("</"+msg_idd+">"))
                        {
                        	stopd = i;
                        }
                        i= i +1;
                } //while
                br.close();
                FileWriter fw=new FileWriter(filePath);
		if(update){ 
			for(int x=0;x<=startd;x++)
                	{
                		fw.write(str[x]+"\r\n");	
			}
                }
		else{    
			for(int x=0;x<startd;x++) 
			{                
                		fw.write(str[x]+"\r\n");
			}
                }
		if(update)
			fw.write(DB_message+"\n");
		if(update){ 
			for(int y=stopd;y<i;y++)
			{                
                		fw.write(str[y]+"\r\n");
			}
                }
		else {   
			for(int y=stopd+1;y<i;y++) 
                	{
                		fw.write(str[y]+"\r\n");
			}
                }
                fw.close();
		str=null;
	}
	catch(Exception ex){
		ErrorDumpUtil.ErrorLog("The error in UpdateTxtFile() - CommonUtil class !!"+ex);
	}
	}
			/**
                        * Write all topic in xml file if topic is not present in xml file
			* @param filePath string
                        */
		public static void cretUpdtxml(String filePath){
			try{
                        String filter[]={"Permission","Remotecourse","content__des.xml"};
                        NotInclude exclude=new  NotInclude(filter);
                        String file[]=(new File(filePath)).list(exclude);
			File Path=new File(filePath+"/content__des.xml");
                        for(int i=0;i<file.length;i++)
                        {
				UploadAction uA=new UploadAction();
                                // get the value from xml file
                                if(Path.exists()){
                                        TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filePath+"/"+"content__des.xml");
                                        String tpcxml[]=topicMetaData.getTopicNames();

                                        for(int j=0;j<tpcxml.length;j++){
                                                String tnm=tpcxml[j];
                                //check topic name exist in xml file or not. If not then write in to xml file
                                                if(file[i].equals(tnm)){
                                                }
                                                else{
                                                        uA.topicSequence(filePath,file[i],((new java.util.Date()).toString()));
                                                }
                                        }
                                }
                                else{
                                        uA.topicSequence(filePath,file[i],((new java.util.Date()).toString()));
                                }
                        }
			}//try
			catch (Exception ex){
				ErrorDumpUtil.ErrorLog("The error in Update and create topic xml file - CommonUtil class(cretUpdtxml method) !!"+ex);
			}
		}

	/**
         * The method analyzes and optimizes the tables of the database
	 * return boolean
         */
	
        public static boolean  optimizeTables() {
		try{

                TurbineUserPeer.executeQuery("ANALYZE TABLE TURBINE_USER");
                TurbineUserPeer.executeQuery("OPTIMIZE TABLE TURBINE_USER");

                TurbineUserGroupRolePeer.executeQuery("ANALYZE TABLE TURBINE_USER_GROUP_ROLE");
                TurbineUserGroupRolePeer.executeQuery("OPTIMIZE TABLE TURBINE_USER_GROUP_ROLE");

                TurbineGroupPeer.executeQuery("ANALYZE TABLE TURBINE_GROUP");
                TurbineGroupPeer.executeQuery("OPTIMIZE TABLE TURBINE_GROUP");

                UsageDetailsPeer.executeQuery("ANALYZE TABLE USAGE_DETAILS");
                UsageDetailsPeer.executeQuery("OPTIMIZE TABLE USAGE_DETAILS");

                UserConfigurationPeer.executeQuery("ANALYZE TABLE USER_CONFIGURATION");
                UserConfigurationPeer.executeQuery("OPTIMIZE TABLE USER_CONFIGURATION");

                SystemCleantimePeer.executeQuery("ANALYZE TABLE SYSTEM_CLEANTIME");
                SystemCleantimePeer.executeQuery("OPTIMIZE TABLE SYSTEM_CLEANTIME");

                NoticeSendPeer.executeQuery("ANALYZE TABLE NOTICE_SEND");
                NoticeSendPeer.executeQuery("OPTIMIZE TABLE NOTICE_SEND");

                NoticeReceivePeer.executeQuery("ANALYZE TABLE NOTICE_RECEIVE");
                NoticeReceivePeer.executeQuery("OPTIMIZE TABLE NOTICE_RECEIVE");

                MailSendPeer.executeQuery("ANALYZE TABLE MAIL_SEND");
                MailSendPeer.executeQuery("OPTIMIZE TABLE MAIL_SEND");
	
		MailReceivePeer.executeQuery("ANALYZE TABLE MAIL_RECEIVE");
                MailReceivePeer.executeQuery("OPTIMIZE TABLE MAIL_RECEIVE");

                GlossaryAliasPeer.executeQuery("ANALYZE TABLE GLOSSARY_ALIAS");
                GlossaryAliasPeer.executeQuery("OPTIMIZE TABLE GLOSSARY_ALIAS");

                GlossaryPeer.executeQuery("ANALYZE TABLE GLOSSARY");
                GlossaryPeer.executeQuery("OPTIMIZE TABLE GLOSSARY");

                DbSendPeer.executeQuery("ANALYZE TABLE DB_SEND");
                DbSendPeer.executeQuery("OPTIMIZE TABLE DB_SEND");

                DbReceivePeer.executeQuery("ANALYZE TABLE DB_RECEIVE");
                DbReceivePeer.executeQuery("OPTIMIZE TABLE DB_RECEIVE");

                //.executeQuery("ANALYZE TABLE COURSEALIAS");
                //.executeQuery("OPTIMIZE TABLE COURSEALIAS");

                CoursesPeer.executeQuery("ANALYZE TABLE COURSES");
                CoursesPeer.executeQuery("OPTIMIZE TABLE COURSES");

               // BasePeer.executeQuery("ANALYZE TABLE COURSECAL_INFORMATION");
               // BasePeer.executeQuery("OPTIMIZE TABLE COURSECAL_INFORMATION");

                CalInformationPeer.executeQuery("ANALYZE TABLE CAL_INFORMATION");
                CalInformationPeer.executeQuery("OPTIMIZE TABLE CAL_INFORMATION");

                NewsPeer.executeQuery("ANALYZE TABLE NEWS");
                NewsPeer.executeQuery("OPTIMIZE TABLE NEWS");

		HintQuestionPeer.executeQuery("ANALYZE TABLE HINT_QUESTION");
		HintQuestionPeer.executeQuery("OPTIMIZE TABLE HINT_QUESTION");

		ProxyUserPeer.executeQuery("ANALYZE TABLE PROXY_USER");
		ProxyUserPeer.executeQuery("OPTIMIZE TABLE PROXY_USER");

		RemoteCoursesPeer.executeQuery("ANALYZE TABLE REMOTE_COURSES");
		RemoteCoursesPeer.executeQuery("OPTIMIZE TABLE REMOTE_COURSES");

		TaskPeer.executeQuery("ANALYZE TABLE TASK");
		TaskPeer.executeQuery("OPTIMIZE TABLE TASK");

		QuizPeer.executeQuery("ANALYZE TABLE QUIZ");
		QuizPeer.executeQuery("OPTIMIZE TABLE QUIZ");
		
		AttendenceSeetPeer.executeQuery("ANALYZE TABLE ATTENDENCE_SEET");
		AttendenceSeetPeer.executeQuery("OPTIMIZE TABLE ATTENDENCE_SEET");

		}
		catch(Exception ex)
        	{
                	ErrorDumpUtil.ErrorLog("The error in optimize database - CommonUtil class !!"+ex);
	        }

		return true;
        }//end of method

	/**
         * The method put the first login entry of instructor in LMS 
	 * in a day, in the database
         * return boolean
         */

        public static boolean  IFLoginEntry(int uid, java.util.Date date) {
		
	try {
		
	        Vector Instructor_Role=UserGroupRoleUtil.getGID(uid,2);
                if(Instructor_Role.size()!=0){
	                int cdate = Integer.parseInt(ExpiryUtil.getCurrentDate(""));
                        Criteria crit=new Criteria();
                        crit.add(AttendenceSeetPeer.USER_ID,uid);
                        List check=AttendenceSeetPeer.doSelect(crit);
                        int intdate=0;
                        if((check.size()) != 0 ){
                        	String find_max="SELECT MAX(ID) FROM ATTENDENCE_SEET WHERE USER_ID="+uid;
                                List v=AttendenceSeetPeer.executeQuery(find_max);
                                int max_entry=0;
                                for(Iterator j=v.iterator();j.hasNext();){
                                	Record item2=(Record)j.next();
                                        max_entry=item2.getValue("MAX(ID)").asInt();
                                }
                                crit=new Criteria();
                                crit.add(AttendenceSeetPeer.ID,max_entry);
                                List check1=AttendenceSeetPeer.doSelect(crit);
                                for(int i=0;i<check1.size();i++){
	                                AttendenceSeet element=(AttendenceSeet)(check1.get(i));
                                        String date1=(element.getLoginDate()).toString();
                                        date1=date1.substring(0,10);
                                        date1=date1.replaceAll("-","");
                                        intdate=Integer.parseInt(date1);
                                }
                        }
			if((intdate < cdate) || (check.size() == 0)) {
                	        if(check.size() > 30) {

                        	        String find_minimum="SELECT MIN(ID) FROM ATTENDENCE_SEET WHERE USER_ID="+uid;
                                        List v=UsageDetailsPeer.executeQuery(find_minimum);
                                        int least_entry=0;
                                        for(Iterator j=v.iterator();j.hasNext();){
                                        	Record item2=(Record)j.next();
                                                least_entry=item2.getValue("MIN(ID)").asInt();
                                        }
                                        crit=new Criteria();
                                        crit.add(AttendenceSeetPeer.ID,least_entry);
                                        crit.add(AttendenceSeetPeer.USER_ID,uid);
                                        AttendenceSeetPeer.doDelete(crit);
                                 }
                                 else {
                                 	crit=new Criteria();
                                        crit.add(AttendenceSeetPeer.USER_ID,uid);
                                        crit.add(AttendenceSeetPeer.LOGIN_DATE,date);
                                        AttendenceSeetPeer.doInsert(crit);
                                 }
                        }
                 }//if top
           }catch(Exception ex){
		ErrorDumpUtil.ErrorLog("The error in IFLoginEntry() - CommonUtil class !!"+ex);
	   }
	   return true;
	}//end of method

	public static void PListing( RunData data , Context context , Vector entry)
        {
                try
                {
                                User user=data.getUser();
                                ParameterParser pp=data.getParameters();
				String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
		                String conf =AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value");
                                int list_conf=Integer.parseInt(conf);

                                context.put("userConf",new Integer(list_conf));
                                context.put("userConf_string",conf);

                                int startIndex=pp.getInt("startIndex",0);
                                int t_size=entry.size();

                                int value[]=new int[7];
                                value=ListManagement.linkVisibility(startIndex,t_size,list_conf);

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
                                Vector splitlist=ListManagement.listDivide(entry,startIndex,list_conf);
                                context.put("entry",splitlist);
                }
                catch(Exception e){data.setMessage("The error in [PListing] of [CommonUtility Util] !!"+e);}
    }//method

		
}//end of class
