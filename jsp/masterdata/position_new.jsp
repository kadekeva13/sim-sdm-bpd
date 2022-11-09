<%-- 
    Document   : position_new
    Created on : Jan 14, 2016, 3:56:54 PM
    Author     : Dimata 007
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcPosition"%>
<%@page import="com.dimata.harisma.entity.search.SrcPosition"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPosition"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPositionKPI"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPosition"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
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
    
    public String getDepartmentName(long departId){
        String name = "-";
        try {
            Department depart = PstDepartment.fetchExc(departId);
            name = depart.getDepartment();
        } catch (Exception e){
            System.out.println("getPositionName ==> "+e.toString());
        }
        return name;
    }

    public String getTemplateName(long tempId){
        String template = "";
        StructureTemplate temp = new StructureTemplate();
        try {
            temp = PstStructureTemplate.fetchExc(tempId);
            template = temp.getTemplateName();
        } catch(Exception ex){
            System.out.println("getTemplateName =>"+ex.toString());
        }
        return template;
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
/* update variable by Hendra Putu | 2015-08-02 */
int customCommand = FRMQueryString.requestInt(request, "custom_command");
int focus = FRMQueryString.requestInt(request, "focus");
/* update by Hendra | 2016-01-04 */
int commandPosMapping = FRMQueryString.requestInt(request, "command_pos_mapping");
long oidPosComp = FRMQueryString.requestLong(request, "oid_pos_comp");
long oidPosDivi = FRMQueryString.requestLong(request, "oid_pos_divi");
long oidPosDepart = FRMQueryString.requestLong(request, "oid_pos_depart");
long oidPosSec = FRMQueryString.requestLong(request, "oid_pos_sec");
long oidPosLevel = FRMQueryString.requestLong(request, "oid_pos_level");

String searchPosition = FRMQueryString.requestString(request, "search_position");
int askCommand = FRMQueryString.requestInt(request, "ask_command");
int deleteCommand = FRMQueryString.requestInt(request, "delete_command");
long deleteAssessmentId = FRMQueryString.requestLong(request, "delete_assessment_id");
long deleteCompetencyId = FRMQueryString.requestLong(request, "delete_competency_id");
long deleteTrainingId = FRMQueryString.requestLong(request, "delete_training_id");
long deleteGradeId = FRMQueryString.requestLong(request, "delete_grade_id");
long deleteEduId = FRMQueryString.requestLong(request, "delete_edu_id");
long deleteExpId = FRMQueryString.requestLong(request, "delete_exp_id");
long deleteKlasifikasiId = FRMQueryString.requestLong(request, "delete_klasifikasi_id");

int kpiCommand = FRMQueryString.requestInt(request, "kpi_command");
long selectCompany = FRMQueryString.requestLong(request, "select_company");
long selectDivisi = FRMQueryString.requestLong(request, "select_divisi");
long selectDepart = FRMQueryString.requestLong(request, "select_depart");
long selectSection = FRMQueryString.requestLong(request, "select_section");
long selectLevel = FRMQueryString.requestLong(request, "select_level");
long positionMapId = FRMQueryString.requestLong(request, "position_map_id");
long topPos = FRMQueryString.requestLong(request, "top_pos");
int topType = FRMQueryString.requestInt(request, "top_type");
long downPos = FRMQueryString.requestLong(request, "down_pos");
int downType = FRMQueryString.requestInt(request, "down_type");

int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPosition = FRMQueryString.requestLong(request, "hidden_position_id");
long oidCompetency = FRMQueryString.requestLong(request, "competency_id");
long oidAssessment = FRMQueryString.requestLong(request, "assessment_id");
long oidGradeMin = FRMQueryString.requestLong(request, "min_grade_req_id");
long oidGradeMax = FRMQueryString.requestLong(request, "max_grade_req_id");
long oidTraining = FRMQueryString.requestLong(request, "training_id");
long oidEducation = FRMQueryString.requestLong(request, "education_id");
long oidExperience = FRMQueryString.requestLong(request, "experience_id");
long oidKlasifikasi = FRMQueryString.requestLong(request, "klasifikasi_id");
long oidKPI = FRMQueryString.requestLong(request, "kpi_id");
long oidPosKPI = FRMQueryString.requestLong(request, "pos_kpi_id");

String orderBy = "";
/* update code by Hendra Putu | 2015-08-02 */
if (customCommand != 0){
    switch(customCommand){
        case 1:
            PositionCompany psc = new PositionCompany();
            psc.setCompanyId(selectCompany);
            psc.setPositionId(oidPosition);
            PstPositionCompany.insertExc(psc);
            customCommand = 0;
            break;
        case 2:
            PositionDivision div = new PositionDivision();
            div.setDivisionId(selectDivisi);
            div.setPositionId(oidPosition);
            PstPositionDivision.insertExc(div);
            customCommand = 0;
            break;
        case 3:
            PositionDepartment depart = new PositionDepartment();
            depart.setDepartmentId(selectDepart);
            depart.setPositionId(oidPosition);
            PstPositionDepartment.insertExc(depart);
            customCommand = 0;
            break;
        case 4:
            PositionSection section = new PositionSection();
            section.setSectionId(selectSection);
            section.setPositionId(oidPosition);
            PstPositionSection.insertExc(section);
            customCommand = 0;
            break;
        case 5:
            TopPosition topPosition = new TopPosition();
            topPosition.setPositionId(oidPosition);
            topPosition.setPositionToplink(topPos);
            topPosition.setTypeOfLink(topType);
            PstTopPosition.insertExc(topPosition);
            customCommand = 0;
            break;
        case 6:
            DownPosition downPosition = new DownPosition();
            downPosition.setPositionId(oidPosition);
            downPosition.setPositionDownlink(downPos);
            downPosition.setTypeOfLink(downType);
            PstDownPosition.insertExc(downPosition);
            customCommand = 0;
            break;                          
       case 8:
            PositionLevel level = new PositionLevel();
            level.setLevelId(selectLevel);
            level.setPositionId(oidPosition);
            PstPositionLevel.insertExc(level);
            customCommand = 0;
            break;
    }
}

/* Update code by Hendra Putu | 2015-09-03 */
if (deleteCommand > 0 && deleteCompetencyId > 0){
    long oidPosCompetency = PstPositionCompetencyRequired.deleteExc(deleteCompetencyId);
    deleteCommand = 0;
    deleteCompetencyId = 0;
}

if (deleteCommand > 0 && deleteAssessmentId > 0){
    long oidPosAssessment = PstPositionAssessmentRequired.deleteExc(deleteAssessmentId);
    deleteCommand = 0;
    deleteAssessmentId = 0;
}

if (deleteCommand > 0 && deleteTrainingId > 0){
    PstPositionTrainingRequired.deleteExc(deleteTrainingId);
    deleteCommand = 0;
    deleteTrainingId = 0;
}

if (deleteCommand > 0 && deleteGradeId > 0){
    PstPositionGradeRequired.deleteExc(deleteGradeId);
    deleteCommand = 0;
    deleteTrainingId = 0;
}

if (deleteCommand > 0 && deleteEduId > 0){
    PstPositionEducationRequired.deleteExc(deleteEduId);
    deleteCommand = 0;
    deleteEduId = 0;
}

if (deleteCommand > 0 && deleteExpId > 0){
    PstPositionExperienceRequired.deleteExc(deleteExpId);
    deleteCommand = 0;
    deleteExpId = 0;
}
if (deleteCommand > 0 && deleteKlasifikasiId > 0){
    PstPositionTypeMapping.deleteExc(deleteKlasifikasiId);
    deleteCommand = 0;
    deleteKlasifikasiId = 0;
}
/* Delete Mapping Position */
if (commandPosMapping > 0){
    switch(commandPosMapping){
        case 1:
            if (oidPosComp != 0){
                try {
                    PstPositionCompany.deleteExc(oidPosComp);
                } catch(Exception e){
                    System.out.println("pos comp =>"+e.toString());
                }
            }
            break;
        case 2: 
            if (oidPosDivi != 0){
                try {
                    PstPositionDivision.deleteExc(oidPosDivi);
                } catch(Exception e){
                    System.out.println("pos divi =>"+e.toString());
                }
            }
            break;
        case 3:
            if (oidPosDepart != 0){
                try {
                    PstPositionDepartment.deleteExc(oidPosDepart);
                } catch(Exception e){
                    System.out.println("pos depart =>"+e.toString());
                }
            }
            break;
        case 4:
            if (oidPosSec != 0){
                try {
                    PstPositionSection.deleteExc(oidPosSec);
                } catch(Exception e){
                    System.out.println("pos sec =>"+e.toString());
                }
            }
            break;
        case 5:
        if (oidPosLevel != 0){
            try {
                PstPositionLevel.deleteExc(oidPosLevel);
            } catch(Exception e){
                System.out.println("pos level =>"+e.toString());
            }
        }
        break;
    }
}

long oidGradeReq=0;
long oidCom = 0;
long oidAss = 0;
long oidTrain = 0;
long oidEdu = 0;
long oidKp = 0;
long oidExp = 0;
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
/*variable declaration*/
int recordToGet = 30;
String msgString = "";
int iErrCode = FRMMessage.NONE;
int iErrCodePosKpi = FRMMessage.NONE;

CtrlPosition ctrlPosition = new CtrlPosition(request);
CtrlPositionKPI ctrlPositionKpi = new CtrlPositionKPI(request);
ControlLine ctrLine = new ControlLine();  
Vector listPosition = new Vector(1,1);

/*switch statement */
iErrCode = ctrlPosition.action(iCommand , oidPosition, attdConfig);
if(oidPosKPI != 0){
    iErrCodePosKpi = ctrlPositionKpi.action(6 , oidPosKPI);
}


/* end switch*/
FrmPosition frmPosition = ctrlPosition.getForm();
/*
 * Description : add competency, training & education
 * Date : 2015-02-05
 * Author : Hendra Putu
*/
if(iCommand == Command.EDIT){
    if (oidCompetency > 0){
        PositionCompetencyRequired posComReq = new PositionCompetencyRequired();
        posComReq.setCompetencyId(oidCompetency);
        posComReq.setPositionId(oidPosition);
        Vector listComLevel = new Vector();
        String whereClause = ""+PstCompetencyLevel.fieldNames[PstCompetencyLevel.FLD_COMPETENCY_ID]+"="+oidCompetency;
        listComLevel = PstCompetencyLevel.list(0, 1, whereClause, "");
        if (listComLevel != null && listComLevel.size() > 0){
            CompetencyLevel comLevel = (CompetencyLevel)listComLevel.get(0);
            posComReq.setCompetencyLevelId(comLevel.getOID());
        }
        oidCom = PstPositionCompetencyRequired.insertExc(posComReq);
    }
    oidCompetency = 0;
    if (oidAssessment > 0){
        PositionAssessmentRequired posAssReq = new PositionAssessmentRequired();
        posAssReq.setAssessmentId(oidAssessment);
        posAssReq.setPositionId(oidPosition);
        oidAss = PstPositionAssessmentRequired.insertExc(posAssReq);
    }
    oidAssessment = 0;
    if (oidTraining > 0){
        PositionTrainingRequired posTrain = new PositionTrainingRequired();
        posTrain.setTrainingId(oidTraining);
        posTrain.setPositionId(oidPosition);
        oidTrain = PstPositionTrainingRequired.insertExc(posTrain);
    }
    oidTraining = 0;

    if (oidGradeMin != 0 && oidGradeMax!=0){
        PositionGradeRequired posGradeReq = new PositionGradeRequired();
        posGradeReq.setPositionId(oidPosition);
        GradeLevel gradeMin = new GradeLevel(oidGradeMin,"",0 );
        GradeLevel gradeMax = new GradeLevel(oidGradeMax,"",0 );
        posGradeReq.setGradeMinimum(gradeMin);
        posGradeReq.setGradeMaximum(gradeMax);        
        try{
        oidGradeReq = PstPositionGradeRequired.insertExc(posGradeReq);
        }catch(Exception exc){
            out.println(exc);
        }
    }
    oidGradeMin= 0;oidGradeMax=0;
    
    
    if (oidEducation != 0){
        PositionEducationRequired posEdu = new PositionEducationRequired();
        posEdu.setEducationId(oidEducation);
        posEdu.setPositionId(oidPosition);
        oidEdu = PstPositionEducationRequired.insertExc(posEdu);
    }
    oidEducation = 0;
    if(oidExperience > 0){
        PositionExperienceRequired posExp = new PositionExperienceRequired();
        posExp.setExperienceId(oidExperience);
        posExp.setPositionId(oidPosition);
        oidExp = PstPositionExperienceRequired.insertExc(posExp);
        
    }
    oidExperience = 0;
    if (oidKPI > 0){
        PositionKPI posKPI = new PositionKPI();
        posKPI.setKpiListId(oidKPI);
        posKPI.setPositionId(oidPosition);
        oidKp = PstPositionKPI.insertExc(posKPI);
    }
    oidKPI = 0;
}

SrcPosition srcPosition = new SrcPosition();
FrmSrcPosition frmSrcPosition  = new FrmSrcPosition(request, srcPosition);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK || iCommand==Command.ASK
   || iCommand==Command.EDIT || iCommand==Command.ADD || iCommand==Command.DELETE || (iCommand==Command.SAVE && frmPosition.errorSize()==0) )
{
    try
    { 
           srcPosition = (SrcPosition)session.getValue(PstPosition.SESS_HR_POSITION); 
    }
    catch(Exception e)
    { 
           srcPosition = new SrcPosition();
    }
}
else
{
    frmSrcPosition.requestEntityObject(srcPosition);
    session.putValue(PstPosition.SESS_HR_POSITION, srcPosition);	
}
/* Code is modified by Hendra Putu | 2015-08-16 */
String whereClause = "";
if(srcPosition!=null){
if (srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0){
    whereClause = PstPosition.fieldNames[PstPosition.FLD_VALID_START]+" BETWEEN '"+srcPosition.getStartDate()+"' AND '"+srcPosition.getEndDate()+"'";
}
if (srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0){
   if (srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0){
       whereClause += " AND ";
   } 
   whereClause += PstPosition.fieldNames[PstPosition.FLD_POSITION] + " LIKE '%" + srcPosition.getPosName() + "%'";
}
if (srcPosition.getPosLevel() >=0 ){
    if ((srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0)||(srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0)){
        whereClause += " AND ";
    }
    whereClause += PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " = " + srcPosition.getPosLevel();
}
if (srcPosition.getLevelRankID() > 0){
    if ((srcPosition.getStartDate().length() > 0 && srcPosition.getEndDate().length() > 0)||(srcPosition.getPosName() != null && srcPosition.getPosName().length() > 0)){
        whereClause += " AND ";
    }
    whereClause += PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID] + " = " + srcPosition.getLevelRankID();
}
}


String orderClause = " POSITION ";

/*count list All Position*/
int vectSize = PstPosition.getCount(whereClause);

Position position = ctrlPosition.getPosition();
msgString =  ctrlPosition.getMessage();

/*switch list Position*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstPosition.findLimitStart(position.getOID(),recordToGet, whereClause);
	oidPosition = position.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPosition.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

if (customCommand == 7){
    whereClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" LIKE '%"+searchPosition+"%'";
    orderClause = PstPosition.fieldNames[PstPosition.FLD_POSITION]+" ASC";
}
/* get record to display */
listPosition = PstPosition.list(start, recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPosition.size() < 1 && start > 0)
{
    if (vectSize - recordToGet > recordToGet)
                   start = start - recordToGet;   //go to Command.PREV
    else{
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
    }
    listPosition = PstPosition.list(start,recordToGet, whereClause , orderClause);
}

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Master Data - <%=(dictionaryD!=null ? dictionaryD.getWord(I_Dictionary.POSITION):"Position")%></title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <script language="JavaScript">
        function cmdBackToSearch(){
            document.frmposition.command.value="<%=Command.BACK%>";
            document.frmposition.action="srcposition.jsp";
            document.frmposition.submit();
        }

        function cmdSearchPosition(){
            document.frmposition.custom_command.value="7";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdAdd(){
            document.frmposition.hidden_position_id.value="0";
            document.frmposition.command.value="<%=Command.ADD%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdJobDescDet(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("jobdescdetail.jsp?comm="+comm+"&statPos="+<%=position.getPositionLevel()%>+"&hidden_position_id="+<%=oidPosition%>,"SelectEmployee", "fullscreen=yes,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
        }

        function cmdAddCompetency(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("competency_search.jsp?comm="+comm+"&frm=frmposition","SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        
        function cmdAddAssessment(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("assessment_search.jsp?comm="+comm,"SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        
        
        function cmdAddGrade(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("grade_level_select.jsp?comm="+comm,"SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        
        function cmdAddKlasifikasi(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("mapping_type_select.jsp?comm="+comm,"SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }

        
        function cmdAddTraining(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("training_search.jsp?comm="+comm,"SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdAddEducation(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("education_search.jsp?comm="+comm,"SelectEmployee", "height=400,width=390,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdAddExperience(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("experience_search.jsp?comm="+comm,"SelectEmployee", "height=400,width=390,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdAddKpi(){
            var comm = document.frmposition.command.value;
            newWindow=window.open("kpi_search.jsp?comm="+comm,"SelectEmployee", "height=400,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditCompetency(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_competency_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=500,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditAssessment(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_assessment_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=500,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditGrade(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_grade_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=500,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        
        function cmdEditTraining(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_training_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=600,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditEducation(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_education_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=400,width=590,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditExperience(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_experience_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=400,width=590,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdEditKpi(oid){
            var comm = document.frmposition.command.value;
            newWindow=window.open("pos_kpi_edit.jsp?comm="+comm+"&oid="+oid,"SelectEmployee", "height=400,width=390,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
            newWindow.focus();
            //document.frm_pay_emp_level.submit();
        }
        function cmdRefresh(){
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        /* Update 2015-08-01 | Putu Hendra */
        function cmdAddPositionMap(oid){
            document.frmposition.hidden_position_id.value=oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdAddPositionCompany(oidPosition){
            document.frmposition.custom_command.value="1";
            cmdAddPositionMap(oidPosition);
        }
        function cmdAddPositionDivisi(oidPosition){
            document.frmposition.custom_command.value="2";
            cmdAddPositionMap(oidPosition);
        }
        function cmdAddPositionDepart(oidPosition){
            document.frmposition.custom_command.value="3";
            cmdAddPositionMap(oidPosition);
        }
        function cmdAddPositionSection(oidPosition){
            document.frmposition.custom_command.value="4";
            cmdAddPositionMap(oidPosition);
        }
        function cmdAddPositionLevel(oidPosition){
            document.frmposition.custom_command.value="8";
            cmdAddPositionMap(oidPosition);
        }
        function cmdAddTopPos(){
            document.frmposition.custom_command.value="5";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdAddDownPos(){
            document.frmposition.custom_command.value="6";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdDeletePosComp(oid){
            document.frmposition.custom_command.value="5";
            document.frmposition.position_map_id.value=oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdGoToForm(){
            document.frmposition.action="position_form.jsp";
            document.frmposition.submit(); 
        }

        function cmdAsk(oidPosition){
            document.frmposition.hidden_position_id.value=oidPosition;
            document.frmposition.command.value="<%=Command.ASK%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdAskGrade(oid){
            document.getElementById("delete_grade_id").value=oid;
            document.getElementById("box-ask-grade").style.visibility="visible";
        }

        function cmdDeleteGrade(){
            var oid = document.getElementById("delete_grade_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_grade_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdCancelDelGrade(){
            document.getElementById("delete_grade_id").value="0";
            document.getElementById("box-ask-grade").style.visibility="hidden";
        }
        
        function cmdDeleteKlasifikasi(){
            var oid = document.getElementById("delete_klasifikasi_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_klasifikasi_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdCancelDelKlasifikasi(){
            document.getElementById("delete_klasifikasi_id").value="0";
            document.getElementById("box-ask-klasifikasi").style.visibility="hidden";
        }

        function cmdAskCompetency(oid){
            document.getElementById("delete_competency_id").value=oid;
            document.getElementById("box-ask-competency").style.visibility="visible";
        }
              
        function cmdDeleteCompetency(){
            var oid = document.getElementById("delete_competency_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_competency_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdCancelDelCompet(){
            document.getElementById("delete_competency_id").value="0";
            document.getElementById("box-ask-competency").style.visibility="hidden";
        }
        
        function cmdAskAssessment(oid){
            document.getElementById("delete_assessment_id").value=oid;
            document.getElementById("box-ask-assessment").style.visibility="visible";
        }
              
        function cmdDeleteAssessment(){
            var oid = document.getElementById("delete_assessment_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_assessment_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdCancelDelAss(){
            document.getElementById("delete_assessment_id").value="0";
            document.getElementById("box-ask-assessment").style.visibility="hidden";
        }

        function cmdAskTraining(oid){
            document.getElementById("delete_training_id").value=oid;
            document.getElementById("box-ask-training").style.visibility="visible";
        }
        function cmdCancelDelTrain(){
            document.getElementById("delete_training_id").value="0";
            document.getElementById("box-ask-training").style.visibility="hidden";
        }
        function cmdDeleteTraining(){
            var oid = document.getElementById("delete_training_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_training_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdAskEducation(oid){
            document.getElementById("delete_edu_id").value=oid;
            document.getElementById("box-ask-education").style.visibility="visible";
        }
        function cmdCancelDelEdu(){
            document.getElementById("delete_edu_id").value="0";
            document.getElementById("box-ask-education").style.visibility="hidden";
        }
        function cmdDeleteEducation(){
            var oid = document.getElementById("delete_edu_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_edu_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        
        function cmdAskExperience(oid){
            document.getElementById("delete_exp_id").value=oid;
            document.getElementById("box-ask-experience").style.visibility="visible";
        }
        function cmdCancelDelExp(){
            document.getElementById("delete_exp_id").value="0";
            document.getElementById("box-ask-experience").style.visibility="hidden";
        }
        function cmdDeleteExperience(){
            var oid = document.getElementById("delete_exp_id").value;
            document.frmposition.delete_command.value="1";
            document.frmposition.ask_command.value="0";
            document.frmposition.delete_exp_id.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        
        function cmdAskKPI(oid){
            document.getElementById("pos_kpi_id").value=oid;
            document.getElementById("box-ask-kpi").style.visibility="visible";
        }
        function cmdCancelDelKpi(){
            document.getElementById("pos_kpi_id").value="0";
            document.getElementById("box-ask-kpi").style.visibility="hidden";
        }
        function cmdDeleteKpi(){
            var oidKpi = document.getElementById("pos_kpi_id").value;
            document.frmposition.kpi_id.value=0;
            document.frmposition.pos_kpi_id.value=oidKpi;
            document.frmposition.kpi_command.value="<%=Command.DELETE%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdAskPosComp(oid){
            document.getElementById("oid_pos_comp").value=oid;
            document.frmposition.focus.value="1";              
            document.getElementById("box-ask-company").style.visibility="visible";
        }
        function cmdAskPosDivi(oid){
            document.getElementById("oid_pos_divi").value=oid;
            document.frmposition.focus.value="2"; 
            document.getElementById("box-ask-division").style.visibility="visible";
        }
        function cmdAskPosDepart(oid){
            document.getElementById("oid_pos_depart").value=oid;
            document.frmposition.focus.value="3"; 
            document.getElementById("box-ask-depart").style.visibility="visible";
        }
        function cmdAskPosSec(oid){
            document.getElementById("oid_pos_sec").value=oid;
            document.frmposition.focus.value="4";
            document.getElementById("box-ask-section").style.visibility="visible";
        }
        function cmdAskPosLevel(oid){
            document.getElementById("oid_pos_level").value=oid;
            document.frmposition.focus.value="8";
            document.getElementById("box-ask-level").style.visibility="visible";
        }
        function cmdAskKlasifikasi(oid){
            document.getElementById("delete_klasifikasi_id").value=oid;
            document.getElementById("box-ask-klasifikasi").style.visibility="visible";
        }


        function cmdCancelPosComp(){
            document.getElementById("oid_pos_comp").value="0";
            document.getElementById("box-ask-company").style.visibility="hidden";
        }
        function cmdCancelPosDivi(){
            document.getElementById("oid_pos_divi").value="0";
            document.getElementById("box-ask-division").style.visibility="hidden";
        }
        function cmdCancelPosDepart(){
            document.getElementById("oid_pos_depart").value="0";
            document.getElementById("box-ask-depart").style.visibility="hidden";
        }
        function cmdCancelPosSec(){
            document.getElementById("oid_pos_sec").value="0";
            document.getElementById("box-ask-section").style.visibility="hidden";
        }
        function cmdCancelPosLevel(){
            document.getElementById("oid_pos_level").value="0";
            document.getElementById("box-ask-level").style.visibility="hidden";
        }

        function cmdDelPosComp(){
            var oid = document.getElementById("oid_pos_comp").value;
            document.frmposition.command_pos_mapping.value="1";
            document.frmposition.oid_pos_comp.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdDelPosDivisi(){
            var oid = document.getElementById("oid_pos_divi").value;
            document.frmposition.command_pos_mapping.value="2";
            document.frmposition.oid_pos_divi.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdDelPosDepart(){
            var oid = document.getElementById("oid_pos_depart").value;
            document.frmposition.command_pos_mapping.value="3";
            document.frmposition.oid_pos_depart.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdDelPosSec(oid){
            var oid = document.getElementById("oid_pos_sec").value;
            document.frmposition.command_pos_mapping.value="4";
            document.frmposition.oid_pos_sec.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        function cmdDelPosLevel(oid){
            var oid = document.getElementById("oid_pos_level").value;
            document.frmposition.command_pos_mapping.value="5";
            document.frmposition.oid_pos_level.value = oid;
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdBackConfirm(){
            document.frmposition.delete_command.value="0";
            document.frmposition.ask_command.value="0";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdConfirmDelete(oidPosition){
            document.frmposition.hidden_position_id.value=oidPosition;
            document.frmposition.command.value="<%=Command.DELETE%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }
        
        function cmdSave(){
            document.frmposition.command.value="<%=Command.SAVE%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdEdit(oidPosition){
            document.frmposition.hidden_position_id.value=oidPosition;
            document.frmposition.command.value="<%=Command.EDIT%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdCancel(oidPosition){
            document.frmposition.hidden_position_id.value=oidPosition;
            document.frmposition.command.value="<%=Command.EDIT%>";
            document.frmposition.prev_command.value="<%=prevCommand%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function cmdBack(){
            document.frmposition.command.value="<%=Command.BACK%>";
            document.frmposition.action="position_new.jsp";
            document.frmposition.submit();
        }

        function fnTrapKD(){	
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

        function cmdExportExcel(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                }
            };
            var valid_status_select = document.frmposition.valid_status_select.value;
            var position_name = document.frmposition.position_name.value;
            if (position_name.length <= 0) { 
            position_name = "0";
            }
            var linkPage = "<%=approot%>/masterdata/export_excel/export_excel_position.jsp?position_name="+position_name+"&valid_status_select="+ valid_status_select;    
            var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
            newWin.focus();
            xmlhttp.open("GET", linkPage, true);
            xmlhttp.send();
        }

        </script>
        <script type="text/javascript">
            function cmdOpenDesc(oid){
                newWindow=window.open("open_description.jsp?oid_position="+oid,"OpenDescription", "height=400,width=590, status=yes, toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
            function loadList() {
                var valid_status_select = document.frmposition.valid_status_select.value;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
//                var valid_status_select = 0;
//                if (valid_status_select.length <= 0) { 
//                    valid_status_select = "1";
//                    }
//                alert(""+valid_status_select);
                xmlhttp.open("GET", "position-ajax.jsp?position_name=&valid_status_select="+valid_status_select+"&level_select=0&division_select=0", true);
                xmlhttp.send();
            }
            /* to be continue..*/
            function cmdSearch(){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                var positionName = document.getElementById("position_name").value;
                var validStatus = document.getElementById("valid_status_select").value;
                var level = document.getElementById("level_select").value;
                var division = document.getElementById("division_select").value;
                
                if (positionName.length <= 0){
                    positionName = "";
                }
                if (validStatus.length <= 0){
                    validStatus = "0";
                }
                if (level.length <= 0){
                    level = "0";
                }
                if (division.length <= 0){
                    division = "0";
                }
                
                var url = "position-ajax.jsp?";
                url += "position_name="+positionName;
                url += "&valid_status_select="+validStatus;
                url += "&level_select="+level;
                url += "&division_select="+division;
                xmlhttp.open("GET", url, true);
                xmlhttp.send();
            }
            
            function loadListByPosition(position_name) {
                document.getElementById("valid_status_select").value="0";
                document.getElementById("level_select").value="0";
                document.getElementById("division_select").value="0";
                if (position_name.length == 0) { 
                    position_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name="+position_name+"&valid_status_select=0&level_select=0&division_select", true);
                xmlhttp.send();
            }
            
            function loadListByValidStatus(valid_status) {
                document.getElementById("position_name").value="";
                document.getElementById("level_select").value="0";
                document.getElementById("division_select").value="0";
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name=0&valid_status_select="+valid_status+"&level_select=0&division_select=0", true);
                xmlhttp.send();
            }
            
            function loadListByLevel(level_value) {
                document.getElementById("position_name").value="";
                document.getElementById("valid_status_select").value="0";
                document.getElementById("division_select").value="0";
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name=0&valid_status_select=0&level_select="+level_value+"&division_select=0", true);
                xmlhttp.send();
            }
            
            function loadListByDivision(division_select) {
                document.getElementById("position_name").value="";
                document.getElementById("valid_status_select").value="0";
                document.getElementById("level_select").value="0";
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name=0&valid_status_select=0&level_select=0&division_select="+division_select, true);
                xmlhttp.send();
            }
            
            function loadFiturOff(){
                document.getElementById("position_name").value="";
                document.getElementById("valid_status_select").value="0";
                document.getElementById("level_select").value="0";
                document.getElementById("division_select").value="0";
                
                document.getElementById("position_name").disabled="disabled";
                document.getElementById("valid_status_select").disabled="disabled";
                document.getElementById("level_select").disabled="disabled";
                document.getElementById("division_select").disabled="disabled";
                loadList();
            }
            
            function loadFiturOn(){
                document.getElementById("position_name").disabled="";
                document.getElementById("valid_status_select").disabled="";
                document.getElementById("level_select").disabled="";
                document.getElementById("division_select").disabled="";
            }

            function cmdListFirst(start){      
                var valid_status_select = document.frmposition.valid_status_select.value;
                var position_name = document.frmposition.position_name.value;
                var level = document.frmposition.level_select.value;
                var division = document.frmposition.division_select.value;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name="+position_name+"&valid_status_select="+valid_status_select+"&level_select="+level+"&division_select="+division+"&command=" + <%=Command.FIRST%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListPrev(start){
                var valid_status_select = document.frmposition.valid_status_select.value;
                var position_name = document.frmposition.position_name.value;
                var level = document.frmposition.level_select.value;
                var division = document.frmposition.division_select.value;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name="+position_name+"&valid_status_select="+valid_status_select+"&level_select="+level+"&division_select="+division+"&command=" + <%=Command.PREV%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListNext(start){
                var valid_status_select = document.frmposition.valid_status_select.value;
                var position_name = document.frmposition.position_name.value;
                var level = document.frmposition.level_select.value;
                var division = document.frmposition.division_select.value;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name="+position_name+"&valid_status_select="+valid_status_select+"&level_select="+level+"&division_select="+division+"&command=" + <%=Command.NEXT%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListLast(start){
                var valid_status_select = document.frmposition.valid_status_select.value;
                var position_name = document.frmposition.position_name.value;
                var level = document.frmposition.level_select.value;
                var division = document.frmposition.division_select.value;
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "position-ajax.jsp?position_name="+position_name+"&valid_status_select="+valid_status_select+"&level_select="+level+"&division_select="+division+"&command=" + <%=Command.LAST%> + "&start="+start, true);
                xmlhttp.send();
            }
        </script>
        
        <style type="text/css">
            #listPos {background-color: #FFF; border: 1px solid #CCC; padding: 3px 9px; cursor: pointer; margin: 1px 0px;}  
            #btn {
                padding: 3px 5px; 
                border: 1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }

            #btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}

            .title_content {
                padding: 9px 14px; 
                border-left: 1px solid #0099FF; 
                font-size: 14px; 
                background-color: #F3F3F3; 
                color:#0099FF;
                font-weight: bold;
            }

            .part_content {
                border-radius: 5px;
                background-color: #F5F5F5;
            }
            .part_name {
                padding: 12px 19px;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                background-color: #a9d5f2;
                color:#04619e;
                font-weight: bold;
                font-size: 12px;
            }
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
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

            .delete-confirm {
                padding: 7px 12px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #delete-message {
                font-weight: bold;
            }
            .btn-delete {
                padding: 3px;
                border: 1px solid #CF5353; 
                background-color: #CF5353; 
                color: #FFF; 
                font-size: 11px; 
                cursor: pointer;
            }
            .info {
                background-color: #a9d5f2;
                color: #04619e;
                padding: 21px;
                border-radius: 3px;
            }

            #box-ask-company {
                visibility: hidden;
            }

            #box-ask-division {
                visibility: hidden;
            }

            #box-ask-depart {
                visibility: hidden;
            }

            #box-ask-section {
                visibility: hidden;
            }
            #box-ask-level{
                visibility: hidden;
            }
            
            #box-ask-grade {
                visibility: hidden;
            }
            
            #box-ask-klasifikasi {
                visibility: hidden;
            }
            
            #box-ask-competency {
                visibility: hidden;
            }
            
            #box-ask-assessment {
                visibility: hidden;
            }

            #box-ask-training {
                visibility: hidden;
            }

            #box-ask-education {
                visibility: hidden;
            }
            
            #box-ask-experience {
                visibility: hidden;
            }

            #box-ask-kpi {
                visibility: hidden;
            }
        </style>
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #tbl_form {
                
            }
            #tbl_form td {
                font-size: 12px;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
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
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
        $(function() {
            var customCommand = "<%=FRMQueryString.requestString(request, "custom_command") %>";
            var focus = "<%=FRMQueryString.requestString(request, "focus") %>";
            $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
            $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
            $(window).scrollTop($("select[name='select_level']"));
            $(function() {
                $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
                $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
                $(window).scrollTop($("select[name='select_level']"));
                if(customCommand!=0){
                    var name = "";
                    switch(customCommand){
                        case "8":
                            name="select_level";
                            break;
                        case "4":
                            name="select_section";
                            break;
                        case "3":
                            name="select_depart";
                            break;
                        case "2":
                            name="select_divisi";
                            break;
                        case "1":
                            name="select_company";
                            break;
                                
                    }
                    $('html, body').animate({
                        scrollTop: $("select[name='"+name+"']").offset().top+$(window).height()
                    }, 1000);
                }
                if(focus!=0){
                    var name = "";
                    switch(focus){
                        case "8":
                            name="select_level";
                            break;
                        case "4":
                            name="select_section";
                            break;
                        case "3":
                            name="select_depart";
                            break;
                        case "2":
                            name="select_divisi";
                            break;
                        case "1":
                            name="select_company";
                            break;
                                
                    }
                    $('html, body').animate({
                        scrollTop: $("select[name='"+name+"']").offset().top+$(window).height()
                    }, 1000);
                }
            });
        });
        </script>
    </head>
    <body onload="loadList()">
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
            <span id="menu_title">Masterdata <strong style="color:#333;"> / </strong><%=dictionaryD.getWord(I_Dictionary.POSITION)%></span>
        </div>
        <div class="content-main">
            <form name="frmposition" method ="post" action="">
                <input type="hidden" name="min_grade_req_id" value="<%=oidGradeMin%>">
                <input type="hidden" name="max_grade_req_id" value="<%=oidGradeMax%>"> 
                <input type="hidden" name="custom_command" value="<%=customCommand%>">
                <input type="hidden" name="focus" value="<%=focus%>">
                <input type="hidden" name="ask_command" value="<%=askCommand%>">
                <input type="hidden" name="delete_command" value="<%=deleteCommand%>">
                <input type="hidden" name="position_map_id" value="<%=customCommand%>">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" name="hidden_position_id" value="<%=oidPosition%>">
                <input type="hidden" name="competency_id" value="<%=oidCompetency%>" />
                <input type="hidden" name="assessment_id" value="<%=oidAssessment%>" />
                <input type="hidden" id="delete_grade_id" name="delete_grade_id" value="<%=deleteGradeId%>" />
                <input type="hidden" id="delete_competency_id" name="delete_competency_id" value="<%=deleteCompetencyId%>" />
                <input type="hidden" id="delete_assessment_id" name="delete_competency_id" value="<%=deleteAssessmentId%>" />
                <input type="hidden" id="delete_training_id" name="delete_training_id" value="<%=deleteTrainingId%>" />
                <input type="hidden" id="delete_klasifikasi_id" name="delete_klasifikasi_id" value="<%=deleteKlasifikasiId%>" />
                <input type="hidden" id="delete_edu_id" name="delete_edu_id" value="<%=deleteEduId%>" />
                <input type="hidden" id="delete_exp_id" name="delete_exp_id" value="<%=deleteExpId%>" />
                <input type="hidden" id="training_id" name="training_id" value="<%=oidTraining%>" />
                <input type="hidden" id="education_id" name="education_id" value="<%=oidEducation%>" />
                <input type="hidden" id="experience_id" name="experience_id" value="<%=oidExperience%>" />
                <input type="hidden" id="klasifikasi_id" name="klasifikasi_id" value="<%=oidKlasifikasi%>" />
                <input type="hidden" id="kpi_id" name="kpi_id" value="<%=oidKPI%>" />
                <input type="hidden" id="pos_kpi_id" name="pos_kpi_id" value="<%=oidPosKPI%>" />
                <input type="hidden" name="kpi_command" value="<%=kpiCommand%>">
                <input type="hidden" name="command_pos_mapping" value="<%=commandPosMapping%>">
                <input type="hidden" id="oid_pos_comp" name="oid_pos_comp" value="<%=oidPosComp%>">
                <input type="hidden" id="oid_pos_divi" name="oid_pos_divi" value="<%=oidPosDivi%>">
                <input type="hidden" id="oid_pos_depart" name="oid_pos_depart" value="<%=oidPosDepart%>">
                <input type="hidden" id="oid_pos_sec" name="oid_pos_sec" value="<%=oidPosSec%>">
                <input type="hidden" id="oid_pos_level" name="oid_pos_level" value="<%=oidPosLevel%>">

                <table>
                    <tr>
                        <td style="font-size: 12px; font-weight: bold; border-bottom: 1px solid #DDD; padding-bottom: 7px;" colspan="4">
                            <input type="radio" id="rb_pencarian" onclick="loadFiturOff()" name="rb_pencarian" value="0" />Without Filter
                            <input type="radio" id="rb_pencarian" onclick="loadFiturOn()" name="rb_pencarian" value="1" />With Filter
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Position Name</td>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Valid Status</td>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Level</td>
                        <td style="font-size: 12px; font-weight: bold; padding-top: 7px;">Division</td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td><input type="text" style="padding:5px 7px" id="position_name" name="position_name" placeholder="Searching..." size="70" /> </td>
                        <td>
                            <select id="valid_status_select" name="valid_status_select" style="padding:4px 6px">
                                <option value="3">- All -</option>
                                <option selected value="<%=PstPosition.VALID_ACTIVE%>"><%= PstPosition.validStatusValue[PstPosition.VALID_ACTIVE] %></option>
                                <option value="<%=PstPosition.VALID_HISTORY%>"><%= PstPosition.validStatusValue[PstPosition.VALID_HISTORY] %></option>
                            </select>
                        </td>
                        <td>
                            <select id="level_select" name="level_select" style="padding:4px 6px">
                                <option value="0">-SELECT-</option>
                                <%
                                Vector listLevelSelect = PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL]);
                                if (listLevelSelect != null && listLevelSelect.size()>0){
                                    for(int i=0; i<listLevelSelect.size(); i++){
                                        Level level = (Level)listLevelSelect.get(i);
                                        %>
                                        <option value="<%=level.getOID()%>"><%= level.getLevel() %></option>
                                        <%
                                    }
                                }
                                %>
                            </select>
                        </td>
                        <td>
                            <select id="division_select" name="division_select" style="padding:4px 6px">
                                <option value="0">-SELECT-</option>
                                <%
                                Vector listDivisiSelect = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                                if (listDivisiSelect != null && listDivisiSelect.size()>0){
                                    for(int i=0; i<listDivisiSelect.size(); i++){
                                        Division divSelect = (Division)listDivisiSelect.get(i);
                                        %>
                                        <option value="<%=divSelect.getOID()%>"><%=divSelect.getDivision()%></option>
                                        <%
                                    }
                                }
                                %>
                            </select>
                        </td>
                        <td>
                            <a class="btn" style="color:#FFF" href="javascript:cmdSearch()">Search</a>
                        </td>
                    </tr>
                </table>
                <div>&nbsp;</div>
                <div style="margin-bottom: 13px;">
                    <% if(privAdd){ %>
                    <a class="btn" style="color:#FFF" href="javascript:cmdAdd()">Tambah Data</a>
                    <% } %>
                    <a class="btn" style="color:#FFF" href="javascript:cmdExportExcel()">Export To Excel</a>
                </div>
                <div id="div_respon"></div>
                <div>&nbsp;</div>
                <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&( iErrCode>0 ||frmPosition.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                        <table border="0" cellspacing="2" cellpadding="2">
                          <tr> 
                              <td colspan="2"><div class="title_part"><%=oidPosition == 0?"Add":"Edit"%> Position</div></td>
                          </tr>
                          <tr> 
                            <td valign="top">
                              <table id="tbl_form">
                                <tr align="left" valign="top"> 
                                  <td valign="top">&nbsp;</td>
                                  <td class="comment">&nbsp;</td>
                                  <td class="comment">*)entry 
                                    required </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Position</td>
                                  <td>:</td>
                                  <td> 
                                      <input type="text" size="70" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION] %>"  value="<%= position.getPosition() %>" class="elemenForm" size="30">
                                    *<%=frmPosition.getErrorMsg(FrmPosition.FRM_FIELD_POSITION)%> </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Position Alias</td>
                                  <td>:</td>
                                  <td> 
                                      <input type="text" size="70" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_ALIAS] %>"  value="<%= position.getAlias() %>" class="elemenForm" size="30">
                                    *<%=frmPosition.getErrorMsg(FrmPosition.FRM_FIELD_ALIAS)%> </td>
                                </tr>

                                <%if(attdConfig!=null && attdConfig.getConfigurationShowPositionCode()){%>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Position Kode</td>
                                  <td>:</td>
                                  <td> 
                                    <input type="text" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE] %>"  value="<%= position.getKodePosition() %>" class="elemenForm" size="30">
                                    *<%=frmPosition.getErrorMsg(FrmPosition.FRM_FIELD_POSITION_KODE)%> </td>
                                </tr>
                                <%}else{%>
                                    <input type="hidden" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_POSITION_KODE] %>"  value="<%= "-" %>" class="elemenForm" size="30">
                                <%}%>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Type</td>
                                  <td>:</td>
                                  <td> 
                                    <%
                                    Vector levelKey = new Vector(1,1);
                                    Vector levelValue = new Vector(1,1);
                                    for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){																							
                                         levelKey.add(PstPosition.strPositionLevelNames[idx]);
                                        levelValue.add(PstPosition.strPositionLevelValue[idx]);														
                                    }
                                    /*
                                    levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_GENERAL]);
                                    levelValue.add(""+PstPosition.LEVEL_GENERAL);														

                                    levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_SECRETARY]);
                                    levelValue.add(""+PstPosition.LEVEL_SECRETARY);														

                                    levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_SUPERVISOR]);
                                    levelValue.add(""+PstPosition.LEVEL_SUPERVISOR);   														

                                    levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_MANAGER]);
                                    levelValue.add(""+PstPosition.LEVEL_MANAGER);														

                                    levelKey.add(PstPosition.strPositionLevelNames[PstPosition.LEVEL_GENERAL_MANAGER]);
                                    levelValue.add(""+PstPosition.LEVEL_GENERAL_MANAGER);														
                                    */
                                    out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_POSITION_LEVEL], "formElemen", null, ""+position.getPositionLevel(), levelValue, levelKey));
                                %>
                                     &nbsp; type for payroll : <%
                                   Vector levelPayrolKey = new Vector(1,1);
                                    Vector levelPayrolValue = new Vector(1,1);
                                     levelPayrolValue.add("-1");
                                     levelPayrolKey.add(" - None - ");
                                    for(int idx=0; idx < PstPosition.strPositionLevelNames.length;idx++){																							
                                         levelPayrolKey.add(PstPosition.strPositionLevelNames[idx]);
                                        levelPayrolValue.add(PstPosition.strPositionLevelValue[idx]);														
                                    }
                                    out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_POSITION_LEVEL_PAYROL], "formElemen", null, ""+position.getPositionLevelPayrol(), levelPayrolValue, levelPayrolKey));
                                %>
                                 *<%=frmPosition.getErrorMsg(FrmPosition.FRM_FIELD_POSITION_LEVEL_PAYROL)%> 
                                  </td> 

                                </tr>
                                <!--Gede_8Maret2012{ -->
                                <tr align="left" valign="top">
                                  <td valign="middle">Head Title</td>
                                  <td>:</td>
                                  <td>
                                    <%  try{
                                        Vector headKey = new Vector(1,1);
                                        Vector headValue = new Vector(1,1);
                                        for(int idx=0; idx < PstPosition.strHeadTitle.length;idx++){
                                             headKey.add(PstPosition.strHeadTitle[idx]);
                                            headValue.add(""+PstPosition.strHeadTitleInt[idx]);
                                        }
                                        out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_HEAD_TITLE], "formElemen", null, ""+position.getHeadTitle(), headValue, headKey));
                                        }
                                        catch (Exception e){
                                        System.out.println("Error on head title : "+e.toString());
                                        }
                                    %>
                                  </td>
                                </tr>
                                <tr align="left" valign="top">
                                  <td valign="middle">Jenis Jabatan</td>
                                  <td>:</td>
                                  <td>
                                    <%  try{
                                        Vector jenisKey = new Vector(1,1);
                                        Vector jenisValue = new Vector(1,1);
                                        for(int idx=0; idx < PstPosition.strJenisJabatan.length;idx++){
                                             jenisKey.add(PstPosition.strJenisJabatan[idx]);
                                            jenisValue.add(""+idx);
                                        }
                                        out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_JENIS_JABATAN], "formElemen", null, ""+position.getJenisJabatan(), jenisValue, jenisKey));
                                        }
                                        catch (Exception e){
                                        System.out.println("Error on head title : "+e.toString());
                                        }
                                    %>
                                  </td>
                                </tr>
                                <tr align="left" valign="top">
                                  <td valign="middle">Klasifikasi Jabatan</td>
                                  <td>:</td>
                                  <td>
                                    <%  try{
                                        Vector posTypeKey = new Vector(1,1);
                                        Vector posTypeValue = new Vector(1,1);
                                        Vector listPositionType = PstPositionType.list(0, 0, "", PstPositionType.fieldNames[PstPositionType.FLD_TYPE]);
                                        posTypeKey.add("Select..");
                                        posTypeValue.add("");
                                        for(int idx=0; idx < listPositionType.size();idx++){
                                            PositionType posType = (PositionType) listPositionType.get(idx);
                                            posTypeKey.add(posType.getType());
                                            posTypeValue.add(""+posType.getOID());
                                        }
                                        out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_POSITION_TYPE_ID], "formElemen", null, ""+position.getPosTypeId(), posTypeValue, posTypeKey));
                                        }
                                        catch (Exception e){
                                        System.out.println("Error on head title : "+e.toString());
                                        }
                                    %>
                                  </td>
                                </tr>
                                <tr>
                                    <td valign="middle">Valid Status</td>
                                    <td valign="middle">:</td>
                                    <td valign="middle">
                                        <select name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_VALID_STATUS]%>">
                                            <%
                                            if (position.getValidStatus()==PstPosition.VALID_ACTIVE){
                                                %>
                                                <option value="<%=PstPosition.VALID_ACTIVE%>" selected="selected">Active</option>
                                                <option value="<%=PstPosition.VALID_HISTORY%>">History</option>
                                                <%
                                            } else {
                                                %>
                                                <option value="<%=PstPosition.VALID_ACTIVE%>">Active</option>
                                                <option value="<%=PstPosition.VALID_HISTORY%>" selected="selected">History</option>
                                                <%
                                            }
                                            %>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="middle">Masa berlaku</td>
                                    <td valign="middle">:</td>
                                    <td valign="middle">
                                        <%
                                        String DATE_FORMAT_NOW = "yyyy-MM-dd";
                                        Date dateStart = position.getValidStart() == null ? new Date() : position.getValidStart();
                                        Date dateEnd = position.getValidEnd() == null ? new Date() : position.getValidEnd();
                                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                                        String strValidStart = sdf.format(dateStart);
                                        String strValidEnd = sdf.format(dateEnd);
                                        %>
                                        <input type="text" name="<%=frmPosition.fieldNames[frmPosition.FRM_FIELD_VALID_START]%>" id="datepicker1" value="<%=strValidStart%>" />&nbsp;to
                                        &nbsp;<input type="text" name="<%=frmPosition.fieldNames[frmPosition.FRM_FIELD_VALID_END]%>" id="datepicker2" value="<%=strValidEnd%>" />
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="middle">Level</td>
                                    <td valign="middle">:</td>
                                    <td valign="middle">
                                        <select name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_LEVEL_ID]%>">
                                            <option value="0">-SELECT-</option>
                                            <%
                                            String orderLevel = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" DESC";
                                            Vector listLevel = PstLevel.list(0, 0, "", orderLevel);
                                            if (listLevel != null && listLevel.size()>0){
                                                for(int l=0; l<listLevel.size(); l++){
                                                    Level level = (Level)listLevel.get(l);
                                                    if (level.getOID()==position.getLevelId()){
                                                        %>
                                                        <option value="<%=level.getOID()%>" selected="selected"><%=level.getLevel()%></option>
                                                        <%
                                                    } else {
                                                        %>
                                                        <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                                                        <%
                                                    }

                                                }
                                            }
                                            %>
                                        </select>
                                    </td>
                                </tr>
                                
                                <tr align="left" valign="top"> 
                                <td valign="top" width="10%">Tenaga Kerja</td>
                                <td width="1%">:</td>
                                <td width="89%">
                                  <%  try{
                                      Vector headKey = new Vector(1,1);
                                      Vector headValue = new Vector(1,1);
                                      for(int idx=0; idx < PstPosition.strTenagaKerja.length;idx++){
                                           headKey.add(PstPosition.strTenagaKerja[idx]);
                                          headValue.add(""+idx);
                                      }
                                      out.println(ControlCombo.draw(frmPosition.fieldNames[frmPosition.FRM_FIELD_TENAGA_KERJA], "formElemen", null, ""+position.getTenagaKerja(), headValue, headKey));
                                      }
                                      catch (Exception e){
                                      System.out.println("Error on head title : "+e.toString());
                                      }
                                  %>
                                </td>
                              </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Show In Pay Input</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="checkbox" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_FLAG_POSITION_SHOW_PAY_INPUT]%>" <%=(position.getFlagShowPayInput()==PstPosition.YES_SHOW_PAY_INPUT ? "checked" : "" ) %> value="1"> 
                                  please check to show in pay input
                                  </td>
                                </tr>  
                                <!--} -->
                                <tr align="left" valign="top">
                                  <td valign="middle">Department</td>
                                  <td>:</td>
                                  <td>
                                  <input type="checkbox" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_ALL_DEPARTMENT]%>" <%=(position.getAllDepartment()==PstPosition.ALL_DEPARTMENT_TRUE ? "checked" : "" ) %> value="1" >
                                  All Department
                                  </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Option</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="checkbox" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_UNDER_SUPERVISOR]%>" <%=(position.getDisabledAppUnderSupervisor()==PstPosition.DISABLED_APP_UNDER_SUPERVISOR_TRUE ? "checked" : "" ) %> value="1">
                                  To DISABLE Leave Approval Employee Under Supervisor please check
                                  </td>
                                </tr>   
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Option</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="checkbox" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_DEPT_SCOPE]%>" <%=(position.getDisabledAppDeptScope()==PstPosition.DISABLED_APP_DEPT_SCOPE_TRUE ? "checked" : "" ) %> value="1" >                                                        
                                  To DISABLE Leave Approval Department Scope please check
                                  </td>
                                </tr>        
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Option</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="checkbox" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DISABLED_APP_DIV_SCOPE]%>" <%=(position.getDisabedAppDivisionScope()==PstPosition.DISABLED_APP_DIV_SCOPE_TRUE ? "checked" : "" ) %> value="1" >                                                        
                                  To DISABLE Leave Approval Division Scope please check
                                  </td>
                                </tr> 

                                <tr align="left" valign="top"> 
                                  <td valign="middle">Time</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="text" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_BEFORE] %>"  value="<%= position.getDeadlineScheduleBefore() %>" class="elemenForm" size="10">
                                  <i style="font-size: 11px">(Hour) Limit Time Update Schedule before current time ( unlimited time = 8640 )</i>
                                  </td>
                                </tr>  
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Time</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="text" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_AFTER] %>"  value="<%= position.getDeadlineScheduleAfter() %>" class="elemenForm" size="10">                                                                                                           
                                  <i style="font-size: 11px">(Hour) Limit Time Update Schedule after current time ( unlimited time = 8640 )</i>
                                  </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Time</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="text" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_LEAVE_BEFORE] %>"  value="<%= position.getDeadlineScheduleLeaveBefore() %>" class="elemenForm" size="10">                                                                                                           
                                  <i style="font-size: 11px">(Hour) Limit Time Update Schedule Leave before current time ( unlimited time = 8640 )</i>
                                  </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                  <td valign="middle">Time</td>
                                  <td>:</td>
                                  <td> 
                                  <input type="text" name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DEDLINE_SCH_LEAVE_AFTER] %>"  value="<%= position.getDeadlineScheduleLeaveAfter() %>" class="elemenForm" size="10">                                                                                                           
                                  <i style="font-size: 11px">(Hour) Limit Time Update Schedule Leave after current time ( unlimited time = 8640 )</i>
                                  </td>
                                </tr>                     

                                <tr align="left" valign="top"> 
                                  <td valign="top"> 
                                    Job Description </td>
                                  <td>:</td>
                                  <td> 
                                    <textarea name="<%=frmPosition.fieldNames[FrmPosition.FRM_FIELD_DESCRIPTION] %>" class="elemenForm" cols="30" rows="3"><%= position.getDescription() %></textarea>
                                    <br><br><button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdJobDescDet()">Job Desc Detail</button>
                                  </td>
                                </tr>
                                <% if (iCommand == Command.EDIT){ %>
                                <tr>
                                    <td valign="top">Grade<br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddGrade()">Add</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-grade">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteGrade()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelGrade()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereGrade = "";
                                        whereGrade = PstPositionGradeRequired.fieldNames[PstPositionGradeRequired.FLD_POSITION_ID]+"="+oidPosition;
                                        Vector listPosGrade = new Vector();
                                        listPosGrade = PstPositionGradeRequired.listInnerJoin(whereGrade);
                                        if(listPosGrade!= null && listPosGrade.size() > 0){
                                            %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">&nbsp;</td>
                                                    <td class="title_tbl">Grade Min</td>
                                                    <td class="title_tbl">Grade Max</td>
                                                    <!--td class="title_tbl">Note</td-->
                                                    <td class="title_tbl">Action</td>
                                                </tr>
                                                <%
                                                for(int k = 0; k < listPosGrade.size(); k++){
                                                    PositionGradeRequired posGradeReq = (PositionGradeRequired)listPosGrade.get(k);
                                                    GradeLevel gradeMin = posGradeReq.getGradeMinimum(); 
                                                    GradeLevel gradeMax = posGradeReq.getGradeMaximum(); 
                                                    %>
                                                <tr>
                                                    <td><%=(k+1)%><!--a href="javascript:cmdEditGrade('<%=posGradeReq.getOID()%>')">[]</a--></td>
                                                    <td><%=gradeMin.getCodeLevel() %></td>
                                                    <td><%=gradeMax.getCodeLevel() %></td>
                                                    <!--td><%=posGradeReq.getNote() %></td-->
                                                    <td><a id="btn" onclick="cmdAskGrade('<%=posGradeReq.getOID()%>')">&times;</a></td>
                                                </tr>
                                                    <%
                                                }
                                                %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                
                                <tr>
                                    <td valign="top">Competency<br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddCompetency()">Add</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-competency">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteCompetency()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelCompet()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String where = ""+oidPosition;
                                        Vector listPosCompetency = new Vector();
                                        listPosCompetency = PstPositionCompetencyRequired.listInnerJoin(where);
                                        if(listPosCompetency!= null && listPosCompetency.size() > 0){
                                            %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">Level Satuan Kerja</td>
                                                    <td class="title_tbl">Level Unit</td>
                                                    <td class="title_tbl">Level Sub Unit</td>
                                                    <td class="title_tbl">Level</td>
                                                    <td class="title_tbl">Competency Name</td>
                                                    <td class="title_tbl">Score Min</td>
                                                    <td class="title_tbl">Score Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                                <%
                                                 Hashtable listHashLevel = (Hashtable) PstLevel.hashlistLevel();
                                                for(int k = 0; k < listPosCompetency.size(); k++){
                                                    Vector vect = (Vector)listPosCompetency.get(k);
                                                    PositionCompetencyRequired posCom = (PositionCompetencyRequired)vect.get(0);
                                                    Competency comp = (Competency)vect.get(1); 
                                                    Level compLevel = null;
                                                    try{
                                                     compLevel = (Level) listHashLevel.get(posCom.getCompetencyLevelId());
                                                    }catch(Exception exc){
                                                      compLevel = null; 
                                                    }
                                                    %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditCompetency('<%=posCom.getOID()%>')"><%=PstDivision.divisionLevelName[posCom.getLevelDivision()]%></a></td>
                                                    <td><%=PstDepartment.departmentLevelName[posCom.getLevelDepartment()]%></td>
                                                    <td><%=PstSection.sectionLevelName[posCom.getLevelSection()]%></td>
                                                    <td><%=compLevel == null ? "Level Not Selected" : compLevel.getLevel()%></td>
                                                    <td><%=comp.getCompetencyName()%></td>
                                                    <td><%=posCom.getScoreReqMin()%></td>
                                                    <td><%=posCom.getScoreReqRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskCompetency('<%=posCom.getOID()%>')">&times;</a></td>
                                                </tr>
                                                    <%
                                                }
                                                %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Assessment<br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddAssessment()">Add</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-assessment">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteAssessment()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelAss()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereAss = "";
                                        whereAss = ""+oidPosition;
                                        Vector listPosAss = new Vector();
                                        listPosAss = PstPositionAssessmentRequired.listInnerJoin(whereAss);
                                        if(listPosAss!= null && listPosAss.size() > 0){
                                            %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">Assessment Name</td>
                                                    <td class="title_tbl">Score Min</td>
                                                    <td class="title_tbl">Score Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                                <%
                                                for(int k = 0; k < listPosAss.size(); k++){
                                                    Vector vect = (Vector)listPosAss.get(k);
                                                    PositionAssessmentRequired posAss = (PositionAssessmentRequired)vect.get(0);
                                                    Assessment ass = (Assessment)vect.get(1); 
                                                    %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditAssessment('<%=posAss.getOID()%>')"><%=ass.getAssessmentType()%></a></td>
                                                    <td><%=posAss.getScoreReqMin()%></td>
                                                    <td><%=posAss.getScoreReqRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskAssessment('<%=posAss.getOID()%>')">&times;</a></td>
                                                </tr>
                                                    <%
                                                }
                                                %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Training<br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddTraining()">Add</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-training">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteTraining()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelTrain()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereTrain = "";
                                        whereTrain = ""+oidPosition;
                                        Vector listPosTrain = new Vector();
                                        listPosTrain = PstPositionTrainingRequired.listInnerJoin(whereTrain);
                                        if(listPosTrain!= null && listPosTrain.size() > 0){
                                            if (askCommand == 2){ %>
                                            <div id="confirm">
                                                <strong>Are you sure to delete item ?</strong> &nbsp;
                                                <button id="btn1" onclick="javascript:cmdDeleteTraining('<%=deleteTrainingId%>')">Yes</button>
                                                &nbsp;<button id="btn1" onclick="javascript:cmdBackConfirm()">No</button>
                                            </div>
                                            <% } %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">Training Name</td>
                                                    <td class="title_tbl">Score Min</td>
                                                    <td class="title_tbl">Score Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                                <%
                                            for(int k = 0; k < listPosTrain.size(); k++){
                                                Vector vect = (Vector)listPosTrain.get(k);
                                                PositionTrainingRequired posT = (PositionTrainingRequired)vect.get(0);
                                                Training train = (Training)vect.get(1); 
                                                %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditTraining('<%=posT.getOID()%>')"><%=train.getName()%></a></td>
                                                    <td><%=posT.getPointMin()%></td>
                                                    <td><%=posT.getPointRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskTraining('<%=posT.getOID()%>')">&times;</a></td>
                                                </tr>
                                                <%
                                            }
                                            %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Education<br> <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddEducation()">Add</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-education">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteEducation()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelEdu()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereEdu = "";
                                        whereEdu = ""+oidPosition;
                                        Vector listPosEdu = new Vector();
                                        listPosEdu = PstPositionEducationRequired.listInnerJoin(whereEdu);
                                        if(listPosEdu!= null && listPosEdu.size() > 0){
                                            %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">Education</td>
                                                    <td class="title_tbl">Point Min</td>
                                                    <td class="title_tbl">Point Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                                <%
                                            for(int k = 0; k < listPosEdu.size(); k++){
                                                Vector vect = (Vector)listPosEdu.get(k);
                                                PositionEducationRequired posE = (PositionEducationRequired)vect.get(0);
                                                Education ed = (Education)vect.get(1);
                                                %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditEducation('<%=posE.getOID()%>')"><%=ed.getEducation()%></a></td>
                                                    <td><%=posE.getPointMin()%></td>
                                                    <td><%=posE.getPointRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskEducation('<%=posE.getOID()%>')">&times;</a></td>
                                                </tr>
                                                <%
                                            }
                                            %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Experience<br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddExperience()">Add </button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                        
                                        <div class="delete-confirm" id="box-ask-experience">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteExperience()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelExp()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereExp = "";
                                        whereExp = ""+oidPosition;
                                        Vector listPosExp = new Vector();
                                        listPosExp = PstPositionExperienceRequired.listInnerJoin(whereExp);
                                        if(listPosExp!= null && listPosExp.size() > 0){
                                            %>
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">Experience</td>
                                                    <td class="title_tbl">Duration Min</td>
                                                    <td class="title_tbl">Duration Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                                <%
                                            for(int k = 0; k < listPosExp.size(); k++){
                                                Vector vect = (Vector)listPosExp.get(k);
                                                PositionExperienceRequired posExp = (PositionExperienceRequired)vect.get(0);
                                                Position pos = (Position)vect.get(1);
                                                %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditExperience('<%=posExp.getOID()%>')"><%=pos.getPosition()%></a></td>
                                                    <td><%=posExp.getDurationMin()%></td>
                                                    <td><%=posExp.getDurationRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskExperience('<%=posExp.getOID()%>')">&times;</a></td>
                                                </tr>
                                                <%
                                            }
                                            %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">Key Performance Indicator (KPI) <br>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdAddKpi()">Add KPI</button>
                                        <button id="btn" style="margin-bottom: 3px;" onclick="javascript:cmdRefresh()">Refresh</button>
                                    </td>
                                    <td valign="top">:</td>
                                    <td valign="top">
                                       
                                        <div class="delete-confirm" id="box-ask-kpi">
                                            <div id="delete-message">
                                                Are you sure to delete data?
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdDeleteKpi()">Yes</a>
                                                <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelDelKpi()">No</a>
                                            </div>
                                        </div>
                                        <%
                                        String whereKpi = "";
                                        whereKpi = ""+oidPosition;
                                        Vector listPosKpi = new Vector();
                                        listPosKpi = PstPositionKPI.listInnerJoin(whereKpi);
                                        if(listPosKpi!= null && listPosKpi.size() > 0){
                                            %>
                                            
                                            <table cellspacing="0" cellpadding="0" class="tblStyle" style="background-color: #FFF">
                                                <tr>
                                                    <td class="title_tbl">KPI Name</td>
                                                    <td class="title_tbl">Score Min</td>
                                                    <td class="title_tbl">Score Req</td>
                                                    <td class="title_tbl">&nbsp;</td>
                                                </tr>
                                            <%
                                            for(int k = 0; k < listPosKpi.size(); k++){
                                                Vector vect = (Vector)listPosKpi.get(k);
                                                PositionKPI posKpi = (PositionKPI)vect.get(0);
                                                KPI_List kpiList = (KPI_List)vect.get(1);

                                                %>
                                                <tr>
                                                    <td><a href="javascript:cmdEditKpi('<%=posKpi.getOID()%>')"><%=kpiList.getKpi_title()%></a></td>
                                                    <td><%=posKpi.getScoreMin()%></td>
                                                    <td><%=posKpi.getScoreRecommended()%></td>
                                                    <td><a id="btn" onclick="cmdAskKPI('<%=posKpi.getOID()%>')">&times;</a></td>
                                                </tr>

                                                <%
                                            }
                                            %>
                                            </table>
                                            <%
                                        }
                                        %>
                                    </td>
                                </tr>
                                <% } %>
                              </table>
                            </td>
                            <td valign="top">
                                <% if (iCommand == Command.EDIT){ %>
                                <div class="part_content">
                                    <div class="part_name">
                                        Position Availability
                                    </div>
                                    <div style="padding: 12px 19px;">

                                        <table>
                                            <tr>
                                                <td valign="top">
                                                    <div class="delete-confirm" id="box-ask-company">
                                                        <div id="delete-message">
                                                            Are you sure to delete data?
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdDelPosComp()">Yes</a>
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelPosComp()">No</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <button id="btn" onclick="cmdAddPositionCompany('<%=oidPosition%>')" style="margin:5px 0px 2px 0px">Add Position to Company</button>
                                                    <select name="select_company">
                                                        <option value="0">-select-</option>
                                                        <%
                                                        Vector listCompany = PstCompany.list(0, 0, "", "");
                                                        if (listCompany != null && listCompany.size()>0){
                                                            for(int i=0; i<listCompany.size(); i++){
                                                                Company comp = (Company)listCompany.get(i);
                                                                %>
                                                                <option value="<%=comp.getOID()%>"><%=comp.getCompany()%></option>
                                                                <%
                                                            }
                                                        }
                                                        %>

                                                    </select>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td class="title_tbl">No</td>
                                                            <td class="title_tbl">Company Name</td>
                                                            <td class="title_tbl">&nbsp;</td>
                                                        </tr>
                                                        <%
                                                        String wherePosComp = "";

                                                        if (oidPosition > 0){
                                                           wherePosComp = "POSITION_ID="+oidPosition;

                                                        Vector listOfPosComp = PstPositionCompany.list(0, 0, wherePosComp, "");
                                                        if (listOfPosComp != null && listOfPosComp.size()>0){
                                                            for(int p1=0; p1<listOfPosComp.size(); p1++){
                                                               try{ PositionCompany posComp = (PositionCompany)listOfPosComp.get(p1);
                                                                Company company = PstCompany.fetchExc(posComp.getCompanyId());
                                                                %>
                                                                <tr>
                                                                    <td><%=p1+1%></td>
                                                                    <td><%=company.getCompany()%></td>
                                                                    <td><a id="btn" href="javascript:cmdAskPosComp('<%=posComp.getOID()%>')">&times;</a></td>
                                                                </tr>
                                                                <%}catch(Exception exc){ System.out.println(exc);}
                                                            }
                                                        }
                                                        }
                                                        %>

                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="delete-confirm" id="box-ask-division">
                                                        <div id="delete-message">
                                                            Are you sure to delete data?
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdDelPosDivisi()">Yes</a>
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelPosDivi()">No</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <button id="btn" onclick="cmdAddPositionDivisi('<%=oidPosition%>')" style="margin:5px 0px 2px 0px">Add Position to Division</button>
                                                    <select name="select_divisi">
                                                        <option value="0">-select-</option>
                                                        <%
                                                        orderBy = PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" ASC";
                                                        Vector listDiv = PstDivision.list(0, 0, "", orderBy);
                                                        if (listDiv != null && listDiv.size() > 0){
                                                            for(int d1=0; d1<listDiv.size(); d1++){
                                                                Division divisi = (Division)listDiv.get(d1);
                                                                %>
                                                                <option value="<%=divisi.getOID()%>"><%=divisi.getDivision()%></option>
                                                                <%
                                                            }
                                                        }
                                                        %>

                                                    </select>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td class="title_tbl">No</td>
                                                            <td class="title_tbl">Division Name</td>
                                                            <td class="title_tbl">&nbsp;</td>
                                                        </tr>
                                                        <%
                                                        if (oidPosition != 0){
                                                            String whereDiv = "POSITION_ID="+oidPosition;
                                                            Vector listOfDiv = PstPositionDivision.list(0, 0, whereDiv, "");
                                                            if (listOfDiv != null && listOfDiv.size()>0){
                                                                for(int p2 = 0; p2<listOfDiv.size(); p2++){
                                                                    PositionDivision posDiv = (PositionDivision)listOfDiv.get(p2);
                                                                    String divisi = "";
                                                                    Vector listDivi = PstDivision.list(0, 0, "DIVISION_ID="+posDiv.getDivisionId(), "");
                                                                    if (listDivi != null && listDivi.size()>0){
                                                                        Division div = (Division)listDivi.get(0);
                                                                        divisi = div.getDivision();
                                                                    }
                                                                    %>
                                                                    <tr>
                                                                        <td><%=p2+1%></td>
                                                                        <td><%=divisi%></td>
                                                                        <td><a id="btn" href="javascript:cmdAskPosDivi('<%=posDiv.getOID()%>')">&times;</a></td>
                                                                    </tr>
                                                                    <%
                                                                }
                                                            }
                                                        }
                                                        %>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="delete-confirm" id="box-ask-depart">
                                                        <div id="delete-message">
                                                            Are you sure to delete data?
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdDelPosDepart()">Yes</a>
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelPosDepart()">No</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <button id="btn" onclick="cmdAddPositionDepart('<%=oidPosition%>')" style="margin:5px 0px 2px 0px">Add Position to Department</button>
                                                    <select name="select_depart">
                                                        <option value="0">-select-</option>
                                                        <%
                                                        orderBy = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" ASC";
                                                        Vector listDepart = PstDepartment.list(0, 0, "", orderBy);
                                                        if (listDepart != null && listDepart.size()>0){
                                                            for(int d2=0; d2<listDepart.size(); d2++){
                                                                Department depart = (Department)listDepart.get(d2);
                                                                %>
                                                                <option value="<%=depart.getOID()%>"><%=depart.getDepartment()%></option>
                                                                <%
                                                            }
                                                        }
                                                        %>
                                                    </select>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td class="title_tbl">No</td>
                                                            <td class="title_tbl">Department Name</td>
                                                            <td class="title_tbl">&nbsp;</td>
                                                        </tr>
                                                        <%
                                                        if (oidPosition != 0){
                                                            String wherePosDepart = "POSITION_ID="+oidPosition;
                                                            Vector listOfPosDepart = PstPositionDepartment.list(0, 0, wherePosDepart, "");
                                                            if (listOfPosDepart != null && listOfPosDepart.size()>0){
                                                                for(int p3=0; p3<listOfPosDepart.size(); p3++){
                                                                    PositionDepartment posDepart = (PositionDepartment)listOfPosDepart.get(p3);
                                                                    if (posDepart.getDepartmentId() != 0){
                                                                        Department dep = PstDepartment.fetchExc(posDepart.getDepartmentId());
                                                                        %>
                                                                        <tr>
                                                                            <td><%=p3+1%></td>
                                                                            <td><%=dep.getDepartment()%></td>
                                                                            <td><a id="btn" href="javascript:cmdAskPosDepart('<%=posDepart.getOID()%>')">&times;</a></td>
                                                                        </tr>
                                                                        <%
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        %>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="delete-confirm" id="box-ask-section">
                                                        <div id="delete-message">
                                                            Are you sure to delete data?
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdDelPosSec()">Yes</a>
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelPosSec()">No</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <button id="btn" onclick="cmdAddPositionSection('<%=oidPosition%>')" style="margin:5px 0px 2px 0px">Add Position to Section</button>
                                                    <select name="select_section">
                                                        <option value="0">-select-</option>
                                                        <%
                                                        orderBy = PstSection.fieldNames[PstSection.FLD_SECTION]+" ASC";
                                                        Vector listSection = PstSection.listSectionJoinDepart();
                                                        if (listSection != null && listSection.size()>0){
                                                            for (int s=0; s<listSection.size(); s++){
                                                                Vector secDept = (Vector)listSection.get(s);
                                                                Long secId = (Long)secDept.get(0);
                                                                String secName = (String)secDept.get(1);
                                                                String depart = (String)secDept.get(2);
                                                                //Section section = (Section)listSection.get(s);
                                                                %>
                                                                <option value="<%=secId%>"><%= secName+" - "+depart %></option>
                                                                <%
                                                            }
                                                        }
                                                        %>
                                                    </select>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td class="title_tbl">No</td>
                                                            <td class="title_tbl">Section Name</td>
                                                            <td class="title_tbl">Department Name</td>
                                                            <td class="title_tbl">&nbsp;</td>
                                                        </tr>
                                                        <%
                                                        if (oidPosition > 0){
                                                            String whereSec = "POSITION_ID="+oidPosition;
                                                            Vector listOfSection = PstPositionSection.list(0, 0, whereSec, "");
                                                            if (listOfSection != null && listOfSection.size()>0){
                                                                for(int p3 = 0; p3<listOfSection.size(); p3++){
                                                                    PositionSection posSection = (PositionSection)listOfSection.get(p3);
                                                                    String section = "";
                                                                    String departname = "";
                                                                    /*
                                                                    Vector listSec = PstSection.list(0, 0, "SECTION_ID="+posSection.getSectionId(), "");
                                                                    if (listSec != null && listSec.size()>0){
                                                                        Section sec = (Section)listSec.get(0);
                                                                        section = sec.getSection();
                                                                    }*/
                                                                    try {
                                                                        Section sec = PstSection.fetchExc(posSection.getSectionId());
                                                                        section = sec.getSection();
                                                                        departname = getDepartmentName(sec.getDepartmentId());
                                                                    } catch(Exception e){
                                                                        System.out.println("out=>"+e.toString());
                                                                    }

                                                                    %>
                                                                    <tr>
                                                                        <td><%=p3+1%></td>
                                                                        <td><%=section%></td>
                                                                        <td><%=departname%></td>
                                                                        <td><a id="btn" href="javascript:cmdAskPosSec('<%=posSection.getOID()%>')">&times;</a></td>
                                                                    </tr>
                                                                    <%
                                                                }
                                                            }
                                                        }
                                                        %>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <div class="delete-confirm" id="box-ask-level">
                                                        <div id="delete-message">
                                                            Are you sure to delete data?
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdDelPosLevel()">Yes</a>
                                                            <a class="btn-delete" style="color:#FFF" href="javascript:cmdCancelPosLevel()">No</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top">
                                                    <button id="btn" onclick="cmdAddPositionLevel('<%=oidPosition%>')" style="margin:5px 0px 2px 0px">Add Position to Level</button>                                                   
                                                    <select name="select_level">
                                                    <option value="0">-SELECT-</option>
                                                    <%
                                                    //String orderLevel = PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]+" DESC";
                                                    //Vector listLevel = PstLevel.list(0, 0, "", orderLevel);
                                                    if (listLevel != null && listLevel.size()>0){
                                                        for(int l=0; l<listLevel.size(); l++){
                                                            Level level = (Level)listLevel.get(l);
                                                            if (level.getOID()==position.getLevelId()){
                                                                %>
                                                                <option value="<%=level.getOID()%>" selected="selected"><%=level.getLevel()%></option>
                                                                <%
                                                            } else {
                                                                %>
                                                                <option value="<%=level.getOID()%>"><%=level.getLevel()%></option>
                                                                <%
                                                            }

                                                        }
                                                    }
                                                    %>
                                                </select>
                                                    <table class="tblStyle">
                                                        <tr>
                                                            <td class="title_tbl">No</td>
                                                            <td class="title_tbl">Level Name</td>
                                                            <td class="title_tbl">&nbsp;</td>
                                                        </tr>
                                                        <%
                                                        if (oidPosition != 0){
                                                            String wherePosLevel = "POSITION_ID="+oidPosition;
                                                            Vector listOfPosLevel = PstPositionLevel.list(0, 0, wherePosLevel, "");
                                                            if (listOfPosLevel != null && listOfPosLevel.size()>0){
                                                                for(int p3=0; p3<listOfPosLevel.size(); p3++){
                                                                    PositionLevel posLevel = (PositionLevel)listOfPosLevel.get(p3);
                                                                    if (posLevel.getLevelId() != 0){
                                                                        Level lvl = PstLevel.fetchExc(posLevel.getLevelId());
                                                                        %>
                                                                        <tr>
                                                                            <td><%=p3+1%></td>
                                                                            <td><%=lvl.getLevel()%></td>
                                                                            <td><a id="btn" href="javascript:cmdAskPosLevel('<%=posLevel.getOID()%>')">&times;</a></td>
                                                                        </tr>
                                                                        <%
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        %>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>   
                                <div>&nbsp;</div>
                                <div class="part_content">
                                    <div class="part_name">
                                        Up Link Position
                                    </div>
                                    <div style="padding: 12px 19px;">
                                        <table class="tblStyle">
                                            <tr>
                                                <td class="title_tbl">No</td>
                                                <td class="title_tbl">Position</td>
                                                <td class="title_tbl">Template</td>
                                                <td class="title_tbl">Start Date</td>
                                                <td class="title_tbl">End Date</td>
                                                <td class="title_tbl">Type</td>
                                            </tr>
                                            <%
                                            if (oidPosition > 0){
                                                String whereUpPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_DOWN_POSITION_ID]+"="+oidPosition;
                                                String orderUpPos = " "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+" ";
                                                Vector listUpPos = PstMappingPosition.list(0, 0, whereUpPos, orderUpPos);
                                                int no = 0;
                                                if (listUpPos != null&& listUpPos.size()>0){
                                                    for (int up=0; up<listUpPos.size(); up++){
                                                        MappingPosition upPos = (MappingPosition)listUpPos.get(up);
                                                        no++;
                                                        %>
                                                <tr>
                                                    <td><%=no%></td>
                                                    <td><%=getPositionName(upPos.getUpPositionId())%></td>
                                                    <td><%=getTemplateName(upPos.getTemplateId())%></td>
                                                    <td><%=upPos.getStartDate()%></td>
                                                    <td><%=upPos.getEndDate()%></td>
                                                    <td><%=PstMappingPosition.typeOfLink[upPos.getTypeOfLink()]%></td>
                                                </tr>
                                                        <%
                                                    }
                                                }
                                            }
                                            %>
                                        </table>
                                    </div>
                                </div>
                                <div>&nbsp;</div>
                                <div class="part_content">
                                    <div class="part_name">
                                        Down Link Position
                                    </div>
                                    <div style="padding: 12px 19px;">
                                        <table class="tblStyle">
                                            <tr>
                                                <td class="title_tbl">No</td>
                                                <td class="title_tbl">Position</td>
                                                <td class="title_tbl">Template</td>
                                                <td class="title_tbl">Start Date</td>
                                                <td class="title_tbl">End Date</td>
                                                <td class="title_tbl">Type</td>
                                            </tr>
                                            <%
                                            if (oidPosition > 0){
                                                String whereDownPos = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+"="+oidPosition;
                                                String orderDownPos = " "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+" ";
                                                Vector listDownPos = PstMappingPosition.list(0, 0, whereDownPos, orderDownPos);
                                                int noDown = 0;
                                                if (listDownPos != null&& listDownPos.size()>0){
                                                    for (int down=0; down<listDownPos.size(); down++){
                                                        MappingPosition downPosition = (MappingPosition)listDownPos.get(down);
                                                        noDown++;
                                                        %>
                                                <tr>
                                                    <td><%=noDown%></td>
                                                    <td><%=getPositionName(downPosition.getDownPositionId())%></td>
                                                    <td><%=getTemplateName(downPosition.getTemplateId())%></td>
                                                    <td><%=downPosition.getStartDate()%></td>
                                                    <td><%=downPosition.getEndDate()%></td>
                                                    <td><%=PstMappingPosition.typeOfLink[downPosition.getTypeOfLink()]%></td>
                                                </tr>
                                                        <%
                                                    }
                                                }
                                            }
                                            %>
                                        </table>
                                    </div>
                                </div>
                                <% } else { %>
                                <div class="info">
                                    You can entry data position mapping after add position, and than edit it.
                                </div>
                                <% } %>
                            </td>
                          </tr>
                          <tr> 
                            <td colspan="2"> 
                              <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80%");
                                                    String scomDel = "javascript:cmdAsk('"+oidPosition+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidPosition+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidPosition+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to List Position");
                                                    ctrLine.setSaveCaption("Save Position");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Position");
                                                    ctrLine.setDeleteCaption("Delete Position");

                                                    if (privDelete){
                                                            ctrLine.setConfirmDelCommand(sconDelCom);
                                                            ctrLine.setDeleteCommand(scomDel);
                                                            ctrLine.setEditCommand(scancel);
                                                    }else{ 
                                                            ctrLine.setConfirmDelCaption("");
                                                            ctrLine.setDeleteCaption("");
                                                            ctrLine.setEditCaption("");
                                                    }

                                                    if(privAdd == false  && privUpdate == false){
                                                            ctrLine.setSaveCaption("");
                                                    }

                                                    if (privAdd == false){
                                                            ctrLine.setAddCaption("");
                                                    }

                                                    if(iCommand == Command.ASK)
                                                            ctrLine.setDeleteQuestion(msgString);
                                                    %>
                              <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                            </td>
                          </tr>
                        </table>
                        <%}%>
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
