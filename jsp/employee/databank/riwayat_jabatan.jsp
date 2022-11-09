<%-- 
    Document   : riwayat_jabatan
    Created on : May 26, 2016, 9:10:54 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.employee.CtrlCareerPath"%>
<%@page import="com.dimata.harisma.form.employee.FrmCareerPath"%>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_CAREERPATH);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* OBJ_DATABANK_PERSONAL_DATA = 1; */
int appObjCodePer = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_PERSONAL_DATA);
boolean privViewPer = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePer, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_FAMILY_MEMBER = 2; */
int appObjCodeFam = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_FAMILY_MEMBER);
boolean privViewFam = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeFam, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_LANG_N_COMPETENCE = 3; */
int appObjCodeLang = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_LANG_N_COMPETENCE);
boolean privViewLang = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeLang, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_EDUCATION = 4; */
int appObjCodeEdu = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_EDUCATION);
boolean privViewEdu = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeEdu, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_EXPERIENCE = 5; */
int appObjCodeExp = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_EXPERIENCE);
boolean privViewExp = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeExp, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_CAREERPATH = 6; */
/* On The Top */
/* OBJ_DATABANK_TRAINING = 7; */
int appObjCodeTra = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_TRAINING);
boolean privViewTra = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeTra, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_WARNING = 8; */
int appObjCodeWar = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_WARNING);
boolean privViewWar = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeWar, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_REPRIMAND = 9; */
int appObjCodeRep = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_REPRIMAND);
boolean privViewRep = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeRep, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_AWARD = 10; */
int appObjCodeAwr = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_AWARD);
boolean privViewAwr = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeAwr, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_PICTURE = 11; */
int appObjCodePic = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_PICTURE);
boolean privViewPic = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePic, AppObjInfo.COMMAND_VIEW));
/* OBJ_DATABANK_RELEVANT_DOC = 12; */
int appObjCodeRel = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK_RELEVANT_DOC);
boolean privViewRel = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeRel, AppObjInfo.COMMAND_VIEW));
/////
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    int commandEdit = FRMQueryString.requestInt(request, "command_edit");
    
    int recordToGet = 30;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
    String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];

    String whereClauseCareer = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND ("+ PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + PstCareerPath.PEJABAT_SEMENTARA_TYPE + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + PstCareerPath.CAREER_TYPE + " )" ;
    String orderClauseCareer = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
    
    CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCareerPath = new Vector(1, 1);
    Vector listCareerPathCareer = new Vector(1, 1);
    
    iErrCode = ctrlCareerPath.action(iCommand, oidCareerPath, oidEmployee, request, emplx.getFullName(), appUserIdSess);
    
    if(iErrCode == ctrlCareerPath.RSLT_FRM_DATE_IN_RANGE){
        iCommand = Command.ADD;
    }
    /* end switch*/
    FrmCareerPath frmCareerPath = ctrlCareerPath.getForm();
    
    CareerPath careerPath = ctrlCareerPath.getCareerPath();
    msgString = ctrlCareerPath.getMessage();

    /*switch list CareerPath*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCareerPath == 0)) {
        start = PstCareerPath.findLimitStart(careerPath.getOID(), recordToGet, whereClause, orderClause);
    }

    /*count list All CareerPath*/
    int vectSize = PstCareerPath.getCount(whereClause);

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlCareerPath.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listCareerPath = PstCareerPath.list(start, recordToGet, whereClause, orderClause);
    listCareerPathCareer = PstCareerPath.list(start, recordToGet, whereClauseCareer, orderClauseCareer);
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCareerPath.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCareerPath = PstCareerPath.list(start, recordToGet, whereClause, orderClause);
    }

    long oidDepartment = 0;
    if (oidEmployee != 0) {
        Employee employee = new Employee();
        try {
            employee = PstEmployee.fetchExc(oidEmployee);
            oidDepartment = employee.getDepartmentId();
        } catch (Exception exc) {
            employee = new Employee();
        }
    }

    if (iCommand == Command.GOTO) {
        frmCareerPath = new FrmCareerPath(request, careerPath);
        frmCareerPath.requestEntityObject(careerPath);
    }


    I_Dictionary dictionaryD = userSession.getUserDictionary();
    /* Pengecualian untuk client bpd */
    String ClientName = "";
    try {
        ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
    } catch (Exception e) {
        System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
    }
%>
<html>
    <head>
        <title>Riwayat Jabatan</title>
        <script type="text/javascript">
            function cmdUpdateDiv(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            function cmdUpdateDep(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            function cmdUpdatePos(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            function cmdAdd(){
                document.frmcareerpath.career_path_oid.value="0";
                document.frmcareerpath.command.value="<%=Command.ADD%>";
                document.frmcareerpath.prev_command.value="<%=Command.ADD%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdAsk(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.ASK%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            
            function cmdMinta(oid){
                document.getElementById("box_delete").style.visibility="visible";
                document.getElementById("oidcareer").value=oid;
            }
            
            function cmdDetail(oid){
                window.open("../databank/careerpath_detail.jsp?oid="+oid, null, "height=550,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
            }
            function cmdDetail2(oid){
                window.open("../databank/careerpath_detail.jsp?oid="+oid+"&now=1", null, "height=550,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
            }
            function cmdDetailDocument(EmpDocument_oid){
                window.open("<%=approot%>/employee/document/EmpDocumentDetailsEditor.jsp?EmpDocument_oid="+EmpDocument_oid+"&fromCareerPath=1");       
            }
            function cmdHapus(){
                
                document.frmcareerpath.command.value="<%=Command.DELETE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdConfirmDelete(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.DELETE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            function cmdSave(){
                document.frmcareerpath.command.value="<%=Command.SAVE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdEdit(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.command_edit.value="1";
                document.frmcareerpath.prev_command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdCancel(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBack(){
                document.frmcareerpath.command.value="<%=Command.BACK%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdUpdateSection(){
                document.frmcareerpath.command.value="<%= Command.GOTO%>";
                document.frmcareerpath.prev_command.value="<%= prevCommand%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBackEmp(empOID){
                document.frmcareerpath.employee_oid.value=empOID;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }


            function cmdListFirst(){
                document.frmcareerpath.command.value="<%=Command.FIRST%>";
                document.frmcareerpath.prev_command.value="<%=Command.FIRST%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListPrev(){
                document.frmcareerpath.command.value="<%=Command.PREV%>";
                document.frmcareerpath.prev_command.value="<%=Command.PREV%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListNext(){
                document.frmcareerpath.command.value="<%=Command.NEXT%>";
                document.frmcareerpath.prev_command.value="<%=Command.NEXT%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListLast(){
                document.frmcareerpath.command.value="<%=Command.LAST%>";
                document.frmcareerpath.prev_command.value="<%=Command.LAST%>";
                document.frmcareerpath.action="riwayat_jabatan.jsp";
                document.frmcareerpath.submit();
            }
            function cmdEditDetail2(EmpDocument_oid){
                    window.open("<%=approot%>/employee/document/EmpDocumentDetailsEditor.jsp?EmpDocument_oid="+EmpDocument_oid+"&fromCareerPath=1");       
            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript">

            function loadCompany(oid) {
                if (oid.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "careerpath_ajax.jsp?career_path_oid=" + oid, true);
                    xmlhttp.send();
                }
            }

            function loadDivision(str) {
                if (str.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "careerpath_ajax.jsp?company_id=" + str, true);
                    xmlhttp.send();
                }
            }

            function loadDepartment(comp_id, divisi_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "careerpath_ajax.jsp?company_id="+comp_id+"&division_id=" + divisi_id, true);
                    xmlhttp.send();
                }
            }

            function loadSection(comp_id, divisi_id, depart_id) {
                if ((comp_id.length == 0)&&(divisi_id.length == 0)&&(depart_id.length == 0)) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                } else {
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                        }
                    };
                    xmlhttp.open("GET", "careerpath_ajax.jsp?company_id="+comp_id+"&division_id=" + divisi_id +"&department_id="+depart_id, true);
                    xmlhttp.send();
                }
            }
        </script>

        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_tbl_part {
                font-weight: bold;
                background-color: #EEE; 
                color: #08aad2;
            }
            .title_tbl_part_sub {
                font-weight: bold;
                background-color: #EEE; 
                color: #729a13;
            }
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #current-list {
                background-color: #CDEDF7;
                color:#508DA1;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #box_title {padding:9px; background-color: #D5D5D5; font-weight: bold; color:#575757; margin-bottom: 7px; }
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .breadcrumb {
                background-color: #EEE;
                color:#0099FF;
                padding: 7px 9px;
            }
            .navbar {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #0084ff;
                padding: 7px 9px;
                color : #FFF;
                border-top:1px solid #0084ff;
                border-bottom: 1px solid #0084ff;
            }
            .navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .navbar li {
                padding: 7px 9px;
                display: inline;
                cursor: pointer;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {
                font-size: 12px;
                font-weight: bold;
                color:#0099FF; 
                background-color: #F7F7F7; 
                border-left: 1px solid #0099FF; 
                padding: 7px 9px;
                margin: 3px 0px;
            }
            .title_part_red {color:#ffffff; background-color: #ff9d9d; border-left: 1px solid #ff0000; padding: 9px 11px;}
        </style>
        <style type="text/css">
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
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
            #title-form {
                border-left: 2px solid #007fba;
                background-color: #EEE;
                padding: 13px 15px;
                color: #575757;
                font-size: 14px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
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
                text-decoration: none;
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                font-size: 12px;
                padding: 7px 0px 8px 15px;
                background-color: #FF6666;
                color: #FFF;
            }
            #btn-confirm-y {
                padding: 7px 15px 8px 15px;
                background-color: #F25757; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
            #btn-confirm-n {
                padding: 7px 15px 8px 15px;
                background-color: #E34949; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
            .footer-page {
                
            }
            #box_delete {visibility: hidden;}
            #txtHint {

            }
            .caption {font-weight: bold;}
            .divinput {margin-bottom: 5px;}
            #req {background-color: #FF6666; color:#FFF; padding: 5px; border-radius: 2px;}
            .box-list {
                background-color: #E5E5E5;
                padding: 2px;
            }
        </style>
<link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
<script src="<%=approot%>/javascripts/jquery.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
<script>
    $(function() {
        $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
    });

   function pageLoad(){ $(".mydate").datepicker({ dateFormat: "yy-mm-dd" }); loadCompany('<%=oidCareerPath%>'); }  
</script>
</head>
<body onload="pageLoad()">
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <strong style="color:#333;"> / </strong> <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></span>
        </div>
        <% if (oidEmployee != 0) {%>
            <div class="navbar">
                <ul style="margin-left: 97px">
                    <% if (privViewPer == true){ %>
                    <li class=""> <a href="employee_edit.jsp?employee_oid=<%=oidEmployee%>&prev_command=<%=Command.EDIT%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PERSONAL_DATA)%></a> </li>
                    <% } %>
                    <% if (privViewFam == true){ %>
                    <li class=""> <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a> </li>
                    <% } %>
                    <% if (privViewLang == true){ %>
                    <li class=""> <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.COMPETENCIES) %></a> </li>
                    <% } %>
                    <% if (privViewEdu == true){ %>
                    <li class=""> <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a> </li>
                    <% } %>
                    <% if (privViewExp == true){ %>
                    <li class=""> <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a> </li>
                    <% } %>
                    <li class="active"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></li>
                    <% if (privViewTra == true){ %>
                    <li class=""> <a href="training.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING_ON_DATABANK)%></a></li>
                    <% } %>
                    <% if (privViewWar == true){ %>
                    <li class=""> <a href="warning.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a> </li>
                    <% } %>
                    <% if (privViewRep == true){ %>
                    <li class=""> <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a> </li>
                    <% } %>
                    <% if (privViewAwr == true){ %>
                    <li class=""> <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a> </li>
                    <% } %>
                    <% if (privViewPic == true){ %>
                    <li class=""> <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a> </li>
                    <% } %>
                    <% if (privViewRel == true){ %>
                    <li class=""> <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a> </li>
                    <% } %>
                </ul>
            </div>
        <%}%>
        <div class="content-main">
            <form name="frmcareerpath" method ="post" action="careerpath.jsp">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" id="oidcareer" name="career_path_oid" value="<%=oidCareerPath%>">
                <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                <input type="hidden" name="command_edit" value="<%=commandEdit%>">
                <div class="content-info">
                    <% 
                        Employee employee = new Employee();
                        if(oidEmployee != 0){
                            employee = new Employee();
                            try {
                                employee = PstEmployee.fetchExc(oidEmployee);
                            } catch (Exception exc) {
                                employee = new Employee();
                            }

                    %>
                        <table border="0" cellspacing="0" cellpadding="0" style="color: #575757">
                        <tr> 
                                <td valign="top" style="padding-right: 11px;"><strong> <%=dictionaryD.getWord(I_Dictionary.PAYROLL_NUMBER) %></strong></td>
                              <td valign="top"><%=employee.getEmployeeNum()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong> <%=dictionaryD.getWord(I_Dictionary.NAME) %></strong></td>
                              <td valign="top"><%=employee.getFullName()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong> <%=dictionaryD.getWord(I_Dictionary.COMMENCING_DATE) %></strong></td>
                              <td valign="top"><%=employee.getCommencingDate()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong> <%=dictionaryD.getWord(I_Dictionary.DATE_OF_BIRTH) %></strong></td>
                              <td valign="top"><%=employee.getBirthDate()%></td>
                        </tr>
                        <% Department department = new Department();
                            try {
                                department = PstDepartment.fetchExc(employee.getDepartmentId());
                            } catch (Exception exc) {
                                department = new Department();
                            }
                       %>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></strong></td>
                              <td valign="top"><%=department.getDepartment()%></td>
                        </tr>
                        <tr> 
                              <td valign="top" style="padding-right: 11px;"><strong><%=dictionaryD.getWord(I_Dictionary.ADDRESS) %></strong></td>
                              <td valign="top"><%=employee.getAddress()%></td>
                        </tr>
                        </table>
                    <% } %>
                </div>
                <div class="content-title">
                    <div id="title-large">Riwayat Jabatan</div>
                    <div id="title-small">Daftar riwayat jabatan karyawan.</div>
                </div>
                <div class="content">
                    <!-- Tombol ADD -->
                    <p style="margin-top: 2px">
                        <%
                        if (privAdd == true){
                            %>
                            <a style="color:#FFF" class="btn" href="javascript:cmdAdd()"><%=dictionaryD.getWord(I_Dictionary.ADD)%> <%=dictionaryD.getWord(I_Dictionary.RECORD)%></button></a>
                            <%
                        }
                        %>
                    </p>
                    <!-- End Tombol ADD-->
                    <div id="box_delete">
                        <span id="confirm">
                            <%=dictionaryD.getWord(I_Dictionary.ARE_YOU_SURE_TO_DELETE_ITEM)%>? 
                            <a id="btn-confirm-y" href="javascript:cmdHapus()"><%=dictionaryD.getWord(I_Dictionary.YES)%></a>
                            <a id="btn-confirm-n" href="javascript:cmdBack()"><%=dictionaryD.getWord(I_Dictionary.NO)%></a>
                        </span>
                    </div>
                    <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmCareerPath.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST) || (iCommand == Command.GOTO)) {%>
                    <div id="menu_utama">
                        <span id="menu_title"><%=dictionaryD.getWord("CAREER_PATH")%> Editor</span>
                    </div>
                    <%if(iErrCode == ctrlCareerPath.RSLT_FRM_DATE_IN_RANGE){%>
                        <div class="title_part_red"><%=msgString%></div>
                    <%}%>
                    <table width="100%" border="0">
                        <tr>
                            <!-- Left Side -->
                            <td valign="top" width="50%">
                                <table>
                                    <tr>
                                        <td valign="top">
                                            <div id="txtHint"></div>
                                        </td>
                                    </tr>
                                    <% int SetLocation = 1;
                                        try {
                                        SetLocation =Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                                        } catch (Exception e){
                                        }
                                        if (SetLocation==1) {
                                   %>
                                   <tr>
                                       <td>
                                       <div id="caption"><%=dictionaryD.getWord("LOCATION")%></div>
                                   <%
                                        String CtrOrderClause = PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID];
                                        Vector vectLocation = PstLocation.list(0,0,"",CtrOrderClause);
                                        Vector val_Location = new Vector(1,1); //hidden values that will be deliver on request (oids) 
                                        Vector key_Location = new Vector(1,1); //texts that displayed on combo box
                                        val_Location.add("0");
                                        key_Location.add("All Location");
                                        for(int c = 0; c < vectLocation.size();c++){
                                        Location location = (Location)vectLocation.get(c);
                                        val_Location.add(""+location.getOID());
                                        key_Location.add(location.getName());
                                        }
                                        %>
                                       <%= ControlCombo.draw(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LOCATION_ID], "formElemen", null, "" + careerPath.getLocationId(), val_Location, key_Location)%>  <%=frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_LOCATION_ID)%>                                                                                                                           
                                       </td>
                                   </tr>
                                   <% } %>
                                   <tr>
                                       <td valign="top">
                                           <div class="caption">
                                               <%=dictionaryD.getWord(I_Dictionary.POSITION)%>
                                           </div>
                                           <div class="divinput">
                                               <% Vector position_value = new Vector(1, 1);
                                                   Vector position_key = new Vector(1, 1);
                                                   Vector listPosition = PstPosition.list(0, 0, "", "POSITION");
                                                   for (int i = 0; i < listPosition.size(); i++) {
                                                       Position position = (Position) listPosition.get(i);
                                                       position_value.add("" + position.getOID());
                                                       position_key.add(position.getPosition());
                                                   }
                                               %>
                                               <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_POSITION_ID], "formElemen", null, "" + careerPath.getPositionId(), position_value, position_key)%> * <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_POSITION_ID)%>
                                           </div>
                                       </td>
                                   </tr>
                                    <!-- Ari_20110903
                                        Menambah Level dan Emp_Category {
                                    -->
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.LEVEL)%>
                                            </div>
                                            <div class="divinput">
                                                <%   Vector level_value = new Vector(1, 1);
                                                Vector level_key = new Vector(1, 1);
                                                Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
                                                for (int i = 0; i < listLevel.size(); i++) {
                                                    Level level = (Level) listLevel.get(i);
                                                    level_value.add("" + level.getOID());
                                                    level_key.add(level.getLevel());
                                                }

                                            %>
                                            <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_LEVEL_ID], "formElemen", null, "" + careerPath.getLevelId(), level_value, level_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_LEVEL_ID)%>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <%=dictionaryD.getWord("CATEGORY")%>
                                            </div>
                                            <div class="divinput">
                                                <%   Vector empCategory_value = new Vector(1, 1);
                                                Vector empCategory_key = new Vector(1, 1);
                                                Vector listEmpCategory = PstEmpCategory.list(0, 0, "", "EMP_CATEGORY");
                                                for (int i = 0; i < listEmpCategory.size(); i++) {
                                                    EmpCategory empCategory = (EmpCategory) listEmpCategory.get(i);
                                                    empCategory_value.add("" + empCategory.getOID());
                                                    empCategory_key.add(empCategory.getEmpCategory());
                                                }

                                            %>
                                            <%= ControlCombo.draw(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID], "formElemen", null, "" + careerPath.getEmpCategoryId(), empCategory_value, empCategory_key)%>  <%= frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_EMP_CATEGORY_ID)%>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="caption">
                                                W. A. <%=dictionaryD.getWord(I_Dictionary.PROVIDER)%>
                                            </div>
                                            <div class="divinput">
                                                <%
                                                Vector provValue = new Vector(1, 1);
                                                Vector provKey = new Vector(1, 1);
                                                provKey.add("-");
                                                    provValue.add(String.valueOf(0));
                                                Vector listProvider = PstContactList.list(0, 0, "", ""+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+","+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]);
                                                for (int i = 0; i < listProvider.size(); i++) {
                                                    ContactList waContact = (ContactList) listProvider.get(i);
                                                    provKey.add(waContact.getCompName());
                                                    provValue.add(String.valueOf(waContact.getOID()));
                                                }

                                            %>
                                            <%=  ControlCombo.draw(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_PROVIDER_ID], "formElemen", null, "" + (careerPath.getProviderID()) /* !=0?careerPath.getProviderID():employee.getProviderID()) */, provValue, provKey) %> * <%= frmCareerPath.getErrorMsg(FrmEmployee.FRM_FIELD_PROVIDER_ID) %>
                                            </div>
                                        </td>  
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.DESCRIPTION)%>
                                            </div>
                                            <div class="divinput">
                                                <textarea cols="50" name="<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DESCRIPTION] %>"><%= careerPath.getDescription() %></textarea>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <!-- End Left Side -->
                            <!-- Right Side -->
                            <td valign="top" width="50%">
                                <table>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.HISTORY_TYPE)%>
                                            </div>
                                            <div class="divinput">
                                                <select name="<%=FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_TYPE]%>">
                                                    <%
                                                    for(int t=0; t<PstCareerPath.historyType.length; t++){
                                                        if (careerPath.getHistoryType() == t){
                                                            %>
                                                            <option selected="selected" value="<%=t%>"><%= PstCareerPath.historyType[t] %></option>
                                                            <%
                                                        } else {
                                                            %>
                                                            <option value="<%=t%>"><%= PstCareerPath.historyType[t] %></option>
                                                            <%
                                                        }
                                                    }
                                                    %>
                                                </select>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.HISTORY)%> <%=dictionaryD.getWord(I_Dictionary.GROUP)%>
                                            </div>
                                            <div class="divinput">
                                                <select name="<%=FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_HISTORY_GROUP]%>">
                                                    <%
                                                    for(int g=0; g<PstCareerPath.historyGroup.length; g++){
                                                        if (careerPath.getHistoryGroup() == g){
                                                            %>
                                                            <option selected="selected" value="<%=g%>"><%= PstCareerPath.historyGroup[g] %></option>
                                                            <%
                                                        } else {
                                                            %>
                                                            <option value="<%=g%>"><%= PstCareerPath.historyGroup[g] %></option>
                                                            <%
                                                        }
                                                    }
                                                    %>
                                                </select>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.WORK_FROM)%>
                                            </div>
                                            <div class="divinput">
                                                <%=	ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_FROM], careerPath.getWorkFrom() == null ? new Date() : careerPath.getWorkFrom(), 10, -55, "formElemen")%> to

                                            <%=	ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_WORK_TO], careerPath.getWorkTo() == null ? new Date() : careerPath.getWorkTo(), 10, -55, "formElemen")%> *
                                            <% String strStart = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_WORK_FROM);
                                                String strEnd = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_WORK_TO);
                                                if ((strStart.length() > 0) && (strEnd.length() > 0)) {
                                            %>
                                            <%= strStart%>
                                            <%} else {
                                                                                                                                                                                        if ((strStart.length() > 0) || (strEnd.length() > 0)) {%>
                                            <%= strStart.length() > 0 ? strStart : strEnd%>
                                            <% }
                                                }%>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                Nomor SK
                                            </div>
                                            <div class="divinput">
                                                <input type="text" name="<%=FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_NOMOR_SK]%>" value="<%= careerPath.getNomorSk() %>" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                Tanggal SK
                                            </div>
                                            <div class="divinput">
                                                <%
                                                /* Conversi Date to String */
                                                String DATE_FORMAT_NOW = "yyyy-MM-dd";
                                                Date date = careerPath.getTanggalSk();
                                                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                                                String stringDate = sdf.format(date );
                                                %>
                                                <input type="text" class="mydate" name="<%=FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_TANGGAL_SK]%>" value="<%= stringDate %>" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.GRADE)%> <%=dictionaryD.getWord(I_Dictionary.LEVEL)%>
                                            </div>
                                            <div class="divinput">
                                                <%
                                                Vector gd_value = new Vector();
                                                Vector gd_key = new Vector();
                                                gd_value.add("0");
                                                gd_key.add("SELECT");
                                                Vector listGradeLevel = PstGradeLevel.list(0, 0, "", PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]); 
                                                for (int i = 0; i < listGradeLevel.size(); i++) {
                                                    GradeLevel gradeLevel = (GradeLevel) listGradeLevel.get(i);
                                                    gd_key.add(gradeLevel.getCodeLevel());
                                                    gd_value.add(String.valueOf(gradeLevel.getOID()));
                                                }
                                                %>
                                                <%= ControlCombo.draw(FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_GRADE_LEVEL_ID], "formElemen", null, "" + careerPath.getGradeLevelId(), gd_value, gd_key)%>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> <%=dictionaryD.getWord(I_Dictionary.DOCUMENT)%>
                                            </div>
                                            <div class="divinput">
                                                <select name="<%= FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_EMP_DOC_ID] %>">
                                                    <option value="0">-SELECT-</option>
                                                    <%
                                                    Vector listEmpDoc = PstEmpDoc.list(0, 0, "", "");
                                                    if (listEmpDoc != null && listEmpDoc.size()>0){
                                                        for(int i=0; i<listEmpDoc.size(); i++){
                                                            EmpDoc empDoc = (EmpDoc)listEmpDoc.get(i);
                                                            if (careerPath.getEmpDocId()==empDoc.getOID()){
                                                                %>
                                                                <option selected="selected" value="<%=empDoc.getOID()%>"><%=empDoc.getDoc_title()%></option>
                                                                <%
                                                            } else {
                                                                %>
                                                                <option value="<%=empDoc.getOID()%>"><%=empDoc.getDoc_title()%></option>
                                                                <%
                                                            }
                                                        }
                                                    }
                                                    %>
                                                </select>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td valign="top">
                                            <div class="caption">
                                                <%=dictionaryD.getWord(I_Dictionary.CONTRACT_FROM)%>
                                            </div>
                                            <div class="divinput">
                                            <%= ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_FROM], careerPath.getContractFrom() == null ? new Date() : careerPath.getContractFrom(), 10, -55, "formElemen")%> to

                                            <%= ControlDate.drawDateWithStyle(frmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_CONTRACT_TO], careerPath.getContractTo() == null ? new Date() : careerPath.getContractTo(), 10, -55, "formElemen")%> *
                                            <% String strCStart = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_CONTRACT_FROM);
                                                String strCEnd = frmCareerPath.getErrorMsg(FrmCareerPath.FRM_FIELD_CONTRACT_TO);
                                                if ((strCStart.length() > 0) && (strCEnd.length() > 0)) {
                                            %>
                                            <%= strCStart%>
                                            <%} else {    

                                                if ((strCStart.length() > 0) || (strCEnd.length() > 0)) {%>
                                            <%= strCStart.length() > 0 ? strCStart : strCEnd%>
                                            <% }
                                                }%>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <!-- End Right Side -->
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <a style="color:#FFF" class="btn" href="javascript:cmdSave()">Save</a>&nbsp;<a style="color:#FFF" class="btn" href="javascript:cmdBack()">Batal</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                    </table>
                    
                   <% } %>
                   <!--
                    ================================================================
                    View List Data
                    ================================================================
                    -->
                    <!-- =============== RIWAYAT JABATAN =============== -->
                    <div class="title_part">Riwayat Jabatan</div>
                    <table class="tblStyle">
                        <%= PstCareerPath.getTableTitle(dictionaryD, ClientName) %>
                        <tr><td colspan="14" class="title_tbl_part">KARIR</td></tr>
                        <%= PstCareerPath.drawRiwayatJabatan(listCareerPath, privUpdate, privDelete, ClientName) %>
                        <tr><td colspan="14" class="title_tbl_part">PENUGASAN</td></tr>
                        <%= PstCareerPath.drawRiwayatJabatanPenugasan(listCareerPath, PstCareerPath.DETASIR_TYPE, privUpdate, privDelete, ClientName) %>
                        <%= PstCareerPath.drawRiwayatJabatanPenugasan(listCareerPath, PstCareerPath.PELAKSANA_TUGAS_TYPE, privUpdate, privDelete, ClientName) %>
                        <tr><td colspan="14" class="title_tbl_part">KARIR SEKARANG</td></tr>
                        <%= PstCareerPath.drawCareerNow(employee, listCareerPath, ClientName) %>
                    </table>
                    <!-- =============== *** =============== -->
                    <div>&nbsp;</div>
                    <div>&nbsp;</div>
                    <!-- =============== *** =============== -->
                    <!-- =============== RIWAYAT GRADE =============== -->
                    <div class="title_part">Riwayat Jabatan</div>
                    <table class="tblStyle">
                        <%= PstCareerPath.getTableTitle(dictionaryD, ClientName) %>
                        <tr><td colspan="14" class="title_tbl_part">GRADE</td></tr>
                        <%= PstCareerPath.drawRiwayatGrade(listCareerPath, privUpdate, privDelete, ClientName) %>
                        <tr><td colspan="14" class="title_tbl_part">GRADE SEKARANG</td></tr>
                        <%= PstCareerPath.drawGradeNow(employee, listCareerPath, ClientName) %>
                    </table>
                </div>
            </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>