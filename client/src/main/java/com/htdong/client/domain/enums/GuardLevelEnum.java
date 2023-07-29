package com.htdong.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author htdong
 * @date 2023年7月29日 下午8:55:55
 */
@Getter
@AllArgsConstructor
public enum GuardLevelEnum {
    LEVEL1(1, "总督"),
    LEVEL2(2, "提督"),
    LEVEL3(3, "舰长"),;

    private int id;
    private String name;

    public static GuardLevelEnum getById(int id) {
        GuardLevelEnum[] vals = GuardLevelEnum.values();
        for (GuardLevelEnum iter : vals) {
            if (iter.id == id) {
                return iter;
            }
        }
        return null;
    }
}