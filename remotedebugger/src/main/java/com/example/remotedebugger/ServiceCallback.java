package com.example.remotedebugger;

public interface ServiceCallback {
    void onSuccess(String fileName);
    void onFailure(String fileName);
}
