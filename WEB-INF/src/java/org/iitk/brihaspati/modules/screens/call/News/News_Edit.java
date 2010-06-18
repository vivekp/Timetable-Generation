package org.iitk.brihaspati.modules.screens.call.News;
/*
 * @(#)News_Edit.java
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

import java.util.List;
import java.util.Vector;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.News;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
* This class contain the code for newsdetails and removal of news
* Grab all the records in a table using a Peer, and
* place the Vector of data objects in the context
* where they can be displayed by a #foreach loop.
* @author <a href="mailto:singh_jaivir@rediffmail.com ">jaivir singh</a>
* @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
*/

public class News_Edit extends SecureScreen
 {
	/**
	*This method return all the details of News
	*@param data RunData
	*@param category Integer
	*@return List
	*/
	private List getNews(RunData data ,int category)
	{
        	try
                {
			Criteria criteria = new Criteria();
                    	criteria.add(NewsPeer.GROUP_ID,category);
                    	criteria.addAscendingOrderByColumn(NewsPeer.PUBLISH_DATE);
			return NewsPeer.doSelect(criteria);
			
                 }
                 catch (Exception e)
                 {
                 	data.setMessage("Exception : "+e);
                     	return null;
                 }
	}

        /**
	* 
	* @param data RunData
	* @param context Context
	*/
        public void doBuildTemplate(RunData data,Context context)  
	{
		try{
			 /**get the status*/
                        String stat = data.getParameters().getString("status");
                        context.put("status",stat);
                        /** News Description Status*/
			String desc = data.getParameters().getString("desc","false");
                        context.put("desc",desc);
			/**Current login user id*/
			String userName=data.getUser().getName();
			int userId=UserUtil.getUID(userName);
			context.put("currentUserId",Integer.toString(userId));
			
			String course_id=new String();
			String course_name=new String();

			if(stat.equals("fromIndex")){
				course_id="global";
			}
			else{
				course_id=(String)data.getUser().getTemp("course_id");
				course_name=(String)data.getUser().getTemp("course_name");
			}
			int gid=GroupUtil.getGID(course_id);
			context.put("course",course_name);
			/**get All deatails of News*/
			List Store=getNews(data,gid);
			if(Store.size()!=0)
			{
				context.put("entries",Store);
				context.put("mode","News");
			}
			else
			{
				
				context.put("mode","NoNews");
			}
			
                        /** News Description*/
                        if(desc.equals("true"))
			{
                        	int news_id=data.getParameters().getInt("id");
				String News_Desc=getNewsDesc(news_id);
				context.put("News_Des",News_Desc);
				context.put("News_Id",Integer.toString(news_id));
			}
                }
		catch (Exception e)
		{
			data.setMessage("Exception : "+e);
		}
        }
	/**
	*This method return news description of particuler news
	*@param news_id Integer
	*@return String
	*/
	public static String getNewsDesc(int news_id)
	{
		String desc=new String();
		try
		{
			/**Code for getting newsid***/
                        Criteria crit=new Criteria();
                        crit.add(NewsPeer.NEWS_ID,news_id);
                   	/**Code for getting the newstext***/
                        List news_details=NewsPeer.doSelect(crit);
                        News t=(News)(news_details.get(0));
                        desc=new String(t.getNewsDescription());
		}
		catch(Exception ex)
		{
			desc="The error in get description of news !!"+ex;
		}
	return desc;
	}
}
