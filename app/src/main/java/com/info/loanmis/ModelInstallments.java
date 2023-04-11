package com.info.loanmis;

public class ModelInstallments {
    String id, InstalledAmount, National_ID, created_at, EmployeeUsername;
    public ModelInstallments(String id, String installedAmount, String national_ID, String created_at, String employeeUsername) {
        this.id = id;
        this.InstalledAmount = installedAmount;
        this.National_ID = national_ID;
        this.created_at = created_at;
        this.EmployeeUsername = employeeUsername;
    }
    public String getId() {
        return id;
    }
    public String getInstalledAmount() {
        return InstalledAmount;
    }
    public String getNational_ID() {
        return National_ID;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getEmployeeUsername() {
        return EmployeeUsername;
    }
}
