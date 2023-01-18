package org.web.controller.topic;

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
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class TopicController {

    @Autowired
    private TopicFacade topicFacade;

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/addTopic")
    public ModelAndView displayAddTopic(@SessionAttribute UserForm userSession) {
        ModelAndView modelAndView = new ModelAndView("addTopic");
        TopicForm topicForm = new TopicForm();
        if (userSession.getRole().equals("admin")) {
            modelAndView.addObject(topicForm);
            modelAndView.addObject("userForm", userSession);
        } else {
            Set<TopicForm> listAllTopic = topicFacade.getAll();
            Set<TopicForm> listUserTopic = topicFacade.getListTopic(userSession.getId());
            Set<TopicForm> topics = new HashSet<>();
            AtomicBoolean isTopics = new AtomicBoolean(false);
            if (listAllTopic.size() != 0) {
                listAllTopic.forEach(topic -> {
                    if (!listUserTopic.contains(topic)) {
                        topics.add(topic);
                        isTopics.set(true);
                    }
                });
                if (!isTopics.get()){
                    modelAndView.addObject("topicNull", "Topics null");
                }
                modelAndView.addObject("userForm", userSession);
                modelAndView.addObject("topicForm", topicForm);
                modelAndView.addObject("topics", topics);
            } else {
                modelAndView.addObject("topicNull", "Topics null");
            }
        }
        return modelAndView;
    }

    @PostMapping("/addTopic")
    public ModelAndView addTopic(HttpServletRequest request, HttpServletResponse response,
                                 @ModelAttribute TopicForm topicForm,
                                 @SessionAttribute(required = false) UserForm userSession) throws IOException {
        ModelAndView modelAndView = new ModelAndView("welcome");
        UserForm resultUserSQL = userFacade.get(userSession.getId());
        topicForm.setNameTopic(topicForm.getNameTopic());

        if (resultUserSQL.getRole().equals("admin")) {
            try {
                Set<UserForm> users = new HashSet<>();
                users.add(resultUserSQL);
                topicForm.setUsers(users);
                topicFacade.save(topicForm);
                response.sendRedirect(request.getContextPath() + "/welcome");
            } catch (MyException e) {
                modelAndView.setViewName("addTopic");
                modelAndView.addObject("topicForm", new TopicForm());
                modelAndView.addObject("userForm", userSession);
                modelAndView.addObject("error", e.getMessage());
            }
        } else {
            try {
                UserForm resulSQLUserForm = userFacade.get(userSession.getId());
                TopicForm resulTopicForm = topicFacade.get(topicForm.getIdAddTopic());
                Set<UserForm> listUser = topicFacade.getListUser(resulTopicForm.getId());
                listUser.add(resulSQLUserForm);
                resulTopicForm.setUsers(listUser);
                topicFacade.update(resulTopicForm);
                response.sendRedirect(request.getContextPath() + "/welcome");
            } catch (MyException e) {
                modelAndView.setViewName("addTopic");
                modelAndView.addObject("userForm", userSession);
                modelAndView.addObject("topicForm", new TopicForm());
                modelAndView.addObject("error", e.getMessage());
            }
        }
        return modelAndView;
    }
}
