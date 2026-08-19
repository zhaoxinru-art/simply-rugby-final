package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Assignment {
    private Long id;
    private Long volunteerId;
    private Long eventId;
    private String status; // ASSIGNED / COMPLETED / CANCELLED
    private LocalDateTime assignTime;
}