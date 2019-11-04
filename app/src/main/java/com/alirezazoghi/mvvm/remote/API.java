package com.alirezazoghi.mvvm.remote;

import com.alirezazoghi.mvvm.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {


    @GET("comments")
    Call<List<Note>> getNotes();

}
