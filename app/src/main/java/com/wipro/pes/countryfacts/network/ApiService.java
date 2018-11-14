package com.wipro.pes.countryfacts.network;

import com.wipro.pes.countryfacts.model.Facts;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    Call<Facts> getFacts();

}
