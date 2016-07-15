package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}
}


@RestController
@RefreshScope
class HelloController {

	@Value("${hello.message}")
	private String message = "NOTHING SET";

	@RequestMapping("/hello")
	public String hello(@RequestHeader(value="Authorization") String authorizationHeader,
						Principal currentUser) {

//			MDC.put("productId", productId);
//			LOG.info("ProductApi: User={}, Auth={}, called with productId={}", currentUser.getName(), authorizationHeader, productId);

		System.out.println("############################################ /hello " + new Date());
		System.out.println("############################################ /hello " + currentUser.getName());
		System.out.println("############################################ /hello " + authorizationHeader);

		return "HI THERE: " + message ;
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/hello/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

}
