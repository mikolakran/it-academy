package org.web.controller.user;

import exception.MyException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.web.configurations.AppContext;
import org.web.configurations.WebMvcConfig;
import org.web.facades.UserFacade;
import org.web.forms.UserForm;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContext.class, WebMvcConfig.class})
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserFacade userFacade;

    private final long ID = 1;
    private final String USERNAME = "radfast1";
    private final String PASSWORD = "*s9C#nFSNx#A";
    private final String CONFIRMPASSWORD = "*s9C#nFSNx#A";
    private final String EMAIL = "nikolai1@gmail.com";
    private final String ROLE = "user";
    private final String ROLEADMIN = "admin";

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void displayLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .param("userName", USERNAME)
                        .param("password", PASSWORD)
                        .flashAttr("userForm", new UserForm()))
//                        .sessionAttr("userSession", new UserForm()))
                .andExpect(model().attribute("userForm", userFacade.getByName(USERNAME)))
                .andExpect(view().name("welcome"))
                .andReturn();
    }

    @Test
    public void displayRegistration() throws Exception {
        mockMvc.perform(get("/addUser"))
                .andExpect(view().name("addUser"))
                .andReturn();
    }

    public UserForm getUser(String name) throws MyException {
        return userFacade.getByName(name);
    }

    @Test
    @Ignore
    public void addUser() throws Exception {
        mockMvc.perform(post("/addUser")
                        .param("userName", USERNAME)
                        .param("password", PASSWORD)
                        .param("confirmPassword", CONFIRMPASSWORD)
                        .param("email", EMAIL)
                        .flashAttr("userForm", new UserForm()))
                .andExpect(model().attribute("userForm", userFacade.getByName(USERNAME)))
                .andExpect(view().name("welcome"))
                .andReturn();
    }
}