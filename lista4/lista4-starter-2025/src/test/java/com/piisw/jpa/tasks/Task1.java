package com.piisw.jpa.tasks;

import com.piisw.jpa.configuration.AuditingConfiguration;
import com.piisw.jpa.entities.Server;
import com.piisw.jpa.repositories.ServerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.transaction.TestTransaction;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuditingConfiguration.class)
class Task1 {

    @Autowired
    private ServerRepository serverRepository;

    @Test
    void shouldIncrementVersionCounterOnSave() throws Exception {
        // given
        Server server = serverRepository.getById(1L);
        Long currentVersion = server.getVersion();

        // when
        server.setName("name2");
        serverRepository.saveAndFlush(server); // we need to force a flush to demonstrate the feature, under normal execution this would happen during transaction commit

        // then
        assertThat("Version counter must be incremented", server.getVersion(), is(2L));
        assertThat("Expected increment is 1", server.getVersion(), is(currentVersion + 1));
    }


    @Test
    void shouldSetCreatedDateOnServerDuringSave() throws Exception {
        // given
        Server server = new Server("server1", "138.0.1.5");

        // when
        serverRepository.saveAndFlush(server); // we need to force a flush to demonstrate the feature, under normal execution this would happen during transaction commit

        // then
        assertThat("Created date must be set", server.getCreatedDate(), is(notNullValue()));
    }

    @Test
    void shouldUpdateLastUpdateDateOnServerAfterUpdate() throws Exception {
        // given
        Server server = serverRepository.getById(1L);
        LocalDateTime base = LocalDateTime.now();

        // when
        server.setName("name2");
        serverRepository.saveAndFlush(server); // we need to force a flush to demonstrate the feature, under normal execution this would happen during transaction commit

        // then
        assertThat("Last update date must be set", server.getLastUpdateDate(), is(notNullValue()));
        assertThat("Last update date must be updated with current date", base.isBefore(server.getLastUpdateDate()), is(true));
    }

    @Test
    void shouldPerformSoftDelete() throws Exception {
        // given
        Server server = new Server("server 1", "138.0.2.5");
        serverRepository.saveAndFlush(server); // we need to force a flush to demonstrate the feature, under normal execution this would happen during transaction commit

        // when
        serverRepository.deleteById(server.getId());
        TestTransaction.end();

        // then
        assertThat(serverRepository.findById(server.getId()).isPresent(), is(false));
    }

    // START OF CHANGE 1

    @Test
    void shouldUseOptimisticLocking() {
        Server s1 = serverRepository.save(new Server("server 1", "138.0.2.5"));
        s1.setName("A");
        serverRepository.saveAndFlush(s1);

        Server s2 = serverRepository.findById(s1.getId()).get();
        TestTransaction.end();

        TestTransaction.start();
        s2.setName("B");

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            serverRepository.saveAndFlush(s2);
        });
    }

    // END OF CHANGE 1
}
