package org.iitk.brihaspati.modules.screens;

/*
 * @(#)BrihaspatiLogin.java	
 *
 *  Copyright (c) 2004-2005,2009 ETRG,IIT Kanpur. 
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

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;       
import org.apache.turbine.modules.screens.VelocityScreen;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.om.NewsPeer;
import org.iitk.brihaspati.om.News;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.RemoteCourseUtilClient;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.NewsHeadlinesUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.NewsDetail;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.apache.turbine.services.servlet.TurbineServlet;
import java.util.List;
import java.util.Date;
import java.util.Vector;
import org.apache.torque.util.Criteria;
import org.apache.xmlrpc.XmlRpc;
/**
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 * @author <a href="mailto:sweetshaista00@yahoo.com">Shaista Bano</a>
 */
public class BrihaspatiLogin extends VelocityScreen
{
    /**
     * Place all the data object in the context
     * for use in the template.
     */
    public void doBuildTemplate( RunData data, Context context )
    {
	 	/**
                 * getting the value of lang  According to selection of Language
                 * getting property file According to selection of Language in temporary variable
                 * and replacing the String from property file
                 * getting value of file parameter
                 * @see MultilingualUtil in utils
                 */
                String LangFile=null;
                MultilingualUtil m_u=new MultilingualUtil();
		System.gc();
                try{
                        ParameterParser pp=data.getParameters();
                        String lang=pp.getString("lang","english");
                        if(lang.equals("hindi"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_hi.properties");
                        }
                        else if(lang.equals("urdu"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_urd.properties");
                        }
                        else if(lang.equals("bangla"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_bn.properties");
                        }
                        else if(lang.equals("marathi"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_mr.properties");
                        }
                        else if(lang.equals("telugu"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_te.properties");
                        }
                        else if(lang.equals("french"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_fr.properties");
                        }
                        else if(lang.equals("german"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_de.properties");
                        }
                        else if(lang.equals("italian"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_it.properties");
                        }
                        else if(lang.equals("russian"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_ru.properties");
                        }
                        else if(lang.equals("korean"))
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_ko.properties");
                        }
                        else 
                        {
                                LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_en.properties");
                        }
                        String selLang=pp.getString("selectedLang");
                        context.put("LangFile",LangFile);
			context.put("lang",lang);
			//context.put("lang",lang);
			Vector newsd=NewsHeadlinesUtil.getNews(1);
			int t_size=newsd.size();
	        	if(t_size!=0)
                	{	
                                Vector split_news=ListManagement.listDivide(newsd,0,10);
                        	context.put("detail",split_news);
                        	context.put("status","Notempty");
                	}
			else
			{
                        	context.put("status","empty");
			}
			context.put("sample",newsd);

			/**
			* Keep xmlrpc port Alive
			*/

                        boolean bool= XmlRpc.getKeepAlive();
                        if(!bool)
			{
                        	XmlRpc.setKeepAlive(true);
			}//if
//			String activatemsg = "";
//			String Successmsg =  data.getServerName();
//			while(!activatemsg.equals(Successmsg))
//			{
//				activatemsg = RemoteCourseUtilClient.ActivateLocalXmlRpcPort();
//			}
			// for notofication
			String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Notification.properties";
			String fhead = AdminProperties.getValue(path,"brihaspati.admin.flashHeading.value");
	                context.put("fNoti",fhead);


		}
                catch(Exception e)
		{
			data.setMessage("The Error in Login Page !!"+e);
		}

    }
}
