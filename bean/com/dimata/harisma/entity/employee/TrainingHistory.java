
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

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class TrainingHistory extends Entity {

    private long employeeId = 0;
    private long departementId =0;
    private String department = "";
    private String trainingProgram = "";
    private Date startDate = new Date();
    private Date endDate = new Date();
    private String trainer = "";
    private String remark = "";
    private long trainingId = 0;
    private int duration = 0;
    private int presence = 0;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private long trainingActivityPlanId = 0;
    private long trainingActivityActualId = 0;
    private double point = 0;
    private String nomorSk = "";
    private Date tanggalSk = new Date();
    private long empDocId = 0;
    /* Add by Hendra Putu | 2015-12-13 */
    private String trainingTitle = "";
    
    // add By Eri Yudi 2022-05-12
    private double preTest = 0;
    private double postTest = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(String trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getTrainingActivityPlanId() {
        return trainingActivityPlanId;
    }

    public void setTrainingActivityPlanId(long trainingActivityPlanId) {
        this.trainingActivityPlanId = trainingActivityPlanId;
    }

    public long getTrainingActivityActualId() {
        return trainingActivityActualId;
    }

    public void setTrainingActivityActualId(long trainingActivityActualId) {
        this.trainingActivityActualId = trainingActivityActualId;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getNomorSk() {
        return nomorSk;
    }

    public void setNomorSk(String nomorSk) {
        this.nomorSk = nomorSk;
    }

    public Date getTanggalSk() {
        return tanggalSk;
    }

    public void setTanggalSk(Date tanggalSk) {
        this.tanggalSk = tanggalSk;
    }

    public long getEmpDocId() {
        return empDocId;
    }

    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }

    /**
     * @return the trainingTitle
     */
    public String getTrainingTitle() {
        return trainingTitle;
    }

    /**
     * @param trainingTitle the trainingTitle to set
     */
    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    /**
     * @return the departementId
     */
    public long getDepartementId() {
        return departementId;
    }

    /**
     * @param departementId the departementId to set
     */
    public void setDepartementId(long departementId) {
        this.departementId = departementId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
      /**
     * @return the preTest
     */
    public double getPreTest() {
        return preTest;
    }

    /**
     * @param preTest the preTest to set
     */
    public void setPreTest(double preTest) {
        this.preTest = preTest;
    }

    /**
     * @return the postTest
     */
    public double getPostTest() {
        return postTest;
    }

    /**
     * @param postTest the postTest to set
     */
    public void setPostTest(double postTest) {
        this.postTest = postTest;
    }

}