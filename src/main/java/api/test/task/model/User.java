package api.test.task.model;

import api.test.task.annotation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long id;

    @ValidEmail
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate birthDate;

    private String address;
    private String phoneNumber;
}
