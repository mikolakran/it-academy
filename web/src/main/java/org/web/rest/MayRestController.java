package org.web.rest;

import exception.MyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.web.facades.TopicFacade;
import org.web.facades.UserFacade;
import org.web.forms.TopicForm;
import org.web.forms.UserForm;
import validation.Validation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class MayRestController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private TopicFacade topicFacade;

    @RequestMapping(value = "/addNewUser",method = RequestMethod.POST)
    public ResponseEntity<UserForm> addUser(HttpServletRequest request,
                                  @RequestBody() UserForm userForm) throws IOException {
        UserForm user;
        if (userForm.getMultipartFile().getBytes().length != 0) {
            userForm.setPhoto(userForm.getMultipartFile().getBytes());
        }
        try {
            new Validation.Builder().
                    validationPassSamePass2(userForm.getPassword(), userForm.getConfirmPassword()).build();
            userForm.setPassword(BCrypt.hashpw(userForm.getPassword(), BCrypt.gensalt(12)));
            userFacade.getRole(userForm);
            user = userFacade.save(userForm);
            request.getSession().setAttribute("userSession", user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (MyException e) {
            user = new UserForm();
            user.setError(e.getMessage());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @GetMapping("/mayUsers")
    public ResponseEntity<List<UserForm>> getUsers(){
        List<UserForm> listUsers = userFacade.getListUsers();
        return new ResponseEntity<>(listUsers,HttpStatus.OK);
    }

    @GetMapping("/mayTopic")
    public ResponseEntity<Set<TopicForm>> getTopic(){
        Set<TopicForm> listTopic = topicFacade.getListTopic(1L);
        return new ResponseEntity<>(listTopic,HttpStatus.OK);
    }

    @GetMapping("/myUser")
    public ResponseEntity<UserForm> getUser(@RequestParam("id")Long id){
        UserForm userForm = userFacade.get(id);
        return new ResponseEntity<>(userForm,HttpStatus.OK);
    }

    @GetMapping("/myUser/{id}")
    public ResponseEntity<UserForm> getUser2(@PathVariable("id")Long id){
        UserForm userForm = userFacade.get(id);
        return new ResponseEntity<>(userForm,HttpStatus.OK);
    }

    @GetMapping("/myTopic/{id}")
    public ResponseEntity<TopicForm> getTopic(@PathVariable("id")Long id){
        TopicForm topicForm = topicFacade.get(id);
        return new ResponseEntity<>(topicForm,HttpStatus.OK);
    }

    @GetMapping("/myTopic")
    public ResponseEntity<TopicForm> getTopic2(@RequestParam("id")Long id){
        TopicForm topicForm = topicFacade.get(id);
        return new ResponseEntity<>(topicForm,HttpStatus.OK);
    }
}
