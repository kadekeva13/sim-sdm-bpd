<%-- 
    Document   : organization_view
    Created on : Jan 25, 2016, 9:58:30 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.report.EmployeeAmountXLS"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.session.employee.SessEmployeePicture"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public String whereEmpGlobal = "";
    public long tempUpPos = -1; 
    public int incUp = 0;
    public int nomor = 0;
    public int incTd = 0;

    public String getDrawDownPosition(long oidPosition, long oidTemplate, String approot){
        String str = "";
        String whereEmployee = "";
        StructureModule structureModule = new StructureModule();
        structureModule.setWhereEmployee(this.whereEmpGlobal);
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+"="+oidPosition;
        whereClause += " AND "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        String topkiri = "border-top:1px solid #999;";
        String topkanan = "border-top:1px solid #999;";
        if (listDown != null && listDown.size()>0){
            str = "<table class=\"tblStyle1\"><tr>";
            for(int i=0; i<listDown.size(); i++){
                MappingPosition pos = (MappingPosition)listDown.get(i);
                whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+pos.getDownPositionId();
                structureModule.setupEmployee(whereEmployee);     
                if (listDown.size() == 1){
                    topkiri = "";
                    topkanan = "";
                } else {
                    if (i == 0 || i == (listDown.size()-1)){
                        if (i == 0){
                            topkiri = "";
                            topkanan = "border-top:1px solid #999;";
                        } else {
                            topkiri = "border-top:1px solid #999;";
                            topkanan = "";
                        }
                    } else {
                        topkiri = "border-top:1px solid #999;";
                        topkanan = "border-top:1px solid #999;";
                    }
                }
                
                str += "<td valign=\"top\">";
                
                str += "<table width=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">";
                str += "<tr>";
                str += "<td width=\"50%\" style=\"border-right:1px solid #999; "+topkiri+"\">&nbsp;</td><td width=\"50%\" style=\""+topkanan+"\">&nbsp;</td>";
                str += "</tr>";
                str += "</table>";
                
                String pictPath = "";
                try {
                    SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                    pictPath = sessEmployeePicture.fetchImageEmployee(structureModule.getEmployeeId());

                } catch (Exception e) {
                    System.out.println("err." + e.toString());
                } 
                
                if (pictPath != null && pictPath.length() > 0) {
                   str += "<img  height=\"64\" id=\"photo\" src=\"" + approot + "/" + pictPath + "\">";
                } else {
                   str += "<img height=\"64\" id=\"photo\" src=\""+approot+"/imgcache/no-img.jpg\" />";   
                }

                if (structureModule.getEmployeeResign()== 0){
                    str += "<div style=\"color: #373737\"><a id=\"linkStyle\" href=\"javascript:cmdViewEmployee('" + structureModule.getEmployeeId() + "')\">";
                    str += "<strong>"+ structureModule.getEmployeeName() + "</strong></a></div>";
                    str += "<div>" + getPositionName(pos.getDownPositionId()) + "</div>" + getDrawDownPosition(pos.getDownPositionId(), oidTemplate, approot);
                } else {
                    str += "<div style=\"color: #373737\"><strong>-Kosong-</strong></div>";
                    str += "<div>" + getPositionName(pos.getDownPositionId()) + "</div>" + getDrawDownPosition(pos.getDownPositionId(), oidTemplate, approot); 
                }
                str += "</td>";
            }
            str += "</tr></table>";
        }
        
        return str;
    }

    public String getPositionName(long posId){
        String position = "";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
        } catch(Exception ex){
            System.out.println("getPositionName ==> "+ex.toString());
        }
        position = pos.getPosition();
        return position;
    }

    public String getDivisionName(long divisionId){
        String name = "-";
        if (divisionId != 0) {
            try {
                Division division = PstDivision.fetchExc(divisionId);
                name = division.getDivision();
            } catch (Exception e) {
                System.out.println("Division Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getDepartmentName(long departmentId){
        String name = "-";
        if (departmentId != 0){
            try {
                Department depart = PstDepartment.fetchExc(departmentId);
                name = depart.getDepartment();
            } catch (Exception e) {
                System.out.println("Department Name =>" + e.toString());
            }
        }
        return name;
    }
    
    public String getSectionName(long sectionId){
        String name = "-";
        if (sectionId != 0){
            try {
                Section section = PstSection.fetchExc(sectionId);
                name = section.getSection();
            } catch (Exception e) {
                System.out.println("Section Name =>" + e.toString());
            }
        }
        return name;
    }
%>
<%
    long selectStructure = FRMQueryString.requestLong(request, "select_structure");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "section_id");
    String whereMap = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+selectStructure;
    Vector listMap = PstMappingPosition.list(0, 0, whereMap, "");
    /* Structure Module adalah kumpulan fungsi utk proses view struktur */
    StructureModule structureModule = new StructureModule();
    /* Period From and Period To */
    String periodFrom = FRMQueryString.requestString(request, "period_from");
    String periodTo = FRMQueryString.requestString(request, "period_to");
    String whereClause = "";
    Date dateNow = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String strDateNow = sdf.format(dateNow);
    if (periodFrom.equals("")){
        periodFrom = strDateNow;
    }
    if (periodTo.equals("")){
        periodTo = strDateNow;
    }
    
    String whereEmployee = "";
    int checkUp = 0;
    long topMain = 0;
    /* get Data Division Type ID */
    Division division = new Division();
    String labelStructure = "-";
    long divisiTypeId = 0;
    int divType = 0;
    String testOut = "";
    if (divisionId != 0 && departmentId == 0 && sectionId == 0){
        try {
            /* Output dari proses ini adalah division name dan division type (divType) */
            division = PstDivision.fetchExc(divisionId);
            divisiTypeId = division.getDivisionTypeId();
            labelStructure = dictionaryD.getWord(I_Dictionary.DIVISION)+" : "+division.getDivision();
            if (divisiTypeId != 0){
                DivisionType divisiType = PstDivisionType.fetchExc(divisiTypeId);
                divType = divisiType.getGroupType();
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
    }
    if (divisionId == 0 && departmentId != 0 && sectionId == 0){
        /* Jika departmentId tidak sama dengan 0, maka secara default division Type adalah Branch Of Company */
        divType = PstDivisionType.TYPE_BRANCH_OF_COMPANY;
        labelStructure = dictionaryD.getWord(I_Dictionary.DEPARTMENT)+" : "+getDepartmentName(departmentId);
    }
    if (divisionId == 0 && departmentId == 0 && sectionId != 0){
        divType = PstDivisionType.TYPE_BRANCH_OF_COMPANY;
        labelStructure = dictionaryD.getWord("SECTION")+" : "+getSectionName(sectionId);
    }

    /* Get template name */
    String templateName = "-";
    try {
        StructureTemplate structTemp = PstStructureTemplate.fetchExc(selectStructure);
        templateName = structTemp.getTemplateName();
    } catch(Exception e){
        System.out.println(""+e.toString());
    }
    /* end Get template name */
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization - View</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            body {
                color:#373737; 
                background-color: #EEE;
            }
            .tblStyle {border-collapse: collapse; font-size: 12px; background-color: #FFF;}
            .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px; }
            .tblStyle1 {border-collapse: collapse; background-color: #FFF;}
            .tblStyle1 td {color:#575757; text-align: center; font-size: 11px; padding: 3px 0px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; }
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            .header {
                
            }
            .content-main {
                padding: 13px 17px;
                margin: 0px 23px 21px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            
            #photo {
                padding: 3px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }

            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            
            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            .item {
                color: #474747;
                background-color: #E5E5E5;
                padding: 3px 5px;
                margin: 3px 0px;
                border-radius: 3px;
            }
        </style>
        <script type="text/javascript">
            function cmdViewEmployee(oid){
                document.frm.employee_oid.value=oid;
                document.frm.action="employee_detail.jsp";
                document.frm.target="_blank";
                document.frm.submit();
            }
            function cmdExportExcel(){
                document.frm.action="<%=printroot%>.report.ExportPositionOrgXLS"; 
                document.frm.target = "ReportExcel";
                document.frm.submit();
            }
        </script>
    </head>
    <body>
        <div id="menu_utama">
            <span id="menu_title"><strong style="color:#474747">Organization :</strong> 
                <%=templateName%><strong style="color:#333;"> / </strong><%=labelStructure%>
                <strong style="color:#333;"> / </strong><%= periodFrom +" to "+periodTo %>
            </span>
        </div>
        <div class="content-main">
            <%
            if (listMap != null && listMap.size()>0){
                long[] arrUp = new long[listMap.size()];
                long[] arrDown = new long[listMap.size()];
                /* inisialisasi array up position dan down position */
                for(int i=0; i<listMap.size(); i++){
                    MappingPosition map = (MappingPosition)listMap.get(i);
                    arrUp[i] = map.getUpPositionId();
                    arrDown[i] = map.getDownPositionId();
                }
                if (divType == PstDivisionType.TYPE_DIVISION){ /* Division Regular */
                    testOut = "Division Regular";
                    /* ambil data position pada mapping PositionDivision */
                    whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
                    Vector listPosDivision = PstPositionDivision.list(0, 0, whereClause, "");
                    String strPos = "";
                    /* setting data employee sesuai division */
                    whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId+" AND ";
                    structureModule.setWhereEmployee(whereEmployee);
                    this.whereEmpGlobal = whereEmployee;
                    /* jika data list position division ada, maka tampung ke strPos */
                    if (listPosDivision != null && listPosDivision.size()>0){
                        for(int i=0; i<listPosDivision.size(); i++){
                            PositionDivision posDiv = (PositionDivision)listPosDivision.get(i);
                            strPos += posDiv.getPositionId() +", ";
                        }
                        strPos += "0";
                        whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+" IN("+strPos+")";
                        Vector listMap2 = PstMappingPosition.list(0, 0, whereClause, "");
                        /* manipulasi atau isi ulang arrUp dan arrDown dg MappingPosition baru */
                        if (listMap2 != null && listMap2.size()>0){
                            arrUp = new long[listMap2.size()];
                            arrDown = new long[listMap2.size()];
                            for(int i=0; i<listMap2.size(); i++){
                                MappingPosition map = (MappingPosition)listMap2.get(i);
                                arrUp[i] = map.getUpPositionId();
                                arrDown[i] = map.getDownPositionId();
                            }
                        }
                        /* mencari position teratas */
                        for(int j=0; j<arrUp.length; j++){
                            for(int k=0; k<arrDown.length; k++){
                                if (arrUp[j] == arrDown[k]){
                                    checkUp++;
                                }
                            }
                            if (checkUp == 0){
                                topMain = arrUp[j];
                            }
                            checkUp = 0;
                        }
                    }
                    structureModule.setupEmployee(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+topMain);
                } else {
                    for(int j=0; j<arrUp.length; j++){
                        for(int k=0; k<arrDown.length; k++){
                            if (arrUp[j] == arrDown[k]){
                                checkUp++;
                            }
                        }
                        if (checkUp == 0){
                            topMain = arrUp[j];
                        }
                        checkUp = 0;
                    }
                    if (divType == PstDivisionType.TYPE_BRANCH_OF_COMPANY){
                        testOut = "B O C";
                        if (divisionId != 0 && departmentId == 0 && sectionId == 0){
                            whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId+" AND ";
                        }
                        if (divisionId == 0 && departmentId != 0 && sectionId == 0){
                            whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId+" AND ";
                        }
                        if (divisionId == 0 && departmentId == 0 && sectionId != 0){
                            whereEmployee = PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId+" AND ";
                        }
                        structureModule.setWhereEmployee(whereEmployee);
                        this.whereEmpGlobal = whereEmployee;
                        structureModule.setupEmployee(PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+topMain);
                    }
                    
                    if (divType == PstDivisionType.TYPE_BOD){
                        testOut = "B O D";
                        structureModule.setWhereEmployee("");
                        this.whereEmpGlobal = whereEmployee;
                        structureModule.setupEmployee(" "+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+"="+topMain);
                    }
                }
                
                if (topMain > 0){
                    %>
                    <form name="frm">
                        <input type="hidden" name="employee_oid" value="0" />
                        <input type="hidden" name="division_id" value="<%= divisionId %>" />
                        <input type="hidden" name="department_id" value="<%= departmentId %>" />
                        <input type="hidden" name="section_id" value="<%= sectionId %>" />
                        <input type="hidden" name="period_from" value="<%= periodFrom %>" />
                        <input type="hidden" name="period_to" value="<%= periodTo %>" />                        
                    </form>
                    <%= "Pilihan : " + testOut %>
                    <table class="tblStyle1">
                        <tr>
                            <td valign="top" style="padding: 21px">
                                <%
                                String pictPath = "";
                                try {
                                    SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                                    pictPath = sessEmployeePicture.fetchImageEmployee(structureModule.getEmployeeId());

                                } catch (Exception e) {
                                    System.out.println("err." + e.toString());
                                }%>
                                <%
                                     if (pictPath != null && pictPath.length() > 0) {
                                         %>
                                        <img height="64" id="photo" src="<%= approot +"/"+ pictPath %>" />
                                        <%
                                     } else {
                                %>
                                        <img height="64" id="photo" src="<%=approot%>/imgcache/no-img.jpg" />
                                <%

                                    }
                                %> 
                                <div style="color: #373737;">
                                    <%
                                    if (structureModule.getEmployeeResign() == 0){
                                        if (structureModule.getEmployeeId() > 0){
                                        %>
                                        <a id="linkStyle" href="javascript:cmdViewEmployee('<%=structureModule.getEmployeeId()%>')">
                                            <strong><%=structureModule.getEmployeeName()%></strong>
                                        </a>
                                        <%
                                        } else {
                                            %>
                                            <strong>-Kosong-</strong>
                                            <%
                                        }
                                    } else {
                                        %>
                                        <strong>-Kosong-</strong>
                                        <%
                                    }
                                    %>
                                </div>
                                <%=getPositionName(topMain)%>
                                <%=getDrawDownPosition(topMain, selectStructure, approot)%>
                            </td>
                        </tr>
                    </table>
                    <%
                }
            }
            %>
        </div>
<%
EmployeeAmountXLS empAmount = new EmployeeAmountXLS();
String strDate = periodTo.replaceAll("-", "");
int periodDate = Integer.valueOf(strDate);
whereClause = PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " <= '" + periodTo+ "'";
Vector dataNoResign = empAmount.getEmployeeNoResign(whereClause, periodDate);
%>        
        <div class="content-main">
            <div id="menu_utama">
                <span id="menu_title">Jabatan yang Tersedia</span>
            </div>
            <div>&nbsp;</div>
            <div>
                <a class="btn" style="color:#FFF" href="javascript:cmdExportExcel()">Export Data to Excel</a>
            </div>
            <div>&nbsp;</div>
            <table class="tblStyle">
                <tr>
                    <td class="title_tbl">Position name</td>
                    <td class="title_tbl">Jumlah</td>
                </tr>
<%
    Vector listPosition = new Vector();
    Vector listPosColl = new Vector();
    if (divisionId != 0 && departmentId == 0 && sectionId == 0){
        whereClause = PstPositionDivision.fieldNames[PstPositionDivision.FLD_DIVISION_ID]+"="+divisionId;
        listPosColl = PstPositionDivision.list(0, 0, whereClause, "");
        if (listPosColl != null && listPosColl.size()>0){
            for(int i=0; i<listPosColl.size(); i++){
                PositionDivision posDiv = (PositionDivision)listPosColl.get(i);
                listPosition.add(posDiv.getPositionId());
            }
        }
    }
    if (divisionId == 0 && departmentId != 0 && sectionId == 0){
        whereClause = PstPositionDepartment.fieldNames[PstPositionDepartment.FLD_DEPARTMENT_ID]+"="+departmentId;
        listPosColl = PstPositionDepartment.list(0, 0, whereClause, "");
        if (listPosColl != null && listPosColl.size()>0){
            for(int i=0; i<listPosColl.size(); i++){
                PositionDepartment posDep = (PositionDepartment)listPosColl.get(i);
                listPosition.add(posDep.getPositionId());
            }
        }
    }
    if (divisionId == 0 && departmentId == 0 && sectionId != 0){
        whereClause = PstPositionSection.fieldNames[PstPositionSection.FLD_SECTION_ID]+"="+sectionId;
        listPosColl = PstPositionSection.list(0, 0, whereClause, "");
        if (listPosColl != null && listPosColl.size()>0){
            for(int i=0; i<listPosColl.size(); i++){
                PositionSection posSec = (PositionSection)listPosColl.get(i);
                listPosition.add(posSec.getPositionId());
            }
        }
    }
    /*===================================*/
    Vector listEmployee = new Vector();
    whereClause = PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " <= '" + periodTo+ "'";
    listEmployee = PstEmployee.list(0, 0, whereClause, "");
    if (listPosition != null && listPosition.size()>0){
        for(int i=0; i<listPosition.size(); i++){
            Long posId = (Long)listPosition.get(i);
            if (divisionId != 0 && departmentId == 0 && sectionId == 0){
                whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+divisionId;
            }
            if (divisionId == 0 && departmentId != 0 && sectionId == 0){
                whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+departmentId;
            }
            if (divisionId == 0 && departmentId == 0 && sectionId != 0){
                whereClause = PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+sectionId;
            }
            whereClause += " AND "+PstCareerPath.fieldNames[PstCareerPath.FLD_POSITION_ID]+"="+posId;
            int total = empAmount.getDataCareerPath(dataNoResign, whereClause, periodFrom, periodTo);
            %>
            <tr>
                <td>
                    <div><strong><%= getPositionName(posId) %></strong></div>
                    <%= empAmount.getEmpCareerPath(dataNoResign, whereClause, periodFrom, periodTo) %>
                </td>
                <td><%=total%></td>
            </tr>
            <%
        }
    }
%>
            </table>
        </div>
    </body>
</html>
