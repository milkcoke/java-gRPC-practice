package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.command.GetBalanceCommand;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.vo.BalanceVO;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DefaultBankServiceProxy implements BankService {
    private final GetBalanceCommand getBalanceCommand;
    private final BankService bankService;

    /** Test 3 wait seconds
    */
    @Override
    public BalanceDTO readBalance(BalanceVO balanceVO) {
        int accountId = balanceVO.accountId();
        int balance = this.getBalanceCommand.execute(accountId);

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        return new BalanceDTO(balance);
    }
}
