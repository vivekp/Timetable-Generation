
/*
 * @(#)Controltemporary.java
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
 *
 *
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 * Last modified on Thu Jan 19 11:28:47 IST 2006 by Manvendra Baghel
 *
 */
import java.util.Properties;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 *
 * This function creates / removes temporary files needed in Texen task
 */


class Controltemporary{
	/**
        * This function creates / removes temporary files needed in Texen task
	* @param work String
	* @param Tempath String
	* @param Propath String
        * @return void
        */
        static void temporary(String work ,String Tempath ,String Propath)
        {
		try
		{
			File fempty = new File(Tempath +"/empty.control");
			File fput   = new File(Tempath+"/Putcontext.control");
			File fhtm   = new File(Tempath+"/HtmlTemplate.control");
			/**
			* Control.property will be needed for build.xml and so has to be kept in Propertypath
			*/
			File fCtrl   = new File(Propath+"/Control.properties");
			if(work.equalsIgnoreCase("insert"))
			{
				/**
                                * Delete if old Control.properties exists
                                */
                                if(fCtrl.exists())
                                	fCtrl.delete();   
			//	System.out.println("Creating temporary Files");
				FileWriter fw = new FileWriter(fCtrl);
				fw.write("ifInsert = true \n");
				fw.close();
			//	System.out.println("Created Control.properties");
				fw= new FileWriter(fempty);
				fw.write("");
				fw.close();
			//	System.out.println("Created empty.control ");
				fw = new FileWriter(fput);
				fw.write("");
				fw.close();
			//	System.out.println("Created Putcontext.control ");
				fw= new FileWriter(fhtm);
				fw.write("#$line1(\"lang\" $line2 \n");
        			fw.write("#$line\"$line3$line0\") ");
        			fw.close();
			//	System.out.println("Created HtmlTemplate.control ");
				/**
				* Release memory
				*/
				fw = null;
			//	System.out.println("Created All Temporary Files");
			}//if
			else
			{
			//	System.out.println("Removing temporary Files");
				File farr []=new File[]{fempty,fput,fhtm,fCtrl};
				for(int fi=0;fi<farr.length;fi++)
				{
					if(farr[fi].exists())
					{
						farr[fi].delete();
					}
				}//for
				farr=null;
			//	System.out.println("Removed All temporary Files");
			}//else
			/**
                        * Release memory
                        */
                        fempty =null;
                        fput =null;
                        fhtm =null;
                        fCtrl =null;
		}//try
		catch(IOException io )
		{
			io.printStackTrace();
		}
	}//temporary ends
}//classtemporary ends
