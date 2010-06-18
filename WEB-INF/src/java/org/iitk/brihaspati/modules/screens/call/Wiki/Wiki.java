package org.iitk.brihaspati.modules.screens.call.Wiki;    

/*
 * @(#)Wiki.java	
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
import org.iitk.brihaspati.modules.utils.UserUtil;
import  org.iitk.brihaspati.modules.screens.call.SecureScreen;    
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import org.iitk.brihaspati.modules.utils.WikiUtil;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.apache.velocity.context.Context; 
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;  
import org.iitk.brihaspati.modules.utils.MultilingualUtil;


/**
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 *
 */

                                                      
public class Wiki extends SecureScreen{

	/**
	 * This is the default method that builds the template page
	 * @param data Rundata
	 * @param context Context
	 */

	public void doBuildTemplate(RunData data,Context context) 
	{
		String LangFile= null;
		try{	 User user=data.getUser();
			 LangFile =(String)user.getTemp("LangFile");
			 String username=user.getName();
			 String firstname=user.getFirstName();
                         String lastname=user.getLastName();
			 String cId=(String)user.getTemp("course_id");
			 context.put("courseid",cId);
			 context.put("course",(String)user.getTemp("course_name"));
                         String role=(String)user.getTemp("role");
			 /**
			 * check if user is primary instructor
			 * as he alone can access Adminwiki.vm
			 */
			 boolean check_Primary=CourseManagement.IsPrimaryInstructor(cId,username);
			 if(check_Primary)
			 context.put("role","instructor");			
			 else
			 context.put("role","");

			 WikiUtil ol = new WikiUtil();
			 int i=0;
			 Vector all=new Vector();
			 String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
			 String filePathH=filePath + "/Wikihistory/" ;
			 String filePathF=filePath + "/Wikifirst/" ;
			 String filePathL=filePath + "/Wikilast/" ;
			 String filePathLog=filePath + "/Wikilog/";
			
			 File ff,fl,flog,fh;
			 /**
			 * test if o/s is Linux or not
			 * wiki assumes o/s to be windows if not linux
			 */
			 if(ol.isLinux())
			 {//for unix
			 	ff=new File(filePathF);
			 	fl=new File(filePathL);
			 	flog=new File(filePathLog);
			 	fh=new File(filePathH);
			 }//if ends
	                 else
			 {//for windows
				ff=new File(filePathF);
                                fl=new File(filePathL +"/RCS");
				/**
				* Wikilog & Wikifirst folder does not need RCS folder
				*/
                                flog=new File(filePathLog);
                                fh=new File(filePathH +"/RCS");		
		
			 }

			 ff.mkdirs();	
			 fl.mkdirs();	
			 flog.mkdirs();	
			 fh.mkdirs();
	

			 File Ftr[]=flog.listFiles();
	        
			 Arrays.sort(Ftr);
			 for(i=0;i<Ftr.length;i++)
			 {	
			 	all.addElement(Ftr[i]);
			 }

                         context.put("dirContent",all); 
			/**
			* below object of util is send to vm to call ol.getAuthor()
			*/
                         context.put("Util",ol); 
			/** 
			* below code displays  user specific pages
			*/
			String FL;
			Vector allFile_User=new Vector();
		        for(i=0;i<Ftr.length;i++)
			{
				 FL= filePathLog+"/"+Ftr[i].getName();	
				 String author=ol.getAuthor(FL);
				 if((author.equals(username))||(author.equals(firstname + lastname)))
				 	allFile_User.addElement(Ftr[i]);
			}//for			 
                         context.put("mypage",allFile_User);
			/**
			* release memory
			*/
			ol=null;
			all=null;
			allFile_User=null;
			fl=null;
			flog=null;
			ff=null;
			fh=null;

		   }//try
		 catch(Exception e)
		  {
			 data.setMessage("Error in screen call,Wiki,Wiki.java is ========>  "+ e);

		  }
	}
}
