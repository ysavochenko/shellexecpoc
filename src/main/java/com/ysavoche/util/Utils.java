package com.ysavoche.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class Utils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

    public static String buildShellCommand(String commandToExecute) {
        return new StringJoiner("\n", "", "\nexit\n").add(commandToExecute).toString();
    }

    public static String buildPythonExecutionCommand(String pythonScriptPath) {
        return new StringJoiner("\n", "python ", "\nexit\n").add(pythonScriptPath).toString();
    }

    public static String buildLogCaptureOutputFileName(String fileLocation, String testName, String logCaptureName) {
        String nameTimePart = LocalDateTime.now().format(formatter);
        String outPutFileName = new StringBuilder()
                .append(fileLocation)
                .append(File.separator)
                .append(testName)
                .append(logCaptureName)
                .append(nameTimePart)
                .append(".csv").toString();

        return outPutFileName;
    }

}
