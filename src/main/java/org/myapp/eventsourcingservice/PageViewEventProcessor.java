package org.myapp.eventsourcingservice;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import static org.myapp.eventsourcingservice.AnalyticsBinding.PAGE_COUNT_MATERIALISE_VIEW;

@Component
public class PageViewEventProcessor {

    @StreamListener
    @SendTo(AnalyticsBinding.PAGE_COUNT_OUT)
    public KStream<String, Long> process(@Input(AnalyticsBinding.PAGE_VIEWS_INPUT)
                                                     KStream<String, PageViewEvent> events) {

        return events
                .filter((key, value) -> value.getDuration() > 10)
                .map((key, value) -> new KeyValue<>(value.getPage(), "0"))
                .groupByKey()
                .count(Materialized.as(PAGE_COUNT_MATERIALISE_VIEW))
                .toStream();
    }

}
