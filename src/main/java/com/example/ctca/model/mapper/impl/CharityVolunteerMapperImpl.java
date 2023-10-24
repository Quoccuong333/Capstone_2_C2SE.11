package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CharityVolunteerDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.CharityVolunteer;
import com.example.ctca.model.mapper.CharityVolunteerMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharityVolunteerMapperImpl implements CharityVolunteerMapper {

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

}
