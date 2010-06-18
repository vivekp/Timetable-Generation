package org.iitk.brihaspati.modules.screens.call.Repository_Mgmt;

/*
 * @(#)UploadSCO.java
 *
 *  Copyright (c) 2005-2006,2009 ETRG,IIT Kanpur.
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


import java.util.Vector;
import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.apache.turbine.om.security.User;
import java.io.File;
import org.iitk.brihaspati.modules.screens.call.SecureScreen_Author;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.NotInclude;
import org.iitk.brihaspati.modules.utils.QuotaUtil;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.CommonUtility;
import org.iitk.brihaspati.om.TurbineUser;
import org.iitk.brihaspati.om.TurbineUserPeer;
/**
* @author <a href="mailto:seema_020504@yahoo.com">Seema Pal</a>
* @author <a href="mailto:kshuklak@rediffmail.com">Kishore kumar shukla</a>
* @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
*/


public class UploadSCO extends SecureScreen_Author
{
	/**
        *@param data RunData
        *@param context Context
        */

	public void doBuildTemplate(RunData data,Context context)
     	{
		try{
		/**
                *Get the UserName and
                *put it in the context
                *for template use
                */
		User user=data.getUser();
                String authorname=user.getName();
		context.put("authorname",authorname);
		/**
                *Retrieve the Parameters
                *by using the Parameter Parser
                */
	
                Vector v=new Vector();
		ParameterParser pp=data.getParameters();
                Vector k=new Vector();
		String fileName=pp.getString("files","");
                String UserPath=data.getServletContext().getRealPath("/Repository");
		File dirHandle=new File(UserPath+"/"+authorname);
		/**
		* getting the list of existing dir(topics) 
		* filtering the des.XmlFiles
		*/
		String filter[]={"__des.xml"};
		NotInclude exclude=new NotInclude(filter);
		String author[]=dirHandle.list(exclude);
              	for(int i=0;i<author.length;i++)
               	{
               		k.add(author[i]);
               	}
               	context.put("allTopics",k);
		String Path=TurbineServlet.getRealPath("/UserArea");
		File PArea=new File(Path+"/"+authorname);
		Criteria crit=new Criteria();
		int uid=UserUtil.getUID(authorname);
		crit.add(TurbineUserPeer.USER_ID,uid);
		List lst=TurbineUserPeer.doSelect(crit);
		long dspace=(((TurbineUser)lst.get(0)).getQuota()).longValue();
                long msize=QuotaUtil.getDirSizeInMegabytes(PArea);
                long dsize=QuotaUtil.getDirSizeInMegabytes(dirHandle);
                long totalsize=dsize+msize;
		context.put("TUSize",totalsize);
                long rSize=dspace-totalsize;
                context.put("SpaceKb",rSize);
		}catch(Exception ex){data.setMessage("The error in UploadSco"+ex);}

        }
}    





