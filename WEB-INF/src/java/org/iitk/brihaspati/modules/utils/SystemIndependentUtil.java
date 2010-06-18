package org.iitk.brihaspati.modules.utils;

/*
 * @(#)SystemIndepedentUtil.java	
 *
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
 */

import java.io.File;
import java.util.Vector;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;


/**
* This class contains code for removal of directory, xml descriptor entry and empty dir
* @author <a href=nksngh_p@yahoo.co.in>Nagendra Kumar Singh</a>
*/
public class SystemIndependentUtil 
{
	/**
        * This Method is responsible for deleting the file and dir 
        * @param f File
        */
	public static void deleteFile(File f)
	{
		int i=0;
		if(f.isDirectory())
		{
			File file[]=f.listFiles();
			while(i<file.length)
			{
				deleteFile(file[i]);
				i++;
			}	
		}
		f.delete();	
	}//method system
	/**
        * This Method is responsible for deleting the entry of xml descriptor file
        * in which permission is removed
        * @param Path String
        * @param xmlFile String
        * @param match String
        * @param username String
        * @return Vector
        */

	public static Vector DeleteXmlEntry(String Path,String xmlFile,String match,String username)
	{
               		Vector Read=new Vector();
		try
		{
               		/**
                	*Here we read the xml file
                	*and compare with the topicname,username,course_id which we want to delete
                	*/
                	int seq =-1;
                	TopicMetaDataXmlReader tr =new TopicMetaDataXmlReader(Path+"/"+xmlFile);
               		Read=tr.getDetails();
			if(Read.size()!=0)
                        {
               			for(int n=0;n<Read.size();n++)
               			{
               	 			String topic =((FileEntry) Read.elementAt(n)).getTopic();
                        		String uname =((FileEntry) Read.elementAt(n)).getUserName();
                        		String coursename =((FileEntry) Read.elementAt(n)).getCourseName();
					String R=null;
                        		if (uname.equals(""))
                        			R=topic+username+coursename;
					else
                        			R=topic+uname+coursename;
                        		if(match.equals(R))
                        		{
                                		seq=n;
                                		break;
                        		}//if
                		}//for
			}//if
              		
			XmlWriter xw = TopicMetaDataXmlWriter.WriteXml_New1(Path,xmlFile);
              		xw.removeElement("Topic",seq);
              		xw.writeXmlFile();
		}//try
		catch(Exception e)
		{
                        ErrorDumpUtil.ErrorLog("The Error in Publish Action in DeleteXmlEntry  !! "+e);
                }
		return Read;
	}//method delete
	/**
        * This Method is responsible for deleting the author dir
        * in which permission given when it empty
        * @param Path String
        * @param username String
        */
        public static void deleteEmptDir(String Path, String username){
                String pathdir=Path+"/"+username;
                File fdir=new File(pathdir);
                Vector y =new Vector();
                String ContentList []=fdir.list();
                for(int j=0;j<ContentList.length;j++)
                        {
                                y.add(ContentList[j]);
                        }
                if(y.size()==0)
                	deleteFile(fdir);
        }

}//class

