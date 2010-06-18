package org.iitk.brihaspati.modules.screens.call.News;
/*
 * @(#)News_Update.java
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
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
/**
 * In this class,We get all details of news for updation
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">jaivir singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

import java.util.Vector;
import java.util.List;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.News;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

public class News_Update extends SecureScreen
{
	/**
	*@param data RunData
	*@param context Context
	*/
        public void doBuildTemplate(RunData data,Context context) 
        {
		try{
		//***Get the status****
			String stat=data.getParameters().getString("status");
			context.put("status",stat);
			ParameterParser pp=data.getParameters();
  			User user=data.getUser();
		
		//***Get the coursename****	 
                        
                        context.put("course",(String)user.getTemp("course_name"));
		
		//***Get the newsId***
		
			String news_id=pp.getString("id");
                
		//***Get the mode****        
		
			String modetype=pp.getString("mode");   
		
		//***Get the value of fields from the table****	
		
			Criteria crit=new Criteria();
			crit.add(NewsPeer.NEWS_ID,news_id);
			List news_detail=NewsPeer.doSelect(crit);
			News t=(News)(news_detail.get(0));
			String text=new String(t.getNewsDescription());
			String pdate=(t.getPublishDate()).toString();  
                     	context.put("pdat",pdate);
			
		//***Get the year from the publish date***
		
			String year=pdate.substring(0,4);

		//***Get the month from the publish date****

			String month=pdate.substring(5,7);
		
		//***Get the days from the publish date****	
			
			String days=pdate.substring(8,10);
		        context.put("year",year);
			 int year1=Integer.parseInt(year);
		        context.put("month1",month);
		        context.put("days",days);
			context.put("details",news_detail);
			context.put("id",news_id); 
			context.put("news_text",text);
                        context.put("mode",modetype);
		
		//***Store the years in a year list****	
			
			Vector year_list=new Vector();
			for(int i=year1;i<=year1+20;i++){
                        	year_list.addElement(Integer.toString(i));
                	}

                	context.put("year_list",year_list);	 
		}
		catch(Exception e)
		{
			data.setMessage("The error in News Updation!!"+e);
		}
	}
}


