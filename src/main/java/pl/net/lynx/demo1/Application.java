package pl.net.lynx.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
CREATE TABLE users (
username VARCHAR(45) NOT NULL ,
password VARCHAR(120) NOT NULL ,
account_non_locked TINYINT NOT NULL DEFAULT 1 ,
PRIMARY KEY (username)
);
CREATE TABLE attempts (
id int(45) NOT NULL AUTO_INCREMENT,
username varchar(45) NOT NULL,
attempts varchar(45) NOT NULL, PRIMARY KEY (id)
);

	 */
}
