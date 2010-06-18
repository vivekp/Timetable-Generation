package org.iitk.brihaspati.modules.screens.call.Dis_Board;

/*
 * @(#)Edit.java	
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
import org.iitk.brihaspati.om.DbSendPeer;
import org.iitk.brihaspati.om.DbSend;
import org.apache.torque.util.Criteria;


/**
 * This class contains code for display message
 *
 * @author  <a href="aktri@iitk.ac.in">Awadhesh Kumar Trivedi</a>
 * @author  <a href="sumanrjpt@yahoo.co.in">Suman Rajput</a>
 * @author  <a href="rekha_20july@yahoo.co.in">Rekha Pal</a>
 */

public class Edit extends SecureScreen
{
	/**
 	* This method actually viewing the messages in the Disscussion board in group system
 	* @param data RunData instance
 	* @param context Context instance
 	* @exception Exception, a generic exception
 	*/
	public void doBuildTemplate( RunData data, Context context )
	{
		try
        	{
			/**
                        * Place all the data object in the context
                        * for use in the template.
                        */

			ParameterParser pp=data.getParameters();
                	String Username=data.getUser().getName();
                	context.put("username",Username);
			context.put("user_role",data.getUser().getTemp("role"));
			String Rep_id=pp.getString("Repid");
			context.put("Repid",Rep_id);
			int expday=pp.getInt("ExDay");
			String EditStat =pp.getString("EditStatus","");
                        String status=pp.getString("status","");
                        String sender=pp.getString("sender","");
                        context.put("sender",sender);
                                      
                        context.put("ExDay",expday);
			if(data.getUser().getTemp("role").equals("instructor"))
			{
				EditStat ="ProvidePerm";
			}
			else
			{
				EditStat ="";
			}
                	context.put("EditStatus",EditStat);
                                 
                       	if((data.getUser().getTemp("role").equals("instructor"))||(data.getUser().getTemp("role").equals("student")))
		 	{
				String mgid =pp.getString("msgid","");
				context.put("mgid",mgid);
				Criteria crit=new Criteria();
		        	crit.add(DbSendPeer.MSG_ID,mgid);
	                	List sendDetail=DbSendPeer.doSelect(crit);
				for(int i=0;i<sendDetail.size();i++)
				{
					DbSend element=(DbSend)sendDetail.get(i);
					int permit=element.getPermission();
					int expirytime=element.getExpiry();
                                                                       
					context.put("permit",Integer.toString(permit));
					context.put("expirytime",Integer.toString(expirytime));
                       
				}
				context.put("cname",(String)data.getUser().getTemp("course_name"));
			}//if

			Vector v=new Vector();
			String topic=data.getParameters().getString("topic","");
			context.put("topic",topic);
                   
                         AccessControlList acl=data.getACL();
		 	String dir=data.getParameters().getString("course_id");
		  	context.put("cid",dir);
			int msg_id=data.getParameters().getInt("msgid");
			
                	/**
		 	* Retrive the group and CourseId
			*/
			String course_id=pp.getString("course_id","");
			context.put("course_id",course_id);
			String mgid =pp.getString("msgid");
			context.put("mgid",mgid);
                        
                         
                	/**
	                * Retrive the UserId from Turbine_User table
        	        * @see UserUtil
                	*/
		   	int user_id=UserUtil.getUID(Username);
		    	context.put("userid",user_id);
			/**
		 	* Getting the actual path where the DB file is stored
		 	* @return String
		 	*/
			String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/DisBoard";
                                                                                                           
                        /**
		 	* read the file using fileReader
		 	* @try and catch Identifies statements that user want to monitor		
	 		* and catch for exceptoin.
	 		*/
 			
			String topicDesc="";
			String str[]=new String[1000];
			int i=0;
			int start=0;
			int stop = 0;
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
			for(int j=start+1;j<stop;j++)
			{
				topicDesc=topicDesc+ "\n"+str[j];
			}
			context.put("message",topicDesc);
			/**
			** here comes the code to view attachment with the message
			**/		
			File dirHandle=new File(filePath+"/"+topic+"/"+"Attachment/"+msg_id);
                                                                 
		        FilenameFilter exclude=new NotInclude("__desc.txt");
	      		File file[]=dirHandle.listFiles(exclude);
			String servContext=TurbineServlet.getContextPath();
			Vector content=new Vector();
			for(int j=0;j<file.length;j++)
			{
				Date date=new Date(file[j].lastModified());
				String fileData[]={file[j].getName(),date.toString()};
				content.addElement(fileData);
                    	}
                                                               
                        context.put("dirContent",content);
			context.put("cname",(String)data.getUser().getTemp("course_name"));
	}
	catch(Exception ex){data.setMessage("Error in the Edit screen in doBuildTemplate  method !! "+ex);}	
	}
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
		} //try
		catch(Exception e){data.setMessage("Error in the Edit screen in boolean method !! "+e);}
		return authorised;
	}//boolean	
}//class






