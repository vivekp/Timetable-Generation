package org.iitk.brihaspati.modules.screens;
/*
 * @(#)News_All.java
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

import java.util.Vector;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.modules.screens.VelocityScreen;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.NewsHeadlinesUtil;
/**
 * This class displays all the general news
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">jaivir singh</a>
 * @author <a href="mailto:madhavi_mungole@yahoo.co.in">Madhavi Mungole</a>
 */

public class News_All extends VelocityScreen 
{
	/**
	 * In this method, get all details of general news till the current date
	 * @param data RunData
	 * @param context Context
	 */
        public void doBuildTemplate(RunData data,Context context) 
	{
		try{
			ParameterParser pp=data.getParameters();
			String lang=pp.getString("lang","english");
			context.put("lang",lang);
			int startIndex=pp.getInt("startIndex",0);
			int endlastIndex=pp.getInt("end");
			context.put("endlastIndex",String.valueOf(endlastIndex));
			/**
			 * Get the news id for the news till the current date
			 */
			Vector newsd=NewsHeadlinesUtil.getNews(1);
			int t_size=newsd.size();
			/**
                         * If no news is there then set the appropriate
                         * message
                         */
	
			if(newsd.isEmpty())
			{
				context.put("status","empty");
			}	
			else{
				/**
	                         * Get the index while viewing news in multiple
        	                 * screens
                	         * @see ListMangement in utils
                        	 */
				int value[]=new int[7];
                                value=ListManagement.linkVisibility(startIndex,t_size,11);

                                int k=value[6];
                                context.put("k",String.valueOf(k));

                                Integer total_size=new Integer(t_size);
                                context.put("total_size",total_size);

                                int eI=value[1];
                                Integer endIndex=new Integer(eI);
                                context.put("endIndex",endIndex);

                                int check_first=value[2];
                                context.put("check_first",String.valueOf(check_first));

                                int check_pre=value[3];
                                context.put("check_pre",String.valueOf(check_pre));

                                int check_last1=value[4];
                                context.put("check_last1",String.valueOf(check_last1));

                                int check_last=value[5];
                                context.put("check_last",String.valueOf(check_last));

                                context.put("startIndex",String.valueOf(eI));

				/**
	                         * Get the news details for a set of specific
        	                 * news
                	         * @see ListMangement in utils
                        	 */

				Vector split_news=ListManagement.listDivide(newsd,startIndex,11);
				context.put("detail",split_news);

				context.put("status","Notempty");
			}
		}
		catch(Exception e){
			data.setMessage("The error in General news :- "+e);
		}
	}
}
