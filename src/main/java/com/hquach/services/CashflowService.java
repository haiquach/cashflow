package com.hquach.services;

import com.hquach.common.DataFactory;
import com.hquach.model.Snapshot;
import com.hquach.repository.FinanceRepository;
import com.hquach.repository.TransactionRepository;
import com.hquach.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CashflowService {

    @Autowired
    TransactionRepository transactionRepository;

    public Collection<Snapshot> getTransactions() {
        return transactionRepository.getTransactions();
    }

    public Collection<String> parseCsv(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().skip(1).map(importTransactions).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    Function<String, String> importTransactions = new Function<String, String>() {
        @Override
        public String apply(String data) {
            return transactionRepository.saveTransaction(DataFactory.buildSnapshot(data));
        }
    };
}
