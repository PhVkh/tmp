package fi.lipp.greatheart.directory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class DirectoryApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(DirectoryApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}

}
