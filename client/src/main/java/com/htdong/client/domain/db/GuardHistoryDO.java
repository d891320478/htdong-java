package com.htdong.client.domain.db;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htdong.common.db.domain.AbstractDbDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author dht31261
 * @date 2023年10月13日 下午2:21:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("guard_history")
@NoArgsConstructor
public class GuardHistoryDO extends AbstractDbDO {
    public static final String DB_FIELD_BILI_UID = "bili_uid";
    public static final String DB_FIELD_BILI_NICK_NAME = "bili_nick_name";
    public static final String DB_FIELD_GUARD_LEVEL = "guard_level";
    public static final String DB_FIELD_ROOM_ID = "room_id";
    public static final String DB_FIELD_GMT_START = "gmt_start";

    private Long biliUid;
    private String biliNickName;
    /**
     * 1总督2提督3舰长
     * 
     * @see com.htdong.client.domain.enums.GuardLevelEnum
     */
    private Integer guardLevel;
    private Long roomId;
    private LocalDate gmtStart;
}