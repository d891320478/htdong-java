package com.htdong.core.service.impl;

import java.math.BigInteger;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.htdong.core.service.RpcService;

@DubboService
@Service("rpcService")
public class RpcServiceImpl implements RpcService {

    @Override
    public BigInteger test(long a) {
        return BigInteger.valueOf(a);
    }
}