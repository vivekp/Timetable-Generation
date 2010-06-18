package org.iitk.brihaspati.modules.actions;


/*
 * Copyright (c) 2006 ETRG,IIT Kanpur.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this  list of conditions and the following disclaimer.
 *
 * Redistribution in binary form must reproducuce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL ETRG OR ITS CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL,SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Contributors: Members of ETRG, I.I.T. Kanpur
 */

//java
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

//turbine
import org.apache.torque.util.BasePeer;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.turbine.services.servlet.TurbineServlet;

//brihaspati
import org.iitk.brihaspati.modules.utils.GetUnzip;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
  * This class is responsible for restoring the database entries
  * and course contents.
  * @author <a href="mailto:nksingh@brihaspatisolutions.co.in">Nagendra Kumar Singh</a>
  */

public class AdminRestoreAction extends SecureAction_Admin{
    
		/**
		 * This method used for restoring the database entries
		 * @param data RunData
	         * @param context Context
		 */

	public void doRestoreDatabase(RunData data, Context context){
		String s=null;
		String LangFile=(String)data.getUser().getTemp("LangFile");
		try {
			ParameterParser pp=data.getParameters();
			FileItem fileItem = data.getParameters().getFileItem("file");
			String fileName = fixFileName( fileItem.getName() );
			if(fileName.endsWith(".sql")){
			ErrorDumpUtil.ErrorLog("File Name_________>"+fileName);
        		//fileItem.write(new File(TurbineServlet.getRealPath( fileName )));
        		fileItem.write(new File(TurbineServlet.getRealPath("/tmp")+"/"+fileName));
	                File f=new File(TurbineServlet.getRealPath("/tmp")+"/"+fileName);
			ErrorDumpUtil.ErrorLog("File f_________>"+f);
	                //String f=new String(TurbineServlet.getRealPath("/tmp")+"/dataBasetest.sql");
        	       // fileItem.write(f.getAbsolutePath());
			FileReader fr=new FileReader(f);
	                BufferedReader br=new BufferedReader(fr);
			while((s=br.readLine()) !=null)
	                {
			ErrorDumpUtil.ErrorLog("s=br.readLine_________>"+s);
				if(!(s.startsWith("#")))
				{
					try{
						BasePeer.executeQuery(s);
					}catch (Exception e){ 
						data.setMessage("Restoration has some error!!!"+s+e);
					}
				}
	                }
			br.close();
	                fr.close();
			f.delete();	
//			data.setMessage("Restoration successfully completed!!!");
			String msg1=MultilingualUtil.ConvertedString("restore_msg3",LangFile);
                        data.setMessage(msg1);
			}
                        else{
                                String msg1=MultilingualUtil.ConvertedString("upload_msg5",LangFile);
                                data.setMessage(msg1);
                        }

	     
		}
		catch(Exception ex){data.addMessage("The Error in database restoration: "+ex +":You are not remove all the entries from database table except table and database ");
	}

}
		/**
		 * Used for restore the course content of courses
		 * as well as individual courses.
		 * @param data RunData
	         * @param context Context
		 */

	public void doRestoreContent(RunData data, Context context)
//        throws Exception
    	{
		String LangFile=(String)data.getUser().getTemp("LangFile");
        	try{
		        FileItem fileItem = data.getParameters().getFileItem("file");
		        String Path = TurbineServlet.getRealPath("/tmp")+"/"+fileItem.getName();
		        File filePath =new File( TurbineServlet.getRealPath("/tmp")+"/"+fileItem.getName());
		        fileItem.write(filePath);
		        File zipSourceFile=new File(Path);
		        String fName=fileItem.getName();
			if(fName.endsWith(".zip")){
			        if(fName.equals("Courses.zip")){
        	        		GetUnzip guz=new GetUnzip( zipSourceFile.getAbsolutePath(),TurbineServlet.getRealPath("/") );
					String msg1=MultilingualUtil.ConvertedString("restore_msg2",LangFile);
                        	        data.setMessage(msg1);
//		                	data.setMessage("All Courses sucessfully restore");
			        }else{
        	        		GetUnzip guz=new GetUnzip( zipSourceFile.getAbsolutePath(),TurbineServlet.getRealPath("/Courses") );
					String msg1=MultilingualUtil.ConvertedString("restore_msg1",LangFile);
                        	        data.setMessage(msg1);
	//		                data.setMessage("Course sucessfully restore");
	        		}
			        zipSourceFile.delete();
			}
			else{
				String msg1=MultilingualUtil.ConvertedString("upload_msg3",LangFile);
                                data.setMessage(msg1);
			}
	        }
        	catch (Exception ex){data.setMessage("The error in unzip and restoration" +ex);}
    	}
  	
    
    
   /**
     * Default action to perform if the specified action
     * cannot be executed.
     */
    public void doPerform( RunData data,Context context ) throws Exception
    {
		String LangFile=(String)data.getUser().getTemp("LangFile");

                String action=data.getParameters().getString("actionName","");
                if(action.equals("eventSubmit_doRestoreContent"))
                        doRestoreContent(data,context);
                else if(action.equals("eventSubmit_doRestoreDatabase"))
                        doRestoreDatabase(data,context);
                else{
                        String msg1=MultilingualUtil.ConvertedString("usr_prof2",LangFile);
                                data.setMessage(msg1);
		}

       // data.setMessage("Can't find the requested action!  System Restore failed");
    }

  	 /**
         * Fix file name if uploaded from a windows client
	 * @param longFile String
	 * @return String
         */
    public static String fixFileName(String longFile)
    {
        int loc = longFile.lastIndexOf("\\");
        return longFile.substring( (loc+1), longFile.length() );
    }

}
