package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    PoitlServiceImpl poitlService;

    @Test
    void contextLoads() {
        poitlService.generateWord();
    }

}
