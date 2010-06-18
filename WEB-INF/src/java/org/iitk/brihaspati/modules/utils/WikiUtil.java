package org.iitk.brihaspati.modules.utils;

/*
 * @(#) WikiUtil.java 
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

import java.util.Arrays; 
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 */

public class WikiUtil
{


/**
* here work to find out Operating System of server
* Wiki support only linux and windows; if your operating system
* is not linux it is assumed to be windows
* @return boolean

*/
public  boolean isLinux()
        {
                String str = System.getProperty("os.name");
                if(str.equalsIgnoreCase("Linux"))
                return true;
		else
		return false ;
        } // end isLinux
/**
* this is versioning in Wikihistory
* we have to keep history version last 20
* so as we soon as we see last version in list is ( >= 20)
* we remove (last version - 19)th version from rcs *,v file
* Input is Wikihistory Directory and file name 
* Output is it deletes version (lastversion -19)in wikihistory folder
* @param filePathh String
* @param fName String
* @return String
*/
public String keep20(String filePathh,String fName)
{
	try
	{
	 	Runtime r=Runtime.getRuntime();
         	Process p=null;
           	String fpath = filePathh +"/"+ fName ;
           	String fpath1 = filePathh +"/RCS/"+ fName ;
           	int hv;
           	if(isLinux())
           	{
                	hv=Integer.parseInt(getfileVersion(fpath+",v"));
           	}
           	else
           	{
                 	 hv=Integer.parseInt(getfileVersion(fpath1));
           	}
           	/**
           	* Check if last version of wikihistory is greater
	        * than 19 or not
           	*/
           	if( hv > 19)
           	{
            	/**
            	* Below command deletes oldest existing version in wikihistory folder
            	*/
             	 String sc1 ="rcs -o1."+(hv - 19)+" " + fName;
                 p = r.exec(sc1, null, new File(filePathh));
                 p.waitFor();
		 return "done";

            	}//if
		else
		return "not under 19";
	}//try
	catch(Exception e)
	{
		return "Error in keep20 util" + e;
	}

}//end keep20



/**
* finds out if merge is done first time or next time
* searches for given Page (*,m) in Wikilast directory
* true==merge next (found *,m)
* false== merge first (not found *,m)
* @param filePath String
* @param word String
* @return String
*/
public String getMerge(String word ,String filePath)
	{
                try{
                        

              		Vector svr=new Vector();
                	File f1=new File(filePath);
                	String Str[]=f1.list();
			if(!(Str == null))
			{  //if starts
				

                		Arrays.sort(Str);//sorts the files
				/**
			        *this for loop searches word in all files in wikilast
				* and stores files in a String
				* for all files
				*/
                		for(int i=0;i<Str.length;i++)
				{
  					if(Str[i].equals(word))
						{
							svr.add(Str[i]);
						}	
        			    
				}//for loop end
				f1=null;
		
				if(svr.isEmpty())
				{
					return "false";
				}//if
				else
				{
					/**
                                	* release memory
                                	*/
                                	svr=null;
					return "true";
				}//else ends

			} //if ends
			else
			{
				return "There are no pages please create few files first";        
			}
                }//end of try 
                catch(Exception e)
                {
			  return "Error in getMerge() Util Page" + e;

		}
}// getMerge ends

/**
* Difference between linux and window rcs is 
* linux creates *,v file in current directory but Windows creates file with same name in RCS directory
* this function has aim to remove this linux window difference by copying RCS file to *,v file 
* this takes current directory and filename as input
* @param currentdir String
* @param filename String
* @return String
*/
 public  String  doWindow(String  currentdir,String filename )
        {
                try{
			FileInputStream fin= new FileInputStream(currentdir +"/RCS/" + filename);
                        FileOutputStream fout = new FileOutputStream(currentdir +"/"+ filename +",v");
                        int i;
                        do{
                           	i = fin.read();
                                if(i != -1)
                                {
                                       fout.write(i);
                                }
                          } while(i != -1);
                         fin.close();
                         fout.close();
			 /**
                         * release memory
                         */
                         fin=null;
                         fout=null;
			 return "window done";

                   }
                catch(Exception e)
                        {
                        return "error in doWindow " + e.toString();
                        }
        } //  doWindow ends





/**
* this gets latest version of Page through Wikilog
* when you pass filename and wikilog directory
* searches version in wikilog file
* @param filepath1 String
* @return String
*/
 public  String getVersion(String  filepath1)
        {	
		try{
				
			FileReader fr= new FileReader(filepath1);
                        
			BufferedReader br=new BufferedReader(fr);
                        String s2="";
                        String vIda="";
			while ((s2=br.readLine())!= null)
				{
                                    vIda=s2;
                                }
                	int start = vIda.indexOf("1.") + 2;
                	int end = vIda.indexOf("\t",start);
                	vIda = vIda.substring(start,end);
			 /**
                         * release memory
                         */
			 br.close();
			 fr.close();
                         br=null;
                         fr=null;
			 return vIda;
		   }
		catch(Exception e)
			{
			return "error in getVersion() " + e.toString();
			}
        } //  getversion ends

/**
* this tells whether file has been disabled for editing
* basiaclly you search for * in last line
* when you pass filename and wikilog directory
* returns true for * found i.e, for Edit Disabled 
* @param filepath1 String
* @return boolean
*/
 public  boolean checktraffic(String  filepath1)
        {
                try{
			
			boolean bool ;
                        FileReader fr= new FileReader(filepath1);
                        BufferedReader br=new BufferedReader(fr);
                        String s2="";
                        String vIda="";
                        while ((s2=br.readLine())!= null)
                                {
                                    vIda=s2;
                                }
			/**
                        * release memory
                        */
			br.close();
			fr.close();
                        br=null;
                        fr=null;
			if(vIda.equals("*"))
                        bool=true;
                        else
                        bool=false;
			return bool;
                   }
                catch(Exception e)
                        {
                        return(false);
                        }
        } //  checktraffic ends

/** 
* this tells whether merged file has confilcts or not
* basiaclly you search for >>>>> in last line
* when you pass filename and wikitemp directory
* returns true for >>>>>> found i.e, for Conflict 
* @param filepath1 String
* @return boolean
*/
 public  boolean checkconflict(String  filepath1)
        {
                try{

                        FileReader fr= new FileReader(filepath1);
                        BufferedReader br=new BufferedReader(fr);
			String s1="";
			String word=">>>>>>";
			String word1="<<<<<<";
			String word2="======";

                         String s2=null;
                         /**
                         * Read the contents of a page
                         * Put the content in String seperate each
                         * line by space
                         */
                         while((s2=br.readLine())!=null)
                         {
                                s1 = s1.concat(s2).concat("");
                         }
			 br.close();
                         fr.close();
			 /**
			 * release memory
			 */
			 fr=null;
			 br=null;
                         /**
                         * Code below uses regular expression
                         * to search for word
                         * if match is found return true else false
                         */
                         Pattern pat =  Pattern.compile(word) ;
                         Matcher mat = pat.matcher(s1);
			 Pattern pat1 =  Pattern.compile(word1) ;
                         Matcher mat1 = pat1.matcher(s1);
			 Pattern pat2 =  Pattern.compile(word2) ;
                         Matcher mat2 = pat2.matcher(s1);

                        if(mat.find()||mat1.find()||mat2.find())
			{
				/**
				* release memory
				*/
				pat=null;
				pat1=null;
				pat2=null;
				mat=null;
				mat1=null;
				mat2=null;
                        	return(true);
			}//if
			else
			{
			 	/**
                        	* release memory
                        	*/
                        	pat=null;
                        	pat1=null;
                        	pat=null;
                        	mat=null;
                        	mat1=null;
                        	mat2=null;
				return(false);
			}//else
                   }//try
                catch(Exception e)
                        {
                        return(false);
                        }
        } //  checkconflict ends

/**
* this gets latest version of Page through Wikilast/history
* when you pass filename and wikilast/history directory
* for linux pass *,v file
* for windows pass RCS file
* @param filepath1 String
* @return String
*/
 public  String getfileVersion(String  filepath1)
        {
                try{

                        FileReader fr= new FileReader(filepath1);
                        BufferedReader br=new BufferedReader(fr);
                        String vId=br.readLine();
                        int start = vId.indexOf("1.") + 2;
                        int end = vId.indexOf(";",start);
                        vId = vId.substring(start,end);
			/**
                        * release memory
                        */
			br.close();
			fr.close();
                        br=null;
                        fr=null;
                        return vId;
                   }
                catch(Exception e)
                        {
                        return "error in getfileVersion() " + e.toString();
                        }
        } //  getfileversion ends



/**
* this gets author of Page when you pass filename and Wikilog directory path 
* @param filePath String
* @return String
*/
 public  String getAuthor(String filePath )
        {
                try{
			
			FileReader fr= new FileReader(filePath);
                	BufferedReader br=new BufferedReader(fr);
			String s2=null;
                	s2=br.readLine();
        		int start = s2.indexOf("author") + 7;
                        int end = s2.indexOf("\t",start);
                        s2 = s2.substring(start,end);
			/**
                        * release memory
                        */
			br.close();
			fr.close();
                        br=null;
                        fr=null;
			return s2;

                   }
                catch(Exception e)
                        {
                        return "error in getAuthor() " + e.toString();
                        }
        } //  getAuthor ends



/**
* this gets shows page  when you pass directory path,filename and version 
* NOTE: method don't  check for integer value of version
* NOTE: method don't  return  value of version
* @param filePathlast String
* @param fName String
* @param vId String
* @return Vector
*/
 public  Vector show(String filePathlast ,String fName ,String vId )
        {

        	try{  //try starts

				/**
				* Below command is for printing the contents of given version
				* on working file  of wikilast
				*/
                                String s ="co -f  -r1."+ vId + " " + fName ;
				Process p=null;
       				Runtime r=Runtime.getRuntime();
        			p = r.exec(s, null, new File(filePathlast));
				p.waitFor();
				/**
				* Now read the contents of wikilast file
				*/
				Vector v =new Vector();
				FileReader fr= new FileReader(filePathlast + "/" + fName);
                                BufferedReader br=new BufferedReader(fr);
                               	String s2=null;
                                /**
                                * Here we are reading contents of Wikilast file
                                * line wise ,storing in vector and sending to vm
                               */
                               while((s2=br.readLine())!=null)
                               {
                                     v.add(s2);
                               }
			       br.close();
                               fr.close();
				/**
				* Now delete the wikilast file as that file is write protected
				*/
			
			       File fo=new File(filePathlast + "/"+ fName);
                               fo.delete();
			       /**
			       * Now create a new blank wikilast file 
			       */	

			       FileWriter fre1 = new FileWriter(filePathlast + "/" + fName);
                               fre1.write("");
                               fre1.close();
			       /**
                               * release memory
                               */
                                fo=null;
				fr=null;
				br = null;
                                fre1=null;
				return v ;
                   }//try ends
                        catch(Exception e)
                   { //catch start
				Vector v = new Vector();
				v.add("error in show of WikiUtil"+e);
				return v;
                   }//catch ends

        } //  show ends

}//class ends

