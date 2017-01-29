package com.hquach.common;

import com.hquach.model.Snapshot;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Optional;

/**
 * System data processor which its owner defined data structure.
 * @author Hai Quach
 */

public class SystemDataProcessor implements DataProcessor {

    private Snapshot snapshot;

    SystemDataProcessor(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    SystemDataProcessor(String[] array) {
        LocalDate date = LocalDate.parse(array[1], DateTimeFormatter.ofPattern(dateFormat()));
        String description = array[2];
        String category = array[4];
        String currencySymbol = array[5];
        Optional<Currency> currency = Optional.empty();
        if (currencySymbol != null) {
            currency = Currency.getAvailableCurrencies().stream().filter(c -> c.getSymbol().equals(currencySymbol)).findFirst();
        }
        BigDecimal amount = new BigDecimal(array[6].replaceAll("\"", "").replaceAll(",", ""));
        String account = array[10];
        this.snapshot = new Snapshot(category, date, amount, currency.orElse(null), description, null, account);
    }

    @Override
    public Snapshot build() {
        return snapshot;
    }

    @Override
    public String extract() {
        StringBuilder result = new StringBuilder();
        result.append("Category").append(",")
                .append("Date").append(",")
                .append("Amount").append(",")
                .append("Currency").append(",")
                .append("Description").append(",")
                .append("Tags");
        snapshot.getTransactions().forEach((k, v) -> {
            v.forEach(item -> result.append(k).append(",")
                .append(item.getDate()).append(",")
                .append(item.getAmount()).append(",")
                .append(item.getCurrency()).append(",")
                .append(item.getDescription()).append(",")
                .append(StringEscapeUtils.escapeCsv(StringUtils.join(item.getTags(), ",")))
            );
        });
        return result.toString();
    }
}
