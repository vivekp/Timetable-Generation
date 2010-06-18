package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)PersonalArea.java	
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
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 *
 */
import java.io.File;
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/**
 * This class contain the code for Private Area of a User
 * Grab all the record in the xml file and
 * Place the Vector Of data object in the context
 * where they can be displayed by #foreach loop
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a>
 */
public class PersonalArea extends SecureScreen
{
	MultilingualUtil mu=new MultilingualUtil();
	/**
        *@param data RunData
        *@param context Context
        */
	public void doBuildTemplate(RunData data,Context context) 
	{
		try
		{
			String file=data.getUser().getTemp("LangFile").toString();
			/**
			*Get the UserName and put it in the context
			*for template use
			*/
			User user=data.getUser();
                	String UserName=user.getName();
                	context.put("uname",UserName);
			/**
			*Retrieve the Parameters using the Parameter Parser
			*/
			Vector v=new Vector();
                	ParameterParser pp=data.getParameters();
                	String Content=pp.getString("topic","");
                	String Filename =pp.getString("filename","");
                	String mode =pp.getString("mode","");
                	context.put("mode",mode);
                	context.put("topic",Content);
                	context.put("fname",Filename);
                	String status=pp.getString("stat","");
                	context.put("stat",status);
			/**
			*Get the Path and retrieve the topics 
			*which are exist in the Private Area of User
			*/
			 
                	String Path=data.getServletContext().getRealPath("/UserArea");
			File Cpath=new File(Path+"/"+UserName);
			if(!Cpath.exists())
			{
				Cpath.mkdirs();
				String cd=Path+"/"+UserName+"/Private";
                                File ff=new File(cd);
                                boolean cc=ff.mkdirs();
                                File f3=new File(Path+"/"+UserName+"/Shared");
                                boolean c3=f3.mkdirs();
                		context.put("stat1","NoArea");
				String create=mu.ConvertedString("personal_ncreate",file);
				if(file.endsWith("hi.properties"))
					context.put(UserName +" "+"message",create);
				else
					context.put("message",create+" "+UserName);
			}
                                File descFile=new File(Cpath+"/Shared/"+"Shared__des.xml");
                                if(!descFile.exists())
                                {
                                        TopicMetaDataXmlWriter.writeWithRootOnly(descFile.getAbsolutePath());
                                }
                		context.put("stat1","Area");
			if(status.equals("fromDirectory") || status.equals("fromSubDirectory"))
			{
                        	File topicDir=new File(Path+"/"+UserName+"/"+"/Private");
                        	Vector y =new Vector();
                        	String filter[]={"__des.xml"};
                        	NotInclude exclude=new NotInclude(filter);
                        	String ContentList []=topicDir.list(exclude);
				String Check=Content+"__des.xml";
                        	for(int j=0;j<ContentList.length;j++)
                        	{
                        		if(!(ContentList[j]).equals(Check))
                        		{
                        			y.add(ContentList[j]);
                        		}
					context.put("contentvalue",y);
                		}
			}
			/**
			*Retrieve the Files of Particular topic exist in the Private Area
			*@see TopicMetaDataXmlReader in utils
			*/
                 	if(status.equals("fromSubDirectory"))
			{
				String filetopic=Path+"/"+UserName+"/"+"/Private"+"/"+Content;
				TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(filetopic+"/"+Content+"__des.xml");
                        	String topicDesc=topicMetaData.getTopicDescription();
                        	Vector h=topicMetaData.getFileDetails();
				if(h==null)
				{
			        	String etopic=mu.ConvertedString("personal_etopic",file);
					data.addMessage("<br>"+etopic);
				}
				else
				{
					context.put("FFfiles",h);
				}
			}
		}
		catch(Exception ex)
		{
			data.setMessage("The error in Personal Area of User"+ex);
		}

	}
}
