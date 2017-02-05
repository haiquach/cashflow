package com.hquach.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Data mapping form
 * @author Hai Quach
 */
public class MappingForm {
    @NotEmpty
    String name;
    @Min(0)
    @Max(100)
    private Integer category;
    @NotNull
    @Min(0)
    @Max(100)
    private Integer date;
    @NotNull
    @Min(0)
    @Max(100)
    private Integer amount;
    @Min(0)
    @Max(999)
    private Integer currency;
    @Min(0)
    @Max(999)
    private Integer description;
    @Min(0)
    @Max(999)
    private Integer tags;
    @Min(0)
    @Max(999)
    private Integer account;
    @Min(0)
    @Max(999)
    private Integer receipt;

    public MappingForm() {
        this.category = 0;
        this.date = 1;
        this.amount = 2;
        this.currency = 3;
        this.description = 4;
        this.tags = 5;
        this.account = 6;
        this.receipt = 999;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getReceipt() {
        return receipt;
    }

    public void setReceipt(Integer receipt) {
        this.receipt = receipt;
    }
}
