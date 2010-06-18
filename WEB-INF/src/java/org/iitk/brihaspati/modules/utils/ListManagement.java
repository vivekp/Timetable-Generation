package org.iitk.brihaspati.modules.utils;

/*
 * @(#)ListManagement.java	
 *
 *  Copyright (c) 2004-2008 ETRG,IIT Kanpur. 
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
 *  WHETHER IN CONTRACT, 
 *  RICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 *  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 *  Contributors: Members of ETRG, I.I.T. Kanpur 
 */

import java.util.Vector;
import java.util.List;
import java.util.ListIterator;
import com.workingdogs.village.Record;
import com.workingdogs.village.Value;
import org.apache.turbine.util.parser.ParameterParser;
import org.apache.torque.util.Criteria;
import org.iitk.brihaspati.modules.utils.CourseUserDetail;
import org.iitk.brihaspati.modules.utils.UserManagement;
import org.iitk.brihaspati.modules.utils.ErrorDumpUtil;
import org.apache.turbine.services.security.torque.om.TurbineUserPeer;
import org.apache.turbine.services.security.torque.om.TurbineUser;
import org.apache.turbine.services.security.torque.om.TurbineGroupPeer;
import org.iitk.brihaspati.om.CoursesPeer;
import org.iitk.brihaspati.om.Courses;
import java.io.FileOutputStream;
/**
 * This class contains methods for listing
 * @author <a href="mailto:awadhk_t@yahoo.com">Awadhesh Kumar trivedi</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a> 
 */

public class ListManagement
{
      /**
       * List of all the registered courses
       * @see UserManagement in Utils
       * @return Vector
       */
	public static Vector getUserList()
	{
		Vector userDetails=new Vector();
		try{
                 /**
                 * Getting the value of file from temporary variable
                 * According to selection of Language./                 
                 */
			List user=UserManagement.getUserDetail("All");
			userDetails=getDetails(user,"User");
		}
		catch(Exception e)
		{}
	return userDetails;
	}
      /**
       * Deatils of all the registered courses or Users
       * @param list List
       * @param type String
       * @return Vector
       */
	public static Vector getDetails(List list,String type)
	{
		Vector Details=new Vector();
		try
		{
			/**
			 * Add the details of each detail in a vector
			 * and put the same in context for use in template
			 */
			if(type.equals("User"))
			{
			for(int i=0;i<list.size();i++)
			{
				TurbineUser element=(TurbineUser)(list.get(i));
				String loginName=(element.getUserName()).toString();
				String firstName=(element.getFirstName()).toString();
				String lastName=(element.getLastName()).toString();
				String email=(element.getEmail()).toString();
				String userName=firstName+" "+lastName;
				CourseUserDetail cuDetail=new CourseUserDetail();
				cuDetail.setLoginName(loginName);
				cuDetail.setUserName(userName);
				cuDetail.setEmail(email);
				Details.add(cuDetail);
			}
			}
			else
			{
			for(int i=0;i<list.size();i++)
			{
				Courses element=(Courses)(list.get(i));
				String GName=(element.getGroupName()).toString();
				String courseName=(element.getCname()).toString();
				String gAlias=(element.getGroupAlias()).toString();
				String dept=(element.getDept()).toString();
				String description=(element.getDescription()).toString();
				byte active=element.getActive();
				String act=Byte.toString(active);
				String crDate=(element.getCreationdate()).toString();
				CourseUserDetail cuDetail=new CourseUserDetail();
				cuDetail.setGroupName(GName);
                                cuDetail.setCourseName(courseName);
                                cuDetail.setCAlias(gAlias);
                                cuDetail.setDept(dept);
                                cuDetail.setActive(act);
                                cuDetail.setDescription(description);
                                cuDetail.setCreateDate(crDate);
				Details.add(cuDetail);
			}
			}
		}
		catch(Exception ex)
		{
			/**
			* Replacing the value of String from property file.
			* @see MultilingualUtil in utils
	                */
			Details.add("The Error in Details"+ex);
		}	
	return(Details);
	}
	public static List getCourseList()
	{
		List v=null;
		try{
		Criteria crit=new Criteria();
		//crit.addAscendingOrderByColumn(TurbineGroupPeer.GROUP_NAME);
		crit.addGroupByColumn(TurbineGroupPeer.GROUP_NAME);
		v=TurbineGroupPeer.doSelect(crit);
		}
		catch(Exception e)
		{
		}
		return v;
		
	}
	public static Vector getListBySearchString(String Type,String query,String searchString)
	{
		MultilingualUtil m_u=new MultilingualUtil();
		Vector Details=new Vector();
		try{
			Criteria crit=new Criteria();
			if(Type.equals("CourseWise"))
			{
				String str="GROUP_NAME";
                                if(query.equals("CourseId"))
                                        str="GROUP_NAME";
                                else if(query.equals("Course Name"))
                                        str="CNAME";
                                else if(query.equals("Dept"))
                                        str="DEPT";
                                /**
                                 * Checks for Matching Records
                                 */
				String table="COURSES";
				crit=new Criteria();
				crit.add(table,str,(Object)("%"+searchString+"%"),crit.LIKE);
                                List v=CoursesPeer.doSelect(crit);
                                Details=getDetails(v,"Course");

			}
			else
			{
				crit=new Criteria();
		                String str=null;
				if(query.equals("First Name"))
					str="FIRST_NAME";
				else if(query.equals("Last Name"))
				        str="LAST_NAME";
				else if(query.equals("User Name"))
					str="LOGIN_NAME";
				else if(query.equals("Email"))
					str="EMAIL";
				/**
				 * Checks for Matching Records
				 */
				int noUid[]={0,1};
				String table="TURBINE_USER";
				crit.addNotIn(TurbineUserPeer.USER_ID,noUid);
				crit.add(table,str,(Object)("%"+searchString+"%"),crit.LIKE);
				List v=TurbineUserPeer.doSelect(crit);
				Details=getDetails(v,"User");
			}
		}
		catch(Exception e)
		{
			String searchMsg="The error in find details !!"+e;
			Details.add(searchMsg);
			
		}
		return Details;
	}
	/**
	 * Partion of List
	 * This shows the list containing records at a time,
	 * if there are more records than it will appear on next page.
	 * @param list Vector Complete list FOR divide
	 * @param startIndex int By default this is 0
	 * @param list_size int contains the value for dividing the list()
	 * @return Vector
	 */
	
	public static Vector listDivide(Vector list, int startIndex, int list_size)
	{
		Vector divideList=new Vector();
		MultilingualUtil m_u=new MultilingualUtil();
		try
		{
			int endIndex=startIndex+list_size;
			int check=(list.size()-startIndex);
			int i=0;
		
		 	/**
		  	* Divide List of any vector
		  	* Vector has whole details of topic
		  	*/

			if(check>=list_size)
			{
				for(i=startIndex;i<endIndex;i++)
				{
					divideList.add(list.get(i));
				}
	 		}
	 		else
			{
				for(i=startIndex;i<startIndex+check;i++)
				{
				divideList.add(list.get(i));
				}
			}
		}
		catch(Exception e)
		{
			String message="The error in list Dividing"+e;
			divideList.add(message);
		}
		return divideList;
	}
	/**
	 * This is common method which is used with the above methods.
	 * This is used for the links First, Next, Previous, Last, 
	 * these links are used to move from one page to another page
	 * here we are using an array of size 8
	 * value[0] is for startIndex, initial value of records:
	 * value[1] is for endIndex, last value of records:
	 * value[2] is for check_first, shows first page having first three records:
	 * value[3] is for check_pre, shows the previous page:
	 * value[4] is for check_last1, gives the remainder value:
	 * value[5] is for check_last, shows the last page:
	 * value[6] is for startIndex+1:
	 * value[7] is for total size-2:
	 * @param startIndex int
	 * @param size int
	 * @param list_size int contains the value for dividing the list
	 * @return int[]
	 */
	public static int[] linkVisibility(int startIndex, int size, int list_size)
	{
		int value[]=new int[7];
		int endIndex=startIndex+list_size;	 
		int check_first=0;
	
 		int check_pre=startIndex-list_size;
		int check_last1=size%list_size;
		int check_last=0;
	
		if(check_last1 == 0){
			check_last=size-list_size;
		}
		else{
			check_last=size-check_last1;
		}
		value[0]=startIndex;
		value[1]=endIndex;
		value[2]=check_first;
		value[3]=check_pre;
		value[4]=check_last1;
		value[5]=check_last;
		value[6]=startIndex+1;
		return value;
	}	
}

