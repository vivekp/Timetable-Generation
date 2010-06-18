package org.iitk.brihaspati.modules.actions;

/*
 *  @(#)Wikieditaction.java
 
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur.
 *  All Rights Reserved.

 *  Redistribution and use in source and binary forms, with or
 *  without modification, are permitted provided that the following
 *  conditions are met:
 
 *  Redistributions of source code must retain the above copyright
 *  notice, this  list of conditions and the following disclaimer.
 
 *  Redistribution in binary form must reproducuce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 
 
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
import java.util.Date;
import java.util.Vector;
import java.util.Arrays;
import java.lang.Integer;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//Turbine
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.WikiUtil;
import org.apache.turbine.util.parser.ParameterParser;    
//Brihaspati
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/** 
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 */


public class Wikieditaction extends SecureAction_User{
 
	/**
	 * Place all the data object in the context for use in the template.
	 * @param data RunData instance
	 * @param context Context instance
	 * @exception Exception, a generic exception
	 * @return nothing
	 */

	WikiUtil ol = new WikiUtil();
	MultilingualUtil m_u=new MultilingualUtil();
	char Buffer[];
	Vector v=new Vector();
	FileReader fr;
        BufferedReader br;

/**
 * doEdit does the versioning in RCS and increments version in Wikilog
 * @param data RunData
 * @param context Context
 */
public  synchronized void  doEdit(RunData data,Context context){
	
	String file,Page,not,Edited,Wikiaction1,Wikiaction2,Wikiaction3,usr_prof1;
	file=Page=not=Edited=Wikiaction1=Wikiaction2=Wikiaction3=usr_prof1=null;
	try{
		usr_prof1=m_u.ConvertedString("usr_prof1",file);
		file=data.getUser().getTemp("LangFile").toString();
        	Page=m_u.ConvertedString("WikiPage",file);
        	not=m_u.ConvertedString("Wikinot",file);
        	Edited=m_u.ConvertedString("WikiEdited",file);
        	Wikiaction1=m_u.ConvertedString("Wikiaction1",file);
        	Wikiaction2=m_u.ConvertedString("Wikiaction2",file);
        	Wikiaction3=m_u.ConvertedString("Wikiaction3",file);
	}
	catch(Exception e1)
	{
		data.setMessage("Language Exception in action Wikieditaction method doEdit is \n"+e1);
	}

	try{
		Date df;
		FileWriter f1;	
		FileWriter f4;	
		User user=data.getUser();
        	String cId=(String)user.getTemp("course_id");
		Runtime r=Runtime.getRuntime();
        	Process p=null;
		ParameterParser pp = data.getParameters();
		String word=pp.getString("text","");
//		String word1=pp.getString("query","");
		/**
                  * Check for special characters
                  */
                         String word1=StringUtil.replaceXmlSpecialCharacters(pp.getString("query",""));

		String search=pp.getString("searchtype","");
        
		Buffer= new char[word.length()];

		/**
		 * converts String to Character array and store in buffer
		 * this is done as   f1.write(Buffer) uses character array
		 */

		word.getChars(0,word.length(),Buffer,0);
		String fId=pp.getString("fname","");
		String fIc=pp.getString("createname","");
		/**
                * Check for special characters
                */
                if(StringUtil.checkString(fIc) != -1)
                {
                        data.addMessage(usr_prof1);
                        return;
                }

			
		String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/") ;
		/**
                * check if there is illegal symbol >>>>>>> in edited file
                */
		if(!((word.contains(">>>>>>>"))||(word.contains("<<<<<<<"))||(word.contains("======"))))
                {//if illegal

		 	/**
                        * check if file is disabled for editing
                        */
			String filePathlog=filePath +"/Wikilog/"+fId;
			if(!(ol.checktraffic(filePathlog)))
			{// check starts if
			 	/**
                         	* check if request is create or edit
                         	*/

				if((fIc==null)||(fIc==""))
				{ // edit starts
			
					String filePathlast1=filePath + "/Wikilast/" ;
					String filePathlast=filePathlast1 + fId;
					/**
					* Write newly edited page to Wikilast directory
					*/
					f1 = new FileWriter(filePathlast);
					f1.write(Buffer);
					f1.close();
					/**
					* get old version of page
					*/
					int oldversion = Integer.parseInt(ol.getVersion(filePathlog));
					/**
					* Checkin the new page written in Wikilast directory 
					*/
					String s1 ="ci -q -mx -l -t-none "+ fId ;
                                	p = r.exec(s1, null, new File(filePathlast1));
					p.waitFor();
                                	data.setMessage("["+fId+"]"+Page+ " " + Edited );
                       			String username=user.getName();
                        		String firstname=user.getFirstName();
                        		String lastname=user.getLastName();
					f4 = new FileWriter(filePathlog,true);
					String d;
                        		df = new Date();
                        		if((firstname==null)|(lastname==null))
                         		d = username;
                        		else
                        		d = firstname + lastname ;
					/**
                         		* below code first checks the version difference
					* of RCS and then  changes  wikilog version
                         		*/
				
                                	int newversion = Integer.parseInt(ol.getfileVersion(filePathlast+",v"));	
					if(newversion > oldversion)
					{
                                		f4.write("\n");
                                		f4.write("version 1."+newversion +"\t\t"+"author "+d +"\t\t"+ df +"\n");
					}//if

					else
					{
						data.setMessage(Wikiaction1);
					}
					f4.close();
				  	/**
		                         * check if edit has been done through search
                		         * if yes than print the previous search result in Wiki.vm
					 * Why have we called for another action after the function????
					 * This is because if we call it at start of current if block
					 * ol.checktraffic(filePathlog) gets value null and throws
					 * null pointer exception.Reason for this is not known
					 * So keep another action call at last otherwise util won't work
                          		*/

                        		if(!(word1.equals("")))
                        		{
						if(search.equals("pattern"))
                                			doFind(data,context);
						else
							doFindPage(data,context);

                        		}

					/**
					* release memory
					*/
					f1=null;
					f4=null;
					df=null;
				}//edit ends (if ends)
			}//check ends (if ends)
			else
			{
				data.setMessage("["+fId +"]"+" " +Page + " "+ not+" "+ Edited + " " +Wikiaction2);
			}
		}//if ends
		 else
                 {
                        data.setMessage("["+fId+"]"+" "+Page +" " + not +" "+Edited+" " + Wikiaction3);
                 }
	}//try ends
	catch(Exception e)
	{
		data.setMessage("Error in doEdit of action Wikieditaction ==========> " + e);
	}
	finally
	{
		releasememory();
	
	}//finally

}   //doEdit ends



/**
* doCreate does the versioning in RCS and 
* creates files in Wikilog,Wikifirst Wikilast Wikihistory
* @param data RunData
* @param context Context
*/
 public void doCreate( RunData data, Context context ) 
{

			
	String file,Page,not,Edited,Wikiaction5,Wikiaction6,Wikiaction7,Wikiaction8,Wikiaction3,usr_prof1;
        file=Page=not=Edited=Wikiaction5=Wikiaction6=Wikiaction7=Wikiaction8=Wikiaction3=usr_prof1=null;
        try{
		usr_prof1=m_u.ConvertedString("usr_prof1",file);
                file=data.getUser().getTemp("LangFile").toString();
                Page=m_u.ConvertedString("WikiPage",file);
                not=m_u.ConvertedString("Wikinot",file);
                Edited=m_u.ConvertedString("WikiEdited",file);
                Wikiaction5=m_u.ConvertedString("Wikiaction5",file);
                Wikiaction6=m_u.ConvertedString("Wikiaction6",file);
                Wikiaction7=m_u.ConvertedString("Wikiaction7",file);
                Wikiaction8=m_u.ConvertedString("Wikiaction8",file);
                Wikiaction3=m_u.ConvertedString("Wikiaction3",file);
        }
        catch(Exception e1)
        {
                data.setMessage("Language Exception in action Wikieditaction method doCreate is \n"+e1);
        }

                                
	try{			
		
		User user=data.getUser();
        	String cId=(String)user.getTemp("course_id");
		Runtime rc=Runtime.getRuntime();
                Process pc=null;
		String username=user.getName();
                String firstname=user.getFirstName();
                String lastname=user.getLastName();
                ParameterParser pp = data.getParameters();
                String word=pp.getString("text","");
                /**
		* Convert the string to character array
		* for use of write() method of Filewriter class
		*/
                Buffer= new char[word.length()];
                word.getChars(0,word.length(),Buffer,0);
                String fIdc=pp.getString("createname","");
		/**
                * Check for special characters
                */
                if(StringUtil.checkString(fIdc) != -1)
                {
                        data.addMessage(usr_prof1);
                        return;
                }

                String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
                String filePathf=filePath + "/Wikifirst/"+  fIdc ;
                String filePathh=filePath + "/Wikihistory/";
                String filePathh1=filePathh + fIdc;
                String filePathlog=filePath + "/Wikilog/"+  fIdc;
                String filePathlast=filePath + "/Wikilast/" ;
                String filePathlast1=filePathlast + fIdc;
		 /**
                * check if there is illegal symbol >>>>>>> in edited file
                */
		if(!((word.contains(">>>>>>>"))||(word.contains("<<<<<<<"))||(word.contains("======"))))
                {//if illegal
			/**
	        	* check if filename has reserved characters
                	*/			

                	if(!((fIdc.endsWith(",v"))||(fIdc.endsWith(",m"))||(fIdc.equalsIgnoreCase("brihaspati"))))
                	{//if starts
				File f2 = new File(filePathf);
			
                		/**
                		* check if filename has space
               	 		*/
				if(!fIdc.contains(" "))
				{//if starts
					/**
                        		* checks if file exists
                        		*/
					if(!f2.exists())
					{ //if starts
						/**
						* Write to Wikifirst directory
						*/
                        			FileWriter f1 = new FileWriter(f2);
                        			f1.write(Buffer);
                        			f1.close();
						/**
						* Write to Wikihistory directory
						*/
						FileWriter fh = new FileWriter(filePathh1);
                                		fh.write(Buffer);
                                		fh.close();
						/**
						* Write to Wikilast directory
						*/
						FileWriter f3 = new FileWriter(filePathlast1);
                        			f3.write(Buffer);
                        			f3.close();
						String d;
						/**
						* Write the log in Wikilog directory
						* Write version number,author and date
						*/
						FileWriter f4 = new FileWriter(filePathlog,true);
						Date df = new Date();
						/**
						* Check if first name and last name
						* of author is null or not
						* if null write login name
						* else write full name
						*/
						if((firstname==null)|(lastname==null))
			 			d = username;
						else
						d = firstname + lastname ;
						f4.write("version 1.1"+"\t\t"+"author "+ d +"\t\t"+ df +"\n" );
						f4.close();
						/**
						* Checkin the created page in 
						* Wikilast folder
						*/

						String sc ="ci -q -mx -l -t-none" +" "+ fIdc ;
                        			pc = rc.exec(sc, null, new File(filePathlast));
						pc.waitFor();
						/**
						* Checkin the created page in 
						* Wikihistory folder
						*/
						pc = rc.exec(sc, null, new File(filePathh));
                                		pc.waitFor();

                       				data.setMessage("["+fIdc+"]"+" "+Page+" "+Wikiaction5);
						/**
						* Release memory
						*/
						df=null;
						f1=null;
						f2=null;
						f3=null;
						fh=null;
						f4=null;
					}//if ends
	
					else
					{
						data.setMessage("["+fIdc+"]"+" "+Page+ " "  + Wikiaction6);
					}
		
				}//if ends
				else
                        	{
                                	data.setMessage(Wikiaction7);
                        	}
			}//if ends
			else
                        {
                                context.put("pad",word);
                                data.setMessage(Wikiaction8);
                        }
		}//if
		 else
                 {
			context.put("pad",word);
			context.put("createname",fIdc);
                        data.setMessage( "["+fIdc+"]"+" "+Page+" "+ not+" " +Edited+" "  +Wikiaction3);
                 }
           }//try ends
                catch(Exception e)
                {
			data.setMessage("Error in doCreate of action Wikieditaction ==========> " + e);
		}
		finally
        	{
                	releasememory();

        	}//finally

} //function  doCreate ends

/**
* shows content of Wikifirst/last/log file 
* @param data RunData
* @param context Context
*/

public void doShoworiginal(RunData data,Context context)
{  //do show starts
		String file,Wikibutton15,Wikibutton13,Wikibutton5;
		file=Wikibutton15=Wikibutton13=Wikibutton5=null;
        	try{
                	file=data.getUser().getTemp("LangFile").toString();
                	Wikibutton15=m_u.ConvertedString("Wikibutton15",file);
                	Wikibutton13=m_u.ConvertedString("Wikibutton13",file);
                	Wikibutton5=m_u.ConvertedString("Wikibutton5",file);
        	}
        	catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action Wikieditaction method doShowOriginal is \n"+e1);
        	}

		
                try{  //try starts
				
			  User user=data.getUser();
        		  String cId=(String)user.getTemp("course_id");
  			  ParameterParser pp = data.getParameters();
			  String choose=pp.getString("eventSubmit_doShoworiginal");
                          String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
			  String filePathf=null;
			  /**
			  * choose if you want to see Wikifirst(original) or Wikilast(new merged file) or Wikilog
			  */
			  if((choose==null)||(choose.equals(Wikibutton15))) //Wikibutton15=Show Log
                          	filePathf=filePath + "/Wikilog/";
			  else if(choose.equals(Wikibutton13)) //Wikibutton13=Show Conflict Merged
                          	filePathf=filePath + "/Wikilast/" ;
			  else if(choose.equals(Wikibutton5))  //Wikibutton5=Show Original
                                filePathf=filePath + "/Wikifirst/" ;

                          String fIda=pp.getString("fname","");
                          String vIda=pp.getString("vname","");
			  String fpath = filePathf +"/"+ fIda ;

			  fr= new FileReader(fpath);

                       	  br=new BufferedReader(fr);

                          String s2=null;
			  /**
			  * Here we are reading contents of Wikifirst file
			  * line wise ,storing in vector and sending to vm
			  */
                          while((s2=br.readLine())!=null)
                          {
                                v.add(s2);
                          }
			  br.close();	
                          fr.close();
			  
                          context.put("fname",fIda);
                          context.put("first",v);
                          context.put("vname",vIda);
     
                   }//try ends
                        catch(Exception e)
                   { //catch starts
		   	  data.setMessage("Error in doShowOriginal of action Wikieditaction ==========> " + e);

                   }//catch ends
		   finally
                   {
                        releasememory();

                  }//finally


}       //doShoworiginal ends




/**
* shows content of Wikihistory file with RCS command ,
* it hides first two lines asthey are not content of file
* @param data RunData
* @param context Context
*/
public void doShowhistory(RunData data,Context context)
{  //do showhistory starts
		
		String file,Wikiaction10,Wikiaction11,Wikibutton1;
        	file=Wikiaction10=Wikiaction11=Wikibutton1=null;
        	try{
                	file=data.getUser().getTemp("LangFile").toString();
                	Wikiaction10=m_u.ConvertedString("Wikiaction10",file);
                	Wikiaction11=m_u.ConvertedString("Wikiaction11",file);
                	Wikibutton1=m_u.ConvertedString("Wikibutton1",file);
        	}
        	catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action Wikieditaction method doShowHistory is \n"+e1);
        	}

                try{  //try starts
				
			  User user=data.getUser();
        		  String cId=(String)user.getTemp("course_id");
  			  ParameterParser pp = data.getParameters();
			  String choose=pp.getString("eventSubmit_doShowhistory");
                          String filePathh=null;
                          String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/" );
                          /**
                          * Choose if you want to see changes in Wikilast or Wikihistory folder
                          */
                          if(choose.equals(Wikibutton1)) //Wikibutton1=Show Page
                                filePathh= filePath +"/Wikilast/" ;
                          else
                                filePathh= filePath + "/Wikihistory/" ;

                          String fIda=pp.getString("fname","");
                          String vIda=pp.getString("vname","");
                          int m=pp.getInt("vname",-1);
			  String fpath = filePathh +"/"+ fIda ;
			  /**
                          * check if version number is integer 
			  * and non zero and positive 
		          */
			  
                          if(vIda.equals("")||(m >0))
                          { // if starts

				 /**
                          	* checks if input version is avilable or not
                          	*/

				String last=ol.getfileVersion(fpath+",v");
				int historyversion = Integer.parseInt(last);
				int myversion = 0;
				/**
				* Here myversion variable is made to check if 
				* last wikihistory version minus 19 is less than 
				* 1 or not if yes correct it to 1 as
				* there is no negative or zero version
				* note that we keep only last 20 versions
				*/
				if(historyversion - 19 < 1)
				myversion = 1;
				else
				myversion = historyversion - 19;				
	         		/**
				* Check if version name is empty or within range for wikihistory only
				* for wikilast no checking needed
				*/
				boolean bool1=true;
				if(!(choose.equals(Wikibutton1)))  //Wikibutton1=Show Page
				bool1=((vIda.equals(""))||(!((m > historyversion)||(m < myversion))));

				if(bool1)
				{

					/**
                                	* send the file name back to vm
                                	*/
                                	context.put("fname",fIda);

                                	/**
                                	* call show of WikiUtil
                                	*/
                                	v = ol.show(filePathh,fIda,vIda);

                                	/**
                                	*  code below finds out version number and sends to vm
                                	*/
                                	if(vIda.equals("")||m > historyversion)
                                        vIda = last;
                                	/**
                                	* Send the page contents and version number to vm
                                	*/
                                	context.put("first",v);
                                	context.put("vname",vIda);


				}//if ends
				 else
                                 {
                                        context.put("fname",fIda);
                                        context.put("vname",vIda);
                                        data.setMessage(Wikiaction10+"  ["+ myversion + ", " + historyversion+"]");
                                 }
                            }// if ends
                            else
                              {
                                        context.put("fname",fIda);
                                        context.put("vname",vIda);
					data.setMessage(Wikiaction11);
                                }
     
                   }//try ends
                        catch(Exception e)
                   { //catch starts
			  data.setMessage("Error in doShowHistory of action Wikieditaction ==========> " + e);


                   }//catch ends
		   finally
                   {
                        releasememory();

                   }//finally



}       //doShowhistory ends


/**
* work is to show all version differences of given file 
* @param data RunData
* @param context Context
*/
public void doDiff(RunData data,Context context)
{  //do Diff starts

		String file,Wikibutton3;
                file=Wikibutton3=null;
                try{
                        file=data.getUser().getTemp("LangFile").toString();
                        Wikibutton3=m_u.ConvertedString("Wikibutton3",file);
                }
                catch(Exception e1)
                {
                        data.setMessage("Language Exception in action Wikieditaction method doShowDiff is \n"+e1);
                }


                try{  //try starts
				
			  User user=data.getUser();
        		  String cId=(String)user.getTemp("course_id");
			  ParameterParser pp = data.getParameters();
			  String choose=pp.getString("eventSubmit_doDiff");
			  String filePathlast=null;	
                          String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/" );
			  /**
			  * Choose if you want to see changes in Wikilast or Wikihistory folder
			  */
			  if(choose.equals(Wikibutton3))  //Wikibutton3=Show Changes
                          	 filePathlast= filePath +"/Wikilast/" ;
			  else
			  	filePathlast= filePath + "/Wikihistory/" ;
                          String fIda=pp.getString("fname","");
                          String vIda=pp.getString("vname","");
                          String fpath = filePathlast +"/"+ fIda ;
			  fr= new FileReader(fpath+",v");	
			  br =new BufferedReader(fr);
			  String s=null;
			  /** 
			  * Here we read the contents of 
			  * Wikilast RCS file that is *,v file
			  * linewise and break if found @none
			  */
			  while((s=br.readLine())!=null)
			  {
				if(s.contains("@none"))
                                break;
			  } 
			  /**
			  * Here we read next lines after delimliter @none 
			  */
			  while((s=br.readLine())!=null)
                          {
                                v.add(s);
                          }
				br.close();
				fr.close();
			  /**
			  * Send the contents of page ,file name,version name back to vm 
		          */
			  context.put("first",v);	
			  context.put("fname",fIda);
                          context.put("vname",vIda);	
                   }//try ends
                        catch(Exception e)
                   { //catch starts
			   data.setMessage("Error in doDiff of action Wikieditaction ==========> " + e);


                   }//catch ends
		     finally
                   {
                        releasememory();

                  }//finally


}       //doDiff ends


/**
* this actually merges specific version to original file
* work here is to copy original/merged version to working file
* note funda here is merge first time with original file than
* with previously merged file
* we are using wikilast file for storing temporary merged files
* so "Stop People from Editing given Page" by using "doStoptraffic" action
* we can merge any number of times  
* @param data RunData
* @param context Context
*/
public void doMergenow(RunData data,Context context)
{  //domergenow starts
	
	String file,Wikiaction15,Wikiaction16,Wikiaction17,Wikiaction18,Wikiaction14;
        file=Wikiaction15=Wikiaction16=Wikiaction17=Wikiaction18=Wikiaction14=null;
        try{
                file=data.getUser().getTemp("LangFile").toString();
                Wikiaction15=m_u.ConvertedString("Wikiaction15",file);
                Wikiaction16=m_u.ConvertedString("Wikiaction16",file);
                Wikiaction17=m_u.ConvertedString("Wikiaction17",file);
                Wikiaction18=m_u.ConvertedString("Wikiaction18",file);
                Wikiaction14=m_u.ConvertedString("Wikiaction14",file);
        }
        catch(Exception e1)
        {
                data.setMessage("Language Exception in action Wikieditaction method doMergenow is \n"+e1);
        }

        try{  //try starts


		User user=data.getUser();
        	String cId=(String)user.getTemp("course_id");
		ParameterParser pp = data.getParameters();
		Runtime r=Runtime.getRuntime();
                Process p=null;
                String word=pp.getString("text","");
                Buffer= new char[word.length()];
                word.getChars(0,word.length(),Buffer,0);
                String fId=pp.getString("fname","");
                int vId=pp.getInt("vname",-1);
		context.put("fname",fId);
		context.put("vname",vId);
             	String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
             	String filePathf=filePath + "/Wikifirst/";
             	String filePatht=filePath + "/Wikilast/";
		String filePath1t=filePatht + fId;
		String filePathl=filePath + "/Wikilog/" + fId;
		/**
                * check if there is conflict in Wikilast merged file
                */
		if(!(ol.checkconflict(filePath1t)))
                {//if conflict

			/**
			* here lastversion parameter is latest version of page before merge
			*/
                        int lastversion=Integer.parseInt(ol.getfileVersion(filePath1t + ",v"));
			

			/**
			*check if edit has been disabled or not
			*/

			if(ol.checktraffic(filePathl))
			{//if starts	
				
				/**
			        * check if version entered is integer and 
			        * above 1 and less than last version
			        */

				if((vId < (lastversion + 1))&&(vId > 1)&&(vId != -1 ))
			        {// if starts
					/**
					* Check if Merge is done first time or not
					* by seeing if *,m exists
					*/

                       		 	if(ol.getMerge(fId +",m" ,filePatht).equals("false"))
                                	{//if starts,case is mergefirst

						/**
				 		* we make *,m in wikilast only to make it act as mark that
						* tells if  merge is first time done or not
						*/

                           			FileWriter frw = new FileWriter(filePatht +"/"+fId+",m");
                                		frw.write("hello this is temporary file");
                                		frw.close();
						/**
						* release memory
						*/
						frw=null;
					
						/**
						* copy original(Wikifirst) file to Wikilast file
						*/
						
						int i;
					
                           			FileInputStream fin1= new FileInputStream(filePathf  +"/"+fId);
                           			FileOutputStream fout1 = new FileOutputStream(filePatht +"/"+fId);
                           			do{
                                			i = fin1.read();
                                			if(i != -1)
                                			{
                                        			fout1.write(i);
                                			}
                             		  	 } while(i != -1);
                               			fin1.close();
                               			fout1.close();
						/**
                                                * release memory
                                                */
                                                fin1=null;
                                                fout1=null;



                                	}//if merge first end

					{ //block merge next starts


						/**
						* command below merges the two file versions of same file
						* and stores output to String Buffer
						*/

						String s21="rcsmerge   -r1.1" +" "+"-r1."+vId+" "+fId;
						p = r.exec(s21, null, new File(filePatht));
						p.waitFor();
				
						fr= new FileReader(filePath1t);
                          			br=new BufferedReader(fr);
                          			String s2=null;
                          			/**
                          			* Here we are reading contents of Wikifirst file
                          			* line wise ,storing in vector and sending to vm
                          			*/
                          			while((s2=br.readLine())!=null)
                          			{
                                			v.add(s2);
                          			}
						br.close();
                          			fr.close();

						context.put("first",v);
				 		context.put("fname",fId);             
				 		context.put("vname",vId);             
				
				 		data.setMessage(Wikiaction14+" "+ fId+" "+ Wikiaction15);

			
					}// block mergenext ends

		   		}//if ends
		   		else
		   		{		
				 	context.put("pad",word);
			         	context.put("fname",fId);
                                 	context.put("vname",vId);
                                 	data.setMessage(Wikiaction16+" "+lastversion);		
		   		}
		  	}//if ends
		    	else
                   	{
                 		 context.put("pad",word);
                                 context.put("fname",fId);
                                 context.put("vname",vId);
                                 data.setMessage(Wikiaction17);
                   	}
	       }//if conflict ends
                else
               {
                    context.put("pad",word);
                    context.put("fname",fId);
                    context.put("vname",vId);
                    data.setMessage(Wikiaction18);
               }//else conflict ends

	 }//try ends
		   catch(Exception e)
		   { //catch starts

			  data.setMessage("Error in doMergenow of action Wikieditaction ==========> " + e);

		   }//catch ends
		   finally
                   {
                        releasememory();

                  }//finally


}	//doMergeNow ends	

/**
* We do Start Traffic and stop Traffic both 
* Start Traffic :::
* It disables the edit option of a page
* it is required when instructor is checking the changes
* in particular file and does not want others
* to add more versions until merge is done
* Basically here remove a "*" in wikilog file
*
* Stop Traffic :::
* It disables the edit option of a page
* it is required when instructor is checking the changes
* in particular file and does not want others 
* to add more versions until merge is done
* Basically here put a "*" in wikilog file
*
* Note I have merged the StartTraffic and Stoptraffic actions ,I have chosen Stop traffic action to merge with
* the Start traffic because here  String choose=pp.getString("eventSubmit_doStoptraffic"); is giving values
* but in Start traffic above expression gives  null .Reason is not found.
* @param data RunData
* @param context Context
*/
public void doStoptraffic(RunData data,Context context)
{  //doStopTraffic starts

		String file,Wikiaction20,Wikiaction21,Wikiaction22,Wikiaction23,Wikibutton8;
	        file=Wikiaction20=Wikiaction21=Wikiaction22=Wikiaction23=Wikibutton8=null;
        	try{
                	file=data.getUser().getTemp("LangFile").toString();
                	Wikiaction20=m_u.ConvertedString("Wikiaction20",file);
                	Wikiaction21=m_u.ConvertedString("Wikiaction21",file);
                	Wikiaction22=m_u.ConvertedString("Wikiaction22",file);
                	Wikiaction23=m_u.ConvertedString("Wikiaction23",file);
                	Wikibutton8=m_u.ConvertedString("Wikibutton8",file);
        	}
        	catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action Wikieditaction method doStoptraffic is \n"+e1);
        	}


                try{  //try starts
			User user=data.getUser();
        		String cId=(String)user.getTemp("course_id");
			ParameterParser pp = data.getParameters();
                        String word=pp.getString("text","");
                        String fId=pp.getString("fname","");
                        String vId=pp.getString("vname","");
             		String filePathl=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/"+ "/Wikilog/"+fId);
			String choose=pp.getString("eventSubmit_doStoptraffic");
			
			/**
                        * Put filename version number values back to vm
                        *
                        context.put("pad",word);
                        context.put("fname",fId);
                        context.put("vname",vId);
			*/

			/**
			* Check if page is already disabled or not
			*/
			boolean bool;
			bool=ol.checktraffic(filePathl);
			/**
			* Choose b/w Stop traffic/Start Traffic 
			*/
			if(choose.equals(Wikibutton8))   //Wikibutton8=Disable Edit
			{//stop traffic
                        	if(!bool)
                        	{// if ends
					/**
					* Here we just add * in corresponding Wikilog folder file 
					*/
		        		FileWriter f4 = new FileWriter(filePathl,true);
                        		f4.write("*");
                        		f4.close();
		        		data.setMessage(Wikiaction20+" " + fId);
						/**
						* release memory
						*/
					f4=null;
				}//if ends
				else
				{
                                	data.setMessage(Wikiaction21+ " " + fId);
				}
			}//if stop traffic
			else
	                {//Start Traffic
         	                       /**
                	                * Check if page is already Disabled or not
                        	        */
                                	if(bool)
                                	{// if ends
                                        	fr= new FileReader(filePathl);
                                        	br=new BufferedReader(fr);
                                        	StringBuffer s3=new StringBuffer();
                                        	String s2=null;
                                       /**
                                        * Read the wikilog file line wise
                                        * and add /n after each line
                                        * and  store in string buffer
                                        */
                                        while ((s2=br.readLine())!= null)
                                        {
                                                s3.append(s2).append("\n");
                                        }
                                        /**
                                        * Delete * and convert string buffer to string
                                        */
                                        s3=s3.deleteCharAt(s3.length()-1);
                                        String sv=s3.deleteCharAt(s3.length()-1).toString();
                                        /**
                                        * Write resulting String to Wikilog File
                                        */

                                        FileWriter f4 = new FileWriter(filePathl);
                                        f4.write(sv);
                                        f4.close();
                                        data.setMessage(Wikiaction22+" " + fId);
                                        /**
                                        * release memory
                                        */
                                        f4=null;
                                        s3=null;
                                }//if ends
                                else
                                {
                                        data.setMessage(Wikiaction23+" " + fId);
                                }
                          }// else starttraffic
			/**
			* show log to vm
			*/
			doShoworiginal(data,context);
				
		   }//try ends
			catch(Exception e)
		   { //catch starts
			  
			  data.setMessage("Error in doStopTraffic of action Wikieditaction ==========> " + e);

		   }//catch ends
		   finally
                   {
                         releasememory();

                   }//finally


}	//doStopTraffic ends	




/**
* content of  page is reflected to original file by this function
* work here is to copy updated version to wikifirst ,(original page)
* it do not do  versioning in Wikilog and RCS 
* @param data RunData
* @param context Context
*/
public void doRevert(RunData data,Context context)
{  //do Revert starts
	
        String file,Wikiaction25,Wikiaction26,Wikiaction27,Wikiaction3,Wikialert4;
        file=Wikiaction25=Wikiaction26=Wikiaction27=Wikiaction3=Wikialert4=null;
        try{
                file=data.getUser().getTemp("LangFile").toString();
                Wikiaction25=m_u.ConvertedString("Wikiaction25",file);
                Wikiaction26=m_u.ConvertedString("Wikiaction26",file);
                Wikiaction27=m_u.ConvertedString("Wikiaction27",file);
                Wikialert4=m_u.ConvertedString("Wikialert4",file);
        	Wikiaction3=m_u.ConvertedString("Wikiaction3",file);
        }
        catch(Exception e1)
        {
        	data.setMessage("Language Exception in action Wikieditaction method doRevert is \n"+e1);
	}

        try{  //try starts
		User user=data.getUser();
        	String cId=(String)user.getTemp("course_id");
		ParameterParser pp = data.getParameters();
		Runtime r=Runtime.getRuntime();
                Process p=null;
                String word=pp.getString("text","");
                Buffer= new char[word.length()];
                word.getChars(0,word.length(),Buffer,0);
                String fId=pp.getString("fname","");
                String vId=pp.getString("vname","");
		context.put("fname",fId);
		context.put("vname",vId);
		context.put("pad",word);
             	String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
		String choose=pp.getString("eventSubmit_doRevert");
                String filePathf=null;
		/**
		* check if blank is entered
		*/
		if(!(word.equals("")))
		{//if blank

		
                	/**
                	* choose if you want to save to Wikifirst(original) or Wikilast(new merged file)
			* you will get null value because input type is button
			* This button also does following thing 
			* conflict in  page after removal is saved to  wikilast file by this function
			* work here is to rewrite wikilast file ,(merged page)
			* it do not do  versioning in Wikilog and RCS
			*/

                	if(choose == null)
                		filePathf=filePath + "/Wikifirst/" + fId;
                	else
			{
                        	filePathf=filePath + "/Wikilast/" + fId;
				data.setMessage(Wikiaction25);
			}
                	String filePathh=filePath+ "/Wikihistory";

                	/**
                	* check if there is illegal symbol >>>>>>> in edited file
                	*/
                	if(!((word.contains(">>>>>>>"))||(word.contains("<<<<<<<"))||(word.contains("======"))))
                	{//if illegal


				/**
                        	* Read form data into String from vm and write
                        	* to corresponding file Wikifirst folder
                        	*/
                        	FileWriter f1 = new FileWriter(filePathf);
                        	f1.write(Buffer);
                        	f1.close();
		         	/**
                        	* release memory
                        	*/
                        	f1=null;
				/**
				* this part of code is executed only when you want to save original
				*/

				if(choose==null)
				{//if (for saving in wikifirst only)
                        		/**
                        		* Check if historyversion is under 20,
                        		* if yes delete oldest version
                        		*/
                        		ol.keep20(filePathh,fId);
                        		/**
                        		* Read form data into String from vm and write
                        		* to corresponding file Wikihistory folder
                        		* and do versioning
                        		*/
                        		FileWriter f2 = new FileWriter(filePathh +"/" + fId);
                        		f2.write(Buffer);
                        		f2.close();

                        		/**
                        		* Now checkin the newly approved page
                        		*/
                        		String sc ="ci -q -mx -l -t-none " + fId;
                        		p = r.exec(sc, null, new File(filePathh));
          				data.setMessage(Wikiaction26);
					/**
					* release memory
					*/
					f1=null;
					f2=null;
				}//if 
			}//if
		 	else
                 	{
                        	data.setMessage("["+fId+"] " +Wikiaction27+" "+Wikiaction3);
                 	}
		}//if blank
  		else
                 {
                        data.setMessage("["+fId+"] "+Wikiaction27+" "+Wikialert4);
                 }

	}//try ends
	catch(Exception e)
	{ //catch starts

		data.setMessage("Error in doRevert of action Wikieditaction ==========> " + e);
	}//catch ends
	 finally
         {
                  releasememory();
         }//finally

}	//doRevert ends	



/**
* searches for give word and returns list of files having that word
* @param data RunData
* @param context Context
*/
public void doFind(RunData data,Context context)
	{
		String file,Wikiadmin1,notExist,ePattern;
        	file=notExist=Wikiadmin1=ePattern=null;
        	try{
                	file=data.getUser().getTemp("LangFile").toString();
                	notExist=m_u.ConvertedString("notExist",file);
                	Wikiadmin1=m_u.ConvertedString("Wikiadmin1",file);
			ePattern=m_u.ConvertedString("WikiPattern",file);
        	}
        	catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action Wikieditaction method doFind is \n"+e1);
        	}


                try{
                        
			User user=data.getUser();
        		String cId=(String)user.getTemp("course_id");
			ParameterParser pp = data.getParameters();
        //                String word=pp.getString("query","");
			/**
                       * Check for special characters
                       */
                         String word=StringUtil.replaceXmlSpecialCharacters(pp.getString("query",""));

  			int i=0;
                	String filePathf=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/"+ "/Wikifirst/" );
                	File f1=new File(filePathf);
			/**
			* Store the list of all wikifirst directory files into File array 
			*/
                	File Str[]=f1.listFiles();
			/**
			* release memory
			*/
			f1=null;
			/**
			* Check if Wikifirst Directory is empty or not 
			*/
			if(!(Str == null))
			{  //if starts
				

                		Arrays.sort(Str);//sorts the files

				/**
				* this for loop searches word in all files in
				* wikifirst and stores files in a Vector
				*/
                		for(i=0;i<Str.length;i++)
				{
  					fr= new FileReader(Str[i]);

        				br=new BufferedReader(fr);
        				String s1="";

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
					* Code below uses regular expression
					* to search for word
					* if match is found fill a vector with page name
					*/
  					Pattern pat =  Pattern.compile(word) ;
        				Matcher mat = pat.matcher(s1);
        				if(mat.find())
        				{
        					v.add(Str[i]);
       					}
					/**
					* release memory
					*/
					pat=null;
					mat=null;
        

				}//for loop end
				/**
				* Check if vector(that stores matched page name) is empty
				*/
				if(v.isEmpty())
				{
		 			data.setMessage(ePattern+" "+word+" "+notExist);
				}
				else
				{
					context.put("first",v);
					context.put("search","true");
					context.put("query",word);
				}
			} //if ends
			else
			{
				data.setMessage(Wikiadmin1);        
			}
            	}//end of try 
            	catch(Exception e)
            	{
	                data.setMessage("Error in doFind of action Wikieditaction ==========> " + e);

	    	}
		finally
                {
                       releasememory();

                }//finally

}// doFind ends


/**
* searches for given Page name in WikiFirst directory
* @param data RunData 
* @param context Context
*/
public void doFindPage(RunData data,Context context)
	{
		
		String file,Wikiadmin1,notExist,Page;
                file=notExist=Wikiadmin1=Page=null;
                try{
                        file=data.getUser().getTemp("LangFile").toString();
                        notExist=m_u.ConvertedString("notExist",file);
                        Wikiadmin1=m_u.ConvertedString("Wikiadmin1",file);
                        Page=m_u.ConvertedString("WikiPage",file);
                }
                catch(Exception e1)
                {
                        data.setMessage("Language Exception in action Wikieditaction method doFindPage is \n"+e1);
                }


                try{
                        
			User user=data.getUser();
        		String cId=(String)user.getTemp("course_id");
			ParameterParser pp = data.getParameters();
   //                     String word=pp.getString("query","");
			/**
                       * Check for special characters
                       */
                         String word=StringUtil.replaceXmlSpecialCharacters(pp.getString("query",""));

  			int i=0;
                	String filePathf=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/"+ "/Wikifirst/" );
                	File f1=new File(filePathf);
                	String Str[]=f1.list();
			/**
			* release memory
			*/
			f1=null;
			/**
			* Check if directory Wikifirst is empty
			*/
			if(!(Str == null))
			{  //if starts
				

                		Arrays.sort(Str);//sorts the files

				/**
				* this for loop searches word in all files
				* in wikifirst and stores files in a String for all files
				*/
                		for(i=0;i<Str.length;i++)
				{	
					/**
					* Check if Page name contains the given pattern
					*/
  					if(Str[i].contains(word))
					{
						v.add(Str[i]);
					}	
        			    
				}//for loop end
		
				if(v.isEmpty())
		 		data.setMessage(Page+" "+word +" "+notExist);
				else
				{
					context.put("second",v);
					context.put("search","true");
					context.put("query",word);
				}//else ends
			} //if ends
			else
			{
				data.setMessage(Wikiadmin1);        
			}
                	}//end of try 
                	catch(Exception e)
                	{
				     data.setMessage("Error in doFindPage of action Wikieditaction ==========> " + e);

			}
			 finally
                   	{
                        	releasememory();

                  	}//finally

}// doFindPage ends

      
/**
* copy the Wikilast to Wikifirst and Wikihistory
* delete the *,m file  in  Wikilast
* make version  in  Wikihistory
* @param data RunData
* @param context Context
*/

public void doMergedone(RunData data,Context context)
	{  //do Mergedone starts

		String file,Wikiaction33,Wikiaction18;
                file=Wikiaction33=Wikiaction18=null;
                try{
                        file=data.getUser().getTemp("LangFile").toString();
			Wikiaction33=m_u.ConvertedString("Wikiaction33",file);
                	Wikiaction18=m_u.ConvertedString("Wikiaction18",file);
                }
                catch(Exception e1)
                {
                        data.setMessage("Language Exception in action Wikieditaction method doMergedone is \n"+e1);
                }


                try{  //try starts
			    User user=data.getUser();
        		    String cId=(String)user.getTemp("course_id");
                            ParameterParser pp = data.getParameters();
			    Runtime r=Runtime.getRuntime();
                            Process p=null;
			    
                       	    String vId=pp.getString("vname","");
                            String fId=pp.getString("fname","");
			    String word=pp.getString("text","");
                            context.put("pad",word);
                            context.put("fname",fId);
                            context.put("vname",vId);
             		    String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");                   
             		    String filePathf=filePath +  "/Wikifirst/" ;                   
             		    String filePathh=filePath + "/Wikihistory/" ;                   
             		    String filePatht=filePath + "/Wikilast/" ;                   
             		    String filePath1t=filePatht + fId;                   

			     /**
                	     * check if there is conflict in Wikilast merged file
                	     */
                
			    if(!(ol.checkconflict(filePath1t)))
                	    {//if conflict			   
			
			   	/**
			   	* below code copies the file from Wikilast to 
			   	* wikifirst,wikihistory and versions it
			   	*/

			   	FileInputStream fin= new FileInputStream(filePatht  +"/"+fId);
			   	FileOutputStream fout = new FileOutputStream(filePathf +"/"+fId);
			   	FileOutputStream fouth = new FileOutputStream(filePathh +"/"+fId);
			   	int i;
			   	do{
					i = fin.read();
					if(i != -1) 
					{
						fout.write(i);
						fouth.write(i);
					}
			     	  } while(i != -1);
                               	 fin.close();
                               	 fout.close();
                                 fouth.close();
	
				/**
				* delete the *,m file  in  Wikilast
				*/
				

				File fo=new File(filePatht + "/"+ fId + ",m");
				fo.delete();



				/**
				* this is versioning in Wikihistory
				* we have to keep history version last 20 
				* so as we soon as we see last version in list is ( >= 20)
				* we remove (last version - 19)th version from rcs *,v file
				*/
				 ol.keep20(filePathh,fId);

				/**
				* Now checkin the finally merged page
				*/
				String sc ="ci -q -mx -l -t-none " + fId;
				p = r.exec(sc, null, new File(filePathh));

          		    	data.setMessage(Wikiaction33);
				/**
				* release memory
				*/
				fin=null;
				fout=null;
				fouth=null;
				fo=null;
			}//if
			else
			{
                    		data.setMessage(Wikiaction18);
               		}//else conflict ends
				
                   }//try ends
                catch(Exception e)
                   { //catch starts
				
			data.setMessage("Error in doMergeDone of action Wikieditaction ==========> " + e);

                   }//catch ends
		   finally
                   {
                        releasememory();
                   }//finally
}//  function MergeDone ends

/**
* work here is to delete all versions except original one
* delete the versioned file "*,v" and wikilast file
* copy the Wikifirst to Wikilast and Wikihistory
* make version 1.1 in Wikilog and RCS and version in Wikihistory
* @param data RunData
* @param context Context
*/

public void doDelversion(RunData data,Context context)
	{  //do Delversion starts
		
		String file,Wikiaction35;
                file=Wikiaction35=null;
                try{
                        file=data.getUser().getTemp("LangFile").toString();
                        Wikiaction35=m_u.ConvertedString("Wikiaction35",file);
                }
                catch(Exception e1)
                {
                        data.setMessage("Language Exception in action Wikieditaction method doDelversion is \n"+e1);
                }

                try{  //try starts
			    User user=data.getUser();
			    String username=user.getName();
                	    String firstname=user.getFirstName();
                	    String lastname=user.getLastName();

        		    String cId=(String)user.getTemp("course_id");
                            ParameterParser pp = data.getParameters();
			    Runtime r=Runtime.getRuntime();
                            Process p=null;
			    
                       	    String vId=pp.getString("vname","");
                           String fId=pp.getString("fname","");
                            context.put("fname",fId);
                            context.put("vname",vId);
             		    String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");                   
             		    String filePathf=filePath + "/Wikifirst/";                   
             		    String filePathlast=filePath + "/Wikilast/";                   
             		    String filePathlog=filePath +"/Wikilog/"+ fId; 
			    /**
			    * Check if edit has been disabled or not 
			    * if yes than disable edit * should be placed
			    * after deletion also  
			    */
			    boolean bool=false;             
			    if(ol.checktraffic(filePathlog))
			    	bool=true;
      
			    File fl = new File(filePathlog);
			    fl.delete(); 
			   /**
			    * below code deletes the original log and  Creates new one 
                            * Write the log in Wikilog directory
                            * Write version number,author and date
			    * Here we have put date according to Wikifirst file date
                            */
                            FileWriter f4 = new FileWriter(filePathlog);
			    File ff01=new File(filePathf+"/"+fId);
                            Date df = new Date(ff01.lastModified());
                            /**
                            * Check if first name and last name
                            * of author is null or not
                            * if null write login name
                            * else write full name
                            */
			    String d;
                            if((firstname==null)|(lastname==null))
                            d = username;
                            else
                            d = firstname + lastname ;
                            f4.write("version 1.1"+"\t\t"+"author "+ d +"\t\t"+ df +"\n" );

			    /**
			    * add the * at end of log if previously disabled  
			    */
			   if(bool)
			   	f4.write("*");
                            f4.close();
			   /**
			   * below code deletes version of the 
			   * file and file of wikilast
			   */
				
			   
			   File f01=new File(filePathlast+"/"+fId);
			   File f02=new File(filePathlast+"/"+fId + ",v");
			   f01.delete();
			   f02.delete();


				
			   /**
			   * below code copies the file from Wikifirst 
			   * to wikilast,wikihistory and versions it
			   */

			   FileInputStream fin= new FileInputStream(filePathf +"/"+fId);
			   FileOutputStream fout = new FileOutputStream(filePathlast +"/"+fId);
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
			   * this is versioning in Wikihistory and Wikilast
			   */

			   String sc ="ci -q -mx -l -t-none " + fId;
                           p = r.exec(sc, null, new File(filePathlast));
                           p.waitFor();
          		   data.setMessage(Wikiaction35+" "+fId);
			   /**
			   * release memory
			   */
			   fl=null;
			   f4=null;
			   df=null;
			   fin=null;
			   fout=null;
			   f01=null;
			   ff01=null;
			   f02=null;
		
                   }//try ends
                catch(Exception e)
                   { //catch starts
				
			data.setMessage("Error in doDelversion of action Wikieditaction ==========> " + e);

                   }//catch ends
		   finally
                   {
                        releasememory();

                   }//finally



}//  function Delversion ends




/**
* work here is to remove all three files that is
* wikilog wikifirst and wikilast wikihistory files also revision file *,v
* @param data RunData
* @param context Context
*/
public void doDelpage(RunData data,Context context)
{  //do Delpage starts

	String file,Wikiaction37,Page;
        file=Wikiaction37=Page=null;
        try{
        	file=data.getUser().getTemp("LangFile").toString();
		Page=m_u.ConvertedString("WikiPage",file);
                Wikiaction37=m_u.ConvertedString("Wikiaction37",file);
           }
           catch(Exception e1)
           {
          	data.setMessage("Language Exception in action Wikieditaction method doDelPage is \n"+e1);
           }

	  try{  //try starts
			User user=data.getUser();
        		String cId=(String)user.getTemp("course_id");
			ParameterParser pp = data.getParameters();
                        Runtime r=Runtime.getRuntime();
                        Process p=null;

                        String fId=pp.getString("fname","");
                        String vId=pp.getString("vname","");
             		String filePath=data.getServletContext().getRealPath("/WIKI"+"/"+cId+"/");
             		String filePathlast=filePath + "/Wikilast/";
             		String filePathh= filePath + "/Wikihistory/";
             		String filePathf=filePath + "/Wikifirst/" ;
             		String filePathlog=filePath + "/Wikilog/" ;
			String fpathlast = filePathlast +"/"+ fId ;
			String fpathh = filePathh +"/"+ fId ;
			String fpathf = filePathf +"/"+ fId ;
			String fpathlog = filePathlog +"/"+ fId ;
			File elflast =new File(fpathlast);
			File elflastm =new File(fpathlast+",v");
			File elfh =new File(fpathh);
			File elfhm =new File(fpathh+",v");
			File elff =new File(fpathf);
			File elflog =new File(fpathlog);
			elflast.delete();
			elflastm.delete();
			elfhm.delete();
			elfh.delete();
			elff.delete();
			elflog.delete();

          		data.setMessage("["+fId+"] "+Page+" "+" "+Wikiaction37);
			/**
			* release memory
			*/
			elflast =null;
                        elflastm =null;
                        elfh =null;
                        elfhm =null;
                        elff =null;
                        elflog =null;

	
                   }//try ends
                catch(Exception e)
 	        {
			data.setMessage("Error in doDelpage of action Wikieditaction ==========> " + e);
 
		 }
		 finally
                  {
                        releasememory();

                  }//finally

} //function Delpage ends

/**
* This method is invoked when no button corresponding to
* action is found
* @param data RunData
* @param context Context
* @exception Exception, a generic exception
*/
public void doPerform(RunData data,Context context) throws Exception{
		String file=data.getUser().getTemp("LangFile").toString();
                String c_msg=m_u.ConvertedString("c_msg",file);
                String action=data.getParameters().getString("actionName","");
		if(action.equals("eventSubmit_doDiff"))
                        doDiff(data,context);
		else if(action.equals("eventSubmit_doShowhistory"))
			doShowhistory(data,context);
		else if(action.equals("eventSubmit_doShoworiginal"))
                        doShoworiginal(data,context);
		else if(action.equals("eventSubmit_doDelversion"))
                        doDelversion(data,context);
		else if(action.equals("eventSubmit_doDelpage"))
                        doDelpage(data,context);	
		else if(action.equals("eventSubmit_doEdit"))
                        doEdit(data,context);	
		else if(action.equals("eventSubmit_doCreate"))
                        doCreate(data,context);	
		else if(action.equals("eventSubmit_doFind"))
                        doFind(data,context);
		else if(action.equals("eventSubmit_doRevert"))
                        doRevert(data,context);		
		else if(action.equals("eventSubmit_doFindPage"))
                       doFindPage(data,context);		
		else if(action.equals("eventSubmit_doMergenow"))
                        doMergenow(data,context);
		else if(action.equals("eventSubmit_doMergedone"))
                        doMergedone(data,context);
		else if(action.equals("eventSubmit_doStoptraffic"))
                        doStoptraffic(data,context);
		else
                        data.setMessage(c_msg);
        }

/**
* work of this function is to release memory
*/
 public  void releasememory()
        {
		ol = null ;
		m_u = null;
		Buffer = null ;
		v=null;
		fr=null;
        	br=null;

        } //  releasememory  ends


}//class ends















