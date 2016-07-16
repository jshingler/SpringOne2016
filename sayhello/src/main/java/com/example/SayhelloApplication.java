package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class SayhelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SayhelloApplication.class, args);
    }
}


@RestController
class SayHelloController {


    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @PreAuthorize("#oauth2.hasScope('health.events')")
    @RequestMapping("/")
    public String sayHello(@RequestHeader(value="Authorization") String authorizationHeader,
                           Principal currentUser) throws JsonProcessingException {
        String greeting = "";

        Map<?, ?> userInfoResponse = restTemplate.getForObject(resourceServerProperties.getUserInfoUri(),
                Map.class);
//        model.addAttribute("username", currentUser.getName());
//        model.addAttribute("response",
//                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfoResponse));
//        model.addAttribute("token", restTemplate.getOAuth2ClientContext().getAccessToken().getValue());



        System.out.println("############################################ /sayhello " + new Date());
        System.out.println("############################################ /sayhello " + currentUser.toString());
        System.out.println("############################################ /sayhello " + authorizationHeader);
        System.out.println("############################################ /sayhello " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfoResponse));
        System.out.println("############################################ /sayhello " + restTemplate.getOAuth2ClientContext().getAccessToken().getValue());

        // http://www.todaysoftmag.com/article/1429/micro-service-discovery-using-netflix-eureka
        // hello is the name of the service in
        // Eureka, as well as of the Ribbon LoadBalancer
        // which gets created automatically.
        ServiceInstance instance = loadBalancerClient.choose("hello");

        if (instance != null) {
            // Invoke server, based on host and port.
            // Example using RestTemplate.
            URI helloUri = URI.create(String
                    .format("http://%s:%s/hello/",
                            instance.getHost(), instance.getPort()));

            System.out.println("############################################ Invoke Hello ");
            greeting = restTemplate.getForObject(helloUri, String.class);
            System.out.println("############################################ Invoke Hello after ");
        }

        return "SayHello(Ribbon) ->" + greeting;
    }
}
