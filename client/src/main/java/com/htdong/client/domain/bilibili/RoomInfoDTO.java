package com.htdong.client.domain.bilibili;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author dht31261
 * @date 2023年12月11日 11:32:32
 */
@Data
public class RoomInfoDTO {
    @JsonProperty("room_id")
    private Long roomId;
    private Long uid;
}