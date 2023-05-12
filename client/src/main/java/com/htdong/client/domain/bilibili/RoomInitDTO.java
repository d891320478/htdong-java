package com.htdong.client.domain.bilibili;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author htdong
 * @date 2023-05-12 14:24:55
 */
@Data
public class RoomInitDTO {
    
    public static final int NOT_START = 0;
    public static final int LIVING = 1;
    public static final int ROTATING = 2;

    @JsonProperty("room_id")
    private Long roomId;
    private Long uid;
    @JsonProperty("live_time")
    private Long liveTime;
    @JsonProperty("live_status")
    private Integer liveStatus;
}