package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventLoadTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void createEventMutation_loadTest() throws InterruptedException {
        int threads = 10;
        int requestsPerThread = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads * requestsPerThread);
        String mutation = """
                    mutation CreateEvent($input: EventInput!) {
                      createEvent(input: $input) { id name }
                    }
                """;
        for (int t = 0; t < threads; t++) {
            for (int i = 0; i < requestsPerThread; i++) {
                int idx = t * requestsPerThread + i;
                executor.submit(() -> {
                    try {
                        var now = LocalDateTime.now();
                        var input = new java.util.HashMap<String, Object>();
                        input.put("venueReferenceIds", List.of("venue-1"));
                        input.put("name", "LoadTest Event " + idx);
                        input.put("startTime", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        input.put("endTime", now.plusHours(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        var result = dgsQueryExecutor.executeAndExtractJsonPath(
                                mutation,
                                "data.createEvent",
                                java.util.Map.of("input", input)
                        );
                        assertThat(result).isInstanceOf(java.util.Map.class);
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        latch.await(60, TimeUnit.SECONDS);
        executor.shutdown();
        assertThat(latch.getCount()).isEqualTo(0);
    }
} 