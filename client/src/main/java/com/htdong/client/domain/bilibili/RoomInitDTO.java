package com.htdong.client.domain.bilibili;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RoomInitDTO {
    
    public static final int NOT_START = 0;
    public static final int LIVING = 1;
    public static final int ROTATING = 2;

    @JsonProperty("room_id")
    private long roomId;
    private long uid;
    @JsonProperty("live_time")
    private long liveTime;
    @JsonProperty("live_status")
    private int liveStatus;
}