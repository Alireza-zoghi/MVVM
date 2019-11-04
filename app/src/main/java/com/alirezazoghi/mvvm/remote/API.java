package com.alirezazoghi.mvvm.remote;

import com.alirezazoghi.mvvm.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {


    @GET("getNote.php")
    Call<List<Note>> geCategory();

    @GET("getNote.php")
    Call<List<Note>> geCategory(@Query("key") String key);

}
