package org.iitk.brihaspati.modules.utils;


/*@(#)UpdateInfoMail.java

 *  Copyright (c) 2008 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
import java.util.List;
import java.util.Vector;

//turbine
import org.apache.torque.util.Criteria;
import org.apache.turbine.services.servlet.TurbineServlet;

//brihaspati
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.NoticeReceivePeer;
import org.iitk.brihaspati.om.NoticeSendPeer;
import org.iitk.brihaspati.om.NoticeReceive;
import org.iitk.brihaspati.om.DbSend;
import org.iitk.brihaspati.om.News;
import org.iitk.brihaspati.om.NoticeSend;
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.CoursesPeer;

import org.apache.turbine.services.security.torque.om.TurbineGroupPeer;
import org.apache.turbine.services.security.torque.om.TurbineGroup;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;

/**
 * This class is used for call the method im mylogin 
 * like Create index for Search, Clean the system 
 * 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:arvindjss17@yahoo.co.in">Arvind Pal</a> 
 * @since 1.0
 * @see ExpiryUtil 
 */
public class UpdateInfoMail{

	public static Vector userListfrmxml(){
		try{
			/**  Getting the path of Updation mail File to send Message */
                        String updatePath=TurbineServlet.getRealPath("/Updationmail");
                        File f=new File(updatePath);
                        String TitalMailMessage="";
                        if(!f.exists())
                                f.mkdirs();
                        /**
                        * Creating the blank xml & maintaining the record
                        * @see TopicMetaDataXmlWriter in Util
                        */

                        File updatexml = null;
                        XmlWriter xmlwriter=null;
                        updatexml = new File(updatePath+"/Update__des.xml");
                        Vector updatelist = new Vector();
                        if(updatexml.exists())
                        {
                        	TopicMetaDataXmlReader topicmetadata=null;
                                topicmetadata= new TopicMetaDataXmlReader(updatePath+"/Update__des.xml");
                                updatelist=topicmetadata.getUpdationDetails();
                        }
                        createXML();
                        return updatelist;
		}catch (Exception ex){
			ErrorDumpUtil.ErrorLog("The error in User List from XML() - UpdateInfoMail class !!"+ex);
         		System.out.println("See Exception message in ExceptionLog.txt file:: ");
		        return null;
		}
	}//end of method
	public static void createXML(){
		try{
			String updatePath=TurbineServlet.getRealPath("/Updationmail");
			File updatexml = new File(updatePath+"/Update__des.xml");
			if(!updatexml.exists()){
				XmlWriter xmlwriter=null;
				TopicMetaDataXmlWriter.updationRootOnly(updatexml.getAbsolutePath());
                        	xmlwriter=new XmlWriter(updatePath+"/Update__des.xml");
			}	
               	}catch(Exception e){}
	}
	
	public static List userList(){
        	try{
			Criteria crit =new Criteria();
                        crit.addJoin(TurbineUserPeer.USER_ID,TurbineUserGroupRolePeer.USER_ID);
                        crit.add(TurbineUserGroupRolePeer.ROLE_ID,3);
                        crit.or(TurbineUserGroupRolePeer.ROLE_ID,2);
                        crit.or(TurbineUserGroupRolePeer.ROLE_ID,1);
                        crit.addAscendingOrderByColumn(TurbineUserPeer.USER_ID);
                        crit.setDistinct();
                        List userList=TurbineUserPeer.doSelect(crit);
			ErrorDumpUtil.ErrorLog(" size in updatelist userList() method -- "+userList.size());
			return userList;
		}catch(Exception ex) {
        		ErrorDumpUtil.ErrorLog("The error in User List () - UpdateInfoMail class !!"+ex);
			System.out.println("See Exception message in ExceptionLog.txt file:: ");
                	return null;
	        }
        }//end of Method

	/**
	 * This method display all task which are moved to index page
	 * @return Vector listTask 
	 */
	public static void checknWriteXml(){
		try{
                	/** To maintain the strength of user per day to send Updation mail
                          * @param stat[] String Array
                        */
			
                        String  stat[]={"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
                        String updatePath=TurbineServlet.getRealPath("/Updationmail");
			Vector updatelist=userListfrmxml();
                //        ErrorDumpUtil.ErrorLog(" size in updatelist "+updatelist.size());
			List userList=userList();
                        //ErrorDumpUtil.ErrorLog("size in userList -->"+userList.size()+" size in updatelist "+updatelist.size());
			//Getting the strength of users from database//
                        int userSize =userList.size();
                        //fixing users strength  per day
                        int userDay=userSize/7;
                        // to send mail Rest users, less then 7
                        int remender=userSize%7;
                        int temp=0;
                        if( updatelist.size() == 0)
                                temp=0;
                        else
                                temp=updatelist.size();
                        XmlWriter xmlwriter=null;
                        if(temp!=userSize )
                        { //if1
                        	if(temp!=0)
                                { //if2
                        	//	ErrorDumpUtil.ErrorLog("updatelist ..read from xml.."+updatelist.size());
                                       //To remove the directory
                                        File f=new File(updatePath+"/Update__des.xml");
					f.delete();
					createXML();
                                } //if2
				int gi=0;
                                int count=0; int temp1=userDay;
                                //String fPath= updatePath;
                                File f1 = new File(updatePath);
                                if(f1.exists() && f1.isDirectory()) {
                        	//	ErrorDumpUtil.ErrorLog("updatlist ..read from xml.12--->  "+userList.size());
                                	for(int c1=0;c1<userList.size();c1++)
                                        { 
                                        	TurbineUser element=(TurbineUser)(userList.get(c1));
                                                int uId=element.getUserId();
                                                String eMail=element.getEmail();
                                                String userId=Integer.toString(uId);
                                                //setting day....
                                                if(gi==temp1 && count<stat.length-1) {
							count=count+1;
                                                        temp1=temp1+userDay;
                                                }
                                                /**Here we appending the entry in the xml File
                                                *@see TopicMetaDataXmlWriter in Util
                                                */
						
            					xmlwriter=TopicMetaDataXmlWriter.WriteXml_UpdationInfo(updatePath,"/Update__des.xml");
                                		TopicMetaDataXmlWriter.appendUpdationInfoMailElement(xmlwriter,userId,eMail,stat[count]);
                                                xmlwriter.writeXmlFile();
                                                gi++;
					} //for(1) end
                             	} //if(1) end
                                temp=userSize;
                        }
 		}catch(Exception ex) {
                	ErrorDumpUtil.ErrorLog("The error in checknWriteXml - UpdateInfoMail class !!"+ex);
			System.out.println("See Exception message in ExceptionLog.txt file:: ");
              }
	}//end of Method
	public static String getUpdationMail(){
                String Mail_msg="";
                try {
                      //  ErrorDumpUtil.ErrorLog("start get Updation Mail !!");
                        java.util.Date curDate=new java.util.Date();
                        long longCurDate= curDate.getTime();
                        String server_name=TurbineServlet.getServerName();
                        String srvrPort=TurbineServlet.getServerPort();
                        String updatePath=TurbineServlet.getRealPath("/Updationmail");
                        File f=new File(updatePath);
                        if(!f.exists())
                                f.mkdirs();
                        File updatexml= new File(updatePath+"/Update__des.xml");
                        TopicMetaDataXmlReader topicmetadata=null;
                        Vector updatelist = new Vector();
                        topicmetadata= new TopicMetaDataXmlReader(updatePath+"/Update__des.xml");
                        updatelist=topicmetadata.getUpdationDetails();
                        String emailId="";
                        for(int c=0;c<updatelist.size();c++) {
                                // xmluid1 contains day (eg. Sunday )
                                String xmluid1 =((FileEntry) updatelist.elementAt(c)).getstatus();
                        //	ErrorDumpUtil.ErrorLog("start get Updation Mail 1 !!"+xmluid1);
                                String cday=curDate.toString();
                                String day="";
                                for(int d=0;d<3;d++ )
                                        day=day+cday.charAt(d);
                                        /**  read xml file    */
                                if(day.equals(xmluid1)) {
                        	//	ErrorDumpUtil.ErrorLog("start get Updation Mail 2!!"+xmluid1);
                                        String xmluid =((FileEntry) updatelist.elementAt(c)).getuserid();
                                        String emailId1=((FileEntry) updatelist.elementAt(c)).getemailId();
                                        int uId=Integer.parseInt(xmluid);
                                        if(uId==1) {
                                                String Messagebackup ="Taken backup";
                                                Mail_msg=MailNotification.sendMail(Messagebackup,emailId1,"","Updation Mail","","","",server_name,srvrPort,"");
                                        }
                                        else
                                        {
						String TitalMailMessage="";
                                                TitalMailMessage=getAllMessage(uId);
                                                if(!TitalMailMessage.equals("")){
                                                	Mail_msg=MailNotification.sendMail(TitalMailMessage,emailId1,"","Updation Mail","","","",server_name,srvrPort,"english");
                                  //              	ErrorDumpUtil.ErrorLog("This is  else  condision !!!!!");
                                                }
                                        }
                                } //if

                        } //for
                }catch(Exception e){ErrorDumpUtil.ErrorLog("The error in getUpdationMail() class UpdateInfoMail util else condition"+e);}
                return Mail_msg;
        }
	public static String getAllMessage(int u_id)
        {
                String msg_all="";
                try{
                        msg_all=getCourss(u_id)+getNews(u_id)+getNotics(u_id)+getDiscussionBoard(u_id);
			return msg_all;
                }catch(Exception ex){   
			ErrorDumpUtil.ErrorLog("Error in UpdateInfoMail util getAllMessage method -->"+ex.getMessage());
                        return msg_all;
		}
        }//method
	
	public static String getCourss(int u_id){
		java.util.Date curDate=new java.util.Date();
		long longCurDate= curDate.getTime();
		String Message="";
		
		try {
                	Criteria crit11=new Criteria();
                        crit11.addJoin(TurbineGroupPeer.GROUP_ID,TurbineUserGroupRolePeer.GROUP_ID);
                        crit11.add(TurbineUserGroupRolePeer.USER_ID,u_id);
                        crit11.setDistinct();
                        List userCourse=TurbineGroupPeer.doSelect(crit11);
                        for(int i7=0;i7<userCourse.size();i7++) {
			        TurbineGroup element2=(TurbineGroup)(userCourse.get(i7));
                        	int gname=element2.getGroupId();
                                if((gname!=1) && (gname!=2)) {
                                	String gname1=element2.getName();
                                        //get All deatails of Courses
                                        Criteria crit=new Criteria();
                                        crit.add(CoursesPeer.GROUP_NAME,gname1);
                                        List userList=CoursesPeer.doSelect(crit);
                                        for(int co=0;co<userList.size();co++){
	                                        Courses element1=(Courses)(userList.get(co));
                                                String course_id=element1.getCname();
                                                java.util.Date modiDate=element1.getLastmodified();
                                                java.util.Date curDate1=new java.util.Date();
                                                long longcurdate=curDate1.getTime();
                                                long longmodidate=modiDate.getTime();
                                                long coursedate=(longCurDate-longmodidate)/(24*3600*1000);
                                                if(coursedate<7 && (longcurdate-longmodidate)!=0)
        	                                        Message=Message+" "+course_id+" ";
                			}//inner for
                                }  //if
                       	}//outer for
			if(!Message.equals("")){
                                Message=" Courss is "+Message;
                                return Message;
                        }
		}catch(Exception ex){
			ErrorDumpUtil.ErrorLog("Error in Courss -->"+ex.getMessage());
			return Message;
		}
		
		return Message;
	}
	
	public static String getNews(int u_id){
                        java.util.Date curDate=new java.util.Date();
		long longCurDate= curDate.getTime();
		String Message="";
		try {
                	Criteria crit3=new Criteria();
                        crit3.add(TurbineUserGroupRolePeer.USER_ID,u_id);
                        List newsUser=TurbineUserGroupRolePeer.doSelect(crit3);
                        for(int i=0;i<newsUser.size();i++){//for nws
				TurbineUserGroupRole element=(TurbineUserGroupRole)(newsUser.get(i));
                                int gid=element.getGroupId();
                                if(gid!=1) {
					Criteria crit12=new Criteria();
                                        crit12.add(NewsPeer.GROUP_ID,gid);
                                        List newsGid=NewsPeer.doSelect(crit12);
                                        for(int as=0;as<newsGid.size();as++){
	                                        News element1=(News)(newsGid.get(as));
                                                String news_id=(element1.getNewsTitle());
                                                java.util.Date pd=element1.getPublishDate();
                                                long longModiDate = pd.getTime();
                                                long newsdate=(longCurDate-longModiDate)/(24*3600*1000);
                                                if(newsdate<7 || (longCurDate-longModiDate)!=0 )
          	                                      Message=Message+" "+news_id+" ";
                                        }
 				}
			}//for nws
			if(!Message.equals("")){
                                Message=" News is "+Message;
                                return Message;
                        }
		}catch(Exception ex){
			ErrorDumpUtil.ErrorLog("Error in News -->"+ex.getMessage());
			return Message;
		}
                return Message;
	}
	
	public static String getNotics(int u_id){
                        java.util.Date curDate=new java.util.Date();
		long longCurDate= curDate.getTime();
		String Message="";
                try {
                	// get All deatails of Notices
                        Criteria crit5=new Criteria();
                        crit5.add(NoticeReceivePeer.RECEIVER_ID,u_id);
                        List userNotice= NoticeReceivePeer.doSelect(crit5);
                        for(int i=0;i<userNotice.size();i++) {//for notice
	                        NoticeReceive element=(NoticeReceive)(userNotice.get(i));
                                int  userid1=(element.getReceiverId());
                                String userid2=Integer.toString(userid1);
                                int Notice_id=(element.getNoticeId());
                                Criteria crit2=new Criteria();
                                crit2.add(NoticeSendPeer.NOTICE_ID,Notice_id);
                                List noticeId=NoticeSendPeer.doSelect(crit2);
                                for(int k=0;k<noticeId.size();k++) {
	                                NoticeSend element1=(NoticeSend)(noticeId.get(k));
                                        String Notice_sub=(element1.getNoticeSubject());
                                        java.util.Date pd=element1.getPostTime();
                                        long longModiDate=pd.getTime();
                                        long noticeDate=(longCurDate-longModiDate)/(24*3600*1000);
                                        if(noticeDate<7 )
         		                        Message=Message+" "+Notice_sub+" ";
                                }
			}//for notice
			if(!Message.equals("")){
                                Message=" Notics is "+Message;
                        	return Message;
			}
 		}catch(Exception ex){
			ErrorDumpUtil.ErrorLog("Error in Notic -->"+ex.getMessage());
			return Message;	
		}
		return Message;
	}
	
	public static String getDiscussionBoard(int u_id){
               	java.util.Date curDate=new java.util.Date();
		long longCurDate= curDate.getTime();
		String Message="";
		try {
                	//get All deatails of discussion
                        Criteria crit4=new Criteria();
                        crit4.addGroupByColumn(DbSendPeer.MSG_SUBJECT);
                        List disSub=DbSendPeer.doSelect(crit4);
                        for(int i=0;i<disSub.size();i++) {//for dis
	                        DbSend element=(DbSend)(disSub.get(i));
                                String DB_topic=(element.getMsgSubject());
                                java.util.Date pd=element.getPostTime();
                                int userid1=(element.getUserId());
                                String userid2=Integer.toString(userid1);
                                long longModiDate=pd.getTime();
                                long DBDate=(longCurDate-longModiDate)/(24*3600*1000);
                                if(DBDate<7 || (longCurDate-longModiDate)!=0 )
         	                	Message=Message+" "+DB_topic+" ,";
                	}//for dis
			if(!Message.equals("")){
				Message=" Discussion Board "+Message;
				return Message;
			}
		}catch(Exception ex){ErrorDumpUtil.ErrorLog("Error in Discussion-->"+ex.getMessage());return Message;}
		return Message;
	}
	
					

}//end of class
