package org.web.controller.user;

import exception.MyException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.facades.UserFacade;
import org.web.forms.UserForm;
import validation.Validation;

import java.io.IOException;

@Controller
@RequestMapping("addUser")
public class RegistrationController {
    @Autowired
    private UserFacade userFacade;

/*    @GetMapping
    public ModelAndView displayRegistration(){
        ModelAndView modelAndView = new ModelAndView("addUser");
        UserForm userForm = new UserForm();
        modelAndView.addObject("userForm",userForm);
        return modelAndView;
    }*/

    @GetMapping
    @ModelAttribute
    public UserForm displayRegistration2(UserForm userForm){
        return userForm;
    }

    @PostMapping
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("userForm")UserForm userForm,Model model) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView("login");
        try {
            new Validation.Builder().
                    validationPassSamePass2(userForm.getPassword(),userForm.getConfirmPassword()).build();
            userFacade.getRole(userForm);
            userFacade.save(userForm);
            modelAndView.addObject("userForm",userForm);
            modelAndView.addObject("test1","Test1");
        } catch (MyException e) {
            modelAndView.setViewName("/addUser");
            modelAndView.addObject("userForm",new UserForm());
            modelAndView.addObject("error",e.getMessage());
            System.out.println("e = " + e);
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return modelAndView;
    }
    //*s9C#nFSNx#A
}
