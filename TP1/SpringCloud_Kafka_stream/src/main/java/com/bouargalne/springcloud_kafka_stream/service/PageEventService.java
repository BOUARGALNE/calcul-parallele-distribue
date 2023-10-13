package com.bouargalne.springcloud_kafka_stream.service;

import com.bouargalne.springcloud_kafka_stream.entities.PageEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class PageEventService {
    //@Bean
    public Consumer<PageEvent> pageEventConsumer(){
        return (pageEvent -> {
            System.out.println("******------************");
            System.out.println(pageEvent.toString());
            System.out.println("*******-----**********");
        });
    }
    //@KafkaListener(topics = "R41", groupId = "myGroup")
    public void consumer(ConsumerRecord<String, PageEvent> record){
        System.out.println("***********");
        System.out.println(record.key());
        System.out.println(record.value().toString());
        System.out.println("***********");
    }
    @Bean
    public Supplier<PageEvent> pageEventSupplier(){
        return ()->{
            return PageEvent.builder()
                    .name((Math.random()>0.5)?"P1":"P2")
                    .user((Math.random()>0.5)?"U1":"U2")
                    .date(new Date())
                    .duration(new Random().nextInt(1000))
                    .build();
        };
    }


}
