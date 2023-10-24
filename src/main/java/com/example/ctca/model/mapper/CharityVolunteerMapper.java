package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.CharityVolunteerDTO;
import com.example.ctca.model.entity.CharityVolunteer;

import java.util.List;

public interface CharityVolunteerMapper {

    CharityVolunteerDTO toDTO(CharityVolunteer charityVolunteer);

    List<CharityVolunteerDTO> toListDTO(List<CharityVolunteer> charityVolunteerList);

}
