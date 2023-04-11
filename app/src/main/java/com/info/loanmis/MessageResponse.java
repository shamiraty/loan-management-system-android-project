package com.info.loanmis;

import com.google.gson.annotations.SerializedName;

//this class receives API message responses
public class MessageResponse {
    @SerializedName("status")
    String status;
    @SerializedName("resultCode")
    int resultCode;
    public String getStatus() {
        return status;
    }
    public int getResultCode() {
        return resultCode;
    }


}
