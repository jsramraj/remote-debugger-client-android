package com.example.remotedebugger;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileUploader {

    RemoteDebuggerService service;

    public FileUploader() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConfig.SERVER_URL + ":" + AppConfig.SERVER_PORT)
                .build();

        service = retrofit.create(RemoteDebuggerService.class);
    }

    public void uploadFile(File file, ServiceCallback callback) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part  = MultipartBody.Part.createFormData("file", file.getName(), fileBody);


        service.uploadFile(part).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("RD", response.toString());
                try {
                    callback.onSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onSuccess(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("RD", t.toString());
                callback.onFailure("");
            }
        });

    }
}

