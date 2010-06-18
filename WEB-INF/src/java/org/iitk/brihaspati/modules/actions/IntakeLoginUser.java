package org.iitk.brihaspati.modules.actions;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2004 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Turbine" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Turbine", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */


// classes that LoginUser pulls from
// Turbine Classes
import org.apache.turbine.Turbine;
import org.apache.turbine.TurbineConstants;

import org.apache.turbine.modules.actions.VelocityAction;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import org.apache.turbine.services.security.TurbineSecurity;
import org.apache.turbine.om.security.TurbineUser;
import org.apache.turbine.om.security.User;

import org.apache.turbine.util.security.DataBackendException;
import org.apache.turbine.util.security.TurbineSecurityException;
import org.apache.turbine.util.security.AccessControlList;

// Intake tool
import org.apache.turbine.services.intake.IntakeTool;
import org.apache.turbine.services.intake.model.*;

// Logging libraries
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IntakeLoginUser extends VelocityAction
{

	private static Log log = LogFactory.getLog(IntakeLoginUser.class);

    /**
     * Updates the user's LastLogin timestamp, sets their state to
     * "logged in" and calls RunData.setUser() .  If the user cannot
     * be authenticated (database error?) the user is assigned
     * anonymous status and, if tr.props contains a TEMPLATE_LOGIN,
     * the screenTemplate is set to this, otherwise the screen is set
     * to SCREEN_LOGIN
     *
     * <p>Note: Turbine clears the session before calling this
     * method</p>
     *
     * @author <a href="mailto:painter@kiasoft.com">Jeffery Painter</a>
     *
     * @param data Turbine information.
     * @exception Exception, a generic exception.
     */
    public void doPerform( RunData data, Context context )
        throws Exception
    {

		String username = "";
		String password = "";

		// Test for valid intake data
		IntakeTool intake = (IntakeTool) context.get("intake");
		if ( !intake.isAllValid() )
		{
			log.info("Intake has found an error with user data");
			return;
		}
		else
		{
			log.info("Intake says we are ok");
			Group login = intake.get("Login", IntakeTool.DEFAULT_KEY);
			username = login.get("Username").toString();
			password = login.get("Password").toString();
		}

        User user = null;

        try
        {
            // Authenticate the user and get the object.
            user = (User) TurbineSecurity.getAuthenticatedUser( username, password );

            // Store the user object.
            data.setUser(user);

            // Mark the user as being logged in.
            user.setHasLoggedIn(new Boolean(true));

            // Set the last_login date in the database.
            user.updateLastLogin();

            // This only happens if the user is valid; otherwise, we
            // will get a valueBound in the User object when we don't
            // want to because the username is not set yet.  Save the
            // User object into the session.
            data.save();

			log.info( username + " is successfully logged in.");

        }
        catch ( TurbineSecurityException e )
        {
            if(e instanceof DataBackendException)
            {
                log.error(e);
            }

            // Retrieve an anonymous user.
            data.setUser (TurbineSecurity.getAnonymousUser());
            String loginTemplate = Turbine.getConfiguration()
							.getString(TurbineConstants.TEMPLATE_LOGIN);

			data.setMessage( Turbine.getConfiguration()
							.getString( TurbineConstants.LOGIN_ERROR ) );

            if (loginTemplate != null && loginTemplate.length() > 0)
            {
                data.setScreenTemplate(loginTemplate);
            }
            else
            {
                data.setScreen(Turbine.getConfiguration().getString(
                    TurbineConstants.SCREEN_LOGIN));
            }
        }
    }

}
