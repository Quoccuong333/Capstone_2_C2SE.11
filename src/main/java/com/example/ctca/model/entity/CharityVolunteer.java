package com.example.ctca.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charity_volunteer")
public class CharityVolunteer extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @Column(name = "note")
    private String note;

    @Column(name = "progress")
    private String progress; // PENDING || CANCELED || APPROVED

    @Column(name = "status")
    private boolean status;

}
