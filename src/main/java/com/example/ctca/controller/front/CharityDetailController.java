package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityDonation;
import com.example.ctca.model.entity.CharityVolunteer;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CharityDonationMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.CharityVolunteerMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CharityDonationService;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.CharityVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"charity/detail"})
public class CharityDetailController {

    @Autowired
    private CharityService charityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CharityVolunteerService charityVolunteerService;

    @Autowired
    private CharityDonationService charityDonationService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CharityVolunteerMapper charityVolunteerMapper;

    @Autowired
    private CharityDonationMapper charityDonationMapper;

    @GetMapping(value = {"/{id}"})
    public String view(Model model, Authentication authentication, @PathVariable long id) {
        Charity charity = charityService.findById(id);
        if (charity == null) return "front/404_error";

        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account1 = customUserDetails.getAccount();
            model.addAttribute("isOwner", account1 != null && account1.getId() == charity.getOwner().getId());
        }

        Account account = accountService.findById(charity.getOwner().getId());
        List<CharityVolunteer> charityVolunteerList = charityVolunteerService.findAllByCharity(id);
        List<CharityDonation> charityDonations = charityDonationService.findAllByCharity(id);

        model.addAttribute("charityDTO", charityMapper.toDTO(charity));
        model.addAttribute("ownerDTO", accountMapper.toDTO(account));
        model.addAttribute("volunteerListDTO", charityVolunteerMapper.toListDTO(charityVolunteerList));
        model.addAttribute("donationListDTO", charityDonationMapper.toListDTO(charityDonations));
        model.addAttribute("totalVolunteer", charityVolunteerList.size());
        model.addAttribute("totalDonation", charityDonations.size());

        return "front/charity_detail";
    }

}
