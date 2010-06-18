package org.iitk.brihaspati.modules.utils;
/*
 * @(#)NewsHeadlinesUtil.java
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur. 
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
 */
import java.util.Vector;
import java.util.Date;
import java.util.List;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.News;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
 
/**
 * In this class,We have news headlines for General or Course
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */


public class NewsHeadlinesUtil{
	/**
	* In this method, get all new news till the current date
	* @param group_id Integer
	* @see UserUtil
	* @return Vector
	*/
	public static Vector getNews(int group_id)
	{
                Vector entry=new Vector();
                try{
                /**
                * Retreives the current date of the system
                * @return String
                * @see ExpiryUtil in utils
                */
                String cdate=ExpiryUtil.getCurrentDate("-");
                /**
                 * Get the news id for the news till the current date
                 */

                Criteria crit=new Criteria();
                crit.add(NewsPeer.GROUP_ID,group_id);
                crit.add(NewsPeer.PUBLISH_DATE,(Object)cdate,crit.LESS_EQUAL);
                List news=NewsPeer.doSelect(crit);
                        /**
                         * Get the news details for a set of specific
                         * news
                         * @see ListdivideUtil in utils
                         */
                        for(int i=0;i<news.size();i++)
                        {
                                        News element=(News)(news.get(i));
                                        String news_subject=(element.getNewsTitle());
                                        String news_id=Integer.toString(element.getNewsId());
                                        int userId=(element.getUserId());
                                        String senderName=UserUtil.getLoginName(userId);

                                        Date pd=element.getPublishDate();
                                        String pdate=pd.toString();
                                        NewsDetail newsD=new NewsDetail();
                                        newsD.setNews_Subject(news_subject);
					newsD.setNews_ID(news_id);
                                        newsD.setSender(senderName);
                                        newsD.setPDate(pdate);
                                        entry.add(newsD);
                        }
        }
        catch(Exception ex)
        {
		ErrorDumpUtil.ErrorLog("The error in getNews() - NewsHeadloinesUtil"+ex);
        }
        return entry;
	}
}
