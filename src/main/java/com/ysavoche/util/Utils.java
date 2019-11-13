package com.ysavoche.util;

import java.util.StringJoiner;

public class Utils {

    public static String buildShellCommand(String commandToExecute) {
        return new StringJoiner("\n", "", "\nexit\n").add(commandToExecute).toString();
    }

    public static String buildPythonExecutionCommand(String pythonScriptPath) {
        return new StringJoiner("\n", "python ", "\nexit\n").add(pythonScriptPath).toString();
    }

}
