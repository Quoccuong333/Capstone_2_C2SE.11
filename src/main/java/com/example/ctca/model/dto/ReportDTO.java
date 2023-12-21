package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReportDTO {
    private String startDate;
    private String endDate;

    private int totalApproved;
    private int totalPost;
    private int totalPending;
    private int totalCompleted;
    private int totalCharity;
    private int totalVolunteer;
    private boolean isSearch;
    private boolean isError;

}
