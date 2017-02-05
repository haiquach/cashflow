package com.hquach.model;

import com.hquach.common.DataProcessor;

/**
 * Created by haiquach on 2/4/17.
 */
public class DataMapping {

    public final static DataMapping DEFAULT_MAPPING = new DataMapping(0, 1, 2, 3, 4, 5, 6, 7);

    private Integer category;
    private Integer date;
    private Integer amount;
    private Integer currency;
    private Integer description;
    private Integer tags;
    private Integer account;
    private Integer receipt;

    public DataMapping(Integer category, Integer date, Integer amount, Integer currency, Integer description,
                       Integer tags, Integer account, Integer receipt) {
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.tags = tags;
        this.account = account;
        this.receipt = receipt;
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

    public boolean isValidMapping(String[] data) {
        int length = data.length;
        if (length <= amount || length <= date || length <= category) return false;
        if (currency < DataProcessor.SKIP_VALUE && length <= currency) return false;
        if (description < DataProcessor.SKIP_VALUE && length <= description) return false;
        if (tags < DataProcessor.SKIP_VALUE && length <= tags) return false;
        if (account < DataProcessor.SKIP_VALUE && length <= account) return false;
        if (receipt < DataProcessor.SKIP_VALUE && length <= receipt) return false;
        return true;
    }
}
