package org.web.controller.topic;

import entity.User;
import exception.MyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.web.facades.TopicFacade;
import org.web.facades.UserFacade;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class TopicController {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/addTopic")
    public ModelAndView displayAddTopic(@SessionAttribute UserForm userSession){
        ModelAndView modelAndView = new ModelAndView("addTopic");
        TopicForm topicForm = new TopicForm();
        modelAndView.addObject(topicForm);
        modelAndView.addObject("userForm",userSession);
        return modelAndView;
    }

    @PostMapping("/addTopic")
    public ModelAndView addTopic(HttpServletRequest request, HttpServletResponse response,
                                 @ModelAttribute TopicForm topicForm,
                                 @SessionAttribute(required = false)UserForm userSession) throws IOException {
        ModelAndView modelAndView = new ModelAndView("welcome");
        UserForm resultUserSQL = userFacade.get(userSession.getId());
        topicForm.setNameTopic(topicForm.getNameTopic());
        Set<UserForm> users = new HashSet<>();
        users.add(resultUserSQL);
        topicForm.setUsers(users);
        try {
            topicFacade.save(topicForm);
            Set<TopicForm> listTopic = topicFacade.getListTopic(userSession.getId());
            request.getSession().setAttribute("topics",listTopic);
            response.sendRedirect(request.getContextPath() + "/welcome");
        } catch (MyException e) {
            modelAndView.setViewName("addTopic");
            modelAndView.addObject("topicForm", new TopicForm());
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;
    }
}
