/* Generated by Together */

package com.dimata.harisma.report.leavedp;

import java.sql.*;
import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.session.attendance.*;

public class GeneralPdf  extends HttpServlet {
    //public static String strURL = SessSystemProperty.PROP_APPURL;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
        
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {

        Vector dep = PstDepartment.listAll();

        // setting the color values
     	Color border = new Color(0x00, 0x00, 0x00);
     
	    // setting some fonts in the color chosen by the user
        Font fontHeaderBig = new Font(Font.HELVETICA, 10, Font.NORMAL, border);
        Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, border);
        Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, border);
        Font tableContent = new Font(Font.HELVETICA, 7, Font.NORMAL, border);
        Font fontSpellCharge = new Font(Font.TIMES_NEW_ROMAN, 7, Font.BOLDITALIC, border);
        Font fontItalic = new Font(Font.TIMES_NEW_ROMAN, 7, Font.ITALIC, border);
        Font fontItalicBold = new Font(Font.TIMES_NEW_ROMAN, 7, Font.BOLDITALIC, border);
        Font fontUnderline = new Font(Font.TIMES_NEW_ROMAN, 7, Font.UNDERLINE, border);
        Font contentBold = new Font(Font.TIMES_NEW_ROMAN, 7, Font.BOLD, border);

        Color bgColor = new Color(240,240,240);

     	Document document = new Document(PageSize.A4, 20, 20, 20, 20);
     	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int start = 0;

        try{
           		PdfWriter.getInstance(document, baos);
	            
	            document.addSubject("This is a subject.");
	            document.addSubject("This is a subject two.");

	            document.open();
        }
        catch(Exception e){
            	System.out.println(e.toString());
        }


		if(dep != null && dep.size()>0){
            try{
	            // step2.2: creating an instance of the writer

		            Table tbl = new Table(1);
		            tbl.setBorderColor(new Color(255,255,255));
		            tbl.setWidth(100);
		            tbl.setBorderWidth(1);
		            tbl.setCellpadding(1);
		            tbl.setCellspacing(1);

                    Cell hcell = new Cell(new Chunk("RECAPITULATION OF YEAR LEAVE, LONG LEAVE, DP\nfor the month of "+Formater.formatDate(new Date(), "MMMM yyyy"),fontHeaderBig));
                    hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tbl.addCell(hcell);

		            document.add(tbl);

                    int headerWidth[] = {3 , 25, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
                    Table tblHeader = new Table(14);
		            //tblHeader.setBorderColor(new Color(255,255,255));
		            tblHeader.setWidth(100);
                    tblHeader.setWidths(headerWidth);
		            tblHeader.setBorderWidth(1);
		            tblHeader.setCellpadding(1);
		            tblHeader.setCellspacing(1);

                    hcell = new Cell(new Chunk("No.",tableContent));
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setBackgroundColor(bgColor);
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setRowspan(2);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Department",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setRowspan(2);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("DP",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(4);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Year Leave",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(4);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Long Leave",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(4);
                    tblHeader.addCell(hcell);

                    //-------

                    hcell = new Cell(new Chunk("LM",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Add",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Taken",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Blnc",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("LM",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Add",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Taken",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Blnc",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("LM",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Add",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Taken",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

                    hcell = new Cell(new Chunk("Blnc",tableContent));
                    hcell.setBackgroundColor(bgColor);
                    //hcell.setBorderColor(new Color(255,255,255));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblHeader.addCell(hcell);

		            document.add(tblHeader);

                    //---------------

                    int dpAmount = 0;
					int alAmount = 0;
					int llAmount = 0;
					int addDp = 0;
					int addAl = 0;
					int addLl = 0;
					int takeAl = 0;
					int takeLl	= 0;
					int takeDp = 0; 
					long depOID = 0;
					  
					  
					int totDpAmount = 0;
					int totAlAmount = 0;
					int totLlAmount = 0;
					
					int totAddDp = 0;
					int totAddAl = 0;
					int totAddLl = 0;
					
					int totTakeAl = 0;
					int totTakeLl	= 0;
					int totTakeDp = 0; 
					
					int blcDp = 0;
					int blcAl = 0;
					int blcLl = 0;


                    Table tblContent = new Table(14);
		            //tblContent.setBorderColor(new Color(255,255,255));
		            tblContent.setWidth(100);
                    tblContent.setWidths(headerWidth);
		            tblContent.setBorderWidth(0);
		            tblContent.setCellpadding(0);
		            tblContent.setCellspacing(1);

                    for(int i=0; i<dep.size(); i++){

                        Department department = (Department)dep.get(i);

                        //---------------

                        depOID = department.getOID();
												
						dpAmount = SessLeaveStock.getTotalDpByDepartment(depOID);
						alAmount = SessLeaveStock.getTotalAlByDepartment(depOID);
						llAmount = SessLeaveStock.getTotalLlByDepartment(depOID);
						
						addDp = SessLeaveStock.getTotalAddDpByDepartment(depOID);
						addAl = SessLeaveStock.getTotalAddAlByDepartment(depOID);
						addLl = SessLeaveStock.getTotalAddLlByDepartment(depOID);
						
						takeAl = SessLeave.getTakenAnualLeaveByDepartment(depOID);
						takeLl	= SessLeave.getTakenLongLeaveByDepartment(depOID);
						takeDp = SessDayOfPayment.getDayOffByDepartment(depOID);
						
						//---------
						
						totDpAmount = totDpAmount + (dpAmount - addDp);
						totAlAmount = totAlAmount + (alAmount - addAl);
						totLlAmount = totLlAmount + (llAmount - addLl);
						
						totAddDp = totAddDp + addDp;
						totAddAl = totAddAl + addAl;
						totAddLl = totAddLl + addLl;
						
						totTakeAl = totTakeAl + takeAl;
						totTakeLl	= totTakeLl + takeLl;
						totTakeDp = totTakeDp + takeDp;

                        //---------------


	                    hcell = new Cell(new Chunk(""+(i+1),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(department.getDepartment(),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(dpAmount-addDp),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+addDp,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+takeDp,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(dpAmount-takeDp),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(alAmount-addAl),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+addAl,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+takeAl,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(alAmount-takeAl),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(llAmount-addLl),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+addLl,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+takeLl,tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
	
	                    hcell = new Cell(new Chunk(""+(llAmount-takeLl),tableContent));
	                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    tblContent.addCell(hcell);
                    }

		            document.add(tblContent);


                    Table tblFooter = new Table(14);
		            //tblFooter.setBorderColor(new Color(255,255,255));
		            tblFooter.setWidth(100);
                    tblFooter.setWidths(headerWidth);
		            tblFooter.setBorderWidth(1);
		            tblFooter.setCellpadding(1);
		            tblFooter.setCellspacing(1);

                    hcell = new Cell(new Chunk("TOTAL",tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(2);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totDpAmount,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totAddDp,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totTakeDp,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totDpAmount + totAddDp - totTakeDp),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totAlAmount,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totAddAl,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totTakeAl,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totAlAmount + totAddAl - totTakeAl),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totLlAmount,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totAddLl,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+totTakeLl,tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totLlAmount + totAddLl - totTakeLl),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    //---------------

                    Date dt = new Date();
					int month = dt.getMonth();
					if(month==0){
					  	month=11;
					}
					else{
					  	month = month -1;	
					}

					String where = PstPrevLeave.fieldNames[PstPrevLeave.FLD_MONTH]+"="+month;
					  
					Vector temp = PstPrevLeave.list(0,0, where, null);
					PrevLeave prev = new PrevLeave();
					if(temp!=null && temp.size()>0){
						prev = (PrevLeave)temp.get(0);
					}
					
					Date prevMonth = new Date();
					prevMonth.setMonth(prevMonth.getMonth()-1);


                    hcell = new Cell(new Chunk("Previous Month("+Formater.formatDate(prevMonth, "MMMM")+")",tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(2);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getDpLm(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getDpAdd(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getDpTaken(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getDpBal(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getAlLm(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getAlAdd(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getAlTaken(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getAlBal(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getLlLm(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getLlAdd(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getLlTaken(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+prev.getLlBal(),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

					//------------------

                    hcell = new Cell(new Chunk("Variance",tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    hcell.setColspan(2);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totDpAmount - prev.getDpLm()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totAddDp - prev.getDpAdd()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totTakeDp - prev.getDpTaken()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+((totDpAmount + totAddDp - totTakeDp) - prev.getDpBal()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totAlAmount - prev.getAlLm()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totAddAl - prev.getAlAdd()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totTakeAl-prev.getAlTaken()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+((totAlAmount + totAddAl - totTakeAl)-prev.getAlBal()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totLlAmount-prev.getLlLm()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totAddLl-prev.getLlAdd()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+(totTakeLl-prev.getLlTaken()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);

                    hcell = new Cell(new Chunk(""+((totLlAmount + totAddLl - totTakeLl)-prev.getLlBal()),tableContent));
                    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tblFooter.addCell(hcell);


		            document.add(tblFooter);




                }
                catch(Exception e){
                    System.out.println("Exception e : "+e.toString());
                }

            }
	        
	        // step 5: closing the document
            try{

            }catch(Exception exc){}

	        document.close();
	        
	        // we have written the pdfstream to a ByteArrayOutputStream,
	        // now we are going to write this outputStream to the ServletOutputStream
	        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
            System.out.println("print==============");
	        response.setContentType("application/pdf");
	        response.setContentLength(baos.size());
	        ServletOutputStream out = response.getOutputStream();
	        baos.writeTo(out);
	        out.flush();

    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}