package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;

    private String title;
    private String description;
    private LocalDateTime eventTime;
    private String location;
    private String status; // UPCOMING / COMPLETED / CANCELLED

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}