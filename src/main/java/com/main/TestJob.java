package com.main;

public class TestJob {
  private String activitySector;
  private String requiredExperience;
  private String studyLevel;
  private String contractType;
  private String remoteWork;
  private String city;
  private String function;
  private Double Salary;
  
  

  public String getActivitySector() {
    return this.activitySector;
  }

  public void setActivitySector(String activitySector) {
    this.activitySector = activitySector;
  }

  public String getRequiredExperience() {
    return this.requiredExperience;
  }

  public void setRequiredExperience(String requiredExperience) {
    this.requiredExperience = requiredExperience;
  }

  public String getStudyLevel() {
    return this.studyLevel;
  }

  public void setStudyLevel(String studyLevel) {
    this.studyLevel = studyLevel;
  }

  public String getContractType() {
    return this.contractType;
  }

  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  public String getRemoteWork() {
    return this.remoteWork;
  }

  public void setRemoteWork(String remoteWork) {
    this.remoteWork = remoteWork;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return """
           {
            city='""" + getCity() + "'" +
      ",\n activitySector='" + getActivitySector() + "'" +
      ",\n requiredExperience='" + getRequiredExperience() + "'" +
      ",\n studyLevel='" + getStudyLevel() + "'" +
      ",\n contractType='" + getContractType() + "'" +
      ",\n remoteWork='" + getRemoteWork() + "'\n" +
      "}";
  }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
