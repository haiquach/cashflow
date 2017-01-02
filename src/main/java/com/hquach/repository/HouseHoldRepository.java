package com.hquach.repository;

import com.hquach.model.Dropbox;
import com.hquach.model.HouseHold;
import com.hquach.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * House House Repository
 * @author Hai Quach
 */
@Repository
public class HouseHoldRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    public Collection<HouseHold> findAll() {
        return mongoTemplate.findAll(HouseHold.class);
    }

    public HouseHold findByHouseHoldId(String houseHoldId) {
        if (houseHoldId == null) {
            return null;
        }
        Query query = Query.query(Criteria.where("houseHoldId").is(houseHoldId));
        return mongoTemplate.findOne(query, HouseHold.class);
    }

    public void save(HouseHold houseHold) {
        mongoTemplate.save(houseHold);
        userRepository.updateHouseHold(houseHold);
    }

    public void delete(String houseHoldId) {
        mongoTemplate.remove(Query.query(Criteria.where("houseHoldId").is(houseHoldId)), HouseHold.class);
        userRepository.clearHouseHold(houseHoldId);
    }


    public HouseHold getHouseHold() {
        // mongodb 2.4 is not support $elemMatch causing return null house hold
        // https://jira.mongodb.org/browse/SERVER-11740
        //String currentUserId = userRepository.getCurrentUser();
        //Query query = Query.query(Criteria.where("members").elemMatch(Criteria.where("$eq").is(currentUserId)));
        if (userRepository.getCurrentUser() == null) {
            throw new SecurityException("No Authority Found.");
        }

        if (userRepository.getLoggedUser().getHouseHoldId() == null) {
            return null;
        }
        // have to find by Id that current user should hold houseHoldId
        return findByHouseHoldId(userRepository.getLoggedUser().getHouseHoldId());
    }

    public void addIncomeCategory(String name, String category) {
        HouseHold houseHold = getHouseHold();
        if (houseHold == null) {
            return;
        }

        Map<String, Collection<String>> incomeCategories = houseHold.getIncomes();
        if (incomeCategories == null) {
            incomeCategories = new HashMap<String, Collection<String>>();
        }
        Collection<String> categories = incomeCategories.get(name);
        if (categories == null) {
            categories = new ArrayList<String>();
        }
        if (categories.contains(category)) {
            return;
        }
        categories.add(category);
        incomeCategories.put(name, categories);
        houseHold.setIncomes(incomeCategories);
        mongoTemplate.save(houseHold);
    }

    public void addExpenseCategory(String name, String category) {
        HouseHold houseHold = getHouseHold();
        if (houseHold == null) {
            return;
        }
        Map<String, Collection<String>> expenseCategories = houseHold.getExpenses();
        if (expenseCategories == null) {
            expenseCategories = new HashMap<String, Collection<String>>();
        }
        Collection<String> categories = expenseCategories.get(name);
        if (categories == null) {
            categories = new ArrayList<String>();
        }
        if (categories.contains(category)) {
            return;
        }
        categories.add(category);
        expenseCategories.put(name, categories);
        houseHold.setExpenses(expenseCategories);
        mongoTemplate.save(houseHold);
    }

    public void removeIncomeCategory(String name, String category) {
        HouseHold houseHold = getHouseHold();
        if (houseHold == null) {
            return;
        }
        Map<String, Collection<String>> incomes = houseHold.getIncomes();
        if (incomes != null) {
            Collection<String> categories = incomes.get(name);
            if (categories != null) {
                categories.remove(category);
                incomes.put(name, categories);
                houseHold.setIncomes(incomes);
                mongoTemplate.save(houseHold);
            }
        }
    }

    public void removeExpenseCategory(String name, String category) {
        HouseHold houseHold = getHouseHold();
        if (houseHold == null) {
            return;
        }
        Map<String, Collection<String>> expenses = houseHold.getExpenses();
        if (expenses != null) {
            Collection<String> categories = expenses.get(name);
            if (categories != null) {
                categories.remove(category);
                expenses.put(name, categories);
                houseHold.setExpenses(expenses);
                mongoTemplate.save(houseHold);
            }
        }
    }

    public void resetCategory() {
        String currentUserId = userRepository.getCurrentUser();
        Query query = Query.query(Criteria.where("members").elemMatch(Criteria.where("$eq").is(currentUserId)));
        Update update = new Update();
        update.unset("incomes");
        update.unset("expenses");
        mongoTemplate.updateFirst(query, update, HouseHold.class);
    }

    public void saveDropbox(Dropbox dropbox) {
        HouseHold houseHold = getHouseHold();
        houseHold.setDropbox(dropbox);
        save(houseHold);
    }
}
