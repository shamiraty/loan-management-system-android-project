package com.info.loanmis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.allstudents> {
    Context context;
    List<ModelDashboard> modelDashboardList;
    public Adapter(Context context, List<ModelDashboard> modelDashboardList) {
        this.context = context;
        this.modelDashboardList = modelDashboardList;
    }
    @NonNull
    @Override
    public allstudents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dashboard, parent, false);
        allstudents allstudents = new allstudents(view);
        return allstudents;
    }
    //then Bind your content with Model class
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull allstudents holder, int position) {
        ModelDashboard model = modelDashboardList.get(position);
        holder.status.setText(model.getStatus());
        holder.total_loan_cost.setText(model.getTotalLoanCost() + " TZS");
        holder.actual_debt.setText(model.getActualDebt() + " TZS");
        holder.remain_amount.setText(model.getRemainAmount() + " TZS");
        holder.requested_amount.setText(model.getRequestedLoan() + " TZS");
        holder.loan_tax.setText(model.getLoanTax() + " %");
        holder.direct_cost.setText(model.getDirectCost() + " %");
        holder.taken_amount.setText(model.getTakenAmount() + " TZS");

        if (model.getStatus().equals("Complited")) {
            holder.status_image.setImageResource(R.drawable.complited);
            holder.status.setTextColor(Color.parseColor("#196060"));
        }
    }

    @Override
    public int getItemCount() {
        return modelDashboardList.size();
    }

    public static class allstudents extends RecyclerView.ViewHolder {
        TextView status, total_loan_cost, actual_debt, remain_amount, requested_amount, loan_tax, direct_cost, taken_amount;
        ImageView status_image;
        public allstudents(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.dashboard_status);
            total_loan_cost = itemView.findViewById(R.id.total_loan_cost);
            actual_debt = itemView.findViewById(R.id.actual_debt);
            remain_amount = itemView.findViewById(R.id.remain_amount);
            requested_amount = itemView.findViewById(R.id.requested_amount);
            loan_tax = itemView.findViewById(R.id.loan_tax);
            direct_cost = itemView.findViewById(R.id.direct_cost);
            taken_amount = itemView.findViewById(R.id.taken_amount);
            status_image = itemView.findViewById(R.id.status_img);
        }
    }
}