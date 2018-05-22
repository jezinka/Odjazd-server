package com.jezinka.odjazd.model.repository;

import com.jezinka.odjazd.model.Stop;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class StopRepository extends SimpleJpaRepository<Stop, Integer> {
    private EntityManager entityManager;

    public StopRepository(EntityManager entityManager) {
        super(Stop.class, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Stop> save(List<Stop> items) {
        items.forEach(item -> entityManager.persist(item));
        return items;
    }
}
