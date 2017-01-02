package com.hquach.controller;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.hquach.Utils.ExcelUtils;
import com.hquach.Validator.FileValidator;
import com.hquach.form.FileBucket;
import com.hquach.form.ReportForm;
import com.hquach.model.CashFlowItem;
import com.hquach.model.HouseHold;
import com.hquach.model.User;
import com.hquach.repository.HouseHoldRepository;
import com.hquach.repository.UserRepository;
import com.hquach.services.EmailService;
import com.hquach.services.FinancialServices;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HouseHoldRepository houseHoldRepository;

    @Autowired
    private FinancialServices financialServices;

    @Autowired
    FileValidator fileValidator;

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

        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new

            String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/

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
        return new String [] {"ROLE_ADMIN", "ROLE_HOUSE_HOLD", "ROLE_BROKER"};
    }

    @RequestMapping(value = { "/houseHold"}, method = RequestMethod.GET)
    public String houseHouse(Model model) {
        model.addAttribute("houseHold", new HouseHold());
        model.addAttribute("users", userRepository.findAllUsersAvailableForHouseHold());
        model.addAttribute("houseHolds", houseHoldRepository.findAll());
        return "houseHold";
    }

    @RequestMapping(value = { "/houseHold/add" }, method = RequestMethod.POST)
    public String saveHouseHold(@Valid HouseHold houseHold, BindingResult result,
                           ModelMap model) {
        if (result.hasErrors()) {
            return "houseHold";
        }
        houseHoldRepository.save(houseHold);
        return "redirect:houseHold";
    }

    @RequestMapping(value = { "/houseHold/{houseHoldId}" }, method = RequestMethod.GET)
    public String deleteHouseHold(@PathVariable String houseHoldId) {
        houseHoldRepository.delete(houseHoldId);
        return "redirect:houseHold";
    }

    @InitBinder("fileBucket")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String getSingleUploadPage(ModelMap model) {
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "import";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket,
                                   BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "import";
        }
        MultipartFile multipartFile = fileBucket.getFile();

        Collection<CashFlowItem> items = ExcelUtils.processFile(multipartFile.getInputStream());
        for (CashFlowItem item : items) {
            financialServices.addItem(item);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String export(Model model) {
        model.addAttribute("form", new ReportForm());
        return "export";
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String getFile(@Valid ReportForm form, BindingResult result, Model model, HttpServletResponse response) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "export";
        }
        try {
            String fileName = FastDateFormat.getInstance("MMddyyyyHHmmss").format(new Date()) + ".xls";
            response.setContentType("application/excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            ExcelUtils.exportFile(response.getOutputStream(),
                    financialServices.search(form.getStartDate(), form.getEndDate(), null, null, null));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
