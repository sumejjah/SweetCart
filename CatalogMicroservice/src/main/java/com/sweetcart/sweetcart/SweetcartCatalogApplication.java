package com.sweetcart.sweetcart;

import com.sweetcart.sweetcart.controller.CakeShopController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class SweetcartCatalogApplication {

	public static void main(String[] args) throws RestClientException, IOException {
		ApplicationContext ctx = SpringApplication.run(
				SweetcartCatalogApplication.class, args);

		CakeShopController cakeShopController=ctx.getBean(CakeShopController.class);
		System.out.println(cakeShopController);
		cakeShopController.getCakeShops();

	}

	@Bean
	public  CakeShopController  cakeShopController()
	{
		return  new CakeShopController();
	}

}
