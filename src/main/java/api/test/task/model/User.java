package api.test.task.model;

import api.test.task.annotation.ValidBirthdate;
import api.test.task.annotation.ValidEmail;
import api.test.task.annotation.ValidOrNullPhone;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String id;

    @ValidEmail
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidBirthdate
    private LocalDate birthdate;

    private String address;

    @ValidOrNullPhone
    private String phoneNumber;
}
