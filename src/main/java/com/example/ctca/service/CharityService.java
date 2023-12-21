package com.example.ctca.service;

import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.model.entity.Charity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CharityService {

    List<Charity> findAll();

    List<Charity> findByOwner(long accountId);

    List<Charity> findByRandom();

    Charity findById(long id);

    Page<Charity> findByStatusTrue(Pageable pageable);

    Charity save(Charity charity);

    List<Charity> findByProgress(String progress);

    List<Charity> findByStatusIsTrue();

    List<Charity> findReport(ReportDTO reportDTO);

    Page<Charity> findByStatusIsTrueAndTitle(Pageable pageable, String key);
}
