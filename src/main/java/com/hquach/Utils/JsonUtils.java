package com.hquach.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;
import org.apache.commons.collections.FastHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utilities class for parsing Object to JSON string
 * @author Hai Quach
 */
public class JsonUtils {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    public static String getIncomesAsString(Collection<Transaction> transactions) {
        Map incomeMap = transactions.stream().filter(Transaction::isIncome).collect(
                Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAbsoluteAmount)));
        Collection<Map<String, Object>> incomeObj = new ArrayList<>();
        incomeMap.forEach((k,v) -> {
            Map<String, Object> item = new FastHashMap();
            item.put("label", k);
            item.put("value", new BigDecimal(v.toString()).setScale(2, BigDecimal.ROUND_HALF_UP));
            incomeObj.add(item);
        });
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(incomeObj);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to parse json to string", e);
        }
        return null;
    }

    public static String getExpenseAsString(Collection<Transaction> transactions) {
        Map expenseMap = transactions.stream().filter(Transaction::isExpense).collect(
                Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAbsoluteAmount)));
        Collection<Map<String, Object>> expenseObj = new ArrayList<>();
        expenseMap.forEach((k,v) -> {
            Map<String, Object> item = new FastHashMap();
            item.put("label", k);
            item.put("value", new BigDecimal(v.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).abs());
            expenseObj.add(item);
        });
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(expenseObj);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to parse json to string", e);
        }
        return null;
    }

    public static String getSnapshotAsString(Collection<Snapshot> snapshots) {
        Collection<Map<String, Object>> summary = new ArrayList();
        snapshots.forEach((Snapshot s) -> {
            double income = s.getTransactions().stream().filter(Transaction::isIncome).collect(
                    Collectors.summingDouble(Transaction::getAbsoluteAmount));
            double expense = s.getTransactions().stream().filter(Transaction::isExpense).collect(
                    Collectors.summingDouble(Transaction::getAbsoluteAmount));
            Map<String, Object> month = new FastHashMap();
            month.put("label", LocalDate.of(s.getYear(), s.getMonth(), 1).format(DateTimeFormatter.ofPattern("MMM yyyy")));
            month.put("income", new BigDecimal(income).setScale(2, BigDecimal.ROUND_HALF_UP));
            month.put("expense", new BigDecimal(expense).setScale(2, BigDecimal.ROUND_HALF_UP).abs());
            summary.add(month);
        });
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(summary);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to parse json to string", e);
        }
        return null;
    }
}
