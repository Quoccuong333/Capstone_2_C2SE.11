package com.example.ctca.service.impl;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityDonation;
import com.example.ctca.repository.CharityDonationRepository;
import com.example.ctca.service.CharityDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityDonationServiceImpl implements CharityDonationService {

    @Autowired
    private CharityDonationRepository charityDonationRepository;

    @Override
    public List<CharityDonation> findAllByCharity(long charityId) {
        Charity charity = new Charity();
        charity.setId(charityId);

        return charityDonationRepository.findByCharity(charity);
    }

}
