package org.myapp.eventsourcingservice;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

interface AnalyticsBinding {

    String PAGE_VIEWS_OUTPUT = "pvout";
    String PAGE_VIEWS_INPUT = "pvin";
    String PAGE_COUNT_MATERIALISE_VIEW = "pcmv";
    String PAGE_COUNT_OUT = "pcout";
    String PAGE_COUNT_IN = "pcin";

    @Output(PAGE_VIEWS_OUTPUT)
    MessageChannel pageViewsOutput();

    @Input(PAGE_VIEWS_INPUT)
    KStream<String, PageViewEvent> pageViewInput();

    @Output(PAGE_COUNT_OUT)
    KStream<String, Long> pageCountOut();

    @Input(PAGE_COUNT_IN)
    KTable<String, Long> pageCountIn();
}
