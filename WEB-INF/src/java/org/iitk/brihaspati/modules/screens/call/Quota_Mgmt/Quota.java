package org.iitk.brihaspati.modules.screens.call.Quota_Mgmt;

/*
 * @(#)Quota.java	
 *
 *  Copyright (c)2008- 2009 ETRG,IIT Kanpur. 
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

import java.util.List;
import java.util.Vector;
import java.math.BigDecimal;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.TurbineUserPeer;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;
/**
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */

public class Quota extends SecureScreen_Admin
{
    /**
     * Place all the data object in the context
     * for use in the template.
     */
	public void doBuildTemplate( RunData data, Context context )
	{
		try
		{
			String mode=data.getParameters().getString("mode"," ");
			Criteria crit=new Criteria();
			int uid[]={0,1};
			crit.addNotIn(TurbineUserPeer.USER_ID,uid);
			crit.addGroupByColumn(TurbineUserPeer.USER_ID);
			List lst=TurbineUserPeer.doSelect(crit);
			Vector vct=new Vector(lst);
			CommonUtility.PListing(data ,context ,vct);
			if(mode.equals("cquota"))
			{
				crit=new Criteria();
				crit.addGroupByColumn(CoursesPeer.GROUP_NAME);
				List clst=CoursesPeer.doSelect(crit);
				Vector cvct=new Vector(clst);
				CommonUtility.PListing(data ,context ,cvct);
			}
			context.put("mode",mode);
			if(mode.equals("edit")){
				String uidedit=data.getParameters().getString("uid");
				String Name=data.getParameters().getString("name");
				String quota=data.getParameters().getString("quota");
				context.put("uid",uidedit);
				context.put("name",Name);
				context.put("quota",quota);
			}
			if(mode.equals("cedit")){
                                String cName=data.getParameters().getString("cname");
                                String cQuota=data.getParameters().getString("cquota");
                                String gname=data.getParameters().getString("grName");
                                context.put("gname",gname);
                                context.put("cname",cName);
                                context.put("cquota",cQuota);
			}
		}
		catch(Exception ex)
		{data.setMessage("The error in getting list:"+ex);}		
	}
}


