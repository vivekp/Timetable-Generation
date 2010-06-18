package org.iitk.brihaspati.modules.actions;

import java.io.*;
import java.util.*;

import jxl.Workbook;
import jxl.demo.XML;
import jxl.read.biff.BiffException;

import org.apache.velocity.context.Context;

import org.apache.turbine.util.RunData;
import org.apache.commons.fileupload.FileItem;

import org.iitk.brihaspati.modules.timetable.ParseXml;
import org.iitk.brihaspati.modules.timetable.Data;

public class Upload extends SecureAction
{

	   String filePath=new String();
	   String xmlFile=new String();
	   
       public void doUpload(RunData data, Context context) throws Exception
       {
    	   FileItem fileItem = data.getParameters().getFileItem("file");
    	   String fileName = fixFileName( fileItem.getName() );
    	   
    	   filePath=data.getServletContext().getRealPath("/uploads")+"/"+fileName;
    	   System.out.println("filepath is "+filePath);
    	   fileItem.write(new File(filePath));
    	   
    	   System.out.println(fileName+" file has been uploaded.");

        /* Generate xml file of the uploaded xls file at same place */
           try {
                System.out.println("Generating xml file");
     
                
                
       	        StringTokenizer st=new StringTokenizer(fileName,".");
       	        
       	        xmlFile=data.getServletContext().getRealPath("/uploads")+"/"+st.nextToken()+".xml";
       	        System.out.println("xmlfile is : "+xmlFile);
       	        
       	        generateXml(filePath);
       	        new ParseXml(xmlFile);
       	        Data.getInstance().reloadData();
       	        

       	        
          } catch(Throwable t) {
      		  t.printStackTrace();
      		  System.out.println("Error in generating xml file");
          }
       
    }
	    /**
     * Default action to perform if the specified action
     * cannot be executed.
     */
    public void doPerform( RunData data,Context context )
        throws Exception
    {
        data.setMessage("Can't find the requested action! ");
    }

	/**
	 * Fix file name if uploaded from a windows client
	 */
    public static String fixFileName(String longFile)
    {
        int loc = longFile.lastIndexOf("\\");
        return longFile.substring( (loc+1), longFile.length() );
    }
    
    public void generateXml(String xlsFile) throws BiffException, IOException {
    	String encoding = "UTF8";
    	boolean formatInfo = false;

    	String file=xlsFile;
    	Workbook w = Workbook.getWorkbook(new File(file));
    	new XML(w,System.out, encoding, formatInfo,xmlFile);

    	/*BufferedReader buf = new BufferedReader(new InputStreamReader(xml.getOutputStream()));*/
    	/*
    	PipedInputStream pin = new PipedInputStream();  
    	PipedOutputStream pout = new PipedOutputStream(pin);  

    	PrintStream out = new PrintStream(pout);  
    	BufferedReader in = new BufferedReader(new InputStreamReader(pin));  

    	String line = "";
    	FileWriter fw=new FileWriter(new File(xmlFile));

    	while ((line=in.readLine())!=null) {
    		fw.write(line+"\n");
    		System.out.println("writing data to the file");	
    	}
    	fw.close(); */
    }

}
