package org.iitk.brihaspati.modules.screens.call;         
/*
 * @(#)ViewNews_Photo.java	
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
import javax.servlet.ServletOutputStream;

import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;  
import org.apache.turbine.modules.screens.VelocityScreen;

import org.iitk.brihaspati.modules.utils.ViewFileUtil;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.News;

/**
 * In this class, Display of contents for any file.
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */

public class ViewNews_Photo extends VelocityScreen
{
	/**
	* In this method,We display contents of any file or dir
	* @param data RunData
	* @param context Context
	* @see ViewFileUtil from Utils
	*/

     	public void doBuildTemplate( RunData data, Context context ) 
     	{
		try{
                        ParameterParser pp=data.getParameters();
			ServletOutputStream out=data.getResponse().getOutputStream();
			String date_string=pp.getString("date");
			String news_id=pp.getString("newsid");
			Criteria crit=new Criteria();
			crit.add(NewsPeer.NEWS_ID,news_id);
			List news=NewsPeer.doSelect(crit);
			String news_details=new String(((News)news.get(0)).getNewsDescription());
			String user_name=pp.getString("user");
		
			/**
		 	* Split the date into day,month and year and get the
		 	* corresponding month name
		 	*/
			String year=date_string.substring(0,4);
			String month=date_string.substring(5,7);
			String day=date_string.substring(8,10);

			int month_num=Integer.parseInt(month);
			String month_name=new String();
			switch(month_num){
			case 1:
				month_name="January";
				break;
			case 2:
				month_name="February";
				break;
			case 3:
				month_name="March";
				break;
			case 4:
				month_name="April";
				break;
			case 5:
				month_name="May";
				break;
			case 6:
				month_name="June";
				break;
			case 7:
				month_name="July";
				break;
			case 8:
				month_name="August";
				break;
			case 9:
				month_name="September";
				break;
			case 10:
				month_name="October";
				break;
			case 11:
				month_name="November";
				break;
			case 12:
				month_name="December";
				break;
			}
	 		String str=day+" "+month_name+" "+year+"\n\t"+news_details+"\n\t\t\t...."+user_name;

		/**
		 * Set the output in the desired format in
		 * ServletOutputStream.
		 */
			ViewFileUtil.ViewInfo(str,data);

                //	out.write(str.getBytes());
		}//try
		catch(Exception e)
		{	
			  data.setMessage("Error in Raw Page for display contains !!"+ e);

		}
    }//dobuild
}


