package com.hquach.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hquach.form.CategoryForm;
import com.hquach.form.ResetForm;
import com.hquach.model.CashFlowConstant;
import com.hquach.model.User;
import com.hquach.repository.UserRepository;
import com.hquach.services.EmailService;
import org.apache.commons.lang3.StringUtils;
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
    private EmailService emailService;

    @Autowired
    MessageSource messageSource;

    //@Autowired
    //AuthenticationTrustResolver authenticationTrustResolver;

    private static final String ERROR_MESSAGE = "errorMessage";

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    /*
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }*/

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
}
