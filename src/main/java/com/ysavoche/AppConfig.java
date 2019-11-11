package com.ysavoche;


import com.jcraft.jsch.JSchException;
import com.ysavoche.logger.LogCapture;
import com.ysavoche.logger.LogCaptureImpl;
import com.ysavoche.shell.Executor;
import com.ysavoche.shell.ShellExecutor;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.ysavoche")
@PropertySource("classpath:suite.properties")
public class AppConfig {

    //sample bean configurers

    @Bean(name = "shellExecutor")
    @Scope("prototype")
    Executor shellExecutor() throws JSchException {
        return new ShellExecutor();
    }

    @Bean(name = "logCaptureMemoryPercentage")
    @Scope("prototype")
    LogCapture logCapture() {
        return new LogCaptureImpl();
    }

    @Bean(name = "logCaptureMemoryUsage")
    @Scope("prototype")
    LogCapture logCapture2() {
        return new LogCaptureImpl();
    }

}
