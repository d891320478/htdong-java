package com.htdong.client.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author htdong
 * @date 2023年7月29日 下午8:28:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllGuardDTO {

    private Long uid;
    private String userName;
    /**
     * 1总督2提督3舰长
     * 
     * @see com.htdong.client.domain.enums.GuardLevelEnum
     */
    private Integer guardLevel;
}