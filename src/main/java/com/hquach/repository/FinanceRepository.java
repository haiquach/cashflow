package com.hquach.repository;

import com.hquach.Utils.DateUtils;
import com.hquach.form.CashSum;
import com.hquach.form.CategoriesSummary;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.CashFlowItem;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;

/**
 * The repository for cash flow handler, calculating the summary of cash flow
 * @author Hai Quach
 */
@Repository
public class FinanceRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    HouseHoldRepository houseHoldRepository;

    public CashFlowItem getItem(Object objectId, String userId) {
        Query query = Query.query(Criteria.where("_id").is(objectId).and("userId").is(userId));
        return mongoTemplate.findOne(query, CashFlowItem.class);
    }

    public void addItem(CashFlowItem item) {
        mongoTemplate.save(item);
        if (StringUtils.isNotBlank(item.getNote())) {
            if (item.isIncome()) {
                houseHoldRepository.addIncomeCategory(item.getCategory(), item.getNote());
            } else if (item.isExpense()) {
                houseHoldRepository.addExpenseCategory(item.getCategory(), item.getNote());
            }
        }
    }

    private Collection<CashFlowItem> getItems(String type, Collection<String> members, LocalDate startDate, LocalDate endDate) {
        Query query = Query.query(
                Criteria.where("userId").in(members)
                        .and("type").is(type)
                        .and("effective").gte(startDate).lte(endDate));
        query.with(new Sort(Sort.Direction.DESC, "effective")).limit(1000);
        return mongoTemplate.find(query, CashFlowItem.class);
    }

    public Collection<CashFlowItem> search(Collection<String> members, Date startDate, Date endDate,
                     Collection<String> incomes, Collection<String> expenses, Collection<String> notes) {
        Criteria criteria = Criteria.where("userId").in(members)
                .and("effective").gte(startDate).lte(endDate);
        Collection<String> categories = new ArrayList<String>();
        if (incomes != null && !incomes.isEmpty()) {
            categories.addAll(incomes);
        }
        if (expenses != null && !expenses.isEmpty()) {
            categories.addAll(expenses);
        }
        if (!categories.isEmpty()) {
            criteria.and("category").in(categories);
        }
        if (notes != null && !notes.isEmpty()) {
            criteria.and("note").in(notes);
        }
        Query query = Query.query(criteria);
        query.with(new Sort(Sort.Direction.DESC, "effective"));
        return mongoTemplate.find(query, CashFlowItem.class);
    }

    public Collection<CashFlowItem> getIncomes(Collection<String> members, LocalDate startDate, LocalDate endDate) {
        return getItems(CashFlowConstant.INCOME, members, startDate, endDate);
    }

    public Collection<CashFlowItem> getExpense(Collection<String> members, LocalDate startDate, LocalDate endDate) {
        return getItems(CashFlowConstant.EXPENSE, members, startDate, endDate);
    }

    public Collection<CashSum> getTotalIncomes(Collection<String> users) {

        Criteria criteria = Criteria.where("userId").in(users)
                .and("effective").gte(DateUtils.beginningThisYear()).lt(DateUtils.beginningNextYear());

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("type").sum("amount").as("total"),
                Aggregation.project("total").and("type").previousOperation()
                //,
                //Aggregation.sort(Sort.Direction.DESC, "total")
        );
        AggregationResults<CashSum> results = mongoTemplate.aggregate(aggregation, CashFlowItem.class, CashSum.class);
        return results.getMappedResults();
    }

    public void remove(Object objId, String userId) {
        Query query = Query.query(Criteria.where("_id").is(objId).and("userId").is(userId));
        mongoTemplate.remove(query, CashFlowItem.class);
    }

    public Collection<CashSum> getRevenueDetails(Collection<String> users) {

        Criteria criteria = Criteria.where("userId").in(users)
                .and("effective").gte(DateUtils.beginningThisYear()).lt(DateUtils.beginningNextYear());

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("type", "category").sum("amount").as("total"),
                Aggregation.project("total").andInclude("type", "category"),
                Aggregation.sort(Sort.Direction.DESC, "type", "category")
        );
        AggregationResults<CashSum> results = mongoTemplate.aggregate(aggregation, CashFlowItem.class, CashSum.class);
        return results.getMappedResults();
    }

    public Collection<CashSum> getCashFlowByMonthly(Collection<String> users) {
        Criteria criteria = Criteria.where("userId").in(users).and("effective").gte(DateUtils.beginningThisYear());
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project("type", "amount")
                        .andExpression("month(effective)").as("month").andExpression("year(effective)").as("year"),
                Aggregation.group("month", "year", "type").sum("amount").as("total"),
                Aggregation.sort(Sort.Direction.ASC, "year", "month", "type")
        );
        AggregationResults<CashSum> results = mongoTemplate.aggregate(aggregation, CashFlowItem.class, CashSum.class);
        return results.getMappedResults();
    }

    public Collection<CategoriesSummary> getCashFlowByCategory(Collection<String> users, String type) {
        Criteria criteria = Criteria.where("userId").in(users)
                .and("effective").gte(DateUtils.beginningThisYear())
                .and("type").is(type);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("type", "category").sum("amount").as("total"),
                Aggregation.sort(Sort.Direction.ASC, "total")
        );
        AggregationResults<CategoriesSummary> results = mongoTemplate.aggregate(aggregation, CashFlowItem.class,
                CategoriesSummary.class);
        return results.getMappedResults();
    }

}
