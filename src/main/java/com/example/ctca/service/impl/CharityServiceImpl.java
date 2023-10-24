package com.example.ctca.service.impl;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.repository.CharityRepository;
import com.example.ctca.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharityServiceImpl implements CharityService {

    @Autowired
    private CharityRepository charityRepository;

    @Override
    public List<Charity> findAll() {
        return charityRepository.findAll();
    }

    @Override
    public List<Charity> findByRandom() {
        return charityRepository.findByRandom();
    }

    @Override
    public Charity findById(long id) {
        return charityRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Charity> findByStatusTrue(Pageable pageable) {
        return charityRepository.findByProgress(pageable, "APPROVED");
    }

    @Override
    public Charity save(Charity charity) {
        return charityRepository.save(charity);
    }
}
