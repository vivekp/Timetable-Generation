package org.iitk.brihaspati.modules.screens.call.Question_Bank;

/* @(#)RepositoryList.java
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur. 
*/ 

import org.apache.turbine.util.parser.ParameterParser;    
import java.util.Vector;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;   
import org.iitk.brihaspati.modules.utils.QuestionBank;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.ReadQuestionBank;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.apache.turbine.modules.screens.VelocityScreen;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import org.apache.turbine.om.security.Group;
import java.io.*;

/**
 * @author <a href="mailto:tarankhan1@yahoo.com ">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com ">Mithelesh Parihar</a>
 */
public class RepositoryList extends SecureScreen 
{

   /**
     * Place all the data object in the context
     * for use in the template.
     */

	public void doBuildTemplate(RunData data,Context context) 
	{
		      
                 /**
                   *Retrieve the Parameters by using the Parameter Parser
                   *Get the UserName and put it in the context
                   *for template use
                   */
		ParameterParser pp=data.getParameters();
		try
		{
			User user=data.getUser();
        		String mode =pp.getString("status"," ");
        		String username=user.getName();
        		//String filename=pp.getString("repositoryname");
			//String temp=fname + username;
        		String mname=pp.getString("modulename"," ");
        		context.put("Ques_Type",pp.getString("Ques_Type"," "));
			String filePath=data.getServletContext().getRealPath("/QuestionBank")+"/"+username;	
			Vector v=QuestionBank.getModuleList(filePath);	
        		//Vector id=QuestionBank.getDrid(filePath,username);
       			context.put("allTopics",v);
       			//context.put("rid",id);
			context.put("username",username);
			context.put("status",mode);
			context.put("modulename",mname);
			//context.put("repository",temp);
			/*if(mode.equals("Repository") || mode.equals("Module"))
			{
				Vector v1=new Vector();
				String path=filePath+"/"+temp;
				//context.put("tara",path);
				v1=QuestionBank.getModuleList(path);
				context.put("allmodule",v1);
				context.put("m_Name",mname);
				context.put("rname",temp);
				File ModuleDir=new File(path+"/"+mname);
                                Vector y =new Vector();
                                String List []=ModuleDir.list();
                                for(int j=0;j<List.length;j++)
                                {
                        		y.add(List[j]);
                                }
				int o=y.size();
                		context.put("paper",y);
			}*/
		}		
		catch(Exception e)
		{
			data.addMessage("Exception in [RepositoryList] screen \n "+e); 
		}
	}
}
