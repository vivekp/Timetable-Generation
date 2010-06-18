/**
 * 
 */
package org.iitk.brihaspati.modules.timetable;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.*;

/**
 * @author Aniket
 *
 */

public class PDFGenerator implements Constants {
	private Image iitrLogo;
	private final STimetable timetable; 
	
	public PDFGenerator (STimetable timetable) {
		this.timetable = timetable;
	}
	
	public void publishAllFacultyHTML(String dirName) throws TimetableException,IOException {
		ArrayList<Faculty> facultyList = Data.getInstance().getFacultyList();

		String days[] = {"", "Monday", "Tueday","Wednesday","Thursday","Friday" };
		String[] months = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		String slots[] = {"", "9-10 AM","10-11 AM","11-12 Noon","12-1 PM", "1-2 PM", 
				"2-3 PM","3-4 PM","4-5 PM","5-6 PM","","","",""};
		int noOfCols = 6;
		int noOfRows = 10;
		int breakSlot = 5;

		for (int i = 0; i < facultyList.size(); i++) {
			String facultyId = facultyList.get(i).getId();
			String facultyName = facultyList.get(i).getName();
			String facultyDepartment = facultyList.get(i).getDepartment();
			FileWriter outFile = new FileWriter(dirName + "/html/faculty/" + facultyId + " " + facultyName + ".html");
			PrintWriter out = new PrintWriter(outFile);

			try{
			out.println("<html><head><script type=\"text/javascript\" src=\"drag.js\"></script>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"drag.css\" /><br/>" +
					"<br/></head>");
			
			GregorianCalendar g = new GregorianCalendar();
			
			out.println("<body onload=\"REDIPS.drag.init()\">");
			out.println(getJS());
			String p = "<p align=\"right\"> <i>Dated: "+months[g.get(Calendar.MONTH)]+"."+g.get(Calendar.DAY_OF_MONTH)+", "+g.get(Calendar.YEAR)+"</i></p>";
			out.println(p);
			
			p= "<h2 align=\"center\">Indian Institute of Technology, Roorkee</h2>";
			out.println(p);
			/*p = new Paragraph(batchDepartment,f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);*/
			
			p = "<h3 align=\"center\">Spring Semester 2010-2011</h3>";
			out.println(p);
			
			/*f = new Font(Font.TIMES_ROMAN,10,Font.NORMAL);
			p=new Paragraph("               Batch: "+batchCode,f);
			p.setFirstLineIndent(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);*/

			p = "<p align=\"center\">";
			out.println(p);
			
			p = facultyName;
			out.println(p);
			
			out.println("<div id=\"save\"><input type=\"button\" value=\"Save\" " +
					"class=\"button\" onclick=\"save()\" title=\"Send content to the server\"/>" +
					"<span class=\"message_line\">Check availability</span></div>");
			
			p = "</p><br/>";
			out.println(p);
			
			int width = 120;
			out.println("<div id=\"drag\"><table border=\"2\" align=\"center\">" +
					"<tr><td class=\"mark dark\" width=\"" + width + "\">Time\\Day</td>");
			
			for(int k = Constants.MONDAY ; k <= Constants.FRIDAY; k++) {
				out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\">" + days[k] + "</td>");
			}
			out.println("</tr>");
			for(int slot = 1; slot < noOfRows; slot++) {
				out.println("<tr>");
				for(int day = 0; day < noOfCols ; day++){
					if(0==day){
						out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\">");
						p = slots[slot];
						out.println(p);
						out.println("</td>");
					}
					else if(slot == breakSlot){
						int x = noOfCols-1;
						out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\" colspan=\"" + x + "\">");
						p = "Break";
						out.println(p);
						day = noOfCols;
						out.println("</td>");
					}
					else{
						out.println("<td align=\"center\" width=\"" + width + "\"><div class=\"drag\">");
						int j = (day - 1) * 8 + slot - 1 - ((slot > breakSlot) ? 1 : 0);
						String cellText = null;
						ArrayList<Integer> events = timetable.getEventsInSlot(j);
						for (Integer tmp : events) {
							int roomIndex = timetable.getRoomOfEvent(tmp);
							if (roomIndex < 0)
								throw new TimetableException("Room index is negative " + tmp);
							Event event = Data.getInstance().getEventObject(tmp);
							Room room = Data.getInstance().getRoomList().get(roomIndex);
							if (event.getFaculty().getId().equals(facultyId)) {
								String batches = "";
								for (Batch bth : event.getBatchList()){
									batches=batches+bth.getId()+", ";
								}
								if(!batches.equals(""))
									batches=batches.substring(0, batches.length()-2);
								cellText = CLASS_CODES[event.getType()] 
								                       + event.getCourse() + "<br/>" 
								                       + room.getCode() + "<br/>"
								                       + batches;
								break;

							}
						}
						if(null == cellText) cellText = "  " + "<br/>" + " "+ "<br/>" + " "+ "<br/>" + " ";
						p = cellText;
						out.println(p);
						out.println("</div>");
						out.println("</td>");
					}
				}
				out.println("</tr>");
			}
			out.println("</table></div></body></html>");
			out.close();
			} catch(Exception e){
				System.out.println("Error occured while writing HTML for batch");
				e.getMessage();
			}
		}
	}
	
	public void publishAllBatchHTML(String dirName) throws TimetableException,IOException {
		ArrayList<Batch> batches = Data.getInstance().getBatchList();

		String days[] = {"", "Monday", "Tueday","Wednesday","Thursday","Friday" };
		String[] months = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		String slots[] = {"", "9-10 AM","10-11 AM","11-12 Noon","12-1 PM", "1-2 PM", 
				"2-3 PM","3-4 PM","4-5 PM","5-6 PM","","","",""};
		int noOfCols = 6;
		int noOfRows = 10;
		int breakSlot = 5;

		for (int i = 0; i < batches.size(); i++) {
			String batchCode = batches.get(i).getId();
			FileWriter outFile = new FileWriter(dirName + "/html/batch/" + batchCode + ".html");
			PrintWriter out = new PrintWriter(outFile);

			try{
			out.println("<html><head><script type=\"text/javascript\" src=\"drag.js\"></script>" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"drag.css\" /><br/>" +
					getJS() + "</head>");
			
			GregorianCalendar g = new GregorianCalendar();
			
			out.println("<body onload=\"REDIPS.drag.init()\">");
			out.println(getJS());
			String p = "<p align=\"right\"><i>Dated: "+months[g.get(Calendar.MONTH)]+"."+g.get(Calendar.DAY_OF_MONTH)+", "+g.get(Calendar.YEAR)+"</i></p>";
			out.println(p);
			
			p= "<h2 align=\"center\">Indian Institute of Technology, Roorkee</h2>";
			out.println(p);
			p = "<h3 align=\"center\">Spring Semester 2010-2011</h3>";
			out.println(p);
			
			/*f = new Font(Font.TIMES_ROMAN,10,Font.NORMAL);
			p=new Paragraph("               Batch: "+batchCode,f);
			p.setFirstLineIndent(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);*/

			p = "<p align=\"center\">";
			out.println(p);
			
			p = batchCode;
			out.println(p);
			
			out.println("<div id=\"save\"><input type=\"button\" value=\"Save\" " +
					"class=\"button\" onclick=\"save()\" title=\"Send content to the server\"/>" +
					"<span class=\"message_line\">Check availability</span></div>");
			
			p = "</p><br/>";
			out.println(p);
			
			int width = 120;
			out.println("<div id=\"drag\"><table border=\"2\" align=\"center\">" +
					"<tr><td class=\"mark dark\" width=\"" + width + "\">Time\\Day</td>");
			
			for(int k = Constants.MONDAY ; k <= Constants.FRIDAY; k++) {
				out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\">" + days[k] + "</td>");
			}
			out.println("</tr>");
			for(int slot = 1; slot < noOfRows; slot++) {
				out.println("<tr>");
				for(int day = 0; day < noOfCols ; day++){
					if(0==day){
						out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\">");
						p = slots[slot];
						out.println(p);
						out.println("</td>");
					}
					else if(slot == breakSlot){
						int x = noOfCols-1;
						out.println("<td class=\"mark dark\" align=\"center\" width=\"" + width + "\" colspan=\"" + x + "\">");
						p = "Break";
						out.println(p);
						day = noOfCols;
						out.println("</td>");
					}
					else{
						out.println("<td align=\"center\" width=\"" + width + "\"><div class=\"drag\">");
						int j = (day - 1) * 8 + slot - 1 - ((slot > breakSlot) ? 1 : 0);
						String cellText = null;
						ArrayList<Integer> events = timetable.getEventsInSlot(j);
						for (Integer tmp : events) {
							int roomIndex = timetable.getRoomOfEvent(tmp);
							if (roomIndex < 0)
								throw new TimetableException("Room index is negative " + tmp);
							Event event = Data.getInstance().getEventObject(tmp);
							Room room = Data.getInstance().getRoomList().get(roomIndex);
							for (Batch bth : event.getBatchList()){
								if (bth.getId().equals(batchCode)) {
									cellText = CLASS_CODES[event.getType()] 
									                       + event.getCourse() + "<br/>" 
									                       + room.getCode() + "<br/>"
									                       + event.getProfessor().getId();
									break;

								}
							}
						}
						if(null == cellText) cellText = "  " + "<br/>" + " "+ "<br/>" + " "+ "<br/>" + " ";
						p = cellText;
						out.println(p);
						out.println("</div>");
						out.println("</td>");
					}
				}
				out.println("</tr>");
			}
			out.println("</table></div></body></html>");
			out.close();
			} catch(Exception e){
				System.out.println("Error occured while writing HTML for batch");
				e.getMessage();
			}
		}
	}
	
	public void publishAllFacultyTimetables(String dirName) throws TimetableException, DocumentException {
		
		try{

			ArrayList<Faculty> facultyList = Data.getInstance().getFacultyList();

			String days[] = {"", "Monday", "Tueday","Wednesday","Thursday","Friday" };
			String[] months = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
			String slots[] = {"", "9-10 AM","10-11 AM","11-12 Noon","12-1 PM", "1-2 PM", 
					"2-3 PM","3-4 PM","4-5 PM","5-6 PM","","","",""};
			int noOfCols = 6;
			int noOfRows = 10;
			int breakSlot = 5;

			for (int i = 0; i < facultyList.size(); i++) {
				String facultyId = facultyList.get(i).getId();
				String facultyName = facultyList.get(i).getName();
				String facultyDepartment = facultyList.get(i).getDepartment();
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, new FileOutputStream(dirName + "/pdf/faculty/" + facultyId + " " + facultyName + ".pdf"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				document.open();
				try{
					iitrLogo = Image.getInstance(dirName + "../images/IITR Logo.jpg");
					iitrLogo.setAbsolutePosition(70, 750);
					iitrLogo.scaleAbsolute(50,50);
					document.add(iitrLogo);
				}
				catch(Exception e){
					//				e.printStackTrace();
				}
				Font f;
				Paragraph p;
				GregorianCalendar g = new GregorianCalendar();
				f = new Font(Font.UNDEFINED,10,Font.ITALIC);
				p = new Paragraph("Dated: "+months[g.get(Calendar.MONTH)]+"."+g.get(Calendar.DAY_OF_MONTH)+", "+g.get(Calendar.YEAR),f);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);

				f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.BOLD);
				p= new Paragraph("Indian Institute of Technology, Roorkee",f);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				p = new Paragraph(facultyDepartment,f);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.NORMAL);
				p = new Paragraph("Spring Semester 2010-2011",f);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);

				/*f = new Font(Font.TIMES_ROMAN,10,Font.NORMAL);
			p=new Paragraph("               Batch: "+batchCode,f);
			p.setFirstLineIndent(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);*/

				float[] colWidths = {1.25f,2f,2f,2f,2f,2f};
				PdfPTable table = new PdfPTable(colWidths);
				PdfPCell cell = new PdfPCell();

				table.setWidthPercentage(80);
				table.setSpacingBefore(25f);
				table.setSpacingAfter(10f);
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setUseBorderPadding(true);

				f = new Font(Font.UNDEFINED,10,Font.BOLD | Font.UNDERLINE);
				p = new Paragraph(facultyName,f);
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);

				f = new Font(Font.UNDEFINED,9,Font.BOLD);
				p=new Paragraph("Time\\Day",f);
				cell = new PdfPCell(p);
				cell.setFixedHeight(20f);
				cell = getSpecialCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				f = new Font(Font.UNDEFINED,9,Font.BOLD);
				for(int k = Constants.MONDAY ; k <= Constants.FRIDAY; k++) {
					Paragraph dayText = new Paragraph(days[k],f);
					cell = new PdfPCell(dayText);
					cell.setFixedHeight(20f);
					cell = getSpecialCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}

				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				table.getDefaultCell().setFixedHeight(40f);
				table.getDefaultCell().setPadding(2);
				table.getDefaultCell().setPaddingBottom(2);
				table.getDefaultCell().setUseAscender(true);
				table.getDefaultCell().setUseDescender(true);

				for(int slot = 1; slot < noOfRows; slot++) {
					for(int day = 0; day < noOfCols ; day++){
						if(0==day){
							f = new Font(Font.TIMES_ROMAN,8,Font.NORMAL);
							p=new Paragraph(slots[slot],f);
							table.addCell(getSpecialCell(new PdfPCell(p)));
						}
						else if(slot == breakSlot){
							f = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
							p = new Paragraph("Break",f);
							cell = new PdfPCell(p);
							cell.setColspan(5);
							cell = getSpecialCell(cell);
							//cell.setBorderWidth(1f);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);
							day = noOfCols;
						}
						else{
							int j = (day - 1) * 8 + slot - 1 - ((slot > breakSlot) ? 1 : 0);
							String cellText = null;
							ArrayList<Integer> events = timetable.getEventsInSlot(j);
							for (Integer tmp : events) {
								int roomIndex = timetable.getRoomOfEvent(tmp);
								if (roomIndex < 0)
									throw new TimetableException("Room index is negative " + tmp);
								Event event = Data.getInstance().getEventObject(tmp);
								Room room = Data.getInstance().getRoomList().get(roomIndex);
								if (event.getFaculty().getId().equals(facultyId)) {
									String batches = "";
									for (Batch bth : event.getBatchList()){
										batches=batches+bth.getId()+", ";
									}
									if(!batches.equals(""))
										batches=batches.substring(0, batches.length()-2);
									cellText = CLASS_CODES[event.getType()] 
									                       + event.getCourse() + "\n" 
									                       + room.getCode() + "\n"
									                       + batches;
									break;

								}
							}
							if(null == cellText) cellText = "  " + "\n" + " ";
							f = new Font(Font.UNDEFINED,10,Font.NORMAL);
							p = new Paragraph(cellText,f);
							table.addCell(getNormalCell(new PdfPCell(p)));
						}
					}
				}
				document.add(table);
				document.close();
			}
		} catch (Exception e) {
			System.out.println("Error in publishAllFaculty");
			System.out.println(e.getMessage());
			System.out.println(e);
		}
	}
	public void publishAllBatchesTimetables(String dirName) throws TimetableException, DocumentException {
		try{
		
		ArrayList<Batch> batches = Data.getInstance().getBatchList();
		String days[]   = {"", "Monday", "Tueday","Wednesday","Thursday","Friday" };
		String months[] = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		String slots[]  = {"", "9-10 AM","10-11 AM","11-12 Noon","12-1 PM", "1-2 PM", 
				"2-3 PM","3-4 PM","4-5 PM","5-6 PM","","","",""};
		
		int noOfCols = 6;
		int noOfRows = 10;
		int breakSlot = 5;

		for (int i = 0; i < batches.size(); i++) {
			String batchCode = batches.get(i).getId();
			String batchName = batches.get(i).getName();
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(dirName + "/pdf/batch/" + batchCode + ".pdf"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			document.open();
			try{
				iitrLogo = com.lowagie.text.Image.getInstance(dirName + "../images/IITR Logo.jpg");
				iitrLogo.setAbsolutePosition(70, 750);
				iitrLogo.scaleAbsolute(50,50);
				document.add(iitrLogo);
			}
			catch(Exception e){
//				e.printStackTrace();
			}
			Font f;
			Paragraph p;
			GregorianCalendar g = new GregorianCalendar();
			f = new Font(Font.UNDEFINED,10,Font.ITALIC);
			p = new Paragraph("Dated: "+months[g.get(Calendar.MONTH)]+"."+g.get(Calendar.DAY_OF_MONTH)+", "+g.get(Calendar.YEAR),f);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			
			f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.BOLD);
			p= new Paragraph("Indian Institute of Technology, Roorkee",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			p = new Paragraph("Department of Computer Science",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.NORMAL);
			p = new Paragraph("Spring Semester 2010-2011",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			f = new Font(Font.TIMES_ROMAN,10,Font.NORMAL);
			p=new Paragraph("               Batch: "+batchCode,f);
			p.setFirstLineIndent(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
			
			float[] colWidths = {1.25f,2f,2f,2f,2f,2f};
			PdfPTable table = new PdfPTable(colWidths);
			PdfPCell cell = new PdfPCell();
			
			table.setWidthPercentage(80);
			table.setSpacingBefore(25f);
			table.setSpacingAfter(10f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setUseBorderPadding(true);
			
			f = new Font(Font.UNDEFINED,10,Font.BOLD | Font.UNDERLINE);
			p = new Paragraph("Branch: " + batchName,f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			f = new Font(Font.UNDEFINED,9,Font.BOLD);
			p=new Paragraph("Time\\Day",f);
			cell = new PdfPCell(p);
			cell.setFixedHeight(20f);
			cell = getSpecialCell(cell);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			f = new Font(Font.UNDEFINED,9,Font.BOLD);
			for(int k = Constants.MONDAY ; k <= Constants.FRIDAY; k++) {
				Paragraph dayText = new Paragraph(days[k],f);
				cell = new PdfPCell(dayText);
				cell.setFixedHeight(20f);
				cell = getSpecialCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
			table.getDefaultCell().setFixedHeight(40f);
			table.getDefaultCell().setPadding(2);
			table.getDefaultCell().setPaddingBottom(2);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			
			for(int slot = 1; slot < noOfRows; slot++) {
				for(int day = 0; day < noOfCols ; day++){
					if(day == 0){
						f = new Font(Font.TIMES_ROMAN,8,Font.NORMAL);
						p=new Paragraph(slots[slot],f);
						table.addCell(getSpecialCell(new PdfPCell(p)));
					}
					else if(slot == breakSlot){
						f = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
						p = new Paragraph("Break",f);
						cell = new PdfPCell(p);
						cell.setColspan(5);
						cell = getSpecialCell(cell);
						//cell.setBorderWidth(1f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						day = noOfCols;
					}
					else{
						int j = (day - 1) * 8 + slot - 1 - ((slot > breakSlot) ? 1 : 0);
						String cellText = null;
						ArrayList<Integer> events = timetable.getEventsInSlot(j);
						for (Integer tmp : events) {
							int roomIndex = timetable.getRoomOfEvent(tmp);
							if (roomIndex < 0)
								throw new TimetableException("Room index is negative "
										+ tmp);
							Event event = Data.getInstance().getEventObject(tmp);
							Room room = Data.getInstance().getRoomList().get(roomIndex);
							for (Batch bth : event.getBatchList()){
								if (bth.getId().equals(batchCode)) {
									cellText = CLASS_CODES[event.getType()] 
									                       + event.getCourse() + "\n" 
									                       + room.getCode() + "\n"
									                       + event.getProfessor().getId();
									break;

								}
							}
						}
						if(null == cellText) cellText = "  " + "\n" + " ";
						f = new Font(Font.UNDEFINED,10,Font.NORMAL);
						p = new Paragraph(cellText,f);
						table.addCell(getNormalCell(new PdfPCell(p)));
					}
				}
			}
			
			document.add(table);
			document.close();
		}
	}
		catch(Exception e){
			System.out.println("error in publishAllBatches");
			System.out.println(e.getMessage());
		}
	}
	
	public void publishAllRoomTimetables(String dirName) throws TimetableException, DocumentException {
		try{
		
		ArrayList<Room> rooms = Data.getInstance().getRoomList();
		String days[]   = {"", "Monday", "Tueday","Wednesday","Thursday","Friday" };
		String months[] = {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		String slots[]  = {"", "9-10 AM","10-11 AM","11-12 Noon","12-1 PM", "1-2 PM", 
				"2-3 PM","3-4 PM","4-5 PM","5-6 PM","","","",""};
		
		int noOfCols = 6;
		int noOfRows = 10;
		int breakSlot = 5;

		for (int i = 0; i < rooms.size(); i++) {
			String roomCode = rooms.get(i).getCode();
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(dirName + "/pdf/room/" + roomCode + ".pdf"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			document.open();
			try{
				iitrLogo = com.lowagie.text.Image.getInstance(dirName + "../images/IITR Logo.jpg");
				iitrLogo.setAbsolutePosition(70, 750);
				iitrLogo.scaleAbsolute(50,50);
				document.add(iitrLogo);
			}
			catch(Exception e){
//				e.printStackTrace();
			}
			Font f;
			Paragraph p;
			GregorianCalendar g = new GregorianCalendar();
			f = new Font(Font.UNDEFINED,10,Font.ITALIC);
			p = new Paragraph("Dated: "+months[g.get(Calendar.MONTH)]+"."+g.get(Calendar.DAY_OF_MONTH)+", "+g.get(Calendar.YEAR),f);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			
			f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.BOLD);
			p= new Paragraph("Indian Institute of Technology, Roorkee",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			p = new Paragraph("Department of Computer Science",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			f = new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.NORMAL);
			p = new Paragraph("Spring Semester 2010-2011",f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			float[] colWidths = {1.25f,2f,2f,2f,2f,2f};
			PdfPTable table = new PdfPTable(colWidths);
			PdfPCell cell = new PdfPCell();
			
			table.setWidthPercentage(80);
			table.setSpacingBefore(25f);
			table.setSpacingAfter(10f);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setUseBorderPadding(true);
			
			f = new Font(Font.UNDEFINED,10,Font.BOLD | Font.UNDERLINE);
			p = new Paragraph("Room: "+roomCode,f);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			f = new Font(Font.UNDEFINED,9,Font.BOLD);
			p=new Paragraph("Time\\Day",f);
			cell = new PdfPCell(p);
			cell.setFixedHeight(20f);
			cell = getSpecialCell(cell);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			f = new Font(Font.UNDEFINED,9,Font.BOLD);
			for(int k = Constants.MONDAY ; k <= Constants.FRIDAY; k++) {
				Paragraph dayText = new Paragraph(days[k],f);
				cell = new PdfPCell(dayText);
				cell.setFixedHeight(20f);
				cell = getSpecialCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
			table.getDefaultCell().setFixedHeight(40f);
			table.getDefaultCell().setPadding(2);
			table.getDefaultCell().setPaddingBottom(2);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			
			for(int slot = 1; slot < noOfRows; slot++) {
				for(int day = 0; day < noOfCols ; day++){
					if(day == 0){
						f = new Font(Font.TIMES_ROMAN,8,Font.NORMAL);
						p=new Paragraph(slots[slot],f);
						table.addCell(getSpecialCell(new PdfPCell(p)));
					}
					else if(slot == breakSlot){
						f = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
						p = new Paragraph("Break",f);
						cell = new PdfPCell(p);
						cell.setColspan(5);
						cell = getSpecialCell(cell);
						//cell.setBorderWidth(1f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						day = noOfCols;
					}
					else{
						int j = (day - 1) * 8 + slot - 1 - ((slot > breakSlot) ? 1 : 0);
						String cellText = null;
						ArrayList<Integer> events = timetable.getEventsInSlot(j);
						for (Integer tmp : events) {
							int roomIndex = timetable.getRoomOfEvent(tmp);
							if (roomIndex < 0)
								throw new TimetableException("Room index is negative "
										+ tmp);
							Room eventRoom = rooms.get(roomIndex);
							Event event = Data.getInstance().getEventObject(tmp);
							if (eventRoom.getCode().equals(roomCode)) {
								String batches = "";
								for (Batch bth : event.getBatchList()){
									batches=batches+bth.getId()+", ";
								}
								if(!batches.equals(""))
									batches=batches.substring(0, batches.length()-2);
								cellText = CLASS_CODES[event.getType()] 
								                       + event.getCourse() + "\n" 
								                       + event.getProfessor().getId() + "\n"
								                       + batches;
								break;

							}
						}
						if(null == cellText) cellText = "  " + "\n" + " ";
						f = new Font(Font.UNDEFINED,10,Font.NORMAL);
						p = new Paragraph(cellText,f);
						table.addCell(getNormalCell(new PdfPCell(p)));
					}
				}
			}
			
			document.add(table);
			document.close();
		}
	}
		catch(Exception e){
			System.out.println("Error in publishAllRooms");
			System.out.println(e.getMessage());
			System.out.println(e);
		}
	}
	
	private PdfPCell getSpecialCell(PdfPCell cell){
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPadding(2);
		cell.setGrayFill(0.8f);
		cell.setBorderWidth(1.1f);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		cell.setUseBorderPadding(true);
		return cell;
	}
	private PdfPCell getNormalCell(PdfPCell cell){
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setPadding(2);
		cell.setBorderWidth(1f);
		cell.setFixedHeight(40f);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		cell.setUseBorderPadding(true);
		return cell;
	}
	private Cell getHTMLCell(Paragraph p){
		Cell cell = new Cell("");
		try{
			cell = new Cell(p);
		}
		catch(Exception e){
			System.out.println("Error while getting HTML cells");
			System.out.println(e);
		}
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		cell.setWidth(100f);
		return cell;
	}
	private String getJS(){
		String js = "<script type=\"text/javascript\">REDIPS.drag.drop_option = 'switch';</script>";
		return js;
	}
}
