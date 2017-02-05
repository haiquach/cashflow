package com.hquach.common;

import com.hquach.model.DataMapping;
import com.hquach.model.Transaction;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Default data processor
 * @author Hai Quach
 */
public abstract class BaseDataProcessor implements DataProcessor {

    final static String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    final static String CSV_DELIMITER = ",";
    final static String NEW_LINE = "\n";
    public final static String DATE = "Date";
    public final static String DESCRIPTION = "Description";
    public final static String CATEGORY = "Category";
    public final static String CURRENCY = "Currency";
    public final static String AMOUNT = "Amount";
    public final static String TAGS = "Tags";
    public final static String ACCOUNT = "Account";
    public final static String RECEIPT = "Receipt";


    private DataMapping dataMapping;

    protected BaseDataProcessor(DataMapping mapping) {
        this.dataMapping = mapping;
    }

    public String validate(String data) {
        String[] array = data.split(REGEX);
        StringBuilder errors = new StringBuilder();
        if (!dataMapping.isValidMapping(array)) {
            errors.append("Invalid file format. The number of columns is less than the definition.");
            return errors.toString();
        }
        try {
            getDate(array);
        } catch (Exception ex) {
            errors.append("Invalid date: " + getString(array, dataMapping.getDate()) +
                    ". The format is " + getDateFormatPattern().toString());
        }
        try {
            getAmount(array);
        } catch (Exception ex) {
            errors.append("Invalid amount: " + getString(array, dataMapping.getAmount()));
        }
        String currency = getString(array, dataMapping.getCurrency());
        if (StringUtils.isNotEmpty(currency)) {
            if (getCurrency(array) == null) {
                errors.append("Invalid currency: " + getString(array, dataMapping.getCurrency()));
            }
        }
        return errors.toString();
    }

    public Transaction readCsv(String data) {
        String[] array = data.split(REGEX);
        return new Transaction(getCategory(array), getDate(array), getAmount(array), getCurrency(array),
                getDescription(array), getTags(array), getAccount(array), getReceipt(array));
    }

    public void writeCsv(OutputStream outputStream, Collection<Transaction> transactions) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(getCsvHeader());
        transactions.forEach((Transaction transaction) -> {
            builder.append(prepCsv(transaction.getCategory())).append(CSV_DELIMITER)
                    .append(prepDate(transaction.getDate())).append(CSV_DELIMITER)
                    .append(prepMoney(transaction.getAmount())).append(CSV_DELIMITER)
                    .append(prepCurrency(transaction.getCurrency())).append(CSV_DELIMITER)
                    .append(prepCsv(transaction.getDescription())).append(CSV_DELIMITER)
                    .append(prepCsv(StringUtils.join(transaction.getTags(), CSV_DELIMITER))).append(CSV_DELIMITER)
                    .append(prepCsv(transaction.getAccount())).append(CSV_DELIMITER)
                    .append(prepCsv(transaction.getReceipt())).append(NEW_LINE);
        });
        outputStream.write(builder.toString().getBytes());
    }

    protected LocalDate getDate(String[] array) {
        return LocalDate.parse(getString(array, dataMapping.getDate()), getDateFormatPattern());
    }

    protected Currency getCurrency(String[] array) {
        String currencySymbol = getString(array, dataMapping.getCurrency());
        Currency currency = null;
        if (currencySymbol != null) {
            Optional<Currency> checkCurrency = Currency.getAvailableCurrencies().stream()
                    .filter(c -> c.getSymbol().equals(currencySymbol)).findFirst();
            if (checkCurrency.isPresent()) {
                currency = checkCurrency.get();
            }
        }
        return currency;
    }

    protected String getDescription(String[] array) {
        return getString(array, dataMapping.getDescription());
    }

    protected String getCategory(String[] array) {
        return getString(array, dataMapping.getCategory());
    }

    protected Double getAmount(String[] array) {
        String amount = getString(array, dataMapping.getAmount()).replaceAll("\"", "").replaceAll(",", "");
        return new BigDecimal(amount).setScale(2).doubleValue();
    }

    protected Collection<String> getTags(String[] array) {
        String tags = getString(array, dataMapping.getTags());
        return tags == null ? null : Arrays.asList(tags.split(" "));
    }

    protected String getAccount(String[] array) {
        return getString(array, dataMapping.getAccount());
    }

    protected String getReceipt(String[] array) {
        return getString(array, dataMapping.getReceipt());
    }

    private String getString(String[] array, int index) {
        if (index == SKIP_VALUE) {
            return null;
        }
        return array[index];
    }

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

    protected static String getCsvHeader() {
        StringBuilder builder = new StringBuilder();
        builder.append(CATEGORY).append(CSV_DELIMITER)
                .append(DATE).append(CSV_DELIMITER)
                .append(AMOUNT).append(CSV_DELIMITER)
                .append(CURRENCY).append(CSV_DELIMITER)
                .append(DESCRIPTION).append(CSV_DELIMITER)
                .append(TAGS).append(CSV_DELIMITER)
                .append(ACCOUNT).append(CSV_DELIMITER)
                .append(RECEIPT).append(NEW_LINE);
        return builder.toString();
    }
}
