<%-- 
    Document   : company
    Created on : Sep 30, 2011, 3:56:51 PM
    Author     : Wiweka
--%>
<%@page import="com.dimata.harisma.entity.recruitment.PstRecrApplication"%>
<%@page import="com.dimata.harisma.entity.recruitment.RecrApplication"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.log.ChangeValue"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  company.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Ari_20110930
             * @version  		: -
             */

            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 			: [output ...]
             *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
ChangeValue changeValue = new ChangeValue();
long empDocListMutationId = FRMQueryString.requestLong(request, "empDocListMutationId");
long empDocId = FRMQueryString.requestLong(request, "empDocId");

EmpDoc empDoc = new EmpDoc();
try{
    empDoc = PstEmpDoc.fetchExc(empDocId);
}catch(Exception e){}

long employeeId = FRMQueryString.requestLong(request, "employeeId");

long companyId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_COMPANY_ID]);
long divisionId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DIVISION_ID]);
long departmentId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DEPARTMENT_ID]);
long sectionId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_SECTION_ID]);

long empCatId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_CAT_ID]);
long positionId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_POSITION_ID]);
long levelId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_LEVEL_ID]);
String empNum = FRMQueryString.requestString(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_NUM]);
long gradeLevelId = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_GRADE_LEVEL_ID]);

long historyType = 0;
long historyGroup = 0;
try{
     historyType = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_TYPE]);
     historyGroup = FRMQueryString.requestLong(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_GROUP]);

} catch (Exception e){ 
}

Date workTo = FRMQueryString.requestDate(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_TO]);

Date workFrom = FRMQueryString.requestDate(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM]);

long oidEmpDocField = FRMQueryString.requestLong(request,"oidEmpDocField");
String ObjectName = FRMQueryString.requestString(request,"ObjectName");
String ObjectType = FRMQueryString.requestString(request,"ObjectType");
String ObjectClass = FRMQueryString.requestString(request,"ObjectClass");
String ObjectStatusfield = FRMQueryString.requestString(request,"ObjectStatusfield");

int tipeDoc = FRMQueryString.requestInt(request, FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_TIPE_DOC]);


int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");


/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = ""+PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID]+"="+empDocId+" AND "+PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMPLOYEE_ID]+"="+employeeId+" AND "+PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_OBJECT_NAME]+"=\""+ObjectName+"\"";
String orderClause = "";
long oidEmployee = 0;
Employee employee = new Employee();
try{
    employee = PstEmployee.fetchExc(employeeId);
    Date fromdate = new Date();
    Date todate = new Date();
    todate.setMonth(todate.getMonth()+1);
    PstPaySlip.getDocumentComponent(fromdate, todate, employeeId);oidEmployee = employee.getOID();
    oidEmployee = employee.getOID();
    } catch (Exception e) {
        System.out.println(e.toString());
    }
/* cari ke surat lamaran */
RecrApplication appLetter = new RecrApplication();
if (employee.getOID() == 0){
    try {
        appLetter = PstRecrApplication.fetchExc(employeeId);
        oidEmployee = appLetter.getOID();
    } catch(Exception e){
        System.out.println(e.toString());
    }
}
        
CtrlEmpDocListMutation ctrlEmpDocListMutation = new CtrlEmpDocListMutation(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpDocListMutation = new Vector(1,1);

/*switch statement */
iErrCode = ctrlEmpDocListMutation.action(iCommand , empDocListMutationId);
/* end switch*/
FrmEmpDocListMutation frmEmpDocListMutation = ctrlEmpDocListMutation.getForm();

/*count list All Position*/
int vectSize = PstEmpDocListMutation.getCount(whereClause);

EmpDocListMutation empDocListMutation = ctrlEmpDocListMutation.getEmpDocListMutation();

msgString =  ctrlEmpDocListMutation.getMessage();
 


/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(division.getOID(),recordToGet, whereClause);
	empDocListMutationId = empDocListMutation.getOID();
}


/* get record to display */
listEmpDocListMutation = PstEmpDocListMutation.list(start,recordToGet, whereClause , orderClause);
if (listEmpDocListMutation.size() > 0){
empDocListMutation = (EmpDocListMutation)listEmpDocListMutation.get(0);
empDocListMutationId=empDocListMutation.getOID();
                                               
                                             
}
Date dateF = new Date(); 

//Date dateT = new Date(); 
Date dateT = null;
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    try{
        //dateF = employee.getCommencingDate();
        dateF = empDoc.getDate_of_issue();
        if (empDocListMutation.getWorkFrom() !=null && empDocListMutation.getOID() != 0){
         dateF = empDocListMutation.getWorkFrom();
         dateT = empDocListMutation.getWorkTo();
        }
    } catch (Exception e){

    }
if (empDocListMutation.getOID() == 0){
    empDocListMutation.setCompanyId(employee.getCompanyId());
    empDocListMutation.setDivisionId(employee.getDivisionId());
    empDocListMutation.setDepartmentId(employee.getDepartmentId());
    empDocListMutation.setSectionId(employee.getSectionId());
    
    
                        empDocListMutation.setPositionId(employee.getPositionId());
                        empDocListMutation.setEmpCatId(employee.getEmpCategoryId());
                        empDocListMutation.setLevelId(employee.getLevelId());
                        empDocListMutation.setGradeLevelId(employee.getGradeLevelId());
                        empDocListMutation.setEmpNum(employee.getEmployeeNum());
    
}
if (tipeDoc != 0 ){
    empDocListMutation.setTipeDoc(tipeDoc);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data </title>
        <script language="JavaScript">
    
function cmdUpdateDep(){
	document.empDocListMutation.command.value="<%=String.valueOf(Command.ADD)%>";
	document.empDocListMutation.action="EmpDocListMutation.jsp"; 
	document.empDocListMutation.submit();
}

function cmdAddComponent(){
        document.empDocListMutation.command.value="<%=String.valueOf(Command.GOTO)%>";
	window.open("<%=approot%>/employee/document/search_component2.jsp?formName=empDocListMutation&empPathId=<%=frmEmpDocListMutation.fieldNames[frmEmpDocListMutation.FRM_FIELD_EMPLOYEE_ID]%>", "List Mutation Comp", "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        window.focus();
        document.empDocListMutation.target="List Mutation Comp";
        document.empDocListMutation.action="<%=approot%>/employee/document/search_component2.jsp?formName=empDocListMutation&empPathId=<%=frmEmpDocListMutation.fieldNames[frmEmpDocListMutation.FRM_FIELD_EMPLOYEE_ID]%>", "List Mutation Comp", "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes";
        document.empDocListMutation.submit();
       
}


function deptChange() {
    document.empDocListMutation.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.empDocListMutation.hidden_goto_dept.value = document.empDocListMutation.<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DEPARTMENT_ID]%>.value;
    document.empDocListMutation.action = "EmpDocListMutation.jsp";
    document.empDocListMutation.submit();
}

function cmdSave(){
    alert("1");
                document.empDocListMutation.command.value="<%=Command.SAVE%>";
                document.empDocListMutation.prev_command.value="<%=prevCommand%>";
                document.empDocListMutation.action="EmpDocListMutation.jsp";
                 alert("EmpDocListMutation");
                document.empDocListMutation.submit();
            }

function cmdSearch() {
    document.empDocListMutation.command.value = "<%=String.valueOf(Command.LIST)%>";									
    document.empDocListMutation.action = "EmpDocListMutation.jsp";
    document.empDocListMutation.submit();
}

 function cmdRefresh(){
                document.empDocListMutation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.empDocListMutation.action="EmpDocListMutation.jsp";
                document.empDocListMutation.submit();
            }

function onAdd() {
    self.close();
    MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg');
}

        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
</script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="<%=iCommand==Command.SAVE?"onAdd()":"MM_preloadImages('"+approot+"/images/BtnNewOn.jpg')" %>" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%//@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
            <tr>
                <td width="88%" valign="top" align="left">
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                    <!-- #BeginEditable "contenttitle" -->
                                                    Master Data &gt; <%=dictionaryD.getWord(I_Dictionary.COMPANY)%><!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="empDocListMutation" method ="post" action="">
                                                                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                                                                        
                                                                                        <input type="hidden" name="empDocListMutationId" value="<%=String.valueOf(empDocListMutationId)%>">
                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_DOC_LIST_MUTATION_ID]%>" value="<%=String.valueOf(empDocListMutationId)%>">
                                                                                        
                                                                                        <input type="hidden" name="empDocId" value="<%=String.valueOf(empDocId)%>">
                                                                                        <input type="hidden" name="employeeId" value="<%=String.valueOf(employeeId)%>">
                                                                                        <input type="hidden" name="ObjectName" value="<%=String.valueOf(ObjectName)%>">
                                                                                        
                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_DOC_ID]%>" value="<%=String.valueOf(empDocId)%>">
                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMPLOYEE_ID]%>" value="<%= oidEmployee %>">
                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_OBJECT_NAME]%>" value="<%=String.valueOf(ObjectName)%>">
                                                                                        
                                                                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                          <tr>
                                                                                                <td>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr> 
                                                                                                    <td width="19%">Mutation Type</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <%
                                                                                                    Vector mt_value = new Vector();
                                                                                                    Vector mt_key = new Vector();
                                                                                                    //hg_key.add("select");
                                                                                                    //hg_value.add(""+0);
                                                                                                    for (int i = 0; i < PstEmpDocListMutation.tipeDoc.length; i++) {
                                                                                                        mt_key.add(PstEmpDocListMutation.tipeDoc[i]);
                                                                                                        mt_value.add(""+i);
                                                                                                    }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_TIPE_DOC],"formElemen",null,""+(empDocListMutation.getTipeDoc()), mt_value, mt_key, "onChange=\"javascript:cmdRefresh()\"") %> </td>
                                                                                                  </tr>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Name</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                        <%
                                                                                                        if (employee.getOID() != 0){
                                                                                                            %>
                                                                                                      <%=String.valueOf(employee.getFullName())%>
                                                                                                            <%
                                                                                                        } else {
                                                                                                            %><%= appLetter.getFullName() %><%
                                                                                                        }
                                                                                                        %>
                                                                                                      
                                                                                                    </td>
                                                                                                  </tr>



                                                                                                  <tr> 
                                                                                                    <td width="19%"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"><% 
                                                                                                            Vector com_value = new Vector(1,1);
                                                                                                            Vector com_key = new Vector(1,1); 
                                                                                                            com_value.add("0");
                                                                                                            com_key.add("-");
                                                                                                            String strWhere = "";
                                                                                                            Vector listCom = PstCompany.list(0, 0, strWhere, "COMPANY");
                                                                                                            for (int i = 0; i < listCom.size(); i++) {
                                                                                                                    Company com = (Company) listCom.get(i);
                                                                                                                    com_key.add(com.getCompany());
                                                                                                                    com_value.add(String.valueOf(com.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_COMPANY_ID],"formElemen",null,String.valueOf(companyId==0?empDocListMutation.getCompanyId():companyId), com_value, com_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                                                                    </td>
                                                                                                  </tr>

                                                                                                  <tr> 
                                                                                                    <td width="19%"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"><% 
                                                                                                            Vector div_value = new Vector(1,1);
                                                                                                            Vector div_key = new Vector(1,1); 
                                                                                                            div_value.add("0");
                                                                                                            div_key.add("-");
                                                                                                            String strWhereDiv = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+(companyId==0?empDocListMutation.getCompanyId():companyId);
                                                                                                            Vector listDiv = PstDivision.list(0, 0, strWhereDiv, "DIVISION");
                                                                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                    Division div = (Division) listDiv.get(i);
                                                                                                                    div_key.add(div.getDivision());
                                                                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DIVISION_ID],"formElemen",null,String.valueOf(divisionId==0?empDocListMutation.getDivisionId():divisionId), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                                                                    </td>
                                                                                                  </tr>

                                                                                                  <tr> 
                                                                                                    <td width="19%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"><% 
                                                                                                            Vector dep_value = new Vector(1,1);
                                                                                                            Vector dep_key = new Vector(1,1); 
                                                                                                            dep_value.add("0");
                                                                                                            dep_key.add("-");
                                                                                                            String strWhereDep = " hr_department."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+(divisionId==0?empDocListMutation.getDivisionId():divisionId);
                                                                                                            Vector listDep = PstDepartment.list(0, 0, strWhereDep, "DEPARTMENT");
                                                                                                            for (int i = 0; i < listDep.size(); i++) {
                                                                                                                    Department dep = (Department) listDep.get(i);
                                                                                                                    dep_key.add(dep.getDepartment());
                                                                                                                    dep_value.add(String.valueOf(dep.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_DEPARTMENT_ID],"formElemen",null,String.valueOf(departmentId==0?empDocListMutation.getDepartmentId():departmentId), dep_value, dep_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  <tr> 
                                                                                                    <td width="19%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                        
                                                                                                    <% 
                                                                                                            Vector sec_value = new Vector(1,1);
                                                                                                            Vector sec_key = new Vector(1,1); 
                                                                                                            sec_value.add("0");
                                                                                                            sec_key.add("all section ...");
                                                                                                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+(departmentId==0?empDocListMutation.getDepartmentId():departmentId);
                                                                                                            Vector listSec = PstSection.list(0, 0, strWhereSec, "SECTION");
                                                                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                                                                    Section sec = (Section) listSec.get(i);
                                                                                                                    sec_key.add(sec.getSection());
                                                                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_SECTION_ID],"formElemen",null,String.valueOf(sectionId==0?empDocListMutation.getSectionId():sectionId), sec_value, sec_key, "") %> 
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Position</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                  <% 
                                                                                                            Vector pos_value = new Vector(1,1);
                                                                                                            Vector pos_key = new Vector(1,1); 
                                                                                                            pos_value.add("0");
                                                                                                            pos_key.add("all position ...");                                                       
                                                                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                                                                    Position pos = (Position) listPos.get(i);
                                                                                                                    pos_key.add(pos.getPosition());
                                                                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_POSITION_ID],"formElemen",null,String.valueOf(positionId==0?empDocListMutation.getPositionId():positionId), pos_value, pos_key, "") %> </td>
                                                                                                  </tr>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Emp Cat</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                  <% 
                                                                                                  if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                      %>
                                                                                                      <input type="hidden" name="<%= FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_CAT_ID] %>" value="<%= empDocListMutation.getEmpCatId() %>" />
                                                                                                      <%= changeValue.getEmpCategory(empDocListMutation.getEmpCatId()) %>
                                                                                                      <%
                                                                                                  } else {
                                                                                                            Vector cat_value = new Vector(1,1);
                                                                                                            Vector cat_key = new Vector(1,1); 
                                                                                                            cat_value.add("0");
                                                                                                            cat_key.add("all emp cat ...");                                                       
                                                                                                            Vector listCat = PstEmpCategory.list(0, 0, "", "");
                                                                                                            for (int i = 0; i < listCat.size(); i++) {
                                                                                                                    EmpCategory empCategory = (EmpCategory) listCat.get(i);
                                                                                                                    cat_key.add(empCategory.getEmpCategory());
                                                                                                                    cat_value.add(String.valueOf(empCategory.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_EMP_CAT_ID],"formElemen",null,String.valueOf(empCatId==0?empDocListMutation.getEmpCatId():empCatId), cat_value, cat_key, "") %> </td>
                                                                                                    <% } %>
                                                                                                  </tr>
                                                                                                  
                                                                                                  <tr> 
                                                                                                    <td width="19%">Level</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                  <% 
                                                                                                  if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                      %>
                                                                                                      <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_LEVEL_ID]%>" value="<%= empDocListMutation.getLevelId()%>" />
                                                                                                      <%= changeValue.getLevelName(empDocListMutation.getLevelId()) %>
                                                                                                      <%
                                                                                                  } else {
                                                                                                            Vector Level_value = new Vector(1,1);
                                                                                                            Vector Level_key = new Vector(1,1); 
                                                                                                            Level_value.add("0");
                                                                                                            Level_key.add("all  ...");                                                       
                                                                                                            Vector listLevel = PstLevel.list(0, 0, "", "");
                                                                                                            for (int i = 0; i < listLevel.size(); i++) {
                                                                                                                    Level Level = (Level) listLevel.get(i);
                                                                                                                    Level_key.add(Level.getLevel());
                                                                                                                    Level_value.add(String.valueOf(Level.getOID()));
                                                                                                            }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_LEVEL_ID],"formElemen",null,String.valueOf(levelId==0?empDocListMutation.getLevelId():levelId), Level_value, Level_key, "") %> </td>
                                                                                                    <% } %>
                                                                                                  </tr>
                                                                                                  <% if (empDocListMutation.getTipeDoc() != PstEmpDocListMutation.PENERIMAAN){ %>
                                                                                                   <tr> 
                                                                                                    <td width="19%">Work From</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM]%>" value="1" class="formElemen" >
                                                                                                    <%=ControlDate.drawDateWithStyle(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_FROM], dateF == null ? empDocListMutation.getWorkFrom() : dateF, 20, -150, "formElemen")%> 
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  
                                                                                                   <tr> 
                                                                                                    <td width="19%">
                                                                                                        <%
                                                                                                        if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                            %>
                                                                                                            Akhir Kontrak
                                                                                                            <%
                                                                                                        } else {
                                                                                                            %>
                                                                                                            Work To
                                                                                                            <%    
                                                                                                        }
                                                                                                        %>
                                                                                                    </td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_TO]%>" value="1" class="formElemen" >
                                                                                                    <%=ControlDate.drawDateWithStyle(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_WORK_TO], dateT == null ? empDocListMutation.getWorkTo() : dateT, 20, -150, "formElemen")%> 
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  
                                                                                                  <tr> 
                                                                                                    <td width="19%">Grade Level</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <%
                                                                                                    if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                        %>
                                                                                                        <input type="hidden" name="<%= FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_GRADE_LEVEL_ID] %>" value="<%= empDocListMutation.getGradeLevelId() %>" />
                                                                                                        <%= changeValue.getGradeLevelName(empDocListMutation.getGradeLevelId()) %>
                                                                                                        <%
                                                                                                    } else {
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
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_GRADE_LEVEL_ID],"formElemen",null,String.valueOf(levelId==0?empDocListMutation.getGradeLevelId():gradeLevelId), gd_value, gd_key, "") %> </td>
                                                                                                    <% } %>
                                                                                                  </tr>

                                                                                                  
                                                                                                  <tr> 
                                                                                                    <td width="19%">History Group</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <%
                                                                                                    if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                        %>
                                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_GROUP]%>" value="<%= empDocListMutation.getHistoryGroup() %>" />
                                                                                                        <%= PstCareerPath.historyGroup[PstCareerPath.RIWAYAT_JABATAN] %>
                                                                                                        <%
                                                                                                    } else {
                                                                                                    Vector hg_value = new Vector();
                                                                                                    Vector hg_key = new Vector();
                                                                                                    //hg_key.add("select");
                                                                                                    //hg_value.add(""+0);
                                                                                                    for (int i = 0; i < PstCareerPath.historyGroup.length; i++) {
                                                                                                        hg_key.add(PstCareerPath.historyGroup[i]);
                                                                                                        hg_value.add(""+i);
                                                                                                    }
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_GROUP],"formElemen",null,""+(historyGroup==0?empDocListMutation.getHistoryGroup():historyGroup), hg_value, hg_key, "") %> </td>
                                                                                                    <% } %>
                                                                                                  </tr>
                                                                                                  
                                                                                                  <tr> 
                                                                                                    <td width="19%">History Type</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                    <%
                                                                                                    if (empDocListMutation.getTipeDoc()== PstEmpDocListMutation.PERPANJANGAN_KONTRAK){
                                                                                                        %>
                                                                                                        <input type="hidden" name="<%=FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_TYPE]%>" value="<%= empDocListMutation.getHistoryType() %>" />
                                                                                                        <%= PstCareerPath.historyType[PstCareerPath.CAREER_TYPE] %>
                                                                                                        <%
                                                                                                    } else {
                                                                                                    Vector ht_value = new Vector();
                                                                                                    Vector ht_key = new Vector();
                                                                                                     //ht_key.add("-");
                                                                                                    //ht_value.add(""+0);
                                                                                                    for (int i = 0; i < PstCareerPath.historyType.length; i++) {
                                                                                                        ht_key.add(PstCareerPath.historyType[i]);
                                                                                                        ht_value.add(""+i);
                                                                                                    } 
                                                                                                    %>
                                                                                                    <%= ControlCombo.draw(FrmEmpDocListMutation.fieldNames[FrmEmpDocListMutation.FRM_FIELD_HISTORY_TYPE],"formElemen",null,String.valueOf(historyType==0?empDocListMutation.getHistoryType():historyType), ht_value, ht_key, "") %> </td>
                                                                                                    <% } %>
                                                                                                  </tr>
                                                                                                  <% } %>
                                                                                                  <% if (empDocListMutation.getTipeDoc()==2){ %>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Reason</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                         <% Vector resKey = new Vector(1, 1);
                                                                                                            Vector resValue = new Vector(1, 1);
                                                                                                            Vector listRes = PstResignedReason.list(0, 0, "", "RESIGNED_REASON");
                                                                                                            resKey.add("select...");
                                                                                                            resValue.add("0");
                                                                                                            for (int i = 0; i < listRes.size(); i++) {
                                                                                                                ResignedReason resignedReason = (ResignedReason) listRes.get(i);
                                                                                                                resKey.add(resignedReason.getResignedReason());
                                                                                                                resValue.add("" + resignedReason.getOID());
                                                                                                            }
                                                                                                            out.println(ControlCombo.draw(frmEmpDocListMutation.fieldNames[frmEmpDocListMutation.FRM_FIELD_RESIGN_REASON], "formElemen", null, "" + (empDocListMutation.getResignReason()==0?employee.getResignedReasonId():empDocListMutation.getResignReason()), resValue, resKey));
                                                                                                        %> 
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Reason Desc</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                          
                                                                                                        <textarea name="<%=frmEmpDocListMutation.fieldNames[frmEmpDocListMutation.FRM_FIELD_RESIGN_DESC]%>" class="formElemen" rows="2" cols="30"><%=empDocListMutation.getResignDesc()%></textarea>

                                                                                                        </td>
                                                                                                  </tr>
                                                                                                  <% } %>
                                                                                                  <% if (empDocListMutation.getTipeDoc()==1){ %>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Emp Num</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                        <input type="text" name="<%=frmEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_NUM] %>" value="<%=empDocListMutation.getEmpNum()%>">
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  
                                                                                                  <% } %>
                                                                                                  <% 
                                                                                                  Vector vComp = PstPayComponent.listAll();
                                                                                                  if (empDocListMutation.getTipeDoc()==PstEmpDocListMutation.SALARY_COMPONENT){ %>
                                                                                                  <tr> 
                                                                                                    <td width="19%">Component</td>
                                                                                                    <td width="1%">:</td>
                                                                                                    <td width="80%"> 
                                                                                                        <input type="text" name="<%=frmEmpDocListMutation.fieldNames[frmEmpDocListMutation.FRM_FIELD_PAY_COMPONENT] %>" value="<%=empDocListMutation.getPayComponent()%>"><a href="javascript:cmdAddComponent()">add Component</a>
                                                                                                    </td>
                                                                                                  </tr>
                                                                                                  
                                                                                                  <% } %>
                                                                                                  <tr> 
                                                                                                    <td width="19%">&nbsp;</td>
                                                                                                    <td width="1%">&nbsp;</td>
                                                                                                    <td width="80%"> 
                                                                                                      <input type="submit" name="Submit" value="Save" onClick="javascript:cmdSave()">
                                                                                                    <!--  <input type="submit" name="Submit" value="Add Employee" onClick="javascript:cmdAdd()">
                                                                                                    -->
                                                                                                    </td>
                                                                                                  </tr>

                                                                                                </table>
                                                                                            </td>
                                                                                          </tr>

                                                                                            </table>
                                                                                </form>
                                                                                <!-- #EndEditable -->
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp; </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

