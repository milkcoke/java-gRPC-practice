package com.example.grpcpractice.server.metadata;

import io.grpc.Metadata;

public class ServerHeaders {
    public static final Metadata.Key<String> TOKEN = Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);
}
