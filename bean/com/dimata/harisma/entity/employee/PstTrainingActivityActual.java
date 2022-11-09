
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.employee;

import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import java.sql.ResultSet;
import java.util.Vector;

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import java.text.SimpleDateFormat;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
public class PstTrainingActivityActual extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_TRAINING_ACTIVITY_ACTUAL = "hr_training_activity_actual";
    public static final int FLD_TRAINING_ACTIVITY_ACTUAL_ID = 0;
    public static final int FLD_TRAINING_ACTIVITY_PLAN_ID = 1;
    public static final int FLD_DATE = 2;
    public static final int FLD_START_TIME = 3;
    public static final int FLD_END_TIME = 4;
    public static final int FLD_ATENDEES = 5;
    public static final int FLD_VENUE = 6;
    public static final int FLD_REMARK = 7;
    public static final int FLD_TRAINING_ID = 8;
    public static final int FLD_TRAINNER = 9;
    public static final int FLD_TRAINING_SCHEDULE_ID = 10;
    public static final int FLD_ORGANIZED_ID = 11;
    public static final int FLD_TRAIN_END_DATE = 12;
    public static final int FLD_TOTAL_HOUR = 13;
    public static final int FLD_TRAINING_TITLE = 14;
    public static final int FLD_SERTIFIKAT_NAME = 15;
    public static final int FLD_KODE_OJK = 16;
    public static String[] fieldNames = {
        "TRAINING_ACTIVITY_ACTUAL_ID",
        "TRAINING_ACTIVITY_PLAN_ID",
        "DATE",
        "START_TIME",
        "END_TIME",
        "ATENDEES",
        "VENUE",
        "REMARK",
        "TRAINING_ID",
        "TRAINNER",
        "TRAINING_SCHEDULE_ID",
        "ORGANIZER_ID",
        "TRAIN_END_DATE",
        "TOTAL_HOUR",
        "TRAINING_TITLE",
        "SERTIFIKAT_NAME",
        "KODE_OJK"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstTrainingActivityActual() {
    }

    public PstTrainingActivityActual(int i) throws DBException {
        super(new PstTrainingActivityActual());
    }

    public PstTrainingActivityActual(String sOid) throws DBException {
        super(new PstTrainingActivityActual(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstTrainingActivityActual(long lOid) throws DBException {
        super(new PstTrainingActivityActual(0));
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

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_TRAINING_ACTIVITY_ACTUAL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstTrainingActivityActual().getClass().getName();
    }

    public static TrainingActivityActual fetchExc(long oid) throws DBException {
        try {
            TrainingActivityActual entTrainingActivityActual = new TrainingActivityActual();
            PstTrainingActivityActual pstTrainingActivityActual = new PstTrainingActivityActual(oid);
            entTrainingActivityActual.setOID(oid);
            entTrainingActivityActual.setTrainingActivityPlanId(pstTrainingActivityActual.getLong(FLD_TRAINING_ACTIVITY_PLAN_ID));
            entTrainingActivityActual.setDate(pstTrainingActivityActual.getDate(FLD_DATE));
            entTrainingActivityActual.setStartTime(pstTrainingActivityActual.getDate(FLD_START_TIME));
            entTrainingActivityActual.setEndTime(pstTrainingActivityActual.getDate(FLD_END_TIME));
            entTrainingActivityActual.setAtendees(pstTrainingActivityActual.getInt(FLD_ATENDEES));
            entTrainingActivityActual.setVenue(pstTrainingActivityActual.getString(FLD_VENUE));
            entTrainingActivityActual.setRemark(pstTrainingActivityActual.getString(FLD_REMARK));
            entTrainingActivityActual.setTrainingId(pstTrainingActivityActual.getLong(FLD_TRAINING_ID));
            entTrainingActivityActual.setTrainner(pstTrainingActivityActual.getString(FLD_TRAINNER));
            entTrainingActivityActual.setScheduleId(pstTrainingActivityActual.getLong(FLD_TRAINING_SCHEDULE_ID));
            entTrainingActivityActual.setOrganizerID(pstTrainingActivityActual.getLong(FLD_ORGANIZED_ID));
            entTrainingActivityActual.setTrainEndDate(pstTrainingActivityActual.getDate(FLD_TRAIN_END_DATE));
            entTrainingActivityActual.setTotalHour(pstTrainingActivityActual.getInt(FLD_TOTAL_HOUR));
            entTrainingActivityActual.setTrainingTitle(pstTrainingActivityActual.getString(FLD_TRAINING_TITLE));
            entTrainingActivityActual.setSertifikatName(pstTrainingActivityActual.getString(FLD_SERTIFIKAT_NAME));
            entTrainingActivityActual.setKodeOjk(pstTrainingActivityActual.getString(FLD_KODE_OJK));
            return entTrainingActivityActual;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingActivityActual(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        TrainingActivityActual entTrainingActivityActual = fetchExc(entity.getOID());
        entity = (Entity) entTrainingActivityActual;
        return entTrainingActivityActual.getOID();
    }

    public static synchronized long updateExc(TrainingActivityActual entTrainingActivityActual) throws DBException {
        try {
            if (entTrainingActivityActual.getOID() != 0) {
                PstTrainingActivityActual pstTrainingActivityActual = new PstTrainingActivityActual(entTrainingActivityActual.getOID());
                pstTrainingActivityActual.setLong(FLD_TRAINING_ACTIVITY_PLAN_ID, entTrainingActivityActual.getTrainingActivityPlanId());
                pstTrainingActivityActual.setDate(FLD_DATE, entTrainingActivityActual.getDate());
                pstTrainingActivityActual.setDate(FLD_START_TIME, entTrainingActivityActual.getStartTime());
                pstTrainingActivityActual.setDate(FLD_END_TIME, entTrainingActivityActual.getEndTime());
                pstTrainingActivityActual.setInt(FLD_ATENDEES, entTrainingActivityActual.getAtendees());
                pstTrainingActivityActual.setString(FLD_VENUE, entTrainingActivityActual.getVenue());
                pstTrainingActivityActual.setString(FLD_REMARK, entTrainingActivityActual.getRemark());
                pstTrainingActivityActual.setLong(FLD_TRAINING_ID, entTrainingActivityActual.getTrainingId());
                pstTrainingActivityActual.setString(FLD_TRAINNER, entTrainingActivityActual.getTrainner());
                pstTrainingActivityActual.setLong(FLD_TRAINING_SCHEDULE_ID, entTrainingActivityActual.getScheduleId());
                pstTrainingActivityActual.setLong(FLD_ORGANIZED_ID, entTrainingActivityActual.getOrganizerID());
                pstTrainingActivityActual.setDate(FLD_TRAIN_END_DATE, entTrainingActivityActual.getTrainEndDate());
                pstTrainingActivityActual.setInt(FLD_TOTAL_HOUR, entTrainingActivityActual.getTotalHour());
                pstTrainingActivityActual.setString(FLD_TRAINING_TITLE, entTrainingActivityActual.getTrainingTitle());
                pstTrainingActivityActual.setString(FLD_SERTIFIKAT_NAME, entTrainingActivityActual.getSertifikatName());
                pstTrainingActivityActual.setString(FLD_KODE_OJK, entTrainingActivityActual.getKodeOjk());
                pstTrainingActivityActual.update();
                return entTrainingActivityActual.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingActivityActual(0), DBException.UNKNOWN);
        }
        return 0;
    }

       public static synchronized long updateSertifikatNameByOid(long actualId, String sertifikatName) throws DBException {
           TrainingActivityActual entTrainingActivityActual = new TrainingActivityActual();
        try {
             entTrainingActivityActual = (TrainingActivityActual) PstTrainingActivityActual.fetchExc(actualId);
                entTrainingActivityActual.setSertifikatName(sertifikatName);
                updateExc(entTrainingActivityActual);
 
            
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingActivityActual(0), DBException.UNKNOWN);
        }
        return entTrainingActivityActual.getOID();
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((TrainingActivityActual) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstTrainingActivityActual pstTrainingActivityActual = new PstTrainingActivityActual(oid);
            pstTrainingActivityActual.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingActivityActual(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(TrainingActivityActual entTrainingActivityActual) throws DBException {
        try {
            PstTrainingActivityActual pstTrainingActivityActual = new PstTrainingActivityActual(0);
            pstTrainingActivityActual.setLong(FLD_TRAINING_ACTIVITY_PLAN_ID, entTrainingActivityActual.getTrainingActivityPlanId());
            pstTrainingActivityActual.setDate(FLD_DATE, entTrainingActivityActual.getDate());
            pstTrainingActivityActual.setDate(FLD_START_TIME, entTrainingActivityActual.getStartTime());
            pstTrainingActivityActual.setDate(FLD_END_TIME, entTrainingActivityActual.getEndTime());
            pstTrainingActivityActual.setInt(FLD_ATENDEES, entTrainingActivityActual.getAtendees());
            pstTrainingActivityActual.setString(FLD_VENUE, entTrainingActivityActual.getVenue());
            pstTrainingActivityActual.setString(FLD_REMARK, entTrainingActivityActual.getRemark());
            pstTrainingActivityActual.setLong(FLD_TRAINING_ID, entTrainingActivityActual.getTrainingId());
            pstTrainingActivityActual.setString(FLD_TRAINNER, entTrainingActivityActual.getTrainner());
            pstTrainingActivityActual.setLong(FLD_TRAINING_SCHEDULE_ID, entTrainingActivityActual.getScheduleId());
            pstTrainingActivityActual.setLong(FLD_ORGANIZED_ID, entTrainingActivityActual.getOrganizerID());
            pstTrainingActivityActual.setDate(FLD_TRAIN_END_DATE, entTrainingActivityActual.getTrainEndDate());
            pstTrainingActivityActual.setInt(FLD_TOTAL_HOUR, entTrainingActivityActual.getTotalHour());
            pstTrainingActivityActual.setString(FLD_TRAINING_TITLE, entTrainingActivityActual.getTrainingTitle());
            pstTrainingActivityActual.setString(FLD_SERTIFIKAT_NAME, entTrainingActivityActual.getSertifikatName());
            pstTrainingActivityActual.setString(FLD_KODE_OJK, entTrainingActivityActual.getKodeOjk());
            pstTrainingActivityActual.insert();
            entTrainingActivityActual.setOID(pstTrainingActivityActual.getLong(FLD_TRAINING_ACTIVITY_ACTUAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTrainingActivityActual(0), DBException.UNKNOWN);
        }
        return entTrainingActivityActual.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((TrainingActivityActual) entity);
    }

    public static void resultToObject(ResultSet rs, TrainingActivityActual entTrainingActivityActual) {
        try {
            entTrainingActivityActual.setOID(rs.getLong(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID]));
            entTrainingActivityActual.setTrainingActivityPlanId(rs.getLong(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID]));
            entTrainingActivityActual.setDate(rs.getDate(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE]));
            entTrainingActivityActual.setStartTime(convertTimeToDate(rs.getTime(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_START_TIME])));
            entTrainingActivityActual.setEndTime(convertTimeToDate(rs.getTime(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_END_TIME])));
            entTrainingActivityActual.setAtendees(rs.getInt(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ATENDEES]));
            entTrainingActivityActual.setVenue(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_VENUE]));
            entTrainingActivityActual.setRemark(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_REMARK]));
            entTrainingActivityActual.setTrainingId(rs.getLong(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ID]));
            entTrainingActivityActual.setTrainner(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINNER]));
            entTrainingActivityActual.setScheduleId(rs.getLong(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_SCHEDULE_ID]));
            entTrainingActivityActual.setOrganizerID(rs.getLong(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_ORGANIZED_ID]));
            entTrainingActivityActual.setTrainEndDate(rs.getDate(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAIN_END_DATE]));
            entTrainingActivityActual.setTotalHour(rs.getInt(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TOTAL_HOUR]));
            entTrainingActivityActual.setTrainingTitle(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_TITLE]));
            entTrainingActivityActual.setSertifikatName(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_SERTIFIKAT_NAME]));
            entTrainingActivityActual.setKodeOjk(rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_KODE_OJK]));
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-01-01 "+rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_START_TIME]));            
            Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-01-01 "+rs.getString(PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_END_TIME]));        
            entTrainingActivityActual.setStartTime(startTime);
            entTrainingActivityActual.setEndTime(endTime);
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

   public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                TrainingActivityActual trainingactivityactual = new TrainingActivityActual();
                resultToObject(rs, trainingactivityactual);
                lists.add(trainingactivityactual);
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

    public static boolean checkOID(long trainingActivityActualId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL + " WHERE "
                    + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + " = " + trainingActivityActualId;

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
            String sql = "SELECT COUNT(" + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + ") FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL;
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


    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    TrainingActivityActual trainingactivityactual = (TrainingActivityActual) list.get(ls);
                    if (oid == trainingactivityactual.getOID()) {
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
    /* This method used to find command where current data */

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
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

    public static Vector listActivity(Date date, int limitStart, int recordToGet, long oidEmployee) {
        if (date == null) {
            return new Vector(1, 1);
        }

        Vector lists = new Vector();
        Vector vTrainingPlan = new Vector();
        long cek = 0;
        String variable = "";
        DBResultSet dbrs = null;
        //update by devin 2014-04-18
        if (oidEmployee != 0) {
            String whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
            Vector listData = PstTrainingHistory.list(0, 0, whereClause, "");
            if (listData != null && listData.size() > 0) {
                for (int y = 0; y < listData.size(); y++) {
                    long oidTrainingPlanId = 0;
                    TrainingHistory history = (TrainingHistory) listData.get(y);
                    if (cek != history.getTrainingActivityPlanId()) {
                        cek = history.getTrainingActivityPlanId();
                        oidTrainingPlanId = history.getTrainingActivityPlanId();
                        vTrainingPlan.add(oidTrainingPlanId);
                    }

                }
            }
        }
        try {
            String sql = " SELECT TAA.*,TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_PROGRAM]
                    + " , TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINER]
                    + " , TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ID]
                    + " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TAA "
                    + " INNER JOIN " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TAP "
                    + " ON TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " = TAA." + fieldNames[FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " ON DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]
                    + " WHERE MONTH(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getMonth() + 1)
                    + " AND "
                    + " YEAR(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getYear() + 1900);
            //update by devin 2014-04-18
            if (vTrainingPlan != null && vTrainingPlan.size() > 0) {

                sql = sql + " AND tap." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] + " IN (";

                for (int xy = 0; xy < vTrainingPlan.size(); xy++) {
                    int hasil = xy - vTrainingPlan.size();
                    long oidTraining = (Long) vTrainingPlan.get(xy);
                    variable += oidTraining;
                    if (hasil != -1) {
                        variable = variable + ",";
                    }
                }
                sql = sql + variable + ")";
            }
            sql = sql + " ORDER BY TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE];

           // System.out.println("DBHandler.DBSVR_TYPE ==== "+DBHandler.DBSVR_TYPE);
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            //System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector(1, 1);
                TrainingActivityActual trainingactivityactual = new TrainingActivityActual();
                TrainingActivityPlan trainingActivityPlan = new TrainingActivityPlan();
                Department department = new Department();

                resultToObject(rs, trainingactivityactual);
                temp.add(trainingactivityactual);

                trainingActivityPlan.setProgram(rs.getString(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_PROGRAM]));
                trainingActivityPlan.setTrainer(rs.getString(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINER]));
                trainingActivityPlan.setTrainingId(rs.getLong(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ID]));
                temp.add(trainingActivityPlan);

                long deptId = rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
                if (deptId == 0) {
                    department.setDepartment("Generic Training");
                } else {
                    department.setOID(deptId);
                    department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                }
                temp.add(department);

                lists.add(temp);
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

    public static Vector listDeptActivity(Date date, int limitStart, int recordToGet, long deptOid) {
        if (date == null) {
            return new Vector(1, 1);
        }

        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT TAA.*,TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_PROGRAM]
                    + " , TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINER]
                    + " , TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ID]
                    + " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " , DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                    + " FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TAA "
                    + " INNER JOIN " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TAP "
                    + " ON TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " = TAA." + fieldNames[FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " ON DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]
                    + " WHERE TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID] + "=" + deptOid
                    + " AND MONTH(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getMonth() + 1)
                    + " AND "
                    + " YEAR(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getYear() + 1900)
                    + " ORDER BY TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE];

            //System.out.println("DBHandler.DBSVR_TYPE ==== "+DBHandler.DBSVR_TYPE);
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Vector temp = new Vector(1, 1);
                TrainingActivityActual trainingactivityactual = new TrainingActivityActual();
                TrainingActivityPlan trainingActivityPlan = new TrainingActivityPlan();
                Department department = new Department();

                resultToObject(rs, trainingactivityactual);
                temp.add(trainingactivityactual);

                trainingActivityPlan.setProgram(rs.getString(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_PROGRAM]));
                trainingActivityPlan.setTrainer(rs.getString(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINER]));
                trainingActivityPlan.setTrainingId(rs.getLong(PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ID]));
                temp.add(trainingActivityPlan);

                long deptId = rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
                if (deptId == 0) {
                    department.setDepartment("Generic Training");
                } else {
                    department.setOID(deptId);
                    department.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                }
                temp.add(department);

                lists.add(temp);
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

    public static int getCount(Date date, int limitStart, int recordToGet, long oidEmployee) {
        DBResultSet dbrs = null;
        //update by devin 2014-04-21
        Vector vTrainingPlan = new Vector();
        long cek = 0;
        String variable = "";
        if (oidEmployee != 0) {
            String whereClause = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
            Vector listData = PstTrainingHistory.list(0, 0, whereClause, "");
            if (listData != null && listData.size() > 0) {
                for (int y = 0; y < listData.size(); y++) {
                    long oidTrainingPlanId = 0;
                    TrainingHistory history = (TrainingHistory) listData.get(y);
                    if (cek != history.getTrainingActivityPlanId()) {
                        cek = history.getTrainingActivityPlanId();
                        oidTrainingPlanId = history.getTrainingActivityPlanId();
                        vTrainingPlan.add(oidTrainingPlanId);
                    }

                }
            }
        }
        try {
            //String sql = "SELECT COUNT("+ PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + ") FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL;
            String sql = " SELECT COUNT(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + ") "
                    + " FROM " + TBL_HR_TRAINING_ACTIVITY_ACTUAL + " TAA "
                    + " INNER JOIN " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TAP "
                    + " ON TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " = TAA." + fieldNames[FLD_TRAINING_ACTIVITY_PLAN_ID]
                    + " LEFT JOIN " + PstDepartment.TBL_HR_DEPARTMENT + " DEP "
                    + " ON DEP." + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]
                    + " = TAP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_DEPARTMENT_ID]
                    + " WHERE MONTH(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getMonth() + 1)
                    + " AND "
                    + " YEAR(TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE] + ")"
                    + " = " + (date.getYear() + 1900);
            //update by devin 2014-04-21
            if (vTrainingPlan != null && vTrainingPlan.size() > 0) {

                sql = sql + " AND tap." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] + " IN (";

                for (int xy = 0; xy < vTrainingPlan.size(); xy++) {
                    int hasil = xy - vTrainingPlan.size();
                    long oidTraining = (Long) vTrainingPlan.get(xy);
                    variable += oidTraining;
                    if (hasil != -1) {
                        variable = variable + ",";
                    }
                }
                sql = sql + variable + ")";
            }
            sql = sql + " ORDER BY TAA." + PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_DATE];

            System.out.println("DBHandler.DBSVR_TYPE ==== " + DBHandler.DBSVR_TYPE);

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
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
}
