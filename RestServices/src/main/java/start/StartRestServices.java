package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Sergiu on 5/18/2017.
 */

@ComponentScan("agency")
@SpringBootApplication
public class StartRestServices {

    public static void main(String[] args) {

        SpringApplication.run(StartRestServices.class, args);
    }
}
