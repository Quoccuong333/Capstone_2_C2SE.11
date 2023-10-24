package com.example.ctca.service;

import com.example.ctca.model.entity.CharityVolunteer;

import java.util.List;

public interface CharityVolunteerService {

    List<CharityVolunteer> findAllByCharity(long charityId);

}
