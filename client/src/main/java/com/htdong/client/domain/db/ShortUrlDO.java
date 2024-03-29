package com.htdong.client.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htdong.common.db.domain.AbstractDbDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("short_url")
public class ShortUrlDO extends AbstractDbDO {
    public static final String DB_FIELD_SHORT_PATH = "short_path";
    public static final String DB_FIELD_REAL_URL = "real_url";

    private String shortPath;
    private String realUrl;
}