package org.iitk.brihaspati.modules.utils;

/*
 * @(#) RemoteCourseUtilClient.java 
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

import java.util.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.turbine.util.TurbineException;
import org.apache.turbine.services.xmlrpc.TurbineXmlRpc;
import java.net.URL;
import org.iitk.brihaspati.om.RemoteCourses;
import org.iitk.brihaspati.om.RemoteCoursesPeer;
import org.apache.torque.util.Criteria;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.actions.RemoteCoursesAction;
import org.iitk.brihaspati.modules.actions.EditContent_Action;

import org.apache.turbine.om.security.User;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
* @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
*/

public class RemoteCourseUtilClient
{
        private static Log log = LogFactory.getLog(RemoteCourseUtilClient.class);


 /**
 * Please note following function is modified form of FileHandler.java
 * of package org.apache.turbine.services.xmlrpc.util;
 * This has been done to avoid changing of TurbineResources.properties as
 * Brihaspati will have huge number of courses and for each new course registered
 * we will have to change  TurbineResources.properties file
 *
 *  This method Returns the content of file encoded for transfer
 *
 * @param location path to file to encode.
 * @param fileName file to encode
 * @return String encoded contents of the requested file.
 *
*/
 public static String readFileContents(String location,String fileName)
            {

               StringBuffer sb = new StringBuffer();
               sb.append(location);
               sb.append(File.separator);
               sb.append(fileName);
               String file = TurbineServlet.getRealPath(sb.toString());
               StringWriter sw = null;
               BufferedReader reader = null;
               try
               {
                           /*
                           * This little routine was borrowed from the
                           * velocity ContentResource class.
                           */

                            sw = new StringWriter();
                            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                            char buf[] = new char[1024];
                            int len = 0;

                            while ((len = reader.read(buf, 0, 1024)) != -1)
                            {
                                sw.write(buf, 0, len);
                            }

                            return MimeUtility.encodeText(sw.toString(), "UTF-8", "B");
                }//try
                catch (IOException ioe)
                {
                        log.error("[RemoteCourseUtilClient]method[readFileContents] Unable to encode the contents " +
                        "of the request file.", ioe);
                        return null;
                }//catch
                finally
                {
                                    try
                            {
                                if (sw != null)
                                {
                                            sw.close();
                                }
                                if (reader != null)
                                {
                                            reader.close();
                                }
                            }
                            catch (Exception e)
                            {
                            }
                }//finally
    }//function readFilecontents ends

	/**
	* This method sends send request to server
	* @param keyinfo String Array
        * @param clientmd5key String
        * @param clientURL String
        * @param serverURL String
        * @param sourceLocationProperty String
        * @param sourceFileName String
        * @param destinationLocationProperty String
        * @param destinationFileName String
        * @return boolean
	*/
        public static boolean send(String []keyinfo ,String clientmd5key,
			    String clientURL ,
			    String serverURL ,
                            String sourceLocationProperty,
                            String sourceFileName,
                            String destinationLocationProperty,
                            String destinationFileName)
            
            {
                        Vector params = new Vector();
                try
                {        
                        params.add(keyinfo);
			params.add(clientmd5key);
			params.add(clientURL);
			params.add(serverURL);
			params.add(sourceLocationProperty);
			params.add(sourceFileName);

                        /*
                        * fileContents
                        */

                        params.add(readFileContents(sourceLocationProperty,sourceFileName));

                        /*
                        * where the fileContents should land.
                        */
                        params.add(destinationLocationProperty);

                         /*
                          * name to give the file contents.
                          */
                        params.add(destinationFileName);

                        Boolean b = (Boolean) TurbineXmlRpc.executeRpc(new URL(serverURL),"remote1.send", params);
			
			return b;

                }
                catch (Exception e)
                {

                        log.error("[RemoteCourseUtilClient] Util [send]Error sending file toserver:", e);
                        return false;
                }//catch
		finally
                {
                        /**
                        * Release memory
                        */
			params = null;
                        keyinfo = null;

                }


            }// send ends
	
	/**
	* This method sends get request to server
	* @param keyinfo String Array
        * @param serverURL String
        * @param clientURL String
        * @param sourceLocationProperty String
        * @param sourceFileName String
        * @param destinationLocationProperty String
        * @param destinationFileName String
        * @return String
	*/
        public static String get(String []keyinfo,
			    String serverURL,
			    String clientURL,
                            String sourceLocationProperty,
                            String sourceFileName,
                            String destinationLocationProperty,
                            String destinationFileName)

            {

		String md5 = null;
                try
                {
                        /**
                        * call ActivateRemoteXmlRpcPort(String serverURL)
                        */ 

                        String activemessage = ActivateRemoteXmlRpcPort(keyinfo[1]);
			if(activemessage.equals("ERROR"))
			{
				//return "Tdk stopped";
				return "RemoteAction_msg2";
				
			}

                        /**
                        * make client in form of http://url:12345
                        */

                        clientURL =  "http://" + clientURL + ":12345/" ;

                        /**
                        * Get Raw key from database
                        */

			String keylocalurl =keyinfo[0];
                        String keyremoteurl = keyinfo[1];
                        String keylocalcourse = keyinfo[2];
                        String keyremotecourse = keyinfo[3];
			Criteria crit=new Criteria();
                        crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,keylocalcourse);
                        crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,keyremotecourse);
                        crit.add(RemoteCoursesPeer.INSTITUTE_IP,keyremoteurl);

                         /**
                          * Status should be buy 
                          */

                         crit.add(RemoteCoursesPeer.STATUS,0);

                         List v = RemoteCoursesPeer.doSelect(crit);

			boolean isNotRegistered=v.isEmpty();
                        if(isNotRegistered)
                        {
                                //return "This  Course  and Remote IP address is NOT Registered with local server ";
                                return "RemoteClient_msg3";

                        }

                         RemoteCourses rce =(RemoteCourses)v.get(0);

                         //Check if Date is Expired
                         Date Expiry_date = rce.getExpiryDate();
                         /**
                         * Current date object
                         */

                         Date currentdateobj =  new Date();


                         /**
                         * Check if expired
                         */
                         boolean isExpired = (currentdateobj.getTime() - Expiry_date.getTime()) > 0 ;
                         if(isExpired)
			 {
				//return "Your subscription is EXPIRED" ;
				return "RemoteServer_msg1";
			 }
			 String sqlkey = rce.getSecretKey();
                         String rawkey = sqlkey;

                        /**
                        * Add Rawkey with url and make modified key
                        */
                        String seperator ="####";
                        String modifiedkey = rawkey + seperator + clientURL + seperator 
					     + serverURL  + seperator + sourceLocationProperty + seperator 
					     + sourceFileName + seperator + destinationLocationProperty + seperator
                        		     + destinationFileName ; 

			/**
			* Take MD5 hashcode modified key 
			*/
			md5 = EncryptionUtil.createDigest("MD5",modifiedkey);

			/**
			* Add to vector below  the md5 string -----------------manav

			* This means both client and server has to run from secretkey md5
			*/

                       Vector params = new Vector();
                       params.add(keyinfo);
                       params.add(md5);
                       params.add(serverURL);
                       params.add(clientURL);
                       params.add(sourceLocationProperty);
                       params.add(sourceFileName);
                       params.add(destinationLocationProperty);
                       params.add(destinationFileName);
                       String response = (String) TurbineXmlRpc.executeRpc(
                       new URL(serverURL), "remote1.get", params);
		       params= null;
		       
 			/**
                        * Call getRemoteFileList method   to create links in Course Contents
                        */
			Vector getListparams = new Vector();
                        getListparams.add(keylocalcourse);
                        getListparams.add(keyremotecourse);
                        getListparams.add(rce.getCourseSeller());
			/**
			* we are buyers
			*/
                        getListparams.add(rce.getCoursePurchaser());
                        getListparams.add(TurbineServlet.getServerName());
			/**
			* Get request means buying so status 0
			*/
                        getListparams.add(rce.getStatus());
                        getListparams.add(sqlkey);

			RemoteCoursesAction RCA = new RemoteCoursesAction();
                        String gotlist = RCA.getRemoteFileList(keyremoteurl,keylocalcourse,keyremotecourse,getListparams);
			getListparams = null;
                        if(gotlist.equals("ERRORS"))
                        {
                                throw new Exception("[RemoteCoursesServer]Exception in action [getRemoteFileList] \n ") ;
                        }
                        else if(gotlist.equals("ERRORC"))
                        {
                                throw new Exception("[RemoteCoursesServer]Exception in action [getRemoteFileList] \n ") ;
                        }
                        else if(!gotlist.equals("SUCCESS"))
                        {
                                throw new Exception("[RemoteCoursesAction]Exception in action [getRemoteFileList] \n "+ gotlist) ;
                        }
			gotlist = null;

                       return  response;
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][get]Error getting file from server:", e);

                        return "[RemoteCourseUtilClient] Util [get]Error sending file to server: on \n"+ TurbineServlet.getServerName();
                }
		finally
                {
                        /**
                        * Release memory
                        */
                        keyinfo = null;

                }
            }// function get ends
		

	/**
	* This method sends ActivateLocalXmlRpcPort() request to server
        * @return String
	*/
	   public static String ActivateLocalXmlRpcPort()
            {
                try
                {
                        String serverURL =  "http://" + TurbineServlet.getServerName() + ":12345/" ;
                        Vector params = new Vector();
                        String response = (String) TurbineXmlRpc.executeRpc(new URL(serverURL)
			, "remote1.ActivateLocalXmlRpcPort", params);
                        return  response;
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][ActivateLocalXmlRpcPort]Error is:", e);

                        return "[RemoteCourseUtilClient] Util [ActivateLocalXmlRpcPort]Error  on:\n"+TurbineServlet.getServerName();

                }
            }// function ActivateLocalXmlRpcPort  ends

	/**
        * This method will activate Remote xmlRpc Port
	* This is done by  calling its BrihaspatiLogin.vm
	* Now BrihaspatiLogin.java call its own ActivateLocalXmlRpcPort method
        * @param serverURL String
        * @return String
        */
        public static String ActivateRemoteXmlRpcPort(String serverURL)
        {
                String filecontents =  null;
                String newurl =  "http://" + serverURL + ":8080/brihaspati/servlet/brihaspati/template/BrihaspatiLogin.vm" ;
                try{
                        java.net.URL url = new  java.net.URL(newurl);
                        java.net.URLConnection con = url.openConnection();
                        java.io.InputStream fin = con.getInputStream();
                        int nread = 0;
                        byte buffer[]=new byte[1000];
                        while((nread=fin.read(buffer))!=-1 )
                        {
                                filecontents = filecontents+new String(buffer,0,nread);
                        }
			fin.close();
			fin=null;
			buffer=null;
			return filecontents;
                   }
                   catch(Exception e)
                   {
			log.error("[RemoteCourseUtilClient][ActivateRemoteXmlRpcPort]Error is:", e);
			return "ERROR";
                   }

        }//ActivateRemoteXmlRpcPort


	  /**
          * This method sends checkRegisteration request to remote server
          * @param serverURL String
          * @param params Vector
          * @return int
          */
           public static int checkRegisteration( String serverURL, Vector params)
            {
                int response = -1;
              try
                {
                        response = (Integer) TurbineXmlRpc.executeRpc(new URL(serverURL)
                        , "remote1.checkRegisteration", params);
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][checkRegisteration]Error is:", e);

                }
		return response;

            }// function   ends
	
	  /**
          * This method sends getRemoteFileList request to remote server
          * @param serverURL String
          * @param params Vector
          * @return String
          */
           public static String getRemoteFileList( String serverURL, Vector params)
            {
                String response = "ERRORC";
              try
                {
                        response = (String) TurbineXmlRpc.executeRpc(new URL(serverURL)
                        , "remote1.getRemoteFileList", params);
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][getRemoteFileList]Error is:", e);

                }
		return response;

            }// function   ends

	/**
	* This method implements LocalCache in Remote Courses
        * This method will now call get() after doing checking
        * that when was last course updated
        * Next update will be  after atleast one day
        * @param pDate String
        * @param autherNm String
        * @param courseNm String
        * @param fileID String
        * @param topcNm String
        * @param dir String
        */

           public static void LocalCache( String pDate , String autherNm 
					,String courseNm 
					,String fileID 
					,String topcNm
					,String dir)
        {
             try{

			/**
			* Current date object
			*/

 	      		Date currentdateobj =  new Date();

			/**
			* Publish date object
			*/
			DateFormat df = null;
        		Date pdateobj = null;
			if(!pDate.equals("----"))
			{
				df = DateFormat.getDateInstance();
        			pdateobj = df.parse(pDate);
			}
			
			/**
			* Check if both the dates have difference of one day =86400 s =86400000 ms
			*/

			long seconds = 86400000 ;					
			int days = 1;
			boolean isFresh = false;

			if(!pDate.equals("----"))
			{
				isFresh = (currentdateobj.getTime() - pdateobj.getTime()) < (days * seconds) ;			      
			}


			/**
			* If difference is greater than one day call get()
			*/

			String msg , url , source ,dest1 , destination;
			msg = url =  source = dest1 = destination =null;
			if(!isFresh)
			{
				url =  "http://" + autherNm + ":12345/" ;
                        	source = "/Courses/"+ "/" + courseNm + "/Content/" + "/" + topcNm +"/";
				dest1 = "/Courses"+ "/" + dir + "/Content" +  "/Remotecourse/" ;
                        	destination = dest1 + "/"+autherNm+"/"+courseNm + "/" + topcNm  ;

				String fullpath = TurbineServlet.getServletContext().getRealPath(destination);
				String subtopic=topcNm;

				/**
                        	* Send information like local url ,remote url, local course ,remote course
                        	* seperately in string array
                        	*/

                        	String  keyinfo[]={TurbineServlet.getServerName(),autherNm,dir,courseNm}; 
                        	msg = get(keyinfo,url,TurbineServlet.getServerName(),source,fileID,destination,fileID);
				keyinfo = null;
				/**
				* If subscription is expired delete all links
				*/
				if(msg.equals("RemoteServer_msg1"))
				{
					EditContent_Action ECA = new EditContent_Action();
 		                        ECA.RemoteDelTopic(dir,courseNm,topcNm,autherNm);
				}
				/**
				* If file got succesfully Write code to change the date of innerxml file 
				*/
				if(msg.equals("RemoteClient_msg1"))
				{
					RemoteCoursesAction.innerxml(fullpath,subtopic,fileID,false);
				}
				
			}

                }//try
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][LocalCache]Error is:", e);

                }

            }// function   ends

 	  /**
          * This method sends Remote Course File List to client
          * @param cId  String
          * @return String
          */

           public  String getRemoteFileList(String cId)
          {
                try
                {
                        String filePath = TurbineServlet.getRealPath("/Courses"+ "/" + cId + "/Content" );
                        File dirHandle = new File(filePath);
                        String filter[]={"Permission","Remotecourse"};
                        String filterxml[] =new String[2];
                        filterxml[0]="Unpublished";
                        NotInclude exclude=new  NotInclude(filter);
                        File topiclist[]=dirHandle.listFiles(exclude);
                        String intopiclist[]= null;
                         /**
                        * Create  File List in string buffer
                        */

                        StringBuffer sw = new StringBuffer();

                        for(int i=0 ;i < topiclist.length ; i++)
                        {
                                if(topiclist[i].isDirectory())
                                {
                                        sw.append(topiclist[i].getName()).append("\n");
                                        filterxml[1]= topiclist[i].getName() + "__des.xml";
                                        exclude = new NotInclude(filterxml);
                                        intopiclist =topiclist[i].list(exclude);
                                        sw.append(intopiclist.length).append("\n");
                                        for(int j=0 ;j < intopiclist.length ; j++)
                                        {
                                                sw.append(intopiclist[j]).append("\n");
                                        }//for
                                }//if
                        }//for

                        /**
                        * Release memory
                        */
			filter = filterxml =  intopiclist = null;
			topiclist = null;
                        return sw.toString();
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilClient][getRemoteFileList(String)]Error is:", e);
                        return "ERRORS";
                }
            }// function   ends

}//class
