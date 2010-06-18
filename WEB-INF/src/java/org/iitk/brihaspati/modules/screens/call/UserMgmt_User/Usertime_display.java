package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)Usertime_display.java	
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur. 
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

import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.UsageDetailsPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil; 
import java.util.List;
/**
 * This class responsible for user login time display
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 */

public class Usertime_display extends SecureScreen_Instructor
{
    public void doBuildTemplate( RunData data, Context context )
    {
	try
        {
		String LangFile =(String)data.getUser().getTemp("LangFile");  
		String course_name=(String)data.getUser().getTemp("course_name");
                context.put("course",course_name);
		String Username=data.getParameters().getString("username");
                int uid=UserUtil.getUID(Username);
                Criteria crit=new Criteria();
                crit.add(UsageDetailsPeer.USER_ID,uid);
                List v=UsageDetailsPeer.doSelect(crit);
		
                if(v.size()!=0)
                {
                        context.put("usage",v);
                        context.put("username",Username);
                        context.put("status","NoBlank");
                }
                else
                {
			if(LangFile.endsWith("_urd.properties"))
                               data.setMessage(MultilingualUtil.ConvertedString("adminUsrTime",LangFile) +" "+Username);
                       else
                               data.setMessage(Username+ MultilingualUtil.ConvertedString("adminUsrTime",LangFile));

                        context.put("status","Blank");
                }
        }
        catch(Exception e)
        {
                data.setMessage("The Error "+e);
        }

    }
}

