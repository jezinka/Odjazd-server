package com.jezinka.odjazd.model.repository;

import com.jezinka.odjazd.model.Trip;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TripRepository extends SimpleJpaRepository<Trip, String> {
    private EntityManager entityManager;

    public TripRepository(EntityManager entityManager) {
        super(Trip.class, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Trip> save(List<Trip> items) {
        items.forEach(item -> entityManager.persist(item));
        return items;
    }

}
