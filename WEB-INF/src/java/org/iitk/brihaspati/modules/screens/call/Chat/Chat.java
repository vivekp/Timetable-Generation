package org.iitk.brihaspati.modules.screens.call.Chat;

/*
 * @(#) Chat.java
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
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/**
 * In this class, Get all details for Chat applet server
 *
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */
public class Chat extends SecureScreen
{
    /**
     * Place all the data object in the context
     * for use in the template.
     */
    public void doBuildTemplate( RunData data, Context context )

    {
	  try{
		/**
		* Get UserName, Passwd, CourseId, serverName and Base Path
		*/
          	String uname=data.getUser().getName();
	  	String pword=data.getUser().getPassword();
	  	String cid=data.getUser().getTemp("course_id").toString();
	  	String hostIP=data.getServerName();
	  	String codeBase=data.getServerScheme()+"://"+hostIP+":"+data.getServerPort()+data.getContextPath()+"/babylon/";
	  	context.put("serverName",hostIP);
	  	context.put("codeBase",codeBase);
	  	context.put("password",pword);
	  	context.put("chatRoom",cid);
	  	context.put("username",uname);
	  	context.put("course",data.getUser().getTemp("course_name").toString());
		}
	catch(Exception ex)
	{
		data.setMessage("The error in chat !!"+ex);
	}
          
    }
}

