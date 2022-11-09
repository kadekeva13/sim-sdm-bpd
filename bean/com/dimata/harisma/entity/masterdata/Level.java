
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Level extends Entity { 

    private long groupRankId ;
	private String level = "";
	private String description = "";
        // added for HR
        private long employeeMedicalId = 0;
        private long familyMedicalId = 0;
        private long gradeLevelId=0;
        private int levelPoint = 0; // added by Hendra McHen 2015-01-08
        private String code = "";
        /* update by Hendra McHen 2015-09-09 */
        private int levelRank = 0;
        private long maxLevelApproval = 0;
        private int hr_approval = 0;
        private int maxNumberApproval = 0;
		private int gradeMin = 0;
		private int gradeMax = 0;
    
    public long getGroupRankId(){
		return groupRankId;
	} 

	public void setGroupRankId(long groupRankId){
    	this.groupRankId = groupRankId;
    }

	public String getLevel(){ 
		return level; 
	} 

	public void setLevel(String level){ 
		if ( level == null ) {
			level = ""; 
		} 
		this.level = level; 
	} 

	public String getDescription(){ 
		return description; 
	} 

	public void setDescription(String description){ 
		if ( description == null ) {
			description = ""; 
		} 
		this.description = description; 
	}

        // added by bayu
        
        public long getEmployeeMedicalId() {
            return employeeMedicalId;
        }

        public void setEmployeeMedicalId(long employeeMedicalId) {
            this.employeeMedicalId = employeeMedicalId;
        }

        public long getFamilyMedicalId() {
            return familyMedicalId;
        }

        public void setFamilyMedicalId(long familyMedicalId) {
            this.familyMedicalId = familyMedicalId;
        }

    /**
     * @return the gradeLevelId
     */
    public long getGradeLevelId() {
        return gradeLevelId;
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(long gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    /**
     * @return the levelPoint
     */
    public int getLevelPoint() {
        return levelPoint;
    }

    /**
     * @param levelPoint the levelPoint to set
     */
    public void setLevelPoint(int levelPoint) {
        this.levelPoint = levelPoint;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the levelRank
     */
    public int getLevelRank() {
        return levelRank;
    }

    /**
     * @param levelRank the levelRank to set
     */
    public void setLevelRank(int levelRank) {
        this.levelRank = levelRank;
    }

    /**
     * @return the maxLevelApproval
     */
    public long getMaxLevelApproval() {
        return maxLevelApproval;
    }

    /**
     * @param maxLevelApproval the maxLevelApproval to set
     */
    public void setMaxLevelApproval(long maxLevelApproval) {
        this.maxLevelApproval = maxLevelApproval;
    }

    /**
     * @return the hr_approval
     */
    public int getHr_approval() {
        return hr_approval;
    }

    /**
     * @param hr_approval the hr_approval to set
     */
    public void setHr_approval(int hr_approval) {
        this.hr_approval = hr_approval;
    }

    /**
     * @return the maxNumberApproval
     */
    public int getMaxNumberApproval() {
        return maxNumberApproval;
    }

    /**
     * @param maxNumberApproval the maxNumberApproval to set
     */
    public void setMaxNumberApproval(int maxNumberApproval) {
        this.maxNumberApproval = maxNumberApproval;
    }

	/**
	 * @return the gradeMin
	 */
	public int getGradeMin() {
		return gradeMin;
	}

	/**
	 * @param gradeMin the gradeMin to set
	 */
	public void setGradeMin(int gradeMin) {
		this.gradeMin = gradeMin;
	}

	/**
	 * @return the gradeMax
	 */
	public int getGradeMax() {
		return gradeMax;
	}

	/**
	 * @param gradeMax the gradeMax to set
	 */
	public void setGradeMax(int gradeMax) {
		this.gradeMax = gradeMax;
	}
        
        
}
