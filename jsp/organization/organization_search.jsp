<%-- 
    Document   : organization_search
    Created on : Jan 25, 2016, 9:58:05 AM
    Author     : Dimata 007
    Description :
        1) Memilih Struktur Organisasi pada input combobox
        2) Daftar Divisi atau cabang akan ditampilkan sebagai pilihan
        3) Submit, maka akan menampilkan view Struktur Organisasi pada halaman baru
--%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
/*  function ini berfungsi untuk menampilkan list template oraganisasi */
    public String getOrganizationList(int adminStatus, long positionId){
        
        String str = "";
        Vector list = new Vector();
        if (adminStatus == 1){
            list = PstStructureTemplate.list(0, 0, "", "");
        } else {
            list = PstStructureTemplate.listWithAccess(0, 0, "acc."+PstStructurePositionAccess.fieldNames[PstStructurePositionAccess.FLD_POSITION_ID]+"="+positionId,"");
        }
        if (list != null && list.size()>0){
            for(int i=0; i<list.size(); i++){
                StructureTemplate template = (StructureTemplate)list.get(i);
                str += "<option value=\""+template.getOID()+"\">"+template.getTemplateName()+"</option>";
            }
        }
        return str;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization - Search</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #tbl_form {
                
            }
            #tbl_form td {
                font-size: 12px;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; border-bottom: 1px solid #DDD;}
            #menu_sub {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #FFF; margin-bottom: 5px; } 
            #note {
                font-size: 12px;
                font-weight: bold;
                padding: 6px 9px;
                border-left: 1px solid #007fba;
                color: #575757;
            }
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {
                background-color: #EEE;
                /*
                background-image: url("<=approot>/stylesheets/images/noisy_grid.png");
                background-repeat: repeat;*/
            }
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
            .box {
                margin: 3px 0px;
                background-color: #FFF;
                border-radius: 3px;
                border: 1px solid #DDD;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            
            .tips {
                background-color: #FFF;
                border-radius: 3px;
                padding: 7px 9px;
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
            
            #floatleft {
                font-size: 12px;
                color: #474747;
                padding: 7px 11px;
                background-color: #FFF;
                border-radius: 3px;
                margin: 5px 0px;
                cursor: pointer;
            }
            #floatleft:hover {
                color: #FFF;
                background-color: #474747;
            }
            .caption {
                font-size: 13px;
                color: #575757;
                font-weight: bold;
                padding: 9px 13px 9px 13px;
                border-bottom: 1px solid #DDD;
            }
            .box-item {
                color: #474747;
                padding: 7px 13px;
                font-size: 11px;
                background-color: #FFF;
                margin: 1px 0px;
                cursor: pointer;
            }
            .box-item:hover {
                color: #373737;
                background-color: #EEE;
            }
            .box-item-active {
                color: #F5F5F5;
                padding: 9px 13px;
                font-size: 11px;
                background-color: #007fba;
                margin: 1px 0px;
                cursor: pointer;
            }
        </style>
        <script type="text/javascript">
            function sendRequest(url) {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", url, true);
                xmlhttp.send();
            }
            function loadList(nilai){
                var url = "organization_available.jsp?select_structure="+nilai;
                sendRequest(url);
            }
            function cmdViewByDiv(struct, divisionId){
                document.frm.select_structure.value = struct;
                document.frm.division_id.value = divisionId;
                document.frm.department_id.value = "0";
                document.frm.section_id.value = "0";
                document.frm.action = "organization_view.jsp";
                document.frm.target = "_blank";
                document.frm.submit();
            }
            function cmdViewByDept(struct, departmentId){
                document.frm.select_structure.value = struct;
                document.frm.division_id.value = "0";
                document.frm.department_id.value = departmentId;
                document.frm.section_id.value = "0";
                document.frm.action = "organization_view.jsp";
                document.frm.target = "_blank";
                document.frm.submit();
            }
            function cmdViewBySect(struct, sectionId){
                document.frm.select_structure.value = struct;
                document.frm.division_id.value = "0";
                document.frm.department_id.value = "0";
                document.frm.section_id.value = sectionId;
                document.frm.action = "organization_view.jsp";
                document.frm.target = "_blank";
                document.frm.submit();
            }
        </script>
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
        function pageLoad(){ $(".mydate").datepicker({ dateFormat: "yy-mm-dd" }); } 
	</script>
    </head>
    <body onload="pageLoad()">
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
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
            <span id="menu_title">Organization <strong style="color:#333;"> / </strong>Search</span>
        </div>
        <div class="content-main">
            <form name="frm" method="post" action="" target="_blank">
                <input type="hidden" name="division_id" value="" />
                <input type="hidden" name="department_id" value="" />
                <input type="hidden" name="section_id" value="" />
                <table cellpadding="5" cellspacing="5">
                    <tr>
                        <td valign="middle">
                            <div id="caption">Pilih Struktur</div>
                            <div id="divinput">
                                <select name="select_structure" style="padding:3px 7px" onchange="loadList(this.value)">
                                    <option value="0">-SELECT-</option>
                                    <%=getOrganizationList(appUserSess.getAdminStatus(), emplx.getPositionId())%>
                                </select>
                            </div>
                        </td>
                        <td valign="middle">
                            <div id="caption">Period</div>
                            <div id="divinput">
                                <input type="text" style="padding:5px;" class="mydate" name="period_from" /> To <input type="text" style="padding:5px;" class="mydate" name="period_to" />
                            </div>
                        </td>
                        <td valign="middle">
                            
                        </td>
                    </tr>
                </table>
                
                <div id="div_respon"></div>
            </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
