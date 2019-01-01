package org.myapp.eventsourcingservice;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.myapp.eventsourcingservice.AnalyticsBinding.PAGE_COUNT_MATERIALISE_VIEW;

@RestController
public class AnalyticsRestController {

    public final QueryableStoreRegistry registry;

    public AnalyticsRestController(QueryableStoreRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/counts")
    Map<String, Long> counts() {
        Map<String, Long> counts = new HashMap<>();

        ReadOnlyKeyValueStore<String, Long> queryableStoreType =
                this
                        .registry
                        .getQueryableStoreType(PAGE_COUNT_MATERIALISE_VIEW, QueryableStoreTypes.keyValueStore());

        KeyValueIterator<String, Long> all = queryableStoreType.all();

        while (all.hasNext()) {
            KeyValue<String, Long> entry = all.next();
            counts.put(entry.key, entry.value);
        }
        return counts;        }

}
