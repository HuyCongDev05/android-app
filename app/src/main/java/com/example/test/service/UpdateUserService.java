package com.example.test.service;

import com.example.test.entity.Account;
import com.example.test.repository.AccountRepository;

import java.util.concurrent.CompletableFuture;

public class UpdateUserService {
    private final AccountRepository accountRepository = new AccountRepository();

    public CompletableFuture<Boolean> checkUpdateUserAsync(Account account) {
        return CompletableFuture.supplyAsync(() -> accountRepository.checkUpdateUser(account));
    }
}
