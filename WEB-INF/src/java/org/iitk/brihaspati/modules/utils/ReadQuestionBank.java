package org.iitk.brihaspati.modules.utils;

/*(#)ReadQuestionBank.java
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

import java.io.*;
import java.util.Vector;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

// using SAX
public class ReadQuestionBank
{
        public static Vector v;
        public static Vector v1;
        public static Vector v2;
        public static Vector v3;
        public static Vector v4;
        public static Vector v5;
        public static Vector v6;
        public static Vector v7;
        public static Vector v8;
        public static Vector v9;
        public static Vector v10;
        public static Vector v11;
        public static Vector v12;
        public static Vector v13;
        public static Vector v14;
        public static Vector v15;
        public static Vector v16;
        public static Vector v17;
        public static Vector v18;
        public static Vector v19;
        public static Vector v20;
	public static String id=new String();
	public static String question=new String();
	public static String op1=new String();
	public static String op2=new String();
	public static String op3=new String();
	public static String op4=new String();
	public static String op5=new String();
	public static String op6=new String();
	public static String ans=new String();
	public static String hint=new String();
	public static String type=new String();

	static class HowToHandler extends DefaultHandler
        {//nested class starts
                String quesId = new String();
                boolean qid = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                        if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                }// startElement
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        quesId = new String(ch, start, length);
                        qid = false;
                        v.addElement(quesId);
                        }
		}//characters
	}//nested class


	public static Vector getQuesId(String fileName)
        {
                v=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                                             
                       parser.setContentHandler(new HowToHandler());
                       parser.parse(fileName);
                }
                catch(Exception e){
                        v.add("The exception catched is : "+e);
                }
                return v;
        }
	static class GetModule extends DefaultHandler
        {
                String modulename = new String();
                boolean mname = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                        if (tagName.equalsIgnoreCase("ModuleName"))
                                mname = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (mname)
                        {
                        modulename= new String(ch, start, length);
                        mname = false;
                        v3.addElement(modulename);
                        }
		}
	}
		
	public static Vector getModuleName(String fileName)
        {
                v3=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new GetModule());
                        parser.parse(fileName);
                }
                catch(Exception e)
		{
		 	ErrorDumpUtil.ErrorLog("Exception  in  getModuleName [ReadQuestionBank.java] util" +e);
                	v3=new Vector();
                        //v3.add("The exception catched is : "+e);
                }
                return v3;
        }
		

	static class getRId extends DefaultHandler
        {
		String repoId=new String();
                boolean rid = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                        if (tagName.equalsIgnoreCase("RepositoryId"))
                                rid = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (rid)
                        {
                        repoId = new String(ch, start, length);
                        rid = false;
                        v1.addElement(repoId);
                        }
		}
	}

	public static Vector getRepositoryId(String fileName)
        {
                v1=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new getRId());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v1.add("The exception catched is : "+e);
                }
                return v1;
	}
	static class QuestionDetail extends DefaultHandler
        {
                String quesId = new String();
                boolean qid = false;
                boolean qtype = false;
                boolean question = false;
                boolean op1 = false;
                boolean op2 = false;
                boolean op3 = false;
                boolean op4 = false;
                boolean op5 = false;
                boolean op6 = false;
                boolean ans = false;
                boolean hint = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Type"))
                                qtype = true;
                       if (tagName.equalsIgnoreCase("Question"))
                                question = true;
                       if (tagName.equalsIgnoreCase("Option1"))
                                op1 = true;
                       if (tagName.equalsIgnoreCase("Option2"))
                                op2 = true;
                       if (tagName.equalsIgnoreCase("Option3"))
                                op3 = true;
                       if (tagName.equalsIgnoreCase("Option4"))
                                op4 = true;
                       if (tagName.equalsIgnoreCase("Option5"))
                                op5 = true;
                       if (tagName.equalsIgnoreCase("Option6"))
                                op6 = true;
                       if (tagName.equalsIgnoreCase("Answer"))
                                ans = true;
                       if (tagName.equalsIgnoreCase("Hint"))
                                hint = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v5.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (qtype)
                        {
                        v5.addElement(new String(ch, start, length));
                        qtype = false;
                        }
			if (question)
                        {
                        v5.addElement(new String(ch, start, length));
                        question = false;
                        }
			if (op1)
                        {
                        v5.addElement(new String(ch, start, length));
                        op1 = false;
                        }
			if (op2)
                        {
                        v5.addElement(new String(ch, start, length));
                        op2 = false;
                        }
			if (op3)
                        {
                        v5.addElement(new String(ch, start, length));
                        op3 = false;
                        }
			if (op4)
                        {
                        v5.addElement(new String(ch, start, length));
                        op4 = false;
                        }
			if (op5)
                        {
                        v5.addElement(new String(ch, start, length));
                        op5 = false;
                        }
			if (op6)
                        {
                        v5.addElement(new String(ch, start, length));
                        op6 = false;
                        }
			if (ans)
                        {
                        v5.addElement(new String(ch, start, length));
                        ans = false;
                        }
			if (hint)
                        {
                        v5.addElement(new String(ch, start, length));
                        hint = false;
                        }
		}
	}
	public static Vector getQuestionDetail(String fileName)
        {
                v5=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new QuestionDetail());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v5.add("The exception catched is : "+e);
                }
                return v5;
}
	static class Questions extends DefaultHandler
        {
                boolean question = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("Question"))
                                question = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (question)
                        {
                        v6.addElement(new String(ch, start, length));
                        question = false;
                        }
		}
	}
	public static Vector getQuestions(String fileName)
        {
		v6=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new Questions());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v6.add("The exception catched is : "+e);
                }
                return v6;
}
	static class Question extends DefaultHandler
        {
                boolean quesid = false;
                boolean question = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                quesid = true;
                       if (tagName.equalsIgnoreCase("Question"))
                                question = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (quesid)
                        {
                        v6.addElement(new String(ch, start, length));
                        quesid = false;
                        }
			if (question)
                        {
                        v6.addElement(new String(ch, start, length));
                        question = false;
                        }
		}
	}
	public static String getQuestion(String fileName,String qid)
        {
                v6=new Vector();
		question=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new Question());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v6.add("The exception catched is : "+e);
                }
		for (int i=0; i<v6.size(); i++)
		{
			if(v6.elementAt(i).equals (qid))
				{
				question=(String)(v6.elementAt(i+1)); 
				}
		}
                return question;
}
/************************************/
	static class Questions_type extends DefaultHandler
        {
                boolean quesType = false;
                boolean question = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("Type"))
                                quesType = true;
                       if (tagName.equalsIgnoreCase("Question"))
                                question = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (quesType)
                        {
                        v6.addElement(new String(ch, start, length));
                        quesType = false;
                        }
			if (question)
                        {
                        v6.addElement(new String(ch, start, length));
                        question = false;
                        }
		}
	}
	public static Vector getQuestions_type(String fileName,String type)
        {
                v6=new Vector();
		String question=new String();
		Vector question1=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new Questions_type());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v6.add("The exception catched is : "+e);
                }
		for (int i=0; i<v6.size(); i++)
		{
			if(v6.elementAt(i).equals(type))
				{
				question=(String)(v6.elementAt(i+1)); 
				question1.add(question);
				}
		}
                return question1;
	}
//////////////////
	static class QuestionsId_type extends DefaultHandler
        {
                boolean quesType = false;
                boolean questionId = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("Type"))
                                quesType = true;
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                questionId = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (quesType)
                        {
                        v6.addElement(new String(ch, start, length));
                        quesType = false;
                        }
			if (questionId)
                        {
                        v6.addElement(new String(ch, start, length));
                        questionId = false;
                        }
		}
	}
	public static Vector getQuestionsId_type(String fileName,String type)
        {
                v6=new Vector();
		String questionId=new String();
		Vector quid=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new QuestionsId_type());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v6.add("The exception catched is : "+e);
                }
		for (int i=0; i<(v6.size()-1); i++)
		{
			if(v6.elementAt(i+1).equals(type))
			{
				questionId=(String)(v6.elementAt(i)); 
				quid.add(questionId);
			}
		}
		return quid;
	}
////////////////////
/************************/
	static class QuestionId extends DefaultHandler
        {
                boolean qid = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v7.addElement(new String(ch, start, length));
                        qid = false;
                        }
		}
	}
	public static Vector getQuestionId(String fileName)
        {
                v7=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new QuestionId());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v7.add("The exception catched is : "+e);
                }
                return v7;
	}
	static class op1 extends DefaultHandler
        {
                boolean qid = false;
                boolean op1 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option1"))
                                op1 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v8.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op1)
                        {
                        v8.addElement(new String(ch, start, length));
                        op1 = false;
                        }
		}
	}
	public static String getOP1(String fileName,String qid)
        {
                v8=new Vector();
		op1=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op1());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v8.add("The exception catched is : "+e);
                }
		for (int i=0; i<v8.size(); i++)
                {
                        if(v8.elementAt(i).equals (qid))
                                {
                                op1=(String)(v8.elementAt(i+1));
                                }
                }
                return op1;
}
	static class op2 extends DefaultHandler
        {
                boolean qid = false;
                boolean op2 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option2"))
                                op2 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v9.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op2)
                        {
                        v9.addElement(new String(ch, start, length));
                        op2 = false;
                        }
		}
	}
	public static String getOP2(String fileName,String qid)
        {
                v9=new Vector();
		op2=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op2());
                        parser.parse(fileName);
                }
                catch(Exception e){    
                       v9.add("The exception catched is : "+e);
                } 
		for (int i=0; i<v9.size(); i++)
                {
                        if(v9.elementAt(i).equals (qid))
                                {
                                op2=(String)(v9.elementAt(i+1));
                                }
                }
                return op2;
}

	static class op3 extends DefaultHandler
        {
                boolean qid = false;
                boolean op3 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option3"))
                                op3 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v10.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op3)
                        {
                        v10.addElement(new String(ch, start, length));
                        op3 = false;
                        }
		}
	}
	public static String getOP3(String fileName,String qid)
        {
                v10=new Vector();
		op3=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op3());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v10.add("The exception catched is : "+e);
                }
		for (int i=0; i<v10.size(); i++)
                {
                        if(v10.elementAt(i).equals (qid))
                                {
                                op3=(String)(v10.elementAt(i+1));
                                }
                }
                return op3;
}
	static class op4 extends DefaultHandler
        {
                boolean qid = false;
                boolean op4 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option4"))
                                op4 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v11.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op4)
                        {
                        v11.addElement(new String(ch, start, length));
                        op4 = false;
                        }
		}
	}
	public static String getOP4(String fileName,String qid)
        {
                v11=new Vector();
		op4=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op4());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v11.add("The exception catched is : "+e);
                }
		for (int i=0; i<v11.size(); i++)
                {
                        if(v11.elementAt(i).equals (qid))
                                {
                                op4=(String)(v11.elementAt(i+1));
                                }
                }
                return op4;
} 
               
	static class op5 extends DefaultHandler
        {
                boolean qid = false;
                boolean op5 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option5"))
                                op5 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v12.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op5)
                        {
                        v12.addElement(new String(ch, start, length));
                        op5 = false;
                        }
		}
	}
	public static String getOP5(String fileName,String qid)
        {
                v12=new Vector();
		op5=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op5());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v12.add("The exception catched is : "+e);
                }
		for (int i=0; i<v12.size(); i++)
                {
                        if(v12.elementAt(i).equals (qid))
                                {
                                op5=(String)(v12.elementAt(i+1));
                                }
                }
           
                return op5;
}
	static class op6 extends DefaultHandler
        {
                boolean qid = false;
                boolean op6 = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Option6"))
                                op6 = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v13.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (op6)
                        {
                        v13.addElement(new String(ch, start, length));
                        op6 = false;
                        }
		}
	}
	public static String getOP6(String fileName,String qid)
        {
                v13=new Vector();
		op6=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new op6());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v13.add("The exception catched is : "+e);
                }
		for (int i=0; i<v13.size(); i++)
                {
                        if(v13.elementAt(i).equals (qid))
                                {
                                op6=(String)(v13.elementAt(i+1));
                                }
                }
           
                return op6;
}
	static class ans extends DefaultHandler
        {
                boolean qid = false;
                boolean ans = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Answer"))
                                ans = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v14.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (ans)
                        {
                        v14.addElement(new String(ch, start, length));
                        ans = false;
                        }
		}
	}
	public static String getAns(String fileName,String qid)
        {
                v14=new Vector();
		ans=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new ans());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v14.add("The exception catched is : "+e);
                }
		for (int i=0; i<v14.size(); i++)
                {
                        if(v14.elementAt(i).equals (qid))
                                {
                                ans=(String)(v14.elementAt(i+1));
                                }
                }
           
                return ans;
}
	static class hint extends DefaultHandler
        {
                boolean qid = false;
                boolean hint = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("QuestionId"))
                                qid = true;
                       if (tagName.equalsIgnoreCase("Hint"))
                                hint = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (qid)
                        {
                        v15.addElement(new String(ch, start, length));
                        qid = false;
                        }
			if (hint)
                        {
                        v15.addElement(new String(ch, start, length));
                        hint = false;
                        }
		}
	}
	public static String getHint(String fileName,String qid)
        {
                v15=new Vector();
		hint=new String();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new hint());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v15.add("The exception catched is : "+e);
                }
		for (int i=0; i<v15.size(); i++)
                {
			ErrorDumpUtil.ErrorLog("Vector in Hint"+v15);
                        if(v15.elementAt(i).equals (qid))
                                {
                                hint=(String)(v15.elementAt(i+1));
                                }
                }
           
                return hint;
}
	static class type extends DefaultHandler
        {
                boolean type = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("Type"))
                                type = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (type)
                        {
                        v16.addElement(new String(ch, start, length));
                        type = false;
                        }
		}
	}
	public static Vector getType(String fileName)
        {
                v16=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new type());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v16.add("The exception catched is : "+e);
                }
           
                return v16;
}
	static class getDrid extends DefaultHandler
        {
                boolean author = false;
                boolean rid = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("AuthorName"))
                                author = true;
                       if (tagName.equalsIgnoreCase("RepositoryId"))
                                rid = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (author)
                        {
                        v17.addElement(new String(ch, start, length));
                        author = false;
                        }
			if (rid)
                        {
                        v17.addElement(new String(ch, start, length));
                        rid = false;
                        }
		}
	}
	public static Vector getDrepoId(String fileName,String rname)
        {
                v17=new Vector();
                v18=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new getDrid());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v17.add("The exception catched is : "+e);
                }
		for(int i=0; i<v17.size(); i++)
                {
                        if(v17.elementAt(i).equals(rname))
                        {
                               	v18.addElement(v17.elementAt(i-1));
                        }
                }
                return v18;
}
	static class getRName extends DefaultHandler
        {
                boolean author = false;
                boolean reponame = false;
 
                public void startElement(String nsURI, String strippedName,String tagName, Attributes attributes)throws SAXException
                {
                       if (tagName.equalsIgnoreCase("AuthorName"))
                                author = true;
                       if (tagName.equalsIgnoreCase("RepositoryName"))
                                reponame = true;
                }
		
		public void characters(char[] ch, int start, int length)
                {
			if (author)
                        {
                        v19.addElement(new String(ch, start, length));
                        author = false;
                        }
			if (reponame)
                        {
                        v19.addElement(new String(ch, start, length));
                        reponame = false;
                        }
		}
	}//class getRName
	public static Vector getRepositoryName(String fileName,String rname)
        {
                v19=new Vector();
                v20=new Vector();
                try
                {
                        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
                        parser.setContentHandler(new getRName());
                        parser.parse(fileName);
                }
                catch(Exception e){
                        v19.add("The exception catched is : "+e);
                }
		for (int i=0; i<v19.size(); i++)
                {
                        if(v19.elementAt(i).equals (rname))
                                {
                                v20.addElement(v19.elementAt(i-1));
                                }
                }
                return v20;
}
}
