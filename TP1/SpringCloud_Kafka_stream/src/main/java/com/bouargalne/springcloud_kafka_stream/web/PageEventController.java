package com.bouargalne.springcloud_kafka_stream.web;

import com.bouargalne.springcloud_kafka_stream.entities.PageEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.*;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class PageEventController {
    private StreamBridge streamBridge;
    private InteractiveQueryService interactiveQueryService;


    public PageEventController(StreamBridge streamBridge, InteractiveQueryService interactiveQueryService) {
        this.streamBridge = streamBridge;
        this.interactiveQueryService = interactiveQueryService;
    }

    @GetMapping("publish/{topic}/{name}")
    public PageEvent publish(@PathVariable String name, @PathVariable String topic){
        PageEvent pageEvent=new PageEvent();
        pageEvent.setName(name);
        pageEvent.setDate(new Date());
        pageEvent.setDuration(new Random().nextInt(1000));
        pageEvent.setUser(Math.random()>0.5?"U1":"U2");
        streamBridge.send(topic,pageEvent);
        return pageEvent;
    }

    @GetMapping(value = "/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String,Long>> analytics(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq->{
                    Map<String,Long> map=new HashMap<>();
                    ReadOnlyKeyValueStore<String, Long> stats = interactiveQueryService.getQueryableStore("count-store", QueryableStoreTypes.keyValueStore());
                    Instant now=Instant.now();
                    Instant from=now.minusSeconds(5);
                    KeyValueIterator<String, Long> keyValueIterator = stats.all();
                    while (keyValueIterator.hasNext()){
                        KeyValue<String, Long> next = keyValueIterator.next();
                        map.put(next.key,next.value);
                    }
                    return map;
                });
    }


}
