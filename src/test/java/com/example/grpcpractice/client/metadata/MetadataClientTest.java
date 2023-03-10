package com.example.grpcpractice.client.metadata;

import com.example.grpcpractice.proto.bank.Balance;
import com.example.grpcpractice.proto.bank.BalanceCheckRequest;
import com.example.grpcpractice.proto.bank.BalanceDeductRequest;
import com.example.grpcpractice.proto.bank.BankServiceGrpc;
import com.example.grpcpractice.proto.error.CustomError;
import io.grpc.*;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.MetadataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
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

    @DisplayName("잔액 부족 에러 메타데이터 뽑아내기")
    @Test
    void withdrawFailTest() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6443)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientHeaders.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);

        Metadata requestMetadata = new Metadata();
        requestMetadata.put(ClientHeaders.REQUEST_ID, "5");

        BalanceDeductRequest balanceCheckRequest = BalanceDeductRequest.newBuilder()
                .setAccountNumber(3)
                .setAmount(60_000)
                .build();

        try {
            Balance balance = this.blockingStub
                    .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(requestMetadata))
                    .withCallCredentials(new UserSessionToken("bank-user-secret"))
                    .deductBalance(balanceCheckRequest);
        } catch (Exception e) {
            Metadata responseMetadata = Status.trailersFromThrowable(e);
            CustomError errorResponse = responseMetadata.get(ProtoUtils.keyForProto(CustomError.getDefaultInstance()));

            log.error(errorResponse.getErrorMessage().toString());
            log.error(errorResponse.getMessage());
            log.error(errorResponse.getCode() + "");
        }


    }

}
