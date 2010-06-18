
/*
 * @(#)Controlinsert.java
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
 * This class  inserts $ after '#' and '!' sign and also checks velocityCount
 */


class Controlinsert{
	/**
	* This function  inserts $ after '#' and '!' sign and also checks velocityCount
	* @param inputfile File
	* @return void
	*/

	static void insert(File inputfile )
	{
		try
		{
			int i=0,j=0,k=0,l=0;
			char charr[]= (new String("elocityCount")).toCharArray();
			byte bcharr[]= (new String("elocityCount")).getBytes();
			byte byrr[]= (new String("Control_velocityCount")).getBytes();
			/**
			* Extract base directory from given File parameter
			*/
			String inputdir = inputfile.toString();
			String inputdir1 = inputfile.toString();
			int end = inputdir.lastIndexOf("/");
			int begin = inputdir.indexOf("/");
			inputdir=inputdir.substring(0,end);
			/**
			* Uncomment to debug
			* System.out.println("input dir ="+ inputdir1 + "end=====>"+ end + "        begin======>" + begin );
			*/
			File fout1 =new File( inputdir + "brihaspati.brihaspati");
			FileReader fr= new FileReader(inputfile);
        		BufferedReader br =new BufferedReader(fr);
			/**
			* Here temporary vm made is brihaspati.brihaspati it should not be made by others
			*/
                	FileOutputStream fout=new FileOutputStream(fout1);
                	do{
				j=br.read();
				switch(j)
				{
                                	case -1 :
                                			continue;
                                	case '#':
                                        		i = br.read();
							switch(i)
							{
                                        			case -1 :
		                                        			continue;
									/**
									* If text is ######## that is vm comment
									* output should be #$#$#$#$#$#$#$#$
									* that is here you insert $ after each #
									*/
								case '#':
                		                        			fout.write(j);
                        		                			fout.write('$');
                                		        			fout.write(i);
                                        					fout.write('$');
										break;					
									/**
									* If already insert has  taken place ie #$#$#$#$ 
									* take no action
									*/
								case '$':
                	        	        	        		fout.write(j);
                                	        				fout.write(i);
										break;
									/**
									* If text is velocity directive eg #if() then 
									* output should be #$if()
									* that is here you insert $ between # and character 
									* other than  # 
									*/
								default :
                                		        			fout.write(j);
                        	        		        		fout.write('$');
										fout.write(i);		
							}//switch
							/**
							* break from outer switch
							*/
							break;
					case '$':
							i = br.read();
							switch(i)
							{
								case -1 :
										continue;
								/**
								* If text is $!data.getMessage() 
								* it should print $!$data.getMessage()
								*/
								case '!':
										k = br.read();
										switch(k)
										{
											case -1 :
													continue;
											/**
											* Check if text is  $!$ or $! 
											* This we are doing to see if already insert action has taken place 
											* than take no action 
											*/
											case '$':
											case ' ':
										       case '\n':
										       case '\t':
						        	                	        	fout.write(j);
                					                                		fout.write(i);
		                                					                fout.write(k);
													break;
											/**
                                                             				 * If text is $!{velocityCount} , ouput should be $!{Control_velocityCount}
                                                            				 */
                                          			                      	case '{':		
         			                                                                      	 l=br.read();
                                			                                                 if(l==-1)
                                                        			                         continue;
			                                                                                 if(l=='v')
                        			                                                         {
                                			                                                 	 fout.write(j);
                                			                                                 	 fout.write(i);
                                                        			                     		 i='{';
                                                                                				 k='v';
                                                                          				 }
                                                                     				         else
                                                                			                 {
														/**
														 * When text is $!{} ouput should
														 * be $!${}
														 */
                                                                           			         	fout.write(j);
                                                                          			                fout.write(i);
														fout.write('$');
                                                                            			                fout.write(k);
                                                                            			                fout.write(l);
                                                                                 			        /**
                                                                                    			        * Break from k switch
                                                                                 			        */
                                                                                      			  	break;
                                                                                			}
											case 'v':			
													/**
													* Mark at this position
													*/
													br.mark(20);
													/**
													* This for loop checks if velocityCount is found or not
													* 'b' checks if pattern $velocityCount is found or not.
													*/
													boolean b=false;
													/**
													 * eof checks end of file
													 */
													boolean eof=false;
													int c1=0;
													iss:	for(int c=0;c<charr.length;c++)
													{
														c1=br.read();
														char c2=(char)c1;
													
														if(c1==-1)
														{
															eof=true;
															break iss;
														}	
												
														if(c1 != charr[c])
														{
															b=true;
															break iss;
														}	
													}//for
													if(eof)
													{
														i = -1;
														continue;
													}
													/**
													* If pattern  '$!velocityCount' is found see if it ends with
													* ' ','\n','!','=','$','"','\t','}'
													* and output is $!$Control_velocityCount
													*/
													if(!b)
													{
														c1=br.read();
														switch(c1)
														{
															case -1 :
														    			i=-1;
														    			continue;
														       case ' ' :		
														       case '\n':		
														       case '\t':		
														       case '$' :		
														       case '"' :		
														       case '}' :	
			  														/**
			   														* Check that if text has '{' it should not have
			   														* arithmatic symbols for case $!{velocityControl+} 
			   														* ${velocityControl+}
			   														*/
																	fout.write(j);
																	fout.write(i);
																	/**
																	* Check if case is $!{v
																	* or $!v that is '{'
																	*/
																	if(i=='!')
																	fout.write('$');
        			        	                		       							fout.write(byrr);
             						   	           				        	    		fout.write(c1);
																	break;
														       case '+' :		
														       case '-' :		
														       case '*' :		
														       case '/' :		
														       case '%' :		
														       case '>' :		
														       case '<' :		
														       case '=' :		
														       case '!' :		
																	boolean b1= i=='!';
																	if(b1)
			  														{
																		fout.write(j);
																		fout.write(i);
																		/**
																	 	* Check if case is $!{v
																	 	* or $!v that is '{'
																	 	*/
																		if(i=='!')
																		fout.write('$');
        			        	                		       								fout.write(byrr);
             						   	           				        	    			fout.write(c1);
																		break;
																	}//if(b1)
																	else
																	{}
																	/**
																	 * Go to default case
																	 */
											        			default :
																	
																	fout.write(j);
																	fout.write(i);
																	if(i=='!')
																	fout.write('$');
																	fout.write(k);
																	fout.write(bcharr);
																	fout.write(c1);
														}//switch(c1)
													}//if(!b) ends
													else
													{
														/**
														 * If text is $!velocityhello ouput
														 * should be $!$velocityhello
														 * or if text is $!{velokk} output
														 * should be $!${velokk}
														 */
														fout.write(j);
														fout.write(i);
														if(i=='!')
														fout.write('$');
														fout.write(k);
														br.reset();
													}
													/**
													 *  Break from k switch
													 */
													break;
											/**
											* If text is not $!$!$! that is already insert has not taken place than 
											* Here we inserting $ between of $! and k where k is character other than space  $,newline
											* for text $!data.getMessage() or $!foo 
											*/
											default :

													fout.write(j);
			       		    	        		                                fout.write(i);
													fout.write('$');
                					        	                                fout.write(k);
										}//switch
										/**
										* Outer Switch break
										*/
										break;
                                                        	/**
                                                        	* If text is ${velocityCount} , ouput should be ${Control_velocityCount}
                                                        	*/
						       		case '{':
										k=br.read();
										if(k==-1)
										continue;
										if(k=='v')
										{
											fout.write(j);
											j='{';
										/**
										 * here we have made i='v' so that  it goes to next case
										 * with these values see below we have not use break at end  if else
										 * but within else so that when text is ${v go to case $v with 
										 * values made fit $v case
										 */
											i='v';
										}
										else
										{
											fout.write(j);
											fout.write(i);
											fout.write(k);
											/**
										 	* Break from i switch
										 	*/
											break;
										}
                                                        	/**
                                                        	* If text is $velocityCount , ouput should be $Control_velocityCount
                                                        	*/
								case 'v':
										/**
										* Mark at this position
										*/
										br.mark(20);
										/**
										* This for loop checks if velocityCount is found or not
										* 'b' checks if pattern $velocityCount is found or not.
										*/
										boolean b=false;
										/**
										 * eof checks end of file
										 */
										boolean eof=false;
										int c1=0;
										iss:	for( int c=0 ;c< charr.length ;c++)
										{
											c1=br.read();
											char c2=(char)c1;
											if(c1==-1)
											{
												eof=true;
												break iss;
											}
											if(c1 != charr[c])
											{
												b=true;
												break iss;
											}	
										}//for
										if(eof)
										{
											i = -1;
											continue;
										}
										/**
										* If pattern  '$velocityCount' is found see if it ends with
										* ' ','\n','!','=','$','"','\t','}'
										*/
										if(!b)
										{
											c1=br.read();
											switch(c1)
											{
												case -1 :
											    			i=-1;
											    			continue;
											       case ' ' :		
											       case '\n':		
											       case '\t':		
											       case '$' :		
											       case '"' :		
											       case '}' :	
														fout.write(j);// j=$ or {
                	                		       							fout.write(byrr);
             			   	           				        	    		fout.write(c1);
														break;
											       case '%' :		
											       case '<' :		
											       case '>' :		
											       case '+' :		
											       case '-' :		
											       case '/' :		
											       case '*' :		
											       case '=' :		
											       case '!' :		
														boolean b1= j=='$';
														if(b1)
														{
															fout.write(j);// j=$ or {
                	                		       								fout.write(byrr);
             			   	           				        	    			fout.write(c1);
															break;
														}
														else
														/**
														 * Go to default
														 */
														{}
											        default :
														fout.write(j);
														fout.write(i);
														fout.write(bcharr);
														fout.write(c1);
											}//switch(c1)
										}//if(!b) ends
										else
										{
											fout.write(j);
											fout.write(i);
											br.reset();
										}
										/**
										 *  Break from i switch
										 */
										break;
								/**
								* If text is $foo or ${foo} 
								* there should be no change
								*/
								default :
 		                                  	  	   		fout.write(j);
                		                        	        	fout.write(i);

							}//switch
							/**
							* Break from outer Switch
							*/
							break;
	 				default :
							fout.write(j);
			
				}//switch
			}while((i != -1)&&(j != -1)&&(k != -1)&&(l != -1));
			br.close();
			fout.close();
			fr.close();
			/**
			* rename brihaspati.brihaspati to the input file
			*/
			fout1.renameTo(inputfile);
		       /**
                        * Release memory
                        */
                        br=null;
			fr=null;
                        fout=null;
                        fout1=null;
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}//insert ends
}//class Controlinsert Ends


