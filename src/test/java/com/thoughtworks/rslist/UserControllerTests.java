package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_user() throws Exception {
        User user = new User("dj", "male", 22, "abc@bac.com", "12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void user_name_should_less_than_8() throws Exception {
        User user = new User("djdddddddd", "male", 22, "abc@bac.com", "12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void user_age_should_between_18_and_100() throws Exception {
        User user = new User("aaa", "male", 17, "abc@bac.com", "12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void email_should_be_valid() throws Exception {
        User user = new User("aaa", "male", 27, "abc", "12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void phone_number_should_be_valid() throws Exception {
        User user = new User("aaa", "male", 27, "abc", "123456789111");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
