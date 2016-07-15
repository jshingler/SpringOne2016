package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication
//@EnableEurekaClient
@EnableZuulProxy
//@EnableResourceServer
//@EnableOAuth2Sso
@EnableDiscoveryClient
public class GatewayApplication  {

//	extends
//} ResourceServerConfigurerAdapter {
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
//				.antMatcher("/api/**")
//				// Add below
//				.authorizeRequests()
//				.anyRequest().authenticated();
//	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

//	@Bean
//	public JwtEncrypter jwtEncrypter() {
//		return new CompressionOnlyJwtEncrypter();
//	}
//
//	@Bean
//	public TokenResponseRewriteFilter tokenResponseRewriteFilter(JwtEncrypter jwtEncrypter) {
//		return new TokenResponseRewriteFilter(jwtEncrypter);
//	}
//
//	@Bean
//	public TokenHeaderDecryptAndVerify tokenHeaderDecrypter(JwtEncrypter jwtEncrypter) {
//		return new TokenHeaderDecryptAndVerify(jwtEncrypter);
//	}
//
//
//	@Configuration
//	public static class RestSecurityConfig extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.csrf().disable();
//		}
//	}

}
