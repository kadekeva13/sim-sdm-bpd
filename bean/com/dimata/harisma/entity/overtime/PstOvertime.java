/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

//package com.dimata.harisma.entity.payroll;

/* package java */
import java.util.Date;
import java.util.Vector;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.sql.ResultSet;

/* package qdep */

import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PublicHolidays;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.form.overtime.FrmOvertime;
import com.dimata.harisma.utility.service.presence.AbsenceAnalyser;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.system.PstSystemProperty;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wiweka
 */
public class PstOvertime extends DBHandler implements I_DBType, I_Language, I_DBInterface, I_PersintentExc {

    public static final String TBL_OVERTIME = "hr_overtime";
    public static final int FLD_OVERTIME_ID = 0;
    public static final int FLD_REQ_DATE = 1;
    public static final int FLD_OV_NUMBER = 2;
    public static final int FLD_OBJECTIVE = 3;
    public static final int FLD_STATUS_DOC = 4;
    public static final int FLD_CUSTOMER_TASK_ID = 5;
    public static final int FLD_LOGBOOK_ID = 6;
    public static final int FLD_REQ_ID = 7;
    public static final int FLD_APPROVAL_ID = 8;
    public static final int FLD_ACK_ID = 9;
    public static final int FLD_COMPANY_ID = 10;
    public static final int FLD_DIVISION_ID = 11;
    public static final int FLD_DEPARTMENT_ID = 12;
    public static final int FLD_SECTION_ID = 13;
    public static final int FLD_COUNT_IDX =14;
    public static final int FLD_COST_DEP_ID=15;
    public static final int FLD_ALLOWANCE =16;
//update by satrya 2013-04-29
    public static final int FLD_TIME_REQ =17;
    public static final int FLD_TIME_APPROVAL=18;
    public static final int FLD_TIME_ACK =19;
    public static final int FLD_APPROVAL_1_ID =20;
    public static final int FLD_TIME_APPROVAL_1 =21;
    public static final int FLD_APPROVAL_2_ID =22;
    public static final int FLD_TIME_APPROVAL_2 =23;
    public static final int FLD_APPROVAL_3_ID =24;
    public static final int FLD_TIME_APPROVAL_3 =25;
    public static final int FLD_APPROVAL_4_ID =26;
    public static final int FLD_TIME_APPROVAL_4 =27;
    public static final int FLD_APPROVAL_5_ID =28;
    public static final int FLD_TIME_APPROVAL_5 =29;
    public static final int FLD_APPROVAL_6_ID =30;
    public static final int FLD_TIME_APPROVAL_6 =31;
    public static final int FLD_OVERTIME_TYPE = 32;
    
    public static final String[] fieldNames = {
        "OVERTIME_ID", "REQ_DATE",
        "OV_NUMBER", "OBJECTIVE","STATUS_DOC",
        "CUSTOMER_TASK_ID", "LOGBOOK_ID",
        "REQ_ID", "APPROVAL_ID",
        "ACK_ID", "COMPANY_ID",
        "DIVISION_ID", "DEPARTMENT_ID",
        "SECTION_ID",
        "COUNT_IDX",
        "COST_DEP_ID",
        "ALLOWANCE",
        "TIME_REQ",
        "TIME_APPROVAL",
        "TIME_ACK",
        "APPROVAL_1_ID",
        "TIME_APPROVAL_1",
        "APPROVAL_2_ID",
        "TIME_APPROVAL_2",
        "APPROVAL_3_ID",
        "TIME_APPROVAL_3",
        "APPROVAL_4_ID",
        "TIME_APPROVAL_4",
        "APPROVAL_5_ID",
        "TIME_APPROVAL_5",
        "APPROVAL_6_ID",
        "TIME_APPROVAL_6",
        "OVERTIME_TYPE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT+TYPE_AI,
        TYPE_LONG,
        TYPE_INT,
        //update by satrya 2013-04-30
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT
    };
    public static final int DRAFT = 1;
    public static final int APPROVE = 2;
    public static final int FINAL = 3;
    public static final String[] statusNames = {
       "Draft", "To Be Approval", "Final"
    };
    public static final  double KOSNTANTA_OVERTIME =0.005780347;//dari 1/173 merupakan standarisai dari overtime
    public static Vector getLevelKeys() {
        Vector keys = new Vector();

        for(int i=0; i<statusNames.length; i++) {
            keys.add(statusNames[i]);
        }

        return keys;
    }

    public static Vector getLevelValues() {
        Vector values = new Vector();

        for(int i=0; i<statusNames.length; i++) {
            values.add(String.valueOf(i));
        }

        return values;
    }

    //UPDATE BY SATRYA 2013-01-31
        /**
     * getKeyAllowance
     * @return 
     */
    public static Vector getKeyAllowance(){
        Vector vx = new Vector();
        for(int x=0; x<Overtime.allowanceType.length;x++){
            vx.add(Overtime.allowanceType[x]);
        }
        return vx;
    }
    /**
     * getValueAllowance
     * @return 
     */
    public static Vector getValueAllowance(){
        Vector vx = new Vector();
        for(int x=0; x<Overtime.allowanceValue.length;x++){
            vx.add(""+Overtime.allowanceValue[x]);
        }
        return vx;
    }
    
     /**
     * getKeyAllowance
     * @return 
     */
    public static Vector getKeyOtForm(){
        Vector vx = new Vector();
        for(int x=0; x<I_DocStatus.fieldDocumentStatus.length;x++){
            vx.add(I_DocStatus.fieldDocumentStatus[x]);
        }
        return vx;
    }
    /**
     * getValueAllowance
     * @return 
     */
    public static Vector getValueOtForm(){
        Vector vx = new Vector();
        for(int x=0; x<I_DocStatus.fieldDocumentStatusVal.length;x++){
            vx.add(""+I_DocStatus.fieldDocumentStatusVal[x]);
        }
        return vx;
    }

    public PstOvertime() {
    }

    public PstOvertime(int i) throws DBException {
        super(new PstOvertime());
    }

    public PstOvertime(String sOid) throws DBException {
        super(new PstOvertime(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOvertime(long lOid) throws DBException {
        super(new PstOvertime(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOvertime().getClass().getName();
    }

    public String getTableName() {
        return TBL_OVERTIME;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Overtime) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Overtime) ent);
    }

    public long fetchExc(Entity ent) throws Exception {
        Overtime overtime = fetchExc(ent.getOID());
        ent = (Entity) overtime;
        return overtime.getOID();
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Overtime fetchExc(long oid) throws DBException {
        try {
            Overtime overtime = new Overtime();
            PstOvertime pstOvertime = new PstOvertime(oid);
            overtime.setOID(oid);

            overtime.setRequestDate(pstOvertime.getDate(FLD_REQ_DATE));
            overtime.setOvertimeNum(pstOvertime.getString(FLD_OV_NUMBER));
            overtime.setObjective(pstOvertime.getString(FLD_OBJECTIVE));
            overtime.setStatusDoc(pstOvertime.getInt(FLD_STATUS_DOC));
            overtime.setCustomerTaskId(pstOvertime.getlong(FLD_CUSTOMER_TASK_ID ));
            overtime.setLogbookId(pstOvertime.getlong(FLD_LOGBOOK_ID ));
            overtime.setRequestId(pstOvertime.getlong(FLD_REQ_ID));
            overtime.setApprovalId(pstOvertime.getlong(FLD_APPROVAL_ID));
            overtime.setAckId(pstOvertime.getlong(FLD_ACK_ID));
            overtime.setCompanyId(pstOvertime.getlong(FLD_COMPANY_ID));
            overtime.setDivisionId(pstOvertime.getlong(FLD_DIVISION_ID));
            overtime.setDepartmentId(pstOvertime.getlong(FLD_DEPARTMENT_ID));
            overtime.setSectionId(pstOvertime.getlong(FLD_SECTION_ID));
            overtime.setCountIdx(pstOvertime.getInt(FLD_COUNT_IDX));
            overtime.setCostDepartmentId(pstOvertime.getlong(FLD_COST_DEP_ID));
            overtime.setAllowence(pstOvertime.getInt(FLD_ALLOWANCE));
            //update by satrya 2013-04-30
            overtime.setTimeReqOt(pstOvertime.getDate(FLD_TIME_REQ));
            overtime.setTimeApproveOt(pstOvertime.getDate(FLD_TIME_APPROVAL));
            overtime.setTimeAckOt(pstOvertime.getDate(FLD_TIME_ACK));
            overtime.setApproval1Id(pstOvertime.getLong(FLD_APPROVAL_1_ID));
            overtime.setTimeApproval1(pstOvertime.getDate(FLD_TIME_APPROVAL_1));
            overtime.setApproval2Id(pstOvertime.getLong(FLD_APPROVAL_2_ID));
            overtime.setTimeApproval2(pstOvertime.getDate(FLD_TIME_APPROVAL_2));
            overtime.setApproval3Id(pstOvertime.getLong(FLD_APPROVAL_3_ID));
            overtime.setTimeApproval3(pstOvertime.getDate(FLD_TIME_APPROVAL_3));
            overtime.setApproval4Id(pstOvertime.getLong(FLD_APPROVAL_4_ID));
            overtime.setTimeApproval4(pstOvertime.getDate(FLD_TIME_APPROVAL_4));
            overtime.setApproval5Id(pstOvertime.getLong(FLD_APPROVAL_5_ID));
            overtime.setTimeApproval5(pstOvertime.getDate(FLD_TIME_APPROVAL_5));
            overtime.setApproval6Id(pstOvertime.getLong(FLD_APPROVAL_6_ID));
            overtime.setTimeApproval6(pstOvertime.getDate(FLD_TIME_APPROVAL_6));
            overtime.setOvertimeType(pstOvertime.getInt(FLD_OVERTIME_TYPE));
            
            return overtime;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime(0), DBException.UNKNOWN);
        }
    }

    public synchronized static long insertExc(Overtime overtime) throws DBException {
        try {
            PstOvertime pstOvertime = new PstOvertime(0);
            Overtime ovMax = fetchOvertimeMaxNumber();
            if(ovMax!=null && ovMax.getOID()!=0){
                int n=1;
                try{
                     n = Integer.parseInt(ovMax.getOvertimeNum() )+1;
                }catch(Exception exc){
                    // klu gagal
                    n=ovMax.getCountIdx()+1; // 
                }
                overtime.setOvertimeNum(""+n);
            }else{
                overtime.setOvertimeNum("1");
            }                    
            pstOvertime.setDate(FLD_REQ_DATE, overtime.getRequestDate());
            pstOvertime.setString(FLD_OV_NUMBER, overtime.getOvertimeNum());
            pstOvertime.setString(FLD_OBJECTIVE, overtime.getObjective());
            pstOvertime.setInt(FLD_STATUS_DOC, overtime.getStatusDoc());
            pstOvertime.setLong(FLD_CUSTOMER_TASK_ID , overtime.getCustomerTaskId());
            pstOvertime.setLong(FLD_LOGBOOK_ID , overtime.getLogbookId());
            pstOvertime.setLong(FLD_REQ_ID, overtime.getRequestId());
            pstOvertime.setLong(FLD_APPROVAL_ID, overtime.getApprovalId());
            pstOvertime.setLong(FLD_ACK_ID, overtime.getAckId());
            pstOvertime.setLong(FLD_COMPANY_ID, overtime.getCompanyId());
            pstOvertime.setLong(FLD_DIVISION_ID, overtime.getDivisionId());
            pstOvertime.setLong(FLD_DEPARTMENT_ID, overtime.getDepartmentId());
            pstOvertime.setLong(FLD_SECTION_ID, overtime.getSectionId());
            pstOvertime.setLong(FLD_COST_DEP_ID, overtime.getCostDepartmentId());
            pstOvertime.setInt(FLD_ALLOWANCE, overtime.getAllowence());
            
            //update by satrya 2013-04-30
            pstOvertime.setDate(FLD_TIME_REQ, overtime.getTimeReqOt());
            pstOvertime.setDate(FLD_TIME_APPROVAL, overtime.getTimeApproveOt());
            pstOvertime.setDate(FLD_TIME_ACK, overtime.getTimeAckOt());
            
            pstOvertime.setLong(FLD_APPROVAL_1_ID, overtime.getApproval1Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_1, overtime.getTimeApproval1());
            pstOvertime.setLong(FLD_APPROVAL_2_ID, overtime.getApproval2Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_2, overtime.getTimeApproval2());
            pstOvertime.setLong(FLD_APPROVAL_3_ID, overtime.getApproval3Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_3, overtime.getTimeApproval3());
            pstOvertime.setLong(FLD_APPROVAL_4_ID, overtime.getApproval4Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_4, overtime.getTimeApproval4());
            pstOvertime.setLong(FLD_APPROVAL_5_ID, overtime.getApproval5Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_5, overtime.getTimeApproval5());
            pstOvertime.setLong(FLD_APPROVAL_6_ID, overtime.getApproval6Id());
            pstOvertime.setDate(FLD_TIME_APPROVAL_6, overtime.getTimeApproval6());
            pstOvertime.setInt(FLD_OVERTIME_TYPE, overtime.getOvertimeType());
            
            pstOvertime.insert();
            overtime.setOID(pstOvertime.getlong(FLD_OVERTIME_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime(0), DBException.UNKNOWN);
        }
        return overtime.getOID();
    }

    public static long updateExc(Overtime overtime) throws DBException {
        try {
            if (overtime.getOID() != 0) {
                if(overtime.getOvertimeNum()==null || overtime.getOvertimeNum().length()<1){
                    Overtime ovMax = fetchOvertimeMaxNumber();
                    if(ovMax!=null && ovMax.getOID()!=0){
                        int n=1;
                        try{
                             n = Integer.parseInt(ovMax.getOvertimeNum() )+1;
                        }catch(Exception exc){
                            // klu gagal
                            n=ovMax.getCountIdx()+1; // 
                        }
                        overtime.setOvertimeNum(""+n);
                    }else{
                        overtime.setOvertimeNum("1");
                    }                    
                }               
                PstOvertime pstOvertime = new PstOvertime(overtime.getOID());
                pstOvertime.setDate(FLD_REQ_DATE, overtime.getRequestDate());
                pstOvertime.setString(FLD_OV_NUMBER, overtime.getOvertimeNum());
                pstOvertime.setString(FLD_OBJECTIVE, overtime.getObjective());
                pstOvertime.setInt(FLD_STATUS_DOC, overtime.getStatusDoc());
                pstOvertime.setLong(FLD_CUSTOMER_TASK_ID , overtime.getCustomerTaskId());
                pstOvertime.setLong(FLD_LOGBOOK_ID , overtime.getLogbookId());
                pstOvertime.setLong(FLD_REQ_ID, overtime.getRequestId());
                pstOvertime.setLong(FLD_APPROVAL_ID, overtime.getApprovalId());
                pstOvertime.setLong(FLD_ACK_ID, overtime.getAckId());
                pstOvertime.setLong(FLD_COMPANY_ID, overtime.getCompanyId());
                pstOvertime.setLong(FLD_DIVISION_ID, overtime.getDivisionId());
                pstOvertime.setLong(FLD_DEPARTMENT_ID, overtime.getDepartmentId());
                pstOvertime.setLong(FLD_SECTION_ID, overtime.getSectionId());
                pstOvertime.setLong(FLD_COST_DEP_ID, overtime.getCostDepartmentId());
                pstOvertime.setInt(FLD_ALLOWANCE, overtime.getAllowence());
                pstOvertime.setInt(FLD_COUNT_IDX, overtime.getCountIdx());
                
                //update by satrya 2013-04-30
                pstOvertime.setDate(FLD_TIME_REQ, overtime.getTimeReqOt());
                pstOvertime.setDate(FLD_TIME_APPROVAL, overtime.getTimeApproveOt());
                pstOvertime.setDate(FLD_TIME_ACK, overtime.getTimeAckOt());
                
                pstOvertime.setLong(FLD_APPROVAL_1_ID, overtime.getApproval1Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_1, overtime.getTimeApproval1());
                pstOvertime.setLong(FLD_APPROVAL_2_ID, overtime.getApproval2Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_2, overtime.getTimeApproval2());
                pstOvertime.setLong(FLD_APPROVAL_3_ID, overtime.getApproval3Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_3, overtime.getTimeApproval3());
                pstOvertime.setLong(FLD_APPROVAL_4_ID, overtime.getApproval4Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_4, overtime.getTimeApproval4());
                pstOvertime.setLong(FLD_APPROVAL_5_ID, overtime.getApproval5Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_5, overtime.getTimeApproval5());
                pstOvertime.setLong(FLD_APPROVAL_6_ID, overtime.getApproval6Id());
                pstOvertime.setDate(FLD_TIME_APPROVAL_6, overtime.getTimeApproval6());
                pstOvertime.setInt(FLD_OVERTIME_TYPE, overtime.getOvertimeType());
            
                pstOvertime.update();
                 //update by satrya 2012-11-20
                //masalahnya itu yg di set stausnya buka allowancenya ,jika dia statusnya final app statusnya akan di set 0 jika memilih allowance foot
                //String whereOvDetailStatus = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS] + " NOT IN (" +
                  //      I_DocStatus.DOCUMENT_STATUS_PAID +") ";
                //PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), overtime.getAllowence(),whereOvDetailStatus);  
                
                return overtime.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstOvertime pstOvertime = new PstOvertime(oid);
            pstOvertime.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOvertime(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Overtime overtime = new Overtime();
                resultToObject(rs, overtime);
                lists.add(overtime);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    
    public static Vector listWithMax(int limitDate,String whereClause) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "
                    + " MAX(det.`DATE_TO`) AS DT_TO,"
                    + " ovt.* "
                        + " FROM " + TBL_OVERTIME+" ovt "
                    + " INNER JOIN `hr_overtime_detail` det "
                        + "ON ovt.`OVERTIME_ID` = det.`OVERTIME_ID`";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY ovt.`OVERTIME_ID`"
                    + " HAVING DATEDIFF(CURDATE(),DT_TO) <= "+limitDate;
            
            System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Overtime overtime = new Overtime();
                resultToObject(rs, overtime);
                lists.add(overtime);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
        public static Overtime fetchOvertimeMaxNumber() {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME + "  ORDER BY "+fieldNames[FLD_COUNT_IDX]+" DESC LIMIT 0,1" ;
            dbrs = DBHandler.execQueryResult(sql);
            Overtime overtime = new Overtime();
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, overtime);
                lists.add(overtime);
            }
            rs.close();
            return overtime;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Overtime();
    }


    public static void resultToObject(ResultSet rs, Overtime overtime) {
        try {
            overtime.setOID(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]));
            Date tm_req = DBHandler.convertDate(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]),rs.getTime(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]));
            overtime.setRequestDate(tm_req);
            //overtime.setRequestDate(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]));
            overtime.setOvertimeNum(rs.getString(PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]));
            overtime.setObjective(rs.getString(PstOvertime.fieldNames[PstOvertime.FLD_OBJECTIVE]));
            overtime.setStatusDoc(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]));
            overtime.setCustomerTaskId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_CUSTOMER_TASK_ID ]));
            overtime.setLogbookId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_LOGBOOK_ID ]));
            overtime.setRequestId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_REQ_ID]));
            overtime.setApprovalId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID]));
            overtime.setAckId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_ACK_ID]));
            overtime.setCompanyId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_COMPANY_ID]));
            overtime.setDivisionId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_DIVISION_ID]));
            overtime.setDepartmentId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_DEPARTMENT_ID]));
            overtime.setSectionId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_SECTION_ID]));
            overtime.setCountIdx(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_COUNT_IDX]));
            overtime.setCostDepartmentId(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_COST_DEP_ID]));
            overtime.setAllowence(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_ALLOWANCE]));
            
            //update by satrya 2013-04-30
            overtime.setTimeReqOt(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_REQ]));
            overtime.setTimeApproveOt(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL]));
            overtime.setTimeAckOt(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_ACK]));
            
            overtime.setApproval1Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_1_ID]));
            overtime.setTimeApproval1(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_1]));
            overtime.setApproval2Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_2_ID]));
            overtime.setTimeApproval2(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_2]));
            overtime.setApproval3Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_3_ID]));
            overtime.setTimeApproval3(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_3]));
            overtime.setApproval4Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_4_ID]));
            overtime.setTimeApproval4(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_4]));
            overtime.setApproval5Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_5_ID]));
            overtime.setTimeApproval5(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_5]));
            overtime.setApproval6Id(rs.getLong(PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_6_ID]));
            overtime.setTimeApproval6(rs.getDate(PstOvertime.fieldNames[PstOvertime.FLD_TIME_APPROVAL_6]));
            overtime.setOvertimeType(rs.getInt(PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_TYPE]));
            
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long overtimeId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OVERTIME + " WHERE "
                    + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] + " = '" + overtimeId + "'";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID] + ") FROM " + TBL_OVERTIME;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Overtime overtime = (Overtime) list.get(ls);
                    if (oid == overtime.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }

    /*public static int getAckId(String employeeNum, long periodId, String workDate) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT STATUS FROM " + TBL_OVERTIME
                    + " WHERE " + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER] + "='" + employeeNum + "'"
                    + " AND " + PstOvertime.fieldNames[PstOvertime.FLD_APPROVAL_ID] + "=" + periodId
                    + " AND " + PstOvertime.fieldNames[PstOvertime.FLD_EMPLOYEE_ID] + " = '" + workDate + "'";
            //System.out.println("SQL getAckId"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();


            int status = 0;
            while (rs.next()) {
                status = rs.getInt(1);
            }

            rs.close();
            return status;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }*/

    /* This method used to list employee overtime
     * Created By Yunny
     */

    /* public static Vector getOvtEmployee(long departmentId, long sectionId, long periodId, int index) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);

        if (departmentId == 0 && sectionId == 0 && periodId == 0) {
            return new Vector(1, 1);
        }
        try {
            String sql = " SELECT  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ", EMP." + PstEmployee.fieldNames[PstEmployee.FLD_OV_NUMBER]
                    + ", CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]
                    + ", OVT." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                    + ", LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP"
                    + " INNER JOIN " + PstOvertime.TBL_OVERTIME + " AS OVT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_OV_NUMBER]
                    + " = OVT." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]
                    + " INNER JOIN " + PstEmpCategory.TBL_HR_EMP_CATEGORY + " AS CAT"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                    + " = CAT." + PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]
                    + " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEVEL"
                    + " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]
                    + " WHERE  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=0"
                    + " AND LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA] + "=" + PstPayEmpLevel.CURRENT;

            String whereClause = "";



            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        + " = " + departmentId + " AND ";
            }


            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId + " AND ";

            }
            if (periodId != 0) {
                whereClause = whereClause + " OVT." + PstPaySlip.fieldNames[PstPaySlip.FLD_APPROVAL_ID]
                        + " = " + periodId + " AND ";

            }

            if (index < 2) {
                whereClause = whereClause + " LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]
                        + " = " + index + " AND ";

            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
                //sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " GROUP BY OVT." + PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER];
            System.out.println("\t SQL PstOvertime.getOvtEmployee With index : " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                Employee employee = new Employee();
                Overtime ovtEmp = new Overtime();
                EmpCategory empCat = new EmpCategory();
                PayEmpLevel payEmpLevel = new PayEmpLevel();

                employee.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                employee.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                employee.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_OV_NUMBER]));
                vect.add(employee);
                //employee.setEmpPin(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));

                ovtEmp.setOvertimeNum(rs.getString(PstOvertime.fieldNames[PstOvertime.FLD_OV_NUMBER]));
                vect.add(ovtEmp);

                empCat.setEmpCategory(rs.getString(PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]));
                vect.add(empCat);

                payEmpLevel.setOvtIdxType(rs.getInt(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_OVT_IDX_TYPE]));
                vect.add(payEmpLevel);

                result.add(vect);
            }

            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on  searchEmployee : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1, 1);


    }*/

   /* public static void main(String args[]) {

        //String stOut = listDateOut(26, 226, 504404343502872505L);
        //System.out.println("dtOut........" + stOut);
        //Date dtOut = Formater.formatDate(stOut, "yyyy-MM-dd HH:mm");
        System.out.println("dtOut........" + dtOut);
        String dtActualReal = Formater.formatDate(dtOut, "yyyy-MM-dd");
        String dtTimeActualReal = Formater.formatTimeLocale(dtOut, "HH:mm");
        System.out.println("dtActualReal........" + dtActualReal);
        System.out.println("dtTimeActualReal........" + dtTimeActualReal);
        Date dtTimeReal = Formater.formatDate(dtTimeActualReal, "HH:mm");
        System.out.println("dtTimeReal........" + dtTimeReal);

        String strCoba = "10";
        double coba = Double.parseDouble(strCoba);
        System.out.println("coba:::::::::::::::::::::::::::::::::::" + coba);

    }*/
    
    public static java.util.Hashtable isScheduleOverlapOvertime(long employeeId,Vector Vschedule,long ScheduleOff,Date selectedFromDate,Date selectedToDate,  EmpScheduleReport empScheduleReport) { 
        int kk=0;
         java.util.Hashtable hasOverlapOT = new java.util.Hashtable();
        if(employeeId==0 || Vschedule==null || Vschedule.size()==0 || selectedFromDate==null || selectedToDate==null || ScheduleOff==0){
            return hasOverlapOT;
        }
       // boolean scheduleOverlapOvertime=false;
      
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        Hashtable hasSchedule = new Hashtable();
        Date selectedFrom= (Date)selectedFromDate.clone();
        selectedFrom = new Date(selectedFrom.getTime() -  24*60*60*1000);
        Date selectedTo = (Date)selectedToDate.clone();
        //di hidden karena pasti dia overlaping jdinya
       // selectedTo = new Date(selectedToDate.getTime() + 24*60*60*1000);/
        if(Vschedule!=null && Vschedule.size()>0){
              while (selectedFrom.before(selectedTo) || selectedFrom.equals(selectedTo)) {
                Date dtScheduleTimeIn = null;
                Date dtScheduleTimeOut = null;
               
                Date dtIdx = empScheduleReport.getDtIDate(kk);
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                    if(scheduleSymbol!=null && scheduleSymbol.getOID()!=oidSch1St){
                   scheduleSymbol = (ScheduleSymbol)hasSchedule.get(""+oidSch1St);
                   if(scheduleSymbol==null){

                    if(oidSch1St!=0){
                      try{
                     scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                     }catch(Exception exc){}
                     hasSchedule.put(""+oidSch1St,scheduleSymbol );
                   }
                   }
                }else{
                   if(scheduleSymbol==null){
                       if(oidSch1St!=0){
                          try{
                         scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                         }catch(Exception exc){}
                         hasSchedule.put(""+oidSch1St,scheduleSymbol );
                       }
                   }
               }
               if(oidSch1St!=0 && oidSch1St==ScheduleOff){
                   //scheduleOverlapOvertime=false;
                   hasOverlapOT.put("false",oidSch1St+"_"+employeeId);
                   //return hasOverlapOT;  
               }else{
               if(scheduleSymbol!=null && dtIdx!=null){
                   if(scheduleSymbol.getTimeIn()!=null){
                        dtScheduleTimeIn = (Date)dtIdx.clone();
                        dtScheduleTimeIn.setHours(scheduleSymbol.getTimeIn().getHours());
                        dtScheduleTimeIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                        dtScheduleTimeIn.setSeconds(0);
                    }
                    if(scheduleSymbol.getTimeOut()!=null){
                        dtScheduleTimeOut = (Date)dtIdx.clone();
                        dtScheduleTimeOut.setHours(scheduleSymbol.getTimeOut().getHours());
                        dtScheduleTimeOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                        dtScheduleTimeOut.setSeconds(0);
                        //jika ada cross day
                        if(dtScheduleTimeOut.getHours()<dtScheduleTimeIn.getHours()){
                            dtScheduleTimeOut.setTime(dtScheduleTimeOut.getTime()+24*60*60*1000); 
                        }
                    }
                    
                    if(scheduleSymbol.getTimeIn()!=null && scheduleSymbol.getTimeOut()!=null){
                      // if(selectedToDate.getTime() >=dtScheduleTimeIn.getTime()&& dtScheduleTimeOut.getTime()>=selectedFromDate.getTime()){
                        if(selectedToDate.getTime()>dtScheduleTimeIn.getTime() && dtScheduleTimeOut.getTime()> selectedFromDate.getTime()){
                              hasOverlapOT.put("true",oidSch1St+"_"+employeeId);
                            return hasOverlapOT;
                        }
                    
                    }
               }
              }
              selectedFrom = new Date(selectedFrom.getTime()+ 24*60*60*1000);
              kk = kk +1;
            }
            
        }
        
        return hasOverlapOT;
    }
    
 public static String getApproveFrmByApprovalIndex(Overtime overtime, int index) {
        
            String result = "";
            try{
                if (index == 1){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_1_ID];
                        } catch (Exception e){}
                        return result;
                    
                }
                if (index == 2){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_2_ID];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 3){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_3_ID];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 4){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_4_ID];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 5){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_5_ID];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 6){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_6_ID];
                        } catch (Exception e){}
                        return result;
                }
                
            }catch(Exception e){} 
            
            return result;
    }
 
 public static String getApproveFrmByApprovalIndexDate(Overtime overtime, int index) {
        
            String result = "";
            try{
                if (index == 1){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_1];
                        } catch (Exception e){}
                        return result;
                    
                }
                if (index == 2){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_2];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 3){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_3];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 4){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_4];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 5){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_5];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 6){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_6];
                        } catch (Exception e){}
                        return result;
                }
                
            }catch(Exception e){} 
            
            return result;
    }
    
    public static long getApproveOidByApprovalIndex(Overtime overtime, int index) {
        
            long result = 0;
            try{
                if (index == 1){
                    if (overtime.getApproval1Id() != 0){
                        try{
                            result = overtime.getApproval1Id();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (overtime.getApproval2Id() != 0){
                        try{
                            result = overtime.getApproval2Id();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (overtime.getApproval3Id() != 0){
                        try{
                            result = overtime.getApproval3Id();    
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (overtime.getApproval4Id() != 0){
                        try{
                            result = overtime.getApproval4Id();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (overtime.getApproval5Id() != 0){
                        try{
                            result = overtime.getApproval5Id() ;
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (overtime.getApproval6Id() != 0){
                        try{
                            result = overtime.getApproval6Id();
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
    
    public static Date getApproveDateByApprovalIndex(Overtime overtime, int index) {
        
            Date result = null;
            try{
                if (index == 1){
                    if (overtime.getApproval1Id() != 0){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (overtime.getApproval2Id() != 0){
                        try{
                            result = overtime.getTimeApproval2();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (overtime.getApproval3Id() != 0){
                        try{
                            result = overtime.getTimeApproval3();    
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (overtime.getApproval4Id() != 0){
                        try{
                            result = overtime.getTimeApproval4();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (overtime.getApproval5Id() != 0){
                        try{
                            result = overtime.getTimeApproval5();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (overtime.getApproval6Id() != 0){
                        try{
                            result = overtime.getTimeApproval6();
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
    
    public static String getApproveFrmDateByApprovalIndex(Overtime overtime, int index) {
        
            String result = "";
            try{
                if (index == 1){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_1];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 2){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_2];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 3){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_3];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 4){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_4];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 5){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_5];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 6){
                        try{
                            result = FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_TIME_APPROVAL_6];
                        } catch (Exception e){}
                        return result;
                }
                
            }catch(Exception e){} 
            
            return result;
    }
    
    public static boolean cekApproval(Overtime overtime,int index) {
        
            boolean cekAdaTrue = false;
            
            try{
                if (index == 1){
                    if (overtime.getApproval1Id() != 0){
                        return true;
                    }
                }
                if (index == 2){
                    if (overtime.getApproval2Id() != 0){
                        return true;
                    }
                }
                if (index == 3){
                    if (overtime.getApproval3Id() != 0){
                        return true;
                    }
                }
                if (index == 4){
                    if (overtime.getApproval4Id() != 0){
                        return true;
                    }
                }
                if (index == 5){
                    if (overtime.getApproval5Id() != 0){
                        return true;
                    }
                }
                if (index == 6){
                    if (overtime.getApproval6Id() != 0){
                        return true;
                    }
                }
                
            }catch(Exception e){} 
            
            return cekAdaTrue;
    }
    
    public static String getObjEmployeeByApprovalIndex(Overtime overtime, int index) {
        
            Employee employee = new Employee();
            String result = "";
            try{
                if (index == 1){
                    if (overtime.getApproval1Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval1Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_1_ID] + "\" value=\"" + overtime.getApproval1Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (overtime.getApproval2Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval2Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_2_ID] + "\" value=\"" + overtime.getApproval2Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (overtime.getApproval3Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval3Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_3_ID] + "\" value=\"" + overtime.getApproval3Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (overtime.getApproval4Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval4Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_4_ID] + "\" value=\"" + overtime.getApproval4Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (overtime.getApproval5Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval5Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_5_ID] + "\" value=\"" + overtime.getApproval5Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (overtime.getApproval6Id() != 0){
                        try{
                            employee = PstEmployee.fetchExc(overtime.getApproval6Id());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmOvertime.fieldNames[FrmOvertime.FRM_FIELD_APPROVAL_6_ID] + "\" value=\"" + overtime.getApproval6Id() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
    
    public static Date getApproveOidByApprovalIndexDate(Overtime overtime, int index) {
        
            Date result = new  Date();
            try{
                if (index == 1){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (overtime.getTimeApproval1() != null){
                        try{
                            result = overtime.getTimeApproval1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
        /**
     * mencari oidOvertimeMain ( hanya salah satu saja )
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Overtime getOvertime(int limitStart, int recordToGet, String whereClause, String order) {
         Overtime objOvertime = new Overtime();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT so.* FROM "+ PstOvertime.TBL_OVERTIME + " AS so "
            + " INNER JOIN "+PstOvertimeDetail.TBL_OVERTIME_DETAIL 
            + " AS sod ON so."+PstOvertime.fieldNames[PstOvertime.FLD_OVERTIME_ID]+"=sod."+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID];
//WHERE so.`REQUEST_DATE_EXTRA_SCHEDULE`="2014-06-15" AND sod.`EMPLOYEE_ID` IN(1,2) AND so.`COMPANY_ID`=1 AND so.`DIVISION_ID`=1 AND so.`DEPARTMENT_ID`=1;;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
               //oidMainExtraSch = rs.getLong(PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_EXTRA_SCHEDULE_MAPPING_ID]);
                resultToObject(rs, objOvertime);
            }
            rs.close();
          

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
              return objOvertime;
        }
    }
    public static int getMaxApp(long employeeId, I_Leave leaveConfig, Overtime overtime ){
        int max = 0;
          Employee employeeObj = new Employee();
            try {
                employeeObj = PstEmployee.fetchExc(employeeId);
            }catch (Exception e){}

            Level level = new Level();
            try{
                level = PstLevel.fetchExc(employeeObj.getLevelId());
            }catch(Exception e){}

            Level maxLevelObj = new Level();
            try{
                maxLevelObj = PstLevel.fetchExc(level.getMaxLevelApproval());
            }catch(Exception e){}

            int minLevel = level.getLevelPoint();
            int maxLevel = maxLevelObj.getLevelPoint();
            int incIndexApp = 1;            
            long oidEmpDinamis = employeeId;
            for (int xx = minLevel; xx<=maxLevel; xx++ ){
            Vector listEmpApproval = leaveConfig.getApprovalEmployeeTopLinkByLevel(oidEmpDinamis, 6, xx, "");
                if (listEmpApproval.size()>0){
//                    if (PstOvertime.getApproveOidByApprovalIndex(overtime, incIndexApp) != 0){
//                        oidEmpDinamis = PstOvertime.getApproveOidByApprovalIndex(overtime, incIndexApp);
//                    }
                    Employee empl = (Employee) listEmpApproval.get(0);
                    oidEmpDinamis = empl.getOID();
                    max++;
                    incIndexApp++;
                }
            }
        return max;
    }
    
    public static boolean checkRequest(long overtimeId, I_Leave leaveConfig, long employeeId){
        boolean status = false;
        
        Overtime overtime = new Overtime();
        try {
            overtime = PstOvertime.fetchExc(overtimeId);

        } catch (Exception e) {
        }
        
        Vector listMaxEmp = PstOvertimeDetail.maxLevelEmployees(overtimeId);
        if(listMaxEmp.size() > 0){
            OvertimeDetail overtimeDetail = (OvertimeDetail) listMaxEmp.get(0);

            if(overtimeDetail.getEmployeeId() != 0){
                Employee employeeObj = new Employee();
                try {
                    employeeObj = PstEmployee.fetchExc(overtimeDetail.getEmployeeId());

                } catch (Exception e) {
                }

                Level level = new Level();
                try {
                    level = PstLevel.fetchExc(employeeObj.getLevelId());
                } catch (Exception e) {
                }

                Level maxLevelObj = new Level();
                try {
                    maxLevelObj = PstLevel.fetchExc(level.getMaxLevelApproval());
                } catch (Exception e) {
                }

                int minLevel = level.getLevelPoint();
                int maxLevel = maxLevelObj.getLevelPoint();
                int incIndexApp = 1;
                int typeApproval = 11;
                if (overtime.getOvertimeType() == 1){
                    typeApproval = 10;
                }
                long oidEmpDinamis = overtimeDetail.getEmployeeId();
                for (int xx = minLevel; xx <= maxLevel; xx++) {
                    Vector listEmpApproval = leaveConfig.getApprovalEmployeeTopLinkByLevel(oidEmpDinamis, typeApproval, xx,"");
                    if (listEmpApproval.size() > 0) {
                        if (listEmpApproval != null && listEmpApproval.size() > 0) {

                            for (int i = 0; i < listEmpApproval.size(); i++) {
                                Employee objEmp = (Employee) listEmpApproval.get(i);
                                if (employeeId == objEmp.getOID()){
                                    status = true;
                                }
                            }
                        }
                        //xx = maxLevel+1;
                    }
                }
            }

        }
        
        return status;
    }
    
    public static boolean checkApprover(long overtimeId, I_Leave leaveConfig, long employeeId, long requestId, long lastApprover){
        boolean status = false;
        
        Overtime overtime = new Overtime();
        try {
            overtime = PstOvertime.fetchExc(overtimeId);

        } catch (Exception e) {
        }
        
        Employee employeeObj = new Employee();
        try {
            employeeObj = PstEmployee.fetchExc(requestId);

        } catch (Exception e) {
        }

        Level level = new Level();
        try {
            level = PstLevel.fetchExc(employeeObj.getLevelId());
        } catch (Exception e) {
        }

        Level maxLevelObj = new Level();
        try {
            maxLevelObj = PstLevel.fetchExc(level.getMaxLevelApproval());
        } catch (Exception e) {
        }

        int minLevel = level.getLevelPoint();
        int maxLevel = maxLevelObj.getLevelPoint();
        int incIndexApp = 1;
        int typeApproval = 6;
        if (overtime.getOvertimeType() == 1){
            typeApproval = 9;
        }
        long oidEmpDinamis = lastApprover;
        for (int xx = minLevel; xx <= maxLevel; xx++) {
            Vector listEmpApproval = leaveConfig.getApprovalEmployeeTopLinkByLevel(oidEmpDinamis, typeApproval, xx,"");
            if (listEmpApproval.size() > 0) {
                if (listEmpApproval != null && listEmpApproval.size() > 0) {

                        for (int i = 0; i < listEmpApproval.size(); i++) {
                            Employee objEmp = (Employee) listEmpApproval.get(i);
                            if (employeeId == objEmp.getOID()){
                                status = true;
                            }
                        }
                    }
                    xx = maxLevel+1;
            }
        }
        
        return status;
    }
    
    public static void setApproval(long overtimeId, I_Leave leaveConfig, long oidApprover, long approvalDate, int indexApproval){
        
        Overtime overtime = new Overtime();
        try{
            overtime = PstOvertime.fetchExc(overtimeId);
        } catch (Exception exc){}
        
        Vector listOvertimeDetail = new Vector(1, 1);
        String whereClauseOvDtl = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID] + " = " + overtimeId;
        String orderClauseOvDtl = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM];
        
        listOvertimeDetail = PstOvertimeDetail.listWithEmployee(0, 0, whereClauseOvDtl, orderClauseOvDtl);

        int finApproval = PstOvertime.getMaxApp(overtime.getRequestId(), leaveConfig, overtime);                    
        Date approvalDates = new Date(approvalDate);

        if (oidApprover != 0) {
            switch (indexApproval) {
                case 1:
                    overtime.setApproval1Id(oidApprover);
                    overtime.setTimeApproval1(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 2:
                    overtime.setApproval2Id(oidApprover);
                    overtime.setTimeApproval2(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 3:
                    overtime.setApproval3Id(oidApprover);
                    overtime.setTimeApproval3(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 4:
                    overtime.setApproval4Id(oidApprover);
                    overtime.setTimeApproval4(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 5:
                    overtime.setApproval5Id(oidApprover);
                    overtime.setTimeApproval5(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 6:
                    overtime.setApproval6Id(oidApprover);
                    overtime.setTimeApproval6(approvalDates);

                    if(indexApproval == finApproval){
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);

                        for (int i = 0; i < listOvertimeDetail.size(); i++) {
                            OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                            if (overtimeDetail.getLateApproval() == 1){
                                setAbsenStatus(overtimeDetail.getDateFrom(), overtimeDetail.getEmployeeId());
                            }
                            try {
                                PstOvertimeDetail.updateExc(overtimeDetail);
                            } catch (Exception E) {
                                System.out.println("Exception " + E.toString());
                            }
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
                case 11:
                    overtime.setRequestId(oidApprover);
                    overtime.setRequestDate(approvalDates);
                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);

                    for (int i = 0; i < listOvertimeDetail.size(); i++) {
                        OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDetail.get(i);
                        overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                        try {
                            PstOvertimeDetail.updateExc(overtimeDetail);
                        } catch (Exception E) {
                            System.out.println("Exception " + E.toString());
                        }
                    }

                    try {
                        PstOvertime.updateExc(overtime);
                    } catch (Exception E) {
                        System.out.println("Exception " + E.toString());
                    }

                break;
            }
        }

        int maxApproval = PstOvertime.getMaxApp(overtime.getRequestId(), leaveConfig, overtime);
    }
    
    public static int getOvertimeNotif(long employeeId, long divisionId){
        int notif = 0;
        I_Leave leaveConfig = null;
        try{
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        
        int limitDayApproval = 0;
        String strlimitDayApproval = PstSystemProperty.getValueByName("OVERTIME_APPROVAL_LIMIT_DAY");
        try {
            limitDayApproval = Integer.valueOf(strlimitDayApproval);
        } catch (Exception exc){}
        
        String whereOvNotif = PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+" IN ('0','1') "
                                        + " AND "+PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]+" > DATE_ADD(CURDATE(),INTERVAL -7 DAY)";
        Vector listOvertime = PstOvertime.list(0, 0, whereOvNotif, "");
        
        
        
        if (listOvertime.size() > 0){
            for (int i = 0; i < listOvertime.size(); i++){
                Overtime overtime = (Overtime) listOvertime.get(i);
                Date dtMax = getMaxOvtDate(overtime.getOID());

                boolean approval = true;
                if (dtMax != null && limitDayApproval > 0 && (overtime.getStatusDoc() == 0 || overtime.getStatusDoc() == 1)){
                    int diff = PstOvertime.getOvertimeApprovalDayDiff(employeeId, dtMax);
                    long maxTime = dtMax.getTime();
                    long nowTime = new Date().getTime();
                    long diffTime = nowTime - maxTime;
                    long diffDays = diffTime / (1000 * 60 * 60 * 24);
                    if (diff > (limitDayApproval)){
                        approval = false;
                    }
                }
                
                if (approval){
                   boolean checkApproval = false;
                    int incIndexApp = 0;
                    boolean indexComplete = false;
                    if (overtime.getRequestId() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 11);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkRequest(overtime.getOID(), leaveConfig, employeeId);
                        }
                    } else if (overtime.getApproval1Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 1);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getRequestId() );
                        }
                    } else if (overtime.getApproval2Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 2);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval1Id() );
                        }
                    } else if (overtime.getApproval3Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 3);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval2Id() );
                        }
                    } else if (overtime.getApproval4Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 4);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval3Id() );
                        }
                    } else if (overtime.getApproval5Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 5);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval4Id() );
                        }
                    } else if (overtime.getApproval6Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 6);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval5Id() );
                        }
                    }

                    if (checkApproval && (overtime.getStatusDoc() == 0 || overtime.getStatusDoc() == 1)){
                        notif = notif + 1;
                    }  
                }
            }
        }
        
        return notif;
    }
    
    public static Date getMaxOvtDate(long overtimeId){
        Date dt = null;
        
        DBResultSet dbrs = null;
        try{
            String sql = "SELECT MAX("+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]+") AS "+PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]+
                         " FROM " +PstOvertimeDetail.TBL_OVERTIME_DETAIL +
                         " WHERE " +PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_ID]+" = "+ overtimeId;

            //System.out.println("sqlgetOvtDuration   "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
           // System.out.println("sql   "+sql);
            while(rs.next()) { 
                dt = DBHandler.convertDate(rs.getDate(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]), rs.getTime(PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO]));
            }
            rs.close();
	        
        }catch(Exception e){
            System.out.println("Error");
        }
        
        return dt;
    }
    
    public static String getOvertimeNotifNum(long employeeId, long divisionId){
        String num = "";
        I_Leave leaveConfig = null;
        try{
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        
        int limitDayApproval = 0;
        String strlimitDayApproval = PstSystemProperty.getValueByName("OVERTIME_APPROVAL_LIMIT_DAY");
        try {
            limitDayApproval = Integer.valueOf(strlimitDayApproval);
        } catch (Exception exc){}
        
        String whereOvNotif = PstOvertime.fieldNames[PstOvertime.FLD_STATUS_DOC]+" IN ('0','1') "
                                        + " AND "+PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE]+" > DATE_ADD(CURDATE(),INTERVAL -7 DAY)";
        Vector listOvertime = PstOvertime.list(0, 0, whereOvNotif, "");
        
        if (listOvertime.size() > 0){
            for (int i = 0; i < listOvertime.size(); i++){
                Overtime overtime = (Overtime) listOvertime.get(i);
                
                Date dtMax = getMaxOvtDate(overtime.getOID());

                boolean approval = true;
                if (dtMax != null && limitDayApproval > 0 && (overtime.getStatusDoc() == 0 || overtime.getStatusDoc() == 1)){
                    int diff = PstOvertime.getOvertimeApprovalDayDiff(employeeId, dtMax);
                    long maxTime = dtMax.getTime();
                    long nowTime = new Date().getTime();
                    long diffTime = nowTime - maxTime;
                    long diffDays = diffTime / (1000 * 60 * 60 * 24);
                    if (diff > (limitDayApproval)){
                        approval = false;
                    }
                }
                
                if (approval){
                    boolean checkApproval = false;
                    int incIndexApp = 0;
                    boolean indexComplete = false;
                    if (overtime.getRequestId() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 11);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkRequest(overtime.getOID(), leaveConfig, employeeId);
                        }
                    } else if (overtime.getApproval1Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 1);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getRequestId() );
                        }
                    } else if (overtime.getApproval2Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 2);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval1Id() );
                        }
                    } else if (overtime.getApproval3Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 3);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval2Id() );
                        }
                    } else if (overtime.getApproval4Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 4);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval3Id() );
                        }
                    } else if (overtime.getApproval5Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 5);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval4Id() );
                        }
                    } else if (overtime.getApproval6Id() == 0){
                        indexComplete = PstOvertime.cekApproval(overtime, 6);
                        if(!indexComplete){
                            checkApproval = PstOvertime.checkApprover(overtime.getOID(), leaveConfig, employeeId, overtime.getRequestId(), overtime.getApproval5Id() );
                        }
                    }

                    if (checkApproval && (overtime.getStatusDoc() == 0 || overtime.getStatusDoc() == 1)){
                        num = num + overtime.getOvertimeNum()+"-";
                    } 
                }
                
                
            }
            num = num.substring(0, num.length()-1);
        }
        
        return num;
    }
    
    public static void setAbsenStatus(Date presenceDate, long empId) {
    
        if (presenceDate != null) {
            DBResultSet dbrs = null;

            int oidAbsenceReportOff = 0;
            
            try {
                
                oidAbsenceReportOff = Integer.parseInt(PstSystemProperty.getValueByName("ABSENCE_REPORT_OFF"));

            } catch (Exception e) {

                oidAbsenceReportOff = 0;    /* Default = 0*/
                System.out.println("EXCEPTION SYS PROP 'ABSENCE_REPORT_OFF' : " + e.toString());

            }

            Date dtTommorow = new Date(presenceDate.getYear(), presenceDate.getMonth(), presenceDate.getDate() + 1);
            
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDate);
            int selectedIndex = dtTommorow.getDate();
            int firstFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + (selectedIndex - 1);
            int firstFieldIndexReason = PstEmpSchedule.OFFSET_INDEX_REASON + (selectedIndex - 1);
            int secondFieldIndex = PstEmpSchedule.OFFSET_INDEX_STATUS + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1);
            int noteFieldIndex = PstEmpSchedule.OFFSET_INDEX_NOTE + (selectedIndex - 1);
            try {
                String sql = "SELECT SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        ", SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] +
                        " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH" +
                        " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                        " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + (selectedIndex - 1)] +
                        " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " LEFT JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM2" +
                        " ON SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + (selectedIndex - 1)] +
                        " = SYM2." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] +
                        " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                        " = " + periodId;

                if (empId != 0) {
                    sql = sql + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                            " = " + empId;
                }

                //System.out.println(new AbsenceAnalyser().getClass().getName() + "Check Employee Absence sql : " + sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {

                    /* Check schedule type, category holiday or on schedule  */
                    long employeeId = rs.getLong(1);
                    int intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_NOT_PROCESSED;
                    int schld1stCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_FIRST_SCHEDULE, employeeId, dtTommorow);
                    int schld2ndCategory = PstEmpSchedule.getScheduleCategory(PstEmpSchedule.INT_SECOND_SCHEDULE, employeeId, dtTommorow);

                    intFirstStatus = PstEmpSchedule.STATUS_PRESENCE_OK;              
                                    
                    intSecondStatus = PstEmpSchedule.STATUS_PRESENCE_OK;

                    updatePresenceStatus(periodId, employeeId, firstFieldIndex, intFirstStatus, secondFieldIndex, intSecondStatus, noteFieldIndex);
                }
            } catch (Exception e) {
                System.out.println("Exception check Employee Absence : " + e.toString());
            } finally {
                DBResultSet.close(dbrs);
            }
        } else {
            System.out.println("\tSelected data is null on listEmployeePresence");
        }
    }
    
    public static void updatePresenceStatus(long periodId, long employeeId, int idxFieldNameFirst, int statusFirst, int idxFieldNameSecond, int statusSecond, int idxFieldNameNote) {
        try {
            
            String sql = "UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE +
                    " SET " + PstEmpSchedule.fieldNames[idxFieldNameFirst] + " = " + statusFirst +
                    ", " + PstEmpSchedule.fieldNames[idxFieldNameSecond] + " = " + statusSecond +
                    ", " + PstEmpSchedule.fieldNames[idxFieldNameNote] + " = 'Sudah ada surat tugas'" +
                    " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] +
                    " = " + periodId +
                    " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                    " = " + employeeId;

            //System.out.println("\tUpdate Presence Status : " + sql);
            int result = DBHandler.execUpdate(sql);
            
        } catch (Exception e) {
            System.out.println("\tExc updatePresenceStatus : " + e.toString());
        } finally {
        //System.out.println("\tFinal updatePresenceStatus");
        }
    }
    
    public static boolean endOfYearOvertime(Date dateOvt){
        boolean status = false;
        
        int yearStart = Calendar.getInstance().get(Calendar.YEAR);
        
        String dateStart = PstSystemProperty.getValueByNameWithStringNull("OVT_END_OF_YEAR_START");
        String dateEnd = PstSystemProperty.getValueByNameWithStringNull("OVT_END_OF_YEAR_END");
        
        int intStart = Integer.valueOf(dateStart.replace("-",""));
        int intEnd =  Integer.valueOf(dateEnd.replace("-",""));
        
        int yearEnd = 0;
        if (intStart > intEnd){
            yearEnd = yearStart+1;
        } else {
            yearEnd = yearStart;
        }
        
        DateFormat format = new SimpleDateFormat("MM-dd");
        
        String dtOvt = new SimpleDateFormat("MM-dd").format(dateOvt);
        int intOvt = Integer.valueOf(dtOvt.replace("-",""));
        
        if (intStart <= intOvt && intOvt <= intEnd){
            status = true;
        }
        
        return status;
    }
    
    
    public static int getOvertimeApprovalDayDiff(long employeeId, Date ovtDate){
        int diff = 0;
        
        Employee emp = new Employee();
        try {
            emp = PstEmployee.fetchExc(employeeId);
        } catch (Exception exc){}
        
        String whereClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]+" BETWEEN '"+Formater.formatDate(ovtDate, "yyyy-MM-dd")+"' "
                + " AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd")+"'";
        
        
        String whereAdd = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        int pengurang = 0;
        Vector listPH = PstPublicHolidays.list(0, 0, whereClause, "holiday_status");
        if (listPH.size()>0){
            for(int x = 0; x < listPH.size(); x++){
                PublicHolidays pH = (PublicHolidays)listPH.get(x);
                if(pH.getiHolidaySts() == 1 || emp.getReligionId() == pH.getiHolidaySts()){
                    pengurang += pH.getDays();
                }
            }
        }
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT ((DATEDIFF('"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00', '"+Formater.formatDate(ovtDate, "yyyy-MM-dd")+" 00:00:00')) - "
                    + "((WEEK('"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00') - WEEK('"+Formater.formatDate(ovtDate, "yyyy-MM-dd")+" 00:00:00')) * 2) - "
                    + "(CASE WHEN WEEKDAY('"+Formater.formatDate(ovtDate, "yyyy-MM-dd")+" 00:00:00') = 6 THEN 1 ELSE 0 END) - "
                    + "(CASE WHEN WEEKDAY('"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 00:00:00') = 5 THEN 1 ELSE 0 END) "
                    + whereAdd + ") AS DifD";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                diff = rs.getInt(1)-pengurang;
            }
            rs.close();
          

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        
        return diff;
    }
    
}
