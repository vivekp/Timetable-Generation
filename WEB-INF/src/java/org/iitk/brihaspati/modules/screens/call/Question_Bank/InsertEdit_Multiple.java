package org.iitk.brihaspati.modules.screens.call.Question_Bank;


/* @(#)InsertEdit_Multiple.java
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
 *
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
/**
 * @author <a href="mailto:tarankhan1@yahoo.com">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com">Mithelesh Parihar</a>
 */

import org.iitk.brihaspati.modules.screens.call.SecureScreen; 
import org.iitk.brihaspati.modules.utils.ReadQuestionBank; 
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil; 
import org.apache.turbine.util.parser.ParameterParser;  
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;

public class InsertEdit_Multiple extends SecureScreen
{
    
    /*
     * Places all the data objects in the context for further use
     */
  
	public void doBuildTemplate(RunData data,Context context)
	{
		ParameterParser pp=data.getParameters();
        	String mName=pp.getString("modulename","");
        	String mode=pp.getString("mode","");
        	String from_path=pp.getString("FromPath","");
        	context.put("modulename",mName);
        	context.put("mode",mode);
        	context.put("FromPath",from_path);
        	context.put("Ques_Type",pp.getString("Ques_Type",""));
		if((mode.equals("Edit"))||(mode.equals("View")))
		{
        		String userName =data.getUser().getName();
        		String quesid =pp.getString("qid","");
        		String filePath=data.getServletContext().getRealPath("/QuestionBank/"+userName+"/"+mName+"__QB.xml");
        		String question=ReadQuestionBank.getQuestion(filePath,quesid);
        		String op1=ReadQuestionBank.getOP1(filePath,quesid);
        		String op2=ReadQuestionBank.getOP2(filePath,quesid);
        		String op3=ReadQuestionBank.getOP3(filePath,quesid);
        		String op4=ReadQuestionBank.getOP4(filePath,quesid);
        		String op5=ReadQuestionBank.getOP5(filePath,quesid);
        		String op6=ReadQuestionBank.getOP6(filePath,quesid);
        		String ans=ReadQuestionBank.getAns(filePath,quesid);
        		String hint=ReadQuestionBank.getHint(filePath,quesid);
        	
			context.put("qid",quesid);
        		context.put("question",question);
        		context.put("op1",op1);
        		context.put("op2",op2);
        		context.put("op3",op3);
        		context.put("op4",op4);
        		context.put("op5",op5);
        		context.put("op6",op6);
        		context.put("ans",ans);
        		context.put("hint",hint);
		}
	}
}

