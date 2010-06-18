package org.iitk.brihaspati.modules.utils;

/*
 * @(#)EncryptionUtil.java
 *
 *  Copyright (c) 2004 ETRG,IIT Kanpur. 
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

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 *  @author <a href="mailto:ammu_india@yahoo.com">Amit Joshi</a>
 */

public class EncryptionUtil
{

	public static String createDigest(String encryption,String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance(encryption);
		byte[] digest = md.digest(input.getBytes());
		return toHexString(digest);
	}

	public static String toHexString(byte[] byteDigest)
	{
		String temp="";
		int i,len=byteDigest.length;
		for(i=0;i<len;i++)
		{
			String byteString=Integer.toHexString(byteDigest[i]);
		
			int iniIndex=byteString.length();
			if(iniIndex==2)
				temp=temp+byteString;
			else if(iniIndex==1)
				temp=temp+"0"+byteString;
			else if(iniIndex==0)
				temp=temp+"00";
			else if(iniIndex>2)
				temp=temp + byteString.substring(iniIndex-2,iniIndex);
		}
		return temp;
	}
}
