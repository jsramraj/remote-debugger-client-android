package com.example.remotedebugger;

import com.google.gson.annotations.SerializedName;

public class Command {
//    private String command;

    @SerializedName("command")
    private CommandType type;

    @SerializedName("source")
    private String sourcePath;

    @SerializedName("destination")
    private String destinationPath;

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

//    public String getCommand() {
//        return command;
//    }
//
//    public void setCommand(String command) {
//        this.command = command;
//    }
}
