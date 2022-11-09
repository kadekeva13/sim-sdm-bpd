<%-- 
    Document   : pay_payslip_group
    Created on : Jan 24, 2013, 10:55:12 AM
    Author     : Satrya Ramayu
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language ="java" %>
<!-- package java -->
<%@ page import ="java.util.*" %>
<%@page import="java.util.Vector"%>
<!-- package dimata -->
<%@ page import ="com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*" %>
<%@ page import ="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.gui.jsp.ControlList"%>
<%@ page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ page import="com.dimata.util.Command"%>
<%@ page import="com.dimata.harisma.entity.payroll.PstPaySlipGroup"%>
<%@ page import="com.dimata.harisma.entity.payroll.FrmPaySlipGroup"%>
<%@ page import="com.dimata.harisma.entity.payroll.PaySlipGroup"%>
<%@ page import="com.dimata.gui.jsp.ControlLine"%>
<%@ page import="com.dimata.harisma.form.payroll.CtrlPaySlipGroup"%>
<%@ page import="com.dimata.qdep.form.FRMMessage"%>
<%@ include file = "../../main/javainit.jsp" %>

<!-- Jsp Block -->
<%!    public String drawList(Vector objectClass, long companyId, boolean privUpdate) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("0");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("Slip Group", "");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Seluruh Karyawan", "");

        Vector lstData = ctrlist.getData();
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            PaySlipGroup paySlipGroup = (PaySlipGroup) objectClass.get(i);
            Vector rowx = new Vector();
            if (companyId == paySlipGroup.getOID()) {
                index = i;
            }
            if (privUpdate == true){
                rowx.add("<a href=\"javascript:cmdEdit('"+paySlipGroup.getOID()+"')\">"+paySlipGroup.getGroupName()+"</a>");
            } else {
                rowx.add(paySlipGroup.getGroupName());
            }
            String show = "Tidak";
            if (paySlipGroup.getShowAll() == 1){
                show = "Ya";
            }
            rowx.add(paySlipGroup.getGroupDesc());
            rowx.add(show);

            lstData.add(rowx);
        }
        return ctrlist.draw(index);
    }

%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidPayslipGroup = FRMQueryString.requestLong(request, "hidden_paySlipGroup_id");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = PstPaySlipGroup.fieldNames[PstPaySlipGroup.FLD_PAYSLIP_GROUP_NAME];

    CtrlPaySlipGroup ctrlPaySlipGroup = new CtrlPaySlipGroup(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCompany = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlPaySlipGroup.action(iCommand, oidPayslipGroup);
    /* end switch*/
    FrmPaySlipGroup frmPaySlipGroup = ctrlPaySlipGroup.getForm();

    /*count list All Position*/
    int vectSize = PstPaySlipGroup.getCount(whereClause);

    PaySlipGroup paySlipGroup = ctrlPaySlipGroup.getCompany();
    msgString = ctrlPaySlipGroup.getMessage();

    /*switch list PaySlipGroup*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        //start = PstPaySlipGroup.findLimitStart(paySlipGroup.getOID(),recordToGet, whereClause);
        oidPayslipGroup = paySlipGroup.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlPaySlipGroup.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listCompany = PstPaySlipGroup.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCompany.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCompany = PstPaySlipGroup.list(start, recordToGet, whereClause, orderClause);
    }


%>
<html>
    <head>
        <title>HARISMA - Master Data Pay Slip Group</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script language="JavaScript">


            function cmdAdd(){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value="0";
                document.frmpayslipgroup.command.value="<%=Command.ADD%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdAsk(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.ASK%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdConfirmDelete(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.DELETE%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }
            function cmdSave(){
                document.frmpayslipgroup.command.value="<%=Command.SAVE%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdEdit(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.EDIT%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdCancel(oidPayslipGroup){
                document.frmpayslipgroup.hidden_paySlipGroup_id.value=oidPayslipGroup;
                document.frmpayslipgroup.command.value="<%=Command.EDIT%>";
                document.frmpayslipgroup.prev_command.value="<%=prevCommand%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdBack(){
                document.frmpayslipgroup.command.value="<%=Command.BACK%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListFirst(){
                document.frmpayslipgroup.command.value="<%=Command.FIRST%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.FIRST%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListPrev(){
                document.frmpayslipgroup.command.value="<%=Command.PREV%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.PREV%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListNext(){
                document.frmpayslipgroup.command.value="<%=Command.NEXT%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.NEXT%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function cmdListLast(){
                document.frmpayslipgroup.command.value="<%=Command.LAST%>";
                document.frmpayslipgroup.prev_command.value="<%=Command.LAST%>";
                document.frmpayslipgroup.action="pay_payslip_group.jsp";
                document.frmpayslipgroup.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
                }

                //-------------- script control line -------------------
                function MM_swapImgRestore() { //v3.0
                    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
                }

                function MM_preloadImages() { //v3.0
                    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                    }

                    function MM_findObj(n, d) { //v4.0
                        var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                        if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                        for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                        if(!x && document.getElementById) x=document.getElementById(n); return x;
                    }

                    function MM_swapImage() { //v3.0
                        var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                            if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                    }
                    
                    function hideObjectForEmployee(){
                    }

                    function hideObjectForLockers(){
                    }

                    function hideObjectForCanteen(){
                    }

                    function hideObjectForClinic(){
                    }

                    function hideObjectForMasterdata(){
                    }

        </script>
<style type="text/css">
    .tblStyle {border-collapse: collapse;}
    .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
    body {color:#373737;}
    #menu_utama {padding: 9px 14px; border-bottom: 1px solid #CCC; font-size: 14px; background-color: #F3F3F3;}
    #menu_title {color:#0099FF; font-size: 14px;}

    .title_part {
        font-size: 12px;
        color:#0099FF; 
        background-color: #F7F7F7; 
        border-left: 1px solid #0099FF; 
        padding: 7px 9px;
    }

    body {background-color: #EEE;}
    .header {

    }
    .content-main {
        padding: 21px 32px;
        margin: 0px 23px 59px 23px;
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

    #div_item_sch {
        background-color: #EEE;
        color: #575757;
        padding: 5px 7px;
    }

    #record_count{
        font-size: 12px;
        font-weight: bold;
        padding-bottom: 9px;
    }
    #box-form {
        background-color: #EEE; 
        border-radius: 5px;
    }
    .form-style {
        font-size: 12px;
        color: #575757;
        border: 1px solid #DDD;
        border-radius: 5px;
    }
    .form-title {
        padding: 11px 21px;
        text-align: left;
        border-bottom: 1px solid #DDD;
        background-color: #FFF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        font-weight: bold;
    }
    .form-content {
        text-align: left;
        padding: 21px;
        background-color: #DDD;
    }
    .form-footer {
        border-top: 1px solid #DDD;
        padding: 11px 21px;
        background-color: #FFF;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    #confirm {
        padding: 13px 17px;
        background-color: #FF6666;
        color: #FFF;
        border-radius: 3px;
        font-size: 12px;
        font-weight: bold;
        visibility: hidden;
    }
    #btn-confirm {
        padding: 4px 9px; border-radius: 3px;
        background-color: #CF5353; color: #FFF; 
        font-size: 11px; cursor: pointer;
    }
    .footer-page {
        font-size: 12px;
    }
</style>
    </head>
    <body style="margin: 0">
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
            <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>Pay Slip Group</span>
        </div>
        <div class="content-main">
            <div class="tablecolor">
                <form name="frmpayslipgroup" method ="post" action="">
                    <input type="hidden" name="command" value="<%=iCommand%>">
                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                    <input type="hidden" name="start" value="<%=start%>">
                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                    <input type="hidden" name="hidden_paySlipGroup_id" value="<%=oidPayslipGroup%>">
                    <%
                    if (listCompany != null && listCompany.size()>0){
                        %><%= drawList(listCompany, oidPayslipGroup, true)%><%
                    } else {    
                    %> No Data <%
                    }
                    %>
                                                                                   
                    <div class="command">
                        <%
                            int cmd = 0;
                            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                cmd = iCommand;
                            } else {
                                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                    cmd = Command.FIRST;
                                } else {
                                    cmd = prevCommand;
                                }
                            }
                        %>
                        <% ctrLine.setLocationImg(approot + "/images");
                            ctrLine.initDefault();
                        %>
                        <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                    </div> 
                    <div>&nbsp;</div>
                    <%
                        if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmPaySlipGroup.errorSize() < 1)) {
                            if (true) {%>

                            <a href="javascript:cmdAdd()" style="color:#FFF" class="btn">Add New Pay Slip Group</a> 
                        <% }
                    }
                    %>

                    <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPaySlipGroup.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                        <tr>
                            <td class="listtitle"><%=oidPayslipGroup == 0 ? "Add" : "Edit"%> Pay Slip Group</td>
                        </tr>
                        <tr>
                            <td height="100%">
                                <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                    <tr align="left" valign="top">
                                        <td valign="top" width="25%">&nbsp;</td>
                                        <td width="75%" class="comment">*)entry required </td>
                                    </tr>
                                    <tr align="left" valign="top">
                                        <td valign="top" width="25%">
                                            Pay Slip Group Name</td>
                                        <td width="75%">
                                            <input type="text" name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_NAME]%>"  value="<%= paySlipGroup.getGroupName() == null ? "" : paySlipGroup.getGroupName()%>" class="elemenForm" size="30">
                                            *<%=frmPaySlipGroup.getErrorMsg(FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_NAME)%>
                                        </td>
                                    </tr>
                                    <tr align="left" valign="top">
                                        <td valign="top" width="25%">
                                            Description </td>
                                        <td width="75%">
                                            <textarea name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_DESCRIPTION]%>" class="elemenForm" cols="30" rows="3"><%= paySlipGroup.getGroupDesc() == null ? "" : paySlipGroup.getGroupDesc()%></textarea>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td valign="top" width="25%">Tampilkan untuk semua user</td>
                                        <td width="75%">
                                            <%  String checkNo = "";
                                                String checkYes = "";
                                                if (paySlipGroup.getShowAll()== 0) {
                                                    checkNo = "checked";
                                                } else {
                                                    checkYes = "checked";
                                                }
                                            %>
                                            <input type="radio" name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_SHOW_ALL]%>" value="0" style="border-color:'#DDDDDD'" <%=checkNo%>>&nbsp;No
                                            <input type="radio" name="<%=frmPaySlipGroup.fieldNames[FrmPaySlipGroup.FRM_FIELD_SHOW_ALL]%>" value="1" style="border-color:'#DDDDDD'" <%=checkYes%>>&nbsp;Yes
                                        </td>
                                    </tr>
                                </table >
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <%
                                    ctrLine.setLocationImg(approot + "/images");
                                    ctrLine.initDefault();
                                    ctrLine.setTableWidth("80%");
                                    String scomDel = "javascript:cmdAsk('" + oidPayslipGroup + "')";
                                    String sconDelCom = "javascript:cmdConfirmDelete('" + oidPayslipGroup + "')";
                                    String scancel = "javascript:cmdEdit('" + oidPayslipGroup + "')";
                                    ctrLine.setBackCaption("Back to List");
                                    ctrLine.setCommandStyle("buttonlink");
                                    ctrLine.setBackCaption("Back to List PaySlipGroup");
                                    ctrLine.setSaveCaption("Save PaySlipGroup");
                                    ctrLine.setConfirmDelCaption("Yes Delete PaySlipGroup");
                                    ctrLine.setDeleteCaption("Delete PaySlipGroup");

                                    if (true) {
                                        ctrLine.setConfirmDelCommand(sconDelCom);
                                        ctrLine.setDeleteCommand(scomDel);
                                        ctrLine.setEditCommand(scancel);
                                    } else {
                                        ctrLine.setConfirmDelCaption("");
                                        ctrLine.setDeleteCaption("");
                                        ctrLine.setEditCaption("");
                                    }

                                    if (true == false && true == false) {
                                        ctrLine.setSaveCaption("");
                                    }

                                    if (true == false) {
                                        ctrLine.setAddCaption("");
                                    }

                                    if (iCommand == Command.ASK) {
                                        ctrLine.setDeleteQuestion(msgString);
                                    }
                                %>
                                <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                            </td>
                        </tr>
                    </table>
                    <%}%>
                                                                                            
                  </form>
            </div>
        </div>
                                                                                              
    </body>
</html>

