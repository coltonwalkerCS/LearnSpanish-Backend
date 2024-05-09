package a.i.learn.spanish.com.learnspanishbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"a.i.learn.spanish.com.learnspanishbackend"})
@EnableJpaRepositories(basePackages = {"a.i.learn.spanish.com.learnspanishbackend"})
public class LearnSpanishBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpanishBackendApplication.class, args);
	}

}
