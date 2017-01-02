package com.hquach.model;

import java.util.*;

/**
 * Created by hquach on 8/16/16.
 */
public class CashFlowConstant {

    public static final String INCOME = "INC";
    public static final String EXPENSE = "EXP";

    private static Collection<String> incomes = new ArrayList<String>();
    private static Collection<String> expenses = new ArrayList<String>();

    private static final String GENERAL_INCOME = "General Income";
    private static final String SALARY = "Salary";
    private static final String BUSINESS = "Business";
    private static final String GIFT = "Gift";
    private static final String INVESTMENT = "Investment";

    private static final String GENERAL_EXPENSE = "General Expense";
    private static final String BILLS = "Bills";
    private static final String CLOTHES = "Clothes";
    private static final String FOOD = "Food";
    private static final String ENTERTAINMENT = "Entertainment";
    private static final String PERSONAL = "Personal";
    private static final String TRANSPORTATION = "Transportation";
    private static final String EDUCATION = "Education";
    private static final String HOME = "Home";
    private static final String MEDICAL = "Medical";
    private static final String INSURANCE = "Insurance";

    static {
        // income categories
        incomes.add(GENERAL_INCOME);
        incomes.add(SALARY);
        incomes.add(INVESTMENT);
        incomes.add(GIFT);
        incomes.add(BUSINESS);

        // expense categories
        expenses.add(GENERAL_EXPENSE);
        expenses.add(BILLS);
        expenses.add(CLOTHES);
        expenses.add(FOOD);
        expenses.add(ENTERTAINMENT);
        expenses.add(PERSONAL);
        expenses.add(TRANSPORTATION);
        expenses.add(EDUCATION);
        expenses.add(HOME);
        expenses.add(MEDICAL);
        expenses.add(INSURANCE);
    }

    public static Collection<String> getIncomes() {
        return Collections.unmodifiableCollection(incomes);
    }

    public static Collection<String> getExpenses() {
        return Collections.unmodifiableCollection(expenses);
    }

    public static boolean isIncomeType(String type) {
        return INCOME.equalsIgnoreCase(type);
    }

    public static boolean isExpenseType(String type) {
        return EXPENSE.equalsIgnoreCase(type);
    }

    public static Collection<String> getCategoriesByType(String type) {
        if(isIncomeType(type)) {
            return incomes;
        } else if (isExpenseType(type)) {
            return expenses;
        }
        return Collections.emptyList();
    }
}
