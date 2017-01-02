package com.hquach.form;

import com.hquach.model.CashFlowConstant;
import org.springframework.format.annotation.NumberFormat;

/**
 * Created by hquach on 8/18/16.
 */
public class CashSum {

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "$#,###,###,00")
    private Double total;
    private String type;
    private String category;
    private int month;
    private int year;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
