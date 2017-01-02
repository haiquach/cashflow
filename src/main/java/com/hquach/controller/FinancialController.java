package com.hquach.controller;

import com.dropbox.core.v2.files.FileMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hquach.form.*;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.CashFlowItem;
import com.hquach.model.HouseHold;
import com.hquach.repository.DropboxRepository;
import com.hquach.repository.HouseHoldRepository;
import com.hquach.services.FinancialServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Financial Cash Flow Controller
 * @author Hai Quach
 */
@Controller
@RequestMapping("/finance")
public class FinancialController {
    @Autowired
    FinancialServices financialServices;
    @Autowired
    HouseHoldRepository houseHoldRepository;
    @Autowired
    DropboxRepository dropboxRepository;

    @RequestMapping(value = "/incomes", method = RequestMethod.GET)
    public String income(Model model) {
        model.addAttribute("incomes", financialServices.getIncomes());
        return "incomes";
    }

    @RequestMapping(value = "/item/{type}", method = RequestMethod.GET)
    public String item(Model model, @PathVariable String type) {
        CashFlowForm form = new CashFlowForm();
        form.setType(type.toUpperCase());
        model.addAttribute("cashFlow", form);
        HouseHold houseHold = houseHoldRepository.getHouseHold();
        if (houseHold != null) {
            model.addAttribute("categories", houseHold.getCategoriesByType(type));
            model.addAttribute("lookupData", houseHold.getCategoriesAsString(type));
            model.addAttribute("dropbox", houseHold.getDropbox());
        } else {
            model.addAttribute("categories", CashFlowConstant.getCategoriesByType(type));
            model.addAttribute("lookupData", "{}");
            model.addAttribute("dropbox", null);
        }

        return "item";
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public String processItem(@Valid CashFlowForm form,
                                   BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("cashFlow", form);
            return "item";
        }
        CashFlowItem item = form.getItem();
        try {
            String path = dropboxRepository.upload(form.getFile());
            item.setReceipt(path);
        } catch (Exception ex) {
            FieldError dropboxError = new FieldError("file", "file", "Failed to upload receipt to dropbox");
            result.addError(dropboxError);
            model.addAttribute("cashFlow", form);
            return "item";
        }
        financialServices.addItem(item);
        return "redirect:/";
    }

    @RequestMapping(value = "/download/{objectId}", method = RequestMethod.GET)
    public String download(@PathVariable String objectId, HttpServletResponse response) {
        CashFlowItem item = financialServices.getItem(objectId);
        Path path = Paths.get(item.getReceipt());
        response.setHeader("Content-Disposition", "attachment; filename=" + path.getFileName().toString());
        try {
            FileMetadata fileMetadata = dropboxRepository.download(item.getReceipt(), response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/items/remove/{type}/{objectId}", method = RequestMethod.GET)
    public String remove(Model model, @PathVariable String objectId, @PathVariable String type) {
        financialServices.removeItem(objectId);
        if (CashFlowConstant.isIncomeType(type)) {
            return income(model);
        } else if (CashFlowConstant.isExpenseType(type)) {
            return expense(model);
        }
        return revenue(model);
    }

    @RequestMapping(value = "/expenses", method = RequestMethod.GET)
    public String expense(Model model) {
        model.addAttribute("expenses", financialServices.getExpenses());
        return "expenses";
    }

    @RequestMapping(value = "/revenue", method = RequestMethod.GET)
    public String revenue(Model model) {
        Collection<CashSum> incomes = new ArrayList();
        Collection<CashSum> expenses = new ArrayList();
        double totalIncomes = 0.0;
        double totalExpenses = 0.0;
        for (CashSum cashSum : financialServices.getRevenue()) {
            if (CashFlowConstant.isIncomeType(cashSum.getType())) {
                incomes.add(cashSum);
                totalIncomes += cashSum.getTotal();
            }
            if (CashFlowConstant.isExpenseType(cashSum.getType())) {
                expenses.add(cashSum);
                totalExpenses += cashSum.getTotal();
            }
        }
        model.addAttribute("incomes", incomes);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalIncomes", totalIncomes);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("revenue", totalIncomes - totalExpenses);
        return "revenue";
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Model model) {
        model.addAttribute("report", new ReportForm());
        return "report";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@Valid ReportForm report, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            model.addAttribute("report", report);
            return "report";
        }
        model.addAttribute("result", financialServices.search(report.getStartDate(), report.getEndDate(),
                report.getIncomes(), report.getExpenses(), report.getNotes()));
        model.addAttribute("report", report);
        return "report";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public @ResponseBody Collection<CashFlowSummary> getSummary() {
        Map<Integer, CashFlowSummary> data = new TreeMap();
        Collection<CashSum> summary = financialServices.getCashFlowByMonthly();
        for(CashSum cashSum : summary) {
            Integer key = cashSum.getMonth() + cashSum.getYear() * 100;
            CashFlowSummary cashFlowSummary = data.get(key);
            if (cashFlowSummary == null) {
                cashFlowSummary = new CashFlowSummary(cashSum.getMonth(), cashSum.getYear());
            }
            cashFlowSummary.setAmount(cashSum.getType(), cashSum.getTotal());
            data.put(key, cashFlowSummary);
        }
        return data.values();
    }

    @RequestMapping(value = "/summary/{type}", method = RequestMethod.GET)
    public @ResponseBody Collection<CategoriesSummary> getSummaryCategory(@PathVariable String type) {
        return financialServices.getCashFlowByCategory(type.toUpperCase());
    }
}
