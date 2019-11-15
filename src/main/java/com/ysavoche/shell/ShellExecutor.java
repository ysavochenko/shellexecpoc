package com.ysavoche.shell;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Component
@PropertySource("classpath:suite.properties")
public class ShellExecutor implements Executor {

    @Value("${remote.host}")
    private String host;

    @Value("${remote.username}")
    private String user;

    @Value("${remote.password}")
    private String password;

    private Session session;
    private int exitStatus;
    private Consumer<String> outputFunction;

    private volatile boolean init = false;

    @Override
    public synchronized void initConnection() throws JSchException {
        if (!init) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setUserInfo(new RemoteUserInfo(user, password));
            session.connect();
            init = true;
        }
    }

    @Override
    public void setOutputFunction(Consumer<String> outputStrategy) {
        this.outputFunction = outputStrategy;
    }

    @Override
    @Step("Executing shell command: {0} ...")
    public void execute(String commandToExecute) throws Exception {
        Channel channel = session.openChannel("shell");
        channel.setInputStream(new ByteArrayInputStream(commandToExecute.getBytes(StandardCharsets.UTF_8)));
        channel.setOutputStream(System.out);
        InputStream inputStream = channel.getInputStream();
        exitStatus = -1;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        channel.connect();

        while (!Thread.currentThread().isInterrupted()) {
            String line;
            while ((line = br.readLine()) != null) {
                this.outputFunction.accept(line);
            }

            if (channel.isClosed()) {
                if (inputStream.available() > 0) continue;
                exitStatus = channel.getExitStatus();
                br.close();
                break;
            }
        }
        channel.disconnect();

        System.out.print("Exit status of the execution: " + exitStatus);
    }

    @Override
    public int getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public synchronized void closeSession() {
        if (!init) {
            session.disconnect();
            init = false;
        }
    }
}
