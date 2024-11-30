package org.samuel.storemanagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreManagementApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String dbUrl = dotenv.get("DB_URL");
		System.setProperty("DB_URL", dbUrl);

		String dbUsername = dotenv.get("DB_USERNAME");
		System.setProperty("DB_USERNAME", dbUsername);

		String dbPassword = dotenv.get("DB_PASSWORD");
		System.setProperty("DB_PASSWORD", dbPassword);

		SpringApplication.run(StoreManagementApplication.class, args);
	}

}
