package org.iitk.brihaspati.modules.screens.call.Backups;

/*
 * @(#)getZipfile.java	
 *
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
//java
import java.io.File;
import java.io.FileInputStream;

//servlet
import javax.servlet.ServletOutputStream;

//turbine
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.turbine.services.servlet.TurbineServlet;

//brihaspati
import org.iitk.brihaspati.modules.screens.call.SecureScreen;


public class getZipfile extends  SecureScreen{

	private String fileID=null;
	private ServletOutputStream out;
	public void doBuildTemplate( RunData data, Context context )
	 {
		
        	try{
			ParameterParser pp=data.getParameters();
			String fileID=pp.getString("fName","");
			String filePath= null;
			filePath=TurbineServlet.getRealPath("/BackupData")+"/"+fileID;
			File fileHnd=new File(filePath);
			FileInputStream fis=new FileInputStream(filePath);		
			out=data.getResponse().getOutputStream();
                        String mimeTp="application/x-download";
                        data.getResponse().setHeader("Content-Type",mimeTp);
                        data.getResponse().setHeader("Content-Disposition","inline;filename="+fileID);
			int readCount;
			byte[] buf=new byte[4*1024];	
			while((readCount=fis.read(buf)) !=-1)
			{
				out.write(buf,0,readCount);
			}						
			fis.close();
			fileHnd.delete();

		}
		catch(Exception ex)
		{
			data.setMessage("The error in getZip File !!"+ex);
		}
				
	}
}
