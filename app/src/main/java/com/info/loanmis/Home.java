package com.info.loanmis;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends Fragment {
    String SHARED_PREF_XML = "user_details_xml";
    private List<ModelDashboard> list;
    private Adapter adapter;
    private RecyclerView recyclerView;
    LinearLayout errorLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        list = new ArrayList<>();
        adapter = new Adapter(getContext(), list);

//then format the recycle view
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

//but also show progress, user to be patient while loading data from server
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading data...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

//then receive data from php file, then response can be success or failure
        final StringRequest request = new StringRequest(Request.Method.POST, "https://projectforce.000webhostapp.com/loan/Dashboard.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

//make sure database exceptional are handled
                try {
                    JSONArray array = new JSONArray(response);

                    //now loop your incoming data

                    for (int loop = 0; loop < array.length(); loop++) {
                        JSONObject object = array.getJSONObject(loop);
                        list.add(new ModelDashboard(object.getString("RequestedLoan"),
                                object.getString("LoanTax"),
                                object.getString("DirectCost"),
                                object.getString("TotalLoanCost"),
                                object.getString("TakenAmount"),
                                object.getString("ActualDebt"),
                                object.getString("created_at"),
                                object.getString("RemainAmount"),
                                object.getString("Status")));
                    }
                    adapter.notifyDataSetChanged();
                }
//whatever Error happens, then throw here in catch block

                catch (Exception e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Error: " + e.getMessage());
                    alertDialog.show();
                }
            }
            //if response is failure give user the message
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast toast= Toast.makeText(getContext(), "Internet Connection problem", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 10, 10);
                toast.show();
            }
        })
                //send current Login user to API to fetch data for a specific Login user
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
                String username = sharedPreferences.getString("NationalID", null);
                params.put("NationalID", username);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
        return view;
    }
}