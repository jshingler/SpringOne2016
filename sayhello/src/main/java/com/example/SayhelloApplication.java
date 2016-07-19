package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class SayhelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SayhelloApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public OAuth2RestTemplate loadBalancedTokenRelayingRestTemplate() {
        return new TokenRelayingRestTemplate();
    }
}

@RestController
class SayHelloController {
    private static Logger log = LoggerFactory.getLogger(SayHelloController.class);

    @Autowired
    @Qualifier("loadBalancedTokenRelayingRestTemplate")
    private OAuth2RestTemplate loadBalancedTokenRelayingRestTemplate;

    private OAuth2RestTemplate tokenRelayingRestTemplate = new TokenRelayingRestTemplate();


    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @PreAuthorize("#oauth2.hasScope('health.events')")
    @RequestMapping("/")
    public String sayHello(@RequestHeader(value = "Authorization") String authorizationHeader,
                           Principal currentUser) throws JsonProcessingException {
        String greeting = "";

        log.info("##### /sayhello UserInfoURI " + resourceServerProperties.getUserInfoUri());
        Map<?, ?> userInfoResponse = tokenRelayingRestTemplate.getForObject(resourceServerProperties.getUserInfoUri(),
                Map.class);

        log.info("##### /sayhello User:         " + currentUser.toString());
        log.info("##### /sayhello Auth Header:  " + authorizationHeader);
        log.info("##### /sayhello " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userInfoResponse));

        greeting = loadBalancedTokenRelayingRestTemplate.getForObject("http://HELLO/hello", String.class);
        log.info("##### Invoke Hello after ");

        return "SayHello ->" + greeting;
    }

    public void setOAuth2RestTemplate(OAuth2RestTemplate oAuth2RestTemplate) {
        this.loadBalancedTokenRelayingRestTemplate = oAuth2RestTemplate;
    }

    public void setUserInfoRestTemplate(OAuth2RestTemplate userInfoRestTemplate) {
        this.tokenRelayingRestTemplate = userInfoRestTemplate;
    }
}
