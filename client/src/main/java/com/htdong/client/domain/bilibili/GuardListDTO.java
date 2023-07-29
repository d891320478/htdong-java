package com.htdong.client.domain.bilibili;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author htdong
 * @date 2023年7月29日 下午8:32:59
 */
@Data
public class GuardListDTO {

    private InfoDTO info;
    private List<ListDTO> list;
    private List<ListDTO> top3;

    @Data
    public static class InfoDTO {

        private Integer num;
        /**
         * 总页数
         */
        private Integer page;
        /**
         * 当前页
         */
        private Integer now;
    }

    @Data
    public static class ListDTO {
        private Long uid;
        private String username;
        @JsonProperty("guard_level")
        private Integer guardLevel;
    }
}