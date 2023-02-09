package com.example.grpcpractice.client.metadata;


import io.grpc.Metadata;

public class ClientHeadersInvalid {
    private static final Metadata METADATA = new Metadata();

    static {
        METADATA.put(
                // key, value
                Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER),
                "fail-bank-client-secret"
        );
    }

    public static Metadata getClientToken() {
        return METADATA;
    }
}
