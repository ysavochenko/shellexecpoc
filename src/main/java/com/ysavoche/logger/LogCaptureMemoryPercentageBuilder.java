package com.ysavoche.logger;

import com.ysavoche.shell.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ysavoche.util.Utils.buildPythonExecutionCommand;

@Component
class LogCaptureMemoryPercentageBuilder {

    @Value("${monitoringScript1.location}")
    private String monitoringScript;

    @Autowired
    Executor shellExecutor;

    LogCapture build() {
        LogCapture logCaptureMemoryPercentage = new LogCaptureImpl(shellExecutor);
        logCaptureMemoryPercentage.setLoggerCommand(buildPythonExecutionCommand(monitoringScript));
        logCaptureMemoryPercentage.setOutputStrategy((line) -> {
            Pattern pattern = Pattern.compile("'Memory:Percent:', (.*?), 'Total:'");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
                System.out.println("PARSED_MEMORY_PERCENT_USAGE_IS: " + matcher.group(1));
        });

        return logCaptureMemoryPercentage;

    }

}
