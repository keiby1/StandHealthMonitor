package com.example.StandHealthMonitor.repository;

import com.example.StandHealthMonitor.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    List<Criteria> findAllByOrderByCountAsc();
}

