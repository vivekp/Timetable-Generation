package org.iitk.brihaspati.modules.screens.call.Dis_Board;

/*
 * @(#)DB.java	
 *
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
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.io.File;
import org.iitk.brihaspati.om.DbSendPeer;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import com.workingdogs.village.Record;
//import com.workingdogs.village.Value;
import org.iitk.brihaspati.modules.utils.UserUtil; 
import org.iitk.brihaspati.modules.utils.GroupUtil; 
//import org.iitk.brihaspati.modules.utils.CourseUtil; 
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.om.DbReceivePeer;

/**
 *   This class contains code for all discussions in workgroup
 *   Compose a discussion and reply.
 *   @author  <a href="aktri@iitk.ac.in">Awadhesh Kumar Trivedi</a>
 *   @author  <a href="sumanrjpt@yahoo.co.in">Suman Rajput</a>
 *   @author  <a href="rekha_20july@yahoo.co.in">Rekha Pal</a>
 */

public class DB extends SecureScreen
{
	/**
	 * @param data RunData instance
	 * @param context Context instance
	 * try and catch Identifies statements that user want to monitor
	 * and catch for exceptoin.
	 */
	
	public void doBuildTemplate(RunData data, Context context)
    	{
		try{
			/**
		  	* Create the instance of user
		  	* Getting the CourseId
			*/
			User user=data.getUser();
			String dir=(String)user.getTemp("course_id","testing");
            		int gid=GroupUtil.getGID(dir);
			String filePath=data.getServletContext().getRealPath("/Courses")+"/"+dir+"/DisBoard";
			File dirHandle=new File(filePath);
			if(!(dirHandle.exists())){
				dirHandle.mkdirs();
			}
			String file[]=dirHandle.list();
			Vector v=new Vector();
			for(int i=0;i<file.length;i++)
			{
				/**
		  		* Add file in the Vector and vector put into context for further uses 
		  		*/
		 		v.addElement(file[i]);
		 	}
			for(int c=0;c<v.size();c++)
			{
				String Dname=(String)v.elementAt(c);
				Criteria crit=new Criteria();
				crit.add(DbSendPeer.MSG_SUBJECT,Dname);
				List l=DbSendPeer.doSelect(crit);
				if(l.size()==0)
				{
					/**
				 	* If no entry in the topic then Remove the topic(directory)
				 	*/ 
					String fileName=filePath+"/"+Dname;
					File f=new File(fileName);
					SystemIndependentUtil.deleteFile(f);
				}
			}//for
			
			/**
		  	* Getting the userId of logged user from Turbine_User table
		  	* @see UserUtil in Utils
		  	*/
			String user_name = user.getName();
			int user_id = UserUtil.getUID(user_name);
			int unread_flag=0;
			String unreads=new String();
		   	// Count the UnRead messages according to userId
			String unread_msg="SELECT COUNT(DB_RECEIVE.MSG_ID) UNREAD FROM DB_RECEIVE, DB_SEND WHERE DB_SEND.MSG_ID=DB_RECEIVE.MSG_ID AND DB_SEND.GROUP_ID="+gid+" AND RECEIVER_ID="+user_id+" AND READ_FLAG="+unread_flag;
		 	List u=DbReceivePeer.executeQuery(unread_msg);
			context.put("groupid",Integer.toString(gid)); 
			context.put("userid",Integer.toString(user_id)); 
			for(Iterator j=u.iterator();j.hasNext();)
			{
				Record item1=(Record)j.next();
				unreads=item1.getValue("UNREAD").asString();
			}		  
		   	// Count the Total messages according to userId
			String totalMsg=new String();
			String total_msg="SELECT COUNT(MSG_ID)TOTAL FROM DB_RECEIVE WHERE RECEIVER_ID = "+user_id+" AND GROUP_ID="+gid;
		 	List totalmsg=DbReceivePeer.executeQuery(total_msg);
			for(Iterator k=totalmsg.iterator(); k.hasNext();)
			{
				Record item=(Record)k.next();
				totalMsg=item.getValue("TOTAL").asString();
			}
		   	//Adds the information to context
			context.put("unread",unreads);
			context.put("total",totalMsg);
			context.put("courseid",dir);
			context.put("cname",(String)user.getTemp("course_name"));
		}//try
		catch(Exception e){data.setMessage("The error in DB screen"+e);}
    	}//method
}//class

