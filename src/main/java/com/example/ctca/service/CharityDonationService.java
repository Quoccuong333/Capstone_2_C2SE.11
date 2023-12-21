package com.example.ctca.service;

import com.example.ctca.model.entity.CharityDonation;

import java.util.List;

public interface CharityDonationService {

    List<CharityDonation> findAllByCharity(long charityId);

    CharityDonation save(CharityDonation charityDonation);

    CharityDonation findById(long id);

}
