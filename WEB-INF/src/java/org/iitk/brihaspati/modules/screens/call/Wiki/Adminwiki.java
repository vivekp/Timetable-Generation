package org.iitk.brihaspati.modules.screens.call.Wiki;         


/*
 * @(#)Adminwiki.java	
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
import java.util.Arrays;
import java.io.File;
import java.util.Vector;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.WikiUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;    
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Instructor;    
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context; 
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

	/*
	 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
	 */

public class Adminwiki extends  SecureScreen_Instructor{

	/**
	 * This is the default method that builds the template page
	 * @param data Rundata
	 * @param context Context
 	 *
 	 */

	public void doBuildTemplate(RunData data,Context context) 
	{
	
	
		  try{  //try starts
				User user=data.getUser();
				String cId=(String)user.getTemp("course_id");			        
				context.put("courseid",cId);
				context.put("course",(String)user.getTemp("course_name"));

				/**
				* code below will send all pages in Wiki in that particular course to vm
				*/
				
				ParameterParser pp = data.getParameters();
				String fName=pp.getString("fname","");
				String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
				String filePath2=filePath + "/Wikilog/" ;
				String filePatht=filePath +  "/Wikilast/" ;
				String fpath = filePath2 +"/"+ fName ;
				File f1=new File(filePath2);
				String Ftr[]=f1.list();
				Arrays.sort(Ftr);
                        	Vector all=new Vector();
                        	for(int i=0;i<Ftr.length;i++)
                        	{
                                       all.addElement(Ftr[i]);
                        	}// end for
				/**
				* Check if any page has been created or not 
				*/
				if(all.isEmpty())
                        	context.put("mypage","empty");
				else
                        	context.put("mypage",all);

				/**
				* below code is written to find out if 
				* the page  has Edit buttton disabled or not
				*/
		
				WikiUtil ol = new WikiUtil();
				if(ol.checktraffic(fpath))
				context.put("traffic","stop");
				else
				context.put("traffic","start");

				/**
				* below code displays list of Edit disabled pages
				*/
				int Count=0;
				/** 
				* count of number of edit disabled pages
				*/
				Vector mall=new Vector();
                                for(int i=0;i<Ftr.length;i++)
                                {
                                        String fpath1 = filePath2 +"/"+ Ftr[i] ;
					if(ol.checktraffic(fpath1))
					{
						mall.addElement(Ftr[i]);
						Count++;
					}//if ends
                                }//for
				/**
				* Check if there are edit disabled pages or not
				*/
                                if(mall.isEmpty())
				{
                                	context.put("dispage","empty");
                                	context.put("num","0");
				}
                                else
				{
                                	context.put("dispage",mall);
                                	context.put("num",Count);
				}	
				/**
				* below code tells if merge has been 
				* done on a file or not
				*/
				if(ol.getMerge(fName +",m" ,filePatht).equals("true"))
                                context.put("merge","true");
				else
				context.put("merge","false");
				/**
                         	* release memory
                         	*/
                         	f1=null;
                         	all=null;
                         	mall=null;
                         	ol=null;
				Ftr=null;

                   }//try ends
                        catch(Exception e)
                   { //catch starts
			
			   data.setMessage("Error in screen call,Wiki,Adminwiki.java is ========>  "+ e);
		   
                   }//catch ends	

	}// function ends

}// class ends


