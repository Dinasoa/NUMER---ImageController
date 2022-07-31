package school.hei.helloworldapii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"school.hei.helloworldapii" , "controller"})
public class HelloWorldApiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApiiApplication.class, args);
    }

}
