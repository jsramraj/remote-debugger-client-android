package com.example.remotedebugger;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RemoteDebuggerService {
    @Multipart
    @POST("fileupload")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);
}
