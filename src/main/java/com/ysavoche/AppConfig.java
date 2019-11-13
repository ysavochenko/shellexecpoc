package com.ysavoche;


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
    Executor shellExecutor() {
        return new ShellExecutor();
    }


}
