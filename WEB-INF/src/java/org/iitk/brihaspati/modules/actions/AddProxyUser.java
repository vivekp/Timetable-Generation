package org.iitk.brihaspati.modules.actions;

/*
 * @(#)AddProxyUser.java	
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
 */


import java.util.List;
import java.util.Vector;

import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;

import org.apache.velocity.context.Context;

import org.apache.torque.util.Criteria;

import org.iitk.brihaspati.om.ProxyUserPeer;

import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;


/**
 * This class is responsible for adding a new proxy user to the system.
 *
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 */

public class AddProxyUser extends SecureAction_Admin
{

/**
 *
 * Method for registering a new proxy user
 * @param data RunData instance
 * @param context Context instance
 *
 */ 
	public void doRegister(RunData data, Context context) throws Exception{
		ParameterParser pp=data.getParameters();
		String LangFile=(String)data.getUser().getTemp("LangFile");
		
		
		/**
                 * Retreiving details entered by the user
                 */

                String uname=pp.getString("UNAME");
                String passwd=pp.getString("PASSWD");
                if(passwd.equals(""))
                        passwd=uname;


                StringUtil S=new StringUtil();

               /**
                * Checking for the illegal characters in the data entered
                * If found then the error message is given
                */

		if(S.checkString(uname)==-1)
			{
			Criteria crit=new Criteria();
			crit.add(ProxyUserPeer.USERNAME,uname);
			//Vector proxy_user=ProxyUserPeer.doSelect(crit);	
			List proxy_user=ProxyUserPeer.doSelect(crit);
			if(proxy_user.size()==0)
				{
				/**
				 * Insert the proxy user name and password
				 */
				crit=new Criteria();
        	                crit.add(ProxyUserPeer.USERNAME,uname);
                	        crit.add(ProxyUserPeer.PASSWORD,passwd);
                        	ProxyUserPeer.doInsert(crit);
				String msg1=MultilingualUtil.ConvertedString("ProxyuserMsg1",LangFile);
                                data.setMessage(msg1);
				//data.setMessage("The proxy user inserted successfully");
				}
			else{
				String msg1=MultilingualUtil.ConvertedString("ProxyuserMsg2",LangFile);
                                data.setMessage(uname + msg1);
                        //data.setMessage("The " +uname+" proxy user already exist");
	                }

		}
		else{
			String msg1=MultilingualUtil.ConvertedString("ProxyuserMsg3",LangFile);
                        data.setMessage(msg1);
		//	data.setMessage("The proxy user name field have illegal characters !!");
		}
	}

	/**
	 * This is the default method called when the button is not found
	 */

	public void doPerform(RunData data,Context context) throws Exception{
	        String LangFile=(String)data.getUser().getTemp("LangFile");
	
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doRegister"))
			doRegister(data,context);
		else{
			String msg1=MultilingualUtil.ConvertedString("usr_prof2",LangFile);
                                data.setMessage(msg1);
		//	data.setMessage("Cannot find the button");
		}
	}
}

