package org.iitk.brihaspati.modules.utils;

/*@(#) XmlReader.java
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
import java.util.Stack;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileOutputStream;
import org.iitk.brihaspati.modules.utils.XmlData;
/**
 * This class Read XML file
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *
 */

public class XmlReader extends DefaultHandler
{
	private String rootElement;	
	private StringBuffer sb;
	private Hashtable elements;
	private Stack st;

	private StringBuffer complete_string=new StringBuffer();
	/**
	* Read xml file
	* @param fileName String
	*/
	public XmlReader(String fileName) throws Exception
	{
		elements=new Hashtable();
		st=new Stack();
        	SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser saxParser = factory.newSAXParser();

		/**
                 * Here 'this' is the DefaultHandler
		 * On parsing, startElement(),characters() and
		 * endElement() methods are executed one by one
                 */

		File file_xml=new File(fileName);
        	saxParser.parse( file_xml,this );
	}
	
	/**
	 * This is a method of DefaultHandler overridden here
	* @param a String
	* @param b String
	* @param name String
	* @param attrs Attributes
	*/

	public void startElement(String a,String b,String name,Attributes attrs) throws SAXException
	{
		if(rootElement==null)
			rootElement=name;
		XmlData tempData=new XmlData();			
		tempData.setName(name);			
		if(attrs!=null)
			tempData.setAttributes(attrs);
		st.push(tempData);
	}

	/**
	* This is a method of DefaultHandler overridden here
	* @param buff char
	* @param offset String
	* @param len integer
	 */

	public void characters(char buff[],int offset,int len) throws SAXException
	{
		if(!st.empty())
		{
			XmlData tempData=(XmlData)st.pop();
				
			if(len!=0)
			{
				if( (tempData.getName()).equals("Desc") ){
					complete_string=complete_string.append(new String(buff,offset,len));
					tempData.setData(new String(complete_string));
				}
				else{
					tempData.setData( new String(buff,offset,len) );
				}
			}
			st.push(tempData);
		}
	}

	/**
	 * This is a method of DefaultHandler overridden here
	 * @param a String
	 * @param b String
	 * @param name String
	 */

	public void endElement(String a,String b,String name) throws SAXException
	{
		int seqno;
		if(!st.empty())
		{
			XmlData tempData=(XmlData)st.pop();
			String currentElement=tempData.getName();
			Vector elementData=(Vector)elements.get((Object)tempData.getName());
			if(elementData==null)
			{
				elementData=new Vector();
			}
			elementData.addElement((Object)tempData);
			elements.put(currentElement,(Object)elementData);
			if(!st.empty())
			{	
				XmlData parent=(XmlData)st.pop();
				parent.setSubElements(tempData);
				st.push(parent);
			}
		}	
	}

	public String getRootElement()
	{
		return rootElement;
	}
	/**
	* This method for get all element from XMl File
	*@param element String 
	*@param seqno integer
	*@return XmlData
	*/
	public XmlData getElement(String element,int seqno) throws ArrayIndexOutOfBoundsException
	{
		Vector vt=(Vector)elements.get(element);
		return (XmlData)vt.elementAt(seqno);		
	}
	public Hashtable getAllElements()
	{
		return elements;
	}
	/**
	* This method for get all elements from XMl File
	* @param element String 
	* @return XmlData[]
	*/
	public XmlData[] getElements(String element)
	{
		Vector vt=(Vector)elements.get(element);	
		XmlData temp[]=new XmlData[vt.size()]; 
		Enumeration Enum=vt.elements();	
		for(int i=0;i<vt.size();i++)
		{
			temp[i]=(XmlData)Enum.nextElement();
		}
		return temp;
	}
	/**
	* This method for get File value from XMl File
	* @param name String 
	* @param filename String 
	* @param date String 
	*/

	public void getFileValue(String name,String filename,String date){
	try
	{
		Vector fv=new Vector();
		XmlData datavalue=new XmlData();
		datavalue.getName();		

	}
	catch(Exception e){}
	}

}
