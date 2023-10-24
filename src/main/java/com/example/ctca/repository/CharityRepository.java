package com.example.ctca.repository;

import com.example.ctca.model.entity.Charity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {

    Page<Charity> findByProgress(Pageable pageable, String progress);

    @Query(value = "SELECT c FROM Charity c WHERE c.progress LIKE 'APPROVED' ORDER BY RAND() LIMIT 6", nativeQuery = false)
    List<Charity> findByRandom();

}
