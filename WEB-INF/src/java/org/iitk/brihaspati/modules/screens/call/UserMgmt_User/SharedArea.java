package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/**
 * @(#)SharedArea.java	
 *
 *  Copyright (c) 2005,2009 ETRG,IIT Kanpur. 
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
 */
import java.util.Vector;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.torque.util.Criteria;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUserGroupRolePeer;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/**
 *This class contain the code for Shared Area of a User
 *and place all the record in a vector of data object
 *where they can be displayed by #foreach loop and
 *Grab all the records in xml files
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a>
 */
public class SharedArea extends SecureScreen 
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
		*Get the User name
		*and put it in the context
		*for use in the templates
		*/  
		User user=data.getUser();
               	String UserName=user.getName();
               	context.put("uname",UserName);

               	ParameterParser pp=data.getParameters();
		String Path=data.getServletContext().getRealPath("/UserArea");

		/**
		*Get the File name and
		*put in the context
		*for template use
		*/               	
		String Filename =pp.getString("filename","");
               	context.put("fname",Filename);
		/**
		*Get the mode
		*/
		String mode =pp.getString("mode","");
               	context.put("mode",mode);
                /**
		*for moving file
		*/	
		String mode1 =pp.getString("mode1","");
                context.put("mode1",mode1);

               	String status=pp.getString("stat","");
		context.put("stat",status); 

               	String username=pp.getString("name");
		context.put("name",username);

		String seq=pp.getString("seq","");
                context.put("seqno",seq);
		
		File topicDir=new File(Path+"/"+username+"/Shared");
			Vector userList=new Vector();
			try{
			int rid[]={1,5,6};
			int uid[]={0,1};
			Criteria crit=new Criteria();
			crit.addJoin(TurbineUserPeer.USER_ID,TurbineUserGroupRolePeer.USER_ID);
			crit.addNotIn(TurbineUserGroupRolePeer.ROLE_ID,rid);
			crit.addNotIn(TurbineUserPeer.USER_ID,uid);
			crit.addGroupByColumn(TurbineUserPeer.LOGIN_NAME);
			List l=TurbineUserPeer.doSelect(crit);
			context.put("userlist",l);
			}
			catch(Exception e){data.setMessage("The error in display userlist "+e);}

		if( status.equals("fromSubDirectory") ||mode1.equals("move"))
		{
			if(!topicDir.exists())
                	{
				String ncreate=mu.ConvertedString("personal_ncreate",file);
				if(file.endsWith("urd.properties"))		
					data.setMessage(ncreate +" "+username);
				else
					data.setMessage(username+" "+ncreate);
                	}
                	else
                	{
				 /**
                                   * Retrive xml file from particuler path
                                   *@see TopicMetaDataXmlReader in utils
                                   */
				File shared_Path=new File(topicDir+"/Shared__des.xml");
				if(!shared_Path.exists())
				{
					String noXml=mu.ConvertedString("personal_noXml",file);
                        		data.setMessage(username+" "+noXml);
				}
				else
				{
				TopicMetaDataXmlReader topicMetaData=new TopicMetaDataXmlReader(topicDir+"/"+"Shared__des.xml");
                                String topicDesc=topicMetaData.getTopicDescription();
                                Vector dc=topicMetaData.getFileDetails();
				Vector vct=new Vector();
                                if(dc==null)
                                {
					String etopic=mu.ConvertedString("personal_etopic",file);
                                        data.setMessage(etopic);
                                }
                                else
                                {
                                        context.put("dirContent",dc);
                                }
		 		if(mode1.equals("move") )  
				{
			        	File topicdir=new File(Path+"/"+username+"/"+"/Private");
                	        	String filter[]={"__des.xml"};
                        		NotInclude exclude=new NotInclude(filter);
	                        	Vector v=new Vector();
        	                	String ContentList[]=topicdir.list(exclude);
                        		for(int i=0;i<ContentList.length;i++)
	                        	{
        	                        	v.add(ContentList[i]);
						context.put("contvalue",(ContentList[i]));
                	        	}
                        	        context.put("contentvalue",v);
				}
				}
			}
		}
		}
		catch(Exception ex)
		{
		data.addMessage("The error in Shared Area!!"+ex);
		}				
	}
}






