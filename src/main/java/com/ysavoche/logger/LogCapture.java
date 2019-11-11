package com.ysavoche.logger;

import java.util.function.Consumer;

public interface LogCapture {

    void startLogger();

    void stopLogger();

    void setLoggerCommand(String command);

    void setOutputStrategy(Consumer<String> outputStrategy);

}
