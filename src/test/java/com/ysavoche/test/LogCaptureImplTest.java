package com.ysavoche.test;

import com.ysavoche.AppConfig;
import com.ysavoche.shell.Executor;
import com.ysavoche.test.listeners.LoggerTestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.ysavoche.util.Utils.buildPythonExecutionCommand;
import static com.ysavoche.util.Utils.buildShellCommand;


@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("classpath:suite.properties")

@Listeners(LoggerTestListener.class)

//had to set SpringTestListener too, because otherwise couldn't get context for TestNG listener
@TestExecutionListeners(LoggerTestListener.class)
public class LogCaptureImplTest extends BaseTest {

    @Autowired
    private Executor shellExecutor;

    @Value("${loadScript1.location}")
    private String sampleScriptLocation;

    @Value("${loadScript2.location}")
    private String longScript;

    //shellExecutor will be used to execute some scripts to perform some load on system
    //so we have to init ssh connection
    @BeforeClass
    void init() throws Exception {
        shellExecutor.initConnection();
        shellExecutor.setOutputFunction(System.out::println);
    }

    @Test
    public void verifyShellExecution() throws Exception {
        shellExecutor.execute(buildShellCommand("hostname"));
        Assert.assertEquals(shellExecutor.getExitStatus(),0);
        shellExecutor.execute(buildShellCommand("ls -l"));
        Assert.assertEquals(shellExecutor.getExitStatus(),0);

        shellExecutor.execute(buildPythonExecutionCommand(sampleScriptLocation));
    }

    @Test
    public void verifyLongScriptExecution() throws Exception {
        //sample script takes 60s to check how logCapture works in background
        shellExecutor.execute(buildPythonExecutionCommand(longScript));
    }

    @AfterClass
    void close() throws Exception {
        shellExecutor.closeSession();
    }

}
