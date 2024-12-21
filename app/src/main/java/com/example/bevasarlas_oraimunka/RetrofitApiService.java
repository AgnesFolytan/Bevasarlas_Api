package com.example.bevasarlas_oraimunka;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface RetrofitApiService {
    @GET("DnQYTi/bevasarlo")
    Call<List<Termekek>> getAllTermek();

    @GET("DnQYTi/bevasarlo/{id}")
    Call <Termekek> getTermekById(@Path("id") int id);

    @POST("DnQYTi/bevasarlo")
    Call<Termekek> createTermek(@Body Termekek termek);

    @PUT("DnQYTi/bevasarlo/{id}")
    Call<Termekek> updateTermek(@Path("id") int id, @Body Termekek termek);

    @DELETE("DnQYTi/bevasarlo/{id}")
    Call<Void> deleteTermek(@Path("id") int id);
}
