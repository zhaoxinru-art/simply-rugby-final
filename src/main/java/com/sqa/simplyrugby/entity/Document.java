package com.sqa.simplyrugby.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Document {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private Long volunteerId;
    private LocalDateTime uploadTime;
    private LocalDateTime expiryDate;
    private String remark;
}