package com.hquach.common;

import com.hquach.model.Transaction;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * System data processor which its owner defined data structure.
 * @author Hai Quach
 */

public class SystemDataProcessor extends BaseDataProcessor {

    private static Map<String, Integer> mapping;
    static {
        Map<String, Integer> result = new HashMap<>();
        result.put(DATE, 1);
        result.put(DESCRIPTION, 2);
        result.put(CATEGORY, 4);
        result.put(CURRENCY, 5);
        result.put(AMOUNT, 6);
        result.put(TAGS, 8);
        result.put(ACCOUNT, 10);
        mapping = Collections.unmodifiableMap(result);
    }

    SystemDataProcessor() {
    }

    @Override
    protected int getIndex(String key) {
        return mapping.get(key);
    }

    @Override
    protected DateTimeFormatter getDateFormatPattern() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }

    @Override
    public Transaction readCsv(String data) {
        String[] array = data.split(REGEX);
        String currencySymbol = getString(array, getIndex(CURRENCY));
        Currency currency = Currency.getInstance(Locale.US);
        if (currencySymbol != null) {
            Optional<Currency> checkCurrency = Currency.getAvailableCurrencies().stream()
                    .filter(c -> c.getSymbol().equals(currencySymbol)).findFirst();
            if (checkCurrency.isPresent()) {
                currency = checkCurrency.get();
            }
        }
        String strAmount = getString(array, getIndex(AMOUNT)).replaceAll("\"", "").replaceAll(",", "");
        BigDecimal amount = new BigDecimal(strAmount).setScale(2);
        String strTags = getString(array, getIndex(TAGS));
        return new Transaction(getCategory(array), getDate(array), amount.doubleValue(), currency, getDescription(array),
                Arrays.asList(strTags.split(" ")), getAccount(array), null);
    }

    @Override
    public void writeCsv(OutputStream outputStream, Collection<Transaction> transactions) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(CATEGORY).append(CSV_DELIMITER)
                .append(DATE).append(CSV_DELIMITER)
                .append(AMOUNT).append(CSV_DELIMITER)
                .append(CURRENCY).append(CSV_DELIMITER)
                .append(DESCRIPTION).append(CSV_DELIMITER)
                .append(TAGS).append(CSV_DELIMITER)
                .append(ACCOUNT).append(CSV_DELIMITER)
                .append(RECEIPT).append(NEW_LINE);
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
}
