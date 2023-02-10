package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.vo.AddBalanceVO;
import com.example.grpcpractice.server.vo.DeductBalanceVO;
import com.example.grpcpractice.server.vo.GetBalanceVO;

public interface BankService {
    BalanceDTO readBalance(GetBalanceVO balanceVO);

    BalanceDTO deposit(AddBalanceVO balanceVO);

    BalanceDTO withdraw(DeductBalanceVO balanceVO);
}
