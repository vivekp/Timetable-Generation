package org.iitk.brihaspati.modules.utils;

/*
 * @(#)QuestionBank.java
 *
 *  Copyright (c) 2004-2006 ETRG,IIT Kanpur. http://www.iitk.ac.in/
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
 */
/** 
 * @author: <a href="mailto:tarankhan1@yahoo.com">Tarannum Khan</a>
 * @author: <a href="mailto:manju_14feb@yahoo.com">Mithelesh Parihar</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in ">Awadhesh Kumar Trivedi</a>
 */

import java.util.Properties;
import org.xml.sax.helpers.*;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.apache.velocity.context.Context;
import org.apache.turbine.util.RunData;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.iitk.brihaspati.modules.utils.NotInclude;  
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Attr;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.Document;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;
import org.apache.xml.serialize.XMLSerializer;
import java.io.*;
import java.io.FileOutputStream;
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import java.util.Vector;
import org.apache.turbine.services.servlet.TurbineServlet;
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import java.util.*;
import java.io.File;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.apache.turbine.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import org.xml.sax.SAXException; 

public class QuestionBank{

	public static FileWriter fw;
	public static ReadQuestionBank rtm;

	public static Vector getModuleList(String filePath)
	{
		Vector v=new Vector();
		try 
		{
			v=rtm.getModuleName(filePath+"/ModuleList.xml");
		}//try
		catch(Exception e)
		{
			ErrorDumpUtil.ErrorLog("Exception  in  getModuleList [QuestionBank.java] util" +e);
		}
		return v;
	}
	public static boolean ModuleExists(String NewModule,Vector moduleName)
	{
			boolean MExist=false;
		try
		{
			for(int i=0;i<moduleName.size();i++)
			{
				if(NewModule.equals((String)moduleName.elementAt(i)))
					return MExist=true;
			}
		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("Exception  in ModuleExists [QuestionBank.java] util" +ex);
		}
		return MExist;
	}	
	public static  void walk(Node node)
    	{
		try{
			int type = node.getNodeType();
	 		switch(type)
        		{
				case Node.ELEMENT_NODE:
            			{
                			fw.write('<' +node.getNodeName() );
                			NamedNodeMap nnm = node.getAttributes();
                			if(nnm != null )
                			{
                    				int len = nnm.getLength() ;
                    				Attr attr;
                    				for(int i = 0; i < len; i++ )
                    				{
                        				attr = (Attr)nnm.item(i);
                        				fw.write(' '+ attr.getNodeName()+ "=\""+ attr.getNodeValue()+  '"' );
                    				}
                			}
                			fw.write('>');
                			break;
            			}//end of element
	 			case Node.ENTITY_REFERENCE_NODE:
            			{
               				fw.write('&' + node.getNodeName() + ';' );
               				break;

            			}//end of entity
            			case Node.CDATA_SECTION_NODE:
            			{
                   			fw.write( "<![CDATA["+ node.getNodeValue()+ "]]>" );
             	        		break;
            			}
				case Node.TEXT_NODE:
            			{
                			fw.write(node.getNodeValue());
               	 			break;
            			}
            			case Node.PROCESSING_INSTRUCTION_NODE:
            			{
                			fw.write("<?"+ node.getNodeName() ) ;
                			String data = node.getNodeValue();
                			if ( data != null && data.length() > 0 ) 
					{
                    				fw.write(' ');
                    				fw.write(data);
                			}
                			fw.write("?>");
                			break;
             			}
        		}//end of switch

			/**
			* Below Recursion is used to traverse through
			* the node and its inner nodes/elements
			* Note most important use is to provide closing tag of element
			*/

			for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling())
        		{
            			walk(child);
        		}
	 		if ( type == Node.ELEMENT_NODE )
        		{
            			fw.write("</" + node.getNodeName() + ">");
        		}

		}
		catch(Exception ex)
		{
			ErrorDumpUtil.ErrorLog("Exception  in  walk() [QuestionBank.java] util" +ex);
		}
	}
	public static String CreateModule(String module_name,String path,String filePath,String LangPropertyFile)
	{
        	String mess="";
		try{
 			Document doc= new DocumentImpl();
			Vector Modulelist=getModuleList(path);
			boolean existModule=ModuleExists(module_name,Modulelist);
			if(existModule==true)
                	{
                		//mess="This Module Already Exists";
                		mess=MultilingualUtil.ConvertedString("QueBankUtil_msg",LangPropertyFile);
                	}
        		else
			{
	 			FileOutputStream fos=new FileOutputStream(filePath);
	 			fos.close();
         			File  readpath=new  File(path+"/ModuleList.xml");

         			String str[] = new String[10000];
         			int i =0;
         			int startd = 0;
         			try{
         				if(readpath.exists())
        				{
         					BufferedReader br=new BufferedReader(new FileReader (readpath));
         					while ((str[i]=br.readLine()) != null)
            					{
            						if (str[i].equals("</Repository>"))
             	 					{
                						startd = i;
              						}
               						i= i +1;
            					}
             					br.close();
           				}
            				fw=new FileWriter(path+"/ModuleList.xml");
            				fw.write("<Repository>");
             				fw.write("\n");
             				fw.write("\n");
             				for(int x=1;x<startd;x++)
 					{
             					fw.write(str[x]+"\n");
             				}
             				fw.close();
             			}
				catch(IOException e)
				{
					mess="Exception  in  (try-2) CreateModule [QuestionBank.java] util" +e;  
				}
                 		fw=new FileWriter(path+"/ModuleList.xml",true);
         			try 
				{
                			Element root,item;
                 			Date d=new Date();
                 			String dat=d.toString();
                 			root = doc.createElement("Module");
                 			root.appendChild(doc.createTextNode(module_name));
                 			root.appendChild(doc.createTextNode("\n"));
                 			item = doc.createElement("ModuleName");
                 			item.appendChild(doc.createTextNode(module_name));
                 			root.appendChild(item);
                 			root.appendChild(doc.createTextNode("\n"));
                 			item = doc.createElement("QuestionBankFileName");
                 			item.appendChild(doc.createTextNode(module_name+"__QB.xml"));
                 			root.appendChild(item);
                 			root.appendChild(doc.createTextNode("\n"));
                 			item = doc.createElement("CreationDate");
                 			item.appendChild(doc.createTextNode(dat));
                 			root.appendChild(item);
                 			root.appendChild(doc.createTextNode("\n"));
                 			root.appendChild(doc.createTextNode(module_name));
                 			doc.appendChild(root);
          			}
                		catch ( Exception ex ) 
				{
					mess="Exception  in (try-3)  CreateModule [QuestionBank.java] util" +ex;  
				}
        			walk(doc);
        			fw.write("\n");
        			fw.write("\n");
				fw.write("</Repository>");
        			fw.write("\n");
        			fw.close();
			//	mess="Module Created Successfully";
				mess=MultilingualUtil.ConvertedString("QueBankUtil_msg4",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("QueBankUtil_msg5",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("Wikiaction5",LangPropertyFile);

 			}
		}
		catch(Exception ex)
		{
			mess="Exception  in (try-1)  CreateModule [QuestionBank.java] util" +ex;  
		
		}
        	return mess;
 	}
	public static int getQuestionId(String filePath)
	{
		int quesId = 1;
		try
		{
			Vector v=rtm.getQuesId(filePath);
			int temp = (Integer.parseInt((String)v.elementAt(0)));
			ErrorDumpUtil.ErrorLog("First"+Integer.toString(temp));
			for (int i=0; i<v.size(); i++)
			{
				int temp1 = (Integer.parseInt((String)v.elementAt(i)));		
				if (temp <= temp1);
				{
					temp=temp1;
				}
			}
			quesId=temp+1;
		}	
		catch(Exception Ex)
		{
			ErrorDumpUtil.ErrorLog("The Error in getQuestionId()-Question Bank Util !!"+Ex);
		}
		return quesId;
	}
	public static String NewXmlFile(String path)
	{
			int quesid=0;
         		try
			{
	 			File readpath=new File(path);   
         			int startd = 0;
         			String str[] = new String[10000];
	 			if(!(readpath.exists()))
	 				return readpath.getName() + "does not exists";
	 			if(!(Integer.parseInt((Long.toString(readpath.length()))) <= 0))
				{
         				int i =0;
	 				quesid=getQuestionId(path);
         				BufferedReader br=new BufferedReader(new FileReader(readpath));
         				while ((str[i]=br.readLine()) != null)
            				{
            					if (str[i].equals("</Topic>"))
              					{
                					startd = i;
              					}
               					i= i +1;
            				}
             				br.close();
	   			}
				else 
				{	
					quesid=1;
				}
            			fw=new FileWriter(path);
            			fw.write("<Topic>");
             			fw.write("\n");
             			fw.write("\n");
             			for(int x=1;x<startd;x++)
             			{
             				fw.write(str[x]+"\n");
             			}
             			fw.close();
             		}
			catch(IOException e)
			{
				return "Exception in NewXmlFile() [QuestionBank.java] util" +e;  
			}
		String qid=Integer.toString(quesid);
		return qid;
	}
	public static String InsertQuestionMultiple(String path,String type,String ques,String op1,String op2,String op3,String op4,String op5,String op6,String ans,String hint,String LangPropertyFile)
        {
		String mess=new String();
		try{
        		Document doc= new DocumentImpl();
        		Element root, item;
			String qid=NewXmlFile(path);
        		fw=new FileWriter(path,true);
            		root = doc.createElement("Start");
            		root.appendChild(doc.createTextNode(qid));
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("QuestionId");
            		item.appendChild(doc.createTextNode(qid));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Type");
            		item.appendChild(doc.createTextNode(type));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		item = doc.createElement("Question");
            		item.appendChild(doc.createTextNode(ques));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		item = doc.createElement("Option1");
            		item.appendChild(doc.createTextNode(op1));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		item = doc.createElement("Option2");
            		item.appendChild(doc.createTextNode(op2));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Option3");
            		item.appendChild(doc.createTextNode(op3));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Option4");
            		item.appendChild(doc.createTextNode(op4));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Option5");
            		item.appendChild(doc.createTextNode(op5));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	     		item = doc.createElement("Option6");
            		item.appendChild(doc.createTextNode(op6));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Answer");
            		item.appendChild(doc.createTextNode(ans));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Hint");
            		item.appendChild(doc.createTextNode(hint));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		root.appendChild(doc.createTextNode(qid));
            		doc.appendChild(root);
			walk(doc);
        		fw.write("\n");
        		fw.write("\n");
        		fw.write("</Topic>");
        		fw.write("\n");
        		fw.close();
			//mess="Question Inserted Sucessfully";
			mess=MultilingualUtil.ConvertedString("QueBankUtil_msg1",LangPropertyFile);
        	} 
		catch(Exception ex) 
		{
			mess="Exception  in   InsertQuestionMultiple  [QuestionBank.java] util" +ex;  
		}
        	return mess;
        }
	public static String InsertQuestionTF(String path,String type,String ques,String ans,String hint,String LangPropertyFile)
        {
		String mess=new String();
       		try
		{ 
			Document doc= new DocumentImpl();
        		Element main, root, item;
			String qid=NewXmlFile(path);
        		fw=new FileWriter(path,true);
            		root = doc.createElement("Start");
            		root.appendChild(doc.createTextNode(qid));
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("QuestionId");
            		item.appendChild(doc.createTextNode(qid));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Type");
            		item.appendChild(doc.createTextNode(type));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		item = doc.createElement("Question");
            		item.appendChild(doc.createTextNode(ques));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Answer");
            		item.appendChild(doc.createTextNode(ans));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Hint");
            		item.appendChild(doc.createTextNode(hint));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		root.appendChild(doc.createTextNode(qid));
            		doc.appendChild(root);
        		walk(doc);
        		fw.write("\n");
        		fw.write("\n");
        		fw.write("</Topic>");
        		fw.write("\n");
        		fw.close();
			//mess="Question Inserted Sucessfully";
			 mess=MultilingualUtil.ConvertedString("QueBankUtil_msg1",LangPropertyFile);
        	} 
		catch ( Exception ex ) 
		{
			mess="Exception  in   InsertQuestionTF  [QuestionBank.java] util" +ex;  
		}
        	return mess;
        }
	public static String InsertQuestionShort(String path,String type,String ques,String ans,String hint,String LangPropertyFile)
        {
		String mess=new String();
		try{
			Document doc= new DocumentImpl();
        		Element main, root, item;
			String qid=NewXmlFile(path);
        		fw=new FileWriter(path,true);
        		try
	 		{
            			root = doc.createElement("Start");
            			root.appendChild(doc.createTextNode(qid));
            			root.appendChild(doc.createTextNode("\n"));
	    			item = doc.createElement("QuestionId");
            			item.appendChild(doc.createTextNode(qid));
            			root.appendChild(item);
            			root.appendChild(doc.createTextNode("\n"));
	    			item = doc.createElement("Type");
            			item.appendChild(doc.createTextNode(type));
            			root.appendChild(item);
            			root.appendChild(doc.createTextNode("\n"));
            			item = doc.createElement("Question");
            			item.appendChild(doc.createTextNode(ques));
            			root.appendChild(item);
            			root.appendChild(doc.createTextNode("\n"));
	    			item = doc.createElement("Answer");
            			item.appendChild(doc.createTextNode(ans));
            			root.appendChild(item);
            			root.appendChild(doc.createTextNode("\n"));
	    			item = doc.createElement("Hint");
            			item.appendChild(doc.createTextNode(hint));
            			root.appendChild(item);
            			root.appendChild(doc.createTextNode("\n"));
            			root.appendChild(doc.createTextNode(qid));
            			doc.appendChild(root);
        		} 
			catch(Exception ex)
			{
				return	"Exception  in   InsertQuestionShort  [QuestionBank.java] util" +ex;
			}  
        		walk(doc);
        		fw.write("\n");
        		fw.write("\n");
        		fw.write("</Topic>");
        		fw.write("\n");
        		fw.close();
			//mess="Question Inserted Sucessfully";
			mess=MultilingualUtil.ConvertedString("QueBankUtil_msg1",LangPropertyFile);
		}
		catch(Exception ex)
		{
		
		}
        	return mess;
        }
	public static String InsertQuestionMatch(String path,String type,String ques,String ans,String hint,String LangPropertyFile) 
        {
		String mess=new String();
        	try{
			Document doc= new DocumentImpl();
        		Element main, root, item;
			String qid=NewXmlFile(path);
        		fw=new FileWriter(path,true);
            		root = doc.createElement("Start");
            		root.appendChild(doc.createTextNode(qid));
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("QuestionId");
            		item.appendChild(doc.createTextNode(qid));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Type");
            		item.appendChild(doc.createTextNode(type));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		item = doc.createElement("Question");
            		item.appendChild(doc.createTextNode(ques));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\t"));
	    		item = doc.createElement("Answer");
            		item.appendChild(doc.createTextNode(ans));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
	    		item = doc.createElement("Hint");
            		item.appendChild(doc.createTextNode(hint));
            		root.appendChild(item);
            		root.appendChild(doc.createTextNode("\n"));
            		root.appendChild(doc.createTextNode(qid));
            		doc.appendChild(root);
        		walk(doc);
        		fw.write("\n");
        		fw.write("\n");
        		fw.write("</Topic>");
        		fw.write("\n");
        		fw.close();
			//mess="Question Inserted Sucessfully";
			mess=MultilingualUtil.ConvertedString("QueBankUtil_msg1",LangPropertyFile);
        	}//try 
		catch(Exception ex)
		{
			mess="Exception  in   InsertQuestionMatch  [QuestionBank.java] util" +ex;
		}//catch
        	return mess;
        }
	public static String deleteModule(String path,String Mname,String LangPropertyFile)
        {
		String mess="";
                try{
			//File f=new File(file);
			String str[] = new String[10000];
                	int i =0;
                	int startd = 0;
                	int stopd = 0;
			BufferedReader br=new BufferedReader(new FileReader (path+"/ModuleList.xml"));
                	while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Module>"+Mname))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(Mname+"</Module>"))
                                {
                                        stopd = i;
 
                                }
                        	i= i +1;
                        }
                        br.close();
			fw=new FileWriter(path+"/ModuleList.xml");
                        for(int x=0;x<startd;x++)
                        {
                                fw.write(str[x]+"\r\n");
                        }
                        for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\r\n");
                        }
                        fw.close();
			File f=new File(Mname+"__QB.xml");
			deleteFile(f);
			//mess="Module Deleted Successfully";
			mess=MultilingualUtil.ConvertedString("QueBankUtil_msg2",LangPropertyFile);
		}
		catch(Exception ex) 
		{
			return	"Exception  in deleteRepositoryModule [QuestionBank.java] util" +ex;
		}
		return mess;

        }
	public static String delete(String file,String LangPropertyFile)throws Exception
        {
		String mess="";
		File f=new File(file);
                if(f.isFile())
		{
                	f.delete();
                }
	//	mess="File Deleted Successfully";
		mess=MultilingualUtil.ConvertedString("QueBankUtil_msg2",LangPropertyFile);
		return mess;
        }

 	public static void deleteFile(File f)
        {
                int i=0;
                if(f.isDirectory())
                {
                        File file[]=f.listFiles();
                        while(i<file.length)
                        {
                                deleteFile(file[i]);
                                i++;
                        }
                }
                f.delete();
        }

	public static String deleteQuestion(String file,String id,String LangPropertyFile) throws Exception
        {
		String mess="";
                try{
		String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
		 BufferedReader br=new BufferedReader(new FileReader (file));
 
                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Start>"+id))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(id+"</Start>"))
                                {
                                        stopd = i;
 
                                }
                        i= i + 1;
                        }
                        br.close();
			 FileWriter fw=new FileWriter(file);
                        for(int x=0;x<startd;x++)
                        {
                                fw.write(str[x]+"\r\n");
                        }
                        for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\r\n");
                        }
 
                        fw.close();
		//mess="Question Deleted Successfully";
		mess=MultilingualUtil.ConvertedString("brih_qus",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("c_msg9",LangPropertyFile);
		}
		catch(Exception ex) {
		return	"Exception  in deleteQuestion [QuestionBank.java] util" +ex;
	}
		return mess;

        }
	 public static String EditMultiple(String path,String qid,String type,String ques,String op1,String op2,String op3,String op4,String op5,String op6,String ans,String hint,String LangPropertyFile)throws Exception
        {
        try
		{
		String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
		 BufferedReader br=new BufferedReader(new FileReader (path));
 
                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Start>"+qid))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(qid+"</Start>"))
                                {
                                        stopd = i;
 
                                }
                        i= i + 1;
                        }
                        br.close();
			 FileWriter fw1=new FileWriter(path);
                      	 for(int x=0;x<startd;x++)
                        {
                                fw1.write(str[x]+"\n");
                        }
			 fw1.close(); 
	    Document doc= new DocumentImpl();
            Element main, root, item;
            fw=new FileWriter(path,true);
            root = doc.createElement("Start");
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(ques));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Option1");
            item.appendChild(doc.createTextNode(op1));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Option2");
            item.appendChild(doc.createTextNode(op2));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option3");
            item.appendChild(doc.createTextNode(op3));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option4");
            item.appendChild(doc.createTextNode(op4));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option5");
            item.appendChild(doc.createTextNode(op5));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	     item = doc.createElement("Option6");
            item.appendChild(doc.createTextNode(op6));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            doc.appendChild(root);
        walk(doc);
        fw.write("\n");
        fw.write("\n");
                    for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\n");
                        } 
        fw.close();

        } 
		catch ( Exception ex ) {
		return	"Exception  in EditMultiple  [QuestionBank.java] util" +ex;
}
	//String mess="Question Updated Sucessfully";
	String mess=MultilingualUtil.ConvertedString("brih_qus",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("c_msg5",LangPropertyFile);
        return mess;
        }


	 public static String EditTF(String path,String qid,String type,String ques,String ans,String hint,String LangPropertyFile)throws Exception
        {
	 String mess = null;
        try
		{
		String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
		 BufferedReader br=new BufferedReader(new FileReader (path));
 
                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Start>"+qid))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(qid+"</Start>"))
                                {
                                        stopd = i;
 
                                }
                        i= i + 1;
                        }
                        br.close();
			 FileWriter fw1=new FileWriter(path);
                      	 for(int x=0;x<startd;x++)
                        {
                                fw1.write(str[x]+"\n");
                        }
			 fw1.close(); 
	    Document doc= new DocumentImpl();
            Element main, root, item;
            fw=new FileWriter(path,true);
            root = doc.createElement("Start");
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(ques));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            doc.appendChild(root);
        walk(doc);
        fw.write("\n");
                    for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\n");
                        } 
        fw.close();

        } 
	catch ( Exception ex ) {
	mess = "Exception in [QuestionBank.java] util  in EditTF "+ex;
	return mess;
	 }
	//mess="Question Updated Sucessfully";
	mess=MultilingualUtil.ConvertedString("brih_qus",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("c_msg5",LangPropertyFile);
        return mess;
        }
	 public static String EditShort(String path,String qid,String type,String ques,String ans,String hint,String LangPropertyFile)throws SAXException
        
	{
	String mess;
        try
		{
		String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
		 BufferedReader br=new BufferedReader(new FileReader (path));
 
                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Start>"+qid))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(qid+"</Start>"))
                                {
                                        stopd = i;
 
                                }
                        i= i + 1;
                        }
                        br.close();
			 FileWriter fw1=new FileWriter(path);
                      	 for(int x=0;x<startd;x++)
                        {
                                fw1.write(str[x]+"\n");
                        }
			 fw1.close(); 
	    Document doc= new DocumentImpl();
            Element main, root, item;
            fw=new FileWriter(path,true);
            root = doc.createElement("Start");
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(ques));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            doc.appendChild(root);
        walk(doc);
        fw.write("\n");
                    for(int y=stopd+1;y<i;y++)
                        {
                                fw.write(str[y]+"\n");
                        } 
        fw.close();

        } 
	catch ( Exception ex ) {
	mess = "Exception in [QuestionBank.java] util  in EditShort "+ex;
	return mess;
		}
	//mess="Question Updated Sucessfully";
	mess=MultilingualUtil.ConvertedString("brih_qus",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("c_msg5",LangPropertyFile);
        return mess;
        }
	 public static String EditMatch(String path,String qid,String type,String ques,String ans,String hint,String LangPropertyFile)throws Exception
        {
        try
		{
		String str[] = new String[10000];
                int i =0;
                int startd = 0;
                int stopd = 0;
		 BufferedReader br=new BufferedReader(new FileReader (path));
 
                        while ((str[i]=br.readLine()) != null)
                        {
                                if (str[i].equals("<Start>"+qid))
                                {
                                        startd = i;
                                }
                                else if(str[i].equals(qid+"</Start>"))
                                {
                                        stopd = i;
 
                                }
                        i= i + 1;
                        }
                        br.close();
		        FileWriter fw1=new FileWriter(path);
                      	 for(int x=0;x<startd;x++)
                        {
                         fw1.write(str[x]+"\n");
                        }
			 fw1.close(); 
	    Document doc= new DocumentImpl();
            Element main, root, item;
            fw=new FileWriter(path,true);
            root = doc.createElement("Start");
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(ques));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            doc.appendChild(root);
        walk(doc);
        fw.write("\n");
                    for(int y=stopd+1;y<i;y++)
                        {
                    fw.write(str[y]+"\n");
                        } 
        fw.close();

        } 
	catch ( Exception ex ) {	
	return "Exception in [QuestionBank.java] util  in EditShort "+ex;

	}
	//String mess="Question Updated Sucessfully";
	String mess=MultilingualUtil.ConvertedString("brih_qus",LangPropertyFile)+" "+MultilingualUtil.ConvertedString("c_msg5",LangPropertyFile);
        return mess;
        }
 public static Vector UploadMultiple(File f,String path,String type,String fileName)throws Exception
	{ 
	
		Vector file=new Vector();
	    	Document doc= new DocumentImpl();
		String str[] = new String[10000];
                int i =0;
                 BufferedReader br=new BufferedReader(new FileReader (f));
		
		 
                        while ((str[i]=br.readLine()) != null)
                        {
			file.addElement(str[i]);
			}


		 Element main,root,item;
 
        main = doc.createElement("Topic");
        main.appendChild(doc.createTextNode("\n"));
	String question = "";
        String qid  = "";
        String op1  = "";
        String op2  = "";
        String op3  = "";
        String op4  = "";
        String op5  = "";
        String op6  = "";
        String ans  = "";
        String hint  = "";
        for(int j = 0 ; j < file.size(); j++)
        {
		 //extract data from the vector
            String data = (String)file.elementAt(j);
            StringTokenizer st = new StringTokenizer(data,"$");
 
            //should check whether there is data not implemented
 
            question = st.nextToken();
            op1 =  st.nextToken();
            op2 =  st.nextToken();
            op3 =  st.nextToken();
            op4 =  st.nextToken();
            op5 =  st.nextToken();
            op6 =  st.nextToken();
            ans =  st.nextToken();
            hint =  st.nextToken();
            fw=new FileWriter(path+"/"+fileName+".xml");
            root = doc.createElement("Start");
	    qid=Integer.toString(j+1);
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(question));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Option1");
            item.appendChild(doc.createTextNode(op1));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Option2");
            item.appendChild(doc.createTextNode(op2));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option3");
            item.appendChild(doc.createTextNode(op3));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option4");
            item.appendChild(doc.createTextNode(op4));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option5");
            item.appendChild(doc.createTextNode(op5));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Option6");
            item.appendChild(doc.createTextNode(op6));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            main.appendChild(root);
	    main.appendChild(doc.createTextNode("\n"));
        	}//end of for
        	doc.appendChild(main);
	walk(doc);
		fw.close();
		f.delete();
		return file;

	}


 public static Vector UploadTF(File f,String path,String type,String fileName)throws Exception
	{ 
		Vector file=new Vector();
				

	    	Document doc= new DocumentImpl();


/**
* Manav ---- String str[] is not needed here 
* String array should be replaced by string
* ----------- BUG  ----------------------
*/
		String str[] = new String[10000];
                int i =0;
                 BufferedReader br=new BufferedReader(new FileReader (f));
		 
                        while ((str[i]=br.readLine()) != null)
                        {
			file.addElement(str[i]);
			}
		 Element main,root,item;
 
        main = doc.createElement("Topic");
        main.appendChild(doc.createTextNode("\n"));
	String question = "";
        String qid  = "";
        String ans  = "";
        String hint  = "";
        for(int j = 0 ; j < file.size(); j++)
        {
		 //extract data from the vector
            String data = (String)file.elementAt(j);
            StringTokenizer st = new StringTokenizer(data,"$");
 
            //should check whether there is data not implemented
 
            question = st.nextToken();
            ans =  st.nextToken();
            hint =  st.nextToken();
	
	   /**
	   * Create uploaded files's .xml 
	   */

            fw=new FileWriter(path+"/"+fileName+".xml");
            root = doc.createElement("Start");
	    qid=Integer.toString(j+1);
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(question));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            main.appendChild(root);
	    main.appendChild(doc.createTextNode("\n"));
        	}//end of for
        	doc.appendChild(main);
		walk(doc);
		fw.close();
		
	       /**
		* Delete uploaded file after  .xml is created
		*/

		f.delete();
		return file;
	}
 public static Vector UploadShort(File f,String path,String type,String fileName)throws Exception
	{ 
		Vector file=new Vector();
	    	Document doc= new DocumentImpl();
		String str[] = new String[10000];
                int i =0;
                 BufferedReader br=new BufferedReader(new FileReader (f));
		 
                        while ((str[i]=br.readLine()) != null)
                        {
			file.addElement(str[i]);
			}
		 Element main,root,item;
 
        main = doc.createElement("Topic");
        main.appendChild(doc.createTextNode("\n"));
	String question = "";
        String qid  = "";
        String ans  = "";
        String hint  = "";
        for(int j = 0 ; j < file.size(); j++)
        {
		 //extract data from the vector
            String data = (String)file.elementAt(j);
            StringTokenizer st = new StringTokenizer(data,"$");
 
            //should check whether there is data not implemented
 
            question = st.nextToken();
            ans =  st.nextToken();
            hint =  st.nextToken();
            fw=new FileWriter(path+"/"+fileName+".xml");
            root = doc.createElement("Start");
	    qid=Integer.toString(j+1);
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(question));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            main.appendChild(root);
	    main.appendChild(doc.createTextNode("\n"));
        	}//end of for
        	doc.appendChild(main);
	walk(doc);
		fw.close();
		f.delete();
		return file;
	}
 public static Vector UploadMatch(File f,String path,String type,String fileName)throws Exception
	{ 
		Vector file=new Vector();
	    	Document doc= new DocumentImpl();
		String str[] = new String[10000];
                int i =0;
                 BufferedReader br=new BufferedReader(new FileReader (f));
		 
                        while ((str[i]=br.readLine()) != null)
                        {
			file.addElement(str[i]);
			}
		 Element main,root,item;
 
        main = doc.createElement("Topic");
        main.appendChild(doc.createTextNode("\n"));
	String question = "";
        String qid  = "";
        String ans  = "";
        String hint  = "";
        for(int j = 0 ; j < file.size(); j++)
        {
		 //extract data from the vector
            String data = (String)file.elementAt(j);
            StringTokenizer st = new StringTokenizer(data,"$");
 
            //should check whether there is data not implemented
 
            question = st.nextToken();
            ans =  st.nextToken();
            hint =  st.nextToken();
            fw=new FileWriter(path+"/"+fileName+".xml");
            root = doc.createElement("Start");
	    qid=Integer.toString(j+1);
            root.appendChild(doc.createTextNode(qid));
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("QuestionId");
            item.appendChild(doc.createTextNode(qid));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Type");
            item.appendChild(doc.createTextNode(type));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            item = doc.createElement("Question");
            item.appendChild(doc.createTextNode(question));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Answer");
            item.appendChild(doc.createTextNode(ans));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
	    item = doc.createElement("Hint");
            item.appendChild(doc.createTextNode(hint));
            root.appendChild(item);
            root.appendChild(doc.createTextNode("\n"));
            root.appendChild(doc.createTextNode(qid));
            main.appendChild(root);
	    main.appendChild(doc.createTextNode("\n"));
        	}//end of for
        	doc.appendChild(main);
		walk(doc);
		fw.close();
		f.delete();
		return file;
  } //method  
}//class
