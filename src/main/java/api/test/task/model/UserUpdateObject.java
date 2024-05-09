package api.test.task.model;

import api.test.task.annotation.ValidOrNullBirthdate;
import api.test.task.annotation.ValidOrNullEmail;
import api.test.task.annotation.ValidOrNullPhone;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateObject {

    @ValidOrNullEmail
    private String email;

    private String firstName;
    private String lastName;

    @ValidOrNullBirthdate
    private LocalDate birthdate;

    private String address;

    @ValidOrNullPhone
    private String phoneNumber;
}
