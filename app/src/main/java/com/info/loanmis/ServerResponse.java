package com.info.loanmis;

import com.google.gson.annotations.SerializedName;

//this class receives API  responses

public class ServerResponse {
    @SerializedName("status")
    String status;

    @SerializedName("resultCode")
    int resultCode;

    @SerializedName("NationalID")
    String NationalID;

    @SerializedName("Fullname")
    String Fullname;

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getNationalID() {
        return NationalID;
    }

    public String getFullname() {
        return Fullname;
    }
}
