package org.myapp.eventsourcingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PageViewEventSource implements ApplicationRunner {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final MessageChannel pageViewOutput;

    public PageViewEventSource(AnalyticsBinding binding) {
        this.pageViewOutput = binding.pageViewsOutput();
    }

    @Override
    public void run(ApplicationArguments args) {

        List<String> names = Arrays.asList("foo", "boo", "moo");
        List<String> pages = Arrays.asList("blog", "sitemap", "news", "about");

        Runnable runnable = () -> {
            String rPage = pages.get(new Random().nextInt(pages.size()));
            String rName = names.get(new Random().nextInt(names.size()));

            PageViewEvent pageViewEvent = new PageViewEvent(rName, rPage, Math.random() > .5 ? 10 : 1000);

            Message<PageViewEvent> message = MessageBuilder
                    .withPayload(pageViewEvent)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, pageViewEvent.getUserId().getBytes())
                    .build();
            try {
                this.pageViewOutput.send(message);
                LOG.info("Message sent " + message.toString());

            } catch (Exception ex) {
                LOG.error("Error while sending message " + ex);
            }
        };

        Executors
                .newScheduledThreadPool(1)
                .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }
}
