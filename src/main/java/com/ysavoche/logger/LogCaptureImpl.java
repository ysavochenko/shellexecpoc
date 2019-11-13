package com.ysavoche.logger;

import com.ysavoche.shell.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class LogCaptureImpl implements LogCapture {

    private Executor shellExecutor;
    private Consumer<String> outputStrategy;

    private String command;

    private Runnable loggerTask = ()-> {
        shellExecutor.setOutputStrategy(this.outputStrategy);
        initShellExecutor();
        try {
            shellExecutor.execute(command + "\n");
        } catch (Exception e) {
            System.out.println("Thread interruption  was called");
        }
    };

    LogCaptureImpl(Executor executor) {
        this.shellExecutor = executor;
    }

    private ExecutorService loggerExecutor = Executors.newSingleThreadExecutor();
    private Future futureExecution;

    @Override
    public void setOutputStrategy(Consumer<String> outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    private void initShellExecutor() {
        try {
            shellExecutor.initConnection();
        } catch (Exception e) {
            // Force to stop thread
            throw new RuntimeException(e);
        }
    }

    private void stopShellExecutor() {
        try {
            shellExecutor.closeSession();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong... Please investigate", e);
        }
    }



    @Override
    public void setLoggerCommand(String command) {
        this.command = command;
    }

    @Override
    public void startLogger() {
        futureExecution = loggerExecutor.submit(loggerTask);
    }

    @Override
    public void stopLogger() {
        futureExecution.cancel(true);
        stopShellExecutor();
    }

}
