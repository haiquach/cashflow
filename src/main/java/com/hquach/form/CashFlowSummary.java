package com.hquach.form;

import com.hquach.model.CashFlowConstant;
import org.springframework.format.annotation.NumberFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Cash Flow Summary contains information for a summary of transaction in period of time.
 */
public class CashFlowSummary {

    private int month;
    private int year;
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "$#,###,###,00")
    private Double income;
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "$#,###,###,00")
    private Double expense;

    public CashFlowSummary(int month, int year) {
        this.month = month;
        this.year = year;
        this.income = 0.0;
        this.expense = 0.0;
    }

    public Double getIncome() {
        return Math.round(income * 100.0) / 100.0;
    }

    public Double getExpense() {
        return Math.round(expense * 100.0) / 100.0;
    }

    public String getLabel() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month - 1, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
        return formatter.format(calendar.getTime());
    }

    public void setAmount(String type, Double amount) {
        if (CashFlowConstant.isIncomeType(type)) {
            income = amount;
        } else if (CashFlowConstant.isExpenseType(type)) {
            expense = amount;
        } else {
            throw new RuntimeException("Invalid cash flow type");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashFlowSummary)) return false;

        CashFlowSummary that = (CashFlowSummary) o;

        if (month != that.month) return false;
        if (year != that.year) return false;
        if (expense != null ? !expense.equals(that.expense) : that.expense != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = month;
        result = 31 * result + year;
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (expense != null ? expense.hashCode() : 0);
        return result;
    }
}
