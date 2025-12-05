package com.example.gameflix.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import com.example.gameflix.model.Account;
import com.example.gameflix.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean validateUser(Account account) {
        for (Account i : accountRepository.findAll()) {
            if (passwordEncoder.matches(account.getPassword(), i.getPassword()) && account.getUsername().equals(i.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void saveAccount(Account account) {this.accountRepository.save(account);}

    @Override
    public Account getAccountById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        Account account = null;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        } else {
            throw new RuntimeException("Account not found with id " + id);
        }
        return account;
    }

    @Override
    public void deleteAccountById(Long id) {this.accountRepository.deleteById(id);}

    @Override
    public Page<Account> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return accountRepository.findAll(pageable);
    }
}
