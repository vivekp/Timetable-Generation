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


// turbine lib
import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;

// Logging libraries
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// sample app om modules
import org.iitk.brihaspati.om.Note;
import org.iitk.brihaspati.om.NotePeer;

// Intake tool
import org.apache.turbine.services.intake.IntakeTool;
import org.apache.turbine.services.intake.model.*;

// jdk stuff
import java.util.Date;

/**
 * Notes are handled from this action.
 *
 * Always performs a Security Check that you've defined before
 * executing the doBuildtemplate().
 * @author <a href="mailto:painter@kiasoft.com">Jeffery Painter</a>
 */
public class NoteSQL extends SecureAction
{

	private static Log log = LogFactory.getLog(NoteSQL.class);

    /**
      * doInsert() creates a new entry in the database
	  * this example is using an intake validation set
      */
    public void doInsert(RunData data, Context context)
        throws Exception
    {
        // Test for valid intake data
        IntakeTool intake = (IntakeTool) context.get("intake");
        if ( !intake.isAllValid() )
        {
            log.info("Intake has found an error with user data");
            return;
        }
        else
        {
			// use try/catch in case we cannot save to the database
			try
			{
				log.info("Intake says we are ok");

				// get the "Note" Group
				Note note = new Note();
				Group group = intake.get("Note", IntakeTool.DEFAULT_KEY);
				group.setProperties (note);
				// now note is properly populated with the form data
				
				Date now = new Date();

				// log the time we were posted
				note.setDatePosted( now );

				// save the entry
				note.setNew( true );
				note.save();

				log.info("New entry created by user: " + data.getUser().getName() );

				// now we must clear the intake group or the data
				// will again repopulate the next call to this form
				// which we don't want in this case
				intake.remove( group );

			} catch ( Exception e ) {
				log.error("data exception for inserting a new note occured: " + e.toString() );
				data.setMessage("An error has occured when attempting to insert a new note: " + e.toString() );
			}

		}

	}

    /**
     * Update a record from the database using
     * the unique id gleaned from the web form.
     */
    public void doUpdate(RunData data, Context context)
        throws Exception
    {
		// no update available at this time
    }

    /**
     * Delete a record from the database using
     * the unique id gleaned from the web form.
     */
    public void doDelete(RunData data, Context context)
        throws Exception
    {
		log.info("User " + data.getUser().getName() +
				 " is trying to delete a note.");
		try
		{
			int noteId = data.getParameters().getInt("noteId");
			Criteria criteria = new Criteria();
			criteria.add(NotePeer.NOTE_ID, noteId );
			NotePeer.doDelete( criteria );

		} catch ( Exception e ) {
			log.error("Could not delete the entry: " + e.toString() );
		}
    }

    /**
     * This is used in the event that the we are called with
	 * an invalid argument
     */
    public void doPerform(RunData data, Context context)
        throws Exception
    {
        data.setMessage("Can't find the button!");
    }

}
