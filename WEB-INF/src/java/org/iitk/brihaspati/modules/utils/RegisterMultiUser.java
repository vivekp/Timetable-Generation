package org.iitk.brihaspati.modules.utils;

/*@(#)RegisterMultiUser.java
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */

import java.io.File;
import java.util.Vector;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 * This is the util class used to upload multiple users simultaneously
 *  @author <a href="mailto:ammuamit@iitk.ac.in">Amit Joshi</a> 
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a> 
 *  @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a> 
 */

public class RegisterMultiUser
{
	/**
	 * This method uploads simultaneously multiple users having the
	 * details stored in a file
	 * @param f File
	 * @param Gname String
	 * @param Role String
	 * @param serverName String
	 * @param serverPort String
	 * @param Langfile String
	 * @return Vector 
	 */
	public static Vector Multi_Register(File f,String Gname,String Role,String serverName,String serverPort,String Langfile)
	{
		System.gc();
		Vector ErrType=new Vector();
		try
		{
			int entryNumber=0;
			CourseUserDetail MsgDetails=new CourseUserDetail();
			/**
		 	* Stores the uploaded file on the specified path
		 	*/
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String line;
			/**
		 	* Read the lines in the file one by one and extracts
		 	* the user details with the
		 	* help of StringTokenizer
		 	*/
			while((line=br.readLine())!=null)
			{
				StringTokenizer st2=new StringTokenizer(line,";",true);
				entryNumber++;
				String username="",first_name="",last_name="",email="", passwd="";
				int error=0;
				String mail_msg="";
				String errMsg="";
				
	
				if(st2.countTokens()<5)
				{ 
					error=1;
				}
				else 
				{
					username=st2.nextToken().trim();
					if(username.equals(";"))
						{error=2;}
			      		else 
					{
						st2.nextToken();				
			      			passwd=st2.nextToken().trim();
			      			if(!passwd.equals(";"))
							{st2.nextToken();}
						else 
							{passwd=username;}
			      			first_name=st2.nextToken().trim();
			      			if(!first_name.equals(";"))
						{
			         			if(!st2.hasMoreTokens())
								{ error=1;}
							else
								{st2.nextToken();}
						}
						else 
							{first_name="";}
 						if(!st2.hasMoreTokens())
							{error=1;}
						else {
							last_name=st2.nextToken().trim();
			      				if(!last_name.equals(";")){
								if(!st2.hasMoreTokens())
								{error=1;}
								else {st2.nextToken();}
							} else {last_name="";}
                              				if(!st2.hasMoreTokens()) 
								{error=1;}
							else { email=st2.nextToken().trim();
				                        

							}
						}
					if(error==0){
							String str=UserManagement.CreateUserProfile(username,passwd,first_name,last_name,email,Gname,Role,serverName,serverPort,Langfile);
								error=3;
								errMsg=str;
						}
					}
				}
			/**
			 * Adds the error message to a vector if all the required fields 
			 * are not entered in the file. The entry number is also added.
			 */
			if( error!=0){
				MsgDetails=new CourseUserDetail();
				String ErrorEntryNumber=Integer.toString(entryNumber);
				MsgDetails.setErr_User(ErrorEntryNumber);
				if(error==1){		
				String error_msg1=MultilingualUtil.ConvertedString("error_msg1",Langfile);
				MsgDetails.setErr_Type(error_msg1);

				}
				if(error==2){

						
						String error_msg2=MultilingualUtil.ConvertedString("error_msg2",Langfile); 
						MsgDetails.setErr_Type(error_msg2);
				}
				if(error==3){
						MsgDetails.setErr_Type(errMsg);}
				ErrType.add(MsgDetails);
			}
		}

		fr.close();
		f.delete();
		}
		catch(Exception ex)
		{	
		
			
			ErrType.add("  The error in multi user registraion "+ex);

		}
		System.gc();
		return(ErrType);
	}
}
