package com.web.controller;

import com.web.forms.TopicForm;
import com.web.forms.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;


import java.util.HashSet;
import java.util.Set;

@Controller
public class WelcomeController {

    @ModelAttribute("topics")
    public Set<TopicForm> getListTopicForm(){
        return new HashSet<>();
    }

    @GetMapping("/welcome")
    public ModelAndView displayWelcome(@SessionAttribute() UserForm userSession,
                                @SessionAttribute(value = "topics",required = false) Set<TopicForm> topics){
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("userForm",userSession);
        if (topics!=null) {
            modelAndView.addObject("topics",topics);
        }else {
            modelAndView.addObject("topicNull","Topics null");
        }
        return modelAndView;
    }

}
