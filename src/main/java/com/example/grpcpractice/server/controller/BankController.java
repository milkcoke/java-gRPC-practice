package com.example.grpcpractice.server.controller;

import com.example.grpcpractice.proto.bank.*;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.service.BankService;
import com.example.grpcpractice.server.vo.AddBalanceVO;
import com.example.grpcpractice.server.vo.DeductBalanceVO;
import com.example.grpcpractice.server.vo.GetBalanceVO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService()
@RequiredArgsConstructor
public class BankController extends BankServiceGrpc.BankServiceImplBase {
    private final BankService bankService;
    // return type is void
    // since there's stream observer

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        // error channel : onError => 이때는 onComplete 호출할 필요 없음
        // data channel: onNext => 완료되면 onComplete 호출

        int reqAccountId = request.getAccountNumber();

        BalanceDTO balanceDTO = this.bankService.readBalance(new GetBalanceVO(reqAccountId));
        int amount = balanceDTO.amount();

        Balance balance = Balance.newBuilder()
                        .setAmount(amount)
                        .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void addBalance(BalanceAddRequest request, StreamObserver<Balance> responseObserver) {
        int reqAccountId = request.getAccountNumber();
        int reqAmount = request.getAmount();

        BalanceDTO balanceDTO = this.bankService.deposit(new AddBalanceVO(reqAccountId, reqAmount));
        int amount = balanceDTO.amount();

        Balance balance = Balance.newBuilder()
                .setAmount(amount)
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void deductBalance(BalanceDeductRequest request, StreamObserver<Balance> responseObserver) {
        int reqAccountId = request.getAccountNumber();
        int reqAmount = request.getAmount();

        BalanceDTO balanceDTO = this.bankService.withdraw(new DeductBalanceVO(reqAccountId, reqAmount));
        int amount = balanceDTO.amount();

        Balance balance = Balance.newBuilder()
                .setAmount(amount)
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
