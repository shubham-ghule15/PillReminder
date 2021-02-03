package demo.demo;

import java.sql.Date;
import java.sql.Time;

public class MedicalHistory {
  private String illness, DoctorDetails, medicine, DosageAmount, DosageFrequency, relation;
  private Date startDate, endDate;
  private Time DosageTime;
  private Boolean EmailNotification;

  // Constructor of the class
  public MedicalHistory(String illness, String DoctorDetails, String medicine, String DosageAmount,
      String DosageFrequency, Date startDate, Date endDate, Time DosageTime, Boolean EmailNotification) {
    this.illness = illness;
    this.DoctorDetails = DoctorDetails;
    this.medicine = medicine;
    this.DosageAmount = DosageAmount;
    this.DosageFrequency = DosageFrequency;
    this.startDate = startDate;
    this.endDate = endDate;
    this.DosageTime = DosageTime;
    this.EmailNotification = EmailNotification;
  }

  public String getRelation() {
    return this.relation;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  // Getters and Setters for all data members
  public String getIllness() {
    return this.illness;
  }

  public void setIllness(String illness) {
    this.illness = illness;
  }

  public String getDoctorDetails() {
    return this.DoctorDetails;
  }

  public void setDoctorDetails(String DoctorDetails) {
    this.DoctorDetails = DoctorDetails;
  }

  public String getMedicine() {
    return this.medicine;
  }

  public void setMedicine(String medicine) {
    this.medicine = medicine;
  }

  public String getDosageAmount() {
    return this.DosageAmount;
  }

  public void setDosageAmount(String DosageAmount) {
    this.DosageAmount = DosageAmount;
  }

  public String getDosageFrequency() {
    return this.DosageFrequency;
  }

  public void setDosageFrequency(String DosageFrequency) {
    this.DosageFrequency = DosageFrequency;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Time getDosageTime() {
    return this.DosageTime;
  }

  public void setDosageTime(Time DosageTime) {
    this.DosageTime = DosageTime;
  }

  public Boolean isEmailNotification() {
    return this.EmailNotification;
  }

  public Boolean getEmailNotification() {
    return this.EmailNotification;
  }

  public void setEmailNotification(Boolean EmailNotification) {
    this.EmailNotification = EmailNotification;
  }

}
