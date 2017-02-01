package com.hquach.services;

import com.hquach.common.DataFactory;
import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;
import com.hquach.repository.TransactionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
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

    public void addTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    Function<String, String> importTransactions = new Function<String, String>() {
        @Override
        public String apply(String data) {
            return transactionRepository.saveTransaction(DataFactory.getProcessor().readCsv(data));
        }
    };

    public Collection<String> readCsv(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().skip(1).map(importTransactions).filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void writeCsv(OutputStream outputStream, LocalDate start, LocalDate end,
                         String category, Collection<String> tags) throws IOException {
        DataFactory.getProcessor().writeCsv(outputStream, transactionRepository.search(start, end, category, tags));
    }

    public Collection<String> getCategories() {
        return transactionRepository.getAmountPerCategory();
    }

    public Collection<Transaction> search(LocalDate start, LocalDate end, String category, Collection<String> tags) {
        return transactionRepository.search(start, end, category, tags);
    }

    public Collection<Snapshot> getSnapshotInYear() {
        LocalDate now = LocalDate.now();
        LocalDate yearAgo = now.minusYears(1);
        return transactionRepository.getSnapshotInYear(now.getYear(), yearAgo.getYear(), yearAgo.getMonthValue());
    }
}
