/* 
 * Form Name  	:  FrmFamilyMember.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmFamilyMember extends FRMHandler implements I_FRMInterface, I_FRMType {

    private FamilyMember familyMember;
    public static final String FRM_NAME_FAMILYMEMBER = "FRM_NAME_FAMILYMEMBER";
    public static final int FRM_FIELD_FAMILY_MEMBER_ID = 0;
    public static final int FRM_FIELD_FULL_NAME = 1;
    public static final int FRM_FIELD_RELATIONSHIP = 2;
    public static final int FRM_FIELD_BIRTH_DATE = 3;
    public static final int FRM_FIELD_JOB = 4;
    public static final int FRM_FIELD_ADDRESS = 5;
    public static final int FRM_FIELD_GUARANTEED = 6;
    public static final int FRM_FIELD_IGNORE_BIRTH = 7;
    public static final int FRM_FIELD_EDUCATION_ID = 8;
    public static final int FRM_FIELD_RELIGION_ID = 9;
    public static final int FRM_FIELD_SEX = 10;
    /* Update by Hendra McHen | 2016-03-27 */
    public static final int FRM_FIELD_CARD_ID = 11;
    public static final int FRM_FIELD_NO_TELP = 12;
    public static final int FRM_FIELD_BPJS_NUM = 13;
    public static final int FRM_FIELD_JOB_PLACE = 14;
    public static final int FRM_FIELD_JOB_POSITION = 15;
    public static final int FRM_FIELD_BIRTH_PLACE = 16;
    public static String[] fieldNames = {
        "FRM_FIELD_FAMILY_MEMBER_ID", "FRM_FIELD_FULL_NAME",
        "FRM_FIELD_RELATIONSHIP", "FRM_FIELD_BIRTH_DATE",
        "FRM_FIELD_JOB", "FRM_FIELD_ADDRESS", "FRM_FIELD_GUARANTEED",
        "FRM_FIELD_IGNORE_BIRTH", "FRM_FIELD_EDUCATION_ID", "FRM_FIELD_RELIGION_ID",
        "FRM_FIELD_SEX", "FRM_FIELD_CARD_ID", "FRM_FIELD_NO_TELP",
        "FRM_FIELD_BPJS_NUM", "FRM_FIELD_JOB_PLACE", "FRM_FIELD_JOB_POSITION",
        "FRM_FIELD_BIRTH_PLACE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED, TYPE_DATE,
        TYPE_STRING, TYPE_STRING, TYPE_BOOL,
        TYPE_BOOL, TYPE_LONG, TYPE_LONG, TYPE_INT, TYPE_STRING,
        TYPE_LONG, TYPE_STRING, TYPE_STRING, TYPE_STRING,
        TYPE_STRING
    };

    public FrmFamilyMember() {
    }

    public FrmFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public FrmFamilyMember(HttpServletRequest request, FamilyMember familyMember) {
        super(new FrmFamilyMember(familyMember), request);
        this.familyMember = familyMember;
    }

    public String getFormName() {
        return FRM_NAME_FAMILYMEMBER;
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

    public FamilyMember getEntityObject() {
        return familyMember;
    }

    public void requestEntityObject(FamilyMember familyMember) {
        try {
            this.requestParam();
            familyMember.setFullName(getString(FRM_FIELD_FULL_NAME));
            familyMember.setRelationship(getString(FRM_FIELD_RELATIONSHIP));
            familyMember.setBirthDate(getDate(FRM_FIELD_BIRTH_DATE));
            familyMember.setJob(getString(FRM_FIELD_JOB));
            familyMember.setAddress(getString(FRM_FIELD_ADDRESS));
            familyMember.setGuaranteed(getBoolean(FRM_FIELD_GUARANTEED));
            familyMember.setIgnoreBirth(getBoolean(FRM_FIELD_IGNORE_BIRTH));
            familyMember.setEducationId(getLong(FRM_FIELD_EDUCATION_ID));
            familyMember.setReligionId(getLong(FRM_FIELD_RELIGION_ID));
            familyMember.setSex(getInt(FRM_FIELD_SEX));
            familyMember.setCardId(getString(FRM_FIELD_CARD_ID));
            familyMember.setNoTelp(getLong(FRM_FIELD_NO_TELP));
            familyMember.setBpjsNum(getString(FRM_FIELD_BPJS_NUM));
            familyMember.setJobPlace(getString(FRM_FIELD_JOB_PLACE));
            familyMember.setJobPosition(getString(FRM_FIELD_JOB_POSITION));
            familyMember.setBirtPlace(getString(FRM_FIELD_BIRTH_PLACE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
