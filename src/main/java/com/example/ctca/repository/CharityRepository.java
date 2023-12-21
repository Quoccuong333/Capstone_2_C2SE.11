package com.example.ctca.repository;

import com.example.ctca.model.entity.Account;
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

    List<Charity> findByOwner(Account account);

    @Query(value = "SELECT c FROM Charity c WHERE c.progress LIKE 'APPROVED' ORDER BY RAND() LIMIT 6", nativeQuery = false)
    List<Charity> findByRandom();

    List<Charity> findByProgress(String progress);

    List<Charity> findByStatusIsTrue();

    @Query(value = "SELECT * FROM charity WHERE start_date BETWEEN :startDate AND :endDate OR end_date BETWEEN :startDate AND :endDate order by start_date DESC", nativeQuery = true)
    List<Charity> findReport(String startDate, String endDate);

    Page<Charity> findByStatusIsTrueAndTitleContains(Pageable pageable, String key);

}
