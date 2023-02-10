package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.command.AddBalanceCommand;
import com.example.grpcpractice.server.command.DeductBalanceCommand;
import com.example.grpcpractice.server.command.GetBalanceCommand;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.vo.AddBalanceVO;
import com.example.grpcpractice.server.vo.DeductBalanceVO;
import com.example.grpcpractice.server.vo.GetBalanceVO;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DefaultBankServiceProxy implements BankService {
    private final GetBalanceCommand getBalanceCommand;
    private final AddBalanceCommand addBalanceCommand;
    private final DeductBalanceCommand deductBalanceCommand;
    private final BankService bankService;

    /** Test 3 wait seconds
    */
    @Override
    public BalanceDTO readBalance(GetBalanceVO balanceVO) {
        int balance = this.getBalanceCommand.execute(balanceVO.accountId());

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        return new BalanceDTO(balance);
    }

    @Override
    public BalanceDTO deposit(AddBalanceVO balanceVO) {
        int balance = this.addBalanceCommand.execute(balanceVO.accountId(), balanceVO.amount());
        return new BalanceDTO(balance);
    }

    @Override
    public BalanceDTO withdraw(DeductBalanceVO balanceVO) {
        int balance = this.deductBalanceCommand.execute(balanceVO.accountId(), balanceVO.amount());
        return new BalanceDTO(balance);
    }
}
