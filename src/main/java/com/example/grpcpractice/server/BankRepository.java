package com.example.grpcpractice.server;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class BankRepository {

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

    public int getBalanceByAccountId(int accountId) {
        return accountBook.get(accountId);
    }

    public Integer deposit(int accountId, int amount) {
        return accountBook.computeIfPresent(accountId, (k, v) -> v + amount);
    }

    public Integer withdraw(int accountId, int amount) {
        return accountBook.computeIfPresent(accountId, (k, v) -> v - amount);
    }
}
