package com.hquach.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * Created by hquach on 8/22/16.
 */
public class ReportForm {
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "Form date is required")
    private Date startDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "To date is required")
    private Date endDate;
    private Collection<String> incomes;
    private Collection<String> expenses;
    private Collection<String> notes;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Collection<String> getIncomes() {
        return incomes;
    }

    public void setIncomes(Collection<String> incomes) {
        this.incomes = incomes;
    }

    public Collection<String> getExpenses() {
        return expenses;
    }

    public void setExpenses(Collection<String> expenses) {
        this.expenses = expenses;
    }

    public Collection<String> getNotes() {
        return notes;
    }

    public void setNotes(Collection<String> notes) {
        this.notes = notes;
    }
}
