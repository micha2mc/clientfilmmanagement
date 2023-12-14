package com.zakado.zkd.clientfilmmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class ClientfilmmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientfilmmanagementApplication.class, args);
	}

}
