package com.example.remotedebugger;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDebugger implements ServiceCallback {
    private Socket socket;
    private String username;
    private FileUploader fileUploader = new FileUploader();

    public void connect(String username) {
        this.username = username;
        try {
            socket = IO.socket(AppConfig.SERVER_URL + ":" + AppConfig.SOCKET_PORT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setupListeners();
        socket.connect();

    }

    private void setupListeners() {
        socket
                .on(Socket.EVENT_CONNECT, args -> {
                    Log.d("RD", "Connected");
                    socket.emit("username", this.username);
                })
                .on(Socket.EVENT_CONNECT_ERROR, args -> {
                    Log.d("RD", "Error");
                })
                .on(Socket.EVENT_CONNECT_TIMEOUT, args -> {
                    Log.d("RD", "Timeout");
                })
        .on("command", args -> {
            Log.d("RD", "Command received");
            handleCommand(args[0].toString());
        });
    }

    private void handleCommand(String commandArgs) {
        Command command = new Gson().fromJson(commandArgs, Command.class);
        switch (command.getType()) {
            case VIEW:
                Log.d("RD", "View files at " + command.getDestinationPath());

                File directory = new File(command.getDestinationPath());
                if (directory.isDirectory()) {
                    File[] files = directory.listFiles();
                    if (files != null) {
                        List<SimpleFile> simpleFiles = new ArrayList<>();
                        Log.d("RD", "Size: " + files.length);
                        for (int i = 0; i < files.length; i++) {
                            Log.d("RD", "FileName: " + files[i].getName()
                                    + " FileSize: " + files[i].length()
                                    + " Is Directory: " + files[i].isDirectory());
                            SimpleFile simpleFile = new SimpleFile();
                            simpleFile.setName(files[i].getName());
                            simpleFile.setPath(files[i].getPath());
                            simpleFile.setSize(files[i].length());
                            simpleFile.setDirectory(files[i].isDirectory());
                            simpleFiles.add(simpleFile);
                        }
                        socket.emit("view", new Gson().toJson(simpleFiles));
                    }
                } else {
                    fileUploader.uploadFile(directory, this);
                }

                break;
            default:
                Log.d("RD", "Not supported");
                break;
        }
    }

    public void sayHello() {
        Log.d("RD", "Hello World!!");
    }

    @Override
    public void onSuccess(String fileName) {
        socket.emit("status", "File uploaded successfully at path " + fileName);
    }

    @Override
    public void onFailure(String fileName) {
        socket.emit("status", "File upload failed");
    }
}
