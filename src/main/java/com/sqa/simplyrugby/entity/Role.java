package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Role {
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createTime;
}