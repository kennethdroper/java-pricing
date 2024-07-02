package kdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PricingApplication {

    public static void main(String[] args) {
        System.out.println("Starting application, if debugging try http://localhost:8080/priceOption?putOrCall=PUT&k=100&s=10&v=5&r=0.01&t=2");
        SpringApplication.run(PricingApplication.class, args);
    }

}
