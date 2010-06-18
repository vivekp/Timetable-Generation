package org.iitk.brihaspati.modules.screens.call.UserMgmt_Admin;

/*
 * @(#) UpdatePass_Admin.java	
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */
/**
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */


import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Admin;

/**
 * Class for updating password of user in the system.
 */

public class UpdatePass_Admin extends SecureScreen_Admin
{
 /**
  * Specific username for modify password 
  * @param data RunData
  * @param context Context
  * @exception Exception, a generic exception
  */
	public void doBuildTemplate(RunData data, Context context ){
		try{
	    		context.put("username",data.getParameters().getString("username"));
	    		context.put("status",data.getParameters().getString("status"));
	    	}
	    	catch(Exception e){
			data.setMessage("The error is :-"+e);
		}
    	}

}

