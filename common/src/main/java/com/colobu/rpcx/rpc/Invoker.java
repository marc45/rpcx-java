package com.colobu.rpcx.rpc;

import com.colobu.rpcx.discovery.IServiceDiscovery;
import com.colobu.rpcx.rpc.impl.RpcInvocation;

import java.lang.reflect.Method;


/**
 * @author goodjava@qq.com
 */
public interface Invoker<T> extends Node {

    Class<T> getInterface();

    Result invoke(RpcInvocation invocation) throws RpcException;

    void setMethod(Method method);

    Method getMethod();

    void setInterface(Class clazz);

    /**
     * 服务发现
     * @return
     */
    default IServiceDiscovery serviceDiscovery() {
        return null;
    }

}
