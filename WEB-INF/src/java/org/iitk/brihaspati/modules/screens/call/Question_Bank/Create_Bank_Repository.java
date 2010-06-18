package org.iitk.brihaspati.modules.screens.call.Question_Bank;

/* @(#)Create_Bank_Repository.java
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
    OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 */

import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;   
import org.iitk.brihaspati.modules.utils.QuestionBank;

/**
 * This class create a question bank and get list of topices
 * @author <a href="mailto:tarankhan1@yahoo.com">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com">Mithelesh Parihar</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */
 
public class Create_Bank_Repository extends SecureScreen_Instructor 
{
   	/**
	* 
	* @param data RunData
	* @param context Context
        * @see QuestionBank
      	*/
	public void doBuildTemplate(RunData data,Context context) 
	{
		try
		{
                      /**
                        *Get the UserName and put it in the context
                        *for template use
                        */
        		String username=data.getUser().getName();
			String filePath=data.getServletContext().getRealPath("/QuestionBank")+"/"+username+"/";		
        		//Vector topicList=QuestionBank.getRepositoryList(filePath,username);
        		Vector topicList=QuestionBank.getModuleList(filePath);
			if(topicList.size()!=0)
			{
       				context.put("allTopics",topicList);
			}
			context.put("username",username);
		}		
		catch(Exception e)
		{
			data.setMessage("The error in Create Question Bank Repository !!"+e);
		}

	}
}
