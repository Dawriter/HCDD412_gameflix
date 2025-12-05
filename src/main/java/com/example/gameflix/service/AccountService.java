package com.example.gameflix.service;

import com.example.gameflix.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public boolean validateUser(Account account);

    List<Account> getAllAccounts();
    void saveAccount(Account account);
    Account getAccountById(Long id);
    void deleteAccountById(Long id);
    Page<Account> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
