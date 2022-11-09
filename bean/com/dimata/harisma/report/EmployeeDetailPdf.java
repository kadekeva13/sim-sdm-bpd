/*
 * EmployeeDetailPdf.java
 *
 * Created on June 23, 2003, 3:02 PM
 */
package com.dimata.harisma.report;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import java.util.*;
import java.sql.*;
import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.session.admin.SessUserSession;
import com.dimata.system.entity.*;
import com.dimata.harisma.session.employee.*;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.lang.I_Dictionary;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeDetailPdf extends HttpServlet {

    /** Initializes the servlet.
     */
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
        System.out.println("===| EmployeeDetailPdf |===");
        long oidEmployee = FRMQueryString.requestLong(request, "oid");
        //add by Eri Yudi 2020-07-07 untuk fitur select data yang mau di print
        boolean showPersonalData = FRMQueryString.requestBoolean(request, "personalData");
        boolean showLastCarrier = FRMQueryString.requestBoolean(request, "lastCarrier");
        boolean showCarrierPath = FRMQueryString.requestBoolean(request, "carrierPath");
        boolean showPenugasan = FRMQueryString.requestBoolean(request, "penugasan");
        boolean showGradeHistory = FRMQueryString.requestBoolean(request, "gradeHistory");
        boolean showFamilyMember = FRMQueryString.requestBoolean(request, "familyMember");
        boolean showLanguage = FRMQueryString.requestBoolean(request, "language");
        boolean showEducation = FRMQueryString.requestBoolean(request, "education");
        boolean showTrainingHistory = FRMQueryString.requestBoolean(request, "trainingHistory");
        boolean showExprience = FRMQueryString.requestBoolean(request, "exprience");
        boolean showWarning = FRMQueryString.requestBoolean(request, "warning");
        boolean showReprimand = FRMQueryString.requestBoolean(request, "reprimand");
        boolean showAward = FRMQueryString.requestBoolean(request, "award");
        
        showPersonalData = showPersonalData;
        Employee employee = new Employee();
        Religion religion = new Religion();
        Marital marital = new Marital();
        Department department = new Department();
        com.dimata.harisma.entity.masterdata.Section section = new com.dimata.harisma.entity.masterdata.Section();
        Position position = new Position();
        EmpCategory empCategory = new EmpCategory();
        Level level = new Level();
        GradeLevel gLvl = new GradeLevel();

        CareerPath career = new CareerPath();
        CareerPath careerGrade = new CareerPath();
        CareerPath careerPJS = new CareerPath();
        CareerPath lastCareer = new CareerPath();
        FamilyMember family = new FamilyMember();
        EmpLanguage empL = new EmpLanguage();
        EmpEducation empE = new EmpEducation();
        Experience exp = new Experience();
        TrainingHistory tr = new TrainingHistory();
        GradeLevel gd = new GradeLevel();

        try {
            employee = PstEmployee.fetchExc(oidEmployee);
            
            if (employee.getReligionId() != 0){
                religion = PstReligion.fetchExc(employee.getReligionId());
            }
            if (employee.getMaritalId() != 0){
                marital = PstMarital.fetchExc(employee.getMaritalId());
            }
            if (employee.getDepartmentId() != 0){
                department = PstDepartment.fetchExc(employee.getDepartmentId());
            }
            if (employee.getSectionId() != 0){
                section = PstSection.fetchExc(employee.getSectionId());
            }
            if (employee.getPositionId() != 0){
                position = PstPosition.fetchExc(employee.getPositionId());
            }
            if (employee.getEmpCategoryId() != 0){
                empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
            }
            if (employee.getLevelId() != 0){
                level = PstLevel.fetchExc(employee.getLevelId());
            }
            if (employee.getGradeLevelId() != 0){
                gLvl = PstGradeLevel.fetchExc(employee.getGradeLevelId());
            }
            
        } catch (Exception e) {
        }


        //load career
        Vector vCareer = PstCareerPath.list(0, 0, "employee_id=" + employee.getOID() + " and HISTORY_GROUP != 1 AND HISTORY_TYPE NOT IN (2,3)", "WORK_FROM asc");
        if (vCareer != null && vCareer.size() > 0) {
            career = (CareerPath) vCareer.get(0);
        }
        
        Vector lastCareer2 = PstCareerPath.list(0, 0, "employee_id=" + employee.getOID() + " and HISTORY_GROUP != 1 AND HISTORY_TYPE NOT IN (2,3)", "WORK_FROM desc");
        if (lastCareer2 != null && lastCareer2.size() > 0) {
            lastCareer = (CareerPath) lastCareer2.get(0);
        }
        
        Vector pjsCareer = PstCareerPath.list(0, 0, "employee_id=" + employee.getOID() + " and HISTORY_GROUP != 1 AND HISTORY_TYPE IN (2,3)", "WORK_FROM asc");
        if (pjsCareer != null && pjsCareer.size()>0){
            careerPJS = (CareerPath) pjsCareer.get(0);
        }
        
        Vector gradeCareer = PstCareerPath.list(0, 0, "employee_id=" + employee.getOID() + " and HISTORY_GROUP != 0", "WORK_FROM asc");
        if (gradeCareer != null && gradeCareer.size() > 0) {
            careerGrade = (CareerPath) gradeCareer.get(0);
        }
        
        
        //load family member
        Vector vFamily = PstFamilyMember.list(0, 0, "employee_id=" + employee.getOID() + " AND show_in_cv = "+PstFamRelation.CV_YES, "");
        if (vFamily != null && vFamily.size() > 0) {
            family = (FamilyMember) vFamily.get(0);
        }

        //load EmpLanguage
        Vector vLan = PstEmpLanguage.list(0, 0, "employee_id=" + employee.getOID() + "", "");
        if (vLan != null && vLan.size() > 0) {
            empL = (EmpLanguage) vLan.get(0);
        }

        //load Education
        Vector vEdu = PstEmpEducation.list(0, 0, "employee_id=" + employee.getOID() + "", "END_DATE asc");
        if (vEdu != null && vEdu.size() > 0) {
            empE = (EmpEducation) vEdu.get(0);
        }

        //load Experience
        Vector vExp = PstExperience.list(0, 0, "employee_id=" + employee.getOID() + "", "END_DATE desc");
        if (vExp != null && vExp.size() > 0) {
            exp = (Experience) vExp.get(0);
        }

        Vector vTraning = PstTrainingHistory.list(0, 0, "employee_id=" + employee.getOID() + "", "TRAINING_PROGRAM, START_DATE ASC");
        if (vTraning != null && vTraning.size() > 0) {
            tr = (TrainingHistory) vTraning.get(0);
        }

        // load warning 
        Vector vWarning = PstEmpWarning.list(0, 0, "employee_id=" + employee.getOID() + "", "WARN_DATE");

        // load reprimand
        Vector tempvReprimand = new Vector();
        Vector vReprimand = PstEmpReprimand.list(0, 0, "employee_id=" + employee.getOID() + "", "REPRIMAND_DATE");
        for (int i = 0; i < vReprimand.size(); i++) {
            
            EmpReprimand empRepri = (EmpReprimand)vReprimand.get(i);
            try{
                Reprimand reprin = PstReprimand.fetchExc(empRepri.getReprimandLevelId());
                if(reprin.getShowInCV()==0){
                    tempvReprimand.add(empRepri);
                }
            }catch(Exception e){
                System.out.println("Error Get Reprimand"+ e);
            }
            
        }
        vReprimand = tempvReprimand;
        // load reward
        Vector vAward = PstEmpAward.list(0, 0, "emp_id=" + employee.getOID() + "", "'DATE'");

        //end load - file    

        //request gambar
        String pictPath = "";
        SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
        try {
            pictPath = sessEmployeePicture.fetchImageEmployee(oidEmployee);
        } catch (Exception e) {
            System.out.println("err." + e.toString());
        }
        
        NumberSpeller numSpeller = new NumberSpeller();

        Color border = new Color(0x00, 0x00, 0x00);

        // setting some fonts in the color chosen by the user
        Font fontHeaderBig = new Font(Font.HELVETICA, 10, Font.BOLD, border);
        Font fontHeaderSmall = new Font(Font.HELVETICA, 6, Font.NORMAL, border);
        Font fontHeader = new Font(Font.HELVETICA, 8, Font.BOLD, border);
        Font fontContent = new Font(Font.HELVETICA, 8, Font.BOLD, border);
        Font tableContent = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
        Font fontSpellCharge = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, border);
        Font fontItalic = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, border);
        Font fontItalicBottom = new Font(Font.HELVETICA, 8, Font.ITALIC, border);
        Font fontUnderline = new Font(Font.HELVETICA, 8, Font.UNDERLINE, border);

        Color bgColor = new Color(240, 240, 240);

        Color blackColor = new Color(0, 0, 0);

        Color putih = new Color(250, 250, 250);

        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
        //Document document = new Document(PageSize.A4.rotate(), 10, 10, 30, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int start = 0;

        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);

            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two.");

            String str = PstSystemProperty.getValueByName("PRINT_HEADER");

            //Header 
            HeaderFooter header = new HeaderFooter(new Phrase(str, fontHeaderSmall), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            HeaderFooter footer = new HeaderFooter(new Phrase("printed : " + Formater.formatDate(new Date(), "dd-MM-yyyy"), fontHeaderSmall), false);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);

            //images
             /* image logo */
            Image logo = null;

            String strPath = PstSystemProperty.getValueByName("IMGCACHE");

            System.out.println("root dan gambar =" + strPath + "" + employee.getEmployeeNum() + ".jpg");
            System.out.println("pictPath =" + pictPath);

            
            
            try {
                if (true) {
                    /* Update by Hendra Putu | 2015-11-19 | dataPath */
                    String[] dataPath = pictPath.split("/"); 
                    //1.menentukan path gambar dan gambarnya
                    logo = Image.getInstance(strPath + "" + dataPath[1]);
                    logo.scalePercent(95);
                    //logo.setWidthPercentage(40);
                    //posisi atau letak gambar yang diinginkan 
                    logo.setAbsolutePosition(100, 100);
                //  logo.setAlignment(Image.ALIGN_MIDDLE | Image.ALIGN_TOP);

                }
            } catch (Exception exc) {
                System.out.println(" ERROR @ InvoicePdf - upload image : \n" + exc.toString());
            }

            // step 3.4: opening the document
            document.open();

            //Personal Data

            int personalHeaderTop[] = {30, 2, 20, 5, 40, 5, 10, 5, 20};
            Table personTable = new Table(9);
            personTable.setWidth(100);
            personTable.setWidths(personalHeaderTop);
            personTable.setBorderColor(new Color(255, 255, 255));
            personTable.setBorderWidth(1);
            personTable.setAlignment(1);
            personTable.setCellpadding(0);
            personTable.setCellspacing(1);

            //0
            Cell titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

        if(showPersonalData){
            titleCellTop = new Cell(new Chunk("EMPLOYEE PERSONAL DATA ", fontHeaderBig));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setColspan(7);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(" ", tableContent));
            titleCellTop.setColspan(9);
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //1Employee Number
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setRowspan(7);
            try {
                titleCellTop.add(logo);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_TOP);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);


            titleCellTop = new Cell(new Chunk("", tableContent));
            //titleCellTop.add(logo);
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Employee Number", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(employee.getEmployeeNum(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space 

            //2 Nama
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Name", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(employee.getFullName(), tableContent));
            titleCellTop.setColspan(5);
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //3 Address
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Address", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + employee.getAddress(), tableContent));
            titleCellTop.setColspan(5);
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //4 Postal Code
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Postal Code", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + employee.getPostalCode(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space          

            //5 Gender
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Gender", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + PstEmployee.sexKey[employee.getSex()], tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space          

            //6 Place/DOB
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Place/DOB", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + employee.getBirthPlace() + "/" + "" + Formater.formatDate(employee.getBirthDate(), "dd-MM-yyyy"), tableContent));
            titleCellTop.setColspan(5);
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            //7 Phone
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Phone", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + employee.getPhone(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
                                    
            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space
            
            //7 Phone
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Mobile", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + employee.getHandphone(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space
            
            //7 Phone
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("Marital Status", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk(":", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);

            titleCellTop = new Cell(new Chunk("" + marital.getMaritalStatus() + " - " + marital.getNumOfChildren(), tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            //add space
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            
            titleCellTop = new Cell(new Chunk("", tableContent));
            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop.setBorderColor(new Color(255, 255, 255));
            personTable.addCell(titleCellTop);
            //end space
            
            document.add(personTable);

            //lanjutanya                         
            int personalHeader[] = {30, 2, 20, 5, 40, 5, 10, 5, 20};
            Table personTable2 = new Table(9);
            personTable2.setWidth(100);
            personTable2.setWidths(personalHeader);
            personTable2.setBorderColor(new Color(255, 255, 255));
            personTable2.setBorderWidth(0);
            personTable2.setAlignment(1);
            personTable2.setCellpadding(0);
            personTable2.setCellspacing(1);

            //8 Blood 
            Cell titleCellTop2 = new Cell(new Chunk("Join Date :", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("Jamsostek Number", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk(":", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("" + employee.getAstekNum(), tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            //add space
            titleCellTop2 = new Cell(new Chunk("", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("Religion", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk(":", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("" + religion.getReligion(), tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);
            //end space          

            //9 Marital Status
            titleCellTop2 = new Cell(new Chunk(Formater.formatDate(employee.getCommencingDate(), "dd-MM-yyyy"), tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("Jamsostek Date", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk(":", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);
            
            if (employee.getAstekDate() != null) {

                titleCellTop2 = new Cell(new Chunk(Formater.formatDate(employee.getAstekDate(), "dd-MM-yyyy"), tableContent));
                titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellTop2.setBorderColor(new Color(255, 255, 255));
                personTable2.addCell(titleCellTop2);

            } else {
                titleCellTop2 = new Cell(new Chunk("", tableContent));
                titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellTop2.setBorderColor(new Color(255, 255, 255));
                personTable2.addCell(titleCellTop2);
            }

            //add space
            titleCellTop2 = new Cell(new Chunk("", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("Blood", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk(":", tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);

            titleCellTop2 = new Cell(new Chunk("" + employee.getBloodType(), tableContent));
            titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellTop2.setBorderColor(new Color(255, 255, 255));
            personTable2.addCell(titleCellTop2);
            //end space          

            document.add(personTable2);
        }
        
        if(showLastCarrier){
            //LAST CARRIER
            int lastCarrierHeaderTop[] = {24, 5, 45, 5, 25, 5, 40};
            Table lastCarrierTable = new Table(7);
            lastCarrierTable.setWidth(100);
            lastCarrierTable.setWidths(lastCarrierHeaderTop);
            lastCarrierTable.setBorderColor(blackColor);
            lastCarrierTable.setBorderWidth(0);
            lastCarrierTable.setAlignment(1);
            lastCarrierTable.setCellpadding(0);
            lastCarrierTable.setCellspacing(1);


            Cell titleCellCarrierTop = new Cell(new Chunk(" ", tableContent));
            titleCellCarrierTop.setColspan(7);
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("Last Carrier ", fontContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setColspan(7);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(" ", tableContent));
            titleCellCarrierTop.setColspan(7);
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            Division div = new Division();
            try {
                div = PstDivision.fetchExc(employee.getDivisionId());
            } catch (Exception exx) {
                System.out.println(exx.toString());
            }

            //1Division
            titleCellCarrierTop = new Cell(new Chunk("Division", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(div.getDivision(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
            lastCarrierTable.addCell(titleCellCarrierTop);

            // add space         
            titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("Employee Category", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("" + empCategory.getEmpCategory(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // end space

            //2Department
            titleCellCarrierTop = new Cell(new Chunk("Department", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("" + department.getDepartment(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // add space         
            titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("Level", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("" + level.getLevel(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // end space

            //3 Section
            titleCellCarrierTop = new Cell(new Chunk("Section", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("" + section.getSection(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // add space         
            titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
            
            titleCellCarrierTop = new Cell(new Chunk("Grade", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
            
            titleCellCarrierTop = new Cell(new Chunk(gLvl.getCodeLevel(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // end space

            //4 Locker
           /* titleCellCarrierTop = new Cell(new Chunk("Locker", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            LockerLocation lock = new LockerLocation();
            try {
                lock = PstLockerLocation.fetchExc(employee.getLockerId());
            } catch (Exception ev) {
                System.out.println(ev.toString());
            }

            titleCellCarrierTop = new Cell(new Chunk("" + lock.getLocation(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
*/
            //5 Jamsostek Number
            titleCellCarrierTop = new Cell(new Chunk("Position", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk("" + position.getPosition(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
            
            titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
            
            //Lask SK
                        
            titleCellCarrierTop = new Cell(new Chunk("Last SK", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
            
            titleCellCarrierTop = new Cell(new Chunk("" + employee.getSkNomor(), tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
                        
            // work from
            titleCellCarrierTop = new Cell(new Chunk("Work From", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            String nextDate = "-";
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); /*new SimpleDateFormat("dd MMMM yyyy");*/
                Calendar c = Calendar.getInstance();
                c.setTime(lastCareer.getWorkTo());
                c.add(Calendar.DATE, 1);  // number of days to add
                nextDate = sdf.format(c.getTime());  // dt is now the new date
            } catch (Exception e) {
                System.out.println("Date=>" + e.toString());
            }
            
            titleCellCarrierTop = new Cell(new Chunk("" + nextDate, tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            // add space         
          /*  titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);
*/


            if (employee.getResigned() == 1) {

                titleCellCarrierTop = new Cell(new Chunk("Resign Reason", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                ResignedReason rrs = new ResignedReason();
                try {
                    rrs = PstResignedReason.fetchExc(employee.getResignedReasonId());
                } catch (Exception exc) {
                    System.out.println(exc.toString());
                }

                titleCellCarrierTop = new Cell(new Chunk(rrs.getResignedReason(), tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

            } else {

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);
            }

            // end space


            // add space         
            titleCellCarrierTop = new Cell(new Chunk("", tableContent));
            titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
            lastCarrierTable.addCell(titleCellCarrierTop);

            if (employee.getResigned() == 1) {

                titleCellCarrierTop = new Cell(new Chunk("Resign Description", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk(employee.getResignedDesc(), tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

            } else {

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);

                titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                lastCarrierTable.addCell(titleCellCarrierTop);
            }

            // end space

            document.add(lastCarrierTable);
        }
        
        if(showCarrierPath){
            //CARRIER PATH

            //Header Carrier Path
            if (vCareer != null && vCareer.size() > 0) {

                int careerHeader[] = {10, 10, 10, 10, 10, 10,10};
                Table careerTableH = new Table(7);
                careerTableH.setWidth(100);
                careerTableH.setWidths(careerHeader);
                careerTableH.setBorderColor(new Color(255, 255, 255));
                careerTableH.setBorderWidth(1);
                careerTableH.setAlignment(1);
                careerTableH.setCellpadding(0);
                careerTableH.setCellspacing(1);


                //0(Experience Header)
                Cell titleCareerCell = new Cell(new Chunk("", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(7);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                titleCareerCell = new Cell(new Chunk("Carrier Path : ", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(7);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                document.add(careerTableH);
            }

            if (vCareer != null && vCareer.size() > 0) {

                //membuat table Career Profile
                int careerHeaderTop[] = {10, 10, 10, 10, 10, 10, 10, 10};
                Table careerTable = new Table(8);
                careerTable.setWidth(100);
                careerTable.setWidths(careerHeaderTop);
                careerTable.setBorderColor(new Color(255, 255, 255));
                careerTable.setBorderWidth(1);
                careerTable.setAlignment(1);
                careerTable.setCellpadding(0);
                careerTable.setCellspacing(1);

                //1
                Cell titleCareerCellTop = new Cell(new Chunk("Nomor SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Tanggal SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Department", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Section", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Position", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Level", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Work From", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

              /*  titleCareerCellTop = new Cell(new Chunk("Work To", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);*/

                titleCareerCellTop = new Cell(new Chunk("Description", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                //value-valuenya
                for (int i = 0; i < vCareer.size(); i++) {
                    CareerPath cr = (CareerPath) vCareer.get(i);

                    //1
                    
                    titleCareerCellTop = new Cell(new Chunk((cr.getNomorSk()==null) ? "" : cr.getNomorSk(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                  /*  int digit = cr.getDepartment().length();
                    String potS = cr.getDepartment();
                    if(digit > 20){
                        potS = potS.substring(0,20);
                        potS = potS + ".....";
                    }*/
                    titleCareerCellTop = new Cell(new Chunk((cr.getTanggalSk() == null) ? "" : "" + Formater.formatDate(cr.getTanggalSk(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                    titleCareerCellTop = new Cell(new Chunk(cr.getDepartment(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk(cr.getSection(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk(cr.getPosition(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                    titleCareerCellTop = new Cell(new Chunk(cr.getLevel(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk((cr.getWorkFrom() == null) ? "" : "" + Formater.formatDate(cr.getWorkFrom(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                  /*  titleCareerCellTop = new Cell(new Chunk((cr.getWorkTo() == null) ? "" : "" + Formater.formatDate(cr.getWorkTo(), "dd MMMM yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);*/

                    titleCareerCellTop = new Cell(new Chunk(cr.getDescription(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                }

                document.add(careerTable);
            }
        
        }
        
        if(showPenugasan){
            //Penugasan
            if (pjsCareer != null && pjsCareer.size() > 0) {

                int careerHeader[] = {10, 10, 10, 10, 10, 10,10};
                Table careerTableH = new Table(7);
                careerTableH.setWidth(100);
                careerTableH.setWidths(careerHeader);
                careerTableH.setBorderColor(new Color(255, 255, 255));
                careerTableH.setBorderWidth(1);
                careerTableH.setAlignment(1);
                careerTableH.setCellpadding(0);
                careerTableH.setCellspacing(1);


                //0(Experience Header)
                Cell titleCareerCell = new Cell(new Chunk("", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(7);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                titleCareerCell = new Cell(new Chunk("Penugasan : ", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(7);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                document.add(careerTableH);
            }

            if (pjsCareer != null && pjsCareer.size() > 0) {

                //membuat table Career Profile
                int careerHeaderTop[] = {10, 10, 10, 10, 10, 10, 10, 10};
                Table careerTable = new Table(8);
                careerTable.setWidth(100);
                careerTable.setWidths(careerHeaderTop);
                careerTable.setBorderColor(new Color(255, 255, 255));
                careerTable.setBorderWidth(1);
                careerTable.setAlignment(1);
                careerTable.setCellpadding(0);
                careerTable.setCellspacing(1);

                //1
                Cell titleCareerCellTop = new Cell(new Chunk("Nomor SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Tanggal SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Department", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Section", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Position", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Level", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Work From", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                titleCareerCellTop = new Cell(new Chunk("Work To", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                /*titleCareerCellTop = new Cell(new Chunk("Description", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);*/

                //value-valuenya
                for (int i = 0; i < pjsCareer.size(); i++) {
                    CareerPath cr = (CareerPath) pjsCareer.get(i);

                    //1
                    
                    titleCareerCellTop = new Cell(new Chunk((cr.getNomorSk()==null) ? "" : cr.getNomorSk(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                  /*  int digit = cr.getDepartment().length();
                    String potS = cr.getDepartment();
                    if(digit > 20){
                        potS = potS.substring(0,20);
                        potS = potS + ".....";
                    }*/
                    titleCareerCellTop = new Cell(new Chunk((cr.getTanggalSk() == null) ? "" : "" + Formater.formatDate(cr.getTanggalSk(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                    titleCareerCellTop = new Cell(new Chunk(cr.getDepartment(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk(cr.getSection(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk(cr.getPosition(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                    titleCareerCellTop = new Cell(new Chunk(cr.getLevel(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk((cr.getWorkFrom() == null) ? "" : "" + Formater.formatDate(cr.getWorkFrom(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk((cr.getWorkTo() == null) ? "" : "" + Formater.formatDate(cr.getWorkTo(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                  /*  titleCareerCellTop = new Cell(new Chunk(cr.getDescription(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop); */

                }

                document.add(careerTable);
            }      
        }
        if(showGradeHistory){
            if (gradeCareer != null && gradeCareer.size() > 0) {

                int careerHeader[] = {10, 10, 10, 10, 10, 10};
                Table careerTableH = new Table(6);
                careerTableH.setWidth(100);
                careerTableH.setWidths(careerHeader);
                careerTableH.setBorderColor(new Color(255, 255, 255));
                careerTableH.setBorderWidth(1);
                careerTableH.setAlignment(1);
                careerTableH.setCellpadding(0);
                careerTableH.setCellspacing(1);


                //0(Experience Header)
                Cell titleCareerCell = new Cell(new Chunk("", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(6);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                titleCareerCell = new Cell(new Chunk("Grade History : ", fontHeader));
                titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCell.setColspan(6);
                titleCareerCell.setBorderColor(new Color(255, 255, 255));
                careerTableH.addCell(titleCareerCell);

                document.add(careerTableH);
            }
        
        
        
            if (gradeCareer != null && gradeCareer.size() > 0) {

                //membuat table Career Profile
                int careerHeaderTop[] = {10, 10, 10, 10};
                Table careerTable = new Table(4);
                careerTable.setWidth(100);
                careerTable.setWidths(careerHeaderTop);
                careerTable.setBorderColor(new Color(255, 255, 255));
                careerTable.setBorderWidth(1);
                careerTable.setAlignment(1);
                careerTable.setCellpadding(0);
                careerTable.setCellspacing(1);

                //1
                Cell titleCareerCellTop = new Cell(new Chunk("Nomor SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Tanggal SK", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Grade", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);
                
                titleCareerCellTop = new Cell(new Chunk("Work From", fontHeader));
                titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                titleCareerCellTop.setBackgroundColor(bgColor);
                careerTable.addCell(titleCareerCellTop);

                //value-valuenya
                for (int i = 0; i < gradeCareer.size(); i++) {
                    CareerPath cr = (CareerPath) gradeCareer.get(i);

                    //1
                    
                    titleCareerCellTop = new Cell(new Chunk((cr.getNomorSk()==null) ? "" : cr.getNomorSk(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                  /*  int digit = cr.getDepartment().length();
                    String potS = cr.getDepartment();
                    if(digit > 20){
                        potS = potS.substring(0,20);
                        potS = potS + ".....";
                    }*/
                    titleCareerCellTop = new Cell(new Chunk((cr.getTanggalSk() == null) ? "" : "" + Formater.formatDate(cr.getTanggalSk(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);
                    
                    try{
                        gd =PstGradeLevel.fetchExc(cr.getGradeLevelId());
                    }catch(Exception ec){
                        
                    }
                    
                    titleCareerCellTop = new Cell(new Chunk(""+gd.getCodeLevel(), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                    titleCareerCellTop = new Cell(new Chunk((cr.getWorkFrom() == null) ? "" : "" + Formater.formatDate(cr.getWorkFrom(), "dd-MM-yyyy"), tableContent));
                    titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                    careerTable.addCell(titleCareerCellTop);

                }

                document.add(careerTable);
            }
        }
        
        if(showFamilyMember){
            //Family Member

            //Header Family Member
            if (vFamily != null && vFamily.size() > 0) {

                int familyHeader[] = {10, 10, 10, 10, 10, 10, 10};
                Table familyTableH = new Table(7);
                familyTableH.setWidth(100);
                familyTableH.setWidths(familyHeader);
                familyTableH.setBorderColor(new Color(255, 255, 255));
                familyTableH.setBorderWidth(1);
                familyTableH.setAlignment(1);
                familyTableH.setCellpadding(0);
                familyTableH.setCellspacing(1);


                //0(Family Header)
                Cell titleFamilyCell = new Cell(new Chunk("", fontHeader));
                titleFamilyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCell.setColspan(7);
                titleFamilyCell.setBorderColor(new Color(255, 255, 255));
                familyTableH.addCell(titleFamilyCell);

                titleFamilyCell = new Cell(new Chunk("Family Member : ", fontHeader));
                titleFamilyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCell.setColspan(7);
                titleFamilyCell.setBorderColor(new Color(255, 255, 255));
                familyTableH.addCell(titleFamilyCell);

                document.add(familyTableH);
            }

            if (vFamily != null && vFamily.size() > 0) {

                //membuat table Family Member
                int familyHeaderTop[] = {10, 10, 10, 10, 10, 10, 10};
                Table familyTable = new Table(7);
                familyTable.setWidth(100);
                familyTable.setWidths(familyHeaderTop);
                familyTable.setBorderColor(new Color(255, 255, 255));
                familyTable.setBorderWidth(1);
                familyTable.setAlignment(1);
                familyTable.setCellpadding(0);
                familyTable.setCellspacing(1);

                //1
                Cell titleFamilyCellTop = new Cell(new Chunk("Nama", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);

                titleFamilyCellTop = new Cell(new Chunk("Pendidikan", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);
                
                titleFamilyCellTop = new Cell(new Chunk("Status", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);
/*
                titleFamilyCellTop = new Cell(new Chunk("Guaranteed", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);
*/
                titleFamilyCellTop = new Cell(new Chunk("Tgl. Lahir", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);

                titleFamilyCellTop = new Cell(new Chunk("Pekerjaan", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);

                titleFamilyCellTop = new Cell(new Chunk("Alamat", fontHeader));
                titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleFamilyCellTop.setColspan(2);
                titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                titleFamilyCellTop.setBackgroundColor(bgColor);
                familyTable.addCell(titleFamilyCellTop);

                //value-valuenya
                for (int i = 0; i < vFamily.size(); i++) {
                    FamilyMember faml = (FamilyMember) vFamily.get(i);

                    //1
                    titleFamilyCellTop = new Cell(new Chunk(faml.getFullName(), tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);
                    String eduFamS = "";
                    
                    try {
                        Education eduFam = PstEducation.fetchExc(faml.getEducationId());
                        eduFamS = eduFam.getEducation();
                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                    
                    titleFamilyCellTop = new Cell(new Chunk(eduFamS, tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);

                   /* String grd = "";
                    if (faml.getGuaranteed() == true) {
                        grd = "Yes";
                    } else {
                        grd = "No";
                    }

                    titleFamilyCellTop = new Cell(new Chunk(grd, tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);
*/
                    String famRelation = "";
                    try {
                        FamRelation famRel = PstFamRelation.fetchExc(Long.valueOf(faml.getRelationship()));
                        famRelation = famRel.getFamRelation();
                    } catch (Exception e){
                        System.out.println(e.toString());
                    }
                    
                    titleFamilyCellTop = new Cell(new Chunk(famRelation, tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);
                    
                    titleFamilyCellTop = new Cell(new Chunk((faml.getBirthDate() == null) ? "" : "" + Formater.formatDate(faml.getBirthDate(), "dd-MM-yyyy"), tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);

                    titleFamilyCellTop = new Cell(new Chunk(faml.getJob(), tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);

                    titleFamilyCellTop = new Cell(new Chunk(faml.getAddress(), tableContent));
                    titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleFamilyCellTop.setColspan(2);
                    titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                    familyTable.addCell(titleFamilyCellTop);

                }

                document.add(familyTable);
            }

        }
        
        if(showLanguage){
            //Language

            //Header Language
            if (vLan != null && vLan.size() > 0) {

                int languageHeader[] = {10, 10, 10, 10, 10, 10};
                Table languageTableH = new Table(6);
                languageTableH.setWidth(100);
                languageTableH.setWidths(languageHeader);
                languageTableH.setBorderColor(new Color(255, 255, 255));
                languageTableH.setBorderWidth(1);
                languageTableH.setAlignment(1);
                languageTableH.setCellpadding(0);
                languageTableH.setCellspacing(1);


                //0(Language)
                Cell titleLanguageCell = new Cell(new Chunk("", fontHeader));
                titleLanguageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCell.setColspan(6);
                titleLanguageCell.setBorderColor(new Color(255, 255, 255));
                languageTableH.addCell(titleLanguageCell);

                titleLanguageCell = new Cell(new Chunk("Language : ", fontHeader));
                titleLanguageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCell.setColspan(6);
                titleLanguageCell.setBorderColor(new Color(255, 255, 255));
                languageTableH.addCell(titleLanguageCell);

                document.add(languageTableH);
            }

            if (vLan != null && vLan.size() > 0) {

                //membuat table Language
                int languageHeaderTop[] = {10, 10, 10, 10, 10, 10};
                Table languageTable = new Table(6);
                languageTable.setWidth(100);
                languageTable.setWidths(languageHeaderTop);
                languageTable.setBorderColor(new Color(255, 255, 255));
                languageTable.setBorderWidth(1);
                languageTable.setAlignment(1);
                languageTable.setCellpadding(0);
                languageTable.setCellspacing(1);

                //1
                Cell titleLanguageCellTop = new Cell(new Chunk("Language", fontHeader));
                titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                titleLanguageCellTop.setBackgroundColor(bgColor);
                languageTable.addCell(titleLanguageCellTop);

                titleLanguageCellTop = new Cell(new Chunk("Oral", fontHeader));
                titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                titleLanguageCellTop.setBackgroundColor(bgColor);
                languageTable.addCell(titleLanguageCellTop);

                titleLanguageCellTop = new Cell(new Chunk("Writen", fontHeader));
                titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                titleLanguageCellTop.setBackgroundColor(bgColor);
                languageTable.addCell(titleLanguageCellTop);

                titleLanguageCellTop = new Cell(new Chunk("Description", fontHeader));
                titleLanguageCellTop.setColspan(3);
                titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                titleLanguageCellTop.setBackgroundColor(bgColor);
                languageTable.addCell(titleLanguageCellTop);

                //value-valuenya
                for (int i = 0; i < vLan.size(); i++) {
                    EmpLanguage lan = (EmpLanguage) vLan.get(i);

                    //1
                    Language lang = new Language();
                    try {
                        lang = PstLanguage.fetchExc(lan.getLanguageId());
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }

                    titleLanguageCellTop = new Cell(new Chunk(lang.getLanguage(), tableContent));
                    titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                    languageTable.addCell(titleLanguageCellTop);

                    String oral = "";
                    switch(lan.getOral()){
                        case 0: oral = "Not Selected"; break;
                        case 1: oral = "Good"; break;
                        case 2: oral = "Fair"; break;
                        case 3: oral = "Poor"; break;
                    }

                    titleLanguageCellTop = new Cell(new Chunk(oral, tableContent));
                    titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                    languageTable.addCell(titleLanguageCellTop);

                    String writen = "";
                    switch(lan.getWritten()){
                        case 0: writen = "Not Selected"; break;
                        case 1: writen = "Good"; break;
                        case 2: writen = "Fair"; break;
                        case 3: writen = "Poor"; break;
                    }

                    titleLanguageCellTop = new Cell(new Chunk(writen, tableContent));
                    titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                    languageTable.addCell(titleLanguageCellTop);

                    titleLanguageCellTop = new Cell(new Chunk(lan.getDescription(), tableContent));
                    titleLanguageCellTop.setColspan(3);
                    titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                    languageTable.addCell(titleLanguageCellTop);

                }

                document.add(languageTable);
            }
        }
        if(showEducation){
            //EDUCATION 

            //Header Education
            if (vEdu != null && vEdu.size() > 0) {

                int educationHeader[] = {10, 10, 10, 10, 10, 10};
                Table educationTableH = new Table(6);
                educationTableH.setWidth(100);
                educationTableH.setWidths(educationHeader);
                educationTableH.setBorderColor(new Color(255, 255, 255));
                educationTableH.setBorderWidth(1);
                educationTableH.setAlignment(1);
                educationTableH.setCellpadding(0);
                educationTableH.setCellspacing(1);


                //0(Family Header)
                Cell titleEducationCell = new Cell(new Chunk("", fontHeader));
                titleEducationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCell.setColspan(6);
                titleEducationCell.setBorderColor(new Color(255, 255, 255));
                educationTableH.addCell(titleEducationCell);

                titleEducationCell = new Cell(new Chunk("Education : ", fontHeader));
                titleEducationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCell.setColspan(6);
                titleEducationCell.setBorderColor(new Color(255, 255, 255));
                educationTableH.addCell(titleEducationCell);

                document.add(educationTableH);
            }

            if (vEdu != null && vEdu.size() > 0) {

                //membuat table Education
                int familyEducationTop[] = {10, 10, 10, 10, 10, 10};
                Table educationTable = new Table(6);
                educationTable.setWidth(100);
                educationTable.setWidths(familyEducationTop);
                educationTable.setBorderColor(new Color(255, 255, 255));
                educationTable.setBorderWidth(1);
                educationTable.setAlignment(1);
                educationTable.setCellpadding(0);
                educationTable.setCellspacing(1);

                //1
                Cell titleEducationCellTop = new Cell(new Chunk("Level", fontHeader));
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);
                
                titleEducationCellTop = new Cell(new Chunk("Institusi/Sekolah", fontHeader));
                //titleEducationCellTop.setColspan(2);
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);

                titleEducationCellTop = new Cell(new Chunk("Description", fontHeader));
                //titleEducationCellTop.setColspan(2);
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);

                titleEducationCellTop = new Cell(new Chunk("Start Date", fontHeader));
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);

                titleEducationCellTop = new Cell(new Chunk("End Date", fontHeader));
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);

                titleEducationCellTop = new Cell(new Chunk("Graduation", fontHeader));
                titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                titleEducationCellTop.setBackgroundColor(bgColor);
                educationTable.addCell(titleEducationCellTop);

                //value-valuenya
                for (int i = 0; i < vEdu.size(); i++) {
                    EmpEducation edu = (EmpEducation) vEdu.get(i);

                    Education education = new Education();
                    if (edu.getEducationId() != 0) {
                        try {
                            education = PstEducation.fetchExc(edu.getEducationId());
                        } catch (Exception exc) {
                            education = new Education();
                        }
                    }

                    //1
                    titleEducationCellTop = new Cell(new Chunk(String.valueOf(education.getEducation()), tableContent));
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);
                    
                    String comName = "";
                    
                    try {
                        Vector listContact = PstContactList.list(0, 0, "contact_id="+edu.getInstitutionId(), "");
                        if(edu.getInstitutionId()!=0 && listContact.size()>0) {
                            ContactList conList = (ContactList)listContact.get(0);
                            comName = conList.getCompName();
                        }
                    } catch(Exception e) {
                    }

                    titleEducationCellTop = new Cell(new Chunk(comName, tableContent));
                    //titleEducationCellTop.setColspan(2);
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);

                    titleEducationCellTop = new Cell(new Chunk(edu.getEducationDesc(), tableContent));
                    //titleEducationCellTop.setColspan(2);
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);

                    titleEducationCellTop = new Cell(new Chunk(String.valueOf(edu.getStartDate()), tableContent));
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);

                    titleEducationCellTop = new Cell(new Chunk(String.valueOf(edu.getEndDate()), tableContent));
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);

                    titleEducationCellTop = new Cell(new Chunk(edu.getGraduation(), tableContent));
                    titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                    educationTable.addCell(titleEducationCellTop);

                }

                document.add(educationTable);
            }
            
        }
        if(showExprience){
            //EXPERIENCE 

            //Header Experience
            if (vExp != null && vExp.size() > 0) {

                int experienceHeader[] = {10, 10, 10, 10, 10, 10, 10};
                Table experienceTableH = new Table(7);
                experienceTableH.setWidth(100);
                experienceTableH.setWidths(experienceHeader);
                experienceTableH.setBorderColor(new Color(255, 255, 255));
                experienceTableH.setBorderWidth(1);
                experienceTableH.setAlignment(1);
                experienceTableH.setCellpadding(0);
                experienceTableH.setCellspacing(1);


                //0(Experience Header)
                Cell titleExperienceCell = new Cell(new Chunk("", fontHeader));
                titleExperienceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperienceCell.setColspan(7);
                titleExperienceCell.setBorderColor(new Color(255, 255, 255));
                experienceTableH.addCell(titleExperienceCell);

                titleExperienceCell = new Cell(new Chunk("Experience : ", fontHeader));
                titleExperienceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperienceCell.setColspan(7);
                titleExperienceCell.setBorderColor(new Color(255, 255, 255));
                experienceTableH.addCell(titleExperienceCell);

                document.add(experienceTableH);
            }

            if (vExp != null && vExp.size() > 0) {

                //membuat table Experience
                int familyExperienceTop[] = {10, 10, 10, 10, 10, 10, 10};
                Table exprienceTable = new Table(7);
                exprienceTable.setWidth(100);
                exprienceTable.setWidths(familyExperienceTop);
                exprienceTable.setBorderColor(new Color(255, 255, 255));
                exprienceTable.setBorderWidth(1);
                exprienceTable.setAlignment(1);
                exprienceTable.setCellpadding(0);
                exprienceTable.setCellspacing(1);

                //1
                Cell titleExperieceCellTop = new Cell(new Chunk("Company Name", fontHeader));
                titleExperieceCellTop.setColspan(2);
                titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                titleExperieceCellTop.setBackgroundColor(bgColor);
                exprienceTable.addCell(titleExperieceCellTop);

                titleExperieceCellTop = new Cell(new Chunk("Start Date", fontHeader));
                titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                titleExperieceCellTop.setBackgroundColor(bgColor);
                exprienceTable.addCell(titleExperieceCellTop);

                titleExperieceCellTop = new Cell(new Chunk("End Date", fontHeader));
                titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                titleExperieceCellTop.setBackgroundColor(bgColor);
                exprienceTable.addCell(titleExperieceCellTop);

                titleExperieceCellTop = new Cell(new Chunk("Position", fontHeader));
                titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                titleExperieceCellTop.setBackgroundColor(bgColor);
                exprienceTable.addCell(titleExperieceCellTop);

                titleExperieceCellTop = new Cell(new Chunk("Move Reason", fontHeader));
                titleExperieceCellTop.setColspan(2);
                titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                titleExperieceCellTop.setBackgroundColor(bgColor);
                exprienceTable.addCell(titleExperieceCellTop);

                //value-valuenya
                for (int i = 0; i < vExp.size(); i++) {
                    Experience ex = (Experience) vExp.get(i);

                    //1
                    titleExperieceCellTop = new Cell(new Chunk(ex.getCompanyName(), tableContent));
                    titleExperieceCellTop.setColspan(2);
                    titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                    exprienceTable.addCell(titleExperieceCellTop);

                    titleExperieceCellTop = new Cell(new Chunk(String.valueOf(ex.getStartDate()), tableContent));
                    titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                    exprienceTable.addCell(titleExperieceCellTop);

                    titleExperieceCellTop = new Cell(new Chunk(String.valueOf(ex.getEndDate()), tableContent));
                    titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                    exprienceTable.addCell(titleExperieceCellTop);

                    titleExperieceCellTop = new Cell(new Chunk(ex.getPosition(), tableContent));
                    titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                    exprienceTable.addCell(titleExperieceCellTop);

                    titleExperieceCellTop = new Cell(new Chunk(ex.getMoveReason(), tableContent));
                    titleExperieceCellTop.setColspan(2);
                    titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                    exprienceTable.addCell(titleExperieceCellTop);

                }

                document.add(exprienceTable);
            }
        }
        
        if(showTrainingHistory){

            //TRANING HISTORY 

            //Header Traning
            if (vTraning != null && vTraning.size() > 0) {

                int traningHeader[] = {10, 10, 10, 10, 10, 10};
                Table traningTableH = new Table(6);
                traningTableH.setWidth(100);
                traningTableH.setWidths(traningHeader);
                traningTableH.setBorderColor(new Color(255, 255, 255));
                traningTableH.setBorderWidth(1);
                traningTableH.setAlignment(1);
                traningTableH.setCellpadding(0);
                traningTableH.setCellspacing(1);


                //0(Traning Header)
                Cell titleTraningCell = new Cell(new Chunk("", fontHeader));
                titleTraningCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTraningCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTraningCell.setColspan(6);
                titleTraningCell.setBorderColor(new Color(255, 255, 255));
                traningTableH.addCell(titleTraningCell);

                titleTraningCell = new Cell(new Chunk("Traning History : ", fontHeader));
                titleTraningCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTraningCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTraningCell.setColspan(6);
                titleTraningCell.setBorderColor(new Color(255, 255, 255));
                traningTableH.addCell(titleTraningCell);

                document.add(traningTableH);
            }

            if (vTraning != null && vTraning.size() > 0) {
                String prevProgram = "";
                for (int i = 0; i < vTraning.size(); i++) {
                    TrainingHistory training = (TrainingHistory) vTraning.get(i);

                    Training trn1 = new Training();
                    try {
                        trn1 = PstTraining.fetchExc(training.getTrainingId());
                    } catch (Exception e) {
                        trn1 = new Training();
                    }


                    int digitNam = trn1.getName().length();
                    String potNam = trn1.getName();
                    if(digitNam > 30){
                        potNam = potNam.substring(0,30);
                        potNam = potNam + ".....";
                    }
                    
                    int digitTit = training.getTrainingTitle().length();
                    String potTit = training.getTrainingTitle();
                    if(digitTit > 30){
                        potTit = potTit.substring(0,30);
                        potTit = potTit + ".....";
                    }
                    
                    //membuat table Traning
                    int familyTraningTop[] = {10, 10, 10};
                    Table traningTable = new Table(3);
                    traningTable.setWidth(100);
                    traningTable.setWidths(familyTraningTop);
                    traningTable.setBorderColor(new Color(255, 255, 255));
                    traningTable.setBorderWidth(1);
                    traningTable.setAlignment(1);
                    traningTable.setCellpadding(0);
                    traningTable.setCellspacing(1);

                    //1
                    Cell titleTraningCellTop = new Cell();
                    if (!prevProgram.equals(training.getTrainingProgram())){
                        titleTraningCellTop = new Cell(new Chunk("Training Program : "+trn1.getName(), fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        titleTraningCellTop.setColspan(3);
                        traningTable.addCell(titleTraningCellTop);
                    }

                    titleTraningCellTop = new Cell(new Chunk("     Training Title : "+training.getTrainingTitle(), fontHeader));
                    titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                    titleTraningCellTop.setBackgroundColor(bgColor);
                    titleTraningCellTop.setColspan(3);
                    traningTable.addCell(titleTraningCellTop);
                    document.add(traningTable);
                    
                    int familyTraningTop2[] = {10, 10, 10};
                    Table traningTable2 = new Table(3);
                    traningTable2.setWidth(100);
                    traningTable2.setWidths(familyTraningTop);
                    traningTable2.setBorderColor(new Color(255, 255, 255));
                    traningTable2.setBorderWidth(1);
                    traningTable2.setAlignment(1);
                    traningTable2.setCellpadding(0);
                    traningTable2.setCellspacing(1);

                    Cell titleTraningCellTop2 = new Cell(new Chunk("          Period", fontHeader));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    titleTraningCellTop2.setBackgroundColor(bgColor);
                    traningTable2.addCell(titleTraningCellTop2);

                    titleTraningCellTop2 = new Cell(new Chunk("Duration", fontHeader));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    titleTraningCellTop2.setBackgroundColor(bgColor);
                    traningTable2.addCell(titleTraningCellTop2);

                    titleTraningCellTop2 = new Cell(new Chunk("Trainer", fontHeader));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    titleTraningCellTop2.setBackgroundColor(bgColor);
                    traningTable2.addCell(titleTraningCellTop2);
                    
                    //value-valuenya
                    titleTraningCellTop2 = new Cell(new Chunk("          "+Formater.formatDate(training.getStartDate(), "dd-MM-yyyy") + " // " + Formater.formatDate(training.getEndDate(), "dd-MM-yyyy"), tableContent));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    traningTable2.addCell(titleTraningCellTop2);

                    titleTraningCellTop2 = new Cell(new Chunk(SessTraining.getDurationString(training.getDuration()), tableContent));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    traningTable2.addCell(titleTraningCellTop2);

                    titleTraningCellTop2 = new Cell(new Chunk(training.getTrainer(), tableContent));
                    titleTraningCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleTraningCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleTraningCellTop2.setBorderColor(new Color(255, 255, 255));
                    traningTable2.addCell(titleTraningCellTop2);

                    document.add(traningTable2); 
                    
                    prevProgram = training.getTrainingProgram();
                }
            }
        }
        
        if(showWarning){
        
            if (vWarning != null && vWarning.size() > 0) {
                //membuat table title Warning
                int warningTop[] = {1};
                Table dataTable = new Table(1);
                dataTable.setWidth(100);
                dataTable.setWidths(warningTop);
                dataTable.setBorderColor(new Color(255, 255, 255));
                dataTable.setBorderWidth(1);
                dataTable.setAlignment(1);
                dataTable.setCellpadding(0);
                dataTable.setCellspacing(1);

                /*
                Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);
                 * */

                /* Cell titleTableTop = new Cell(new Chunk("Warning History", fontHeader)); // diganti menjadi Pembinaan */ 
                Cell titleTableTop = new Cell(new Chunk("Sanksi", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);
                
                document.add(dataTable);                
              }
            
            if (vWarning != null && vWarning.size() > 0) {
                //membuat table Traning
                int warningTop[] = {5, 8, 10, 17, 8};
                Table dataTable = new Table(5);
                dataTable.setWidth(100);
                dataTable.setWidths(warningTop);
                dataTable.setBorderColor(new Color(255, 255, 255));
                dataTable.setBorderWidth(1);
                dataTable.setAlignment(1);
                dataTable.setCellpadding(0);
                dataTable.setCellspacing(1);

                //1
                Cell titleWarningCellTop = new Cell(new Chunk("Tanggal", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);
                /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                titleWarningCellTop = new Cell(new Chunk("Break Fact", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);
                */
                titleWarningCellTop = new Cell(new Chunk("Berlaku Sampai", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);

                titleWarningCellTop = new Cell(new Chunk("Diberikan Oleh", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);                
                
                titleWarningCellTop = new Cell(new Chunk("Deskripsi", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);
                
                titleWarningCellTop = new Cell(new Chunk("Level", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);


                /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                titleWarningCellTop = new Cell(new Chunk("Warning Level", fontHeader));
                titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                titleWarningCellTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleWarningCellTop);
                */
                //value-valuenya
                for (int i = 0; i < vWarning.size(); i++) {
                    EmpWarning wrn = (EmpWarning) vWarning.get(i);

                    titleWarningCellTop = new Cell(new Chunk(Formater.formatDate(wrn.getWarningDate(), "dd-MM-yyyy"), tableContent));
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);
                    /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                    titleWarningCellTop = new Cell(new Chunk("" + wrn.getBreakFact(), tableContent));
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);
                    */
                    titleWarningCellTop = new Cell(new Chunk(Formater.formatDate(wrn.getValidityDate(), "dd-MM-yyyy"), tableContent));
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);

                    titleWarningCellTop = new Cell(new Chunk(wrn.getWarningBy(), tableContent));
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);

                    
                    int digitDesc = wrn.getBreakFact().length();
                    String potDesc = wrn.getBreakFact();
                    if(digitDesc > 300){
                        potDesc = potDesc.substring(0,300);
                        potDesc = potDesc + ".....";
                    }
                    titleWarningCellTop = new Cell(new Chunk(potDesc, tableContent));
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);
                    
                    Vector vLvlName = new Vector();
                    vLvlName = PstWarning.list(0,0,"WARN_ID="+wrn.getWarnLevelId(),"");
                    Warning warn = (Warning)vLvlName.get(0);
                    titleCellTop = new Cell(new Chunk("" + warn.getWarnDesc(), tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleCellTop);

                    //titleWarningCellTop = new Cell(new Chunk(PstEmpWarning.levelNames[wrn.getWarnLevel()], tableContent));
                    //titleWarningCellTop = new Cell(new Chunk(PstEmpWarning.levelNames[wrn.getWarnLevelId()], tableContent));
                    /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                    titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                    dataTable.addCell(titleWarningCellTop);*/

                }
            

                document.add(dataTable);
            }
            
        }
        
        if(showReprimand){
                // Print Reprimand
            if (vReprimand != null && vReprimand.size() > 0) {
                //membuat table title Warning
                int warningTop[] = {10};
                Table dataTable = new Table(1);
                dataTable.setWidth(100);
                dataTable.setWidths(warningTop);
                dataTable.setBorderColor(new Color(255, 255, 255));
                dataTable.setBorderWidth(1);
                dataTable.setAlignment(1);
                dataTable.setCellpadding(0);
                dataTable.setCellspacing(1);

                //1
                /*
                Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);
                 * */
                /* Update by Hendra Putu | Reprimand History diganti menjadi Peringatan
                Cell titleTableTop = new Cell(new Chunk("Reprimand History", fontHeader));
                */
                Cell titleTableTop = new Cell(new Chunk("Pembinaan", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);
                
                document.add(dataTable);                
              }
            
            
                if (vReprimand != null && vReprimand.size() > 0) {
                    //membuat table 
                    int dataTop[] = {5, 8, 12, 17,8};
                    Table dataTable = new Table(5);
                    dataTable.setWidth(100);
                    dataTable.setWidths(dataTop);
                    dataTable.setBorderColor(new Color(255, 255, 255));
                    dataTable.setBorderWidth(1);
                    dataTable.setAlignment(1);
                    dataTable.setCellpadding(0);
                    dataTable.setCellspacing(1);

                    //1
                    titleCellTop = new Cell(new Chunk("Tanggal", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);
                    
                    titleCellTop = new Cell(new Chunk("Berlaku Sampai", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Satuan Kerja", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);                    
                    
                    titleCellTop = new Cell(new Chunk("Deskripsi", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

//                    titleCellTop = new Cell(new Chunk("Article", fontHeader));
//                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    titleCellTop.setBorderColor(new Color(255, 255, 255));
//                    titleCellTop.setBackgroundColor(bgColor);
//                    dataTable.addCell(titleCellTop);
//
//                    titleCellTop = new Cell(new Chunk("Page", fontHeader));
//                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                    titleCellTop.setBorderColor(new Color(255, 255, 255));
//                    titleCellTop.setBackgroundColor(bgColor);
//                    dataTable.addCell(titleCellTop);
                    /* Update by Hendra Putu | 2015-11-04 | Description is not show
                    titleCellTop = new Cell(new Chunk("Description", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);
                    */
                    

                    titleCellTop = new Cell(new Chunk("Level", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    //value-valuenya
                    for (int i = 0; i < vReprimand.size(); i++) {
                        EmpReprimand rpm = (EmpReprimand) vReprimand.get(i);

                        titleCellTop = new Cell(new Chunk(Formater.formatDate(rpm.getReprimandDate(), "dd-MM-yyyy"), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);
                        
//                        Vector vChapter = new Vector();
//                        String warnBy = "";
//                        long empId = rpm.get;
//                        if(chapter != 0 ){
//                            vChapter = PstWarningReprimandBab.list(0,0,"BAB_ID="+rpm.getDes(),"");
//                            WarningReprimandBab warningChapter = (WarningReprimandBab)vChapter.get(i);
//                            
//                            babTitle = warningChapter.getBabTitle();
//                        }
                        
                        titleCellTop = new Cell(new Chunk(Formater.formatDate(rpm.getValidityDate(), "dd-MM-yyyy"), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);
                        
                        Vector vDivision = new Vector();
                        String divName = "";
                        Employee emp = new Employee();
                        try {
                            emp = PstEmployee.fetchExc(rpm.getEmployeeId());
                        } catch (Exception exc){}
                        long divId = 0;
                        try {
                            divId = emp.getDivisionId();
                        } catch (Exception exc){}

                        if(divId != 0){
                            vDivision = PstDivision.list(0,0,PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+rpm.getDivisionId(),"");
							if (vDivision.size()>0){
								Division division = (Division)vDivision.get(0);
								divName = division.getDivision();
							} else {
								divName = "-";
							}
                            
                            
                        }
                        titleCellTop = new Cell(new Chunk("" + divName, tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);                        
                        
                        
                        int digitDesc = rpm.getDescription().length();
                        String potDesc = rpm.getDescription();
                        if(digitDesc > 300){
                            potDesc = potDesc.substring(0,300);
                            potDesc = potDesc + ".....";
                        }
                        titleCellTop = new Cell(new Chunk("" + potDesc, tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);

                        
//
//                        titleCellTop = new Cell(new Chunk("" + rpm.getPage(), tableContent));
//                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
//                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        titleCellTop.setBorderColor(new Color(255, 255, 255));
//                        dataTable.addCell(titleCellTop);
                        /* Update by Hendra Putu | 2015-11-04 | Description is not show
                        titleCellTop = new Cell(new Chunk((rpm.getDescription().length() > 100) ? rpm.getDescription().substring(0, 100) + " ..." : rpm.getDescription(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);*/
//
//                        titleCellTop = new Cell(new Chunk("" + Formater.formatDate(rpm.getValidityDate(), "d-MMM-yyyy"), tableContent));
//                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
//                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                        titleCellTop.setBorderColor(new Color(255, 255, 255));
//                        dataTable.addCell(titleCellTop);

                        //titleCellTop = new Cell(new Chunk("" + PstEmpReprimand.levelNames[rpm.getReprimandLevel()], tableContent));
                        Vector vLvlName = new Vector();
                        vLvlName = PstReprimand.list(0,0,"REPRIMAND_ID="+rpm.getReprimandLevelId(),"");
                        String repLevel = "";
                        try {
                            Reprimand reprim = (Reprimand)vLvlName.get(0);
                            repLevel = reprim.getReprimandDesc();
                        } catch (Exception exc){
                            
                        }
                        titleCellTop = new Cell(new Chunk("" + repLevel, tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);
                    }
                    document.add(dataTable);
                }
        }
        
        if(showAward){
                // Print Award
            if (vAward != null && vAward.size() > 0) {
                //membuat table title Award
                int warningTop[] = {10};
                Table dataTable = new Table(1);
                dataTable.setWidth(100);
                dataTable.setWidths(warningTop);
                dataTable.setBorderColor(new Color(255, 255, 255));
                dataTable.setBorderWidth(1);
                dataTable.setAlignment(1);
                dataTable.setCellpadding(0);
                dataTable.setCellspacing(1);

                /*
                Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);*/

                Cell titleTableTop = new Cell(new Chunk("Award History", fontHeader));
                titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleTableTop.setBorderColor(new Color(255, 255, 255));
                titleTableTop.setBackgroundColor(bgColor);
                dataTable.addCell(titleTableTop);
                
                document.add(dataTable);                
              }

            if (vAward != null && vAward.size() > 0) {
                    //membuat table 
                    int dataTop[] = {10, 10, 10, 10, 10, 10};
                    Table dataTable = new Table(6);
                    dataTable.setWidth(100);
                    dataTable.setWidths(dataTop);
                    dataTable.setBorderColor(new Color(255, 255, 255));
                    dataTable.setBorderWidth(1);
                    dataTable.setAlignment(1);
                    dataTable.setCellpadding(0);
                    dataTable.setCellspacing(1);

                    //1
                    titleCellTop = new Cell(new Chunk("Date", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Department", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Section", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);
                    
                    titleCellTop = new Cell(new Chunk("Award from", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Award Type", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Description", fontHeader));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    titleCellTop.setBackgroundColor(bgColor);
                    dataTable.addCell(titleCellTop);

                    //value-valuenya
                    for (int i = 0; i < vAward.size(); i++) {
                        EmpAward awd = (EmpAward) vAward.get(i);
                        Department dept = new Department();
                        com.dimata.harisma.entity.masterdata.Section sect = new com.dimata.harisma.entity.masterdata.Section();
                        AwardType typ = new AwardType();
                        ContactList contList = new ContactList();
                        
                        try {
                            dept = PstDepartment.fetchExc(awd.getDepartmentId());
                        } catch (Exception e) {
                            dept = new Department();
                        }

                        try {
                            sect = PstSection.fetchExc(awd.getSectionId());
                        } catch (Exception e) {
                            sect = new com.dimata.harisma.entity.masterdata.Section();
                        }

                        try {
                            typ = PstAwardType.fetchExc(awd.getAwardType());
                        } catch (Exception e) {
                            typ = new AwardType();
                        }
                        
                        try {
                            contList = PstContactList.fetchExc(awd.getProviderId());
                        } catch (Exception e) {
                            contList = new ContactList();
                        }

                        titleCellTop = new Cell(new Chunk(Formater.formatDate(awd.getAwardDate(), "dd-MM-yyyy"), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);

                        titleCellTop = new Cell(new Chunk("" + dept.getDepartment(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);

                        titleCellTop = new Cell(new Chunk("" + sect.getSection(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);
                        
                        titleCellTop = new Cell(new Chunk("" + contList.getCompName(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);

                        titleCellTop = new Cell(new Chunk("" + typ.getAwardType(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);

                        titleCellTop = new Cell(new Chunk((awd.getAwardDescription().length() > 100) ? awd.getAwardDescription().substring(0, 100) + " ..." : awd.getAwardDescription(), tableContent));
                        titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellTop.setBorderColor(new Color(255, 255, 255));
                        dataTable.addCell(titleCellTop);
                    }
                    document.add(dataTable);
                }
        }
            
        } catch (Exception e) {
            System.out.println("PRINT EMPLOYEE DATA ==>" + e);
        }

        document.close();

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
    
    /* Update by Hendra Putu | 2015-11-04 */
    public String getRelationName(String oids){
        String str = "-";
        long oid = Long.valueOf(oids);
        try {
            FamRelation famRelation = PstFamRelation.fetchExc(oid);
            str = famRelation.getFamRelation();
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return str;
    }
    
    public static int getAge(String birthDate) {
        DBResultSet dbrs = null;
        int age = 0;
        try {
            String sql = "SELECT YEAR(CURDATE()) - YEAR('"+birthDate+"') - IF(STR_TO_DATE(CONCAT(YEAR(CURDATE()), '-', MONTH('"+birthDate+"'), '-', DAY('"+birthDate+"')) ,'%Y-%c-%e') > CURDATE(), 1, 0)";
            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL getCompFormValue"+sql);
            while (rs.next()) {
                age = rs.getInt(1);
			}

            rs.close();
            return age;
        } catch (Exception e) {
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static int getBirthDayDiff(String birthDate) {
        DBResultSet dbrs = null;
        int dayDiff = 0;
        /* jika daydiff < 0 berarti tgl ulang tahun sudah lewat*/
        try {
            String sql = "SELECT  DATEDIFF( CONCAT(YEAR(CURDATE()),'-',MONTH('"+birthDate+"'),'-',DAY('"+birthDate+"')),NOW()) AS DAY_DIFF;";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                dayDiff = rs.getInt(1);
            }

            rs.close();
            return dayDiff;
        } catch (Exception e) {
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
}
