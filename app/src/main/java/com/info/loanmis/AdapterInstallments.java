package com.info.loanmis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterInstallments extends RecyclerView.Adapter<AdapterInstallments.allstudents> {
    Context context;
    List<ModelInstallments> modelInstallmentsList;
    public AdapterInstallments(Context context, List<ModelInstallments> modelInstallmentsList) {
        this.context = context;
        this.modelInstallmentsList = modelInstallmentsList;
    }
    @NonNull
    @Override
    public allstudents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_installments, parent, false);
        allstudents allstudents = new allstudents(view);
        return allstudents;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull allstudents holder, int position) {
        ModelInstallments model = modelInstallmentsList.get(position);
        holder.id.setText("Transaction no: " + model.getId());
        holder.InstalledAmount.setText("Installed Amount: " + model.getInstalledAmount());
        holder.National_ID.setText("my ID: " + model.getNational_ID());
        holder.created_at.setText("Date: " + model.getCreated_at());
        holder.EmployeeUsername.setText("Employee: " + model.getEmployeeUsername());
    }
    @Override
    public int getItemCount() {
        return modelInstallmentsList.size();
    }
    public static class allstudents extends RecyclerView.ViewHolder {
        TextView id, InstalledAmount, National_ID, created_at, EmployeeUsername;
        public allstudents(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.transaction_id);
            InstalledAmount = itemView.findViewById(R.id.installed_amount_);
            National_ID = itemView.findViewById(R.id.national_id);
            created_at = itemView.findViewById(R.id.created_date_);
            EmployeeUsername = itemView.findViewById(R.id.employee_id);
        }
    }
}