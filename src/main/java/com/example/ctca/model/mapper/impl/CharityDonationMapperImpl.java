package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CharityDonationDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.CharityDonation;
import com.example.ctca.model.mapper.CharityDonationMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CharityDonationService;
import com.example.ctca.service.CharityService;
import com.example.ctca.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharityDonationMapperImpl implements CharityDonationMapper {
    @Autowired
    CharityDonationService charityDonationService;

    @Autowired
    CharityService charityService;

    @Autowired
    AccountService accountService;

    @Autowired
    CharityMapper charityMapper;

    @Override
    public CharityDonationDTO toDTO(CharityDonation charityDonation) {
        if (charityDonation == null){
            return null;
        }

        CharityDonationDTO donationDTO = new CharityDonationDTO();
        donationDTO.setId(charityDonation.getId());
        donationDTO.setNote(charityDonation.getNote());
        donationDTO.setImage(charityDonation.getImage());
        donationDTO.setProgress(charityDonation.getProgress());
        donationDTO.setDate(DateUtil.convertDateToString(charityDonation.getCreatedOn(), "dd/MM/yyyy"));

        Account account = charityDonation.getAccount();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        donationDTO.setAccount(accountDTO);

        if (charityDonation.getCharity() != null) {
            donationDTO.setCharityId(charityDonation.getCharity().getId());
            donationDTO.setCharity(charityMapper.toDTO(charityDonation.getCharity()));
        }

        return donationDTO;
    }

    @Override
    public List<CharityDonationDTO> toListDTO(List<CharityDonation> charityDonationList) {
        if (charityDonationList == null) {
            return null;
        }
        List<CharityDonationDTO> list = new ArrayList<>(charityDonationList.size());
        for (CharityDonation donation : charityDonationList) {
            CharityDonationDTO donationDTO = toDTO(donation);
            if (donationDTO != null) {
                list.add(donationDTO);
            }
        }

        return list;
    }

    @Override
    public CharityDonation toEntity(CharityDonationDTO charityDonationDTO) {
        if(charityDonationDTO ==null){
            return null;
        }
        CharityDonation charityDonation = charityDonationService.findById(charityDonationDTO.getId());

        if (charityDonation == null)
            charityDonation = new CharityDonation();
        charityDonation.setNote(charityDonationDTO.getNote());
        charityDonation.setStatus(true);
        charityDonation.setProgress(charityDonationDTO.getProgress());
        if (charityDonationDTO.getCharityId() !=0) {
            charityDonation.setCharity(charityService.findById(charityDonationDTO.getCharityId()));
        }
        if (charityDonationDTO.getAccountId() !=0) {
            charityDonation.setAccount(accountService.findById(charityDonationDTO.getAccountId()));
        }

        return charityDonation;
    }

}
