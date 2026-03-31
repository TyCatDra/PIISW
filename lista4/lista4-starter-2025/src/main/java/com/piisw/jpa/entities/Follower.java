// START OF CHANGE 6
package com.piisw.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Follower {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private LocalDateTime subscriptionDate;

    @ManyToOne
    private Event event;
}
// START OF CHANGE 6
