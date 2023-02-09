package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.command.GetBalanceCommand;
import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.vo.BalanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultBankService implements BankService {
    private final GetBalanceCommand getBalanceCommand;
    @Override
    public BalanceDTO readBalance(BalanceVO balanceVO) {
        int accountId = balanceVO.accountId();
        int balance = this.getBalanceCommand.execute(accountId);
        return new BalanceDTO(balance);
    }
}
