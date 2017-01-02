package com.hquach.controller;

import com.hquach.form.CashSum;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.CashFlowItem;
import com.hquach.services.FinancialServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by HQ on 8/5/2016.
 */
@Controller
public class PublicController {
    @Autowired
    FinancialServices financialServices;
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
    public String home(Model model) {

        Double income = 0.0;
        Double expense = 0.0;
        Collection<CashSum> finance = financialServices.getTotalIncomes();
        for (CashSum item : finance) {
            if (CashFlowConstant.isExpenseType(item.getType())) {
                expense += item.getTotal();
            }
            if (CashFlowConstant.isIncomeType(item.getType())) {
                income += item.getTotal();
            }
        }
        model.addAttribute("income", Math.round(income * 100) / 100);
        model.addAttribute("expense", Math.round(expense * 100) / 100);
        model.addAttribute("revenue", Math.round((income - expense) * 100 / 100));
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
