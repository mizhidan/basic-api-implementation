package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    private UserDto userDto;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();
        userDto =
                UserDto.builder()
                        .voteNum(10)
                        .phone("12345678911")
                        .gender("male")
                        .email("a@b.com")
                        .age(22)
                        .userName("dj")
                        .build();
    }

    @Test
    public void should_register_user() throws Exception {
        User user = new User("dj","male",22,"abc@bcd.com","12345678911");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<UserDto> all = userRepository.findAll();
        assertEquals(1, all.size());
        assertEquals("dj", all.get(0).getUserName());
        assertEquals("abc@bcd.com", all.get(0).getEmail());
    }

    @Test
    public void should_get_user_by_id() throws Exception {
        userRepository.save(userDto);
        mockMvc.perform(get("/user/1"))
            .andExpect(jsonPath("$.userName", is("dj")))
            .andExpect(jsonPath("$.email", is("a@b.com")));
    }

    @Test
    public void should_delete_user_by_id() throws Exception {
        userRepository.save(userDto);
        UserDto userDto2 = UserDto.builder().voteNum(10).phone("12345678911").gender("male").email("a@b.com").age(22)
                        .userName("dj").build();
        userRepository.save(userDto2);
        mockMvc.perform(delete("/user/1")).andExpect(status().isOk());
        assertEquals(1,userRepository.findAll().size());
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

    @Test
    public void should_verify_is_add_user() throws Exception {
        String jsonString = "{\"eventName\":\"猪肉涨价啦\"," +
                "\"keyWord\":\"经济\"," +
                "\"user\": {\"username\":\"dj\", \"gender\":\"male\",\"age\":22,\"email\":\"abc@bac.com\",\"phone\":\"12345678911\"}}";
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[3].eventName", is("猪肉涨价啦")))
                .andExpect(jsonPath("$[3].keyWord", is("经济")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/list"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username",is("dj")))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[0].age",is(22)))
                .andExpect(jsonPath("$[0].email",is("abc@bac.com")))
                .andExpect(jsonPath("$[0].phone",is("12345678911")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_throw_method_invalid_user_exception() throws Exception {
        User user = new User("aaa", "male", 27, "abc", "123456789111");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }

}
