package org.iitk.brihaspati.modules.timetable;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.sql.PreparedStatement;

public class ParseXml {
	
	public ParseXml(String xmlFile) throws TimetableException, SQLException {
		this.xmlFile=xmlFile;
		parseXmlFile(xmlFile);
		parseDocument();
	}
	
	Document dom;
	String xmlFile;
	
	public void parseXmlFile(String xmlFile) throws TimetableException{
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(xmlFile);
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
			throw new TimetableException("Error in xml configuration");
		}catch(SAXException se) {
			se.printStackTrace();
			throw new TimetableException("Error while parsing xml file");
		}catch(IOException ioe) {
			ioe.printStackTrace();
			throw new TimetableException("Input/Output Error");
		}
	}

	public void parseDocument() throws SQLException {

		Connection conn = Data.getConnection();
		// get the root element -> workbook
		Element docEle=dom.getDocumentElement();

		// get a nodelist of <sheet> elements
		NodeList nl = docEle.getElementsByTagName("sheet");
		if( nl != null && nl.getLength() > 0 ) {
			for(int i=0;i<nl.getLength();i++) {

				if(i == 0) {
					deleteBatchInfo(conn);
					deleteBatchCourseInfo(conn);
				}
				else if(i == 1)
					deleteCourse(conn);
				else if(i == 2) {
					deleteFacultyInfo(conn);
					deleteFacultyCourse(conn);
				}
				else if(i == 3){
					deleteVenue(conn);
				}
				
				// get the sheet element
				Element el=(Element)nl.item(i);

				//get the nodelist of <row> elements
				NodeList nlRow=el.getElementsByTagName("row");
				if(nlRow!=null && nlRow.getLength()>0) {
					for(int j=2;j<nlRow.getLength();j++) {
						
						//get the row element
						Element e=(Element)nlRow.item(j);

						//String number=e.getAttribute("number");

						//get the object corresponding to Batch/Course/Faculty
						if(i == 0) {
							InsertBatchInfo(conn, e);
						} else if(i == 1) {
							InsertCourseInfo(conn, e);
						} else if(i == 2) {
							InsertFacultyInfo(conn, e);
						}
						else if(i == 3){
							InsertVenueInfo(conn, e);
						}
					}

				}
			}

		}
	}
	
	private void InsertVenueInfo(Connection conn, Element e) throws SQLException {
		NodeList nl = e.getElementsByTagName("col");
		if(null != nl && nl.getLength() > 0) {
			String venueCode = "";
			int capacity = 0;
			int numComputer = 0;
			int projector = 0;
			int type = 0;
			
			for(int k = 0; k < nl.getLength(); k++) {

				Element col = (Element)nl.item(k);
				int number = Integer.parseInt(col.getAttribute("number"));
				
				switch(number) {
				case 0:
					venueCode = getTextValue(col);
					break;
				case 1:
					capacity = Integer.parseInt(getTextValue(col));
					break;
				case 2:
					numComputer = Integer.parseInt(getTextValue(col));
					break;
				case 3:
					projector = Integer.parseInt(getTextValue(col));
					break;
				case 4:
					type = Integer.parseInt(getTextValue(col));
					break;
				}
			}
			
			String sql = "INSERT INTO venue(code,capacity,ncomputers,projector,type) VALUES(?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, venueCode);
			st.setInt(2, capacity);
			st.setInt(3, numComputer);
			st.setInt(4, projector);
			st.setInt(5, type);
		    st.execute();
		    st.close();
		}
	}


	private void deleteVenue(Connection conn) throws SQLException {

		String sql = "DELETE FROM venue";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
	}

	private void InsertFacultyInfo(Connection conn, Element e) throws SQLException {
		NodeList nl = e.getElementsByTagName("col");
		if(null != nl && nl.getLength() > 0) {
			// name, department, institute, id
			String facultyId = "";
			String facultyName = "";
			String department = "";
			String institute = "";
			ArrayList<String> courseCodes = new ArrayList<String>();
			for(int k = 0; k < nl.getLength(); k++) {
				
				Element col = (Element)nl.item(k);
				
				int number = Integer.parseInt(col.getAttribute("number"));
				
				switch(number) {
				case 0:
					facultyName = getTextValue(col);
					break;
				case 1:
					facultyId = getTextValue(col);
					break;
				case 2:
					department = getTextValue(col);
					break;
				case 3:
					institute = getTextValue(col);
					break;
				default:
					courseCodes.add(getTextValue(col));
				}
				
			}
			
			String sql = "INSERT INTO fac_info(id, name, department, institute) values(?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, facultyId);
			st.setString(2, facultyName);
			st.setString(3, department);
			st.setString(4, institute);
			st.execute();
			st.close();
			
			for(String course_code:courseCodes) {
				sql = "INSERT INTO faculty_course(course_code,faculty_id) values(?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, course_code);
				st.setString(2, facultyId);
				st.execute();
				st.close();
			}
		}
		
	}

	private void InsertCourseInfo(Connection conn, Element e) throws SQLException {
		NodeList nl = e.getElementsByTagName("col");
		if(null != nl && nl.getLength() > 0) {
			// name, department, institute, id
			String courseCode = "";
			int numLectures = 0;
			int lecDuration = 0;
			int numTutorials = 0;
			int tutDuration = 0;
			int numPracticals = 0;
			int pracDuration = 0;
			int scheduled = 0;
			
			for(int k = 0; k < nl.getLength(); k++) {
				
				Element col = (Element)nl.item(k);
				
				int number = Integer.parseInt(col.getAttribute("number"));
				
				switch(number) {
				case 0:
					courseCode = getTextValue(col);
					break;
				case 2:
					numLectures = Integer.parseInt(getTextValue(col));
					break;
				case 3:
					lecDuration = Integer.parseInt(getTextValue(col));
					break;
				case 4:
					numTutorials = Integer.parseInt(getTextValue(col));
					break;
				case 5:
					tutDuration = Integer.parseInt(getTextValue(col));
					break;
				case 6:
					numPracticals = Integer.parseInt(getTextValue(col));
					break;
				case 7:
					pracDuration = Integer.parseInt(getTextValue(col));
					break;
				case 8:
					scheduled = Integer.parseInt(getTextValue(col));
					break;
				default:
					break;
				}
				
			}
			
			String sql = "INSERT INTO course(course_code, course_type, events_per_week, duration, " +
										   " computer, projector, venue_code, scheduled ) values(?,?,?,?,?,?,?,?)";
			
			if(numLectures != 0) {
				String type = String.valueOf(Constants.CLASS_CODES[Constants.LECTURE]);
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, courseCode);
				st.setString(2, type);
				st.setInt(3, numLectures);
				st.setInt(4, lecDuration);
				st.setInt(5, 0);
				st.setInt(6, 0);
				st.setString(7, "");
				st.setInt(8,scheduled);
				st.execute();
				st.close();
			}
			
			if(numTutorials != 0) {
				String type = String.valueOf(Constants.CLASS_CODES[Constants.TUTORIAL]);
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, courseCode);
				st.setString(2, type);
				st.setInt(3, numTutorials);
				st.setInt(4, tutDuration);
				st.setInt(5, 0);
				st.setInt(6, 0);
				st.setString(7, "");
				st.setInt(8,scheduled);
				st.execute();
				st.close();
			}
			
			if(numPracticals != 0) {
				String type = String.valueOf(Constants.CLASS_CODES[Constants.LABORATORY]);
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, courseCode);
				st.setString(2, type);
				st.setInt(3, numPracticals);
				st.setInt(4, pracDuration);
				st.setInt(5, 0);
				st.setInt(6, 0);
				st.setString(7, "");
				st.setInt(8,scheduled);
				st.execute();
				st.close();
			}
			
		}
		
		
	}

	private void deleteFacultyCourse(Connection conn) throws SQLException {
		String sql = "DELETE FROM faculty_course";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
		
	}

	private void deleteFacultyInfo(Connection conn) throws SQLException {
		String sql = "DELETE FROM fac_info";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
		
	}

	private void deleteCourse(Connection conn) throws SQLException {
		String sql = "DELETE FROM course";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
		
	}

	private void deleteBatchCourseInfo(Connection conn) throws SQLException {
		String sql = "DELETE FROM batch_course";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
		
	}

	private void deleteBatchInfo(Connection conn) throws SQLException {
		String sql = "DELETE FROM batch";
		PreparedStatement st = conn.prepareStatement(sql);
		st.execute();
		st.close();
		
	}

	private void InsertBatchInfo(Connection conn, Element e) throws SQLException {
		NodeList nl = e.getElementsByTagName("col");
		if(null != nl && nl.getLength() > 0) {
			String batchId = "";
			String batchName = "";
			int strength = 0;
			ArrayList<String> courseCodes = new ArrayList<String>();
			for(int k = 0; k < nl.getLength(); k++) {
				
				Element col = (Element)nl.item(k);
				
				int number = Integer.parseInt(col.getAttribute("number"));
				
				switch(number) {
				case 0:
					batchId = getTextValue(col);
					break;
				case 1:
					batchName = getTextValue(col);
					break;
				case 2:
					strength = Integer.parseInt(getTextValue(col));
					break;
				default:
					courseCodes.add(getTextValue(col));
				}
				
			}
			
			String sql = "INSERT INTO batch(batch_code,strength,batch_name) values(?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, batchId);
			st.setInt(2, strength);
			st.setString(3, batchName);
			st.execute();
			st.close();
			
			for(String course_code:courseCodes) {
				sql = "INSERT INTO batch_course(course_code,batch_code) values(?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, course_code);
				st.setString(2, batchId);
				st.execute();
				st.close();
			}
		}
	}
	
	
	
		
	private String getTextValue(Element el) {            
          String textVal = new String();
          textVal = el.getFirstChild().getNodeValue();                         
          return textVal;                                               
	}               

	/*public static void main(String[] args) throws TimetableException, SQLException{
		//create an instance
		

		ParseXml px=new ParseXml();	
		px.parseXmlFile();
		px.parseDocument();
	

	}
*/

}
