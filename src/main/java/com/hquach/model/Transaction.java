package com.hquach.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;

/**
 * Handle a single transaction in cash flow
 */
public class Transaction {
    private String category;
    private LocalDate date;
    private BigDecimal amount;
    private Currency currency;
    private String description;
    private Collection<String> tags;
    private String account;

    public Transaction(){}

    public Transaction(String category, LocalDate date, BigDecimal amount, Currency currency, String description,
                       Collection<String> tags, String account) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.tags = tags;
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAccount() {
        return account;
    }
}
