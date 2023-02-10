package com.example.grpcpractice.server.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GlobalExceptionHandler {

    @GrpcExceptionHandler(CustomException.class)
    public StatusRuntimeException handleInvalidArgument(CustomException e) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("code", Metadata.ASCII_STRING_MARSHALLER), e.getCode()+"");
        metadata.put(Metadata.Key.of("message", Metadata.ASCII_STRING_MARSHALLER), e.getMessage());
        return Status.INVALID_ARGUMENT.asRuntimeException(metadata);
    }
}
