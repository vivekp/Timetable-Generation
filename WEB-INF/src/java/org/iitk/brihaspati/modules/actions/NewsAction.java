package org.iitk.brihaspati.modules.actions;
/*
 * @(#)NewsAction.java
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

import java.sql.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

import org.iitk.brihaspati.om.News;
import org.iitk.brihaspati.om.NewsPeer;

/**
 *
 * This class contains code for inserting,Updating and deleting news in the database
 * Grab all the records in a table using a Peer, and
 * place the Vector of data objects in the context
 * @author <a href="mailto:singh_jaivir@rediffmail.com">jaivir singh</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
*/

public class  NewsAction extends SecureAction
{
	/**
	 * In this method,we post News in general type or course specific
	 * @param data RunData
	 * @param context Context
	 */	 				
        public void doInsert(RunData data, Context context)
        {
		try
		{
			String LangFile=data.getUser().getTemp("LangFile").toString();
			/**
			*Retrieves current date of the System
			*@see ExpiryUtil in utils
			*/ 	
        		
			int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
 			ParameterParser pp=data.getParameters();

			/**get the userid,username and courseid****/

        		User user=data.getUser();

                	String cid=new String();
			String path=pp.getString("status");

			if(path.equals("fromIndex"))
				cid="global";
			else if(path.equals("fromCourse"))
				cid=(String)user.getTemp("course_id");

 			String uname=user.getName();
			int u_id=UserUtil.getUID(uname);
                	String uid=Integer.toString(u_id);
			int gid=GroupUtil.getGID(cid);
			
			/**get the publishdate****/			
			
			String Year=pp.getString("Start_year");
			int Year1=Integer.parseInt(Year);
			String Month=pp.getString("Start_mon");
			int Month1=Integer.parseInt(Month);
			String date=pp.getString("Start_day");
			int Day=Integer.parseInt(date);
				
			String pdate=Year+Month+date;
			int pubdate=Integer.parseInt(pdate);

			/** Publish date Convert String type to Date type*/
			String pdate1=Year+"-"+Month+"-"+date;
			Date Post_date=Date.valueOf(pdate1);
 			
			String news_title=pp.getString("nt");
			/** 
			 *  Convert special character and scripts 
			 *  @see StringUtil in Util
			 */
                       news_title = StringUtil.replaceXmlSpecialCharacters(news_title);

        		String news=pp.getString("news");
			int ext=Integer.parseInt(pp.getString("et"));
			/** Expiry date get using ExpiryUtil and convert String type to Date type*/
			String Expdate=ExpiryUtil.getExpired(pdate1,ext);
			Date Expiry_date=Date.valueOf(Expdate);
			
			String msg=new String();
			if(pubdate >= curdate) 
			{
				if((Month1==4||Month1==6||Month1==9||Month1==11) && (Day>30))
                          	{
					msg=MultilingualUtil.ConvertedString("news_Checkmsg1",LangFile);
					data.setMessage(msg);
				   	return;
				}
         	        	if(Month1==2)
	       			{
           				if((Day>29)&&((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0)&&(Year1%400==0)))
                    			{
						msg=MultilingualUtil.ConvertedString("news_Checkmsg2",LangFile);
						data.setMessage(msg);
						return;	
					}	
             			else if (Day>28 && (!(((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0)&&(Year1%400==0)))))
                    				{
						msg=MultilingualUtil.ConvertedString("news_Checkmsg3",LangFile);
						data.setMessage(msg);
						return;
						}
          			}
				
				//***Insert the details of news in the database*****
				Criteria crit=new Criteria();
				crit.add(NewsPeer.GROUP_ID,gid);
				crit.add(NewsPeer.USER_ID,uid);
				crit.add(NewsPeer.NEWS_TITLE,news_title);
				crit.add(NewsPeer.NEWS_DESCRIPTION,news);
				crit.add(NewsPeer.PUBLISH_DATE,Post_date);
				crit.add(NewsPeer.EXPIRY,ext);
				crit.add(NewsPeer.EXPIRY_DATE,Expiry_date);
				NewsPeer.doInsert(crit);
				msg=MultilingualUtil.ConvertedString("news_msg1",LangFile);
                        	data.addMessage(msg);
			}
			else
			{
				msg=MultilingualUtil.ConvertedString("news_msg2",LangFile);
                        	data.setMessage(msg);
                    	}
		
		}
		catch(Exception e)
		{
			data.setMessage("The Error in Add News !!"+e);
		}  
	}       
	/**
	 * In this method, Updation of News
	 * @param data RunData
	 * @param context Context
	 * 
	 */
        public void doUpdate(RunData data, Context context)
        {
		try
		{
		String LangFile=data.getUser().getTemp("LangFile").toString();
		ParameterParser pp=data.getParameters();
		/**
		 * Get the status from URL and put it in context
		 */

		context.put("status",pp.getString("status"));

		/**
		 * Get newsId for updation of data
		 */
		int news_id=pp.getInt("id");
		/**
		 * Get the user name of the current and get user id for the user
		 * @see UserUtil in utils
		 */

		String Uname=data.getUser().getName();
	        int cUid=UserUtil.getUID(Uname);		
						
		/**
		 * Get the user id of the user who has posted the news
		 */
                Criteria crit = new Criteria();
                crit.add(NewsPeer.NEWS_ID,news_id);
                List Nuid=NewsPeer.doSelect(crit);
                News element=(News)Nuid.get(0);
                int uid=element.getUserId();
		/**
		 * Check if the current user can update the news. If he can, then
		 * update the news else return the error message
		 */
		String msg=new String();
                if((cUid == uid) ||(cUid == 1))
		{
			/**
		 	* Get all the updated data for the news from the vm page
		 	*/
			String newst=pp.getString("newst");
			/**
                         *  Convert special character and scripts
                         *  @see StringUtil in Util
                         */
                       newst = StringUtil.replaceXmlSpecialCharacters(newst);

			String news=pp.getString("news");
			int expt=pp.getInt("expt");
			String year=pp.getString("year");
			String month=pp.getString("month");
			String day=pp.getString("days");

			/**
		 	* Get the date by joining the day,month and year obtained from the vm page 
		 	*/
		
			String date=year+month+day;
			String modi_date=year+"-"+month+"-"+day;
			Date Modi_PDate=Date.valueOf(modi_date);	
			int Year1=Integer.parseInt(year);
			int Month1=Integer.parseInt(month);
			int Day=Integer.parseInt(day);
			int pubdate=Integer.parseInt(date);
			/** Expiry date get using ExpiryUtil and convert String type to Date type*/
			String Expdate=ExpiryUtil.getExpired(modi_date,expt);
			Date Expiry_date=Date.valueOf(Expdate);

			/**
		 	* Get the current date (Date format yyyymmdd)
		 	* @see ExpiryUtil utils
		 	*/
			int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
			/**
		 	* Check if the date chosen by the user is a valid date
		 	*/
			if(pubdate >= curdate)
			{
				if((Month1==4||Month1==6||Month1==9||Month1==11) && (Day>30))
				{
					msg=MultilingualUtil.ConvertedString("news_Checkmsg1",LangFile);
					data.setMessage(msg);
					return;
				}
         	        	if(Month1==2){
					if((Day>29)&&((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0)&&(Year1%400==0))){
						msg=MultilingualUtil.ConvertedString("news_Checkmsg2",LangFile);
						data.setMessage(msg);
						return;	
					}	
             				else if (Day>28 && (!(((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0)&&(Year1%400==0))))){
						msg=MultilingualUtil.ConvertedString("news_Checkmsg3",LangFile);
						data.setMessage(msg);
						return;
					}
           			}
				//***Update the details of news in the database*****
				crit=new Criteria();
				crit.add(NewsPeer.NEWS_ID,news_id);
				crit.add(NewsPeer.NEWS_TITLE,newst);
				crit.add(NewsPeer.NEWS_DESCRIPTION,news);
				crit.add(NewsPeer.PUBLISH_DATE,Modi_PDate);
				crit.add(NewsPeer.EXPIRY,expt);
				crit.add(NewsPeer.EXPIRY_DATE,Expiry_date);
				NewsPeer.doUpdate(crit);
				msg=MultilingualUtil.ConvertedString("news_msg3",LangFile);
                       		data.setMessage(msg);
			}
			else
			{
				msg=MultilingualUtil.ConvertedString("news_msg5",LangFile);
                        	data.setMessage(msg);
			}
		}
		else
		{
			String msg3=MultilingualUtil.ConvertedString("news_msg4",LangFile);
                        data.setMessage(msg3);
			return;
		}
		}//end of try
		catch(Exception ex)
		{
			data.setMessage("The error in updation of news "+ex);
		}
	}
                                                    
	/**
	 * In this method, Delete news from the database
	 * @param data RunData
       	 * @param context Context
       	 * 
       	 */

	public void doDelete(RunData data, Context context)
	 {
		try
		{
		String LangFile=data.getUser().getTemp("LangFile").toString();
		ParameterParser pp=data.getParameters();
		String stat = data.getParameters().getString("status");
                context.put("status",stat);
		/**
		 * Get the user name and corresponding user id for the current user
		 * @see UserUtil in utils
		 */
		String Uname=data.getUser().getName();
		int cUid=UserUtil.getUID(Uname);
		/**
		 * Get the user id of the user who has posted the news
		 */
		String msg=new String();
                Criteria crit = new Criteria();
		String news_List=data.getParameters().getString("deleteFileNames","");
                if(!news_List.equals(""))
                {
                	StringTokenizer st=new StringTokenizer(news_List,"^");
                        for(int j=0;st.hasMoreTokens();j++)
                        { //first 'for' loop
                                String n_id=st.nextToken();
                		crit = new Criteria();
				crit.add(NewsPeer.NEWS_ID,n_id);
                		List Nuid=NewsPeer.doSelect(crit);
	        		News element=(News)Nuid.get(0);
	        		int uid=element.getUserId();
				/**
		 		* Check if the current user can update the news. If he can, then
		 		* update the news else return the error message
		 		*/
				if((cUid == uid) ||(cUid == 1))
				{
                   			crit = new Criteria();
					crit.add(NewsPeer.NEWS_ID,n_id);
					NewsPeer.doDelete(crit);
					msg=MultilingualUtil.ConvertedString("news_msg6",LangFile);
                        		data.setMessage(msg);
				}
				else
				{
					msg=MultilingualUtil.ConvertedString("news_msg7",LangFile);
                        		data.setMessage(msg);
				}
			}
		}
		}//end of try
		catch(Exception ex)
		{
			data.setMessage("The error in the news deletion !!"+ex);
		}
	}
	/**
          * This method is invoked when no button corresponding to
          * doInsert is found
          * @param data RunData
          * @param context Context
          * @exception Exception, a generic exception
          *
          */
	public void doPerform(RunData data,Context context) throws Exception
        {
        	String action=data.getParameters().getString("actionName","");
                if(action.equals("eventSubmit_doInsert"))
                	doInsert(data,context);
                else if(action.equals("eventSubmit_doUpdate"))
                	doUpdate(data,context);
                else if(action.equals("eventSubmit_doDelete"))
                	doDelete(data,context);
                else    
                	data.setMessage("Cannot find the button");
       }
}	
