package com.example.grpcpractice.json;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JsonBalance {
    private int amount;

    JsonBalance(int amount) {
        this.amount = amount;
    }
}
