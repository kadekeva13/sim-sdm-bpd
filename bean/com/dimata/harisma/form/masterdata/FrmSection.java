/* 
 * Form Name  	:  FrmSection.java 
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

public class FrmSection extends FRMHandler implements I_FRMInterface, I_FRMType {

	private Section section;
    public static final String FRM_NAME_SECTION = "FRM_NAME_SECTION";
    public static final int FRM_FIELD_SECTION_ID = 0;
    public static final int FRM_FIELD_DEPARTMENT_ID = 1;
    public static final int FRM_FIELD_SECTION = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    public static final int FRM_FIELD_SECTION_LINK_TO = 4;
    public static final int FRM_FIELD_VALID_STATUS = 5;
    public static final int FRM_FIELD_VALID_START = 6;
    public static final int FRM_FIELD_VALID_END = 7;
    /*add by Eri Yudi 2021-07-31 untuk mapping kompetensi berdasarkan kelas sub unit*/
    public static final int FRM_FIELD_LEVEL_SECTION = 8;
    
	public static String[] fieldNames = {
        "FRM_FIELD_SECTION_ID", "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_SECTION_LINK_TO",
        "FRM_FIELD_VALID_STATUS",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END",
        "FRM_FIELD_LEVEL_SECTION"
    };
	public static int[] fieldTypes = {
		TYPE_LONG, TYPE_LONG + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED, TYPE_STRING, TYPE_STRING, TYPE_INT, TYPE_STRING, TYPE_STRING,TYPE_INT
    };

    public FrmSection() {
	}

    public FrmSection(Section section) {
		this.section = section;
	}

    public FrmSection(HttpServletRequest request, Section section) {
		super(new FrmSection(section), request);
		this.section = section;
	}

    public String getFormName() {
        return FRM_NAME_SECTION;
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

    public Section getEntityObject() {
        return section;
    }

	public void requestEntityObject(Section section) {
        try {
			this.requestParam();
                        section.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
			section.setSection(getString(FRM_FIELD_SECTION));
			section.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        section.setSectionLinkTo(getString(FRM_FIELD_SECTION_LINK_TO));
                        section.setValidStatus(getInt(FRM_FIELD_VALID_STATUS));
                        section.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
                        section.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
                        section.setLevelSection(getInt(FRM_FIELD_LEVEL_SECTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
		}
	}
}
