package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Participation {
    private Long id;
    private Long volunteerId;
    private Long eventId;
    private String signStatus; // SIGNED / ABSENT / LATE
    private LocalDateTime signTime;
    private String remark;
}