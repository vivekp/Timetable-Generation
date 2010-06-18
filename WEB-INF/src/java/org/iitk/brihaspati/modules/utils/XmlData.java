package org.iitk.brihaspati.modules.utils;
/*
 * @(#)XmlData.java
 *
 *  Copyright (c) 2005 ETRG,IIT Kanpur.
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

import java.util.Vector;
import java.util.Arrays;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
/**
 * This class set and get values Xml file with attributes and values
 * @author <a href="mailto:ammuamit@hotmail.com">Amit Joshi</a>
 * @author <a href="mailto:awadhesh_trivedi@yahoo.co.in">Awadhesh Kumar Trivedi</a>
 *
 */

public class XmlData
{
	private String name;
	private String topicname;
	private Attributes attributes;
	private String data;
	private int countOfChildren=0;
	private Vector subElements=new Vector();

	public void setName(String eleName)
	{
		this.name=eleName;
	}
	public String getName()
	{
		return name;
	}
	public void setData(String data)
	{
		this.data=data;
	}
	public String getData()
	{
		return data;
	}
	public void setAttributes(Attributes attrs)
        {
                this.attributes=new AttributesImpl(attrs);
        }
	public Attributes getAttributes()
	{
		return attributes;
	}
	
	public void setSubElements(XmlData data)
	{
		subElements.addElement(data);
		++countOfChildren;
	}	
	public void setSubElements(int seqno,XmlData data)
	{
		subElements.add(seqno,data);
		++countOfChildren;
	}

	public void replaceSubElements(int seqno,XmlData data)
	{
		subElements.set(seqno,data);
	}

	public void removeSubElements(int seqno[])
	{
		int removedCount=0;
		Arrays.sort(seqno);
		for(int i=0;i<seqno.length;i++)
		{
			subElements.removeElementAt(seqno[i]-removedCount);
			++removedCount;
		}
		--countOfChildren;
	}
	
	public Vector getSubElements()
	{
		return subElements;
	}
	public XmlData getSubElement(int seqno)
	{
		return (XmlData)subElements.elementAt(seqno);
	}
		
	public int getCountOfChildren()
	{
		return countOfChildren;
	}

}
