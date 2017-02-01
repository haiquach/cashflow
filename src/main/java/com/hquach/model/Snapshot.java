package com.hquach.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Cash flow snapshot which contains all income and expense transactions for a specific category within a month of year.
 *
 * @author Hai Quach
 */
@Document(collection = "snapshot")
public class Snapshot {
    @Id
    private String id;
    private String userId;
    private int month;
    private int year;
    private Collection<Transaction> transactions;

    public Snapshot() {}

    private Snapshot(String userId, int year, int month) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.transactions = new ArrayList<>();
    }

    public Snapshot(String category, LocalDate date, Double amount, Currency currency, String description,
                    Collection<String> tags, String account, String receipt) {
        this.month = date.getMonthValue();
        this.year = date.getYear();
        addTransaction(new Transaction(category, date, amount, currency, description, tags, account, receipt));
    }

    public String getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getUserId() {
        return userId;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public Collection<Transaction> getSortedByDateTransactions() {
        return transactions.stream().sorted((Transaction t1, Transaction t2) -> t2.getDate().compareTo(t1.getDate()))
                .collect(Collectors.toList());
    }

    public void addTransaction(Transaction transaction) {
        if (year != transaction.getDate().getYear() || month != transaction.getDate().getMonthValue()) {
            throw new IllegalArgumentException("Adding incorrect date to snapshot!!!");
        }
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }

    public static Snapshot createBlankSnapshot(int year, int month, String owner) {
        return new Snapshot(owner, year, month);
    }

    public String getDisplayDate() {
        return LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }
}
