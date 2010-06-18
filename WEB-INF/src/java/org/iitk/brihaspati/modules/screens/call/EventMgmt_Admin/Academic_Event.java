package org.iitk.brihaspati.modules.screens.call.EventMgmt_Admin;
/*
 * @(#)Academic_Event.java
 *
 *  Copyright (c) 2007 ETRG,IIT Kanpur.
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
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
import java.io.FileReader;
import java.io.BufferedReader;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.utils.ExpiryUtil;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.iitk.brihaspati.modules.utils.YearListUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 * This class contains code for writing
 * Academic events and holidays in properties file.
 * @author <a href="mailto:singh_jaivir@rediffmail.com ">Jaivir Singh</a>
 */
public class Academic_Event extends SecureScreen
{
	/**
	* @param data RunData
	* @param context Context
	* @return nothing
	*/		
  	public void doBuildTemplate(RunData data,Context context)
	{
		try
		{
		/**
		*Retrieves the current date of the System
		*@see ExpiryUtil in utils
		*/
			String mode=data.getParameters().getString("mode"," ");
			context.put("mode",mode);
			String cdate=ExpiryUtil.getCurrentDate("");
			String cyear=cdate.substring(0,4);
			int cyear1=Integer.parseInt(cyear);
			String cmonth=cdate.substring(4,6);
			context.put("cmonth",cmonth);
			context.put("cyear",cyear);
			Vector year_list=YearListUtil.getYearList();
			context.put("year_list",year_list);
		}
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("The exception in Academic Event file::"+e);
			data.setMessage("See ExceptionLog");
		}
	}
}
