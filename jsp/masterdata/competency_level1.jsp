<%-- 
    Document   : competency_level
    Created on : Feb 3, 2015, 12:29:40 PM
    Author     : Dimata 007 org
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetency"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.masterdata.CompetencyLevel"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetencyLevel"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCompetencyLevel"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCompetencyLevel"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!    // drawList(Vector, long); untuk menggambar table
    long comp_id = 0;
    public String drawList(Vector objectClass, long oidCompetencyLevel) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Competency Name", "");
        ctrlist.addHeader("Score Value", "");
        ctrlist.addHeader("Level Min", "");
        ctrlist.addHeader("Level Max", "");
        ctrlist.addHeader("Level Unit", "");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Delete", "");
        /////////
        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ////////


        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;

        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            // membuat object WarningReprimandAyat berdasarkan objectClass ke-i
            
            CompetencyLevel competencyLevel = (CompetencyLevel) objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();

            no = no + 1;
            rowx.add("" + no);
            
            Competency compe = new Competency();
            try {
                compe = PstCompetency.fetchExc(competencyLevel.getCompetencyId());

            } catch (Exception e) {
            }

            if (oidCompetencyLevel == competencyLevel.getOID()){
                comp_id = competencyLevel.getCompetencyId();
            }
            
            rowx.add(compe.getCompetencyName());
            rowx.add(String.valueOf(competencyLevel.getScoreValue()));
            rowx.add(String.valueOf(competencyLevel.getLevelMin()));
            rowx.add(String.valueOf(competencyLevel.getLevelMax()));
            rowx.add(competencyLevel.getLevelUnit());
            rowx.add(competencyLevel.getDescription());
            rowx.add("<a href=\"javascript:cmdAsk('"+competencyLevel.getOID()+"')\">&times;&nbsp;Delete</a>");
            lstData.add(rowx);
            // menambah ID ke list LinkData
            lstLinkData.add(String.valueOf(competencyLevel.getOID()));

        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
    
    public String getCompetencyName(long comId){
        String str = "";
        try {
            Competency compe = PstCompetency.fetchExc(comId);
            str = compe.getCompetencyName();
        } catch (Exception e) {
            System.out.println("getCompetencyName=>"+e.toString());
        }
        return str;
    }

%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidCompetencyLevel = FRMQueryString.requestLong(request, "hidden_comp_level_id");
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    
    CtrlCompetencyLevel ctrCompetencyLevel = new CtrlCompetencyLevel(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCompetencyLevel = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrCompetencyLevel.action(iCommand, oidCompetencyLevel);
    /* end switch*/
    FrmCompetencyLevel frmCompetencyLevel = ctrCompetencyLevel.getForm();

    /*count list All Position*/
    int vectSize = PstCompetencyLevel.getCount(whereClause); 
    CompetencyLevel competencyLevel = ctrCompetencyLevel.getCompetencyLevel();
    msgString = ctrCompetencyLevel.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstCompetencyLevel.findLimitStart(competencyLevel.getOID(), recordToGet, whereClause, orderClause);
        oidCompetencyLevel = competencyLevel.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrCompetencyLevel.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listCompetencyLevel = PstCompetencyLevel.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCompetencyLevel.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCompetencyLevel = PstCompetencyLevel.list(start, recordToGet, whereClause, orderClause);
    }


%>
<%

    /*
     * Some code of ComboBox
     */

    String CtrOrderClause = PstCompetency.fieldNames[PstCompetency.FLD_COMPETENCY_ID];
    Vector vectListComp = PstCompetency.list(0, 100, "", CtrOrderClause);
    Vector valComp = new Vector(1, 1); //hidden values that will be deliver on request (oids) 
    Vector keyComp = new Vector(1, 1); //texts that displayed on combo box
    valComp.add("0");
    keyComp.add("All Competency");
    for (int c = 0; c < vectListComp.size(); c++) {
        Competency comp = (Competency) vectListComp.get(c);
        valComp.add("" + comp.getOID());
        keyComp.add(comp.getCompetencyName());
    }
    
    Vector valLevelUnit = new Vector(1,1);
    Vector keyLevelUnit = new Vector(1,1);
    valLevelUnit.add("0");
    valLevelUnit.add("1");
    valLevelUnit.add("2");
    valLevelUnit.add("3");
    keyLevelUnit.add("point");
    keyLevelUnit.add("Day");
    keyLevelUnit.add("Month");
    keyLevelUnit.add("Year");

%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Competency Level</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 

        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}            
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
            
            #btnX {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            #btnX:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            #titleTd {background-color: #3cb0fd; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
            #subtitle {padding: 2px 7px; font-weight: bold; background-color: #FFF; border-left: 1px solid #3498db;}
            #td1{ padding: 3px;}
            #td2{ padding: 3px 7px 3px 5px;}
            
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
        </style>
        <script language="JavaScript">
            function getCmd(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.action = "competency_level.jsp";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.PREV%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.LAST%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value="<%=Command.ASK%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>.hidden_comp_level_id.value = oid;
                getCmd();
            }

        </script>
        <!-- #EndEditable --> 
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
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
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    

                        <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                            <tr> 
                                <td width="100%" colspan="3" valign="top"> 
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td height="20"> <div id="menu_utama">Competency Level</div> </td>
                                        </tr>


                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color:#EEE;">
                                    
                                <form name="<%=FrmCompetencyLevel.FRM_NAME_COMPETENCY_LEVEL%>" method="POST" action="">

                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="hidden_comp_level_id" value="<%=oidCompetencyLevel%>">
                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                    <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr>
                                            <td>
                                                <div id="mn_utama">List Competency Level</div>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <%
                                        if (iCommand == Command.ASK){
                                        %>
                                        <tr>
                                            <td>
                                                <span id="confirm">
                                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                                    <button id="btn1" onclick="javascript:cmdDelete('<%=oidCompetencyLevel%>')">Yes</button>
                                                    &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                </span>
                                            </td>
                                        </tr>
                                        <%
                                        }
                                        %>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <%if (listCompetencyLevel != null && listCompetencyLevel.size() > 0) {%>
                                            <td colspan="3">
                                                <!-- untuk drawlist -->
                                                <!-- membuat tabel yang ditulis dari method drowList(); -->
                                                <%=drawList(listCompetencyLevel, oidCompetencyLevel)%>
                                            </td>

                                            <%} else {%>
                                            <td>
                                                record not found
                                            </td>
                                            <%}%>
                                        </tr>
                                        <tr>
                                            <td>
                                                <%
                                                if (listCompetencyLevel != null && listCompetencyLevel.size() > 0) {
                                                    for(int i=0; i<listCompetencyLevel.size(); i++){
                                                        CompetencyLevel comLevel = (CompetencyLevel)listCompetencyLevel.get(i);
                                                %>
                                                <table class="tblStyle">
                                                    <tr>
                                                        <td class="title_tbl">1</td>
                                                        <td class="title_tbl"><%=getCompetencyName(comLevel.getCompetencyId())%></td>
                                                    </tr>
                                                    <tr>
                                                        <td>&nbsp;</td>
                                                        <td>
                                                            <table class="tblStyle">
                                                                <tr>
                                                                    <td class="title_tbl">Level Min</td>
                                                                    <td class="title_tbl">Level Max</td>
                                                                    <td class="title_tbl">Level Unit</td>
                                                                    <td class="title_tbl">Score Value</td>
                                                                    <td class="title_tbl">Description</td>
                                                                    <td class="title_tbl">&nbsp;</td>
                                                                </tr>
                                                                <tr>
                                                                    <td>5</td>
                                                                    <td>7</td>
                                                                    <td>Point</td>
                                                                    <td>7</td>
                                                                    <td>-</td>
                                                                    <td><button id="btnX">&times;</button></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>5</td>
                                                                    <td>7</td>
                                                                    <td>Point</td>
                                                                    <td>7</td>
                                                                    <td>-</td>
                                                                    <td><button id="btnX">&times;</button></td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <%      }
                                                    } 
                                                %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <span class="command"> 
                                                    <%
                                                        int cmd = 0;
                                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                            cmd = iCommand;
                                                        } else {
                                                            if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                cmd = Command.FIRST;
                                                            } else {
                                                                if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCompetencyLevel == 0)) {
                                                                    cmd = PstCompetencyLevel.findLimitCommand(start, recordToGet, vectSize);
                                                                } else {
                                                                    cmd = prevCommand;
                                                                }
                                                            }
                                                        }
                                                    %>
                                                    <% 
                                                        ctrLine.setLocationImg(approot + "/images");
                                                        ctrLine.initDefault();
                                                    %>
                                                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                </span>
                                            </td><td>&nbsp;</td><td>&nbsp;</td>
                                        </tr>

                                        <%if (!(iCommand == Command.ADD || iCommand == Command.EDIT)) {%>
                                        <tr>

                                            <td>

                                                <button id="btn" onclick="cmdAdd()">Add New</button>

                                            </td><td>&nbsp;</td><td>&nbsp;</td>

                                        </tr>
                                        <%}%>
                                        <!-- inputan user -->
                                        <%if (iCommand == Command.ADD || iCommand == Command.EDIT) {%>
                                        <% if (iCommand == Command.ADD){%>
                                        <tr><td><div id="mn_utama">Add Competency Level </div></td></tr>
                                        <% } else { %>
                                        <tr><td><div id="mn_utama">Edit Competency Level</div></td></tr>
                                        <% } %>
                                        <tr><td>
                                        <table>
                                        <tr>
                                            <td>Competency</td>
                                            <td>
                                                <%=ControlCombo.draw(frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_COMPENTENCY_ID], null, String.valueOf(comp_id), valComp, keyComp, "", "cbComp")%>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Score</td>
                                            <td>
                                                <input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_SCORE_VALUE]%>" placeholder="Score value" value="<%=competencyLevel.getScoreValue()%>" />
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Level Minimum</td>
                                            <td>
                                                <input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_MIN]%>" placeholder="Level min" value="<%=competencyLevel.getLevelMin()%>" />
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Level Maximum</td>
                                            <td>
                                                <input type="text" name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_MAX]%>" placeholder="Level max" value="<%=competencyLevel.getLevelMax()%>" />
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Unit</td>
                                            <td>
                                                <%=ControlCombo.draw(frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_LEVEL_UNIT], null, "-", keyLevelUnit, keyLevelUnit, "", "cbComp")%>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Description</td>
                                            <td>
                                                <textarea name="<%=frmCompetencyLevel.fieldNames[frmCompetencyLevel.FRM_FIELD_DESCRIPTION]%>" placeholder="Description"><%=competencyLevel.getDescription()%></textarea>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>

                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>
                                                <button id="btn" onclick="cmdSave()">Save</button>
                                                <button id="btn" onclick="cmdBack()">Back</button>
                                            </td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        </table>
                                            </td></tr>
                                        <%}%> 

                                        <tr><td>&nbsp;</td>
                                            <td colspan="3">&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                            <tr>
                                <td valign="bottom">
                                    <!-- untuk footer -->
                                    <%@include file="../../footer.jsp" %>
                                </td>

                            </tr>
                            <%} else {%>
                            <tr> 
                                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                                    <%@ include file = "../../main/footer.jsp" %>
                                    <!-- #EndEditable --> </td>
                            </tr>
                            <%}%>
                        </table>
                        </body>
                        <!-- #BeginEditable "script" --> <script language="JavaScript">
                            var oBody = document.body;
                            var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
                        </script>

                        <!-- #EndEditable --> <!-- #EndTemplate -->
                        </html>
