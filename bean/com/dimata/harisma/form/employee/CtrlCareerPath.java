/* 
 * Ctrl Name  		:  CtrlCareerPath.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.location.Location;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.session.log.SessHistoryLog;
import com.dimata.system.entity.PstSystemProperty;
//import javax.mail.Session;
//import sun.security.action.GetLongAction;

public class CtrlCareerPath extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FRM_DATE_IN_RANGE = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", "Tanggal yang diinputkan sudah ada"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private String msgErrorDep;
    private CareerPath careerPath;
    private Employee employee;
    private PstEmployee pstEmployee;
    private PstCareerPath pstCareerPath;
    private FrmCareerPath frmCareerPath;
    int language = LANGUAGE_DEFAULT;

    public CtrlCareerPath(HttpServletRequest request) {
        msgString = "";
        careerPath = new CareerPath();
        try {
            pstCareerPath = new PstCareerPath(0);
        } catch (Exception e) {;
        }
        frmCareerPath = new FrmCareerPath(request, careerPath);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCareerPath.addError(frmCareerPath.FRM_FIELD_WORK_HISTORY_NOW_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public CareerPath getCareerPath() {
        return careerPath;
    }

    public FrmCareerPath getForm() {
        return frmCareerPath;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCareerPath, long oidEmployee, HttpServletRequest request) {
        return action(cmd, oidCareerPath, oidEmployee, request, "", 0); 
    }
    
    public int action(int cmd, long oidCareerPath, long oidEmployee, HttpServletRequest request, String loginName, long userId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        String updateNeedApproval = com.dimata.system.entity.PstSystemProperty.getValueByName("UPDATE_DATA_NEED_APPROVAL");
        String logDetail = "";
        String logPrev = "";
        String logCurr = "";
        Date nowDate = new Date();
        AppUser appUser = new AppUser();
        Employee emp = new Employee();
        try {
            appUser = PstAppUser.fetch(userId);
            emp = PstEmployee.fetchExc(appUser.getEmployeeId());
        } catch (Exception e) {
            System.out.println("Get AppUser: userId: " + e.toString());
        }
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                CareerPath prevCareerPath = null;
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                        if (sysLog == 1) {
                            prevCareerPath = PstCareerPath.fetchExc(oidCareerPath);

                        }
                    } catch (Exception exc) {
                    }
                }

                careerPath.setOID(oidCareerPath);

                frmCareerPath.requestEntityObject(careerPath);

                careerPath.setEmployeeId(oidEmployee);
                //start dedy-20151123 ambil jumlah data berdasarkan tanggal work
                String str_dt_WorkFrom = Formater.formatDate(careerPath.getWorkFrom(), "yyyy-MM-dd");
                String str_dt_WorkTo = Formater.formatDate(careerPath.getWorkTo(), "yyyy-MM-dd");

                int sumWork = PstCareerPath.getCount("work_from='" + str_dt_WorkFrom + "' && work_to='" + str_dt_WorkTo + "'");
                //end

                //    careerPath.setLocationId(frm);
                //   HttpSession session=request.getSession(false);  
                //   String sesloc = (String)session.getValue("sesloc");  

                //   careerPath.setLocationId(Long.valueOf(sesloc));

                //department
                Vector vector = PstDepartment.list(0, 1, PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + careerPath.getDepartmentId(), ""); //update by satrya 2013-12-19 PstDepartment.list(0,1,PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" = "+careerPath.getDepartmentId(),"");
                if (vector != null && vector.size() > 0) {
                    Department dept = (Department) vector.get(0);
                    careerPath.setDepartment(dept.getDepartment());
                }
                //company
                vector = PstCompany.list(0, 1, PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " = " + careerPath.getCompanyId(), "");
                if (vector != null && vector.size() > 0) {
                    Company comp = (Company) vector.get(0);
                    careerPath.setCompany(comp.getCompany());
                }
                //section
                vector = PstSection.list(0, 1, PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " = " + careerPath.getSectionId(), "");
                if (vector != null && vector.size() > 0) {
                    Section section = (Section) vector.get(0);
                    careerPath.setSection(section.getSection());
                }

                //position
                vector = PstPosition.list(0, 1, PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = " + careerPath.getPositionId(), "");
                if (vector != null && vector.size() > 0) {
                    Position position = (Position) vector.get(0);
                    careerPath.setPosition(position.getPosition());
                }
                //division
                vector = PstDivision.list(0, 1, PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = " + careerPath.getDivisionId(), "");
                if (vector != null && vector.size() > 0) {
                    Division division = (Division) vector.get(0);
                    careerPath.setDivision(division.getDivision());
                }
                //level
                vector = PstLevel.list(0, 1, PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID] + " = " + careerPath.getLevelId(), "");
                if (vector != null && vector.size() > 0) {
                    Level level = (Level) vector.get(0);
                    careerPath.setLevel(level.getLevel());
                }
                //emp_category
                vector = PstEmpCategory.list(0, 1, PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " = " + careerPath.getEmpCategoryId(), "");
                if (vector != null && vector.size() > 0) {
                    EmpCategory empCategory = (EmpCategory) vector.get(0);
                    careerPath.setEmpCategory(empCategory.getEmpCategory());
                }



                vector = PstEmployee.list(0, 1, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + careerPath.getEmployeeId(), "");
                if (vector != null && vector.size() > 0) {
                    Employee emplocation = (Employee) vector.get(0);
                    careerPath.setLocationId(emplocation.getLocationId());
                }

                //Location
                vector = PstLocation.list(0, 1, PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " = " + careerPath.getLocationId(), "");
                if (vector != null && vector.size() > 0) {
                    Location location = (Location) vector.get(0);
                    careerPath.setLocation(location.getName());
                }

                if (frmCareerPath.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                //update by devin 2014-02-05
                /* Update by Hendra Putu - 2016-01-19 */
                //if (!(PstSystemProperty.getValueByName("CLIENT_NAME").equals("BPD"))) { sudah di fixkan..  priska 20160331
                    Vector data = new Vector();
                    if (careerPath.getEmployeeId() != 0) {
                        long oidEmp = careerPath.getEmployeeId();
                        
                        //data = PstCareerPath.dateCareerPath(oidEmp);
                        String whereClauseX = "";
                        if ((careerPath.getHistoryType() == 0) || (careerPath.getHistoryType() == 1)){
                          whereClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 0 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 1 + " )"
                                  + " AND " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP] + " = " + careerPath.getHistoryGroup() ;
                        } else {
                          whereClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee + " AND (" + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 2 + " OR " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_TYPE] + " = " + 3 + " )"
                                  + " AND " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP] + " = " + careerPath.getHistoryGroup() ;
                        }
                        String orderClauseX = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
                        data = PstCareerPath.list(0, 0, whereClauseX, orderClauseX);
                        
                        //cari contract from
                        
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                CareerPath care = (CareerPath) data.get(i);
                                if (careerPath != null && care != null && care.getWorkFrom() != null && care.getWorkTo() != null && careerPath.getWorkFrom() != null && careerPath.getWorkTo() != null) {
                                    Date newStartDate = care.getWorkFrom();
                                    Date newEndDate = care.getWorkTo();
                                    Date startDate = careerPath.getWorkFrom();
                                    Date endDate = careerPath.getWorkTo();
                                    String sTanggalTo = Formater.formatDate(newStartDate, "dd-MM-yyyy");
                                    String sTanggalFrom = Formater.formatDate(newEndDate, "dd-MM-yyyy");
                                    String Error = "" + sTanggalTo + " TO " + sTanggalFrom;
                                    if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.after(careerPath.getWorkFrom()) && newStartDate.before(careerPath.getWorkTo())) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } else if ((oidCareerPath != 0 ? (care.getOID() == oidCareerPath ? false : true) : care.getOID() != oidCareerPath) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                        msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Career Path form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + care.getOID() + "\');\">" + Error + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    } /*else if (newEndDate.equals(endDate)) {
                                     msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                     return RSLT_FRM_DATE_IN_RANGE;
                                     }*/ else {
                                        //maka dia tidak overlap
                                    }
                                }
                            }
                            
                           // careerPath.setContractFrom(PstCareerPath.getNewContractFromOrCommencingDate(careerPath.getEmployeeId()));
                            
                            
                            
                        }
                    }
                //}

                if (careerPath.getOID() == 0) {
                    try {
                        if (sumWork != 0) {
                            msgString = resultText[language][RSLT_UNKNOWN_ERROR] + " " + resultText[language][RSLT_FRM_DATE_IN_RANGE];
                            rsCode = RSLT_FRM_DATE_IN_RANGE;
                        } else {
                            long oid = 0;
                            boolean isValid = true;
                            if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                                oid = pstCareerPath.insertExcPending(this.careerPath);
                                this.careerPath.setOID(oid);
                                
                                String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_QUERY]+" = \""+pstCareerPath.getQuery()+"\" "
                                        + " AND "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_STATUS]+" = 0";
                                Vector listLog = PstLogSysHistory.list(0, 0, whereLog, "");
                                if (listLog.size()>0){
                                    isValid = false;
                                }
                                if (isValid){
                                    request.setAttribute("insert", this.careerPath);
                                    request.setAttribute("query", pstCareerPath.getQuery());
                                    SessHistoryLog.insertNDeleteCareerPath(request);
                                }
                            } else {
                                oid = pstCareerPath.insertExc(this.careerPath);
                            }
                            if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                                if (isValid){
                                    msgString = FRMMessage.getMsg(FRMMessage.MSG_ON_VALIDATION);
                                } else {
                                    msgString = FRMMessage.getMsg(FRMMessage.MSG_EXIST_ON_VALIDATION);
                                }
                            } else {
                                msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                            }
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = 0;
                        boolean isValid = true;
                        if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                            oid = pstCareerPath.updateExcPending(this.careerPath);
                            this.careerPath.setOID(oid);
                            String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_QUERY]+" = \""+pstCareerPath.getQuery()+"\" "
                                    + " AND "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_STATUS]+" = 0";
                            Vector listLog = PstLogSysHistory.list(0, 0, whereLog, "");
                            if (listLog.size()>0){
                                    isValid = false;
                            }
                            if (isValid){
                                request.setAttribute("currData", this.careerPath);
                                request.setAttribute("prevData", prevCareerPath);
                                request.setAttribute("query", pstCareerPath.getQuery());
                                SessHistoryLog.updateCareerPath(request);
                            }
                        } else {
                            oid = pstCareerPath.updateExc(this.careerPath);
                        }
                        if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                            if (isValid){
                                msgString = FRMMessage.getMsg(FRMMessage.MSG_ON_VALIDATION);
                            } else {
                                msgString = FRMMessage.getMsg(FRMMessage.MSG_EXIST_ON_VALIDATION);
                            }
                        } else {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_UPDATED);
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCareerPath != 0) {
                    try {
                        careerPath = PstCareerPath.fetchExc(oidCareerPath);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCareerPath != 0) {
                    try {
                        CareerPath careerPath = PstCareerPath.fetchExc(oidCareerPath);
                        long oid = 0;
                        boolean isValid = true;
                        if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                            oid = PstCareerPath.deleteExcPending(oidCareerPath);
                            String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_QUERY]+" = \""+pstCareerPath.getQuery()+"\" "
                                    + " AND "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_STATUS]+" = 0";
                            Vector listLog = PstLogSysHistory.list(0, 0, whereLog, "");
                            if (listLog.size()>0){
                                    isValid = false;
                            }
                            if (isValid){
                                request.setAttribute("delete", careerPath);
                                request.setAttribute("query", pstCareerPath.getQuery());
                                SessHistoryLog.insertNDeleteWorkHistory(request);
                            }
                        } else {
                            oid = PstCareerPath.deleteExc(oidCareerPath);
                        }
                        
                        if (oid != 0) {
                            if (updateNeedApproval.equals("1") && appUser.getAdminStatus() == 0){
                                if (isValid){
                                    msgString = FRMMessage.getMsg(FRMMessage.MSG_ON_VALIDATION);
                                } else {
                                    msgString = FRMMessage.getMsg(FRMMessage.MSG_EXIST_ON_VALIDATION);
                                }
                            } else {
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            }
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return rsCode;
    }
}