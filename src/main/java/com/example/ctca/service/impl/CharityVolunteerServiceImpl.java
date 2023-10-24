package com.example.ctca.service.impl;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityVolunteer;
import com.example.ctca.repository.CharityVolunteerRepository;
import com.example.ctca.service.CharityVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityVolunteerServiceImpl implements CharityVolunteerService {

    @Autowired
    private CharityVolunteerRepository charityVolunteerRepository;

    @Override
    public List<CharityVolunteer> findAllByCharity(long charityId) {
        Charity charity = new Charity();
        charity.setId(charityId);

        return charityVolunteerRepository.findByCharity(charity);
    }

}
