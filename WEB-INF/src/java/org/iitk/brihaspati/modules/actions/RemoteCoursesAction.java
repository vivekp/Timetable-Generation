package org.iitk.brihaspati.modules.actions;

/*
 * @(#) RemoteCoursesAction.java
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
//JDK
import java.io.File;
import java.util.List;
import java.util.Date;
//import java.sql.Date; cannot define sql.Date one of two will exist
import java.util.Vector;
import java.io.FileWriter;
import java.util.StringTokenizer;
//Turbine
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;
//Brihaspati
import org.iitk.brihaspati.om.RemoteCourses;
import org.iitk.brihaspati.om.RemoteCoursesPeer;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.EncryptionUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.RemoteCourseUtilClient;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;

/**
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>

 */

public class RemoteCoursesAction extends SecureAction_Instructor
{
	MultilingualUtil m_u=new MultilingualUtil();

	/**
	* This method brings links to all remote courses file in particular course 
	* @param data RunData
	* @param context Context
	*/
  	public void doGet(RunData data,Context context)
        {
	
		/**
		* We initially check if we have got all multilingual messages or not (that are used in this method)
		* this is done to avoid discomfort later
		*/

		String file ,RemoteAction_msg1,RemoteAction_msg2,RemoteAction_msg3,RemoteAction_msg4,RemoteClient_msg6,RemoteAction_msg5,RemoteAction_msg6,RemoteClient_msg1,RemoteClient_msg2,RemoteClient_msg3,RemoteServer_msg3,RemoteServer_msg1,RemoteClient_msg4;
		file = RemoteAction_msg1 = RemoteAction_msg2 = RemoteAction_msg3 = RemoteAction_msg4 = RemoteAction_msg5 = RemoteAction_msg6 = RemoteClient_msg1 = RemoteClient_msg2 = RemoteClient_msg3 = RemoteClient_msg4= RemoteClient_msg6= RemoteServer_msg3 =RemoteServer_msg1=null;
        	try{
			file=data.getUser().getTemp("LangFile").toString();
                	RemoteAction_msg1=m_u.ConvertedString("RemoteAction_msg1",file);
                	RemoteAction_msg2=m_u.ConvertedString("RemoteAction_msg2",file);
                	RemoteAction_msg3=m_u.ConvertedString("RemoteAction_msg3",file);
                	RemoteAction_msg4=m_u.ConvertedString("RemoteAction_msg4",file);
                	RemoteAction_msg5=m_u.ConvertedString("RemoteAction_msg5",file);
                	RemoteAction_msg6=m_u.ConvertedString("RemoteAction_msg6",file);
                	RemoteClient_msg1=m_u.ConvertedString("RemoteClient_msg1",file);
                	RemoteClient_msg2=m_u.ConvertedString("RemoteClient_msg2",file);
                	RemoteClient_msg3=m_u.ConvertedString("RemoteClient_msg3",file);
                	RemoteClient_msg4=m_u.ConvertedString("RemoteClient_msg4",file);
                	RemoteClient_msg6=m_u.ConvertedString("RemoteClient_msg6",file);
                	RemoteServer_msg1=m_u.ConvertedString("RemoteServer_msg1",file);
                	RemoteServer_msg3=m_u.ConvertedString("RemoteServer_msg3",file);
        	}
 	       catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action [RemoteCoursesAction] method [doGet]  is \n"+e1);
			return;
        	}

                try
                {	
			User user=data.getUser();
			ParameterParser pp=data.getParameters();
			String myCourse = (String)data.getUser().getTemp("course_id");

			int startIndex=pp.getInt("updatestartIndex",0);
			String conf=(String)user.getTemp("confParam","10");
                        int list_conf=Integer.parseInt(conf);


			if(startIndex > 0)
			{
				pp.add("startIndex",startIndex - list_conf);			
			}
			else
			{
				pp.add("startIndex",startIndex );			
			}

			int serial=pp.getInt("serial",0);
			pp.add("serial",serial);
			/**
			* AS File is for purchase so status1=0
			*/

                        int status1=0;
                        Date d=new Date();
                        String cId=pp.getString("cid","");

                        String course_s=pp.getString("csell","");

                        String course_p=pp.getString("cpurch","");

                        String url =pp.getString("iip","");


                        String inst_name=pp.getString("inm","");

                        String sec_key=pp.getString("sec_key","");

                        String status=pp.getString("order");
			

                        if(status.equals("Sell"))
			{
				data.addMessage(RemoteAction_msg1);
                        	pp.add("status",1);
				return;
			}
                        pp.add("status",status1);

			/**
                        * Check if Remote Turbine is Stop
                        */
	
			String RemoteTurbineMsg = RemoteCourseUtilClient.ActivateRemoteXmlRpcPort(url);
                        if(RemoteTurbineMsg.equals("ERROR"))
                        {
                                data.addMessage(RemoteAction_msg2);
                                return;
                        }
		

			Criteria crit=new Criteria();
			crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,myCourse);
			crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,cId);
			crit.add(RemoteCoursesPeer.COURSE_SELLER,course_s);
			crit.add(RemoteCoursesPeer.COURSE_PURCHASER,course_p);
			crit.add(RemoteCoursesPeer.INSTITUTE_IP,url);
			crit.add(RemoteCoursesPeer.INSTITUTE_NAME,inst_name);
			crit.add(RemoteCoursesPeer.STATUS,status1);
			crit.add(RemoteCoursesPeer.SECRET_KEY,sec_key);

			/**
			* check if registration  already done if yes than proceed
			* else return
			*/

			List l =  RemoteCoursesPeer.doSelect(crit);
			crit=null;
			
                        boolean isNotRegistered=l.isEmpty();
                        if(isNotRegistered)
			{
				data.addMessage(RemoteClient_msg3);
				return;

			}


			 RemoteCourses rce =(RemoteCourses)l.get(0);
			 l=null;

                         //Check if Date is Expired
                         Date Expiry_date = rce.getExpiryDate();
			 rce = null;
                         /**
                         * Current date object
                         */

                         Date currentdateobj =  new Date();
                         /**
                         * Check if subscription expired
                         */
                         boolean isExpired = (currentdateobj.getTime() - Expiry_date.getTime()) > 0 ;
                         if(isExpired)
                         {
				data.addMessage(RemoteServer_msg1);
				return;
                         }

			String serverURL =  "http://" + url + ":12345/" ;

			/**
			* Check if  you are authorized (with password)to acces the file
			*/

			Vector params = new Vector();
			params.add(myCourse);
			params.add(cId);
			params.add(course_s);
			params.add(course_p);
			params.add(data.getServerName());
			params.add(new Integer(status1));
			params.add(sec_key);
			int expirytime = RemoteCourseUtilClient.checkRegisteration(serverURL,params);

			/**
			* Check if expiry time is less than 3
			* means registration failed else successfull  at 3
			* because expirytime will be -1,0,1,2 or 3 
			*/

			if(expirytime == -1 )
			{
				data.addMessage(RemoteClient_msg4 + RemoteAction_msg3);
				return ;
			}
			else if(expirytime == 0 )
			{
				data.addMessage(RemoteClient_msg4 + RemoteAction_msg4);
				return ;
			}
			else if(expirytime == 1)
			{		
				data.addMessage(RemoteClient_msg4 + RemoteAction_msg5);
				return ;
			}
			else if(expirytime == 2)
			{		
				data.addMessage(RemoteClient_msg4 + RemoteAction_msg6);
				return ;
			}

			/**
			* Call getRemoteFileList method   to create links in Course Contents 
			*/

			String gotlist = getRemoteFileList(url,myCourse,cId,params);
			if(gotlist.equals("ERRORS"))
			{
                                data.addMessage(RemoteClient_msg4 + RemoteAction_msg6);
				return ;
			}
			else if(gotlist.equals("ERRORC"))
			{
                                data.addMessage(RemoteClient_msg4 + RemoteAction_msg3);
                                return ;
			}
			else if(!gotlist.equals("SUCCESS"))
			{
                                throw new Exception("[RemoteCoursesAction]Exception in action [getRemoteFileList] \n "+ gotlist) ;
			}
                        data.addMessage(RemoteClient_msg6);
			/**
			* Release memory
			*/
			params = null;
			gotlist = null;
			

		}//try
		catch( Exception e)
		{
			data.addMessage("[RemoteCoursesAction]Exception in action [doGet] \n"+e );

		}//catch
		finally
		{
			
			file = RemoteAction_msg1 = RemoteAction_msg2 = RemoteAction_msg3 = RemoteAction_msg4 = RemoteAction_msg5 = RemoteAction_msg6 = RemoteClient_msg1 = RemoteClient_msg2 = RemoteClient_msg3 = RemoteClient_msg4 = RemoteServer_msg3 =RemoteServer_msg1=RemoteClient_msg6=null;
		}

	}//doGet ends

	/**
	* This method will insert into database the particulars of remote buyer/seller 
	* @param data RunData
	* @param context Context
	*/
	public void doInsert(RunData data,Context context)
	{

		String file, news_msg2 ,u_msg3,RemoteAction_msg7,RemoteAction_msg9 ,news_Checkmsg1,news_Checkmsg2,news_Checkmsg3 ,RemoteAction_msg10,RemoteAction_msg11,RemoteAction_msg12,db_msg4;
                file = news_msg2 =  u_msg3 = RemoteAction_msg7 = RemoteAction_msg9 = news_Checkmsg1 = news_Checkmsg1 = news_Checkmsg2 = news_Checkmsg3 = RemoteAction_msg10 = RemoteAction_msg11= RemoteAction_msg12= db_msg4=null;
                try{
                        file=data.getUser().getTemp("LangFile").toString();
                        RemoteAction_msg7=m_u.ConvertedString("RemoteAction_msg7",file);
                        RemoteAction_msg9=m_u.ConvertedString("RemoteAction_msg9",file);
                        RemoteAction_msg10=m_u.ConvertedString("RemoteAction_msg10",file);
                        RemoteAction_msg11=m_u.ConvertedString("RemoteAction_msg11",file);
                        RemoteAction_msg12=m_u.ConvertedString("RemoteAction_msg12",file);
                        news_Checkmsg1=m_u.ConvertedString("news_Checkmsg1",file);
                        news_Checkmsg2=m_u.ConvertedString("news_Checkmsg2",file);
                        news_Checkmsg3=m_u.ConvertedString("news_Checkmsg3",file);
                        news_msg2=m_u.ConvertedString("news_msg2",file);
                        u_msg3=m_u.ConvertedString("u_msg3",file);
			db_msg4=m_u.ConvertedString("db_msg4",file);	
                }
               catch(Exception e1)
                {
                        data.setMessage("Language Exception in action [RemoteCoursesAction] method [doInsert]  is \n"+e1);
			return;
                }
		try
		{       

			String LangFile=data.getUser().getTemp("LangFile").toString();
			User user=data.getUser();

			String username=user.getName();
	// comment lower 7 lines because here we use login name which is unique
	                String firstname=user.getFirstName();
        	        String lastname=user.getLastName();
			String name = null;
			if((firstname==null)|(lastname==null))
                        name = username;
                        else
                        name = firstname + lastname ;


                        String cId=(String)user.getTemp("course_id");
			String Path=data.getServerName();

			ParameterParser pp=data.getParameters();
			int status1=0;
			Date d=new Date();
			String course_id=pp.getString("cid","");
			context.put("c1",course_id);
			
			String RIName = pp.getString("riname","");
			context.put("c2",RIName);
			
			
			String inst_ip=pp.getString("iip","");
			context.put("c4",inst_ip);
						
			
			String inst_name=pp.getString("inm","");
			context.put("c5",inst_name);
						
			String sec_key=pp.getString("sec_key","");
			context.put("sec_key",sec_key);

			String status=pp.getString("order");
			context.put("order",status);


			int startIndex=pp.getInt("updatestartIndex",0);
			pp.add("startIndex",startIndex);			

			String course_s=null;
			String course_p=null;
			if(status.equals("Sell"))
			{
				/**
                                * Check if you have atleast one course to sell
                                */
                                RemoteCourseUtilClient RCUC = new RemoteCourseUtilClient();
                                String courselist = RCUC.getRemoteFileList(cId);
				/**
				* Either you should have "" or two "\n"
				*/
                                if(courselist.equals("")||(courselist.split("\n")).length==2)
                                {
                                        data.setMessage(RemoteAction_msg9);
                                        setTemplate(data,"call,CourseMgmt_User,Configuration.vm");
                                        return;
                                }

				courselist = null;

				status1=1;
				course_s = name;
				course_p = RIName;
			}
			else if(status.equals("Purchase")) 
			{
				status1=0;
				course_p = name;		
				course_s = RIName;		
			}

			int serial=pp.getInt("serial",0);
			pp.add("serial",serial);

			if(inst_ip.equals (Path))
			{
				data.setMessage(RemoteAction_msg7);
				setTemplate(data,"call,CourseMgmt_User,Configuration.vm");
			}
			else 
			{
				 /**
                                * Here we setting template ViewRemote.vm with status
                                */

                                setTemplate(data,"call,CourseMgmt_User,ViewRemote.vm");
                                pp.add("status",status1);

					

				  /**get the expiry date****/

				int curdate=Integer.parseInt(ExpiryUtil.getCurrentDate(""));
	                        String Year=pp.getString("Start_year");
        	                int Year1=Integer.parseInt(Year);
                	        String Month=pp.getString("Start_mon");
                        	int Month1=Integer.parseInt(Month);
                       	 	String date=pp.getString("Start_day");
                        	int Day=Integer.parseInt(date);

                        	String pdate=Year+Month+date;
                        	int pubdate=Integer.parseInt(pdate);
				
				/**
				* Expiry date in standard sql format
				*/
				
				String Expdate=Year+"-"+Month+"-"+date;
				java.sql.Date Expiry_date=java.sql.Date.valueOf(Expdate);
                        	if(pubdate >= curdate)
                        	{
                                	if((Month1==4||Month1==6||Month1==9||Month1==11) && (Day>30))
                                	{
                                        	data.addMessage(news_Checkmsg1);
                                        	return;
                                	}
                                	if(Month1==2)
                                	{
                                        	if((Day>29)&&((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0)&&(Year1%400==0)))
                                        	{
                                                	data.addMessage(news_Checkmsg2);
                                                	return;
                                        	}
                                		else if (Day>28 && (!(((Year1%4==0)&&(Year1%100!=0))||((Year1%100==0
)&&(Year1%400==0)))))
                                                {
                                                	data.addMessage(news_Checkmsg3);
                                                	return;
                                                }
                                	}

					/**
					* Check if request is update insert by seeing value of serial
					*/
					Criteria crit=null;
		
					if(serial == 0)
					{
						/**
						* Case is insert 
						*/
						crit=new Criteria();
						crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,cId);
						crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,course_id);
						crit.add(RemoteCoursesPeer.INSTITUTE_IP,inst_ip);
						crit.add(RemoteCoursesPeer.STATUS,status1);
						/**
						* Check for duplicate entry
						*/			
                		                List l =  RemoteCoursesPeer.doSelect(crit);
						/**
                                                * If List is empty means no duplicate entry/row found
                                                */
                                                if(!l.isEmpty())
						{
                                			data.addMessage(" "+ l.size() + " " +RemoteAction_msg10 + " "+RemoteAction_msg11);
							return;
						}
						l = null;

						crit.add(RemoteCoursesPeer.COURSE_SELLER,course_s);
						crit.add(RemoteCoursesPeer.COURSE_PURCHASER,course_p);
						crit.add(RemoteCoursesPeer.INSTITUTE_NAME,inst_name);
						crit.add(RemoteCoursesPeer.SECRET_KEY,EncryptionUtil.createDigest("MD5",sec_key));
						crit.add(RemoteCoursesPeer.EXPIRY_DATE,Expiry_date);
		        			RemoteCoursesPeer.doInsert(crit);
						pp.add("act","INSERT");
                                		data.addMessage(u_msg3);

						/**
						* Release memory
						*/
						crit=null;
					}
					else
					{

						/**
						* Case is update 
						*/
						crit=new Criteria();
						crit.add(RemoteCoursesPeer.LOCAL_COURSE_ID,cId);
						crit.add(RemoteCoursesPeer.REMOTE_COURSE_ID,course_id);
						crit.add(RemoteCoursesPeer.INSTITUTE_IP,inst_ip);
						crit.add(RemoteCoursesPeer.STATUS,status1);
						crit.add(RemoteCoursesPeer.SR_NO, serial);
						crit.add(RemoteCoursesPeer.COURSE_SELLER,course_s);
						crit.add(RemoteCoursesPeer.COURSE_PURCHASER,course_p);
						crit.add(RemoteCoursesPeer.INSTITUTE_NAME,inst_name);
                		                List l =  RemoteCoursesPeer.doSelect(crit);

						/**
                                                * If List is empty means case is insert not update
                                                */
                                                if(!l.isEmpty())
                                                {
                                                        RemoteCourses rce =(RemoteCourses)l.get(0);
                                                        String sec_keyDb =rce.getSecretKey();
							/**
							* If secret case has not changed don't add sec_key else add
							* This is done as update will send  MD5 which if added 
							* password will be changed which was not intended
							*/
                                                        if(!sec_keyDb.equals(sec_key))
                                                        {
                                                                crit.add(RemoteCoursesPeer.SECRET_KEY,EncryptionUtil.createDigest("MD5",sec_key));
                                                        }
                                                }
                                                else
                                                {
                                                        crit.add(RemoteCoursesPeer.SECRET_KEY,sec_key);
                                                }
						crit.add(RemoteCoursesPeer.EXPIRY_DATE,Expiry_date);
			        		RemoteCoursesPeer.doUpdate(crit);
						/**
						* Check for duplicate entry
						*/			
						Criteria crit1= new Criteria();
						crit1.add(RemoteCoursesPeer.LOCAL_COURSE_ID,cId);
						crit1.add(RemoteCoursesPeer.REMOTE_COURSE_ID,course_id);
						crit1.add(RemoteCoursesPeer.INSTITUTE_IP,inst_ip);
						crit1.add(RemoteCoursesPeer.STATUS,status1);
                                		data.addMessage(db_msg4);
						/**
						* Check for duplicate entry
						*/			
                		                l =  RemoteCoursesPeer.doSelect(crit1);
						/**
                                                * If List is empty means no duplicate entry/row found
                                                */
                                                if(l.size() > 1)
						{
                                			data.addMessage(" "+ (l.size() - 1) + " " +RemoteAction_msg10 + " "+RemoteAction_msg12);
							return;
						}
						/**
						* Release memory
						*/
						crit=null;
						l=null;
					}//update ends
				}//if ends
				else
				{
                                	data.addMessage(news_msg2);
                                        return;
				}
			}//else

		}//try
		catch(Exception e)
		{
		 	data.addMessage("[RemoteCoursesAction]Exception in action [doInsert] \n"+ e);
		}
		finally
		{
                	file = news_msg2 =  u_msg3 = RemoteAction_msg7 = RemoteAction_msg9 = news_Checkmsg1 = news_Checkmsg1 = news_Checkmsg2 = news_Checkmsg3 = RemoteAction_msg10 = RemoteAction_msg11= RemoteAction_msg12= null;

		}	
	
	}//doInsert ends

	

	/**
	* This method creates xml on path (say):
	* brihaspati2/Courses/CS101kishore/Content/Remotecourse/172.28.44.9/CN9awadh/xmlrpc/Unpublished 
	* @param fullpath String
	* @param subtopic String
	* @param fName String
	* @param fromGetAction boolean
	*/
	public static void innerxml(String fullpath ,String subtopic,String fName,boolean fromGetAction) 
	{//start method
		try{//(1)
			
			/**
			* fromGetAction indicates  if request is from action 
			* or from local cache ViewFileContent.java
			* If from action than date is not to specified
			*/

			XmlWriter xmlWriter=null;
			XmlWriter xmlWriter001=null;
			Date date=new Date();

			File descFile1= new File(fullpath +"/"+ subtopic + "__des.xml");
			boolean fileexists1 = descFile1.exists();
			

		 	String topicpath = fullpath.replaceAll("Unpublished","");
			int seqpub = checkTopicXmlFile(topicpath,subtopic,fName);
			int seqaccess = checkTopicXmlFile(topicpath,"/Access__des.xml",fName);
			/**
			* Check if the given course has already entry in published/accssible topic__des.xml file
			* if found return (for publish purpose) i.e, dont do any thing when new file got from gotlist
			* exists already in accessible or visible list 
			*/
			if(!(seqpub == -1 && seqaccess == -1))
			{
				/**
				* If the file exists in visible list and request of innerxml is from Local cache 
				* than remove old date add new date
				*/
				if(seqpub != -1)
				{
					if(!fromGetAction)
					{
              		        		xmlWriter001=TopicMetaDataXmlWriter.WriteXml_New(topicpath , subtopic);
						xmlWriter001.removeElement("File",seqpub);
						TopicMetaDataXmlWriter.appendFileElement(xmlWriter001,fName,fName,date.toLocaleString());
						xmlWriter001.writeXmlFile();
					}
				}
				return;
			}
		        if(!fileexists1)
                	{
				/**
				* Create empty file.xml with start tags <Topic> only
				*/
	                	TopicMetaDataXmlWriter.writeWithRootOnly(descFile1.getAbsolutePath());

				/**
				* Creates a xmlwriter object
				*/
        	         	xmlWriter=new XmlWriter(fullpath +"/"+ subtopic + "__des.xml");

                	}
			else
			/**
			* Check if the given course has already entry in Unpublished/topic__des.xml file
			* if no entry than only make entry
			*/
			{
				int seq = checkTopicXmlFile(fullpath,subtopic,fName);
				try{
              		        	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(fullpath , subtopic);
					xmlWriter.writeXmlFile();
				/**
				* Remove the entry which have same file name
				*/
					if(seq != -1)
					{
              		        		xmlWriter=TopicMetaDataXmlWriter.WriteXml_New(fullpath , subtopic);
						xmlWriter.removeElement("File",seq);
						xmlWriter.writeXmlFile();
					}
				}//try
				catch(Exception e)
				{
					ErrorDumpUtil.ErrorLog("Error Inner xml Remote course Action line 670 =====>"+e);
				}
			} //else
			/**
			* If filename does not exists in .xml file or .xml file does not exist
			*/

			/**
			* Append   to xml data
			*/
			if(fromGetAction)
			{
				TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fName,fName,"----");
			}
			else
			{
				TopicMetaDataXmlWriter.appendFileElement(xmlWriter,fName,fName,date.toLocaleString());
			}

			/**
			* write new entry to xml file
			*/

			xmlWriter.writeXmlFile();
			

		}//try(1)
		catch(Exception e)
		{
			
				ErrorDumpUtil.ErrorLog("\nException in action [RemoteCoursesAction] method [innerxml] is"+e);
		}

	}//method innerxml ends

	/**
	* This method creates xml on path (say):
	* brihaspati2/Courses/CS101kishore/Content/Remotecourse/ 
	* @param path String
	* @param url String
	* @param subtopic String
	* @param cId String
	*/

	public  static void outerxml(String path ,String url,String subtopic,String cId) 
	{//start method
		try{//(1)
			XmlWriter xmlWriter=null;
			File descFile= new File( path +"/"+ "RemoteCourse__des.xml");
			File descDir= new File( path );
			boolean fileexists = descFile.exists();
			boolean direxists = descDir.exists();
			boolean inXML = false;
			/**
			* Role is reudundant Tag hence is left blank
			*/
			String Role = "";
		        if(!fileexists && direxists)
                	{
				/**
				* Create empty file.xml with start tags <Permission> only
				*/
	                	TopicMetaDataXmlWriter.WriteWithRootOnly(descFile.getAbsolutePath());

				/**
				* Creates a xmlwriter object
				*/
        	         	xmlWriter=new XmlWriter(path +"/"+ "RemoteCourse__des.xml");

                	}
			else
			{
     				/**
 	        		* Here we append the new element in tne Xml File
				* Check if the given course has already entry in RemoteCourse__des.xml file
                		* @see TopicMetaDtaXmlWriter in utils
				*/
				String check = url+subtopic+Role+cId;
				String user1=null,topic1=null,role1=null,course1=null,match1=null;


				TopicMetaDataXmlReader remoteRead=new TopicMetaDataXmlReader(path +"/"+ "RemoteCourse__des.xml");
                                Vector Read=new Vector();
				try{
                                	Read=remoteRead.getDetails();


                                	for(int i=0;i<Read.size();i++)
                                	{//for
                                		user1 =((FileEntry) Read.elementAt(i)).getUserName();
                                    	    	topic1 =((FileEntry) Read.elementAt(i)).getTopic();
                                 	        role1 =((FileEntry) Read.elementAt(i)).getRole();
                                  	        course1 =((FileEntry) Read.elementAt(i)).getCourseName();
                                        	match1=user1+topic1+role1+course1;
                                        	if(check.equals(match1))
                                        	{//if
                                        		inXML=true;
							break;
                                        	}//if
					}//for

				/**
				* xmlWriter.writeXmlFile() is called after 
				* TopicMetaDataXmlWriter.WriteXml_New1 because it deletes file after reading
				*/
              		        	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path ,"/RemoteCourse__des.xml");
					xmlWriter.writeXmlFile();
				}//try
				catch(Exception e)
				{
					ErrorDumpUtil.ErrorLog("\nException in action [RemoteCoursesAction] method [outerxml] line [787] is"+e);
				}
			} //else
			if(!inXML || (!fileexists && direxists) )  
			{
				/**
				* Append   to xml data
				*/
				
				TopicMetaDataXmlWriter.appendFile(xmlWriter,subtopic,url,cId,Role);

				/**
				* write new entry to xml file
				*/

				xmlWriter.writeXmlFile();
			}

		}//try(1)
		catch(Exception e)
		{
			
				ErrorDumpUtil.ErrorLog("\nException in action [RemoteCoursesAction] method [outerxml] is"+e);

		}

	}//method outerxml ends

	/**
	* This method will Delete from database the particulars of remote buyer/seller 
	* @param data RunData
	* @param context Context
	*/
  	public void doDelete(RunData data,Context context)
        {
		
		String file ,c_msg9;
		file = c_msg9 = null;
        	try{
			file=data.getUser().getTemp("LangFile").toString();
                	c_msg9=m_u.ConvertedString("c_msg9",file);
        	}
 	       catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action [RemoteCoursesAction] method [doDelete]  is \n"+e1);
			return;
        	}

                try
                {
                        User user=data.getUser();
			ParameterParser pp=data.getParameters();
			String myCourse = (String)data.getUser().getTemp("course_id");
			String conf=(String)user.getTemp("confParam","10");
                        int list_conf=Integer.parseInt(conf);

                        int startIndex=pp.getInt("updatestartIndex",0);


			String DB_subject=pp.getString("DB_subject","");
			int status=pp.getInt("status");
                        pp.add("status",status);
                        Criteria crit=new Criteria();
 			String mid_delete = pp.getString("deleteFileNames","");

			/**
			 * Code to get message from DB_subject
			 */
			 String [] subjectarray = DB_subject.split("@@@@");
                                                      				
                 	if(!mid_delete.equals(""))
		         { //outer 'if'
                         	StringTokenizer st=new StringTokenizer(mid_delete,"^");
			       
		         	for(int j=0;st.hasMoreTokens();j++)
		              	{ //first 'for' loop
		                	String msg_idd=st.nextToken();
			
					/**
					 * get New subject	
					 */
			 
					 DB_subject = subjectarray[j];
				
                			 /*Delete row in database */

					 crit=new Criteria();
                                         crit.add(RemoteCoursesPeer.SR_NO, msg_idd);
			        	 RemoteCoursesPeer.doDelete(crit);


      				}//for
                   
			}//if		     
			
		 	data.addMessage(c_msg9);
			subjectarray = null;
			crit=null;
		}//try
		catch(Exception e)
		{
		 	data.addMessage("[RemoteCoursesAction]Exception in action [doDelete] \n"+ e);
		}
		finally
		{
			file = c_msg9 = null;

		}

	}//method doDelete ends


	/**
	* This method will check in Remote database the particulars of our transaction 
	* @param data RunData
	* @param context Context
	*/
        public void doCheckregistration(RunData data,Context context) 
        {
		String file ,RemoteAction_msg2,RemoteAction_msg3,RemoteAction_msg4,RemoteAction_msg5,RemoteAction_msg6,RemoteAction_msg8,RemoteClient_msg5;
		file =  RemoteAction_msg8 = RemoteAction_msg2 = RemoteAction_msg3 = RemoteAction_msg4 = RemoteAction_msg5 = RemoteAction_msg6 = RemoteClient_msg5  = null;
        	try{
			file=data.getUser().getTemp("LangFile").toString();
                	RemoteAction_msg2=m_u.ConvertedString("RemoteAction_msg2",file);
                	RemoteAction_msg3=m_u.ConvertedString("RemoteAction_msg3",file);
                	RemoteAction_msg4=m_u.ConvertedString("RemoteAction_msg4",file);
                	RemoteAction_msg5=m_u.ConvertedString("RemoteAction_msg5",file);
                	RemoteClient_msg5=m_u.ConvertedString("RemoteClient_msg5",file);
                	RemoteAction_msg6=m_u.ConvertedString("RemoteAction_msg6",file);
                	RemoteAction_msg8=m_u.ConvertedString("RemoteAction_msg8",file);
        	}
 	       catch(Exception e1)
        	{
                	data.setMessage("Language Exception in action [RemoteCoursesAction] method [doCheckregistration]  is \n"+e1);
			return;
        	}

                try
                {
                        User user=data.getUser();
			ParameterParser pp=data.getParameters();
			String myCourse = (String)data.getUser().getTemp("course_id");
			String conf=(String)user.getTemp("confParam","10");
                        int list_conf=Integer.parseInt(conf);

                        int startIndex=pp.getInt("updatestartIndex",0);

			if(startIndex > 0)
			{
				pp.add("startIndex",startIndex - list_conf);			
			}
			else
			{
				pp.add("startIndex",startIndex );			
			}

			int serial=pp.getInt("serial",0);
			pp.add("serial",serial);

			/**
			* AS File is for purchase so status1=0
			*/

                        int status1=0;
                        String cId=pp.getString("cid","");
                        String course_s=pp.getString("csell","");
                        String course_p=pp.getString("cpurch","");
                        String url =pp.getString("iip","");
                        String inst_name=pp.getString("inm","");
                        String sec_key=pp.getString("sec_key","");
                        String status=pp.getString("order");
			String username=user.getName();
		//comment lower 7 lines this is not required because we use login name
	                String firstname=user.getFirstName();
        	        String lastname=user.getLastName();
			String name = null;
			if((firstname==null)||(lastname==null))
                        name = username;
                        else
                        name = firstname + lastname ;
			if(status.equals("Sell"))
			{
				status1=1;
				course_s = name;
			}
			else if(status.equals("Purchase")) 
			{
				status1=0;
				course_p = name;		
			}
                        pp.add("status",status1);
			/**
                        * Check if Remote Turbine is Stop
                        */
			String RemoteTurbineMsg = RemoteCourseUtilClient.ActivateRemoteXmlRpcPort(url);
                        if(RemoteTurbineMsg.equals("ERROR"))
                        {
                                data.addMessage(RemoteAction_msg2);
                                return;
                        }
			/**
			* add lines before message
			* Request to Check Remote Server Registration  send Successfully
			* Response from RemoteServer 
			*/
			//data.addMessage(RemoteClient_msg5);
			//data.addMessage(RemoteAction_msg8);
			Vector params = new Vector();
			params.add(myCourse);
			params.add(cId);
			params.add(course_s);
			params.add(course_p);
			params.add(data.getServerName());
			params.add(new Integer(status1));
			params.add(sec_key);
			String serverURL =  "http://" + url + ":12345/" ;
			int expirytime = RemoteCourseUtilClient.checkRegisteration(serverURL,params);
			params=null;
			/**
			* If expiry time is less than 3
			* means registration failed else successfull  
			* because expirytime will be -1,0,1,2,3  
			*/
			if(expirytime == -1 )
			{
                                data.addMessage(RemoteAction_msg3);
				return ;
			}
			else if(expirytime == 0 )
			{
                                data.addMessage(RemoteAction_msg4);
				return ;
			}
			else if(expirytime == 1)
			{		
                                data.addMessage(RemoteAction_msg5);
				return ;
			}
			else if(expirytime == 2)
			{		
                                data.addMessage(RemoteAction_msg6);
				return ;
			}
			
                        data.addMessage(RemoteAction_msg8);
		}//try
		catch(Exception e)
		{
		 	data.addMessage("[RemoteCoursesAction]Exception in action [doCheckregistration] \n"+ e);
		}
		finally
		{
			
			//file =  RemoteAction_msg8 = RemoteAction_msg2 = RemoteAction_msg3 = RemoteAction_msg4 = RemoteAction_msg5 = RemoteAction_msg6 = RemoteClient_msg5  = null;
			file =  RemoteAction_msg8 = RemoteAction_msg2 = RemoteAction_msg3 = RemoteAction_msg4 = RemoteAction_msg5 = RemoteAction_msg6 = null;
		}

        }//method ends

	/**
	* This method is part of doGet() method whose businesslogic is made into 
	* separate method so that it could be called from Util RemoteCourseUtilClient/Server also
	* This method brings links to all remote courses file in particular course 
	* @param url String
	* @param myCourse String
	* @param cId String
	* @param checkRegisterparams Vector
 	* @return String
	*/

        public String  getRemoteFileList(String url,String myCourse ,String cId ,Vector checkRegisterparams )
        {
		try{
			/**
			* Get Remote File List  serverURL ,cId,myCourse
			*/
			String serverURL =  "http://" + url + ":12345/" ;
			String gotlist = RemoteCourseUtilClient.getRemoteFileList(serverURL,checkRegisterparams);
			if(gotlist.equals("ERRORS") || gotlist.equals("ERRORC"))
			{
                                return gotlist;
			}
			/**
			*  destination path is specified below  
			*/
                        String dest1 = "/Courses"+ "/" + myCourse + "/Content" +  "/Remotecourse/" ;
			String path = TurbineServlet.getServletContext().getRealPath(dest1);
                        String destination = null;
			String fullpath = null;
			String subtopic = null;
			String fName = null;
			/**
			* Now make directories and xml files according to gotlist 
			*/

			String [] ll = gotlist.split("\n") ;
			/**
			* Test if gotlist is empty which is when your subscription is expired
			* Either you should have "" or two "\n"
			*/
                        if(gotlist.equals("")||(gotlist.split("\n")).length==2)
                        {
				return "SUCCESS";
                        }
			String dest2 =   path  + "/"+url+"/"+cId;
			int subtopiclength = -1;
                        for(int k=0 ;k < ll.length ; k++)
                        {//for(01)
					
				subtopic = ll[k];
                        	destination = dest2 + "/" + subtopic  ;
				fullpath =  destination + "/Unpublished";
				subtopiclength = Integer.parseInt(ll[++k]);
				for( ;subtopiclength >0; subtopiclength --)
				{//for(02)
					fName = ll[++k];

			/**
			* destination path is specified below ,make directories if not existing 
			* This is done to store the file recieved from remote server
			*/
					File dest = new File(fullpath);
					dest.mkdirs();
					/**
					* Create ErrorResponse.html here
					*/
					File ff = new File(path + "/ErrorResponse.html");
					if(!ff.exists())
					{
						FileWriter fw = new FileWriter(ff);
						fw.write("Sorry !!! Brihaspati was Unable to Fetch Contents of the File from Server");
						fw.close();
					}//if

			/**
			* Make .xml files if  not already existing 
			*/
			/**
			*  call outerxml to create outer XML
			*/
					outerxml(path ,url,subtopic,cId); 
			/**
			*  call innerxml to create Unpublished  inner XML
			*/
					innerxml(fullpath,subtopic,fName,true);
				}//for(02)
                        }//for(01)
			return "SUCCESS";
		}//try
		catch( Exception e)
		{
			ErrorDumpUtil.ErrorLog("[RemoteCoursesAction]Exception in action [getRemoteFileList] \n"+e );
               		return e.toString();
		}//catch

	}//getRemoteFileList ends


	 /**
         * This is the  method called to check if fileName exists in 'Topic' tag xml File
	 * @param fullpath String
	 * @param topic String
	 * @param fName String
         * @return int -1 when  found and other values 0+ when fileName not found (sequence number is returned)
         */
        public static int  checkTopicXmlFile(String fullpath,String topic ,String fName )
        {
		int seq = -1;
		try{
			String Xml_file="";
                        if(topic.endsWith(".xml"))
                                Xml_file=topic;
                        else
                                Xml_file=topic+"__des.xml";

			String filename=null;
			TopicMetaDataXmlReader remoteRead1=new TopicMetaDataXmlReader(fullpath +"/"+ Xml_file);
                        Vector Read1=new Vector();
                        Read1=remoteRead1.getFileDetails();
                        for(int i=0;i<Read1.size();i++)
                        {//for
                           	filename =((FileEntry) Read1.elementAt(i)).getName();
                                if(fName.equals(filename))
                                {//if
					seq = i;	
					break;
                                }//if
			}//for

		}//try
		catch( Exception e)
		{
			ErrorDumpUtil.ErrorLog("[RemoteCoursesAction]Exception in action [checkTopicXmlFile] \n"+e );
		}//catch
               	return seq;
	}

	 /**
         * This is the  method called to get Institute Name when Remote serverIp Address is given
	 * @param RemoteIp String
         * @return String  
         */
        public static String  getRemoteInstituteName(String RemoteIp )
        {
		try{
			Criteria crit=new Criteria();
			crit.add(RemoteCoursesPeer.INSTITUTE_IP,RemoteIp);
                	List l =  RemoteCoursesPeer.doSelect(crit);
			RemoteCourses rce =(RemoteCourses)l.get(0);
			String inst_name=rce.getInstituteName();
			crit=null;
			l=null;
			rce=null;
			return inst_name;

		}//try
		catch( Exception e)
		{
			ErrorDumpUtil.ErrorLog("[RemoteCoursesAction]Exception in action [getRemoteInstituteName] \n"+e );
               		return e.getMessage();
		}//catch
	}

	 /**
         * This is the default method called when the button is not found
         * @param data RunData
         * @param context Context
         */
        public void doPerform(RunData data,Context context) throws Exception
        {
                String action=data.getParameters().getString("actionName","");
               /**
                 * Passing the value of file from temporary variable
                 * According to selection of Language.
                 */
                MultilingualUtil m_u= new MultilingualUtil();
                String LangFile=(String)data.getUser().getTemp("LangFile");
                if(action.equals("eventSubmit_doDelete"))
                        doDelete(data,context);
                else if(action.equals("eventSubmit_doCheckregistration"))
                        doCheckregistration(data,context);
                else if(action.equals("eventSubmit_doGet"))
                        doGet(data,context);
                else
                {

                        String str=m_u.ConvertedString("c_msg",LangFile);
                        data.setMessage(str);
                }
        }
	
   }//class


			
