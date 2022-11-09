/* 
 * Form Name  	:  FrmDepartment.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.masterdata;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import java.sql.Date;
import com.dimata.harisma.entity.masterdata.*;

public class FrmDepartment extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Department department;
    public static final String FRM_NAME_DEPARTMENT = "FRM_NAME_DEPARTMENT";
    public static final int FRM_FIELD_DEPARTMENT_ID = 0;
    public static final int FRM_FIELD_DEPARTMENT = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static final int FRM_FIELD_DIVISION_ID = 3;
    public static final int FRM_FIELD_JOIN_TO_DEP_ID = 4;
    public static final int FRM_FIELD_DEPARTMENT_TYPE_ID = 5;
    public static final int FRM_FIELD_ADDRESS = 6;
    public static final int FRM_FIELD_CITY = 7;
    public static final int FRM_FIELD_NPWP = 8;
    public static final int FRM_FIELD_PROVINCE = 9;
    public static final int FRM_FIELD_REGION = 10;
    public static final int FRM_FIELD_SUB_REGION = 11;
    public static final int FRM_FIELD_VILLAGE = 12;
    public static final int FRM_FIELD_AREA = 13;
    public static final int FRM_FIELD_TELEPHONE = 14;
    public static final int FRM_FIELD_FAX_NUMBER = 15;
    public static final int FRM_FIELD_VALID_STATUS = 16;
    public static final int FRM_FIELD_VALID_START = 17;
    public static final int FRM_FIELD_VALID_END = 18;
    /*add by Eri Yudi 2021-07-31 untu mapping kompetensi bersdarkan kelas unit*/
    public static final int FRM_FIELD_LEVEL_DEPARTMENT = 19;
    
    public static String[] fieldNames = {
        "FRM_FIELD_DEPARTMENT_ID", "FRM_FIELD_DEPARTMENT",
        "FRM_FIELD_DESCRIPTION", "FRM_FIELD_DIVISION_ID", "FRM_FIELD_JOIN_TO_DEP_ID",
        "FRM_FIELD_DEPARTMENT_TYPE_ID",
        "FRM_FIELD_ADDRESS",
        "FRM_FIELD_CITY",
        "FRM_FIELD_NPWP",
        "FRM_FIELD_PROVINCE",
        "FRM_FIELD_REGION",
        "FRM_FIELD_SUB_REGION",
        "FRM_FIELD_VILLAGE",
        "FRM_FIELD_AREA",
        "FRM_FIELD_TELEPHONE",
        "FRM_FIELD_FAX_NUMBE",
        "FRM_FIELD_VALID_STATUS",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END",
        "FRM_FIELD_LEVEL_DEPARTMENT"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING, TYPE_LONG + ENTRY_REQUIRED, TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT
    };

    public FrmDepartment() {
    }

    public FrmDepartment(Department department) {
        this.department = department;
    }

    public FrmDepartment(HttpServletRequest request, Department department) {
        super(new FrmDepartment(department), request);
        this.department = department;
    }

    public String getFormName() {
        return FRM_NAME_DEPARTMENT;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public Department getEntityObject() {
        return department;
    }

    public void requestEntityObject(Department department) {
        try {
            this.requestParam();
            department.setDepartment(getString(FRM_FIELD_DEPARTMENT));
            department.setDescription(getString(FRM_FIELD_DESCRIPTION));
            department.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            department.setJoinToDepartmentId(getLong(FRM_FIELD_JOIN_TO_DEP_ID));
            department.setDepartmentTypeId(getLong(FRM_FIELD_DEPARTMENT_TYPE_ID));
            department.setAddress(getString(FRM_FIELD_ADDRESS));
            department.setCity(getString(FRM_FIELD_CITY));
            department.setNpwp(getString(FRM_FIELD_NPWP));
            department.setProvince(getString(FRM_FIELD_PROVINCE));
            department.setRegion(getString(FRM_FIELD_REGION));
            department.setSubRegion(getString(FRM_FIELD_SUB_REGION));
            department.setVillage(getString(FRM_FIELD_VILLAGE));
            department.setArea(getString(FRM_FIELD_AREA));
            department.setTelphone(getString(FRM_FIELD_TELEPHONE));
            department.setFaxNumber(getString(FRM_FIELD_FAX_NUMBER));
            department.setValidStatus(getInt(FRM_FIELD_VALID_STATUS));
            department.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            department.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
            department.setLevelDepartment(getInt(FRM_FIELD_LEVEL_DEPARTMENT));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
