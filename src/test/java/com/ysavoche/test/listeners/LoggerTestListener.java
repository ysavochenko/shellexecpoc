package com.ysavoche.test.listeners;

import com.ysavoche.logger.LogCapture;
import com.ysavoche.logger.LogCaptureFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class LoggerTestListener extends AbstractSpringTestListener implements ITestListener {

    @Autowired
    LogCaptureFactory logCaptureFactory;

    //let's assume we require two parallel monitoring loggers
    private LogCapture logCaptureMemoryPercentage;
    private LogCapture logCaptureMemoryUsage;

    @Override
    public void onTestStart(ITestResult result) {
        //autowiring doesn't work for TestNG listeners...
        context.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .autowireBean(this);

        //loggers init is done through factory
        logCaptureMemoryPercentage = logCaptureFactory.getMemoryPercentageLogger();
        logCaptureMemoryUsage = logCaptureFactory.getMemoryUsageLogger();

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
