package com.sk.signet.onm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.sk.signet")
@EnableFeignClients
@SpringBootApplication
public class OnmApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnmApplication.class, args);
	}

}
