package com.htdong.client.domain.db;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class SuperDbDO {
    public static final String DB_FIELD_ID  = "id";
    public static final String DB_FIELD_GMT_CREATE  = "gmt_create";
    public static final String DB_FIELD_GMT_MODIFIED  = "gmt_modified";
    
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}