package com.example.grpcpractice.server.interceptor;

import com.example.grpcpractice.server.metadata.ServerHeaders;
import io.grpc.*;

import java.util.Objects;

public class AuthInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String clientToken = headers.get(ServerHeaders.TOKEN);

        if (!this.isValidToken(clientToken)) {
            Status invalidStatus =  Status.UNAUTHENTICATED.withDescription("Invalid token");
            call.close(invalidStatus, headers);
            // 여기서 리턴타입 Listener 무조건 해야하나?
        }

        return next.startCall(call, headers);
    }

    private boolean isValidToken(String token) {
        return Objects.nonNull(token) && token.equals("bank-client-secret");
    }
}
