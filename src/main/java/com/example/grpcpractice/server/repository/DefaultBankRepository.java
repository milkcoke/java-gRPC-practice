package com.example.grpcpractice.server.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class DefaultBankRepository implements BankRepository {

    /*
     * 1 : 10_000,
     * 2 : 20_000,
     * ...
     * 10: 100_000
     */
    private static final Map<Integer, Integer> accountBook = IntStream
            .rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(
                    Function.identity(),
                    v -> v * 10_000));

    public int findBalanceById(int accountId) {
        return accountBook.get(accountId);
    }

    public Integer addBalanceById(int accountId, int amount) {
        int remainBalance = accountBook.get(accountId);
        accountBook.put(accountId, remainBalance + amount);
        return accountBook.get(accountId);
    }

    public Integer deductBalanceById(int accountId, int amount) {
        int remainBalance = accountBook.get(accountId);

        if (remainBalance < amount) {
            throw new IllegalStateException("잔액이 부족해요");
        }

        accountBook.put(accountId, remainBalance - amount);
        return accountBook.get(accountId);
    }
}
