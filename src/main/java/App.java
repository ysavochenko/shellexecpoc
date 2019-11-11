import com.ysavoche.AppConfig;
import com.ysavoche.shell.Executor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class App {

    public static void main(String[] args) {
        AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Executor executor = appContext.getBean("shellExecutor", Executor.class);
        System.out.println(executor.getExitStatus());
    }

}
