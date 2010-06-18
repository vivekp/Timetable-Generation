package org.iitk.brihaspati.modules.screens.call.Wiki;         

/*
 * @(#)Logwiki.java	
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
 * 
 */


import  org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;  
import org.apache.turbine.om.security.User;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;



/**
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 */

public class Logwiki extends SecureScreen
{
    public void doBuildTemplate( RunData data, Context context ) 
   {
	try{

		/**
		* Put the contents of corresponding log file to vm
		*/
	    	User user=data.getUser();
		String cId=(String)user.getTemp("course_id");
		context.put("courseid",cId);
		context.put("course",(String)user.getTemp("course_name"));
    	    	String fName=data.getParameters().getString("filename","");
		String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/"+ "/Wikilog/"+fName);
 		FileReader fr= new FileReader(filePath);
		BufferedReader br=new BufferedReader(fr);
		Vector v = new Vector();
		String s2=null;
		while((s2=br.readLine())!=null)
		{
			v.addElement(s2);
	     	}	
	    	context.put("first",v);
	        br.close();
		fr.close();
		/**
		* this is done to put value of file name addPathInfo to action file 
		*/
  		context.put("filename",fName);
		/**
		* release memory
		*/
		v=null;
		fr=null;
		br=null;
		/**
		* Getting file object from temporary variable and 
		* Replacing the String from Propety file
		*/
              }
		catch(Exception e)
		{
			   	
			   data.setMessage("Error in screen call,Wiki,Logwiki.java is ========>  "+ e);

		}
    }
}


