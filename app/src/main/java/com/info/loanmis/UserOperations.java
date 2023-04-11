package com.info.loanmis;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserOperations
{
    //login method
    @FormUrlEncoded
    @POST("login.php")
    Call<ServerResponse>login_method(@Field("username")String username,@Field("password")String password);

    //send message method
    @FormUrlEncoded
    @POST("message.php")
    Call<MessageResponse>message_method(@Field("username")String username,@Field("heading")String heading,@Field("message")String message);



}
