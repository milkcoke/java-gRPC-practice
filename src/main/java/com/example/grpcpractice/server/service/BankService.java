package com.example.grpcpractice.server.service;

import com.example.grpcpractice.server.dto.BalanceDTO;
import com.example.grpcpractice.server.vo.BalanceVO;

public interface BankService {
    BalanceDTO readBalance(BalanceVO balanceVO);
}
