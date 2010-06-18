
/*
 * @(#)Controlremove.java
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
 *  Contributors: Members of ETRG, I.I.T. Kanpur
 *
 * Last modified on Thu Jan 19 11:28:47 IST 2006 by Manvendra Baghel
 *
 */
import java.util.Properties;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @author <a href="mailto:shaistashekh@gmail.com">Shaista</a>
 * @author <a href="mailto:manav_cv@yahoo.co.in">Manvendra Baghel</a>
 *
 * This class removes $  after  '#' , '!'  and checks velocityCount variable 
 * in Raw hindi english urdu directory
 */


class Controlremove{
	/**
	* This function removes $  after  '#' , '!'  and checks velocityCount variable in Raw hindi english urdu directory
	* @param inputfile File
        * @return void
	*/
	static void remove(File  inputfile )
        {
                try
                {
			byte byrr[]= (new String("velocityCount")).getBytes();
		 	char charr[]= (new String("ontrol_velocityCount")).toCharArray();
	 		byte bcharr[]= (new String("ontrol_velocityCount")).getBytes();
			/**
			 *  int front is (front - runner) reader fr1 ,it will tell int j,i,k (backrunners) 
			 *  reader fr what lies ahead 
			 */
			int i=0,j=0,k=0 ,front=0;
                        /**
                        * Extract base directory from given File parameter
                        */
                        String inputdir = inputfile.toString();
                        int end = inputdir.lastIndexOf("/");
                        inputdir=inputdir.substring(0,end);
                        File fout1 =new File( inputdir + "brihaspati.brihaspati");
			/**
			 * Why have we made 2 FileReader objects fr1 and fr for 2 diffent BufferedReader objects br1 and fr
			 * Why can't we use same FileReader object for both when they both(fr & fr1) point to same file
			 * Answer is because BufferedReader will read with first object (say br) but when another one is asked to read
			 * (say br1)It will return -1.
			 */
			FileReader fr= new FileReader(inputfile);
			BufferedReader br =new BufferedReader(fr);
			FileReader fr1= new FileReader(inputfile);
			BufferedReader br1 =new BufferedReader(fr1);
                        /**
                        * Here temporary vm made is brihaspati.brihaspati it should not be made by others
                        */
                        FileOutputStream fout=new FileOutputStream(fout1);
                        do{
				/**
	 			* boolean that will tell if '{' is found or not   
		 		*/
				boolean foundbrace=false;
                                /**
                                * Check if text is '!$C' ,If true than do not read br1 in beginning
                                */
                                boolean b1 = j=='!';
                                boolean b2 = i=='$';
                                boolean b3 = front=='C';
                                boolean b4 = b1 && b2 && b3;
                                if(!b4)
                                {
                                        front=br1.read();
                                        if(front==-1)
                                        continue;
                                }
                                j=br.read();
				switch(j)
				{
	                                case -1:
        	                        		continue;
					/**
					* If case is #
					* If you encounter first caracter as  #  
					*/
        	                       case '#':
	                        	                fout.write(j);
        	                        	        front = br1.read();
							if(front==-1)
							continue;
        	                        	        i = br.read();
							switch(i)
							{
              		  	                        	case -1 :
	        		       	                        		continue;
								/**
								* than if next character is $ than  dont write it
								* This is done to see that if already remove is called or not
								*/
								case '$':
										break;
								default :
										fout.write(i);	
							}//switch
							/**
							* Break from outer loop
							*/
							break;
					/**
        	                        * If case is !
                	                * If you encounter first caracter as  !
                        	        */
				       case '!':
							front=br1.read();
							if(front==-1)
							continue;
							i=br.read();
							/**
							 * Vital Debug information is obtained by seeing the value of Front and back runners br1 and br
							 * Both have to be equal at this point
							System.out.print("Front ======>"+ front);
							System.out.print("i===========>"+ i);
							* Uncomment above statements if you wish to debug
							*/
							switch(i)
							{
								case -1 :
										continue;
	
								/**
        		        	                        * and next character is $ 
                		        	                */
								case '$' :
										front =br1.read();
										switch(front)
										{
											case -1 :
													continue;
											/**
											* if text is !$! than make no change
											*/
											case '!':

													fout.write(j);	
													fout.write(i);
													k=br.read();
													if(k==-1)
													continue;
													fout.write(front);
													break;
											
											case 'C':
													fout.write(j);
													break; 
											case '{':
													foundbrace=true;
											/**
											* if text is !$data than make it $data that is skip  $
											*/
											default :
													fout.write(j);
													k=br.read();
													if(k==-1)
													continue;
                	                			                        		fout.write(front);
										}//switch
										/**
										* Break from outer loop
										*/
										break;
								/**
			                                        * and next character is  not $ than no change eg != or !!
        				                        */
								default :
										fout.write(j);
        	                			                        fout.write(i);
							}//switch
							/**
							* Break from outer switch
							*/
							break;
					/**
					 * When you encounter text Control_velocityCount change it to 
					 * velocityCount
					 */
				       case 'C':
							br1.mark(200);
							br.mark(200);
							/**
							* This for loop checks if Control_velocityCount is found or not
							*/
							boolean b=false;
							boolean eof=false;
							int c1=0;
							for(int c=0;c<charr.length;c++)
							{
								front=br1.read();
								c1=br.read();
								char c2=(char)c1;
								if(c1==-1 || front==-1)
								{
									eof=true;
									break;
								}
								if(c1 != charr[c])
								{
									b=true;
									break;
								}
							}//for
							if(eof)
							{
								i=-1;
								continue;
							}
							/**
							 *  if pattern Control_velocityCount is found 
							 */
							if(!b)
							{
								front=br1.read();
								if(front==-1)
								continue;	
								c1=br.read();
								if(c1==-1)
								{
									i=-1;
									continue;
								}
								if(c1==' ' || c1 =='\n' || c1=='!' || c1=='=' || c1=='$' || c1== '"' || c1=='\t' || c1=='}')
								{
                	                				fout.write(byrr);
                	                       				fout.write(c1);
								}//if
								else if(c1=='+'|| c1=='-'||c1=='/'||c1=='*'||c1=='%' || c1=='<'||c1=='>')
								{
									if(foundbrace)
									{
										/**
										* Than dont change
										* Control_velocityCount .. to velocityCount ..
										*/
											
										fout.write(j);
										fout.write(bcharr);
										fout.write(c1);
									}
									else
									{
                	                			       		fout.write(byrr);
                	                       					fout.write(c1);
									}
								}
								else
								{
									fout.write(j);
									fout.write(bcharr);
									fout.write(c1);
								}//else
							}//if(!b)ends
							else
							{
								fout.write(j);
								br.reset();
								br1.reset();
							}
							break;
					default:
							fout.write(j);
				}//Switch
                        }while((i != -1)&&(j != -1)&&(k != -1)&&(front!=-1));
			br.close();
			br1.close();
			fr.close();
			fr1.close();
			fout.close();
                        /**
                        * rename brihaspati.brihaspati to the input file
                        */
                        fout1.renameTo(inputfile);
			/**
                        * Release memory
                        */
			fr=null;
			fr1=null;
                        br=null;
                        br1=null;
                        fout=null;
                        fout1=null;
                }//try
                catch(Exception e)
                {
			e.printStackTrace();
                }
        }//remove ends
}//class Controlremove Ends

