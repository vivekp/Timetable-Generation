package org.iitk.brihaspati.modules.screens.call;

/*
 * @(#)SelectHintQuestion.java	
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
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 * 
 */

import java.util.List;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.om.HintQuestionPeer;
 /**
 * In this class, Choose hint question if  
 * <a href="singhsatyapal@rediffmail.com">Satyapal Singh</a>
 * <a href="awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 */

public class SelectHintQuestion extends SecureScreen
{
	public void doBuildTemplate(RunData data, Context context)
	{
		try{
			String loginName=data.getParameters().getString("username");
		        int uid=UserUtil.getUID(loginName);
			context.put("uid",Integer.toString(uid));
			Criteria crit = new Criteria();
			crit.addAscendingOrderByColumn(HintQuestionPeer.QUESTION_ID);
			List question=HintQuestionPeer.doSelect(crit);
			context.put("question",question);
		}
		catch(Exception e)
		{
			data.setMessage("The error in Select Hint Question !! "+e);
		}
	}
}

