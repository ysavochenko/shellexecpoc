package com.ysavoche.test.listeners;

import com.ysavoche.logger.LogCapture;
import com.ysavoche.logger.LogCaptureFactory;
import io.qameta.allure.Allure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ysavoche.util.Utils.buildLogCaptureOutputFileName;

public class LoggerTestListener extends AbstractSpringTestListener implements ITestListener {

    @Autowired
    LogCaptureFactory logCaptureFactory;

    @Value("${monitoringScript1.outPutLocation}")
    private String logCaptureMemoryPercentageOutPutLocation;

    @Value("${monitoringScript2.outPutLocation}")
    private String logCaptureMemoryUsageOutPutLocation;

    //let's assume we require two parallel monitoring loggers
    private LogCapture logCaptureMemoryPercentage;
    private LogCapture logCaptureMemoryUsage;

    private String logCaptureMemoryPercentageOutFileName;
    private String logCaptureMemoryUsageOutPutFileName;


    @Override
    public void onTestStart(ITestResult result) {
        //autowiring doesn't work for TestNG listeners...
        context.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .autowireBean(this);


        //setting filenames which will be used to store outputs
        logCaptureMemoryPercentageOutFileName = buildLogCaptureOutputFileName(logCaptureMemoryPercentageOutPutLocation,
                result.getMethod().getMethodName(),
                "_memoryPercentage_");
        logCaptureMemoryUsageOutPutFileName = buildLogCaptureOutputFileName(logCaptureMemoryUsageOutPutLocation,
                result.getMethod().getMethodName(),
                "_memoryUsageMB_");

        //loggers init is done through factory
        logCaptureMemoryPercentage = logCaptureFactory.getMemoryPercentageLogger(logCaptureMemoryPercentageOutFileName);
        logCaptureMemoryUsage = logCaptureFactory.getMemoryUsageLogger(logCaptureMemoryUsageOutPutFileName);

        logCaptureMemoryPercentage.startLogger();
        logCaptureMemoryUsage.startLogger();
    }

    @Override
    public void onFinish(ITestContext context) {
        stopLoggers();
        System.out.println("About to attach output files...");
        Path fileName = Paths.get(logCaptureMemoryPercentageOutFileName);
        try (InputStream io = Files.newInputStream(fileName)) {
            Allure.addAttachment("newFileName","text/csv", io,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void stopLoggers() {
        logCaptureMemoryPercentage.stopLogger();
        logCaptureMemoryUsage.stopLogger();
    }
}
