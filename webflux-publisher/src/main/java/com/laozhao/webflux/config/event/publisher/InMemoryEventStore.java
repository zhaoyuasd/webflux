package com.laozhao.webflux.config.event.publisher;

import com.laozhao.webflux.config.event.InstanceEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by viruser on 2018/11/1.
 */

public class InMemoryEventStore  extends  InstanceEventPublisher{
    private ConcurrentMap<String, List<ServiceInstance>> eventLog;
    public InMemoryEventStore(){
        eventLog=new ConcurrentHashMap();
    }

    public Mono  appendEvents(List<ServiceInstance> events){
      return    Mono.fromRunnable(()->this.publish(events));
    }
}
