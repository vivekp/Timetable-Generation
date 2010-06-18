package org.iitk.brihaspati.modules.actions;

/*
 * @(#)changeAParam.java	
 *
 *  Copyright (c) 2005-2006,2009 ETRG,IIT Kanpur. 
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
 */
import java.io.File;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.QuotaUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.services.security.TurbineSecurity;
/**
 * @author <a href="mailto:chitvesh@yahoo.com">Chitvesh Dutta</a>
 * @author <a href="mailto:awadhk_t@yahoo.com">Awahesh Kumar Trivedi</a>
 */

public class changeAParam extends SecureAction_Admin{

	/**
	 * This method updates the first, last name and configuration 
	 * parameters of Admin
	 * @see MultilingualUtil in  utils
	 * @param data RunData
	 * @param context Context
	 * @return nothing
	 */
	private String LangFile=null;
	MultilingualUtil m_u= new MultilingualUtil();
	public void doUpdate(RunData data, Context context) throws Exception{
		/**
		 * Get the user object from RunData for the user
		 * currently logged in
		 */

		User user=data.getUser();

		/**
                 * getting property file According to selection of Language in temporary variable 
		 * getting the values of first,last names and
		 * configuration parameter.
		 */


		LangFile=(String)user.getTemp("LangFile");
		ParameterParser pp=data.getParameters();
		String AFName=pp.getString("AFName","");
		String ALName=pp.getString("ALName","");
	 	String AdminConf = pp.getString("AdminConf","");	
	 	String AdminCrsExp = pp.getString("AdminCrsExp","");	
	 	String mailServ = pp.getString("mailServ","");	
	 	String mailFrom = pp.getString("mailFrom","");	
	 	String muName = pp.getString("muName","");	
	 	String mPass = pp.getString("mPass","");	
	 	String domainNM = pp.getString("mailDomain","");	
	 	String aquota = pp.getString("cquota","");
	 	String uquota = pp.getString("uquota","");
	 	String hdir = pp.getString("hdir","");
		
		/**
		 * Replacing the variable value from Property file
		 * Update the first,last name configuration parameter values for Admin
		 * @see AdminProperties in utils
		 */
		String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
		StringUtil S = new StringUtil();
		String prof_update=null;
		if (S.checkString(AFName)==-1 && S.checkString(ALName)==-1){
			user.setFirstName(AFName);
			user.setLastName(ALName);
			TurbineSecurity.saveUser(user);
				// for delete the file  and set the value for admin configuration
		 	(new File(path)).delete();
			AdminProperties.setValue(path,AdminConf,"brihaspati.admin.listconfiguration.value");
			AdminProperties.setValue(path,AdminCrsExp,"brihaspati.admin.courseExpiry");
			AdminProperties.setValue(path,mailServ,"brihaspati.mail.server");
			AdminProperties.setValue(path,mailFrom,"brihaspati.mail.smtp.from");
			AdminProperties.setValue(path,muName,"brihaspati.mail.username");
			AdminProperties.setValue(path,mPass,"brihaspati.mail.password");
			AdminProperties.setValue(path,domainNM,"brihaspati.mail.local.domain.name");
			AdminProperties.setValue(path,aquota,"brihaspati.admin.quota.value");
			AdminProperties.setValue(path,uquota,"brihaspati.user.quota.value");
			AdminProperties.setValue(path,hdir,"brihaspati.home.dir.value");
			prof_update=m_u.ConvertedString("usr_prof",LangFile);
			data.setMessage(prof_update);
			boolean qct=QuotaUtil.CreateandUpdate();	
			data.addMessage("Disk space is update for user and Courses");
			}
		else
			 prof_update=m_u.ConvertedString("usr_prof1",LangFile);
			data.setMessage(prof_update);
	}	

	/**
	 * This is the default method called when the action is not
	 * found
	 * @param data RunData
	 * @param context Context
	 */

	public void doPerform(RunData data, Context context) throws Exception{
		String action=data.getParameters().getString("actionName","");
		LangFile=(String)data.getUser().getTemp("LangFile");
		if(action.equals("eventSubmit_doUpdate"))
			doUpdate(data,context);
		else
		{
			String msg=MultilingualUtil.ConvertedString("usr_prof2",LangFile);
			data.setMessage(msg);
		}
	}
}
