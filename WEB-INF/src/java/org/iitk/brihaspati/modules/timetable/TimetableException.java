package org.iitk.brihaspati.modules.timetable;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TimetableException extends Exception {
	private String description;
	private String stackTrace;
	private String message;
	
	public TimetableException(Exception aException) {
		description = aException.toString();
		stackTrace = getStackTrace(aException);
		message = "Exception in Timetable Module";
	}
	
	public TimetableException(String message) {
		this.message = message;
		this.description = message;
	}
	
	public TimetableException(Exception aException,String message) {
		description = aException.toString();
		stackTrace = getStackTrace(aException);
		this.message = message; 
	}
	
	/*public void printStackTrace() {
		System.out.println(message);
		if(null != stackTrace) 
			System.out.println(stackTrace);
	}
	*/
	public String getMessage() {
		return message;
	}

	private String getStackTrace(Exception aException) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		aException.printStackTrace(pw);
		return sw.toString();
	}
}
