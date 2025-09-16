    package com.htdong.common.rpc.bili;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: bili.proto")
public interface BiliRpcService {
static final String JAVA_SERVICE_NAME = "com.htdong.common.rpc.bili.BiliRpcService";
static final String SERVICE_NAME = ".BiliRpcService";

    // FIXME, initialize Dubbo3 stub when interface loaded, thinking of new ways doing this.
    static final boolean inited = BiliRpcServiceDubbo.init();

    com.google.protobuf.BoolValue roomCanUseServer(com.google.protobuf.Int64Value request);

    CompletableFuture<com.google.protobuf.BoolValue> roomCanUseServerAsync(com.google.protobuf.Int64Value request);

    com.google.protobuf.Empty addNewGuard(com.htdong.common.rpc.bili.AddNewGuardRequest request);

    CompletableFuture<com.google.protobuf.Empty> addNewGuardAsync(com.htdong.common.rpc.bili.AddNewGuardRequest request);


}
