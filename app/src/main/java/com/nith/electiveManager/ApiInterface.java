package com.nith.electiveManager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("get_electives")
    Call<String> getElectives();

    @GET("student_register")
    Call<String> doRegister(@Query("name") String name,
                            @Query("roll_no") String roll_no,
                            @Query("password") String password,
                            @Query("fName") String fName,
                            @Query("reg_no") String reg_no,
                            @Query("dob") String dob,
                            @Query("branch") String branch,
                            @Query("phone") String phone,
                            @Query("email") String email,
                            @Query("act") String act
    );

    @GET("student_login")
    Call<String> doLogin (@Query("roll_no") String rollNo, @Query("password") String password);

    @GET("submit_electives")
    Call<String> submitElectives (@Query("electives") String electives);
}
