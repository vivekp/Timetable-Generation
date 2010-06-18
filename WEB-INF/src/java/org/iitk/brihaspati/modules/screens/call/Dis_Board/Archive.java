package org.iitk.brihaspati.modules.screens.call.Dis_Board;

/*
 * @(#) Archive.java
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
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.parser.ParameterParser;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.ListManagement;
import org.iitk.brihaspati.modules.utils.AdminProperties;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.AssignmentDetail;

/**
 *   This class contains code for all discussions in workgroup
 *   Archive Message with listing
 *   @author  <a href="arvindjss17@gmail.com">Arvind Pal</a>
 */

public class Archive extends SecureScreen

{
        public void doBuildTemplate(RunData data, Context context)
        {
                try
                { 
                        ParameterParser pp=data.getParameters();
                        String UserName=data.getUser().getName();
                        String course_id=(String)data.getUser().getTemp("course_id");
                        String msg_id=(String)data.getUser().getTemp("msg_id");
                        String path1=data.getServletContext().getRealPath("/Courses"+"/"+course_id+"/Archive");
                        File topicDir=new File(path1);
                        String ContentList []=topicDir.list();
                        int t_size=0;
                        context.put("UserName",UserName);
			context.put("cname",(String)data.getUser().getTemp("course_name"));
                        context.put("course_id",course_id);
                       	String path2="";
                       	String path3="";
			if(!topicDir.exists())
                        	topicDir.mkdirs();
			Vector FileViewId_tiopic=new Vector();
                        if(ContentList.length == 0) {
				String LangFile=(String)data.getUser().getTemp("LangFile");
                                String  mssg=MultilingualUtil.ConvertedString("archive_msg3",LangFile);
                                data.setMessage(mssg);
                                return;
                      	}

                        for(int j=0;j<=ContentList.length;j++)
			{
				
				AssignmentDetail assignmentdetail=new AssignmentDetail();
				assignmentdetail.setStudentfile(ContentList[j]);
				path2=path1+"/"+ContentList[j];
				File topicDir1=new File(path2);
			        if(!topicDir1.exists())
					topicDir1.mkdirs();
				String ContentList1 []=topicDir1.list();
				for(int k=0;k<ContentList1.length;k++)
                      		{
					if(ContentList1[k].endsWith(".html"))
					{
						assignmentdetail.setStudentname(ContentList1[k]);
					}
				}
				path3=path2+"/"+"Attachment";
                                File topicDir2=new File(path3);
				if(topicDir2.exists())
				{
					String ContentList2 []=topicDir2.list();
                                	for(int k=0;k<ContentList2.length;k++)
                                	{
						assignmentdetail.setfeedback(ContentList2[k]);
	                                }
				}
				else{
					assignmentdetail.setfeedback("");
				}
				FileViewId_tiopic.add(assignmentdetail);	
				t_size=FileViewId_tiopic.size();
				if(t_size !=0) {
					MultilingualUtil m_u=new MultilingualUtil();
        	        	        String file=(String)data.getUser().getTemp("LangFile");
	                	        String Mode=data.getParameters().getString("mode");
	                        	context.put("mode","All");
					String path=data.getServletContext().getRealPath("/WEB-INF")+"/conf"+"/"+"Admin.properties";
		                        int AdminConf = Integer.valueOf(AdminProperties.getValue(path,"brihaspati.admin.listconfiguration.value"));
		                        context.put("AdminConf",new Integer(AdminConf));
		                        context.put("AdminConf_str",Integer.toString(AdminConf));
		                        int startIndex=pp.getInt("startIndex",0);
		                        String status=new String();
		                        if( t_size!=0)
		                        {
		                                status="notempty";
	        	                        int value[]=new int[7];
	                	                value=ListManagement.linkVisibility(startIndex,t_size,AdminConf);
	                        	        int k=value[6];
		                                context.put("k",String.valueOf(k));
		                                Integer total_size=new Integer(t_size);
						context.put("total_size",total_size);
	                	                int eI=value[1];
	                        	        Integer endIndex=new Integer(eI);
	                                	context.put("endIndex",endIndex);
		                                int check_first=value[2];
		                                context.put("check_first",String.valueOf(check_first));
	        	                        int check_pre=value[3];
	                	                context.put("check_pre",String.valueOf(check_pre));
	                        	        int check_last1=value[4];
	                                	context.put("check_last1",String.valueOf(check_last1));
		                                int check_last=value[5];
		                                context.put("check_last",String.valueOf(check_last));
	        	                        context.put("startIndex",String.valueOf(eI));
	                	                Vector splitlist=ListManagement.listDivide(FileViewId_tiopic,startIndex,AdminConf);
	                        	        context.put("contentvalue",splitlist);     
		                        }	
				}
				context.put("t_size",t_size);
 			}
		}
                catch(Exception e) { ErrorDumpUtil.ErrorLog("Error in Archive.java");    }
        }
}






