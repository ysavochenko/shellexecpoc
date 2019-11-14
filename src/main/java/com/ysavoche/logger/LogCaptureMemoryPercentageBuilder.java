package com.ysavoche.logger;

import com.ysavoche.logger.output.FileOutputStrategyImpl;
import com.ysavoche.logger.output.OutputStrategy;
import com.ysavoche.shell.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ysavoche.util.Utils.buildPythonExecutionCommand;

@Component
class LogCaptureMemoryPercentageBuilder {

    @Value("${monitoringScript1.location}")
    private String monitoringScript;

    @Autowired
    Executor shellExecutor;

    LogCapture build(String outPutFileName) {
        LogCapture logCaptureMemoryPercentage = new LogCaptureImpl(shellExecutor);
        logCaptureMemoryPercentage.setLoggerCommand(buildPythonExecutionCommand(monitoringScript));

        Function<String, String> outputParser = (line) -> {
            Pattern pattern = Pattern.compile("'Memory:Percent:', (.*?), 'Total:'");
            Matcher matcher = pattern.matcher(line);
            String out = "";
            if (matcher.find())
                return matcher.group(1);
            return out;
        };

        OutputStrategy fileOutputStrategy = new FileOutputStrategyImpl(outputParser,
                outPutFileName);

        logCaptureMemoryPercentage.setOutputStrategy(fileOutputStrategy);

        return logCaptureMemoryPercentage;

    }

}
