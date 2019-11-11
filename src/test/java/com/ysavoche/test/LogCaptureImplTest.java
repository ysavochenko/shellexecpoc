package com.ysavoche.test;

import com.ysavoche.AppConfig;
import com.ysavoche.shell.Executor;
import com.ysavoche.test.listeners.LoggerTestListener;
import org.springframework.beans.factory.annotation.Autowired;
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
@TestExecutionListeners(LoggerTestListener.class)
public class LogCaptureImplTest extends BaseTest {

    @Autowired
    private Executor shellExecutor;

    @BeforeClass
    void init() throws Exception {
        shellExecutor.initConnection();
        shellExecutor.setOutputStrategy(System.out::println);
    }

    @Test
    public void verifyShellExecution() throws Exception {
        shellExecutor.execute("hostname\n" + "exit\n");
        Assert.assertEquals(shellExecutor.getExitStatus(),0);
        shellExecutor.execute("ls -l\n" + "exit\n");
        Assert.assertEquals(shellExecutor.getExitStatus(),0);
        shellExecutor.execute("python /opt/docker/dockerTestImage/samplePythonScript.py\n" + "exit\n");
        shellExecutor.execute("python /opt/docker/dockerTestImage/samplePythonScript.py\n" + "exit\n");
    }

    @Test
    public void verifyLongScriptExecution() throws Exception {
        shellExecutor.execute("python /opt/docker/dockerTestImage/longScript.py\n" + "exit\n");
    }

    @AfterClass
    void close() throws Exception {
        shellExecutor.closeSession();
    }

}
