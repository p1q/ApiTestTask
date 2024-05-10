package api.test.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import api.test.task.model.User;
import api.test.task.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Test
    public void test_CreateUser_ValidInput_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"mail@gmail.com\", \"firstName\": \"Serj\", \"lastName\": \"Petrenko\", \"birthdate\": \"2001-10-18\", \"address\": \"Vul, 2, Kiyv, Ukraine\", \"phoneNumber\": \"+380932541254\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("mail@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Serj"))
                .andExpect(jsonPath("$.lastName").value("Petrenko"))
                .andExpect(jsonPath("$.birthdate").value("2001-10-18"))
                .andExpect(jsonPath("$.address").value("Vul, 2, Kiyv, Ukraine"))
                .andExpect(jsonPath("$.phoneNumber").value("+380932541254"));
    }

    @Test
    public void testCreateUser_IncorrectOrAbsentInputData_ShouldReturn422() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"mailgmail.com\"}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreateUser_InvalidRequestBody_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers_ShouldReturnListOfAllUsers() throws Exception {
        User user1 = new User();
        user1.setId("ID1");
        user1.setEmail("mail@gmail.com");
        user1.setFirstName("Serj");
        user1.setLastName("Petrenko");
        user1.setBirthdate(LocalDate.of(2001, 10, 18));
        user1.setAddress("Vul, 2, Kiyv, Ukraine");
        user1.setPhoneNumber("+380932541254");

        User user2 = new User();
        user2.setId("ID2");
        user2.setEmail("alex@mail.ua");
        user2.setFirstName("Alex");
        user2.setLastName("Ivanenko");
        user2.setBirthdate(LocalDate.of(1995, 5, 3));
        user2.setAddress("Vul, 5, Kharkiv, Ukraine");
        user2.setPhoneNumber("+380971234567");

        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("ID1"))
                .andExpect(jsonPath("$[0].email").value("mail@gmail.com"))
                .andExpect(jsonPath("$[0].firstName").value("Serj"))
                .andExpect(jsonPath("$[0].lastName").value("Petrenko"))
                .andExpect(jsonPath("$[0].birthdate").value("2001-10-18"))
                .andExpect(jsonPath("$[0].address").value("Vul, 2, Kiyv, Ukraine"))
                .andExpect(jsonPath("$[0].phoneNumber").value("+380932541254"))
                .andExpect(jsonPath("$[1].id").value("ID2"))
                .andExpect(jsonPath("$[1].email").value("alex@mail.ua"))
                .andExpect(jsonPath("$[1].firstName").value("Alex"))
                .andExpect(jsonPath("$[1].lastName").value("Ivanenko"))
                .andExpect(jsonPath("$[1].birthdate").value("1995-05-03"))
                .andExpect(jsonPath("$[1].address").value("Vul, 5, Kharkiv, Ukraine"))
                .andExpect(jsonPath("$[1].phoneNumber").value("+380971234567"));
    }

    @Test
    public void testGetUser_ShouldReturnExactUser() throws Exception {
        User user = new User();
        user.setId("ID1");
        user.setEmail("mail@gmail.com");
        user.setFirstName("Serj");
        user.setLastName("Petrenko");
        user.setBirthdate(LocalDate.of(2001, 10, 18));
        user.setAddress("Vul, 2, Kiyv, Ukraine");
        user.setPhoneNumber("+380932541254");

        when(userService.get("ID1")).thenReturn(Optional.of(user));
        ResultActions resultActions = mockMvc.perform(get("/users/{userId}", "ID1"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ID1"))
                .andExpect(jsonPath("$.email").value("mail@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Serj"))
                .andExpect(jsonPath("$.lastName").value("Petrenko"))
                .andExpect(jsonPath("$.birthdate").value("2001-10-18"))
                .andExpect(jsonPath("$.address").value("Vul, 2, Kiyv, Ukraine"))
                .andExpect(jsonPath("$.phoneNumber").value("+380932541254"));
    }

    @Test
    public void testSearchUsersByBirthdateRange_ShouldReturnAllUsersWithBirthdateInsideTheRange() throws Exception {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(1996, 12, 31);

        User user1 = new User();
        user1.setId("ID1");
        user1.setEmail("mail@gmail.com");
        user1.setFirstName("Serj");
        user1.setLastName("Petrenko");
        user1.setBirthdate(LocalDate.of(1997, 6, 15));
        user1.setAddress("Vul, 2, Kiyv, Ukraine");
        user1.setPhoneNumber("+380932541254");

        User user2 = new User();
        user2.setId("ID4");
        user2.setEmail("ivan.sokolov@yahoo.com");
        user2.setFirstName("Ivan");
        user2.setLastName("Sokolov");
        user2.setBirthdate(LocalDate.of(1992, 4, 20));
        user2.setAddress("Vul, 10, Odessa, Ukraine");
        user2.setPhoneNumber("+380631234567");

        User user3 = new User();
        user3.setId("ID2");
        user3.setEmail("alex@mail.ua");
        user3.setFirstName("Alex");
        user3.setLastName("Ivanenko");
        user3.setBirthdate(LocalDate.of(2000, 3, 25));
        user3.setAddress("Vul, 5, Kharkiv, Ukraine");
        user3.setPhoneNumber("+380971234567");

        User user4 = new User();
        user4.setId("ID3");
        user4.setEmail("olena.kovalenko@gmail.com");
        user4.setFirstName("Olena");
        user4.setLastName("Kovalenko");
        user4.setBirthdate(LocalDate.of(1994, 8, 10));
        user4.setAddress("Vul, 3, Lviv, Ukraine");
        user4.setPhoneNumber("+380501234567");

        when(userService.searchUsers(fromDate, toDate)).thenReturn(Arrays.asList(user4, user2));

        mockMvc.perform(get("/users/search")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("ID3"))
                .andExpect(jsonPath("$[0].email").value("olena.kovalenko@gmail.com"))
                .andExpect(jsonPath("$[0].firstName").value("Olena"))
                .andExpect(jsonPath("$[0].lastName").value("Kovalenko"))
                .andExpect(jsonPath("$[0].birthdate").value("1994-08-10"))
                .andExpect(jsonPath("$[0].address").value("Vul, 3, Lviv, Ukraine"))
                .andExpect(jsonPath("$[0].phoneNumber").value("+380501234567"))
                .andExpect(jsonPath("$[1].id").value("ID4"))
                .andExpect(jsonPath("$[1].email").value("ivan.sokolov@yahoo.com"))
                .andExpect(jsonPath("$[1].firstName").value("Ivan"))
                .andExpect(jsonPath("$[1].lastName").value("Sokolov"))
                .andExpect(jsonPath("$[1].birthdate").value("1992-04-20"))
                .andExpect(jsonPath("$[1].address").value("Vul, 10, Odessa, Ukraine"))
                .andExpect(jsonPath("$[1].phoneNumber").value("+380631234567"));
    }

    @Test
    void updateUser() {
    }

    @Test
    void partialUpdateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void handleValidationExceptions() {
    }

    @Test
    void handleIneligibleUserAgeException() {
    }

    @Test
    void handleIncorrectSearchDateRangeException() {
    }

    @Test
    void handleNotReadableException() {
    }

    @Test
    void handleDataAccessResourceFailureException() {
    }

    @Test
    void handleOtherException() {
    }
}
