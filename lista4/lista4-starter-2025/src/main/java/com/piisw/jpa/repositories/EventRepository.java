// START OF CHANGE 2
package com.piisw.jpa.repositories;

import com.piisw.jpa.dto.EventFollowerDTO;
import com.piisw.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByTimeBetweenAndAnalysisRequired(
            LocalDateTime start,
            LocalDateTime end,
            boolean analysisRequired,
            Pageable pageable
    );

    // START OF CHANGE 3
    @Modifying
    @Query("DELETE FROM Event e WHERE e.time < :time")
    void deleteOlderThan(@Param("time") LocalDateTime time);

    @Modifying
    @Query("""
    UPDATE Event e 
    SET e.analysisRequired = true 
    WHERE TYPE(e) = :clazz 
    AND e.duration > :minDuration
""")
    int updateAnalysisRequired(
            @Param("clazz") Class<? extends Event> clazz,
            @Param("minDuration") int minDuration
    );
    // END OF CHANGE 3
    // START OF CHANGE 4
    @Query("""
    SELECT new com.piisw.jpa.repositories.ServerStatistic(s, COUNT(e))
    FROM Event e
    JOIN e.server s
    GROUP BY s.name
""")
    List<ServerStatistic> getServerStatistics();
    // END OF CHANGE 4
    // START OF CHANGE 6
    @EntityGraph(value = "event-with-comments-followers")
    @Query("""
    SELECT e FROM Event e
    JOIN e.followers f
    WHERE f.userId = :userId
""")
    List<Event> findEventsWithGraph(@Param("userId") String userId);

    @Query("""
    SELECT new com.piisw.jpa.dto.EventFollowerDTO(
        e.description,
        e.time,
        e.analysisRequired,
        c.content,
        f.subscriptionDate
    )
    FROM Event e
    JOIN e.comments c
    JOIN e.followers f
    WHERE f.userId = :userId
""")
    List<EventFollowerDTO> findEventsForFollower(@Param("userId") String userId);
    // END OF CHANGE 6
}
// END OF CHANGE 2
