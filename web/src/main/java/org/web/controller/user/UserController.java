package org.web.controller.user;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import exception.MyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web.facades.TopicFacade;
import org.web.facades.UserFacade;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;
import validation.Validation;

import java.io.IOException;
import java.util.Set;


@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private TopicFacade topicFacade;

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
            userForm = userFacade.getByName(userForm.getUserName());
            request.getSession().setAttribute("userSession", userForm);
            response.sendRedirect(request.getContextPath() + "/welcome");
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
        try {
            new Validation.Builder().
                    validationPassSamePass2(userForm.getPassword(), userForm.getConfirmPassword()).build();
            userFacade.getRole(userForm);
            UserForm save = userFacade.save(userForm);
            request.getSession().setAttribute("userSession", save);
            modelAndView.addObject("userForm", save);
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
                                      @RequestParam(value = "idKey", required = false ,defaultValue = "0") Long idKey,
                                      HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("upDateUser");
        if (idKey != 0) {
            UserForm userForm = userFacade.get(idKey);
            request.getSession().setAttribute("userAndAdmin", userForm);
            modelAndView.addObject("userForm", userForm);
            modelAndView.addObject("role",userSession.getRole());
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
    public ModelAndView getUsersList(@RequestParam(value = "idKey", required = false) Long idKey) {
        ModelAndView modelAndView = new ModelAndView("users");
        UserDAO userDAO = new UserDAOImpl();
        if (idKey != null) {
            userFacade.delete(idKey);
        }
        if (userFacade.getListUsers().size() == 0) {
            modelAndView.addObject("userListNull", "Sorry your not lucky");
        }
        modelAndView.addObject("userList", userFacade.getListUsers());
        return modelAndView;
    }

    @GetMapping("/logout")
    public void setLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void updateDataByUser(UserForm userForm, UserForm resultUserSQL) throws MyException {
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
        }
    }
}
