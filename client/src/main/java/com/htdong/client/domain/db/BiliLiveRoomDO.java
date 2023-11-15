package com.htdong.client.domain.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htdong.common.db.domain.AbstractDbDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author dht31261
 * @date 2023年11月15日 下午5:29:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bili_live_room")
@NoArgsConstructor
public class BiliLiveRoomDO extends AbstractDbDO {
    public static final String DB_FIELD_ROOM_ID = "room_id";

    private Long roomId;
}