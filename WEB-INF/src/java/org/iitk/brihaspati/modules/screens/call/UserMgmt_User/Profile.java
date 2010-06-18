package org.iitk.brihaspati.modules.screens.call.UserMgmt_User;

/*
 * @(#)Profile.java	
 *
 *  Copyright (c) 2006-2007 ETRG,IIT Kanpur. 
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
//Java classes
import java.util.List;
//Apache classes
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
//Brihaspati classes
import org.iitk.brihaspati.om.HintQuestion;
import org.iitk.brihaspati.om.HintQuestionPeer;
import org.iitk.brihaspati.om.TurbineUserPeer;
import org.iitk.brihaspati.om.UserConfiguration;
import org.iitk.brihaspati.om.UserConfigurationPeer;
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
/**
 * @author <a href="mailto:singhnk@iitk.ac.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:singh_jaivir@rediffmail.com">Jaivir Singh</a>
 */


public class Profile extends SecureScreen
{
    /**
     * Adds the information to the context
     */

    public void doBuildTemplate( RunData data, Context context )
    {
	User uName=data.getUser();
	String username=uName.getName();	
	int uid=UserUtil.getUID(username);
	try{
	
	Criteria crt=new Criteria();

	crt.add(TurbineUserPeer.USER_ID,uid);
	List unm=TurbineUserPeer.doSelect(crt);
	context.put("udetl",unm);

	crt=new Criteria();
	crt.addGroupByColumn(HintQuestionPeer.QUESTION_NAME);
	List qw=HintQuestionPeer.doSelect(crt);
	context.put("question",qw);		

	crt=new Criteria();
	crt.add(UserConfigurationPeer.USER_ID,uid);
	List qu=UserConfigurationPeer.doSelect(crt);
	int qid=0;
	for(int i=0;i<qu.size();i++)
	{
		UserConfiguration uc=(UserConfiguration)qu.get(i);
		qid=uc.getQuestionId();
		String ans=uc.getAnswer();
		context.put("qid",qid);
		context.put("ans",ans);
		context.put("conf",uc.getListConfiguration());
		context.put("TaskConf",uc.getTaskConfiguration());
	}

	crt=new Criteria();
	crt.add(HintQuestionPeer.QUESTION_ID,qid);
	List qname=HintQuestionPeer.doSelect(crt);
	context.put("qname",qname);
	}
	catch(Exception e){data.setMessage("The error in profile"+e);}
    }

}


