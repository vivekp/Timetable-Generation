package org.iitk.brihaspati.modules.utils;

/*@(#)StringUtil.java
 *  Copyright (c) 2003-2006 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
  * This class handle the illegal and xml character
  * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
  */


import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

// Copyright 2004, Y.N.Singh

public class StringUtil
{
	/**
	 * This method checks for illegal characters in the string
	 * @param to_be_checked String contains the string to be checked for illegal characters
	 * @return int
	 */

	public static int checkString(String to_be_checked)
	{
		int x=-1;
		char illegal_characters[]={'!' , '@' , '#' , '$' , '%' , '^' , '&' , '*' , '(' , ')' , '-' , '=' , '+' , '<' , ',' , '>' , '.' , '/' , '?' ,  ';' , '\"' , '\'' , '{' , '[' , '}' , ']' } ;
		for(int a=0;a<illegal_characters.length;a++)
		{
		    x=to_be_checked.indexOf(illegal_characters[a]);
		    if(x!=-1)
			break;
		}
		return(x);
		// returns -1 if illegal character not present, else
		// returns the location of first occurance of illegal
		// character.
	}

	/**
	 * This method checks for the number of tokens present in the string
	 * @param to_be_checked String contains the string to be checked for number of tokens
	 * @param start_index int contains starting index for search 
	 * @param end_index int contains end index for search
	 * @return int specifies number of delimiters
	 */

	public static int checkNumberofTokens(String to_be_checked,int start_index,int end_index)
	{
		String Temp=to_be_checked.substring(start_index,end_index);
		StringTokenizer check=new StringTokenizer(Temp,";");
		int number_of_delim=check.countTokens();
		return(number_of_delim);
	}
	

	/**
	 * This method replaces the special characters with their equivalent string for XML
	 * @param data String contains the string to be checked for XML special characters
	 * @return nothing
	 */

	public static String replaceXmlSpecialCharacters(String data){
		int[] special={34,38,39,60,62};

		/**
		 * ASCII values
		 * " = 34 , & = 38 , ' = 39, < = 60 , > = 62
		 */

		StringBuffer sb=new StringBuffer(data);

		for(int i=0;i<5;i++){
			int character=special[i];
			int start_index=0;
			int from_index=0;
			int last_index=0;
			while(from_index <= last_index){
				start_index=data.indexOf(character,from_index);
				last_index=data.lastIndexOf(character);
				
				if(start_index!=-1){
					int index=start_index+1;
					if(character==38){
						if(!(data.substring(index,index+3).equals("amp;")))
							sb.replace(start_index,index,"&amp;");
					}
					else if(character==60){
						sb.replace(start_index,index,"&lt;");
					}
					else if(character==62){
						sb.replace(start_index,index,"&gt;");
					}
					else if(character==39){
						sb.replace(start_index,index,"&apos;");
					}
					else if(character==34){
						sb.replace(start_index,index,"&quot;");
					}
					data=new String(sb);
					from_index=data.indexOf(";",start_index);
				}
			}
		}
		return(data);
	}

	/**
	 * This method insert the specified charaters between the tokens if the tokens are empty
	 * @param sourceFile String contains the path of the file which is to be read and
	 *		     uploaded
	 * @param destinationFile String contains the path where the file has to be stored
	 * 			  after changes 
	 * @param delimiter char contains the character which is the delimiter in the file
	 */

	public static void insertCharacter(String sourceFile, String destinationFile,char delimiter, char insertCharacter)
	{
		try{
			FileReader fr=new FileReader(sourceFile);
	                BufferedReader br=new BufferedReader(fr);

        	        String line;		

			FileOutputStream fout=new FileOutputStream(destinationFile);
			while( (line=br.readLine())!=null ){
				StringBuffer sb=new StringBuffer(line);
				int startIndex=line.indexOf(delimiter);
				int endIndex=line.length()-1;

				while( (startIndex <= endIndex) && (startIndex > 0) )
				{
					int nextIndex=startIndex+1;
					try{
						if(line.charAt(nextIndex)==delimiter)
						{
							sb.insert(nextIndex,insertCharacter);
						}
					}catch(Exception e){
						sb.insert(nextIndex,insertCharacter);
					}
			
					line=new String(sb);
					startIndex=line.indexOf(delimiter,nextIndex+1);
					endIndex=line.length()-1;
				}
				fout.write(line.getBytes());
				fout.write(("\n").getBytes());
			}
			fout.close();
		}
		catch(Exception e){
		}
	}

	 /**
         * This method checks for the number of lines present in the file
         * @param file file contains number of lines
         * @return long specifies number of lines
         */

        public static long checkNumberoflines(File file )
        {
                long numLines=0;
	try{
		BufferedReader in =new BufferedReader(new FileReader(file));
                String line;
                do {
	                line = in.readLine();
        	        if (line != null)
                	{
                		numLines++;
                        }
                }
                while (line != null);
	}
	catch(Exception ex){ErrorDumpUtil.ErrorLog("Error in String Util (checkNumberoflines) block==>"+ex);}
	                return(numLines);
        }


}
