package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/**
 * @(#)Upload_File.java
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
 */
import java.util.Vector;
import java.util.List;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.services.servlet.TurbineServlet; 
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.apache.turbine.util.parser.ParameterParser;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.QuotaUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.om.TurbineUser;
import org.iitk.brihaspati.om.TurbineUserPeer;
/**
 *This class contain the code for
 *Getting all the Parameters
 *Wich are used for uploading the system file
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a>
 */ 
public class Upload_File extends SecureScreen
 {

   	/**
          *@param data RunData
          *@param context Context
          */
	public void doBuildTemplate(RunData data,Context context)
        {
                try
		{
			ParameterParser pp=data.getParameters();
                	Vector k=new Vector();
			/**
			*Get the user name and 
			*put in the context for 
			*use in templates
			*/
                	String Username=data.getUser().getName();
                	context.put("uname",Username);
			/**
			*Get the file name with the help 
			*of ParameterParser and put in the context
			*/														
			String fileName=pp.getString("files","");
                	context.put("filename",fileName);
                	
			/**
			*Get the Path and
			*Topics contained in the Private Area
			*and also filter the xml file
			*/
			String UserPath=TurbineServlet.getRealPath("/UserArea");
                	File dirHandle=new File(UserPath+"/"+Username+"/"+"/Private");
			String filter[]={"__des.xml"};
                	NotInclude exclude=new NotInclude(filter);
 			String file[]=dirHandle.list(exclude);
                	for(int i=0;i<file.length;i++)
                	{
                        	k.add(file[i]);
                	}
                	context.put("allTopics",k);
                   //     ErrorDumpUtil.ErrorLog("k at line 102==="+k);
			/**
                        *Get the alloted size for uploading the file
                        */
                        File UAreaFile=new File(UserPath+"/"+Username);
                        String repositoryRealPath=TurbineServlet.getRealPath("/Repository");
                        File Rlist=new File(repositoryRealPath+"/"+Username);
                        Criteria crit=new Criteria();
                        int uid=UserUtil.getUID(Username);
                        crit.add(TurbineUserPeer.USER_ID,uid);
                        List lst=TurbineUserPeer.doSelect(crit);
                        long dspace=(((TurbineUser)lst.get(0)).getQuota()).longValue();
                        long ShSize=QuotaUtil.getDirSizeInMegabytes(UAreaFile);
			long msize=0;
			if(Rlist.exists()){
                        	msize=QuotaUtil.getDirSizeInMegabytes(Rlist);
			}
                        long totalsize=ShSize+msize;
			context.put("TUSize",totalsize);
                        long rSize=dspace-totalsize;
                        context.put("SpaceKb",rSize);

		}
		catch(Exception e)
		{
			data.setMessage("The Error in Uploading files on private area !! "+e);
		}
         }
}

