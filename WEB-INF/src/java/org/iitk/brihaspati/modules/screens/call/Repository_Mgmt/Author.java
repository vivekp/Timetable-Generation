package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)Author.java
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
 */

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Author;
import java.io.File;

/**
* @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
* @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
* @author <a href="mailto:manjaripal@yahoo.co.in">Manjari Pal</a>
*/

public class Author extends SecureScreen_Author
{
	/**
        *@param data RunData
        *@param context Context
        */

        public void doBuildTemplate( RunData data, Context context )
	{//do
        	try
		{
         		/**
                	*Get the UserName and
                	*put it in the context
                	*for template use
                	*/
                	User user=data.getUser();
                	String username=user.getName();
			
                	context.put("username",username);
                	context.put("authorname",username);
                	String checkForUser="";
               		String UserPath=data.getServletContext().getRealPath("/Repository");
			/**
			*above get the Author name
			*check for the Login author
			*/
               		File dirHandle=new File(UserPath);
               		String author[]=dirHandle.list();
               		for(int i=0;i<author.length;i++)
               		{
               			if(author[i].equals(username))
				{
					checkForUser="disable";
                			context.put("check",checkForUser);
				}//if
			}//for
          	}//try
                catch(Exception e){}
        }//do
}//class

