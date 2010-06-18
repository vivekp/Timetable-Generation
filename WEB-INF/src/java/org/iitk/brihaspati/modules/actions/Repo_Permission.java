package org.iitk.brihaspati.modules.actions;
/*
 * @(#) Repo_Permission.java
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur.
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
import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.om.security.User;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import java.io.FileOutputStream;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.CoursesPeer;
import java.util.List;
import org.iitk.brihaspati.modules.utils.SystemIndependentUtil;




/**
*@author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
*@author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
*/

/**
*This class contain the code for given the permission,
*Deleting the permission
*/

public class Repo_Permission extends SecureAction
{
	MultilingualUtil Mutil=new MultilingualUtil();

    	/**
    	* Method for given permission by the Content Author
    	* @param data RunData instance
    	* @param context Context instance
    	*/
        public void doPermission(RunData data,Context context) throws Exception
        {//do 	

		String file=data.getUser().getTemp("LangFile").toString();
		String perm1=Mutil.ConvertedString("Repo_perm1",file);
                String perm2=Mutil.ConvertedString("Repo_perm2",file);
                String perm=Mutil.ConvertedString("check_user",file);
                String perm4=Mutil.ConvertedString("check_course",file);

		try
		{//try
			/**
                	*Get the User name and Parameters
                	*by Using the ParameterParser and
                	*Put in the context for using in the templates
                	*/

			User user=data.getUser();
			String authorname=user.getName();
               		context.put("authorname",authorname);
               		ParameterParser pp=data.getParameters();
			String username=pp.getString("UNAME","");
			context.put("username",username);
			String coursename=pp.getString("CNAME","");
			String topicname=pp.getString("tname");
			context.put("contentlist",topicname);
			String role=pp.getString("group1","");
			context.put("role",role);
			String stat=pp.getString("stats","");
			String statvalue=pp.getString("statvalue","");
			context.put("status",stat);
			Vector Read=new Vector();
			XmlWriter xmlwriter=null;
			String check=username+topicname+role+coursename;
			if(stat.equals("permission") || stat.equals("PermissionRecieve")) //start of main if
			{
				//Get the path for using the different cases
               			String UserPath=data.getServletContext().getRealPath("Repository");
				String Path=data.getServletContext().getRealPath("/UserArea");
				String filePath=data.getServletContext().getRealPath("/Courses");
				// For Given XML
				File authordesc= new File(UserPath+"/"+authorname);
				File authordesc1= new File(UserPath+"/"+authorname +"/" + topicname + "/"+topicname+"__des.xml");
		//		File authordesc1= new File(authordesc +"/" + topicname + "/"+topicname+"__des.xml");
				
				String Apath=UserPath+"/"+authorname;
				String Upath2= filePath+"/"+coursename+"/Content/Permission";
				String entry="";
				String Upath="",Upath1="";
				File userdesc=null;
				File userdesc1=null;
				File userdesc2=null;
				File userdesc3=null;
				boolean found=true;
				//Here checking for the user exists or not (legal user)
				if(!username.equals(""))
				{//if1
					if(TurbineSecurity.accountExists(username))
					{//if2
						entry=username;
						userdesc= new File(UserPath+"/"+entry);
					 	userdesc1= new File(Path+"/"+entry+"/Private");
						Upath=UserPath+"/"+entry;
						Upath1= Path+"/"+entry+"/Private";
					}//if2
					else
					{
						data.setMessage(perm);
						found=false;
			 		}
						
				}//if1
				else
				{//else
					//Here checking for the course exists or not (legal course)
					Criteria crit = new Criteria();
					crit.add(CoursesPeer.GROUP_NAME,coursename);
					List C_size=CoursesPeer.doSelect(crit);
					if(!(C_size.size()==0))
					{
						userdesc2= new File(filePath+"/"+coursename+"/Content/Permission" );
						userdesc3= new File(filePath+"/"+coursename+"/Content/Permission/"+ authorname+"/"+topicname+"/Unpublished");
					}
					else
					{
						data.setMessage(perm4);
						found=false;
			 		}
				}//else
				if(found)
				{//if of user and courses
					boolean myflag=false;
					// Create permission directory
					if(!coursename.equals(""))
					{//if3
						userdesc3.mkdirs();
					}//if3
					File AdescFile= new File(authordesc+"/permissiongiven__des.xml");
					/**
					*Here we creating new XmlFile
					*@see TopicMetaDataXmlWriter in utils
					*/
					if(!AdescFile.exists())
					{//if4
						TopicMetaDataXmlWriter.WriteWithRootOnly(AdescFile.getAbsolutePath());
						xmlwriter=new XmlWriter(authordesc+"/permissiongiven__des.xml");
						
					}//if4
			
					/**
					*Here we reading the XmlFile
					*@see TopicMetaDataXmlReader in utils
					*checking the Existing permission (username,topicname)
					*/	
					else
					{//else1
						TopicMetaDataXmlReader permissionRead;
		       				permissionRead=new TopicMetaDataXmlReader(authordesc+"/permissiongiven__des.xml");
		       				Read=new Vector();
		        			Read=permissionRead.getDetails();
						for(int i=0;i<Read.size();i++)
						{//for
							String user1 =((FileEntry) Read.elementAt(i)).getUserName();
							String topic =((FileEntry) Read.elementAt(i)).getTopic();
							String role1 =((FileEntry) Read.elementAt(i)).getRole();
							String course =((FileEntry) Read.elementAt(i)).getCourseName();
							String match=user1+topic+role1+course;	
							if(check.equals(match))
							{//if
								myflag=true;
							}//if
							data.setMessage(perm2);	
						}//for

						/**
						*Here we appending the element in the permissionXmlFiles
						*@see Xmlwriter in utils
						*/
						if(myflag==false)
		  				{
							String xmlFile="/permissiongiven__des.xml";
							xmlwriter=TopicMetaDataXmlWriter.WriteXml_New1(Apath,xmlFile);
		   				}//if
					}//else1
			
					/**
					*Here we appending the element in the permissionXmlFiles
					*@see Xmlwriter in utils
					*/
					if(myflag==false)
					{
	                			TopicMetaDataXmlWriter.appendFile(xmlwriter,topicname,entry,coursename,role);
						xmlwriter.writeXmlFile();
						data.setMessage(perm1);
						data.setScreenTemplate("call,Repository_Mgmt,RepositoryList.vm");
					}//if
					
					if(statvalue.equals("PermissionRecieve"))
					{//ifRece
						// Get the path for the creating xml files
						File descFile=null;
                       				if(role.equalsIgnoreCase("Instructor"))
						{
							descFile =new File(userdesc1+"/permissionReceive__des.xml");
						}
                        			if (role.equalsIgnoreCase("Author"))
						{
							descFile= new File(userdesc+"/permissionReceive__des.xml");
						}
						if(role.equalsIgnoreCase("Courses"))	
						{
							descFile= new File(userdesc2+"/permissionReceive__des.xml");
						}
						/**
						* Here we creat the three new XmlFile(blank);
						*@see TopicMetaDataXmlWriter
						*/
                				if(!descFile.exists())
						{//if
                					TopicMetaDataXmlWriter.WriteWithRootOnly(descFile.getAbsolutePath());
							if(role.equals("Instructor"))
							xmlwriter=new XmlWriter(userdesc1+"/permissionReceive__des.xml");
							if(role.equals("Author"))
							xmlwriter=new XmlWriter(userdesc+"/permissionReceive__des.xml");
							if(role.equals("Courses"))
							xmlwriter=new XmlWriter(userdesc2+"/permissionReceive__des.xml");
						}//if
						/**
						*Here we appending the element in the permission XmlFile
						*in different remote Receive xmlfiles
						*/
						else
						{//else2
						/**
						*Here we appending the element in the permissionXmlFiles
						*@see Xmlwriter in utils
						*/
							if(myflag==false)
							{
					       			String xmlFile="/permissionReceive__des.xml";
								if(role.equals("Instructor"))
								xmlwriter=TopicMetaDataXmlWriter.WriteXml_New1(Upath1,xmlFile);
								if(role.equals("Author"))
								xmlwriter=TopicMetaDataXmlWriter.WriteXml_New1(Upath,xmlFile);
								if(role.equals("Courses"))
								xmlwriter=TopicMetaDataXmlWriter.WriteXml_New1(Upath2,xmlFile);
				   			}//if
						}//else2  
						/**
						*Here we appending the element in the permissionXmlFiles
						*@see Xmlwriter in utils
						*/
						if(myflag==false)
						{//if
               						TopicMetaDataXmlWriter.appendFile(xmlwriter,topicname,authorname,coursename,role);
               						xmlwriter.writeXmlFile();
						}//if
					
					}//end of ifRece
					
					// Here we coping the (topic)desc xml File
					if((myflag==false) &&(!coursename.equals("")))
					{//if	
						int is= 0;
						FileInputStream fin = new FileInputStream(authordesc1);
						FileOutputStream fout = new FileOutputStream(userdesc3 + "/" + topicname +"__des.xml");
						do{
							is = fin.read();
							if(is != -1)
							{
			 					fout.write(is);
							}
						}while (is != -1); 
						fin.close();
						fout.close();
						/**
						*release memory
                               			*/
                               			fin=null;
                               			fout=null;
					}//if
				}//end if for user or course

			}//end of main if
		
		} //try ends
		catch(Exception e){data.setMessage("error in Permission"+e);}
	}//do permission
	
	/**
        * Method for Deleting Permissions (receive to given)
        * @param data RunData instance
        * @param context Context instance
        */

	public void doRemoveperm_receive(RunData data,Context context)
        {
        	String file=data.getUser().getTemp("LangFile").toString();
        	String perm3=Mutil.ConvertedString("Repo_perm3",file);
        	String perm4=Mutil.ConvertedString("Repo_perm4",file);
                try
		{
			/**
                        *Get the User name and Parameters
                        *by Using the ParameterParser and
                        *Put in the context for using in the templates
                        */

                        User user=data.getUser();
                        ParameterParser pp=data.getParameters();
                        String authorname=user.getName();
                        int list[]=pp.getInts("permdel");
			int [] newlist = list;
                        String mode=pp.getString("mode","");
                        context.put("mode",mode);
                        String empty=pp.getString("empty","");
                        context.put("status","permisssionreceive");
                        XmlWriter xmlWriter=null;
                        String UserPath=data.getServletContext().getRealPath("/Repository");
                        String UserPath1=data.getServletContext().getRealPath("/UserArea");
                        String path=UserPath+"/"+authorname;
                        String path2=UserPath1+"/"+authorname+"/Private";
                        /**
                        * this will give seqquence number  of permissionRecieve to delete it
                        */

                        int seqcheck=0;
  			/**
                        * deleting the particular topic from the XmlFile.
                        *@see TopicMetaDataXmlWriter in utils.
                        */
                        int len=0;
                        if(list == null)
                        {
                        	len = 0;
                                data.setMessage(perm4);
                        }//if1
                        else
                        {
                        // Get the lenght of array.
                        len = list.length;
                        File filepath=null;
                        TopicMetaDataXmlReader PermissionRead=null;
                        String role="";
                        /**
                        * Read permissiongiven xml of login user
                        * select path of remote permissionRecieve.xml  according to role
                        */
                        if(mode.equals("fromauthor"))
                        PermissionRead=new TopicMetaDataXmlReader(path+"/permissionReceive__des.xml");
                        else
                        PermissionRead=new TopicMetaDataXmlReader(path2+"/permissionReceive__des.xml");

                       	Vector Read=new Vector();
                      	Read=PermissionRead.getDetails();
                       	for(int num=0;num<len;num++)
                       	{
                        	String s1 =((FileEntry) Read.get(newlist[num])).getUserName();
                        	String s2 =((FileEntry) Read.get(newlist[num])).getTopic();
                        	role =((FileEntry) Read.get(newlist[num])).getRole();
 				String s=authorname+s2+role;
                        	/**
                        	* Use loginname as loginname will be available
                        	* in Instructor's and author permisionRecieve.xml   (Remote)
                        	* Add  'loginname' 'topicname'and 'role'
                        	* We read username and topic name &role ;from username we get path1
                        	* that is path of person to whom you want to delete permission
                        	*/
                        	String path1=UserPath+"/"+s1;
                        	TopicMetaDataXmlReader PermissionRead1=null;
                        	PermissionRead1=new TopicMetaDataXmlReader(path1+"/permissiongiven__des.xml");
                        	Vector Read1=new Vector();
                        	Read1=PermissionRead1.getDetails();

                        	for(int i=0;i<Read1.size();i++)
                        	{
                                	String s3 =((FileEntry) Read1.elementAt(i)).getUserName();
                                        String s4 =((FileEntry) Read1.elementAt(i)).getTopic();
                                        String s5 =((FileEntry) Read1.elementAt(i)).getRole();
                                        String ss=s3+s4+s5;
                                        if(s.equals(ss))
                                        {
                                        /**
                                        * To get sequence number we take help of loop count at which match was found
                                        * so Check if topic name ,loginname of user,s permisiongiven.xml and
                                        * permisiongiven.xml topic name and username are same or not
                                        */
                                        seqcheck=i;
                                        break;
                                        }//if
                                }//for

				/**
                                * We are deleting particular entry from  permissiongiven.xml
				* Here we are creating a new list that has elements whose new value is given by
				* list[num]= list[num] - num ;
				* Concept here is as we delete one entry from permissionReceive__des.xml 
				* it will have one less sequence every time  
				*/
                                newlist[num]= newlist[num] - num ;
                                String xmlFile=null;
                                xmlFile="/permissionReceive__des.xml";
                                if(role.equals("Author"))
                                {
                                	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path,xmlFile);
                                }
                                else if(role.equals("Instructor"))
                                {
                                	xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path2,xmlFile);
                                }
                                xmlWriter.removeElement("Topic",newlist[num]);
                                xmlWriter.writeXmlFile();

 				 /**
                                 * if this is last record in xml file delete the file
                                 */
                                 if(empty.equals("true") && num == len -1 )
                                 {
                                	 if(role.equals("Author"))
                                         {
                                        	 filepath=new File(path+"/permissionReceive__des.xml");
                                         }
                                         else if(role.equals("Instructor"))
                                         {
                                         	filepath=new File(path2+"/permissionReceive__des.xml");
                                         }
                                         filepath.delete();
                                 }//if

                                /**
                                * We are deleting particular entry from  permissiongiven.xml
                                */
                                xmlFile="/permissiongiven__des.xml";
                                xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path1,xmlFile);
                                xmlWriter.removeElement("Topic",seqcheck);
                                xmlWriter.writeXmlFile();
                                /**
                                * if this is last record in xml file delete the file
                                */
				if(num == len -1)
				{
                        		Read1=PermissionRead1.getDetails();
					if(Read1.size() == 1)
					{
                                		filepath = new File(path1+"/permissiongiven__des.xml");
                                        	filepath.delete();
					}
				}//if
                        }//for
                        data.setMessage(perm3);
                }//else
         }//try
         catch(Exception e)
         {
         	data.setMessage("Exception in doRemoveperm_receive method in Permission Delete action"+e);
         }

   }// Perm_recevice

	/**
        * Method for Deleting Permission(given to receive) by the content author
        * @param data RunData instance
        * @param context Context instance
        */

	public void doRemoveperm_given(RunData data,Context context)
        {
		String file=data.getUser().getTemp("LangFile").toString();
                String perm3=Mutil.ConvertedString("Repo_perm3",file);
                String perm4=Mutil.ConvertedString("Repo_perm4",file);
		try{
			/**
                        *Get the User name and Parameters
                        *by Using the ParameterParser and
                        *Put in the context for using in the templates
                        */

			User user=data.getUser();
                	ParameterParser pp=data.getParameters();
                	String authorname=user.getName();
                	int list[]=pp.getInts("permdel");
			int [] newlist = list;
                	String mode=pp.getString("mode","");
                	context.put("mode",mode);
                	String empty=pp.getString("empty","");
                	context.put("status","permisssionreceive");
                	XmlWriter xmlWriter=null;
                	String UserPathR=data.getServletContext().getRealPath("/Repository");
                	String UserPathU=data.getServletContext().getRealPath("/UserArea");
                	String path=UserPathR+"/"+authorname;
                	String UserPathC=data.getServletContext().getRealPath("/Courses");
                        File filepath = null;
                	/**
                	* this will give seqquence number  of permissionRecieve to delete it
                	*/
                	int seqcheck=0 ;
                	//List seqcheck=0 ;
			/**
                        * deleting the particular topic from the XmlFile.
                        *@see TopicMetaDataXmlWriter in utils.
                        */
                        int len=0;

                        if(list == null)
                        {
                        	len = 0;
                                data.setMessage(perm4);
                        }
                        else
                        {
                        	len = list.length;
                                TopicMetaDataXmlReader PermissionRead=null;
                                /**
                                * Read permissiongiven xml of login user
                                */
                                PermissionRead=new TopicMetaDataXmlReader(path+"/permissiongiven__des.xml");
                                Vector Read=new Vector();
                                Read=PermissionRead.getDetails();
                                String s="",role="",coursename="",path4="",s4="",pathdir="",path2="";
                                for(int num=0;num<len;num++)
                                {
                                        //String path2="";
                                	coursename =((FileEntry) Read.get(newlist[num])).getCourseName();
                                        role =((FileEntry) Read.get(newlist[num])).getRole();
                                        String suser =((FileEntry) Read.get(newlist[num])).getUserName();
                                        String stopic =((FileEntry) Read.get(newlist[num])).getTopic();
                                        /**
                                        * Use loginname as loginname will be available
                                        * in Instructor's permisionRecieve.xml   (Remote)
                                        * Add  'loginname' and 'topicname'
                                        * We read username and topic name &role ;from username we get path2
                                        * that is path of person/Course to whom you want to delete permission
                                        */
                                        s=authorname+stopic;
                                        /**
                                        * select path of remote permissionRecieve.xml  according to role
                                        */

                                       // String path2="";
                                        if(role.equals("Instructor"))
                                        path2=UserPathU+"/"+suser+"/Private";

                                        else if(role.equals("Author"))
                                        path2=UserPathR+"/"+suser;
                                        else if(role.equals("Courses"))
                                        path2=UserPathC+"/"+coursename+"/"+"Content"+"/" + "Permission";

                                        /**
                                        * Here we are reading permissionRecieve.xml of instructor (Remote)
                                        */
                                         TopicMetaDataXmlReader PermissionRead1=null;
                                         PermissionRead1=new TopicMetaDataXmlReader(path2+"/permissionReceive__des.xml");
                                         Vector Read1=new Vector();
                                         Read1=PermissionRead1.getDetails();
					
					
                                         for(int i=0;i<Read1.size();i++)
                                         {
                                              String s3 =((FileEntry) Read1.elementAt(i)).getUserName();
                                              s4 =((FileEntry) Read1.elementAt(i)).getTopic();
                                              String ss=s3+s4;
                                              /**
                                              * To get sequence number we take help of loop count at which match was found
                                              * so Check if topic name ,loginname of user,s permisiongiven.xml and
                                              * remote permisionRecieve.xml topic name and username are same or not
                                              */
                                              if(s.equals(ss))
                                              {
                                       			seqcheck=i;
                                              		break;
                                              }//if
                                        }//for
					/**
                                        * We are deleting particular entry from  permissiongiven.xml
                                        */
					/**
                                	* Here we are creating a new list that has elements whose new value is given by
                                	* list[num]= list[num] - num ;
                                	* Concept here is as we delete one entry from permissiongiven.xml
                                	* it will have one less sequence every time
                                	*/
					
                        		newlist[num]= newlist[num] - num ;
                                        String xmlFile=null;
                                        xmlFile="/permissiongiven__des.xml";
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path,xmlFile);
                                        xmlWriter.removeElement("Topic",newlist[num]);
                                        xmlWriter.writeXmlFile();
                                        /**
                                        * if this is last record in xml file delete the file
                                        */
                                        if(empty.equals("true")&& num == len -1 )
                                        {
                                        	filepath = new File(path+"/permissiongiven__des.xml");
                                                filepath.delete();
                                        }//if
					/**
					*Here we deleting the particular topic
					* which is selected form the courses in xml
					*/

					if(role.equals("Courses"))
					{
						path4=path2+"/"+authorname+"/"+s4;
						File f=new File(path4);
                        			SystemIndependentUtil.deleteFile(f);
						/**
						*Here we deleting the AuthorDir
						*in Courses if there is no Permission
						*/
						SystemIndependentUtil.deleteEmptDir(path2, authorname);
					}//if
                                        /**
                                        * We are deleting particular entry from remote permissionRecieve.xml
                                        */
                                        xmlFile="/permissionReceive__des.xml";
                                        if(role.equals("Author"))
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path2,xmlFile);
                                        else if(role.equals("Instructor") )
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path2,xmlFile);
                                        else if( role.equals("Courses") )
                                        xmlWriter=TopicMetaDataXmlWriter.WriteXml_New1(path2,xmlFile);
                                        xmlWriter.removeElement("Topic",seqcheck);
                                        xmlWriter.writeXmlFile();
                                	/**
                                	* if this is last record in xml file delete the file
                                	*/
					if(num == len -1)
					{
                        			Read1=PermissionRead1.getDetails();
						if(Read1.size() == 1)
						{
							if((role.equals("Author"))||(role.equals("Instructor")))
							{
                                				filepath = new File(path2+"/permissionReceive__des.xml");
							}

                                        		filepath.delete();
						}
					}//if
		
                                 }//for
                                 data.setMessage(perm3);
                           }//else
                }//try
		catch(Exception e)
                {data.setMessage("Exception in Permission Given" + e.getMessage());}
	}//permgiven

/**
*This is the default method called when the button is not found
*@param data RunData
*@param context Context
*/
public void doPerform(RunData data,Context context) throws Exception
	{
		String file=data.getUser().getTemp("LangFile").toString();
                String nfind=Mutil.ConvertedString("action_msg",file);
		ParameterParser pp=data.getParameters();
		String action= pp.getString("actionName","");
			context.put("action",action);
                if(action.equals("eventSubmit_doPermission"))
                	doPermission(data,context);
                else if(action.equals("eventSubmit_doRemoveperm_receive"))
                	doRemoveperm_receive(data,context);
                else if(action.equals("eventSubmit_doRemoveperm_given"))
                	doRemoveperm_given(data,context);
                else
			data.setMessage(nfind);
       	}
}

