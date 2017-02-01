package com.hquach.common;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Optional;

/**
 * Default data processor
 */
public abstract class BaseDataProcessor implements DataProcessor {

    protected final static String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    protected final static String CSV_DELIMITER = ",";
    protected final static String NEW_LINE = "\n";
    protected final static String DATE = "Date";
    protected final static String DESCRIPTION = "Description";
    protected final static String CATEGORY = "Category";
    protected final static String CURRENCY = "Currency";
    protected final static String AMOUNT = "Amount";
    protected final static String TAGS = "Tags";
    protected final static String ACCOUNT = "Account";
    protected final static String RECEIPT = "Receipt";

    protected BaseDataProcessor() {
    }

    protected LocalDate getDate(String[] array) {
        return LocalDate.parse(array[getIndex(DATE)], getDateFormatPattern());
    }

    protected String getDescription(String[] array) {
        return array[getIndex(DESCRIPTION)];
    }

    protected String getCategory(String[] array) {
        return array[getIndex(CATEGORY)];
    }

    protected String getAmount(String[] array) {
        return array[getIndex(AMOUNT)];
    }

    protected String getAccount(String[] array) {
        return getString(array, getIndex(ACCOUNT));
    }

    protected String getString(String[] array, int index) {
        return array[index];
    }

    protected abstract int getIndex(String key);

    protected abstract DateTimeFormatter getDateFormatPattern();

    protected String prepCsv(String data) {
        return StringEscapeUtils.escapeCsv(StringUtils.trimToEmpty(data));
    }

    protected String prepCurrency(Currency data) {
        return data.getSymbol();
    }

    protected String prepMoney(Double money) {
        BigDecimal amount = new BigDecimal(money);
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    protected String prepDate(LocalDate date) {
        return date.format(getDateFormatPattern());
    }
}
