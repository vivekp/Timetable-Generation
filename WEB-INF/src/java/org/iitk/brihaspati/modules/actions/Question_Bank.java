package org.iitk.brihaspati.modules.actions;
/* @(#)Question_Bank.java
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
 */ 

import java.io.File;
import java.io.FileWriter;

import java.util.Date;
import java.util.Vector;
import java.util.StringTokenizer;

import org.apache.velocity.context.Context;

import org.apache.commons.fileupload.FileItem;

import org.apache.turbine.Turbine; 
import org.apache.turbine.modules.screens.TemplateScreen; 
import org.apache.turbine.modules.actions.VelocitySecureAction;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.services.velocity.TurbineVelocity;
import org.apache.turbine.services.velocity.TurbineVelocityService;

import org.iitk.brihaspati.modules.utils.StringUtil;    
import org.iitk.brihaspati.modules.utils.QuestionBank;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;    
import org.iitk.brihaspati.modules.utils.MultilingualUtil;    
/**
 * Class responsible for performing the company admin actions
 * in the system.
 *
 * @author <a href="mailto:tarankhan1@yahoo.com">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com">Mithelesh Parihar</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class Question_Bank extends SecureAction
{
	MultilingualUtil Multi=new MultilingualUtil();
 
 	public void doSubmit(RunData data, Context context) throws Exception 
   	{
 		String file=data.getUser().getTemp("LangFile").toString();
        	String msg1=Multi.ConvertedString("QuestBank_msg1",file);
        	String msg3=Multi.ConvertedString("Repo_create",file); 
		try{
	
        		String username=data.getUser().getName();
			String mess="";
			ParameterParser pp=data.getParameters();
			String mName=pp.getString("contentTopic");
			int i =StringUtil.checkString(mName);
			if(i == -1){
				context.put("mName",mName);
		       		String topic =pp.getString("topicExisting");
				String repositoryPath=TurbineServlet.getRealPath("/QuestionBank");
				String modulePath=repositoryPath+"/"+username;
				File f=new File(modulePath);
				f.mkdirs();
				String filePath=modulePath+"/"+mName+"__QB.xml";
				mess=QuestionBank.CreateModule(mName,modulePath,filePath,file);
				data.setMessage(mess);
				context.put("M_Create","Module_create");
			}
			else{
				mess=Multi.ConvertedString("ProxyuserMsg3",file);
				data.setMessage(mess);
			}
		}
        	catch(Exception e)
		{	
			data.setMessage("error in  doSubmit [Question_Bank.java] action "+ e);
		}
      	}

    	/**
     	* Default action to perform if the specified action
     	* cannot be executed.
     	*/

	public void doSaveQuestion(RunData data, Context context,String status,String Ques_Type)
	{
		try
		{	
			String mess=new String();
 			String file=data.getUser().getTemp("LangFile").toString();
        		String msg2=Multi.ConvertedString("QueBankAction_msg1",file); 
			ParameterParser pp=data.getParameters();
                	String from_path=pp.getString("FromPath"," ");
       			String mName=pp.getString("modulename","");
			String UserName=data.getUser().getName();
	        	String Path=TurbineServlet.getRealPath("/QuestionBank/"+UserName+"/"+mName+"__QB.xml");
                	String ques =pp.getString("Question","");
                	String ans=pp.getString("Answer");
                	String hint=pp.getString("hint"," ");
			if(Ques_Type.equals("Multiple"))
			{
                		String op1=pp.getString("op1","");
                		String op2=pp.getString("op2");
                		String op3=pp.getString("op3");
                		String op4=pp.getString("op4");
                		String op5=pp.getString("op5");
                		String op6=pp.getString("op6");
			
                		if(ques.equals("") || op1.equals("") || op2.equals("") || op3.equals(""))
                		{
					data.setMessage(msg2);
                        		return;
                        	}
				mess=QuestionBank.InsertQuestionMultiple(Path,Ques_Type,ques,op1,op2,op3,op4,op5,op6,ans,hint,file);
			}
			else if(Ques_Type.equals("TF"))
			{
                		 mess=QuestionBank.InsertQuestionTF(Path,Ques_Type,ques,ans,hint,file);
			}
			else if(Ques_Type.equals("Short"))
			{
                		 mess=QuestionBank.InsertQuestionTF(Path,Ques_Type,ques,ans,hint,file);
			}
			data.setMessage(mess);
			if(status.equals("More"))
			{
				setTemplate(data,"call,Question_Bank,InsertEdit_"+Ques_Type+".vm");
			}
			else if(status.equals("Finish"))
			{
				if(from_path.equals("QuesList"))
                        		setTemplate(data,"call,Question_Bank,Question_List.vm");
				else
                        		setTemplate(data,"call,Question_Bank,Create_Bank_Repository.vm");
			}
			context.put("modulename",mName);
		}
		catch(Exception ex)
		{
			
		}
	}
	public void doEditQuestion(RunData data, Context context,String Ques_Type)
	{
		try
		{	
			String mess=new String();
 			String file=data.getUser().getTemp("LangFile").toString();
        		String msg2=Multi.ConvertedString("QueBankAction_msg1",file); 
			ParameterParser pp=data.getParameters();
       			String mName=pp.getString("modulename","");
                	String qid =pp.getString("qid","");
			String UserName=data.getUser().getName();
	        	String Path=TurbineServlet.getRealPath("/QuestionBank/"+UserName+"/"+mName+"__QB.xml");
                	String ques =pp.getString("Question","");
                	String ans=pp.getString("Answer");
                	String hint=pp.getString("hint"," ");
			if(Ques_Type.equals("Multiple"))
			{
                		String op1=pp.getString("op1","");
                		String op2=pp.getString("op2");
                		String op3=pp.getString("op3");
                		String op4=pp.getString("op4");
                		String op5=pp.getString("op5");
                		String op6=pp.getString("op6");
			
                		if(ques.equals("") || op1.equals("") || op2.equals("") || op3.equals(""))
                		{
                       // data.setMessage("Mandatory fields should not be left blank");
					data.setMessage(msg2);
                        		return;
                        	}
				mess=QuestionBank.EditMultiple(Path,qid,Ques_Type,ques,op1,op2,op3,op4,op5,op6,ans,hint,file);
			}
			else if(Ques_Type.equals("TF"))
			{
                		 mess=QuestionBank.EditTF(Path,qid,Ques_Type,ques,ans,hint,file);
			}
			else if(Ques_Type.equals("Short"))
			{
                		 mess=QuestionBank.EditTF(Path,qid,Ques_Type,ques,ans,hint,file);
			}
			data.setMessage(mess);
			context.put("modulename",mName);
			context.put("Ques_Type",Ques_Type);
			setTemplate(data,"call,Question_Bank,Question_List.vm");
		}
		catch(Exception ex)
		{
			data.setMessage("error in  doDelete [Question_Bank.java] action "+ ex);
			
		}
	}
    	public void doDelete(RunData data, Context context)
	{
		try {
                ParameterParser pp=data.getParameters();
		User user=data.getUser();
		String file=user.getTemp("LangFile").toString();
	        String username=user.getName();
		String s="";
		context.put("status","delete");
                String path=TurbineServlet.getRealPath("/QuestionBank")+"/"+username+"/";
		String moduleList=pp.getString("deleteFileNames",""); 
                if(!moduleList.equals(""))
                {
                        StringTokenizer st=new StringTokenizer(moduleList,"^");
                        for(int j=0;st.hasMoreTokens();j++)
                        { //first 'for' loop
                                String moduleName=st.nextToken();
				s=QuestionBank.deleteModule(path,moduleName,file);
			}
                   	data.setMessage(s);
		}
		}
		catch(Exception e){
			data.setMessage("error in  doDelete [Question_Bank.java] action "+ e);
		}
		
	}



	public void doDeletequestion(RunData data, Context context)
	{
		try
		{
		ParameterParser pp=data.getParameters();
		String file=data.getUser().getTemp("LangFile").toString();
		String userName=data.getUser().getName();
       		String mName=pp.getString("modulename","");
	        String Path=TurbineServlet.getRealPath("/QuestionBank/"+userName+"/"+mName+"__QB.xml");
                String s ="";
		String quesList=pp.getString("deleteQuestionsId",""); 
                if(!quesList.equals(""))
                {
                        StringTokenizer st=new StringTokenizer(quesList,"^");
                        for(int j=0;st.hasMoreTokens();j++)
                        { //first 'for' loop
                                String qid=st.nextToken();
				s=QuestionBank.deleteQuestion(Path,qid,file);
			}
                   	data.setMessage(s);
		}
		}
		catch(Exception e){
			data.setMessage("error in  doDelete [Question_Bank.java] action "+ e);
		}
		
	}

 	public void doUpload(RunData data, Context context)
        {

		String LangFile=data.getUser().getTemp("LangFile").toString();
                String msg2=Multi.ConvertedString("upload_msg2",LangFile);
		try{
                        
 	       /**
                * Get the file for uploading and check if the 
		* file  extension is ".txt" 
                */
			ParameterParser pp=data.getParameters();
			Vector v=new Vector();
                        FileItem file = pp.getFileItem("file");
                        String fileName=file.getName();
                	String type=pp.getString("type","");
                	String name=pp.getString("FName","");
                	String rName=pp.getString("rname","");
                	String mName=pp.getString("mname","");
                        if(!fileName.endsWith(".txt"))
                        {
                               //data.setMessage("The file for uploading should have '.txt' extension & proper format !!");
				data.setMessage(msg2);
                        }
                        else{
                                File f=new File(TurbineServlet.getRealPath("/QuestionBank/"+rName+"/"+mName+"")+"/"+fileName+"");
	        String path=TurbineServlet.getRealPath("/QuestionBank/"+rName+"/"+mName+"");
                                file.write(f);
				if (type.equals("Multiple"))
					{
					v=QuestionBank.UploadMultiple(f,path,type,name);
					}
				else if (type.equals("TF"))
					{
					v=QuestionBank.UploadTF(f,path,type,name);
					}
				else if (type.equals("Short"))
					{
					v=QuestionBank.UploadShort(f,path,type,name);
					}
				else if (type.equals("Match"))
					{
					v=QuestionBank.UploadMatch(f,path,type,name); 
					}
				
                                context.put("size",Integer.toString(v.size()));
                                context.put("Msg",v);
                        }
                }
                catch(Exception ex){data.setMessage("The Error in Uploading!! "+ex);}
        }
		
 	public void doPerform(RunData data,Context context)
        throws Exception
        {
		String file=(String)data.getUser().getTemp("LangFile");
                ParameterParser pp=data.getParameters();
                String action=pp.getString("actionName","");
		
                String Ques_type=pp.getString("Ques_Type","");
		if(!action.equals(""))
		{
			if(!Ques_type.equals(""))
			{
                		if(action.equals("eventSubmit_doSaveQuestion"))
				{
					doSaveQuestion(data,context,"More",Ques_type);
				}
                		else if(action.equals("eventSubmit_doFinishQuestion"))
				{
					doSaveQuestion(data,context,"Finish",Ques_type);
				}
                		else if(action.equals("eventSubmit_doEditQuestion"))
				{
					doEditQuestion(data,context,Ques_type);
				}
              			else if(action.equals("eventSubmit_doQuesDelete"))
				{
					doDeletequestion(data,context);
				}
			}
			else
			{
                		if(action.equals("eventSubmit_doSubmit"))
				{
                        		doSubmit(data,context);
				}
              			else if(action.equals("eventSubmit_doDelete"))
				{
					doDelete(data,context);
				}
			}
		}
		else
			//data.setMessage("Can not find action !!");
			data.setMessage(MultilingualUtil.ConvertedString("action_msg",file));
        }
}

