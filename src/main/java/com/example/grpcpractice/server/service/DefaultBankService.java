package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.command.AddBalanceCommand;
import com.example.grpcpractice.server.command.DeductBalanceCommand;
import com.example.grpcpractice.server.command.GetBalanceCommand;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.metadata.ServerHeaders;
import com.example.grpcpractice.server.vo.AddBalanceVO;
import com.example.grpcpractice.server.vo.DeductBalanceVO;
import com.example.grpcpractice.server.vo.GetBalanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultBankService implements BankService {
    private final GetBalanceCommand getBalanceCommand;
    private final AddBalanceCommand addBalanceCommand;
    private final DeductBalanceCommand deductBalanceCommand;


    @Override
    public BalanceDTO readBalance(GetBalanceVO balanceVO) {
        // Get the value form current Context
        log.info("I'm in Default service, Request id : " + ServerHeaders.CTX_REQUEST_ID.get());
        int accountId = balanceVO.accountId();
        int balance = this.getBalanceCommand.execute(accountId);
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
