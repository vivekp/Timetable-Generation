package org.iitk.brihaspati.modules.screens.call.Glossary;

/*
 *  @(#)Glossary_Search_Word.java

 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur.
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

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import org.apache.turbine.util.RunData;
import org.apache.torque.util.Criteria;
import org.apache.velocity.context.Context;
import org.iitk.brihaspati.om.GlossaryPeer;
import org.iitk.brihaspati.om.Glossary;
import org.iitk.brihaspati.om.GlossaryAliasPeer;
import org.iitk.brihaspati.om.GlossaryAlias;
import org.iitk.brihaspati.modules.screens.call.SecureScreen;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;

/**
 *  This class contains code for getting the list of all the words according to
 *  a particular alphabate and description of a particular word 
 *  Grab all the records in a table using a Peer and place the
 *  Vector of data  objects in  the context where they can be
 *  displayed by a #foreach loop.
 *
 *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 *  @author <a href="mailto:sharad23nov@yahoo.com">Sharad Singh</a>
 */

public class Glossary_Search_Word extends SecureScreen
{
	/**
	* place all the data object in context
	* for use in template
 	* @param data Rundata
  	* @param context Context
  	*/
 
	public void doBuildTemplate(RunData data,Context context)
   	{
    	try
	{       

		String word = data.getParameters().getString("alpha","");
      		context.put("alpha",word);
      		String status = data.getParameters().getString("status");
      		context.put("stat",status);
		String LangFile=(String)data.getUser().getTemp("LangFile");
				/**
				 * Assigning the English Character in word Parameter
				*/
			word=MultilingualUtil.ConvertedAlphabates(word, LangFile);
		      // Get list of word and wordid according to particular alphabate.
		/*
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_A",LangFile)))
				word="A";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_B",LangFile)))
				word="B";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_C",LangFile)))
				word="C";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_D",LangFile)))
				word="D";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_E",LangFile)))
				word="E";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_F",LangFile)))
				word="F";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_G",LangFile)))
				word="G";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_H",LangFile)))
				word="H";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_I",LangFile)))
				word="I";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_J",LangFile)))
				word="J";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_K",LangFile)))
				word="K";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_L",LangFile)))
				word="L";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_M",LangFile)))
				word="M";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_N",LangFile)))
				word="N";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_O",LangFile)))
				word="O";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_P",LangFile)))
				word="P";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_Q",LangFile)))
				word="Q";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_R",LangFile)))
				word="R";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_S",LangFile)))
				word="S";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_T",LangFile)))
				word="T";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_U",LangFile)))
				word="U";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_V",LangFile)))
				word="V";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_W",LangFile)))
				word="W";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_X",LangFile)))
				word="X";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_Y",LangFile)))
				word="Y";
			if(word.equals(MultilingualUtil.ConvertedString("Glossary_Z",LangFile)))
				word="Z";
			*/
		if(!word.equals("")){
      			Criteria crit3=new Criteria();
			crit3.add(GlossaryPeer.WORD,(Object)(word+"%"),crit3.LIKE);
		 	List wlist=GlossaryPeer.doSelect(crit3);
			if(wlist.size()!=0)
          		{
				context.put("v",wlist);
          		}
         		else if(!status.equals(""))
          		{
            			context.put("stat","blank");
                        	String msg=MultilingualUtil.ConvertedString("GlossaryMsg9",LangFile);
                        	data.setMessage(msg + word);
//            			data.setMessage("Word Not Exist in DataBase regarding to alphabate:" + word);
          		}
      		}
      		String wid = data.getParameters().getString("wordid","");
      		context.put("wid",wid);
      		String Word = data.getParameters().getString("word");
      		String str = "";
      		Vector walias = new Vector();
      		if(!wid.equals(""))
      		{
       		//Get alias word of given word.	      
       			Criteria crit1 = new Criteria(); 
       			crit1.add(GlossaryAliasPeer.WORD_ID,wid);
       			List v1 = GlossaryAliasPeer.doSelect(crit1);
       			Criteria crit2 = new Criteria();
       			crit2.add(GlossaryAliasPeer.WORD_ALIAS,Word);
       			List v2 = GlossaryAliasPeer.doSelect(crit2);
       			context.put("size",Integer.toString(v2.size()));
       			int len  = v1.size();
       			int len1 =v2.size();
       			if(v1.size()!=0)
        		{
         			for(int num=0; num<len; num++)
          			{	      
           				GlossaryAlias ele1 = (GlossaryAlias)v1.get(num);
           				String str1=ele1.getWordAlias();
           				walias.addElement(str1);
          			}
        		}
       			if(v2.size()!=0)
        		{
	 			GlossaryAlias ele2 = (GlossaryAlias)v2.get(0);
	 			String wiD =String.valueOf(ele2.getWordId());
	 			Criteria crit4 = new Criteria();
	 			crit4.add(GlossaryPeer.WORD_ID,wiD);
	 			List v4 = GlossaryPeer.doSelect(crit4);
	 			Glossary ele4 = (Glossary)v4.get(0);
	 			String Wrd = ele4.getWord().toString();
	 			walias.addElement(Wrd);
        		} 
      
      			// Get Description of a word according to wordid.
      
      			Criteria crit = new Criteria();
      			crit.add(GlossaryPeer.WORD_ID,wid);
      			List v = GlossaryPeer.doSelect(crit);
      			Glossary ele = (Glossary)v.get(0);
      			str = new String(ele.getDefinition()); 
      			context.put("description",str);
     		}
      		if(walias.size()!=0) 
       		{
	       		context.put("walias",walias);
	       		context.put("waliassize",Integer.toString(walias.size()));
       		}
	}
    	catch(Exception ex){data.setMessage(" The error in search glossary "+ex);}
     }
} 
