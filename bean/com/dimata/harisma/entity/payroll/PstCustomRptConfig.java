/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 * Description : Persistent Custom Rpt Config Date : 2015-03-26
 *
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.employee.EmpCustomField;
import com.dimata.harisma.entity.employee.PstEmpCustomField;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

public class PstCustomRptConfig extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CUSTOM_RPT_CONFIG = "hr_custom_rpt_config";
    public static final int FLD_RPT_CONFIG_ID = 0;
    public static final int FLD_RPT_CONFIG_SHOW_IDX = 1;
    public static final int FLD_RPT_CONFIG_DATA_TYPE = 2;
    public static final int FLD_RPT_CONFIG_DATA_GROUP = 3;
    public static final int FLD_RPT_CONFIG_TABLE_GROUP = 4;
    public static final int FLD_RPT_CONFIG_TABLE_NAME = 5;
    public static final int FLD_RPT_CONFIG_FIELD_NAME = 6;
    public static final int FLD_RPT_CONFIG_FIELD_TYPE = 7;
    public static final int FLD_RPT_CONFIG_FIELD_HEADER = 8;
    public static final int FLD_RPT_CONFIG_FIELD_COLOUR = 9;
    public static final int FLD_RPT_CONFIG_TABLE_PRIORITY = 10;
    public static final int FLD_RPT_MAIN_ID = 11;
    public static String[] fieldNames = {
        "RPT_CONFIG_ID",
        "RPT_CONFIG_SHOW_IDX",
        "RPT_CONFIG_DATA_TYPE",
        "RPT_CONFIG_DATA_GROUP",
        "RPT_CONFIG_TABLE_GROUP",
        "RPT_CONFIG_TABLE_NAME",
        "RPT_CONFIG_FIELD_NAME",
        "RPT_CONFIG_FIELD_TYPE",
        "RPT_CONFIG_FIELD_HEADER",
        "RPT_CONFIG_FIELD_COLOUR",
        "RPT_CONFIG_TABLE_PRIORITY",
        "RPT_MAIN_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };
    public static String[] statusDataType = {"Single", "Combine", "Component"};
    public static String[] statusDataGroup = {"SELECT", "WHERE", "ORDER BY", "GROUP BY"};
    public static String[] showTableList = {
        "Employee Databank",
        "Payment",
        "Payment Type"
    };
    public static String[] realTableList = {
        "hr_employee",
        "pay_slip",
        "pay_emp_level"
    };
    public static String[][] showFieldList = {
        {
            "Payroll Number",
            "Full Name",
            "Company",
            "Division",
            "Department",
            "Section",
            "Position",
            "Level",
            "Marital Status",
            "Date of Birth",
            "Commencing Date",
            "Tax Status",
            "Bank Account",
            "NPWP",
            "Employee Category",
            "Resigned",
            "Resigned Reason",
            "Resigned Date",
            "BPJS Number",
            "BPJS Date",
            "Jamsostek Number",
            "Jamsostek Date",
            /* Update by Hendra | 2015-11-04 */
            "ID Card Number",
            "ID Card Type",
            /* Update By Hendra | 2016-03-01 */
            "Address",
            "Phone",
            "Address Permanent",
            "Phone Emergency",
            "Hand Phone",
            "Postal Code",
            "Sex",
            "Birth Place",
            "Probation Until",
            "Religion", /*RELIGION_ID hr_religion*/
            "Blood Type",
            "Resigned Description",
            "Barcode Number",
            "Valid ID Card",
            "Email Address",
            "Emergency Name",
            "Kecamatan 1",
            "Kabupaten 1",
            "Propinsi 1",
            "Kecamatan 2",
            "Kabupaten 2",
            "Propinsi 2",
            "Father",
            "Mother",
            "Parents Address",
            "Phone Emergency",
            "Address Emergency",
            "Grade Level", /* GRADE_LEVEL_ID */
            "End Contract",
            "Shio",
            "Element",
            "IQ",
            "EQ",
            "Probation End Date",
            "Status Pension Program",
            "Start Date Pension",
            "Member of Kesehatan",
            "Member of Ketenagakerjaan"
        },
        {
            "Period",
            "Day Present",
            "Day Absent",
            "Pay Slip ID",
            /* Update by Gunadi | 2016-03-08 */
            "Pay Slip Division",
            "Pay Slip Department",
            "Pay Slip Position",
            "Pay Slip Grade"
        },
        {
            "Bank Account",
            "BANK or CASH",
            "Status Data"
        }
    };
    public static String[][] showFieldSystem = {
        {
            "EMPLOYEE_NUM",
            "FULL_NAME",
            "COMPANY",
            "DIVISION",
            "DEPARTMENT",
            "SECTION",
            "POSITION",
            "LEVEL",
            "MARITAL_STATUS",
            "BIRTH_DATE",
            "COMMENCING_DATE",
            "MARITAL_STATUS_TAX",
            "NO_REKENING",
            "NPWP",
            "EMP_CATEGORY",
            "RESIGNED",
            "RESIGNED_REASON",
            "RESIGNED_DATE",
            "BPJS_NO",
            "BPJS_DATE",
            "ASTEK_NUM",
            "ASTEK_DATE",
            /* Update by Hendra | 2015-11-04 */
            "INDENT_CARD_NR",
            "ID_CARD_TYPE",
            /* Update By Hendra | 2016-03-01 */
            "ADDRESS",
            "phone",
            "ADDRESS_PERMANENT",
            "phone_emergency",
            "HANDPHONE",
            "POSTAL_CODE",
            "SEX",
            "BIRTH_PLACE",
            "PROBATION_UNTIL",
            "RELIGION",
            "BLOOD_TYPE",
            "RESIGNED_DESC",
            "BARCODE_NUMBER",
            "INDENT_CARD_VALID_TO",
            "EMAIL_ADDRESS",
            "EMERGENCY_NAME",
            "KECAMATAN1",
            "KABUPATEN1",
            "PROPINSI1",
            "KECAMATAN2",
            "KABUPATEN2",
            "PROPINSI2",
            "FATHER",
            "MOTHER",
            "PARENTS_ADDRESS",
            "PHONE_EMG",
            "ADDRESS_EMG",
            "GRADE_CODE",
            "END_CONTRACT",
            "SHIO",
            "ELEMEN",
            "IQ",
            "EQ",
            "PROBATION_END_DATE",
            "STATUS_PENSIUN_PROGRAM",
            "START_DATE_PENSIUN",
            "MEMBER_OF_KESEHATAN",
            "MEMBER_OF_KETENAGAKERJAAN"
        },
        {
            "PERIOD",
            "DAY_PRESENT",
            "DAY_ABSENT",
            "PAY_SLIP_ID",
            /* Update by Gunadi | 2016-03-08 */
            "DIVISION",
            "DEPARTMENT",
            "POSITION",
            "GRADE_CODE"
        },
        {
            "BANK_ACC_NR",
            "BANK_ID",
            "STATUS_DATA"
        }
    };
    
    public static String[][] tableSystem = {
        {
            "hr_employee",
            "hr_employee",
            "pay_general",
            "hr_division",
            "hr_department",
            "hr_section",
            "hr_position",
            "hr_level",
            "hr_marital",
            "hr_employee",
            "hr_employee",
            "hr_marital",
            "hr_employee",
            "hr_employee",
            "hr_emp_category",
            "hr_employee",
            "hr_resigned_reason",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            /* Update by Hendra | 2015-11-04 */
            "hr_employee",
            "hr_employee",
            /* Update By Hendra | 2016-03-01 */
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_religion",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_kecamatan",
            "hr_kabupaten",
            "hr_propinsi",
            "hr_kecamatan",
            "hr_kabupaten",
            "hr_propinsi",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_grade_level",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee",
            "hr_employee"
        },
        {
            "pay_period",
            "pay_slip",
            "pay_slip",
            "pay_slip",
            /* Update by Gunadi | 2016-03-08 */
            "pay_slip",
            "pay_slip",
            "pay_slip",
            "pay_slip"
        },
        {
            "pay_emp_level",
            "pay_emp_level",
            "pay_emp_level"
        }
    };
    /* 0 = Int, 1 = String, 2 = Long, 3 = Date, 4 = Double*/
    public static int[][] fieldTypeSystem = {
        {
            1,//"EMPLOYEE_NUM",
            1,//"FULL_NAME",
            1,//"COMPANY",
            1,//"DIVISION",
            1,//"DEPARTMENT",
            1,//"SECTION",
            1,//"POSITION",
            1,//"LEVEL",
            1,//"MARITAL_STATUS",
            1,//"BIRTH_DATE",
            1,//"COMMENCING_DATE",
            0,//"MARITAL_STATUS_TAX",
            1,//"NO_REKENING",
            1,//"NPWP",
            1,//"EMP_CATEGORY",
            0,//"RESIGNED",
            1,//"RESIGNED_REASON",
            1,//"RESIGNED_DATE"
            1,//"BPJS_NO",
            1,//"BPJS_DATE",
            1,//"ASTEK_NUM",
            3,//"ASTEK_DATE"
            /* Update by Hendra | 2015-11-04 */
            1,/*"INDENT_CARD_NR",*/
            1, /*"ID_CARD_TYPE"*/
            /* Update By Hendra | 2016-03-01 */
            1,//"ADDRESS",
            1,//"phone",
            1,//"ADDRESS_PERMANENT",
            1,//"phone_emergency",
            1,//"HANDPHONE",
            1,//"POSTAL_CODE",
            0,//"SEX",
            1,//"BIRTH_PLACE",
            1,//"PROBATION_UNTIL",
            1,//"RELIGION",
            1,//"BLOOD_TYPE",
            1,//"RESIGNED_DESC",
            1,//"BARCODE_NUMBER",
            1,//"INDENT_CARD_VALID_TO",
            1,//"EMAIL_ADDRESS",
            1,//"EMERGENCY_NAME",
            1,//"KECAMATAN1",
            1,//"KABUPATEN1",
            1,//"PROPINSI1",
            1,//"KECAMATAN2",
            1,//"KABUPATEN2",
            1,//"PROPINSI2",
            1,//"FATHER",
            1,//"MOTHER",
            1,//"PARENTS_ADDRESS",
            1,//"PHONE_EMG",
            1,//"ADDRESS_EMG",
            1,//"GRADE_CODE",
            1,//"END_CONTRACT",
            1,//"SHIO",
            1,//"ELEMEN",
            1,//"IQ",
            1,//"EQ",
            1,//"PROBATION_END_DATE",
            0,//"STATUS_PENSIUN_PROGRAM",
            1,//"START_DATE_PENSIUN",
            0,//"MEMBER_OF_KESEHATAN",
            0//"MEMBER_OF_KETENAGAKERJAAN"
        },
        {
            1,//"PERIOD",
            4,//"DAY_PRESENT",
            4,//"DAY_ABSENT"
            2,//PAY_SLIP_ID
            1, //DIVISION
            1,//DEPARTMENT
            1,//POSITION
            1//GRADE_CODE
        },
        {
            1,//"BANK_ACC_NR"
            2,//"BANK_ID"
            0//"STATUS_DATA"
        }
    };

    public static String[] joinData = {
        "INNER JOIN pay_general ON hr_employee.COMPANY_ID=pay_general.GEN_ID",
        "INNER JOIN hr_division ON hr_employee.DIVISION_ID=hr_division.DIVISION_ID",
        "INNER JOIN hr_department ON hr_employee.DEPARTMENT_ID=hr_department.DEPARTMENT_ID",
        "INNER JOIN hr_section ON hr_employee.SECTION_ID=hr_section.SECTION_ID",
        "INNER JOIN hr_position ON hr_employee.POSITION_ID=hr_position.POSITION_ID",
        "INNER JOIN hr_resigned_reason ON hr_employee.RESIGNED_REASON_ID=hr_resigned_reason.RESIGNED_REASON_ID",
        "INNER JOIN hr_level ON hr_employee.LEVEL_ID=hr_level.LEVEL_ID",
        "INNER JOIN hr_grade_level ON hr_grade_level.GRADE_LEVEL_ID=hr_employee.GRADE_LEVEL_ID",
        "INNER JOIN hr_marital ON hr_employee.MARITAL_ID=hr_marital.MARITAL_ID",
        "INNER JOIN hr_emp_category ON hr_employee.EMP_CATEGORY_ID=hr_emp_category.EMP_CATEGORY_ID",
        "INNER JOIN pay_slip ON hr_employee.EMPLOYEE_ID=pay_slip.EMPLOYEE_ID",
        "INNER JOIN pay_emp_level ON hr_employee.EMPLOYEE_ID=pay_emp_level.EMPLOYEE_ID",
        "INNER JOIN pay_slip_comp ON pay_slip.PAY_SLIP_ID=pay_slip_comp.PAY_SLIP_ID",
        "INNER JOIN pay_period ON pay_slip.PERIOD_ID=pay_period.PERIOD_ID",
        "INNER JOIN hr_religion ON hr_employee.RELIGION_ID=hr_religion.RELIGION_ID"
    };
    
    public static int[] joinDataPriority = {
        0,//"INNER JOIN pay_general ON hr_employee.COMPANY_ID=pay_general.GEN_ID",
        1,//"INNER JOIN hr_division ON hr_employee.DIVISION_ID=hr_division.DIVISION_ID",
        2,//"INNER JOIN hr_department ON hr_employee.DEPARTMENT_ID=hr_department.DEPARTMENT_ID",
        3,//"INNER JOIN hr_section ON hr_employee.SECTION_ID=hr_section.SECTION_ID",
        4,//"INNER JOIN hr_position ON hr_employee.POSITION_ID=hr_position.POSITION_ID",
        5,//"INNER JOIN hr_resigned_reason ON hr_employee.RESIGNED_REASON_ID=hr_resigned_reason.RESIGNED_REASON_ID",
        6,//"INNER JOIN hr_level ON hr_employee.LEVEL_ID=hr_level.LEVEL_ID",
        7,//"INNER JOIN hr_grade_level ON hr_grade_level.GRADE_LEVEL_ID=hr_employee.GRADE_LEVEL_ID",
        8,//"INNER JOIN hr_marital ON hr_employee.MARITAL_ID=hr_marital.MARITAL_ID",
        9,//"INNER JOIN hr_emp_category ON hr_employee.EMP_CATEGORY_ID=hr_emp_category.EMP_CATEGORY_ID",
        10,//"INNER JOIN pay_slip ON hr_employee.EMPLOYEE_ID=pay_slip.EMPLOYEE_ID",
        11,//"INNER JOIN pay_emp_level ON hr_employee.EMPLOYEE_ID=pay_emp_level.EMPLOYEE_ID",
        12,//"INNER JOIN pay_slip_comp ON pay_slip.PAY_SLIP_ID=pay_slip_comp.PAY_SLIP_ID",
        13,//"INNER JOIN pay_period ON pay_slip.PERIOD_ID=pay_period.PERIOD_ID"
        14//"INNER JOIN hr_religion ON hr_employee.RELIGION_ID=hr_religion.RELIGION_ID"
    };

    public PstCustomRptConfig() {
    }

    public PstCustomRptConfig(int i) throws DBException {
        super(new PstCustomRptConfig());
    }

    public PstCustomRptConfig(String sOid) throws DBException {
        super(new PstCustomRptConfig(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCustomRptConfig(long lOid) throws DBException {
        super(new PstCustomRptConfig(0));
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
        return TBL_CUSTOM_RPT_CONFIG;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCustomRptConfig().getClass().getName();
    }

    public static CustomRptConfig fetchExc(long oid) throws DBException {
        try {
            CustomRptConfig entCustomRptConfig = new CustomRptConfig();
            PstCustomRptConfig pstCustomRptConfig = new PstCustomRptConfig(oid);
            entCustomRptConfig.setOID(oid);
            entCustomRptConfig.setRptConfigShowIdx(pstCustomRptConfig.getInt(FLD_RPT_CONFIG_SHOW_IDX));
            entCustomRptConfig.setRptConfigDataType(pstCustomRptConfig.getInt(FLD_RPT_CONFIG_DATA_TYPE));
            entCustomRptConfig.setRptConfigDataGroup(pstCustomRptConfig.getInt(FLD_RPT_CONFIG_DATA_GROUP));
            entCustomRptConfig.setRptConfigTableGroup(pstCustomRptConfig.getString(FLD_RPT_CONFIG_TABLE_GROUP));
            entCustomRptConfig.setRptConfigTableName(pstCustomRptConfig.getString(FLD_RPT_CONFIG_TABLE_NAME));
            entCustomRptConfig.setRptConfigFieldName(pstCustomRptConfig.getString(FLD_RPT_CONFIG_FIELD_NAME));
            entCustomRptConfig.setRptConfigFieldType(pstCustomRptConfig.getInt(FLD_RPT_CONFIG_FIELD_TYPE));
            entCustomRptConfig.setRptConfigFieldHeader(pstCustomRptConfig.getString(FLD_RPT_CONFIG_FIELD_HEADER));
            entCustomRptConfig.setRptConfigFieldColour(pstCustomRptConfig.getString(FLD_RPT_CONFIG_FIELD_COLOUR));
            entCustomRptConfig.setRptConfigTablePriority(pstCustomRptConfig.getInt(FLD_RPT_CONFIG_TABLE_PRIORITY));
            entCustomRptConfig.setRptMainId(pstCustomRptConfig.getLong(FLD_RPT_MAIN_ID));
            return entCustomRptConfig;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptConfig(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CustomRptConfig entCustomRptConfig = fetchExc(entity.getOID());
        entity = (Entity) entCustomRptConfig;
        return entCustomRptConfig.getOID();
    }

    public static synchronized long updateExc(CustomRptConfig entCustomRptConfig) throws DBException {
        try {
            if (entCustomRptConfig.getOID() != 0) {
                PstCustomRptConfig pstCustomRptConfig = new PstCustomRptConfig(entCustomRptConfig.getOID());
                pstCustomRptConfig.setInt(FLD_RPT_CONFIG_SHOW_IDX, entCustomRptConfig.getRptConfigShowIdx());
                pstCustomRptConfig.setInt(FLD_RPT_CONFIG_DATA_TYPE, entCustomRptConfig.getRptConfigDataType());
                pstCustomRptConfig.setInt(FLD_RPT_CONFIG_DATA_GROUP, entCustomRptConfig.getRptConfigDataGroup());
                pstCustomRptConfig.setString(FLD_RPT_CONFIG_TABLE_GROUP, entCustomRptConfig.getRptConfigTableGroup());
                pstCustomRptConfig.setString(FLD_RPT_CONFIG_TABLE_NAME, entCustomRptConfig.getRptConfigTableName());
                pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_NAME, entCustomRptConfig.getRptConfigFieldName());
                pstCustomRptConfig.setInt(FLD_RPT_CONFIG_FIELD_TYPE, entCustomRptConfig.getRptConfigFieldType());
                pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_HEADER, entCustomRptConfig.getRptConfigFieldHeader());
                pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_COLOUR, entCustomRptConfig.getRptConfigFieldColour());
                pstCustomRptConfig.setInt(FLD_RPT_CONFIG_TABLE_PRIORITY, entCustomRptConfig.getRptConfigTablePriority());
                pstCustomRptConfig.setLong(FLD_RPT_MAIN_ID, entCustomRptConfig.getRptMainId());
                pstCustomRptConfig.update();
                return entCustomRptConfig.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptConfig(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((CustomRptConfig) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstCustomRptConfig pstCustomRptConfig = new PstCustomRptConfig(oid);
            pstCustomRptConfig.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptConfig(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(CustomRptConfig entCustomRptConfig) throws DBException {
        try {
            PstCustomRptConfig pstCustomRptConfig = new PstCustomRptConfig(0);
            pstCustomRptConfig.setInt(FLD_RPT_CONFIG_SHOW_IDX, entCustomRptConfig.getRptConfigShowIdx());
            pstCustomRptConfig.setInt(FLD_RPT_CONFIG_DATA_TYPE, entCustomRptConfig.getRptConfigDataType());
            pstCustomRptConfig.setInt(FLD_RPT_CONFIG_DATA_GROUP, entCustomRptConfig.getRptConfigDataGroup());
            pstCustomRptConfig.setString(FLD_RPT_CONFIG_TABLE_GROUP, entCustomRptConfig.getRptConfigTableGroup());
            pstCustomRptConfig.setString(FLD_RPT_CONFIG_TABLE_NAME, entCustomRptConfig.getRptConfigTableName());
            pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_NAME, entCustomRptConfig.getRptConfigFieldName());
            pstCustomRptConfig.setInt(FLD_RPT_CONFIG_FIELD_TYPE, entCustomRptConfig.getRptConfigFieldType());
            pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_HEADER, entCustomRptConfig.getRptConfigFieldHeader());
            pstCustomRptConfig.setString(FLD_RPT_CONFIG_FIELD_COLOUR, entCustomRptConfig.getRptConfigFieldColour());
            pstCustomRptConfig.setInt(FLD_RPT_CONFIG_TABLE_PRIORITY, entCustomRptConfig.getRptConfigTablePriority());
            pstCustomRptConfig.setLong(FLD_RPT_MAIN_ID, entCustomRptConfig.getRptMainId());
            pstCustomRptConfig.insert();
            entCustomRptConfig.setOID(pstCustomRptConfig.getLong(FLD_RPT_CONFIG_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptConfig(0), DBException.UNKNOWN);
        }
        return entCustomRptConfig.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((CustomRptConfig) entity);
    }

    public static void resultToObject(ResultSet rs, CustomRptConfig entCustomRptConfig) {
        try {
            entCustomRptConfig.setOID(rs.getLong(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_ID]));
            entCustomRptConfig.setRptConfigShowIdx(rs.getInt(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_SHOW_IDX]));
            entCustomRptConfig.setRptConfigDataType(rs.getInt(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_DATA_TYPE]));
            entCustomRptConfig.setRptConfigDataGroup(rs.getInt(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_DATA_GROUP]));
            entCustomRptConfig.setRptConfigTableGroup(rs.getString(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_TABLE_GROUP]));
            entCustomRptConfig.setRptConfigTableName(rs.getString(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_TABLE_NAME]));
            entCustomRptConfig.setRptConfigFieldName(rs.getString(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_FIELD_NAME]));
            entCustomRptConfig.setRptConfigFieldType(rs.getInt(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_FIELD_TYPE]));
            entCustomRptConfig.setRptConfigFieldHeader(rs.getString(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_FIELD_HEADER]));
            entCustomRptConfig.setRptConfigFieldColour(rs.getString(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_FIELD_COLOUR]));
            entCustomRptConfig.setRptConfigTablePriority(rs.getInt(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_TABLE_PRIORITY]));
            entCustomRptConfig.setRptMainId(rs.getLong(PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_MAIN_ID]));
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
            String sql = "SELECT * FROM " + TBL_CUSTOM_RPT_CONFIG;
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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CustomRptConfig customRptConfig = new CustomRptConfig();
                resultToObject(rs, customRptConfig);
                lists.add(customRptConfig);
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

    public static Vector listData(String sqlData, Vector data) {
        /* Code is updated 2017-03-02 */
        /* find custom field */
        Vector vctRptCField = new Vector();
        Vector vctRptNRField = new Vector();
        CustomRptConfig rptCustom = (CustomRptConfig) data.get(0);
        if (rptCustom.getRptMainId() != 0) {
            String where = " RPT_CONFIG_DATA_TYPE = 4 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + rptCustom.getRptMainId();
            vctRptCField = PstCustomRptConfig.list(0, 0, where, "");
            
            String whereNR = " RPT_CONFIG_DATA_TYPE = 5 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID ="+rptCustom.getRptMainId();
            vctRptNRField = PstCustomRptConfig.list(0, 0, whereNR, "");
        }
        /*
         * sqlData : nilai query hasil generate
         * data : adalah data list yg di SELECT
         */
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = sqlData;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CustomRptDynamic dyc = new CustomRptDynamic();
                for (int i = 0; i < data.size(); i++) {
                    CustomRptConfig rpt = (CustomRptConfig) data.get(i);
                    //if ini mendadak untuk tax status 
                    if (rpt.getRptConfigFieldName().equals("MARITAL_STATUS_TAX")) {
                        Vector maritalS = PstMarital.list(0, 0, "MARITAL_STATUS_TAX = " + rs.getString(rpt.getRptConfigFieldName()), "");
                        Marital marital = (Marital) maritalS.get(0);
                        dyc.setFields(rpt.getRptConfigFieldName(), marital.getMaritalCode());
                    } else {
                        if (rs.getString(rpt.getRptConfigFieldName()) != null) {
                            dyc.setFields(rpt.getRptConfigFieldName(), rs.getString(rpt.getRptConfigFieldName()));
                        } else {
                            dyc.setFields(rpt.getRptConfigFieldName(), "-");
                        }
                    }
                    /* WHERE untuk Salary Component */
                    String where = " RPT_CONFIG_DATA_TYPE = 2 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + rpt.getRptMainId();
                    /* listData untuk Salary Component */
                    Vector listData = list(0, 0, where, "");
                    if (listData != null && listData.size() > 0) {
                        for (int d = 0; d < listData.size(); d++) {
                            CustomRptConfig comp = (CustomRptConfig) listData.get(d);
                            String qComp = "SELECT COMP_VALUE FROM pay_slip_comp WHERE PAY_SLIP_ID=" + rs.getString("PAY_SLIP_ID") + " AND COMP_CODE='" + comp.getRptConfigFieldName() + "'";
                            /* listComp */
                            Vector listComp = PstCustomRptConfig.listComponent(qComp);
                            if (listComp != null && listComp.size() > 0) {
                                for (int c = 0; c < listComp.size(); c++) {
                                    PaySlipComp payComp = (PaySlipComp) listComp.get(c);
                                    dyc.setFields("" + comp.getRptConfigFieldName(), String.format("%.2f", payComp.getCompValue()));
                                }
                            } else {
                                dyc.setFields("" + comp.getRptConfigFieldName(), "0");
                            }
                        }
                    }
                    /* Jika ada Custom Field maka */
                    if (vctRptNRField != null && vctRptNRField.size() > 0) {
                        long empId = getEmployeeId(rs.getString("EMPLOYEE_NUM"));
                        for (int cf = 0; cf < vctRptNRField.size(); cf++) {
                            CustomRptConfig rptConfigCField = (CustomRptConfig) vctRptNRField.get(cf);
                            String whereNR = PstPayConfigPotongan.fieldNames[PstPayConfigPotongan.FLD_EMPLOYEE_ID] + "=" + empId;
                            Vector vnilaiNRField = PstPayConfigPotongan.list(0, 0, whereNR, "");
                            if (vnilaiNRField != null && vnilaiNRField.size() > 0) {
                                PayConfigPotongan empNR = (PayConfigPotongan) vnilaiNRField.get(0);
                                if (empNR.getNoRekening() == null || empNR.getNoRekening().equals("")) {
                                    dyc.setFields(rptConfigCField.getRptConfigFieldName(), "-");
                                } else {
                                    dyc.setFields(rptConfigCField.getRptConfigFieldName(), empNR.getNoRekening());
                                }
                            } else {
                                dyc.setFields(rptConfigCField.getRptConfigFieldName(), "--");
                            }

                        }
                    }
                    /* Jika ada Potongan Kredit maka */
                    if (vctRptCField != null && vctRptCField.size() > 0) {
                        long empId = getEmployeeId(rs.getString("EMPLOYEE_NUM"));
                        for (int cf = 0; cf < vctRptCField.size(); cf++) {
                            CustomRptConfig rptConfigCField = (CustomRptConfig) vctRptCField.get(cf);
                            String whereCF = PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_CUSTOM_FIELD_ID] + "=" + rptConfigCField.getRptConfigFieldName();
                            whereCF += " AND " + PstEmpCustomField.fieldNames[PstEmpCustomField.FLD_EMPLOYEE_ID] + "=" + empId;
                            Vector vnilaiCField = PstEmpCustomField.list(0, 0, whereCF, "");
                            if (vnilaiCField != null && vnilaiCField.size() > 0) {
                                EmpCustomField empCF = (EmpCustomField) vnilaiCField.get(0);
                                if (empCF.getDataText() == null || empCF.getDataText().equals("")) {
                                    dyc.setFields(rptConfigCField.getRptConfigFieldName(), "-");
                                } else {
                                    dyc.setFields(rptConfigCField.getRptConfigFieldName(), empCF.getDataText());
                                }
                            } else {
                                dyc.setFields(rptConfigCField.getRptConfigFieldName(), "--");
                            }

                        }
                    }

                    /* WHERE untuk Salary Combine */
                    String whereCombine = " RPT_CONFIG_DATA_TYPE = 1 AND RPT_CONFIG_DATA_GROUP = 0 AND RPT_MAIN_ID =" + rpt.getRptMainId();
                    /* listData untuk Salary Component */
                    String qCombine = "";
                    String[] combineStr;
                    String[] operanStr;
                    Vector listCompValue;
                    double totalCombine = 1;
                    double[] listValue;
                    Vector listCombine = list(0, 0, whereCombine, "");
                    if (listCombine != null && listCombine.size() > 0) {

                        for (int b = 0; b < listCombine.size(); b++) {
                            CustomRptConfig combine = (CustomRptConfig) listCombine.get(b);

                            /*
                             * combine.getRptConfigFieldName() == ALW01+ALW04-ALW21
                             */
                            combineStr = apostrophe(combine.getRptConfigFieldName());
                            operanStr = apostrophe(combine.getRptConfigTableGroup());

                            listValue = new double[combineStr.length];
                            for (int arrCom = 0; arrCom < combineStr.length; arrCom++) {
                                qCombine = "SELECT COMP_VALUE FROM pay_slip_comp WHERE PAY_SLIP_ID=" + rs.getString("PAY_SLIP_ID") + " AND COMP_CODE='" + combineStr[arrCom] + "'";
                                listCompValue = listComponent(qCombine);
                                if (listCompValue != null && listCompValue.size() > 0) {

                                    for (Object listCompValue1 : listCompValue) {
                                        PaySlipComp payComp = (PaySlipComp) listCompValue1;
                                        listValue[arrCom] = payComp.getCompValue();
                                    }

                                } else {
                                    listValue[arrCom] = 0;
                                }
                            }

                            int v = 0;

                            while (v < listValue.length) {
                                if (v == 0) {
                                    totalCombine = listValue[v];
                                }
                                for (String operanStr1 : operanStr) {


                                    if (("+".equals(operanStr1)) || "-".equals(operanStr1)) {
                                        v++;
                                        if ("+".equals(operanStr1)) {
                                            totalCombine = totalCombine + listValue[v];
                                        }
                                        if ("-".equals(operanStr1)) {
                                            totalCombine = totalCombine - listValue[v];
                                        }
                                    }

                                }v++;
                                }

                            dyc.setFields("" + combine.getRptConfigFieldName(), "" + String.format("%.2f", totalCombine));
                            qCombine = "";
                            totalCombine = 0;
                            listCompValue = new Vector();
                            listValue = null;
                        }
                    }
                }

                lists.add(dyc);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println("error : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listComponent(String query) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = query;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PaySlipComp payComp = new PaySlipComp();
                payComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                lists.add(payComp);
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

    public static boolean checkOID(long entId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CUSTOM_RPT_CONFIG + " WHERE "
                    + PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_ID] + " = " + entId;

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
            String sql = "SELECT COUNT(" + PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_CONFIG_ID] + ") FROM " + TBL_CUSTOM_RPT_CONFIG;
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
                    CustomRptConfig entCustomRpt = (CustomRptConfig) list.get(ls);
                    if (oid == entCustomRpt.getOID()) {
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

    public static String[] apostrophe(String value) {
        /* value = ALW01+ALW04-ALW21 */
        String[] str = new String[value.length()];
        int inc = 0;
        for (String retval : value.split(",")) {
            str[inc] = retval;
            inc++;
        }
        String[] data = new String[inc];
        inc = 0;
        for (String retval : value.split(",")) {
            data[inc] = retval;
            inc++;
        }
        /* ada masalah di String[length] */
        return data;
    }

    public static String apostrophe(String value, String opr) {
        String str = "";
        if (opr.equals("=")) {
            str = "'" + value + "'";
        } else if (opr.equals("BETWEEN")) {
            String[] data = new String[5];
            int inc = 0;
            for (String retval : value.split(" ")) {
                data[inc] = retval;
                inc++;
            }
            str = "'" + data[0] + "' AND '" + data[1] + "'";
        } else if (opr.equals("LIKE")) {
            str = "'" + value + "'";
        } else if (opr.equals("IN")) {
            String stIn = "";
            for (String retval : value.split(",")) {
                stIn += " '" + retval + "', ";
            }
            stIn += "'0'";
            str += "(" + stIn + ")";
        } else if (opr.equals("!=")) {
            str = "'" + value + "'";
        } else {
            str = value;
        }
        return str;
    }
    /*
     * Update By Hendra | 2016-03-01
     */
    public static Vector listWhere(long oidCustom){
        String whereList = fieldNames[FLD_RPT_CONFIG_DATA_TYPE]+" = 0 AND ";
        whereList += fieldNames[FLD_RPT_CONFIG_DATA_GROUP]+" = 1 AND "+fieldNames[FLD_RPT_MAIN_ID]+"="+oidCustom;
        Vector listWhere = PstCustomRptConfig.list(0, 0, whereList, "");
        return listWhere;
    }
    
    public static String selectJoin(Vector listData){
        String select = "SELECT ";
        String join =  " FROM hr_view_work_history_now";
        boolean chkJoinEmp = false;
        boolean chkJoinPaySlip = false;
        boolean chkJoinDivision = false;
        boolean chkJoinDepartment = false;
        boolean chkJoinSection = false;
        boolean chkJoinPosition = false;
        boolean chkJoinLevel = false;
        boolean chkGrade = false;
        boolean chkPeriod = false;
        boolean chkCategory = false;
        
        if (listData != null && listData.size() > 0){
            for(int i=0;i<listData.size(); i++){
                CustomRptConfig rpt = (CustomRptConfig)listData.get(i);
                String strSelect = rpt.getRptConfigTableName()+"."+rpt.getRptConfigFieldName();
                if (strSelect.equals("pay_slip.DIVISION")){
                    select += "hr_division.DIVISION";
                } else if (strSelect.equals("pay_slip.DEPARTMENT")){
                    select += "hr_department.DEPARTMENT";
                } else if (strSelect.equals("pay_slip.POSITION")){
                    select += "hr_position.POSITION";
                } else if (strSelect.equals("pay_slip.SECTION")){
                    select += "hr_section.SECTION";
                } else if (strSelect.equals("pay_slip.LEVEL")){
                    select += "hr_level.LEVEL";
                } else if (strSelect.equals("pay_slip.GRADE_CODE")){
                    select += "hr_grade_level.GRADE_CODE";
                }
                else {
                    select += strSelect;
                }
                
                if (rpt.getRptConfigTableName().equals("hr_employee") && !chkJoinEmp){
                    join += " INNER JOIN hr_employee ON hr_view_work_history_now.EMPLOYEE_ID = hr_employee.EMPLOYEE_ID";
                    chkJoinEmp = true;
                }
                if (rpt.getRptConfigTableName().equals("pay_slip") && !chkJoinPaySlip){
                    join += " INNER JOIN pay_slip ON hr_view_work_history_now.EMPLOYEE_ID = pay_slip.EMPLOYEE_ID";
                    chkJoinPaySlip = true;
                }
//                if (rpt.getRptConfigTableName().equals("pay_period") && !chkPeriod){
//                    join += " INNER JOIN pay_period ON pay_slip.PERIOD_ID = pay_period.PERIOD_ID";
//                    chkPeriod = true;
//                }
                if ((strSelect.equals("pay_slip.DIVISION") || strSelect.equals("hr_division.DIVISION")) && !chkJoinDivision){
                    join += " INNER JOIN hr_division ON hr_view_work_history_now.`DIVISION_ID` = hr_division.`DIVISION_ID`";
                    chkJoinDivision = true;
                }
                if ((strSelect.equals("pay_slip.DEPARTMENT")  || strSelect.equals("hr_department.DEPARTMENT")) && !chkJoinDepartment){
                    join += " INNER JOIN hr_department ON hr_view_work_history_now.`DEPARTMENT_ID` = hr_department.`DEPARTMENT_ID`";
                    chkJoinDepartment = true;
                }
                if ((strSelect.equals("pay_slip.SECTION") || strSelect.equals("hr_section.SECTION")) && !chkJoinSection){
                    join += " INNER JOIN hr_section ON hr_view_work_history_now.`SECTION_ID` = hr_section.`SECTION_ID`";
                    chkJoinSection = true;
                }
                if ((strSelect.equals("pay_slip.POSITION") || strSelect.equals("hr_position.POSITION")) && !chkJoinPosition){
                    join += " INNER JOIN hr_position ON hr_view_work_history_now.`POSITION_ID` = hr_position.`POSITION_ID`";
                    chkJoinPosition = true;
                }
                if ((strSelect.equals("pay_slip.LEVEL") || strSelect.equals("hr_level.LEVEL")) && !chkJoinLevel){
                    join += " INNER JOIN hr_level ON hr_view_work_history_now.`LEVEL_ID` = hr_level.`LEVEL_ID`";
                    chkJoinLevel = true;
                }
                if ((strSelect.equals("pay_slip.GRADE_CODE") || strSelect.equals("hr_grade_level.GRADE_CODE")) && !chkGrade){
                    join += " INNER JOIN hr_grade_level ON hr_view_work_history_now.`GRADE_LEVEL_ID` = hr_grade_level.`GRADE_LEVEL_ID`";
                    chkGrade = true;
                }
                if (strSelect.equals("hr_emp_category.EMP_CATEGORY") && !chkCategory){
                    join += " INNER JOIN hr_emp_category  ON hr_employee.EMP_CATEGORY_ID = hr_emp_category.EMP_CATEGORY_ID ";
                    chkCategory = true;
                }
                
                if (i == listData.size()-1){
                    select +=" ";
                } else {
                    select += ", ";
                }
            }
        }
        if (chkJoinPaySlip){
            join += " INNER JOIN pay_period ON pay_slip.PERIOD_ID = pay_period.PERIOD_ID";
        }
        
        select += join;
        
        return select;
    }
    
    public static boolean checkMBT(int selectionChoose, String[] whereField, Vector whereValue, String[] whereType, String[] operator, String whereCustom) {
        boolean MBT = false;
        
        if (selectionChoose == 0) {
            String valueInp = "";
            String[] dataWhere = new String[whereField.length];
            int a = 0;
            /* Jika input selection tidak kosong */
            if (whereValue != null && whereValue.size() > 0) {
                /* Ulang sebanyak jumlah field */
                for (int w = 0; w < whereField.length; w++) {
                    String[] dataWhereValue = (String[])whereValue.get(w);
                    if (dataWhereValue != null && dataWhereValue.length > 0) {
                        /* Jika tipe data field = String maka..*/
                        if (whereType[w].equals("1")) {
                            /* manipulasi data dengan menambahkan apostrophe (') */
                            for (int v = 0; v < dataWhereValue.length; v++) {
                                if (dataWhereValue[v].equals("MASA BEBAS TUGAS")){
                                    MBT = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return MBT;
    }
    
    public static String whereDate(int selectionChoose, String[] whereField, Vector whereValue, String[] whereType, String[] operator, String whereCustom){
        String whereClause = "";
        if (selectionChoose == 0) {
            String valueInp = "";
            String[] dataWhere = new String[whereField.length];
            int a = 0;
            /* Jika input selection tidak kosong */
            if (whereValue != null && whereValue.size() > 0) {
                /* Ulang sebanyak jumlah field */
                for (int w = 0; w < whereField.length; w++) {
                    String[] dataWhereValue = (String[])whereValue.get(w);
                    if (dataWhereValue != null && dataWhereValue.length > 0) {
                        /* Jika tipe data field = String maka..*/
                        if (whereType[w].equals("1")) {
                            /* manipulasi data dengan menambahkan apostrophe (') */
                            for (int v = 0; v < dataWhereValue.length; v++) {
                                if (whereField[w].equals("pay_period.PERIOD")){
                                    String period = dataWhereValue[v];
                                    Vector listPeriod = PstPayPeriod.list(0, 0, PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD] + " = '" +  period+"'", "");
                                    if (listPeriod.size()>0){
                                        for (int i =0; i < listPeriod.size(); i++){
                                            PayPeriod payPeriod = (PayPeriod) listPeriod.get(i);
                                            whereClause = " WHERE pay_period.PERIOD = '"+period+"'"
                                                    + " AND hr_view_work_history_now.DIVISION_ID = '2026'"
                                                    + " AND `hr_view_work_history_now`.`WORK_FROM` BETWEEN '"+ Formater.formatDate(payPeriod.getStartDate(), "yyyy-MM-dd")+"' AND '"+Formater.formatDate(payPeriod.getEndDate(), "yyyy-MM-dd")+"'";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return whereClause;
    }

    public static String findWhereData(int selectionChoose, String[] whereField, Vector whereValue, String[] whereType, String[] operator, String whereCustom,String[] whereFieldCompare,Vector whereValueCompare, String[] operatorCompare ) {
        String whereClause = "";
        if (selectionChoose == 0) {
            String valueInp = "";
            String[] dataWhere = new String[whereField.length];
            int a = 0;
            /* Jika input selection tidak kosong */
            if (whereValue != null && whereValue.size() > 0) {
                whereClause = " WHERE ";
                /* Ulang sebanyak jumlah field */
                for (int w = 0; w < whereField.length; w++) {
                    String[] dataWhereValue = (String[])whereValue.get(w);
                    if (dataWhereValue != null && dataWhereValue.length > 0) {
                        /* Jika tipe data field = String maka..*/
                        if (whereType[w].equals("1")) {
                            /* manipulasi data dengan menambahkan apostrophe (') */
                            String dataValue = "";
                            for (int v = 0; v < dataWhereValue.length; v++) {
                                dataValue += dataWhereValue[v] + ",";
                            }
                            dataValue = dataValue.substring(0, dataValue.length() - 1);
                            if (dataValue.equals("")) {
                                valueInp = "";
                            } else {
                                valueInp = apostrophe(dataValue, operator[w]);
                            }

                        } else {
                            valueInp = dataWhereValue[0];
                        }
                        if (!valueInp.equals("")) {
                            //add by Eri Yudi 2020-09-28
                            String dataValueCompare = "";
                            if(whereFieldCompare.length > 0){
                              
                                    if(whereField[w].equals(whereFieldCompare[0]) ){
                                        operator[w] = operatorCompare[0];
                                        String[] dataWhereValueCompare =  (String[])whereValueCompare.get(0);
                                         
                                        for (int v = 0; v < dataWhereValueCompare.length; v++) {
                                            dataValueCompare += dataWhereValueCompare[v] + ",";
                                        }
                                         dataValueCompare = dataValueCompare.substring(0, dataValueCompare.length() - 1);
                                         
                                         if (dataValueCompare.equals("")) {
                                            valueInp = "";
                                        } else {
                                            valueInp = apostrophe(dataValueCompare, operator[w]);
                                        }
                                    }
                                     
                                
                            }
                           
                            //end add by Eri
                            dataWhere[a] = whereField[w] + " " + operator[w] + " " + valueInp + " ";
                            a++;
                        }
                    }
                }
                for (int x = 0; x < a; x++) {
                    whereClause += dataWhere[x];
                    if (x == a - 1) {
                        whereClause += " ";
                    } else {
                        whereClause += " AND ";
                    }
                }
            }
        } else {
            whereCustom = whereCustom.replace("#", "'");
            whereClause = " " + whereCustom + " ";
        }
        return whereClause;
    }

    /* list data jika tanpa pay slip */
    public static Vector listDataWithoutPaySlip(String sqlData, Vector data) {
        /*
         * sqlData : nilai query hasil generate
         * data : adalah data list yg di SELECT
         */
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = sqlData;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CustomRptDynamic dyc = new CustomRptDynamic();
                for (int i = 0; i < data.size(); i++) {
                    CustomRptConfig rpt = (CustomRptConfig) data.get(i);
                    if (rs.getString(rpt.getRptConfigFieldName()) != null) {
                        //if ini mendadak untuk tax status 
                        if (rpt.getRptConfigFieldName().equals("MARITAL_STATUS_TAX")) {
                            Vector maritalS = PstMarital.list(0, 0, "MARITAL_STATUS_TAX = " + rs.getString(rpt.getRptConfigFieldName()), "");
                            Marital marital = (Marital) maritalS.get(0);
                            dyc.setFields(rpt.getRptConfigFieldName(), marital.getMaritalCode());
                        } else {
                            dyc.setFields(rpt.getRptConfigFieldName(), rs.getString(rpt.getRptConfigFieldName()));
                        }
                    } else {
                        dyc.setFields(rpt.getRptConfigFieldName(), "-");
                    }
                }

                lists.add(dyc);
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

    /* menampilkan daftar data pada <select></select> */
    public static Vector listSelectData(String sqlData, String field) {
        /*
         * sqlData : nilai query hasil generate
         * data : adalah data list yg di SELECT
         */
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = sqlData;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CustomRptDynamic dyc = new CustomRptDynamic();
                dyc.setFields(field, rs.getString(field));
                lists.add(dyc);
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

    public static long getEmployeeId(String nrk) {
        long employeeId = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT hr_employee.EMPLOYEE_ID FROM hr_employee ";
            sql += " WHERE hr_employee.EMPLOYEE_NUM='" + nrk + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                employeeId = rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            }
            rs.close();
            return employeeId;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return employeeId;
    }
}