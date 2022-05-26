package com.htdong.client.domain.db;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public abstract class SuperDbDO {
    public static final String DB_FIELD_ID = "id";
    public static final String DB_FIELD_GMT_CREATE = "gmt_create";
    public static final String DB_FIELD_GMT_MODIFIED = "gmt_modified";

    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}