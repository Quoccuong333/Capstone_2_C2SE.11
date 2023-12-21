package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.service.CharityService;
import com.example.ctca.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharityMapperImpl implements CharityMapper {

    @Autowired
    CharityService charityService;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    AccountMapper accountMapper;


    @Override
    public CharityDTO toDTO(Charity charity) {
        if (charity == null){
            return null;
        }
        CharityDTO charityDTO = new CharityDTO();
        charityDTO.setId(charity.getId());
        charityDTO.setTitle(charity.getTitle());
        charityDTO.setDescription(charity.getDescription());
        charityDTO.setNote(charity.getNote());
        charityDTO.setImage(charity.getImage());
        charityDTO.setStartDate(DateUtil.convertDateToString(charity.getStartDate(),"yyyy-MM-dd"));
        charityDTO.setEndDate(DateUtil.convertDateToString(charity.getEndDate(),"yyyy-MM-dd"));
        charityDTO.setProgress(charity.getProgress());
        charityDTO.setStatus(charity.isStatus());

        if (charity.getOwner() != null) {
            charityDTO.setOwnerId(charity.getOwner().getId());
            charityDTO.setOwner(accountMapper.toDTO(charity.getOwner()));
        }

        // base
        charityDTO.setId(charity.getId());
        charityDTO.setVersion(charity.getVersion());
        charityDTO.setCreatedOn(dateUtil.convertDateToString(charity.getCreatedOn(), "yyyy-MM-dd"));
        charityDTO.setCreatedOn(dateUtil.convertDateToString(charity.getUpdatedOn(), "yyyy-MM-dd"));

        return charityDTO;
    }

    @Override
    public List<CharityDTO> toListDTO(List<Charity> charityList) {
        if (charityList == null) {
            return null;
        }
        List<CharityDTO> list = new ArrayList<>(charityList.size());
        for (Charity charity : charityList) {
            CharityDTO charityDTO = toDTO(charity);
            if (charityDTO != null) {
                list.add(charityDTO);
            }
        }
        return list;
    }

    @Override
    public Charity toEntity(CharityDTO charityDTO) {
        if (charityDTO == null){
            return null;
        }
        Charity charity = charityService.findById(charityDTO.getId());
        if (charity == null)
            charity = new Charity();
        charity.setTitle(charityDTO.getTitle());
        charity.setDescription(charityDTO.getDescription());
        charity.setNote(charityDTO.getNote());
        charity.setProgress(charityDTO.getProgress());
        charity.setStartDate(DateUtil.convertStringToDate(charityDTO.getStartDate(),"yyyy-MM-dd"));
        charity.setEndDate(DateUtil.convertStringToDate(charityDTO.getEndDate(),"yyyy-MM-dd"));
        charity.setStatus(charityDTO.isStatus());

        if (charityDTO.getImage() != null) {
            charity.setImage(charityDTO.getImage());
        }

        return charity;
    }
}
