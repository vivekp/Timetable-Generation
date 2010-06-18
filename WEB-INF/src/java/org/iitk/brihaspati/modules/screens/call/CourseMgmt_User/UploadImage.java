package org.iitk.brihaspati.modules.screens.call.CourseMgmt_User;
/*
 * @(#) UploadImage.java
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur.
 *  All Rights Reserved.
 *
 *
 *
 *  Contributors: Members of Brihaspati Software Solutions  Kanpur
 *
 */
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;	
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

import java.util.Vector;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;


/**
 * This class Upload file for course Content
 *
 */
	
public class UploadImage extends SecureScreen_Instructor {

	public void doBuildTemplate(RunData data,Context context)
	{
		try {
			ParameterParser pp = data.getParameters();
        		String mode = pp.getString("mode","UploadImage");
			User user=data.getUser();
			String Courseid=(String)user.getTemp("course_name");	
			String courseid=(String)user.getTemp("course_id","");
			context.put("course",Courseid);
			context.put("mode",mode);
			if(!mode.equals("UploadImage")) {
				ErrorDumpUtil.ErrorLog(" da Upload Image in GRade");
				String path=TurbineServlet.getRealPath("/Courses"+"/"+courseid);
				TopicMetaDataXmlReader xmlreader1=null;
				Vector vect=new Vector();
				xmlreader1=new TopicMetaDataXmlReader(path+"/configuregrade.xml");
				vect=xmlreader1.getAssignmentDetails1();
				
				int percentage=Integer.parseInt(((FileEntry) vect.elementAt(0)).getUserName());
				int percentage1=Integer.parseInt(((FileEntry) vect.elementAt(1)).getUserName());
				int percentage2=Integer.parseInt(((FileEntry) vect.elementAt(2)).getUserName());
				int percentage3=Integer.parseInt(((FileEntry) vect.elementAt(3)).getUserName());
				int percentage4=Integer.parseInt(((FileEntry) vect.elementAt(4)).getUserName());
				context.put("percentage",percentage);
				context.put("percentage1",percentage1);
				context.put("percentage2",percentage2);
				context.put("percentage3",percentage3);
				context.put("percentage4",percentage4);
					
			}
		}catch(Exception e){ErrorDumpUtil.ErrorLog(e.getMessage());}
	}
}

