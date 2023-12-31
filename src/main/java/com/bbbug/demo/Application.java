package com.bbbug.demo;

import cn.hamm.airpower.config.GlobalConfig;
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
@ComponentScan({"cn.hamm.airpower", "com.bbbug.demo"})
@EnableWebSocket
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        GlobalConfig.isCacheEnabled = false;
        SpringApplication.run(Application.class, args);
        Initialization.run();
        System.out.println("---------------------------------");
        System.out.println("   Hi Guy, Service is running!   ");
        System.out.println("   URL:  http://127.0.0.1:8080   ");
        System.out.println("---------------------------------");
    }

}
