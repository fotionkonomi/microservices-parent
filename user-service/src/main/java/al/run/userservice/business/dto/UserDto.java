package al.run.userservice.business.dto;

import al.run.userservice.persistence.entity.enums.Gender;
import al.run.userservice.persistence.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long userId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Gender gender;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    private RoleEnum role;

}
