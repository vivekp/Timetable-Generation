package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;

/*
 * @(#)BrihaspatiEditor.java	
 *
 *  Copyright (c) 2008 ETRG,IIT Kanpur. 
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
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

/**
 *This class contains code for Creating a group
 *@author: <a href="mailto:seema_020504@yahoo.com">Seemapal</a>
 *@author: <a href="mailto:kshuklak@rediffmail.com">Kishore Kumar shukla</a>
 */

public class BrihaspatiEditor extends SecureScreen{

	public void doBuildTemplate(RunData data,Context context) 
	{
		try
		{
			User user=data.getUser();
                        String authorname=user.getName();
                        context.put("username",authorname);
                	String C_Name=(String)user.getTemp("course_name");
			context.put("Cname",C_Name);

			String UserPath=data.getServletContext().getRealPath("/BrihaspatiEditor");
			File topicDir=new File(UserPath);
			String filter[]={".jar","myKeystore","lib","tmp","src","CVS"};
                        NotInclude exclude=new NotInclude(filter);
                        String ContentList []=topicDir.list(exclude);

                        Vector y =new Vector();
                        for(int j=0;j<ContentList.length;j++)
                        {
                        	y.add(ContentList[j]);
                        }
                        context.put("contentvalue",y);

		}
	 	catch(Exception e)
                {
                        data.addMessage("The error in Reload page :-"+e);
                }

	}
}

