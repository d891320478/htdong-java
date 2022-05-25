package com.htdong.client.domain.db;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class SuperDbDO {
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}