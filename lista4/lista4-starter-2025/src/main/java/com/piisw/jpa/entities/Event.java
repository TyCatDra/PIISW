package com.piisw.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// START OF CHANGE 6
@NamedEntityGraph(
        name = "event-with-comments-followers",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("followers")
        }
)
// END OF CHANGE 6
public abstract class Event {

    @Id
    @SequenceGenerator(name = "EVENT_ID_GENERATOR", sequenceName = "EVENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    private int duration;

    @Column(length = 10)
    private String threadId;

    @Column(length = 30)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVER_ID", nullable = false)
    private Server server;

    @Column
    private boolean analysisRequired;

    // START OF CHANGE 6
    private String description;

    @OneToMany(mappedBy = "event")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "event")
    private Set<Follower> followers;
    // END OF CHANGE 6
}
