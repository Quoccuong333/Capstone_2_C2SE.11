package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CharityDonationDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.CharityDonation;
import com.example.ctca.model.mapper.CharityDonationMapper;
import com.example.ctca.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CharityDonationMapperImpl implements CharityDonationMapper {

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

}
