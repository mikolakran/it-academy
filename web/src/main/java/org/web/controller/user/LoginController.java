package org.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.web.forms.UserForm;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("userForm")UserForm userForm) {
        return "login";
    }

}
