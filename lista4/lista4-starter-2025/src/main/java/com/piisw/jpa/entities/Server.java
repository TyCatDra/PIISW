package com.piisw.jpa.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// START OF CHANGE 1
@SQLDelete(sql = "UPDATE server SET is_active = false WHERE id = ? AND version = ?")
@Where(clause = "is_active = true")
@EntityListeners(AuditingEntityListener.class)
// END OF CHANGE 1
public class Server {

    @Id
    @SequenceGenerator(name = "SERVER_ID_GENERATOR", sequenceName = "SERVER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVER_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ip;

    // START OF CHANGE 1

    @Version // Required for Optimistic Locking
    private Long version;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    private boolean isActive = true; // Required for Soft Delete

    // END OF CHANGE 1

    public Server(String name, String ip) {
        super();
        this.name = name;
        this.ip = ip;
    }
    // START OF CHANGE 1 (REMOVED TODOs)
    // END OF CHANGE 1
}
