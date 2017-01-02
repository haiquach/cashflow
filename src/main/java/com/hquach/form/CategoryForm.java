package com.hquach.form;

import com.hquach.model.CashFlowConstant;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by hquach on 8/20/16.
 */
public class CategoryForm {
    @NotEmpty(message = "Category type is required.")
    private String type;
    @NotEmpty(message = "Category name is required.")
    private String name;
    @NotEmpty(message = "Category item is required.")
    private String item;

    public CategoryForm() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
