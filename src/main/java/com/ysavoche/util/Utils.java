package com.ysavoche.util;

import java.io.File;
import java.time.LocalDate;
import java.util.StringJoiner;

public class Utils {

    public static String buildShellCommand(String commandToExecute) {
        return new StringJoiner("\n", "", "\nexit\n").add(commandToExecute).toString();
    }

    public static String buildPythonExecutionCommand(String pythonScriptPath) {
        return new StringJoiner("\n", "python ", "\nexit\n").add(pythonScriptPath).toString();
    }

    public static String buildLogCaptureOutputFileName(String fileLocation, String testName, String logCaptureName) {
        String outPutFileName = new StringBuilder()
                .append(fileLocation)
                .append(File.separator)
                .append(testName)
                .append(logCaptureName)
                .append(LocalDate.now())
                .append(".csv").toString();

        return outPutFileName;
    }

}
