package com.hquach.form;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Hai Quach
 */
public class CategoriesSummary {

    private String category;
    private Double total;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getLabel() {
        return category;
    }

    public Double getValue() {
        return Math.round(total * 100.0) / 100.0;
    }
}
