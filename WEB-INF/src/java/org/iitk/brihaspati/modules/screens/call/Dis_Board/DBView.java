package org.iitk.brihaspati.modules.screens.call.Dis_Board;

/*
 * @(#)DBView.java	
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
 * 
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;
import java.util.Date;
import java.util.List;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
//import org.apache.turbine.services.resources.TurbineResources;
import org.apache.turbine.Turbine;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.util.security.AccessControlList;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.CourseUtil;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.om.DbReceivePeer;
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbSend;
import org.apache.torque.util.Criteria;

/**
 * This class contains code for display message
 * @author  <a href="aktri@iitk.ac.in">Awadhesh Kumar Trivedi</a>
 * @author  <a href="sumanrjpt@yahoo.co.in">Suman Rajput</a>
 * @author  <a href="rekha_20july@yahoo.co.in">Rekha Pal</a>
 */
public class DBView extends SecureScreen 
{
	/**
 	* This method actually for viewing the messages in the Disscussion board in group system
 	* @param data RunData instance
 	* @param context Context instance
 	* @exception Exception, a generic exception
 	*/
	public void doBuildTemplate(RunData data,Context context)
	{
		try 
		{
			User user=data.getUser();
			String topic=data.getParameters().getString("topic","");
			context.put("topic",topic);
			ParameterParser pp=data.getParameters();
			AccessControlList acl=data.getACL();
			int msg_id=data.getParameters().getInt("msgid");
			
		 	// Retrive the group and CourseId

			String dir,group,topicDesc="";
			group=dir=pp.getString("course_id");
			String cname=CourseUtil.getCourseName(group);
			context.put("CName",cname);
			context.put("workgroup",group);
			/**
		 	* Getting the actual path where the DB file is stored
		 	* @return String
		 	*/
			String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/DisBoard";

		 	// check role of User
			if( acl.hasRole("instructor",group) )
			{
				context.put("isInstructor","true");
			}
			/**
		 	* read the file using XML descriptior
		 	* @try and catch Identifies statements that user want to monitor
		 	* and catch for exceptoin.
		 	*/ 
			try{//try2
				String str[]=new String[1000];
				int i=0;
				int start=0;
				int stop = 0;
				try{
					BufferedReader br=new BufferedReader(new FileReader (filePath+"/"+topic+"/Msg.txt"));
		                	while ((str[i]=br.readLine()) != null)
	                        	{
						if (str[i].equals("<"+msg_id+">"))
						{
							start = i;
						}
						else if(str[i].equals("</"+msg_id+">"))
						{
							stop = i;
						}
			                	i= i +1;
					}
			        	br.close();
			       	}//try3
				catch(Exception e)
				{
					data.addMessage("The error in reading message in DBView Screens !!"+e);
					//e.printStackTrace();
				}
				for(int j=start+1;j<stop;j++)
				{
					topicDesc=topicDesc+ "\n"+str[j];
				}
			}
			catch(Exception e) {data.setMessage("The error in DBView Screens !!"+e);}
			/**
		 	* here comes the code to view attachment with the message
		 	**/		
			context.put("message",topicDesc);
			File dirHandle=new File(filePath+"/"+topic+"/"+"Attachment/"+msg_id);
			FilenameFilter exclude=new NotInclude("__desc.txt");
	      		File file[]=dirHandle.listFiles(exclude);
			String servContext=TurbineServlet.getContextPath();
			Vector content=new Vector();
			for(int i=0;i<file.length;i++)
			{
				Date date=new Date(file[i].lastModified());
				String fileData[]={file[i].getName(),date.toString()};
				content.addElement(fileData);
			}
			context.put("dirContent",content);
		
			/** 
	 		* From Here comes the code for change in Status.( Unread to Read ) 
	 		* @return nothing
	 		*/
			try
			{
				String dbtopic=Integer.toString(msg_id);
				context.put("msgID",dbtopic);
				String Username=user.getName();
				int receiver_user_id=UserUtil.getUID(Username);
				String update_message="UPDATE DB_RECEIVE SET READ_FLAG=1 WHERE MSG_ID="+msg_id+" AND RECEIVER_ID=" +receiver_user_id;
				DbReceivePeer.executeStatement(update_message);
			}
			catch(Exception e) {data.setMessage("The Error in DB Receive table for read flag updatation "+e);}
	 		// code for sender and posted date 
			try
			{
		        	Criteria crit1=new Criteria();
				crit1.add(DbSendPeer.MSG_ID,msg_id);
				List u=DbSendPeer.doSelect(crit1);
                         	for(int count=0;count<u.size();count++)
			 	{
			 		DbSend element=(DbSend)(u.get(count));
    				  	String subject=(element.getMsgSubject());
                                  	int userid=(element.getUserId());
                                  	String username=UserUtil.getLoginName(userid);
                                  	String retrive_date=(element.getPostTime()).toString();
				  	/**
				    	* Adds the information to the context
				    	*/
       				  	context.put("sub",subject);
                                  	context.put("retrive_user",username);
                                  	context.put("retrive_date",retrive_date);
			 	}//for
			}
			catch(Exception e) {data.setMessage("The error in sender name and post date in DBView Screens !!"+e);}
		}//try main
		catch(Exception ex) 
		{
		
                        data.setMessage("The error in retrive details from DB Send "+ex);
		}
	}//method

 	/**
  	* Performs the security check required
  	* @param data RunData
  	* @return boolean
  	*/           
	public boolean isAuthorized(RunData data)
	{
		boolean authorised=false;
		ParameterParser pp=data.getParameters();
		try
		{
			AccessControlList acl=data.getACL();
			User user=data.getUser();
			String g=pp.getString("course_id");
                        /**
		  	* Checks if the user has logged in as an instructor. If so, then he is
		  	* authorized to view this page
		  	*/ 
		   	if(g!=null && acl.hasRole("instructor",g) || acl.hasRole("student",g))
			{
				authorised=true;
			}
			else
			{
				data.setScreenTemplate(Turbine.getConfiguration().getString("template.login"));
				authorised=false;
			}
		}
		catch(Exception e){data.setMessage("The error in  authorisation block in DBView Screens !!"+e);}
		return authorised;
	}//method boolean
}//class




