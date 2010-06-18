package org.iitk.brihaspati.modules.actions;
/*
 * @(#)RegisterMultiCourseInstructor.java	
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur. 
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
 */
//JDK
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
//Turbine
import org.apache.torque.util.Criteria;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
//Brihaspati
import org.iitk.brihaspati.om.Courses;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CourseManagement;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
/**
 *
 * This Action class for Registering a multiple course with Instructor(Primary) 
 * in the system from file.
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 */
public class RegisterMultiCourseInstructor extends SecureAction_Admin
{
	
	private String LangFile=new String();
	/**
 	  * This method actually registers a new course along with the instructor 
	  * in the system
 	  * @param data RunData instance
 	  * @param context Context instance
 	  * @exception Exception, a generic exception
 	  */
	public void doRegister(RunData data, Context context)
	{
	 /**
          * Getting file value from temporary variable according to selection
          * Replacing the value from Property file
         **/

		CourseUserDetail MsgDetails=new CourseUserDetail();
		LangFile=(String)data.getUser().getTemp("LangFile");
	        try
		 {//try
		 	ParameterParser pp=data.getParameters();
                        FileItem file = pp.getFileItem("file");
                        String fileName=file.getName();
//                        if((!fileName.endsWith(".txt"))||(!fileName.endsWith(".TXT")))
                        if((!fileName.endsWith(".txt"))||(file.getSize()<=0))
                        {//if#1
                                 /**
                                 * Getting file value from temporary variable according to selection of Language
                                 * Replacing the static value from Property file
                                 **/

                                String upload_msg1=MultilingualUtil.ConvertedString("upload_msg2",LangFile);
                                data.setMessage(upload_msg1+fileName);
                        }//end if#1
                        else{//else#1
				Date date=new Date();
                                File f=new File(TurbineServlet.getRealPath("/tmp")+"/"+date.toString()+".txt");
                                file.write(f);
				int entryNumber=0;
				Vector ErrType=new Vector();
				FileReader fr=new FileReader(f);
                	        BufferedReader br=new BufferedReader(fr);
                        	String line;
                        /**
                        * Read the lines in the file one by one and extracts
                        * the user details with the
                        * help of StringTokenizer
                        */
                        	while((line=br.readLine())!=null)
                        	{//while#1

                                	StringTokenizer st1=new StringTokenizer(line,";",true);
	                                entryNumber++;
        	                        String first_name="",email="", courseid="", courseName="";
                	                int error=0;
                        	        String mail_msg="";
                                	String errMsg="";
		                               if(st1.countTokens()<7 ||st1.countTokens()>7)//if#2
                                		        {error=1;}
							//insufficient argument
						else
                                		{//else#2
		                                        first_name=st1.nextToken().trim();
                		                        if(first_name.equals(";"))//if#3
                                	                	{error=2;}
                                        		else//else#3
                                        		{
		                                                st1.nextToken();
                		                                email=st1.nextToken().trim();
                                		                if(email.equals(";"))
                                                		        {error=2;}
		                                                else{//else#4
                			                                st1.nextToken();
                                        			        courseid=st1.nextToken().trim();
		                                           	        if(courseid.equals(";"))
                                        	        			{error=2;}
									else{//else#5
                			                                	st1.nextToken();
                                        			        	courseName=st1.nextToken().trim();
                                                				if(courseName.equals(";"))
											{error=2;}
                        		                       			else{//else#6
											int i=email.indexOf("@");
											String uname=email.substring(0,i);
											String check=courseid.concat(uname);
								/** Getting the group name from the database
								 *  and compare this group name with current group name
								 */
											Criteria crit = new Criteria();
											crit.add(CoursesPeer.GROUP_NAME,check);
		                							List v=CoursesPeer.doSelect(crit);
									                String gName="";
									                if(v.size() > 0 )
									                        gName=((Courses)(v.get(0))).getGroupName();

												if(check.equalsIgnoreCase(gName)){
												error=4;
												}
												else{//else#7
													
													 String passwd=uname;
													 String serverName=data.getServerName();
											                 int srvrPort=data.getServerPort();
											                 String serverPort=Integer.toString(srvrPort);
													 String dept="", description="", lname="";
											 /**
											  * Register a new course with instructor
											  * @see CourseManagement Utils
											  */ 
					 								String msg=CourseManagement.CreateCourse(courseid,courseName,dept,description,uname,passwd,first_name,lname,email,serverName,serverPort,LangFile);
													error=3;
		                                			                                errMsg=msg;
												}//end Else#7

                                                        				}//endelse#6
                                                				}//endelse#5
									}//endelse#4
								}//endelse#3
							}//endelse#2
				
			/**
                         * Adds the error message to a vector if all the required fields
                         * are not entered in the file. The entry number is also added.
                         */
                        if( error!=0){//if error
                                MsgDetails=new CourseUserDetail();
                                String ErrorEntryNumber=Integer.toString(entryNumber);
                                MsgDetails.setErr_User(ErrorEntryNumber);
                                if(error==1){
                                String error_msg1=MultilingualUtil.ConvertedString("error_msg1",LangFile);
                                MsgDetails.setErr_Type(error_msg1);

                                }
                                if(error==2){


                                                String error_msg2=MultilingualUtil.ConvertedString("error_msg2",LangFile);
                                                MsgDetails.setErr_Type(error_msg2);
                                }
                                if(error==3){
                                                MsgDetails.setErr_Type(errMsg);}
				if(error==4){
					MsgDetails.setErr_Type(MultilingualUtil.ConvertedString("c_msg1",LangFile));}
                                ErrType.add(MsgDetails);
                        }//endif error


			}//end while
			context.put("Msg",ErrType);
			br.close();
			fr.close();
			f.delete();
			}//end else#1
		}//end try
		catch(Exception e)
		{
			 data.setMessage("The error"+e);	
                          
		}
		
	}

	/**
 	  * This method is invoked when no button corresponding to 
 	  * doRegister is found
 	  * @param data RunData
  	  * @param context Context
 	  * @exception Exception, a generic exception
 	  */
	public void doPerform(RunData data,Context context) throws Exception{
		String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doUploadcourse"))
			doRegister(data,context);
		else{
			
		
		/**
                 * getting property file According to selection of Language in temporary variable
                 * getting the values of first,last names and
                 * configuration parameter.
                 */
 
                	LangFile=(String)data.getUser().getTemp("LangFile"); 
			String str=MultilingualUtil.ConvertedString("c_msg",LangFile);
                        data.setMessage(str);
		}
	}
}

