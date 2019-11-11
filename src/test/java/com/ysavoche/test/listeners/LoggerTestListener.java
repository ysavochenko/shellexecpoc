package com.ysavoche.test.listeners;

import com.ysavoche.logger.LogCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggerTestListener extends AbstractSpringTestListener implements ITestListener {

    @Autowired
    private LogCapture logCaptureMemoryPercentage;

    @Autowired
    private LogCapture logCaptureMemoryUsage;

    @Override
    public void onTestStart(ITestResult result) {
        context.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .autowireBean(this);

        logCaptureMemoryPercentage.setLoggerCommand("python /opt/docker/dockerTestImage/monitor.py\n");
        logCaptureMemoryPercentage.setOutputStrategy((line) -> {
            Pattern pattern = Pattern.compile("'Memory:Percent:', (.*?), 'Total:'");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
                System.out.println("PARSED_MEMORY_PERCENT_USAGE_IS: " + matcher.group(1));
        });

        logCaptureMemoryUsage.setLoggerCommand("python /opt/docker/dockerTestImage/monitor.py\n");
        logCaptureMemoryUsage.setOutputStrategy((line) -> {
            Pattern pattern = Pattern.compile("'Used:', (.*?), 'MB'");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find())
                System.out.println("PARSED_MEMORY_MB_USED_IS: " + matcher.group(1));
        });
        logCaptureMemoryPercentage.startLogger();
        logCaptureMemoryUsage.startLogger();
    }

    @Override
    public void onFinish(ITestContext context) {
        stopLoggers();
    }

    private void stopLoggers() {
        logCaptureMemoryPercentage.stopLogger();
        logCaptureMemoryUsage.stopLogger();
    }
}
