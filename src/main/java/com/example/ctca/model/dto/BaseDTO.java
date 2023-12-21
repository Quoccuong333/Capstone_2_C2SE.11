package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseDTO {
    private long id;
    private long version;
    private String createdOn;
    private String updatedOn;

}
