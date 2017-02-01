package com.hquach.controller;

import com.hquach.form.FileBucket;
import com.hquach.form.ReportForm;
import com.hquach.form.TransactionForm;
import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;
import com.hquach.repository.DropboxRepository;
import com.hquach.services.CashflowService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transactions controller.
 */
@Controller
@RequestMapping("/txn")
public class TransactionController {

    private final static Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private CashflowService cashflowService;

    @Autowired
    private DropboxRepository dropboxRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("txn", cashflowService.getTransactions());
        return "transaction";
    }

    @RequestMapping(value = "/newtxn", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("transaction", new TransactionForm());
        return "newtxn";
    }

    @ModelAttribute(name = "currencies")
    public Collection<Currency> getAllCurrencies() {
        return Currency.getAvailableCurrencies();
    }

    @RequestMapping(value = "/newtxn", method = RequestMethod.POST)
    public String processItem(@Valid @ModelAttribute("transaction")TransactionForm form,
                              BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "newtxn";
        }
        Transaction item = form.getTransaction();
        try {
            String path = dropboxRepository.upload(form.getFile());
            item.setReceipt(path);
        } catch (Exception ex) {
            FieldError dropboxError = new FieldError("file", "file", "Failed to upload receipt to dropbox");
            result.addError(dropboxError);
            return "newtxn";
        }
        cashflowService.addTransaction(item);
        model.addAttribute("message", "Successfully added transaction!!!");
        return "newtxn";
    }

    @ModelAttribute(name = "categories")
    public Collection<String> getCategories() {
        Collection<String> categories = new ArrayList();
        categories.add(StringUtils.EMPTY);
        categories.addAll(cashflowService.getCategories());
        return categories;
    }

    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public String statement(Model model) {
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "statement";
    }

    @RequestMapping(value = "/processStatement", method = RequestMethod.POST)
    public String importStatement(@Valid FileBucket fileBucket,
                                   BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "statement";
        }

        Collection<String> errors = cashflowService.readCsv(fileBucket.getFile().getInputStream());
        if (!errors.isEmpty()) {
            errors.forEach(error ->
                    result.addError(new FieldError("fileBucket","*", error))
            );
            return "statement";
        }
        return "redirect:/txn/list";
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
        model.addAttribute("result", cashflowService.search(report.getStartDate(), report.getEndDate(),
                report.getCategory(), report.getTags()));
        model.addAttribute("report", report);
        return "report";
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String download(@Valid ReportForm report, BindingResult result, Model model, HttpServletResponse response) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            model.addAttribute("report", report);
            return "report";
        }
        try {
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddyyyyHHmmss")) + ".csv";
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            cashflowService.writeCsv(response.getOutputStream(), report.getStartDate(), report.getEndDate(),
                    report.getCategory(), report.getTags());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("Unable to download report.", e);
        }
        return null;
    }

    @RequestMapping(value = "/revenue", method = RequestMethod.GET)
    public String revenue(Model model) {
        Collection<Snapshot> snapshots = cashflowService.getSnapshotInYear();
        Collection<Transaction> transactions = snapshots.stream().map(Snapshot::getTransactions)
                .flatMap(l -> l.stream()).collect(Collectors.toList());
        Double income = transactions.stream().filter(Transaction::isIncome).collect(
                Collectors.summingDouble(Transaction::getAbsoluteAmount));
        Double expense = transactions.stream().filter(Transaction::isExpense).collect(
                Collectors.summingDouble(Transaction::getAbsoluteAmount));
        model.addAttribute("totalIncomes", new BigDecimal(income).setScale(2, BigDecimal.ROUND_HALF_UP));
        model.addAttribute("totalExpenses", new BigDecimal(expense).setScale(2, BigDecimal.ROUND_HALF_UP));
        model.addAttribute("revenue", new BigDecimal(income - expense).setScale(2, BigDecimal.ROUND_HALF_UP));

        model.addAttribute("incomes", transactions.stream().filter(Transaction::isIncome).collect(
                Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAbsoluteAmount))));
        model.addAttribute("expenses", transactions.stream().filter(Transaction::isExpense).collect(
                Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAbsoluteAmount))));
        return "revenue";
    }
}
