package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Volunteer {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String skill;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String availableMon;
    private String availableTue;
    private String availableWed;
    private String availableThu;
    private String availableFri;
    private String availableSat;
    private String availableSun;
}