package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CharityVolunteerDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.CharityVolunteer;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.CharityVolunteerMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.CharityVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharityVolunteerMapperImpl implements CharityVolunteerMapper {
    @Autowired
    CharityService charityService;

    @Autowired
    CharityVolunteerService charityVolunteerService;

    @Autowired
    AccountService accountService;

    @Autowired
    CharityMapper charityMapper;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public CharityVolunteerDTO toDTO(CharityVolunteer charityVolunteer) {
        if (charityVolunteer == null){
            return null;
        }

        CharityVolunteerDTO volunteerDTO = new CharityVolunteerDTO();
        volunteerDTO.setId(charityVolunteer.getId());
        volunteerDTO.setNote(charityVolunteer.getNote());
        volunteerDTO.setProgress(charityVolunteer.getProgress());

        Account account = charityVolunteer.getAccount();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        volunteerDTO.setAccount(accountDTO);

        if (charityVolunteer.getCharity() != null) {
            volunteerDTO.setCharityId(charityVolunteer.getCharity().getId());
            volunteerDTO.setCharity(charityMapper.toDTO(charityVolunteer.getCharity()));
        }

        return volunteerDTO;
    }

    @Override
    public List<CharityVolunteerDTO> toListDTO(List<CharityVolunteer> charityVolunteerList) {
        if (charityVolunteerList == null) {
            return null;
        }
        List<CharityVolunteerDTO> list = new ArrayList<>(charityVolunteerList.size());
        for (CharityVolunteer volunteer : charityVolunteerList) {
            CharityVolunteerDTO volunteerDTO = toDTO(volunteer);
            if (volunteerDTO != null) {
                list.add(volunteerDTO);
            }
        }
        return list;
    }

    @Override
    public CharityVolunteer toEntity(CharityVolunteerDTO charityVolunteerDTO) {
        if(charityVolunteerDTO ==null){
            return null;
        }
        CharityVolunteer charityVolunteer = charityVolunteerService.findById(charityVolunteerDTO.getId());

        if (charityVolunteer == null)
            charityVolunteer = new CharityVolunteer();
        charityVolunteer.setNote(charityVolunteerDTO.getNote());
        charityVolunteer.setStatus(true);
        charityVolunteer.setProgress(charityVolunteerDTO.getProgress());
        if (charityVolunteerDTO.getCharityId() != 0) {
            charityVolunteer.setCharity(charityService.findById(charityVolunteerDTO.getCharityId()));
        }
        if (charityVolunteerDTO.getAccountId() !=0) {
            charityVolunteer.setAccount(accountService.findById(charityVolunteerDTO.getAccountId()));
        }

        return charityVolunteer;
    }

}
