package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InternationalVolunteer {
    private Long id;
    private String name;
    private String country;
    private String language;
    private String passportNo;
    private String phone;
    private String status;
    private LocalDateTime createTime;
}