/*
 * @(#)unicodeConverter.java
 *
 *  Copyright (c) 2006-2007 ETRG,IIT Kanpur.
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
 *
 */

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.io.IOException;

/**
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista Bano</a>
 */

public class unicodeConverter{

	public static void main(String args[]) {
	try{

	/**
	 * FileInputStream fis = new FileInputStream("./Var_Key.properties")
	 * Uncomment The above Line of code When user want to convert
	 * templates Variable (it is mentioned in Var_Key.properties file)
	 * It takes source file to convert unicode into UTF-8
	 *
	*/
		String inputdir;
		String PropertyPath;
		String inputFileToConvert=null;
		String str;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File  existingFile = null;
		String guiDirPath=null;

        /**
        * Get the template path and property path  from build.properties
        * Get brih.Lang property from build.properties
        */
                String propName ="Multilingual.PropertyPath.property"; 
                Properties sysProps = System.getProperties();
                PropertyPath = sysProps.getProperty(propName);
                if(PropertyPath == null )
                {
                        System.out.println("Multilingual.PropertyPath is not defined in build.properties");
                        System.out.println("UNICODE CONVERTER IS FAILED");
                        System.exit(-1);
                }
		try
                {
                        Properties prop =new Properties();
                        FileInputStream finn = new FileInputStream(PropertyPath +"/build.properties");
                        prop.load(finn);
                        finn.close();
                        finn=null;
			inputFileToConvert = prop.getProperty("Multilingual.GUIPropFile");
                        guiDirPath= prop.getProperty("MultilingualGUIFile.dir");
		/**
		  * Uncomment these lines while debuuging
		 */
	/*	
                	System.out.println("\nRunning   On sysProps  " +sysProps);
	                System.out.println("\nRunning   On PropertyPath  " +PropertyPath);
		
                        System.out.println("Running Input File to Conver ======>"+inputFileToConvert);
                        System.out.println("Running Input File to Conver ======>"+ guiDirPath);
	*/	
                }//try 
	
                catch(IOException io)
                {
                        io.printStackTrace();
                        System.out.println("CONTROL FAILED\nMake sure that Multilingual.GUIPropFile and MultilingualGUIFile.dir has been defined in build.properties.");
                        System.exit(-1);
                }

		String Directory [] =inputFileToConvert.split(" ");
	        for(int i =0; i < Directory.length ;i++)
        	{ //for0
			System.gc();
			Properties p=new Properties();
			str= guiDirPath + Directory[i];


			fis = new FileInputStream(str);
                        System.out.println("\nRunning Input File to Conver ======>"+ Directory[i]);
		/**
		  * Uncomment these lines while debuuging
		 */
                 /*       System.out.println("Running Input File to Conver ======>"+ Directory[i]);
                        System.out.println("Running Input File to Conver1 ======>"+ str);
                        System.out.println("Running Input File to Conver2 ======>"+ fis);
                 */
			p.load(fis);
                   //     System.out.println("Running Input File to Conver3 ======>"+ p);
			Enumeration et = p.propertyNames();
		/**
		  * Uncomment these lines while debuuging
		 */
                  //      System.out.println("Running Input File to Conver ======>"+ et);
			String key= null,value = null;
			byte [] utf8 = null;

			existingFile=new File (guiDirPath+"/../../../templates/app/screens/"+Directory[i]);
			if(existingFile.exists())
                        {
                                boolean success=existingFile.delete();
				System.out.println("File deleted  ======>"+Directory[i]);
                        }

			fos = new FileOutputStream(guiDirPath+"/../../../templates/app/screens/"+Directory[i],true);
			while(et.hasMoreElements())
			{
				key =(String) et.nextElement();
				utf8 = p.getProperty(key,"").getBytes("UTF-8");
		        	value = new String(utf8, "UTF-8");
		//		System.out.println("key =====>" + key);
		//		System.out.println("Value =====>" + value);
	/**
	 * Uncomment the above two lines to see the key and their values in UTF-8 encoded format on screen, while conversion
	 * is taking place.
	 */	
				fos.write((key+"="+value +"\n").getBytes());
			}
			fos.close();
			System.out.println("File created  ======>"+Directory[i]);
			et = null;
			str = null;
			p = null;
			fis = null;
	        	System.gc();
		}//for0

	}//try
	catch(java.lang.Throwable t)
	{
		System.out.println("Exception " + t);
	}
	
	}//main
}//class

