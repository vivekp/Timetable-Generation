
/*
 * @(#)Control.java
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
 * This class was made to read the directives in Velocity 
 * as Genrator parse do not print directives 
 */

public class Control{

	static String inputdir;
	static String PropertyPath;
	static String TemplatePath;
        static String DevelopingPath;
        static String Langprop;

	/**
	* Constructor 
	* @param action String accepts values insert/remove
	* @param  Directory String accepts values Raw/Raw_RL
	* @return Control Object
	*/
	Control(String action ,String Directory)
	{

	/**
	* Get the current path
	*/
		inputdir  = System.getProperty("user.dir");
		System.out.println("Running "+ action +"   On   " +inputdir);
	/**
	* Get the template path and property path  from build.properties
	* Get brih.Lang property from build.properties 
	*/
		String  propName = "Multilingual.PropertyPath.property";
        	Properties sysProps = System.getProperties();
  	        PropertyPath = sysProps.getProperty(propName);
		if(PropertyPath == null )
		{
			System.out.println("Property Path is null please check build.xml/property for Multilingual.PropertyPath");
			System.out.println("CONTROL FAILED");	
			System.exit(-1);
		}
		try
		{
			Properties prop =new Properties();
        		FileInputStream finn = new FileInputStream(PropertyPath +"/build.properties");
        		prop.load(finn);
        		finn.close();
			finn=null;
			if(Directory.equals("Raw"))
			{
        			Langprop = prop.getProperty("Multilingual.Lang");
			}
			else
			{
        			Langprop = prop.getProperty("Multilingual.LangRL");
			}
			TemplatePath= prop.getProperty("Multilingual.TemplatePath");
			DevelopingPath= prop.getProperty("Multilingual.DevelopingPath");
            	        System.out.println("On Language Directories "+Langprop);
         		/* System.out.println("Developing Path ========================> "+DevelopingPath); 
        		System.out.println("Template Path ========================> "+TemplatePath);
    		    	System.out.println("Property Path ========================> "+PropertyPath);
			*/
		}//try
		catch(IOException io)
		{
			io.printStackTrace();
			System.out.println("CONTROL FAILED");	
			System.exit(-1);
		}

		
	}//constructor ends

public static void main(String arg[]){
	/**
        * This checks if any  argument is supplied by user
        */
        if( arg.length < 1  )
        {
                System.out.println("USAGE: java Control  [insert | remove ][value]");
                System.exit(-1);
        }                                                                           
	boolean bi = arg[0].equalsIgnoreCase("insert");
	boolean br = arg[0].equalsIgnoreCase("remove");
	boolean b = !(bi ^ br);
	/**
	* This checks if any correct argument is supplied by user
	*/
	if(b)
	{
		System.out.println("USAGE: java Control  [insert | remove ][value]");
		System.exit(-1);
	}
	/**
	* Call the constructor than sub program according to String Array 
	*/
	Control c = null;
	String Directory [] =new String [] {"Raw","Raw_RL" } ;
	for(int i =0; i < Directory.length ;i++)
	{
		c = new Control(arg[0],Directory[i]);
		parsedir(arg,Directory[i],bi,br);
	}//for


}//main


/**
* This method parses directories
* @param arg String [] have values insert,remove
* @param  Directory String accepts values Raw/Raw_RL
* @param  bi boolean ,indicates if action insert
* @param  br boolean ,indicates if action remove
*/

public static void parsedir(String arg[],String Directory,boolean bi,boolean br){

	/**
	* Convert this string into String array and than to file array
	*/
	String Langarr[]=Langprop.split(" ");
	File LangFilearr[]=new File[Langarr.length];
	String l=null;
	String s=null;
	for(int i=0;i< Langarr.length ;i++)
	{
		s=Langarr[i];
		l= TemplatePath +"/"+s ;
		LangFilearr[i]=new File(l);
	}
	/**
	* Control.vm will be in template path change if path changes
	*/
	File fvm = new File(TemplatePath +"/Control.vm");
	File fbuild = new File(PropertyPath +"/build.xml");
	/**
	* Raw directory have all templates files or directories 
	*/
	File fRaw = new File(TemplatePath +"/"+Directory);
	/**
	* call directory have all templates files link according language  
	*/
	File fCall = new File(TemplatePath +"/call");
	boolean bRaw = fRaw.exists();
	boolean bCall = fCall.exists();
	boolean bvm = fvm.exists();
	boolean bbuild = fbuild.exists();
	/**
	* Checks if Files/Folders  required for Control.java exists or not
	*/
	boolean bexists1 =(!(bRaw && bvm && bbuild));
	/**
	* Checks if Raw , Control.vm ,Control.java ,build.xml exists or not
	* Runs only when Insert command is executed
	*/
	boolean bexists2 = bi && bexists1;
	/**
	* Checks if language directory is created or not
	* Runs only when Remove command is executed 
	*/
	/**
	* boolean bEng = fEng.exists();
	* boolean bHin = fHin.exists();
	* boolean bUrdu = fUrdu.exists();
	* boolean bexists31 = bEng && bHin && bUrdu ;
	* above statements are implemented by for loop below
	*/
	boolean bexists31=true;
	for(int i=0;i< Langarr.length ;i++)
	{
		bexists31=bexists31 && LangFilearr[i].exists(); 

	}
	boolean bexists3 = bexists31 && bCall ;
	boolean bexists4 = br && (!(bexists3));
	if(bexists2 || bexists4)
	{
		if(bexists2)
		{
			System.out.println("Some or All of required files i.e. Raw directory / build.xml / Control.vm  does not exists in directory\n" + inputdir );
		}
		if(bexists4)
		{
			System.out.println("Language directories are not created.\nPlease run 'ant control' command before running '$java Control Remove' command" );
		}
	        System.exit(-1);
	}
	/**
	* Creating / removing temporary files 
	*/
	Controltemporary.temporary(arg[0],TemplatePath,PropertyPath);
	/**
	* Inserting Removing values inside *.vm files
	*/
	try
	{
		if(arg[0].equalsIgnoreCase("insert"))
		{
			System.out.println("Starting to INSERT $ after # , ! and checking velocityCount variable in the Raw directory in Templates\n This is done  as Generator.Parse method parse the directives ");
		}
		else
		{
			System.out.println("Starting to REMOVE $ after # , ! and checking velocityCount variable .\n This will remove all $ after # ! in directories Raw, hindi ,english, urdu ");
		}
		File farr1 [] =new File [] {fRaw } ;
		if(arg[0].equalsIgnoreCase("remove"))
		{
			/**
			* Add new language in File array farr1
			* farr1  = new File []{fRaw , fHin , fEng , fUrdu } ;
			* Below we have to implement above statement 
			**/
			File ftmp[]=new File[LangFilearr.length + 1]; 
			System.arraycopy(farr1,0,ftmp,0,farr1.length);
			System.arraycopy(LangFilearr,0,ftmp,farr1.length,LangFilearr.length);
			farr1=ftmp;
			ftmp=null;
		}
		File farr2 [] =farr1 ;
		File farr [] =farr2 ;
		for(int i=0;i< farr2.length ;i++)
		{//for(001)
			/**
			* Uncomment to debug
			* System.out.println("farr2[i]"+farr2[i]);
			*/
			/**
			* If there are no language directories dont run java Control remove on them 
			* eg if urdu directory is not there dont try to run remove on it.
			*/
			if(!farr2[i].exists())
			continue;
         		/**
                        * If DevelopingPath is not null or "" then traverse through the path
                        */
                        File DvpPath =null;
                        boolean bdpath = DevelopingPath != null;
                        if(bdpath)
                        {
                                if(!(DevelopingPath.equals("")))
                                {
                                        DvpPath =new File(farr2[i]+"/"+ DevelopingPath);
                                        if(DvpPath.exists())
                                        	farr = DvpPath.listFiles();
                                        else
                                        {
                                                /**
                                                * Control.property will be needed for build.xml and so has to be kept in Propertypath
                                                */
                                                System.out.println("Writing on Control.properties");
                                                File fCtrl   = new File(PropertyPath+"/Control.properties");
                                                FileWriter fw = new FileWriter(fCtrl,true);
                                                fw.write("ifDevelop = true\n");
                                                fw.close();
                                                System.out.println("Written on  Control.properties");
                                                throw new Exception("Directory not Found ========> "+ DevelopingPath );
                                        }
                                }//if
                                else bdpath =false;
                        }//if
                        if(!bdpath)
                        farr  = farr2[i].listFiles();     
			for(int j=0;j< farr.length ;j++)
			{
				if(farr[j].getName().equals("CVS"))
                                continue;   
				/**
				* uncomment to debug
				* System.out.println("farr[j]"+farr[j]);
				*/
				if(farr[j].isDirectory())
				{ //(1)
					farr1 =farr[j].listFiles();
					for(int k=0;k< farr1.length ;k++)
					{//for
						/**
						* Uncomment to debug
						* System.out.println("farr1[k]"+farr1[k]);
						*/
						if(farr1[k].isFile())
						{
							if(arg[0].equalsIgnoreCase("insert"))
								Controlinsert.insert(farr1[k]);
							else
								Controlremove.remove(farr1[k]);
						}
					}//for 
				}//if ends(1)
				else
				{
					if(arg[0].equalsIgnoreCase("insert"))
                   				Controlinsert.insert(farr[j]);
                                        else
                                        	Controlremove.remove(farr[j]);
				}
			}//for
		}//for(001)
		System.out.println(arg[0]+ " operation succesfully completed");
		 /**
                 * Release memory
                 */
		farr= null;
		farr1=null;
		farr2=null;
                fRaw = null ;
                LangFilearr = null ;
                fvm = null;
                fbuild =null;
		Langarr = null;
        	fCall = null;
	}//try
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("CONTROL FAILED");	
		System.exit(-1);
	}
	}//parsedir

}//class Control Ends
