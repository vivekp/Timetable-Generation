package org.iitk.brihaspati.modules.utils;
/*
 * @(#)QuotaUtil.java
 *
 *  Copyright (c) 2008-2009 ETRG,IIT Kanpur.
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 */
import java.io.File;
import java.util.Vector;
import java.util.List;
import java.math.BigDecimal;
import java.util.StringTokenizer;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.TurbineUserPeer;
import org.iitk.brihaspati.om.TurbineUser;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.Courses;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.commons.io.FileSystemUtils;
import org.iitk.brihaspati.modules.utils.AdminProperties;


/**
 *  @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class QuotaUtil {
	public static boolean CreateandUpdate(){
		//Create Quota of User in Turbine User Table
		try{
		String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                String userquotasize =AdminProperties.getValue(path,"brihaspati.user.quota.value");
                Criteria crit=new Criteria();
                crit.addGroupByColumn(TurbineUserPeer.LOGIN_NAME);
                List lst=TurbineUserPeer.doSelect(crit);
                int cqt=0;
                for(int j=0;j<lst.size();j++){
                	TurbineUser tulst=(TurbineUser)lst.get(j);
                        BigDecimal qt=tulst.getQuota();
			int uid=tulst.getUserId();
                        long quota=qt.longValue();
                        if(quota != 0){
				if(Long.valueOf(userquotasize).longValue() > quota){
				//	ErrorDumpUtil.ErrorLog("In Side loop util");
                        		String uquery="UPDATE TURBINE_USER SET QUOTA='"+userquotasize+"' where USER_ID="+uid;
                                	TurbineUserPeer.executeStatement(uquery);
				}
                        }
                        else{
                        	String uquery="UPDATE TURBINE_USER SET QUOTA="+"'"+userquotasize+"'";
                                TurbineUserPeer.executeStatement(uquery);
                        }
                }
//Create Quota of User in Courses Table
                String Coursequotasize =AdminProperties.getValue(path,"brihaspati.admin.quota.value");
                crit=new Criteria();
                crit.addGroupByColumn(CoursesPeer.GROUP_NAME);
                List clst=CoursesPeer.doSelect(crit);
                for(int k=0;k<clst.size();k++){
                	Courses crslst=(Courses)clst.get(k);
                        BigDecimal qta=crslst.getQuota();
			String gid=crslst.getGroupName();
                        long cquota=qta.longValue();
                        if(cquota != 0){
				if(((Long.valueOf(Coursequotasize)).longValue()) > cquota){
                                        String cquery="UPDATE COURSES SET QUOTA='"+Coursequotasize+"' where GROUP_NAME='"+gid+"'";
                                        CoursesPeer.executeStatement(cquery);
                                }
                        }
                        else{
                        	String cquery="UPDATE COURSES SET QUOTA="+"'"+Coursequotasize+"'";
                                CoursesPeer.executeStatement(cquery);
                        }
                }
	}
	catch(Exception ex){ErrorDumpUtil.ErrorLog("The exception in create and update quota"+ex);}
	return true;
	}
// convert the file size in MB
	public static long getDirSizeInMegabytes(File dir) {
		return getDirSize(dir) / 1024 / 1024;
	}
//Get the file size in any dir
	static long getDirSize(File dir) {
		long size = 0;
		if (dir.isFile()) {
			size = dir.length();
		} else {
			File[] subFiles = dir.listFiles();
			for (File file : subFiles) {
				if (file.isFile()) {
					size += file.length();
				} else {
					size += getDirSize(file);
				}
			}
		}
		return size;
	}
//This methods gives course quota from database of particular course
	public static long getCrsQuota(String gname){
		long quota=0;
		try{
		Criteria crit=new Criteria();
		crit.add(CoursesPeer.GROUP_NAME,gname);
                List lst=CoursesPeer.doSelect(crit);
		for(int k=0;k<lst.size();k++)
                {
			Courses ele=(Courses)lst.get(k);
                        String scr=(ele.getQuota()).toString();
                        quota=Long.parseLong(scr);
		}
		}
		catch(Exception EX){ System.out.println("The error in getting Course Quota (Quota Util)"+EX);}
		return quota;
	}
//This methods gives user quota from database  of particular user
	public static long getUsrQuota(int uid){
		long quota=0;
		try{
			Criteria crit=new Criteria();
			crit.add(TurbineUserPeer.USER_ID,uid);
                        List lst=TurbineUserPeer.doSelect(crit);
			for(int k=0;k<lst.size();k++)
	                {
				TurbineUser element=(TurbineUser)lst.get(k);
                                quota=Long.parseLong((element.getQuota()).toString());
			}
		}
		catch(Exception EX){ System.out.println("The error in getting user Quota (Quota Util)"+EX);}
		return quota;
	}
/*
*This method return the availability of user area space
*/
	public static boolean CompareQuotainRoleUser(long tsize,int uid){
		boolean flag=false;
		long uquota=getUsrQuota(uid);
		if(uquota>tsize)
                {
                        flag=true;
                }
                return flag;
	}
/*
*This method return the availability of course area space
*/
	public static boolean CompareQuotainCourse(long dir,String gname){
		boolean flag=false;
		long Crsquota=getCrsQuota(gname);
		if(Crsquota>dir)
                {
                        flag=true;
                }
                return flag;
	}
/*
* This method return true if available disk space for new user
*/ 
	public static long checkUsrSpace(){
		long differ=0;
		try{
                long dspace=getFileSystemSpace();
                String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                String SpacefPrp=AdminProperties.getValue(path,"brihaspati.user.quota.value");
                long UQuota=new Long(SpacefPrp).longValue();
                differ=dspace-UQuota;
		}
		catch(Exception ex){System.out.println("The error in checkUsrCrsSpace (Quota Util)"+ex);}
                return differ;
        }

/*
* This method return true if available disk space for new user with course
*/
        public static long checkUsrCrsSpace(){
		long differ=0;
		try{
                long dspace=getFileSystemSpace();
                String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
                String SpacefPrp=AdminProperties.getValue(path,"brihaspati.user.quota.value");
                long UQuota=new Long(SpacefPrp).longValue();
                String Spacefcrs=AdminProperties.getValue(path,"brihaspati.admin.quota.value");
                long CQuota=new Long(Spacefcrs).longValue();
                differ=dspace-(UQuota+CQuota);
		}
		catch(Exception ex){System.out.println("The error in checkUsrCrsSpace (Quota Util)"+ex);}
                return differ;

        }
/*
* This method return Free disk space for brihaspati users
*/
	public static long getFileSystemSpace() {
	         long fdisks =0;
		 try{
			 String path=TurbineServlet.getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
        	         String dirName=AdminProperties.getValue(path,"brihaspati.home.dir.value");
			 if(dirName.equals("")){
				dirName=System.getProperty("user.home");
			 }
//			ErrorDumpUtil.ErrorLog("The disk dir name"+dirName);
	        	 fdisks = FileSystemUtils.freeSpaceKb(dirName);
			 fdisks = fdisks/1024;
//			ErrorDumpUtil.ErrorLog("The space is on the disk"+fdisks);
        	 } catch (Exception ex) {
	             ex.printStackTrace();
        	 }
	return fdisks;
     }
}
