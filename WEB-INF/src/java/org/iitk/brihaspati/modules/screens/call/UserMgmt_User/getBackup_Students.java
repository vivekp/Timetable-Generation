package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)getBackup_Students.java
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

import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Vector;

import javax.servlet.ServletOutputStream;

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.GroupUtil;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.UserGroupRoleUtil;

/**
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kuamr Singh</a>
 */

public class getBackup_Students extends SecureScreen
{
     public void doBuildTemplate( RunData data, Context context )
     {
        try{
                String c_id=(String)data.getUser().getTemp("course_id");
		int g_id=GroupUtil.getGID(c_id);
		Vector userList=UserGroupRoleUtil.getUDetail(g_id,3);	
		String filePath=data.getServletContext().getRealPath("/tmp");
                        FileWriter f=new FileWriter(filePath+"/1234567890.txt");
                        f.write("Roll No. ; First Name Last Name ; Email"+"\n");
                        f.write("-----------------------------------------"+"\n");
			CourseUserDetail cUD=new CourseUserDetail();
			for(int i=0;i<userList.size();i++){
				cUD=(CourseUserDetail)userList.elementAt(i);
				String lN=cUD.getLoginName();
				String uN=cUD.getUserName();
				String eMail=cUD.getEmail();
				f.write(lN+";;"+uN+";;"+eMail +"\n");
			}
			f.close();
                FileReader fr=new FileReader(filePath+"/1234567890.txt");
                BufferedReader br=new BufferedReader(fr);
                ServletOutputStream out=data.getResponse().getOutputStream();
                
		//data.getResponse().setHeader("Content-Type","test/html");
		 data.getResponse().setHeader("Content-Type","application/x-download");
                data.getResponse().setHeader("Content-Disposition","inline;filename=studentlist.txt");
                 //data.getResponse().setHeader("Content-Disposition","attachment;filename=studentlist.txt");
		
		String s;
                while((s=br.readLine())!=null)
                {
                        out.write((s+"\n").getBytes());
                }
                fr.close();
		File f2=new File(filePath+"/1234567890.txt");
		f2.delete();
	}//try
        catch(Exception e)
        {
        	data.setMessage("Error in  Page "+ e);
        }
    }//dobuild
}
