package com.example.grpcpractice.server.interceptor;

import com.example.grpcpractice.server.metadata.ServerHeaders;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class AuthInterceptor implements ServerInterceptor {
    private final static String VALID_CLIENT_TOKEN = "bank-client-secret";
    private final static String VALID_USER_TOKEN = "bank-user-secret";

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String clientToken = headers.get(ServerHeaders.TOKEN);
        String userToken = headers.get(ServerHeaders.USER_TOKEN);

        if (!this.isValidClient(clientToken)) {
            Status invalidStatus =  Status.UNAUTHENTICATED.withDescription("Invalid client token");
            call.close(invalidStatus, headers);
            // 여기서 리턴타입 Listener 무조건 해야하나?
        } else if (!this.isValidUser(userToken)) {
            Status invalidStatus =  Status.UNAUTHENTICATED.withDescription("Invalid user token");
            call.close(invalidStatus, headers);
        }

        return next.startCall(call, headers);
    }

    private boolean isValidClient(String clientToken) {
        return Objects.nonNull(clientToken) && clientToken.equals(VALID_CLIENT_TOKEN);
    }

    private boolean isValidUser(String userToken) {
        return Objects.nonNull(userToken) && userToken.equals(VALID_USER_TOKEN);
    }
}
