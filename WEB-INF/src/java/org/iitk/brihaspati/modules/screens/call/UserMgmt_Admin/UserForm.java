package org.iitk.brihaspati.modules.screens.call.UserMgmt_Admin;

/*
 * @(#)UserForm.java	
 *
 *  Copyright (c) 2004 - 2006 ETRG,IIT Kanpur. 
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
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.UserUtil;
/**
  * This class contains code for edit user profile according specific username
  * Grab all the records in a table using a Peer, and
  * place the List of data objects in the context
  * where they can be displayed by a #foreach loop.
  *
  * @author  <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
  * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a> 
  */
public class UserForm extends SecureScreen_Admin{
	 /**
	   * Place all the data object in the context for use in the template.
	   * @param data RunData instance
	   * @param context Context instance
	   * @exception Exception, a generic exception
	   */ 
	public void doBuildTemplate(RunData data, Context context){
		try{
			ParameterParser pp=data.getParameters();
			String userName=pp.getString("username");
			String modetype=pp.getString("Process");
			String mode=pp.getString("mode");
			String stat=pp.getString("status");
			int uid=UserUtil.getUID(userName);
			List userList=UserManagement.getUserDetail(Integer.toString(uid));
			context.put("udetail",userList);
			context.put("Process",modetype);
			context.put("mode",mode);
			context.put("status",stat);
		}
		catch (Exception e){
			data.setMessage("The error in user id :- "+e);
		}
	}
}

