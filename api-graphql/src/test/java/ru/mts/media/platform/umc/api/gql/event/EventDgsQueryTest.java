package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventDgsQueryTest {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void eventsQuery_returnsList() {
        String query = """
                    query {
                      events {
                        id
                        name
                        startTime
                        endTime
                        venues { id name }
                      }
                    }
                """;
        var result = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.events");
        assertThat(result).isInstanceOf(List.class);
    }

    @Test
    void venuesWithEventsQuery_returnsList() {
        String query = """
                    query {
                      venuesWithEvents {
                        id
                        name
                        events { id name }
                      }
                    }
                """;
        var result = dgsQueryExecutor.executeAndExtractJsonPath(query, "data.venuesWithEvents");
        assertThat(result).isInstanceOf(List.class);
    }
} 