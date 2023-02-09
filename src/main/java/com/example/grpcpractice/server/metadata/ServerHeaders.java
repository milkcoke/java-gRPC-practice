package com.example.grpcpractice.server.metadata;

import io.grpc.Context;
import io.grpc.Metadata;

public class ServerHeaders {
    public static final Metadata.Key<String> TOKEN = Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);

    // have to use same of implementation.
    public static final Metadata.Key<String> USER_TOKEN  = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    public static final Context.Key<String> CTX_REQUEST_ID = Context.key("request-id");
}
