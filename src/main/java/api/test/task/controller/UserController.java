package api.test.task.controller;

import api.test.task.exception.IncorrectSearchDateRangeException;
import api.test.task.exception.IneligibleUserAgeException;
import api.test.task.model.ExceptionResponse;
import api.test.task.model.User;
import api.test.task.model.UserUpdateObject;
import api.test.task.service.UserService;
import com.mongodb.client.result.DeleteResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Integer UNPROCESSABLE_ENTITY = 422;
    private static final Integer BAD_REQUEST = 400;
    private static final Integer INTERNAL_SERVER_ERROR = 500;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        Optional<User> user = userService.get(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByBirthdateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {


        List<User> users = userService.searchUsers(fromDate, toDate);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @Valid @RequestBody User user) {
        Optional<User> updatingUser = userService.get(userId);
        if (updatingUser.isPresent()) {
            user.setId(userId);
            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable("userId") String userId,
                                                  @Valid @RequestBody UserUpdateObject userUpdateObject) {
        Optional<User> updatingUser = userService.get(userId);
        if (updatingUser.isPresent()) {
            User user = updatingUser.get();
            user.setId(userId);

            setUserFilledFields(user, userUpdateObject);

            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        Optional<User> user = userService.get(userId);
        if (user.isPresent()) {
            DeleteResult deleteResult = userService.delete(user.get());
            if (deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() > 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the user");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errors);
    }

    @ExceptionHandler(IneligibleUserAgeException.class)
    public ResponseEntity<ExceptionResponse> handleIneligibleUserAgeException(IneligibleUserAgeException e) {
        return ResponseEntity.unprocessableEntity().body(getExceptionResponse(e.getMessage(), UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(IncorrectSearchDateRangeException.class)
    public ResponseEntity<ExceptionResponse> handleIncorrectSearchDateRangeException(IncorrectSearchDateRangeException e) {
        return ResponseEntity.unprocessableEntity().body(getExceptionResponse(e.getMessage(), UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest()
                .body(getExceptionResponse("Invalid request body: %s".formatted(e.getMessage()), BAD_REQUEST));
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ExceptionResponse> handleDataAccessResourceFailureException(DataAccessResourceFailureException e) {
        return ResponseEntity.internalServerError().body(getExceptionResponse("Failed to connect to the database: %s"
                .formatted(e.getMessage()), INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleOtherException(Exception e) {
        return ResponseEntity.internalServerError().body(getExceptionResponse("An error occurred: %s"
                .formatted(e.getMessage()), INTERNAL_SERVER_ERROR));
    }

    private void setUserFilledFields(User user, UserUpdateObject userUpdateObject) {
        if (userUpdateObject.getEmail() != null && !userUpdateObject.getEmail().isEmpty()) {
            user.setEmail(userUpdateObject.getEmail());
        }
        if (userUpdateObject.getFirstName() != null && !userUpdateObject.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateObject.getFirstName());
        }
        if (userUpdateObject.getLastName() != null && !userUpdateObject.getLastName().isEmpty()) {
            user.setLastName(userUpdateObject.getLastName());
        }
        if (userUpdateObject.getBirthdate() != null) {
            user.setBirthdate(userUpdateObject.getBirthdate());
        }
        if (userUpdateObject.getAddress() != null && !userUpdateObject.getAddress().isEmpty()) {
            user.setAddress(userUpdateObject.getAddress());
        }
        if (userUpdateObject.getPhoneNumber() != null && !userUpdateObject.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userUpdateObject.getPhoneNumber());
        }
    }

    private ExceptionResponse getExceptionResponse(String detail, Integer code) {
        return ExceptionResponse.builder()
                .errors(Collections.singletonList(
                        ExceptionResponse.Error.builder()
                                .code(code)
                                .detail(detail)
                                .build()))
                .build();
    }
}
