/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class EmpDocListMutation extends Entity {

    private long empDocListMutationId = 0;
    private long employeeId = 0;
    private long empDocId = 0;
    private String objectName = "";
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long positionId = 0;
    private long empCatId = 0;
    private long levelId = 0;
    private Date workFrom;
    private long gradeLevelId = 0;
    private int historyType = 0;
    private int historyGroup = 0;
    private Date workTo;
    private int tipeDoc = 0;
    private int resignReason = 0;
    private String resignDesc = "";
    private String empNum = "";
    private String payComponent = "";

    /**
     * @return the empDocListMutationId
     */
    public long getEmpDocListMutationId() {
        return empDocListMutationId;
    }

    /**
     * @param empDocListMutationId the empDocListMutationId to set
     */
    public void setEmpDocListMutationId(long empDocListMutationId) {
        this.empDocListMutationId = empDocListMutationId;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the empDocId
     */
    public long getEmpDocId() {
        return empDocId;
    }

    /**
     * @param empDocId the empDocId to set
     */
    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the departmentId
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the sectionId
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the empCatId
     */
    public long getEmpCatId() {
        return empCatId;
    }

    /**
     * @param empCatId the empCatId to set
     */
    public void setEmpCatId(long empCatId) {
        this.empCatId = empCatId;
    }

    /**
     * @return the levelId
     */
    public long getLevelId() {
        return levelId;
    }

    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the workFrom
     */
    public Date getWorkFrom() {
        return workFrom;
    }

    /**
     * @param workFrom the workFrom to set
     */
    public void setWorkFrom(Date workFrom) {
        this.workFrom = workFrom;
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
     * @return the historyType
     */
    public int getHistoryType() {
        return historyType;
    }

    /**
     * @param historyType the historyType to set
     */
    public void setHistoryType(int historyType) {
        this.historyType = historyType;
    }

    /**
     * @return the historyGroup
     */
    public int getHistoryGroup() {
        return historyGroup;
    }

    /**
     * @param historyGroup the historyGroup to set
     */
    public void setHistoryGroup(int historyGroup) {
        this.historyGroup = historyGroup;
    }

    /**
     * @return the workTo
     */
    public Date getWorkTo() {
        return workTo;
    }

    /**
     * @param workTo the workTo to set
     */
    public void setWorkTo(Date workTo) {
        this.workTo = workTo;
    }

    /**
     * @return the tipeDoc
     */
    public int getTipeDoc() {
        return tipeDoc;
    }

    /**
     * @param tipeDoc the tipeDoc to set
     */
    public void setTipeDoc(int tipeDoc) {
        this.tipeDoc = tipeDoc;
    }

    /**
     * @return the resignReason
     */
    public int getResignReason() {
        return resignReason;
    }

    /**
     * @param resignReason the resignReason to set
     */
    public void setResignReason(int resignReason) {
        this.resignReason = resignReason;
    }

    /**
     * @return the resignDesc
     */
    public String getResignDesc() {
        return resignDesc;
    }

    /**
     * @param resignDesc the resignDesc to set
     */
    public void setResignDesc(String resignDesc) {
        this.resignDesc = resignDesc;
    }

    /**
     * @return the empNum
     */
    public String getEmpNum() {
        return empNum;
    }

    /**
     * @param empNum the empNum to set
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    /**
     * @return the payComponent
     */
    public String getPayComponent() {
        return payComponent;
    }

    /**
     * @param payComponent the payComponent to set
     */
    public void setPayComponent(String payComponent) {
        this.payComponent = payComponent;
    }
}
