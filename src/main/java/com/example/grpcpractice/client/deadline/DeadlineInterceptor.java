package com.example.grpcpractice.client.deadline;

import io.grpc.*;

import java.util.concurrent.TimeUnit;

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        // if custom deadline is set, pass it.
        if (callOptions.getDeadline() != null) return next.newCall(method, callOptions);

        return next.newCall(method, callOptions.withDeadline(Deadline.after(2, TimeUnit.SECONDS)));
    }
}
