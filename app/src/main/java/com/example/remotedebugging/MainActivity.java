package com.example.remotedebugging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.remotedebugger.RemoteDebugger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private RemoteDebugger remoteDebugger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remoteDebugger = new RemoteDebugger();
        remoteDebugger.connect("suresh");

        findViewById(R.id.helloButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RD", "Button pressed");
                remoteDebugger.sayHello();
            }
        });

        String path = android.os.Environment.getExternalStorageDirectory().toString();

        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
    }
}
