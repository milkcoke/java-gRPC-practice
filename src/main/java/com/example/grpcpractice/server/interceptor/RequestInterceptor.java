package com.example.grpcpractice.server.interceptor;

import com.example.grpcpractice.server.metadata.ServerHeaders;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RequestInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String requestId = headers.get(Metadata.Key.of("request_id", Metadata.ASCII_STRING_MARSHALLER));

        if (Objects.isNull(requestId)) {
            Status invalidStatus =  Status.UNAUTHENTICATED.withDescription("Should input request_id");
            call.close(invalidStatus, headers);
        }

        // ThreadLocal current thread only the information
        // it's safe to use
        Context context = Context.current().withValue(
                ServerHeaders.CTX_REQUEST_ID,
                requestId
        );

        return Contexts.interceptCall(context, call, headers, next);
//        return next.startCall(call, headers);
    }
}
