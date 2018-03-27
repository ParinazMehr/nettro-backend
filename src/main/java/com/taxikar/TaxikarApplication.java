package com.taxikar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class TaxikarApplication extends SpringBootServletInitializer
{
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
//    {
//        return application.sources(TaxikarApplication.class);
//    }
	public static void main(String[] args) {
		SpringApplication.run(TaxikarApplication.class, args);
	}
}
