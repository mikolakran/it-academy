package org.web.controller.user;

import exception.MyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web.facades.UserFacade;
import org.web.forms.UserForm;
import validation.Validation;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserFacade userFacade;

    @GetMapping("/login")
    public ModelAndView displayLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        UserForm userForm = new UserForm();
        modelAndView.addObject(userForm);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView getLogin(@ModelAttribute("userForm") UserForm userForm, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView("welcome");
        try {
            UserForm resultSQLUserForm = userFacade.getByName(userForm.getUserName());
            if (resultSQLUserForm.getPassword().equals(userForm.getPassword())) {
                if (resultSQLUserForm.getPhoto().length == 0) {
                    resultSQLUserForm.setPhoto(null);
                }
                request.getSession().setAttribute("userSession", resultSQLUserForm);
                response.sendRedirect(request.getContextPath() + "/welcome");
            } else {
                modelAndView.setViewName("login");
                modelAndView.addObject("userForm", new UserForm());
                modelAndView.addObject("error", "password not exist");
            }
        } catch (MyException e) {
            modelAndView.setViewName("login");
            modelAndView.addObject("userForm", new UserForm());
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/addUser")
    @ModelAttribute
    public UserForm displayRegistration(UserForm userForm) {
        return userForm;
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("userForm") UserForm userForm) throws IOException {
        ModelAndView modelAndView = new ModelAndView("welcome");
        if (userForm.getMultipartFile() != null) {
            userForm.setPhoto(userForm.getMultipartFile().getBytes());
        }
        try {
            new Validation.Builder().
                    validationPassSamePass2(userForm.getPassword(), userForm.getConfirmPassword()).build();
            userFacade.getRole(userForm);
            UserForm save = userFacade.save(userForm);
            if (save.getPhoto().length == 0) {
                save.setPhoto(null);
            }
            request.getSession().setAttribute("userSession", save);
            response.sendRedirect(request.getContextPath() + "/welcome");
        } catch (MyException e) {
            modelAndView.setViewName("addUser");
            modelAndView.addObject("userForm", new UserForm());
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/upDateUser")
    public ModelAndView displayUpDate(@SessionAttribute() UserForm userSession,
                                      @RequestParam(value = "idKey", required = false, defaultValue = "0") Long idKey,
                                      HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("upDateUser");
        if (idKey != 0) {
            UserForm userForm = userFacade.get(idKey);
            request.getSession().setAttribute("userAndAdmin", userForm);
            modelAndView.addObject("userForm", userForm);
            modelAndView.addObject("role", userSession.getRole());
        } else {
            modelAndView.addObject("userForm", userSession);
        }
        return modelAndView;
    }

    @PostMapping("/upDateUser")
    public ModelAndView getUpDate(@ModelAttribute UserForm userForm,
                                  HttpServletRequest request, HttpServletResponse response,
                                  @SessionAttribute(required = false) UserForm userAndAdmin,
                                  @SessionAttribute(required = false) UserForm userSession) throws IOException {
        ModelAndView modelAndView = new ModelAndView("welcome");
        UserForm resultUserSQL;
        if (userAndAdmin == null) {
            try {
                resultUserSQL = userFacade.get(userSession.getId());
                updateDataByUser(userForm, resultUserSQL);
                modelAndView.addObject("userForm", resultUserSQL);
                request.getSession().setAttribute("userSession", resultUserSQL);
                response.sendRedirect(request.getContextPath() + "/welcome");
            } catch (MyException e) {
                modelAndView.setViewName("upDateUser");
                modelAndView.addObject("userForm", userSession);
                modelAndView.addObject("error", e.getMessage());
            }
        } else {
            try {
                resultUserSQL = userFacade.get(userAndAdmin.getId());
                updateDataByUser(userForm, resultUserSQL);
                request.getSession().removeAttribute("userAndAdmin");
                response.sendRedirect(request.getContextPath() + "/welcome");
            } catch (MyException e) {
                modelAndView.setViewName("upDateUser");
                modelAndView.addObject("userForm", userFacade.get(userAndAdmin.getId()));
                modelAndView.addObject("error", e.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView getUsersList(@SessionAttribute(required = false) UserForm userSession,
                                     @RequestParam(value = "idKey", required = false) Long idKey) {
        ModelAndView modelAndView = new ModelAndView("users");
        if (idKey != null) {
            userFacade.delete(idKey);
        }
        if (userFacade.getListUsers().size() == 0) {
            modelAndView.addObject("userListNull", "Sorry your not lucky");
        }
        modelAndView.addObject("userForm", userSession);
        modelAndView.addObject("userList", userFacade.getListUsers());
        return modelAndView;
    }

    @GetMapping("/image")
    public void addImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserForm userForm = (UserForm) request.getSession().getAttribute("userSession");
        if (userForm.getPhoto().length != 0) {
            response.setContentType("image/jpg");
            response.getOutputStream().write(userForm.getPhoto());
        }
        response.getOutputStream().close();
    }

    private void updateDataByUser(UserForm userForm, UserForm resultUserSQL) throws MyException, IOException {
        if (resultUserSQL != null) {
            if (!resultUserSQL.getUserName().equals(userForm.getUserName())) {
                resultUserSQL.setUserName(userForm.getUserName());
                userFacade.update(resultUserSQL);
            }
            if (!userForm.getNewPassword().equals("")) {
                if (!resultUserSQL.getPassword().equals(userForm.getNewPassword())) {
                    resultUserSQL.setPassword(userForm.getNewPassword());
                    userFacade.update(resultUserSQL);
                }
            } else {
                if (!resultUserSQL.getPassword().equals(userForm.getPassword())) {
                    resultUserSQL.setPassword(userForm.getPassword());
                    userFacade.update(resultUserSQL);
                }
            }
            if (!resultUserSQL.getEmail().equals(userForm.getEmail())) {
                resultUserSQL.setEmail(userForm.getEmail());
                userFacade.update(resultUserSQL);
            }
            if (userForm.getMultipartFile().getBytes().length != 0) {
                resultUserSQL.setPhoto(userForm.getMultipartFile().getBytes());
                userFacade.update(resultUserSQL);
            } else {
                if (resultUserSQL.getPhoto().length==0) {
                    resultUserSQL.setPhoto(null);
                }
            }
        }
    }
}
