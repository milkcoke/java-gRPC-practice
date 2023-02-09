package com.example.grpcpractice.client.metadata;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MetadataClientTest {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @DisplayName("클라이언트 토큰 검증 성공")
    @Test
    void successWithClientToken() {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientHeaders.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);

        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build();

        Balance balance = this.blockingStub
                .withCallCredentials(new UserSessionToken("bank-user-secret"))
                .getBalance(balanceCheckRequest);

        assertThat(balance.getAmount()).isEqualTo(2 * 10_000);
    }

    @DisplayName("클라이언트 토큰 검증 실패")
    @Test
    void failWithClientToken() {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientHeadersInvalid.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);


        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(3)
                .build();

        assertThrows(StatusRuntimeException.class, ()-> this.blockingStub.getBalance(balanceCheckRequest));
    }


    @DisplayName("User 토큰 검증 실패")
    @Test
    void invalidUserToken() {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientHeaders.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);


        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(3)
                .build();


        assertThrows(StatusRuntimeException.class, ()->this.blockingStub.withCallCredentials(new UserSessionToken("user-secret")).getBalance(balanceCheckRequest));    }


    @DisplayName("User 토큰 검증 성공")
    @Test
    void validUserToken() {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientHeaders.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);


        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(3)
                .build();


        Balance balance = this.blockingStub
                .withCallCredentials(new UserSessionToken("bank-user-secret"))
                .getBalance(balanceCheckRequest);
    }

}
