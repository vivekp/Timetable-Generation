package org.iitk.brihaspati.modules.screens.call.Question_Bank;

/* @(#)Question_bank.java
 *
 *  Copyright (c) 2004-2006 ETRG,IIT Kanpur.
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur.
 *
 */

import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;

import org.apache.velocity.context.Context;
/**
 * This class contain the code for getting all the Parameters
 * @author <a href="mailto:tarankhan1@yahoo.com ">Tarannum Khan</a>
 * @author <a href="mailto:manju_14feb@yahoo.com ">Mithelesh Parihar</a>
 */

public class Question_bank extends SecureScreen
{
        public void doBuildTemplate(RunData data,Context context)
        {
		User user=data.getUser();
                        /**
                         * Retrieves the course name from temporary variable
                         */
		context.put("username",user.getName());

                String C_Name=(String)user.getTemp("course_name");
		context.put("course",C_Name);

    	}
}                                                           
