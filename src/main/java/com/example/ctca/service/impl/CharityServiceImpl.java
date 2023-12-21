package com.example.ctca.service.impl;

import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.model.entity.Account;
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
    public List<Charity> findByOwner(long accountId) {
        Account account = new Account();
        account.setId(accountId);

        return charityRepository.findByOwner(account);
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

    @Override
    public List<Charity> findByProgress(String progress) {
        return charityRepository.findByProgress(progress);
    }

    @Override
    public List<Charity> findByStatusIsTrue() {
        return charityRepository.findByStatusIsTrue();
    }

    @Override
    public List<Charity> findReport(ReportDTO reportDTO) {
        String startDate = reportDTO.getStartDate() + " 00:00:00";
        String endDate = reportDTO.getEndDate() +  " 23:59:59";
        return charityRepository.findReport(startDate,endDate);
    }

    @Override
    public Page<Charity> findByStatusIsTrueAndTitle(Pageable pageable, String key) {
        return charityRepository.findByStatusIsTrueAndTitleContains(pageable, key);
    }
}
