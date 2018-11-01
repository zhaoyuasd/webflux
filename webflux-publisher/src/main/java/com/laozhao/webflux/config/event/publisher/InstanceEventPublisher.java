package com.laozhao.webflux.config.event.publisher;

import com.laozhao.webflux.config.event.InstanceEvent;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.cloud.client.ServiceInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.util.List;

/**
 * Created by viruser on 2018/11/1.
 */
public abstract class InstanceEventPublisher implements Publisher<ServiceInstance> {
    private final Flux<ServiceInstance> publishedFlux;
    private final FluxSink<ServiceInstance> sink;

    protected InstanceEventPublisher() {
        UnicastProcessor<ServiceInstance> unicastProcessor = UnicastProcessor.create();
        this.publishedFlux = unicastProcessor.publish().autoConnect(0);
        this.sink = unicastProcessor.sink();
    }

    protected void publish(List<ServiceInstance> events) {
        events.forEach(event -> {
            System.out.println("Event published  name:"+event.getClass().getSimpleName()+" time:"+System.nanoTime());
            this.sink.next(event);
        });
    }

    @Override
    public void subscribe(Subscriber<? super ServiceInstance> s) {
        publishedFlux.subscribe(s);
    }

    public abstract Mono appendEvents(List<ServiceInstance> events);
}


