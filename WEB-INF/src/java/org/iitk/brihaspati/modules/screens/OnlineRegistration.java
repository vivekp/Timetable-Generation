package org.iitk.brihaspati.modules.screens;

/* 
 * @(#)OnlineRegistration.java
 *
 *  Copyright (c) 2008 ETRG,IIT Kanpur.
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
 */


import java.util.List;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.apache.turbine.modules.screens.VelocityScreen;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.services.servlet.TurbineServlet;


 /**
 * @author <a href="mailto:omprakash_kgp@yahoo.co.in">Om Prakash</a>
 */

public class OnlineRegistration extends VelocityScreen
{
	public void doBuildTemplate(RunData data, Context context)
	{
		try{
 			ParameterParser pp=data.getParameters();
			String lang=pp.getString("lang","english");
        	        context.put("lang",lang);
			String LangFile=new String();
			String status=pp.getString("status","");
	
			if(status.equals("UserResitration"))
				context.put("status","UserResitration");
				
			else if(status.equals("CourseRegistration"))
				context.put("status","CourseRegistration");
			List CourseList=ListManagement.getCourseList();
			context.put("courseList",CourseList);
		}
		catch(Exception e) { 	data.setMessage("Error in Online Registration !!" +e); }
		
	}
}

