package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.command.GetBalanceCommand;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.metadata.ServerHeaders;
import com.example.grpcpractice.server.vo.BalanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultBankService implements BankService {
    private final GetBalanceCommand getBalanceCommand;
    @Override
    public BalanceDTO readBalance(BalanceVO balanceVO) {
        // Get the value form current Context
        log.info("I'm in Default service, Request id : " + ServerHeaders.CTX_REQUEST_ID.get());
        int accountId = balanceVO.accountId();
        int balance = this.getBalanceCommand.execute(accountId);
        return new BalanceDTO(balance);
    }
}
