<%@page import="com.sun.org.apache.xml.internal.security.Init"%>
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.system.*" %>
<%@ page import="com.dimata.system.session.system.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../main/checkuser.jsp" %>
<%!
 Vector vSysProp = SessSystemProperty.getvSysProp();
/*****/

%>

<%!
public String drawList(int iCommand, FrmSystemProperty frmSystemProperty, String groupName, long lOid)
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgensell");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.setTitle(groupName + " Properties");	
	ctrlist.dataFormat("Name","20%","center","center");
	ctrlist.dataFormat("Value","30%","left","left");
	ctrlist.dataFormat("Value Type","10%","left","left");	
	ctrlist.dataFormat("Description","40%","left","left");	

	String editValPre = "<input type=\"text\" name=\"" + frmSystemProperty.fieldNames[frmSystemProperty.FRM_VALUE] +"\" size=\"100\" value=\"";
	String editValSup = "\"> * <a href=\"javascript:cmdUpdate('"+lOid+"')\"> Save </a>"+ frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_NAME);

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData 		= ctrlist.getData();
	Vector lstLinkData 	= ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	try{
		if((vSysProp!=null) && (vSysProp.size()>0)){  
			for(int i=0; i<vSysProp.size(); i++){
				 Vector rowx = new Vector();
				 SystemProperty sysProp2 = (SystemProperty)vSysProp.get(i);
                                 if(sysProp2!=null){
                                    SystemProperty sysPropX = PstSystemProperty.fetchByName(sysProp2.getName());                                 
                                    if(sysPropX!=null && sysPropX.getOID()!=0){
                                        sysProp2.setOID(sysPropX.getOID());
                                        sysProp2.setValue(sysPropX.getValue());
                                    } else{
                                        PstSystemProperty.insert(sysProp2);
                                    }
                                 }
                                 if(sysProp2.getValue()==null || sysProp2.getValue().length()<1  ){
                                    rowx.add("<blink>"+sysProp2.getName()+"</blink>");
                                 } else{
                                    rowx.add( sysProp2.getName());
                                 }

				 if(iCommand==Command.ASSIGN && lOid==sysProp2.getOID()){
					rowx.add("<b>"+editValPre + sysProp2.getValue() + editValSup+"</b>");
				 }else{
					rowx.add("<a href=\"javascript:cmdAssign('"+sysProp2.getOID()+"')\">"+sysProp2.getValue()+"</a>");
				 }

				 rowx.add(sysProp2.getValueType());
				 rowx.add(sysProp2.getNote());

				 lstData.add(rowx); 
				 lstLinkData.add(String.valueOf(sysProp2.getOID()));
			}
		}
	}catch(Exception e){
		System.out.println("Exc : " + e.toString());
	} 
	return ctrlist.draw();
}
%>

<%   
int iCommand = FRMQueryString.requestCommand(request);
long lOid = FRMQueryString.requestLong(request, "oid");
CtrlSystemProperty ctrlSystemProperty = new CtrlSystemProperty(request);
ctrlSystemProperty.action(iCommand, lOid, request);

SystemProperty sysProp = ctrlSystemProperty.getSystemProperty();
FrmSystemProperty frmSystemProperty = ctrlSystemProperty.getForm();
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>System Property</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee()
{    
} 
 
function hideObjectForLockers()
{ 
}

function hideObjectForCanteen()
{
}

function hideObjectForClinic()
{
}

function hideObjectForMasterdata()
{
}

function showObjectForMenu()
{
}
	
function cmdList() 
{
  document.frmData.command.value="<%= Command.LIST %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdLoad() 
{
  document.frmData.command.value="<%= Command.LOAD %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdNew() 
{
  document.frmData.command.value="<%= Command.ADD %>";          
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdEdit(oid) 
{
  document.frmData.command.value="<%= Command.EDIT %>";                    
  document.frmData.oid.value = oid;
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdAssign(oid) 
{
  document.frmData.command.value="<%= Command.ASSIGN %>";       
  document.frmData.oid.value= oid;          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdUpdate(oid) 
{
  document.frmData.command.value="<%= Command.UPDATE %>";          
  document.frmData.oid.value = oid;
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}	
</SCRIPT>
<script type="text/javascript">
    
            var valueCheck = 0;
            
            function putValue(v){
                valueCheck = v;
            }
            
           function loadList(sysprop_cari) {
                if (sysprop_cari.length == 0) { 
                    sysprop_cari = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("sysprop_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "sysprop-ajax.jsp?sysprop_cari=" + sysprop_cari +"&type_name=0", true);
                xmlhttp.send();
                
            }
            
            function prepare(){
                loadList("0");
            }
            
            function loadByType(type_name) {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("sysprop_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "sysprop-ajax.jsp?type_name=" + type_name, true);
                xmlhttp.send();
            }
            
            
        </script>
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 11px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
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
            
            .navbar li a {
                color : #F5F5F5;
                text-decoration: none;
            }
            
            .navbar li a:hover {
                color:#FFF;
            }
            
            .navbar li a:active {
                color:#FFF;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
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
            .box {
                margin: 17px 7px;
                background-color: #FFF;
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
<!-- #EndEditable --> 
</head>
<body onload="prepare()"> 
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
            <%} %>
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">System <strong style="color:#333;"> / </strong>System Properties</span>
        </div>
        <div class="content-main"> 
         <form name="frmData" method="post" action="">
         <input type="hidden" name="command" value="0">
         <input type="hidden" id ="oid" name="oid" value="<%=lOid%>">
         <table width="100%" border="0" cellspacing="6" cellpadding="0">
         <tr> 
         <td> 
         <%
            String cbxName = FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_GROUP];
								
            String groupName = FRMQueryString.requestString(request, cbxName);
            if(groupName == null || groupName.equals("")) groupName = "";
								
            Vector grs = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "> ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ", true);
            Vector val = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "", "", false);
											
            String strChange = "onChange=\"javascript:cmdList()\"";
            out.println("&nbsp;"+ControlCombo.draw(cbxName, "formElemen", null, groupName, val, grs, strChange));
            Vector vctData = PstSystemProperty.listByGroup(groupName);							
        %>
                                        
                                        </tr>
                                       <tr> 
                                          <td>  <input type="text" style="padding:6px 7px" name="sysprop_cari" onkeyup="loadList(this.value)" placeholder="Searching..." size="70" /> 
                                            <select name="type_name" style="padding:4px 7px" onchange="loadByType(this.value)">
                                            <option value="0">-Select Modul-</option>
                                            <option value="1">Master</option>
                                            <option value="2">Attendance</option>
                                            <option value="3">Payroll</option>
                                            <option value="4">Organization</option>
                                            <option value="5">Training</option>
                                            <option value="6">Leave</option>
                                            <option value="7">Candidate</option>
                                            </select>
                                              <div>&nbsp;</div>
                                            <div id="sysprop_respon"></div> </td>
                                        </tr>
                                        <tr> 
                                          <td align="right"><%="<i>"+ctrlSystemProperty.getMessage()+"</i>"%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="right"> 
                                            <% 
											if(iCommand==Command.ASSIGN && privUpdate)
											{
												out.println("<a href=\"javascript:cmdUpdate('"+lOid+"')\">Update Value</a> | <a href='javascript:cmdList()'>Cancel</a> | ");
											}
											
											if(privAdd)
											{
												out.println("<a href=\"javascript:cmdNew()\">New System Property</a> | ");
											}
											
											out.println("<a href=\"javascript:cmdLoad()\">Load New value</a>&nbsp;");
											%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="left"> N O T E : <br>
                                            - Use "\\" character when you want 
                                            to input "\" character in value field.<br>
                                            - Click "Load new value" link when 
                                            property it's updated. </td>
                                        </tr>
                                      </table>
                                    </form>
        </div>
                                          <table>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
<%--     <SCRIPT>
// Before you reuse this script you may want to have your head examined
// 
// Copyright 1999 InsideDHTML.com, LLC.  

function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("BLINK")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT> */ --%>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
