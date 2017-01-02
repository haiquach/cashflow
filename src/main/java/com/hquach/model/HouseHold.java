package com.hquach.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * House Hold Settings
 * @author Hai Quach
 */
@Document(collection = "houseHold")
public class HouseHold {
    @Id
    private String houseHoldId;
    private String name;
    private Collection<String> members;
    private Map<String, Collection<String>> incomes;
    private Map<String, Collection<String>> expenses;
    private Dropbox dropbox;

    public HouseHold() {
        houseHoldId = RandomStringUtils.randomAlphanumeric(30);
    }

    public String getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(String houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getMembers() {
        return members;
    }

    public void setMembers(Collection<String> members) {
        this.members = members;
    }

    public Map<String, Collection<String>> getIncomes() {
        return incomes;
    }

    public void setIncomes(Map<String, Collection<String>> incomes) {
        this.incomes = incomes;
    }

    public Map<String, Collection<String>> getExpenses() {
        return expenses;
    }

    public void setExpenses(Map<String, Collection<String>> expenses) {
        this.expenses = expenses;
    }

    public Dropbox getDropbox() {
        return dropbox;
    }

    public void setDropbox(Dropbox dropbox) {
        this.dropbox = dropbox;
    }

    private String parseObjectToString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return "{}";
        }
    }

    public Collection<String> getCategoriesByType(String type) {
        if (CashFlowConstant.isIncomeType(type)) {
            return incomes.keySet();
        } else if (CashFlowConstant.isExpenseType(type)) {
            return expenses.keySet();
        }
        return Collections.emptyList();
    }

    public String getCategoriesAsString(String type) {
        if (CashFlowConstant.isIncomeType(type)) {
            return parseObjectToString(incomes);
        } else if (CashFlowConstant.isExpenseType(type)) {
            return parseObjectToString(expenses);
        }
        return "{}";
    }
}
