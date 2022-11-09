
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.employee; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class TrainingActivityPlan extends Entity { 

	private long departmentId;
	private Date date;
	private String program = "";
	private String trainer = "";
	private int programsPlan;
	private int totHoursPlan;
	private int traineesPlan;
	private String remark = "";
        private long organizerID =0;
        /* Added by Hendra Putu | 2015-12-12 */
        private String trainingTitle = "";
        private long positionId = 0;
        private String kodeOjk = "";
       
    private long trainingId;

	public long getDepartmentId(){ 
		return departmentId; 
	} 

	public void setDepartmentId(long departmentId){ 
		this.departmentId = departmentId; 
	} 

	public Date getDate(){ 
		return date; 
	} 

	public void setDate(Date date){ 
		this.date = date; 
	} 

	public String getProgram(){ 
		return program; 
	} 

	public void setProgram(String program){ 
		if ( program == null ) {
			program = ""; 
		} 
		this.program = program; 
	} 

	public String getTrainer(){ 
		return trainer; 
	} 

	public void setTrainer(String trainer){ 
		if ( trainer == null ) {
			trainer = ""; 
		} 
		this.trainer = trainer; 
	} 

	public int getProgramsPlan(){ 
		return programsPlan; 
	} 

	public void setProgramsPlan(int programsPlan){ 
		this.programsPlan = programsPlan; 
	} 

	public int getTotHoursPlan(){ 
		return totHoursPlan; 
	} 

	public void setTotHoursPlan(int totHoursPlan){ 
		this.totHoursPlan = totHoursPlan; 
	} 

	public int getTraineesPlan(){ 
		return traineesPlan; 
	} 

	public void setTraineesPlan(int traineesPlan){ 
		this.traineesPlan = traineesPlan; 
	} 

	public String getRemark(){ 
		return remark; 
	} 

	public void setRemark(String remark){ 
		this.remark = remark; 
	} 

    public long getTrainingId(){ return trainingId; }

    public void setTrainingId(long trainingId){ this.trainingId = trainingId; }

    /**
     * @return the organizerID
     */
    public long getOrganizerID() {
        return organizerID;
    }

    /**
     * @param organizerID the organizerID to set
     */
    public void setOrganizerID(long organizerID) {
        this.organizerID = organizerID;
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
     * @return the kodeOjk
     */
    public String getKodeOjk() {
        return kodeOjk;
    }
    
    /**
     * @param kodeOjk the kodeOjk to set
     */
    public void setKodeOjk(String kodeOjk) {
        this.kodeOjk = kodeOjk;
    }

    
}
