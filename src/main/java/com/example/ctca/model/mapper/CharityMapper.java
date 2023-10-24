package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.entity.Charity;

import java.util.List;

public interface CharityMapper {

    CharityDTO toDTO(Charity charity);

    List<CharityDTO> toListDTO(List<Charity> charityList);

    Charity toEntity(CharityDTO charityDTO);
}
