package com.hquach.form;

import com.hquach.model.Transaction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by haiquach on 1/29/17.
 */
public class TransactionForm {

    @NotNull
    private int type;
    @NotNull
    private String category;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull
    private LocalDate date;
    @NotNull
    private Double amount;
    private Currency currency;
    private String description;
    private Collection<String> tags;
    private String account;
    MultipartFile file;

    public TransactionForm() {
        this.date = LocalDate.now();
        this.currency = Currency.getInstance(Locale.US);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Transaction getTransaction() {
        BigDecimal payment = BigDecimal.valueOf(amount).setScale(2);
        if (type < 0) {
            payment = payment.negate();
        }
        return new Transaction(category, date, payment.doubleValue(), currency, description, tags, account, null);
    }
}
