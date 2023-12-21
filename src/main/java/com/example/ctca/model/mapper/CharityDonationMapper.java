package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.CharityDonationDTO;
import com.example.ctca.model.entity.CharityDonation;

import java.util.List;

public interface CharityDonationMapper {

    CharityDonationDTO toDTO(CharityDonation charityDonation);

    List<CharityDonationDTO> toListDTO(List<CharityDonation> charityDonationList);

    CharityDonation toEntity(CharityDonationDTO charityDonationDTO);

}
