/*
 * @(#)CheckProperties.java
 *
 *  Copyright (c) 2008 ETRG,IIT Kanpur.
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
 *
 */

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used for generating report for
 * difference multi language files with english
 *
 *
 * @author <a href="mailto:ashutoshkapor1234@gmail.com">Ashutosh Kapoor</a>
 * @author <a href="mailto:nksngh_p@yahoo.co.in">Nagendra Kumar Singh</a>
 * @version 1.0
 * @since 1.0
 * 
 */

public class CheckProperties {
    
    private Properties p;
    private String master;
    private String slave;
    
    private String userhome;   //store the path of the user's home directory
    
    private String mainFile1;  //path of main file
    private String mainFile2;  //path of main file
    
    
    
    private String checkFile1path; //path of files to be checked
    private String checkFile2path; //path of files to be checked
   
    
    private ArrayList<String> filenames1=new ArrayList<String>();
    private ArrayList<String> filenames2=new ArrayList<String>();
    
    
    private String reportFile;
	/**
         * This method set the path of all files 
	 * like language report and master files.
         * 
         */
    
    public CheckProperties()
    {
        userhome=System.getProperty("user.home");
        //reportFile=userhome+"/tdk-2.3_01/webapps/brihaspati2/templates/app/screens/missingTag.txt";
        reportFile=userhome+"/tdk-2.3_01/webapps/brihaspati2/missingTag.txt";
        mainFile1=userhome+"/tdk-2.3_01/webapps/brihaspati2/templates/app/screens/Var_english.properties";
        mainFile2=userhome+"/tdk-2.3_01/webapps/brihaspati2/WEB-INF/conf/BrihLang_en.properties";
        checkFile1path=userhome+"/tdk-2.3_01/webapps/brihaspati2/WEB-INF/conf/MultilingualGUIFile";
        checkFile2path=userhome+"/tdk-2.3_01/webapps/brihaspati2/WEB-INF/conf";
	doFileListing();
    }

	/**
         * This method is used to getting list of language files
         * Action and templates 
         */   
    private void doFileListing()
    {
        File Directory1 = new File(checkFile1path);
	String[] filesInDir1 = Directory1.list();
	for(int i=0;i<filesInDir1.length;i++)
	{
		if(filesInDir1[i].startsWith("Var_"))
		{
			filenames1.add(filesInDir1[i]);
		}
	}
	File Directory2 = new File(checkFile2path);
        String[] filesInDir2 = Directory2.list();
        for(int i=0;i<filesInDir2.length;i++)
        {
                if(filesInDir2[i].startsWith("BrihLang_")&&(!filesInDir2[i].equals("BrihLang_en.properties")))
                {
                        filenames2.add(filesInDir2[i]);
                }
        }

    }
        /**
         * This method generate the final report file
         *
         */
    private void createReport() throws FileNotFoundException, IOException
    {
        String missing1=new String();
        String missing2=new String();
        
        //doing for main file 1
        master=new String();
        
        this.getKey(mainFile1,true);
//	System.out.println("making master key");
        master=master+"@@@@@ ";
	ListIterator<String> it1=filenames1.listIterator();
	int i=0;
  	while(it1.hasNext())
        {
		String st=it1.next();
		try
		{
            		slave=new String();
			if(i==0)
			{
            			missing1=missing1+st+": \n\n";
				i++;
			}
			else
            			missing1=missing1+"\n\n\n\n\n"+st+": \n\n";
            		this.getKey(checkFile1path+"/"+st,false);
		//	System.out.println("coming here");
            		missing1=missing1+this.extract();
            	//	System.out.println("also coming here");
		}
		catch(Exception e)
		{
			System.out.println("Error :"+e.toString());
			missing1=missing1+e.toString()+"\n";
		}
        }
        
        //doing for main file 2
        master=new String();
        
        this.getKey(mainFile2,true);
	//System.out.println("making key2");
        master=master+"@@@@@ ";
	//System.out.println(master);
        ListIterator<String> it2=filenames2.listIterator();
        while(it2.hasNext())
        {
		String st=it2.next();
		try
		{	
            		slave=new String();
            		missing2=missing2+"\n\n\n\n\n"+st+": \n\n";
            		this.getKey(checkFile2path+"/"+st,false);
	
	//	 	System.out.println("coming here 2");

            		missing2=missing2+this.extract();
          //   		System.out.println("also coming here 2");
		}
		catch(Exception e)
		{
			System.out.println("Error :"+e.toString());
			 missing2=missing2+e.toString()+"\n";

		}
        }
        
        File f=new File(reportFile);
        PrintWriter wt=new PrintWriter(f);
        wt.write(missing1);
        wt.println();
	wt.println();
        wt.print(missing2);
        wt.close();
	System.out.println("report file created :"+reportFile);
       // System.out.println("Insert these :\n"+missing1+"\n"+missing2);
        
    }
	/**
         * This method get keys from language file
	 * @param fileName String The name of lanuage file
	 * @param isMaster boolean True if file is master
         * 
         */
    private void getKey(String fileName,boolean isMaster) throws FileNotFoundException, IOException
    {
        String temp;
        p=new Properties();
    //    System.out.println(fileName);
        FileInputStream fr = new FileInputStream(fileName);
//	System.out.println("made stream");
        p.load(fr);
  //      System.out.println("file loaded");
        FileWriter f=new FileWriter(userhome+"/keys_temp.txt");
        PrintWriter wt=new PrintWriter(f);
        p.list(wt);
//	System.out.println("file listed in temp file");
        FileReader read=new FileReader(userhome+"/keys_temp.txt");
        BufferedReader br=new BufferedReader(read);
        temp=br.readLine();
        while(temp!=null)
        {
            String []result=temp.split("=");
            if(result.length==2)
                if(isMaster)
                    master=master+result[0]+"@@@@@";
                else
                    slave=slave+result[0]+" ";
             temp=br.readLine();   
        }
        br.close();
        read.close();
        wt.close();
        f.close();
        fr.close();
        
        File ff=new File(userhome+"/keys_temp.txt");
        if(ff.exists())
        {
            ff.delete();
        }
    }

	/**
         * This method extract the string from master file
         * @return String
         */
    public String extract()
    {
            String required=new String();
            
            String []result=master.split("@@@@@");
            for(int i=0;i<result.length;i++)
            {
                if(!slave.contains(result[i]))
                {
                    required=required+result[i]+"\n";
                }
            }
            return required;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	//System.out.println(System.getProperty("user.home"));
        try {
              
                        // TODO code application logic here
            CheckProperties pro = new CheckProperties();
            pro.createReport();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CheckProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

}
