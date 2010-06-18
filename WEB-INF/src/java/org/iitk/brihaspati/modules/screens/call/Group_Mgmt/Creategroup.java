package org.iitk.brihaspati.modules.screens.call.Group_Mgmt;

/*
 * @(#)Creategroup.java
 *
 *  Copyright (c) 2006-07 ETRG,IIT Kanpur.
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

/**
 *This class contains code for Creating a group
 *@author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *@author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;

public class Creategroup extends SecureScreen
{
	/**
     	* Place all the data object in the context
     	* for use in the template.
     	*/
	public void doBuildTemplate(RunData data, Context context)
	{
		try
		{
			
                        ParameterParser pp=data.getParameters();

			/**
                        * Get courseid  and coursename for the user currently logged in
                        *Put it in the context for Using in templates
                        * @see UserUtil in Util.
                        */
                	User user=data.getUser();
			context.put("coursename",(String)user.getTemp("course_name"));
			String courseid=(String)user.getTemp("course_id");
			context.put("courseid",courseid);

			/**
                        *Retrieve the parameters by using the ParameterParser
                        *Putting the parameters context for use in templates.
                        */
			String groupname=pp.getString("groupname","");
			context.put("groupname",groupname);
			String grouptype=pp.getString("grouptype","");
			context.put("grouptype",grouptype);
			String groupdesc=pp.getString("groupdesc","");
			context.put("groupdesc",groupdesc);
			String actionName=pp.getString("actionName","");
			String groupno =pp.getString("groupno","");
                        String studentno =pp.getString("studentno","");
			String flag=" ";
			if(actionName.equals("eventSubmit_doCreategrouptype"))
				flag="false";
			else
				flag="true";
			context.put("flag",flag);
		}//try
		catch(Exception e){
                                   ErrorDumpUtil.ErrorLog("Error in Screen:Creategroup !!"+e);	
                                   data.setMessage("See ExceptionLog !! " );
                                  }
	}
}
