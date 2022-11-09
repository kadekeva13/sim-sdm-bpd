<%-- 
    Document   : training_plan_list
    Created on : Jan 16, 2009, 9:49:52 AM
    Author     : bayu 
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
<%@ page import = "com.dimata.harisma.session.employee.*" %>

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
	public String drawList(Vector objectClass, Position position){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.addHeader("Position","","0","0");
		ctrlist.addHeader("Staff Count","","0","0");	
		ctrlist.addHeader("Program","","0","0");		
		ctrlist.addHeader("No. of Programs","","0","0");		
		ctrlist.addHeader("Total Hours","","0","0");		
		ctrlist.addHeader("No. of Trainees","","0","0");	
		ctrlist.addHeader("Trainer","","0","0");
		ctrlist.addHeader("Remark","","0","0");
		ctrlist.addHeader("Training Material","","0","0");
                ctrlist.addHeader("Action","","0","0");

		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		String whereClause = "";
		Vector rowx = new Vector();

		int sumPlanPrg = 0;
		int sumActPrg = 0;
		int sumPlanHour = 0;
		int sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
			for (int i = 0; i < objectClass.size(); i++) {
				TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)objectClass.get(i);
				
				rowx = new Vector();
				if(i==0){
					rowx.add(position.getPosition()); //0
				}else{
					rowx.add("");
				}

				rowx.add(""); //1
				
				Training trn = new Training();
				try{
					trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				}
				catch(Exception e){
					trn = new Training();
				}
				rowx.add(trn.getName()); //2

				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //3		
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                                			
				rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //4				
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				
				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //5				
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				
				rowx.add(trainingActivityPlan.getTrainer());    //6 
				rowx.add(trainingActivityPlan.getRemark());     //7
                                
				// get training material
                                rowx.add("<button class=\"btn-small-1\" onclick=\"javascript:cmdView('"+trainingActivityPlan.getTrainingId()+"')\">View</button>");     //8
                                rowx.add("<button class=\"btn-small-1\" onclick=\"javascript:cmdEdit('"+trainingActivityPlan.getOID()+"')\">Edit</button>");
				lstData.add(rowx);
			}
		}else{
			rowx = new Vector();
			rowx.add("&nbsp;"+position.getPosition());
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			
			lstData.add(rowx);	
		}
		rowx = new Vector();
		
		rowx.add("&nbsp;<b>Total</b>");
		whereClause = PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]+ " = "+position.getOID()+
		" AND "+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
		int staffCount = PstEmployee.getCount(whereClause);
		rowx.add(staffCount==0?"":""+staffCount);
		rowx.add("");
		
		rowx.add(String.valueOf(sumPlanPrg));
				
		rowx.add(SessTraining.getDurationString(sumPlanHour));
				
		rowx.add(String.valueOf(sumPlanTrain));
		
		rowx.add("");
		rowx.add("");
		rowx.add("");
                rowx.add("");
		lstData.add(rowx);
		
		return ctrlist.drawList();
	}

        public String drawListGen(Vector objectClass, int start){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
                ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setHeaderStyle("title_tbl");
			
                ctrlist.addHeader("No","","0","0");
                ctrlist.addHeader("Training Title","","0","0");
		ctrlist.addHeader("Program","","0","0");	
                /* ctrlist.addHeader("Department","","0","0");	*/
		ctrlist.addHeader("No. of Programs","","0","0");		
		ctrlist.addHeader("Total Hours","","0","0");		
		ctrlist.addHeader("No. of Trainees","","0","0");
		ctrlist.addHeader("Trainer","","0","0");
		ctrlist.addHeader("Remark","","0","0");
		ctrlist.addHeader("Training Material","","0","0");
                ctrlist.addHeader("Action","","0","0");

		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		String whereClause = "";
		Vector rowx = new Vector();

		int sumPlanPrg = 0;
		int sumActPrg = 0;
		int sumPlanHour = 0;
		int sumActHour = 0;
		int sumPlanTrain = 0;
		int sumActTrain = 0;
		int procent = 0;
		String strProcent = "";
                
		if(objectClass != null && objectClass.size()>0){
                        int totalDept = PstDepartment.getCount("");
                    
			for (int i = 0; i < objectClass.size(); i++) {
				TrainingActivityPlan trainingActivityPlan = (TrainingActivityPlan)objectClass.get(i);
				Training trn = new Training();
                                Vector vctDept = new Vector();                          
                                
				try{
					trn = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
				}
				catch(Exception e){
					trn = new Training();
				}
                                
                                String where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + "=" + trn.getOID();                              
                                vctDept = PstTrainingDept.list(0, 0, where, "");
                                
				rowx = new Vector();
				
                                rowx.add("" + (++start));
                                rowx.add(trainingActivityPlan.getTrainingTitle());
                                rowx.add(trn.getName()); //0
                                /* Untuk sementara di Off | by Hendra Putu | 2015-12-18
                                if(vctDept != null) {
                                    if(totalDept == vctDept.size()) {
                                        rowx.add("All Departments"); // 1
                                    }
                                    else {
                                        String dept = "";
                                        
                                        for(int j=0; j<vctDept.size(); j++) {
                                            TrainingDept td = (TrainingDept)vctDept.get(j);
                                            
                                            try {
                                                Department d = PstDepartment.fetchExc(td.getDepartmentId());

                                                dept += d.getDepartment();

                                                if(j<vctDept.size()-1)
                                                    dept += ", ";
                                                }
                                            catch(Exception e) {}
                                        }
                                        
                                        rowx.add(dept); //1
                                    }
                                }
                                else {
                                    rowx.add(""); //1
                                } */
                                
				rowx.add(String.valueOf(trainingActivityPlan.getProgramsPlan()));   //2		
				sumPlanPrg = sumPlanPrg + trainingActivityPlan.getProgramsPlan();
                                			
				rowx.add(SessTraining.getDurationString(trainingActivityPlan.getTotHoursPlan()));   //3				
				sumPlanHour = sumPlanHour + trainingActivityPlan.getTotHoursPlan();
				
				rowx.add(String.valueOf(trainingActivityPlan.getTraineesPlan()));   //4				
				sumPlanTrain = sumPlanTrain + trainingActivityPlan.getTraineesPlan();
				
				rowx.add(trainingActivityPlan.getTrainer());    //5
				rowx.add(trainingActivityPlan.getRemark());     //6
                                
				// get training material
                                rowx.add("<button class=\"btn-small-1\" onclick=\"javascript:cmdView('"+trainingActivityPlan.getTrainingId()+"')\">View</button>");     //7
                                rowx.add("<button class=\"btn-small-1\" onclick=\"javascript:cmdEdit('"+trainingActivityPlan.getOID()+"')\">Edit</button>");
				lstData.add(rowx);
			}
		}else{
			rowx = new Vector();
                        
                        rowx.add("");
                        rowx.add("");	
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			
			lstData.add(rowx);	
		}
                
		rowx = new Vector();
		
		
		rowx.add("");	
                rowx.add("&nbsp;<b>Total</b>");	
                rowx.add("");
		rowx.add(String.valueOf(sumPlanPrg));				
		rowx.add(SessTraining.getDurationString(sumPlanHour));				
		rowx.add(String.valueOf(sumPlanTrain));		
		rowx.add("");
		rowx.add("");
		rowx.add("");
                rowx.add("");
		lstData.add(rowx);
		
		return ctrlist.drawList();
	}
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    Date date = FRMQueryString.requestDate(request,"date");	
    int start = FRMQueryString.requestInt(request,"start");
    int typ = FRMQueryString.requestInt(request, "type");
    CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);
        
    int recordToGet = 20;
    int vectSize = PstPosition.getCount("") + 1;
            
    if(typ == PRIV_GENERAL) {
        Vector listTotalPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByPos(0, date, 0, 0);
        if(listTotalPlanning != null){
            vectSize = listTotalPlanning.size();
        } else {
            vectSize = 0;
        }
    }
    
    String whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_POSITION_ID]+" = 0"+
         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";

    //Vector listDepartment = new Vector(1,1);
    Vector listPosition = new Vector(1,1);
   
    Vector listPlanning = PstTrainingActivityPlan.list(start,recordToGet,whereClause,"");

    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)){
        start = ctrlTrainingActivityPlan.actionList(iCommand, start, vectSize, recordToGet);
        
        if(trainType == PRIV_GENERAL) {        
            listPosition = PstPosition.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,"",PstPosition.fieldNames[PstPosition.FLD_POSITION]);
            
        }
        else if(trainType == PRIV_DEPT) {
            String filter = PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=" + departmentOid;
            listPosition = PstPosition.list((start-1)<0?0:(start-1),(start-1)<0?recordToGet-1:recordToGet,filter,PstPosition.fieldNames[PstPosition.FLD_POSITION]);
        }
    }

    if(iCommand == Command.BACK)
          date = new Date();
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Plan</title>
<script language="JavaScript">

	function cmdAdd(){
		document.fract.command.value="<%=Command.ADD%>";
                document.fract.prev_command.value="<%=Command.ADD%>";
		document.fract.action="training_plan_edit.jsp";
		document.fract.submit();
	}

	function cmdEdit(oid){
		document.fract.command.value="<%=Command.EDIT%>";
                document.fract.prev_command.value="<%=Command.EDIT%>";
                document.fract.training_plan_id.value = oid;
		document.fract.action="training_plan_edit.jsp";
		document.fract.submit();
	}

	function cmdListFirst(){
		document.fract.command.value="<%=Command.FIRST%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}
         
        function cmdListFirstType(typ){
		document.fract.command.value="<%=Command.FIRST%>";
                document.fract.type.value=typ;
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListPrev(){
		document.fract.command.value="<%=Command.PREV%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListNext(){
		document.fract.command.value="<%=Command.NEXT%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}

	function cmdListLast(){
		document.fract.command.value="<%=Command.LAST%>";
		document.fract.action="training_plan_list.jsp";
		document.fract.submit();
	}
	
	function cmdView(trainingId){
                window.open("../../employee/training/list_training_material.jsp?hidden_training_id=" + trainingId,"trainingmaterial" , "height=600,width=800,status=no,toolbar=no,menubar=no,location=no");

        }
	
	function cmdPrint(){
		var dtYear  = document.fract.date_yr.value;								
		var dtMonth = document.fract.date_mn.value;
		var dtDay   = document.fract.date_dy.value;
		
		var linkPage   = "training_act_plan_buffer.jsp?" +
						 "date_yr="+ dtYear +"&"+
						 "date_mn="+ dtMonth +"&"+
						 "date_dy="+dtDay;
						 
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
            <span id="menu_title">Training <strong style="color:#333;"> / </strong>Training Activities Plan</span>
        </div>
        <div class="content-main">
            <form name="fract" method="post" action="">
            <input type="hidden" name="command" value="<%=iCommand%>"> 
            <input type="hidden" name="prev_command" value=""> 
            <input type="hidden" name="start" value="<%=start%>">     
            <input type="hidden" name="training_plan_id">
            <input type="hidden" name="type" value="<%=typ%>">  
            <div>
                <strong>Month:</strong>
                <%=ControlDate.drawDateMY("date",iCommand == Command.NONE?new Date():date,"MMMM","formElemen",+4,-8)%>
            </div>
            <div>&nbsp;</div>
            <div>
                <a style="color:#FFF" class="btn" href="javascript:cmdAdd()">Add New Training Plan</a>&nbsp;
                <a style="color:#FFF" class="btn" href="javascript:cmdListFirstType(<%=PRIV_DEPT%>)">View Plan (Departmental)</a>&nbsp;
                <a style="color:#FFF" class="btn" href="javascript:cmdListFirstType(<%=PRIV_GENERAL%>)">View Plan (General)</a>
            </div>
            <div>&nbsp;</div>

            

              <% if(listPosition != null && listPosition.size()>0){%>
              <h1 align="center">Month : <%=Formater.formatDate(date,"MMMM yyyy")%></h1>
              <table width="100%" border="0" cellspacing="2" cellpadding="0">
              <tr> 
                <td width="100%" class="command"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">                                            
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>

                    <% if(typ == PRIV_DEPT) {

                          for(int d=0;d<listPosition.size();d++){ 
                              Position position = (Position)listPosition.get(d);
                              listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByPos(position.getOID(), date, start, recordToGet);

                              //whereClause = PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]+" = "+department.getOID()+
                              //                                         " AND "+PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DATE]+" = '"+Formater.formatDate(date,"yyyy-MM-dd")+"'";
                              //listPlanning = PstTrainingActivityPlan.list(0,0,whereClause,"");                                            


                        %>
                            <tr> 
                              <td><%=drawList(listPlanning, position)%></td>
                            </tr>
                            <tr> 
                              <td>&nbsp;</td>
                            </tr>
                        <%
                          } // end for
                       } 
                       else if(typ == PRIV_GENERAL)
                       {
                           listPlanning = (Vector)PstTrainingActivityPlan.listTrainingPlanByPos(0, date, start, recordToGet);

                           /*if(listPlanning != null) {
                               vectSize = listPlanning.size();
                            } else {
                               vectSize = 0;
                            }*/

                           %>
                            <tr> 
                              <td><%=drawListGen(listPlanning, start)%></td>
                            </tr>
                            <tr> 
                              <td>&nbsp;</td>
                            </tr>
                    <% } %>
                    <tr> 
                      <td> 
                        <table>
                          <tr> 
                            <td> 
                              <% ControlLine ctrLine = new ControlLine();
                                   ctrLine.setLocationImg(approot+"/images");
                                   ctrLine.initDefault();
                                %>
                              <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <%}else{
                  if(iCommand == Command.LIST){%>
                      <tr> 
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td>No Training Activity available</td>
                      </tr>
                  <%}%>
              <%}%>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <% if(listPosition != null && listPosition.size()>0){%>
               <tr>
                <td>  </td>
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