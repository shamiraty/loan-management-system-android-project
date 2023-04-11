package com.info.loanmis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    //declare views
    TextInputLayout username, password;
    Button login;
    ProgressDialog progressDialog;

    //create/session shared preference
    String SHARED_PREF_XML = "user_details_xml";

    //date
   SwitchCompat switchCompat;
    public static String formated_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //date

        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formated_date=df.format(date);

        //define action bar behaviours
        getSupportActionBar().setTitle("LOAN  MANAGEMENT");
        getSupportActionBar().setSubtitle(formated_date);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //initialize views
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);

       //show help dialog when switch is clicked

        switchCompat=findViewById(R.id.switcher);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    //call dialog method
                   dialog();
                }
            }
        });

        //when login  button is clicked, validate data
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }
    //validation
    public void validate() {
        if (Objects.requireNonNull(username.getEditText()).getText().toString().trim().equals("")) {
            username.setError("Username is required");
            username.setHintEnabled(false);
        } else if (Objects.requireNonNull(password.getEditText()).getText().toString().trim().equals("")) {
            password.setError("Password is required");
            password.setHintEnabled(false);
        } else {
            //call login method
            progressDialog.setMessage("Authenticating...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            login_method();
        }
    }
    public void login_method()
    {
        String username2 = username.getEditText().getText().toString();
        String password2 = password.getEditText().getText().toString();

        Call<ServerResponse> call = ServerConnection.getServerConnection().create(UserOperations.class).login_method(username2, password2);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            //save user to shared pref
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // save the user
                            editor.putString("NationalID", response.body().getNationalID());
                            editor.putString("Fullname", response.body().getFullname());
                            editor.apply();

                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "welcome " + response.body().getFullname(), Toast.LENGTH_LONG).show();
                        } else {
                            //username or password is not correct
                            progressDialog.dismiss();
                            username.setError("invalid username");
                            password.setError("invalid password");
                            username.setHintEnabled(false);
                            password.setHintEnabled(false);
                            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        //status is not OK
                        Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    //no connection
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast toast= Toast.makeText(getApplicationContext(), "Internet Connection problem", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 10, 10);
                toast.show();
            }
        });
    }

    @Override
    //on activity start   check the user
    protected void onStart()
    {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
        String customerNumber = sharedPreferences.getString("NationalID", null);
        if (customerNumber != null)
        {
            //if the customer has already logged-in, then skip this activity.
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }
    }
    //show help dialog
void dialog()
{
    Dialog dialog=new Dialog(this);
    dialog.setContentView(R.layout.dialog);
    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(true);
    dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
    dialog.show();
}
}