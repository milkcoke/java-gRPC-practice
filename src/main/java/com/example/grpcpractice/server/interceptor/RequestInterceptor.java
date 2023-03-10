package com.example.grpcpractice.server.interceptor;

import com.example.grpcpractice.server.metadata.ServerHeaders;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;

import java.util.Objects;

@Slf4j
@Order(Integer.MAX_VALUE)
@GrpcGlobalServerInterceptor
public class RequestInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("[Request interceptor] on server side");
        String requestId = headers.get(ServerHeaders.REQUEST_ID);
        String clientToken = headers.get(ServerHeaders.CLIENT_TOKEN);

        // Validation
        if (Objects.isNull(requestId)) {
            Status invalidStatus =  Status.UNAUTHENTICATED.withDescription("Should input request_id");
            call.close(invalidStatus, headers);
        }

        // ThreadLocal current thread only the information
        // it's safe to use
        Context context = Context.current().withValues(
                ServerHeaders.CTX_REQUEST_ID, requestId,
                ServerHeaders.CTX_CLIENT_TOKEN, clientToken
        );

        return Contexts.interceptCall(context, call, headers, next);
    }
}
