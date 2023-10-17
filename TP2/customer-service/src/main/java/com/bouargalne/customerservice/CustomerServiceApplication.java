package com.bouargalne.customerservice;

import com.bouargalne.customerservice.entities.Customer;
import com.bouargalne.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            RepositoryRestConfiguration repositoryRestConfiguration
    ){
        repositoryRestConfiguration.exposeIdsFor(Customer.class);
        return args->{

            Stream.of("hamid", "abdo", "karima", "ihssan", "khalid", "mouad", "samira").forEach(name -> {

                customerRepository.save(new Customer(name,name+"@gmail.com"));

            });

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
