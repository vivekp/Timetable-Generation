package org.iitk.brihaspati.modules.actions;


/*
 * Copyright (c) 2008 ETRG,IIT Kanpur.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * Redistribution in binary form must reproducuce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Contributors: Members of ETRG, I.I.T. Kanpur
 */


import java.io.File;
import java.util.List;
import java.util.Vector;

import java.sql.Date;

import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import java.util.StringTokenizer;

import org.apache.torque.util.Criteria;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.modules.screens.VelocityScreen;
import org.apache.turbine.services.servlet.TurbineServlet;

import org.iitk.brihaspati.om.News;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.Assignment;
import org.iitk.brihaspati.om.AssignmentPeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

	/** This class contains code of Sending Assignment  to the Assignment
	*  with  attachments file txt formate
	*  @author<a href="arvindjss17@gmail.com">Arvind Pal</a>
	*/

public class Add extends SecureAction
{
        /**
        * @param data RunData
        * @param context Context
        * @return nothing
        * @exception Exception a generic exception
        */
        VelocityScreen vs=new VelocityScreen();
        public void do_submit(RunData data,Context context)
        {
                try
                {
			String LangFile=data.getUser().getTemp("LangFile").toString();
			String msg="";			
		        ParameterParser pp=data.getParameters();
			String userList=pp.getString("DB_subject","");
			StringTokenizer st=new StringTokenizer(userList,"^");
			String userVector="";
			//Vector userVector=new Vector();
                        for(int i=0;st.hasMoreTokens();i++)
                        {
				userVector=userVector+st.nextToken()+",";
                        }
			context.put("userVector",userVector);	
			context.put("checkVector","AddressBook");	
		        context.put("check","UseAddress");		
		}catch(Exception ex) {   data.setMessage("Rind !!"+ex); }
        }
	public void do_search(RunData data,Context context){
		try
                {
			ParameterParser pp=data.getParameters();
                        String valueString=pp.getString("valueString","");
			context.put("valueString",valueString);
                        String modes=pp.getString("mod","");
			context.put("mod",modes);
			ErrorDumpUtil.ErrorLog("User name valueString   ----> "+valueString);
			data.setScreenTemplate("call,Local_Mail,viewall.vm");	
			
		
		}catch(Exception ex) {   data.setMessage("Rind !!"+ex); }
	}

	public void doPerform(RunData data,Context context)
        {       try
                {
                        String action=data.getParameters().getString("actionName","");
                        if(action.equals("AddUser"))
                                do_submit(data,context);
                        if(action.equals("Search"))
                                do_search(data,context);
                        else
                        {       String LangFile=data.getUser().getTemp("LangFile").toString();
                                String msg=MultilingualUtil.ConvertedString("action_msg",LangFile);
                                data.setMessage(msg);
                        }
                }
                catch(Exception ex)  {    data.setMessage("The error in Assignment !!"+ex);   }
        }
}

				
