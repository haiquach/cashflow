package com.hquach.repository;

import com.hquach.Utils.DateUtils;
import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    static final Logger LOG = LoggerFactory.getLogger(TransactionRepository.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    private final static String[] TRANSACTION_FIELDS = new String[] { "transactions.category", "transactions.amount",
            "transactions.date", "transactions.description", "transactions.account", "transactions.tags",
            "transactions.currency", "transactions.receipt"};
    private final static String TRANSACTION = "transactions";
    private final static String TRANSACTION_DATE = TRANSACTION + ".date";
    private final static String TRANSACTION_CATEGORY = TRANSACTION + ".category";
    private final static String TRANSACTION_TAGS = TRANSACTION + ".tags";
    private final static String TRANSACTION_AMOUNT = TRANSACTION + ".amount";

    public Collection<Snapshot> getTransactions() {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userRepository.getCurrentUser())), Snapshot.class);
    }

    public String saveTransaction(Transaction transaction) {
        try {
            Criteria criteria = Criteria.where("userId").is(userRepository.getCurrentUser())
                    .and("month").is(transaction.getDate().getMonthValue())
                    .and("year").is(transaction.getDate().getYear());
            Snapshot snapshot = mongoTemplate.findOne(Query.query(criteria), Snapshot.class);
            if (snapshot == null) {
                snapshot = Snapshot.createBlankSnapshot(transaction.getDate().getYear(),
                        transaction.getDate().getMonthValue(), userRepository.getCurrentUser());
            }
            snapshot.addTransaction(transaction);
            mongoTemplate.save(snapshot);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return StringUtils.EMPTY;
    }

    public Collection<String> getAmountPerCategory() {
        Criteria criteria = Criteria.where("userId").is(userRepository.getCurrentUser());
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.unwind(TRANSACTION),
                Aggregation.group(TRANSACTION_CATEGORY),
                Aggregation.project("_id").and("value").previousOperation(),
                Aggregation.sort(Sort.Direction.ASC, "_id"));
        AggregationResults<Map> results =
                mongoTemplate.aggregate(aggregation, Snapshot.class, Map.class);
        return results.getMappedResults().stream().map(l -> (String)l.get("value")).collect(Collectors.toList());
    }

    public Collection<Transaction> search(LocalDate start, LocalDate end, String category, Collection<String> tags) {
        Criteria firstCriteria = Criteria.where("userId").is(userRepository.getCurrentUser());
        List<Criteria> secondCriteria =  new ArrayList<>();
        secondCriteria.add(Criteria.where(TRANSACTION_DATE).gte(DateUtils.asDate(start)));
        secondCriteria.add(Criteria.where(TRANSACTION_DATE).lte(DateUtils.asDate(end)));
        if (StringUtils.isNotEmpty(category)) {
            secondCriteria.add(Criteria.where(TRANSACTION_CATEGORY).is(category));
        }
        if (tags != null && !tags.isEmpty()) {
            secondCriteria.add(Criteria.where(TRANSACTION_TAGS).all(tags));
        }
        Criteria[] matchCriteria = new Criteria[secondCriteria.size()];
        matchCriteria = secondCriteria.toArray(matchCriteria);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(firstCriteria),
                Aggregation.unwind(TRANSACTION),
                Aggregation.project(TRANSACTION).andExclude("_id"),
                Aggregation.match(new Criteria().andOperator(matchCriteria)),
                Aggregation.project(TRANSACTION_FIELDS),
                Aggregation.sort(Sort.Direction.DESC, TRANSACTION_DATE)
        );
        AggregationResults<Transaction> results = mongoTemplate.aggregate(aggregation, Snapshot.class, Transaction.class);
        return results.getMappedResults();
    }

    public Collection<Snapshot> getSnapshotInYear(int thisYear, int lastYear, int startMonth) {
        Criteria criteria = Criteria.where("userId").is(userRepository.getCurrentUser())
                .orOperator(
                        Criteria.where("year").is(thisYear),
                        Criteria.where("year").is(lastYear).and("month").gte(startMonth));
        return mongoTemplate.find(Query.query(criteria), Snapshot.class);
    }
}
