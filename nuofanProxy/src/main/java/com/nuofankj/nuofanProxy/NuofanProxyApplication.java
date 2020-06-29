package com.nuofankj.nuofanProxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nuofankj")
public class NuofanProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NuofanProxyApplication.class, args);
	}
}
