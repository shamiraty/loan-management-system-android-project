package com.info.loanmis;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Support extends Fragment {
//declare views
    String SHARED_PREF_XML = "user_details_xml";
    EditText heading;
    TextInputLayout message;
    Button send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        //initialize views
        heading = view.findViewById(R.id.heading);
        message = view.findViewById(R.id.message);
        send = view.findViewById(R.id.btn_support);

      //when heading is clicked prompt dialog
        heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //alert dialog to pick problem
                showAlertDialog();
            }
        });

        //when send button is clicked, then validate data
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call validation method
                validate();
            }
        });
        return view;
    }

    void validate() {
        String heading_ = heading.getText().toString();
        String message_ = message.getEditText().getText().toString();

        if (message_.equals("")) {
            message.setError("Required");
        } else if (heading_.equals("")) {
            heading.setError("required");
        } else {
            send.setText("please wait, sending...");
            send.setClickable(false);
            //call send message method
            sendMessage();
        }
    }

    //show alert dialog pick choice
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("select category");
        String[] items = {"Extend loan duration", "Incorrect installments", "Postpone loan duration", "Transfer the loan","Invalid Loan Computation","Loan Completion issues","other"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        heading.setText("Extend loan duration");
                        break;
                    case 1:
                        heading.setText("Incorrect installments");
                        break;
                    case 2:
                        heading.setText("Postpone loan duration");
                        break;
                    case 3:
                        heading.setText("Transfer the loan");
                        break;
                    case 4:
                        heading.setText("Invalid Loan Computation");
                        break;

                    case 5:
                        heading.setText("Loan Completion issues");
                        break;

                    case 6:
                        heading.setText("Other");
                        break;
                }
            }

        });

        // Setting Positive "Yes" Btn
        alertDialog.setPositiveButton("select",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getContext(), "selected", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    //send message method
    public void sendMessage()
    {
        //instantiate instance variables
        String heading_=heading.getText().toString();
        String message_=message.getEditText().getText().toString();

        //get current login customer
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
        String username=sharedPreferences.getString("NationalID",null);

        Call<MessageResponse>
                call=ServerConnection.getServerConnection().create(UserOperations.class).message_method(username,heading_,message_);

        //listen response from API  serverResponse

        call.enqueue(new Callback<MessageResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response)
            {
                //check server state
                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {
                        if(response.body().getResultCode()==1)
                        {
                                message.setError("The same problem has already been sent");
                                send.setClickable(true);
                                send.setText("retry");
                        }
                        else if(response.body().getResultCode()==0)
                        {
                            Toast.makeText(getContext(), "Successfully sent", Toast.LENGTH_LONG).show();
                            send.setClickable(true);
                            send.setText("done");
                        }

                    }

                }
                else
                {
                    heading.setError("server response timed out");
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}