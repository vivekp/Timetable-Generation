package org.iitk.brihaspati.modules.utils;

/*
 * @(#)CreateZip.java
 *
 *  Copyright (c) 2006 ETRG,IIT Kanpur.
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
 * Contributors: Members of ETRG, I.I.T. Kanpur
*/


import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Date;

public class CreateZip
{

  private ZipOutputStream fos;
  private int startIndex;
  public CreateZip(String source,String destination) throws IOException
  {
     File f=new File(source) ;
     if( f.exists() )
     {
        if(f.getParent()!=null)
        {
 	   String temp=f.getAbsolutePath();
	   startIndex=temp.lastIndexOf(f.getName());
        }
	Date date=new Date();
        fos=new ZipOutputStream( new FileOutputStream(destination+"/"+f.getName()+ExpiryUtil.getCurrentDate("")+".zip") );
	doZip(f);
	fos.close();
     }
  }

  public void doZip(File f) throws IOException
  {
     String parent=f.getParent();
     if(parent==null)
	parent="";
     else
	parent=parent+"/";
     if( f.isDirectory())
     {
        fos.putNextEntry( new ZipEntry( parent.substring(startIndex)+f.getName()+"/" ) );
	File fileList[]= f.listFiles();

	for(int i=0;i<fileList.length;i++)
	{
	   doZip(fileList[i]);
	}
        fos.closeEntry();
     }
     else
     {
       	FileInputStream in = new FileInputStream(f);
    	int len;
        byte[] buf = new byte[1024];
        fos.putNextEntry( new ZipEntry( parent.substring(startIndex)+f.getName() ) );
	while ((len = in.read(buf)) > 0)
	{
           fos.write(buf, 0, len);
        }
	in.close();
        fos.closeEntry(); 	
     }

  }
}

   		
