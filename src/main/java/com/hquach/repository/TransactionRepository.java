package com.hquach.repository;

import com.hquach.model.Snapshot;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TransactionRepository {

    static final Logger LOG = LoggerFactory.getLogger(TransactionRepository.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Snapshot> getTransactions() {
        return mongoTemplate.find(Query.query(Criteria.where("userId").is(userRepository.getCurrentUser())), Snapshot.class);
    }

    public String saveTransaction(Snapshot snapshot) {
        try {
            Criteria criteria = Criteria.where("userId").is(userRepository.getCurrentUser())
                    .and("month").is(snapshot.getMonth())
                    .and("year").is(snapshot.getYear());
            Snapshot existing = mongoTemplate.findOne(Query.query(criteria), Snapshot.class);
            if (existing == null) {
                existing = snapshot.copySnapshot(userRepository.getCurrentUser());
            } else {
                LOG.debug(snapshot.getTransactions().toString());
                existing.addTransactions(snapshot.getTransactions());
            }
            mongoTemplate.save(existing);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return StringUtils.EMPTY;
    }
}
