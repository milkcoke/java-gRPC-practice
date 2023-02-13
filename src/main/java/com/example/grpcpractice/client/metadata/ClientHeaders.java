package com.example.grpcpractice.client.metadata;


import com.example.grpcpractice.proto.error.CustomError;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;

public class ClientHeaders {
    private static final Metadata METADATA = new Metadata();
    public static final Metadata.Key<String> USER_TOKEN = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> REQUEST_ID = Metadata.Key.of("request-id", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<CustomError> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(CustomError.getDefaultInstance());



    static {
        METADATA.put(
                // key, value
                Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER),
                "bank-client-secret"
        );
    }

    public static Metadata getClientToken() {
        return METADATA;
    }
}
