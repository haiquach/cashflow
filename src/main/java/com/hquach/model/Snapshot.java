package com.hquach.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;


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
    private Map<Integer, Collection<Transaction>> transactions;

    public Snapshot() {}

    private Snapshot(String userId, int year, int month,
                     Map<Integer, Collection<Transaction>> transactions) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.transactions = new HashMap<>(transactions);
    }

    public Snapshot(String category, LocalDate date, BigDecimal amount, Currency currency, String description,
                    Collection<String> tags, String account) {
        this.month = date.getMonthValue();
        this.year = date.getYear();
        addTransaction(category, date, amount, currency, description, tags, account);
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

    public Map<Integer, Collection<Transaction>> getTransactions() {
        return transactions;
    }

    public void addTransaction(String category, LocalDate date, BigDecimal amount, Currency currency,
                               String description, Collection<String> tags, String account) {
        if (year != date.getYear() || month != date.getMonthValue()) {
            throw new IllegalArgumentException("Adding incorrect date to snapshot!!!");
        }
        if (transactions == null) {
            transactions = new HashMap();
        }
        Collection<Transaction> list = transactions.get(date.getDayOfMonth());
        if (list == null) list = new ArrayList();
        list.add(new Transaction(category, date, amount, currency, description, tags, account));
        transactions.put(date.getDayOfMonth(), list);
    }

    public void addTransactions(Map<Integer, Collection<Transaction>> txns) {
        txns.forEach((k,v) ->
            v.forEach(item -> addTransaction(item.getCategory(), item.getDate(), item.getAmount(), item.getCurrency(),
                    item.getDescription(), item.getTags(), item.getAccount()))
        );
    }

    public Snapshot copySnapshot(String owner) {
        return new Snapshot(owner, year, month, transactions);
    }
}
