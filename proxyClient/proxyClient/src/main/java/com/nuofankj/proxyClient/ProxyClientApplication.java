package com.nuofankj.proxyClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nuofankj")
public class ProxyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyClientApplication.class, args);
	}

}
