package com.hquach.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Search transaction form
 */
public class ReportForm {
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "Start Date is required")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @NotNull(message = "End Date is required")
    private LocalDate endDate;
    private String category;
    private Collection<String> tags;

    public ReportForm() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}
