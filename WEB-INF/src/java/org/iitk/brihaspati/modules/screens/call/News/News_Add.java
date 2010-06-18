package org.iitk.brihaspati.modules.screens.call.News;
/*
 * @(#)News_Add.java
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
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 * This class contains code for all record of News
 * Grab all the records in a table using a Peer, and
 * place the Vector of data objects in the context
 * where they can be displayed by a #foreach loop.
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">jaivir singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
 */

public class News_Add extends SecureScreen
{
	/**
	* @param data RunData
	* @param context Context
	*/		
  	public void doBuildTemplate(RunData data,Context context)
	{
		try
		{
		 /**get the status***/
                        String stat = data.getParameters().getString("status");
                        context.put("status",stat);
		/**
		*Retrieves the current date of the System
		*@see ExpiryUtil in utils
		*/
			String course_name=(String)data.getUser().getTemp("course_name");
                        context.put("course",course_name);
			String cdate=ExpiryUtil.getCurrentDate("");
			int currentdate=Integer.parseInt(cdate);

		/**
		 * Get the current day from the currentdate
		 */
			int cday=currentdate%100;
			String cdays=Integer.toString(cday);
			if(cday<10)
                        	cdays="0"+cdays;
			context.put("cdays",cdays);
			currentdate=currentdate/100;

		/**
		 * Get the current month from the currentdate
		 */

			int c_month=currentdate%100;
			String cmonth=Integer.toString(c_month);
			if(c_month<10)
                        	cmonth="0"+cmonth;
			context.put("cmonth",cmonth);
		/**
		 * Get the current year from the currentdate
		 */
			int cyear1=currentdate/100;
			String cyear=Integer.toString(cyear1);
			context.put("cyear",cyear);
		
		/**			
		* Store the years in the year list
		*/				
		
			Vector year_list=new Vector();
			for(int i=cyear1;i<=cyear1+20;i++){
				year_list.addElement(Integer.toString(i));
			}

			context.put("year_list",year_list);

		}
		catch(Exception e)
		{
			data.setMessage("The error in News Added !!!"+e);
		}
	}
}
