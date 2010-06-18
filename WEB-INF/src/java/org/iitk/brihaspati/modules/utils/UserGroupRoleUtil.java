package org.iitk.brihaspati.modules.utils;
/*
 * @(#)UserGroupRoleUtil.java
 *
 *  Copyright (c) 2004-2006 ETRG,IIT Kanpur.
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
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import java.util.Vector;
import java.util.List;
import java.util.ListIterator;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRole;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.apache.turbine.services.security.torque.om.TurbineUser;
/**
  * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a> 
  * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
  */
 
public class UserGroupRoleUtil{

	public static Vector getGID(int uid,int role_id)
	{
		Vector gid=new Vector();
		List v=null;
		try{

			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,uid);
			crit.and(TurbineUserGroupRolePeer.ROLE_ID,role_id);
			v=TurbineUserGroupRolePeer.doSelect(crit);
			for(int i=0;i<v.size();i++){
				TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
				String s=Integer.toString(element.getGroupId());
				gid.add(s);
			}
		}		

		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try getGID()- UserGroupRoleUtil !!"+e);
			//log something
		}
		return gid;
	}
	public static Vector getUID(int gid,int role_id){
		Vector uid=new Vector();
		List v=null;
		int[]  uId={1,0};
		try{
			if(role_id == 0){
				
				Criteria crit=new Criteria();
				crit.add(TurbineUserGroupRolePeer.GROUP_ID,gid);
				crit.andNotIn(TurbineUserGroupRolePeer.USER_ID,uId);
				v=TurbineUserGroupRolePeer.doSelect(crit);
			}
			else{
				Criteria crit=new Criteria();
				crit.add(TurbineUserGroupRolePeer.GROUP_ID,gid);
				crit.and(TurbineUserGroupRolePeer.ROLE_ID,role_id);
				crit.andNotIn(TurbineUserGroupRolePeer.USER_ID,uId);
				v=TurbineUserGroupRolePeer.doSelect(crit);
				
			}
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try1 getUID()- UserGroupRoleUtil !!"+e);
			//Log somehting
		}
		try{

			for(int i=0;i<v.size();i++){
				TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
				String s=Integer.toString(element.getUserId());
				uid.add(s);
			}

		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try2 getUID()- UserGroupRoleUtil !!"+e);
			//Log something
		}

		return uid;
	}

	public static Vector getUID(int gid)
	{
		Vector uid=new Vector();
		int uId[]={1,0};
		List v=null;
		try{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.GROUP_ID,gid);
			crit.andNotIn(TurbineUserGroupRolePeer.GROUP_ID,uId);
			v=TurbineUserGroupRolePeer.doSelect(crit);
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try1 getUID() with one parameter- UserGroupRoleUtil !!"+e);
			//Log something
		}
		try{
			for(int i=0;i<v.size();i++){
				TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
				String s=Integer.toString(element.getUserId());
				uid.add(s);
		}	}
	
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try2 getUID()with one parameter- UserGroupRoleUtil !!"+e);
			//Log something
		}
		return uid;
	}

	public static Vector getUDetail(int gid,int role_id)
	{
		Vector uid=new Vector();
		List v=null;
		int [] uId={1,0};
		try{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.GROUP_ID,gid);
			crit.and(TurbineUserGroupRolePeer.ROLE_ID,role_id);
			crit.andNotIn(TurbineUserGroupRolePeer.USER_ID,uId);
			v=TurbineUserGroupRolePeer.doSelect(crit);
		}
		catch (Exception e)
		{
			ErrorDumpUtil.ErrorLog("The error in try1 getUDetail()- UserGroupRoleUtil !!"+e);
		}
		try{
                        for(int i=0;i<v.size();i++)
			{
                                TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
                                String s=Integer.toString(element.getUserId());
				List st=UserManagement.getUserDetail(s);
				for(int j=0;j<st.size();j++)
				{
                                TurbineUser element1=(TurbineUser)st.get(j);
                                String uName=element1.getUserName();
                                String fName=element1.getFirstName();
                                String lName=element1.getLastName();
                                String eMail=element1.getEmail();
				CourseUserDetail cDetails=new CourseUserDetail();
				String userName=fName+" "+lName;
				cDetails.setLoginName(uName);
				cDetails.setUserName(userName);
				cDetails.setEmail(eMail);
				uid.add(cDetails);
				}
                	}
       		}

                catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try2 getUDetail()- UserGroupRoleUtil !!"+e);
                        //Log something
                }

		return uid;
	}
       /**
         * Modify
         */
	 public static Vector getAllUID(int role_id)
        {
          Vector uid=new Vector();
          Vector disuid=new Vector();
          List v=null;
          	String queryString="select DISTINCT USER_ID from TURBINE_USER_GROUP_ROLE where USER_ID!=1 and USER_ID!=0 and ROLE_ID="+role_id;
          try{
              	v=TurbineUserGroupRolePeer.executeQuery(queryString);
             }
          catch(Exception e)
             {
               //log something
             }
             try
	     {
             	for(ListIterator i=v.listIterator();i.hasNext();)
                {
                	Record item=(Record)i.next();
                	String s=item.getValue("USER_ID").asString();
                	uid.addElement(s);
                }
             }
            catch(Exception e)
               {
			ErrorDumpUtil.ErrorLog("The error in try2 getAllUID()- UserGroupRoleUtil !!"+e);
              //log something
               }
            return uid;
          }

	public static Vector getRID(int uid,int gid)
	{
		Vector roleid=null;
		List v=null;
		try{
			Criteria crit=new Criteria();
			crit.add(TurbineUserGroupRolePeer.USER_ID,uid);
			crit.and(TurbineUserGroupRolePeer.GROUP_ID,gid);
			v=TurbineUserGroupRolePeer.doSelect(crit);
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try1 getRID()- UserGroupRoleUtil !!"+e);
			//Log something
		}
		try{
			for(int i=0;i<v.size();i++){
				TurbineUserGroupRole element=(TurbineUserGroupRole)v.get(i);
				String s=Integer.toString(element.getRoleId());
				roleid.add(s);
			}
		}
		catch(Exception e){
			ErrorDumpUtil.ErrorLog("The error in try2 getRID()- UserGroupRoleUtil !!"+e);
			//Log some thing
		}
		return roleid;
	}
    public static String getRoleName(int rid)
    {
	    if(rid==1)
		    return("turbine_root");
	    else
	    if(rid==2)
		    return("instructor");
	    else
	    if(rid==3)
		    return("student");
	    else
	    if(rid==4)
		    return("group_admin");
	    else
	    if(rid==5)
		    return("author");
	    else
	    if(rid==6)
		    return("user");
			    
	    else
		    return("error");
	    
   }
}

