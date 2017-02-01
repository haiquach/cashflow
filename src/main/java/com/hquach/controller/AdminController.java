package com.hquach.controller;

import com.hquach.model.User;
import com.hquach.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Locale;

/**
 * Administration controller
 *
 * @author Hai Quach
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        Collection<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
         * and applying it on field [sso] of Model class [User].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        if(userRepository.isUserExisted(user.getUserId())){
            FieldError ssoError =new FieldError("user", "userId",
                    messageSource.getMessage("non.unique.userId", new String[]{user.getUserId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }

        String password = RandomStringUtils.randomAlphanumeric(15);
        userRepository.save(user, password);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("message", "User " + user.getUserId()
                + "has been added successfully. Initial password is " + password);
        return "registration";
    }


    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{userId}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String userId, ModelMap model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-user-{userId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String userId) {
        if (result.hasErrors()) {
            return "registration";
        }

        userRepository.updateUser(userId, user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles());

        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("message", "User " + userId + " has been updated successfully.");
        return "registration";
    }


    /**
     * This method will delete an user by it's USERID value.
     */
    @RequestMapping(value = { "/delete-user-{userId}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String userId) {
        userRepository.deleteUser(userId);
        return "redirect:list";
    }

    /**
     * This method will delete an user by it's USERID value.
     */
    @RequestMapping(value = { "/reset-user-{userId}" }, method = RequestMethod.GET)
    public String resetUser(@PathVariable String userId, Model model) {
        String password = RandomStringUtils.randomAlphanumeric(15);
        userRepository.resetPassword(userId, password);
        model.addAttribute("message", "UserID " + userId + " has been reset password to " + password);
        Collection<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }

    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public String[] initializeProfiles() {
        return new String [] {"ROLE_ADMIN", "ROLE_BROKER"};
    }
}
