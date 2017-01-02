package com.hquach.services;

import com.hquach.Utils.DateUtils;
import com.hquach.form.CashSum;
import com.hquach.form.CategoriesSummary;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.CashFlowItem;
import com.hquach.model.HouseHold;
import com.hquach.model.User;
import com.hquach.repository.FinanceRepository;
import com.hquach.repository.HouseHoldRepository;
import com.hquach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * This class handles almost business logic in cash flow transactions such as add/delete transaction
 * OR get a collection of transaction in a period of time.
 * @author Hai Quach
 */
@Service
public class FinancialServices {
    @Autowired
    private FinanceRepository financeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HouseHoldRepository houseHoldRepository;

    public CashFlowItem getItem(Object objectId) {
        return financeRepository.getItem(objectId, userRepository.getCurrentUser());
    }

    public CashFlowItem addItem(CashFlowItem item) {
        financeRepository.addItem(item);
        return item;
    }

    public void removeItem(Object objId) {
        financeRepository.remove(objId, userRepository.getCurrentUser());
    }

    public Collection<CashFlowItem> getIncomes() {
        return financeRepository.getIncomes(getMembers(),
                DateUtils.getBeginThisYear(), DateUtils.getBeginNextYear());
    }

    public Collection<CashFlowItem> search(Date startDate, Date endDate,
                           Collection<String> types, Collection<String> categories, Collection<String> notes) {
        return financeRepository.search(getMembers(), startDate, endDate, types, categories, notes);
    }

    public Collection<CashFlowItem> getExpenses() {
        return financeRepository.getExpense(getMembers(),
                DateUtils.getBeginThisYear(), DateUtils.getBeginNextYear());
    }

    public Collection<CashSum> getTotalIncomes() {
        return financeRepository.getTotalIncomes(getMembers());
    }

    private Collection<String> getMembers() {
        User user = userRepository.getLoggedUser();
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        // if user has not set up house hold, then only he/she is member of house hold
        if (user.getHouseHoldId() == null) {
            return Arrays.asList(new String[]{user.getUserId()});
        }
        HouseHold houseHold = houseHoldRepository.findByHouseHoldId(user.getHouseHoldId());
        return houseHold.getMembers();
    }

    public Collection<CashSum> getRevenue() {
        return financeRepository.getRevenueDetails(getMembers());
    }

    public Collection<CashSum> getCashFlowByMonthly() {
        return financeRepository.getCashFlowByMonthly(getMembers());
    }

    public Collection<CategoriesSummary> getCashFlowByCategory(String type) {
        return financeRepository.getCashFlowByCategory(getMembers(), type);
    }
 }
