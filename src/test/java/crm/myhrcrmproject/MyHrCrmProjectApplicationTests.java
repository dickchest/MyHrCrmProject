package crm.myhrcrmproject;

import crm.myhrcrmproject.controller.UserDetailsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
