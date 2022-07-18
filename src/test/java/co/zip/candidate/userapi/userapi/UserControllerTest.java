package co.zip.candidate.userapi.userapi;

import co.zip.candidate.userapi.controller.UserController;
import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller Tests
 * @author Yogesh P
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void createUserControllerTest() throws Exception{
        UserRequest userRequest = new UserRequest("Test","test@gmmail.com",4567.0,2532.0);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setExpenses(userRequest.getExpenses());
        when(userService.createUser(Mockito.any(UserRequest.class))).thenReturn(user);
        String jsonRequest = objectMapper.writeValueAsString(user);
        RequestBuilder requestBuilder = post("/api/v1/users")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        //assert check status code
        //assertNotNull(result.getResponse().getContentAsString());
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void getAllUsersTest() throws Exception{

        when(userService.getAllUsers()).thenReturn(Stream.of(new User(3, "Yogesh", "yogesh@gmail.com", 44000.0, 540.30),
                new User(4, "Tom", "tom@gmail.com", 43444.20, 452.21)).collect(Collectors.toList()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        MockHttpServletResponse response = result.getResponse();
        //check status
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void getUsersByIdControllerTest() throws Exception{
        when(userService.getUser(1)).thenReturn(new User(1, "Yogesh", "yogesh@gmail.com", 44000.0, 540.30));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        MockHttpServletResponse response = result.getResponse();
        //check status as 200
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }


}
