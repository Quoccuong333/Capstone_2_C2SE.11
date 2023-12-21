package com.example.ctca.repository;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityVolunteerRepository extends JpaRepository<CharityVolunteer, Long> {

    List<CharityVolunteer> findByCharity(Charity charity);

    List<CharityVolunteer> findByStatusIsTrue();

    @Query(value = "SELECT COUNT(*) FROM charity_volunteer WHERE charity_id = :charityId AND account_id = :accountId AND progress = 'PENDING'", nativeQuery = true)
    int countAccountByCharity(long charityId, long accountId);

}
