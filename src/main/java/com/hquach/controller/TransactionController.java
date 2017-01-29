package com.hquach.controller;

import com.hquach.form.FileBucket;
import com.hquach.services.CashflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

/**
 * Transactions controller.
 */
@Controller
@RequestMapping("/txn")
public class TransactionController {

    @Autowired
    private CashflowService cashflowService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("txn", cashflowService.getTransactions());
        return "transaction";
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

        Collection<String> errors = cashflowService.parseCsv(fileBucket.getFile().getInputStream());
        if (!errors.isEmpty()) {
            errors.forEach(error ->
                    result.addError(new FieldError("fileBucket","*", error))
            );
            return "statement";
        }
        return "redirect:/";
    }
}
