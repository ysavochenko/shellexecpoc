package com.ysavoche.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogCaptureFactory {

    @Autowired
    private LogCaptureMemoryPercentageBuilder logCaptureMemoryPercentageBuilder;

    @Autowired
    private LogCaptureMemoryUsageBuilder logCaptureMemoryUsageBuilder;

    public LogCapture getMemoryPercentageLogger(String outPutFileName) {
        return logCaptureMemoryPercentageBuilder.build(outPutFileName);
    }

    public LogCapture getMemoryUsageLogger(String outPutFileName) {
        return logCaptureMemoryUsageBuilder.build(outPutFileName);
    }
}
