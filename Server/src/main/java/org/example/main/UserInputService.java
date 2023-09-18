package org.example.main;

import java.io.BufferedReader;
import java.io.File;

public class UserInputService {
    private static Mode mode;
    private static File scriptFile;

    public Mode getMode() {
        return mode;
    }
    public File getScriptFile() {
        return scriptFile;
    }

    public void setMode(Mode mode) {
        UserInputService.mode = mode;
    }
    public void setScriptFile(File scriptFile) {
        UserInputService.scriptFile = scriptFile;
    }
}
