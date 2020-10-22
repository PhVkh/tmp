package fi.lipp.greatheart.directory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableZuulProxy
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class DirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoryApplication.class, args);
	}

}