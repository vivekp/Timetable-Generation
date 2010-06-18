
package jxl.demo;

import java.io.BufferedWriter;
import java.io.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Simple demo class which uses the api to present the contents
 * of an excel 97 spreadsheet as an XML document, using a workbook
 * and output stream of your choice
 */
public class XML
{
  /**
   * The output stream to write to
   */
  private OutputStream out;

  /** 
   * The encoding to write
   */
  private String encoding;

  /**
   * The workbook we are reading from
   */
  private Workbook workbook;
  
  /**
   * Constructor
   *
   * @param w The workbook to interrogate
   * @param out The output stream to which the XML values are written
   * @param enc The encoding used by the output stream.  Null or 
   * unrecognized values cause the encoding to default to UTF8
   * @param f Indicates whether the generated XML document should contain
   * the cell format information
   * @exception java.io.IOException
   */
  public XML(Workbook w, OutputStream out, String enc, boolean f,String xmlFile)
    throws IOException
  {
    encoding = enc;
    workbook = w;
    this.out = out;

    writeXML(xmlFile);

  }

  /**
   * Writes out the workbook data as XML, without formatting information
   */
  private void writeXML(String xmlFile) throws IOException
  {
    try
    {
      OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
    /*  BufferedWriter bw = new BufferedWriter(osw);*/
      FileWriter bw=new FileWriter(new File(xmlFile));
      
      bw.write("<?xml version=\"1.0\" ?>\n");
    //  bw.newLine();
    //  bw.newLine();
      bw.write("<workbook>\n");
    //  bw.newLine();
      for (int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++)
      {
        Sheet s = workbook.getSheet(sheet);

        bw.write("  <sheet>\n");
      //  bw.newLine();
        bw.write("    <name>"+s.getName()+"</name>\n");
      //  bw.newLine();
      
        Cell[] row = null;
      
        for (int i = 0 ; i < s.getRows() ; i++)
        {
          bw.write("    <row number=\"" + i + "\">\n");
      //    bw.newLine();
          row = s.getRow(i);

          for (int j = 0 ; j < row.length; j++)
          {
            if (row[j].getType() != CellType.EMPTY)
            {
              bw.write("      <col number=\"" + j + "\">");
              bw.write(row[j].getContents());
              bw.write("</col>\n");
        //      bw.newLine();
            }
          }
          bw.write("    </row>\n");
         // bw.newLine();
        }
        bw.write("  </sheet>\n");
       // bw.newLine();
      }
      
      bw.write("</workbook>");
     // bw.newLine();

 //     bw.flush();
      bw.close();
    }
    catch (UnsupportedEncodingException e)
    {
      System.err.println(e.toString());
    }
  }

  public OutputStream getOutputStream() {
	  return out;
  }

}






