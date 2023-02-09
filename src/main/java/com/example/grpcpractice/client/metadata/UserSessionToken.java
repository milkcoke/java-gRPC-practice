package com.example.grpcpractice.client.metadata;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class UserSessionToken extends CallCredentials {
    // Attach user data from the server side th
    private String jwt;

    public UserSessionToken(String jwt) {
        this.jwt = jwt;
    }

    // Whole process is executed asynchronously.
    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(()->{
            Metadata metadata = new Metadata();
            metadata.put(ClientHeaders.USER_TOKEN, this.jwt);
            applier.apply(metadata);
//            applier.fail();
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // may change in the future
    }
}
