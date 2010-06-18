package org.iitk.brihaspati.modules.utils;
/*
 * @(#)XmlWriter.java
 *
 *  Copyright (c) 2005-2007 ETRG,IIT Kanpur.
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

import java.io.FileWriter;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.Arrays;
import java.util.Hashtable;
import org.iitk.brihaspati.modules.utils.XmlData;
import org.iitk.brihaspati.modules.utils.XmlReader;
import org.xml.sax.Attributes;
/**
* This class write xml and insert element,append element,remove element in existing XML file
* @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
* @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
* @author <a href="mailto:nksinghiitk@yahoo.co.in">Nagendra Kumar Singh</a>
*/

public class XmlWriter
{
	protected XmlReader xmlReader;
	protected Hashtable ht;
	public String fileName;
	protected int dataOffset=0,fileOffset=1,act=2;

	/**
	* @param fileName String
	*/
	public XmlWriter(String fileName) throws Exception
	{	
		xmlReader=new XmlReader(fileName);
		this.fileName=fileName;
		ht=xmlReader.getAllElements();
	}
	
	public int getElementCount(String element)
	{
		Vector v=(Vector)ht.get(element);
		return v.size();
	}
	/**
	* In this method append element in existing xml
	* @param element String 
	* @param data String
	* @param nameValue Attributes
	*/

	public void appendElement(String element,String data,Attributes nameValue)
	{
		Vector v=(Vector)ht.get(element);
		if(v==null)
			v=new Vector();
		XmlData xmlData=new XmlData();
		xmlData.setName(element);
		xmlData.setData(data);
		xmlData.setAttributes(nameValue);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		parent.setSubElements(xmlData);
		
		v.addElement(xmlData);	
		ht.put(element,v);
	}
	/**
	* In this method insert element in xml
	* @param element String 
	* @param data String
	* @param nameValue Attributes
	* @param seqno Integer
	*/
	
	public void insertElement(String element,String data,Attributes nameValue,int seqno)
	{
		Vector v=(Vector)ht.get(element);
		if(v==null)
			v=new Vector();
		XmlData xmlData=new XmlData();
		xmlData.setName(element);
		xmlData.setData(data);
		xmlData.setAttributes(nameValue);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		if((element.equals("File"))||(element.equals("Group")))
			parent.setSubElements(fileOffset+seqno,xmlData);
		else if(element.equals("Desc"))
			parent.setSubElements(dataOffset+seqno,xmlData);
		else
			parent.setSubElements(seqno,xmlData);
		v.add(seqno,xmlData);
		ht.put(element,v);
	}
	/**
	* In this method insert element in xml
	* @param xmlData XmlData 
	* @param seqno Integer
	*/

	public void insertElement(XmlData xmlData,int seqno)
	{
		String element=xmlData.getName();
		Vector v=(Vector)ht.get(element);
		if(v==null)
			v=new Vector();
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		if((element.equals("File"))||(element.equals("Group")))
			parent.setSubElements(fileOffset+seqno,xmlData);
		else if(element.equals("Desc"))
			parent.setSubElements(dataOffset+seqno,xmlData);
		else
			parent.setSubElements(seqno,xmlData);
		v.add(seqno,xmlData);
		ht.put(element,v);
	}
	/**
	* In this method insert element in xml
	* @param element String 
	* @param seqno Integer
	* @return XmlData
	*/


	public XmlData getElement(String element,int seqno) throws Exception
	{
		Vector v=(Vector)ht.get(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		int offset=0;
		if((element.equals("File"))||(element.equals("Group")))
			offset=fileOffset;
		else if(element.equals("Desc"))
			offset=dataOffset;
		XmlData xmlnode=parent.getSubElement(seqno+offset);
		return xmlnode;
	}
	/**
	* In this method set element in xml
	* @param xmlData XmlData 
	* @param seqno Integer
	*/

	public void setElement(XmlData xmlData,int seqno) throws Exception
	{
		String element=xmlData.getName();
		Vector v=(Vector)ht.get(element);
		if(v==null)
			v=new Vector();
		xmlData.setName(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		if((element.equals("File"))||(element.equals("Group")))
			parent.replaceSubElements(fileOffset+seqno,xmlData);
		else if(element.equals("Desc"))
			parent.replaceSubElements(dataOffset+seqno,xmlData);
		else
			parent.replaceSubElements(seqno,xmlData);
		v.set(seqno,xmlData);
		ht.put(element,v);
	}
	/**
	* In this method remove element in xml
	* @param element String 
	* @param seqno Integer
	*/


	public void removeElement(String element,int seqno[])
	{	
		Vector v=(Vector)ht.get(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		XmlData xmlData;
		int offset=0;
		if((element.equals("File"))||(element.equals("Topic")))
			offset=fileOffset;
		else if(element.equals("Group"))
                        offset=act;
                        //offset=0;
		else if(element.equals("Desc"))
			offset=dataOffset;
		else if(element.equals("activity"))
                        offset=dataOffset;
                        //offset=actOffset;

		for(int i=0;i<seqno.length;i++)
		{
			seqno[i]=seqno[i]+offset;
		}
		int removedCount=0;
		parent.removeSubElements(seqno);
		Arrays.sort(seqno);
		for(int i=0;i<seqno.length;i++)
		{
			v.removeElementAt(seqno[i]-removedCount-offset);
			++removedCount;
		}

		ht.put(element,v);
	}
	/**
	* In this method change attributes in xml
	* @param element String
	* @param nameValue Attributes 
	* @param seqno Integer
	*/
	public void changeAttributes(String element,Attributes nameValue,int seqno) throws Exception
	{
		Vector v=(Vector)ht.get(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		int offset=0;
		if((element.equals("File"))||(element.equals("Group")))
			offset=fileOffset;
		else if(element.equals("Desc"))
			offset=dataOffset;
	//		offset=0;  uncomment after testing
		XmlData xmlnode=parent.getSubElement(seqno+offset);
		xmlnode.setAttributes(nameValue);
		v.set(seqno,xmlnode);
		ht.put(element,v);
	}
	/**
	* In this method change data in xml
	* @param element String 
	* @param Data String
	* @param seqno Integer
	*/
	public void changeData(String element,String Data,int seqno)
	{
		Vector v=(Vector)ht.get(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		int offset=0;
		if((element.equals("File"))||(element.equals("Group"))||(element.equals("activity")))
			offset=fileOffset;
		else if(element.equals("Desc"))
			offset=dataOffset;
		
		XmlData xmlnode=parent.getSubElement(seqno+offset);
		xmlnode.setData(Data);
		v.set(seqno,xmlnode);
		ht.put(element,v);
		
	}
	
	public void insertData(String element,String Data,int seqno)
        {
                Vector v=(Vector)ht.get(element);
                Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
                XmlData parent=(XmlData)parentVector.elementAt(0);
        }

	/**
	* In this method remove element in xml
	* @param element String 
	* @param seqno Integer
	* @return XmlData
	*/

	public XmlData removeElement(String element,int seqno)
	{
		Vector v=(Vector)ht.get(element);
		Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
		XmlData parent=(XmlData)parentVector.elementAt(0);
		XmlData xmlData;
		int offset=0;
		if(element.equals("File"))
			offset=fileOffset;
		else if(element.equals("Desc"))
			offset=dataOffset;
///////////
		else if(element.equals("Topic"))
                        offset=0;
		else if(element.equals("Group"))
                        offset=act;
                else if(element.equals("activity"))
                        offset=fileOffset;
                else if(element.equals("Repository"))
                        offset=fileOffset;
                else if(element.equals("Bookmarks"))
                        offset=fileOffset;
///////////////////////////

		int array[]={seqno+offset};
		parent.removeSubElements(array);
		xmlData=(XmlData)v.elementAt(seqno);
		v.removeElementAt(seqno);
		ht.put(element,v);
		return xmlData;
	}
	
	/**
	* In this method change sequence number of existing data in xml
	* @param element String
	* @param initialSeqno Integer
	* @param finalSeqno Integer
	* @param exchange boolean
	*/
	public void changeSeqNo(String element,int initialSeqno,int finalSeqno,boolean exchange)
	{
		
		if(initialSeqno!=finalSeqno)
		{
			Vector v=(Vector)ht.get(element);
			Vector parentVector=(Vector)ht.get(xmlReader.getRootElement());
			XmlData parent=(XmlData)parentVector.elementAt(0);
			int offset=0;
			if(element.equals("File"))
				offset=fileOffset;
			else if(element.equals("Desc"))
				offset=dataOffset;
			XmlData initialXmlData=parent.getSubElement(initialSeqno+offset);

			if(exchange)
			{
				XmlData finalXmlData=parent.getSubElement(finalSeqno+offset);
				parent.replaceSubElements(offset+initialSeqno,finalXmlData);
				parent.replaceSubElements(offset+finalSeqno,initialXmlData);

				v.set(initialSeqno,finalXmlData);
				v.set(finalSeqno,initialXmlData);
			}
			else
			{
				int array[]={initialSeqno+offset};
				parent.removeSubElements(array);
				v.removeElementAt(initialSeqno);
				parent.setSubElements(offset+finalSeqno,initialXmlData);
			}
			ht.put(element,v);
		}
	}
	/**
	* In this method write xml in the given path
	*/
	public void writeXmlFile()
	{
		try
		{
		FileOutputStream fos=new FileOutputStream(fileName);
		Vector v=(Vector)ht.get(xmlReader.getRootElement());

		XmlData xmlData=(XmlData)v.elementAt(0);
		traverseAndPrint(xmlData,fos);
		fos.close();
		}catch(Exception ioe){}
	}
	public void traverseAndPrint(XmlData xd,FileOutputStream fos)
	{
		try
		{
		fos.write( ("<"+xd.getName()).getBytes());
		Attributes ats=xd.getAttributes();
		int atsLength;
		if(ats!=null)
		{
			atsLength=ats.getLength();
			for(int i=0;i<atsLength;i++)
			{
				fos.write( (" "+ ats.getLocalName(i)+"=\""+ats.getValue(i)+"\"").getBytes());
			}
		}
		String Data=xd.getData();
		Vector v=xd.getSubElements();
		if(Data==null)
		{
			if(v.size()==0)
			{
				fos.write("/>\n".getBytes());
			}
			else
			{
				fos.write(">".getBytes());
				for(int i=0;i<v.size();i++)
				{
					traverseAndPrint( (XmlData)v.elementAt(i),fos );
				}
				fos.write( ("</"+xd.getName()+">\n").getBytes());
			}
		}
		else
		{
			if(v.size()==0)
			{
				fos.write( (">"+Data).getBytes());
				fos.write( ("</"+xd.getName()+">\n").getBytes());
			}
			else
			{				
				fos.write( (">"+Data).getBytes());
				for(int i=0;i<v.size();i++)
				{
					traverseAndPrint( (XmlData)v.elementAt(i),fos );
				}
				fos.write( ("</"+xd.getName()+">\n").getBytes() );
			}

		}
		}catch(Exception ioe){}
	}
}
