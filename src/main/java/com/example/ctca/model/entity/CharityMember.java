package com.example.ctca.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charity_member")
public class CharityMember extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "charity_id")
    private Charity charity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "role")
    private String role; // OWNER || MANAGER || GUEST

    @Column(name = "note")
    private String note;

    @Column(name = "progress")
    private String progress; // PENDING || CANCELED || APPROVED || COMPLETED

    @Column(name = "status")
    private boolean status;

}
