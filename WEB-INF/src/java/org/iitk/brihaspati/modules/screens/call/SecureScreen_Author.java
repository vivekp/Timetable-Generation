package org.iitk.brihaspati.modules.screens.call;


import java.util.List;

import org.apache.turbine.modules.screens.VelocitySecureScreen;
import org.apache.turbine.util.RunData;
import org.apache.turbine.util.security.*;
import org.apache.turbine.Turbine;
import org.apache.velocity.context.Context;


 
  /**
    * This class has code of authorized Screens for Author
    * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
    */


public class SecureScreen_Author extends VelocitySecureScreen
{
    public void doBuildTemplate(RunData data, Context context)
    {
    }

    protected boolean isAuthorized( RunData data )  throws Exception
    {
        boolean isAuthorized = false;

        AccessControlList acl = data.getACL();
		if(acl == null || !acl.hasRole("author","author"))
		{
				
            data.setScreenTemplate(Turbine.getConfiguration().getString("template.login"));
                
            isAuthorized = false;
		}
		else if(acl.hasRole("author","author"))
		{
				isAuthorized=true;
	}
        return isAuthorized;
    }
}
