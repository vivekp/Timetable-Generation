package org.iitk.brihaspati.modules.utils;

/*  @(#) GroupUtil.java
 *  Copyright (c) 2004 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */  

import org.apache.torque.util.Criteria;
import java.util.List;
import java.util.Vector;
import org.apache.turbine.services.security.torque.om.TurbineGroupPeer;
import org.apache.turbine.services.security.torque.om.TurbineGroup;

/**
 * This class is provides group details of user
 *
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @version 1.0
 * @since 1.0
 * @see UserGroupRoleUtil
 */

public class GroupUtil{

	/**
         * This method gives group Id 
	 * @param groupName String
         * @return int
         */

	public static int getGID(String groupName)
	{
		int gid=0;
	
		try{
			Criteria crit=new Criteria();
			crit.add(TurbineGroupPeer.GROUP_NAME,groupName);
			List group_list=TurbineGroupPeer.doSelect(crit);
			
			gid=((TurbineGroup)group_list.get(0)).getGroupId();
		}catch(Exception e){
			//error log
		}
		return gid;

	}

	/**
         * This method provides group name
	 * @param gid Integer
         * @return String
         */

	public static String getGroupName(int gid)
	{
		String groupName=null;

		try{
			Criteria crit=new Criteria();
			crit.add(TurbineGroupPeer.GROUP_ID,Integer.toString(gid));
			List group_list=TurbineGroupPeer.doSelect(crit);
			
			groupName=((TurbineGroup)group_list.get(0)).getName();
		}catch(Exception e){
			//log exception
		}
		return groupName;
	}

	/**
         * This method provides group name
	 * @param userId Integer
	 * @param roleId Integer
         * @return String
         */

	public static String getGroupName(int userId, int roleId){
                String gname="";
                Vector v=UserGroupRoleUtil.getGID(userId,roleId);
                for(int c=0;c<v.size();c++){
                        String gId=(String)(v.elementAt(c));
                        int gid=Integer.parseInt(gId);
                        gname=getGroupName(gid);
                }
                return gname;
        }


}
