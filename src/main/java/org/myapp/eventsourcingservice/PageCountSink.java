package org.myapp.eventsourcingservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class PageCountSink {

    private final Log log = LogFactory.getLog(getClass());

    @StreamListener
    public void process(@Input((AnalyticsBinding.PAGE_COUNT_IN)) KTable<String, Long> counts) {
        counts
                .toStream()
                .foreach((key, value) -> log.info(key + "=" + value));
    }
}
