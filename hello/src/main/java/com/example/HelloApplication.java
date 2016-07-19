package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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
    private static Logger log = LoggerFactory.getLogger(HelloController.class);

    @Value("${hello.message}")
    private String message = "NOTHING SET";
    @Autowired
    private DiscoveryClient discoveryClient;

    @PreAuthorize("#oauth2.hasScope('health.events')")
    @RequestMapping("/hello")
    public String hello(@RequestHeader(value = "Authorization") String authorizationHeader,
                        Principal currentUser) {
        log.info("@@@@@ /hello " + currentUser.getName());
        log.info("@@@@@ /hello " + authorizationHeader);

        return "HI THERE: " + message;
    }

    @RequestMapping("/hello/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

}
