package com.example.grpcpractice.server.metadata;

import io.grpc.Context;
import io.grpc.Metadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerHeaders {

    public static final Metadata.Key<String> TOKEN = Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);

    // have to use same of implementation.
    public static final Metadata.Key<String> USER_TOKEN  = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> REQUEST_ID = Metadata.Key.of("request-id", Metadata.ASCII_STRING_MARSHALLER);

    public static final Context.Key<String> CTX_REQUEST_ID = Context.key("request-id");

    public static final Set<Metadata.Key<String>> REQUIRED_HEADER_SET = new HashSet<>(List.of(REQUEST_ID));
}
