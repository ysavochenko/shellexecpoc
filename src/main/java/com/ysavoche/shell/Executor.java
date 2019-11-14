package com.ysavoche.shell;

import java.util.function.Consumer;

public interface Executor {

    void initConnection() throws Exception;
    void execute(String path) throws Exception;
    void closeSession() throws Exception;
    void setOutputFunction(Consumer<String> outputStrategy);

    int getExitStatus();

}
