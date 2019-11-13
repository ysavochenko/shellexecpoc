package com.ysavoche.logger;

import com.ysavoche.shell.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ysavoche.util.Utils.buildPythonExecutionCommand;

@Component
class LogCaptureMemoryUsageBuilder {

    @Value("${monitoringScript2.location}")
    private String monitoringScript;

    @Autowired
    Executor shellExecutor;

    LogCapture build() {
        LogCapture logCaptureMemoryUsage = new LogCaptureImpl(shellExecutor);
        logCaptureMemoryUsage.setLoggerCommand(buildPythonExecutionCommand(monitoringScript));
        logCaptureMemoryUsage.setOutputStrategy((line) -> {
            Pattern pattern = Pattern.compile("'Used:', (.*?), 'MB'");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
                System.out.println("PARSED_MEMORY_MB_USED_IS: " + matcher.group(1));
        });

        return logCaptureMemoryUsage;
    }
}
