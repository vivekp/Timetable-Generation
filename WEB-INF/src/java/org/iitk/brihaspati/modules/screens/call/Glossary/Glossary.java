package org.iitk.brihaspati.modules.screens.call.Glossary;

/*
 *  @(#)Glossary.java

 *  Copyright (c) 2005 ETRG,IIT Kanpur.
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

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;

/**
 * Grab all the records in a table using a Peer, and
 * place the Vector of data objects in the context
 * where they can be displayed by a #foreach loop.
 *
 * @author <a href="mailto:sharad23nov@yahoo.com">Sharad Singh</a>
 */

public class Glossary extends SecureScreen
 {
  /**
   * Place all the data object in the context
   * for use in the template.
   */
	 
 public void doBuildTemplate( RunData data, Context context )
 
 /**
  * Get the user who is currently login 
  * @param RunData data.
  * @param Context context.
  * @return nothing.
  */
  
   {
    User user = data.getUser();
    if(user.getName().equals("guest"))
      context.put("guest_login","true");
    else
      context.put("guest_login","false");	    
   }
 }  
