package fi.lipp.greatheart.directory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.boot.context.ApplicationPidFileWriter;

@EnableZuulProxy
@SpringBootApplication
public class DirectoryApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(DirectoryApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}

}
