/*
 * Form Name  	:  FrmPosition.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

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

public class FrmDivision extends FRMHandler implements I_FRMInterface, I_FRMType {
    private Division division;

    public static final String FRM_NAME_DIVISION = "FRM_NAME_DIVISION";

    public static final int FRM_FIELD_DIVISION_ID = 0;
    public static final int FRM_FIELD_DIVISION = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static final int FRM_FIELD_COMPANY_ID = 3;
    public static final int FRM_FIELD_DIVISION_TYPE_ID = 4;
    public static final int FRM_FIELD_ADDRESS = 5;
    public static final int FRM_FIELD_CITY = 6;
    public static final int FRM_FIELD_NPWP = 7;
    public static final int FRM_FIELD_PROVINCE = 8;
    public static final int FRM_FIELD_REGION = 9;
    public static final int FRM_FIELD_SUB_REGION = 10;
    public static final int FRM_FIELD_VILLAGE = 11;
    public static final int FRM_FIELD_AREA = 12;
    public static final int FRM_FIELD_TELEPHONE = 13;
    public static final int FRM_FIELD_FAX_NUMBER = 14;
    public static final int FRM_FIELD_VALID_STATUS = 15;
    public static final int FRM_FIELD_VALID_START = 16;
    public static final int FRM_FIELD_VALID_END = 17;
    public static final int FRM_FIELD_PEMOTONG = 18;
    /* Update by Hendra Putu | 2016-09-27 | emp yg menanda tangani slip gaji */
    public static final int FRM_FIELD_EMPLOYEE_ID = 19;
    /* add by Eri Yudi 2021-07-31 digunakan untuk mapping pada report kompetensi*/
    public static final int FRM_FIELD_LEVEL_DIVISION = 20;
    
    public static String[] fieldNames = {
        "FRM_FIELD_DIVISION_ID", 
        "FRM_FIELD_DIVISION",
        "FRM_FIELD_DESCRIPTION", 
        "FRM_FIELD_COMPANY_ID", 
        "FRM_FIELD_DIVISION_TYPE_ID",
        "FRM_FIELD_ADDRESS",
        "FRM_FIELD_CITY",
        "FRM_FIELD_NPWP",
        "FRM_FIELD_PROVINCE",
        "FRM_FIELD_REGION",
        "FRM_FIELD_SUB_REGION",
        "FRM_FIELD_VILLAGE",
        "FRM_FIELD_AREA",
        "FRM_FIELD_TELEPHONE",
        "FRM_FIELD_FAX_NUMBER",
        "FRM_FIELD_VALID_STATUS",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END",
        "FRM_FIELD_PEMOTONG",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_LEVEL_DIVISION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_LONG, 
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
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT
    };

    public FrmDivision() {
    }

    public FrmDivision(Division division) {
        this.division = division;
    }

    public FrmDivision(HttpServletRequest request, Division division) {
        super(new FrmDivision(division), request);
        this.division = division;
    }

    public String getFormName() {
        return FRM_NAME_DIVISION;
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

    public Division getEntityObject() {
        return division;
    }

    public void requestEntityObject(Division division) {
        try {
            this.requestParam();
            division.setDivision(getString(FRM_FIELD_DIVISION));
            division.setDescription(getString(FRM_FIELD_DESCRIPTION));
            division.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            division.setDivisionTypeId(getLong(FRM_FIELD_DIVISION_TYPE_ID));
            division.setAddress(getString(FRM_FIELD_ADDRESS));
            division.setCity(getString(FRM_FIELD_CITY));
            division.setNpwp(getString(FRM_FIELD_NPWP));
            division.setProvince(getString(FRM_FIELD_PROVINCE));
            division.setRegion(getString(FRM_FIELD_REGION));
            division.setSubRegion(getString(FRM_FIELD_SUB_REGION));
            division.setVillage(getString(FRM_FIELD_VILLAGE));
            division.setArea(getString(FRM_FIELD_AREA));
            division.setTelphone(getString(FRM_FIELD_TELEPHONE));
            division.setFaxNumber(getString(FRM_FIELD_FAX_NUMBER));
            division.setValidStatus(getInt(FRM_FIELD_VALID_STATUS));
            division.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            division.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
            division.setPemotong(getString(FRM_FIELD_PEMOTONG));
            division.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            division.setLevelDivision(getInt(FRM_FIELD_LEVEL_DIVISION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}