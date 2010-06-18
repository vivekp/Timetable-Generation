package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)Permission.java
 *
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur.
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


import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Author;
import org.apache.turbine.util.parser.ParameterParser;

/**
* @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
* @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
*/


public class Permission extends SecureScreen_Author
{
	/**
        *@param data RunData
        *@param context Context
        */
  
	public void doBuildTemplate(RunData data,Context context)
	{
		try{
			
			/**
     			* Place all the data object in the context
     			* for use in the template.
     			*/
			String status=new String();
			ParameterParser pp=data.getParameters();	
			String topic=pp.getString("topic");
			context.put("contentlist",topic);
			String stat=pp.getString("stat","");
			String statvalue=pp.getString("statvalue","");
			context.put("status",stat);
			context.put("statvalue",statvalue);
			String role=pp.getString("group1","");
			context.put("role",role);
		}//try
		catch(Exception e)
		{
			data.setMessage("The error in Permission Screens !!"+e);
		}
	}
}
