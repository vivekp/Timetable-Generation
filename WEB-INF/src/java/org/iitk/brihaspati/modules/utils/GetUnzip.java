package org.iitk.brihaspati.modules.utils;
/*
 * @(#)GetUnzip.java
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
 *
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 */


import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import java.util.zip.*;
import java.util.Enumeration;

/**
 * This class Read zip file and Extract specified path
 * @author <a href="mailto:madhavi_mungole@hotmail.com">Madhavi Mungole</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *
 */

public class GetUnzip
{

//  private ZipInputputStream fis;
  private ZipFile zipfile;
  private String destDir;
	/***********modify the construtor to 3 parameter, send it the RunData as third Arg *************/
  public GetUnzip(String source,String destination) throws IOException
  {
     zipfile=new ZipFile( source );
     if(destination==null)
     {
	destDir="";
     }
     else
     {
	destDir=destination+"/";
	
     }					
     doUnZip();
     zipfile.close();	
   
  }
  
  public void doUnZip() throws IOException
  {
     Enumeration entries=zipfile.entries();		
     while(entries.hasMoreElements())
     {
	ZipEntry ze=(ZipEntry)entries.nextElement();
	File f=new File(destDir+ze.getName());
	if(ze.isDirectory())
	{
	   f.mkdirs();
	}
	else
	{
	   FileOutputStream fos=new FileOutputStream(f);
	   InputStream in=zipfile.getInputStream(ze);
  	   int len;
           byte[] buf = new byte[1024];

	   while ((len = in.read(buf)) > 0)
	   {
              fos.write(buf, 0, len);
           }
	
           fos.close(); 	
       }
    }
  }
}
