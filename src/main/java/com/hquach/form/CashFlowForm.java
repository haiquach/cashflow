package com.hquach.form;

import com.hquach.Validator.EffectiveDate;
import com.hquach.model.CashFlowItem;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity class is used for user input for a specific transaction in cash flow stream.
 * @author Hai Quach
 */
public class CashFlowForm {
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
    private String note;
    MultipartFile file;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public CashFlowItem getItem() {
        CashFlowItem item = new CashFlowItem();
        item.setType(type);
        item.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        item.setAmount(amount);
        item.setCategory(category);
        item.setEffective(effective);
        item.setNote(note);
        return item;
    }
}
