package cn.hamm.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootConfiguration
class ApplicationTests {
    @Test
    void testInit() {
        System.out.println("Hello AirPower!");
    }

}
