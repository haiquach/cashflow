package com.hquach.model;

import com.hquach.Validator.EffectiveDate;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * Created by HQ on 8/7/2016.
 */
@Document(collection = "cashItem")
public class CashFlowItem {

    public CashFlowItem(){}
    private CashFlowItem(String type, String userId) {
        this.type = type;
        this.userId = userId;
    }

    @Id
    private String id;

    @NotEmpty
    private String type;
    @NotEmpty
    private String category;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "Effective date is required.")
    @EffectiveDate
    private Date effective;
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "$#,###,###,00")
    @NotNull(message = "Amount is required.")
    @Range(min = 0, max = 100000000, message = "The amount doesn't seem right.")
    private Double amount;
    @NotEmpty
    private String userId;
    private String note;
    private String receipt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIncome() {
        return CashFlowConstant.isIncomeType(type);
    }

    public boolean isExpense() {
        return CashFlowConstant.isExpenseType(type);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getEffective() {
        return effective;
    }

    public void setEffective(Date effective) {
        this.effective = effective;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public boolean isYours() {
        return userId.equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
