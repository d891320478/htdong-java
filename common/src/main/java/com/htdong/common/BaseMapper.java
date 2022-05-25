package com.htdong.common;

import java.util.List;

public interface BaseMapper<D, Q> {
    Long insert(D domain);

    int update(D domain);

    Long count(Q query);

    List<D> find(Q query);

    D get(Q query);

    int delete(Long id);
}