package org.iitk.brihaspati.modules.screens.call.Question_Bank;

/* @(#)Question_List.java
 *
 *  Copyright (c) 2004-2007 ETRG,IIT Kanpur.
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
/**
 * @author <a href="mailto:tarankhan1@yahoo.com ">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com ">Mithelesh Parihar</a>
 */ 
import org.w3c.dom.*;
import org.xml.sax.helpers.*; 
import org.w3c.dom.Text; 
import com.workingdogs.village.Record;    
import org.iitk.brihaspati.modules.screens.call.SecureScreen; 
import org.apache.turbine.util.parser.ParameterParser;  
import org.iitk.brihaspati.modules.utils.ReadQuestionBank;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import java.util.Vector;  
import org.apache.turbine.om.security.User;
import org.xml.sax.SAXParseException;
import java.io.IOException;  


public class Question_List extends SecureScreen
{
	public void doBuildTemplate(RunData data,Context context)
	{
       		try
        	{
			/**
	                 * Getting the value of file from temporary variable
        	         * According to selection of Language.
                	 * and replacing the String from property file.
	                 * @see MultilingualUtil in utils
        	         */

                	String LangFile=(String)data.getUser().getTemp("LangFile");
			ParameterParser pp=data.getParameters();
			String userName=data.getUser().getName();
        		String mName=pp.getString("modulename","");
        		String Qustype=pp.getString("Ques_Type","");
        		context.put("modulename",mName);
        		context.put("Ques_Type",Qustype);
			if((!mName.equals("")) && (!Qustype.equals("")))
			{
				String filePath=data.getServletContext().getRealPath("/QuestionBank/"+userName+"/"+mName+"__QB.xml");
				Vector questions=ReadQuestionBank.getQuestions_type(filePath,Qustype);
				Vector quid=ReadQuestionBank.getQuestionsId_type(filePath,Qustype);
				if(questions.size()!=0)
				{
        				context.put("questions",questions);
        				context.put("id",quid);
        				context.put("status","NoBlank");
				}
				else
				{
					if(Qustype.equals("MT")){Qustype="Match";} if(Qustype.equals("TF")){Qustype="True/False";}
					doRedirect(data,"call,Question_Bank,RepositoryList.vm");
					if(LangFile.endsWith("urd.properties")){
                                                data.setMessage(MultilingualUtil.ConvertedString("QueBank_msg1",LangFile)+" "+" Type " +Qustype+" " +MultilingualUtil.ConvertedString("QueBank_contentIn",LangFile)+" "+mName);
					}
                                        else{
                                                data.setMessage(MultilingualUtil.ConvertedString("QueBank_msg1",LangFile)+" "+mName+" Type "+Qustype);
					}

					//data.setMessage("No Question in Topic "+mName);
        				context.put("status","quesList");
				}
			}
			else
			{
				setTemplate(data,"call,Question_Bank,RepositoryList.vm");
				data.setMessage(MultilingualUtil.ConvertedString("QueBank_msg2",LangFile));
				//data.setMessage("Topic or Question type are blank,So Please try again !!");
			}
		}
          	catch(Exception e)
		{
			data.setMessage("The Error in Question List !!"+e);
		}
	}
}
