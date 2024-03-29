package cn.hamm.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * <h1>入口类</h1>
 *
 * @author Hamm
 */
@EnableAutoConfiguration
@ComponentScan({"cn.hamm.airpower", "cn.hamm.demo"})
@EnableWebSocket
@EnableScheduling
public class Application {
    private static Initialization initialization;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        initialization.run();
        System.out.println("---------------------------------");
        System.out.println("   Hi Guy, Service is running!   ");
        System.out.println("   URL:  http://127.0.0.1:8080   ");
        System.out.println("---------------------------------");
    }

    @Autowired
    private void init(Initialization initialization) {
        Application.initialization = initialization;
    }
}
