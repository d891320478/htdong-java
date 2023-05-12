package com.htdong.client.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htdong.common.db.domain.AbstractDbDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("not_start_live")
@NoArgsConstructor
public class NotStartLiveDO extends AbstractDbDO {
    public static final String DB_FIELD_ROOM_ID = "room_id";
    public static final String DB_FIELD_REMARK = "remark";

    private Long roomId;
    private String remark;

    public NotStartLiveDO(Long roomId, String remark) {
        super();
        this.roomId = roomId;
        this.remark = remark;
    }
}