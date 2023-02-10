package com.example.grpcpractice.server.interceptor;

import com.example.grpcpractice.server.metadata.ServerHeaders;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// This is for forwarding metadata after copying data from client.
public class ResponseInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT>
                                                                             call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        return next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
                    @Override
                    public void sendHeaders(Metadata headers) {
                        log.info("[Response interceptor] forward headers!");
                        log.info(headers.toString());

                        headers.put(ServerHeaders.REQUEST_ID, ServerHeaders.CTX_REQUEST_ID.get());
                        headers.put(ServerHeaders.CLIENT_TOKEN, ServerHeaders.CTX_CLIENT_TOKEN.get());

                        super.sendHeaders(headers);
                    }
                }, headers
        );
    }
}
