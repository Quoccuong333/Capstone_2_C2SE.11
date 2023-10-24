package com.example.ctca.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charity_donation")
public class CharityDonation extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "image")
    private String image;

    @Column(name = "note")
    private String note;

    @Column(name = "progress")
    private String progress; // PENDING || CANCELED || APPROVED

    @Column(name = "status")
    private boolean status;

}
