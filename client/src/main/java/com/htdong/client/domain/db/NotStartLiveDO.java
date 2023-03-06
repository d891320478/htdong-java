package com.htdong.client.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htdong.common.db.domain.SuperDbDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("not_start_live")
@NoArgsConstructor
public class NotStartLiveDO extends SuperDbDO {
    public static final String DB_FIELD_ROOM_ID = "room_id";

    private Long roomId;

    public NotStartLiveDO(Long roomId) {
        super();
        this.roomId = roomId;
    }
}