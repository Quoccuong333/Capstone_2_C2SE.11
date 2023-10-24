package com.example.ctca.repository;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityDonationRepository extends JpaRepository<CharityDonation, Long> {

    List<CharityDonation> findByCharity(Charity charity);

}