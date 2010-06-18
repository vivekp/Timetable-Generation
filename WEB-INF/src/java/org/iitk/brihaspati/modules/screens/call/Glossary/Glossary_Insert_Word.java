package org.iitk.brihaspati.modules.screens.call.Glossary;

/*
 *  @(#)Glossary_Insert_Word.java

 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur.
 *  All Rights Reserved.

 *  Redistribution and use in source and binary forms, with or
 *  without modification, are permitted provided that the following
 *  conditions are met:

 *  Redistributions of source code must retain the above copyright
 *  notice, this  list of conditions and the following disclaimer.

 *  Redistribution in binary form must reproducuce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.

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

 *  Contributors: Members of ETRG, I.I.T. Kanpur
 */

import java.util.List;
import java.util.Vector;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;

import org.iitk.brihaspati.om.GlossaryPeer;
import org.iitk.brihaspati.om.Glossary;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/* This class contain the code for getting the word and their description
 * of a word whom we want to update.
 *
 * 
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:sharad23nov@yahoo.com">Sharad Singh</a>
 */
public class Glossary_Insert_Word extends SecureScreen
 {
	 
  /**
    * Place all the data object in the context
    * for use in templates.
    * @param data RunData.
    * @param context Context.
    */
	 
	public void doBuildTemplate(RunData data,Context context){
		try{
			String wid = data.getParameters().getString("wid","");
			context.put("w_id",wid);

			String Word=data.getParameters().getString("alpha");
			context.put("alpha",Word);

			String status=data.getParameters().getString("status");
			context.put("stat",status);

			String mode = data.getParameters().getString("mode");
			context.put("mode",mode);

      // Get word and description of a word according their wordid from table peer.
     			if(wid !=""){ 
			Criteria crit = new Criteria();
			crit.add(GlossaryPeer.WORD_ID,wid);
			List v=GlossaryPeer.doSelect(crit);
			context.put("GwList",v);	

			Glossary ele=(Glossary)v.get(0);
			String word=ele.getWord().toString();
			context.put("word",word);

			String def=new String(ele.getDefinition());
			context.put("description",def);
			}
		}
		catch(Exception e){data.setMessage("The error in Insert Screen "+e);}
	}
 } 
