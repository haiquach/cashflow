package com.hquach.controller;

import com.hquach.form.MappingForm;
import com.hquach.form.ResetForm;
import com.hquach.model.Dropbox;
import com.hquach.model.User;
import com.hquach.repository.DropboxRepository;
import com.hquach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * User Controller
 * @author Hai Quach
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DropboxRepository dropboxRepository;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = { "/profile" }, method = RequestMethod.GET)
    public String profile(Model model) {
        model.addAttribute("self", userRepository.getLoggedUser());
        return "profile";
    }

    @RequestMapping(value = { "/reset" }, method = RequestMethod.GET)
    public String resetPassword(HttpServletRequest request, ModelMap model) {
        model.addAttribute("resetForm", new ResetForm());
        return "reset";
    }

    @RequestMapping(value = { "/processPassword" }, method = RequestMethod.POST)
    public String processPassword(@Valid ResetForm form, BindingResult result,
                                  HttpServletRequest request, ModelMap model) {
        User loggedUser = userRepository.getLoggedUser();
        if (loggedUser == null) {
            return "redirect:login";
        }

        String password = form.getNewPassword();
        String confPass = form.getConfirmPassword();
        if (password == null || confPass == null || !confPass.equals(password)) {
            FieldError error = new FieldError("password", "confirmPassword",
                    messageSource.getMessage("password.not.match", new String[]{}, Locale.getDefault()));
            result.addError(error);
            return "reset";
        }

        userRepository.save(loggedUser, password);

        model.addAttribute("message", "Your password has been changed.");
        model.addAttribute("resetForm", new ResetForm());
        return "reset";
    }

    @RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
    public String settings(Model model) {
        model.addAttribute("user", userRepository.getLoggedUser());
        return "settings";
    }

    @RequestMapping(value = { "/update/{userId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             Model model, @PathVariable String userId) {
        if (result.hasErrors()) {
            return "settings";
        }

        userRepository.updateUser(userId, user.getFirstName(), user.getLastName(), user.getEmail(), null);
        model.addAttribute("user", user);
        model.addAttribute("message", "Your profile has been updated successfully.");
        return "settings";
    }

    @RequestMapping(value = { "/dropbox" }, method = RequestMethod.GET)
    public String dropbox(Model model) {
        model.addAttribute("dropbox", dropboxRepository.getDropbox(userRepository.getLoggedUser().getDropboxToken()));
        return "dropbox";
    }

    @RequestMapping(value = { "/dropbox" }, method = RequestMethod.POST)
    public String saveDropbox(@Valid Dropbox dropbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "dropbox";
        }

        dropbox = dropboxRepository.getDropbox(dropbox.getToken());
        if (dropbox == null) {
            FieldError tokenError =new FieldError("dropbox", "token",
                    "Invalid Dropbox Access Token");
            result.addError(tokenError);
            return "dropbox";
        }
        userRepository.saveDropbox(dropbox.getToken());
        return "redirect:dropbox";
    }

    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String listData(Model model) {
        model.addAttribute("data", userRepository.getLoggedUser().getDataDefined());
        return "list";
    }

    @RequestMapping(value = { "/data" }, method = RequestMethod.GET)
    public String importData(Model model) {
        model.addAttribute("data", new MappingForm());
        return "import";
    }

    @RequestMapping(value = { "/data/remove-{key}" }, method = RequestMethod.GET)
    public String removeData(Model model, @PathVariable String key) {
        userRepository.removeDataMapping(key);
        model.addAttribute("data", userRepository.getLoggedUser().getDataDefined());
        return "list";
    }

    @RequestMapping(value = { "/data" }, method = RequestMethod.POST)
    public String processData(@Valid @ModelAttribute("data") MappingForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "import";
        }

        User user = userRepository.getLoggedUser();
        String name = form.getName();
        if (user.getDataDefined(name) != null) {
            FieldError nameError =new FieldError("data", "name",
                    "You already had the same name in your mapping configuration.");
            result.addError(nameError);
            return "import";
        }
        userRepository.addDataMapping(form.getName(), form.getCategory(), form.getDate(), form.getAmount(),
                form.getCurrency(), form.getDescription(), form.getTags(), form.getAccount(), form.getReceipt());
        return "redirect:list";
    }
}
