package com.jezinka.odjazd.model.repository;

import com.jezinka.odjazd.model.StopTime;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class StopTimeRepository extends SimpleJpaRepository<StopTime, Integer> {
    private EntityManager entityManager;

    public StopTimeRepository(EntityManager entityManager) {
        super(StopTime.class, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    public List<StopTime> save(List<StopTime> items) {
        items.forEach(item -> entityManager.persist(item));
        return items;
    }
}
