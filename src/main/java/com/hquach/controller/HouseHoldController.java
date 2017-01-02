package com.hquach.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hquach.form.CategoryForm;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.Dropbox;
import com.hquach.model.HouseHold;
import com.hquach.model.User;
import com.hquach.repository.DropboxRepository;
import com.hquach.repository.HouseHoldRepository;
import com.hquach.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * House Hold Controller used for setting and sharing between among users in a house house
 * @author Hai Quach
 */
@Controller
@RequestMapping("/household")
public class HouseHoldController {
    @Autowired
    private HouseHoldRepository houseHoldRepository;
    @Autowired
    private DropboxRepository dropboxRepository;

    @RequestMapping(value = { "/category" }, method = RequestMethod.GET)
    public String category(Model model) {
        model.addAttribute("form", new CategoryForm());
        HouseHold houseHold = houseHoldRepository.getHouseHold();
        if (houseHold != null) {
            model.addAttribute("existingIncomes", houseHold.getIncomes());
            model.addAttribute("existingExpenses", houseHold.getExpenses());
        } else {
            model.addAttribute("errorMessage", "House Hold is not available");
        }
        return "category";
    }

    @ModelAttribute("iCategories")
    public String getIncomeCategories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(CashFlowConstant.getIncomes());
        } catch (JsonProcessingException ex) {
            return "";
        }
    }

    @ModelAttribute("eCategories")
    public String getExpenseCategories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(CashFlowConstant.getExpenses());
        } catch (JsonProcessingException ex) {
            return "";
        }
    }

    @RequestMapping(value = { "/category/add" }, method = RequestMethod.POST)
    public String addCategory(@Valid CategoryForm form, BindingResult result, Model model) {
        if (CashFlowConstant.isIncomeType(form.getType())) {
            houseHoldRepository.addIncomeCategory(form.getName(), form.getItem());
        } else if (CashFlowConstant.isExpenseType(form.getType())) {
            houseHoldRepository.addExpenseCategory(form.getName(), form.getItem());
        } else {
            throw new RuntimeException("Invalid category type - add");
        }
        return "redirect:category";
    }

    @RequestMapping(value = { "/category/income/remove" }, method = RequestMethod.GET)
    public String removeIncome(Model model, @RequestParam("type") String type, @RequestParam("name") String name) {
        houseHoldRepository.removeIncomeCategory(type, name);
        return "redirect:category";
    }

    @RequestMapping(value = { "/category/expense/remove" }, method = RequestMethod.GET)
    public String removeExpense(Model model, @RequestParam("type") String type, @RequestParam("name") String name) {
        houseHoldRepository.removeExpenseCategory(type, name);
        return "redirect:category";
    }

    @RequestMapping(value = { "/category/reset" }, method = RequestMethod.GET)
    public String resetCategory() {
        houseHoldRepository.resetCategory();
        return "redirect:category";
    }

    @RequestMapping(value = { "/dropbox" }, method = RequestMethod.GET)
    public String dropbox(Model model) {
        HouseHold houseHold = houseHoldRepository.getHouseHold();
        Dropbox dropbox;
        if (houseHold != null && houseHold.getDropbox() != null) {
            dropbox = houseHold.getDropbox();
        } else {
            dropbox = new Dropbox();
        }
        model.addAttribute("dropbox", dropbox);
        return "dropbox";
    }

    @RequestMapping(value = { "/dropbox" }, method = RequestMethod.POST)
    public String saveDropbox(@Valid Dropbox dropbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dropbox";
        }
        dropbox = dropboxRepository.getDropbox(dropbox.getToken());
        houseHoldRepository.saveDropbox(dropbox);
        return "redirect:dropbox";
    }
}
