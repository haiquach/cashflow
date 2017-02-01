package com.hquach.controller;

import com.hquach.Utils.JsonUtils;
import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;
import com.hquach.services.CashflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This controller is used for processing login/logout and home page dashboard
 * @author Hai Quach
 */
@Controller
public class PublicController {
    private final static Logger LOG = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    CashflowService cashflowService;
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String home(Model model) {
        Collection<Snapshot> snapshots = cashflowService.getSnapshotInYear();
        Collection<Transaction> transactions = snapshots.stream().map(Snapshot::getTransactions)
                .flatMap(l -> l.stream()).collect(Collectors.toList());
        Double income = transactions.stream().filter(Transaction::isIncome).collect(
                Collectors.summingDouble(Transaction::getAbsoluteAmount));
        Double expense = transactions.stream().filter(Transaction::isExpense).collect(
                Collectors.summingDouble(Transaction::getAbsoluteAmount));
        model.addAttribute("income", new BigDecimal(income).setScale(2, BigDecimal.ROUND_HALF_UP));
        model.addAttribute("expense", new BigDecimal(expense).setScale(2, BigDecimal.ROUND_HALF_UP));
        model.addAttribute("revenue", new BigDecimal(income - expense).setScale(2, BigDecimal.ROUND_HALF_UP));

        model.addAttribute("jIncome", JsonUtils.getIncomesAsString(transactions));
        model.addAttribute("jExpense", JsonUtils.getExpenseAsString(transactions));
        model.addAttribute("jSummary", JsonUtils.getSnapshotAsString(snapshots));
        return "dashboard";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        return "accessDenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        if (isCurrentAuthenticationAnonymous()) {
        return "login";
        }
        return "redirect:/";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:login?logout";
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
