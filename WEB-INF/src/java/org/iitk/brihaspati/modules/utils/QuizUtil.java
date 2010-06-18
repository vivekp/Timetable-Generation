package org.iitk.brihaspati.modules.utils;

/*
 * @(#)QuizUtil.java
 *
 *  Copyright (c) 2007 ETRG,IIT Kanpur.
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


import java.io.File;
import java.util.Vector;
import java.util.StringTokenizer;
import org.iitk.brihaspati.modules.utils.FileEntry;
import org.iitk.brihaspati.modules.utils.XmlWriter;
import org.apache.turbine.services.servlet.TurbineServlet;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlReader;
import org.iitk.brihaspati.modules.utils.TopicMetaDataXmlWriter;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;

/**
 * This is util class for quiz
 * @author <a href="mailto:arvindjss17@yahoo.co.in">Arvind Pal</a>
 * @author <a href="mailto:nksinghiitk@yahoo.co.in">Nagendra Kumar Singh</a>
 * 
*/

public class QuizUtil
{

	public String storeans="0";
        public XmlWriter xmlwriter=null;
	public TopicMetaDataXmlReader topicmetadata=null;
        public  String ans1="";
	
	/**
	         * This method generate a garde card for student
                * @param qid String quiz identification code
                * @param cid String Course identification code

         */
		
	public void getGradecard(String qid,String cid)
        {
                try{
		String path=TurbineServlet.getRealPath("/Courses"+"/"+cid+"/Quiz"+"/"+qid);
		String path1=path+"/Student_Quiz";
                File f10=new File(path1);
                String flist1[] = f10.list();
                boolean flag1=false;
		for(int kk=0;kk<flist1.length;kk++)
                {
                        String totalmarks="";
                        String Quizname="";
                        String noofquestion="";
                        String passmarks="";
                        String Typeofquestion="";
                        File f1= new File(path1+"/"+flist1[kk]);
                	if(f1.exists()){
	                	topicmetadata=null;
                                Vector Gradelist=new Vector();
                                topicmetadata=new TopicMetaDataXmlReader(path1+"/"+flist1[kk]);
                                Gradelist=topicmetadata.getAssignmentDetails1();
                                File f2= new File(path+"/Quizid.xml");
                                if(f2.exists()){
         	                	topicmetadata=null;
                                        Vector Quiz1=new Vector();
					topicmetadata=new TopicMetaDataXmlReader(path+"/Quizid.xml");
                                        Quiz1=topicmetadata.getQuizDetails();
                                        for(int i=1;i<Quiz1.size();i++)
                                        {
						storeans="0";
                                        	String ans=((FileEntry) Quiz1.elementAt(i)).getinstructorans().trim();
                                                String questionido=((FileEntry) Quiz1.elementAt(i)).getquestionid().trim();
                                                String Question=((FileEntry) Quiz1.elementAt(i)).getquestion().trim();
                                                String tmarks=((FileEntry) Quiz1.elementAt(i)).getmarksperqustion().trim();
                                                Typeofquestion=((FileEntry) Quiz1.elementAt(i)).getTypeofquestion().trim();
                                                if(Typeofquestion.equals("Multiple") || Typeofquestion.equals("TF"))
                                                {
                                                	for(int k=0;k<Gradelist.size();k++)
                                                        {
								
                                                                ans1=((FileEntry) Gradelist.elementAt(k)).getUserName().trim();
								boolean flag=matchQID(path,flist1[kk]);
                                                                int questionid=Integer.parseInt(ans1);
                                                                if(questionid == i ) {
									String question=((FileEntry) Gradelist.elementAt(k)).getGrade().trim();
                                                                	if(Typeofquestion.equals("Multiple"))
                                                                        {
										storeans=checkMultiple(question,ans,tmarks);
									}//if
                                                                        if(Typeofquestion.equals("TF"))
                                                                        {
	                                                                	if(ans.equals(question))
                                                                                   	storeans=tmarks;
									}
                                                                        if(flag){
										writex(path,flist1[kk]);
									}
								} // if question id
							} //for question id
						}//if
					} //for
                                }//if f2
                        } // if f1
                } // for
                }//try
                catch(Exception e){
		 	ErrorDumpUtil.ErrorLog("The exception in quizUtil::"+e);
                        System.out.println("See ExceptionLog !! ");
		}

        }
	/**
                * This method is used for writing xml entry
                * @param path String abstract path of file
                * @param fname String name of file 

        */

	public void writex(String path,String fname){
			int kk1=-1;
                        Vector xmlVct=new Vector();
                        xmlVct.add(kk1);
                        xmlwriter=TopicMetaDataXmlWriter.WriteXml_New4(path,"/"+fname,xmlVct);
                        String temp="";
                        storeans=storeans.trim();
                        ans1=ans1.trim();
                        TopicMetaDataXmlWriter.appendGrade(xmlwriter,ans1,storeans,temp);
                        xmlwriter.writeXmlFile();
			xmlVct=null;
	}
	/**
                * This method is used for checking multiple type questions 
                * @param question String question
                * @param ans String answer
                * @param tmarks String total marks
                * @return String 

        */

	public String checkMultiple(String question,String ans,String tmarks){
		int intlength=question.length();
		int anslength=ans.length();  
                boolean correctans1=false;
		if(intlength==anslength) {
			boolean answer1=false;
			StringTokenizer st=new StringTokenizer(question,",");
			for(int j=0;st.hasMoreTokens();j++) { //first 'for' loop
				answer1=false;
				String ans2=st.nextToken();
				if(ans.contains(ans2)) {
					correctans1=true;
					answer1=true;
				}
				if(!answer1)
					break;
			}//for
			if(correctans1 && answer1)
				storeans=tmarks;
		}
		return storeans;
	}
//Match question id for writng duplicate prevention in student id .xml file
	/**
		* This method is used for perventing the duplicate entry in quiz
		* @param path String
		* @param flist String
		* @return boolean

	*/
	private boolean matchQID(String path, String flist){
		boolean flag=true;
		try{
			Vector QidList=new Vector();
			File f3=new File(path+"/"+flist);
			xmlwriter=null;
			if(!f3.exists())  
                        {  
	                        TopicMetaDataXmlWriter.writeWithRootOnly1(f3.getAbsolutePath());
        	                xmlwriter=new XmlWriter(path+"/"+flist);
                        }
                        topicmetadata=new TopicMetaDataXmlReader(path+"/"+flist);
                        QidList=topicmetadata.getAssignmentDetails1();   
                        if(QidList != null)  
                        {
                	        for(int g=0;g<QidList.size();g++)  
                        	{
		                        String ans2=( (FileEntry) QidList.elementAt(g)).getUserName().trim();   
                		        if(ans1.equals(ans2))
                        			flag=false;
		                        if(!flag)
	        	        	        break;  
                        	}  
                        }
			QidList=null;
		}
		catch(Exception ex){
			flag=false;
			ErrorDumpUtil.ErrorLog("The exception in quizUtil match QID block::"+ex);
		}
		return flag;
	}
}//end class
