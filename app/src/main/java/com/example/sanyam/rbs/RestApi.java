package com.example.sanyam.rbs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sanyam on 14/5/16.
 */
public interface RestApi {
    @POST("/")
    Call<HttpBinResponse> setInfo(@Query("user") String user, @Body ArrayList<InfoInt> allInfoArray);
}
