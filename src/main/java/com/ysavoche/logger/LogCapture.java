package com.ysavoche.logger;

import com.ysavoche.logger.output.OutputStrategy;

public interface LogCapture {

    void startLogger();

    void stopLogger();

    void setLoggerCommand(String command);

    void setOutputStrategy(OutputStrategy outputStrategy);

}
