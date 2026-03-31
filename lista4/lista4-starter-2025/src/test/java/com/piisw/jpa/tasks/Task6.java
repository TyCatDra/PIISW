package com.piisw.jpa.tasks;

import com.piisw.jpa.dto.EventFollowerDTO;
import com.piisw.jpa.entities.*;
import com.piisw.jpa.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class Task6 {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    void shouldReturnEventFollower() {
        // given
        Server server = new Server("test", "0.0.0.0");

        entityManager.persist(server);

        RequestEvent event = new RequestEvent();
        event.setServer(server);
        event.setDescription("desc");
        event.setTime(LocalDateTime.now());
        event.setAnalysisRequired(true);
        event.setMethod(HttpMethod.GET);

        entityManager.persist(event);

        Comment comment = new Comment();
        comment.setContent("comment1");
        comment.setEvent(event);

        entityManager.persist(comment);

        Follower follower = new Follower();
        follower.setUserId("Jan");
        follower.setSubscriptionDate(LocalDateTime.now());
        follower.setEvent(event);

        entityManager.persist(follower);

        entityManager.flush();
        entityManager.clear();

        // when
        List<EventFollowerDTO> result =
                eventRepository.findEventsForFollower("Jan");

        List<Event> result2 =
                eventRepository.findEventsWithGraph("Jan");

        // then
        assertFalse(result.isEmpty());
        assertFalse(result2.isEmpty());

        EventFollowerDTO dto = result.get(0);
        Event eventResult = result2.get(0);

        assertNotNull(dto.description());
        assertNotNull(dto.time());
        assertNotNull(dto.content());
        assertNotNull(dto.subscriptionDate());

        assertNotNull(eventResult.getComments());
        assertNotNull(eventResult.getFollowers());
    }

    @Test
    void shouldReturnOnlyEventsForGivenUser() {
        // given
        Server server = new Server("test", "0.0.0.0");

        entityManager.persist(server);

        RequestEvent event = new RequestEvent();
        event.setServer(server);
        event.setDescription("desc");
        event.setTime(LocalDateTime.now());
        event.setAnalysisRequired(true);
        event.setMethod(HttpMethod.GET);

        entityManager.persist(event);

        Comment comment = new Comment();
        comment.setContent("comment1");
        comment.setEvent(event);

        entityManager.persist(comment);

        Follower follower = new Follower();
        follower.setUserId("Jan");
        follower.setSubscriptionDate(LocalDateTime.now());
        follower.setEvent(event);

        entityManager.persist(follower);

        entityManager.flush();
        entityManager.clear();

        // when
        List<EventFollowerDTO> result =
                eventRepository.findEventsForFollower("Jan");

        List<Event> result2 =
                eventRepository.findEventsWithGraph("Jan");

        // then
        assertTrue(result.stream()
                .allMatch(r -> r != null));
        assertTrue(result2.stream()
                .allMatch(r -> r != null));
    }
}
