package com.myhrcrmproject;

import com.myhrcrmproject.controller.UserDetailsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyHrCrmProjectApplicationTests {
    @Autowired
    private UserDetailsController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
