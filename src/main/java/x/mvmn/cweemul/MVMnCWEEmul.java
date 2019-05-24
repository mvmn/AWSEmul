package x.mvmn.cweemul;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "x.mvmn.cweemul.persistence")
public class MVMnCWEEmul {
	public static void main(String args[]) throws Exception {
		new File(new File(new File(System.getProperty("user.home", ".")), ".cweemul"), "db").mkdirs();
		SpringApplication.run(MVMnCWEEmul.class, args);
	}
}
