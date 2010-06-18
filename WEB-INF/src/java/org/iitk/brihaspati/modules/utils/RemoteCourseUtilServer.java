package org.iitk.brihaspati.modules.utils;

/*
 * @(#) RemoteCourseUtilServer.java 
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

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;
import java.util.Vector;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.mail.internet.MimeUtility;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.RemoteCourses;
import org.iitk.brihaspati.om.RemoteCoursesPeer;

/**
* @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
*/


public class RemoteCourseUtilServer
{
        
        private static  Log log = LogFactory.getLog(RemoteCourseUtilServer.class);
        

         /**
         * Please note following function is modified form of FileHandler.java 
         * of package org.apache.turbine.services.xmlrpc.util;
         * This has been done to avoid use of  TurbineResources.properties as 
         * Brihaspati will have huge number of courses and for each new course registered 
         * we will have to change  TurbineResources.properties file 

         * Simply take the file contents that have been sent
         * by the client and write them to disk in the
         * specified location: targetLocationProperty specifies
         * the directory in which to place the fileContents
         * with the name fileName.
         *
         * @param fileContents are contents of file to encode
         * @param location path to file to encode.
         * @param fileName is to file to encode.
         * @return String encoded contents of the requested file.
         */


         private static boolean writeFileContents(String fileContents,String location,String fileName)
            {

                /*
                 * The target location is always within the webapp to
                 * make the application fully portable. So use the TurbineServlet
                 * service to map the target location in the webapp space.
                 */
                
                File targetLocation = new File(TurbineServlet.getRealPath(location));

                if (!targetLocation.exists())
                {
                                    /*
                             * If the target location doesn't exist then
                             * attempt to create the target location and any
                             * necessary parent directories as well.
                             */
                            if (!targetLocation.mkdirs())
                            {
                                log.error("[RemoteCourseUtilServer][writeFileContents] Could not create target location: " + targetLocation + ". Cannot transfer file from client.");

                                return false;
                            }
                            else
                            {
                                log.info("[RemoteCourseUtilServer][writeFileContents] Creating target location:" + targetLocation + " in order to complete file transfer from client.");
                            }
                }

                FileWriter fileWriter = null;
                try
                {
                            /*
                             * Try to create the target file and write it out
                             * to the target location.
                             */
                            fileWriter = new FileWriter(targetLocation + "/" + fileName);

                            /*
                             * It is assumed that the file has been encoded
                             * and therefore must be decoded before the
                             * contents of the file are stored to disk.
                             */
                            fileWriter.write(MimeUtility.decodeText(fileContents));

                            return true;
                }//try
                catch (IOException ioe)
                {
                            log.error("[RemoteCourseUtilServer][writeFileContents] Could not write the decoded file " +
                            "contents to disk for the following reason.", ioe);

                            return false;
                }//catch
                finally
                {
                            try
                            {
                                if (fileWriter != null)
                                {
                                            fileWriter.close();
                                }
                            }
                            catch (Exception e)
                            {
                            }
                }//finally
            }//function writeFileContents ends

         /**
         * Simply take the file contents that have been sent
         * by the client and write them to disk in the
         * specified location: targetLocationProperty specifies
         * the directory in which to place the fileContents
 	 * with the name fileName.
	 * 
         * @param keyinfo Vector
         * @param md5keyclient String
         * @param clientURL String
         * @param serverURL String
         * @param sourceLocationProperty String
         * @param sourceFileName String
         * @param fileContents String
         * @param fileName String
         * @return boolean
         */

         public boolean send(Vector keyinfo,String md5keyclient ,
			String clientURL ,
			String serverURL ,
                        String sourceLocationProperty,
                        String sourceFileName,
			String fileContents,
                        String targetLocationProperty,
                        String fileName)
            {

                        String keylocalurl =(String)keyinfo.elementAt(0);
                        String keyremoteurl = (String)keyinfo.elementAt(1);
                        String keylocalcourse = (String)keyinfo.elementAt(2);
                        String keyremotecourse = (String)keyinfo.elementAt(3);

                        /**
                        * Get Raw key from database
                        */
			List v = null;
			try{
                        	Criteria crit=new Criteria();
                  	        crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,keyremotecourse);
                        	crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,keylocalcourse);
                 	       /**
                        	*  Their local url is remote URl here
               		        */
                 	       crit.add(RemoteCoursesPeer.INSTITUTE_IP,keylocalurl);

                        	 /**
                	         * Status should be buy here 
                        	 */

	                        crit.add(RemoteCoursesPeer.STATUS,0);
					
                        	v = RemoteCoursesPeer.doSelect(crit);
				crit = null;
			}//try
			catch(Exception e)
			{
                  		 log.error("[RemoteCourseUtilServer][send]Error is:", e);

				return false ;
			}//catch
				//check if v is null 
			if(v.isEmpty())
			return false ;
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
			return false ;
				
                        String sqlkey = rce.getSecretKey();

			rce = null;

			/**
			* Authorization Check 
			*/
					
			/**
        		 * Get Raw key from database
                         */
                         String rawkey = sqlkey;
			/**
                        * Add Rawkey with url and make modified key
                        * Order of key is serverURL first client URL next
                        */
                         String seperator ="####";
                         String modifiedkey = rawkey + seperator
                                              + serverURL  + seperator
                                              + clientURL + seperator
                                              + sourceLocationProperty + seperator
                                              + sourceFileName + seperator
                                              + targetLocationProperty + seperator
                                              + fileName ;

			 boolean isAuthorized = false;

               		 try{
              			/**
       		       		* Take MD5 hashcode  of modified key
                	       	*/

	          		String md5keyserver = EncryptionUtil.createDigest("MD5",modifiedkey);
				if(md5keyserver.equals(md5keyclient))
				isAuthorized = true; 


        	       }//try
                       catch(Exception e)
			{
                  		 log.error("[RemoteCourseUtilServer][send]Error is:", e);
				return false ;
			}
			finally
			{
				keyinfo = null;
				v = null;
				
			}

			if(isAuthorized)
			{	
                		return writeFileContents(fileContents, targetLocationProperty,fileName);
			}
			else return false ;

            }//function send ends


	/**
	* This Method Recieves Get Course request from client ,Verifies and  than call send() method
         * @param keyinfo Vector
         * @param md5keyclient String
         * @param serverURL String
         * @param clientURL String
         * @param sourceLocationProperty String
         * @param sourceFileName String
         * @param destinationLocationProperty String
         * @param destinationFileName String
         * @return String
	* NOTE :string array was send from RemoteCourseUtilClient here it is vector (xml-rpc property)
	*/
        public  String get(Vector keyinfo,
			    String md5keyclient,
			    String serverURL,
                            String clientURL,
                            String sourceLocationProperty,
                            String sourceFileName,
                            String destinationLocationProperty,
                            String destinationFileName)
            {        
                
                try{

                        String SystemIp = TurbineServlet.getServerName();

			/**
			* Convert client url to http://url:port format
			* 
			*/

			String filePath = TurbineServlet.getRealPath(sourceLocationProperty +"/"+sourceFileName);
                        File f1= new File(filePath);
                        boolean bool = f1.exists();
			 /**
                        * check if course exists
                        */
                        if(bool)
                        {
				/**
				* Authorization Check 
				*/

				String keylocalurl =(String)keyinfo.elementAt(0);			
				String keyremoteurl = (String)keyinfo.elementAt(1);			
				String keylocalcourse = (String)keyinfo.elementAt(2);			
				String keyremotecourse = (String)keyinfo.elementAt(3);			
				String  newkeyinfo[]={keyremoteurl,keylocalurl,keyremotecourse,keylocalcourse};

				/**
        		        * Get Raw key from database
                        	*/

				Criteria crit=new Criteria();
                                crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,keyremotecourse);
                                crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,keylocalcourse);

				/**
				*  Their local url is remote URl here
				*/
                                crit.add(RemoteCoursesPeer.INSTITUTE_IP,keylocalurl);


                                /**
                                * Status should be sell 
                                */

                                crit.add(RemoteCoursesPeer.STATUS,1);

				List v = RemoteCoursesPeer.doSelect(crit);
                                RemoteCourses rce =(RemoteCourses)v.get(0);

				crit = null;
				v=null;
	
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
				return "RemoteServer_msg1" ;

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
              		          * Take MD5 hashcode  of modified key
                       		   */
	          		 String md5keyserver = EncryptionUtil.createDigest("MD5",modifiedkey);
			
				 boolean isAuthorized = false;
				 if(md5keyserver.equals(md5keyclient))
				 	isAuthorized = true; 

				if(isAuthorized)
				{

                                        boolean Remoteresponse = RemoteCourseUtilClient.send(newkeyinfo,md5keyclient, 
						     serverURL,
						     clientURL ,
                                                     sourceLocationProperty,
                                                     sourceFileName,
                                                     destinationLocationProperty,
                                                     destinationFileName);
					newkeyinfo = null;
					if(Remoteresponse)
		                        {
                     			 	return  "RemoteClient_msg1" ;
                        		}
                		        else
                        		{
                        		        return  "RemoteClient_msg2" ;
                    			}
				}
				else
				{
                                        return "RemoteServer_msg2" ;
				}

                        }//if
                        else
                        {
				return "RemoteServer_msg3" ;
                        }
                }//try
                catch(Exception e)
                {
                  	log.error("[RemoteCourseUtilServer][get]Error is:", e);
			 return "[RemoteCourseUtilServer] Util [get]Error  on:\n"+TurbineServlet.getServerName();

                }
		finally
		{
			keyinfo = null;
		}

                        
            }//function get ends

	/**
	 * This Method is used for  activating local xml rpc port 12345
	 * This is necessary as server has to send recives remote course you need to keep port 12345 alive
	 * It returns server ip address when port active  else returns some error message 
         * @return String
	*/

	 public String ActivateLocalXmlRpcPort()
         {
             try{
                        String SystemIp = TurbineServlet.getServerName();
                        return SystemIp;
                }//try
                catch(Exception e)
                {
			/**
			* This Exception message  does not reveal any information to outside user
			* so is Safe and UseFul 
			*/
                  	log.error("[RemoteCourseUtilServer][ActivateLocalXmlRpcPort]Error is:", e);
                        return e.getMessage();
                }
            }//ActivateLocalXmlRpcPort ends

	  /**
	  * This method checks Regitration on Remote Server and sends status codes
	  * This method will 
	  * Return 0 zero if registeration not successful (other entry plus password)
	  * Return 1 one if registeration  successful but subscription expired  
	  * Return 2 one if Error in checkRegisteration    
	  * Return 3 if Registeration success and subscription not expired
          * @param myCourse String
          * @param cId String
          * @param course_s String
          * @param course_p String
          * @param url String
          * @param status1 int
          * @param sec_key String
          * @return int
	  */

	   public static int checkRegisteration( String myCourse, String cId,String course_s,String course_p,String url ,int status1,String sec_key)
            {
 	    	Boolean response = false;

              try
                {
			Criteria crit=new Criteria();
			/**
			* LOCAL_COURSE_ID will become REMOTE_COURSE_ID and vice versa
			*/		
                        crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,cId);
                        crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,myCourse);
			
			/**
			* COURSE_PURCHASER and  COURSE_SELLER will remain same here also
			*/		
	
                        crit.add(RemoteCoursesPeer.COURSE_SELLER,course_s);
                        crit.add(RemoteCoursesPeer.COURSE_PURCHASER,course_p);
			/**
			* Here url is local url of client side ---------send local url ----manav
			*/
                        crit.add(RemoteCoursesPeer.INSTITUTE_IP,url);
			/**
			* Their status should be opposite  mine 
			*/
			if(status1 == 0)
			status1 = 1;
			else if(status1 == 1)
			status1 = 0;
                        crit.add(RemoteCoursesPeer.STATUS,status1);
			List l =  RemoteCoursesPeer.doSelect(crit);
			
			crit = null;

			/**
			* If List l is empty means registration not on Remote Server
			*/
			if(l.isEmpty())
                        return 0 ;

			/**
			* Password matched using MD5 only
			*/
			RemoteCourses rce =(RemoteCourses)l.get(0);

			l =null;

			String password = rce.getSecretKey();

                        boolean isRegistered=password.equals(sec_key);
			/**
			* Here we have sent same message for invalid password and not registered
			* so that password guessing is prevented 
			*/
			if(!isRegistered)
			return 0;


			/**
			* See if registration is valid date wise 
			*/
			
			 //Check if Date is Expired     
			Date Expiry_date = rce.getExpiryDate();

			rce = null;

			/**
			* Current date object
			*/
			Date currentdateobj =  new Date();
			/**
			* Check if expired  if true
			* than say false else true
		        */                                   
			long Exptime = Expiry_date.getTime();
			boolean isExpired = (currentdateobj.getTime() - Expiry_date.getTime()) > 0 ;
			if(isExpired)
			return 1 ;
			else 
			return 3; 
			
                }
                catch (Exception e)
                {
                        log.error("[RemoteCourseUtilServer][checkRegisteration]Error is:", e);
                	return 2;
                }

            }// function   ends

	  /**
          * This method sends Remote Course File List to client
          * @param myCourse String
          * @param cId      String
          * @param course_s String
          * @param course_p String
          * @param url String
          * @param status1 int
          * @param sec_key String
          * @return String
          */

           public  String getRemoteFileList(String myCourse, String cId,String course_s,String course_p,String url ,int status1,String sec_key)
            {
			/**
			* Perform authorization check before getting list ,if authorization fails return ""
			*/
			int j = checkRegisteration( myCourse,  cId, course_s, course_p, url , status1, sec_key);
			if(j != 3)
			{
				return (new StringBuffer()).toString();
			}
			RemoteCourseUtilClient RCUC = new RemoteCourseUtilClient();
                        return RCUC.getRemoteFileList(cId);
	     }//method

}//class
