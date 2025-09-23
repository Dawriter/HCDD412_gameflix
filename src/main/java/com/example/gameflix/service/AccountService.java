package com.example.gameflix.service;

import com.example.gameflix.model.Account;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    void saveAccount(Account account);
    Account getAccountById(Long id);
    void deleteAccountById(Long id);
    Page<Account> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
