package com.colobu.rpcx.rpc.impl;

import com.colobu.rpcx.config.Constants;
import com.colobu.rpcx.discovery.IServiceDiscovery;
import com.colobu.rpcx.netty.IClient;
import com.colobu.rpcx.protocol.*;
import com.colobu.rpcx.rpc.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author goodjava@qq.com
 */
public class RpcConsumerInvoker<T> implements Invoker<T> {

    private static final Logger logger = LoggerFactory.getLogger(RpcConsumerInvoker.class);

    private static final AtomicInteger seq = new AtomicInteger();

    private IClient client;

    private URL url;

    public RpcConsumerInvoker(IClient client) {
        this.client = client;
    }

    @Override
    public Class<T> getInterface() {
        return null;
    }

    @Override
    public Result invoke(RpcInvocation invocation) throws RpcException {
        String className = invocation.getClassName();
        String method = invocation.getMethodName();
        RpcResult result = new RpcResult();

        Message req = new Message(className, method);
        req.setVersion((byte) 0);
        req.setMessageType(MessageType.Request);
        req.setHeartbeat(false);
        req.setOneway(false);
        req.setCompressType(CompressType.None);
        req.setSerializeType(SerializeType.SerializeNone);
        req.metadata.put(Constants.LANGUAGE, LanguageCode.JAVA.name());
        req.metadata.put(Constants.SEND_TYPE, invocation.getSendType());
        invocation.setUrl(this.url);
        byte[] data = HessianUtils.write(invocation);
        req.payload = data;

        try {
            req.setSeq(seq.incrementAndGet());
            Message res = client.call(req, invocation.getTimeOut());
            if (res.metadata.containsKey("_rpcx_error_code")) {
                int code = Integer.parseInt(res.metadata.get("_rpcx_error_code"));
                String message = res.metadata.get("_rpcx_error_message");
                logger.warn("client call error:{}:{}", code, message);
                RpcException error = new RpcException(message, code);
                result.setThrowable(error);
            } else {
                byte[] d = res.payload;
                if (d.length > 0) {
                    Object r = HessianUtils.read(d);
                    result.setValue(r);
                }
            }
        } catch (Throwable e) {
            result.setThrowable(e);
            logger.info("client call error:{} ", e.getMessage());
        }

        logger.info("class:{} method:{} result:{} finish", className, method, new Gson().toJson(result));
        return result;
    }

    @Override
    public void setMethod(Method method) {

    }

    @Override
    public void setInterface(Class clazz) {

    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public IServiceDiscovery serviceDiscovery() {
        return this.client.getServiceDiscovery();
    }
}
