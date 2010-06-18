package org.iitk.brihaspati.modules.utils;
 
/*@(#)MultilingualUtil.java
 *  Copyright (c) 2005-2006 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */
             
/**
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista Bano</a>
 **/

import java.io.FileInputStream;
import java.util.PropertyResourceBundle;
import org.apache.turbine.services.servlet.TurbineServlet;

public  class MultilingualUtil{
 
        /**
         * This method retreives the String in Unicode and Convert it into a UTF-8 String
	 * @param str String str Contains the variable to be replaced in vm
	 * @param file_properties String file_properties Contains the name of property file for language
         * @return String
         */
 
        public static String ConvertedString(String str,String file_properties){ 
		String string_converted=new String();
		try{	
			PropertyResourceBundle bundle=new PropertyResourceBundle(new FileInputStream(file_properties));
			String mystr=bundle.getString(str);
	                byte[] utf8 = mystr.getBytes("UTF-8");
               	        string_converted = new String(utf8, "UTF-8");
                                                        
		}	
		catch(Exception e)
		{
			string_converted="Error in property file variable not Match with property file :-"+e; 
		}
		return string_converted;
	}
	public static String ConvertedMonth(int monthNumber, String propertyFile)
	{
			/**
                         * Initializing the month name depending on the
                         * month number
                         */ 
			String month_name=new String();
                        switch(monthNumber){
                                case 1:
                                        month_name=MultilingualUtil.ConvertedString("brih_January",propertyFile);
                                        break;
                                case 2:
                                        month_name=MultilingualUtil.ConvertedString("brih_February",propertyFile);
                                        break;
                                case 3:
                                        month_name=MultilingualUtil.ConvertedString("brih_March",propertyFile);
                                        break;
                                case 4:
                                        month_name=MultilingualUtil.ConvertedString("brih_April",propertyFile);
                                        break;
                                case 5:
                                        month_name=MultilingualUtil.ConvertedString("brih_May",propertyFile);
                                        break;
                                case 6:
                                        month_name=MultilingualUtil.ConvertedString("brih_June",propertyFile);
                                        break;
                                case 7:
                                        month_name=MultilingualUtil.ConvertedString("brih_July",propertyFile);
                                        break;
                                case 8:
                                        month_name=MultilingualUtil.ConvertedString("brih_August",propertyFile);
                                        break;
                                case 9:
                                        month_name=MultilingualUtil.ConvertedString("brih_September",propertyFile);
                                        break;
                                case 10:
                                        month_name=MultilingualUtil.ConvertedString("brih_October",propertyFile);
                                        break;
                                case 11:
                                        month_name=MultilingualUtil.ConvertedString("brih_November",propertyFile);
                                        break;
                                case 12:
                                        month_name=MultilingualUtil.ConvertedString("brih_December",propertyFile);
                                        break;
                        }
			return month_name;
	}

	 public static String ConvertedAlphabates(String word, String LangFile)
        {
		 // Get list of word and wordid according to particular alphabate.
                
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
			return word;
	}
	public static String LanguageSelection(String Language)
                {
                        String LangFile=new String();
                        String lang=Language;
			try{
	                        if(lang.equals("bangla"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_bn.properties");
	                        else if(lang.equals("english"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_en.properties");
	                        else if(lang.equals("french"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_fr.properties");
	                        else if(lang.equals("hindi"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_hi.properties");
	                        else if(lang.equals("marathi"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_mr.properties");
	                        else if(lang.equals("urdu"))
	                               LangFile=TurbineServlet.getRealPath("/WEB-INF/conf/BrihLang_urd.properties");
			} // End Try
			catch(Exception e)
			{
				LangFile = "Exception to select a BrihLang_"+lang+".properties file";
			}
        	                return LangFile;
        } //End LanguageSelection

}

