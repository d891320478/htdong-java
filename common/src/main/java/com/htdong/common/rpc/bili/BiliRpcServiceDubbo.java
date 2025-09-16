    package com.htdong.common.rpc.bili;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: bili.proto")
public final class BiliRpcServiceDubbo {
private static final AtomicBoolean registered = new AtomicBoolean();

public static boolean init() {
    if (registered.compareAndSet(false, true)) {
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.Empty.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.Int64Value.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.google.protobuf.BoolValue.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            com.htdong.common.rpc.bili.AddNewGuardRequest.getDefaultInstance());
    }
    return true;
}

private BiliRpcServiceDubbo() {}

}
