<%-- 
    Document   : training_actual_list.jsp
    Created on : Jan 16, 2009, 9:49:29 AM
    Author     : guest
--%>
 
<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
		ctrlist.addHeader("No","");	
		ctrlist.addHeader("Date","");
		ctrlist.addHeader("Time","");
		ctrlist.addHeader("Training Program","");
		ctrlist.addHeader("Training Title","");
		ctrlist.addHeader("Trainer","");
		ctrlist.addHeader("Attd(pax)","");
		ctrlist.addHeader("Venue","");
		ctrlist.addHeader("Remark","");
                ctrlist.addHeader("Action","");

		Vector lstData = ctrlist.getData();
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			TrainingActivityActual trainingActivityActual = (TrainingActivityActual)temp.get(0);					
			TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)temp.get(1);
			Department department = (Department)temp.get(2);
			
			Vector rowx = new Vector();
			rowx.add("<div align=\"center\">"+(start+(i+1))+"</div>");
			rowx.add(Formater.formatDate(trainingActivityActual.getDate(),"dd-MMM-yy")+" To "+Formater.formatDate(trainingActivityActual.getTrainEndDate()));
			rowx.add(Formater.formatDate(trainingActivityActual.getStartTime(),"HH.mm") + " - " +
			
			Formater.formatDate(trainingActivityActual.getEndTime(),"HH.mm"));
			
			String topic = "";
			try{
                            String[] trainingProgram = PstTrainingActivityMapping.getArrayTrainingProgram(trainingActivityActual.getTrainingActivityPlanId());
                            if (trainingProgram != null){
                                for (String s: trainingProgram) {
                                    Training tr = new Training();
                                    try {
                                       tr = PstTraining.fetchExc(Long.valueOf(s));
                                       topic += tr.getName() + " , ";
                                    } catch (Exception exc){

                                    }

                                }
                                if (topic.length()>0){
                                    topic = topic.substring(0, topic.length() - 2);
                                }
                            } else {
                        	//System.out.println("trainingActivityPlan.getTrainingId() : "+trainingActivityPlan.getTrainingId());
				//System.out.println("trainingActivityActual.setTrainingActivityPlanId() : "+trainingActivityActual.getTrainingActivityPlanId());
				trainingActivityPlan = PstTrainingActivityPlan.fetchExc(trainingActivityActual.getTrainingActivityPlanId());
				//System.out.println("trainingActivityPlan.getTrainingId() : "+trainingActivityPlan.getTrainingId());
				Training tr = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				topic = tr.getName();
                            }
			}
			catch(Exception e){
				System.out.println("Error : "+e.toString());
			}
			
			rowx.add(topic);
			rowx.add(trainingActivityActual.getTrainingTitle());
			rowx.add(trainingActivityActual.getTrainner());
			rowx.add(String.valueOf(trainingActivityActual.getAtendees()));
			rowx.add(trainingActivityActual.getVenue());
			rowx.add(trainingActivityActual.getRemark());
			rowx.add("<button class=\"btn-small-1\" onclick=\"javascript:cmdEdit('"+String.valueOf(trainingActivityActual.getOID()) + "','" +
                                        String.valueOf(department.getOID()) + "','" +
                                        String.valueOf(trainingActivityPlan.getTrainingId()) + "','" +
                                        String.valueOf(trainingActivityPlan.getOID()) + "','" +
                                        String.valueOf(trainingActivityActual.getScheduleId())+"')\">Edit</button>");
                        lstData.add(rowx);
                       
		}
		return ctrlist.draw();
	}
%>
<%
	int iCommand = FRMQueryString.requestCommand(request);
	Date date = FRMQueryString.requestDate(request,"date");
	int start = FRMQueryString.requestInt(request,"start");
        int prevCommand = FRMQueryString.requestInt(request, "prev_command");

	ControlLine ctrLine = new ControlLine();
	CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);

	int iErrCode = FRMMessage.ERR_NONE;
	String msgStr = "";
	int recordToGet = 10;
	int vectSize = 0;
	String whereClause = "";
        long oidemp=0; 
	//vectSize = PstTrainingActivityActual.getCount(whereClause);
        vectSize = PstTrainingActivityActual.getCount(date,0,recordToGet,oidemp);

	Vector records = new Vector(1,1);

	if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
            start = ctrlTrainingActivityActual.actionList(iCommand, start, vectSize, recordToGet);
            if(trainType == PRIV_DEPT) {
                records = PstTrainingActivityActual.listDeptActivity(date,start,recordToGet,departmentOid);
            } else {
                records = PstTrainingActivityActual.listActivity(date,start,recordToGet,oidemp); 
            }
	}
        
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head> 
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Actual</title>
<script language="JavaScript">
	function cmdAdd(){
		document.fractual.command.value="<%=Command.ADD%>";
		document.fractual.action="training_actual_edit.jsp";
		document.fractual.submit();
	}
         
        function cmdAddDirect(){
		document.fractual.command.value="<%=Command.ADD%>";
		document.fractual.action="training_actual_direct_edit.jsp";
		document.fractual.submit();
	}

	function cmdEdit(oidAct, oidDept, oidTrain, oidPlan, oidSchedule){
		document.fractual.training_actual_id.value=oidAct;
                document.fractual.department_id.value=oidDept;
                document.fractual.training_id.value=oidTrain;
                document.fractual.training_plan_id.value=oidPlan;
                document.fractual.training_schedule_id.value=oidSchedule;
		document.fractual.command.value="<%=Command.EDIT%>";
		document.fractual.action="training_actual_edit.jsp";
		document.fractual.submit();
	}

	function cmdListFirst(){
		document.fractual.command.value="<%=Command.FIRST%>";
		document.fractual.action="training_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListPrev(){
		document.fractual.command.value="<%=Command.PREV%>";
		document.fractual.action="training_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListNext(){
		document.fractual.command.value="<%=Command.NEXT%>";
		document.fractual.action="training_actual_list.jsp";
		document.fractual.submit();
	}

	function cmdListLast(){
		document.fractual.command.value="<%=Command.LAST%>";
		document.fractual.action="training_actual_list.jsp";
		document.fractual.submit();
	}
	
	function cmdPrintAll(){
		var dtYear  = document.fractual.date_yr.value;								
		var dtMonth = document.fractual.date_mn.value;
		var dtDay   = document.fractual.date_dy.value;
		
		var linkPage   = "training_act_actual_buffer.jsp?" +
						 "date_yr="+ dtYear +"&"+
						 "date_mn="+ dtMonth +"&"+
						 "date_dy="+ dtDay;
						 
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
	}
        
        function cmdPrintDept(){
		var dtYear  = document.fractual.date_yr.value;								
		var dtMonth = document.fractual.date_mn.value;
		var dtDay   = document.fractual.date_dy.value;

		var linkPage   = "training_act_actual_buffer_dept.jsp?" +
                                                 "dept="+ <%= departmentOid %> +"&"+
						 "date_yr="+ dtYear +"&"+
						 "date_mn="+ dtMonth +"&"+
						 "date_dy="+ dtDay;                                                
  
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
	}


</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>


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
//-->
</SCRIPT>
        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px; background-color: #FFF;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #menu_utama {
                padding: 9px 14px; 
                border-left: 1px solid #0099FF; 
                font-size: 14px; 
                background-color: #F3F3F3;
                border-bottom: 1px solid #E3E3E3;
            }
            #menu_title {color:#0099FF; font-size: 14px;}
            body {color:#373737;}
            
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
                padding: 3px 5px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
                border: 1px solid #DDD;
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
    <body>
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
            <span id="menu_title">Training <strong style="color:#333;"> / </strong>Monthly Training Activities</span>
        </div>
        <div class="content-main">
            <form name="fractual" method="post" action="">
            <input type="hidden" name="command" value="<%=iCommand%>">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="training_actual_id">
            <input type="hidden" name="department_id">
            <input type="hidden" name="training_id">
            <input type="hidden" name="training_plan_id"> 
            <input type="hidden" name="training_schedule_id">
            
            <div>
                <strong>Month:</strong>
                <%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",+4,-8)%>
            </div>
            <div>&nbsp;</div>
            <div>
                <a style="color:#FFF" class="btn" href="javascript:cmdAdd()">Add New Training Activity  Actual (Based on Plan)</a>&nbsp;
                <a style="color:#FFF" class="btn" href="javascript:cmdAddDirect()">Add New Training Activity  Actual (Input Directly)</a>&nbsp;
                <a style="color:#FFF" class="btn" href="javascript:cmdListFirst()">View Training Activity Actual</a>
            </div>
            <div>&nbsp;</div>
            
            <table border="0" width="100%">

              <%if((records!=null)&&(records.size()>0)){%>
              <tr> 
                <td height="8" width="100%" class="command"> 
                  <div align="center"><font size="3">Training Activities Report</font></div>
                </td>
              </tr>
              <tr> 
                <td height="8" width="100%" class="command"> 
                  <div align="center">MONTH : <%=Formater.formatDate(date,"MMMM yyyy")%></div>
                </td>
              </tr>
              <%try{%>
              <tr> 
                <td height="8" width="100%" class="command"> 
                  <%=drawList(records, start)%> </td>
              </tr>
              <%}catch(Exception exc){
                      System.out.println(exc);
              }%>
              <tr> 
                <td height="8" width="100%" class="command">&nbsp;</td>
              </tr>
              <tr>
                <td height="8" width="100%" class="command">
                  <% ctrLine.setLocationImg(approot+"/images");
                      ctrLine.initDefault();
                  %>
                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
              </tr>
              <%}
                  else{
                      if(iCommand == Command.FIRST){
                  %>
              <tr> 
                <td> 
                  <div align="left"><span class="comment"><br>
                    &nbsp;&nbsp;&nbsp;No Training Activities 
                    available</span></div>
                </td>
              </tr>
              <%}
              }%>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                        
              <tr> 
                <td width="46%">&nbsp;</td>
              </tr>
              <%if((records!=null)&&(records.size()>0)){%>
              <tr> 
                <td width="46%" nowrap align="left" class="command">
                  <table border="0" cellspacing="0" cellpadding="0" align="left">
                    <%-- if(trainType == PRIV_GENERAL) { %>
                        <tr> 
                          <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                          <td width="24"><a href="javascript:cmdPrintAll()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                          <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                          <td nowrap><a href="javascript:cmdPrintAll()" class="command">Print 
                            Training Activity</a></td>
                        </tr>
                    <%
                     } else if(trainType == PRIV_DEPT) {
                    %>
                        <tr> 
                          <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                          <td width="24"><a href="javascript:cmdPrintDept()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                          <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                          <td nowrap><a href="javascript:cmdPrintDept()" class="command">Print 
                            Departemental Training Activity</a></td>
                        </tr>
                    <% } --%>
                  </table>
                </td>
              </tr>
              <%}%>
            </table>
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
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>

