package com.info.loanmis;

public class ModelDashboard {
    String RequestedLoan, LoanTax, DirectCost, TotalLoanCost, TakenAmount, ActualDebt, created_at, RemainAmount, Status;
    public ModelDashboard(String requestedLoan, String loanTax, String directCost, String totalLoanCost, String takenAmount, String actualDebt, String created_at, String remainAmount, String status) {
        this.RequestedLoan = requestedLoan;
        this.LoanTax = loanTax;
        this.DirectCost = directCost;
        this.TotalLoanCost = totalLoanCost;
        this.TakenAmount = takenAmount;
        this.ActualDebt = actualDebt;
        this.created_at = created_at;
        this.RemainAmount = remainAmount;
        this.Status = status;
    }
    public String getRequestedLoan() {
        return RequestedLoan;
    }
    public String getLoanTax() {
        return LoanTax;
    }
    public String getDirectCost() {
        return DirectCost;
    }
    public String getTotalLoanCost() {
        return TotalLoanCost;
    }
    public String getTakenAmount() {
        return TakenAmount;
    }
    public String getActualDebt() {
        return ActualDebt;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getRemainAmount() {
        return RemainAmount;
    }
    public String getStatus() {
        return Status;
    }
}
