package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventDgsMutationTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void createEventMutation_works() {
        // Предполагается, что venue с referenceId "venue-1" уже существует
        String mutation = """
                    mutation CreateEvent($input: EventInput!) {
                      createEvent(input: $input) {
                        id
                        name
                        startTime
                        endTime
                        venues { id name }
                      }
                    }
                """;
        var now = LocalDateTime.now();
        var input = new java.util.HashMap<String, Object>();
        input.put("venueReferenceIds", List.of("venue-1"));
        input.put("name", "Test Event");
        input.put("startTime", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        input.put("endTime", now.plusHours(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        var result = dgsQueryExecutor.executeAndExtractJsonPath(
                mutation,
                "data.createEvent",
                java.util.Map.of("input", input)
        );
        assertThat(result).isInstanceOf(java.util.Map.class);
        var resultMap = (java.util.Map<?, ?>) result;
        assertThat(resultMap.get("name")).isEqualTo("Test Event");
        assertThat(resultMap.get("venues")).isInstanceOf(List.class);
    }
} 