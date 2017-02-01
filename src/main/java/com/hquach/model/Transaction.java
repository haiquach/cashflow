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
    private Double amount;
    private Currency currency;
    private String description;
    private Collection<String> tags;
    private String account;
    private String receipt;

    public Transaction(){}

    public Transaction(String category, LocalDate date, Double amount, Currency currency, String description,
                       Collection<String> tags, String account, String receipt) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.tags = tags;
        this.account = account;
        this.receipt = receipt;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
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

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public boolean isIncome() {
        return amount > 0;
    }

    public boolean isExpense() {
        return amount < 0.0;
    }

    public Double getAbsoluteAmount() {
        return new BigDecimal(amount).abs().doubleValue();
    }
}
