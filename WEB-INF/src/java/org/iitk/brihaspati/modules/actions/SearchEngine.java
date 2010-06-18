package org.iitk.brihaspati.modules.actions;

/*
 * @(#)SearchEngine.java
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
 */


//JDK
import java.io.File;
import java.util.Date;
import java.util.Vector;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
//Turbine
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.apache.turbine.om.security.User;
import org.apache.turbine.util.parser.ParameterParser;
//Lucene
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
//Brihaspati
import org.iitk.brihaspati.modules.utils.UserUtil;
import org.iitk.brihaspati.modules.utils.MultilingualUtil;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.StudentInstructorMAP;

/**
  * Action class for searching  a file that contais the query string.
  * This class also provides result of searching to templates
  * This class is invoked whenever a user search a file using search engine.
  *
  *  @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
  *  @author <a href="mailto:sunil_gupta20801@rediffmail.com">Sunil Kumar Gupta</a>
  */
  
				 
public class SearchEngine extends SecureAction{
	/**varibale to hold the count of documents
	 * that contains the query
	*/
	int cou=0;	 
	/**This varibale is used to hold the
	 * total time in searching process
	*/
	long ti=0;
	CourseUserDetail cDetail;


	/**This method is responsible for getting
	 * the name of the courses in which the user
	 * is instructor or Student.
	 * This method then calls the doGetData() method
	*/
public void doSearch(RunData data, Context context)throws Exception {

		String keyword = data.getParameters().getString("WORD");
        	context.put("key",keyword);
		Vector end=new Vector();
		User user=data.getUser();
	        String LangFile=(String)user.getTemp("LangFile");

                String username=user.getName();
	        int u_id=UserUtil.getUID(username);
	//	Vector course_inst=StudentInstructorMAP.getIMAP(u_id,data);
		Vector course_inst=StudentInstructorMAP.getIMAP(u_id);
		Vector course_stud=StudentInstructorMAP.getSMAP(u_id);
	//	Vector course_stud=StudentInstructorMAP.getSMAP(u_id,data);

		for(int i=0;i<course_inst.size();i++){
			cDetail=(CourseUserDetail)course_inst.elementAt(i);
			String courseId=cDetail.getGroupName();
		        File indexDir = new File(data.getServletContext().getRealPath("/Courses")+"/"+courseId+"_Index");
			if(indexDir.exists() && indexDir.isDirectory() && Array.getLength(indexDir.list())!=0)
			{
				Vector sd=doMySearch(indexDir, keyword, context);
				for(int j=0;j<sd.size();j++)
					end.addElement(sd.elementAt(j));
			}
		}
		for(int i=0;i<course_stud.size();i++){
                        cDetail=(CourseUserDetail)course_stud.elementAt(i);
                        String courseId=cDetail.getGroupName();
		        File indexDir = new File(data.getServletContext().getRealPath("/Courses")+"/"+courseId+"_Index");
			if(!(!indexDir.exists() || !indexDir.isDirectory() || Array.getLength(indexDir.list())==0))
			{
				Vector sd=doMySearch(indexDir, keyword, context);
                        	for(int j=0;j<sd.size();j++)
                                	end.addElement(sd.elementAt(j));
			}
                }
	Vector fin=new Vector();
	Vector fout=new Vector();
	String sub=new String();
	for(int k=0;k<end.size();k++)
	{
		String last=(String)end.elementAt(k);
		StringTokenizer st=new StringTokenizer(last,"/");
		while(st.hasMoreTokens())
		{
			if(st.nextToken().equals("Courses"))
			{
				String one=st.nextToken();
				if(!(st.nextToken().equals("Marks"))){
					String two=st.nextToken();
					String three=st.nextToken();
					fin.addElement("../"+one+"/"+two+"/"+three);
					fout.add(one);
					fout.add(two);
					fout.add(three);
				}
				else
					cou--;
			}
		}
	}
	context.put("k",keyword);
	context.put("vec",fin);
	context.put("out",fout);
	context.put("Search", end);
	String msg=MultilingualUtil.ConvertedString("brih_found",LangFile);
	String msg1=MultilingualUtil.ConvertedString("brih_found1",LangFile);
	String msg2=MultilingualUtil.ConvertedString("brih_found2",LangFile);
        //data.setMessage(word + msg1);
	if(LangFile.endsWith("hi.properties"))
		context.put("val",keyword +" " +msg +" "+ cou + " "+msg1+" "+(ti) +" "+msg2);
	else
		context.put("val",msg +" "+ cou + " "+msg1+" "+(ti) +" "+msg2+" "+ keyword +":");
//	context.put("val","Found " + cou + " document(s)(in "+(ti) +" miliseconds) that matched query '" + keyword + "':");
}




   /** This method perform the search process and return the result
    *  of search process back to the doGetData() method
   */

     public  Vector  doMySearch(File indexDir, String q, Context context)  throws Exception{
	Vector search=new Vector();
    	Directory fsDir = FSDirectory.getDirectory(indexDir, false);
    	IndexSearcher is = new IndexSearcher(fsDir);
    	Query query = QueryParser.parse(q, "contents", new StandardAnalyzer());

	long start=new Date().getTime();
    	Hits hits = is.search(query);
	long end=new Date().getTime();
    	cou=cou+hits.length();
	ti=ti+(end-start);
    	for (int i = 0; i < hits.length(); i++) {
        	Document doc = hits.doc(i);
		search.addElement(doc.get("filename"));
    	}
	return search;
        
   }

	 public void doPerform(RunData data,Context context) throws Exception{
		User user=data.getUser();
                 String action=data.getParameters().getString("actionName","");
		String LangFile=(String)user.getTemp("LangFile");
                if(action.equals("eventSubmit_doSearch"))
                        doSearch(data,context);
                else{
			  String msg1=MultilingualUtil.ConvertedString("action_msg",LangFile);
		          data.setMessage(msg1);
		}
        }
}

