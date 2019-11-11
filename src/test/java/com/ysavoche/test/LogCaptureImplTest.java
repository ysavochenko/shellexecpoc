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


@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("classpath:suite.properties")

@Listeners(LoggerTestListener.class)

//had to set SpringTestListener too, because otherwise couldn't get context for TestNG listener
@TestExecutionListeners(LoggerTestListener.class)
public class LogCaptureImplTest extends BaseTest {

    @Autowired
    private Executor shellExecutor;

    @Value("${script1.location}")
    private String sampleScriptLocation;

    @Value("${script2.location}")
    private String longScript;



    //shellExecutor will be used to execute some scripts to perform some load on system
    //so we have to init ssh connection
    @BeforeClass
    void init() throws Exception {
        shellExecutor.initConnection();
        shellExecutor.setOutputStrategy(System.out::println);
    }

    @Test
    public void verifyShellExecution() throws Exception {

        //for POC using command as string... ideally it should be some builder, but depends on requirements
        shellExecutor.execute("hostname\n" + "exit\n");
        Assert.assertEquals(shellExecutor.getExitStatus(),0);
        shellExecutor.execute("ls -l\n" + "exit\n");
        Assert.assertEquals(shellExecutor.getExitStatus(),0);

        shellExecutor.execute("python " + sampleScriptLocation + "\n" + "exit\n");
        shellExecutor.execute("python " + sampleScriptLocation + "\n" + "exit\n");
    }

    @Test
    public void verifyLongScriptExecution() throws Exception {
        shellExecutor.execute("python " + longScript +"\n" + "exit\n");
    }

    @AfterClass
    void close() throws Exception {
        shellExecutor.closeSession();
    }

}
