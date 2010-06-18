package org.iitk.brihaspati.modules.actions;

/*
 *  @(#)Glossary_Action.java
 
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
 */

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import org.apache.turbine.util.RunData;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.velocity.context.Context;
import org.apache.torque.util.Criteria;

import org.iitk.brihaspati.om.GlossaryPeer;
import org.iitk.brihaspati.om.Glossary;
import org.iitk.brihaspati.om.GlossaryAliasPeer;

import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.StringUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;


/** This class contain the code Insert word , Insert aliasword, Update word,
 * and Delete word.
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:sharad23nov@yahoo.com.">Sharad Singh</a>
 *
 */


public class Glossary_Action extends SecureAction{
 
	/**
	 * Place all the data object in the context for use in the template.
	 * @param data RunData instance
	 * @param context Context instance
	 * @exception Exception, a generic exception
	 */
	 
	/**
	 * This method Insert word and description along with userid of user 
	 * who is currently logged.
	 */
	 
	public void doInsertWord(RunData data,Context context){
		try{
			ParameterParser pp = data.getParameters();
	
			/**
			 * Get userid for the user currently logged in
			 * @see UserUtil in Util.
			 */
			User user = data.getUser();
			
			String username=user.getName();
		        int uid=UserUtil.getUID(username);
		        String word=pp.getString("WORD","");
		        String des =pp.getString("DESCRIPTION","");
        		
			String LangFile=(String)user.getTemp("LangFile");
			/**
			 * Check for illegal character
			 * @see StringUtil in Util
			 */

			if(StringUtil.checkString(word)!=-1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("ProxyuserMsg3",LangFile));
                               setTemplate(data,"call,Glossary,Glossary_Insert_Word.vm");
                               return;
                        }
 

			// Insert word,description and userid in table Peer.
	
			Criteria crit = new Criteria();
		        crit.add(GlossaryPeer.WORD,word);
			List v = GlossaryPeer.doSelect(crit);
		        boolean doesNotExist=v.isEmpty();
			if(doesNotExist==false){
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg1",LangFile);
				if(LangFile.endsWith("urd.properties"))
                                	data.setMessage(msg1 +word);
				else
                                	data.setMessage(word + msg1);
				
				setTemplate(data,"call,Glossary,Glossary_Insert_Word.vm");
				context.put("mode","Insert");
				return;
			}
			else{
				crit.add(GlossaryPeer.WORD,word);
				crit.add(GlossaryPeer.DEFINITION,des);
				crit.add(GlossaryPeer.USER_ID,Integer.toString(uid));
				GlossaryPeer.doInsert(crit);
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg2",LangFile);
                                data.setMessage(msg1);
				setTemplate(data,"call,Glossary,Glossary_Insert_Word.vm");
				context.put("mode","Insert");
			}
		}
		catch(Exception e){data.setMessage(" Error in Insert" +e);}
	}

  /**
    * This method insert alias word and description of a word in table Peer 
    * and aliasPeer
    * @param data RunData .
    * @param context Context.
    */  
  
	public void doInsertalias(RunData data,Context context) {
		try{
       			User user = data.getUser();
			String LangFile=(String)user.getTemp("LangFile");

	
		       String username=user.getName();
			/**
                         * Get userid for the user currently logged in
                         * @see UserUtil in Util.
                         */

		       int uid=UserUtil.getUID(username);
		       String wid = data.getParameters().getString("wordId");
		       int w_id =Integer.parseInt(wid);
		       String status = data.getParameters().getString("status");
		       context.put("stat",status);
		       String word = data.getParameters().getString("WORD","");
		       String des = data.getParameters().getString("DESCRIPTION","");
			/**
			 * Check for illegal character
			 * @see StringUtil in Util
			 */

			if(StringUtil.checkString(word)!=-1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("ProxyuserMsg3",LangFile));
                               setTemplate(data,"call,Glossary,Glossary_Delete_Edit_Word.vm");
                               return;
                        }
       
       // Insert word and description in table Peer.
       
		       Criteria crit = new Criteria();
		       Criteria crit1 = new Criteria();
       			crit.add(GlossaryPeer.WORD,word);
			crit1.add(GlossaryAliasPeer.WORD_ALIAS,word);
		       List v = GlossaryPeer.doSelect(crit);
		       List v1 =GlossaryAliasPeer.doSelect(crit1);
		       boolean doesNotExist=v1.isEmpty();
		       boolean doesNotExists=v.isEmpty();
		   if((doesNotExist==false) || (doesNotExists==false))
			     {
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg1",LangFile);
                                data.setMessage(word + msg1);

				setTemplate(data,"call,Glossary,Glossary_Delete_Edit_Word.vm");
			        context.put("mode","alias");
			        return;
		     }
        	   else
	             {		   
        		      crit.add(GlossaryPeer.WORD,word);
			      crit.add(GlossaryPeer.DEFINITION,des);
			      crit.add(GlossaryPeer.USER_ID,Integer.toString(uid));
			      GlossaryPeer.doInsert(crit);
			      crit1.add(GlossaryAliasPeer.WORD_ALIAS,word);
			      crit1.add(GlossaryAliasPeer.WORD_ID,w_id);
			      GlossaryAliasPeer.doInsert(crit1);	
			      String msg1=MultilingualUtil.ConvertedString("GlossaryMsg3",LangFile);
                              data.setMessage(msg1);
	
		              setTemplate(data,"call,Glossary,Glossary_Delete_Edit_Word.vm");
	             }
	       }
	   
	    catch(Exception e){data.setMessage("The error in Insert Alias"+e);}
    }

 
	/**
         * This method delete word from word and word Alias peer
         * Get the userid from the UserUtil.
         * @see UserUtil in Utils.
         * @param data RunData .
         * @param context Context.
	 * @exception Exception, a generic exception.
         */

	public void doDeleteWord(RunData data,Context context) throws Exception{
		try{

			User user = data.getUser();
		        String username=user.getName();
		        int uid=UserUtil.getUID(username);
		        ParameterParser pp = data.getParameters();
			String LangFile=(String)user.getTemp("LangFile");
      
			        // Get list of word in array   
	      
		        String w_id[] = pp.getStrings("word_list");
		        context.put("WID",w_id);
		        Criteria crit = new Criteria();
		        Criteria crit1 = new Criteria();
		        Criteria crit2 = new Criteria();
		        Criteria crit3 = new Criteria();
		        int len=0;
		        if(w_id == null)
			{ 
				len = 0;
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg4",LangFile);
                                data.setMessage(msg1);
		        }	 
		        else
			{	// Get the lenght of array.
			  
	 			len = w_id.length;
		         	for(int num=0;num<len;num++)
	   			{
					// Delete word from GlossaryPeer and GlosarryaliasPeer.

			     	        crit2.add(GlossaryPeer.WORD_ID,w_id[num]);
				    	List v = GlossaryPeer.doSelect(crit2);
				    	Glossary ele =(Glossary)v.get(0);
				    	int userid = ele.getUserId();
				    	String word = ele.getWord().toString();
				    	crit3 .add(GlossaryAliasPeer.WORD_ALIAS,word);
	   
						// Check for user authorization to delete a word.
	    
					if(uid==userid)
					 {
						GlossaryPeer.doDelete(crit2);
					        crit1.add(GlossaryAliasPeer.WORD_ID,w_id[num]);
					        GlossaryAliasPeer.doDelete(crit1);
					        GlossaryAliasPeer.doDelete(crit3);
						String msg1=MultilingualUtil.ConvertedString("GlossaryMsg5",LangFile);
                                		data.setMessage( msg1);
					 }
					else{
						String msg1=MultilingualUtil.ConvertedString("GlossaryMsg6",LangFile);
                                		data.setMessage( msg1);
					}
				 } 
			}
		}
			catch(Exception ex){data.setMessage("Error in Delete word and alias" + ex);}
	}
 
		/**
		 *  This Method Update word and wordAlias
	         * @param data RunData.
        	 * @param context Context.
		 * @exception Exception, a generic exception.
	         */

	public void doUpdateword(RunData data,Context context) throws Exception {
		try{   
			User user = data.getUser();
		        String username=user.getName();
			String LangFile=(String)user.getTemp("LangFile");
				/**
				 *  Get the userid from the UserUtil.
         			 * @See UserUtil in Utils class.
				 */
		        int uid=UserUtil.getUID(username);
		        ParameterParser pp = data.getParameters();
        		String status=pp.getString("status");
		        context.put("stat",status);
        		String w_id = pp.getString("wordId");
			int wid=Integer.parseInt(w_id);

	        	Criteria crit = new Criteria();
	        	crit.add(GlossaryPeer.WORD_ID,w_id);
		        List v = GlossaryPeer.doSelect(crit);
        		Glossary ele =(Glossary)v.get(0);
		        int userid = ele.getUserId();
        		String word = pp.getString("WORD");
	        	String des = pp.getString("DESCRIPTION");
			/**
			 * Check for illegal character
			 * @see StringUtil in Util
			 */

			if(StringUtil.checkString(word)!=-1)
                        {
                                data.addMessage(MultilingualUtil.ConvertedString("ProxyuserMsg3",LangFile));
                               setTemplate(data,"call,Glossary,Glossary_Delete_Edit_Word.vm");
                               return;
                        }
      
        			//Check for user authorization to update word
      	
		        if((uid == userid) || (uid == 0))
        		 {	
	
				// Update word and description from GlossaryPeer. 
				crit = new Criteria();
				crit.add(GlossaryPeer.WORD_ID,wid);
				crit.add(GlossaryPeer.WORD,word);
				crit.add(GlossaryPeer.DEFINITION,des);
				GlossaryPeer.doUpdate(crit);	

				setTemplate(data,"call,Glossary,Glossary_Delete_Edit_Word.vm");
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg7",LangFile);
                                data.setMessage(msg1);
	         	}
		        else	
       				{
				String msg1=MultilingualUtil.ConvertedString("GlossaryMsg8",LangFile);
                                data.setMessage(msg1);
			}	
		}
		catch(Exception ex){data.setMessage("The error in Update"+ex);}
	}

        /** 
	  * This method give the list of word according to string.   
   	  * @param data RunData.
          * @param context Context.
	  * @exception Exception, a generic exception.
          */

  
	public void doSearch(RunData data,Context context) throws Exception {
		try{
		String LangFile=(String)data.getUser().getTemp("LangFile");
		//String keyword = data.getParameters().getString("WORD");
	        String search = data.getParameters().getString("search");
	        context.put("search","search");
		/**
                  * Check for special characters
                  */
                 String keyword=StringUtil.replaceXmlSpecialCharacters(data.getParameters().getString("WORD"));
        	 context.put("key",keyword);
	
				//Select list of words from table according to string.
	        if(keyword !=""){
			Criteria crit = new Criteria();
        		crit.add(GlossaryPeer.WORD,(Object)(keyword+"%"), crit.LIKE);
  	              	List wlist=GlossaryPeer.doSelect(crit);

        	        if(wlist.size()!=0) {
				context.put("v",wlist);
 				context.put("stat","Noblank");
	
			}
	                else
        	        {
			String msg1=MultilingualUtil.ConvertedString("GlossaryMsg9",LangFile);
                        data.setMessage(msg1 + keyword);
                	}
        	}
		}
		catch(Exception ex){data.setMessage(" The error in search action "+ex);}
	   }	     
	     
  
	public void doPerform(RunData data,Context context) throws Exception  {
			
		String action=data.getParameters().getString("actionName","");
		String langFile=data.getUser().getTemp("LangFile").toString();
   	
		if(action.equals("eventSubmit_doInsertWord"))
     			doInsertWord(data,context);
	   	else if(action.equals("eventSubmit_doDeleteWord"))
     			doDeleteWord(data,context);
	   	else if(action.equals("eventSubmit_doUpdateword"))
     			doUpdateword(data,context);
	   	else if(action.equals("eventSubmit_doInsertalias"))
     			doInsertalias(data,context);
	   	else if(action.equals("eventSubmit_doSearch"))
     			doSearch(data,context);	   
	  	else
     			//data.setMessage("Cannot find the button");
     			data.setMessage(MultilingualUtil.ConvertedString("c_msg",langFile));
	  }
 }	 

