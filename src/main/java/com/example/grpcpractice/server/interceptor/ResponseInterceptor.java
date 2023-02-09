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

                        // TODO
                        //  아... 진짜 이거 어쩌지 리얼..
                        for (var headerKey : headers.keys()) {
                            log.info("metadata key : " + headerKey);
                            String metadataValue = headers.get(Metadata.Key.of(headerKey, Metadata.ASCII_STRING_MARSHALLER));
                            log.info("metadata value : " + metadataValue);

                            headers.put(Metadata.Key.of(headerKey, Metadata.ASCII_STRING_MARSHALLER), metadataValue);
                        }

                        headers.put(ServerHeaders.REQUEST_ID, ServerHeaders.CTX_REQUEST_ID.get());

//                        headers.put(ServerHeaders.REQUEST_ID, headers.get(ServerHeaders.REQUEST_ID));
                        super.sendHeaders(headers);
                    }
                }, headers
        );
    }
}
